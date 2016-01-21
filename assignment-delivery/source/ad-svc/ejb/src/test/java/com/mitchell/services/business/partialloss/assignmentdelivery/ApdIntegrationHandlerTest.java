package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.File;
import java.util.Calendar;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.jmock.core.InvocationMatcher;
import org.jmock.core.constraint.IsAnything;
import org.jmock.core.constraint.IsEqual;
import org.jmock.core.constraint.IsInstanceOf;

import  org.apache.xmlbeans.XmlException;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoDocument;
import com.mitchell.common.types.AttachmentItemType;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.CrossOverUserType;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.NameValuePairType;
import com.mitchell.common.types.OnlineUserOrgCodeType;
import com.mitchell.common.types.OnlineUserType;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.OrgInfoType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.ActivityStatusType;
import com.mitchell.schemas.EnvelopeBodyContentType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.EnvelopeContextType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
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
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.AbstractMsgBusDelHndlr;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.ApdIntAssDelHandlerImpl;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.workprocess.WorkProcessServiceClientAPI;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Unit test for
 * {@link com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.ApdIntAssDelHandlerImpl
 * ApdIntAssDelHandlerImpl}
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Aug 31, 2010
 */
public class ApdIntegrationHandlerTest extends MockObjectTestCase {
    
	private static final String CLIENT_CLAIM_NUMBER = "ClientClaim#";
	private static final String DRP_ORG = "Drp Org !!!";
    private static final String TARGET_USER_FOR_DISPATCH = "TargetUserForDispatch";
    private static final String MY_TEST_ORG = "My test ORG";
    private static final long CLAIM_ID = -4141592654L;	
    private static final int REVIEWER_ORG_ID = 1234;
    private static final String REVIEWER_CO_CODE = "IF";
    private static final String NON_DRP_ORG_FOR_DRP = "4";
    private static final String TEST_ORG = "1";
    private static final String TEST_DRP_ORG = "2";
    private static final long TASK_ID = -3141592654L;
    private static final String WORK_ITEM_ID = "work-1234";
    private static final String DRP_ORG_USER = "DRP Org user, woo hoo!!!";
    private static final String TARGET_USER_FOR_CANCEL = "TargetUserForCancel";
	private static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
	private static final String LAST_MODIFIED_DATE_TIME_VALUE = "2013-03-26T03:22:16.932-07:00";
    private ApdIntAssDelHandlerImpl target;
    private CIECADocument ciecaDocument;

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
    
    private void addLastModifiedDateTimeInNVPair(APDDeliveryContextType apdDeliveryContext) {
    	final MitchellEnvelopeType mitchellEnvelope = apdDeliveryContext.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope();
    	final EnvelopeContextType  envelopeContext = mitchellEnvelope.addNewEnvelopeContext();
    	final com.mitchell.schemas.NameValuePairType nameValuePairType = envelopeContext.addNewNameValuePair();
    	nameValuePairType.setName(LAST_MODIFIED_DATE_TIME);
    	nameValuePairType.setValueArray(new String [] {"2013-03-26T03:22:16.932-07:00"});
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

    protected void setUp() throws Exception {
        super.setUp();
        target = new ApdIntAssDelHandlerImpl();
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
        mockWorkProcessServiceClient = mock(WorkProcessServiceClientAPI.class);
        mockUserInfoClient = mock(UserInfoServiceEJBRemote.class);
        mockBmsConverterFactory = mock(BmsConverterFactory.class);
        // Set dependencies
        target.setDrBuilder((AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
        target.setLogger(new NoOpAssignmentDeliveryLogger("No-op", null));
        target.setAssignmentDeliveryUtils((AbstractAssignmentDeliveryUtils) mockAssignmentDeliveryUtils.proxy());
        target.setConverter((Converter) mockConverter.proxy());
        target.setDrBuilder((AbstractDispatchReportBuilder) mockDispatchReportBuilder.proxy());
        target.setErrorLoggingService((ErrorLoggingServiceWrapper) mockErrorLoggingServiceWrapper.proxy());
        target.setHandlerUtils((AbstractHandlerUtils) mockHandlerUtils.proxy());
        target.setLogger(new NoOpAssignmentDeliveryLogger("test", null));
        target.setAppLoggerBridge((AppLoggerBridge) mockAppLoggerBridge.proxy());
        target.setAssignmentDeliveryConfigBridge(assignmentDeliveryConfigBridge);
        target.setHandlerUtils((AbstractHandlerUtils) mockHandlerUtils.proxy());
        target.setDocumentStoreClientProxy((DocStoreClientProxy) mockDocumentStoreClientProxy.proxy());
        target.setMessagePostingAgent(messagePostingAgent);
        target.setUserInfoClient((UserInfoServiceEJBRemote) mockUserInfoClient.proxy());
        target.setWorkServiceClient((WorkProcessServiceClientAPI) mockWorkProcessServiceClient.proxy());
        target.setBmsConverterFactory((BmsConverterFactory) mockBmsConverterFactory.proxy());
//
    }

    public void testDependenciesAreGuarded() throws Exception {
        target = new ApdIntAssDelHandlerImpl();
        try {
            System.out
                    .println("The line \" No logger has been set. Exting \" following this staement is expected as part of a successful test");
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
                            + "Document Store Proxy, Message Posting Agent, Work Process Service Client, App Logger Bridge, Error Logging Service, User Info Client]",
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
            assertEquals("Only SUPPLEMENT and ORGINAL_ESTIMATE messages are supported, got[OTHER]", expected.getCause()
                    .getMessage());
        }
    }

    /**
     * Test RC/WC integration for Staff Dispatch.
     * 
     * @throws Exception
     */
    public void testDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(userInfoDocument, false);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, false);
        assertCiecaBody(helper, false);
        assertAttachments(helper, status, false);
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", false);
    }
    
    /**
     * Test RC/WC integration for Shop Complete
     * 
     * @throws Exception
     */
    public void testComplete() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument apdDeliveryContext;
        apdDeliveryContext = initializeCompleteAPDDeliveryContext(false);
        assertPayloadSanity(apdDeliveryContext);
        registerExpectationsForCompleteDelivery(userInfoDocument, false);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(apdDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "COMPLETED";
        assertNameValuePairsForComplete(helper, status, false);
        assertCiecaBodyIsNULL(helper);
        assertAttachmentsIsNULL(helper);
        assertWorkProcessCompleteUpdateMessage(helper, REVIEWER_CO_CODE + "");
    }
    
    /**
     * Test No public key exception for cancel and complete
     * 
     * @throws Exception
     */
    public void testNoPublicKeyException() throws Exception {
    	// COMPLETE
        APDDeliveryContextDocument apdDeliveryContext;
        apdDeliveryContext = initializeCompleteAPDDeliveryContext(false);
        assertPayloadSanity(apdDeliveryContext);
        registerExpectationsForCompleteNoPublicKey();
        assertNotNull("Logger is null", target.getLogger());
        try {
        	target.deliverAssignment(apdDeliveryContext);
        } catch (MitchellException exception) {
        	MitchellException cause = (MitchellException) exception.getCause();
        	assertEquals("Wrong Exception caught", "Error retrieving public work process key, CompanyCode: [ IF ], ClaimNumber: [ ClientClaim# ].",cause.getDescription());
        }
        try {
        	apdDeliveryContext.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().setMessageStatus("CANCEL");
        	target.deliverAssignment(apdDeliveryContext);
        } catch (MitchellException exception) {
        	MitchellException cause = (MitchellException) exception.getCause();
        	assertEquals("Wrong Exception caught", "Error retrieving public work process key, CompanyCode: [ IF ], ClaimNumber: [ ClientClaim# ].",cause.getDescription());        	
        }
    }
    /**
     * Test RC/WC integration for Shop Supplement Complete
     * 
     * @throws Exception
     */
    public void testSupplementComplete() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument apdDeliveryContext;
        apdDeliveryContext = initializeCompleteAPDDeliveryContext(true);
        assertPayloadSanity(apdDeliveryContext);
        registerExpectationsForCompleteDelivery(userInfoDocument, true);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(apdDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "COMPLETED";
        assertNameValuePairsForComplete(helper, status, true);
        assertCiecaBodyIsNULL(helper);
        assertAttachmentsIsNULL(helper);
        assertWorkProcessCompleteUpdateMessage(helper, REVIEWER_CO_CODE + "");
    }
    /**
     * Test RC/WC integration for Supplement Dispatch.
     * 
     * @throws Exception
     */
    public void testSupplementDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(true);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(userInfoDocument, true);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, true);
        assertCiecaBody(helper, false);
        assertAttachments(helper, status, false);
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    /**
     * Test RC/WC integration for ReDispatch.
     * 
     * @throws Exception
     */
    public void testReDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForReDispatchDelivery(userInfoDocument, false);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, true, false);
        assertCiecaBody(helper, true);
        assertAttachments(helper, status, true);
        assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    /**
     * Test RC/WC integration for Supplement ReDispatch.
     * 
     * @throws Exception
     */
    public void testSupplementReDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDispatchAPDDeliveryContext(true);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForReDispatchDelivery(userInfoDocument, true);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, true, true);
        assertCiecaBody(helper, true);
        // assertAttachments(helper, status, true);
        //ORG assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_ORG_ID + "", true);
        assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    /**
     * @param helper
     * @param status
     * @param isSupplement
     *            TODO
     */
    private void assertNameValuePairsForDispatch(final MitchellEnvelopeHelper helper, final String status,
            final boolean isReDispatch, final boolean isSupplement) {
        final long NOW = Calendar.getInstance().getTimeInMillis();
        final long FIVE_SECOND_AGO = (NOW - 5000);
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

    public void testDrpDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDrpDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(userInfoDocument, false);
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, false);
        assertCiecaBody(helper, false);
        assertAttachments(helper, status, false);
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    public void testSupplementDrpDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDrpDispatchAPDDeliveryContext(true);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForDispatchDelivery(userInfoDocument, true);
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, false, true);
        assertCiecaBody(helper, false);
        assertAttachments(helper, status, false);
        assertWorkProcessInitiationMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    public void testDrpReDispatch() throws Exception {
        final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument nonDrpAPDDeliveryContext;
        nonDrpAPDDeliveryContext = initializeDrpDispatchAPDDeliveryContext(false);
        assertPayloadSanity(nonDrpAPDDeliveryContext);
        registerExpectationsForReDispatchDelivery(userInfoDocument, false);
        target.deliverAssignment(nonDrpAPDDeliveryContext);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "DISPATCHED";
        assertNameValuePairsForDispatch(helper, status, true, false);
        assertCiecaBody(helper, true);
        assertAttachments(helper, status, true);
        assertWorkProcessRedispatchUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    /**
     * Test Staff cancel for RC/WC integration
     * 
     * @throws Exception
     */
    public void testCancel() throws Exception {
    	final UserInfoDocument userInfoDocument = buildUserInfoDocument();
    	APDDeliveryContextDocument context;
        context = initializeCancelAPDDeliveryContext(false);
        assertPayloadSanity(context);
        registerExpectationsForCancelDelivery(userInfoDocument);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(context);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "CANCELLED";
        assertNameValuePairsForCancel(helper, status, false);
        assertCiecaBody(helper, false);
        assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    /**
     * Test Staff cancel for RC/WC integration
     * 
     * @throws Exception
     */
    public void testCancelSupplement() throws Exception {
    	final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument context;
        context = initializeCancelAPDDeliveryContext(true);
        assertPayloadSanity(context);
        registerExpectationsForCancelDelivery(userInfoDocument);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(context);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "CANCELLED";
        assertNameValuePairsForCancel(helper, status, true);
        assertCiecaBody(helper, false);
        assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", true);
    }

    /**
     * Test Shop cancel for RC/WC integration
     * 
     * @throws Exception
     */
    public void testDrpCancel() throws Exception {
    	final UserInfoDocument userInfoDocument = buildUserInfoDocument();
        APDDeliveryContextDocument context;
        context = initializeDrpCancelAPDDeliveryContext(false);
        assertPayloadSanity(context);
        registerExpectationsForCancelDelivery(userInfoDocument);
        assertNotNull("Logger is null", target.getLogger());
        target.deliverAssignment(context);
        final MitchellEnvelopeHelper helper = synthesizeMwmHelper();
        // Confirm name value pairs
        final String status = "CANCELLED";
        assertNameValuePairsForCancel(helper, status, false);
        assertCiecaBody(helper, false);
        assertWorkProcessCancelUpdateMessage(helper, REVIEWER_CO_CODE + "", false);
    }

    private void assertWorkProcessCancelUpdateMessage(final MitchellEnvelopeHelper helper, final String organization,
            final boolean isSupplement) throws XmlException {
        final EnvelopeBodyType workProcessUpdateMessageBody = helper.getEnvelopeBody("WorkProcessUpdateMessage");
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getIdentifier());
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getMitchellDocumentType());
        assertEquals("com.mitchell.schemas.WorkProcessUpdateMessageDocument", workProcessUpdateMessageBody
                .getMetadata().getXmlBeanClassname());
        final WorkProcessUpdateMessageDocument workProcessUpdateMessageDocument = WorkProcessUpdateMessageDocument.Factory
        .parse(workProcessUpdateMessageBody.getContent().toString());
        final WorkProcessUpdateMessage workProcessUpdateMessage = workProcessUpdateMessageDocument.getWorkProcessUpdateMessage();
        assertEquals(ActivityStatusType.BEGIN, workProcessUpdateMessage.getActivityStatus());
        assertEquals("Cancel activity operation does not match", isSupplement ? "CANCEL_SUPPLEMENT_ASSIGNMENT"
                : "CANCEL_APPRAISAL_ASSIGNMENT", workProcessUpdateMessage.getActivityOperation());
        assertEquals("Cancel Update Colaborators do not match" + "[" + organization + "]" + "["
                + workProcessUpdateMessage.getCollaborator() + "]", organization,
                workProcessUpdateMessage.getCollaborator());
        assertNotNull(workProcessUpdateMessage.getPrivateIndex());
        assertNotNull(workProcessUpdateMessage.getPublicIndex());
    }
    private void assertWorkProcessCompleteUpdateMessage(final MitchellEnvelopeHelper helper, final String organization) throws XmlException {
        final EnvelopeBodyType workProcessUpdateMessageBody = helper.getEnvelopeBody("WorkProcessUpdateMessage");
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getIdentifier());
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getMitchellDocumentType());
        assertEquals("com.mitchell.schemas.WorkProcessUpdateMessageDocument", workProcessUpdateMessageBody
                .getMetadata().getXmlBeanClassname());
        final WorkProcessUpdateMessageDocument workProcessUpdateMessageDocument = WorkProcessUpdateMessageDocument.Factory
        .parse(workProcessUpdateMessageBody.getContent().toString());
        final WorkProcessUpdateMessage workProcessUpdateMessage = workProcessUpdateMessageDocument.getWorkProcessUpdateMessage();
        assertEquals(ActivityStatusType.BEGIN, workProcessUpdateMessage.getActivityStatus());
        assertEquals("Complete activity operation does not match", "COMPLETE_APPRAISAL_ASSIGNMENT", workProcessUpdateMessage.getActivityOperation());
        assertEquals("Complete Update Colaborators do not match" + "[" + organization + "]" + "["
                + workProcessUpdateMessage.getCollaborator() + "]", organization,
                workProcessUpdateMessage.getCollaborator());
        assertNotNull(workProcessUpdateMessage.getPrivateIndex());
        assertNotNull(workProcessUpdateMessage.getPublicIndex());
    }
    
    private void assertWorkProcessRedispatchUpdateMessage(final MitchellEnvelopeHelper helper,
            final String organization, final boolean isSupplement) throws XmlException {
        final EnvelopeBodyType workProcessUpdateMessageBody = helper.getEnvelopeBody("WorkProcessUpdateMessage");
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getIdentifier());
        assertEquals("WorkProcessUpdateMessage", workProcessUpdateMessageBody.getMetadata().getMitchellDocumentType());
        assertEquals("com.mitchell.schemas.WorkProcessUpdateMessageDocument", workProcessUpdateMessageBody
                .getMetadata().getXmlBeanClassname());
        final WorkProcessUpdateMessageDocument workProcessUpdateMessageDocument = WorkProcessUpdateMessageDocument.Factory
                .parse(workProcessUpdateMessageBody.getContent().toString());
        final WorkProcessUpdateMessage workProcessUpdateMessage = workProcessUpdateMessageDocument.getWorkProcessUpdateMessage();
		assertEquals(ActivityStatusType.BEGIN, workProcessUpdateMessage.getActivityStatus());
        assertEquals(isSupplement ? "UPDATE_SUPPLEMENT_ASSIGNMENT" : "UPDATE_APPRAISAL_ASSIGNMENT",
                workProcessUpdateMessage.getActivityOperation());
        assertEquals(
                "Colaborators do not match" + "[" + organization + "]" + "["
                        + workProcessUpdateMessage.getCollaborator() + "]", organization,
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
    /**
     * @param helper
     * @param status
     * @param isSupplement
     *            TODO
     */
    private void assertNameValuePairsForComplete(final MitchellEnvelopeHelper helper, final String status,
            final boolean isSupplement) {

        assertEquals(isSupplement ? "SUPPLEMENT" : "ORIGINAL_ESTIMATE",
                helper.getEnvelopeContextNVPairValue("MessageType"));
        assertEquals(status, helper.getEnvelopeContextNVPairValue("MessageStatus"));
        assertEquals(REVIEWER_CO_CODE, helper.getEnvelopeContextNVPairValue("MitchellCompanyCode"));
        assertEquals(CLIENT_CLAIM_NUMBER, helper.getEnvelopeContextNVPairValue("ClaimNumber"));
        assertEquals(WORK_ITEM_ID, helper.getEnvelopeContextNVPairValue("MitchellWorkItemId"));
        assertEquals(""+TASK_ID, helper.getEnvelopeContextNVPairValue("TaskId"));
        assertNull(helper.getEnvelopeContextNVPairValue("PassThroughInfo"));
        assertEquals(MY_TEST_ORG, helper.getEnvelopeContextNVPairValue("ReviewerCoCd"));
        assertEquals("3", helper.getEnvelopeContextNVPairValue("ShopId"));
        assertEquals(TARGET_USER_FOR_DISPATCH, helper.getEnvelopeContextNVPairValue("ReviewerId"));
        assertEquals(DRP_ORG, helper.getEnvelopeContextNVPairValue("UserCoCd"));
        assertEquals(DRP_ORG_USER, helper.getEnvelopeContextNVPairValue("UserId"));
        assertEquals(AbstractMsgBusDelHndlr.PRIMARY_CONTACT_TYPE_CLAIMNT, helper.getEnvelopeContextNVPairValue("PrimaryContactType"));
    }
    /**
     * @param helper
     * @throws XmlException
     */
    private void assertWorkProcessInitiationMessage(final MitchellEnvelopeHelper helper, final String organization,
            final boolean isSupplement) throws XmlException {
        final EnvelopeBodyType workProcessInitiationMessageBody = helper
                .getEnvelopeBody("WorkProcessInitiationMessage");
        assertEquals("WorkProcessInitiationMessage", workProcessInitiationMessageBody.getMetadata().getIdentifier());
        assertEquals("WorkProcessInitiationMessage", workProcessInitiationMessageBody.getMetadata()
                .getMitchellDocumentType());
        assertEquals("com.mitchell.schemas.WorkProcessInitiationMessageDocument", workProcessInitiationMessageBody
                .getMetadata().getXmlBeanClassname());
        final WorkProcessInitiationMessageDocument workProcessInitiationMessageDocument = WorkProcessInitiationMessageDocument.Factory
                .parse(workProcessInitiationMessageBody.getContent().toString());
        final WorkProcessInitiationMessage workProcessInitializationMessage = workProcessInitiationMessageDocument
                .getWorkProcessInitiationMessage();
        assertNotNull(workProcessInitializationMessage);
        assertEquals("Expected organization does not match actual work process colaborator", organization,
                workProcessInitializationMessage.getWorkProcessCollaborator());  //TODO Compare returning NULL -- WHY??
        if (!isSupplement) {
            assertEquals("AppraisalAssignment", workProcessInitializationMessage.getDefinition().getType());
        } else {
            assertEquals("SupplementAssignment", workProcessInitializationMessage.getDefinition().getType());
        }
        assertEquals("Work process message version does not match", "1.0", workProcessInitializationMessage
                .getDefinition().getVersion());
        assertEquals("CARRIER", workProcessInitializationMessage.getRoleMapping().getRoleCollaboratorPairArray(0)
                .getRole());
        assertEquals("Expected organization does not match actual role colaborator", organization,
                workProcessInitializationMessage.getRoleMapping().getRoleCollaboratorPairArray(0).getCollaborator());
        assertEquals("SHOP", workProcessInitializationMessage.getRoleMapping().getRoleCollaboratorPairArray(1)
                .getRole());
        assertNotNull(workProcessInitializationMessage.getPrivateIndex());
        assertNotNull(workProcessInitializationMessage.getPublicIndex());
    }

    /**
     * @param helper
     * @param isUpdate
     *            TODO
     * @param NOW
     * @param FIVE_SECOND_AGO
     * @throws XmlException
     */
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
    /**
     * @param helper
     * @param isUpdate
     *            TODO
     * @return
     * @throws XmlException
     */
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
     * @param isUpdate
     *            TODO
     * @throws XmlException
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
    /**
     * @param helper
     * @param isUpdate
     *            TODO
     * @throws XmlException
     */
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
            mockHandlerUtils.expects(exactly(4)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();

    	} else {
    		mockHandlerUtils
    		.expects(atLeastOnce())
    		.method("getCiecaDocumentFromMitchellEnvelope")
    		.with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
    				eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

			// TODO: more specific params. 
            mockHandlerUtils.expects(exactly(4)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
    	}

        setupMockSystemPropertiesIsDispatchReportReqdForDispatch(userInfoDocument);
        // TODO: Check case where IsDispatchReport is FALSE
        mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                .will(returnValue(new File("dr-rep.file")));
        
        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once()).method("putDocument").withAnyArguments()
                .will(returnValue(putDocResponse));
        final WPKeyRequestDocument request = WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(exactly(3)).method("initWPKeyRequest").withAnyArguments()
                .will(returnValue(request));// 3

        // mockWorkProcessServiceClient.expects(exactly(2)).method("retrieveWorkProcessKey").withAnyArguments().will(returnValue(null));
        mockWorkProcessServiceClient.expects(once()).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() }).will(returnValue(null));
        mockWorkProcessServiceClient.expects(twice()).method("retrieveWorkProcessKey")// 2
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() }).will(returnValue(null));// 2
        mockWorkProcessServiceClient.expects(twice()).method("registerWorkProcessKey").withAnyArguments();
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
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                .will(returnValue(xOverUserInfo));
        final AppLoggingDocument apploggingDocument = AppLoggingDocument.Factory.newInstance();
        final AppLoggingType appLogging = apploggingDocument.addNewAppLogging();

        final Constraint constraints[] = new Constraint[] { new IsInstanceOf(AppLoggingDocument.class),
                new IsInstanceOf(UserInfoDocument.class), new IsAnything(),
                new IsEqual(AssignmentDeliveryConstants.APPLICATION_NAME),
                new IsEqual(AssignmentDeliveryConstants.APPLICATION_NAME), new IsInstanceOf(AppLoggingNVPairs.class) };
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        setUpShopNotificationForSupplement(isSupplement);
        mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));
    }

	private void registerExpectationsForCompleteNoPublicKey() {
		final WPKeyRequestDocument request = WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(exactly(2)).method("initWPKeyRequest").withAnyArguments()
                .will(returnValue(request));
        mockWorkProcessServiceClient.expects(exactly(2)).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() }).will(returnValue(null));
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

            mockHandlerUtils.expects(twice()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();

    	} else {
    		mockHandlerUtils
    		.expects(once())
    		.method("getCiecaDocumentFromMitchellEnvelope")
    		.with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
    				eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

            mockHandlerUtils.expects(twice()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
    	}

        
        final WPKeyRequestDocument request = WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(exactly(2)).method("initWPKeyRequest").withAnyArguments()
                .will(returnValue(request));

        
        mockWorkProcessServiceClient.expects(exactly(1)).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() }).will(returnValue("123"));
       
       
        mockWorkProcessServiceClient.expects(exactly(1)).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() }).will(returnValue("22"));
       
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
        mockUserInfoClient.expects(atLeastOnce()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                .will(returnValue(xOverUserInfo));
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
    }
	/**
	 * @param isSupplement
	 */
	protected void setUpShopNotificationForSupplement(final boolean isSupplement) {
		if ( isSupplement){
			//TODO Add arguments
        	mockHandlerUtils.expects(once()).method("sendNonStaffSuppNotification").withAnyArguments();
        }
	}

    private void setupMockSystemPropertiesIsDispatchReportReqdForDispatch(final UserInfoDocument userInfoDocument) {
        mockAssignmentDeliveryUtils
                .expects(once())
                .method("getUserCustomSetting")
				.with(new IsUserInfoCorrectForDispatch(),
						eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
                .will(returnValue("true"));
        	// TODO:  // Check ARC7Handler
            // .with(eq(userInfoDocument), eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
    }

    private void setupMockSystemPropertiesIsDispatchReportReqdForCancel(final UserInfoDocument userInfoDocument) {
        mockAssignmentDeliveryUtils
                .expects(once())
                .method("getUserCustomSetting")
				.with(new IsUserInfoCorrectForCancel(),
						eq(AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG))
                .will(returnValue("true"));
    }
    
    private UserInfoDocument buildUserInfoDocument() {
        final UserInfoDocument userInfoDocument = new MockUserInfoDocumentType();
        final MockUserInfo userInfo = new MockUserInfo();
        userInfo.setUserID(USER_ID);
        userInfo.setOrgID("PK");
        userInfoDocument.setUserInfo(userInfo);
        userInfo.setAppCodeArray(new String[]{AssignmentDeliveryConstants.APPL_ACCESS_CODE_ECLAIM_MANAGER});  //TODO: FixME
        return userInfoDocument;
    }
    
    private void registerExpectationsForReDispatchDelivery(final UserInfoDocument userInfoDocument, final boolean isSupplement) {
    	if (isSupplement) {
    		mockHandlerUtils
    		.expects(twice())
    		.method("getCiecaDocumentFromMitchellEnvelope")
    		.with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
    				eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

    		// TODO: more specific params. 
            mockHandlerUtils.expects(exactly(4)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
    	} else {
    		mockHandlerUtils
    		.expects(atLeastOnce())
    		.method("getCiecaDocumentFromMitchellEnvelope")
    		.with(acceptableInboundMeDoc(), eq(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER),
    				eq(WORK_ITEM_ID)).will(returnValue(ciecaDocument));// TODO: setup

    		// TODO: more specific params. 
            mockHandlerUtils.expects(exactly(4)).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
    	}

        setupMockSystemPropertiesIsDispatchReportReqdForDispatch(userInfoDocument);              
        // TODO: Check case where IsDispatchReport is FALSE
        mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                .will(returnValue(new File("dr-rep.file")));
       
        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once()).method("putDocument").withAnyArguments()
                .will(returnValue(putDocResponse));
        final WPKeyRequestDocument request = WPKeyRequestDocument.Factory.newInstance();
        request.addNewWPKeyRequest();
        mockWorkProcessServiceClient.expects(twice()).method("initWPKeyRequest").withAnyArguments()
                .will(returnValue(request));// 3

        // This is different than dispatch. A key is returned.
        mockWorkProcessServiceClient.expects(once()).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() }).will(returnValue("Iamdoc,DocIam"));
        mockWorkProcessServiceClient.expects(once()).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() }).will(returnValue("Iamdoc,DocIam"));
        final CrossOverUserInfoDocument xOverUserInfo = CrossOverUserInfoDocument.Factory.newInstance();
        xOverUserInfo.addNewCrossOverUserInfo().addNewNonStaffInfo().setNonStaffCompanyCode("My shop");
        xOverUserInfo.getCrossOverUserInfo().addNewReviewerInfo().setReviewerOrgCode("1234");
        final OnlineUserType onlineUsers = xOverUserInfo.getCrossOverUserInfo().addNewOnlineInfo().addNewOnlineOffice()
                .addNewOnlineUsers();
        final OnlineUserOrgCodeType onlineUserOrgCode = onlineUsers.addNewOnlineUserOrgCode();
        onlineUserOrgCode.setIsPrimaryUser(true);
        onlineUsers.setOnlineUserOrgID(3);
        final OrgInfoDocument orgInfoDocument = OrgInfoDocument.Factory.newInstance();
        //orgInfoDocument.addNewOrgInfo().setOrgCode(REVIEWER_CO_CODE);  //NEW
        OrgInfoType orgInfoType = orgInfoDocument.addNewOrgInfo();
        orgInfoType.setOrgCode(REVIEWER_CO_CODE);  //NEW
        orgInfoType.setCompanyCode(REVIEWER_CO_CODE);
        //orgInfoDocument.addNewOrgInfo().setOrgID(REVIEWER_ORG_ID);
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                .will(returnValue(xOverUserInfo));
        mockAppLoggerBridge.expects(once()).method("logEvent").withAnyArguments();
        setUpShopNotificationForSupplement(isSupplement);
        mockBmsConverterFactory.expects(once()).method("createCiecaBmsConvertor").withAnyArguments().will(returnValue(new TestBmsConvertor()));        
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

    private void registerExpectationsForCancelDelivery(final UserInfoDocument userInfoDocument) {
        mockHandlerUtils.expects(atLeastOnce()).method("getCiecaDocumentFromMitchellEnvelope").withAnyArguments()
                .will(returnValue(ciecaDocument));// TODO: setup more  specfic params.

        mockHandlerUtils.expects(twice()).method("getAAAInfoDocFromMitchellEnv").withAnyArguments();
        
        setupMockSystemPropertiesIsDispatchReportReqdForCancel(userInfoDocument);
        // TODO: Check case where IsDispatchReport is FALSE
        mockDispatchReportBuilder.expects(once()).method("createDispatchReport").withAnyArguments()
                .will(returnValue(new File("dr-rep.file")));
        final PutDocResponse putDocResponse = new PutDocResponse();
        putDocResponse.setdocid(1);
        mockDocumentStoreClientProxy.expects(once()).method("putDocument").withAnyArguments()
                .will(returnValue(putDocResponse));

        final WPKeyRequestDocument wprDoc = WPKeyRequestDocument.Factory.newInstance();
        wprDoc.addNewWPKeyRequest().setContextIdentifier("APD");
        mockWorkProcessServiceClient.expects(once()).method("initWPKeyRequest")
                .with(eq("CTX_PUBLIC_CLIENT_CLAIM_NUMBER")).will(returnValue(wprDoc));
        mockWorkProcessServiceClient.expects(twice()).method("initWPKeyRequest")
                .with(eq("CTX_PRIVATE_APPRAISAL_ASSIGNMENT")).will(returnValue(wprDoc));
        mockWorkProcessServiceClient.expects(once()).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() }).will(returnValue("Iamdoc,DocIam"))
                .id("Private key call");
        mockWorkProcessServiceClient.expects(once()).method("retrieveWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePublicKeyWprDoc() }).will(returnValue("Iamdoc,DocIam"))
                .id("Public key call");
        mockWorkProcessServiceClient.expects(once()).method("removeWorkProcessKey")
                .with(new Constraint[] { eq("IF"), acceptablePrivateKeyWprDoc() }).id("Private removal for cancel");
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
        mockUserInfoClient.expects(twice()).method("getOrgInfo").withAnyArguments().will(returnValue(orgInfoDocument));
        mockUserInfoClient.expects(atLeastOnce()).method("getCrossOverUserInfo").withAnyArguments()
                .will(returnValue(xOverUserInfoDocument));
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
                return in.getWPKeyRequest().getClientClaimNumber().equals(CLIENT_CLAIM_NUMBER);

            }
        };
    }

    private Constraint acceptablePrivateKeyWprDoc() {
        return new Constraint() {

            public StringBuffer describeTo(final StringBuffer buffer) {
                return buffer.append("Comparing private document key");
            }

            public boolean eval(final Object o) {
                final WPKeyRequestDocument in = (WPKeyRequestDocument) o;
                assertEquals("Client claim number does not match", CLIENT_CLAIM_NUMBER, in.getWPKeyRequest()
                        .getClientClaimNumber());
                // assertEquals(-4141592654L, in.getWPKeyRequest().getClaimId()
                // );
                assertEquals("Work item id does not match", WORK_ITEM_ID, in.getWPKeyRequest().getWorkItemId());
                assertEquals("Task ID does not match", TASK_ID, in.getWPKeyRequest().getWorkAssignmentId());
                return true;
            }
        };
    }

    /**
     * @param nonDrpAPDDeliveryContext
     */
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

    /**
     * @return
     * @throws XmlException
     */
    private MitchellEnvelopeHelper synthesizeMwmHelper() throws XmlException {
        final MitchellWorkflowMessageDocument payload = messagePostingAgent.getPayload();
        assertNotNull(payload);
        assertNotNull(payload.getMitchellWorkflowMessage());
        assertNotNull(payload.getMitchellWorkflowMessage().getTrackingInfo());
        assertNotNull(payload.getMitchellWorkflowMessage().getData());
        assertEquals(155101, payload.getMitchellWorkflowMessage().getData().getType());
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
    
    private static final String USER_ID = "1";
    private Mock mockAppLoggerBridge;
    private Mock mockAssignmentDeliveryUtils;
    private Mock mockConverter;
    private Mock mockDispatchReportBuilder;
    private Mock mockErrorLoggingServiceWrapper;
    private Mock mockHandlerUtils;
    private Mock mockDocumentStoreClientProxy;
    private MockMessagePostingAgent messagePostingAgent;
    private MockAssignmentDeliveryConfigBridge assignmentDeliveryConfigBridge;
    private Mock mockUserInfoClient;
    private Mock mockWorkProcessServiceClient;
    private Mock mockErrorLoggingService;
    private Mock mockBmsConverterFactory;

}
