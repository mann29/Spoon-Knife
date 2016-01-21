package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MICommonException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.ActivityStatusType;
import com.mitchell.schemas.ArtifactContextDocument;
import com.mitchell.schemas.ArtifactContextDocument.ArtifactContext;
import com.mitchell.schemas.ArtifactItemDocument.ArtifactItem;
import com.mitchell.schemas.ArtifactViewDocument.ArtifactView;
import com.mitchell.schemas.DefinitionType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.RelatedContextsDocument.RelatedContexts;
import com.mitchell.schemas.WorkProcessUpdateMessage;
import com.mitchell.schemas.WorkProcessUpdateMessageDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AssociatedAttachmentsType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.utils.mcf.MCFConstants;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class WcapMsgBusDelHndlr extends AbstractMsgBusDelHndlr {
	private static final String ESTIMATOR = "ESTIMATOR";
	private static final String WCAP_APPRAISAL_ASSIGNMENT = "WCAp Appraisal Assignment";
	private static final String ARTIFACT_CTX_TYPE_CLAIM = "CLAIM";
	private static final String ARTIFACT_CTX_TYPE_ASSIGNMENT = "ASSIGNMENT";
	private static final String SUPPLEMENT = "SUPPLEMENT";
	private static final String ARTIFACT_CTX_TYPE_ESTIMATE = "ESTIMATE";

	private static final String ARTIFACT_ITEM_TYPE_PHOTO = "PHOTO";
	private static final String ARTIFACT_ITEM_TYPE_VIDEO = "VIDEO";
	private static final String ARTIFACT_ITEM_TYPE_AUDIO = "AUDIO";
	private static final String ARTIFACT_ITEM_TYPE_DOC = "DOCUMENT";
	private static final String ARTIFACT_ITEM_TYPE_NICB_RPT = "REPORT";
	private static final String ARTIFACT_ITEM_TYPE_DR = "REPORT";
	private static final String ARTIFACT_ITEM_TYPE_ESTIMATE = "ESTIMATE";
	private static final String ARTIFACT_ITEM_TYPE_UM_NO_PRIDMG_PI_PDF = "ESTIMATE";

	private static final String ARTIFACT_VIEW_TYPE_IMAGE = "IMAGE";
	private static final String ARTIFACT_VIEW_TYPE_VIDEO = "DEFAULT";
	private static final String ARTIFACT_VIEW_TYPE_AUDIO = "DEFAULT";
	private static final String ARTIFACT_VIEW_TYPE_DOC = "DATA_FILE";
	private static final String ARTIFACT_VIEW_TYPE_NICB_RPT = "NICB_REPORT";
	private static final String ARTIFACT_VIEW_TYPE_DR = "DISPATCH_REPORT";
	private static final String ARTIFACT_VIEW_TYPE_MIE = "MIE";
	private static final String ARTIFACT_VIEW_TYPE_UM_NO_PRIDMG_PI_PDF = "UM_NO_PRIDMG_PI_PDF";

	private static final String ARTIFACT_DOCSTORE_ADAPTER = "MitchellAdapter://";
	private static final int ARTIFACT_MAJOR_REV = 0;
	private static final int ARTIFACT_MAJOR_REV_1 = 1;
	private static final int ARTIFACT_SHARED_STATE = 0;
	protected static final String APD_WCAP_DISPATCH_TRANSACTION = "106807";
	protected static final String APD_WCAP_REDISPATCH_TRANSACTION = "106808";
	protected static final String APD_WCAP_CANCEL_TRANSACTION = "106809";

	protected static final String XML_OPEN_CHAR = "<";
	protected static final String XML_CLOSE_CHAR = ">";
	protected static final String XML_ESCAPED_OPEN_CHAR = "&lt;";
	protected static final String XML_ESCAPED_CLOSE_CHAR = "&gt;";
	protected static final String ESTIMATE_ID_TAG = "ESTIMATE_ID";
	protected static final String COMMIT_DATE_TAG = "COMMIT_DATE";
	protected static final String ESTIMATE_KEY_TAG = "ESTIMATE_KEY";

	private static final Logger logger = Logger
			.getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.WcapMsgBusDelHndlr");

	protected long getTargetColaboratorIdFromContext(
			final APDDeliveryContextType context) throws MICommonException,
			MitchellException {
		final BaseAPDCommonType apdCommonInfo = context
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
		final String orgID = apdCommonInfo.getTargetUserInfo().getUserInfo()
				.getOrgID();
		return Long.valueOf(orgID).longValue();
	}

	protected void resolveWorkProcessType(final boolean isSupplement,
			final DefinitionType definition) {
		if (isSupplement) {
			definition.setType(WCAP_APPRAISAL_ASSIGNMENT); // Supplements now
															// using Original WP
															// Definition
		} else {
			definition.setType(WCAP_APPRAISAL_ASSIGNMENT);
		}
	}

	protected String getTargetCollaboratorRole() {
		return ESTIMATOR;
	}

	protected String findUserIdforTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (apdCommonInfo.getTargetUserInfo().getUserInfo().getUserID());
	}

	protected String findUserCoCdforTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (apdCommonInfo.getTargetUserInfo().getUserInfo().getOrgCode());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.business.partialloss.assignmentdelivery.handler
	 * .apd.
	 * impl.AbstractMsgBusDelHndlr#processContextForWorkCenterAttachments(com
	 * .mitchell.schemas.apddelivery.APDDeliveryContextType,
	 * com.mitchell.utils.xml.MitchellEnvelopeHelper,
	 * com.mitchell.utils.xml.MitchellEnvelopeHelper,
	 * com.mitchell.common.types.UserInfoType,
	 * com.mitchell.services.business.partialloss
	 * .assignmentdelivery.handler.apd.impl.AnnotatedLogger, boolean)
	 */
	protected void processContextForWorkCenterAttachments(
			final APDDeliveryContextType context,
			final MitchellEnvelopeHelper inboundPayload,
			final MitchellEnvelopeHelper outboundPayload,
			final UserInfoType userInfo, final AnnotatedLogger log,
			final boolean isReDispatch) throws Exception {
		if (log.isLoggable(Level.FINE)) {
			log.fine("Inbound payload [" + inboundPayload + "]");
			log.fine("Outboundpayload [" + outboundPayload + "]");
			log.fine(isReDispatch ? "Redispatch" : "Dispatch");
		}

		final boolean isSupplement = SUPPLEMENT
				.equals(context.getMessageType());
		final String clientClaimNumber = context
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getClientClaimNumber();

		final String insuranceCompanyCode = context
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode();
		final String workItemId = context.getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getWorkItemId();
		final ArtifactContextDocument artifactContextDocument = ArtifactContextDocument.Factory
				.newInstance();

		final ArtifactContext claimArtifactContext = initializeParentArtifactContext(
				clientClaimNumber, artifactContextDocument);

		// Refactor for addition of Dispatch Report File cleanup
		//
		attachDspRptToArtifactContext(inboundPayload, userInfo, log,
				isSupplement, clientClaimNumber, insuranceCompanyCode,
				workItemId, claimArtifactContext);

		if (isSupplement) {
			// Get Original Estimate DocStoreID from ME Doc
			final long orgEstDocStoreID = retrieveOrgEstDocStoreID(
					inboundPayload, workItemId, log);

			// Get GetDocResponse for Original Estimate MIE from DocStore for
			// file attributes.
			final GetDocResponse getOrgEstDocStoreResponse = getDocFromDocstore(
					log, orgEstDocStoreID);

			// Get Original Estimate DocumentID from ME Doc
			final long orgEstDocumentID = retrieveOrgEstimateID(inboundPayload,
					workItemId, log);

			// get Original Estimate Object
			Estimate estObj = null;
			if (orgEstDocumentID != -1) {
				estObj = getEstimateFromCCDB(orgEstDocumentID, log);
			}
			if (log.isLoggable(Level.INFO) && estObj != null) {
				log.info("processContextForWorkCenterAttachments:: Have estimate object!!");
			}

			// Attach Original Estimate into Estimate ArtifactContext
			attachEstimateContext(claimArtifactContext,
					getOrgEstDocStoreResponse, estObj, orgEstDocStoreID,
					clientClaimNumber, log);
			if (log.isLoggable(Level.INFO)) {
				log.info("WcapMsgBusDelHndlr:: Attached EstimateArtifact to MEDoc");
			}
		}

		// Attach File Attachments to outboundPayload from
		// AdditionalAppraisalAssignmentInfoDocument included in inboundPayload
		if (gatherRequestStatusFrom(context) != CANCEL) {
			attachArtifacts(inboundPayload, log, claimArtifactContext,
					workItemId);
		}

		// Add new ME body for either an AttachmentInfoDocument or
		// ArtifactContextDocument
		addAttachmentBodyToMeDoc(outboundPayload, isReDispatch,
				artifactContextDocument);
	}

	/**
	 * Creates Dispatch Report from inboundPayload - Stores copy of DR in
	 * DocStore, - Retrieves DR from DocStore as a NAS temp file - Attaches
	 * Dispatch Report into claimArtifactContext, - finally cleanup NAS Dispatch
	 * Report temp file
	 * 
	 * @param inboundPayload
	 * @param userInfo
	 * @param log
	 * @param isSupplement
	 * @param clientClaimNumber
	 * @param insuranceCompanyCode
	 * @param workItemId
	 * @param claimArtifactContext
	 * @throws TransformerFactoryConfigurationError
	 * @throws AssignmentDeliveryException
	 */
	private void attachDspRptToArtifactContext(
			final MitchellEnvelopeHelper inboundPayload,
			final UserInfoType userInfo, final AnnotatedLogger log,
			final boolean isSupplement, final String clientClaimNumber,
			final String insuranceCompanyCode, final String workItemId,
			final ArtifactContext claimArtifactContext)
			throws TransformerFactoryConfigurationError,
			AssignmentDeliveryException {

		long docId = 0;
		File drFile = null;
		String drActualFileName = "";

		try {
			drFile = createDispatchRptFile(inboundPayload, userInfo, workItemId);
			drActualFileName = drFile.getName();

			// Put Dispatch Report in DocStore
			final PutDocResponse drPutDocStoreResponse = putDispatchRptInDocstore(
					log, insuranceCompanyCode, drFile, drActualFileName);

			if (drPutDocStoreResponse.getdocid() > 0) {
				docId = drPutDocStoreResponse.getdocid();
			}
			if (log.isLoggable(Level.INFO)) {
				log.info("WcapMsgBusDelHndlr:: Attached AssignmentArtifact, Dispatch Report Filename =[ "
						+ drActualFileName + " ]");
				log.info("WcapMsgBusDelHndlr:: Attached AssignmentArtifact, DocStore docId =["
						+ docId + " ]");
			}

			// Get GetDocResponse for Dispatch Report from DocStore for file
			// attributes.
			final GetDocResponse drGetDocStoreResponse = getDocFromDocstore(
					log, drPutDocStoreResponse.getdocid());

			// Attach Dispatch Report into Assignment ArtifactContext
			attachDr(claimArtifactContext, drGetDocStoreResponse,
					drPutDocStoreResponse.getdocid(), clientClaimNumber, log,
					isSupplement);
			if (log.isLoggable(Level.INFO)) {
				log.info("WcapMsgBusDelHndlr:: Attached AssignmentArtifact(DR) to MEDoc");
			}

		} catch (Exception e) {
			throw new AssignmentDeliveryException(
					AssignmentDeliveryErrorCodes.GENERAL_ERROR, "HandlerUtils",
					"attachDspRptToArtifactContext",
					"Received Exception from attachDspRptToArtifactContext: "
							+ " Companycode: " + insuranceCompanyCode
							+ " ,\t clientClaimNumber= " + clientClaimNumber
							+ " ,\t workItemId= " + workItemId, e);
		} finally {

			// CLEANUP Dispatch Report File
			if (drFile != null && docId > 0) {
				cleanUpFileIfPresent(drFile, "Dispatch Report File");
			}
		}
	}

	/**
	 * @param inboundPayload
	 * @param workItemId
	 * @param log
	 * @return
	 * @throws MitchellException
	 */
	protected long retrieveOrgEstDocStoreID(
			final MitchellEnvelopeHelper inboundPayload,
			final String workItemId, final AnnotatedLogger log)
			throws MitchellException {
		long estimateDocId = -1;
		long origEstimateMieDocStoreId = -1;
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;

		try {
			// Retrieve AdditionalAppraisalAssignmentInfoDocument from
			// inboundPayload - MitchellEnvelopeDocument
			aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
					inboundPayload.getDoc(), workItemId);

			if (aaaInfoDoc != null) {
				if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
						.isSetAssignmentDetails()) {
					if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
							.getAssignmentDetails()
							.isSetRelatedEstimateDocumentID()) {
						estimateDocId = aaaInfoDoc
								.getAdditionalAppraisalAssignmentInfo()
								.getAssignmentDetails()
								.getRelatedEstimateDocumentID();
						if (log.isLoggable(Level.INFO)) {
							log.info(" Debug: retrieveOrgEstDocStoreID(), Before EstimatePackageClient.getAttachmentByDocIdEObject,  estimateDocId= [ "
									+ estimateDocId + " ]\n");
						}
					}
					// Get DocStore ID for the Estimate MIE
					EstimatePackageClient estClient = new EstimatePackageClient();
					final Attachment attach = estClient
							.getAttachmentByDocIdEObject(estimateDocId,
									MCFConstants.ATTACH_OBJ_MIE);
					origEstimateMieDocStoreId = attach.getReferenceId()
							.longValue();
					if (log.isLoggable(Level.INFO)) {
						log.info(" Debug: retrieveOrgEstDocStoreID(), After  EstimatePackageClient.getAttachmentByDocIdEObject origEstimateMieDocStoreId= [ "
								+ origEstimateMieDocStoreId + " ]\n");
					}
				}
			}
		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
		return origEstimateMieDocStoreId;
	}

	/**
	 * @param inboundPayload
	 * @param workItemId
	 * @param log
	 * @return estimateDocId
	 * @throws MitchellException
	 */
	protected long retrieveOrgEstimateID(
			final MitchellEnvelopeHelper inboundPayload,
			final String workItemId, final AnnotatedLogger log)
			throws MitchellException {

		long estimateDocId = -1;
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
		try {
			// Retrieve AdditionalAppraisalAssignmentInfoDocument from
			// inboundPayload - MitchellEnvelopeDocument
			aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
					inboundPayload.getDoc(), workItemId);

			if (aaaInfoDoc != null) {
				if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
						.isSetAssignmentDetails()) {
					if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
							.getAssignmentDetails()
							.isSetRelatedEstimateDocumentID()) {
						estimateDocId = aaaInfoDoc
								.getAdditionalAppraisalAssignmentInfo()
								.getAssignmentDetails()
								.getRelatedEstimateDocumentID();
						if (log.isLoggable(Level.INFO)) {
							log.info(" retrieveOrgEstimateID(),  estimateDocId= [ "
									+ estimateDocId + " ]\n");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
		return estimateDocId;
	}

	/**
	 * @param outboundPayload
	 * @param isReDispatch
	 * @param artifactContextDocument
	 */
	private void addAttachmentBodyToMeDoc(
			final MitchellEnvelopeHelper outboundPayload,
			final boolean isReDispatch,
			final ArtifactContextDocument artifactContextDocument) {
		final EnvelopeBodyType workCenterAttachments = outboundPayload
				.addNewEnvelopeBody(isReDispatch ? UPDATED_WC_ARTIFACT_CTX
						: WC_ARTIFACT_CTX, artifactContextDocument,
						ARTIFACT_CONTEXT);
		workCenterAttachments.getMetadata().setXmlBeanClassname(
				ARTIFACT_CTX_XML_CLASSNAME);
	}

	/**
	 * @param claimArtifactContext
	 * @param drGetDocStoreResponse
	 *            TODO
	 * @param docStoreID
	 *            TODO
	 * @param clientClaimNumber
	 * @param log
	 * @param isSupplement
	 *            TODO
	 * @param getDocStoreResponse
	 *            TODO
	 * @throws AssignmentDeliveryException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws MitchellException
	 */
	private void attachDr(final ArtifactContext claimArtifactContext,
			GetDocResponse drGetDocStoreResponse, long docStoreID,
			final String clientClaimNumber, final AnnotatedLogger log,
			boolean isSupplement) throws AssignmentDeliveryException,
			UnsupportedEncodingException, TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException,
			MitchellException {

		// WCAP - ArtifactContextDocument (DispatchReport)
		final ArtifactContext drArtifactContext = claimArtifactContext
				.addNewRelatedContexts().addNewArtifactContext();
		drArtifactContext.setVersion(ARTIFACT_CTX_VERSION);

		drArtifactContext.setArtifactKey(clientClaimNumber);
		drArtifactContext.setArtifactContextType(ARTIFACT_CTX_TYPE_ASSIGNMENT);
		drArtifactContext.setSharedState(ARTIFACT_SHARED_STATE);
		if (isSupplement) {
			drArtifactContext.setMajorVersion(ARTIFACT_MAJOR_REV_1);
		} else {
			drArtifactContext.setMajorVersion(ARTIFACT_MAJOR_REV);
		}

		// ** REQUIRED FIELDS - Populate with defaults as needed
		final RelatedContexts emptyRelatedContexts = drArtifactContext
				.addNewRelatedContexts(); // REQD by schema!!
		drArtifactContext.setMinorVersion(0);
		drArtifactContext.setContextId(0);
		drArtifactContext.setChangeTrackingNumber(0);
		drArtifactContext.setIsDeleted("");

		final ArtifactItem drRelatedArtifactItem = drArtifactContext
				.addNewItems().addNewArtifactItem();

		drRelatedArtifactItem.setArtifactItemType(ARTIFACT_ITEM_TYPE_DR);

		final ArtifactView artifactView = drRelatedArtifactItem.addNewViews()
				.addNewArtifactView();
		artifactView.setRemoteDocStoreReference(ARTIFACT_DOCSTORE_ADAPTER
				+ docStoreID + "");
		artifactView.setCreatedDate(Calendar.getInstance());
		artifactView.setAttachedDate(Calendar.getInstance());
		artifactView.setFileName(drGetDocStoreResponse.getfilenameoriginal());
		artifactView.setFileExtension(drGetDocStoreResponse.getfileextension()); // artifactView.setFileExtension(ARTIFACT_DR_FILE_EXTEN);
		artifactView.setSharedState(ARTIFACT_SHARED_STATE);

		// ** REQUIRED FIELDS - Populate with defaults as needed
		artifactView.setFileMD5Hash(drGetDocStoreResponse.getdoc_md5hash());
		artifactView.setOriginalFileName(drGetDocStoreResponse
				.getfilenameoriginal()); // artifactView.setOriginalFileName("");
		artifactView.setFileSize((int) (drGetDocStoreResponse.getdoc_size())); // artifactView.setFileSize(0);
		artifactView.setLastModifiedDate(Calendar.getInstance());
		artifactView.setViewId(0);
		artifactView.setParentId(0);
		artifactView.setChangeTrackingNumber(0);
		artifactView.setIsDeleted("");

		com.mitchell.schemas.MetadataDocument.Metadata drMetaData = artifactView
				.addNewMetadata();
		drMetaData.setArtifactViewType(ARTIFACT_VIEW_TYPE_DR);

		// ** REQUIRED FIELDS - Populate with defaults as needed
		drMetaData.setFileLocation("");
		drRelatedArtifactItem.setItemId(0);
		drRelatedArtifactItem.setParentId(0);
		drRelatedArtifactItem.setChangeTrackingNumber(0);
		drRelatedArtifactItem.setIsDeleted("");

		if (log.isLoggable(Level.FINE)) {
			log.fine("WcapMsgBusDelHndlr.attachDr - Set all ArtifactContext properties of dispatch report completed. [ "
					+ docStoreID + " ].");
			log.fine("WcapMsgBusDelHndlr.attachDr - drArtifactContext is [ "
					+ drArtifactContext.toString() + " ].");
		}
	}

	/**
	 * @param claimArtifactContext
	 * @param getDocStoreResponse
	 *            TODO
	 * @param estObj
	 *            TODO
	 * @param docStoreID
	 *            TODO
	 * @param clientClaimNumber
	 * @param log
	 * @throws AssignmentDeliveryException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws MitchellException
	 */
	public void attachEstimateContext(
			final ArtifactContext claimArtifactContext,
			GetDocResponse getDocStoreResponse, Estimate estObj,
			long docStoreID, final String clientClaimNumber,
			final AnnotatedLogger log) throws AssignmentDeliveryException,
			UnsupportedEncodingException, TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException,
			MitchellException {

		// WCAP - Estimate ArtifactContextDocument
		final ArtifactContext estArtifactContext = claimArtifactContext
				.getRelatedContexts().addNewArtifactContext();
		estArtifactContext.setVersion(ARTIFACT_CTX_VERSION);

		String artifactKeyXMLString = "";
		if (estObj != null) {

			if (log.isLoggable(Level.INFO)) {
				log.info("WcapMsgBusDelHndlr.attachDr -  "
						+ estObj.getSupplementNumber());
				log.info("WcapMsgBusDelHndlr.attachDr -  "
						+ estObj.getCorrectionNumber());
			}

			// estArtifactContext.setArtifactKey(estObj.getClientEstimateId());
			estArtifactContext.setMajorVersion(estObj.getSupplementNumber()
					.intValue());
			estArtifactContext.setMinorVersion(estObj.getCorrectionNumber()
					.intValue());

			// XML Construction with non-escaped XML
			artifactKeyXMLString = XML_OPEN_CHAR + ESTIMATE_KEY_TAG
					+ XML_CLOSE_CHAR + XML_OPEN_CHAR + ESTIMATE_ID_TAG
					+ XML_CLOSE_CHAR + estObj.getClientEstimateId()
					+ XML_OPEN_CHAR + "/" + ESTIMATE_ID_TAG + XML_CLOSE_CHAR
					+ XML_OPEN_CHAR + COMMIT_DATE_TAG + XML_CLOSE_CHAR
					+ estObj.getCommitDate().toString() + XML_OPEN_CHAR + "/"
					+ COMMIT_DATE_TAG + XML_CLOSE_CHAR + XML_OPEN_CHAR + "/"
					+ ESTIMATE_KEY_TAG + XML_CLOSE_CHAR;
			estArtifactContext.setArtifactKey(artifactKeyXMLString);
			log.info("\nWcapMsgBusDelHndlr.attachEstimateContext - artifactKeyXMLString [ "
					+ artifactKeyXMLString + " ].\n");

		} else {
			if (log.isLoggable(Level.INFO)) {
				log.info("ESTIMATE OBJ is null");

			}

			estArtifactContext.setArtifactKey(clientClaimNumber);
			estArtifactContext.setMajorVersion(ARTIFACT_MAJOR_REV);
			estArtifactContext.setMinorVersion(ARTIFACT_MAJOR_REV);
		}
		estArtifactContext.setArtifactContextType(ARTIFACT_CTX_TYPE_ESTIMATE);
		estArtifactContext.setSharedState(ARTIFACT_SHARED_STATE);

		// ** REQUIRED FIELDS - Populate with defaults as needed
		// REQD by schema!!
		final RelatedContexts emptyRelatedContexts = estArtifactContext
				.addNewRelatedContexts();
		estArtifactContext.setContextId(0);
		estArtifactContext.setChangeTrackingNumber(0);
		estArtifactContext.setIsDeleted("");

		final ArtifactItem estRelatedArtifactItem = estArtifactContext
				.addNewItems().addNewArtifactItem();

		estRelatedArtifactItem.setArtifactItemType(ARTIFACT_ITEM_TYPE_ESTIMATE);

		final ArtifactView artifactView = estRelatedArtifactItem.addNewViews()
				.addNewArtifactView();
		artifactView.setRemoteDocStoreReference(ARTIFACT_DOCSTORE_ADAPTER
				+ docStoreID + "");

		Calendar now = Calendar.getInstance();
		artifactView.setCreatedDate(now);
		artifactView.setAttachedDate(now);
		artifactView.setLastModifiedDate(now);
		artifactView.setFileName(getDocStoreResponse.getfilenameoriginal());
		artifactView.setSharedState(ARTIFACT_SHARED_STATE);

		artifactView.setFileExtension("");
		if (getDocStoreResponse.getfileextension() != null) {
			artifactView.setFileExtension(getDocStoreResponse
					.getfileextension());
		}

		// ** REQUIRED FIELDS - Populate with defaults as needed
		artifactView.setFileMD5Hash(getDocStoreResponse.getdoc_md5hash());
		artifactView.setOriginalFileName(getDocStoreResponse
				.getfilenameoriginal()); // artifactView.setOriginalFileName("");
		artifactView.setFileSize((int) (getDocStoreResponse.getdoc_size())); // artifactView.setFileSize(0);
		artifactView.setViewId(0);
		artifactView.setParentId(0);
		artifactView.setChangeTrackingNumber(0);
		artifactView.setIsDeleted("");

		com.mitchell.schemas.MetadataDocument.Metadata estMetaData = artifactView
				.addNewMetadata();
		estMetaData.setArtifactViewType(ARTIFACT_VIEW_TYPE_MIE);

		// ** REQUIRED FIELDS - Populate with defaults as needed
		estMetaData.setFileLocation("");
		estRelatedArtifactItem.setItemId(0);
		estRelatedArtifactItem.setParentId(0);
		estRelatedArtifactItem.setChangeTrackingNumber(0);
		estRelatedArtifactItem.setIsDeleted("");

		if (log.isLoggable(Level.INFO)) {
			log.info("WcapMsgBusDelHndlr.attachEstimateContext - "
					+ "Set all EstimateArtifactContext properties completed, Estimate DocStoreID [ "
					+ docStoreID + " ].");
			log.info("WcapMsgBusDelHndlr.attachEstimateContext - estArtifactContext is [ "
					+ estArtifactContext.toString() + " ].");

		}
	}

	/**
	 * @param inboundPayload
	 * @param userInfo
	 * @param workItemId
	 * @return
	 * @throws AssignmentDeliveryException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	private File createDispatchRptFile(
			final MitchellEnvelopeHelper inboundPayload,
			final UserInfoType userInfo, final String workItemId)
			throws AssignmentDeliveryException, UnsupportedEncodingException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(userInfo);
		final File drFile = drBuilder.createDispatchReport(userInfoDocument,
				workItemId, inboundPayload.getDoc());
		return drFile;
	}

	/**
	 * @param log
	 * @param insuranceCompanyCode
	 * @param drFile
	 * @param drActualFileName
	 * @return
	 * @throws MitchellException
	 */
	private PutDocResponse putDispatchRptInDocstore(final AnnotatedLogger log,
			final String insuranceCompanyCode, final File drFile,
			final String drActualFileName) throws MitchellException {

		final PutDocResponse getDocStoreResponse = documentStoreClientProxy
				.putDocument(drFile.getAbsolutePath(), insuranceCompanyCode,
						AssignmentDeliveryConstants.APPLICATION_NAME,
						AssignmentDeliveryConstants.APPLICATION_NAME,
						MIME_TEXT_TYPE, !DUPLICATES_ALLOWED);
		if (log.isLoggable(Level.INFO)) {
			log.info("Stored dispatch report in docstore. DocStore id is [ "
					+ getDocStoreResponse.getdocid() + " ].");
			log.info("Stored dispatch report in docstore. Dispatch Report FileName [ "
					+ drActualFileName + " ].");
		}
		return getDocStoreResponse;
	}

	/**
	 * @param log
	 * @param estDocID
	 *            TODO
	 * @return
	 * @throws MitchellException
	 */
	private GetDocResponse getDocFromDocstore(final AnnotatedLogger log,
			long docStoreId) throws MitchellException {

		final GetDocResponse getResponse = documentStoreClientProxy
				.getDocument(docStoreId);
		if (log.isLoggable(Level.INFO)) {
			log.info("Stored Document in docstore. Document FileName [ "
					+ getResponse.getfilenameoriginal() + " ].");
			log.info("Stored Document in docstore. Document FileExtension [ "
					+ getResponse.getfileextension() + " ].");
			log.info("Stored Document in docstore. Document getdoc_size [ "
					+ getResponse.getdoc_size() + " ].");
			log.info("Stored Document in docstore. Document getdoc_md5hash [ "
					+ getResponse.getdoc_md5hash() + " ].");
		}
		return getResponse;
	}

	private ArtifactContext initializeParentArtifactContext(
			final String clientClaimNumber,
			ArtifactContextDocument artifactContextDocument) {

		final ArtifactContext claimArtifactContext = artifactContextDocument
				.addNewArtifactContext();
		claimArtifactContext.setVersion(ARTIFACT_CTX_VERSION);

		claimArtifactContext.setArtifactKey(clientClaimNumber);
		claimArtifactContext.setArtifactContextType(ARTIFACT_CTX_TYPE_CLAIM);
		claimArtifactContext.setSharedState(ARTIFACT_SHARED_STATE);
		claimArtifactContext.setMajorVersion(ARTIFACT_MAJOR_REV);

		// ** REQUIRED FIELDS - Populate with defaults
		claimArtifactContext.setMinorVersion(0);
		claimArtifactContext.setContextId(0);
		claimArtifactContext.setChangeTrackingNumber(0);
		claimArtifactContext.setIsDeleted("");

		// Initialize the claimArtifactContext Items List for Dispatch Report
		// Only case
		final com.mitchell.schemas.ItemsDocument.Items artifactItems = claimArtifactContext
				.addNewItems();
		return claimArtifactContext;
	}

	/**
	 * 
	 * @param inboundPayload
	 * @param log
	 * @param claimArtifactContext
	 * @param workItemId
	 * @throws MitchellException
	 */
	private void attachArtifacts(final MitchellEnvelopeHelper inboundPayload,
			final AnnotatedLogger log,
			final ArtifactContext claimArtifactContext, final String workItemId)
			throws MitchellException {

		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;

		try {

			// Retrieve AdditionalAppraisalAssignmentInfoDocument from
			// inboundPayload - MitchellEnvelopeDocument
			aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
					inboundPayload.getDoc(), workItemId);

			if (log.isLoggable(Level.FINE)) {
				log.fine("Retrieved AdditionalAppraisalAssignmentInfoDocument: "
						+ aaaInfoDoc);
			}

			if (aaaInfoDoc != null) {

				AssociatedAttachmentsType assocAttachments = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getAssociatedAttachments();

				if (assocAttachments != null
						&& assocAttachments.isSetFileAttachments()) {

					final int attArrayLen = assocAttachments
							.getFileAttachments().getFileAttachmentArray().length;

					final com.mitchell.schemas.ItemsDocument.Items artifactItems = claimArtifactContext
							.getItems();

					for (int i = 0; i < attArrayLen; i++) {

						final ArtifactItem imgArtifactItem = artifactItems
								.addNewArtifactItem();

						String altAttachmentType = assocAttachments
								.getFileAttachments().getFileAttachmentArray(i)
								.getAltAttachmentType();
						final String artifactItemType = getArtifactItemType(altAttachmentType);
						final String artifactViewType = getArtifactViewType(altAttachmentType);

						imgArtifactItem.setArtifactItemType(artifactItemType);

						if (log.isLoggable(Level.INFO)) { // TODO: Temp Debug
															// Remove Later
							log.info(">> Just Before adding artifactView (set_1)...");
							log.info("Properties for file ArtifactItem: # [ "
									+ i + ", artifactItemType= "
									+ artifactItemType + " ].");
							log.info("Properties for file ArtifactItem: # [ "
									+ i + ", artifactViewType= "
									+ artifactViewType + " ].");
							log.info("Properties for file ArtifactItem: # [ "
									+ i
									+ ", docStoreID= "
									+ assocAttachments.getFileAttachments()
											.getFileAttachmentArray(i)
											.getDocStoreID() + " ].");
							String fileNameTemp = assocAttachments
									.getFileAttachments()
									.getFileAttachmentArray(i).getFileName();
							log.info("Properties for file ArtifactItem: # [ "
									+ i + ", fileName= " + fileNameTemp + " ].");

							String fileExtnTmp = "";
							if (fileNameTemp.lastIndexOf(".") > 0) {
								fileExtnTmp = fileNameTemp.substring(
										fileNameTemp.lastIndexOf("."),
										fileNameTemp.length());
							}
							log.info("Properties for file ArtifactItem: # [ "
									+ i + ", fileExtnTmp= " + fileExtnTmp
									+ " ].");
						}

						long docStoreID = assocAttachments.getFileAttachments()
								.getFileAttachmentArray(i).getDocStoreID();
						String fileName = assocAttachments.getFileAttachments()
								.getFileAttachmentArray(i).getFileName();
						String fileExtn = "";
						if (fileName.lastIndexOf(".") > 0) {
							fileExtn = fileName.substring(
									fileName.lastIndexOf("."),
									fileName.length());
						}

						final ArtifactView artifactView = imgArtifactItem
								.addNewViews().addNewArtifactView();
						artifactView
								.setRemoteDocStoreReference(ARTIFACT_DOCSTORE_ADAPTER
										+ docStoreID + "");
						artifactView.setCreatedDate(Calendar.getInstance());
						artifactView.setAttachedDate(Calendar.getInstance());
						artifactView.setFileName(fileName);
						artifactView.setFileExtension(fileExtn);
						artifactView.setSharedState(ARTIFACT_SHARED_STATE);

						// ** REQUIRED FIELDS - Populate with defaults
						artifactView.setFileMD5Hash("");
						artifactView.setOriginalFileName("");
						artifactView.setFileSize(0);
						artifactView
								.setLastModifiedDate(Calendar.getInstance());
						artifactView.setViewId(0);
						artifactView.setParentId(0);
						artifactView.setChangeTrackingNumber(0);
						artifactView.setIsDeleted("");

						com.mitchell.schemas.MetadataDocument.Metadata imgMetaData = artifactView
								.addNewMetadata();
						imgMetaData.setArtifactViewType(artifactViewType);

						// ** REQUIRED FIELDS - Populate with defaults
						imgMetaData.setFileLocation("");
						imgArtifactItem.setItemId(0);
						imgArtifactItem.setParentId(0);
						imgArtifactItem.setChangeTrackingNumber(0);
						imgArtifactItem.setIsDeleted("");

						if (log.isLoggable(Level.FINE)) { // TODO: Temp Debug
															// Remove Later
							log.fine("Set all properties for file ArtifactItem: # [ "
									+ i
									+ ", docStoreID= "
									+ ARTIFACT_DOCSTORE_ADAPTER
									+ docStoreID
									+ " ].");
							log.fine("WcapMsgBusDelHndlr.attachArtifacts - ArtifactItem #"
									+ i
									+ " [ "
									+ imgArtifactItem.toString()
									+ " ].");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
	}

	/**
	 * @param altAttachmentType
	 * @return artifactItemType
	 */
	protected String getArtifactItemType(final String altAttachmentType) {
		// Determine ArtifactItemType and ArtifactViewType
		String artifactItemType = ARTIFACT_ITEM_TYPE_DOC; // TODO Default??
		if (altAttachmentType.equalsIgnoreCase("2")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_PHOTO;
		} else if (altAttachmentType.equalsIgnoreCase("5")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_DOC;
		} else if (altAttachmentType.equalsIgnoreCase("46")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_UM_NO_PRIDMG_PI_PDF;
		} else if (altAttachmentType.equalsIgnoreCase("47")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_VIDEO;
		} else if (altAttachmentType.equalsIgnoreCase("48")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_AUDIO;
		} else if (altAttachmentType.equalsIgnoreCase("4")) {
			artifactItemType = ARTIFACT_ITEM_TYPE_NICB_RPT;
		}
		return artifactItemType;
	}

	/**
	 * @param altAttachmentType
	 * @return artifactViewType
	 */
	protected String getArtifactViewType(final String altAttachmentType) {
		// Determine ArtifactItemType and ArtifactViewType
		String artifactViewType = ARTIFACT_VIEW_TYPE_DOC; // TODO Default??
		if (altAttachmentType.equalsIgnoreCase("2")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_IMAGE;
		} else if (altAttachmentType.equalsIgnoreCase("5")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_DOC;
		} else if (altAttachmentType.equalsIgnoreCase("46")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_UM_NO_PRIDMG_PI_PDF;
		} else if (altAttachmentType.equalsIgnoreCase("47")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_VIDEO;
		} else if (altAttachmentType.equalsIgnoreCase("48")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_AUDIO;
		} else if (altAttachmentType.equalsIgnoreCase("4")) {
			artifactViewType = ARTIFACT_VIEW_TYPE_NICB_RPT;
		}
		return artifactViewType;
	}

	/**
	 * @param workItemId
	 * @param cieca
	 * @param userInfoForMessagebus
	 * @param status
	 * @param isReDispatch
	 * @throws MitchellException
	 */
	public void writeApplogEvent(final String workItemId,
			final CIECADocument cieca,
			final UserInfoDocument userInfoForMessagebus, final int status,
			final boolean isReDispatch) throws MitchellException {
		final String userId = userInfoForMessagebus.getUserInfo().getUserID();
		final String orgCode = userInfoForMessagebus.getUserInfo().getOrgCode();
		final String applogTransactionCode = (isReDispatch ? APD_WCAP_REDISPATCH_TRANSACTION
				: ((status == CANCEL) ? APD_WCAP_CANCEL_TRANSACTION
						: APD_WCAP_DISPATCH_TRANSACTION));
		final AppLoggingDocument appLogDocument = prepareAppLoggingDocument(
				cieca, STATUS_TO_KEEP_LOGGING_API_HAPPY, workItemId,
				applogTransactionCode, userId);
		final AppLoggingNVPairs applogNameValuePairs = prepareAppLoggingNameValuePairs(
				cieca, NOT_ARCHIVED, applogTransactionCode, false, userId,
				orgCode);
		appLoggerBridge.logEvent(appLogDocument, userInfoForMessagebus,
				workItemId, AssignmentDeliveryConstants.APPLICATION_NAME,
				AssignmentDeliveryConstants.APPLICATION_NAME,
				applogNameValuePairs);
	}

	protected void processContextForWorkProcessUpdateMessage(
			final APDDeliveryContextType apdDeliveryContext,
			final AnnotatedLogger log, final UserInfoType userInfo,
			final MitchellEnvelopeHelper inboundPayload,
			final MitchellEnvelopeHelper outboundPayload,
			final Map workProcessKeys, final boolean isSupplement)
			throws MitchellException, RemoteException {
		final WorkProcessUpdateMessageDocument document = WorkProcessUpdateMessageDocument.Factory
				.newInstance();
		final WorkProcessUpdateMessage wpum = document
				.addNewWorkProcessUpdateMessage();
		wpum.setActivityStatus(ActivityStatusType.BEGIN);
		final String companyCode = extractCompanyCodefromUserInfo(userInfo);
		wpum.setCollaborator(companyCode); // **Change to companyCode for
											// ArtifactUpload w/o Assignment
		wpum.setPrivateIndex((String) workProcessKeys.get(PRIVATE));
		wpum.setPublicIndex((String) workProcessKeys.get(PUBLIC));
		if (gatherRequestStatusFrom(apdDeliveryContext) == CANCEL) {
			if (isSupplement) {
				wpum.setActivityOperation(CANCEL_APPRAISAL_ASSIGNMENT); // CANCEL_SUPPLEMENT_ASSIGNMENT
			} else {
				wpum.setActivityOperation(CANCEL_APPRAISAL_ASSIGNMENT);
			}
		} else {
			if (isSupplement) {
				wpum.setActivityOperation(UPDATE_APPRAISAL_ASSIGNMENT); // UPDATE_SUPPLEMENT_ASSIGNMENT
			} else {
				wpum.setActivityOperation(UPDATE_APPRAISAL_ASSIGNMENT);
			}
		}
		final EnvelopeBodyType wpumBody = outboundPayload.addNewEnvelopeBody(
				WORK_PROCESS_UPDATE_MESSAGE, document,
				WORK_PROCESS_UPDATE_MESSAGE);
		wpumBody.getMetadata().setXmlBeanClassname(
				"com.mitchell.schemas.WorkProcessUpdateMessageDocument");
	}

	/**
	 * This method retrieves the Estimate Object and the Document Object from
	 * the CCDB for the provided estimate document id.
	 * 
	 * @param estimateDocId
	 *            Document Id of the estimate object that is to be retrieved
	 *            from CCDB.
	 * @param log
	 * @return Returns the Estimate object (EstimatePackage Service BO).
	 * @throws MitchellException
	 */
	public Estimate getEstimateFromCCDB(long estimateDocId,
			final AnnotatedLogger log) throws MitchellException {
		Estimate estObj = null;
		try {
			if (log.isLoggable(Level.FINE)) {
				log.fine("getEstimateFromCCDB:: Requesting a reference to EstimatePackageRemote for original estimate document id:["
						+ estimateDocId + "]");
			}
			// final EstimatePackageRemote ejb =
			// EstimatePackageClient.getEstimatePackageEJB();
			EstimatePackageClient ejb = new EstimatePackageClient();
			final boolean includeSubObjects = false;

			estObj = ejb.getEstimateAndDocByDocId(estimateDocId,
					includeSubObjects);

			if (estObj != null && log.isLoggable(Level.INFO)) {
				log.info("getEstimateFromCCDB:: Have estimate object");
				String estimateID = estObj.getClientEstimateId();
				Long supplementNo = estObj.getSupplementNumber();
				Long correctionNo = estObj.getCorrectionNumber();
				Date commitDate = new Date();
				commitDate = estObj.getCommitDate();
				Date createdDate = new Date();
				createdDate = estObj.getCreatedDate();
				Date estimateReceivedDate = new Date();
				estimateReceivedDate = estObj.getEstimateReceivedDate();
				Date updatedDate = new Date();
				updatedDate = estObj.getUpdatedDate();
				log.info("getEstimateFromCCDB::  estimate details: estimateID ["
						+ estimateID + "]");
				log.info("getEstimateFromCCDB::  estimate details: supplementNo ["
						+ supplementNo + "]");
				log.info("getEstimateFromCCDB::  estimate details: correctionNo ["
						+ correctionNo + "]");
				log.info("getEstimateFromCCDB::  estimate details: commitDate ["
						+ commitDate + "]");
				log.info("getEstimateFromCCDB::  estimate details: createdDate ["
						+ createdDate + "]");
				log.info("getEstimateFromCCDB::  estimate details: estimateReceivedDate ["
						+ estimateReceivedDate + "]");
				log.info("getEstimateFromCCDB::  estimate details: updatedDate ["
						+ updatedDate + "]");
			}

		} catch (final Exception e) {
			throw new MitchellException(
					AssignmentDeliveryErrorCodes.GENERAL_ERROR, "HandlerUtils",
					"getEstimateFromCCDB",
					"Received Exception calling EstimatePackageService", e);
		}
		return estObj;
	}

	protected boolean protocolRequiresShopSupplementNotification() {
		return false;
	}

	public void deliverAssignment(final APDDeliveryContextDocument document)
			throws AssignmentDeliveryException {
		if (gatherRequestStatusFrom(document.getAPDDeliveryContext()) != COMPLETE) {
			super.deliverAssignment(document);
		} else {
			final AnnotatedLogger log = AnnotatedLogger.annotate(mLogger);
			if (log.isLoggable(Level.INFO)) {
				log.info("COMPLETED message status not supported for WCAP!");
			}
		}
	}

	protected boolean allowBMSAndAttachmentInMitchellEnvelope(final int status) {
		return true;
	}

}
