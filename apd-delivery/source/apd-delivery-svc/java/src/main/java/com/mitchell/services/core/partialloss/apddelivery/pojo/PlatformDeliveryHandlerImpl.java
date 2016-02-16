package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CIECADocument.CIECA;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoDocument;
import com.mitchell.common.types.AttachmentInfoType;
import com.mitchell.common.types.AttachmentItemType;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ActivityStatusType;
import com.mitchell.schemas.DefinitionType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.RoleCollaboratorPairType;
import com.mitchell.schemas.WorkProcessInitiationMessage;
import com.mitchell.schemas.WorkProcessInitiationMessageDocument;
import com.mitchell.schemas.WorkProcessUpdateMessage;
import com.mitchell.schemas.WorkProcessUpdateMessageDocument;
import com.mitchell.schemas.apddelivery.APDAlertInfoType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentNotificationInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDEstimateStatusEventType;
import com.mitchell.schemas.apddelivery.APDEstimateStatusInfoType;
import com.mitchell.schemas.apddelivery.APDRepairAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDRequestStaffSupplementInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.messagebus.MessageDispatcher;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ClaimServiceProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CustomSettingProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.EstimatePackageProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext;
import com.mitchell.services.core.partialloss.apddelivery.utils.WorkProcessUpdateMessageContext;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class delivers following artifacts to the Platfrom.
 * <ul>Alerts- upload accepted, upload rejected, global alerts
 * <li>Estimate status- Approved, rejected, recommended total loss, reopened 
 * and no Estimate Review
 * <li>Appraisal Assignments (original and supplement)- Dispatch, Cancelled, 
 * Resend, Reassign, and status updates (Rejected, Accepted)
 * <li>Repair Assignment (Rework Assignment)- Delivered, Cancelled, Updated
 * </ul>
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class PlatformDeliveryHandlerImpl implements PlatformDeliveryHandler { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = PlatformDeliveryHandlerImpl.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private ClaimServiceProxy claimServiceProxy;
    
    private CustomSettingProxy customSettingProxy;
    
    private APDDeliveryUtil apdDeliveryUtil;
    
    private APDCommonUtilProxy apdCommonUtilProxy;
    
    private EstimatePackageProxy estimatePackageProxy;
    
    //private MitchellEnvelopeDocument meDoc = null;
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler#deliverAlert(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument, boolean)
	 */
    public void deliverAlert(APDDeliveryContextDocument apdContext, 
                                                    boolean shopUser) 
                                                    throws MitchellException {
        
        String methodName = "deliverAlert";
        logger.entering(CLASS_NAME, methodName);
        
        APDAlertInfoType apdAlertInfoType = 
                            apdContext.getAPDDeliveryContext().getAPDAlertInfo();
                            
        // populate MitchellEnvelop
        MitchellEnvelopeDocument meDoc = null;
        UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
        if(shopUser) {
            // target is Platform Shop
            meDoc = this.populateMeForAlertsDeliveryToPlatformShop(apdContext);
            userInfoDoc.setUserInfo(apdAlertInfoType.getAPDCommonInfo().
                                        getTargetDRPUserInfo().getUserInfo());
        } else {
            // target is Platform Staff
            meDoc = this.populateMeForAlertsDeliveryToPlatformStaff(apdContext);
            userInfoDoc.setUserInfo(apdAlertInfoType.getAPDCommonInfo().
                                        getTargetUserInfo().getUserInfo());
        }
        
        // get the event id from the SET file
        // Fix 117663
        int eventId = Integer.parseInt(systemConfigurationProxy.getAlertDeliveryEventId());
        
        // populate MessagingContext 
        MessagingContext msgContext = new MessagingContext(eventId,
                            meDoc,
                            userInfoDoc,
                            apdAlertInfoType.getAPDCommonInfo().getWorkItemId(),
                            "");
        
        // publish message to message bus
        this.publishToMessageBus(msgContext);
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler#deliverRepairAssignment(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverRepairAssignment(APDDeliveryContextDocument apdContext)
                                                    throws MitchellException {
        String methodName = "deliverRepairAssignment";
        logger.entering(CLASS_NAME, methodName);
        
        APDRepairAssignmentInfoType apdRAInfoType = 
                            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
                            
        // populate MitchellEnvelop
        MitchellEnvelopeDocument meDoc = this.populateMeForRADelivery(apdContext);
        
        // get the event id from the SET file
        // Fix 117663
        int eventId = Integer.parseInt(systemConfigurationProxy.getRADeliveryEventId());
        
        // populate MessagingContext 
        UserInfoDocument drpUserInfoDoc = UserInfoDocument.Factory.newInstance();
        drpUserInfoDoc.setUserInfo(apdRAInfoType.getAPDCommonInfo().
                                        getTargetDRPUserInfo().getUserInfo());
        MessagingContext msgContext = new MessagingContext(eventId,
                            meDoc,
                            drpUserInfoDoc,
                            apdRAInfoType.getAPDCommonInfo().getWorkItemId(),
                            "");
        
        // publish message to message bus
        this.publishToMessageBus(msgContext);
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler#deliverEstimateStatus(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverEstimateStatus(APDDeliveryContextDocument apdDeliveryDoc)
                                                throws MitchellException {
    
        String methodName = "deliverEstimateStatus";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDDeliveryContextDocument apdDeliveryContext = null; 
        apdDeliveryContext = apdDeliveryDoc;
    
        // populate MitchellEnvelop
        meDoc = this.populateMeForEstimateDelivery(apdDeliveryDoc);
        
        // get the event id from the SET file
        // Fix 117663
        int eventId = Integer.parseInt(systemConfigurationProxy.getEstimateDeliveryEventId());
    
        //get the value for Estimate Status Info type
        APDEstimateStatusInfoType estimateStatusInfoType = apdDeliveryContext
                                                            .getAPDDeliveryContext()
                                                                .getAPDEstimateStatusInfo();
    
        // populate MessagingContext 
        UserInfoDocument drpUserInfoDoc = UserInfoDocument.Factory.newInstance();
        drpUserInfoDoc.setUserInfo(
            estimateStatusInfoType.getAPDCommonInfo().getTargetDRPUserInfo().getUserInfo());
        MessagingContext msgContext = 
                new MessagingContext(
                    eventId,
                    meDoc, 
                    drpUserInfoDoc, 
                    estimateStatusInfoType.getAPDCommonInfo().getWorkItemId(),
                    "");        
        // publish message to message bus
        this.publishToMessageBus(msgContext);
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler#deliverAppraisalAssignmentNotification(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverAppraisalAssignmentNotification(
                                APDDeliveryContextDocument apdDeliveryDoc) 
                                throws MitchellException {
        String methodName = "deliverAppraisalAssignmentNotification";
        logger.entering(CLASS_NAME, methodName);
        
        APDAppraisalAssignmentNotificationInfoType apdApprAsmtNtfnInfoType = 
            apdDeliveryDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo();
                    
        if (!apdApprAsmtNtfnInfoType.getAPDCommonInfo().isSetClientClaimNumber() ||
                !apdApprAsmtNtfnInfoType.getAPDCommonInfo().isSetSuffixId()) {
            throw new MitchellException (
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG);
        }
        
        MitchellEnvelopeDocument meDoc = null;
        
        // populate MitchellEnvelop
        meDoc = this.populateMeForApprAsmtNtfnDelivery(apdDeliveryDoc);
        
        // get the event id from the SET file
     // Fix 117663
        int eventId = 
            Integer.parseInt(systemConfigurationProxy.getApprAsmtNtfnDeliveryEventId());
        
        // populate MessagingContext 
        UserInfoDocument drpUserInfoDoc = UserInfoDocument.Factory.newInstance();
        drpUserInfoDoc.setUserInfo(apdApprAsmtNtfnInfoType.getAPDCommonInfo().
                                        getTargetDRPUserInfo().getUserInfo());
        MessagingContext msgContext = 
                new MessagingContext(
                    eventId,
                    meDoc, 
                    drpUserInfoDoc, 
                    apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId(),
                    "");        
        
        // publish message to message bus
        this.publishToMessageBus(msgContext);
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * @param apdDeliveryDoc
     * @throws MitchellException
     * Mitchell Exception
     */
    private MitchellEnvelopeDocument populateMeForApprAsmtNtfnDelivery(
                                APDDeliveryContextDocument apdDeliveryDoc) 
                                throws MitchellException {
        String methodName = "populateMeForApprAsmtNtfnDelivery";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDAppraisalAssignmentNotificationInfoType apdApprAsmtNtfnInfoType = null;
                
        try {
            apdApprAsmtNtfnInfoType = 
                    apdDeliveryDoc.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo();
                    
            String messageStatus = 
                apdApprAsmtNtfnInfoType.getMessageStatus();

            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();

            // message type
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MESSAGE_TYPE,
                APDDeliveryConstants.APPR_ASMT_NTFN_ARTIFACT_TYPE);

            // add client claim number
            helper.addEnvelopeContextNVPair(
                APDDeliveryConstants.CLAIM_NUMBER,
                apdApprAsmtNtfnInfoType.getAPDCommonInfo().getClientClaimNumber());
            
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.SUFFIX_ID,
                Long.toString(apdApprAsmtNtfnInfoType.getAPDCommonInfo().getSuffixId()));
            
            helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.ESTIMATOR_NAME,
                    apdApprAsmtNtfnInfoType.getEstimatorName());
                    
            helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.ESTIMATOR_USER_ID,
                    apdApprAsmtNtfnInfoType.getEstimatorUserID());

            if (apdApprAsmtNtfnInfoType.getAPDCommonInfo().isSetNotes()) {
            	helper.addEnvelopeContextNVPair( 
                        APDDeliveryConstants.NOTES,
                        apdApprAsmtNtfnInfoType.getAPDCommonInfo().getNotes());
            	
            }
            
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MITCHELL_WORK_ITEM_ID,
                apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId());

            if (apdApprAsmtNtfnInfoType.isSetScheduleDateTime()) {
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.SCHEDULE_DATE_TIME,
                    apdDeliveryUtil.calendarToString(
                                        apdApprAsmtNtfnInfoType.getScheduleDateTime(), 
                                        "yyyy-MM-dd'T'HH:mm:ssz"));
            }
            
            if (apdApprAsmtNtfnInfoType.isSetPreferredScheduleDate()) {
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.PREFERRED_SCHEDULE_DATE,
                    apdDeliveryUtil.calendarToString(
                    		apdApprAsmtNtfnInfoType.getPreferredScheduleDate(), 
                                        "yyyy-MM-dd"));
            }
            
            if (apdApprAsmtNtfnInfoType.isSetPreferredScheduleWindow()) {
                helper.addEnvelopeContextNVPair( 
                		APDDeliveryConstants.PREFERRED_SCHEDULE_WINDOW,
                    apdApprAsmtNtfnInfoType.getPreferredScheduleWindow());
            }
            
            
            
            /**
             * populate an Assignment BMS document
             */
            CIECADocument asmtBMSDoc = 
                populateAsmtBMS(
                    apdDeliveryDoc.getAPDDeliveryContext()
                        .getAPDAppraisalAssignmentNotificationInfo().getAPDCommonInfo());
            
            if (asmtBMSDoc != null) {
            	apdCommonUtilProxy.logINFOMessage("Adding Assignment BMS in EnvelopeBody");
                String asmtBMSIdentifier = null;
                if ((APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_CREATE).equalsIgnoreCase(messageStatus)) {
                    asmtBMSIdentifier = APDDeliveryConstants.ASSIGNMENT_BMS_IDENTIFIER_CREATE;
                } else {
                    asmtBMSIdentifier = APDDeliveryConstants.ASSIGNMENT_BMS_IDENTIFIER_UPDATE;
                }
                helper.addNewEnvelopeBody(
                        asmtBMSIdentifier,
                        asmtBMSDoc, 
                        APDDeliveryConstants.ASSIGNMENT_BMS_MITCHELL_DOC_TYPE);
            }
            
            // get XZ UserInfo
            UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
            userInfo.setUserInfo(apdApprAsmtNtfnInfoType.getAPDCommonInfo()
                                        .getTargetUserInfo().getUserInfo());
            UserInfoDocument xzUserInfo = apdDeliveryUtil.getXZUserInfo(userInfo);

            // Populate additional context items

            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.SHOP_ID,
                xzUserInfo.getUserInfo().getOrgID());

            // This represents the CoCd of the reviewer associated 
            // with shop to which the assignment needs to be sent
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.REVIEWER_CO_CD,
                apdApprAsmtNtfnInfoType
                    .getAPDCommonInfo().getTargetUserInfo()
                        .getUserInfo().getOrgCode());
                
            // This represents the UserId of the reviewer 
            // associated with shop to which the assignment needs to be sent
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.REVIEWER_ID,
                apdApprAsmtNtfnInfoType
                    .getAPDCommonInfo().getTargetUserInfo()
                        .getUserInfo().getUserID());
                    
            // This represents the CoCd of the Body Shop
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.USER_CO_CD,
                apdApprAsmtNtfnInfoType
                    .getAPDCommonInfo().getTargetDRPUserInfo()
                        .getUserInfo().getOrgCode());
                    
            // This represents the UserId of the Body Shop
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.USER_ID,
                apdApprAsmtNtfnInfoType
                    .getAPDCommonInfo().getTargetDRPUserInfo()
                        .getUserInfo().getUserID());

            // Mitchell Company Code
            helper.addEnvelopeContextNVPair(
                APDDeliveryConstants.MITCHELL_COMPANY_CODE,
                apdApprAsmtNtfnInfoType
                    .getAPDCommonInfo().getInsCoCode());

            
            if ((APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_CREATE).equalsIgnoreCase(messageStatus)) {
                /**
                 * populate a WorkProcessInitiationMessage Document
                 */
                WorkProcessInitiationMessageDocument workProcessInitiationMessageDoc = 
                        WorkProcessInitiationMessageDocument.Factory.newInstance();
                populateWorkProcessInitiationMessageForApprAsmtNtfn(
                                                apdDeliveryDoc, 
                                                workProcessInitiationMessageDoc, 
                                                xzUserInfo);
                helper.addNewEnvelopeBody(
                    APDDeliveryConstants.WORK_PROCESS_INITIATION_MESSAGE_IDENTIFIER,
                    workProcessInitiationMessageDoc, 
                    APDDeliveryConstants.WORK_PROCESS_INITIATION_MESSAGE_MITCHELL_DOC_TYPE);                
            } 
            
            if ((APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_UPDATE).equalsIgnoreCase(messageStatus) 
                    || (APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_CANCEL).equalsIgnoreCase(messageStatus)) {
                /**
                 * populate a WorkProcessUpdateMessage document
                 */
                WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                                WorkProcessUpdateMessageDocument.Factory.newInstance();
                WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                                new WorkProcessUpdateMessageContext();
             // Fix 117663
                if ((APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_UPDATE).equalsIgnoreCase(messageStatus)) {
                    workProcessUpdateMessageContext.setActivityOperation(
                    		systemConfigurationProxy.getApprAsmtNtfnUpdateActivityOperation());
                } else if ((APDDeliveryConstants.APPR_ASMT_NTFN_MESSAGE_STATUS_CANCEL).equalsIgnoreCase(messageStatus)) {
                    workProcessUpdateMessageContext.setActivityOperation(
                    		systemConfigurationProxy.getApprAsmtNtfnCancelActivityOperation());
                }
                workProcessUpdateMessageContext.setActivityStatusType(
                                                    ActivityStatusType.BEGIN);
                OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(
                            apdApprAsmtNtfnInfoType.getAPDCommonInfo().getInsCoCode());
                // set collaborator
                //changes done by Akshat for setting the coCode as collaborator.
                /*
                workProcessUpdateMessageContext.setCollaborator(
                                Long.toString(orgInfo.getOrgInfo().getOrgID()));                                     
                */
                workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
                //changes completed
                
                // set Private Index in WorkProcessUpdateMessageContext
                String privateIndex = apdDeliveryUtil.getPrivateIndexForApprAsmtNtfn(
                                            apdApprAsmtNtfnInfoType.getNotificationID());                        
                if (privateIndex == null) {
                    throw new MitchellException(
                                            CLASS_NAME, 
                                            methodName,
                                            "Private Index is null");
                }
                workProcessUpdateMessageContext.setPrivateIndex(privateIndex);
                
                // set Public Index in WorkProcessUpdateMessageContext
                String publicIndex = apdDeliveryUtil.getPublicIndex(
                    apdApprAsmtNtfnInfoType.getAPDCommonInfo().getClientClaimNumber(), 
                    apdApprAsmtNtfnInfoType.getAPDCommonInfo().getInsCoCode());
                if (publicIndex == null) {
                    throw new MitchellException(
                        CLASS_NAME, 
                        methodName,
                        "Public Index is null for Appraisal Assignment Notification");
                }
                workProcessUpdateMessageContext.setPublicIndex(publicIndex);
                                
                this.populateWorkProcessUpdateMessage(
                                                workProcessUpdateMessageContext, 
                                                workProcessUpdateMessageDoc,
                                                apdApprAsmtNtfnInfoType.getAPDCommonInfo().getInsCoCode(),
                                                apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId());
               
                // put WorkProcessUpdateMessage in ME 
                helper.addNewEnvelopeBody(
                        "WorkProcessUpdateMessage",
                        workProcessUpdateMessageDoc, 
                        "WorkProcessUpdateMessage");
            } 
            meDoc = helper.getDoc();
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdApprAsmtNtfnInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }
    
    /**
     * This method populates WorkProcessInitiationMessage.
     * 
     * @param apdContext
     * @param workProcessInitiationMessageDoc
     * @throws MitchellException
     * Mitchell Exception
     */
    private void populateWorkProcessInitiationMessageForApprAsmtNtfn(
                        APDDeliveryContextDocument apdContext, 
                        WorkProcessInitiationMessageDocument workProcessInitiationMsgDoc, 
                        UserInfoDocument xzUserInfo) 
                        throws MitchellException {
        String methodName = "populateWorkProcessInitiationMessageForApprAsmtNtfn";
        logger.entering(CLASS_NAME, methodName);
        // Fix 117663
        WorkProcessInitiationMessage workProcessInitiationMessage = 
            workProcessInitiationMsgDoc.addNewWorkProcessInitiationMessage();
        
        APDAppraisalAssignmentNotificationInfoType apdApprAsmtNtfnInfoType = 
            apdContext.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo();
        
        String coCode = apdApprAsmtNtfnInfoType.getAPDCommonInfo().getInsCoCode();
        OrgInfoDocument orgInfoDoc = apdDeliveryUtil.getOrgInfo(coCode);
        
        String orgCode = null;
        if (orgInfoDoc != null) {
            orgCode = orgInfoDoc.getOrgInfo().getOrgCode();
        }
        //changes done by Akshat for setting the coCode as collaborator.
        //workProcessInitiationMessage.setWorkProcessCollaborator(insCoOrgIdStr);
        workProcessInitiationMessage.setWorkProcessCollaborator(orgCode);
        //changes completed
        String clientClaimNumber = 
            apdApprAsmtNtfnInfoType.getAPDCommonInfo().getClientClaimNumber();
            
        String publicIndex = apdDeliveryUtil.getPublicIndex(
                                                        clientClaimNumber, 
                                                        coCode);
        if (publicIndex == null) {
            publicIndex = UUIDFactory.getInstance().getCeicaUUID();
            apdDeliveryUtil.registerPublicKey(clientClaimNumber, coCode, publicIndex);
        }
        workProcessInitiationMessage.setPublicIndex(publicIndex);
        long xzUserOrgId = Long.parseLong(xzUserInfo.getUserInfo().getOrgID());
        String privateIndex = apdDeliveryUtil.getPrivateIndexForApprAsmtNtfn(
                                    apdApprAsmtNtfnInfoType.getNotificationID());
        if (privateIndex == null) {
            privateIndex = UUIDFactory.getInstance().getCeicaUUID();
            apdDeliveryUtil.registerPrivateKeyForApprAsmtNtfn(apdApprAsmtNtfnInfoType.getNotificationID(), 
                                            xzUserOrgId, 
                                            coCode, 
                                            privateIndex);
        }
        workProcessInitiationMessage.setPrivateIndex(privateIndex);
        
        DefinitionType definition = workProcessInitiationMessage.addNewDefinition();
     // Fix 117663
        definition.setType(systemConfigurationProxy.getApprAsmtNtfnDefType());
        
        definition.setVersion(APDDeliveryConstants.WORK_PROCESS_VERSION);

        // add role mapping
        workProcessInitiationMessage.addNewRoleMapping();        
        
        // add carrier collaborator
        RoleCollaboratorPairType carrierRoleCollaboratorPair =   
            workProcessInitiationMessage.getRoleMapping().addNewRoleCollaboratorPair();
        
        carrierRoleCollaboratorPair.setRole(APDDeliveryConstants.CARRIER_ROLE);
        //changes done by Akshat for setting the coCode as collaborator.
        //carrierRoleCollaboratorPair.setCollaborator(insCoOrgIdStr);
        carrierRoleCollaboratorPair.setCollaborator(orgCode);
        //changes completed
        
        // add shop collaborator
        RoleCollaboratorPairType shopRoleCollaboratorPair =  
            workProcessInitiationMessage.getRoleMapping().addNewRoleCollaboratorPair();
        
        shopRoleCollaboratorPair.setRole(APDDeliveryConstants.SHOP_ROLE);
        
        shopRoleCollaboratorPair.setCollaborator(xzUserInfo.getUserInfo().getOrgID());
        
        apdCommonUtilProxy.logINFOMessage("WorkProcessInitiationMessageDocument is: \n" 
            + workProcessInitiationMsgDoc.toString());
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method hands over a message to Message Bus Service, 
     * to publish it to JMS queue based on given Event ID.
     * <p>
     * 
     * @param msgContext
     * It holds all fields required to create an MWM doc
     * @throws MitchellException
     * Mitchell Exception
     */
    private void publishToMessageBus(MessagingContext msgContext) 
                                                    throws MitchellException {
        
        String methodName = "publishToMessageBus";
        logger.entering(CLASS_NAME, methodName);
        
        TrackingInfoDocument trackInfoDoc = WorkflowMsgUtil.createTrackingInfo(
            APDDeliveryConstants.APP_NAME, // Name of the source application
            APDDeliveryConstants.BUSINESS_SERVICE_NAME,   // Name of the Business Service
            APDDeliveryConstants.MODULE_NAME, // Name of the source module
            msgContext.getWorkItemId(), // workItemId of the business service instance// This should be a UUID
            msgContext.getUserInfoDoc(), // UserInfoDoc for the user associated with the instance of this workflow
            msgContext.getComment());  // COMMENT
        
            // init the msg
            MitchellWorkflowMessageDocument mwmDoc = 
                            WorkflowMsgUtil.createWorkflowMessage(trackInfoDoc);
            
        try {
            // insert ME
            mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, 
                                    msgContext.getMitchellEnvelopeDocument(), 
                                    msgContext.getEventId());
                          
            
            apdCommonUtilProxy.logINFOMessage("Message posted to MB " + mwmDoc.toString());
            
            
            // Fix 117663                        
           /* String publishToMessageQueue = 
                SystemConfiguration.getSettingValue(
                    "/APDDeliveryService/PublishToMessageQueue"); */
            
            String publishToMessageQueue = 
            	systemConfigurationProxy.getPublishToMessageQueue();
            
            if ("true".equalsIgnoreCase(publishToMessageQueue)) {
                MessageDispatcher.postMessage(
                                                    msgContext.getEventId(),
                                                    mwmDoc.xmlText(), 
                                                    null, 
                                                    null);
            } 
        } catch (Exception e) {
            throw new MitchellException(
                            APDDeliveryConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS,
                            CLASS_NAME,
                            methodName,
                            msgContext.getWorkItemId(),
                            APDDeliveryConstants.ERROR_PUBLISHING_TO_MESSAGE_BUS_MSG
                                + "\n" 
                                + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method populates MitchellEnvelop for Alerts delivery 
     * to Platform Shop user.
     * <p>
     * 
     * @param apdContext
     * A XML Bean of type APDDeliveryContextDocument,
     * that encapsulates the relevant infomation needed to handle alert delievry.
     * @throws MitchellException
     * Mitchell Exception
     * @return MitchellEnvelopeDocument
     * Mitchell Envelop for Alerts
     */
    private MitchellEnvelopeDocument populateMeForAlertsDeliveryToPlatformShop(
                                        APDDeliveryContextDocument apdContext)
                                        throws MitchellException {
        
        String methodName = "populateMeForAlertsDeliveryToPlatformShop";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDAlertInfoType apdAlertInfoType = null;
        // Fix 117663
        try {
            apdAlertInfoType = 
                        apdContext.getAPDDeliveryContext().getAPDAlertInfo();
            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
            
            // add Mitchell Envelop context name value pairs
            helper.addEnvelopeContextNVPair(
                                "MessageStatus", 
                                APDDeliveryConstants.ALERTS_MESSAGE_STATUS);
            helper.addEnvelopeContextNVPair(
                                "ProcessingResult", 
                                apdAlertInfoType.getAlertType().toString());
            helper.addEnvelopeContextNVPair(
                                "ProcessingResultNotes", 
                                apdAlertInfoType.getMessage());
            helper.addEnvelopeContextNVPair(
                            "MitchellCompanyCode", 
                            apdAlertInfoType.getAPDCommonInfo().getInsCoCode());
                            
            // get XZ UserInfo
            UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
            userInfo.setUserInfo(apdAlertInfoType.getAPDCommonInfo().
                getTargetUserInfo().getUserInfo());
            UserInfoDocument xzUserInfo = apdDeliveryUtil.getXZUserInfo(userInfo);
            
            if(xzUserInfo==null) {
                throw new RuntimeException("COuld not get XZUserInfo for user " + 
                    userInfo.getUserInfo().getOrgCode() + "-" + 
                    userInfo.getUserInfo().getUserID());
            }
            
            helper.addEnvelopeContextNVPair(
                                "ShopId", 
                                xzUserInfo.getUserInfo().getOrgID());
            String currentDate = apdDeliveryUtil.getDateStrForFormat(
                                                    new java.util.Date(), 
                                                    "yyyy-MM-dd'T'HH:mm:ss");
                                                    
            helper.addEnvelopeContextNVPair("SentDateTime", currentDate);
            helper.addEnvelopeContextNVPair(
                            "MitchellWorkItemId", 
                            apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
            
            if (apdAlertInfoType.getNoEstimateReviewFlag()) {
                helper.addEnvelopeContextNVPair("NoReviewFlag", "true");
            } else {
                helper.addEnvelopeContextNVPair("NoReviewFlag", "false");
            }
			
			//adding client claim number
            helper.addEnvelopeContextNVPair(APDDeliveryConstants.CLAIM_NUMBER, 
                                        apdContext.getAPDDeliveryContext().getAPDAlertInfo()
                                        .getAPDCommonInfo().getClientClaimNumber());
            
            // populate a WorkProcessUpdateMessage document
            WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                            WorkProcessUpdateMessageDocument.Factory.newInstance();
            WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                            new WorkProcessUpdateMessageContext();
         // Fix 117663
            if(apdAlertInfoType.getMcfPackageType().toString()
                    .matches(systemConfigurationProxy.getEstimateUploadTypes())) {
                workProcessUpdateMessageContext.setActivityOperation(
                		systemConfigurationProxy.getEstimateUploadAlertDeliveryActivityOperation());
            } else {
                workProcessUpdateMessageContext.setActivityOperation(
                		systemConfigurationProxy.getArtifactAlertDeliveryToShopActivityOperation());
            }
            workProcessUpdateMessageContext.setActivityStatusType(
                                                    ActivityStatusType.BEGIN);
            OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(
                        apdAlertInfoType.getAPDCommonInfo().getInsCoCode());
            //changes by Akshat start for setting collaborator as coCode
            /*workProcessUpdateMessageContext.setCollaborator(
                            Long.toString(orgInfo.getOrgInfo().getOrgID())); 
            */
            workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
            //changes completed
            /*workProcessUpdateMessageContext.setPrivateIndex(
                            apdAlertInfoType.getAPDCommonInfo().getCorrelationId());*/
            
            // set Private Index in WorkProcessUpdateMessageContext
            String privateIndex = apdDeliveryUtil.getPrivateIndexBasedOnCoCdAndWorkItemId(
                                        apdAlertInfoType.getAPDCommonInfo().getInsCoCode(),
                                        apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
                                        
            if (privateIndex == null) {
                throw new MitchellException(
                                        CLASS_NAME, 
                                        methodName,
                                        "Private Index is null");
            }
            
            workProcessUpdateMessageContext.setPrivateIndex(privateIndex);
                            
            this.populateWorkProcessUpdateMessage(
                                            workProcessUpdateMessageContext, 
                                            workProcessUpdateMessageDoc,
                                            apdAlertInfoType.getAPDCommonInfo().getInsCoCode(),
                                            apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
           
            // put WorkProcessUpdateMessage in ME 
            helper.addNewEnvelopeBody(
                    "WorkProcessUpdateMessage",
                    workProcessUpdateMessageDoc, 
                    "WorkProcessUpdateMessage");
            
            // get ME
            meDoc = helper.getDoc();
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdAlertInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }
    
    /**
     * This method populates MitchellEnvelop for Alerts delivery 
     * to Platform Staff user.
     * <p>
     * 
     * @param apdContext
     * A XML Bean of type APDDeliveryContextDocument,
     * that encapsulates the relevant infomation needed to handle alert delievry.
     * @throws MitchellException
     * Mitchell Exception
     * @return MitchellEnvelopeDocument
     * Mitchell Envelop for Alerts
     */
    private MitchellEnvelopeDocument populateMeForAlertsDeliveryToPlatformStaff(
                                        APDDeliveryContextDocument apdContext)
                                        throws MitchellException {
        
        String methodName = "populateMeForAlertsDeliveryToPlatformStaff";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDAlertInfoType apdAlertInfoType = null;
        // Fix 117663
        try {
            apdAlertInfoType = 
                        apdContext.getAPDDeliveryContext().getAPDAlertInfo();
            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
            
            // add Mitchell Envelop context name value pairs
            helper.addEnvelopeContextNVPair(
                                "MessageStatus", 
                                APDDeliveryConstants.ALERTS_MESSAGE_STATUS);
            helper.addEnvelopeContextNVPair(
                                "ProcessingResult", 
                                apdAlertInfoType.getAlertType().toString());
            helper.addEnvelopeContextNVPair(
                                "ProcessingResultNotes", 
                                apdAlertInfoType.getMessage());
            helper.addEnvelopeContextNVPair(
                            "MitchellCompanyCode", 
                            apdAlertInfoType.getAPDCommonInfo().getInsCoCode());
                            
            String currentDate = apdDeliveryUtil.getDateStrForFormat(
                                                    new java.util.Date(), 
                                                    "yyyy-MM-dd'T'HH:mm:ss");
                                                    
            helper.addEnvelopeContextNVPair("SentDateTime", currentDate);
            helper.addEnvelopeContextNVPair(
                            "MitchellWorkItemId", 
                            apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
            
            if (apdAlertInfoType.getNoEstimateReviewFlag()) {
                helper.addEnvelopeContextNVPair("NoReviewFlag", "true");
            } else {
                helper.addEnvelopeContextNVPair("NoReviewFlag", "false");
            }
            
			//adding client claim number
            helper.addEnvelopeContextNVPair(APDDeliveryConstants.CLAIM_NUMBER, 
                                        apdContext.getAPDDeliveryContext().getAPDAlertInfo()
                                        .getAPDCommonInfo().getClientClaimNumber());
										
            // populate a WorkProcessUpdateMessage document
            WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                            WorkProcessUpdateMessageDocument.Factory.newInstance();
            WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                            new WorkProcessUpdateMessageContext();
         // Fix 117663
            workProcessUpdateMessageContext.setActivityOperation(
            		systemConfigurationProxy.getArtifactAlertDeliveryToStaffActivityOperation());
            workProcessUpdateMessageContext.setActivityStatusType(
                                                    ActivityStatusType.BEGIN);
            OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(
                        apdAlertInfoType.getAPDCommonInfo().getInsCoCode());
            //changes done by Akshat for setting the coCode as collaborator.
            /*
            workProcessUpdateMessageContext.setCollaborator(
                            Long.toString(orgInfo.getOrgInfo().getOrgID()));                                     
            */
            workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
            //changes completed
            // set Private Index in WorkProcessUpdateMessageContext
            String privateIndex = apdDeliveryUtil.getPrivateIndexBasedOnCoCdAndWorkItemId(
                                        apdAlertInfoType.getAPDCommonInfo().getInsCoCode(),
                                        apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
                                        
            if (privateIndex == null) {
                throw new MitchellException(
                                        CLASS_NAME, 
                                        methodName,
                                        "Private Index is null");
            }
            
            workProcessUpdateMessageContext.setPrivateIndex(privateIndex);
                            
            this.populateWorkProcessUpdateMessage(
                                            workProcessUpdateMessageContext, 
                                            workProcessUpdateMessageDoc,
                                            apdAlertInfoType.getAPDCommonInfo().getInsCoCode(),
                                            apdAlertInfoType.getAPDCommonInfo().getWorkItemId());
           
            // put WorkProcessUpdateMessage in ME 
            helper.addNewEnvelopeBody(
                    "WorkProcessUpdateMessage",
                    workProcessUpdateMessageDoc, 
                    "WorkProcessUpdateMessage");
            
            // get ME
            meDoc = helper.getDoc();
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdAlertInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }
    
    /**
     * This method populates MitchellEnvelop for Repair/Rework Assignment delivery.
     * <p>
     * 
     * @param apdContext
     * A XML Bean of type APDDeliveryContextDocument,
     * that encapsulates the relevant infomation needed to handle 
     * Repair/Rework Assignment delievry.
     * @throws MitchellException
     * Mitchell Exception
     * @return MitchellEnvelopeDocument
     * Mitchell envelop for RA
     */
    private MitchellEnvelopeDocument populateMeForRADelivery(
                                        APDDeliveryContextDocument apdContext)
                                        throws MitchellException {
        
        String methodName = "populateMeForRADelivery";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null;
        APDRepairAssignmentInfoType apdRAInfoType = null;
        boolean isEstimateAuthorShop = false;
        boolean canUploadEstimate = false;
        
        // Fix 117663
        try {
            apdRAInfoType = 
                apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
            
            // add Mitchell Envelop context name value pairs
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MESSAGE_TYPE,
                apdContext.getAPDDeliveryContext().getMessageType());
            String messageStatus = 
                apdContext.getAPDDeliveryContext()
                    .getAPDRepairAssignmentInfo().getMessageStatus();
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MESSAGE_STATUS2,
                messageStatus);
            // This represents the insurance co code
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MITCHELL_COMPANY_CODE,
                apdRAInfoType.getAPDCommonInfo().getInsCoCode());
            
            // add client claim number
            if (apdRAInfoType.getAPDCommonInfo().isSetClientClaimNumber()) {
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.CLAIM_NUMBER,
                    apdRAInfoType.getAPDCommonInfo().getClientClaimNumber());
            }

            //add Nv pair for status email notification to shop customers
            // Fix 117663
            String settingValue = "TRUE";
            
            settingValue = customSettingProxy.getCompanyCustomSetting(
					apdRAInfoType.getAPDCommonInfo().getInsCoCode(),
					systemConfigurationProxy.getWorkFlowSettingFlagForRepair(),
					systemConfigurationProxy
							.getWorkFlowSettingPropertyForRepair());
			
			 if (settingValue != null) {                 
                 if(settingValue.equals("Y")){
                	 settingValue = "TRUE";
                 }else{
                	 settingValue = "FALSE";
                 }
             }
			
            helper.addEnvelopeContextNVPair(APDDeliveryConstants.REPAIR_STATUS_NOTIFICATION,settingValue);
            
                
            // get XZ UserInfo
            UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
            userInfo.setUserInfo(apdRAInfoType.getAPDCommonInfo()
                                        .getTargetUserInfo().getUserInfo());
            UserInfoDocument xzUserInfo = apdDeliveryUtil.getXZUserInfo(userInfo);
                
            if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CREATE).equals(messageStatus)) {
                if (apdRAInfoType.getAPDCommonInfo().getNotes() != null) {
                    helper.addEnvelopeContextNVPair( 
                        APDDeliveryConstants.NOTES,
                        apdRAInfoType.getAPDCommonInfo().getNotes());
                }
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.MITCHELL_WORK_ITEM_ID,
                    apdRAInfoType.getAPDCommonInfo().getWorkItemId());
                // This represents the task id of work assignment
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.TASK_ID,
                    Long.toString(apdRAInfoType.getTaskId()));
                // This represents the OrgId of the XZ shop 
                // to which the assignment needs to be sent. 
                if(xzUserInfo == null) {
                    throw new RuntimeException("Could not get XZUserInfo for user " 
                        + userInfo.getUserInfo().getOrgCode() 
                        + "-" 
                        + userInfo.getUserInfo().getUserID());
                }
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.SHOP_ID,
                    xzUserInfo.getUserInfo().getOrgID());
                // This represents the CoCd of the reviewer associated 
                // with shop to which the assignment needs to be sent
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.REVIEWER_CO_CD,
                    apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .getAPDCommonInfo().getTargetUserInfo()
                            .getUserInfo().getOrgCode());
                
                // This represents the UserId of the reviewer 
                // associated with shop to which the assignment needs to be sent
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.REVIEWER_ID,
                    apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .getAPDCommonInfo().getTargetUserInfo()
                            .getUserInfo().getUserID());
                    
                // This represents the CoCd of the Body Shop
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.USER_CO_CD,
                    apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .getAPDCommonInfo().getTargetDRPUserInfo()
                            .getUserInfo().getOrgCode());
                    
                // This represents the UserId of the Body Shop
                helper.addEnvelopeContextNVPair( 
                    APDDeliveryConstants.USER_ID,
                    apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .getAPDCommonInfo().getTargetDRPUserInfo()
                            .getUserInfo().getUserID());
                            
                /**
                 * populate a WorkProcessInitiationMessage Document
                 */
                WorkProcessInitiationMessageDocument workProcessInitiationMessageDoc = 
                        WorkProcessInitiationMessageDocument.Factory.newInstance();
                populateWorkProcessInitiationMessage(
                                                apdContext, 
                                                workProcessInitiationMessageDoc, 
                                                xzUserInfo);
                helper.addNewEnvelopeBody(
                    APDDeliveryConstants.WORK_PROCESS_INITIATION_MESSAGE_IDENTIFIER,
                    workProcessInitiationMessageDoc, 
                    APDDeliveryConstants.WORK_PROCESS_INITIATION_MESSAGE_MITCHELL_DOC_TYPE);
            } else if ((APDDeliveryConstants.RA_MESSAGE_STATUS_UPDATE).equals(messageStatus)) {
                // Update notes
                if (apdRAInfoType.getAPDCommonInfo().getNotes() != null) {
                    helper.addEnvelopeContextNVPair( 
                        APDDeliveryConstants.UPDATE_NOTES,
                        apdRAInfoType.getAPDCommonInfo().getNotes());
                }
            } else if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CANCEL).equals(messageStatus)) {
                // Cancellation reason
                // Later on notes have to be replaced with cancel reason code  
                if (apdRAInfoType.getIsRework()) {
                    if (apdRAInfoType.getAPDCommonInfo().getNotes() != null) {
                        helper.addEnvelopeContextNVPair( 
                            APDDeliveryConstants.CANCELLATION_REASON,
                            apdRAInfoType.getAPDCommonInfo().getNotes());
                    }
                }
            } 
            
            // SV Added. Refactored the code to handle supplement update not sent to RC if had compliance - SCR 32188
            String estAuthorType = null;
            Estimate estimate = null;
            
            if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CREATE).equals(messageStatus) 
                    || (APDDeliveryConstants.RA_MESSAGE_STATUS_UPDATE).equals(messageStatus)) {
                        
                        
                /**
                 * populate a Assignment BMS document
                 */
                CIECADocument asmtBMSDoc = 
                    populateAsmtBMS(
                        apdContext.getAPDDeliveryContext()
                            .getAPDRepairAssignmentInfo().getAPDCommonInfo());
                
                if (asmtBMSDoc != null) {
                	apdCommonUtilProxy.logINFOMessage("Adding Assignment BMS in EnvelopeBody");
                    String asmtBMSIdentifier = null;
                    if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CREATE).equalsIgnoreCase(messageStatus)) {
                        asmtBMSIdentifier = APDDeliveryConstants.ASSIGNMENT_BMS_IDENTIFIER_CREATE;
                    } else {
                        asmtBMSIdentifier = APDDeliveryConstants.ASSIGNMENT_BMS_IDENTIFIER_UPDATE;
                    }
                    helper.addNewEnvelopeBody(
                            asmtBMSIdentifier,
                            asmtBMSDoc, 
                            APDDeliveryConstants.ASSIGNMENT_BMS_MITCHELL_DOC_TYPE);
                }                        
                
                /**
                 * populate a Estimate BMS document
                 */
                
                apdCommonUtilProxy.logFINEMessage("apdContext: \n" + apdContext);
                
                        
                // get Estimate document object
                if (apdRAInfoType.isSetEstimateDocId()) {
                    estimate = apdDeliveryUtil.getEstimate(apdRAInfoType.getEstimateDocId());
                }                
                        
                // Estimate Author type
                if (estimate!=null) {
                    estAuthorType = apdDeliveryUtil.getEstimateAuthorType(estimate);
                    helper.addEnvelopeContextNVPair( 
                        APDDeliveryConstants.ESTIMATE_AUTHOR,estAuthorType);
                    
                    // checking if the author is non-shop
                    if (estAuthorType!=null 
        					&& (UserTypeConstants.SHOP_TYPE.equals(estAuthorType)
        						||UserTypeConstants.DRP_SHOP_TYPE.equals(estAuthorType))) {
                    		isEstimateAuthorShop = true; 
                        }
                    
                }

                
                // populate estimate bms if estimate was created by non-shop
                if ( (estimate!=null) && !isEstimateAuthorShop ) {        
                    
                	apdCommonUtilProxy.logINFOMessage("Estimate is not created by BODYSHOP");
                    
                    CIECADocument estBMSDoc = populateEstimateBMS(apdContext, estimate);
                    if (estBMSDoc != null) {
                        
                    	apdCommonUtilProxy.logFINEMessage("Adding Assignment BMS in EnvelopeBody");
                        
                        helper.addNewEnvelopeBody(
                            APDDeliveryConstants.ESTIMATE_BMS_IDENTIFIER,
                            estBMSDoc, 
                            APDDeliveryConstants.ESTIMATE_BMS_MITCHELL_DOC_TYPE);
                    }
                }
                
                // populate can upload estimate based on custom settings
                canUploadEstimate = getUploadEstimateFlagForRepair(isEstimateAuthorShop, apdContext);
                
				if (canUploadEstimate) 
                {           
                    helper.addEnvelopeContextNVPair(APDDeliveryConstants.CAN_UPLOAD_ESTIMATE,"TRUE");
                } else{
					helper.addEnvelopeContextNVPair(APDDeliveryConstants.CAN_UPLOAD_ESTIMATE,"FALSE");
				}
				
                /**
                 * populate a AttachmentInfo document 
                 */
                if (apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .isSetAttachments()) {
                            
                    AttachmentInfoDocument attachmentDoc = AttachmentInfoDocument.Factory.newInstance();
                    populateAttachmentInfo(apdContext, attachmentDoc);
                    
                    String attachmentIdentifier = null;
                    if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CREATE).equals(messageStatus)) {
                        attachmentIdentifier = APDDeliveryConstants.WC_ATTACHMENT_INFO_IDENTIFIER_CREATE;
                    } else {
                        attachmentIdentifier = APDDeliveryConstants.WC_ATTACHMENT_INFO_IDENTIFIER_UPDATE;
                    }
                
                    helper.addNewEnvelopeBody(
                        attachmentIdentifier,
                        attachmentDoc, 
                        APDDeliveryConstants.WC_ATTACHMENT_INFO_MITCHELL_DOC_TYPE);
                }
            }
            
            if ((APDDeliveryConstants.RA_MESSAGE_STATUS_CANCEL).equals(messageStatus) 
                    || (APDDeliveryConstants.RA_MESSAGE_STATUS_UPDATE).equals(messageStatus) 
                    || (APDDeliveryConstants.RA_MESSAGE_STATUS_COMPLETE).equals(messageStatus)) {
                /**
                 * populate a WorkProcessUpdateMessage Document
                 */
                WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                                WorkProcessUpdateMessageDocument.Factory.newInstance();
                WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                                new WorkProcessUpdateMessageContext();
                
                String activityOperation = apdDeliveryUtil.getActivityOperation(messageStatus,
                    apdRAInfoType.getIsRework());
                    
                workProcessUpdateMessageContext.setActivityOperation(
                    activityOperation);
                    
                workProcessUpdateMessageContext.setActivityStatusType(
                                                        ActivityStatusType.BEGIN);
                                                        
                OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(
                            apdRAInfoType.getAPDCommonInfo().getInsCoCode());
                            
                //changes done by Akshat for setting the coCode as collaborator.
                workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
                
                //changes completed
                // set Public Index in WorkProcessUpdateMessageContext
                String publicIndex = apdDeliveryUtil.getPublicIndex(
                    apdRAInfoType.getAPDCommonInfo().getClientClaimNumber(), 
                    apdRAInfoType.getAPDCommonInfo().getInsCoCode());
                if (publicIndex == null) {
                    throw new MitchellException(
                                            CLASS_NAME, 
                                            methodName,
                                            "Public Index is null for RA");
                }
                workProcessUpdateMessageContext.setPublicIndex(publicIndex);
                
                // set Private Index in WorkProcessUpdateMessageContext
                String privateIndex = apdDeliveryUtil.getPrivateIndexForRepairAssignment(
                        apdRAInfoType.getAPDCommonInfo().getInsCoCode(), 
                        apdRAInfoType.getTaskId(),
                        Long.parseLong(xzUserInfo.getUserInfo().getOrgID()));
                if (privateIndex == null) {
                    throw new MitchellException(CLASS_NAME, methodName,
                                                "Private Index is null for RA");
                }
                workProcessUpdateMessageContext.setPrivateIndex(privateIndex);
                                
                this.populateWorkProcessUpdateMessage(
                                                workProcessUpdateMessageContext, 
                                                workProcessUpdateMessageDoc,
                                                apdRAInfoType.getAPDCommonInfo().getInsCoCode(),
                                                apdRAInfoType.getAPDCommonInfo().getWorkItemId());
               
                // put WorkProcessUpdateMessage in ME 
                helper.addNewEnvelopeBody(
                        "WorkProcessUpdateMessage",
                        workProcessUpdateMessageDoc, 
                        "WorkProcessUpdateMessage");
            }

            // get MitchellEnvelope
            meDoc = helper.getDoc();
            apdCommonUtilProxy.logINFOMessage("MitchellEnvelope for RA delivery: \n" + meDoc.toString());
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdRAInfoType.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }
    
    /**
     * This method populates a AttachmentInfoDocument.
     * <p>
     * 
     * @param apdContext
     * @param attachmentInfoDoc
     */
    private void populateAttachmentInfo(
            APDDeliveryContextDocument apdContext, 
            AttachmentInfoDocument attachmentInfoDoc) throws MitchellException {
        
        String methodName = "populateAttachmentInfo";
        logger.entering(CLASS_NAME, methodName);
        
        // PBI 205005 : Add RC thumbnails        
        populateThumbnailAttachmentItems(apdContext); 
        
        AttachmentInfoType attachmentInfo = 
            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo().getAttachments();
            
        if(attachmentInfo != null) { 
            attachmentInfoDoc.setAttachmentInfo(attachmentInfo);
        }
        apdCommonUtilProxy.logFINEMessage("Attachment info doc is: \n" + attachmentInfoDoc.toString());
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method populates WorkProcessInitiationMessage.
     * 
     * @param apdContext
     * @param workProcessInitiationMessageDoc
     * @throws MitchellException
     * Mitchell Exception
     */
    private void populateWorkProcessInitiationMessage(
                                APDDeliveryContextDocument apdContext, 
                                WorkProcessInitiationMessageDocument 
                                    workProcessInitiationMessageDoc,
                                UserInfoDocument xzUserInfo) 
                                throws Exception {
        
        String methodName = "populateWorkProcessInitiationMessage";
        logger.entering(CLASS_NAME, methodName);
        // Fix 117663
        WorkProcessInitiationMessage workProcessInitiationMessage = 
            workProcessInitiationMessageDoc.addNewWorkProcessInitiationMessage();
        
        APDRepairAssignmentInfoType apdRepairAssignmentInfo = 
            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
        
        String coCode = apdRepairAssignmentInfo.getAPDCommonInfo().getInsCoCode();
        OrgInfoDocument orgInfoDoc = apdDeliveryUtil.getOrgInfo(coCode);
        
        String orgCode = null;
        if (orgInfoDoc != null) {
            orgCode = orgInfoDoc.getOrgInfo().getOrgCode();
        }
        //changes by Akshat start for setting collaborator to coCode
        //workProcessInitiationMessage.setWorkProcessCollaborator(insCoOrgIdStr);
        workProcessInitiationMessage.setWorkProcessCollaborator(orgCode);
        //changes copmpletd
        
        String clientClaimNumber = 
            apdRepairAssignmentInfo.getAPDCommonInfo().getClientClaimNumber();
            
        String publicIndex = apdDeliveryUtil.getPublicIndex(
                                                        clientClaimNumber, 
                                                        coCode);
        if (publicIndex == null) {
            publicIndex = UUIDFactory.getInstance().getCeicaUUID();
            apdDeliveryUtil.registerPublicKey(clientClaimNumber, coCode, publicIndex);
        }
        workProcessInitiationMessage.setPublicIndex(publicIndex);
        long xzUserOrgId = Long.parseLong(xzUserInfo.getUserInfo().getOrgID());
        String privateIndex = apdDeliveryUtil.getPrivateIndexForRepairAssignment(
                        coCode, 
                        apdRepairAssignmentInfo.getTaskId(),
                        xzUserOrgId);
        if (privateIndex == null) {
            privateIndex = UUIDFactory.getInstance().getCeicaUUID();
            apdDeliveryUtil.registerPrivateKey(apdRepairAssignmentInfo.getTaskId(), 
                                            xzUserOrgId, 
                                            coCode, 
                                            privateIndex);
        }
        workProcessInitiationMessage.setPrivateIndex(privateIndex);
        
        DefinitionType definition = workProcessInitiationMessage.addNewDefinition();
     // Fix 117663
        if ("IF".equals(coCode)) {
            if (apdRepairAssignmentInfo.getIsRework()) {
                definition.setType(systemConfigurationProxy.getReworkAsmtDefTypeIF());
            } else {
                definition.setType(systemConfigurationProxy.getRprAsmtDefTypeIF());
            }
        } else {
            if (apdRepairAssignmentInfo.getIsRework()) {
                definition.setType(systemConfigurationProxy.getReworkAsmtDefType());
            } else {
                definition.setType(systemConfigurationProxy.getRprAsmtDefType());
            }
        }
        
        definition.setVersion(APDDeliveryConstants.WORK_PROCESS_VERSION);

        // add role mapping
        workProcessInitiationMessage.addNewRoleMapping();        
        
        // add carrier collaborator
        RoleCollaboratorPairType carrierRoleCollaboratorPair =   
            workProcessInitiationMessage.getRoleMapping().addNewRoleCollaboratorPair();
        
        carrierRoleCollaboratorPair.setRole(APDDeliveryConstants.CARRIER_ROLE);
        //changes by Akshat start for setting collaborator to coCode
        //carrierRoleCollaboratorPair.setCollaborator(insCoOrgIdStr);
        carrierRoleCollaboratorPair.setCollaborator(orgCode);
        //changes completed
        
        // add carrier collaborator
        RoleCollaboratorPairType shopRoleCollaboratorPair =  
            workProcessInitiationMessage.getRoleMapping().addNewRoleCollaboratorPair();
        
        shopRoleCollaboratorPair.setRole(APDDeliveryConstants.SHOP_ROLE);
        
        shopRoleCollaboratorPair.setCollaborator(xzUserInfo.getUserInfo().getOrgID());
        
        apdCommonUtilProxy.logINFOMessage("WorkProcessInitiationMessageDocument is: \n" 
            + workProcessInitiationMessageDoc.toString());
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * @param apdContext
     * @throws MitchellException
     * Mitchell Exception
     */
    private CIECADocument populateAsmtBMS(
                                /*APDDeliveryContextDocument apdContext) */
                                BaseAPDCommonType apdCommonType) 
                                throws MitchellException {
        String methodName = "populateAsmtBMS";
        logger.entering(CLASS_NAME, methodName);
        
        /*APDRepairAssignmentInfoType apdRepairAssignmentInfo = 
            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();*/
        CIECADocument asmtBmsDoc = null;
            
        try {
            // get latest assignment bms from claim svc
        	// Fix 97154 : Use ClaimServiceRemote and ClaimServiceClient
        	// Fix 117663
        	// ClaimServiceRemote claimRemote = ClaimServiceClient.getEjb();
        	// ClaimRemote claimRemote = ClaimClientSupport.getEJB();
            // old method
            /*asmtBmsDoc = (CIECADocument) claimRemote.readClaimInfoIntoBms(
                userInfo, 
                apdRepairAssignmentInfo.getAPDCommonInfo().getClaimNumber());*/
            UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
            /*userInfo.setUserInfo(
                apdRepairAssignmentInfo.getAPDCommonInfo().getSourceUserInfo().getUserInfo());*/
            userInfo.setUserInfo(
                apdCommonType.getSourceUserInfo().getUserInfo());
            /*AssignmentAddRqDocument assignmentAddRq = 
                (AssignmentAddRqDocument) claimRemote.readClaimInfoIntoBms(
                    userInfo, 
                    apdRepairAssignmentInfo.getAPDCommonInfo().getClaimNumber());*/
            /*AssignmentAddRqDocument assignmentAddRq = 
                (AssignmentAddRqDocument) claimRemote.readClaimInfoIntoBms(
                    userInfo, 
                    apdRepairAssignmentInfo.getAPDCommonInfo().getClientClaimNumber());*/
            // Fix 117663
           /* AssignmentAddRqDocument assignmentAddRq = 
                (AssignmentAddRqDocument) claimRemote.readClaimInfoIntoBms(
                    userInfo, 
                    apdCommonType.getClientClaimNumber()); */
            AssignmentAddRqDocument assignmentAddRq = claimServiceProxy.readClaimInfoIntoBms(
                    userInfo, 
                    apdCommonType.getClientClaimNumber());
            
            CIECA cieca = null;
            if (assignmentAddRq != null) {
                asmtBmsDoc = CIECADocument.Factory.newInstance();
                cieca = asmtBmsDoc.addNewCIECA();
                cieca.setAssignmentAddRq(assignmentAddRq.getAssignmentAddRq());
            }
            
            apdCommonUtilProxy.logFINEMessage("Assignment BMS: \n" + cieca);
            
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdCommonType.getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return asmtBmsDoc;
    }
    
    /**
     * @param apdContext
     * @param estimateBmsDoc
     * @throws MitchellException
     * Mitchell Exception
     */
    private CIECADocument populateEstimateBMS(
                                APDDeliveryContextDocument apdContext,
                                Estimate estimate) 
                                throws MitchellException {
        
        String methodName = "populateEstimateBMS";
        logger.entering(CLASS_NAME, methodName);
        
        APDRepairAssignmentInfoType apdRepairAssignmentInfo = 
            apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
        CIECADocument estimateBmsDoc = null;
        Attachment[] attachments = null;
        // Fix 117663
        try {

            String estimateBmsStr = null; 
               
            if(estimate!=null 
                && estimate.getEstimateXmlEstimate().getXmlData()!=null) {

                estimateBmsStr = estimate.getEstimateXmlEstimate().getXmlData();
                apdCommonUtilProxy.logINFOMessage("Estimate BMS string: \n" + estimateBmsStr);
                if (estimateBmsStr != null && !"".equals(estimateBmsStr.trim())) {
                    VehicleDamageEstimateAddRqDocument vehDamageEstAddRqDoc = 
                        VehicleDamageEstimateAddRqDocument.Factory.parse(estimateBmsStr);
                    estimateBmsDoc = CIECADocument.Factory.newInstance();
                    estimateBmsDoc.addNewCIECA()
                        .setVehicleDamageEstimateAddRq(
                            vehDamageEstAddRqDoc.getVehicleDamageEstimateAddRq());
                }
                
                
                apdCommonUtilProxy.logINFOMessage("Estimate BMS: \n" + estimateBmsDoc);
                
                
                // get attachments
                attachments = apdDeliveryUtil.getEstimateAttachments(
                    apdRepairAssignmentInfo.getEstimateDocId());
                
                /**
                 * Send Print PDF or Text along with Attachments
                 */
                includePrintPDF(estimate, attachments, apdContext);
                
                // Include estimate MIE in attachments 
                includeMIE(estimate, attachments, apdContext);
            }
            // SV added check to throw exception if we cannot get estimate BMS
            else {
                throw new Exception("Cannot get Estimate BMS for estimatedocid " + 
                    apdRepairAssignmentInfo.getEstimateDocId());
            }
            
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        apdRepairAssignmentInfo.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return estimateBmsDoc;
    }
    
    /**
     * This method ensures to send MIE Estimate as attachment 
     * along with APDDeliveryContext.
     * 
     * @param doc
     * @param apdContext
     * @throws Exception
     */
    private void includeMIE(Estimate estimate, Attachment[] attachments,  
        APDDeliveryContextDocument apdContext) throws Exception {
        
        String methodName = "includeMIE";
        logger.entering(CLASS_NAME, methodName);
        
        boolean mieEstimateFoundInRAAtcmt = false;
     // Fix 117663
        String mieEstimateAttachmentType = 
        	systemConfigurationProxy.getMIEEstimateAttachmentTypes();
        AttachmentInfoType attachmentInfo = 
            apdContext.getAPDDeliveryContext()
                .getAPDRepairAssignmentInfo().getAttachments();
                
        if (attachmentInfo != null) {
            AttachmentItemType[] attachmentItemArray = 
                attachmentInfo.getAttachmentInfoList().getAttachmentItemArray();
            AttachmentItemType attachmentItemTypeTemp = null;
            String attachmentType = null;
            
            // traverse through the attachments received with RA in APDContext
            for (int i = 0; i < attachmentItemArray.length; i++) {
                attachmentItemTypeTemp = attachmentItemArray[i];
                attachmentType = attachmentItemTypeTemp.getAttachmentType();
                if (mieEstimateAttachmentType.matches(attachmentType)) {
                	apdCommonUtilProxy.logFINEMessage(
                        "MIE Estimate attachment found in RA attachments- " 
                            + attachmentItemTypeTemp.toString());
                    mieEstimateFoundInRAAtcmt = true;
                    break;
                } 
            }
        }
        
        if (!mieEstimateFoundInRAAtcmt) {
        	apdCommonUtilProxy.logFINEMessage(
                "MIE Estimate attachment not found in RA attachments");
            String estAttachmentTypeCode = null;
            Attachment estAttachment = null;
            
            // loop thru' attachments
            for(int z=0; z <attachments.length; z++) {
                estAttachment = attachments[z];
                estAttachmentTypeCode = estAttachment.getAttachmentTypeCode();
                if (mieEstimateAttachmentType.matches(estAttachmentTypeCode)) {
                    
                	apdCommonUtilProxy.logFINEMessage(
                            "MIE Estimate attachment found in Estimate- " 
                                + estAttachment.toString());
                    
                    addAttachmentToAPDContext(
                                apdContext, 
                                estAttachment, 
                                estimate);
                    break;
                }                
                
            }
        }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    private void includePrintPDF(Estimate estimate, Attachment[] attachments, 
        APDDeliveryContextDocument apdContext) throws Exception {
        
        boolean printPDFFound = false;
        Attachment attachment = null;
        String attachmentTypeCode = null;
                
        // traverse through all attachments to find Print PDFs
     // Fix 117663
        String pdfAttachmentTypesForRepair = 
        	systemConfigurationProxy.getPDFAttachmentTypesForRepair();
                
        // loop thru' attachments
        for(int z=0; z<attachments.length; z++) {
            
            attachment = attachments[z];
            attachmentTypeCode = attachment.getAttachmentTypeCode();
                    
            if (attachmentTypeCode.matches(pdfAttachmentTypesForRepair)) {
                
            	apdCommonUtilProxy.logFINEMessage(
                        "Print PDF attachment found- AttachmentTypeCode: " 
                            + attachmentTypeCode);
                
                printPDFFound = true;
                
                // add Print PDF to APD context
                addAttachmentToAPDContext(
                                    apdContext, 
                                    attachment, 
                                    estimate);
                                    
                break;
            }             
        }
                
        // if Print PDF is not found look for Text attachment
        boolean textFound = false;
        if (!printPDFFound) {
            
            
        	apdCommonUtilProxy.logFINEMessage("No Print PDF attachment found");
            
            String textAttachmentTypesForRepair = 
            	systemConfigurationProxy
                                    .getTextAttachmentTypesForRepair();
            
            // loop thru' attachments
            for(int z=0; z <attachments.length; z++) {
                
                attachment = attachments[z];
                attachmentTypeCode = attachment.getAttachmentTypeCode();

                if (attachmentTypeCode.matches(textAttachmentTypesForRepair)) {
                    textFound = true;
                    
                    
                    apdCommonUtilProxy.logFINEMessage("Text attachment found- AttachmentTypeCode: " 
                            + attachmentTypeCode);
                    
                    // add Text to APD context
                    addAttachmentToAPDContext(
                                        apdContext, 
                                        attachment, 
                                        estimate);
                    break;
                }                
                
            }
            
            if (!textFound) {
                
            	apdCommonUtilProxy.logFINEMessage("No text attachment found");
                
            }
            
        }
        if (printPDFFound || textFound) {
            
        	apdCommonUtilProxy.logFINEMessage("At least one print PDF/Text attachment found- " 
                    + "The updated APD context is: \n"
                    + apdContext.toString());
            
        }
    }
    
    /**
     * This method adds the given Attachment to the given APD context.
     * 
     * @param apdContext
     * @param attachment
     * @param estimate
     * @throws Exception
     */
    private void addAttachmentToAPDContext(
                                    APDDeliveryContextDocument apdContext, 
                                    Attachment attachment, 
                                    Estimate estimate) throws Exception{
        
        if (!apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                        .isSetAttachments()) {
            // add new attachment info
            apdContext.getAPDDeliveryContext()
                            .getAPDRepairAssignmentInfo().addNewAttachments();
            // add new empty attachment list
            apdContext.getAPDDeliveryContext()
                    .getAPDRepairAssignmentInfo().getAttachments()
                                                .addNewAttachmentInfoList();
        } 
        // get attachment info
        AttachmentInfoType attachmentInfo = 
                    apdContext.getAPDDeliveryContext()
                                .getAPDRepairAssignmentInfo().getAttachments();
        
        // populate new Attachment Item
        populateAttachmentItem(attachmentInfo, attachment, estimate);
    }
    
    /**
     * This method trasforms the given Attachment in to AttachmentItemType.
     * 
     * @param attachmentInfo
     * @param attachment
     * @param estimate
     * @return AttachmentItemType
     * @throws Exception
     */
    private void populateAttachmentItem(
                                    AttachmentInfoType attachmentInfo, 
                                    Attachment attachment, 
                                    Estimate estimate) throws Exception {
        AttachmentItemType attachmentItem = 
                            attachmentInfo.getAttachmentInfoList().addNewAttachmentItem();
        // Fix 117663
        
        /*if (attachment.getId() != null) {
            attachmentItem.setAttachmentId(attachment.getId().toString());
        } else {
            throw new Exception("Insufficient Attachment data: AttachmentId not available");
        }*/
        attachmentItem.setAttachmentId(UUIDFactory.getInstance().getCeicaUUID());
        
        if (attachment.getAttachmentTypeCode() != null) {
            attachmentItem.setAttachmentType(attachment.getAttachmentTypeCode());
        } else {
            throw new Exception("Insufficient Attachment data: AttachmentTypeCode not available");
        }
        
        if (attachment.getCreatedDate() != null) {
            attachmentItem.setDateAdded(
            		apdDeliveryUtil.dateToCalendar(attachment.getCreatedDate(), "yyyy-MM-dd'T'HH:mm:ss"));
        } else {
            throw new Exception("Insufficient Attachment data: CreatedDate not available");
        }
        
        if (attachment.getUpdatedDate() != null) {
            attachmentItem.setDateModified(
            		apdDeliveryUtil.dateToCalendar(attachment.getUpdatedDate(), "yyyy-MM-dd'T'HH:mm:ss"));
        }
        if (attachment.getReferenceId() != null) {
            attachmentItem.setDocStoreFileReference(
                                attachment.getReferenceId().toString());
        }
        
        if (attachment.getAttachmentFilename() != null) {
            attachmentItem.setActualFileName(attachment.getAttachmentFilename());
        } else {
            throw new Exception("Insufficient Attachment data: ActualFileName not available");
        }
        
        attachmentItem.setStatus(0);
        
        if (attachment.getAttachmentMemo() != null) {
            attachmentItem.addNewNotes();
            attachmentItem.getNotes().setStringValue(attachment.getAttachmentMemo());
        }
        
        if (estimate.getSupplementNumber() != null) {
            attachmentItem.setSupplementNo(
                            Integer.parseInt(
                                estimate.getSupplementNumber().toString()));
        } else {
            throw new Exception("Insufficient Attachment data: SupplementNo not available");
        }
        
        if (estimate.getCorrectionNumber() != null) {
            attachmentItem.setCorrectionNo(
                            Integer.parseInt(
                                estimate.getCorrectionNumber().toString()));
        }
        //attachmentItem.setAssociatedAttachmenItemtList();
        //attachmentItem.setAttachmentNameValuePairList();
    }
    
    /**
     * This method populates MitchellEnvelop for Estimate delivery.
     * 
     * @param apdDeliveryDoc
     * A XML Bean of type APDDeliveryContextDocument,
     * that encapsulates the relevant infomation needed to handle 
     * Repair/Rework Assignment delievry.
     * @throws MitchellException
     * Mitchell Exception
     * @return MitchellEnvelopeDocument
     * Mitchell envelop for Estimate
     */
    private MitchellEnvelopeDocument populateMeForEstimateDelivery(
                                    APDDeliveryContextDocument apdDeliveryDoc)
                                    throws MitchellException {

        String methodName = "populateMeForEstimateDelivery";
        logger.entering(CLASS_NAME, methodName);
        MitchellEnvelopeDocument meDoc = null; 
        BaseAPDCommonType baseAPDCommonType = null;
        // Fix 117663
        try {

            apdDeliveryUtil.getDateStrForFormat(
                                    new java.util.Date(), "yyyy-MM-dd'T'HH:mm:ss");
        
            APDDeliveryContextDocument apdDeliveryContext = null; 
            apdDeliveryContext = apdDeliveryDoc;
            APDEstimateStatusInfoType estimateStatusInfoType = 
                apdDeliveryContext.getAPDDeliveryContext().
                                                        getAPDEstimateStatusInfo();

            baseAPDCommonType = 
                apdDeliveryContext.getAPDDeliveryContext().
                                    getAPDEstimateStatusInfo().getAPDCommonInfo();

            String coCode = baseAPDCommonType.getInsCoCode();
            String workItenId = baseAPDCommonType.getWorkItemId();
        
            // get estimate status
            APDEstimateStatusEventType.Enum estimateStatus = 
                apdDeliveryContext.getAPDDeliveryContext().
                    getAPDEstimateStatusInfo().getEstimateStatusEvent();
	
            // get XZ UserInfo
            UserInfoDocument userInfo = UserInfoDocument.Factory.newInstance();
            userInfo.setUserInfo(baseAPDCommonType.getTargetUserInfo().getUserInfo());
            UserInfoDocument xzUserInfo = apdDeliveryUtil.getXZUserInfo(userInfo);
            String orgId = xzUserInfo.getUserInfo().getOrgID();


            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
            
            // add Mitchell Envelop context name value pairs
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MESSAGE_TYPE,
                apdDeliveryContext.getAPDDeliveryContext().getMessageType());

            // message status
            helper.addEnvelopeContextNVPair( 
                APDDeliveryConstants.MESSAGE_STATUS2,
                APDDeliveryConstants.MESSAGE_STATUS);

            // Estimate Review Status
            helper.addEnvelopeContextNVPair(
                                        "EstimateReviewStatus", 
                                        estimateStatus.toString());

            helper.addEnvelopeContextNVPair("MitchellCompanyCode", 
                coCode);

            helper.addEnvelopeContextNVPair("ShopId", orgId);
            helper.addEnvelopeContextNVPair("MitchellWorkItemId", workItenId);
			
			//adding client claim number
            helper.addEnvelopeContextNVPair(APDDeliveryConstants.CLAIM_NUMBER,
                                apdDeliveryContext.getAPDDeliveryContext().getAPDEstimateStatusInfo()
                                .getAPDCommonInfo().getClientClaimNumber());
            
            // populate a WorkProcessUpdateMessage document
            WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                WorkProcessUpdateMessageDocument.Factory.newInstance();
            WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                                        new WorkProcessUpdateMessageContext();  
         // Fix 117663
            workProcessUpdateMessageContext.setActivityOperation(
            		systemConfigurationProxy.getEstimateDeliveryActivityOperation());
            
            workProcessUpdateMessageContext.setActivityStatusType(
                                                    ActivityStatusType.BEGIN);

            // get ins. co code org id
            OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(coCode);
                            
            //changes done by Akshat for setting the coCode as collaborator.
            /*
            workProcessUpdateMessageContext.setCollaborator(
                            Long.toString(orgInfo.getOrgInfo().getOrgID()));                                     
            */
            workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
            //changes completed

            // set Private Index in WorkProcessUpdateMessageContext
            String privateIndex = apdDeliveryUtil.getPrivateIndexBasedOnCoCdAndEstDocId(
                                        coCode,
                                        estimateStatusInfoType.getEstimateDocId());

            if (privateIndex == null) {
            
                throw new MitchellException(CLASS_NAME, methodName,
                                            "Private Index is null for Req For Estimate????");
            }
            
            workProcessUpdateMessageContext.setPrivateIndex(privateIndex);

            // set Public Index in WorkProcessUpdateMessageContext
            /*  Commented now for WC-RC testing on dev.      
                           
            String publicIndex = APDDeliveryUtil.getPublicIndex(
                                    estimateStatusInfoType.getAPDCommonInfo().getClientClaimNumber(),
                                    coCode);

            if (publicIndex == null) {
            
                throw new MitchellException(CLASS_NAME, methodName,
                                            "PublicIndex is null for Req For Estimate????");
            }
            
            workProcessUpdateMessageContext.setPublicIndex(publicIndex);
            */

            populateWorkProcessUpdateMessage(
                                            workProcessUpdateMessageContext, 
                                            workProcessUpdateMessageDoc,
                                            coCode,
                                            estimateStatusInfoType.getAPDCommonInfo().getWorkItemId());

            // put WorkProcessUpdateMessage in ME
            helper.addNewEnvelopeBody(
                    "WorkProcessUpdateMessage",
                    workProcessUpdateMessageDoc, 
                    "WorkProcessUpdateMessage");
            meDoc = helper.getDoc();

        } catch (Exception me) {
            throw new MitchellException(
                APDDeliveryConstants.ERROR_IN_POPULATING_MITCHELL_ENVELOPE,
                CLASS_NAME, 
                methodName, 
                baseAPDCommonType.getWorkItemId(), 
                APDDeliveryConstants.ERROR_IN_POPULATING_MITCHELL_ENVELOPE_MSG
                    + "\n" 
                    + AppUtilities.getStackTraceString(me, true));        
        }        
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;
    }   
    
    /**
     * This method populates a WorkProcessUpdateMessageDocument.
     * <p>
     * 
     * @param workProcessUpdateMessageContext
     * Input for WorkProcessUpdateMessageDocument.
     * @param workProcessUpdateMessageDoc
     * WorkProcessUpdateMessageDocument to be populated.
     */
    private void populateWorkProcessUpdateMessage(
            WorkProcessUpdateMessageContext workProcessUpdateMessageContext,
            WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc,
            String coCode,
            String workItemId) throws Exception {
        
        String methodName = "populateWorkProcessUpdateMessage";
        logger.entering(CLASS_NAME, methodName);
        
        WorkProcessUpdateMessage workProcessUpdateMessage = 
                workProcessUpdateMessageDoc.addNewWorkProcessUpdateMessage();
                    
        workProcessUpdateMessage.setActivityOperation(
                    workProcessUpdateMessageContext.getActivityOperation());
        workProcessUpdateMessage.setActivityStatus(
                    workProcessUpdateMessageContext.getActivityStatusType());
                    
        // populate private index
        //APDDeliveryUtil.populatePrivateIndexForAlerts(coCode, workItemId, workProcessUpdateMessageContext);
        
        workProcessUpdateMessage.setPrivateIndex(
                    workProcessUpdateMessageContext.getPrivateIndex());
        workProcessUpdateMessage.setPublicIndex(
                    workProcessUpdateMessageContext.getPublicIndex());
        
        workProcessUpdateMessage.setCollaborator(
                    workProcessUpdateMessageContext.getCollaborator());
        
        logger.exiting(CLASS_NAME, methodName);
        
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.PlatformDeliveryHandler#deliverRequestForStaffSupplement(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverRequestForStaffSupplement(APDDeliveryContextDocument apdContext) 
                                                    throws MitchellException {
        
        String methodName = "deliverRequestForStaffSupplement";
        
        logger.entering(CLASS_NAME, methodName);
        
        APDRequestStaffSupplementInfoType requestSupplement = 
                                apdContext.getAPDDeliveryContext().getAPDRequestStaffSupplementInfo();
                            
        // populate MitchellEnvelop
        MitchellEnvelopeDocument meDoc = this.populateMeForRequestForStaffSupplementDelivery(apdContext);
        
        // get the event id from the SET file
        // Fix 117663
        int eventId = Integer.parseInt(systemConfigurationProxy.getReqForStaffSupplementDeliveryEventId());
        
        // populate MessagingContext 
        UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
        
        userInfoDoc.setUserInfo(requestSupplement.getAPDCommonInfo().getTargetDRPUserInfo().getUserInfo());
                                        
        MessagingContext msgContext = new MessagingContext(
                                                eventId,
                                                meDoc,
                                                userInfoDoc,
                                                requestSupplement.getAPDCommonInfo().getWorkItemId(),
                                                "");
        
        apdCommonUtilProxy.logINFOMessage("msgContext "+msgContext.toString());
        // publish message to message bus
        this.publishToMessageBus(msgContext);
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method populates MitchellEnvelope for delivering Request for Staff Supplement.
     * @param apdContext APDDeliveryContextDocument
     * @return MitchellEnvelopeDocument MitchellEnvelopeDocument
     * @throws MitchellException MitchellException 
     */
    private MitchellEnvelopeDocument populateMeForRequestForStaffSupplementDelivery(
                                            APDDeliveryContextDocument apdContext) 
                                                    throws MitchellException {
        
        String methodName = "populateMeForRequestForStaffSupplementDelivery";
        
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument meDoc = null; 
        
        APDRequestStaffSupplementInfoType requestSupplement = null;
        // Fix 117663
        // Fix 117663
        try {
            
            requestSupplement = 
                                apdContext.getAPDDeliveryContext().getAPDRequestStaffSupplementInfo();
            // create ME
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
            
            helper.addEnvelopeContextNVPair("MitchellWorkItemId",
                                            requestSupplement.getAPDCommonInfo().getWorkItemId());
            
            // add Mitchell Envelop context name value pairs
            helper.addEnvelopeContextNVPair("MessageStatus",
                                            requestSupplement.getMessageStatus());
                                            
            // Create WorkProcessUpdateMessageContext object
            WorkProcessUpdateMessageContext workProcessUpdateMessageContext = 
                            new WorkProcessUpdateMessageContext();
                            
            if (APDDeliveryConstants.REQ_STAFF_SUPPLEMENT_ACCEPT.equalsIgnoreCase(
                                            requestSupplement.getMessageStatus())){
                
                // set Accepted Activity Operation in WorkProcessUpdateMessageContext
            	workProcessUpdateMessageContext.setActivityOperation(
                		systemConfigurationProxy.getReqForStaffSupplementAcceptedActivityOperation());
                
            } else if (APDDeliveryConstants.REQ_STAFF_SUPPLEMENT_REJECT.equalsIgnoreCase(
                                                    requestSupplement.getMessageStatus())) {
                
                if (requestSupplement.isSetRejectReason()) {
                    
                    helper.addEnvelopeContextNVPair("RejectReason",
                                            requestSupplement.getRejectReason().toString());
                }
                
                if (requestSupplement.isSetRejectReasonNotes()) {    
                                            
                    helper.addEnvelopeContextNVPair("RejectReasonNotes",
                                            requestSupplement.getRejectReasonNotes());
                }
                
                if (requestSupplement.isSetSystemRejected()) {  
                                              
                    helper.addEnvelopeContextNVPair("SystemRejected",
                                            String.valueOf(requestSupplement.getSystemRejected()));
                }
                
                // set Rejected Activity Operation in WorkProcessUpdateMessageContext
                workProcessUpdateMessageContext.setActivityOperation(
                		systemConfigurationProxy.getReqForStaffSupplementRejectedActivityOperation());
            }
            
            // set Activity Status Type in WorkProcessUpdateMessageContext 
            workProcessUpdateMessageContext.setActivityStatusType(
                                                    ActivityStatusType.BEGIN);
            
            // Get OrgInfoDocument                                        
            OrgInfoDocument orgInfo = apdDeliveryUtil.getOrgInfo(
                        requestSupplement.getAPDCommonInfo().getInsCoCode());
            
            // set Collaborator in WorkProcessUpdateMessageContext            
            //changes done by Akshat for setting the coCode as collaborator.
            /*
            workProcessUpdateMessageContext.setCollaborator(
                            Long.toString(orgInfo.getOrgInfo().getOrgID()));                                     
            */
            workProcessUpdateMessageContext.setCollaborator(orgInfo.getOrgInfo().getOrgCode());
            //changes completed            
            
            // set Private Index in WorkProcessUpdateMessageContext
            String privateIndex = apdDeliveryUtil.getPrivateIndexBasedOnCoCdAndWorkItemId(
                                        requestSupplement.getAPDCommonInfo().getInsCoCode(),
                                        requestSupplement.getAPDCommonInfo().getWorkItemId());
                                        
            if (privateIndex == null) {
            
                throw new MitchellException(CLASS_NAME, methodName,
                                            "Private Index is null for Req For Staff Supplement????");
            }
            
            workProcessUpdateMessageContext.setPrivateIndex(privateIndex);
        
            // set Public Index in WorkProcessUpdateMessageContext               
            String publicIndex = apdDeliveryUtil.getPublicIndex(
                                    requestSupplement.getAPDCommonInfo().getClientClaimNumber(),
                                    requestSupplement.getAPDCommonInfo().getInsCoCode());
        
            if (publicIndex == null) {
            
                throw new MitchellException(CLASS_NAME, methodName,
                                            "PublicIndex is null for Req For Staff Supplement????");
            }
            
            workProcessUpdateMessageContext.setPublicIndex(publicIndex);
                            
            // Create instance of WorkProcessUpdateMessage Document
            WorkProcessUpdateMessageDocument workProcessUpdateMessageDoc = 
                                WorkProcessUpdateMessageDocument.Factory.newInstance();
            
            // Populate WorkProcessUpdateMessageDocument from WorkProcessUpdateMessageContext            
            this.populateWorkProcessUpdateMessage(
                                            workProcessUpdateMessageContext, 
                                            workProcessUpdateMessageDoc,
                                            requestSupplement.getAPDCommonInfo().getInsCoCode(),
                                            requestSupplement.getAPDCommonInfo().getWorkItemId());
           
            // put WorkProcessUpdateMessage in MitchellEnvelope 
            helper.addNewEnvelopeBody("WorkProcessUpdateMessage",
                                      workProcessUpdateMessageDoc,
                                      "WorkProcessUpdateMessage");
            
            // get Mitchell Envelope
            meDoc = helper.getDoc();
            
        } catch (MitchellException me) {
            
            throw me;
            
        } catch (Exception e) {
            
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        requestSupplement.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                        + "\n"
                        + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
        return meDoc;
    }
    
    /**
     * This method populates RC thumbnails while delivering RA to RepairCenter.
     * @param apdContext APDDeliveryContextDocument
     * @throws MitchellException
     */
    private void populateThumbnailAttachmentItems(APDDeliveryContextDocument apdContext) throws MitchellException {
        
        String methodName = "populateThumbnailAttachmentItems";
        logger.entering(CLASS_NAME, methodName);                                                
        try {
            
            Long exposureId = null;
            String workitemId = null;
            BaseAPDCommonType commonType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo().getAPDCommonInfo();            
            
            if (commonType != null && commonType.isSetSuffixId()) {
                exposureId = Long.valueOf(commonType.getSuffixId());
                workitemId = commonType.getWorkItemId();                               
            }
                                                                 
            if (exposureId != null) {
                
                List<String> docTypes = new ArrayList<String>();
                docTypes.add(APDDeliveryConstants.DOCTYPE_PHOTO);           
                Document[] photoDocs= estimatePackageProxy.getDocumentsByExposureId(exposureId, docTypes); 
                
                if (photoDocs != null && photoDocs.length >0) {
                    
                    for (int index1 = 0; index1 < photoDocs.length; index1++) {
                       
                        
                        Set<Attachment> attachements = photoDocs[index1].getAttachments();
                        if (attachements != null && !attachements.isEmpty()) {
                            
                            Attachment attachment = null;
                            Attachment imageAttachment = null;
                            Attachment thumbnailAttachment = null;
                            // Fetch all current attachments.
                            AttachmentInfoType attachmentInfo =  apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo().getAttachments();   
                            Iterator<Attachment> itr = attachements.iterator();
                            
                            while (itr.hasNext()) {
                                attachment = (Attachment)itr.next();
                                
                                if (APDDeliveryConstants.ATTACHMENTTYPE_THUMBNAIL.equalsIgnoreCase(attachment.getAttachmentTypeCode())) {
                                    thumbnailAttachment = attachment;
                                    
                                } else if (APDDeliveryConstants.ATTACHMENTTYPE_IMAGE.equalsIgnoreCase(attachment.getAttachmentTypeCode())) {
                                    imageAttachment = attachment;
                                }                                
                            }
                            
                            if (thumbnailAttachment != null && imageAttachment != null) {
                            	
                            	AttachmentItemType[] allAttachments = attachmentInfo.getAttachmentInfoList().getAttachmentItemArray();
                            	 
                                    for (int index2 = 0; index2 < allAttachments.length; index2++) {
                                                                            
                                        if (imageAttachment.getReferenceId() != null && imageAttachment.getReferenceId().toString().equalsIgnoreCase(allAttachments[index2].getDocStoreFileReference())) {
                                            
                                        	String attachmentId = UUIDFactory.getInstance().getCeicaUUID();
                                            // Build and attach new thumbnail AttachmentItem to existing AttachmentInfoList.
                                            AttachmentItemType attachmentItem =  attachmentInfo.getAttachmentInfoList().addNewAttachmentItem();
                                            // Set Attachmentid to newly added thumbnail AttachmentItem.
                                            attachmentItem.setAttachmentId(attachmentId);
                                            
                                            if (thumbnailAttachment.getAttachmentFilename() != null) {
                                                attachmentItem.setActualFileName(thumbnailAttachment.getAttachmentFilename());
                                            } else {
                                                throw new Exception("Insufficient Attachment data: AttachmentFilename not available");
                                            }
                                            
                                            if (thumbnailAttachment.getAttachmentTypeCode() != null) {
                                                attachmentItem.setAttachmentType(thumbnailAttachment.getAttachmentTypeCode());
                                            } else {
                                                throw new Exception("Insufficient Attachment data: AttachmentTypeCode not available");
                                            }
                                            
                                            if (thumbnailAttachment.getCreatedDate() != null) {
                                                attachmentItem.setDateAdded(apdDeliveryUtil.dateToCalendar(thumbnailAttachment.getCreatedDate(), APDDeliveryConstants.DATE_FORMAT_YYYY_MM_DD));
                                            } else {
                                                throw new Exception("Insufficient Attachment data: CreatedDate not available");
                                            }
                                                                                                                
                                            if (thumbnailAttachment.getUpdatedDate() != null) {
                                                attachmentItem.setDateModified(apdDeliveryUtil.dateToCalendar(thumbnailAttachment.getUpdatedDate(), APDDeliveryConstants.DATE_FORMAT_YYYY_MM_DD));
                                            } 
                                            
                                            if (thumbnailAttachment.getReferenceId() != null) {
                                                attachmentItem.setDocStoreFileReference(thumbnailAttachment.getReferenceId().toString());
                                            }
                                            
                                            if (attachment.getAttachmentMemo() != null) {
                                                attachmentItem.addNewNotes();
                                                attachmentItem.getNotes().setStringValue(thumbnailAttachment.getAttachmentMemo());
                                            }                                    
                                            // Set Status 0.
                                            attachmentItem.setStatus(0);
                                            if (allAttachments[index2].getAssociatedAttachmenItemtList() == null) {
                                                allAttachments[index2].addNewAssociatedAttachmenItemtList();                                            
                                            }
                                            // Set thumbnail attachmentId into Image- AssociatedItemAttachmentId.
                                            allAttachments[index2].getAssociatedAttachmenItemtList().addAssociatedItemAttachmentId(attachmentId);                                                                 
                                        }                                
                                    }
                            }
                        }
                     }                
                     
                } else {
                    // No photo docs retrieved for exposure.                    
                	apdCommonUtilProxy.logINFOMessage("No Documents of Doctype PHOTO returned for exposureId =" + exposureId);                    
                }
            
            } else {
                // No suffixId available.                
            	apdCommonUtilProxy.logINFOMessage("SuffixId not available in APDRepairAssignmentInfo for workitemId = " + workitemId);
                
            }
        } catch (MitchellException me) {
                
                throw me;
                
        } catch (Exception e) {
                
                throw new MitchellException(CLASS_NAME,
                                            methodName,
                                            "Error occured while adding RC Thumbnails"
                                            + "\n"
                                            + AppUtilities.getStackTraceString(e, true));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
    }
    
    /**
     * 
     * This method use SET file to check, if CanUploadEstimateFlag flag needs to be returned true
     * 
     */
    private boolean getUploadEstimateFlagForRepair(boolean isEstimateAuthorShop, 
    		APDDeliveryContextDocument apdContext) throws Exception {
    	
    	boolean retValue = false;
    	String overrideCoCodeList = null;
    	
    	// get the override co codes from SET file
    	overrideCoCodeList = systemConfigurationProxy.getOverrideCanUploadEstimateFlagCoCodes();

    	// If the INS co code matches with the allowedlist from SET file, set the boolean to true
    	if(overrideCoCodeList!=null && apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo().
			getAPDCommonInfo().getInsCoCode().toUpperCase().matches(overrideCoCodeList)) {
    		retValue = true;
    	} else {
    		// Fall back to std approach; if shop wrote the estimate set boolean to true.
			if (isEstimateAuthorShop) {           
                retValue = true;
            } 
    	}
    	
    	return retValue;
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
	 * @return the claimServiceProxy
	 */
	public ClaimServiceProxy getClaimServiceProxy() {
		return claimServiceProxy;
	}

	/**
	 * @param claimServiceProxy the claimServiceProxy to set
	 */
	public void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy) {
		this.claimServiceProxy = claimServiceProxy;
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

	public EstimatePackageProxy getEstimatePackageProxy() {
		return estimatePackageProxy;
	}

	public void setEstimatePackageProxy(EstimatePackageProxy estimatePackageProxy) {
		this.estimatePackageProxy = estimatePackageProxy;
	}

        
} 
