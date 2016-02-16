package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDEstimateStatusInfoType;
import com.mitchell.schemas.apddelivery.APDUserInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
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
 * 
 */
public class EstimateStatusDeliveryHandler implements APDDeliveryHandler { 
    /**
     * class name.
     */
    private static final String CLASS_NAME = EstimateStatusDeliveryHandler.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private AppLogHelper appLogHelper;
    private PlatformDeliveryHandler platformDeliveryHandler;
    private APDDeliveryUtil apdDeliveryUtil;
    private APDCommonUtilProxy apdCommonUtilProxy;
    /**
     * @param apdDeliveydoc
     * Encapsulates the relevant infomation needed to handle EstimateStatus delievry.
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument apdDeliveydoc) throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
       
    
        // determine the delivery endpoint
        UserInfoDocument sourceUserInfoDoc = null;
        
        APDDeliveryContextDocument apdDeliverydocument = null;
        apdDeliverydocument = apdDeliveydoc;
        // Fix 117663 : using APDDeliveryUtil instance.
        
        APDDeliveryContext apdDeliveryContext = null;
        
        // validate APDDeliveryContext
        if (apdDeliveydoc == null) {
            throw new MitchellException(
                                CLASS_NAME, 
                                methodName, 
                                "APDDeliveryContext Document  is null ?????");
        }
        
        APDEstimateStatusInfoType apdEstimateStatusInfoType = 
            apdDeliverydocument.getAPDDeliveryContext().getAPDEstimateStatusInfo();
                                                                        
       
        // validate if EstimateStatusInfo is not null                                                      
        if (apdEstimateStatusInfoType == null) {
            throw new MitchellException(
                                    CLASS_NAME, 
                                    methodName, 
                                    "APDEstimateStatusInfo is null ????");
        }
        BaseAPDCommonType baseAPDCommonType = apdEstimateStatusInfoType.getAPDCommonInfo();
            
        // determine if target user is a Shop User or not
        UserInfoType targetUserInfo = baseAPDCommonType.getTargetUserInfo().getUserInfo();
        UserInfoType sourceUserInfo = baseAPDCommonType.getSourceUserInfo().getUserInfo();
      
        String cmpCode = sourceUserInfo.getOrgCode();                  
        String sourceUserID = sourceUserInfo.getUserID();
        sourceUserInfoDoc = apdDeliveryUtil.getUserInfo(
                                                    cmpCode, 
                                                    sourceUserID, 
                                                    null);
            
        Map hashMap = new HashMap();
        hashMap.put("DateTime", baseAPDCommonType.getDateTime());
        if (baseAPDCommonType.isSetNotes()) {
            hashMap.put("Notes", baseAPDCommonType.getNotes());
        }
        hashMap.put("TargetUserInfo", baseAPDCommonType.getTargetUserInfo());
        //hashMap.put("TargetDRPUserInfo", baseAPDCommonType.getTargetDRPUserInfo());
        hashMap.put("MessageType", 
                            apdDeliveydoc.getAPDDeliveryContext().getMessageType());
        hashMap.put("EstimateStatusEvent", 
                            apdEstimateStatusInfoType.getEstimateStatusEvent().toString());
        
        apdCommonUtilProxy.logFINEMessage("About to check if the user is platform");                                
        try {        	
            boolean isPlatform = apdDeliveryUtil.isEndpointPlatformWithOverride(apdEstimateStatusInfoType.getAPDCommonInfo()) && !apdDeliveryUtil.isEndpointRCWeb(baseAPDCommonType);
            apdCommonUtilProxy.logFINEMessage("The user is Platform : " + isPlatform);


            if (isPlatform) {    // deleivery endpoint is Platform
                boolean isShopUser = apdDeliveryUtil.isShopUser(targetUserInfo);    
                if (isShopUser) { 
                    APDUserInfoType targetDrpUserInfo = 
                                    baseAPDCommonType.getTargetDRPUserInfo();
                    if (targetDrpUserInfo == null) {
                        throw new Exception(
                                APDDeliveryConstants.
                                ERROR_TARGET_DRP_USERINFO_NULL_MSG);
                    }
                  
                    // user is shop User and deleivery endpoint is Platform
                   
                    // deliver  Estimate Staus to Repair Center shop
                    platformDeliveryHandler.deliverEstimateStatus(apdDeliveydoc);
                    apdCommonUtilProxy.logFINEMessage("Estimate Status : "
                                            + "delivered to Platform");
                } else {
                    hashMap.put("Message", "Target User is not Shop User");
                    apdDeliveryContext = 
                    	apdDeliveryUtil.populateContextForAppLog(
                            baseAPDCommonType,
                            hashMap,
                            APDDeliveryConstants.USER_IS_NOT_SHOP_USER);  
                    apdCommonUtilProxy.logFINEMessage("Context before passing to AppLog" 
                                            + apdDeliveryContext.toString());
                    //apdDeliveryUtil.doAppLog(apdDeliveryContext, sourceUserInfoDoc);
                    appLogHelper.doAppLog(apdDeliveryContext, sourceUserInfoDoc);
                }      
            } else {
                hashMap.put("Message", "Delivery end point is non platform");                                                                          
                apdDeliveryContext = apdDeliveryUtil.populateContextForAppLog(
                        baseAPDCommonType, 
                        hashMap, 
                        APDDeliveryConstants.DELIVERY_END_POINT_NON_PLATFORM);
                        
                apdCommonUtilProxy.logFINEMessage("HashMap Context before passing to AppLog" 
                                            + hashMap);
                //apdDeliveryUtil.doAppLog(apdDeliveryContext, sourceUserInfoDoc);
                appLogHelper.doAppLog(apdDeliveryContext, sourceUserInfoDoc);
            }
        } catch (Exception e) { 
            throw new MitchellException(
                APDDeliveryConstants.ERROR_IN_ESTIMATE_STATUS_INPUT, 
                CLASS_NAME, 
                methodName, 
                apdDeliverydocument.getAPDDeliveryContext().getAPDEstimateStatusInfo().getAPDCommonInfo().getWorkItemId(), 
                APDDeliveryConstants.ERROR_IN_ESTIMATE_STATUS_INPUT_MSG
                    + "\n" 
                    + AppUtilities.getStackTraceString(e, true));
        }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method is never used.
     * 
     * @param apdDeliveydoc
     * An instance of APDDeliveryContextDocument
     */
    public void deliverArtifactNotification(APDDeliveryContextDocument apdDeliveydoc) {
        //No Operation done 
    }
    
    /**
     * @param context APDDeliveryContextDocument
     * @param attachments ArrayList
     * @throws MitchellException  MitchellException
     */
    public void deliverAppraisalAssignmentToEclaim(APDDeliveryContextDocument context,
                                                   ArrayList attachments)
                                                        throws MitchellException {
                                                            
        // No Operation.
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
