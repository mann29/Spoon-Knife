package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentNotificationInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class handles delivery of Appraisal Assignment Notification delivery.
 *  
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class AppraisalAssignmentNotificationHandler implements APDDeliveryHandler { 
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            AppraisalAssignmentNotificationHandler.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private AppLogHelper appLogHelper;
    private APDDeliveryUtil apdDeliveryUtil;
    private PlatformDeliveryHandler platformDeliveryHandler;
    private APDCommonUtilProxy apdCommonUtilProxy;
    
    /**
     * This method will deliver Appraisal Assignment Notification.
     * 
     * @param apdContext 
     * A XML bean of type APDDeliveryContextDocument.
     * Encapsulates the relevant infomation needed to handle delievry.
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument apdContext) 
                                                    throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
        
        APDAppraisalAssignmentNotificationInfoType apdApprAsmtNtfnInfoType = 
            apdContext.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo();
        if (apdApprAsmtNtfnInfoType == null) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_APPR_ASMT_NTFN_INFO_NULL,
                            CLASS_NAME,
                            methodName,
                            UUIDFactory.getInstance().getUUID(),
                            APDDeliveryConstants.ERROR_APPR_ASMT_NTFN_INFO_NULL_MSG);
        }
        
        String workItemId = apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId();
        
        BaseAPDCommonType baseAPDCommonType = apdApprAsmtNtfnInfoType.getAPDCommonInfo();
                            
        // determine the delivery endpoint
        UserInfoType targetUserInfo = 
                                apdApprAsmtNtfnInfoType.getAPDCommonInfo().
                                    getTargetUserInfo().getUserInfo();
        try {
            // determine if target is platform or not
        	// Fix 117663 : using APDDeliveryUtil instance.
            
            boolean isPlatform = apdDeliveryUtil.isEndpointPlatform2(apdApprAsmtNtfnInfoType.getAPDCommonInfo());
            apdCommonUtilProxy.logFINEMessage("Appraisal Assignment Notification Delivery: " 
                    + "Endpoint is Platform? " 
                    + isPlatform);
                    
            // determine if target user is a Shop User or not
            boolean isShopUser = 
            	apdDeliveryUtil.isShopUser(targetUserInfo);
            apdCommonUtilProxy.logFINEMessage("Appraisal Assignment Notification Delivery: " 
                    + "Target is a Shop user? "
                    + isShopUser);
            if (isPlatform) {
                // delivery endpoint is Platform
                if (isShopUser) {
                    // it's a Shop user
                    
                    // deliver Appraisal Assignment Notification to Repair Center shop
                    platformDeliveryHandler.deliverAppraisalAssignmentNotification(apdContext);
                    apdCommonUtilProxy.logFINEMessage("Appraisal Assignment Notification Delivery: "
                                            + "Alert delivered to Platform!!");
                } else {
                    // Target is not a Shop user
                    Map additionalMap = new HashMap();
                    
                    additionalMap.put("MessageType", 
                            apdContext.getAPDDeliveryContext().getMessageType());
                    additionalMap.put("MessageStatus", 
                            apdApprAsmtNtfnInfoType.getMessageStatus());
                    additionalMap.put("NotificationID", 
                            Long.toString(apdApprAsmtNtfnInfoType.getNotificationID()));
                    additionalMap.put("Message", "Target User is not Shop User");
                    additionalMap.put("DateTime", baseAPDCommonType.getDateTime());
                    if (baseAPDCommonType.isSetNotes()) {
                        additionalMap.put("Notes", baseAPDCommonType.getNotes());
                    }
                    additionalMap.put("TargetUserInfo", baseAPDCommonType.getTargetUserInfo());
                    APDDeliveryContext context = 
                    	apdDeliveryUtil.populateContextForAppLog(
                                baseAPDCommonType, 
                                additionalMap,
                                APDDeliveryConstants.USER_IS_NOT_SHOP_USER);
                    UserInfoDocument sourceUserInfoDoc = 
                                    UserInfoDocument.Factory.newInstance();
                    sourceUserInfoDoc.setUserInfo(
                                    apdApprAsmtNtfnInfoType.getAPDCommonInfo().
                                        getSourceUserInfo().getUserInfo());
                    context.setUserId(
                            sourceUserInfoDoc.getUserInfo().getUserID());
                    // App logging
                    // Fix 117663 : using appLogHelper for applogging.
                    appLogHelper.doAppLog(context, sourceUserInfoDoc);
                    apdCommonUtilProxy.logFINEMessage(
                        "Appraisal Assignment Notification Delivery: " 
                            + "Appraisal Assignment Notification not delivered to Platform for non-shop user");
                }
            } else {
                // delivery endpoint is non Platform
                if (!isShopUser) {
                    throw new MitchellException(
                            APDDeliveryConstants.ERROR_UNSUPPORTED_USER_TYPE,
                            CLASS_NAME,
                            methodName,
                            workItemId,
                            APDDeliveryConstants.ERROR_UNSUPPORTED_USER_TYPE_MSG);
                }
            }
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_APPR_ASMT_NTFN_DELIVERY,
                            CLASS_NAME,
                            methodName,
                            workItemId,
                            APDDeliveryConstants.ERROR_APPR_ASMT_NTFN_DELIVERY_MSG 
                                + "\n" 
                                + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
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

	
} 
