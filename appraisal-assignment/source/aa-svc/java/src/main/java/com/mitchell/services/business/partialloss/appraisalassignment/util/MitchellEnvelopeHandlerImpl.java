package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cieca.bms.AddressType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.EstimatorIDsTypeType;
import com.cieca.bms.EstimatorType;
import com.cieca.bms.IDInfoType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.VehicleDamageAssignmentType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.AssignmentScheduleType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.VehicleLocationGeoCodeType.AddressValidStatus;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.dispatchservice.ScheduleConstraintsType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.geoservice.dto.GeoResult;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class MitchellEnvelopeHandlerImpl implements MitchellEnvelopeHandler {

	private static Logger		logger		= Logger.getLogger(MitchellEnvelopeHandlerImpl.class.getName());
	private static final String	CLASS_NAME	= MitchellEnvelopeHandlerImpl.class.getName();

	private AASErrorLogUtil		errorLogUtil;

	public void setErrorLogUtil(final AASErrorLogUtil errorLogUtil) {
		this.errorLogUtil = errorLogUtil;
	}

	private AASCommonUtils	aasCommonUtils;

	public void setAasCommonUtils(final AASCommonUtils aasCommonUtils) {
		this.aasCommonUtils = aasCommonUtils;
	}

	private EstimatePackageProxy	estimatePackageProxy;

	public void setEstimatePackageProxy(final EstimatePackageProxy estimatePackageProxy) {
		this.estimatePackageProxy = estimatePackageProxy;
	}

	/**
	 * This method is a helper method which extracts
	 * AdditionalAppraisalAssignmentInfo from MitchellEnvelope Document.
	 * @return AdditionalAppraisalAssignmentInfoDocument
	 * @throws Exception in case MitchellEnvelope Document doesn't contains
	 *             AdditionalAppraisalAssignmentInfoDocument
	 */
	public AdditionalAppraisalAssignmentInfoDocument getAdditionalAppraisalAssignmentInfoDocumentFromME(
			final MitchellEnvelopeHelper mitchellEnvelopeHelper) throws Exception {
		final String METHOD_NAME = "getAdditionalAppraisalAssignmentInfoDocumentFromME";
		logger.entering(CLASS_NAME, METHOD_NAME);
		String contentString = null;
		AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument = null;

		if (mitchellEnvelopeHelper != null) {
			final EnvelopeBodyType envelopeBody = mitchellEnvelopeHelper
					.getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
			if (envelopeBody != null) {
				final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
				if (metadata != null) {
					final String xmlBeanClassname = metadata.getXmlBeanClassname();

					contentString = mitchellEnvelopeHelper.getEnvelopeBodyContentAsString(envelopeBody);

					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						logger.info("Retrieved AdditionalAppraisalAssignmentInfo from meHelper as String is:"
								+ contentString);
					}

					if (xmlBeanClassname != null
							&& xmlBeanClassname.equals(AdditionalAppraisalAssignmentInfoDocument.class.getName())) {

						additionalAppraisalAssignmentInfoDocument = AdditionalAppraisalAssignmentInfoDocument.Factory
								.parse(contentString);

						if (logger.isLoggable(java.util.logging.Level.FINE)) {
							logger.fine("Retrieved AdditionalAppraisalAssignmentInfoDocument by parsing ContentString:"
									+ additionalAppraisalAssignmentInfoDocument);
						}

					} else {
						final String errMsg = "MithcellEnvelope does not contains getAdditionalAppraisalAssignmentInfoDocumentFromME";
						throw new MitchellException(CLASS_NAME, "getAdditionalAppraisalAssignmentInfoDocumentFromME", errMsg);
					}
				}
			}
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return additionalAppraisalAssignmentInfoDocument;

	}

	public long getMOIOrgIdFromMitchellEnvelop(final MitchellEnvelopeHelper mitchellEnvelopeHelper) throws Exception {
		final String METHOD_NAME = "getMOIOrgIdFromMitchellEnvelop";
		logger.entering(CLASS_NAME, METHOD_NAME);
		long moiOrgId = -1;
		AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = getAdditionalAppraisalAssignmentInfoDocumentFromME(mitchellEnvelopeHelper);
		if (addAppAsmtInfo != null && addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo() != null
				&& addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails() != null
				&& addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails().getTempUserSelectedMOI() != null) {
			moiOrgId = addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails().getTempUserSelectedMOI()
					.getMOIOrgID();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return moiOrgId;
	}

	public boolean checkServiceCenterFromMitchellEnvelop(final MitchellEnvelopeHelper mitchellEnvelopeHelper)
			throws Exception {
		boolean isMOIServiceCenter = false;
		AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = getAdditionalAppraisalAssignmentInfoDocumentFromME(mitchellEnvelopeHelper);
		if (addAppAsmtInfo != null && addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo() != null
				&& addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails() != null
				&& addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails().getTempUserSelectedMOI() != null) {
			final String moiType = addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getMOIDetails()
					.getTempUserSelectedMOI().getMethodOfInspection();
			isMOIServiceCenter = AppraisalAssignmentConstants.MOI_DRIVE_IN.equalsIgnoreCase(moiType)
					|| AppraisalAssignmentConstants.MOI_DROP_OFF.equalsIgnoreCase(moiType)
					|| AppraisalAssignmentConstants.MOI_TOW_IN.equalsIgnoreCase(moiType);
		}
		return isMOIServiceCenter;
	}

	/**
	 * This method is a helper method which extracts CIECADocument from
	 * MitchellEnvelope Document and returns CIECADocument.
	 * @param meHelper MitchellEnvelopeHelper
	 * @return CIECADocument
	 * @throws Exception in case MitchellEnvelope Document doesn't contain
	 *             CIECADocument.
	 */
	public CIECADocument getCiecaFromME(final MitchellEnvelopeHelper meHelper) throws Exception {

		final String METHOD_NAME = "getCiecaFromME";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			logger.fine("Input Received :: MitchellEnvelopeDocument: " + meHelper.getDoc());
		}

		String contentString = null;
		CIECADocument ciecaDocument = null;

		final EnvelopeBodyType envelopeBody = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
		final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
		final String xmlBeanClassname = metadata.getXmlBeanClassname();

		try {
			contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Retrieved CIECABMSAssignmentAddRq from meHelper as String is:" + contentString);
			}

		} catch (final MitchellException mitchellException) {
			final String errMsg = "Error getting CIECABMSAssignmentAddRq ContentString from MithcellEnvelope";
			throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg, mitchellException);
		}

		if (xmlBeanClassname == null || xmlBeanClassname.equals(CIECADocument.class.getName())) {
			try {
				ciecaDocument = CIECADocument.Factory.parse(contentString);

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("CIECADocument obtained by parsing contectString is :" + ciecaDocument);
				}

			} catch (final Exception exception) {
				final String errMsg = "Error parsing and creating object of CIECADocument";
				throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg, exception);
			}
		} else if (xmlBeanClassname == null || xmlBeanClassname.equals(AssignmentAddRqDocument.class.getName())) {
			AssignmentAddRqDocument assignmentAddRqDoc = null;
			try {
				assignmentAddRqDoc = AssignmentAddRqDocument.Factory.parse(contentString);
				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("AssignmentAddRqDocument obtained by parsing contectString is :" + assignmentAddRqDoc);
				}
			} catch (final Exception exception) {
				final String errMsg = "Error parsing and creating object of AssignmentAddRqDocument";
				throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg, exception);
			}
			ciecaDocument = CIECADocument.Factory.newInstance();
			final CIECADocument.CIECA cieca = ciecaDocument.addNewCIECA();
			final AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = assignmentAddRqDoc.getAssignmentAddRq();
			cieca.setAssignmentAddRq(assignmentAddRq);
			logger.info("Updated CIECA with assignmentAddRq");
		} else {
			final String errMsg = "MithcellEnvelope does not contains CIECA or AssignmentAddRq.";
			throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg);
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return ciecaDocument;
	}

	/**
	 * Updates MitchellEnvelopeDocument to remove Estimator ID and Estimator's
	 * info. Saves updated MitchellEnvelopeDocument to EstimatePackage Service.
	 * @param documentId Document ID of EstimatePackage Service.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void unScheduleMitchellEnvelope(final Long documentId, final UserInfoDocument logdInUsrInfo) throws Exception {
		final String METHOD_NAME = "unScheduleMitchellEnvelope";

		logger.entering(CLASS_NAME, METHOD_NAME);

		long tcn = 0;
		CIECADocument ciecaDocument = null;
		final long docId = documentId.longValue();
		AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = null;
		MitchellEnvelopeDocument mitchellEnvDoc = null;
		MitchellEnvelopeHelper meHelper = null;
		AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfoDoc = null;
		AdditionalTaskConstraintsDocument additinalTaskConstraintsDocument = null;

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received DocumentId : " + docId);
		}

		try {

			// get BMS through Estimate Package Serive
			final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appAsmtDTO = this.estimatePackageProxy
					.getAppraisalAssignmentDocument(docId);

			// fetching object of "AssignmentAddRqDocument" from DTO & hence
			// from Cieca & setting it into a new "AssignmentAddRqDocument"
			// object
			if (appAsmtDTO != null) {

				mitchellEnvDoc = MitchellEnvelopeDocument.Factory.parse(appAsmtDTO.getAppraisalAssignmentMEStr());

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Fetched MitchellEnvelopeDocument from EstimatePackage Service:" + mitchellEnvDoc);
				}

				meHelper = new MitchellEnvelopeHelper(mitchellEnvDoc);
				ciecaDocument = getCiecaFromME(meHelper);

				assignmentAddRq = ciecaDocument.getCIECA().getAssignmentAddRq();

				// Updating Estimator related info from newly created
				// "AssignmentAddRqDocument" object
				if (assignmentAddRq != null) {
					if (assignmentAddRq.isSetVehicleDamageAssignment()) {

						// When Re-assignment scenario
						logger.info("Updating Estimator for exising Vehicle Damage Assignment");

						if (assignmentAddRq.getVehicleDamageAssignment().isSetEstimatorIDs()) {

							logger.info("Unset Estimator info from VehicleDamageAssignment");

							final EstimatorIDsTypeType estimatorIDs = assignmentAddRq.getVehicleDamageAssignment()
									.getEstimatorIDs();

							if (estimatorIDs != null && estimatorIDs.isSetCurrentEstimatorID()) {
								estimatorIDs.unsetCurrentEstimatorID();
							}
						}

					}

					// removing estimator from MitchellEnvelope

					final AdminInfoType adminInfo = assignmentAddRq.getAdminInfo();
					final int estimatorArrSize = adminInfo.sizeOfEstimatorArray();
					if (estimatorArrSize >= 0) {
						for (int i = 0; i < estimatorArrSize; i++) {
							adminInfo.removeEstimator(0);
						}
					}

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("CIECA from MitchellEnvelope Document" + ciecaDocument.toString());
					}

					// Adding latest updated Cieca to MitchellEnvelope Document.
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
					meHelper.updateEnvelopeBodyContent(envBodyType, ciecaDocument);

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("CiecaUpdateMEDOC" + meHelper.getDoc());
					}
				}

				// fetching AdditionalAppraisalAssignmentInfoDocument from
				// MitchellEnvelope Document & then updating the Notification
				// Details from AdditionalAppraisalAssignmentInfoDocument
				addAppAsmtInfoDoc = getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
				if (addAppAsmtInfoDoc != null
						&& addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetNotificationDetails()) {
					addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo().unsetNotificationDetails();
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
					if (null != envBodyType) {
						meHelper.updateEnvelopeBodyContent(envBodyType, addAppAsmtInfoDoc);
					} else {
						meHelper.addNewEnvelopeBody("AdditionalAppraisalAssignmentInfo", addAppAsmtInfoDoc,
							"AdditionalAppraisalAssignmentInfo");
					}

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("AdditionalAppraisalAssignmentInfoUpdateMEDOC" + meHelper.getDoc());
					}

				}

				// fetching AdditionalTaskConstraintsDocument from
				// MitchellEnvelope Document & then updating the ScheduleInfo
				// details from AdditionalTaskConstraintsDocument
				additinalTaskConstraintsDocument = getAdditionalTaskConstraintsFromME(meHelper);
				if (additinalTaskConstraintsDocument != null
						&& additinalTaskConstraintsDocument.getAdditionalTaskConstraints().isSetScheduleInfo()) {
					if (additinalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
							.isSetScheduledDateTime()) {
						additinalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
								.unsetScheduledDateTime();
					}
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("AdditionalTaskConstraints");
					if (null != envBodyType) {
						meHelper.updateEnvelopeBodyContent(envBodyType, additinalTaskConstraintsDocument);
					} else {
						meHelper.addNewEnvelopeBody("AdditionalTaskConstraints", additinalTaskConstraintsDocument,
							"AdditionalTaskConstraints");
					}
				}

				// fetching tcn from the DTO
				tcn = appAsmtDTO.getEstPkgAppAsgDoc().getEstPkgAppraisalAssignment().getTCN();

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("New ME Document after removing Schedule Information is :" + mitchellEnvDoc);
				}

				// updating the MitchellEnvelopeDocument through
				// EstimatePackageService
				this.estimatePackageProxy.updateAppraisalAssignmentDocument(docId, meHelper.getDoc(), logdInUsrInfo,
					Long.valueOf(tcn));

				logger.info("Updated the MitchellEnvelopeDocument through Estimate Package Serive");
			}

		} catch (final Exception exception) {

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS_MSG, "Document Id: " + docId + ", TCN:: "
						+ tcn, exception, logdInUsrInfo, null, null);

		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Updates MitchellEnvelopeDocument to remove Estimator ID and Estimator's
	 * info. Saves updated MitchellEnvelopeDocument to EstimatePackage Service.
	 * @param documentId Document ID of EstimatePackage Service.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void unScheduleMitchellEnvelope(final Long documentId, final UserInfoDocument logdInUsrInfo,
			ItineraryViewDocument itineraryXML) throws Exception {
		final String METHOD_NAME = "unScheduleMitchellEnvelope";

		logger.entering(CLASS_NAME, METHOD_NAME);

		long tcn = 0;
		CIECADocument ciecaDocument = null;
		final long docId = documentId.longValue();
		AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = null;
		MitchellEnvelopeDocument mitchellEnvDoc = null;
		MitchellEnvelopeHelper meHelper = null;
		AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfoDoc = null;
		AdditionalTaskConstraintsDocument additinalTaskConstraintsDocument = null;

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("unScheduleMitchellEnvelope Input Received DocumentId : " + docId);
		}

		try {

			// get BMS through Estimate Package Serive
			final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appAsmtDTO = this.estimatePackageProxy
					.getAppraisalAssignmentDocument(docId);

			// fetching object of "AssignmentAddRqDocument" from DTO & hence
			// from Cieca & setting it into a new "AssignmentAddRqDocument"
			// object
			if (appAsmtDTO != null) {

				mitchellEnvDoc = MitchellEnvelopeDocument.Factory.parse(appAsmtDTO.getAppraisalAssignmentMEStr());

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Fetched MitchellEnvelopeDocument from EstimatePackage Service:" + mitchellEnvDoc);
				}

				meHelper = new MitchellEnvelopeHelper(mitchellEnvDoc);
				ciecaDocument = getCiecaFromME(meHelper);

				assignmentAddRq = ciecaDocument.getCIECA().getAssignmentAddRq();

				// Updating Estimator related info from newly created
				// "AssignmentAddRqDocument" object
				if (assignmentAddRq != null) {
					if (assignmentAddRq.isSetVehicleDamageAssignment()) {

						// When Re-assignment scenario
						logger.info("Updating Estimator for exising Vehicle Damage Assignment");

						if (assignmentAddRq.getVehicleDamageAssignment().isSetEstimatorIDs()) {

							logger.info("Unset Estimator info from VehicleDamageAssignment");

							final EstimatorIDsTypeType estimatorIDs = assignmentAddRq.getVehicleDamageAssignment()
									.getEstimatorIDs();

							if (estimatorIDs != null && estimatorIDs.isSetCurrentEstimatorID()) {
								estimatorIDs.unsetCurrentEstimatorID();
							}
						}

					}

					// removing estimator from MitchellEnvelope

					final AdminInfoType adminInfo = assignmentAddRq.getAdminInfo();
					final int estimatorArrSize = adminInfo.sizeOfEstimatorArray();
					if (estimatorArrSize >= 0) {
						for (int i = 0; i < estimatorArrSize; i++) {
							adminInfo.removeEstimator(0);
						}
					}

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("CIECA from MitchellEnvelope Document" + ciecaDocument.toString());
					}

					// Adding latest updated Cieca to MitchellEnvelope Document.
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
					meHelper.updateEnvelopeBodyContent(envBodyType, ciecaDocument);

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("CiecaUpdateMEDOC" + meHelper.getDoc());
					}
				}

				/*
				 * fetching AdditionalAppraisalAssignmentInfoDocument from
				 * MitchellEnvelope Document & then updating the Notification
				 * Details from AdditionalAppraisalAssignmentInfoDocument
				 */
				addAppAsmtInfoDoc = getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
				if (addAppAsmtInfoDoc != null
						&& addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo().isSetNotificationDetails()) {
					addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo().unsetNotificationDetails();
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
					if (null != envBodyType) {
						meHelper.updateEnvelopeBodyContent(envBodyType, addAppAsmtInfoDoc);
					} else {
						meHelper.addNewEnvelopeBody("AdditionalAppraisalAssignmentInfo", addAppAsmtInfoDoc,
							"AdditionalAppraisalAssignmentInfo");
					}

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("AdditionalAppraisalAssignmentInfoUpdateMEDOC" + meHelper.getDoc());
					}

				}

				/*
				 * fetching AdditionalTaskConstraintsDocument from
				 * MitchellEnvelope Document & then updating the ScheduleInfo
				 * details from AdditionalTaskConstraintsDocument
				 */
				additinalTaskConstraintsDocument = getAdditionalTaskConstraintsFromME(meHelper);
				if (additinalTaskConstraintsDocument != null
						&& additinalTaskConstraintsDocument.getAdditionalTaskConstraints().isSetScheduleInfo()) {

					if (additinalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
							.isSetScheduledDateTime()) {

						additinalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
								.unsetScheduledDateTime();
					}

				}

				if (additinalTaskConstraintsDocument != null
						&& additinalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleConstraints() != null) {

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("Reset ScheduleConstraints detail ");
					}

					ScheduleConstraintsType constraintsType = additinalTaskConstraintsDocument
							.getAdditionalTaskConstraints().getScheduleConstraints();

					AssignmentScheduleType assignmentScheduleType = itineraryXML.getItineraryView().getAssignmentSchedule();

					if ((null != itineraryXML) && (null != itineraryXML.getItineraryView())
							&& (null != itineraryXML.getItineraryView().getAssignmentSchedule())) {

						// Checking Urgency
						if (null != assignmentScheduleType.getUrgency()) {
							constraintsType.setPriority(aasCommonUtils.priorityTypeToString(assignmentScheduleType
									.getUrgency().intValue()));
						}

						// Checking PreferredScheduleDate
						if (null != assignmentScheduleType.getPreferredScheduleDate()) {
							constraintsType.setPreferredScheduleDate(assignmentScheduleType.getPreferredScheduleDate());
						}

						// Checking PreferredScheduleEndTime
						if (null != assignmentScheduleType.getPreferredScheduleEndTime()) {
							constraintsType
									.setPreferredScheduleEndTime(assignmentScheduleType.getPreferredScheduleEndTime());
						} else {
							if (null != constraintsType.getPreferredScheduleEndTime())
								constraintsType.unsetPreferredScheduleEndTime();
						}

						// Checking PreferredScheduleStartTime
						if (null != assignmentScheduleType.getPreferredScheduleStartTime()) {
							constraintsType.setPreferredScheduleStartTime(assignmentScheduleType
									.getPreferredScheduleStartTime());
						} else {
							if (null != constraintsType.getPreferredScheduleStartTime())
								constraintsType.unsetPreferredScheduleStartTime();
						}
					}

				}

				EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("AdditionalTaskConstraints");

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Updated additinalTaskConstraintsDocument : " + additinalTaskConstraintsDocument);
				}

				if (null != envBodyType) {
					meHelper.updateEnvelopeBodyContent(envBodyType, additinalTaskConstraintsDocument);
				} else {
					meHelper.addNewEnvelopeBody("AdditionalTaskConstraints", additinalTaskConstraintsDocument,
						"AdditionalTaskConstraints");
				}

				// fetching tcn from the DTO
				tcn = appAsmtDTO.getEstPkgAppAsgDoc().getEstPkgAppraisalAssignment().getTCN();

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("New ME Document after removing Schedule Information is :" + mitchellEnvDoc);
				}

				// updating the MitchellEnvelopeDocument through
				// EstimatePackageService
				this.estimatePackageProxy.updateAppraisalAssignmentDocument(docId, meHelper.getDoc(), logdInUsrInfo,
					Long.valueOf(tcn));

				logger.info("Updated the MitchellEnvelopeDocument through Estimate Package Serive");
			}

		} catch (final Exception exception) {

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_UNSCHEDULEMEBMS_MSG, "Document Id: " + docId + ", TCN:: "
						+ tcn, exception, logdInUsrInfo, null, null);

		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * This method is a helper method which extracts AdditionalTaskConstraints
	 * from MitchellEnvelope Document.
	 * @return AdditionalTaskConstraintsDocument
	 * @throws Exception in case MitchellEnvelope Document doesn't contains
	 *             AdditionalTaskConstraintsDocument
	 */
	public AdditionalTaskConstraintsDocument getAdditionalTaskConstraintsFromME(final MitchellEnvelopeHelper meHelper)
			throws Exception {

		final String METHOD_NAME = "getAdditionalTaskTaskConstraintsFromME";
		logger.entering(CLASS_NAME, METHOD_NAME);

		String contentString = null;
		com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = null;
		final EnvelopeBodyType envelopeBody = meHelper.getEnvelopeBody("AdditionalTaskConstraints");

		if (envelopeBody != null) {

			final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
			if (metadata != null) {

				final String xmlBeanClassname = metadata.getXmlBeanClassname();

				contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Retrieved AdditionalTaskConstraints from meHelper as String is:" + contentString);
				}

				if (xmlBeanClassname != null && xmlBeanClassname.equals(AdditionalTaskConstraintsDocument.class.getName())) {

					additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory.parse(contentString);

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("Retrieved AdditionalTaskConstraintsDocument by parsing ContentString:"
								+ additionalTaskConstraintsDocument);
					}

				} else {
					final String errMsg = "MithcellEnvelope does not contains AdditionalTaskConstraintsDocument";
					throw new MitchellException(CLASS_NAME, "getAdditionalTaskTaskConstraintsFromME", errMsg);
				}
			}
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return additionalTaskConstraintsDocument;
	}

	/**
	 * This method is helper method which saves MitchellEnvelope to
	 * EstimatePackage Service.
	 * @param claimId ClaimId of Estimate
	 * @param claimExposureId ClaimExposureID of Estimate
	 * @return Document ID of EstimatePackage Service.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public Long saveBMS(final long claimId, final long claimExposureId, final long relatedEstimateDocID,
			final MitchellEnvelopeDocument mitchellEnvDoc, final UserInfoDocument logdInUsrInfo) throws Exception {

		final String METHOD_NAME = "saveBMS";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			logger.fine("Input Received :: claimId: " + claimId + "\t claimExposureId: " + claimExposureId
					+ "\t relatedEstimateDocID: " + relatedEstimateDocID);
		}

		final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(mitchellEnvDoc);

		Long documentId = null;
		Long relatedEstimateDocId = null;
		String companyCode = null;

		try {
			if (relatedEstimateDocID > 0) {
				relatedEstimateDocId = Long.valueOf(relatedEstimateDocID);
			}
			companyCode = meHelper
					.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_COMPANY_CODE);
			documentId = this.estimatePackageProxy.saveAppraisalAssignmentDocument(mitchellEnvDoc, logdInUsrInfo,
				companyCode, claimId, claimExposureId, relatedEstimateDocId);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Mitchell Envelope saved successfully. DocId= " + documentId);
			}

		} catch (final Exception exception) {
			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEBMS, CLASS_NAME, METHOD_NAME,
				ErrorLoggingService.SEVERITY.FATAL, AppraisalAssignmentConstants.ERROR_SAVE_AA_SAVEBMS_MSG, "claimId="
						+ claimId + ", claimExposureId=" + claimExposureId + ", relatedEstimateDocID="
						+ relatedEstimateDocID, exception, logdInUsrInfo, null, null);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return documentId;
	}

	/**
	 * This method is helper method. Its main task is to retrieve work
	 * assignment, documentID and TCN using workAssignmentTaskID and call
	 * EstimatePackage Service to update MitchellEnvelope Document.
	 * @param documentId Document ID of EstimatePackage Service.
	 * @param tcn TCN number of request.
	 * @throws MitchellException
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void updateBMS(final Long documentId, final Long tcn, final MitchellEnvelopeDocument mitchellEnvDoc,
			final UserInfoDocument logdInUsrInfo) throws MitchellException {

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Input Received :: documentId: " + documentId + " tcn: " + tcn);
		}

		// updating BMS
		this.estimatePackageProxy.updateAppraisalAssignmentDocument(documentId.longValue(), mitchellEnvDoc, logdInUsrInfo,
			tcn);

	}

	/**
	 * Updates BMS to add Estimator ID and Estimator's info. Saves updated BMS
	 * to EstimatePackage Service.
	 * @param documentId Document ID of EstimatePackage Service.
	 * @param assigneeInfo Estimator's UserInfo Document to whom this assignment
	 *            is assigned.
	 * @throws Exception Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void assignMitchellEnvelope(final Long documentId, final String dispatchCenter,
			final UserInfoDocument assigneeInfo, final UserDetailDocument assigneeUserDetail, final String userType,
			final Calendar scheduleDateTime, final UserInfoDocument logdInUsrInfo, final String workItemID) throws Exception {

		final String METHOD_NAME = "assignMitchellEnvelope";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.FINE)) {
			StringBuilder localMethodParams = new StringBuilder();
			localMethodParams.append("DocumentId: " + documentId).append("\n dispatchCenter: " + dispatchCenter)
					.append("\nassigneeInfo: " + assigneeInfo).append("\nassigneeUserDetail: " + assigneeUserDetail)
					.append("\nuserType: " + userType);
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		MitchellEnvelopeHelper mitchellEnvelopeHelper = null;
		long tcn;
		CIECADocument ciecaDocument = null;
		final long docId = documentId.longValue();
		AssignmentAddRqDocument assignAddRqDoc = null;
		MitchellEnvelopeDocument mitchellEnvDoc = null;
		AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfoDoc = null;
		com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = null;
		try {

			// get BMS through Estimate Package Service
			final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appAsmtDTO = this.estimatePackageProxy
					.getAppraisalAssignmentDocument(docId);

			if (logger.isLoggable(java.util.logging.Level.INFO)) {
				logger.info("Successfully fetched AppraisalAssignmentDTO from Estimate Package Service for Document Id :"
						+ docId);
			}

			// fetching object of "AssignmentAddRqDocument" from DTO & hence
			// from Cieca & setting it into a new "AssignmentAddRqDocument"
			// object
			mitchellEnvDoc = MitchellEnvelopeDocument.Factory.parse(appAsmtDTO.getAppraisalAssignmentMEStr());

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("Fetched MitchellEnvelopeDocument from EstimatePackage Service:" + mitchellEnvDoc);
			}

			mitchellEnvelopeHelper = new MitchellEnvelopeHelper(mitchellEnvDoc);
			ciecaDocument = getCiecaFromME(mitchellEnvelopeHelper);

			if (assigneeInfo != null && assigneeUserDetail != null) {
				ciecaDocument = getCiecaFromME(mitchellEnvelopeHelper);
				assignAddRqDoc = AssignmentAddRqDocument.Factory.newInstance();
				final AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = ciecaDocument.getCIECA()
						.getAssignmentAddRq();

				// Updating Estimator related info from newly created
				// "AssignmentAddRqDocument" object
				if (assignmentAddRq.isSetVehicleDamageAssignment()) {

					logger.info("Updating Estimator for exising Vehicle Damage Assignment");

					if (!assignmentAddRq.getVehicleDamageAssignment().isSetEstimatorIDs()) {
						// When assign scenario
						logger.info("Setting estimator in VehicleDamageAssignment from asigneeInfo");

						assignmentAddRq.getVehicleDamageAssignment().addNewEstimatorIDs();
					}

				} else {
					logger.info("Updating Estimator for new Vehicle Damage Assignment");
					assignmentAddRq.addNewVehicleDamageAssignment().addNewEstimatorIDs();
				}

				final EstimatorIDsTypeType estimatorIDs = assignmentAddRq.getVehicleDamageAssignment().getEstimatorIDs();
				estimatorIDs.setCurrentEstimatorID(assigneeInfo.getUserInfo().getUserID());

				logger.info("Estimator for Vehicle Damage Assignment is set successfully!");

				IDInfoType routingIDInfo = estimatorIDs.getRoutingIDInfo();

				if (routingIDInfo == null) {
					routingIDInfo = IDInfoType.Factory.newInstance();
				}
				routingIDInfo.setIDQualifierCode("WAGroupID");
				routingIDInfo.setIDNum(dispatchCenter);
				estimatorIDs.setRoutingIDInfo(routingIDInfo);

				// When assign scenario

				// Creating a new estimator from the assignee information
				final EstimatorType estimator = EstimatorType.Factory.newInstance();
				final PartyType party = estimator.addNewParty();
				final com.cieca.bms.PersonInfoType personInfo = party.addNewPersonInfo();
				final com.cieca.bms.PersonNameType personName = personInfo.addNewPersonName();
				com.cieca.bms.ContactInfoType contactInfoType = null;

				// setting PersonInfoType
				if (assigneeInfo.getUserInfo().isSetFirstName()) {
					personName.setFirstName(assigneeInfo.getUserInfo().getFirstName());
				}
				if (assigneeInfo.getUserInfo().isSetLastName()) {
					personName.setLastName(assigneeInfo.getUserInfo().getLastName());
				}

				if (assigneeInfo.getUserInfo().isSetEmail() || assigneeUserDetail.getUserDetail().isSetPhone()) {
					contactInfoType = party.addNewContactInfo();
				}

				if (assigneeInfo.getUserInfo().isSetEmail()) {
					// final com.cieca.bms.CommunicationsType comm =
					// personInfo.addNewCommunications();
					final com.cieca.bms.CommunicationsType comm = contactInfoType.addNewCommunications();
					comm.setCommQualifier("EM");
					comm.setCommEmail(assigneeInfo.getUserInfo().getEmail());
				}

				if (assigneeUserDetail.getUserDetail().isSetPhone()) {
					final String phone = assigneeUserDetail.getUserDetail().getPhone();
					if (phone != null && !"".equals(phone)) {
						final com.cieca.bms.CommunicationsType comm = contactInfoType.addNewCommunications();
						comm.setCommQualifier("WP");
						comm.setCommPhone(phone);
						logger.info("Setting CommunicationType phone from AssigneeUserDetail");
					}
				}

				if (assigneeUserDetail.getUserDetail().isSetAddress()) {
					final com.mitchell.services.core.userinfo.types.AddressType addressType = assigneeUserDetail
							.getUserDetail().getAddress();
					if (addressType != null) {
						String address2 = null;
						final String address1 = addressType.getAddress1();
						if (addressType.isSetAddress2()) {
							address2 = addressType.getAddress2();
						}

						final com.cieca.bms.CommunicationsType comm = personInfo.addNewCommunications();
						comm.setCommQualifier("AL");
						final com.cieca.bms.AddressType address = comm.addNewAddress();
						address.setAddress1(address1);
						if ((address2 != null && !"".equals(address2))) {
							address.setAddress2(address2);
						}
						address.setCity(addressType.getCity());
						address.setStateProvince(addressType.getState());
						address.setPostalCode(addressType.getZip());
						address.setCountryCode(addressType.getCountry());
					}
				}

				if (UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(userType)) {
					estimator.setAffiliation("IN");
				} else if (UserTypeConstants.SHOP_TYPE.equalsIgnoreCase(userType)
						|| UserTypeConstants.DRP_SHOP_TYPE.equalsIgnoreCase(userType)) {
					estimator.setAffiliation("BS");
				} else if (UserTypeConstants.DRP_IA_TYPE.equalsIgnoreCase(userType)
						|| UserTypeConstants.IA_TYPE.equalsIgnoreCase(userType)) {
					estimator.setAffiliation("IP");
				}

				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("Updated estimator with Affiliation for isBodyShopUser value: " + userType);
				}

				final EstimatorType[] myEstimatorTypeArray = {estimator};

				// removing estimator from MitchellEnvelope
				final AdminInfoType adminInfo = assignmentAddRq.getAdminInfo();
				final int estimatorArrSize = adminInfo.sizeOfEstimatorArray();

				if (estimatorArrSize <= 0) {

					// setting the new estimator into adminInfo & hence updating
					// the newly created "AssignmentAddRqDocument"
					adminInfo.setEstimatorArray(myEstimatorTypeArray);

				} else {

					for (int i = 0; i < estimatorArrSize; i++) {
						adminInfo.removeEstimator(0);
					}

					// setting the new estimator into adminInfo & hence updating
					// the newly created "AssignmentAddRqDocument"
					adminInfo.setEstimatorArray(myEstimatorTypeArray);

				}

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Updated AdminInfo with new Estimator" + assignmentAddRq.toString());
				}

				// setting the newly created "AssignmentAddRqDocument" into
				// Cieca Document & hence updating the Cieca doc
				assignAddRqDoc.setAssignmentAddRq(assignmentAddRq);
				ciecaDocument.getCIECA().setAssignmentAddRq(assignAddRqDoc.getAssignmentAddRq());

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Updated CIECA with new AssignmentAddRqDocument");
					logger.fine("CIECA from MitchellEnvelope Document" + ciecaDocument.toString());
				}

				// Adding latest updated Cieca to MitchellEnvelope Document.
				final EnvelopeBodyType envBodyType = mitchellEnvelopeHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
				mitchellEnvelopeHelper.updateEnvelopeBodyContent(envBodyType, ciecaDocument);

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("CiecaUpdateMEDOC::" + mitchellEnvelopeHelper.getDoc());
				}

			}

			// fetching AdditionalAppraisalAssignmentInfoDocument from
			// MitchellEnvelope Document & then updating the notification
			// details from AdditionalAppraisalAssignmentInfoDocument
			AdditionalAppraisalAssignmentInfoType additionalAAInfoType = null;
			addAppAsmtInfoDoc = getAdditionalAppraisalAssignmentInfoDocumentFromME(mitchellEnvelopeHelper);
			if (addAppAsmtInfoDoc != null) {
				additionalAAInfoType = addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo();
			} else {
				addAppAsmtInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory.newInstance();
				addAppAsmtInfoDoc.addNewAdditionalAppraisalAssignmentInfo();
				additionalAAInfoType = addAppAsmtInfoDoc.getAdditionalAppraisalAssignmentInfo();
			}

			com.mitchell.schemas.appraisalassignment.AssignmentNotificationType notificationType = null;
			com.mitchell.schemas.appraisalassignment.EmailAddressesType emailTO = null;
			com.mitchell.schemas.appraisalassignment.EmailAddressesType emailCC = null;

			if (additionalAAInfoType.getNotificationDetails() != null) {
				notificationType = additionalAAInfoType.getNotificationDetails();
			} else {
				notificationType = additionalAAInfoType.addNewNotificationDetails();
			}

			boolean notifyEmailTo = true;
			boolean notifyEmailCC = true;
			if (notificationType.isSetNotificationEmailTo()
					&& !notificationType.getNotificationEmailTo().getNotifyRecipients()) {
				notifyEmailTo = false;
			}
			if (notificationType.isSetNotificationEmailCC()
					&& !notificationType.getNotificationEmailCC().getNotifyRecipients()) {
				notifyEmailCC = false;
			}
			if (assigneeInfo != null && notifyEmailTo) {
				if (assigneeInfo.getUserInfo().isSetEmail()) {
					final String emailOfEstimator = assigneeInfo.getUserInfo().getEmail();
					if (notificationType.isSetNotificationEmailTo()) {
						notificationType.unsetNotificationEmailTo();
					}
					emailTO = notificationType.addNewNotificationEmailTo();
					emailTO.setNotifyRecipients(notifyEmailTo);
					emailTO.addEmailAddress(emailOfEstimator);
				}
			}

			if (logdInUsrInfo.getUserInfo().isSetEmail() && notifyEmailCC) {
				final String emailOfLoggedInUser = logdInUsrInfo.getUserInfo().getEmail();
				if (notificationType.isSetNotificationEmailCC()) {
					notificationType.unsetNotificationEmailCC();
				}
				emailCC = notificationType.addNewNotificationEmailCC();
				emailCC.setNotifyRecipients(notifyEmailCC);
				emailCC.addEmailAddress(emailOfLoggedInUser);
			}

			final EnvelopeBodyType addAAInfoBodyType = mitchellEnvelopeHelper
					.getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
			if (null != addAAInfoBodyType) {
				mitchellEnvelopeHelper.updateEnvelopeBodyContent(addAAInfoBodyType, addAppAsmtInfoDoc);
			} else {
				mitchellEnvelopeHelper.addNewEnvelopeBody("AdditionalAppraisalAssignmentInfo", addAppAsmtInfoDoc,
					"AdditionalAppraisalAssignmentInfo");
			}

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("AdditionalAppraisalAssignmentInfoUpdateMEDOC" + mitchellEnvelopeHelper.getDoc());
			}

			// fetching AdditinalTaskConstraintsDocument from MitchellEnvelope
			// Document & then updating the ScheduleInfo details from
			additionalTaskConstraintsDocument = getAdditionalTaskConstraintsFromME(mitchellEnvelopeHelper);
			if (scheduleDateTime != null) {
				if (additionalTaskConstraintsDocument != null) {
					if (additionalTaskConstraintsDocument.getAdditionalTaskConstraints().isSetScheduleInfo()) {
						additionalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
								.setScheduledDateTime(scheduleDateTime);
					} else {
						additionalTaskConstraintsDocument.getAdditionalTaskConstraints().addNewScheduleInfo()
								.setScheduledDateTime(scheduleDateTime);
					}

				} else {
					additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory.newInstance();
					additionalTaskConstraintsDocument.addNewAdditionalTaskConstraints().addNewScheduleInfo()
							.setScheduledDateTime(scheduleDateTime);
				}
				final EnvelopeBodyType addTaskConstBodyType = mitchellEnvelopeHelper
						.getEnvelopeBody("AdditionalTaskConstraints");
				if (null != addTaskConstBodyType) {
					mitchellEnvelopeHelper
							.updateEnvelopeBodyContent(addTaskConstBodyType, additionalTaskConstraintsDocument);
				} else {
					mitchellEnvelopeHelper.addNewEnvelopeBody("AdditionalTaskConstraints",
						additionalTaskConstraintsDocument, "AdditionalTaskConstraints");
				}
			}

			// fetching tcn from the DTO
			tcn = appAsmtDTO.getEstPkgAppAsgDoc().getEstPkgAppraisalAssignment().getTCN();

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("New ME Document after adding assign/reassign information is :"
						+ mitchellEnvelopeHelper.getDoc());
			}

			// updating the MitchellEnvelopeDocument through
			// EstimatePackageService
			this.estimatePackageProxy.updateAppraisalAssignmentDocument(docId, mitchellEnvelopeHelper.getDoc(),
				logdInUsrInfo, Long.valueOf(tcn));

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("Updated the MitchellEnvelopeDocument through Estimate Package Serive");
			}

		} catch (final Exception exception) {

			StringBuilder localMethodParams = new StringBuilder();
			localMethodParams.append("DocumentId: " + documentId).append(", dispatchCenter: " + dispatchCenter)
					.append(", userType: " + userType);

			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_ASSIGNUIDTOMEBMS, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_ASSIGN_AA_ASSIGNUIDTOMEBMS_MSG, localMethodParams.toString(), exception,
				logdInUsrInfo, null, null);

		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	public CIECADocument getCiecaDocFromMitchellEnv(final MitchellEnvelopeDocument mEnvDoc, final String workItemId)
			throws Exception, MitchellException {
		final String methodName = "getCiecaDocFromMitchellEnv";
		logger.entering(CLASS_NAME, methodName);

		CIECADocument ciecaDoc = null;

		final MitchellEnvelopeHelper mitchellEnvHelper = new MitchellEnvelopeHelper(mEnvDoc);

		// Get CIECADocument
		final EnvelopeBodyType envelopeBody = mitchellEnvHelper
				.getEnvelopeBody(AppraisalAssignmentConstants.ME_METADATA_CIECA_IDENTIFIER);

		if (envelopeBody != null) {
			if (logger.isLoggable(Level.INFO)) {
				logger.info("***** DEBUG getCiecaDoc: Have a getEnvelopeBody for Type: CIECABMSAssignmentAddRq");
			}
		}

		final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
		final String xmlBeanClassname = metadata.getXmlBeanClassname();
		final String contentString = mitchellEnvHelper.getEnvelopeBodyContentAsString(mitchellEnvHelper
				.getEnvelopeBody(AppraisalAssignmentConstants.ME_METADATA_CIECA_IDENTIFIER));

		if (xmlBeanClassname == null || xmlBeanClassname.equals(CIECADocument.class.getName())) {
			ciecaDoc = CIECADocument.Factory.parse(contentString);
		} else {

			final String errmsg = "MitchellEnvelope does not contain CIECA Document. ";
			logger.severe(errmsg);

			ErrorLoggingService.logError(AppraisalAssignmentConstants.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR, null,
				CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL, workItemId,
				AppraisalAssignmentConstants.INVALID_CIECA_IN_MITCHELL_ENVELOPE_ERROR_MSG, null, 0, null);

			final MitchellException me = new MitchellException(CLASS_NAME, methodName, errmsg, null);
			throw me;
		}

		logger.exiting(CLASS_NAME, methodName);
		return ciecaDoc;
	}

	/**
	 * This method retrieves EstimateDocumentId from
	 * AdditionalAppraisalAssignmentInfoDocument.
	 * @return long EstimateDocumentID
	 * @throws MitchellException
	 */
	public long getEstimateDocId(final MitchellEnvelopeHelper mitchellEnvelopeHelper) throws MitchellException {
		long relatedEstimateDocumentId = 0;
		final String METHOD_NAME = "getestimateDocId";
		logger.entering(CLASS_NAME, METHOD_NAME);
		try {
			final AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = this
					.getAdditionalAppraisalAssignmentInfoDocumentFromME(mitchellEnvelopeHelper);

			if (logger.isLoggable(java.util.logging.Level.FINE)) {
				logger.fine("Fetched AppraisalAssignmentInfoDocument From ME: " + addAppAsmtInfo);
			}

			if (addAppAsmtInfo != null) {
				if (addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().isSetAssignmentDetails()) {
					if (addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
							.isSetRelatedEstimateDocumentID()) {
						relatedEstimateDocumentId = addAppAsmtInfo.getAdditionalAppraisalAssignmentInfo()
								.getAssignmentDetails().getRelatedEstimateDocumentID();
					}

					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						logger.info("Fetched RelatedEstimateDocumentId from getAdditionalAppraisalAssignmentInfo: "
								+ relatedEstimateDocumentId);
					}
				}
			}
			if (relatedEstimateDocumentId == 0) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Received NULL/'0' RelatedEstimateDocumentId From MitchellEnvelope Document.\nClaimID : ");
			}
		} catch (final Exception ex) {
			throw new MitchellException(CLASS_NAME, METHOD_NAME,
				"Received NULL/'0' RelatedEstimateDocumentId From MitchellEnvelope Document.\nClaimID : ", ex);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return relatedEstimateDocumentId;
	}

	/**
	 * This helper method retrieves CiecaBms AssignmentAddRqDocument from
	 * MitchellEnvelopeDocument
	 * @return AssignmentAddRqDocument
	 * @throws Exception
	 */
	public AssignmentAddRqDocument fetchAssignmentAddRqDocument(final MitchellEnvelopeHelper meHelper) throws Exception {

		final String METHOD_NAME = "fetchAssignmentAddRqDocument";
		logger.entering(CLASS_NAME, METHOD_NAME);
		CIECADocument ciecaDocument;
		AssignmentAddRqDocument assignAddRqDoc = null;
		ciecaDocument = this.getCiecaFromME(meHelper);
		assignAddRqDoc = AssignmentAddRqDocument.Factory.newInstance();
		assignAddRqDoc.setAssignmentAddRq(ciecaDocument.getCIECA().getAssignmentAddRq());

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return assignAddRqDoc;
	}

	/**
	 * This helper method retrieves AddressType element from CiecaBms. It also
	 * retrieves CiecaBms from MitchellEnvelope Document.
	 * @param mitchellEnvDoc Object of MitchellEnvelopeDocument.
	 * @return AddressType
	 * @throws Exception
	 */
	public AddressType fetchInspectionSiteAddress(final MitchellEnvelopeDocument mitchellEnvDoc) throws Exception {
		final String METHOD_NAME = "fetchInspectionSiteAddress";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("fetchInspectionSiteAddress:: Input Received :: mitchellEnvDoc: " + mitchellEnvDoc);
		}
		AddressType address = null;

		// Manoj Vehicle address can be populated either OrgInfo or personal
		// Info based on InpectionSite Type
		// if InspectionType is Home then this address will be populate in
		// personal info
		// assignAddRqDoc.getAssignmentAddRq().getAdminInfo().getInspectionSite().getParty().getPersonInfo()
		// if InspectionType is Not Home then this address will be populate in
		// Org info
		// put this business rule in detail design
		// i am not sure what is posiblity to BMS have both info

		final AssignmentAddRqDocument assignAddRqDoc = fetchAssignmentAddRqDocument(new MitchellEnvelopeHelper(
			mitchellEnvDoc));
		final AssignmentAddRq addRq = assignAddRqDoc.getAssignmentAddRq();

		if (addRq.getAdminInfo().isSetInspectionSite()) {
			final OrgInfoType orgInfo = addRq.getAdminInfo().getInspectionSite().getParty().getOrgInfo();

			// -----------------------------------------------------------------
			// Alternate 1, Get InspectionSite Address from OrgInfo Aggregate
			if (orgInfo != null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Retrieving InspectionSite Address from OrgInfo Communication Array");
				}
				final CommunicationsType[] communicationsType = orgInfo.getCommunicationsArray();
				if (communicationsType != null) {
					for (int i = 0; i < communicationsType.length; i++) {
						if (communicationsType[i].isSetAddress()) {
							address = communicationsType[i].getAddress();
							break;
						}
					}
				}
			}

			// -----------------------------------------------------------------
			// Alternate 2, Get InspectionSite Address from PersonInfo Aggregate
			if (address == null) {
				final PersonInfoType personInfo = addRq.getAdminInfo().getInspectionSite().getParty().getPersonInfo();

				if (personInfo != null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("Retrieving InspectionSite Address from personInfo Communication Array");
					}
					final CommunicationsType[] communicationsType2 = personInfo.getCommunicationsArray();
					if (communicationsType2 != null) {
						for (int i = 0; i < communicationsType2.length; i++) {
							if (communicationsType2[i].isSetAddress()) {
								address = communicationsType2[i].getAddress();
								break;
							}
						}
					}
				}
			}
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return address;
	}

	/**
	 * This method validates Inspection Site Address in case it is not validated
	 * before.
	 * @throws MitchellException
	 */
	public AddressValidStatus.Enum validateInspectionSiteAddress(final MitchellEnvelopeHelper meHelper,
			final UserInfoDocument logdInUsrInfo) throws MitchellException {

		final String METHOD_NAME = "validateInspectionSiteAddress";
		logger.entering(CLASS_NAME, METHOD_NAME);

		AdditionalAppraisalAssignmentInfoDocument aaaiDocument = null;
		String zip = null;
		String city = null;
		String state = null;
		String street = null;
		String country = null;
		GeoResult geoResult = null;
		AddressValidStatus.Enum addressValidStatusEnum = null;

		try {
			MitchellEnvelopeDocument mitchellEnvDoc = meHelper.getDoc();

			// Check if <code>AdditionalAppraisalAssignmentInfo</code> is
			// available. If available then retrieve document.

			try {
				aaaiDocument = this.getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);

				if (logger.isLoggable(java.util.logging.Level.FINE)) {
					logger.fine("Fetched Additional AppraisalAssignment Info Document: " + aaaiDocument);
				}

			} catch (final Exception exception) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Error fetching AdditionalAppraisalAssignmentInfo from MitchellEnvelope Document. MitchellEnvelope Document: \n"
							+ meHelper.getDoc().toString(), exception);
			}

			// check if AddressValidStatus is set. If set, then retrieve.
			if (aaaiDocument != null
					&& aaaiDocument.getAdditionalAppraisalAssignmentInfo().isSetAssignmentDetails()
					&& aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
							.isSetInspectionSiteGeoCode()) {

				addressValidStatusEnum = aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
						.getInspectionSiteGeoCode().getAddressValidStatus();

				// check if AddressValidStatus is set UNKNOWN then do the rest
				// of the things. Otherwise there is no need to do.
				if (addressValidStatusEnum.equals(AddressValidStatus.UNKNOWN)) {
					AddressType address = null;

					// Fetch inspection site address from MitchellEnvelope
					// document.
					try {
						address = fetchInspectionSiteAddress(mitchellEnvDoc);
					} catch (final Exception exception) {
						throw new MitchellException(CLASS_NAME, METHOD_NAME,
							"Error fetching Address Type from CiecaBms Document / MitchellEnvelopeDocument. Sent MitchellEnvelope Document: \n"
									+ mitchellEnvDoc.toString(), exception);
					}

					if (address.isSetAddress1()) {
						street = address.getAddress1();
					}
					if (address.isSetAddress2()) {
						street += " " + address.getAddress2();
					}
					if (address.isSetCity()) {
						city = address.getCity();
					}
					if (address.isSetStateProvince()) {
						state = address.getStateProvince();
					}
					if (address.isSetPostalCode()) {
						zip = address.getPostalCode();
					}
					if (address.isSetCountryCode()) {
						country = address.getCountryCode();
					}

					// Call GEOService for validation.
					try {
						geoResult = aasCommonUtils.validateAddress(street, city, state, zip, country);
					} catch (final Exception exception) {
						throw new MitchellException(CLASS_NAME, METHOD_NAME,
							"Error validating address through GEOService. Sent values:\n" + "Street: " + street + "\n"
									+ "City: " + city + "\n" + "State: " + state + "\n" + "Zip: " + zip + "\n" + "Country: "
									+ country + "\n", exception);
					}

					// Update AAAIDocument to set result from GEOService.
					if (geoResult.getIsValid()) {
						aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
								.getInspectionSiteGeoCode().setAddressValidStatus(AddressValidStatus.VALID);
						aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
								.getInspectionSiteGeoCode().setLatitude(geoResult.getLatLong().getLatitude());
						aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
								.getInspectionSiteGeoCode().setLongitude(geoResult.getLatLong().getLongitude());

						logger.info("Setting the GEO service Results in AdditionalAppraisalAssignment for Valid Address");

					} else {
						aaaiDocument.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
								.getInspectionSiteGeoCode().setAddressValidStatus(AddressValidStatus.INVALID);

						logger.info("Setting the GEO service Results in AdditionalAppraisalAssignment for InValid Address");

					}

					// Add latest updated AAAI Document to ME using MEHelper.
					// Then update local MEDocument object with latest
					// MEDocument in MEHelper.
					final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
					if (null != envBodyType) {
						meHelper.updateEnvelopeBodyContent(envBodyType, aaaiDocument);
					} else {
						meHelper.addNewEnvelopeBody("AdditionalAppraisalAssignmentInfo", aaaiDocument,
							"AdditionalAppraisalAssignmentInfo");
					}

					if (logger.isLoggable(java.util.logging.Level.FINE)) {
						logger.fine("Added latest updated AAAI Document to ME using MEHelper" + meHelper.getDoc());
					}
				}
			}
		} catch (final Exception exception) {
			errorLogUtil.logAndThrowError(AppraisalAssignmentConstants.ERROR_VALIDATEINSPECTIONSITEADDRESS, CLASS_NAME,
				METHOD_NAME, ErrorLoggingService.SEVERITY.FATAL,
				AppraisalAssignmentConstants.ERROR_VALIDATEINSPECTIONSITEADDRESS_MSG, "", exception, logdInUsrInfo, null,
				null);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return addressValidStatusEnum;
	}

	/**
	 * Method return AssigneeId from CIECADocument.
	 * @param ciecaDocument CIECADocument.
	 * @return String AssigneeId.
	 */
	public String getAssigneeIdFromCiecaDocument(final CIECADocument ciecaDocument) {
		final String METHOD_NAME = "getAssigneeIdFromCiecaDocument";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + ciecaDocument.toString());
		}
		String assigneeId = null;

		if (ciecaDocument != null
				&& ciecaDocument.getCIECA().isSetAssignmentAddRq()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().isSetVehicleDamageAssignment()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().isSetEstimatorIDs()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getEstimatorIDs()
						.isSetCurrentEstimatorID()) {
			assigneeId = ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getEstimatorIDs()
					.getCurrentEstimatorID();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return assigneeId;
	}

	/**
	 * Method return GroupCode from CIECADocument.
	 * @param ciecaDocument CIECADocument.
	 * @return String GroupCode.
	 */
	public String getGroupCodeFromCiecaDocument(final CIECADocument ciecaDocument) {
		final String METHOD_NAME = "getGroupCodeFromCiecaDocument";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + ciecaDocument.toString());
		}
		String groupCode = null;
		if (ciecaDocument != null
				&& ciecaDocument.getCIECA().isSetAssignmentAddRq()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().isSetVehicleDamageAssignment()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().isSetEstimatorIDs()
				&& ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getEstimatorIDs()
						.isSetRoutingIDInfo()) {
			groupCode = ciecaDocument.getCIECA().getAssignmentAddRq().getVehicleDamageAssignment().getEstimatorIDs()
					.getRoutingIDInfo().getIDNum();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return groupCode;
	}

	/**
	 * Method return scheduleDateTime from AdditionalTaskConstraintsDocument.
	 * @param additionalTaskConstraintsDocument
	 *            AdditionalTaskConstraintsDocument.
	 * @return Calendar scheduleDateTime.
	 */
	public Calendar getScheduleDateTime(final AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument) {
		final String METHOD_NAME = "getScheduleDateTime";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + additionalTaskConstraintsDocument.toString());
		}
		Calendar scheduleDateTime = null;
		if (additionalTaskConstraintsDocument != null
				&& additionalTaskConstraintsDocument.getAdditionalTaskConstraints() != null
				&& additionalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo() != null
				&& additionalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo().getScheduledDateTime() != null) {
			scheduleDateTime = additionalTaskConstraintsDocument.getAdditionalTaskConstraints().getScheduleInfo()
					.getScheduledDateTime();
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return scheduleDateTime;
	}

	/**
	 * Method update the ME Document with dispatch center in
	 * EstimatePackageService.
	 * @param documentId
	 * @param workItemID
	 * @param claimNumber
	 * @param dispatchCenter
	 * @param loggedInUserInfoDocument
	 * @exception MitchellException
	 */
	public void updateGroupIdInMitchellEnvelope(final Long documentId, final String workItemID, final String claimNumber,
			final String dispatchCenter, final UserInfoDocument loggedInUserInfoDocument) throws MitchellException {
		final String METHOD_NAME = "updateGroupIdInMitchellEnvelope";
		logger.entering(CLASS_NAME, METHOD_NAME);

		try {
			final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = estimatePackageProxy
					.getAppraisalAssignmentDocument(documentId.longValue());

			if (logger.isLoggable(Level.INFO)) {
				logger.info("Fetched AppraisalAssignmentDTO from getAppraisalAssignmentDocument method of EstimatePackage Service for documentID : "
						+ documentId);
			}

			if (appraisalAssignmentDTO == null) {
				throw new MitchellException(CLASS_NAME, METHOD_NAME,
					"Received NULL AppraisalAssignmentDTO object From EstimatePackage Service. Document ID : " + documentId);
			}

			final long tcn = appraisalAssignmentDTO.getEstPkgAppAsgDoc().getEstPkgAppraisalAssignment().getTCN();

			final String mitchellEnvelopeStr = appraisalAssignmentDTO.getAppraisalAssignmentMEStr();
			final MitchellEnvelopeDocument mitchellEnvelopeDoc = MitchellEnvelopeDocument.Factory.parse(mitchellEnvelopeStr);
			final MitchellEnvelopeHelper mitchellEnvelopeHelper = new MitchellEnvelopeHelper(mitchellEnvelopeDoc);
			final CIECADocument ciecaDocument = getCiecaFromME(mitchellEnvelopeHelper);

			VehicleDamageAssignmentType vehicleDamageAssignment = null;
			AssignmentAddRq assignmentAddRq = null;
			if (ciecaDocument != null && ciecaDocument.getCIECA().isSetAssignmentAddRq()) {
				assignmentAddRq = ciecaDocument.getCIECA().getAssignmentAddRq();
			} else {
				assignmentAddRq = ciecaDocument.getCIECA().addNewAssignmentAddRq();
			}
			if (assignmentAddRq != null && !assignmentAddRq.isSetVehicleDamageAssignment()) {
				vehicleDamageAssignment = assignmentAddRq.addNewVehicleDamageAssignment();
			} else {
				vehicleDamageAssignment = assignmentAddRq.getVehicleDamageAssignment();
			}
			// Unsetting the EstimatorID
			if (vehicleDamageAssignment.isSetEstimatorIDs()) {
				vehicleDamageAssignment.unsetEstimatorIDs();
			}
			// Creating the EstimatorID tag and setting Dispatch Center
			final EstimatorIDsTypeType estimatorIDsTypeType = EstimatorIDsTypeType.Factory.newInstance();
			final IDInfoType iDInfoType = IDInfoType.Factory.newInstance();
			iDInfoType.setIDQualifierCode("WAGroupID");
			if (dispatchCenter != null && !"".equals(dispatchCenter)) {
				iDInfoType.setIDNum(dispatchCenter);
			}
			estimatorIDsTypeType.setRoutingIDInfo(iDInfoType);
			vehicleDamageAssignment.setEstimatorIDs(estimatorIDsTypeType);

			final EnvelopeBodyType envBodyType = mitchellEnvelopeHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
			mitchellEnvelopeHelper.updateEnvelopeBodyContent(envBodyType, ciecaDocument);

			estimatePackageProxy.updateAppraisalAssignmentDocument(documentId.longValue(), mitchellEnvelopeHelper.getDoc(),
				loggedInUserInfoDocument, Long.valueOf(tcn));

		} catch (final RemoteException remoteEstimatePackageServiceException) {
			final String errorMessage = "Error while getting the EstimatePackageServiceRemote Object";
			throw new MitchellException(CLASS_NAME, METHOD_NAME, errorMessage, remoteEstimatePackageServiceException);
		} catch (final org.apache.xmlbeans.XmlException parseXMLException) {
			final String errorMessage = "Error parsing and creating object of MitchellEnvelopeDocument";
			throw new MitchellException(CLASS_NAME, METHOD_NAME, errorMessage, parseXMLException);
		} catch (final Exception exception) {
			final String errorMessage = "Error in updating MitchellEnvelopeDocument with groupID in EstimatePackageService";
			throw new MitchellException(CLASS_NAME, METHOD_NAME, errorMessage, exception);
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
	}
}
