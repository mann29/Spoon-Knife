package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoType;
import com.mitchell.common.types.AttachmentItemType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.mcfsvc.AttachmentObject;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ApdJdbcDao;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.DocumentStoreProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserData;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class delivers Appraisal Assignments (original and supplement) 
 * for following status'.
 * <ul>
 * <li>Dispatch
 * <li>Cancelled
 * <li>Resend
 * <li>Reassign
 * <li>status updates (Rejected, Accepted)
 * </ul>
 * @author Deepak Saxena
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class AppraisalAssignmentDeliveryHandler implements APDDeliveryHandler { 
    /**
     * class name.
     */
    private static final String CLASS_NAME = AppraisalAssignmentDeliveryHandler.class.getName();
    private static final String COMPLETED_STATUS = "COMPLETED";
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);

	private ApdJdbcDao dao;
	
	private ADSProxy adsProxy;
	
	private UserInfoProxy userInfoProxy;
    
	private DocumentStoreProxy documentStoreProxy;
    
	private AppLogHelper appLogHelper;
	
	private APDDeliveryUtil apdDeliveryUtil;
	
	private APDCommonUtilProxy apdCommonUtilProxy;
	
    private UserHelper userHelper;
    
    private CustomSettingProxy customSettingProxy;
	
    /**
     * This method handles delivery of original appraisal assignment, 
     * supplement appraisal assignment and 
     * Request for supplement task status updates.
     * <p>
     * 
     * @param apdDelContext
     * Encapsulates the relevant infomation needed to handle 
     * request for Supplement Task status updates.
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument apdDelContext) throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
            
        APDAppraisalAssignmentInfoType appAssignmentInfoType = apdDelContext.getAPDDeliveryContext()
                                                                    .getAPDAppraisalAssignmentInfo();
        
        // validate if APDAppraisalAssignmentInfo is not null                                                  
        if (appAssignmentInfoType == null) {
            
            throw new MitchellException(CLASS_NAME, methodName,
                                         "APDAppraisalAssignmentInfo is null ????");
        }
        
        ArrayList attachmentList = new ArrayList();
        
        try {
            // check if AttachmentInfo is present in appAssignmentInfoType
            if (appAssignmentInfoType.isSetAttachments()) {
                
                AttachmentInfoType attachments = appAssignmentInfoType.getAttachments();
                                                        
                AttachmentItemType[] attachmentItem = attachments.getAttachmentInfoList()
                                                                    .getAttachmentItemArray();
                if (attachmentItem != null) {
                    
                    for (int i = 0; i < attachmentItem.length; i++) {
                    
                        if (attachmentItem[i].isSetDocStoreFileReference()) {
                            
                            //get docStoreFileReference
                            long docStoreFileReference = 
                                Long.valueOf(attachmentItem[i].getDocStoreFileReference()).longValue();
                            
                            //get docResponse
                            // Fix 117633 : using documentStoreProxy for DocumentStoreClient.
                            // TODO : Null check for docResponse required?
                            
                            GetDocResponse docResponse = documentStoreProxy.getDocument(docStoreFileReference);
                            //Create attachmentObject
                            AttachmentObject attachmentObject = 
                                new AttachmentObject(new File(docResponse.getfilenameondisk()),
                                                              AttachmentObject.OBJ_TYPE_PARTS_LIST);
    
                            attachmentObject.setTabNumber(1);
    
                            attachmentList.add(attachmentObject);
                        }
                    }
                }
            }
            //Deliver Assignment using AppraisalAssignment
            deliverArtifact(apdDelContext, attachmentList); 
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(CLASS_NAME, methodName,
                                        "Exception occured in deliverArtifact"
                                        + "\n" 
                                        + AppUtilities.getStackTraceString(e, true));
        }
    }
    
    /**
     * This method handles Appraisal Assignment notification to 
     * RepairCenter shops.
     * 
     * @param context
     * Encapsulates the relevant infomation needed to handle 
     * request for Supplement Task status updates
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifactNotification(APDDeliveryContextDocument context) throws MitchellException {
        
        String methodName = "deliverArtifactNotification";
        logger.entering(CLASS_NAME, methodName);
        BaseAPDCommonType baseAPDCommonType = context.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo();
        UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
        
        // If end point is Platform
        // Fix 117663 : using APDDeliveryUtil instance.
        
        if (apdDeliveryUtil.isEndpointPlatform(targetUserInfoType)) {
            // If the user is a shop user
            if (apdDeliveryUtil.isShopUser(targetUserInfoType)) {  
            	apdCommonUtilProxy.logFINEMessage("TODO"); 
                /**
                 * to-do For original appraisal assignment invoke deliverAssignmentNotification
                 * (APDDeliveryContext, AssignmentServiceContext, UserInfo) method
                 * of AssignmentDeliveryClient class
                 */
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * <p>
     * This method will handle delivering assignment to ECLAIM.
     * </p>
     * @param apdDelContext APDDeliveryContextDocument
     * @param partsListAttachment ArrayList
     * @throws MitchellException  MitchellException
     */
    public void deliverArtifact(APDDeliveryContextDocument apdDelContext,
                                                   ArrayList partsListAttachment)
                                                                throws MitchellException {
        
        String methodName = "deliverArtifact";

        logger.entering(CLASS_NAME, methodName);
		boolean isShopUser = false;
		boolean isStaffUser = false;
		String userType = null;
        try {

            APDAppraisalAssignmentInfoType appAssignmentInfoType = apdDelContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo();
            
            // validate if APDAppraisalAssignmentInfo is not null                                                      
            if (appAssignmentInfoType == null) {
                throw new MitchellException(CLASS_NAME, methodName,
                                             "APDAppraisalAssignmentInfo is null ????");
            }
            
            BaseAPDCommonType baseAPDCommonType = appAssignmentInfoType.getAPDCommonInfo();
            UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
            
            // get user type
            userType = apdDeliveryUtil.getUserType(targetUserInfoType.getOrgCode(),
                    targetUserInfoType.getUserID());
            
            // populate user type boolean values
            isStaffUser = apdDeliveryUtil.isStaffUser(userType);
            isShopUser  = apdDeliveryUtil.isShopUser(userType);

            // if End point is RCWeb deliver to RCWeb
            if (apdDeliveryUtil.isEndpointRCWeb(baseAPDCommonType)) {
                if (!shouldDeliverToRcWeb(appAssignmentInfoType)) {
                    handleRCWebDelivery(apdDelContext);
                }
                return;
            }

            if (apdDeliveryUtil.isEndpointPlatformWithOverride(baseAPDCommonType)) { // if End point is Platform
                handlePlatformDelivery(apdDelContext, isShopUser, isStaffUser);
 
            } else { 
			
				// handle email delivery
				handleEmailDelivery(apdDelContext, userType, targetUserInfoType.getOrgCode());
				
				// handle legacy delivery
				handleLegacyADSDelivery(apdDelContext, partsListAttachment);
			}
	
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(APDDeliveryConstants.ERROR_DELIVER_APPRAISAL_ASMT, 
                                        CLASS_NAME,
                                        methodName, 
                                        apdDelContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId(), 
                                        APDDeliveryConstants.ERROR_DELIVER_APPRAISAL_ASMT_MSG
                                        + "\n" 
                                        + AppUtilities.getStackTraceString(e, true));
        } 
        
    }


    private boolean shouldDeliverToRcWeb(APDAppraisalAssignmentInfoType appAssInfoType)
    {
        return COMPLETED_STATUS.equals(appAssInfoType.getMessageStatus());
    }

    // Handles delivery for RCWeb
    private void handleRCWebDelivery(APDDeliveryContextDocument context) throws Exception {

        String methodName = "handleRCWebDelivery";
        this.logger.entering(CLASS_NAME, methodName);

        this.apdCommonUtilProxy.logINFOMessage("End point is RC Web. Handling RC Web delivery");

        this.logger.exiting(CLASS_NAME, methodName);
        this.adsProxy.deliverRCWebAssignment(context);
    }

    /**
     * This handles delivery for RC and WCAP users
     */
	private void handlePlatformDelivery(final APDDeliveryContextDocument apdDelContext,
                boolean isShopUser, boolean isStaffUser) throws Exception {
        
        APDAppraisalAssignmentInfoType appAssignmentInfoType = apdDelContext.getAPDDeliveryContext()
                                                            .getAPDAppraisalAssignmentInfo();
        BaseAPDCommonType baseAPDCommonType = appAssignmentInfoType.getAPDCommonInfo();
        apdCommonUtilProxy.logINFOMessage("End point is platform.........");
        
        // If the user is a shop user
        if (isShopUser || isStaffUser) {

            // For original appraisal assignment and supplement appraisal assignments
            if (APDDeliveryConstants.ORIGINAL_ESTIMATE_ARTIFACT_TYPE.equalsIgnoreCase(
                                    apdDelContext.getAPDDeliveryContext().getMessageType())
                    || APDDeliveryConstants.SUPPLEMENT_ARTIFACT_TYPE.equalsIgnoreCase(
                                    apdDelContext.getAPDDeliveryContext().getMessageType())) {
            	apdCommonUtilProxy.logINFOMessage("Calling AssignmentDeliveryClient to deliver Assignment....");
                
                adsProxy.deliverAssignment(apdDelContext);
                
                apdCommonUtilProxy.logINFOMessage("Assignment delivered to AssignmentDeliveryClient success....");
                
            }
        } else { //do applog that user is not a shop user.
            
            apdCommonUtilProxy.logINFOMessage("User is a platform IA user ?........");
            
            
            Map hashMap = new HashMap();
            
            hashMap.put("MessageType", 
                    apdDelContext.getAPDDeliveryContext().getMessageType());
            hashMap.put("MessageStatus", 
                    appAssignmentInfoType.getMessageStatus());
            hashMap.put("TaskId", 
                    Long.toString(appAssignmentInfoType.getTaskId()));
            hashMap.put("Message", "Target User is not Shop User");
            hashMap.put("DateTime", baseAPDCommonType.getDateTime());
            if (baseAPDCommonType.isSetNotes()) {
                hashMap.put("Notes", baseAPDCommonType.getNotes());
            }
            hashMap.put("TargetUserInfo", baseAPDCommonType.getTargetUserInfo());
            //hashMap.put("TargetDRPUserInfo", baseAPDCommonType.getTargetDRPUserInfo());
            
            APDDeliveryContext context = apdDeliveryUtil.populateContextForAppLog(
                                                         baseAPDCommonType,
                                                         hashMap,
                                                         APDDeliveryConstants.USER_IS_NOT_SHOP_USER);
            
            UserInfoDocument sourceUserInfoDoc = UserInfoDocument.Factory.newInstance();
            sourceUserInfoDoc.setUserInfo(baseAPDCommonType.getSourceUserInfo().getUserInfo());
            context.setUserId(sourceUserInfoDoc.getUserInfo().getUserID());
            
            // do App Log by calling util method
            appLogHelper.doAppLog(context, sourceUserInfoDoc);
        }
	}  

    /**
     * This method handles email delivery for RC-Connect
     */
	private void handleEmailDelivery(APDDeliveryContextDocument apdDelContext,
		final String userType, final String companyCode) throws Exception {
        
        String methodName = "handleEmailDelivery";
        logger.entering(CLASS_NAME, methodName);
        APDAppraisalAssignmentInfoType appAssignmentInfoType = apdDelContext.getAPDDeliveryContext()
                                                                        .getAPDAppraisalAssignmentInfo();		
        
        
        apdCommonUtilProxy.logINFOMessage(" Handling Email Delivery .........");
        
        
        if (APDDeliveryConstants.MSG_STATUS_DISPATCHED.equalsIgnoreCase(appAssignmentInfoType.getMessageStatus()) && 
        		isEmailRequired(companyCode, userType)) {
        		final UserInfoDocument userInfoDocument = apdDeliveryUtil.getUserInfoDocumentFromAPDContext(apdDelContext);
            	final String emailType = getEmailTypeFromUser(userInfoDocument,userType);
            	if(emailType != null) {
            		adsProxy.deliverAssignmentEmailNotification(apdDelContext,null,emailType);
            	}	   
        }
        
    }

	/**
	 * This method checks if send email is required.
	 * @param companyCode
	 * @param userType
	 * @return boolean
	 * @throws MitchellException
	 */
	private boolean isEmailRequired(final String companyCode, final String userType) throws MitchellException {
		String methodName = "isEmailRequired";
		logger.entering(CLASS_NAME, methodName);
		boolean needToSendEmail = false;
		if (checkExternalEstimationValue(companyCode) || checkUserTypeAndNonDRPSolution(userType)) {
        	needToSendEmail = true;
        }
        apdCommonUtilProxy.logINFOMessage("Email needs to be sent :" + needToSendEmail);
        logger.exiting(CLASS_NAME, methodName);
		return needToSendEmail;
	}
	
	private boolean checkExternalEstimationValue(final String companyCode) throws MitchellException{
		boolean isExternalEstimationSystemValue=false;
		final String externalEstimationValue=customSettingProxy.getCompanyCustomSetting(companyCode, APDDeliveryConstants.CUSTOM_SETTING_GROUP_NAME_CARRIER_GLOBAL_SETTINGS,APDDeliveryConstants.CUSTOM_SETTING_EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION);
		 if (APDDeliveryConstants.GTE_CUSTOM_SETTING_EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION_VALUE.equalsIgnoreCase(externalEstimationValue)){
			 isExternalEstimationSystemValue=true;
		 }
		 return isExternalEstimationSystemValue;
	}
	
	private boolean checkUserTypeAndNonDRPSolution(final String userType) throws MitchellException{
		boolean isUserShopIANonDRP=false;
		final boolean isHideNonDrpSolution = userHelper.isHideNonNetworkSolution();
		boolean isShopIAUser=false;
		isShopIAUser=apdDeliveryUtil.isShopUser(userType)?true:apdDeliveryUtil.isIAUser(userType);
		if(!isHideNonDrpSolution && isShopIAUser){
			isUserShopIANonDRP=true;
		}
		return isUserShopIANonDRP;
	}
	
	 /**
	  * This method gets Email Type as per User.
	 * @param userInfoDocument
	 * @param userType
	 * @return String
	 * @throws Exception
	 */
	private String getEmailTypeFromUser(final UserInfoDocument userInfoDocument, final String userType) throws Exception{
	        String methodName = "getEmailTypeFromUser";
	        logger.entering(CLASS_NAME, methodName);
	        String emailType = null;
			
	        if(apdDeliveryUtil.isStaffUser(userType)) { 
	        		// For STAFF user send STAFF type email
					emailType = AssignmentDeliveryConstants.STAFF_EMAIL_TYPE;
			} else if (apdDeliveryUtil.isShopUser(userType)) { 
				   // Shop user send either Premium or basic type email based on appl code.
					UserData userData = this.userHelper.checkUserData(userInfoDocument);	
					if(userData != null) {
						if(userData.isShopPremium()) {
							// For Premium shop user send Premium shop type email
							emailType = AssignmentDeliveryConstants.SHOP_PREMIUM_EMAIL_TYPE;
						} else if(userData.isShopBasic()) {
							// For Basic shop user send basic shop type email
							emailType = AssignmentDeliveryConstants.SHOP_BASIC_EMAIL_TYPE;
						}
					}
			} else if (apdDeliveryUtil.isIAUser(userType)) {
					// For IA user send either Premium or basic type email based on appl code.
					UserData userData = this.userHelper.checkUserData(userInfoDocument);
					if(userData != null) {
						if(userData.isShopPremium()) {
							// For Premium IA user send Premium IA type email
							emailType = AssignmentDeliveryConstants.IA_PREMIUM_EMAIL_TYPE;
						} else if(userData.isShopBasic()) {
							// For Basic IA user send Basic IA type email
							emailType = AssignmentDeliveryConstants.IA_BASIC_EMAIL_TYPE;
						}
					}
			}
	        
	        apdCommonUtilProxy.logINFOMessage("Email type is:" + emailType);		    
	        
			logger.exiting(CLASS_NAME, methodName);
			return emailType;
	   }
	

	/**
	 * This method invokes Assignment Delivery Service for Email.
	 * @param apdDelContext
	 * @param partsListAttachment
	 * @param userData
	 * @throws Exception
	 */
	private void invokeADS4Email(final APDDeliveryContextDocument apdDelContext,
            final ArrayList partsListAttachment, final UserData userData)
    		throws Exception {
    	
    	if (isOriginalAssignment(apdDelContext)) {
    	    if (userData.isShopPremium()) {
    	        adsProxy.deliverAssignmentEmail4DRP(apdDelContext);
    	    } else {
    	        adsProxy.deliverAssignmentEmail(apdDelContext);
    	    }
    	} else {
    	    if (userData.isShopPremium()) {
    	        adsProxy.deliverNetworkShopSuppAsgEmail(
    	                apdDelContext, partsListAttachment);
    	    } else {
    	        adsProxy.deliverNonNetworkShopSuppAsgEmail(
    	            apdDelContext, partsListAttachment);
    	    }
    	}
    }
	
	/**
     * This method handles legacy EClaim/ARC5/ARC7 handlers
     */
    private void handleLegacyADSDelivery(APDDeliveryContextDocument apdDelContext,
                                                   ArrayList partsListAttachment)
                                                                throws Exception {
        
        String methodName = "handleLegacyADSDelivery";
        logger.entering(CLASS_NAME, methodName);

        APDAppraisalAssignmentInfoType appAssignmentInfoType = apdDelContext.getAPDDeliveryContext()
                                                                    .getAPDAppraisalAssignmentInfo();
        
        BaseAPDCommonType baseAPDCommonType = appAssignmentInfoType.getAPDCommonInfo();		
        UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
            
                
        // Get MitchellEnvelopeType from AppraisalAssignmentInfoType
        MitchellEnvelopeType meType = appAssignmentInfoType.getAssignmentMitchellEnvelope()
                                                           .getMitchellEnvelope();
        // Create MitchellEnvelopeDocument                                                   
        MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory.newInstance();
        meDoc.setMitchellEnvelope(meType);
        
        UserInfoDocument targetUserInfoDoc = UserInfoDocument.Factory.newInstance();
        targetUserInfoDoc.setUserInfo(targetUserInfoType);
        
        UserInfoDocument drpUserInfoDoc = null;
        
        if (baseAPDCommonType.isSetTargetDRPUserInfo()) {
            drpUserInfoDoc = UserInfoDocument.Factory.newInstance();
            drpUserInfoDoc.setUserInfo(baseAPDCommonType.getTargetDRPUserInfo().getUserInfo());
        }
        
        // Code Change for Testing :- Start
        apdCommonUtilProxy.logINFOMessage("Checking for PartsList Attachments Arraylist....");
        
        
        if (partsListAttachment != null) {
        	apdCommonUtilProxy.logINFOMessage("Size of PartListAttachment Arraylist:: " + partsListAttachment.size());
                        
        } else {
            
        	apdCommonUtilProxy.logINFOMessage("PartsList Attachments ArrayList is null....");
            
        }
        // Code Change for Testing :- End
        
        AssignmentServiceContext assignmentServiceContext 
                        = new AssignmentServiceContext(
                                        targetUserInfoDoc,
                                        baseAPDCommonType.getWorkItemId(),
                                        meDoc,
                                        baseAPDCommonType.isSetTargetDRPUserInfo(),
                                        drpUserInfoDoc,
                                        partsListAttachment);
        
        /**
         * Code change for SCR # 23058 :- Start
         */
        // if taskid is available in APDContext, set workassignmentId in assignmentServiceContext 
        if (appAssignmentInfoType.getTaskId() > 0) {
            
            assignmentServiceContext.setWorkAssignmentId(
                        String.valueOf(appAssignmentInfoType.getTaskId()));
        }
        //Code change for SCR # 23058 :- End
                        
        // if "MessageStatus" is "DISPATCHED"
        if (APDDeliveryConstants.MSG_STATUS_DISPATCHED.equalsIgnoreCase(
                                appAssignmentInfoType.getMessageStatus())) {
            
        	apdCommonUtilProxy.logINFOMessage(":::::: calling AssignmentDelivery for MSG_STATUS_DISPATCHED ::::::");
            
            adsProxy.deliverAssignment(assignmentServiceContext);
            
            apdCommonUtilProxy.logINFOMessage("*** call to AssignmentDelivery Successful ***");
                        
        } else if (APDDeliveryConstants.MSG_STATUS_CANCEL.equalsIgnoreCase(
                                appAssignmentInfoType.getMessageStatus())) {
            // If MessageStatus is "CANCEL"
            
        	apdCommonUtilProxy.logINFOMessage(":::::: calling AssignmentDelivery for MSG_STATUS_CANCEL ::::::");
            
            adsProxy.cancelAssignment(assignmentServiceContext);
            
            apdCommonUtilProxy.logINFOMessage("*** call to AssignmentDelivery Successful ***");
            
        }
        
    }

     
    
    /**
     * This method checks Original Assignment.
     * @param apdDelContext
     * @return boolean
     * @throws Exception
     */
    public boolean isOriginalAssignment(APDDeliveryContextDocument apdDelContext)
    		throws Exception {
    	boolean isOriginal = false;
    	
    	final MitchellEnvelopeType mitchellEnvelope =
    			apdDelContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
                .getAssignmentMitchellEnvelope().getMitchellEnvelope();
    	
        final MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper.newInstance();
        mitchellEnvHelper.getDoc().setMitchellEnvelope(mitchellEnvelope);
        // Fix:97154 : Replace param with ME_METADATA_CIECA_IDENTIFIER string.
        // Fix 117663
    
       final EnvelopeBodyType envelopeBody = mitchellEnvHelper
                .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER);
        final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata(); 
        final String xmlBeanClassname = metadata.getXmlBeanClassname();
        
        final String contentString = mitchellEnvHelper.getEnvelopeBodyContentAsString(envelopeBody);
        CIECADocument ciecaDoc = null;
        if (xmlBeanClassname == null || xmlBeanClassname.equals(CIECADocument.class.getName())) {
            ciecaDoc = CIECADocument.Factory.parse(contentString);
        } else {
        	throw new Exception("CIECADocument does not exist.");
        }
        
        if (ciecaDoc.getCIECA().getAssignmentAddRq().getDocumentInfo()
        		.getDocumentVerArray()[0].getDocumentVerCode().equals("EM")) {
            isOriginal = true;
        }        
        
    	return isOriginal;
    }
    
	/**
	 * @return the dao
	 */
	public ApdJdbcDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(ApdJdbcDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the adsProxy
	 */
	public ADSProxy getAdsProxy() {
		return adsProxy;
	}

	/**
	 * @param adsProxy the adsProxy to set
	 */
	public void setAdsProxy(ADSProxy adsProxy) {
		this.adsProxy = adsProxy;
	}

	/**
	 * @return the userInfoProxy
	 */
	public UserInfoProxy getUserInfoProxy() {
		return userInfoProxy;
	}

	/**
	 * @param userInfoProxy the userInfoProxy to set
	 */
	public void setUserInfoProxy(UserInfoProxy userInfoProxy) {
		this.userInfoProxy = userInfoProxy;
	}

	/**
	 * @return the documentStoreProxy
	 */
	public DocumentStoreProxy getDocumentStoreProxy() {
		return documentStoreProxy;
	}

	/**
	 * @param documentStoreProxy the documentStoreProxy to set
	 */
	public void setDocumentStoreProxy(DocumentStoreProxy documentStoreProxy) {
		this.documentStoreProxy = documentStoreProxy;
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

	public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

	public CustomSettingProxy getCustomSettingProxy() {
		return customSettingProxy;
	}

	public void setCustomSettingProxy(CustomSettingProxy customSettingProxy) {
		this.customSettingProxy = customSettingProxy;
	}

	public UserHelper getUserHelper() {
		return userHelper;
	}
}