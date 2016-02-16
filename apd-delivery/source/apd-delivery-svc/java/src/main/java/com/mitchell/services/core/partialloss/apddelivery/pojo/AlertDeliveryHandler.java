package com.mitchell.services.core.partialloss.apddelivery.pojo;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.*;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ApdJdbcDao;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ECAlertProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserData;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.schemas.apddelivery.APDMCFPackageTyp.Enum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class delivers the following types of alerts in response to uploads
 * initiated by desktop clients (eClaim, UM/MCM, RC).
 * <ul>
 * <li>Upload accepted
 * <li>Upload rejected
 * <li>Global alerts
 * </ul>
 * <p>
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class AlertDeliveryHandler implements APDDeliveryHandler
{

  /**
   * class name.
   */
  private static final String CLASS_NAME = AlertDeliveryHandler.class.getName();
  /**
   * logger instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  private ApdJdbcDao dao;

  private ADSProxy adsProxy;

  private ECAlertProxy ecAlertProxy;

  private AppLogHelper appLogHelper;

  private UserHelper userHelper;

  private DaytonaDeliveryHandler  daytonaDeliveryHandler;

  //Fix 117663
  private APDDeliveryUtil apdDeliveryUtil;
  private PlatformDeliveryHandler platformDeliveryHandler;
  private APDCommonUtilProxy apdCommonUtilProxy;

  /**
   * This method will deliver alerts to legacy clients like eClaim/MCM and
   * also to Repair Center. For delivering alerts to eClaim/MCM this will
   * delegate calls to eClaim alert service and for delivering to
   * Repair Center this will delegate to PlatformDeliveryHandler.
   * 
   * @param apdContext
   *          A XML bean of type APDDeliveryContextDocument.
   *          Encapsulates the relevant infomation needed to handle alert
   *          delievry.
   * @throws MitchellException
   *           Mitchell Exception
   */
  public void deliverArtifact(APDDeliveryContextDocument apdContext)
      throws MitchellException
  {
    String methodName = "deliverArtifact";
    logger.entering(CLASS_NAME, methodName);

    APDAlertInfoType apdAlertInfoType = apdContext.getAPDDeliveryContext()
        .getAPDAlertInfo();
    if (apdAlertInfoType == null) {
      throw new MitchellException(CLASS_NAME, methodName,
          APDDeliveryConstants.ERROR_ALERTS_INFO_NULL);
    }

    try {

      BaseAPDCommonType baseAPDCommonType = apdAlertInfoType.getAPDCommonInfo();

      // determine the delivery endpoint
      UserInfoType targetUserInfo = apdAlertInfoType.getAPDCommonInfo()
          .getTargetUserInfo().getUserInfo();
      APDUserInfoType targetDrpUserInfo = null;

    // if endpoint is RCWeb / Daytona
    if (apdDeliveryUtil.isEndpointRCWeb(baseAPDCommonType)) {
        daytonaDeliveryHandler.deliverAlert(apdContext);
        return;
    }

      // if End point is Platform

      boolean isPlatform = apdDeliveryUtil
          .isEndpointPlatformWithOverride(baseAPDCommonType)
          && !apdDeliveryUtil.isEndpointRCWeb(baseAPDCommonType);

      apdCommonUtilProxy
          .logFINEMessage("Alert Delivery: Is Endpoint Platform?  "
              + isPlatform);

      // determine if target user is a Shop User or IA
      String targetUserType = apdDeliveryUtil.getUserType(
          targetUserInfo.getOrgCode(), targetUserInfo.getUserID());
      // is Shop?
      boolean shopUser = UserTypeConstants.SHOP_TYPE.equals(targetUserType)
          || UserTypeConstants.DRP_SHOP_TYPE.equals(targetUserType);

      // is IA?
      boolean indAdj = UserTypeConstants.IA_TYPE.equals(targetUserType)
          || UserTypeConstants.DRP_IA_TYPE.equals(targetUserType);
      apdCommonUtilProxy
          .logFINEMessage("Alert Delivery: Target is a Shop user or IA? "
              + (shopUser || indAdj));

      // is Staff?
      boolean staffUser = UserTypeConstants.STAFF_TYPE.equals(targetUserType);

      if (isPlatform) {

        // Moved the validation to this step, to handle an existing standalone non-staff UM issue
        // wherein the reviewer id is not passed or bogus id.

        // validate DRP if it is a shop user            
        if (shopUser || indAdj) {
          targetDrpUserInfo = baseAPDCommonType.getTargetDRPUserInfo();
          if (targetDrpUserInfo == null) {
            throw new Exception(
                APDDeliveryConstants.ERROR_TARGET_DRP_USERINFO_NULL_MSG);
          }
        }

        // deleivery endpoint is Platform

        if (shopUser || indAdj || staffUser) {
          // it's a Shop user
          // deliver alert to Repair Center shop
          platformDeliveryHandler
              .deliverAlert(apdContext, (shopUser || indAdj));
          apdCommonUtilProxy
              .logFINEMessage("Alert Delivery: Alert delivered to Platform");
        } else {
          // Target is not a Shop user
          Map additionalMap = new HashMap();
          additionalMap.put("MessageType", apdContext.getAPDDeliveryContext()
              .getMessageType());
          additionalMap.put("AlertType", apdAlertInfoType.getAlertType()
              .toString());
          additionalMap.put("Message", "Target User is not Shop/Staff User");
          additionalMap.put("DateTime", baseAPDCommonType.getDateTime());
          if (baseAPDCommonType.isSetNotes()) {
            additionalMap.put("Notes", baseAPDCommonType.getNotes());
          }
          additionalMap.put("TargetUserInfo",
              baseAPDCommonType.getTargetUserInfo());
          APDDeliveryContext context = apdDeliveryUtil
              .populateContextForAppLog(baseAPDCommonType, additionalMap,
                  APDDeliveryConstants.USER_IS_NOT_SHOP_USER);
          UserInfoDocument sourceUserInfoDoc = UserInfoDocument.Factory
              .newInstance();
          sourceUserInfoDoc.setUserInfo(apdAlertInfoType.getAPDCommonInfo()
              .getSourceUserInfo().getUserInfo());
          context.setUserId(sourceUserInfoDoc.getUserInfo().getUserID());
          // AppLogging
          appLogHelper.doAppLog(context, sourceUserInfoDoc);
          apdCommonUtilProxy
              .logFINEMessage("Alert Delivery: Alert not delivered to Platform for non-shop/non-staff user");
        }
      } else {

        if (shopUser) {

          final boolean isHideNonDrpSolution = userHelper
              .isHideNonNetworkSolution();

          if (!isHideNonDrpSolution) {

            final UserInfoDocument userInfoToCheck = UserInfoDocument.Factory
                .newInstance();
            userInfoToCheck.setUserInfo(apdContext.getAPDDeliveryContext()
                .getAPDAlertInfo().getAPDCommonInfo().getTargetUserInfo()
                .getUserInfo());
            final UserData userData = this.userHelper
                .checkUserData(userInfoToCheck);

            if (userData != null
                && (userData.isShopBasic() || userData.isShopPremium())) {
              handleNonNetworkShop(apdContext, userData);
            }
          }
        }

        // delivery endpoint is non Platform
        EClaimAlertRequestDocument doc = null;
        UserInfoType userInfo = null;

        // validate DRP if it is a shop user            
        if (shopUser || indAdj) {
          targetDrpUserInfo = baseAPDCommonType.getTargetDRPUserInfo();

          // if targetdrpuserinfo is null , use the targetuserinfo instead to handle the standalone non-staff UM issue
          if (targetDrpUserInfo == null) {
            userInfo = targetUserInfo;
          } else {
            // if target user is a shop user proceed with target DRP user
            userInfo = targetDrpUserInfo.getUserInfo();
          }
        }
        // if Staff - use Target UserInfo
        else {
          userInfo = targetUserInfo;
        }

        // check Alert type
        APDAlertType.Enum alertType = apdAlertInfoType.getAlertType();
        apdCommonUtilProxy.logFINEMessage("Alert Delivery: Alert type is "
            + alertType.toString());

        if (APDAlertType.GLOBAL.equals(alertType)) {
          // Alert Type is GLOBAL
          // extract other information from apdAlertInfoType
          String origin = apdAlertInfoType.getOrigin();
          String message = apdAlertInfoType.getMessage();
          String workItemId = apdAlertInfoType.getAPDCommonInfo()
              .getWorkItemId();

          // Fix 117663: using ecAlertProxy for ECAlertSvcClient.
          ecAlertProxy
              .deliverGlobalAlert(userInfo, workItemId, origin, message);

          apdCommonUtilProxy.logFINEMessage("Alert Delivery: "
              + alertType.toString() + " Alert delivered");
        } else if (APDAlertType.ACCEPTED.equals(alertType)) {
          // Alert Type is ACCEPTED
          // create EClaimAlertRequest
          doc = apdDeliveryUtil.createAlertRequest(apdContext, userInfo);
          // Fix 117663 : using ecAlertProxy for ECAlertSvcClient.
          ecAlertProxy.sendUploadAcceptedAlert(doc);
          apdCommonUtilProxy.logFINEMessage("Alert Delivery: "
              + alertType.toString() + " Alert delivered");
        } else if (APDAlertType.REJECTED.equals(alertType)) {
          // Alert Type is REJECTED
          // create EClaimAlertRequest
          doc = apdDeliveryUtil.createAlertRequest(apdContext, userInfo);

          // Fix 117663 : using ecAlertProxy for ECAlertSvcClient.
          ecAlertProxy.sendUploadRejectedAlert(doc);
          apdCommonUtilProxy.logFINEMessage("Alert Delivery: "
              + alertType.toString() + " Alert delivered");
        }
      }
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(APDDeliveryConstants.ERROR_ALERT_DELIVERY,
          CLASS_NAME, methodName, apdContext.getAPDDeliveryContext()
              .getAPDAlertInfo().getAPDCommonInfo().getWorkItemId(),
          APDDeliveryConstants.ERROR_ALERT_DELIVERY_MSG + "\n"
              + AppUtilities.getStackTraceString(e));
    }

    logger.exiting(CLASS_NAME, methodName);
  }

  /**
   * This method is never used in Alert's context.
   * 
   * @param context
   *          An instance of APDDeliveryContextDocument
   */
  public void deliverArtifactNotification(APDDeliveryContextDocument context)
  {
    //no-op
  }

  /**
   * @param context APDDeliveryContextDocument
   * @param attachments ArrayList
   * @throws MitchellException MitchellException
   */
  public void deliverArtifact(APDDeliveryContextDocument context,
      ArrayList attachments)
      throws MitchellException
  {
    // No Operation.

  }

  public boolean isNonNetworkShop(APDDeliveryContextDocument apdDelContext)
      throws MitchellException
  {
    boolean isNonNetworkShop = false;

    final String orgId = apdDelContext.getAPDDeliveryContext()
        .getAPDAlertInfo().getAPDCommonInfo().getTargetUserInfo().getUserInfo()
        .getOrgID();

    final String flag = dao.getNetworkFlag(orgId);

    if (flag == null || flag.trim().equalsIgnoreCase("n")) {
      isNonNetworkShop = true;
    }
    return isNonNetworkShop;
  }

  public boolean isUserHasEmail(APDDeliveryContextDocument apdDelContext)
  {
    boolean hasEmail = false;

    final String email = apdDelContext.getAPDDeliveryContext()
        .getAPDAlertInfo().getAPDCommonInfo().getTargetUserInfo().getUserInfo()
        .getEmail();

    if (email != null && email.trim().length() > 0) {

      hasEmail = true;
    }

    return hasEmail;
  }

  /**
 * @param apdContext
 * @param userData
 * @throws MitchellException
 * Email Notification will send if mcfPackageTypes are MCF_XML_MIE_ESTIMATE , MCF_XML_EMS_ADP_ESTIMATE and MCF_XML_EMS_CCC_ESTIMATE
 */
private void handleNonNetworkShop(
      final APDDeliveryContextDocument apdContext, final UserData userData)
      throws MitchellException
  {

    final APDAlertType.Enum alertType = apdContext.getAPDDeliveryContext()
        .getAPDAlertInfo().getAlertType();
    
    Enum mcfPackageType = apdContext.getAPDDeliveryContext().getAPDAlertInfo().getMcfPackageType();

		if (APDAlertType.ACCEPTED.equals(alertType)) {
			
			// Email Notification will send if mcfPackageTypes are not matched
		
			if (!APDMCFPackageTyp.MCF_XML_DIARY.equals(mcfPackageType)
					&& !APDMCFPackageTyp.MCF_XML_ATTACHMENTS
							.equals(mcfPackageType)) {
				if (userData.isShopPremium()) {
					adsProxy.deliverUploadSuccessEmail4DRP(apdContext);
				} else {
					adsProxy.deliverUploadSuccessEmail(apdContext);
				}
			}
		} else if (APDAlertType.REJECTED.equals(alertType)) {
			if (userData.isShopPremium()) {
				adsProxy.deliverUploadFailEmail4DRP(apdContext);
			} else {
				adsProxy.deliverUploadFailEmail(apdContext);
			}
		}
	}

  /**
   * @return the dao
   */
  public ApdJdbcDao getDao()
  {
    return dao;
  }

  /**
   * @param dao the dao to set
   */
  public void setDao(ApdJdbcDao dao)
  {
    this.dao = dao;
  }

  /**
   * @return the adsProxy
   */
  public ADSProxy getAdsProxy()
  {
    return adsProxy;
  }

  /**
   * @param adsProxy the adsProxy to set
   */
  public void setAdsProxy(ADSProxy adsProxy)
  {
    this.adsProxy = adsProxy;
  }

  /**
   * @return the ecAlertProxy
   */
  public ECAlertProxy getEcAlertProxy()
  {
    return ecAlertProxy;
  }

  /**
   * @param ecAlertProxy the ecAlertProxy to set
   */
  public void setEcAlertProxy(ECAlertProxy ecAlertProxy)
  {
    this.ecAlertProxy = ecAlertProxy;
  }

  /**
   * @return the appLogHelper
   */
  public AppLogHelper getAppLogHelper()
  {
    return appLogHelper;
  }

  /**
   * @param appLogHelper the appLogHelper to set
   */
  public void setAppLogHelper(AppLogHelper appLogHelper)
  {
    this.appLogHelper = appLogHelper;
  }

  /**
   * @return the apdDeliveryUtil
   */
  public APDDeliveryUtil getApdDeliveryUtil()
  {
    return apdDeliveryUtil;
  }

  /**
   * @param apdDeliveryUtil the apdDeliveryUtil to set
   */
  public void setApdDeliveryUtil(APDDeliveryUtil apdDeliveryUtil)
  {
    this.apdDeliveryUtil = apdDeliveryUtil;
  }

  /**
   * @return the platformDeliveryHandler
   */
  public PlatformDeliveryHandler getPlatformDeliveryHandler()
  {
    return platformDeliveryHandler;
  }

  /**
   * @param platformDeliveryHandler the platformDeliveryHandler to set
   */
  public void setPlatformDeliveryHandler(
      PlatformDeliveryHandler platformDeliveryHandler)
  {
    this.platformDeliveryHandler = platformDeliveryHandler;
  }

  /**
   * @return the apdCommonUtilProxy
   */
  public APDCommonUtilProxy getApdCommonUtilProxy()
  {
    return apdCommonUtilProxy;
  }

  /**
   * @param apdCommonUtilProxy the apdCommonUtilProxy to set
   */
  public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy)
  {
    this.apdCommonUtilProxy = apdCommonUtilProxy;
  }

  public void setUserHelper(UserHelper userHelper)
  {
    this.userHelper = userHelper;
  }

    public void setDaytonaDeliveryHandler(DaytonaDeliveryHandler daytonaDeliveryHandler) {
        this.daytonaDeliveryHandler = daytonaDeliveryHandler;
    }
}
