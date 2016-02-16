package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDRepairAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDUserInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.utils.misc.AppUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles delivery of Repair Assignment and Rework Assignment.
 *  
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class RepairAssignmentDeliveryHandler implements APDDeliveryHandler { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            RepairAssignmentDeliveryHandler.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private AppLogHelper appLogHelper;
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private APDDeliveryUtil apdDeliveryUtil;
    
    private PlatformDeliveryHandler platformDeliveryHandler;
    
    private EmailDeliveryHandler emailDeliveryHandler;
    
    private APDCommonUtilProxy apdCommonUtilProxy;

    private ADSProxy adsProxy;
    
    /**
     * This method will deliver Repair/Rework Assignment to legacy clients like
     * eClaim/MCM and also to Repair Center. 
     * For delivering Repair/Rework Assignment to eClaim/MCM this will delegate
     * calls to EmailDeliveryHandler.deliverRepairAssignment() and
     * delivering to Repair Center this will delegate to PlatformDeliveryHandler.
     * 
     * @param apdContext 
     * A XML bean of type APDDeliveryContextDocument.
     * Encapsulates the relevant infomation needed to handle alert delievry.
     * 
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument apdContext) 
                                                    throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
        
        APDRepairAssignmentInfoType apdRAInfoType = 
            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
        if (apdRAInfoType == null) {
            throw new MitchellException(CLASS_NAME,
                                methodName,
                                APDDeliveryConstants.ERROR_RA_INFO_NULL);
        }
        
        BaseAPDCommonType baseAPDCommonType = apdRAInfoType.getAPDCommonInfo();
                            
        // determine the delivery endpoint
        UserInfoType targetUserInfo = 
                                apdRAInfoType.getAPDCommonInfo().
                                    getTargetUserInfo().getUserInfo();
        
    	try {
            // if End point is RCWeb deliver to RCWeb
            if (apdDeliveryUtil.isEndpointRCWeb(baseAPDCommonType)) {
                handleRCWebDelivery(apdContext);
                return;
            }
        	
            boolean isPlatform = apdDeliveryUtil.isEndpointPlatform2(apdRAInfoType.getAPDCommonInfo());
            String ignoreRCAppCode = systemConfigurationProxy.getIgnoreRCApplicationCode();
            if (apdDeliveryUtil.evaluateUserInfoApplicationCode(baseAPDCommonType, ignoreRCAppCode)) {
            	isPlatform = false;
            }
            
            apdCommonUtilProxy.logINFOMessage("Repair/Rework Assignment Delivery: Is Endpoint Platform?  " 
                                                                + isPlatform);
            // determine if target user is a Shop User or not
            boolean isShopUser = 
            	apdDeliveryUtil.isShopUser(targetUserInfo);
            apdCommonUtilProxy.logFINEMessage("Repair/Rework Assignment Delivery: Target is a Shop user? ");
            if (isPlatform) {
                // delivery endpoint is Platform
                if (isShopUser) {
                    // it's a Shop user
                    APDUserInfoType targetDrpUserInfo = 
                                    baseAPDCommonType.getTargetDRPUserInfo();
                    if (targetDrpUserInfo == null) {
                        throw new Exception(
                                APDDeliveryConstants.
                                ERROR_TARGET_DRP_USERINFO_NULL_MSG);
                    }
                    
                    // deliver alert to Repair Center shop
                    platformDeliveryHandler.deliverRepairAssignment(apdContext);
                    apdCommonUtilProxy.logFINEMessage("Repair/Rework Assignment Delivery: "
                                            + "Alert delivered to Platform");
                } else {
                    // Target is not a Shop user
                    Map additionalMap = new HashMap();
                    additionalMap.put("MessageType", 
                        apdContext.getAPDDeliveryContext().getMessageType());
                    additionalMap.put("Type", 
                        apdContext.getAPDDeliveryContext().getMessageType());
                    additionalMap.put("Message", 
                                        "Target User is not Shop User");
                    additionalMap.put("DateTime", 
                                        baseAPDCommonType.getDateTime());
                    if (baseAPDCommonType.isSetNotes()) {
                        additionalMap.put("Notes", 
                                        baseAPDCommonType.getNotes());
                    }
                    additionalMap.put("TargetUserInfo", 
                                        baseAPDCommonType.getTargetUserInfo());
                    APDDeliveryContext context = 
                    	apdDeliveryUtil.populateContextForAppLog(
                                baseAPDCommonType, 
                                additionalMap,
                                APDDeliveryConstants.USER_IS_NOT_SHOP_USER);
                    UserInfoDocument sourceUserInfoDoc = 
                                    UserInfoDocument.Factory.newInstance();
                    sourceUserInfoDoc.setUserInfo(
                                    apdRAInfoType.getAPDCommonInfo().
                                        getSourceUserInfo().getUserInfo());
                    context.setUserId(
                            sourceUserInfoDoc.getUserInfo().getUserID());
                    // App logging
                    // Fix 117663 : use appLogHelper for applogging.
                    // apdDeliveryUtil.doAppLog(context, sourceUserInfoDoc);
                    
                    appLogHelper.doAppLog(context, sourceUserInfoDoc);
                    apdCommonUtilProxy.logFINEMessage(
                        "Repair/Rework Assignment Delivery: Repair/Rework not delivered to Platform for non-shop user");
                }
            } else {
                // delivery endpoint is non Platform
                
                if (isShopUser) {
                    // it's a Shop user
                    /*
                    APDUserInfoType targetDrpUserInfo = 
                                    baseAPDCommonType.getTargetDRPUserInfo();
                    if (targetDrpUserInfo == null) {
                        throw new Exception(
                            APDDeliveryConstants.
                                ERROR_TARGET_DRP_USERINFO_NULL_MSG);
                    }
                    // if target user is a shop user proceed with target DRP user
                    userInfo = targetDrpUserInfo.getUserInfo();*/
                    
                    // Check if AssignmentEmailEnabled is true in SET file.
                	// Fix 117663 : using systemConfigurationProxy for APDDeliverySystemConfig
                	if (APDDeliveryConstants.TRUE.equalsIgnoreCase(systemConfigurationProxy.getAssignmentEmailEnabled())) {                        
                        /**
                         * If email id present in target user info then call EmailDeliveryHandler to send mail.
                         */
                        if (targetUserInfo != null && targetUserInfo.isSetEmail()) {
                            
                        	apdCommonUtilProxy.logINFOMessage("Calling EmailDeliveryHandler to deliver email..");
                            
                            emailDeliveryHandler.deliverRepairAssignment(apdContext);
                            
                        } else { // else do applog that no email id found for the shop.
                            
                            Map additionalMap = new HashMap();
                            
                            additionalMap.put("MessageType", apdContext.getAPDDeliveryContext().getMessageType());
                                
                            additionalMap.put("RequestType", 
                                apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo().getMessageStatus());
                                
                            additionalMap.put("Message", "Email Id not found for shop");
                            
                            additionalMap.put("TASK_ID", String.valueOf(apdContext.getAPDDeliveryContext()
                                                                        .getAPDRepairAssignmentInfo().getTaskId()));
                            
                            if (baseAPDCommonType.isSetNotes()) {
                                
                                additionalMap.put("Notes", baseAPDCommonType.getNotes());
                                
                            }
                            
                            APDDeliveryContext context = apdDeliveryUtil.populateContextForAppLog(
                                                                        baseAPDCommonType, 
                                                                        additionalMap,
                                                                        APDDeliveryConstants.EMAIL_ID_NOT_FOUND_FOR_SHOP);
                                                                        
                            UserInfoDocument sourceUserInfoDoc = UserInfoDocument.Factory.newInstance();
                            
                            sourceUserInfoDoc.setUserInfo(apdRAInfoType.getAPDCommonInfo().getSourceUserInfo().getUserInfo());
                            
                            context.setUserId(sourceUserInfoDoc.getUserInfo().getUserID());
                            
                            context.setWorkItemId(baseAPDCommonType.getWorkItemId());
                            
                            // App logging
                            // Fix 117663 : using appLogHelper for applogging.
                            //apdDeliveryUtil.doAppLog(context, sourceUserInfoDoc);
                            appLogHelper.doAppLog(context, sourceUserInfoDoc);
                            apdCommonUtilProxy.logINFOMessage("Repair/Rework Notification : Email NOT sent as the email id is NOT found");
                                
                        }
                    } else {
                    	apdCommonUtilProxy.logINFOMessage("Repair/Rework Assignment Delivery: Email for Non RC shop is not configured");
                        
                    }                    
                    
                } else {
                    //userInfo = targetUserInfo;
                    throw new MitchellException(CLASS_NAME, methodName, "Unsupported user type ????");
                }
                
            }
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_RA_DELIVERY,
                            CLASS_NAME,
                            methodName,
                            baseAPDCommonType.getWorkItemId(),
                            APDDeliveryConstants.ERROR_RA_DELIVERY_MSG 
                                + "\n" 
                                + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }

    private void handleRCWebDelivery(APDDeliveryContextDocument context) throws Exception {
        String methodName = "handleRCWebDelivery";
        this.logger.entering(CLASS_NAME, methodName);

        this.apdCommonUtilProxy.logINFOMessage("End point is RC Web. Handling RC Web delivery");

        this.logger.exiting(CLASS_NAME, methodName);
        this.adsProxy.deliverRCWebAssignment(context);
    }

    /**
     * This method is never used.
     * 
     * @param context
     * An instance of APDDeliveryContextDocument.
     */
    public void deliverArtifactNotification(APDDeliveryContextDocument context) {
        //no-op
    }
    
    /**
     * @param context APDDeliveryContextDocument
     * @param attachments ArrayList
     * @throws MitchellException  MitchellException
     */
    public void deliverArtifact(APDDeliveryContextDocument context,
                                                   ArrayList attachments)
                                                        throws MitchellException {
        // No Operation.
        
    }

	/**
	 * @return the appLogHelper
	 */
	public AppLogHelper getAppLogHelper() {
		return appLogHelper;
	}

	/**
	 * @param appLogHelper the appLogHelper to set
	 */
	public void setAppLogHelper(AppLogHelper appLogHelper) {
		this.appLogHelper = appLogHelper;
	}

	/**
	 * @return the systemConfigurationProxy
	 */
	public SystemConfigurationProxy getSystemConfigurationProxy() {
		return systemConfigurationProxy;
	}

	/**
	 * @param systemConfigurationProxy the systemConfigurationProxy to set
	 */
	public void setSystemConfigurationProxy(
			SystemConfigurationProxy systemConfigurationProxy) {
		this.systemConfigurationProxy = systemConfigurationProxy;
	}

	/**
	 * @return the apdDeliveryUtil
	 */
	public APDDeliveryUtil getApdDeliveryUtil() {
		return apdDeliveryUtil;
	}

	/**
	 * @param apdDeliveryUtil the apdDeliveryUtil to set
	 */
	public void setApdDeliveryUtil(APDDeliveryUtil apdDeliveryUtil) {
		this.apdDeliveryUtil = apdDeliveryUtil;
	}

	/**
	 * @return the platformDeliveryHandler
	 */
	public PlatformDeliveryHandler getPlatformDeliveryHandler() {
		return platformDeliveryHandler;
	}

	/**
	 * @param platformDeliveryHandler the platformDeliveryHandler to set
	 */
	public void setPlatformDeliveryHandler(
			PlatformDeliveryHandler platformDeliveryHandler) {
		this.platformDeliveryHandler = platformDeliveryHandler;
	}

	/**
	 * @return the emailDeliveryHandler
	 */
	public EmailDeliveryHandler getEmailDeliveryHandler() {
		return emailDeliveryHandler;
	}

	/**
	 * @param emailDeliveryHandler the emailDeliveryHandler to set
	 */
	public void setEmailDeliveryHandler(EmailDeliveryHandler emailDeliveryHandler) {
		this.emailDeliveryHandler = emailDeliveryHandler;
	}

	/**
	 * @return the apdCommonUtilProxy
	 */
	public APDCommonUtilProxy getApdCommonUtilProxy() {
		return apdCommonUtilProxy;
	}

	/**
	 * @param apdCommonUtilProxy the apdCommonUtilProxy to set
	 */
	public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy) {
		this.apdCommonUtilProxy = apdCommonUtilProxy;
	}

    public ADSProxy getAdsProxy() {
        return adsProxy;
    }

    public void setAdsProxy(ADSProxy adsProxy) {
        this.adsProxy = adsProxy;
    }
}
