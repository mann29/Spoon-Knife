package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MICommonException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoDocument;
import com.mitchell.common.types.AttachmentInfoListType;
import com.mitchell.common.types.AttachmentInfoType;
import com.mitchell.common.types.AttachmentItemType;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.ActivityStatusType;
import com.mitchell.schemas.DefinitionType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.WorkProcessUpdateMessage;
import com.mitchell.schemas.WorkProcessUpdateMessageDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AssociatedAttachmentsType;
import com.mitchell.schemas.appraisalassignment.VehicleType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.ApdIntAssDelHandler;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.documentstore.dto.PutDocResponse;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Aug 9, 2010
 *         <p/>
 *         <b>NOTE: Clients should not consume this class directly.</b> It is
 *         recommended that clients use this class via the
 *         {@link com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.ApdIntAssDelHandler
 *         ApdIntegrationAssignmentDeliveryHandler} interface.
 */
public final class ApdIntAssDelHandlerImpl extends AbstractMsgBusDelHndlr
		implements ApdIntAssDelHandler {

	protected static final String APPRAISAL_ASSIGNMENT = "AppraisalAssignment";
	protected static final String SUPPLEMENT_ASSIGNMENT = "SupplementAssignment";
	protected static final String APD_RC_DISPATCH_TRANSACTION = "106804";
	protected static final String APD_RC_REDISPATCH_TRANSACTION = "106805";
	protected static final String APD_RC_CANCEL_TRANSACTION = "106806";
	protected static final String APD_RC_COMPLETED_TRANSACTION = "106815";
	private static final String PLATFORM_FILE_TYPE_IMAGE = "IMAGE";
	private static final String WORKCENTER_FILE_TYPE_IMG = "IMG";

	private static final Logger logger = Logger
			.getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.ApdIntAssDelHandlerImpl");

	protected long getTargetColaboratorIdFromContext(
			final APDDeliveryContextType context) throws MICommonException,
			MitchellException {
		try {
			final CrossOverUserInfoDocument crossOverUserInfo = fishOutXOverInfo(context);
			if (mLogger.isLoggable(Level.FINE)) {
				mLogger.fine("Crossover user info:"
						+ String.valueOf(crossOverUserInfo));
			}
			final long targetColaboratorId = getTargetColaboratorId(crossOverUserInfo);
			return targetColaboratorId;
		} catch (final RemoteException e) {
			throw new MitchellException(CLASS_NAME, CALLER_METHOD,
					e.getMessage());
		}
	}

	protected void resolveWorkProcessType(final boolean isSupplement,
			final DefinitionType definition) {
		if (isSupplement) {
			definition.setType(SUPPLEMENT_ASSIGNMENT);
		} else {
			definition.setType(APPRAISAL_ASSIGNMENT);
		}
	}

	protected String getTargetCollaboratorRole() {
		return SHOP;
	}

	protected String findUserIdforTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (noDRPInfoFound ? " " : apdCommonInfo.getTargetDRPUserInfo()
				.getUserInfo().getUserID());
	}

	protected String findUserCoCdforTargetUserInfo(
			final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound) {
		return (noDRPInfoFound ? " " : apdCommonInfo.getTargetDRPUserInfo()
				.getUserInfo().getOrgCode());
	}

	/**
	 * @param outboundPayload
	 * @param isReDispatch
	 * @param attachmentInfoDocument
	 */
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

	/**
	 * @param inboundPayload
	 * @param userInfo
	 * @param log
	 * @param drAttachmentItem
	 * @param insuranceCompanyCode
	 * @param workItemId
	 * @throws AssignmentDeliveryException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 * @throws MitchellException
	 */
	private void attachDr(final MitchellEnvelopeHelper inboundPayload,
			final UserInfoType userInfo, final AnnotatedLogger log,
			final AttachmentItemType drAttachmentItem,
			final String insuranceCompanyCode, final String workItemId)
			throws AssignmentDeliveryException, UnsupportedEncodingException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException,
			MitchellException, Exception {
		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(userInfo);
		long docId = 0;
		File drFile = null;
		String drActualFileName = "";
		try {
			drFile = drBuilder.createDispatchReport(userInfoDocument,
					workItemId, inboundPayload.getDoc());
			drActualFileName = drFile.getName();
			final PutDocResponse drDocStoreResponse = documentStoreClientProxy
					.putDocument(drFile.getAbsolutePath(),
							insuranceCompanyCode,
							AssignmentDeliveryConstants.APPLICATION_NAME,
							AssignmentDeliveryConstants.APPLICATION_NAME,
							MIME_TEXT_TYPE, !DUPLICATES_ALLOWED);

			if (drDocStoreResponse.getdocid() > 0) {
				docId = drDocStoreResponse.getdocid();
			}
			if (log.isLoggable(Level.INFO)) {
				log.info("ApdIntAssDelHandlerImpl:: Attached AssignmentArtifact, Dispatch Report Filename =[ "
						+ drActualFileName + " ]");
				log.info("ApdIntAssDelHandlerImpl:: Stored dispatch report in docstore. DocStore id =["
						+ docId + " ]");
			}
			// Attach Dispatch Report into drAttachmentItem
			attacheDRFileToDRAttachementItem(log, drAttachmentItem, drFile,
					drDocStoreResponse);

		} catch (MitchellException me) {
			log.severe("ApdIntAssDelHandlerImpl:: attachDr, throwing Mitchell exception.");
			throw me;

		} catch (Exception ex) {
			// TODO: handle exception
			// --------------- need catch block for all exception types ------
			log.severe("ApdIntAssDelHandlerImpl:: attachDr, throwing general exception.");
			throw ex;

		} finally {

			// CLEANUP Dispatch Report File
			if (drFile != null && docId > 0) {
				cleanUpFileIfPresent(drFile, "Dispatch Report File");
			}
		}
	}

	private void attacheDRFileToDRAttachementItem(final AnnotatedLogger log,
			final AttachmentItemType drAttachmentItem, final File drFile,
			final PutDocResponse drDocStoreResponse) {
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
			log.info("ApdIntAssDelHandlerImpl.attachDr - Set all drAttachmentItem properties of dispatch report completed. [ "
					+ drDocStoreResponse.getdocid() + " ].");
		}
	}

	/**
	 * @param inboundPayload
	 * @param log
	 * @param wcAttachmentInfoList
	 * @param workItemId
	 * @return attachItemCount
	 * @throws MitchellException
	 */
	private int attachArtifacts(final MitchellEnvelopeHelper inboundPayload,
			final AnnotatedLogger log,
			final AttachmentInfoListType wcAttachmentInfoList,
			final String workItemId) throws MitchellException {

		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
		int attachItemCount = 0;

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

						if (log.isLoggable(Level.INFO)) { // TODO: Temp Debug
															// Remove Later
							log.info("Set all the properties for file Attachment: # [ "
									+ i + ", docStoreID= " + docStoreID + " ].");
							log.info("Set all the properties for file Attachment: # [ "
									+ i + ", fileName= " + fileName + " ].");
							log.info("Set all the properties for file Attachment: # [ "
									+ i + ", fileType= " + fileType + " ].");
							log.info("Set all the properties for file Attachment: # [ "
									+ i
									+ ", AltAttachmentType= "
									+ assocAttachments.getFileAttachments()
											.getFileAttachmentArray(i)
											.getAltAttachmentType() + " ].");
						}
					}
				}
			}

		} catch (Exception e) {
			logger.warning(AppUtilities.getStackTraceString(e, true));
		}
		return attachItemCount;

	}

	/**
	 * 
	 * @param inboundPayload
	 * @param log
	 * @param workItemId
	 * @throws MitchellException
	 */
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

					// TODO: Debug -- remove later
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

	/**
	 * @param log
	 * @param aaaInfoDocOut
	 * @param aaaInfoDoc
	 */
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

	/**
	 * @param outboundPayload
	 * @param additionalAppraisalAssignmentInfoDocument
	 */
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

		// WC/RC - AttachmentInfoDocument
		final AttachmentInfoDocument attachmentInfoDocument = AttachmentInfoDocument.Factory
				.newInstance();
		final AttachmentInfoType wcAttachmentInfo = attachmentInfoDocument
				.addNewAttachmentInfo();
		final AttachmentInfoListType wcAttachmentInfoList = wcAttachmentInfo
				.addNewAttachmentInfoList();
		AttachmentItemType drAttachmentItem = null;
		final String insuranceCompanyCode = context
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode();
		final String workItemId = context.getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getWorkItemId();
		final UserInfoType targetUserInfo = context
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getTargetUserInfo().getUserInfo();

		// -- Check Required CustomSetting Flag
		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(targetUserInfo);
		final boolean isDispatchReportRequired = isTrue(assignmentDeliveryUtils
				.getUserCustomSetting(
						userInfoDocument,
						AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP_FLAG));
		if (log.isLoggable(Level.FINE)) {
			log.fine("CustomSetting isDispatchReportRequired: "
					+ isDispatchReportRequired);
		}

		if (isDispatchReportRequired) {
			drAttachmentItem = wcAttachmentInfoList.addNewAttachmentItem();
			attachDr(inboundPayload, targetUserInfo, log, drAttachmentItem,
					insuranceCompanyCode, workItemId);
			if (log.isLoggable(Level.FINE)) {
				log.fine("Attached DR to MEDoc");
			}
		}

		// Attach File Attachments and Vehicle Details to outboundPayload
		// from AdditionalAppraisalAssignmentInfoDocument included in
		// inboundPayload
		int attItemCnt = 0;
		if (gatherRequestStatusFrom(context) != CANCEL
				&& gatherRequestStatusFrom(context) != COMPLETE) {

			attItemCnt = attachArtifacts(inboundPayload, log,
					wcAttachmentInfoList, workItemId);

			// Attach Vehicle Details
			AdditionalAppraisalAssignmentInfoDocument vehicleDetailsInfoDoc = attachVehicleDetailsInfo(
					inboundPayload, log, workItemId);
			if (log.isLoggable(Level.FINE)) {
				log.fine("Return from attachVehicleDetailsInfo, vehicleDetailsInfoDoc: [ "
						+ vehicleDetailsInfoDoc.toString() + " ] ");
			}
			if (vehicleDetailsInfoDoc != null) {
				// Add new ME body for Vehicle Details
				addVehicleDetailsBodyToMeDoc(outboundPayload,
						vehicleDetailsInfoDoc);
			}
		}

		// Add new ME body for Attachments in AttachmentInfoDocument
		if (attItemCnt > 0 || isDispatchReportRequired) {
			addAttachmentBodyToMeDoc(outboundPayload, isReDispatch,
					attachmentInfoDocument);
		}
	}

	private boolean isTrue(String value) {
		value = (null != value) ? value.toLowerCase() : "";
		return "true".equals(value) || value.indexOf("y") != -1
				|| value.indexOf("t") != -1;
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
				wpum.setActivityOperation(CANCEL_SUPPLEMENT_ASSIGNMENT);
			} else {
				wpum.setActivityOperation(CANCEL_APPRAISAL_ASSIGNMENT);
			}
		} else if (gatherRequestStatusFrom(apdDeliveryContext) == COMPLETE) {
			wpum.setActivityOperation(COMPLETE_APPRAISAL_ASSIGNMENT); // same
																		// for
																		// both
																		// original
																		// and
																		// supplement
		} else {
			if (isSupplement) {
				wpum.setActivityOperation(UPDATE_SUPPLEMENT_ASSIGNMENT);
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

	protected boolean protocolRequiresShopSupplementNotification() {
		return true;
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
