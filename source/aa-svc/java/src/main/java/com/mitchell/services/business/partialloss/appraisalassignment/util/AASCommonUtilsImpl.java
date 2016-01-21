package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.AssignmentSaveStepType;
import com.mitchell.schemas.appraisalassignment.MOIDetailsType;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.workassignment.PriorityType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.geoservice.client.GeoServiceClient;
import com.mitchell.services.core.geoservice.common.exception.GeoServiceException;
import com.mitchell.services.core.geoservice.dto.GeoResult;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * 
 * Class for performing Common Utility.
 * 
 */
public class AASCommonUtilsImpl implements AASCommonUtils {

	// CONSTANTS
	private static Logger logger = Logger.getLogger(AASCommonUtilsImpl.class
			.getName());
	protected static final String CLASS_NAME = AASCommonUtilsImpl.class
			.getName();
	protected static final double LAT_LONG_NORMALIZE_SCALER = 10000.0;

	/**
	 * 
	 * @param settingName
	 *            Custom Setting Name
	 * @param defaultValue
	 *            Default Value of the Custom Setting
	 * @return Value of the Custom Setting
	 */

	private AASErrorLogUtil errorLogUtil;
	private SystemConfigProxy systemConfigProxy;
	private ClaimProxy claimProxy;
	private IAppraisalAssignmentConfig appraisalAssignmentConfig;

	private static final int SWITCH_CASE_ELEVATED_PRIORITY = 50;
	private static final int SWITCH_CASE_HIGH_PRIORITY = 11;
	private static final int SWITCH_CASE_MUST_SEE_DATE = 1;
	private static final int SWITCH_CASE_MUST_SEE_TIME = 0;
	private static final int SWITCH_CASE_STANDARD_PRIORITY = 99;

	public void setAppraisalAssignmentConfig(
			final IAppraisalAssignmentConfig appraisalAssignmentConfig) {

		this.appraisalAssignmentConfig = appraisalAssignmentConfig;
	}

	public void setClaimProxy(final ClaimProxy claimProxy) {

		this.claimProxy = claimProxy;
	}

	public void setSystemConfigProxy(final SystemConfigProxy systemConfigProxy) {

		this.systemConfigProxy = systemConfigProxy;
	}

	public void setErrorLogUtil(final AASErrorLogUtil errorLogUtil) {

		this.errorLogUtil = errorLogUtil;
	}

	public String getSystemConfigurationSettingValue(final String settingName,
			final String defaultValue) {
		String settingValue = SystemConfiguration.getSettingValue(settingName);
		if (settingValue == null) {
			settingValue = defaultValue;
		}
		return settingValue;
	}

	/**
	 * 
	 * @param errorType
	 *            ErrorType of Error
	 * @param classNm
	 *            Class Name
	 * @param methodNm
	 *            Method Name
	 * @param desc
	 *            Description of the Error
	 * @param t
	 *            java.lang.Throwable
	 * @return MitchellException
	 */
	public MitchellException logError(final int errorType,
			final java.lang.String classNm, final java.lang.String methodNm,
			final java.lang.String desc, final java.lang.Throwable t) {
		final MitchellException mitExc = new MitchellException(errorType,
				classNm, methodNm, desc, t);
		mitExc.setSeverity(MitchellException.SEVERITY.WARNING);
		final String correlationID = ErrorLoggingService.logCommonError(mitExc);
		mitExc.setCorrelationId(correlationID);
		return mitExc;
	}

	/**
	 * 
	 * @param errorType
	 *            ErrorType of Error
	 * @param classNm
	 *            Class Name
	 * @param methodNm
	 *            Method Name
	 * @param desc
	 *            Description of the Error
	 * @param t
	 *            java.lang.Throwable
	 * @return MitchellException
	 */
	public MitchellException createMitchellException(final int errorType,
			final java.lang.String classNm, final java.lang.String methodNm,
			final java.lang.String desc, final java.lang.Throwable t) {
		final MitchellException mitExc = new MitchellException(errorType,
				classNm, methodNm, desc, t);
		mitExc.setSeverity(MitchellException.SEVERITY.WARNING);
		return mitExc;
	}

	/**
	 * 
	 * @param errorType
	 *            ErrorType of Error
	 * @param classNm
	 *            Class Name
	 * @param methodNm
	 *            Method Name
	 * @param desc
	 *            Description of the Error
	 * @param t
	 *            java.lang.Throwable
	 * @param outLogger
	 *            Logger
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public void logAndThrowError(final int errorType,
			final java.lang.String classNm, final java.lang.String methodNm,
			final java.lang.String desc, final java.lang.Throwable t,
			final Logger outLogger) throws MitchellException {
		MitchellException mitExc = null;
		if (t != null && t instanceof MitchellException) {
			mitExc = (MitchellException) t;
		} else {
			mitExc = new MitchellException(errorType, classNm, methodNm, desc,
					t);
			mitExc.setSeverity(MitchellException.SEVERITY.WARNING);
		}

		if (outLogger != null) {
			outLogger.severe(desc);
		} else {
			logger.severe(desc);
		}

		final String correlationID = ErrorLoggingService.logCommonError(mitExc);
		mitExc.setCorrelationId(correlationID);

		throw mitExc;
	}

	public void validateTaskDocument(final TaskDocument taskDocument,
			final String msgDetail, final String workItemID,
			final UserInfoDocument logdInUsrInfo) throws MitchellException {
		final String methodName = "validateTaskDocument";
		logger.entering(CLASS_NAME, methodName);
		final String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		final java.util.ArrayList validationErrors = new java.util.ArrayList();
		final boolean isValidDocument = taskDocument
				.validate(getXmlOptions(validationErrors));
		if (!isValidDocument) {
			final String message = getXmlValidationErrors(validationErrors);
			logger.severe("Invalid TaskDocument::\n" + taskDocument.toString()
					+ "\nMessage:: " + message);
			errorLogUtil
					.logAndThrowError(
							AppraisalAssignmentConstants.ERROR_VALIDATING_CREATE_TASK_DOCUMENT,
							CLASS_NAME,
							methodName,
							ErrorLoggingService.SEVERITY.FATAL,
							AppraisalAssignmentConstants.ERROR_VALIDATING_CREATE_TASK_DOCUMENT_MSG,
							msgDetail, new Exception(), logdInUsrInfo,
							workItemID, claimNumber);
		}
		logger.exiting(CLASS_NAME, methodName);
	}

	public XmlOptions getXmlOptions(
			final java.util.ArrayList validationErrorsArrayList) {

		final XmlOptions xmlOptions = new XmlOptions();
		xmlOptions.setLoadLineNumbers();
		xmlOptions.setErrorListener(validationErrorsArrayList);

		return xmlOptions;
	}

	/**
	 * Validate an XmlBean XmlObject against its Xml Schema. Details of all
	 * schema validation errors are logged to JUL as warning messages.
	 * 
	 * @param doc
	 *            The XmlObject hierarchy of the document to schema validate.
	 * 
	 * @return true if document successfully schema validates, false otherwise.
	 */
	public boolean validate(final XmlObject doc) {
		boolean retval = false;

		final XmlOptions xmloptions = new XmlOptions();
		final ArrayList arraylist = new ArrayList();
		xmloptions.setErrorListener(arraylist);
		retval = doc.validate(xmloptions);

		final Iterator iter = arraylist.iterator();

		while (iter.hasNext()) {
			logger.info("Schema Validation Error:" + iter.next().toString());
		}

		return retval;
	}

	public String getXmlValidationErrors(
			final java.util.ArrayList validationErrorsArrayList) {

		String invalidDocErrors = "";
		final java.util.Iterator errorsIterator = validationErrorsArrayList
				.iterator();
		while (errorsIterator.hasNext()) {
			invalidDocErrors = errorsIterator.next().toString() + "\n\n";
		}
		return invalidDocErrors;
	}

	/**
	 * @param comment
	 *            Activity Comment.
	 * @param exposureId
	 *            Claim Exposure ID of Claim Service.
	 * @param referenceId
	 *            Reference ID of DocStore Service.
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void saveExposureActivityLog(final String comment,
			final Long exposureId, final Long referenceId,
			final String workItemID, final UserInfoDocument logdInUsrInfo)
			throws Exception {

		final String METHOD_NAME = "saveExposureActivityLog";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("\t comment : ").append(comment)
					.append("\t exposureId: ").append(exposureId)
					.append("\t referenceId: ").append(referenceId)
					.append("\nloggedinUser : ").append(logdInUsrInfo);
			logger.info(localMethodParams.toString());
			logger.info("Input Received :: comment: " + comment
					+ "\t exposureId: " + exposureId + "\t referenceId: "
					+ referenceId);
		}

		final String referenceTableName = "Appraisal_Assignment_doc";
		final String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;

		try {

			if (exposureId != null) {
				this.claimProxy.writeExposureActLog(exposureId, comment,
						logdInUsrInfo, referenceId, referenceTableName);
			}
		} catch (final Exception exception) {

			final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA_EXPOSUREACTIVITYLOG;

			final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_EXPOSUREACTIVITYLOG_MSG;

			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("\t comment : ").append(comment)
					.append("\t exposureId: ").append(exposureId)
					.append("\t referenceId: ").append(referenceId)
					.append("\nloggedinUser : ").append(logdInUsrInfo);

			errorLogUtil.logWarning(CLASS_NAME, METHOD_NAME, errorCode,
					errorDescription + " " + localMethodParams, workItemID,
					exception, claimNumber, logdInUsrInfo);

		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * @param comment
	 *            Activity Comment.
	 * @param exposureId
	 *            Claim Exposure ID of Claim Service.
	 * @param referenceId
	 *            Reference ID of DocStore Service.
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void saveClaimExpActivtyLogForSuppRequestDoc(final String comment,
			final Long exposureId, final Long referenceId,
			final UserInfoDocument logdInUsrInfo) throws Exception {

		final String METHOD_NAME = "saveClaimExpActivtyLogForSuppRequestDoc";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Input Received :: comment: " + comment
					+ "\t exposureId: " + exposureId + "\t referenceId: "
					+ referenceId);
		}

		if (exposureId != null) {
			this.claimProxy.writeExposureActLog(exposureId, comment,
					logdInUsrInfo, referenceId,
					AppraisalAssignmentConstants.SUP_REQUEST_REF_TABLE);
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * This method calls GEO Service to validate the provided address.
	 * 
	 * @param street
	 *            Street Address.
	 * @param city
	 *            City Name.
	 * @param state
	 *            State Name/code.
	 * @param zip
	 *            Zip code.
	 * @param country
	 *            Country code.
	 * 
	 * @return GeoResult
	 * 
	 * @throws Exception
	 */
	public GeoResult validateAddress(final String street, final String city,
			final String state, final String zip, final String country)
			throws MitchellException {
		final String METHOD_NAME = "validateAddress";

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("Input Received :: street: " + street + "\t city: "
					+ city + "\t state: " + state + "\t zip: " + zip
					+ "\t country: " + country);
		}

		GeoServiceClient gsClient = getNewGeoServiceClient();
		GeoResult validationResult = null;

		try {
			// PBI 302020 -Fix Salesforce Bug 358786: Bing Address is getting
			// validated even when the Zip code is invalid for that address
			validationResult = gsClient.validateAddressWithCorrection(street,
					city, state, zip, country);
		} catch (final GeoServiceException geoEx) {
			throw new MitchellException(
					CLASS_NAME,
					METHOD_NAME,
					"A GeoServiceException was thrown while attempting to validate the provided address."
							+ " GeoService method called: validateAddressWithCorrection(street, city, state, zip, country)."
							+ " Street argument: "
							+ street
							+ ". City argument: "
							+ city
							+ ". State argument: "
							+ state
							+ ". Zip argument: "
							+ zip
							+ ". Country argument: " + country, geoEx);
		}

		// Normalize the latitude and longitude values in the result of the Geo
		// Service validateAddressWithCorrection call
		normalizeLatitudeAndLongitude(validationResult);

		return validationResult;
	}

	public void doDriveInAppointmentActivityLog(final Long claimExposureId,
			final Calendar scheduleDateTime,
			final UserInfoDocument logedInUserInfoDocument,
			final String workItemID,
			final AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo,
			AppraisalAssignmentDTO inputAppraisalAssignmentDTO)
			throws Exception {
		final String METHOD_NAME = "doDriveInAppointmentActivityLog";
		logger.entering(CLASS_NAME, METHOD_NAME);
		final String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		try {

			logger.info("DRIVE IN NULL PASSWD 1...");
			if (addAppAsmtInfo != null) {
				final AdditionalAppraisalAssignmentInfoType additionalAppraisalAssignmentInfoType = addAppAsmtInfo
						.getAdditionalAppraisalAssignmentInfo();
				logger.info("DRIVE IN NULL PASSWD 2...");
				if (additionalAppraisalAssignmentInfoType != null) {
					final AssignmentSaveStepType.Enum saveStepEnumME = additionalAppraisalAssignmentInfoType
							.getAssignmentSaveStep();
					final MOIDetailsType moiDetailsType = additionalAppraisalAssignmentInfoType
							.getMOIDetails();
					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						logger.info("saveStepEnumME in doDriveInAppointmentActivityLog :"
								+ saveStepEnumME);
						logger.info("moiDetailsType in doDriveInAppointmentActivityLog :"
								+ moiDetailsType);
						logger.info("scheduleDateTime in doDriveInAppointmentActivityLog :"
								+ scheduleDateTime);
					}
					if (AssignmentSaveStepType.Enum.forString(
							"ASSIGNMENT_DETAILS").equals(saveStepEnumME)
							&& moiDetailsType != null
							&& moiDetailsType.getUserSelectedMOI() != null) {
						final String methodOfInspection = moiDetailsType
								.getUserSelectedMOI().getMethodOfInspection();
						if (logger.isLoggable(java.util.logging.Level.INFO)) {
							logger.info("methodOfInspection in doDriveInAppointmentActivityLog :"
									+ methodOfInspection);
						}
						if (methodOfInspection != null
								&& AppraisalAssignmentConstants.DRIVE_IN
										.equals(methodOfInspection)
								&& scheduleDateTime != null) {

							StringBuffer activityLogDesc = new StringBuffer();
							final SimpleDateFormat sdf = new SimpleDateFormat(
									"MM/dd/yyyy HH:mm:ss");

							/*
							 * DateTime conversion based on time zone | START
							 */
							// Get the timezone from vehicle location details
							String timeZoneDisc = inputAppraisalAssignmentDTO
									.getTimeZone();
							if (logger.isLoggable(java.util.logging.Level.INFO)) {
								logger.info("Time Zone fetched from inputDTO : "
										+ timeZoneDisc);
							}

							Map<String, String> timeZoneMap = createTimeZoneMapFromSetFile(), miTimeZoneLabelMap = createMITimeZoneLabelMap();

							String timeZone = "PST", mitchellTZLabel = "";
							if (timeZoneDisc != null
									&& !timeZoneDisc.trim().equals("")) {
								if (logger
										.isLoggable(java.util.logging.Level.INFO)) {
									logger.info("TimeZone found in Vehicle Location Details : "
											+ timeZoneDisc);
								}
								// timezone found, convert it into standard 3
								// letter timezone
								timeZone = timeZoneMap.get(timeZoneDisc);
								mitchellTZLabel = miTimeZoneLabelMap
										.get(timeZoneDisc);
							} else {
								logger.severe("TimeZone not found in Vehicle Location Details");
								/*
								 * MitchellEnvelopeHelper meHelper = new
								 * MitchellEnvelopeHelper(
								 * inputAppraisalAssignmentDTO
								 * .getMitchellEnvelopDoc()); final Long
								 * moiOrgID = mitchellEnvelopeHandler.
								 * getMOIOrgIdFromMitchellEnvelop(meHelper); if
								 * (
								 * logger.isLoggable(java.util.logging.Level.INFO
								 * )) { logger.info(
								 * "TimeZone not found in Vehicle Location Details. "
								 * + "Fetching from OrgId : "+moiOrgID); }
								 */
								timeZone = null;
								timeZoneDisc = "N/A";
							}

							// call the method to convert datetime base on time
							// zone.
							String date = formatDateToTimeZone(
									scheduleDateTime.getTime(), timeZone);

							/*
							 * DateTime conversion based on time zone | END
							 */
							String[] dateTime = { "", "", "" , "" };
							if (date != null) {
								dateTime = date.split(" ");
							}
							if (logger.isLoggable(java.util.logging.Level.INFO)) {
								logger.info("date in doDriveInAppointmentActivityLog :"
										+ Arrays.toString(dateTime)
										+ " "
										+ mitchellTZLabel);
							}
							if(dateTime!=null && dateTime.length==4){
								activityLogDesc
										.append(appraisalAssignmentConfig
												.getDriveInAppointmentActivityLog())
										.append(" ").append(dateTime[0])
										.append(" at ").append(dateTime[1])
										.append(" ").append(dateTime[2])
										.append(" " + mitchellTZLabel);
							}
							if (logger.isLoggable(java.util.logging.Level.INFO)) {
								logger.info("activityLogDesc in doDriveInAppointmentActivityLog :"
										+ activityLogDesc);
							}
							saveExposureActivityLog(activityLogDesc.toString(),
									claimExposureId, null, workItemID,
									logedInUserInfoDocument);
						}
					}
				} else {
					if (logger.isLoggable(java.util.logging.Level.INFO)) {
						logger.info("AdditionalAppraisalAssignmentInfoType is NULL in doDriveInAppointmentActivityLog");
					}
				}
			} else {
				if (logger.isLoggable(java.util.logging.Level.INFO)) {
					logger.info("AdditionalAppraisalAssignmentInfoDocument is NULL in doDriveInAppointmentActivityLog");
				}
			}
		} catch (final Exception exception) {
			final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA_EXPOSUREACTIVITYLOG;
			final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_EXPOSUREACTIVITYLOG_MSG;
			errorLogUtil.logWarning(CLASS_NAME, METHOD_NAME, errorCode,
					errorDescription, workItemID, exception, claimNumber,
					logedInUserInfoDocument);

		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

	}

	/**
	 * Creates a hashmap of time zone mitchell label mapping from SET file
	 * 
	 * @return timeZoneMap
	 * @throws MitchellException
	 */
	private Map<String, String> createMITimeZoneLabelMap()
			throws MitchellException {
		Map<String, String> timeZoneLabelMap = new HashMap<String, String>();
		String tzMappingStr = appraisalAssignmentConfig
				.getTimezonemappingList();
		String[] tzMappingList = tzMappingStr.split(";");
		for (String tz : tzMappingList) {
			String[] tzMapping = tz.split(",");
			timeZoneLabelMap.put(tzMapping[0], tzMapping[2]);
		}

		return timeZoneLabelMap;
	}

	/**
	 * Creates a hashmap of time zone mapping from SET file
	 * 
	 * @return timeZoneMap
	 * @throws MitchellException
	 */
	private Map<String, String> createTimeZoneMapFromSetFile()
			throws MitchellException {
		Map<String, String> timeZoneMap = new HashMap<String, String>();
		String tzMappingStr = appraisalAssignmentConfig
				.getTimezonemappingList();
		String[] tzMappingList = tzMappingStr.split(";");
		for (String tz : tzMappingList) {
			String[] tzMapping = tz.split(",");
			timeZoneMap.put(tzMapping[0], tzMapping[1]);
		}

		return timeZoneMap;
	}

	/**
	 * Utility function to convert java Date to TimeZone format
	 * 
	 * @param date
	 * @param format
	 * @param timeZone
	 * @return
	 */
	private String formatDateToTimeZone(java.util.Date date, String timeZone) {
		if (date == null)
			return null;
		
		String format = "MM/dd/yyyy hh:mm:ss aa z";
		// create SimpleDateFormat object with input format
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}

		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));

		// return Date in required format with timezone as String
		return sdf.format(date);
	}

	public PriorityType convertPriorityToString(int priorityTypeInt)
			throws MitchellException {
		final String METHOD_NAME = "convertPriorityToString";
		logger.entering(CLASS_NAME, METHOD_NAME);

		String priorityTypeString = null;
		PriorityType priType;

		try {
			switch (priorityTypeInt) {
			case SWITCH_CASE_ELEVATED_PRIORITY:
				priorityTypeString = "ELEVATED_PRIORITY";
				break;
			case SWITCH_CASE_HIGH_PRIORITY:
				priorityTypeString = "HIGH_PRIORITY";
				break;
			case SWITCH_CASE_MUST_SEE_DATE:
				priorityTypeString = "MUST_SEE_DATE";
				break;
			case SWITCH_CASE_MUST_SEE_TIME:
				priorityTypeString = "MUST_SEE_TIME";
				break;
			case SWITCH_CASE_STANDARD_PRIORITY:
				priorityTypeString = "STANDARD_PRIORITY";
				break;
			default:
				break;
			}
			if (priorityTypeString == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("priorityTypeString == null");
				}
			}

			priType = PriorityType.Factory.newInstance();
			priType.setStringValue(priorityTypeString);
			priType.setPriorityValue(priorityTypeInt);
			logger.exiting(CLASS_NAME, METHOD_NAME);
		} catch (Exception e) {
			logger.severe("Exception occured in convertPriorityToString  Method ");
			final MitchellException mitchellException = new MitchellException(
					CLASS_NAME, "convertTimeToDuration",
					"Exception occured in convertPriorityToString  Method", e);
			throw mitchellException;
		}

		return priType;
	}

	public com.mitchell.schemas.dispatchservice.PriorityType priorityTypeToString(
			int priorityTypeInt) throws MitchellException {
		final String METHOD_NAME = "convertPriorityToString";
		logger.entering(CLASS_NAME, METHOD_NAME);

		String priorityTypeString = null;
		com.mitchell.schemas.dispatchservice.PriorityType priType;

		try {
			switch (priorityTypeInt) {
			case SWITCH_CASE_ELEVATED_PRIORITY:
				priorityTypeString = "ELEVATED_PRIORITY";
				break;
			case SWITCH_CASE_HIGH_PRIORITY:
				priorityTypeString = "HIGH_PRIORITY";
				break;
			case SWITCH_CASE_MUST_SEE_DATE:
				priorityTypeString = "MUST_SEE_DATE";
				break;
			case SWITCH_CASE_MUST_SEE_TIME:
				priorityTypeString = "MUST_SEE_TIME";
				break;
			case SWITCH_CASE_STANDARD_PRIORITY:
				priorityTypeString = "STANDARD_PRIORITY";
				break;
			default:
				break;
			}
			if (priorityTypeString == null) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("priorityTypeString == null");
				}
			}

			priType = com.mitchell.schemas.dispatchservice.PriorityType.Factory
					.newInstance();
			priType.setStringValue(priorityTypeString);
			priType.setPriorityValue(priorityTypeInt);
			logger.exiting(CLASS_NAME, METHOD_NAME);
		} catch (Exception e) {
			logger.severe("Exception occured in convertPriorityToString  Method ");
			final MitchellException mitchellException = new MitchellException(
					CLASS_NAME, "convertTimeToDuration",
					"Exception occured in convertPriorityToString  Method", e);
			throw mitchellException;
		}

		return priType;
	}

	// @formatter:off
	/*
	 * This method will take a provided GeoResult and normalize the latitude and
	 * longitude contained therein.
	 * 
	 * The formula for normalizing the latitude and longitude is as follows:
	 * 		Latitude:
	 * 			1. Multiply the provided value by the scaler constant:
	 * 			   LAT_LONG_NORMALIZE_SCALER
	 * 			2. Cast the result from step 1 to a long. This
	 * 			   will remove any unnecessary data after the decimal point
	 * 			3. Cast the result of step 2 to a double as this is the type of the value in
	 * 			   the GeoResult
	 * 
	 * Longitude: 1. See Latitude.
	 * 
	 * The caller is expected know before calling this method that the latitude
	 * and longitude are not already normalized
	 */
	// @formatter:on
	protected void normalizeLatitudeAndLongitude(GeoResult toUpdate) {
		// Get the current version of the latitude and longitude from the
		// provided GeoResult
		double latitudeToNormalize = toUpdate.getLatLong().getLatitude();
		double longitudeToNormalize = toUpdate.getLatLong().getLongitude();

		// Normalize the latitude
		double normalizedLatitude = (double) ((long) (latitudeToNormalize * LAT_LONG_NORMALIZE_SCALER));

		// Normalize the longitude
		double normalizedLongitude = (double) ((long) (longitudeToNormalize * LAT_LONG_NORMALIZE_SCALER));

		// Set the normalized latitudes and longitudes in the provided GeoResult
		toUpdate.getLatLong().setLatitude(normalizedLatitude);
		toUpdate.getLatLong().setLongitude(normalizedLongitude);
	}

	protected GeoServiceClient getNewGeoServiceClient() {
		return new GeoServiceClient();
	}

}
