package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.AttachmentInfoDocument;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.MitchellWorkflowMessageTrackingInfoType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.common.types.WorkflowUserInfoType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentFailureResponseContext;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentFailureResponseDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AssignmentAppLogHelper;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AssignmentAppLogHelperImpl;
import com.mitchell.services.core.validation.schemas.ErrorElementDocument;
import com.mitchell.services.core.validation.schemas.ErrorElementsDocument;
import com.mitchell.services.core.validation.schemas.ValidationResultDocument;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * @author rk104152
 * 
 */
public class AssignmentFailureResponseHandlerImpl implements
		AssignmentFailureResponseHandler {

	/**
	 * Class name.
	 */
	private static final String CLASS_NAME = AssignmentFailureResponseHandlerImpl.class
			.getName();

	/**
	 * logger.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);
	private MitchellWorkflowMessageDocument mwmDoc;
	private MitchellEnvelopeDocument meDoc;
	private ValidationResultDocument valResDoc = null;
	MitchellWorkflowMessageTrackingInfoType trackInfo = null;
	private UserInfoDocument userInfoDocument = null;
	private AttachmentInfoDocument attachmentInfoDocument = null;
	private String partnerKey = null;
	AssignmentFailureResponseContext asFailRespContext = null;
	private AssignmentAppLogHelper appLogHelper;
	AssignmentFailureResponseDTO asFailureRespDTO = null;
	MitchellEnvelopeHelper meHelper = null;

	/**
	 * This method parses message string and extracts MWM document, User Info
	 * document, Validation Result document and MEHelper.
	 * 
	 * @param message
	 *            String message text containing MWM
	 * @throws MitchellException
	 *             Mitchell Exception
	 */
	public void processMessage(String message) throws MitchellException {
		String methodName = "processMessage";
		try {
			logger.entering(CLASS_NAME, methodName);

			if (logger.isLoggable(Level.INFO)) {
				/*
				 * Cannot use the word Failure in the logs and so making
				 * "Appraisal Assignment Failure " as AAF "
				 */
				logger.info("received AAF Request !!\n" + message);
			}
			mwmDoc = this.extractMitchellWorkflowMessage(message);
			if (mwmDoc != null) {
				trackInfo = mwmDoc.getMitchellWorkflowMessage()
						.getTrackingInfo();
				meDoc = this.extractMitchellEnvelopeMessage(mwmDoc);
			}
			if (trackInfo != null) {
				userInfoDocument = this.extractUserInfo(trackInfo);
			}
			if (meDoc != null) {
				meHelper = new MitchellEnvelopeHelper(meDoc);
			}

			valResDoc = this.extractValidationResultsDoc(meHelper);
			if (userInfoDocument == null || valResDoc == null) {
				throw new MitchellException(CLASS_NAME, methodName,
						AppraisalAssignmentConstants.ERROR_EMPTY_DOCS);
			}
			asFailureRespDTO = this.prepareFailureResponse(userInfoDocument,
					valResDoc, meHelper);
			this.processAssignmentFailureResponse(asFailureRespDTO);
		} catch (Exception e) {
			MitchellException me = new MitchellException(
					AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_DTO,
					CLASS_NAME, methodName, "Error processing input message"
							+ "\n" + AppUtilities.getStackTraceString(e, true));
			if (logger.isLoggable(Level.SEVERE)) {
				logger.severe("Exception occurred while processing message in processMessage handler: "

						+ "\n" + AppUtilities.getStackTraceString(me));
			}
			throw me;
		}

	}

	/**
	 * Based on this User Info Type, Validation Result and MEHelper this method
	 * prepares AssignmentFailureResponseDTO
	 * 
	 * @param UserInfoDocument
	 * @param ValidationResultDocument
	 * @param MitchellEnvelopeHelper
	 * 
	 * @return AssignmentFailureResponseDTO
	 */
	private AssignmentFailureResponseDTO prepareFailureResponse(
			UserInfoDocument userInfoDocument,
			ValidationResultDocument valResDoc, MitchellEnvelopeHelper meHelper)
			throws MitchellException {
		String methodName = "prepareFailureResponse";
		logger.entering(CLASS_NAME, methodName);

		AssignmentFailureResponseDTO asFailureRespDTO = new AssignmentFailureResponseDTO();
		ErrorElementsDocument.ErrorElements errorElements = valResDoc
				.getValidationResult().getErrorElements();

        // Add handling of ValidationResultDocument with multiple error nodes
		ErrorElementDocument.ErrorElement[] errorElementArray = errorElements
				.getErrorElementArray();
		StringBuilder errorMessage = new StringBuilder();
		String errorMessageNode = "";

		// logger.info("prepareFailureResponse: errorElementArray.length= "
		// 		+ errorElementArray.length);

		for (int j = 0; j < errorElementArray.length; j++) {

			ErrorElementDocument.ErrorElement errorElementNext = errorElements
					.getErrorElementArray(j);
			errorMessageNode = errorElementNext.getErrorMessage();
			errorMessage.append(errorMessageNode).append("\n");
		}

		// logger.info("prepareFailureResponse: final errorMessage="
		// 		+ errorMessage);
		
		boolean isWarning = valResDoc.getValidationResult().getSuccessFlag();
		if (isWarning) {
			asFailureRespDTO
					.setErrorType(AppraisalAssignmentConstants.WARNING_MSG);
		} else {
			asFailureRespDTO
					.setErrorType(AppraisalAssignmentConstants.ERROR_MSG);
		}

		// v1 asFailureRespDTO.setErrDesciption(errorMessage);
		asFailureRespDTO.setErrDesciption(errorMessage.toString());

		asFailureRespDTO
				.setTransactionType(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_ASSIGNMENT_TYPE);
		asFailureRespDTO.setUserInfoDocument(userInfoDocument.toString());
		UserInfoType userInfoType = userInfoDocument.getUserInfo();
		if (null != userInfoType) {
			asFailureRespDTO.setUserId(userInfoType.getUserID());
			asFailureRespDTO.setCompCode(userInfoType.getOrgCode());
		}

		if (meHelper != null) {
			asFailureRespDTO
					.setClaimNumber(meHelper
							.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_CLAIM_NUMBER));
			asFailureRespDTO
					.setPartnerKey(meHelper
							.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_PARTNER_KEY));
			asFailureRespDTO
					.setCorrelationId(meHelper
							.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_CORRELATION_ID));
			asFailureRespDTO
					.setWorkItemId(meHelper
							.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_MITCHELL_WORKITEMID));
			asFailureRespDTO
					.setSubmitterId(meHelper
							.getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_SUBMITTER_ID));
		}

		logger.exiting(CLASS_NAME, methodName);

		return asFailureRespDTO;

	}

	/**
	 * method to process and publish event for Assignment failure Response
	 * 
	 * @param asFailRespDTO
	 */
	private void processAssignmentFailureResponse(
			AssignmentFailureResponseDTO asFailRespDTO)
			throws MitchellException {
		String methodName = "processAssignmentFailureResponse";
		logger.entering(CLASS_NAME, methodName);
		AssignmentFailureResponseContext asFailRespContext = new AssignmentFailureResponseContext();
		UserInfoDocument userInfoDocument = null;
		try {
			Map<String, String> map = new HashMap<String, String>();

			asFailRespContext.setUserId(asFailRespDTO.getUserId());
			asFailRespContext.setClaimId(asFailRespDTO.getClaimId());
			asFailRespContext.setSuffixId(asFailRespDTO.getSuffixId());
			asFailRespContext.setCoCode(asFailRespDTO.getCompCode());
			asFailRespContext.setClaimNumber(asFailRespDTO.getClaimNumber());
			asFailRespContext
					.setTransactiontype(String
							.valueOf(AppraisalAssignmentConstants.APPLOG_PROCESSING_FAILURE_RESPONSE_EVENT));
			asFailRespContext.setWorkItemId(asFailRespDTO.getWorkItemId());
			this.populateMapForResponseAppLog(map, asFailRespDTO);
			asFailRespContext.setMap(map);
			// do AppLog
			String strUserInfoDoc = asFailRespDTO.getUserInfoDocument();
			if (null != strUserInfoDoc) {
				userInfoDocument = UserInfoDocument.Factory
						.parse(strUserInfoDoc);
			}
			if (logger.isLoggable(Level.INFO)) {
				/*
				 * Cannot use the word Failure in the logs and so making
				 * "Appraisal Assignment Failure " as AAF "
				 */
				logger.info(" process AAF Response(): Invoking App Logger!!\n");
			}
			appLogHelper = new AssignmentAppLogHelperImpl();
			appLogHelper.appLog(userInfoDocument, asFailRespContext);

		} catch (Exception e) {
			MitchellException me = new MitchellException(
					AppraisalAssignmentConstants.ERROR_PROCESSING_FAILURE_RESPONSE_MESSAGE,
					CLASS_NAME, methodName,
					"Exception occurred while doing App logging" + "\n"
							+ AppUtilities.getStackTraceString(e, true));
			if (logger.isLoggable(Level.SEVERE)) {
				logger.severe("Exception occurred while doing App logging: "
						+ "\n" + AppUtilities.getStackTraceString(e, true));
			}

		}
		logger.exiting(CLASS_NAME, methodName);

	}

	/**
	 * Extract a Mitchell Envelope From MWM Message.
	 */
	private MitchellWorkflowMessageDocument extractMitchellWorkflowMessage(
			String msg) throws MitchellException, XmlException {
		String methodName = "extractMitchellWorkflowMessage";
		logger.entering(CLASS_NAME, methodName);
		MitchellWorkflowMessageDocument mwmDoc = MitchellWorkflowMessageDocument.Factory
				.parse(msg.trim());
		logger.exiting(CLASS_NAME, methodName);
		return mwmDoc;
	}

	/**
	 * Extract the Logged In UserInfo document from the MitchellEnvelope
	 * Parallel processing request.
	 * 
	 * @param meHelper
	 * @return Returns the extracted UserInfoDocument.
	 * 
	 * @throws MitchellException
	 * @throws XmlException
	 */
	private UserInfoDocument extractUserInfo(
			MitchellWorkflowMessageTrackingInfoType trackInfo)
			throws MitchellException, XmlException {
		final String methodName = "extractUserInfo";
		logger.entering(CLASS_NAME, methodName);

		WorkflowUserInfoType userInfoType = null;
		// Get the UserInfo
		if (trackInfo != null) {
			userInfoType = trackInfo.getWorkflowUserInfo();
		}

		UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.parse(userInfoType.toString());
		logger.exiting(CLASS_NAME, methodName);
		return userInfoDocument;
	}

	/**
	 * Extract a Mitchell Envelope From MWM Message.
	 */
	private MitchellEnvelopeDocument extractMitchellEnvelopeMessage(
			MitchellWorkflowMessageDocument mwmDoc) throws MitchellException,
			XmlException {
		String methodName = "extractMitchellEnvelopeMessage";
		logger.entering(CLASS_NAME, methodName);
		MitchellEnvelopeDocument workflowEnvelopeDoc = MitchellEnvelopeDocument.Factory
				.parse(mwmDoc.getMitchellWorkflowMessage().getData().xmlText());
		logger.exiting(CLASS_NAME, methodName);
		return workflowEnvelopeDoc;
	}

	/**
	 * This method is a helper method which extracts ValidationResultDocument
	 * from MitchellEnvelope Document.
	 * 
	 * @param meHelper
	 *            MitchellEnvelopeHelper, input
	 * 
	 * @return ValidationResultDocument ceicaDoc
	 * 
	 * @throws Exception
	 *             in case MitchellEnvelope Document doesn't contains
	 *             ValidationResultDocument
	 */
	private ValidationResultDocument extractValidationResultsDoc(
			final MitchellEnvelopeHelper meHelper) throws MitchellException,
			XmlException {

		final String methodName = "extractValidationResultsDoc";
		logger.entering(CLASS_NAME, methodName);

		ValidationResultDocument valResDoc = null;
		String contentString = null;

		if (logger.isLoggable(Level.INFO)) {
			logger.info("extractValidationResultsDoc - \nInput Received: MitchellEnvelopeDocument: "
					+ meHelper.getDoc());
		}

		final EnvelopeBodyType envelopeBody = meHelper
				.getEnvelopeBody(AppraisalAssignmentConstants.VALIDATION_RESULTS_DOC);
		if (envelopeBody != null) {
			final EnvelopeBodyMetadataType metadata = envelopeBody
					.getMetadata();
			final String xmlBeanClassname = metadata.getXmlBeanClassname();
			contentString = meHelper
					.getEnvelopeBodyContentAsString(envelopeBody);
			valResDoc = ValidationResultDocument.Factory.parse(contentString);
			if (logger.isLoggable(Level.INFO)) {
				logger.info("extractValidationResultsDoc: Retrieved ValidationResultDocument"
						+ " by parsing ContentString:" + valResDoc);
			}

		} else {
			final String errMsg = "MitchellEnvelope does not contains ValidationResultDocument";
			throw new MitchellException(CLASS_NAME,
					"extractValidationResultsDoc", errMsg);
		}

		logger.exiting(CLASS_NAME, methodName);

		return valResDoc;
	}

	/**
	 * This method is used to populate name value pairs for failure response app
	 * logging or negative event publishing.
	 * 
	 * @param map
	 * @param asFailureRespDTO
	 * @throws MitchellException
	 */
	private void populateMapForResponseAppLog(Map<String, String> map,
			AssignmentFailureResponseDTO asFailureRespDTO)
			throws MitchellException {
		String methodName = "populateMapForResponseAppLog";
		logger.entering(CLASS_NAME, methodName);
		if (null != asFailureRespDTO.getTransactionType()) {
			map.put(AppraisalAssignmentConstants.KEY_SOURCE_TRANSACTION_TYPE,
					asFailureRespDTO.getTransactionType());
		}
		if (null != asFailureRespDTO.getErrDesciption()) {
			map.put(AppraisalAssignmentConstants.KEY_FAILURE_MESSAGE,
					asFailureRespDTO.getErrDesciption());
		}
		if (null != asFailureRespDTO.getErrorType()) {
			map.put(AppraisalAssignmentConstants.KEY_SOURCE_FAILURE_ID,
					asFailureRespDTO.getErrorType());
		}
		if (null != asFailureRespDTO.getCorrelationId()) {
			map.put(AppraisalAssignmentConstants.KEY_SOURCE_TRANSACTION_ID,
					asFailureRespDTO.getCorrelationId());
		}
		if (null != asFailureRespDTO.getPartnerKey()) {
			map.put(AppraisalAssignmentConstants.KEY_EXTERNAL_TASK_ID,
					asFailureRespDTO.getPartnerKey());
		}
		if (null != asFailureRespDTO.getSubmitterId()) {
			map.put(AppraisalAssignmentConstants.KEY_SUBMITTER_ID,
					asFailureRespDTO.getSubmitterId());
		}

		logger.exiting(CLASS_NAME, methodName);
	}

}
