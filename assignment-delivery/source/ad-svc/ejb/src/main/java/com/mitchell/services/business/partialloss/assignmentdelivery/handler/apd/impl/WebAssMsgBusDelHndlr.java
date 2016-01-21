package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoDocument;
import com.mitchell.common.types.AttachmentInfoListType;
import com.mitchell.common.types.AttachmentInfoType;
import com.mitchell.common.types.AttachmentItemType;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AssociatedAttachmentsType;
import com.mitchell.schemas.appraisalassignment.VehicleType;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public final class WebAssMsgBusDelHndlr extends
		AbstractMessageBusDeliveryHandler {
	protected static final String APD_RC_DISPATCH_TRANSACTION = "106804";
	protected static final String APD_RC_REDISPATCH_TRANSACTION = "106805";
	protected static final String APD_RC_CANCEL_TRANSACTION = "106806";
	protected static final String APD_RC_COMPLETED_TRANSACTION = "106815";
	private static final String PLATFORM_FILE_TYPE_IMAGE = "IMAGE";
	private static final String WORKCENTER_FILE_TYPE_IMG = "IMG";

	protected boolean getIsReDispatch(DeliveryHandlerContext context) {
		return false;
	}

	private static final Logger logger = Logger
			.getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.WebAssMsgBusDelHndlr");

	protected long getTargetCollaboratorIdFromContext(
			final APDDeliveryContextType context) throws MitchellException {
		try {
			final CrossOverUserInfoDocument crossOverUserInfo = fishOutXOverInfo(context);
			if (mLogger.isLoggable(Level.FINE)) {
				mLogger.fine("Crossover user info:"
						+ String.valueOf(crossOverUserInfo));
			}
			return getTargetCollaboratorId(crossOverUserInfo);
		} catch (final Exception e) {
			throw new MitchellException(CLASS_NAME, CALLER_METHOD,
					e.getMessage());
		}
	}

	protected String findUserIdForTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (noDRPInfoFound ? " " : apdCommonInfo.getTargetDRPUserInfo()
				.getUserInfo().getUserID());
	}

	protected String findUserCoCdForTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (noDRPInfoFound ? " " : apdCommonInfo.getTargetDRPUserInfo()
				.getUserInfo().getOrgCode());
	}

	private void addAttachmentBodyToMeDoc(
			final MitchellEnvelopeHelper outboundPayload,
			final boolean isReDispatch,
			final AttachmentInfoDocument attachmentInfoDocument) {
		final EnvelopeBodyType workCenterAttachments = outboundPayload
				.addNewEnvelopeBody(isReDispatch ? UPDATED_WC_ATTACHMENT_INFO
						: WC_ATTACHMENT_INFO, attachmentInfoDocument,
						ATTACHMENT_INFO);
		workCenterAttachments.getMetadata().setXmlBeanClassname(
				ATTACHMENT_INFO_XML_CLASSNAME);
	}

	private void attachDr(final MitchellEnvelopeHelper inboundPayload,
			final UserInfoType userInfo, final AnnotatedLogger log,
			final AttachmentItemType drAttachmentItem,
			final String insuranceCompanyCode, final String workItemId)
			throws AssignmentDeliveryException, UnsupportedEncodingException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException,
			MitchellException {
		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(userInfo);
		final File drFile = drBuilder.createDispatchReport(userInfoDocument,
				workItemId, inboundPayload.getDoc());
		final PutDocResponse drDocStoreResponse = documentStoreClientProxy
				.putDocument(drFile.getAbsolutePath(), insuranceCompanyCode,
						AssignmentDeliveryConstants.APPLICATION_NAME,
						AssignmentDeliveryConstants.APPLICATION_NAME,
						MIME_TEXT_TYPE, !DUPLICATES_ALLOWED);
		if (log.isLoggable(Level.INFO)) {
			log.info("WebAssMsgBusDelHndlr.attachDr Stored dispatch report in docstore. DocStore id is [ "
					+ drDocStoreResponse.getdocid() + " ].");
		}
		final String drActualFileName = drFile.getName();
		drAttachmentItem.setActualFileName(drActualFileName);
		final String drUUID = UUIDFactory.getInstance().getUUID();
		drAttachmentItem.setAttachmentId(drUUID);
		drAttachmentItem.setDateAdded(Calendar.getInstance());
		drAttachmentItem.setDocStoreFileReference(drDocStoreResponse.getdocid()
				+ "");
		drAttachmentItem.setStatus(0);
		drAttachmentItem.setAttachmentType(DISPATCH_REPORT);
		if (log.isLoggable(Level.INFO)) {
			log.info("WebAssMsgBusDelHndlr.attachDr - Set all drAttachmentItem properties of dispatch report completed. [ "
					+ drDocStoreResponse.getdocid() + " ].");
		}
		// Bug 518098 - Cleanup temp Dispatch Report File from NAS
		if (drFile != null && drDocStoreResponse.getdocid() > 0) {
			cleanUpFileIfPresent(drFile, "Dispatch Report File");
			if (log.isLoggable(Level.INFO)) {
				log.info("WebAssMsgBusDelHndlr.attachDr after cleanUpFileIfPresent ");
			}
		}
	}

	private int attachArtifacts(final MitchellEnvelopeHelper inboundPayload,
			final AnnotatedLogger log,
			final AttachmentInfoListType wcAttachmentInfoList,
			final String workItemId) throws MitchellException {

		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
		int attachItemCount = 0;

		try {

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

				if (assocAttachments.isSetFileAttachments()) {

					final int attArrayLen = assocAttachments
							.getFileAttachments().getFileAttachmentArray().length;
					attachItemCount = attArrayLen;

					for (int i = 0; i < attArrayLen; i++) {

						final AttachmentItemType attAttachmentItem = wcAttachmentInfoList
								.addNewAttachmentItem();

						long docStoreID = assocAttachments.getFileAttachments()
								.getFileAttachmentArray(i).getDocStoreID();
						String fileName = assocAttachments.getFileAttachments()
								.getFileAttachmentArray(i).getFileName();
						String fileType = assocAttachments.getFileAttachments()
								.getFileAttachmentArray(i).getFileType();

						// Yet another missed RC requirement
						if (fileType.equalsIgnoreCase(WORKCENTER_FILE_TYPE_IMG))
							fileType = PLATFORM_FILE_TYPE_IMAGE;

						final String attUUID = UUIDFactory.getInstance()
								.getUUID();
						attAttachmentItem.setActualFileName(fileName);
						attAttachmentItem.setAttachmentId(attUUID);
						attAttachmentItem.setDateAdded(Calendar.getInstance());
						attAttachmentItem.setDocStoreFileReference(docStoreID
								+ "");
						attAttachmentItem.setStatus(0);
						attAttachmentItem.setAttachmentType(fileType);
					}
				}
			}

		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
		return attachItemCount;

	}

	private AdditionalAppraisalAssignmentInfoDocument attachVehicleDetailsInfo(
			final MitchellEnvelopeHelper inboundPayload,
			final AnnotatedLogger log, final String workItemId)
			throws MitchellException {

		AdditionalAppraisalAssignmentInfoDocument aaaInfoDocOut = null;
		try {

			// Retrieve AdditionalAppraisalAssignmentInfoDocument from
			// inboundPayload - MitchellEnvelopeDocument
			AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
			aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
					inboundPayload.getDoc(), workItemId);

			if (log.isLoggable(Level.FINE)) {
				log.fine("Retrieved AdditionalAppraisalAssignmentInfoDocument: "
						+ aaaInfoDoc);
			}

			if (aaaInfoDoc != null) {
				VehicleType vehicleDetails = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails();

				if (log.isLoggable(Level.INFO)) {
					log.info("attachVehicleDetailsInfo:: Inbound vehicleDetails: [ "
							+ vehicleDetails.toString() + " ] ");
				}
				if (vehicleDetails != null) {

					aaaInfoDocOut = AdditionalAppraisalAssignmentInfoDocument.Factory
							.newInstance();

					// Add full VehicleDetails aggregate from inbound
					aaaInfoDocOut
							.addNewAdditionalAppraisalAssignmentInfo()
							.setVehicleDetails(
									aaaInfoDoc
											.getAdditionalAppraisalAssignmentInfo()
											.getVehicleDetails());

					removeVehicleAmbiguityAttributes(log, aaaInfoDocOut,
							aaaInfoDoc);

					if (log.isLoggable(Level.INFO)) {
						log.info("attachVehicleDetailsInfo:: Updated vehicleDetails: [ "
								+ aaaInfoDocOut.toString() + " ]  ");
					}
				}
			}
		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
		return aaaInfoDocOut;
	}

	protected void removeVehicleAmbiguityAttributes(final AnnotatedLogger log,
			AdditionalAppraisalAssignmentInfoDocument aaaInfoDocOut,
			AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc) {
		BigInteger additionalAAInfoVehicleCategoryId = null;
		BigInteger additionalAAInfoVehicleTypeId = null;
		BigInteger additionalAAInfoMakeId = null;
		BigInteger additionalAAInfoModelId = null;
		BigInteger additionalAAInfoSubModelId = null;
		BigInteger additionalAAInfoBodyStyleId = null;
		BigInteger additionalAAInfoEngineId = null;
		BigInteger additionalAAInfoTransmissionId = null;
		BigInteger additionalAAInfoDriveTrainId = null;
		BigInteger additionalAAInfoYearId = null;
		if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
				.getVehicleDetails().isSetVehicleTypeAmbiguity()) {
			if (log.isLoggable(Level.FINE)) {
				log.fine("VehicleTypeAmbiguity is present: [ "
						+ aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
								.getVehicleDetails().getVehicleTypeAmbiguity()
						+ " ] ");
			}

			// If VehicleTypeAmbiguity exists, Always Remove!!
			aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().unsetVehicleTypeAmbiguity();

			// 1 if isSetDriveTrain() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetDriveTrain()) {
				additionalAAInfoDriveTrainId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getDriveTrain().getID();
				if (additionalAAInfoDriveTrainId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetDriveTrain();
				}
			}
			// 2 if isSetTransmission() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetTransmission()) {
				additionalAAInfoTransmissionId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getTransmission().getID();
				if (additionalAAInfoTransmissionId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetTransmission();
				}
			}
			// 3 if isSetEngine() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetEngine()) {
				additionalAAInfoEngineId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getEngine().getID();
				if (additionalAAInfoEngineId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetEngine();
				}
			}
			// 4 if isSetBodyStyle() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetBodyStyle()) {
				additionalAAInfoBodyStyleId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getBodyStyle().getID();
				if (additionalAAInfoBodyStyleId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetBodyStyle();
				}
			}
			// 5 if isSetSubModel() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetSubModel()) {
				additionalAAInfoSubModelId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getSubModel().getID();
				if (additionalAAInfoSubModelId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetSubModel();
				}
			}
			// 6 if isSetModel() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetModel()) {
				additionalAAInfoModelId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getModel().getID();
				if (additionalAAInfoModelId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetModel();
				}
			}
			// 7 if isSetMake() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetMake()) {
				additionalAAInfoMakeId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getMake().getID();
				if (additionalAAInfoMakeId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetMake();
				}
			}
			// 8 if isSetYear() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetYear()) {
				additionalAAInfoYearId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getYear();
				if (additionalAAInfoYearId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetYear();
				}
			}
			// 9 if isSetVehicleType() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetVehicleType()) {
				additionalAAInfoVehicleTypeId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getVehicleType().getID();
				if (additionalAAInfoVehicleTypeId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetVehicleType();
				}
			}
			// 10 if isSetVehicleCategory() and Id = -1 then remove
			if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getVehicleDetails().isSetVehicleCategory()) {
				additionalAAInfoVehicleCategoryId = aaaInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getVehicleDetails().getVehicleCategory().getID();
				if (additionalAAInfoVehicleCategoryId.longValue() < 0) {
					aaaInfoDocOut.getAdditionalAppraisalAssignmentInfo()
							.getVehicleDetails().unsetVehicleCategory();
				}
			}
		}
	}

	private void addVehicleDetailsBodyToMeDoc(
			final MitchellEnvelopeHelper outboundPayload,
			final AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument) {
		final EnvelopeBodyType aaInfoVehicleDetails = outboundPayload
				.addNewEnvelopeBody(
						AssignmentDeliveryConstants.ME_AAINFO_VEHICLE_DETAILS_INFO_IDENTIFIER,
						additionalAppraisalAssignmentInfoDocument,
						AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER);
		aaInfoVehicleDetails.getMetadata().setXmlBeanClassname(
				AssignmentDeliveryConstants.ME_AAINFO_XML_BEAN_CLASS_TYPE);
	}

	public void writeApplogEvent(final String workItemId,
			final CIECADocument cieca,
			final UserInfoDocument userInfoForMessagebus, final int status,
			final boolean isReDispatch) throws MitchellException {
		final String userId = userInfoForMessagebus.getUserInfo().getUserID();
		final String orgCode = userInfoForMessagebus.getUserInfo().getOrgCode();
		final String applogTransactionCode = (isReDispatch ? APD_RC_REDISPATCH_TRANSACTION
				: ((status == CANCEL) ? APD_RC_CANCEL_TRANSACTION
						: (status == COMPLETE) ? APD_RC_COMPLETED_TRANSACTION
								: APD_RC_DISPATCH_TRANSACTION));
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

	protected void processContextForWorkCenterAttachments(
			DeliveryHandlerContext context, UserInfoType userInfo)
			throws Exception {
		AnnotatedLogger log = context.getLog();
		APDDeliveryContextType apdDeliveryContext = context
				.getApdDeliveryContext();

		if (log.isLoggable(Level.FINE)) {
			log.fine("Inbound payload [" + context.getInboundEnvelope() + "]");
			log.fine("Outboundpayload [" + context.getOutboundEnvelope() + "]");
			log.fine(context.getIsReDispatch() ? "Redispatch" : "Dispatch");
		}

		final AttachmentInfoDocument attachmentInfoDocument = AttachmentInfoDocument.Factory
				.newInstance();
		final AttachmentInfoType wcAttachmentInfo = attachmentInfoDocument
				.addNewAttachmentInfo();
		final AttachmentInfoListType wcAttachmentInfoList = wcAttachmentInfo
				.addNewAttachmentInfoList();
		AttachmentItemType drAttachmentItem = null;
		final String insuranceCompanyCode = apdDeliveryContext
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode();
		final String workItemId = apdDeliveryContext
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getWorkItemId();
		final UserInfoType targetUserInfo = apdDeliveryContext
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getTargetUserInfo().getUserInfo();

		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(targetUserInfo);

		boolean isSupplement = apdDeliveryContext.getMessageType().equals(
				SUPPLEMENT);

		addSupplementReport(context.getApdDeliveryContextDocument(),
				wcAttachmentInfoList, context.getApdCommonInfo(),
				context.getWorkItemId(), isSupplement);

		drAttachmentItem = wcAttachmentInfoList.addNewAttachmentItem();

		attachDr(context.getInboundEnvelope(), targetUserInfo, log,
				drAttachmentItem, insuranceCompanyCode, workItemId);
		if (log.isLoggable(Level.FINE)) {
			log.fine("Attached DR to MEDoc");
		}

		int attItemCnt = 0;
		if (gatherRequestStatusFrom(apdDeliveryContext) != CANCEL
				&& gatherRequestStatusFrom(apdDeliveryContext) != COMPLETE) {

			attItemCnt = attachArtifacts(context.getInboundEnvelope(), log,
					wcAttachmentInfoList, workItemId);

			// Attach Vehicle Details
			AdditionalAppraisalAssignmentInfoDocument vehicleDetailsInfoDoc = attachVehicleDetailsInfo(
					context.getInboundEnvelope(), log, workItemId);
			if (log.isLoggable(Level.FINE)) {
				log.fine("Return from attachVehicleDetailsInfo, vehicleDetailsInfoDoc: [ "
						+ vehicleDetailsInfoDoc.toString() + " ] ");
			}
			if (vehicleDetailsInfoDoc != null) {
				// Add new ME body for Vehicle Details
				addVehicleDetailsBodyToMeDoc(context.getOutboundEnvelope(),
						vehicleDetailsInfoDoc);
			}
		}

		addAttachmentBodyToMeDoc(context.getOutboundEnvelope(),
				context.getIsReDispatch(), attachmentInfoDocument);

	}

	private void addSupplementReport(APDDeliveryContextDocument context,
			AttachmentInfoListType wcAttachmentInfoList,
			BaseAPDCommonType baseAPDCommonType, String workItemId,
			boolean isSupplement) throws Exception {

		if (!isSupplement)
			return;

		AssignmentEmailDeliveryUtils assignmentEmailDeliveryUtils = AssignmentEmailDeliveryUtils
				.getInstance();

		AssignmentServiceContext assignmentServiceContext = assignmentEmailDeliveryUtils
				.createAssignmentServiceContext(context, new ArrayList(0));

		MitchellEnvelopeDocument supplementXmlDoc = handlerUtils
				.retrieveSupplementRequestXMLDocAsMEDoc(
						mapToAppraisalAssignmentDTO(assignmentServiceContext),
						workItemId);

		if (supplementXmlDoc == null)
			return;

		MitchellEnvelopeHelper envelopeHelper = new MitchellEnvelopeHelper(
				supplementXmlDoc);

		String docStoreId = envelopeHelper
				.getEnvelopeContextNVPairValue("SupplementRequestDocStoreId");

		logger.info("DaytonaSupp - docStoreId: " + docStoreId);

		AttachmentItemType supplementAttachment = wcAttachmentInfoList
				.addNewAttachmentItem();

		supplementAttachment.setActualFileName("LineAnnotationReport.txt");
		supplementAttachment.setAttachmentId(UUIDFactory.getInstance()
				.getUUID());
		supplementAttachment.setDateAdded(Calendar.getInstance());
		supplementAttachment.setDocStoreFileReference(docStoreId);
		supplementAttachment.setStatus(0);
		supplementAttachment.setAttachmentType(ANNOTATION_XML);

	}

	private AssignmentDeliveryServiceDTO mapToAppraisalAssignmentDTO(
			AssignmentServiceContext context) {

		AssignmentDeliveryServiceDTO dto = new AssignmentDeliveryServiceDTO();
		if (context.getDrpUserInfo() != null) {
			dto.setDrpUserInfo(context.getDrpUserInfo());
		}
		if (context.getUserInfo() != null) {
			dto.setUserInfo(context.getUserInfo());
		}
		if (context.getMitchellEnvDoc() != null) {
			dto.setMitchellEnvDoc(context.getMitchellEnvDoc());
		}
		if (!checkForNullOrBlank(context.getWorkAssignmentId())) {
			dto.setWorkAssignmentId(context.getWorkAssignmentId());
		}

		dto.setDrp(context.isDrp());
		dto.setNonNetWorkShop(context.isNonNetWorkShop());
		return dto;
	}

	private boolean checkForNullOrBlank(final String string) {
		boolean status = false;

		if (string == null || "".equals(string)) {
			status = true;
		}
		return status;
	}

	protected boolean allowBMSAndAttachmentInMitchellEnvelope(final int status) {
		if (status == COMPLETE) {
			return false; // Do not add attachment and BMS for COMPLETED
							// assignment
		} else {
			return true;
		}
	}
}