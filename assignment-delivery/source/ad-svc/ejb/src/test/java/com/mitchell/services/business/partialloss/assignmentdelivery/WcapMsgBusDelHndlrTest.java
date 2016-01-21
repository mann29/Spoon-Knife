package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.File;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;

import org.apache.xmlbeans.XmlException;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.CrossOverUserType;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.OnlineUserOrgCodeType;
import com.mitchell.common.types.OnlineUserType;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.OrgInfoType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.ActivityStatusType;
import com.mitchell.schemas.ArtifactContextDocument;
import com.mitchell.schemas.ArtifactContextDocument.ArtifactContext;
import com.mitchell.schemas.ArtifactItemDocument.ArtifactItem;
import com.mitchell.schemas.ArtifactViewDocument.ArtifactView;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.ItemsDocument.Items;
import com.mitchell.schemas.MetadataDocument.Metadata;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.RelatedContextsDocument.RelatedContexts;
import com.mitchell.schemas.ViewsDocument.Views;
import com.mitchell.schemas.EnvelopeContextType;
import com.mitchell.schemas.WorkProcessInitiationMessage;
import com.mitchell.schemas.WorkProcessInitiationMessageDocument;
import com.mitchell.schemas.WorkProcessUpdateMessage;
import com.mitchell.schemas.WorkProcessUpdateMessageDocument;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractDispatchReportBuilder;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.DocStoreClientProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.EstimatePackageClientProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.WcapMsgBusDelHndlr;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.workprocess.WorkProcessServiceClientAPI;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Unit test for Assignment Delivery to WCAP.
 * @author vb100291
 */
public class WcapMsgBusDelHndlrTest extends MockObjectTestCase {
    private static final String CLIENT_CLAIM_NUMBER = "ClientClaim#";
	private static final String TARGET_USER_FOR_DISPATCH = "TargetUserForDispatch";
    private static final String MY_TEST_ORG = "My test ORG";
    private static final long CLAIM_ID = -4141592654L;
    private static final String REVIEWER_CO_CODE = "IF";
    private static final String TEST_ORG = "1";
    private static final long TASK_ID = -3141592654L;
    private static final String WORK_ITEM_ID = "work-1234";
    private static final String TARGET_USER_FOR_CANCEL = "TargetUserForCancel";
    private static final String ARTIFACT_CONTEXT_TYPE_CLAIM = "CLAIM";
    private static final String ARTIFACT_CONTEXT_TYPE_ASMT = "ASSIGNMENT";
    private static final String ARTIFACT_CONTEXT_TYPE_ESTIMATE = "ESTIMATE";
    private static final String ARTIFACT_ITEM_TYPE_ESTIMATE = "ESTIMATE";    
    private static final String ARTIFACT_ITEM_TYPE_REPORT = "REPORT";
    private static final String ARTIFACT_ITEM_TYPES = "PHOTO|DOCUMENT";
    private static final String ARTIFACT_VIEW_TYPES = "IMAGE|DATA_FILE";
    private static final String ARTIFACT_VIEW_TYPE_DSPH_RPT = "DISPATCH_REPORT";
    private static final String ARTIFACT_VIEW_TYPE_ESTIMATE = "ESTIMATE";
	private static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
	private static final String LAST_MODIFIED_DATE_TIME_VALUE = "2013-03-26T03:22:16.932-07:00";
    private WcapMsgBusDelHndlr target;
    private CIECADocument ciecaDocument;

    private APDDeliveryContextDocument initializeDispatchAPDDeliveryContext(
                                                    final boolean isSupplement)
                                                    throws Exception {

        final APDDeliveryContextDocument doc =
                            APDDeliveryContextDocument.Factory.newInstance();
        final APDDeliveryContextType apdDeliveryContext =
                                                doc.addNewAPDDeliveryContext();
        doc.getAPDDeliveryContext().setMessageType(
                            isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
        attachCieca(doc, apdDeliveryContext);
        addLastModifiedDateTimeInNVPair(apdDeliveryContext);
        final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo =
                    doc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo();
        apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
        final BaseAPDCommonType apdCommonInfo =
                            apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
        apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
        apdCommonInfo.setNotes("My note!");
        apdCommonInfo.setInsCoCode("IF");
        apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
        apdCommonInfo.setClaimId(CLAIM_ID);
        apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
        apdAppraisalAssignmentInfo.setMessageStatus("DISPATCHED");
        final UserInfoType targetUserInfo =
                        apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
        targetUserInfo.setOrgID(TEST_ORG);
        targetUserInfo.setOrgCode(MY_TEST_ORG);
        targetUserInfo.setUserID(TARGET_USER_FOR_DISPATCH);
        return doc;
    }

    private void addLastModifiedDateTimeInNVPair(APDDeliveryContextType apdDeliveryContext) {
    	final MitchellEnvelopeType mitchellEnvelope = apdDeliveryContext.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope();
    	final EnvelopeContextType  envelopeContext = mitchellEnvelope.addNewEnvelopeContext();
    	final com.mitchell.schemas.NameValuePairType nameValuePairType = envelopeContext.addNewNameValuePair();
    	nameValuePairType.setName(LAST_MODIFIED_DATE_TIME);
    	nameValuePairType.setValueArray(new String [] {LAST_MODIFIED_DATE_TIME_VALUE});
	}
    private void attachCieca(
                        final APDDeliveryContextDocument doc,
                        final APDDeliveryContextType apdDeliveryContext) {
        
        final MitchellEnvelopeDocument med =
                                MitchellEnvelopeDocument.Factory.newInstance();
        final MitchellEnvelopeType mitchellEnvelope =
                                    MitchellEnvelopeType.Factory.newInstance();
        med.setMitchellEnvelope(mitchellEnvelope);
        ciecaDocument = CIECADocument.Factory.newInstance();
        final AssignmentAddRq assignmentAddRq =
                            ciecaDocument.addNewCIECA().addNewAssignmentAddRq();
        final ClaimInfoType claimInfo = assignmentAddRq.addNewClaimInfo();
        claimInfo.setClaimNum("My claim!");
        assignmentAddRq.addNewDocumentInfo().addNewReferenceInfo()
                                .setPassThroughInfo("Let me through, please!");
        final MitchellEnvelopeHelper meh = new MitchellEnvelopeHelper(med);
        apdDeliveryContext.addNewAPDAppraisalAssignmentInfo()
                                            .addNewAssignmentMitchellEnvelope();
        meh.addNewEnvelopeBody(
                    AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER,
                    ciecaDocument,
                    "CIECA");
        doc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
                                    .getAssignmentMitchellEnvelope()
                                        .setMitchellEnvelope(meh.getEnvelope());
    }

    private APDDeliveryContextDocument initializeCancelAPDDeliveryContext(
                                                    final boolean isSupplement)
                                                    throws Exception {
        final APDDeliveryContextDocument doc =
                            APDDeliveryContextDocument.Factory.newInstance();
        final APDDeliveryContextType aPDDeliveryContext =
                                                doc.addNewAPDDeliveryContext();
        attachCieca(doc, aPDDeliveryContext);
        addLastModifiedDateTimeInNVPair(aPDDeliveryContext);
        final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo =
                    doc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo();
        apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
        final BaseAPDCommonType apdCommonInfo =
                            apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
        apdAppraisalAssignmentInfo.setMessageStatus("CANCEL"); // CANCELLED");
        apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
        apdCommonInfo.setNotes("My note!");
        apdCommonInfo.setInsCoCode("IF");
        apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
        final UserInfoType userInfo =
                        apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
        userInfo.setOrgID(TEST_ORG);
        userInfo.setOrgCode(MY_TEST_ORG);
        userInfo.setUserID(TARGET_USER_FOR_CANCEL);
        apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
        apdCommonInfo.setClaimId(CLAIM_ID);
        doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
        return doc;
    }

    protected void setUp() throws Exception {
        super.setUp();
        target = new WcapMsgBusDelHndlr();
        mockAssignmentDeliveryUtils = mock(AbstractAssignmentDeliveryUtils.class);
        mockHandlerUtils = mock(AbstractHandlerUtils.class);
        mockErrorLoggingServiceWrapper = mock(ErrorLoggingServiceWrapper.class);
        mockConverter = mock(Converter.class);
        mockDispatchReportBuilder = mock(AbstractDispatchReportBuilder.class);
        mockAppLoggerBridge = mock(AppLoggerBridge.class);
        mockDocumentStoreClientProxy = mock(DocStoreClientProxy.class);
   
        mockEstimatePackageClientProxy = mock(EstimatePackageClientProxy.class);
        assignmentDeliveryConfigBridge = new MockAssignmentDeliveryConfigBridge();
        assignmentDeliveryConfigBridge.setTempDir("i_am_temp");
        messagePostingAgent = new MockMessagePostingAgent();
        mockWorkProcessServiceClient = mock(WorkProcessServiceClientAPI.class);
        mockUserInfoClient = mock(UserInfoServiceEJBRemote.class);
        mockBmsConverterFactory = mock(BmsConverterFactory.class);
        // Set dependencies
        target.setDrBuilder(
                (AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
        target.setLogger(new NoOpAssignmentDeliveryLogger("No-op", null));
        target.setAssignmentDeliveryUtils(
                (AbstractAssignmentDeliveryUtils) mockAssignmentDeliveryUtils.proxy());
        target.setConverter((Converter) mockConverter.proxy());
        target.setDrBuilder(
                (AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
        target.setErrorLoggingService(
                (ErrorLoggingServiceWrapper) mockErrorLoggingServiceWrapper.proxy());
        target.setHandlerUtils(
                (AbstractHandlerUtils) mockHandlerUtils.proxy());
        target.setLogger(new NoOpAssignmentDeliveryLogger("test", null));
        target.setAppLoggerBridge(
                (AppLoggerBridge) mockAppLoggerBridge.proxy());
        target.setAssignmentDeliveryConfigBridge(assignmentDeliveryConfigBridge);
        target.setHandlerUtils((AbstractHandlerUtils) mockHandlerUtils.proxy());
        target.setDocumentStoreClientProxy(
                (DocStoreClientProxy) mockDocumentStoreClientProxy.proxy());
        target.setEstimatePackageClientProxy(
                (EstimatePackageClientProxy) mockEstimatePackageClientProxy.proxy());
        target.setMessagePostingAgent(messagePostingAgent);
        target.setUserInfoClient(
                (UserInfoServiceEJBRemote) mockUserInfoClient.proxy());
        target.setWorkServiceClient(
                (WorkProcessServiceClientAPI) mockWorkProcessServiceClient.proxy());
        target.setBmsConverterFactory((BmsConverterFactory) mockBmsConverterFactory.proxy());
    }

    public void testDependenciesAreGuarded() throws Exception {
        target = new WcapMsgBusDelHndlr();
        try {
            System.out.println("The line \" No logger has been set. "
                    + "Exting \" following this statement is expected as part of a successful test");
            target.deliverAssignment(null);
            fail("The fact that no dependencies are set was not detected, failing");
        } catch (final NullPointerException expected) {
            expected.toString();// Keeps check-style happy he-he-he
        }
        try {
            target.setLogger(new NoOpAssignmentDeliveryLogger("No-op", null));
            // Don't worry about code coverage here, this NOT supposed to finish.
            target.deliverAssignment(initializeDispatchAPDDeliveryContext(false));
            fail("The fact that no dependencies are set was not detected, failing");
        } catch (final AssignmentDeliveryException expected) {
            assertEquals(
                "Make sure all the dependencies are initailized. "
                    + "The following dependencies are un-satisfied [Converter, "
                    + "HandlerUtility, Dispatch Report Builder, "
                    + "Document Store Proxy, Message Posting Agent, "
                    + "Work Process Service Client, App Logger Bridge, "
                    + "Error Logging Service, User Info Client]",
                    expected.getCause().getMessage());
        }
    }

    public void testCorrectTypeAreSupported() throws Exception {
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        try {
            nonDrpAPDDeliveryContext.getAPDDeliveryContext().setMessageType("OTHER");
            target.deliverAssignment(nonDrpAPDDeliveryContext);
            fail("OTHER type should not pass!");
        } catch (final AssignmentDeliveryException expected) {
            assertEquals(
                "Only SUPPLEMENT and ORGINAL_ESTIMATE messages are supported, "
                    + "got[OTHER]",
                expected.getCause().getMessage());
        }
    }

    /**
     * Test for WCAP dispatch.
     * @throws Exception
     */
    public void testWCAPDispatch() throws Exception { 
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(false);  // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, false);
        assertCiecaBody(helper, false);
        assertAssignmentArtifactContext(helper, false);         
        assertEstimateArtifactContext(helper, false);  // isSupplement
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", false);
    }
    /**
     * Test for WCAP dispatch.
     * @throws Exception
     */
    public void testWCAPComplete() throws Exception { 
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        assertNotNull("Logger is null", target.getLogger());
        nonDrpAPDDeliveryContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().setMessageStatus("COMPLETED");
        target.deliverAssignment(nonDrpAPDDeliveryContext);
    }
    /**
     * Test for WCAP dispatch.
     * @throws Exception
     */
    public void testWCAPSupplementDispatch() throws Exception {
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(true);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(true);   // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, true);
        assertCiecaBody(helper, false);
        assertAssignmentArtifactContext(helper, false);
        assertEstimateArtifactContext(helper,true);   // isSupplement
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    /**
     * Test for WCAP dispatch.
     * @throws Exception
     */
    public void testWCAPReDispatch() throws Exception {
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForReDispatchDelivery(false);  // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, true, false);
        assertCiecaBody(helper, true);
        assertAssignmentArtifactContext(helper, false);
        assertEstimateArtifactContext(helper,false);   // isSupplement
        assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    /**
     * Test for WCAP dispatch.
     * @throws Exception
     */
    public void testWCAPSupplementReDispatch() throws Exception {
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(true);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForReDispatchDelivery(true);  // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, true, true);
        assertCiecaBody(helper, true);
        assertAssignmentArtifactContext(helper, false);
        assertEstimateArtifactContext(helper,true);   // isSupplement
        assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    /**
     * @param helper
     * @param status
     * @param isSupplement
     *            TODO
     */
    private void assertNameValuePairsForDispatch(
                                        final MitchellEnvelopeHelper helper,
                                        final String status,
                                        final boolean isReDispatch,
                                        final boolean isSupplement) {

        assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                helper.getEnvelopeContextNVPairValue("MessageType"));
        assertEquals(status,helper.getEnvelopeContextNVPairValue("MessageStatus"));
        
        assertEquals("My note!", helper.getEnvelopeContextNVPairValue(isReDispatch ? "UpdatedNotes" : "Notes"));

        assertEquals(
                "IF",
                helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
        // Changed mappings
        assertNull(helper.getEnvelopeContextNVPairValue("SentDateTime"));
        assertEquals(
                "-3141592654",
                helper.getEnvelopeContextNVPairValue("TaskId"));
        assertEquals(
                "Let me through, please!",
                helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
        // MitchellWorkItemId
        assertEquals(
                WORK_ITEM_ID,
                helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
        // -- Not applicable for Staff Assignments
        // assertEquals("Reviewer Company code does not match", MY_TEST_ORG,
        //        helper.getEnvelopeContextNVPairValue("ReviewerCoCd"));
        // assertEquals(
        //       "Reviewer User ID does not match",
        //        TARGET_USER_FOR_DISPATCH,
        //        helper.getEnvelopeContextNVPairValue("ReviewerId"));
        assertEquals(
                "User Co Code does not match",
                MY_TEST_ORG,
                helper.getEnvelopeContextNVPairValue("UserCoCd"));
        assertEquals(
                "User Id does not match",
                TARGET_USER_FOR_DISPATCH,
                helper.getEnvelopeContextNVPairValue("UserId"));
        assertEquals("LastModified date time did not match", LAST_MODIFIED_DATE_TIME_VALUE, helper.getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME));
    }

    /**
     * Test WCAP cancel.
     * @throws Exception
     */
    public void testWCAPCancel() throws Exception {
        APDDeliveryContextDocument context;
        context = initializeCancelAPDDeliveryContext(false);
        assertPayloadSanity(context);
        registerExpectationsForCancelDelivery(false);   // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(context);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "CANCELLED";
        assertNameValuePairsForCancel(helper, status, false);
        assertCiecaBody(helper, false);
        assertAssignmentArtifactContext(helper, false);   // isSupplement
        assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    /**
     * Test WCAP cancel.
     * @throws Exception
     */
    public void testWCAPCancelSupplement() throws Exception {
        APDDeliveryContextDocument context;
        context = initializeCancelAPDDeliveryContext(true);
        assertPayloadSanity(context);
        registerExpectationsForCancelDelivery(true);    // isSupplement
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(context);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "CANCELLED";
        assertNameValuePairsForCancel(helper, status, true);
        assertCiecaBody(helper, false);
        assertAssignmentArtifactContext(helper, false);   // isSupplement
        assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    private void assertWorkProcessCancelUpdateMessage(
                                            final MitchellEnvelopeHelper helper,
                                            final String organization,
                                            final boolean isSupplement)
                                            throws XmlException {
        final EnvelopeBodyType workProcessUpdateMessageBody =
                            helper.getEnvelopeBody("WorkProcessUpdateMessage");
        assertEquals(
                "WorkProcessUpdateMessage",
                workProcessUpdateMessageBody.getMetadata().getIdentifier());
        assertEquals(
                "WorkProcessUpdateMessage",
                workProcessUpdateMessageBody.getMetadata().getMitchellDocumentType());
        assertEquals(
                "com.mitchell.schemas.WorkProcessUpdateMessageDocument",
                workProcessUpdateMessageBody.getMetadata().getXmlBeanClassname());
        final WorkProcessUpdateMessageDocument workProcessUpdateMessageDocument =
            WorkProcessUpdateMessageDocument.Factory
                .parse(workProcessUpdateMessageBody.getContent().toString());
        final WorkProcessUpdateMessage workProcessUpdateMessage =
                workProcessUpdateMessageDocument.getWorkProcessUpdateMessage();
        assertEquals(
                ActivityStatusType.BEGIN,
                workProcessUpdateMessage.getActivityStatus());
        assertEquals(
                "Cancel activity operation does not match",
                isSupplement ? "CANCEL_APPRAISAL_ASSIGNMENT" : "CANCEL_APPRAISAL_ASSIGNMENT",
                workProcessUpdateMessage.getActivityOperation());
        assertEquals(
                "Cancel Update Colaborators do not match"
                    + "[" + organization + "]"
                    + "["
                    + workProcessUpdateMessage.getCollaborator()
                    + "]",
                organization,
                workProcessUpdateMessage.getCollaborator());
        assertNotNull(workProcessUpdateMessage.getPrivateIndex());
        assertNotNull(workProcessUpdateMessage.getPublicIndex());
    }

    private void assertWorkProcessRedispatchUpdateMessage(
                                        final MitchellEnvelopeHelper helper,
                                        final String organization,
                                        final boolean isSupplement)
                                        throws XmlException {
        final EnvelopeBodyType workProcessUpdateMessageBody =
                            helper.getEnvelopeBody("WorkProcessUpdateMessage");
        assertEquals(
                "WorkProcessUpdateMessage",
                workProcessUpdateMessageBody.getMetadata().getIdentifier());
        assertEquals(
                "WorkProcessUpdateMessage",
                workProcessUpdateMessageBody.getMetadata()
                                                    .getMitchellDocumentType());
        assertEquals(
                "com.mitchell.schemas.WorkProcessUpdateMessageDocument",
                workProcessUpdateMessageBody.getMetadata()
                                                    .getXmlBeanClassname());
        final WorkProcessUpdateMessageDocument workProcessUpdateMessageDocument =
            WorkProcessUpdateMessageDocument.Factory
                .parse(workProcessUpdateMessageBody.getContent().toString());
        final WorkProcessUpdateMessage workProcessUpdateMessage =
                workProcessUpdateMessageDocument.getWorkProcessUpdateMessage();
		assertEquals(
                ActivityStatusType.BEGIN,
                workProcessUpdateMessage.getActivityStatus());
        assertEquals(
                isSupplement ?
                    "UPDATE_APPRAISAL_ASSIGNMENT" : "UPDATE_APPRAISAL_ASSIGNMENT",
                workProcessUpdateMessage.getActivityOperation());
        assertEquals(
                "Colaborators do not match" 
                    + "["
                    + organization
                    + "]"
                    + "["
                    + workProcessUpdateMessage.getCollaborator()
                    + "]",
                organization,
                workProcessUpdateMessage.getCollaborator());
        assertNotNull(workProcessUpdateMessage.getPrivateIndex());
        assertNotNull(workProcessUpdateMessage.getPublicIndex());
    }

    /**
     * @param helper
     * @param status
     * @param isSupplement
     *            TODO
     */
    private void assertNameValuePairsForCancel(
                                            final MitchellEnvelopeHelper helper,
                                            final String status,
                                            final boolean isSupplement) {
        assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                helper.getEnvelopeContextNVPairValue("MessageType"));
        assertEquals(
                status,
                helper.getEnvelopeContextNVPairValue("MessageStatus"));
        assertEquals(
                "My note!",
                helper.getEnvelopeContextNVPairValue("UpdatedNotes"));
        assertEquals(
                "IF",
                helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
        assertEquals(
                "-3141592654", helper.getEnvelopeContextNVPairValue("TaskId"));
        assertNull(helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
        assertEquals(
                WORK_ITEM_ID,
                helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
        assertEquals("LastModified date time did not match", LAST_MODIFIED_DATE_TIME_VALUE, helper.getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME));
    }

    /**
     * @param helper
     * @throws XmlException
     */
    private void assertWorkProcessInitiationMessage(
                                        final MitchellEnvelopeHelper helper,
                                        final String organization,
                                        final boolean isSupplement)
                                        throws XmlException {
        final EnvelopeBodyType workProcessInitiationMessageBody = helper
                .getEnvelopeBody("WorkProcessInitiationMessage");
        assertEquals(
                "WorkProcessInitiationMessage",
                workProcessInitiationMessageBody.getMetadata().getIdentifier());
        assertEquals(
                "WorkProcessInitiationMessage",
                workProcessInitiationMessageBody.getMetadata()
                                                    .getMitchellDocumentType());
        assertEquals(
                "com.mitchell.schemas.WorkProcessInitiationMessageDocument",
                workProcessInitiationMessageBody.getMetadata()
                                                    .getXmlBeanClassname());
        final WorkProcessInitiationMessageDocument workProcessInitiationMessageDocument = 
                WorkProcessInitiationMessageDocument.Factory.parse(
                    workProcessInitiationMessageBody.getContent().toString());
        final WorkProcessInitiationMessage workProcessInitializationMessage =
                workProcessInitiationMessageDocument.getWorkProcessInitiationMessage();
        assertNotNull(workProcessInitializationMessage);
        assertEquals(
                "Expected organization does not match actual work process colaborator",
                organization,
                workProcessInitializationMessage.getWorkProcessCollaborator());
        //TODO Compare returning NULL -- WHY??
        if (!isSupplement) {
            assertEquals(
                    "WCAp Appraisal Assignment",
                    workProcessInitializationMessage.getDefinition().getType());
        } else {
            assertEquals(                    
                    "WCAp Appraisal Assignment",    // Supplements using Original WP Definition - "WCAp Supplement Appraisal Assignment",
                    workProcessInitializationMessage.getDefinition().getType());
        }
        assertEquals(
                "Work process message version does not match",
                "1.0",
                workProcessInitializationMessage.getDefinition().getVersion());
        assertEquals(
                "CARRIER",
                workProcessInitializationMessage.getRoleMapping()
                            .getRoleCollaboratorPairArray(0).getRole());
        assertEquals(
                "Expected organization does not match actual role colaborator",
                organization,
                workProcessInitializationMessage.getRoleMapping()
                            .getRoleCollaboratorPairArray(0).getCollaborator());
        assertEquals(
                "ESTIMATOR",
                workProcessInitializationMessage.getRoleMapping()
                            .getRoleCollaboratorPairArray(1).getRole());
        assertNotNull(workProcessInitializationMessage.getPrivateIndex());
        assertNotNull(workProcessInitializationMessage.getPublicIndex());
    }

    /**
     *
     * @param helper
     * @throws com.bea.xml.XmlException
     */
    private void assertAssignmentArtifactContext(
                                    final MitchellEnvelopeHelper helper,
                                    final boolean isSupplement)
                                    throws XmlException {
        final EnvelopeBodyType artifactContextBody = helper
                                    .getEnvelopeBody("PlatformArtifactContext");
        assertEquals(
                "Identifier for CLAIM doesn't match",
                "PlatformArtifactContext",
                artifactContextBody.getMetadata().getIdentifier());
        assertEquals(
                "Document Type for CLAIM doesn't match",
                "ARTIFACT_CONTEXT_XML",
                artifactContextBody.getMetadata().getMitchellDocumentType());
        assertEquals(
                "XmlBeanClassname for CLAIM doesn't match",
                "com.mitchell.schemas.ArtifactContextDocument",
                artifactContextBody.getMetadata().getXmlBeanClassname());
        final ArtifactContextDocument artifactContextDocument = 
                ArtifactContextDocument.Factory.parse(
                                   artifactContextBody.getContent().toString());
        final ArtifactContext artifactContext =
                                   artifactContextDocument.getArtifactContext();
        assertNotNull("ArtifactContext for CLAIM can't be null", artifactContext);
        assertNotNull(
                "ArtifactKey can't be null",
                artifactContext.getArtifactKey());
        assertEquals(
                "Expected Artifact Key for CLAIM doesn't match Client Claim Number",
                CLIENT_CLAIM_NUMBER,
                artifactContext.getArtifactKey());
        assertEquals(
                "ArtifactContextType for CLAIM doesn't match",
                ARTIFACT_CONTEXT_TYPE_CLAIM,
                artifactContext.getArtifactContextType());

        /**
         * Validate related context
         */
        RelatedContexts relatedContexts = artifactContext.getRelatedContexts();
        assertNotNull("RelatedContexts can't be null", relatedContexts);
        ArtifactContext[] relConArtifactContextArr =
                relatedContexts.getArtifactContextArray();
        if (relConArtifactContextArr != null) {
        	for (int i=0; i < relConArtifactContextArr.length; i++) {
        		
        		if (i==0)  {         //  NOTE@!! Only asserting first ArtifactContext = ASSIGNMENT
        			assertEquals(
        					"ArtifactContextType for ASSIGNMENT doesn't match",
        					ARTIFACT_CONTEXT_TYPE_ASMT,
        					relConArtifactContextArr[i].getArtifactContextType());
        			
        			Items items = relConArtifactContextArr[i].getItems();
        			assertNotNull("Items can't be null", items);
        			ArtifactItem[] relConArtifactItemArr =
        				items.getArtifactItemArray();
        			if (relConArtifactItemArr != null) {
        				for (int j = 0; j < relConArtifactItemArr.length; j++) {
        					assertEquals(
        							"ArtifactItemType for REPORT doesn't match",
        							ARTIFACT_ITEM_TYPE_REPORT,
        							relConArtifactItemArr[i].getArtifactItemType());
        					Views views = relConArtifactItemArr[i].getViews();
        					assertNotNull("Views can't be null", views);
        					ArtifactView[] relConArtifactViewArr =
        						views.getArtifactViewArray();
        					assertNotNull(
        							"ArtifactView can't be null",
        							relConArtifactViewArr);
        					for (int k = 0; k < relConArtifactViewArr.length; k++) {
        						Metadata metaData =
        							relConArtifactViewArr[k].getMetadata();
        						assertNotNull(
        								"Metadata can't be null",
        								relConArtifactViewArr);
        						assertEquals(
        								"ArtifactViewType for REPORT doesn't match",
        								ARTIFACT_VIEW_TYPE_DSPH_RPT,
        								metaData.getArtifactViewType());
        					}
        				}
        			}
        		}
        	}
        }

        /**
         * validate items
         */
        Items items = artifactContext.getItems();
        assertNotNull("Items can't be null", items);
        ArtifactItem[] artifactItemArr = items.getArtifactItemArray();
        if (artifactItemArr != null) {
            for (int i = 0; i < artifactItemArr.length; i++) {
                if (artifactItemArr[i].getArtifactItemType()
                                                .matches(ARTIFACT_ITEM_TYPES)) {
                    Views views = artifactItemArr[i].getViews();
                    assertNotNull(
                            "Views for PHOTO/DOCUMENT can't be null",
                            views);
                    ArtifactView[] artifactViewArr =
                                                views.getArtifactViewArray();
                    assertNotNull(
                            "ArtifactView for PHOTO/DOCUMENT can't be null",
                            artifactViewArr);
                    for (int k = 0; k < artifactViewArr.length; k++) {
                        assertNotNull(
                                "RemoteDocStoreReference for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getRemoteDocStoreReference());
                        assertNotNull(
                                "FileName for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getFileName());
                        assertNotNull(
                                "FileExtension for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getFileExtension());
                        assertNotNull(
                                "AttachedDate for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getAttachedDate());
                        assertNotNull(
                                "CreatedDate for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getCreatedDate());
                        assertNotNull(
                                "LastModifiedDate for PHOTO/DOCUMENT can't be null",
                                artifactViewArr[k].getLastModifiedDate());
                        Metadata metaData = artifactViewArr[k].getMetadata();
                        if (!metaData.getArtifactViewType()
                                                .matches(ARTIFACT_VIEW_TYPES)) {
                            fail("Artifact view type must be either IMAGE or DATA_FILE");
                        }
                    }
                } else {
                    fail("Artifact items type must be either PHOTO or DOCUMENT");
                }
            }
        }
    }

    /**
    *
    * @param helper
    * @throws com.bea.xml.XmlException
    */
   private void assertEstimateArtifactContext(
                                   final MitchellEnvelopeHelper helper,
                                   final boolean isSupplement)
                                   throws XmlException {
       final EnvelopeBodyType artifactContextBody = helper
                                   .getEnvelopeBody("PlatformArtifactContext");
       assertEquals(
               "Identifier for CLAIM doesn't match",
               "PlatformArtifactContext",
               artifactContextBody.getMetadata().getIdentifier());
       assertEquals(
               "Document Type for CLAIM doesn't match",
               "ARTIFACT_CONTEXT_XML",
               artifactContextBody.getMetadata().getMitchellDocumentType());
       assertEquals(
               "XmlBeanClassname for CLAIM doesn't match",
               "com.mitchell.schemas.ArtifactContextDocument",
               artifactContextBody.getMetadata().getXmlBeanClassname());
       final ArtifactContextDocument artifactContextDocument = 
               ArtifactContextDocument.Factory.parse(
                                  artifactContextBody.getContent().toString());
       final ArtifactContext artifactContext =
                                  artifactContextDocument.getArtifactContext();
       assertNotNull("ArtifactContext for CLAIM can't be null", artifactContext);
       assertNotNull(
               "ArtifactKey can't be null",
               artifactContext.getArtifactKey());
       assertEquals(
               "Expected Artifact Key for CLAIM doesn't match Client Claim Number",
               CLIENT_CLAIM_NUMBER,
               artifactContext.getArtifactKey());
       assertEquals(
               "ArtifactContextType for CLAIM doesn't match",
               ARTIFACT_CONTEXT_TYPE_CLAIM,
               artifactContext.getArtifactContextType());
       
       /**
        * Validate related context
        */
       RelatedContexts relatedContexts = artifactContext.getRelatedContexts();
       assertNotNull("RelatedContexts can't be null", relatedContexts);
       ArtifactContext[] relConArtifactContextArr =
               relatedContexts.getArtifactContextArray();
       if (relConArtifactContextArr != null) {
           for (int i=0; i < relConArtifactContextArr.length; i++) {

               if (i == 0)  {
            	   assertEquals(
           				"ArtifactContextType for ASSIGNMENT doesn't match",
        				ARTIFACT_CONTEXT_TYPE_ASMT,      //NOTE: ArtifactContext #1 = ASSIGNMENT
        				relConArtifactContextArr[i].getArtifactContextType());
               }
               if (i > 1)  {
            	   assertEquals(
            			   "ArtifactContextType for ESTIMATE doesn't match",
            			   ARTIFACT_CONTEXT_TYPE_ESTIMATE,     //NOTE: ArtifactContext #2 = ESTIMATE
            			   relConArtifactContextArr[i].getArtifactContextType());            	   

            	   Items items = relConArtifactContextArr[i].getItems();
            	   assertNotNull("Items can't be null", items);
            	   ArtifactItem[] relConArtifactItemArr =
            		   items.getArtifactItemArray();
            	   if (relConArtifactItemArr != null) {
            		   for (int j = 0; j < relConArtifactItemArr.length; j++) {
            			   assertEquals(
            					   "ArtifactItemType for ESTIMATE doesn't match",
            					   ARTIFACT_ITEM_TYPE_ESTIMATE,                   
            					   relConArtifactItemArr[i].getArtifactItemType());
            			   Views views = relConArtifactItemArr[i].getViews();
            			   assertNotNull("Views can't be null", views);
            			   ArtifactView[] relConArtifactViewArr =
            				   views.getArtifactViewArray();
            			   assertNotNull(
            					   "ArtifactView can't be null",
            					   relConArtifactViewArr);
            			   for (int k = 0; k < relConArtifactViewArr.length; k++) {
            				   Metadata metaData =
            					   relConArtifactViewArr[k].getMetadata();
            				   assertNotNull(
            						   "Metadata can't be null",
            						   relConArtifactViewArr);
            				   assertEquals(
            						   "ArtifactViewType for ESTIMATE doesn't match",
            						   ARTIFACT_VIEW_TYPE_ESTIMATE,         
            						   metaData.getArtifactViewType());
            			   }
            		   }
            	   }               
               }
           }
       }

       /**
        * validate items
        */
       Items items = artifactContext.getItems();
       assertNotNull("Items can't be null", items);
       ArtifactItem[] artifactItemArr = items.getArtifactItemArray();
       if (artifactItemArr != null) {
           for (int i = 0; i < artifactItemArr.length; i++) {
               if (artifactItemArr[i].getArtifactItemType()
                                               .matches(ARTIFACT_ITEM_TYPES)) {
                   Views views = artifactItemArr[i].getViews();
                   assertNotNull(
                           "Views for PHOTO/DOCUMENT can't be null",
                           views);
                   ArtifactView[] artifactViewArr =
                                               views.getArtifactViewArray();
                   assertNotNull(
                           "ArtifactView for PHOTO/DOCUMENT can't be null",
                           artifactViewArr);
                   for (int k = 0; k < artifactViewArr.length; k++) {
                       assertNotNull(
                               "RemoteDocStoreReference for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getRemoteDocStoreReference());
                       assertNotNull(
                               "FileName for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getFileName());
                       assertNotNull(
                               "FileExtension for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getFileExtension());
                       assertNotNull(
                               "AttachedDate for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getAttachedDate());
                       assertNotNull(
                               "CreatedDate for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getCreatedDate());
                       assertNotNull(
                               "LastModifiedDate for PHOTO/DOCUMENT can't be null",
                               artifactViewArr[k].getLastModifiedDate());
                       Metadata metaData = artifactViewArr[k].getMetadata();
                       if (!metaData.getArtifactViewType()
                                               .matches(ARTIFACT_VIEW_TYPES)) {
                           fail("Artifact view type must be either IMAGE or DATA_FILE");
                       }
                   }
               } else {
                   fail("Artifact items type must be either PHOTO or DOCUMENT");
               }
           }
       }
   }
    
    
    /**
     * @param helper
     * @param isUpdate
     *            TODO
     * @throws XmlException
     */
    private void assertCiecaBody(
                            final MitchellEnvelopeHelper helper,
                            final boolean isUpdate)
                            throws XmlException {
        final EnvelopeBodyType assignmentBms =
                helper.getEnvelopeBody(isUpdate ?
                                    "UpdatedAssignmentBMS" : "AssignmentBMS");
        assertNotNull("Assignment BMS is not attached", assignmentBms);
        assertEquals(
                "com.cieca.bms.CIECADocument",
                assignmentBms.getMetadata().getXmlBeanClassname());
        final CIECADocument recieved =
                CIECADocument.Factory.parse(assignmentBms.getContent().toString());
        assertEquals(
                recieved.getCIECA().getAssignmentAddRq().getDocumentInfo()
                                    .getReferenceInfo().getPassThroughInfo(),
                ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo()
                                    .getReferenceInfo().getPassThroughInfo());
    }

    /**
	 *
	 */
    private void registerExpectationsForDispatchDelivery(final boolean isSupplement) {
        mockHandlerUtils.expects(atLeastOnce())
                .method("getCiecaDocumentFromMitchellEnvelope")
                .with(acceptableInboundMeDoc(),
                    eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                    eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup more specific params.
        //TODO Add proper arguments 
        if( isSupplement) {
            mockHandlerUtils.expects(exactly(5)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
        } else {
            mockHandlerUtils.expects(exactly(3)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
        }
        mockDispatchReportBuilder.expects(once())
                    .method("createDispatchReport").withAnyArguments()
                                    .will(returnValue(new File("dr-rep.file")));
        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once())
                    .method("putDocument").withAnyArguments()
                                    .will(returnValue(putDocResponse));
        
        final GetDocResponse getDocResponse = new GetDocResponse();
        getDocResponse.getfilenameoriginal();
        if(isSupplement) {
            mockDocumentStoreClientProxy.expects(twice())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));  	
        } else {
            mockDocumentStoreClientProxy.expects(once())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));        	
        }

        final WPKeyRequestDocument request =
                                    WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(exactly(3))
                    .method("initWPKeyRequest").withAnyArguments()
                                    .will(returnValue(request));// 3

        mockWorkProcessServiceClient.expects(once())
            .method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() })
                            .will(returnValue(null));
        mockWorkProcessServiceClient.expects(twice())
            .method("retrieveWorkProcessKey")// 2
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() })
                            .will(returnValue(null));// 2
        mockWorkProcessServiceClient.expects(twice())
                        .method("registerWorkProcessKey").withAnyArguments();
        final CrossOverUserInfoDocument xOverUserInfo =
                                CrossOverUserInfoDocument.Factory.newInstance();
        xOverUserInfo.addNewCrossOverUserInfo()
                        .addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
        xOverUserInfo.getCrossOverUserInfo()
                        .addNewReviewerInfo().setReviewerOrgCode("1234");
        final OnlineUserType onlineUsers = 
                xOverUserInfo.getCrossOverUserInfo().addNewOnlineInfo()
                        .addNewOnlineOffice().addNewOnlineUsers();
        final OnlineUserOrgCodeType onlineUserOrgCode =
                                        onlineUsers.addNewOnlineUserOrgCode();
        onlineUserOrgCode.setIsPrimaryUser(true);
        onlineUsers.setOnlineUserOrgID(3);
        final OrgInfoDocument orgInfoDocument =
                                        OrgInfoDocument.Factory.newInstance();
        OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
        orgInfoType.setOrgCode(REVIEWER_CO_CODE);
        orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        final AppLoggingDocument apploggingDocument = AppLoggingDocument.Factory.newInstance();
        final AppLoggingType appLogging = apploggingDocument.addNewAppLogging();

        // TODO:  Remove constraints if not needed!!
        final Constraint constraints[] = 
                new Constraint[] {
                    new IsInstanceOf(AppLoggingDocument.class),
                    new IsInstanceOf(UserInfoDocument.class), new IsAnything(),
                    new IsEqual(AssignmentDeliveryConstants.APPLICATION_NAME),
                    new IsEqual(
                            AssignmentDeliveryConstants.APPLICATION_NAME),
                            new IsInstanceOf(AppLoggingNVPairs.class) };
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));

    }

	private void registerExpectationsForReDispatchDelivery(final boolean isSupplement) {
        mockHandlerUtils.expects(atLeastOnce())
                    .method("getCiecaDocumentFromMitchellEnvelope")
                        .with(acceptableInboundMeDoc(),
                              eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                              eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));  // TODO: setup more specific params.
        
        if( isSupplement) {
            mockHandlerUtils.expects(exactly(5)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();  //TODO
        } else {
            mockHandlerUtils.expects(exactly(3)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();   //TODO
        }
        mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                .will(returnValue(new File("dr-rep.file")));

        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once())
                    .method("putDocument").withAnyArguments()
                .will(returnValue(putDocResponse));

        final GetDocResponse getDocResponse = new GetDocResponse();
        if(isSupplement) {
            mockDocumentStoreClientProxy.expects(twice())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));  	
        } else {
            mockDocumentStoreClientProxy.expects(once())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));        	
        }
        
        final WPKeyRequestDocument request = WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(twice())
                    .method("initWPKeyRequest").withAnyArguments()
                                    .will(returnValue(request));// 3

        // This is different than dispatch. A key is returned.
        mockWorkProcessServiceClient.expects(once())
            .method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() })
                    .will(returnValue("Iamdoc,DocIam"));
        mockWorkProcessServiceClient.expects(once())
            .method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() })
                    .will(returnValue("Iamdoc,DocIam"));
        final CrossOverUserInfoDocument xOverUserInfo =
                            CrossOverUserInfoDocument.Factory.newInstance();
        xOverUserInfo.addNewCrossOverUserInfo().addNewNonStaffInfo()
                                            .setNonStaffCompanyCode("My shop");
        xOverUserInfo.getCrossOverUserInfo().addNewReviewerInfo()
                                            .setReviewerOrgCode("1234");
        final OnlineUserType onlineUsers = 
                xOverUserInfo.getCrossOverUserInfo().addNewOnlineInfo()
                                    .addNewOnlineOffice().addNewOnlineUsers();
        final OnlineUserOrgCodeType onlineUserOrgCode =
                                        onlineUsers.addNewOnlineUserOrgCode();
        onlineUserOrgCode.setIsPrimaryUser(true);
        onlineUsers.setOnlineUserOrgID(3);
        final OrgInfoDocument orgInfoDocument =
                                        OrgInfoDocument.Factory.newInstance();
        OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
        orgInfoType.setOrgCode(REVIEWER_CO_CODE);
        orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));
    }

    private Constraint acceptableInboundMeDoc() {
        return new Constraint() {

            public StringBuffer describeTo(final StringBuffer buffer) {
                return buffer.append("Valid MEDoc for handler utils");
            }

            public boolean eval(final Object o) {
                final MitchellEnvelopeDocument mitchellEnvelopeDocument =
                                                (MitchellEnvelopeDocument) o;
                assertNotNull(
                        "Mitchell envelope document is null.",
                        mitchellEnvelopeDocument);
                assertNotNull(
                        "Mitchell envelope  is null.",
                        mitchellEnvelopeDocument.getMitchellEnvelope());
                final MitchellEnvelopeHelper meh =
                        new MitchellEnvelopeHelper(mitchellEnvelopeDocument);
                assertNotNull(
                    "No CIECABMS attached to the envelope document",
                    meh.getEnvelopeBody(
                        AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER));
                return true;
            }
        };
    }

    private void registerExpectationsForCancelDelivery(final boolean isSupplement) {

        if( isSupplement) {
            mockHandlerUtils.expects(exactly(4)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
        } else {
            mockHandlerUtils.expects(exactly(2)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
        }
        
        mockHandlerUtils.expects(atLeastOnce())
                .method("getCiecaDocumentFromMitchellEnvelope").withAnyArguments()
                    .will(returnValue(ciecaDocument));// TODO: setup more specfic params.
        mockDispatchReportBuilder.expects(once())
                    .method("createDispatchReport").withAnyArguments()
                                .will(returnValue(new File("dr-rep.file")));        

        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once())
                    .method("putDocument").withAnyArguments()
                                .will(returnValue(putDocResponse));

        final GetDocResponse getDocResponse = new GetDocResponse();
        if(isSupplement) {
            mockDocumentStoreClientProxy.expects(twice())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));  	
        } else {
            mockDocumentStoreClientProxy.expects(once())
            .method("getDocument").withAnyArguments()
                            .will(returnValue(getDocResponse));        	
        }

        final WPKeyRequestDocument wprDoc =
                                    WPKeyRequestDocument.Factory.newInstance();
        wprDoc.addNewWPKeyRequest().setContextIdentifier("APD");
        mockWorkProcessServiceClient.expects(once())
                .method("initWPKeyRequest")
                    .with(eq("CTX_PUBLIC_CLIENT_CLAIM_NUMBER"))
                        .will(returnValue(wprDoc));
        mockWorkProcessServiceClient.expects(twice())
                .method("initWPKeyRequest")
                    .with(eq("CTX_PRIVATE_APPRAISAL_ASSIGNMENT"))
                        .will(returnValue(wprDoc));
        mockWorkProcessServiceClient.expects(once())
                .method("retrieveWorkProcessKey")
                    .with(new Constraint[] {
                                    eq("IF"),
                                    acceptablePrivateKeyWprDoc() })
                        .will(returnValue("Iamdoc,DocIam"))
                .id("Private key call");
        mockWorkProcessServiceClient.expects(once())
                .method("retrieveWorkProcessKey")
                    .with(new Constraint[] {
                                    eq("IF"),
                                    acceptablePublicKeyWprDoc() })
                        .will(returnValue("Iamdoc,DocIam"))
                .id("Public key call");
        mockWorkProcessServiceClient.expects(once())
                .method("removeWorkProcessKey")
                    .with(new Constraint[] {
                                    eq("IF"),
                                    acceptablePrivateKeyWprDoc() })
                        .id("Private removal for cancel");
        final CrossOverUserInfoDocument xOverUserInfoDocument =
                            CrossOverUserInfoDocument.Factory.newInstance();
        final CrossOverUserType xOverUserInfo =
                            xOverUserInfoDocument.addNewCrossOverUserInfo();
        xOverUserInfo.addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
        xOverUserInfo.addNewReviewerInfo().setReviewerOrgCode("1234");
        final OnlineUserType onlineUser = 
                xOverUserInfo.addNewOnlineInfo()
                                    .addNewOnlineOffice().addNewOnlineUsers();
        onlineUser.setOnlineUserOrgID(1234);
        final OnlineUserOrgCodeType onlineUserOrgCode =
                                        onlineUser.addNewOnlineUserOrgCode();
        onlineUserOrgCode.setIsPrimaryUser(true);
        final OrgInfoDocument orgInfoDocument =
                                        OrgInfoDocument.Factory.newInstance();
        OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
        orgInfoType.setOrgCode(REVIEWER_CO_CODE);
        orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));
    }

    /**
     * @return
     */
    private InvocationMatcher twice() {
        return exactly(2);
    }

    private Constraint acceptablePublicKeyWprDoc() {
        return new Constraint() {

            public StringBuffer describeTo(final StringBuffer buffer) {
                return buffer.append("Comparing public document key");
            }

            public boolean eval(final Object o) {
                final WPKeyRequestDocument in = (WPKeyRequestDocument) o;
                return in.getWPKeyRequest()
                        .getClientClaimNumber().equals(CLIENT_CLAIM_NUMBER);

            }
        };
    }

    private Constraint acceptablePrivateKeyWprDoc() {
        return new Constraint() {

            public StringBuffer describeTo(final StringBuffer buffer) {
                return buffer.append("Comapring private document key");
            }

            public boolean eval(final Object o) {
                final WPKeyRequestDocument in = (WPKeyRequestDocument) o;
                assertEquals(
                        "Client claim number does not match",
                        CLIENT_CLAIM_NUMBER,
                        in.getWPKeyRequest().getClientClaimNumber());
                // assertEquals(-4141592654L, in.getWPKeyRequest().getClaimId());
                assertEquals(
                        "Work item id does not match",
                        WORK_ITEM_ID,
                        in.getWPKeyRequest().getWorkItemId());
                assertEquals(
                        "Task ID does not match",
                        TASK_ID,
                        in.getWPKeyRequest().getWorkAssignmentId());
                return true;
            }
        };
    }

    /**
     * @param nonDrpAPDDeliveryContext
     */
    private void assertPayloadSanity(
                    final APDDeliveryContextDocument nonDrpAPDDeliveryContext) {
        assertNotNull(nonDrpAPDDeliveryContext);
        assertNotNull(nonDrpAPDDeliveryContext.getAPDDeliveryContext());
        assertNotNull(
                nonDrpAPDDeliveryContext.getAPDDeliveryContext()
                                            .getAPDAppraisalAssignmentInfo());
        assertNotNull(
                nonDrpAPDDeliveryContext.getAPDDeliveryContext()
                                        .getAPDAppraisalAssignmentInfo()
                                            .getAPDCommonInfo());
        final MitchellEnvelopeType assignmentMitchellEnvelope =
                nonDrpAPDDeliveryContext.getAPDDeliveryContext()
                        .getAPDAppraisalAssignmentInfo()
                            .getAssignmentMitchellEnvelope()
                                .getMitchellEnvelope();
        final MitchellEnvelopeHelper meh = MitchellEnvelopeHelper.newInstance();
        meh.getDoc().setMitchellEnvelope(assignmentMitchellEnvelope);
    }

    /**
     * @return
     * @throws XmlException
     */
    private MitchellEnvelopeHelper synthesizeMwmHelper() throws XmlException {
        final MitchellWorkflowMessageDocument payload =
                                            messagePostingAgent.getPayload();
        assertNotNull(payload);
        assertNotNull(payload.getMitchellWorkflowMessage());
        assertNotNull(payload.getMitchellWorkflowMessage().getTrackingInfo());
        assertNotNull(payload.getMitchellWorkflowMessage().getData());
        assertEquals(155101, payload.getMitchellWorkflowMessage().getData().getType());
        final MitchellEnvelopeDocument envelope =
                MitchellEnvelopeDocument.Factory.parse(payload
                        .getMitchellWorkflowMessage().getData().toString());
        final MitchellEnvelopeHelper helper =
                                        new MitchellEnvelopeHelper(envelope);
        return helper;
    }

    // Simple turnaround converter for testing
    private class TestBmsConvertor implements CiecaBmsConverter {
		public CIECADocument convert(CIECADocument input) {
			return input;
		}
    }
    
    private Mock mockAppLoggerBridge;
    private Mock mockAssignmentDeliveryUtils;
    private Mock mockConverter;
    private Mock mockDispatchReportBuilder;
    private Mock mockErrorLoggingServiceWrapper;
    private Mock mockHandlerUtils;
    private Mock mockDocumentStoreClientProxy;
    private Mock mockEstimatePackageClientProxy;
    private MockMessagePostingAgent messagePostingAgent;
    private MockAssignmentDeliveryConfigBridge assignmentDeliveryConfigBridge;
    private Mock mockUserInfoClient;
    private Mock mockWorkProcessServiceClient;
    private Mock mockBmsConverterFactory;

}
