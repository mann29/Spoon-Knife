package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDNICBInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.CommonUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * <p>
 * This class handles NICB Report Delivery.
 * </p>
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class NICBReportHandler implements APDDeliveryHandler { 
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            NICBReportHandler.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private AppLogHelper appLogHelper;
    
    private CustomSettingProxy customSettingProxy;
    
    private APDDeliveryUtil apdDeliveryUtil;
    
    private CommonUtil commonUtil;
    
    private APDCommonUtilProxy apdCommonUtilProxy;
    /**
     * This method is responsible for NICB Report delivery 
     * to Platform or non-Platform.
     * 
     * @param apdContext
     * A XML bean of type APDDeliveryContextDocument
     * Encapsulates the relevant infomation needed to handle 
     * NICB Report delievry.
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(
                            APDDeliveryContextDocument apdContext) 
                                throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
        
        /**
         * extract NICB Report info from APD delivery context
         */
        APDNICBInfoType apdNICBInfoType = 
                        apdContext.getAPDDeliveryContext().getAPDNICBInfo();
        try {
            // validate APDNICBInfoType bean
            if (apdNICBInfoType == null) {
                throw new Exception(
                    APDDeliveryConstants.ERROR_NICB_REPORT_DELIVERY_INFO_NULL);
            }
            
            /**
             * extract info required for NICB Report delivery
             */
            BaseAPDCommonType baseAPDCommonType = 
                                            apdNICBInfoType.getAPDCommonInfo();
            // determine the delivery endpoint
            UserInfoType targetUserInfo = 
                                    apdNICBInfoType.getAPDCommonInfo().
                                        getTargetUserInfo().getUserInfo();
            // if endpoint is Platform
         // Fix 117663 : using APDDeliveryUtil instance.
            boolean platform = apdDeliveryUtil.isEndpointPlatform2(baseAPDCommonType);

            apdCommonUtilProxy.logFINEMessage("NICB Report Delivery: Is Endpoint Platform?  "
					+ platform);

            //String targetUserType = APDDeliveryUtil.getUserType(
                                        //        targetUserInfo.getOrgCode(), 
                                       //         targetUserInfo.getUserID());
            // is Staff?
            //boolean staffUser = 
                      //  UserTypeConstants.STAFF_TYPE.equals(targetUserType);
            
            /**
             * deliver NICB Report to Platform or non-Platform
             */
            if (!platform) {                
                // the endpoint is non-Platform
                //if (!staffUser) { will work for both staff and non-staff user
                    /**
                     * get NICB Report Handler event ID
                     */
                    int nicbDelEventId = 0;
                    // get customs settings (event ID) 
                    // for PARTIALLOSS_NICB_REPORT_DELIVER_HANDLERS
                    // Fix 117663 : using CustomSettingUtil instance.
                    /* CustomSettingUtil customSettingUtil = new CustomSettingUtil();
                    String nicbDelEventIdStr = 
                    	customSettingUtil.getNICBReportDeliveryEventSetting(
                                                targetUserInfo.getOrgCode()); */
                    
                    String nicbDelEventIdStr = 
                    	customSettingProxy.getNICBReportDeliveryEventSetting(
                                                targetUserInfo.getOrgCode());
                    // validate event ID
                    try {
                        nicbDelEventId = Integer.parseInt(nicbDelEventIdStr);
                    } catch (NumberFormatException nfe) {
                        throw new Exception(
                            APDDeliveryConstants
                                .ERROR_NICB_REPORT_DLVR_HDLR_EVT_ID_NOT_FOUND);
                    }
                    
                    /**
                     * publish to message bus
                     */
                    // populate MitchellEnvelop
                    MitchellEnvelopeDocument meDoc = this
                        .populateMeForNICBReportDeliveryToNonPlatform(apdContext);
                    
                    // populate MessagingContext 
                    UserInfoDocument targetUserInfoDoc = 
                                        UserInfoDocument.Factory.newInstance();
                    targetUserInfoDoc.setUserInfo(targetUserInfo);
                    MessagingContext msgContext = new MessagingContext(
                            nicbDelEventId,
                            meDoc,
                            targetUserInfoDoc,
                            apdNICBInfoType.getAPDCommonInfo().getWorkItemId(),
                            "");
                    // publish message to message bus
                    // Fix 117663 : using CommonUtil instance.
                    commonUtil.publishToMessageBus(msgContext);
                    
                    /**
                     * app log
                     */
                    Map additionalMap = new HashMap();
                    // put NV pairs
                    additionalMap.put(
                                    "MessageType", 
                                    apdContext.getAPDDeliveryContext()
                                                            .getMessageType());
                    additionalMap.put(
                        "Message", 
                        APDDeliveryConstants.NICB_REPORT_DELIVERY_SUCCESS_MSG);
                    // populate delivery context
                    APDDeliveryContext context = 
                    	apdDeliveryUtil.populateContextForAppLog(
                                baseAPDCommonType, 
                                additionalMap,
                                APDDeliveryConstants.NICB_REPORT_DELIVERED_TO_NON_PLATFORM);
                    UserInfoDocument sourceUserInfoDoc = 
                                        UserInfoDocument.Factory.newInstance();
                    sourceUserInfoDoc.setUserInfo(
                        apdNICBInfoType.getAPDCommonInfo()
                            .getSourceUserInfo().getUserInfo());
                    context.setUserId(sourceUserInfoDoc.getUserInfo().getUserID());
                    context.setWorkItemId(baseAPDCommonType.getWorkItemId());
                    // app log
                    //apdDeliveryUtil.doAppLog(context, sourceUserInfoDoc);
                    // Fix 117663 : using appLogHelper for applogging.
                    appLogHelper.doAppLog(context, sourceUserInfoDoc);
               // }
            }
            
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_NICB_REPORT_DELIVERY,
                            CLASS_NAME,
                            methodName,
                            apdNICBInfoType.getAPDCommonInfo().getWorkItemId(),
                            APDDeliveryConstants.ERROR_NICB_REPORT_DELIVERY_MSG 
                                + "\n" 
                                + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * <p>
     * This method populates MitchellEnvelop for NICB Report delivery 
     * to non-Platform and non-Staff user.
     * </p>
     * 
     * @param apdContext
     * A XML Bean of type APDDeliveryContextDocument,
     * that encapsulates the relevant infomation needed 
     * to handle NICB Report delievry.
     * @throws MitchellException
     * Mitchell Exception
     * @return MitchellEnvelopeDocument
     * Mitchell Envelop for NICB Report
     */
    private MitchellEnvelopeDocument populateMeForNICBReportDeliveryToNonPlatform(
                                        APDDeliveryContextDocument apdContext)
                                        throws MitchellException {
        
        String methodName = "populateMeForNICBReportDeliveryToNonPlatform";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDNICBInfoType apdNICBInfoType = 
                        apdContext.getAPDDeliveryContext().getAPDNICBInfo();
        try {
            // populate MitchellEnvelop
            MitchellEnvelopeHelper helper = 
                                MitchellEnvelopeHelper.newInstance();
            // get ME
            meDoc = helper.getDoc();
            meDoc.setMitchellEnvelope(
                apdNICBInfoType
                    .getNICBMitchellEnvelope().getMitchellEnvelope());
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_NICB_REPORT_DELIVERY,
                        CLASS_NAME,
                        methodName,
                        apdNICBInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_NICB_REPORT_DELIVERY_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }
    
    /**
     * <p>
     * This method is never used in NICB Report delivery context.
     * </p>
     * 
     * @param context 
     * APDDeliveryContextDocument
     * @param attachments 
     * ArrayList
     * @throws MitchellException  
     * Mitchell Exception
     */
    public void deliverArtifact(
                            APDDeliveryContextDocument context, 
                            ArrayList attachments) 
                                throws MitchellException {
        //no-op
    }
    
    /**
     * <p>
     * This method is never used in NICB Report delivery context.
     * </p>
     * 
     * @param context
     * An instance of APDDeliveryContextDocument
     * @throws MitchellException  
     * Mitchell Exception
     */
    public void deliverArtifactNotification(
                            APDDeliveryContextDocument context) 
                                throws MitchellException {
        //no-op
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
	 * @return the customSettingProxy
	 */
	public CustomSettingProxy getCustomSettingProxy() {
		return customSettingProxy;
	}

	/**
	 * @param customSettingProxy the customSettingProxy to set
	 */
	public void setCustomSettingProxy(CustomSettingProxy customSettingProxy) {
		this.customSettingProxy = customSettingProxy;
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
	 * @return the commonUtil
	 */
	public CommonUtil getCommonUtil() {
		return commonUtil;
	}

	/**
	 * @param commonUtil the commonUtil to set
	 */
	public void setCommonUtil(CommonUtil commonUtil) {
		this.commonUtil = commonUtil;
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
