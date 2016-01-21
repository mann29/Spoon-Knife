package com.mitchell.services.business.partialloss.assignmentdelivery.handler.daytona;

import com.cieca.bms.*;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.APDRepairAssignmentInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AppLoggerBridge;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.ClaimServiceProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.MessagePostingAgent;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;
import org.apache.xmlbeans.XmlException;
import javax.jms.JMSException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Logger;

public class DaytonaRAHandlerImpl implements DaytonaRAHandler {
    private static final int MESSAGE_TYPE = 171602;
    private static final String ASSIGNMENT_BMS_ENVELOPE = "AssignmentBMS";
    private static final String CIECA_BMS_ASG_XML = "CIECA_BMS_ASG_XML";
    private static final String ASSIGNMENT_MESSAGE_TYPE = "MessageType";
    private static final String ASSIGNMENT_TASK_ID = "TaskId";
    private static final String ASSIGNMENT_MESSAGE_STATUS = "MessageStatus";
    private static final String CLASS_NAME = "DaytonaDeliveryHandler";
    private static final boolean INCLUDE_NESTED_STACKTRACE = true;
    private static final String DELIVER_ASSIGNMENT_METHOD = "deliverAssignment";
    private static final String ERROR_DELIVERING_ASSIGNMENT_MESSAGE = "Error Delivering Assignment";

    private static final String APD_RC_DISPATCH_TRANSACTION = "106804";
    private static final String APD_RC_CANCEL_TRANSACTION = "106806";
    private static final String APD_RC_COMPLETED_TRANSACTION = "106815";

    private static final String CANCEL_STATUS = "Cancel";
    private static final String COMPLETE_STATUS = "Complete";
    private static final String BUSINESS_SERVICE_NAME = "AssignmentDeliveryService";
    private static final String ESTIMATE_DOCUMENT_ID = "EstimateDocumentId";
    protected static final String BMS_VERSION = "2.10.0";

    private MessagePostingAgent messagePostingAgent;
    private ClaimServiceProxy claimServiceProxy;
    private AppLoggerBridge appLoggerBridge;

    private static final Logger logger = Logger.getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.handler.daytona.DaytonaDeliveryHandlerImpl");

    public void deliverAssignment(APDDeliveryContextDocument document) throws AssignmentDeliveryException {
        try {
            MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();

            BaseAPDCommonType baseAPDCommonType = document
                    .getAPDDeliveryContext()
                    .getAPDRepairAssignmentInfo()
                    .getAPDCommonInfo();

            // Add Cieca bms to envelope
            addCiecaToEnvelope(helper, baseAPDCommonType.getSourceUserInfo().getUserInfo(), baseAPDCommonType.getClientClaimNumber());

            // add name value pairs to envelope
            addNameValuePairsToEnvelope(helper, document.getAPDDeliveryContext());

            // post envelope to message bus
            postMessage(helper, baseAPDCommonType.getTargetUserInfo().getUserInfo(), baseAPDCommonType.getWorkItemId());

            writeAppLogEvent(document.getAPDDeliveryContext().getAPDRepairAssignmentInfo());

        } catch (AssignmentDeliveryException e) {
            logError(e);
            throw e;
        } catch(Exception e) {
            logError(e);
            throw new AssignmentDeliveryException(CLASS_NAME, DELIVER_ASSIGNMENT_METHOD, ERROR_DELIVERING_ASSIGNMENT_MESSAGE, e);
        }
    }

    private void logError(Exception e) {
        logger.severe(AppUtilities.getStackTraceString(e, INCLUDE_NESTED_STACKTRACE));
    }

    private void writeAppLogEvent(APDRepairAssignmentInfoType apdRepairAssignmentInfo) throws MitchellException {
        BaseAPDCommonType baseAPDCommonType = apdRepairAssignmentInfo.getAPDCommonInfo();
        UserInfoType userInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
        String transactionCode = getTransactionCode(apdRepairAssignmentInfo.getMessageStatus());

        AppLoggingDocument appLogDoc = AppLoggingDocument.Factory.newInstance();
        AppLoggingType appLoggingType = appLogDoc.addNewAppLogging();
        appLoggingType.setClaimNumber(baseAPDCommonType.getClientClaimNumber());
        appLoggingType.setClaimId(baseAPDCommonType.getClaimId());

        String workItemId = baseAPDCommonType.getWorkItemId();
        appLoggingType.setCurrentWorkflowId(workItemId);
        appLoggingType.setMitchellUserId(userInfoType.getUserID());
        appLoggingType.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
        appLoggingType.setStatus(0);
        appLoggingType.setTransactionType(transactionCode);

        AppLoggingNVPairs nvPairs = new AppLoggingNVPairs();
        nvPairs.addPair("ReviewerUserId", userInfoType.getUserID());
        nvPairs.addPair("ReviewerCompanyCode", userInfoType.getOrgCode());
        String machineInfo = AppUtilities.buildServerName();
        if (machineInfo != null && machineInfo.length() > 0) {
            nvPairs.addInfo("ProcessingMachineInfo", machineInfo);
        }

        UserInfoDocument userInfoDocument = UserInfoDocument.Factory.newInstance();
        userInfoDocument.setUserInfo(baseAPDCommonType.getTargetUserInfo().getUserInfo());

        appLoggerBridge.logEvent(
                appLogDoc,
                userInfoDocument,
                workItemId,
                AssignmentDeliveryConstants.APPLICATION_NAME,
                AssignmentDeliveryConstants.APPLICATION_NAME,
                nvPairs);
    }

    private void addNameValuePairsToEnvelope(MitchellEnvelopeHelper helper, APDDeliveryContextType apdDeliveryContext) {
        String messageType = apdDeliveryContext.getMessageType();
        helper.addEnvelopeContextNVPair(ASSIGNMENT_MESSAGE_TYPE, messageType);

        String messageStatus = apdDeliveryContext
                .getAPDRepairAssignmentInfo()
                .getMessageStatus();

        helper.addEnvelopeContextNVPair(ASSIGNMENT_MESSAGE_STATUS, messageStatus);

        long taskId = apdDeliveryContext
                .getAPDRepairAssignmentInfo()
                .getTaskId();

        helper.addEnvelopeContextNVPair(ASSIGNMENT_TASK_ID, String.valueOf(taskId));

        if (apdDeliveryContext.getAPDRepairAssignmentInfo().isSetEstimateDocId()) {
            long estimateDocumentId = apdDeliveryContext
                    .getAPDRepairAssignmentInfo()
                    .getEstimateDocId();

            helper.addEnvelopeContextNVPair(ESTIMATE_DOCUMENT_ID, String.valueOf(estimateDocumentId));
        }
    }

    private void addCiecaToEnvelope(MitchellEnvelopeHelper helper, UserInfoType sourceUserInfoType, String clientClaimNumber) throws MitchellException {
        UserInfoDocument userInfoDocument = createUserInfoDocument(sourceUserInfoType);
        AssignmentAddRqDocument assignmentAddRqDocument = claimServiceProxy.getAssignmentBms(userInfoDocument, clientClaimNumber);

        //Add needed stuff so bms schema doesn't complain
        AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = formatAssignmentAddRqForMessageBusValidation(assignmentAddRqDocument);

        CIECADocument ciecaDocument = createCiecaDocument(assignmentAddRq);
        helper.addNewEnvelopeBody(ASSIGNMENT_BMS_ENVELOPE, ciecaDocument, CIECA_BMS_ASG_XML);
    }

    private UserInfoDocument createUserInfoDocument(UserInfoType userInfoType) {
        UserInfoDocument userInfoDocument = UserInfoDocument.Factory.newInstance();
        userInfoDocument.setUserInfo(userInfoType);
        return userInfoDocument;
    }

    private CIECADocument createCiecaDocument(AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq) {
        CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
        CIECADocument.CIECA cieca = ciecaDocument.addNewCIECA();
        cieca.setAssignmentAddRq(assignmentAddRq);
        return ciecaDocument;
    }

    private AssignmentAddRqDocument.AssignmentAddRq formatAssignmentAddRqForMessageBusValidation(AssignmentAddRqDocument assignmentAddRqDocument) {
        AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = assignmentAddRqDocument.getAssignmentAddRq();
        assignmentAddRq.setRqUID(UUID.randomUUID().toString());
        DocumentInfoType documentInfoType = assignmentAddRq.addNewDocumentInfo();
        documentInfoType.setBMSVer(BMS_VERSION);
        documentInfoType.setDocumentType("A");
        documentInfoType.setDocumentID("0");
        documentInfoType.setCreateDateTime(Calendar.getInstance());
        DocumentVerType documentVerType = documentInfoType.addNewDocumentVer();
        documentVerType.setDocumentVerCode("EM");
        documentVerType.setDocumentVerNum(0);

        EventInfoType eventInfo = assignmentAddRq.addNewEventInfo();
        AssignmentEventType assignmentEventType = eventInfo.addNewAssignmentEvent();
        assignmentEventType.setCreateDateTime(Calendar.getInstance());

        VehicleInfoType vehicleInfoType = getVehicleInfo(assignmentAddRq);

        if (vehicleInfoType == null) {
            return assignmentAddRq;
        }

        if (vehicleInfoType.isSetPowertrain() && vehicleInfoType.getPowertrain().isSetTransmissionInfo()) {
            PowertrainType.TransmissionInfo transmissionInfo = vehicleInfoType
                    .getPowertrain()
                    .getTransmissionInfo();

            if (transmissionInfo.isSetTransmissionDesc()) {
                transmissionInfo.unsetTransmissionDesc();
            }
            transmissionInfo.setTransmissionCode("VD");
        }

        return assignmentAddRq;
    }

    private VehicleInfoType getVehicleInfo(AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq) {
        VehicleDamageAssignmentType vehicleDamageAssignment = assignmentAddRq.getVehicleDamageAssignment();
        if (vehicleDamageAssignment == null) {
            return null;
        }

        return vehicleDamageAssignment.getVehicleInfo();
    }

    private void postMessage(MitchellEnvelopeHelper helper, UserInfoType userInfoType, String workItemId) throws XmlException, ServiceLocatorException, JMSException, BadXmlFormatException {
        UserInfoDocument userInfoDocument = createUserInfoDocument(userInfoType);

        TrackingInfoDocument trackingInfoDocument = WorkflowMsgUtil
                .createTrackingInfo(
                        AssignmentDeliveryConstants.APPLICATION_NAME,
                        BUSINESS_SERVICE_NAME,
                        AssignmentDeliveryConstants.MODULE_NAME,
                        workItemId,
                        userInfoDocument,
                        null);

        MitchellWorkflowMessageDocument mwmDoc = WorkflowMsgUtil.createWorkflowMessage(trackingInfoDocument);
        mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, helper.getDoc(), MESSAGE_TYPE);
        messagePostingAgent.postMessage(mwmDoc);
    }

    private String getTransactionCode(String messageStatus) {
        if (messageStatus.equalsIgnoreCase(CANCEL_STATUS)) {
            return APD_RC_CANCEL_TRANSACTION;
        }

        if (messageStatus.equalsIgnoreCase(COMPLETE_STATUS)) {
            return APD_RC_COMPLETED_TRANSACTION;
        }

        return APD_RC_DISPATCH_TRANSACTION;
    }

    public void setMessagePostingAgent(MessagePostingAgent messagePostingAgent) {
        this.messagePostingAgent = messagePostingAgent;
    }

    public void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy) {
        this.claimServiceProxy = claimServiceProxy;
    }

    public void setAppLoggerBridge(AppLoggerBridge appLoggerBridge) {
        this.appLoggerBridge = appLoggerBridge;
    }
}
