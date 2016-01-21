package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.cieca.bms.*;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.*;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.VehicleType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.ExtractClassName;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractAssignmentDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.*;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;
import org.apache.xmlbeans.XmlException;

import javax.jms.JMSException;
import java.lang.Boolean;
import java.lang.Integer;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractMessageBusDeliveryHandler extends
        AbstractAssignmentDeliveryHandler implements MsgBusDelHandler {

    private static final boolean DO_NOT_RETURN_EXCEPTION = false;
    protected static final Integer DEPENDENCY_AND_SANITY_CHECK = 106835;
    protected static final String SUPPLEMENT = "SUPPLEMENT";
    private static final String CIECA_BMS_ASG_XML = "CIECA_BMS_ASG_XML";
    private static final String ASSIGNMENT_BMS = "AssignmentBMS";
    private static final String UPDATED_ASSIGNMENT_BMS = "UpdatedAssignmentBMS";
    public static final String PRIMARY_CONTACT_TYPE_CLAIMNT = "CLAIMANT";
    public static final String PRIMARY_CONTACT_TYPE_INSURED = "INSURED";

    // -- MetaData for AttachmentInfo
    protected static final String ATTACHMENT_INFO = "AttachmentInfo";
    protected static final String WC_ATTACHMENT_INFO = "WCAttachmentInfo";
    protected static final String UPDATED_WC_ATTACHMENT_INFO = "UpdatedWCAttachmentInfo";
    protected static final String ATTACHMENT_INFO_XML_CLASSNAME = "com.mitchell.types.AttachmentInfoDocument";

    // -- MetaData for ArtifactContext
    protected static final String CALLER_METHOD = "deliverAssignment(APDDeliveryContextDocument)";
    private static final String ORIGINAL_ESTIMATE = "ORIGINAL_ESTIMATE";
    protected static final String DISPATCH_REPORT = "DISPATCH_REPORT";
    protected static final String ANNOTATION_XML = "ANNOTATION_XML";
    protected static final int CANCEL = 0;
    protected static final int DISPATCH = 1;
    protected static final int COMPLETE = 2;
    protected static final String MIME_TEXT_TYPE = "text/plain";
    private static final String CANCEL_STRING = "CANCEL";
    private static final String CANCELLED_STRING = "CANCELLED";
    private static final String CANCEL_REASON_STRING = "CancelReason";
    private static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
    private static final String CANCEL_REASON_DESC_STRING = "CancelReasonDescription";
    private static final String DISPATCHED_STRING = "DISPATCHED";
    protected static final String COMPLETED_STRING = "COMPLETED";
    private static final int APD_DELIVERY_TYPE = 171602;
    private static final boolean UPDATE = true;
    protected static final long NOT_ARCHIVED = 0;
    protected static final boolean INCLUDE_NESTED_STACKTRACE = true;

    public void deliverAssignment(final APDDeliveryContextDocument document)
            throws AssignmentDeliveryException {

        Integer operation = DEPENDENCY_AND_SANITY_CHECK; // Set so that when there is an exception, we know from where it originated

        try {
            mLogger.entering(CALLER_METHOD);

            validateDependencies();

            DeliveryHandlerContext contextBag = createContext(document);

            addAdditionalItemsToOutboundEnvelope(contextBag);

            addAssignmentBms(contextBag); // more descriptive name

            addAttachments(contextBag);

            //onBeforePostMessage(contextBag); work process additions to envelope will go here

            postMessage(contextBag);

            //onAfterPostMessage(contextBag); email notification would go here

        } catch (final AssignmentDeliveryException e) {
            mLogger.severe(e.getMessage());
            mLogger.severe((AppUtilities.getStackTraceString(e,
                    INCLUDE_NESTED_STACKTRACE)));
            if (e.getCause() != null) {
                mLogger.severe(e.getCause().getMessage());
                mLogger.severe(AppUtilities.getStackTraceString(e,
                        INCLUDE_NESTED_STACKTRACE));
            }
            throw e;
        } catch (final Exception e) {
            logAndRethrowAsAssignmentDeliveryException(operation, e);
        }
    }

    private DeliveryHandlerContext createContext(APDDeliveryContextDocument document) throws MitchellException, XmlException {

        DeliveryHandlerContext context = new DeliveryHandlerContext();

        context.setApdDeliveryContextDocument(document);

        setApdDeliveryContextType(context, document);

        setLog(context);

        setApdCommonInfo(context);

        setWorkItemOnLog(context);

        setIsReDispatch(context);

        setInboundEnvelope(context);

        setOutboundEnvelope(context);

        setAssignmentBms(context);

        return context;
    }

    private void setLog(DeliveryHandlerContext context) {
        context.setLog(annotate(mLogger));
    }

    private void setOutboundEnvelope(DeliveryHandlerContext context) {
        context.setOutboundEnvelope(MitchellEnvelopeHelper.newInstance());
    }

    private void setIsReDispatch(DeliveryHandlerContext context) {
        final boolean isReDispatch = getIsReDispatch(context);
        context.setIsReDispatch(isReDispatch);
    }

    private void postMessage(DeliveryHandlerContext context)
            throws MitchellException, XmlException, JMSException {

        AnnotatedLogger log = context.getLog();

        final UserInfoDocument userInfoForMessagebus = UserInfoDocument.Factory.newInstance();

        userInfoForMessagebus.setUserInfo(context.getApdCommonInfo().getTargetUserInfo()
                .getUserInfo());
        if (log.isLoggable(Level.FINE)) {
            log.fine("Outbound envelope being posted to message bus.["
                    + context.getOutboundEnvelope().getEnvelope() + "]");
        }
        final String messageId = postToMessageBus(context.getOutboundEnvelope(), context.getWorkItemId(),
                userInfoForMessagebus);

        context.setPostedMessageId(messageId);

        if (log.isLoggable(Level.INFO)) {
            log.info("Sent mitchell envelope to its destination. Message Id is ["
                    + messageId + "]");
        }
        writeApplogEvent(context.getWorkItemId(), context.getAssignmentBms(), userInfoForMessagebus, gatherRequestStatusFrom(context.getApdDeliveryContext()),
                context.getIsReDispatch());
    }

  //  protected void onBeforePostMessage(DeliveryHandlerContext context) {}

  //  protected void onAfterPostMessage(DeliveryHandlerContext context){}

    protected void addAssignmentBms(DeliveryHandlerContext context)
            throws Exception {

        AnnotatedLogger log = context.getLog();

        if (allowBMSAndAttachmentInMitchellEnvelope(gatherRequestStatusFrom(context.getApdDeliveryContext()))) {
            CIECADocument outCiecaBms = performTransformOfCiecaBmsInInboundPayload(
                    context.getAssignmentBms(), context.getApdCommonInfo(), extractStaffUserInfo(context.getApdCommonInfo()));

            if (outCiecaBms != null) {
                context.getInboundEnvelope().updateEnvelopeBodyContent(
                        context.getInboundEnvelope().getEnvelopeBody(), outCiecaBms);
                if (log.isLoggable(Level.FINE)) {
                    log.fine("Updated InboundEnvelope.["
                            + context.getInboundEnvelope().getEnvelope() + "]");
                }
            }

            processContextForCiecaBms(context.getOutboundEnvelope(), context.getInboundEnvelope(),
                    context.getWorkItemId(), context.getIsReDispatch());
            if (log.isLoggable(Level.FINE)) {
                log.fine("Attached Cieca BMS to outbound MEDoc.");
            }
        }
    }

    protected void addAttachments(DeliveryHandlerContext context)
            throws Exception {

        AnnotatedLogger log = context.getLog();

        if (allowBMSAndAttachmentInMitchellEnvelope(gatherRequestStatusFrom(context.getApdDeliveryContext()))) {

            processContextForWorkCenterAttachments(context, extractUserInfo(context.getApdCommonInfo()));
            if (log.isLoggable(Level.FINE)) {
                log.fine("Attached work center attachments to outbound MEDoc.");
            }
        }
    }

    protected void addAdditionalItemsToOutboundEnvelope(DeliveryHandlerContext context) throws Exception {
        AnnotatedLogger log = context.getLog();

        addAdditionalAssignmentInfoToContextBag(context);
        String primaryContactType = context.getPrimaryContact();
        if (log.isLoggable(Level.INFO)) {
            log.info("Return from attempt to add primary contact to context bagc: primaryContactType =[ "
                    + primaryContactType + " ]");
        }

        if (!checkNullEmptyString(primaryContactType)) {
            primaryContactType = determinePrimaryContactTypefromCiecaBmsDoc(context.getAssignmentBms(),
                    log);
            if (log.isLoggable(Level.INFO)) {
                log.info("Return from determinePrimaryContactTypefromCiecaBmsDoc: primaryContactType =[ "
                        + primaryContactType + " ]");
            }
        }

        setNameValuePairs(context.getOutboundEnvelope(), context.getAssignmentBms(), context.getApdDeliveryContext(),
                primaryContactType, context.getVid(), context.getIsReDispatch(), context.getEstimateDocumentId(), log);

        if (log.isLoggable(Level.FINE)) {
            log.fine("Assigned name-value pairs to MEDoc.");
        }
    }

    protected abstract boolean getIsReDispatch(DeliveryHandlerContext context);

    private void setAssignmentBms(DeliveryHandlerContext context) throws MitchellException, XmlException {

        CIECADocument cieca = handlerUtils.getCiecaDocumentFromMitchellEnvelope(
                context.getInboundEnvelope().getDoc(),
                AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER,
                context.getWorkItemId());

        context.setAssignmentBms(cieca);
    }


    private void setInboundEnvelope(DeliveryHandlerContext context) {

        final MitchellEnvelopeHelper inboundEnvelope =
                extractInboundEnvelope(context.getApdDeliveryContext());

        context.setInboundEnvelope(inboundEnvelope);

        AnnotatedLogger log = context.getLog();

        if (log.isLoggable(Level.FINE)) {
            log.fine("Inbound envelope.[" + inboundEnvelope.getEnvelope() + "]");
        }

    }

    private void setWorkItemOnLog(DeliveryHandlerContext contextBag) {
        contextBag.getLog().with(contextBag.getWorkItemId());
    }

    private boolean isSupplement(APDDeliveryContextType apdDeliveryContext) {
        return SUPPLEMENT.equals(apdDeliveryContext
                .getMessageType());
    }

    private void setApdDeliveryContextType(DeliveryHandlerContext context, APDDeliveryContextDocument document) {

        final APDDeliveryContextType apdDeliveryContext = document.getAPDDeliveryContext();

        guard(apdDeliveryContext);

        context.setApdDeliveryContextType(apdDeliveryContext);
    }

    private void setApdCommonInfo(DeliveryHandlerContext context) {
        BaseAPDCommonType apdCommonInfo = context.getApdDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();

        context.setApdCommonInfo(apdCommonInfo);
    }

    protected void addAdditionalAssignmentInfoToContextBag(DeliveryHandlerContext context)
            throws Exception {
        AnnotatedLogger log = context.getLog();
        try {
            AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
                    context.getInboundEnvelope().getDoc(), context.getWorkItemId());
            if (log.isLoggable(Level.INFO)) {
                log.info("getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc, aaaInfoDoc =[ "
                        + aaaInfoDoc + " ]");
            }
            if (aaaInfoDoc != null) {
                AdditionalAppraisalAssignmentInfoType additionalAAInfoType = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo();
                context.setPrimaryContact(additionalAAInfoType.getAssignmentDetails().getPrimaryContactType().toUpperCase());
                VehicleType vehicleType = additionalAAInfoType.getVehicleDetails();
                if (vehicleType != null) {
                    BigInteger mitchellVid = vehicleType.getMitchellVID();
                    if (mitchellVid != null) {
                        context.setVid(mitchellVid.longValue());
                    }
                }
                context.setAdditionalAppraisalAssignmentInfoType(additionalAAInfoType);
                if (additionalAAInfoType.getAssignmentDetails().isSetRelatedEstimateDocumentID()) {
                    context.setEstimateDocumentId(additionalAAInfoType.getAssignmentDetails().getRelatedEstimateDocumentID());
                }
            }
        } catch (Exception e) {
            log.severe(AppUtilities.getStackTraceString(e, true));
            log.severe("Error in getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc()");
        }
    }

    protected String determinePrimaryContactTypefromCiecaBmsDoc(
            CIECADocument cieca, final AnnotatedLogger log) {
        String primaryContactType = "";
        try {
            AdminInfoType adminInfo = null;
            AssignmentAddRqDocument.AssignmentAddRq assignmentAddRqDoc = cieca
                    .getCIECA().getAssignmentAddRq();
            if (assignmentAddRqDoc != null)
                adminInfo = assignmentAddRqDoc.getAdminInfo();

            if (adminInfo != null && adminInfo.getClaimant() != null
                    && adminInfo.getClaimant().getParty() != null
                    && adminInfo.getClaimant().getParty().getPersonInfo() != null) {
                if (isClaimantInsured(adminInfo.getClaimant().getParty()
                        .getPersonInfo()))
                    primaryContactType = PRIMARY_CONTACT_TYPE_CLAIMNT;
            }

            if (!checkNullEmptyString(primaryContactType) && adminInfo != null
                    && adminInfo.getPolicyHolder() != null
                    && adminInfo.getPolicyHolder().getParty() != null
                    && adminInfo.getPolicyHolder().getParty().getPersonInfo() != null) {
                if (isClaimantInsured(adminInfo.getPolicyHolder().getParty()
                        .getPersonInfo()))
                    primaryContactType = PRIMARY_CONTACT_TYPE_INSURED;
            }

        } catch (Exception e) {
            log.severe(AppUtilities.getStackTraceString(e, true));
            log.severe("Error in determinePrimaryContactTypefromCiecaBmsDoc()");
        }

        if (log.isLoggable(Level.INFO)) {
            log.info("Return from determinePrimaryContactTypefromCiecaBMSDoc: primaryContactType =[ "
                    + primaryContactType + " ]");
        }
        return primaryContactType;
    }


    private boolean isClaimantInsured(PersonInfoType personInfo) {
        boolean isClaimantInsured = false;
        PersonNameType personNameType = personInfo.getPersonName();
        if (personNameType != null
                && (checkNullEmptyString(personNameType.getFirstName()) || checkNullEmptyString(personNameType
                .getLastName())))
            isClaimantInsured = true;
        return isClaimantInsured;
    }

    protected static boolean checkNullEmptyString(String inputString) {
        return (inputString != null && !inputString.trim().equals(""));
    }

    protected CIECADocument performTransformOfCiecaBmsInInboundPayload(
            CIECADocument cieca, final BaseAPDCommonType apdCommonInfo,
            final UserInfoType staffUserInfo)
            throws RemoteException, MitchellException {

        Map<String, java.io.Serializable> context = new HashMap<String, java.io.Serializable>(2);
        final String companyCode = extractCompanyCodeFromUserInfo(staffUserInfo);
        context.put(BmsConverterFactory.COMPANY_CODE, companyCode);

        final boolean isShop = (apdCommonInfo.getTargetDRPUserInfo() != null);
        if (isShop) {
            context.put(BmsConverterFactory.SHOP, Boolean.TRUE);
        } else {
            context.put(BmsConverterFactory.SHOP, Boolean.FALSE);
        }

        CiecaBmsConverter converter = bmsConverterFactory
                .createCiecaBmsConvertor(context);

        return converter.convert(cieca);
    }

    public abstract void writeApplogEvent(final String workItemId,
                                          final CIECADocument cieca, final UserInfoDocument userInfoForMessagebus,
                                          final int status, final boolean isReDispatch)
            throws MitchellException;

    protected void logAndRethrowAsAssignmentDeliveryException(final Integer error,
                                                              final Exception e)
            throws AssignmentDeliveryException {
        if (error == null) {
            throw new IllegalArgumentException("Error number was not set");
        }
        mLogger.severe(e.getMessage());
        mLogger.severe((AppUtilities.getStackTraceString(e,
                INCLUDE_NESTED_STACKTRACE)));
        throw new AssignmentDeliveryException(error, getClass()
                .getName(), "deliverAssignment", "Error delivering assignment", e);
    }

    protected abstract void processContextForWorkCenterAttachments(DeliveryHandlerContext context, UserInfoType userInfo)
            throws Exception;

    protected AdditionalAppraisalAssignmentInfoDocument findAdditionalAppraisalAssignmentInfoDocument(
            final MitchellEnvelopeDocument meDoc, final String workItemID)
            throws Exception {
        return handlerUtils
                .getAAAInfoDocFromMitchellEnv(meDoc, workItemID,
                        DO_NOT_RETURN_EXCEPTION);
    }

    protected void processContextForCiecaBms(
            final MitchellEnvelopeHelper outboundPayload,
            final MitchellEnvelopeHelper inboundPayload, final String workItemId,
            final boolean isRedispatch)
            throws MitchellException, XmlException {
        final EnvelopeBodyType bmsEnvelopeBody = outboundPayload
                .addNewEnvelopeBody(isRedispatch ? UPDATED_ASSIGNMENT_BMS
                        : ASSIGNMENT_BMS, handlerUtils
                        .getCiecaDocumentFromMitchellEnvelope(inboundPayload.getDoc(),
                                AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER,
                                workItemId), CIECA_BMS_ASG_XML);
        bmsEnvelopeBody.getMetadata().setXmlBeanClassname(
                "com.cieca.bms.CIECADocument");
    }

    protected abstract long getTargetCollaboratorIdFromContext(
            final APDDeliveryContextType context)
            throws MitchellException;

    protected CrossOverUserInfoDocument fishOutXOverInfo(
            final APDDeliveryContextType context)
            throws Exception {
        final BaseAPDCommonType apdCommonInfo = context
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
        return userInfoClient
                .getCrossOverUserInfo(apdCommonInfo.getInsCoCode(), apdCommonInfo
                        .getTargetUserInfo().getUserInfo().getUserID());
    }

    protected long getTargetCollaboratorId(
            final CrossOverUserInfoDocument crossOverUserInfo) {
        long returnValue = 0;
        final OnlineUserType[] onlineUsersArray = crossOverUserInfo
                .getCrossOverUserInfo().getOnlineInfo().getOnlineOffice()
                .getOnlineUsersArray();
        for (final OnlineUserType onlineUser : onlineUsersArray) {
            if (mLogger.isLoggable(Level.FINE)) {
                mLogger.fine("Online User Details" + String.valueOf(onlineUser));
            }
            if (onlineUser.getOnlineUserOrgCode().getIsPrimaryUser()) {
                if (mLogger.isLoggable(Level.FINE)) {
                    mLogger.fine("Last Online User made the cut as primary user "
                            + onlineUser.getOnlineUserOrgID());
                }
                returnValue = onlineUser.getOnlineUserOrgID();
            }
            if (mLogger.isLoggable(Level.FINE)) {
                mLogger.fine("Last Online User did not make the cut as primary user "
                        + onlineUser.getOnlineUserOrgID());
            }
        }
        return returnValue;
    }

    public String extractCompanyCodeFromUserInfo(final UserInfoType userInfo)
            throws RemoteException, MitchellException {
        final OrgInfoDocument orgInfo = userInfoClient.getOrgInfo(
                userInfo.getOrgCode(), userInfo.getOrgCode(),
                UserInfoClient.ORG_TYPE_COMPANY);
        return orgInfo.getOrgInfo().getCompanyCode();
    }

    protected void setNameValuePairs(final MitchellEnvelopeHelper outboundPayload,
                                     final CIECADocument cieca, final APDDeliveryContextType context,
                                     String primaryContactType, long mitchellVid, final boolean isRedispatch,
                                     Long estimateDocumentId, final AnnotatedLogger log)
            throws Exception {
        if (CANCEL == gatherRequestStatusFrom(context)) {
            assignCommonNameValuePairsTo(outboundPayload, context, CANCELLED_STRING,
                    UPDATE, primaryContactType, mitchellVid, log);
        } else if (COMPLETE == gatherRequestStatusFrom(context)) {
            assignCommonNameValuePairsTo(outboundPayload, context, COMPLETED_STRING,
                    UPDATE, primaryContactType, mitchellVid, log);
        } else if (DISPATCH == gatherRequestStatusFrom(context)) {
            assignCommonNameValuePairsTo(outboundPayload, context, DISPATCHED_STRING,
                    isRedispatch, primaryContactType, mitchellVid, log);
            // This is only applicable to dispatch.
            if (getPassThruInfoFromCieca(cieca) != null) {
                final String[][] passThruInfo = {{"PassThroughInfo",
                        getPassThruInfoFromCieca(cieca)}};
                assign(passThruInfo, outboundPayload, log);
            }
            if (getNotesFromBmsAssignmentMemo(cieca, log) != null) {
                final String[][] bmsNotesNVPair = {{
                        isRedispatch ? "UpdatedNotes" : "Notes",
                        getNotesFromBmsAssignmentMemo(cieca, log)}};
                assign(bmsNotesNVPair, outboundPayload, log);
                if (log.isLoggable(Level.FINE)) {
                    log.fine("setNameValuePairs::  BmsAssignmentMemo->Notes [ "
                            + Arrays.deepToString(bmsNotesNVPair) + " ]");
                }
            }
        }

        if (estimateDocumentId != null) {
            outboundPayload.addEnvelopeContextNVPair("EstimateDocumentId", String.valueOf(estimateDocumentId));
        }
    }

    private void assignCommonNameValuePairsTo(
            final MitchellEnvelopeHelper outboundPayload,
            final APDDeliveryContextType context, final String status,
            final boolean isUpdate, String primaryContactType, long mitchellVid, final AnnotatedLogger log)
            throws MitchellException, RemoteException {
        final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = context
                .getAPDAppraisalAssignmentInfo();
        final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo
                .getAPDCommonInfo();
        final boolean noDRPInfoFound = (apdCommonInfo.getTargetDRPUserInfo() == null);
        final String userCoCd = findUserCoCdForTargetUserInfo(apdCommonInfo,
                noDRPInfoFound);
        final String userId = findUserIdForTargetUserInfo(apdCommonInfo,
                noDRPInfoFound);
        String reviewerId = apdCommonInfo.getTargetUserInfo().getUserInfo()
                .getUserID();

        String reviewerCoCd = apdCommonInfo.getTargetUserInfo().getUserInfo()
                .getOrgCode();
        Long taskId = apdAppraisalAssignmentInfo.getTaskId();
        Long referenceId = this.workAssignmentProxy.getWorkAssignmentReferenceID(taskId);
        Long claimExposureId = this.workAssignmentProxy.getWorkAssignmentClaimExposureID(taskId);
        Long claimId = this.workAssignmentProxy.getWorkAssignmentClaimID(taskId);

        final String clientClaimNumber = context.getAPDAppraisalAssignmentInfo()
                .getAPDCommonInfo().getClientClaimNumber();
        if (noDRPInfoFound) {
            reviewerId = "";
            reviewerCoCd = "";
        }
        if (log.isLoggable(Level.INFO)) {
            log.info("assignCommonNameValuePairsTo::  noDRPInfoFound [ "
                    + noDRPInfoFound + " ]");
            log.info("assignCommonNameValuePairsTo::  status [ " + status + " ]");
            log.info("assignCommonNameValuePairsTo::  userCoCd [ " + userCoCd + " ]");
            log.info("assignCommonNameValuePairsTo::  userId [ " + userId + " ]");
            log.info("assignCommonNameValuePairsTo::  apdCommonInfo ["
                    + apdCommonInfo + "]");
            log.info("assignCommonNameValuePairsTo::  taskId [ " + taskId + " ]");
            log.info("assignCommonNameValuePairsTo::  mitchellVid [ " + mitchellVid + " ]");
            log.info("assignCommonNameValuePairsTo::  referenceId [ " + referenceId + " ]");
            log.info("assignCommonNameValuePairsTo::  claimExposureId [ " + claimExposureId + " ]");
            log.info("assignCommonNameValuePairsTo::  claimId [ " + claimId + " ]");
            log.info("assignCommonNameValuePairsTo::  primaryContactType ["
                    + primaryContactType + "]");
        }
        final String[][] nameValuePairs = {
                {"MessageType", context.getMessageType()},
                {"MessageStatus", status},
                {isUpdate ? "UpdatedNotes" : "Notes", apdCommonInfo.getNotes()},
                {"MitchellCompanyCode", apdCommonInfo.getInsCoCode()},
                {"ClaimNumber", clientClaimNumber},
                {"MitchellWorkItemId", apdCommonInfo.getWorkItemId()},
                {"TaskId", taskId + ""},
                {"ReferenceId", referenceId + ""},
                {"ClaimExposureId", claimExposureId + ""},
                {"MitchellVid", mitchellVid + ""},
                {"ClaimId", claimId + ""},
                {"ReviewerCoCd", reviewerCoCd},
                {"ShopId", getTargetCollaboratorIdFromContext(context) + ""},
                {"ReviewerId", reviewerId}, {"UserCoCd", userCoCd},
                {"UserId", userId}, {"PrimaryContactType", primaryContactType}};
        assign(nameValuePairs, outboundPayload, log);
        assignCancelReasonToOutboundPayload(outboundPayload, context, status, log);
        assignLastModifiedDateTimeToNVPair(outboundPayload, context, log);
    }

    private void assignLastModifiedDateTimeToNVPair(
            final MitchellEnvelopeHelper outboundPayload,
            final APDDeliveryContextType context, final AnnotatedLogger log) {
        final MitchellEnvelopeHelper inboundEnvelope = extractInboundEnvelope(context);
        final String lastModifiedDateTime = inboundEnvelope
                .getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME);

        if (log.isLoggable(Level.FINE)) {
            log.fine("lastModifiedDateTime retrieved from MitchellEnvelope is:"
                    + lastModifiedDateTime);
        }

        if (lastModifiedDateTime != null) {
            final String[][] nameValuePair = {{LAST_MODIFIED_DATE_TIME,
                    lastModifiedDateTime}};
            assign(nameValuePair, outboundPayload, log);
        }
    }

    protected void assignCancelReasonToOutboundPayload(
            final MitchellEnvelopeHelper outboundPayload,
            final APDDeliveryContextType context, final String status,
            final AnnotatedLogger log) {
        final boolean isCancelledCase = status.equalsIgnoreCase(CANCELLED_STRING);
        if (isCancelledCase) {
            if (log.isLoggable(Level.FINE)) {
                log.fine("*********** Entering assignCancelReasonToOutboundPayload::  ");
            }
            final MitchellEnvelopeHelper inboundEnvelope = extractInboundEnvelope(context);
            final String cancelReason = inboundEnvelope
                    .getEnvelopeContextNVPairValue(CANCEL_REASON_STRING);
            final String cancelReasonDescription = inboundEnvelope
                    .getEnvelopeContextNVPairValue(CANCEL_REASON_DESC_STRING);

            if ((cancelReason != null && cancelReason.length() > 0)
                    || (cancelReasonDescription != null && cancelReasonDescription
                    .length() > 0)) {
                final String[][] nameValuePairs = {
                        {CANCEL_REASON_STRING, cancelReason},
                        {CANCEL_REASON_DESC_STRING, cancelReasonDescription}};
                if (log.isLoggable(Level.INFO)) {
                    log.info("*********** cancelReason =[ " + cancelReason + " ]");
                    log.info("*********** cancelReasonDescription =[ "
                            + cancelReasonDescription + " ]");
                }
                assign(nameValuePairs, outboundPayload, log);
            }
            if (log.isLoggable(Level.FINE)) {
                log.fine("*********** Exiting assignCancelReasonToOutboundPayload::  ");
            }
        }
    }

    protected abstract String findUserIdForTargetUserInfo(
            final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound);

    protected abstract String findUserCoCdForTargetUserInfo(
            final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound);

    private void assign(final String[][] nameValuePairs,
                        final MitchellEnvelopeHelper outboundPayload, final AnnotatedLogger log) {
        final int NAME = 0;
        final int VALUE = 1;
        for (int i = 0; i != nameValuePairs.length; i++) {
            if (log.isLoggable(Level.FINE)) {
                log.fine("Adding name value pair to MEDoc ["
                        + nameValuePairs[i][NAME] + "," + nameValuePairs[i][VALUE] + "]");
            }
            if (nameValuePairs[i][VALUE] != null) {
                outboundPayload.addEnvelopeContextNVPair(nameValuePairs[i][NAME],
                        nameValuePairs[i][VALUE]);
            }
        }
    }

    private String getPassThruInfoFromCieca(final CIECADocument doc) {
        if (doc != null
                && doc.getCIECA() != null
                && doc.getCIECA().getAssignmentAddRq() != null
                && doc.getCIECA().getAssignmentAddRq().getDocumentInfo() != null
                && doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
                .getReferenceInfo() != null
                && doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
                .getReferenceInfo().getPassThroughInfo() != null) {
            return doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
                    .getReferenceInfo().getPassThroughInfo();
        } else {
            return null;
        }
    }

    private String getNotesFromBmsAssignmentMemo(final CIECADocument doc,
                                                 AnnotatedLogger log) {
        String returnValue = null;
        if (doc != null && doc.getCIECA() != null
                && doc.getCIECA().getAssignmentAddRq() != null) {
            if (doc.getCIECA().getAssignmentAddRq().isSetVehicleDamageAssignment()) {
                String[] assMemos = doc.getCIECA().getAssignmentAddRq()
                        .getVehicleDamageAssignment().getAssignmentMemoArray();
                if (assMemos != null && assMemos.length > 0) {
                    StringBuilder sbuilder = new StringBuilder("");
                    String startCh = "";
                    for (String assMemo : assMemos) {
                        if (assMemo != null && assMemo.length() > 0) {
                            sbuilder.append(startCh);
                            sbuilder.append(assMemo);
                            startCh = " ";
                        }
                    }
                    returnValue = sbuilder.toString();
                }
            }
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("getNotesFromBmsAssignmentMemo::  retval [ " + returnValue + " ]");
        }
        return returnValue;
    }

    protected UserInfoType extractUserInfo(final BaseAPDCommonType apdCommonInfo) {
        final boolean isDrp = (apdCommonInfo.getTargetDRPUserInfo() != null);
        return (isDrp ? apdCommonInfo.getTargetDRPUserInfo()
                .getUserInfo() : apdCommonInfo.getTargetUserInfo().getUserInfo());
    }

    protected UserInfoType extractStaffUserInfo(
            final BaseAPDCommonType apdCommonInfo) {
        return (apdCommonInfo.getTargetUserInfo().getUserInfo());
    }

    protected MitchellEnvelopeHelper extractInboundEnvelope(
            final APDDeliveryContextType context) {
        final MitchellEnvelopeType mitchellEnvelope = context
                .getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
                .getMitchellEnvelope();
        final MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
        helper.getDoc().setMitchellEnvelope(mitchellEnvelope);
        return helper;
    }

    protected String postToMessageBus(final MitchellEnvelopeHelper outboundPayload,
                                      final String workItemId, final UserInfoDocument userInfo)
            throws XmlException, BadXmlFormatException, ServiceLocatorException,
            JMSException {
        final TrackingInfoDocument trackInfoDoc = WorkflowMsgUtil
                .createTrackingInfo(AssignmentDeliveryConstants.APPLICATION_NAME,
                        "AssignmentDeliveryService",
                        AssignmentDeliveryConstants.MODULE_NAME, workItemId, userInfo,
                        null);
        MitchellWorkflowMessageDocument mwmDoc = WorkflowMsgUtil
                .createWorkflowMessage(trackInfoDoc);
        mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, outboundPayload.getDoc(),
                APD_DELIVERY_TYPE); // we need a new event ID
        if (mLogger.isLoggable(Level.INFO)) {
            mLogger.info("About to post message [" + mwmDoc.toString() + "]");
        }
        return messagePostingAgent.postMessage(mwmDoc);
    }

    protected int gatherRequestStatusFrom(final APDDeliveryContextType context) {
        if (CANCEL_STRING.equals(context.getAPDAppraisalAssignmentInfo()
                .getMessageStatus())) {
            return CANCEL;
        } else if (DISPATCHED_STRING.equals(context.getAPDAppraisalAssignmentInfo()
                .getMessageStatus())) {
            return DISPATCH;
        } else if (COMPLETED_STRING.equals(context.getAPDAppraisalAssignmentInfo()
                .getMessageStatus())) {
            return COMPLETE;
        } else {
            throw new IllegalStateException(
                    "Only cancel and dispatch are supported. Job with  ["
                            + context.getAPDAppraisalAssignmentInfo().getMessageStatus()
                            + "] status was sent instead. Set APDDeliveryContextType/APDAppraisalAssignmentInfo/MessageStatus correctly."
            );
        }
    }

    private void validateDependencies() {
        final StringBuilder reason = new StringBuilder();
        if (handlerUtils == null) {
            reason.append("HandlerUtility, ");
        }
        if (mLogger == null) {
            reason.append("Logger, ");
        }
        if (drBuilder == null) {
            reason.append("Dispatch Report Builder, ");
        }
        if (documentStoreClientProxy == null) {
            reason.append("Document Store Proxy, ");
        }
        if (messagePostingAgent == null) {
            reason.append("Message Posting Agent, ");
        }
        if (appLoggerBridge == null) {
            reason.append("App Logger Bridge, ");
        }
        if (errorLoggingService == null) {
            reason.append("Error Logging Service, ");
        }
        if (userInfoClient == null) {
            reason.append("User Info Client");
        }
        if (reason.length() != 0) {
            throw new IllegalStateException(
                    "Make sure all the dependencies are initialized. "
                            + "The following dependencies are un-satisfied [" + reason + "]"
            );
        }
    }

    private void guard(final APDDeliveryContextType context) {
        if (context == null) {
            mLogger.severe("APD Context should not be NULL");
            throw new IllegalArgumentException("APD Context should not be NULL");
        }
        validate(context);
        mLogger.fine("Context is valid.");
        final int status = gatherRequestStatusFrom(context);
        if (status != CANCEL && status != DISPATCH && status != COMPLETE) {
            // not a valid status
            throw new IllegalArgumentException(
                    "Currently this component can only handle CANCEL, COMPLETED and DISPATCHED assignments");
        }
        if (!isSupplement(context)
                && !ORIGINAL_ESTIMATE.equals(context.getMessageType())) {
                    throw new IllegalArgumentException(
                            "Only SUPPLEMENT and ORGINAL_ESTIMATE messages are supported, got["
                                    + context.getMessageType() + "]"
                    );
                }
    }

    protected static boolean DUPLICATES_ALLOWED = true;
    protected DocStoreClientProxy documentStoreClientProxy;
    private MessagePostingAgent messagePostingAgent;
    private UserInfoServiceEJBRemote userInfoClient;
    private BmsConverterFactory bmsConverterFactory;
    private WorkAssignmentProxy workAssignmentProxy;

    public WorkAssignmentProxy getWorkAssignmentProxy() { return workAssignmentProxy; }

    public void setWorkAssignmentProxy(WorkAssignmentProxy workAssignmentProxy) {
        this.workAssignmentProxy = workAssignmentProxy;
    }

    public BmsConverterFactory getBmsConverterFactory()
    {
        return bmsConverterFactory;
    }

    public void setBmsConverterFactory(BmsConverterFactory bmsConverterFactory)
    {
        this.bmsConverterFactory = bmsConverterFactory;
    }

    public UserInfoServiceEJBRemote getUserInfoClient() {
        return userInfoClient;
    }

    public void setUserInfoClient(final UserInfoServiceEJBRemote userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    private void validate(final APDDeliveryContextType context) {
        context.validate();
    }

    protected AnnotatedLogger annotate(final Logger logger) {
        return AnnotatedLogger.annotate(logger);
    }

    public DocStoreClientProxy getDocumentStoreClientProxy() {
        return documentStoreClientProxy;
    }

    public void setDocumentStoreClientProxy(
            final DocStoreClientProxy documentStoreClientProxy) {
        this.documentStoreClientProxy = documentStoreClientProxy;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(final Converter converter) {
        this.converter = converter;
    }

    protected static final String CLASS_NAME = ExtractClassName
            .from(WebAssMsgBusDelHndlr.class);

    public AbstractMessageBusDeliveryHandler() {
        super();
    }

    protected String getClassName() {
        return CLASS_NAME;
    }

    public void setMessagePostingAgent(
            final MessagePostingAgent messagePostingAgent) {
        this.messagePostingAgent = messagePostingAgent;
    }

    public MessagePostingAgent getMessagePostingAgent() {
        return messagePostingAgent;
    }

    protected abstract boolean allowBMSAndAttachmentInMitchellEnvelope(
            final int status);
}