package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.mitchell.common.types.*;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.EnvelopeContextType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractDispatchReportBuilder;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.DocStoreClientProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.WorkAssignmentProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.AbstractMessageBusDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.WebAssMsgBusDelHndlr;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;
import org.apache.xmlbeans.XmlException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Enclosed;
import java.io.File;
import java.util.Calendar;

@RunWith(Enclosed.class)
public class DaytonaHandlerTest {

    private static final String CLIENT_CLAIM_NUMBER = "ClientClaim#";
    private static final String DRP_ORG = "Drp Org !!!";
    private static final String TARGET_USER_FOR_DISPATCH = "TargetUserForDispatch";
    private static final String MY_TEST_ORG = "My test ORG";
    private static final long CLAIM_ID = -4141592654L;
    private static final String REVIEWER_CO_CODE = "IF";
    private static final String NON_DRP_ORG_FOR_DRP = "4";
    private static final String TEST_ORG = "1";
    private static final String TEST_DRP_ORG = "2";
    private static final long TASK_ID = -3141592654L;
    private static final long CLAIM_EXPOSURE_ID = 99898L;
    private static final long REFERENCE_ID = 4354334L;
    private static final String WORK_ITEM_ID = "work-1234";
    private static final String DRP_ORG_USER = "DRP Org user, woo hoo!!!";
    private static final String TARGET_USER_FOR_CANCEL = "TargetUserForCancel";
    private static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
    private static final String LAST_MODIFIED_DATE_TIME_VALUE = "2013-03-26T03:22:16.932-07:00";
    private static CIECADocument ciecaDocument;
    private static final String USER_ID = "1";

    public static class When_deliver_is_called extends MockObjectTestCase {

        private WebAssMsgBusDelHndlr sut;

        public void setUp() throws Exception {

            sut = new WebAssMsgBusDelHndlr();
            mockAssignmentDeliveryUtils = mock(AbstractAssignmentDeliveryUtils.class);
            mockHandlerUtils = mock(AbstractHandlerUtils.class);
            mockErrorLoggingServiceWrapper = mock(ErrorLoggingServiceWrapper.class);
            mockConverter = mock(Converter.class);
            mockDispatchReportBuilder = mock(AbstractDispatchReportBuilder.class);
            mockAppLoggerBridge = mock(AppLoggerBridge.class);
            mockDocumentStoreClientProxy = mock(DocStoreClientProxy.class);
            assignmentDeliveryConfigBridge = new MockAssignmentDeliveryConfigBridge();
            assignmentDeliveryConfigBridge.setTempDir("i_am_temp");
            messagePostingAgent = new MockMessagePostingAgent();
            mockUserInfoClient = mock(UserInfoServiceEJBRemote.class);
            mockBmsConverterFactory = mock(BmsConverterFactory.class);
            mockWorkAssignmentProxy = mock(WorkAssignmentProxy.class);
            sut.setDrBuilder((AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
            sut.setLogger(new NoOpAssignmentDeliveryLogger("No-op", null));
            sut.setAssignmentDeliveryUtils((AbstractAssignmentDeliveryUtils) mockAssignmentDeliveryUtils.proxy());
            sut.setConverter((Converter) mockConverter.proxy());
            sut.setDrBuilder((AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
            sut.setErrorLoggingService((ErrorLoggingServiceWrapper) mockErrorLoggingServiceWrapper.proxy());
            sut.setHandlerUtils((AbstractHandlerUtils) mockHandlerUtils.proxy());
            sut.setLogger(new NoOpAssignmentDeliveryLogger("test", null));
            sut.setAppLoggerBridge((AppLoggerBridge) mockAppLoggerBridge.proxy());
            sut.setAssignmentDeliveryConfigBridge(assignmentDeliveryConfigBridge);
            sut.setHandlerUtils((AbstractHandlerUtils) mockHandlerUtils.proxy());
            sut.setDocumentStoreClientProxy((DocStoreClientProxy) mockDocumentStoreClientProxy.proxy());
            sut.setMessagePostingAgent(messagePostingAgent);
            sut.setUserInfoClient((UserInfoServiceEJBRemote) mockUserInfoClient.proxy());
            sut.setBmsConverterFactory((BmsConverterFactory) mockBmsConverterFactory.proxy());
            sut.setWorkAssignmentProxy((WorkAssignmentProxy) mockWorkAssignmentProxy.proxy());
        }

        /*public void test_it_should_fail_when_no_dependencies_are_set() throws Exception {
            sut = new WebAssMsgBusDelHndlr();
            try {
                System.out
                        .println("The line \" No logger has been set. Exiting \" following this statement is expected as part of a successful test");
                sut.deliverAssignment(null);
                fail("The fact that no dependencies are set was not detected, failing");
            } catch (final NullPointerException expected) {}
            try {
                sut.setLogger(new NoOpAssignmentDeliveryLogger("No-op", null));
                // Don't worry about code coverage here, this NOT supposed to finish.
                sut.deliverAssignment(initializeDispatchAPDDeliveryContext(false));
                fail("The fact that no dependencies are set was not detected, failing");
            } catch (final AssignmentDeliveryException expected) {
                assertEquals(
                        "Make sure all the dependencies are initialized. "
                                + "The following dependencies are un-satisfied [Converter, "
                                + "HandlerUtility, Dispatch Report Builder, "
                                + "Document Store Proxy, Message Posting Agent, App Logger Bridge, Error Logging Service, User Info Client]",
                        expected.getCause().getMessage()
                );
            }

        }*/

      /*  public void test_it_should_fail_when_assignment_status_is_unsupported_type() throws Exception {
            APDDeliveryContextDocument nonDrpAPDDeliveryContext;
            nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
            try {
                nonDrpAPDDeliveryContext.getAPDDeliveryContext().setMessageType("BLAH");
                sut.deliverAssignment(nonDrpAPDDeliveryContext);
                fail("BLAH type should not pass!");
            } catch (final AssignmentDeliveryException expected) {
                assertEquals("Only SUPPLEMENT and ORGINAL_ESTIMATE messages are supported, got[BLAH]", expected.getCause()
                        .getMessage());
            }
        }*/

        @Test
        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_dispatch() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument nonDrpAPDDeliveryContext;
            nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
            assertPayloadSanity(nonDrpAPDDeliveryContext);
            registerExpectationsForDispatchDelivery(userInfoDocument, false);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(nonDrpAPDDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "DISPATCHED";
            assertNameValuePairsForDispatch(helper, status, false, false);
            assertCiecaBody(helper, false);
            assertAttachments(helper, status, false);
        }

        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_complete() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument apdDeliveryContext;
            apdDeliveryContext = initializeCompleteAPDDeliveryContext(false);
            assertPayloadSanity(apdDeliveryContext);
            registerExpectationsForCompleteDelivery(userInfoDocument, false);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(apdDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "COMPLETED";
            assertNameValuePairsForComplete(helper, status, false);
            assertCiecaBodyIsNULL(helper);
            assertAttachmentsIsNULL(helper);
        }

        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_complete_and_supplement() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument apdDeliveryContext;
            apdDeliveryContext = initializeCompleteAPDDeliveryContext(true);
            assertPayloadSanity(apdDeliveryContext);
            registerExpectationsForCompleteDelivery(userInfoDocument, true);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(apdDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "COMPLETED";
            assertNameValuePairsForComplete(helper, status, true);
            assertCiecaBodyIsNULL(helper);
            assertAttachmentsIsNULL(helper);
            // assertWorkProcessCompleteUpdateMessage(helper, REVIEWER_CO_CODE + "");
        }

        /*@Test
        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_dispatch_and_supplement() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument nonDrpAPDDeliveryContext;
            nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(true);
            assertPayloadSanity(nonDrpAPDDeliveryContext);
            registerExpectationsForDispatchDelivery(userInfoDocument, true);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(nonDrpAPDDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "DISPATCHED";
            assertNameValuePairsForDispatch(helper, status, false, true);
            assertCiecaBody(helper, false);
            assertAttachments(helper, status, false);
            // assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", true);
        }*/

        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_Drp_dispatch() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument nonDrpAPDDeliveryContext;
            nonDrpAPDDeliveryContext = initializeDrpDispatchAPDDeliveryContext(false);
            assertPayloadSanity(nonDrpAPDDeliveryContext);
            registerExpectationsForDispatchDelivery(userInfoDocument, false);
            sut.deliverAssignment(nonDrpAPDDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "DISPATCHED";
            assertNameValuePairsForDispatch(helper, status, false, false);
            assertCiecaBody(helper, false);
            assertAttachments(helper, status, false);
        }

        /*public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_Drp_dispatch_and_supplement() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument nonDrpAPDDeliveryContext;
            nonDrpAPDDeliveryContext = initializeDrpDispatchAPDDeliveryContext(true);
            assertPayloadSanity(nonDrpAPDDeliveryContext);
            registerExpectationsForDispatchDelivery(userInfoDocument, true);
            sut.deliverAssignment(nonDrpAPDDeliveryContext);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "DISPATCHED";
            assertNameValuePairsForDispatch(helper, status, false, true);
            assertCiecaBody(helper, false);
            assertAttachments(helper, status, false);

        }*/

        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_cancel() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument context;
            context = initializeCancelAPDDeliveryContext(false);
            assertPayloadSanity(context);
            registerExpectationsForCancelDelivery(userInfoDocument);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(context);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "CANCELLED";
            assertNameValuePairsForCancel(helper, status, false);
            assertCiecaBody(helper, false);
            // assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
        }

        /*public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_cancel_and_supplement() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument context;
            context = initializeCancelAPDDeliveryContext(true);
            assertPayloadSanity(context);
            registerExpectationsForCancelDelivery(userInfoDocument);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(context);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "CANCELLED";
            assertNameValuePairsForCancel(helper, status, true);
            assertCiecaBody(helper, false);
        }*/

        public void test_it_should_create_and_post_assignment_to_message_bus_when_status_is_Drp_Cancel() throws Exception {
            final UserInfoDocument userInfoDocument = buildUserInfoDocument();
            APDDeliveryContextDocument context;
            context = initializeDrpCancelAPDDeliveryContext(false);
            assertPayloadSanity(context);
            registerExpectationsForCancelDelivery(userInfoDocument);
            assertNotNull("Logger is null", sut.getLogger());
            sut.deliverAssignment(context);
            final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
            // Confirm name value pairs
            final String status = "CANCELLED";
            assertNameValuePairsForCancel(helper, status, false);
            assertCiecaBody(helper, false);
        }

        private APDDeliveryContextDocument initializeDispatchAPDDeliveryContext(final boolean isSupplement)
                throws Exception {

            final APDDeliveryContextDocument doc = APDDeliveryContextDocument.Factory.newInstance();
            final APDDeliveryContextType apdDeliveryContext = doc.addNewAPDDeliveryContext();
            doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
            attachCieca(doc, apdDeliveryContext);
            addLastModifiedDateTimeInNVPair(apdDeliveryContext);
            final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = doc.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo();
            apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
            final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
            apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
            apdCommonInfo.setInsCoCode("IF");
            apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClaimId(CLAIM_ID);
            apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
            apdAppraisalAssignmentInfo.setMessageStatus("DISPATCHED");
            final UserInfoType targetUserInfo = apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
            targetUserInfo.setOrgID(TEST_ORG);
            targetUserInfo.setOrgCode(MY_TEST_ORG);
            targetUserInfo.setUserID(TARGET_USER_FOR_DISPATCH);
            final UserInfoType targetDrpUserInfo = apdCommonInfo.addNewTargetDRPUserInfo().addNewUserInfo();
            targetDrpUserInfo.setOrgCode(DRP_ORG);
            targetDrpUserInfo.setUserID(DRP_ORG_USER);
            return doc;
        }

        private APDDeliveryContextDocument initializeCompleteAPDDeliveryContext(final boolean isSupplement)
                throws Exception {

            final APDDeliveryContextDocument doc = APDDeliveryContextDocument.Factory.newInstance();
            final APDDeliveryContextType apdDeliveryContext = doc.addNewAPDDeliveryContext();
            doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
            attachCieca(doc, apdDeliveryContext);

            final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = doc.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo();
            apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
            final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
            apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
            apdCommonInfo.setInsCoCode("IF");
            apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClaimId(CLAIM_ID);
            apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
            apdAppraisalAssignmentInfo.setMessageStatus("COMPLETED");
            final UserInfoType targetUserInfo = apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
            targetUserInfo.setOrgID(TEST_ORG);
            targetUserInfo.setOrgCode(MY_TEST_ORG);
            targetUserInfo.setUserID(TARGET_USER_FOR_DISPATCH);
            final UserInfoType targetDrpUserInfo = apdCommonInfo.addNewTargetDRPUserInfo().addNewUserInfo();
            targetDrpUserInfo.setOrgCode(DRP_ORG);
            targetDrpUserInfo.setUserID(DRP_ORG_USER);
            return doc;
        }

        private APDDeliveryContextDocument initializeDrpDispatchAPDDeliveryContext(final boolean isSupplement)
                throws Exception {

            final APDDeliveryContextDocument doc = APDDeliveryContextDocument.Factory.newInstance();
            final APDDeliveryContextType aPDDeliveryContext = doc.addNewAPDDeliveryContext();
            doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
            attachCieca(doc, aPDDeliveryContext);
            addLastModifiedDateTimeInNVPair(aPDDeliveryContext);
            final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = doc.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo();
            apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
            apdAppraisalAssignmentInfo.addNewAssignmentMitchellEnvelope().addNewMitchellEnvelope();
            final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
            apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
            apdCommonInfo.setInsCoCode("IF");
            apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClaimId(CLAIM_ID);
            apdAppraisalAssignmentInfo.setMessageStatus("DISPATCHED");
            final UserInfoType targetUserInfo = apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
            targetUserInfo.setOrgID(TEST_ORG);
            targetUserInfo.setOrgCode(MY_TEST_ORG);
            targetUserInfo.setUserID(TARGET_USER_FOR_DISPATCH);
            final UserInfoType targetDrpUserInfo = apdCommonInfo.addNewTargetDRPUserInfo().addNewUserInfo();
            targetDrpUserInfo.setOrgCode(DRP_ORG);
            targetDrpUserInfo.setUserID(DRP_ORG_USER);

            return doc;
        }

        private APDDeliveryContextDocument initializeCancelAPDDeliveryContext(final boolean isSupplement) throws Exception {
            final APDDeliveryContextDocument doc = APDDeliveryContextDocument.Factory.newInstance();
            final APDDeliveryContextType aPDDeliveryContext = doc.addNewAPDDeliveryContext();
            attachCieca(doc, aPDDeliveryContext);
            addLastModifiedDateTimeInNVPair(aPDDeliveryContext);
            final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = doc.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo();
            apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
            final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
            apdAppraisalAssignmentInfo.setMessageStatus("CANCEL"); // CANCELLED");
            apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
            apdCommonInfo.setInsCoCode("IF");
            apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
            final UserInfoType userInfo = apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
            userInfo.setOrgID(NON_DRP_ORG_FOR_DRP);
            userInfo.setOrgID(TEST_ORG);
            userInfo.setOrgCode(MY_TEST_ORG);
            userInfo.setUserID(TARGET_USER_FOR_CANCEL);
            final UserInfoType drpUserInfo = apdCommonInfo.addNewTargetDRPUserInfo().addNewUserInfo();
            drpUserInfo.setOrgID(NON_DRP_ORG_FOR_DRP);
            apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClaimId(CLAIM_ID);
            doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
            return doc;
        }

        private APDDeliveryContextDocument initializeDrpCancelAPDDeliveryContext(final boolean isSupplement)
                throws Exception {
            final APDDeliveryContextDocument doc = APDDeliveryContextDocument.Factory.newInstance();
            final APDDeliveryContextType aPDDeliveryContext = doc.addNewAPDDeliveryContext();
            doc.getAPDDeliveryContext().setMessageType(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE");
            attachCieca(doc, aPDDeliveryContext);
            addLastModifiedDateTimeInNVPair(aPDDeliveryContext);
            final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = doc.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo();
            apdAppraisalAssignmentInfo.setTaskId(TASK_ID);
            final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo.addNewAPDCommonInfo();
            apdAppraisalAssignmentInfo.setMessageStatus("CANCEL");  // CANCELLED");
            apdCommonInfo.setWorkItemId(WORK_ITEM_ID);
            apdCommonInfo.setInsCoCode("IF");
            apdCommonInfo.setClientClaimNumber(CLIENT_CLAIM_NUMBER);
            final UserInfoType userInfo = apdCommonInfo.addNewTargetUserInfo().addNewUserInfo();
            userInfo.setOrgID(NON_DRP_ORG_FOR_DRP);
            userInfo.setOrgID(TEST_ORG);
            userInfo.setOrgCode(MY_TEST_ORG);
            userInfo.setUserID(TARGET_USER_FOR_CANCEL);
            apdCommonInfo.addNewTargetDRPUserInfo().addNewUserInfo().setOrgID(TEST_DRP_ORG);
            apdCommonInfo.setClaimNumber(CLIENT_CLAIM_NUMBER);
            apdCommonInfo.setClaimId(CLAIM_ID);
            return doc;
        }

        private void addLastModifiedDateTimeInNVPair(APDDeliveryContextType apdDeliveryContext) {
            final MitchellEnvelopeType mitchellEnvelope = apdDeliveryContext.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope();
            final EnvelopeContextType envelopeContext = mitchellEnvelope.addNewEnvelopeContext();
            final com.mitchell.schemas.NameValuePairType nameValuePairType = envelopeContext.addNewNameValuePair();
            nameValuePairType.setName(LAST_MODIFIED_DATE_TIME);
            nameValuePairType.setValueArray(new String[]{"2013-03-26T03:22:16.932-07:00"});
        }

        private void attachCieca(final APDDeliveryContextDocument doc, final APDDeliveryContextType apdDeliveryContext) {
            final MitchellEnvelopeDocument med = MitchellEnvelopeDocument.Factory.newInstance();
            final MitchellEnvelopeType mitchellEnvelope = MitchellEnvelopeType.Factory.newInstance();
            med.setMitchellEnvelope(mitchellEnvelope);
            ciecaDocument = CIECADocument.Factory.newInstance();
            final AssignmentAddRq assignmentAddRq = ciecaDocument.addNewCIECA().addNewAssignmentAddRq();
            final ClaimInfoType claimInfo = assignmentAddRq.addNewClaimInfo();
            claimInfo.setClaimNum("My claim!");
            assignmentAddRq.addNewDocumentInfo().addNewReferenceInfo().setPassThroughInfo("Let me through, please!");
            assignmentAddRq.addNewVehicleDamageAssignment().addAssignmentMemo("My note");
            final MitchellEnvelopeHelper meh = new MitchellEnvelopeHelper(med);
            apdDeliveryContext.addNewAPDAppraisalAssignmentInfo().addNewAssignmentMitchellEnvelope();
            meh.addNewEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER, ciecaDocument, "CIECA");
            doc.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
                    .setMitchellEnvelope(meh.getEnvelope());
        }

        private void assertNameValuePairsForDispatch(final MitchellEnvelopeHelper helper, final String status,
                                                     final boolean isReDispatch, final boolean isSupplement) {
            assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                    helper.getEnvelopeContextNVPairValue("MessageType"));
            assertEquals(status, helper.getEnvelopeContextNVPairValue("MessageStatus"));
            // Notes now populated from Cieca to Outbound payload.
            // assertEquals("My note!", helper.getEnvelopeContextNVPairValue(isReDispatch ? "UpdatedNotes" : "Notes"));
            assertEquals("IF", helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
            // Changed mappings
            assertNull(helper.getEnvelopeContextNVPairValue("SentDateTime"));
            assertEquals("-3141592654", helper.getEnvelopeContextNVPairValue("TaskId"));
            assertEquals("Let me through, please!", helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
            // MitchellWorkItemId
            assertEquals(WORK_ITEM_ID, helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
            assertEquals("Reviewer Company code does not match", MY_TEST_ORG,
                    helper.getEnvelopeContextNVPairValue("ReviewerCoCd"));
            assertEquals("Reviewer User ID does not match", TARGET_USER_FOR_DISPATCH,
                    helper.getEnvelopeContextNVPairValue("ReviewerId"));
            assertEquals("User Co Code does not match", DRP_ORG, helper.getEnvelopeContextNVPairValue("UserCoCd"));
            assertEquals("User Id does not match", DRP_ORG_USER, helper.getEnvelopeContextNVPairValue("UserId"));
            assertEquals("LastModified date time did not match", LAST_MODIFIED_DATE_TIME_VALUE, helper.getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME));
        }

        private void assertNameValuePairsForComplete(final MitchellEnvelopeHelper helper, final String status,
                                                     final boolean isSupplement) {

            assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                    helper.getEnvelopeContextNVPairValue("MessageType"));
            assertEquals(status, helper.getEnvelopeContextNVPairValue("MessageStatus"));
            assertEquals(REVIEWER_CO_CODE, helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
            assertEquals(CLIENT_CLAIM_NUMBER, helper.getEnvelopeContextNVPairValue("ClaimNumber"));
            assertEquals(WORK_ITEM_ID, helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
            assertEquals("" + TASK_ID, helper.getEnvelopeContextNVPairValue("TaskId"));
            assertNull(helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
            assertEquals(MY_TEST_ORG, helper.getEnvelopeContextNVPairValue("ReviewerCoCd"));
            assertEquals("3", helper.getEnvelopeContextNVPairValue("ShopId"));
            assertEquals(TARGET_USER_FOR_DISPATCH, helper.getEnvelopeContextNVPairValue("ReviewerId"));
            assertEquals(DRP_ORG, helper.getEnvelopeContextNVPairValue("UserCoCd"));
            assertEquals(DRP_ORG_USER, helper.getEnvelopeContextNVPairValue("UserId"));
            assertEquals(AbstractMessageBusDeliveryHandler.PRIMARY_CONTACT_TYPE_CLAIMNT, helper.getEnvelopeContextNVPairValue("PrimaryContactType"));
        }

        private void assertNameValuePairsForCancel(final MitchellEnvelopeHelper helper, final String status,
                                                   final boolean isSupplement) {
            final long NOW = Calendar.getInstance().getTimeInMillis();
            assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                    helper.getEnvelopeContextNVPairValue("MessageType"));
            assertEquals(status, helper.getEnvelopeContextNVPairValue("MessageStatus"));
            // Notes now populated from Cieca to Outbound payload.
            assertEquals("IF", helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
            // Spec changed
            // assertNotNull(helper.getEnvelopeContextNVPairValue("SentDateTime"));
            assertEquals("-3141592654", helper.getEnvelopeContextNVPairValue("TaskId"));
            assertNull(helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
            // assertTrue(FIVE_SECOND_AGO < (new
            // Date(helper.getEnvelopeContextNVPairValue("SentDateTime"))).getTime());
            // MitchellWorkItemId
            assertEquals(WORK_ITEM_ID, helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
            assertEquals("LastModified date time did not match", LAST_MODIFIED_DATE_TIME_VALUE, helper.getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME));
        }

        private void assertAttachments(final MitchellEnvelopeHelper helper, final String status, final boolean isUpdate)
                throws XmlException {
            final long NOW = Calendar.getInstance().getTimeInMillis();
            final long FIVE_SECOND_AGO = (NOW - 5000);

            final AttachmentInfoDocument attachmentInfoDocument = assertAttachmentSanity(helper, isUpdate);
            if ("DISPATCHED".equals(status)) {
                assertDispatchReport(NOW, FIVE_SECOND_AGO, attachmentInfoDocument, 0);
            } else {
                assertDispatchReport(NOW, FIVE_SECOND_AGO, attachmentInfoDocument, 0);
            }
        }

        private void assertAttachmentsIsNULL(final MitchellEnvelopeHelper helper)
                throws XmlException {
            final EnvelopeBodyType updatedWCAttachmentInfo = helper.getEnvelopeBody("UpdatedWCAttachmentInfo");
            final EnvelopeBodyType wCAttachmentInfo = helper.getEnvelopeBody("WCAttachmentInfo");
            assertNull(updatedWCAttachmentInfo);
            assertNull(wCAttachmentInfo);
        }

        private AttachmentInfoDocument assertAttachmentSanity(final MitchellEnvelopeHelper helper, final boolean isUpdate)
                throws XmlException {
            final EnvelopeBodyType attachments = helper.getEnvelopeBody(isUpdate ? "UpdatedWCAttachmentInfo"
                    : "WCAttachmentInfo");
            assertNotNull(attachments);
            assertEquals("AttachmentInfo", attachments.getMetadata().getMitchellDocumentType());
            assertEquals("com.mitchell.types.AttachmentInfoDocument", attachments.getMetadata().getXmlBeanClassname());
            assertNotNull(attachments.getContent());
            final AttachmentInfoDocument attachmentInfoDocument = AttachmentInfoDocument.Factory.parse(attachments
                    .getContent().toString());
            return attachmentInfoDocument;
        }

        /**
         * @param NOW
         * @param FIVE_SECOND_AGO
         * @param attachmentInfoDocument
         */
        private void assertDispatchReport(final long NOW, final long FIVE_SECOND_AGO,
                                          final AttachmentInfoDocument attachmentInfoDocument, final int pos) {
            final AttachmentItemType dispatchReportAttachment = attachmentInfoDocument.getAttachmentInfo()
                    .getAttachmentInfoList().getAttachmentItemArray()[pos];
            assertNotNull(dispatchReportAttachment);
            assertEquals("dr-rep.file", dispatchReportAttachment.getActualFileName());
            assertNotNull(dispatchReportAttachment.getAttachmentId());
            assertEquals("DISPATCH_REPORT", dispatchReportAttachment.getAttachmentType());
            assertTrue(NOW >= dispatchReportAttachment.getDateAdded().getTimeInMillis());
            assertTrue(FIVE_SECOND_AGO < dispatchReportAttachment.getDateAdded().getTimeInMillis());
            assertEquals("1", dispatchReportAttachment.getDocStoreFileReference());
            assertEquals(0, dispatchReportAttachment.getStatus());
        }

        /**
         * @param helper
         * @param isUpdate TODO
         * @throws org.apache.xmlbeans.XmlException
         */
        private void assertCiecaBody(final MitchellEnvelopeHelper helper, final boolean isUpdate) throws XmlException {
            final EnvelopeBodyType assignmentBms = helper.getEnvelopeBody(isUpdate ? "UpdatedAssignmentBMS"
                    : "AssignmentBMS");
            assertNotNull("Assignment BMS is not attached", assignmentBms);
            assertEquals("com.cieca.bms.CIECADocument", assignmentBms.getMetadata().getXmlBeanClassname());
            final CIECADocument recieved = CIECADocument.Factory.parse(assignmentBms.getContent().toString());
            assertEquals(
                    recieved.getCIECA().getAssignmentAddRq().getDocumentInfo().getReferenceInfo().getPassThroughInfo(),
                    ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo().getReferenceInfo().getPassThroughInfo());
            assertEquals(
                    recieved.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getAssignmentMemoArray(0),
                    ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getAssignmentMemoArray(0));
        }

        private void assertCiecaBodyIsNULL(final MitchellEnvelopeHelper helper) throws XmlException {
            final EnvelopeBodyType assignmentBms = helper.getEnvelopeBody("AssignmentBMS");
            final EnvelopeBodyType updateAssignmentBms = helper.getEnvelopeBody("UpdatedAssignmentBMS");

            assertNull("Assignment BMS is attached", assignmentBms);
            assertNull("Assignment BMS is attached", updateAssignmentBms);
        }

        /**
         *
         */
        private void registerExpectationsForDispatchDelivery(final UserInfoDocument userInfoDocument, final boolean isSupplement) {
            if (isSupplement) {
                mockHandlerUtils
                        .expects(twice())
                        .method("getCiecaDocumentFromMitchellEnvelope")
                        .with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                                eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

                // TODO: more specific params.
                mockHandlerUtils.expects(exactly(3)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentReferenceID").withAnyArguments().will(returnValue(REFERENCE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimExposureID").withAnyArguments().will(returnValue(CLAIM_EXPOSURE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimID").withAnyArguments().will(returnValue(CLAIM_ID));

            } else {
                mockHandlerUtils
                        .expects(atLeastOnce())
                        .method("getCiecaDocumentFromMitchellEnvelope")
                        .with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                                eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup



                // TODO: more specific params.
                mockHandlerUtils.expects(exactly(3)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentReferenceID").withAnyArguments().will(returnValue(REFERENCE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimExposureID").withAnyArguments().will(returnValue(CLAIM_EXPOSURE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimID").withAnyArguments().will(returnValue(CLAIM_ID));
            }

            //setupMockSystemPropertiesIsDispatchReportReqdForDispatch(userInfoDocument);
            // TODO: Check case where IsDispatchReport is FALSE
            mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                    .will(returnValue(new File("dr-rep.file")));

            final PutDocResponse putDocResponse = new PutDocResponse();
            putDocResponse.setdocid(1);
            mockDocumentStoreClientProxy.expects(once()).method("putDocument").withAnyArguments()
                    .will(returnValue(putDocResponse));
            final CrossOverUserInfoDocument xOverUserInfo = CrossOverUserInfoDocument.Factory.newInstance();
            xOverUserInfo.addNewCrossOverUserInfo().addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
            xOverUserInfo.getCrossOverUserInfo().addNewReviewerInfo().setReviewerOrgCode("1234");
            final OnlineUserType onlineUsers = xOverUserInfo.getCrossOverUserInfo().addNewOnlineInfo().addNewOnlineOffice()
                    .addNewOnlineUsers();
            final OnlineUserOrgCodeType onlineUserOrgCode = onlineUsers.addNewOnlineUserOrgCode();
            onlineUserOrgCode.setIsPrimaryUser(true);
            onlineUsers.setOnlineUserOrgID(3);
            final OrgInfoDocument orgInfoDocument = OrgInfoDocument.Factory.newInstance();
            OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
            orgInfoType.setOrgCode(REVIEWER_CO_CODE);  //NEW
            orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
            mockUserInfoClient.expects(once()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
            mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                    .will(returnValue(xOverUserInfo));
            final AppLoggingDocument apploggingDocument = AppLoggingDocument.Factory.newInstance();
            final AppLoggingType appLogging = apploggingDocument.addNewAppLogging();

            final Constraint constraints[] = new Constraint[]{new IsInstanceOf(AppLoggingDocument.class),
                    new IsInstanceOf(UserInfoDocument.class), new IsAnything(),
                    new IsEqual(AssignmentDeliveryConstants.APPLICATION_NAME),
                    new IsEqual(AssignmentDeliveryConstants.APPLICATION_NAME), new IsInstanceOf(AppLoggingNVPairs.class)};
            mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
            mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));
        }

        private void registerExpectationsForCompleteDelivery(final UserInfoDocument userInfoDocument, final boolean isSupplement) {
            // Populate Primary contact as claimaint
            ciecaDocument.getCIECA().getAssignmentAddRq().addNewAdminInfo().addNewClaimant().addNewParty().addNewPersonInfo().addNewPersonName().setFirstName("Claimaint");

            if (isSupplement) {
                mockHandlerUtils
                        .expects(once())
                        .method("getCiecaDocumentFromMitchellEnvelope")
                        .with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                                eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

                mockHandlerUtils.expects(once()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();

                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentReferenceID").withAnyArguments().will(returnValue(REFERENCE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimExposureID").withAnyArguments().will(returnValue(CLAIM_EXPOSURE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimID").withAnyArguments().will(returnValue(CLAIM_ID));

            } else {
                mockHandlerUtils
                        .expects(once())
                        .method("getCiecaDocumentFromMitchellEnvelope")
                        .with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
                                eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

                mockHandlerUtils.expects(once()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();

                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentReferenceID").withAnyArguments().will(returnValue(REFERENCE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimExposureID").withAnyArguments().will(returnValue(CLAIM_EXPOSURE_ID));
                mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimID").withAnyArguments().will(returnValue(CLAIM_ID));
            }

            final CrossOverUserInfoDocument xOverUserInfo = CrossOverUserInfoDocument.Factory.newInstance();
            xOverUserInfo.addNewCrossOverUserInfo().addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
            xOverUserInfo.getCrossOverUserInfo().addNewReviewerInfo().setReviewerOrgCode("1234");
            final OnlineUserType onlineUsers = xOverUserInfo.getCrossOverUserInfo().addNewOnlineInfo().addNewOnlineOffice()
                    .addNewOnlineUsers();
            final OnlineUserOrgCodeType onlineUserOrgCode = onlineUsers.addNewOnlineUserOrgCode();
            onlineUserOrgCode.setIsPrimaryUser(true);
            onlineUsers.setOnlineUserOrgID(3);
            final OrgInfoDocument orgInfoDocument = OrgInfoDocument.Factory.newInstance();
            OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
            orgInfoType.setOrgCode(REVIEWER_CO_CODE);
            orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
            // mockUserInfoClient.expects(atLeastOnce()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
            mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                    .will(returnValue(xOverUserInfo));
            mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        }

        private void registerExpectationsForCancelDelivery(final UserInfoDocument userInfoDocument) {
            mockHandlerUtils.expects(atLeastOnce()).method("getCiecaDocumentFromMitchellEnvelope").withAnyArguments()
                    .will(returnValue(ciecaDocument));// TODO: setup more  specfic params.

            mockHandlerUtils.expects(once()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();

            //setupMockSystemPropertiesIsDispatchReportReqdForCancel(userInfoDocument);
            // TODO: Check case where IsDispatchReport is FALSE
            mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                    .will(returnValue(new File("dr-rep.file")));
            final PutDocResponse putDocResponse = new PutDocResponse();
            putDocResponse.setdocid(1);
            mockDocumentStoreClientProxy.expects(once()).method("putDocument").withAnyArguments()
                    .will(returnValue(putDocResponse));

            final CrossOverUserInfoDocument xOverUserInfoDocument = CrossOverUserInfoDocument.Factory.newInstance();
            final CrossOverUserType xOverUserInfo = xOverUserInfoDocument.addNewCrossOverUserInfo();
            xOverUserInfo.addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
            xOverUserInfo.addNewReviewerInfo().setReviewerOrgCode("1234");
            final OnlineUserType onlineUser = xOverUserInfo.addNewOnlineInfo().addNewOnlineOffice().addNewOnlineUsers();
            onlineUser.setOnlineUserOrgID(1234);
            final OnlineUserOrgCodeType onlineUserOrgCode = onlineUser.addNewOnlineUserOrgCode();
            onlineUserOrgCode.setIsPrimaryUser(true);
            final OrgInfoDocument orgInfoDocument = OrgInfoDocument.Factory.newInstance();
            OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
            orgInfoType.setOrgCode(REVIEWER_CO_CODE);
            orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
            mockUserInfoClient.expects(once()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
            mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                    .will(returnValue(xOverUserInfoDocument));
            mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
            mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));
            mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentReferenceID").withAnyArguments().will(returnValue(REFERENCE_ID));
            mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimExposureID").withAnyArguments().will(returnValue(CLAIM_EXPOSURE_ID));
            mockWorkAssignmentProxy.expects(exactly(1)).method("getWorkAssignmentClaimID").withAnyArguments().will(returnValue(CLAIM_ID));
        }

        /*private void setupMockSystemPropertiesIsDispatchReportReqdForDispatch(final UserInfoDocument userInfoDocument) {
            mockAssignmentDeliveryUtils
                    .expects(once())
                    .method("getUserCustomSetting")
                    .with(new IsUserInfoCorrectForDispatch(),
                            eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
                    .will(returnValue("true"));
            // TODO:  // Check ARC7Handler
            // .with(eq(userInfoDocument), eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
        }*/

        /*private void setupMockSystemPropertiesIsDispatchReportReqdForCancel(final UserInfoDocument userInfoDocument) {
            mockAssignmentDeliveryUtils
                    .expects(once())
                    .method("getUserCustomSetting")
                    .with(new IsUserInfoCorrectForCancel(),
                            eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
                    .will(returnValue("true"));
        }*/

        private UserInfoDocument buildUserInfoDocument() {
            final UserInfoDocument userInfoDocument = new MockUserInfoDocumentType();
            final MockUserInfo userInfo = new MockUserInfo();
            userInfo.setUserID(USER_ID);
            userInfo.setOrgID("PK");
            userInfoDocument.setUserInfo(userInfo);
            userInfo.setAppCodeArray(new String[]{AssignmentDeliveryConstants.APPL_ACCESS_CODE_ECLAIM_MANAGER});  //TODO: FixME
            return userInfoDocument;
        }

        private Constraint acceptableInboundMeDoc() {
            return new Constraint() {

                public StringBuffer describeTo(final StringBuffer buffer) {
                    return buffer.append("Valid MEDoc for handler utils");
                }

                public boolean eval(final Object o) {
                    final MitchellEnvelopeDocument mitchellEnvelopeDocument = (MitchellEnvelopeDocument) o;
                    assertNotNull("Mitchell envelope document is null.", mitchellEnvelopeDocument);
                    assertNotNull("Mitchell envelope  is null.", mitchellEnvelopeDocument.getMitchellEnvelope());
                    final MitchellEnvelopeHelper meh = new MitchellEnvelopeHelper(mitchellEnvelopeDocument);
                    assertNotNull("No CIECABMS attached to the envelope document",
                            meh.getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER));
                    return true;
                }
            };
        }

        private InvocationMatcher twice() {
            return exactly(2);
        }

        private void assertPayloadSanity(final APDDeliveryContextDocument nonDrpAPDDeliveryContext) {
            assertNotNull(nonDrpAPDDeliveryContext);
            assertNotNull(nonDrpAPDDeliveryContext.getAPDDeliveryContext());
            assertNotNull(nonDrpAPDDeliveryContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo());
            assertNotNull(nonDrpAPDDeliveryContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
                    .getAPDCommonInfo());
            final MitchellEnvelopeType assignmentMitchellEnvelope = nonDrpAPDDeliveryContext.getAPDDeliveryContext()
                    .getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope();
            final MitchellEnvelopeHelper meh = MitchellEnvelopeHelper.newInstance();
            meh.getDoc().setMitchellEnvelope(assignmentMitchellEnvelope);
        }

        private MitchellEnvelopeHelper synthesizeMwmHelper() throws XmlException {
            final MitchellWorkflowMessageDocument payload = messagePostingAgent.getPayload();
            assertNotNull(payload);
            assertNotNull(payload.getMitchellWorkflowMessage());
            assertNotNull(payload.getMitchellWorkflowMessage().getTrackingInfo());
            assertNotNull(payload.getMitchellWorkflowMessage().getData());
            assertEquals(171602, payload.getMitchellWorkflowMessage().getData().getType());
            final MitchellEnvelopeDocument envelope = MitchellEnvelopeDocument.Factory.parse(payload
                    .getMitchellWorkflowMessage().getData().toString());
            final MitchellEnvelopeHelper helper = new MitchellEnvelopeHelper(envelope);
            return helper;
        }

        private final class IsUserInfoCorrectForDispatch implements Constraint {
            public StringBuffer describeTo(StringBuffer buffer) {
                return buffer.append("Check userinfo document");
            }

            final public boolean eval(final Object o) {
                if (o instanceof UserInfoDocument) {
                    UserInfoDocument that = (UserInfoDocument) o;
                    UserInfoType userInfo = that.getUserInfo();
                    return userInfo.getUserID().equals(TARGET_USER_FOR_DISPATCH) && userInfo.getOrgID().equals(TEST_ORG);
                }
                return true;
            }
        }

        private final class IsUserInfoCorrectForCancel implements Constraint {
            public StringBuffer describeTo(StringBuffer buffer) {
                return buffer.append("Check userinfo document");
            }

            final public boolean eval(final Object o) {
                if (o instanceof UserInfoDocument) {
                    UserInfoDocument that = (UserInfoDocument) o;
                    UserInfoType userInfo = that.getUserInfo();
                    return userInfo.getUserID().equals(TARGET_USER_FOR_CANCEL) && userInfo.getOrgID().equals(TEST_ORG);
                }
                return true;
            }
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
        private Mock mockWorkAssignmentProxy;
        private Mock mockDispatchReportBuilder;
        private Mock mockErrorLoggingServiceWrapper;
        private Mock mockHandlerUtils;
        private Mock mockDocumentStoreClientProxy;
        private MockMessagePostingAgent messagePostingAgent;
        private MockAssignmentDeliveryConfigBridge assignmentDeliveryConfigBridge;
        private Mock mockUserInfoClient;
        private Mock mockBmsConverterFactory;

    }
}
