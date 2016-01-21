package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.MitchellException.SEVERITY;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.apddelivery.APDAlertInfoType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentNotificationInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.APDUserInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.EmailAddressesType;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.dao.CultureDAO;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.AppraisalAssignmentProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.ErrorLogProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.NotificationProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformer;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.CustomSettingHelper;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.notification.client.Notification;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.notification.types.FaxRequestDocument;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;

public abstract class AbstractAssignmentEmailHandler implements
		AssignmentEmailDeliveryHandler {

	private static final int MAX_FAX_SUBJECT_LENGTH = 64;
	private static final int MAX_FAX_FROM_LENGTH = 30;
	private static final String CLASS_NAME = AbstractAssignmentEmailHandler.class
			.getName();
	private AssignmentDeliveryLogger mLogger;
	private static final String ADP_DELV_CONST_ORIGINAL_ESTIMATE_ARTIFACT_TYPE = "ORIGINAL_ESTIMATE";
	private static final String ADP_DELV_CONST_ALERT_ARTIFACT_TYPE = "ALERT";

	protected static class EmailData {
		public String fromDisplayName;
		public String fromAddress;
		public String toAddress;
		public String subject;
		public String messageBody;
		public String toCCAddress;
	}

	protected SystemConfigProxy systemConfigProxy;
	protected ErrorLogProxy errorLogProxy;
	protected AppLogProxy appLogProxy;
	protected NotificationProxy notificationProxy;
	protected XsltTransformer xsltTransformer;
	protected AppraisalAssignmentProxy appraisalAssignmentProxy;
	protected CustomSettingHelper customSettingHelper;

	private CultureDAO cultureDAO;

	private Logger logger = Logger.getLogger(CLASS_NAME);

	public SystemConfigProxy getSystemConfigProxy() {
		return systemConfigProxy;
	}

	public void setSystemConfigProxy(SystemConfigProxy systemConfigProxy) {
		this.systemConfigProxy = systemConfigProxy;
	}

	public ErrorLogProxy getErrorLogProxy() {
		return errorLogProxy;
	}

	public void setErrorLogProxy(ErrorLogProxy errorLogProxy) {
		this.errorLogProxy = errorLogProxy;
	}

	public AppLogProxy getAppLogProxy() {
		return appLogProxy;
	}

	public void setAppLogProxy(AppLogProxy appLogProxy) {
		this.appLogProxy = appLogProxy;
	}

	public NotificationProxy getNotificationProxy() {
		return notificationProxy;
	}

	public void setNotificationProxy(NotificationProxy notificationProxy) {
		this.notificationProxy = notificationProxy;
	}

	public void setAssignmentEmailDevlieryUtils(
			AssignmentEmailDeliveryUtils utils) {
		this.assignmentEmailUtils = utils;
	}

	public AssignmentDeliveryLogger getmLogger() {
		return mLogger;
	}

	public void setmLogger(AssignmentDeliveryLogger mLogger) {
		this.mLogger = mLogger;
	}

	public CultureDAO getCultureDAO() {
		return cultureDAO;
	}

	public void setCultureDAO(CultureDAO cultureDAO) {
		this.cultureDAO = cultureDAO;
	}

	public CustomSettingHelper getCustomSettingHelper() {
		return customSettingHelper;
	}

	public void setCustomSettingHelper(CustomSettingHelper customSettingHelper) {
		this.customSettingHelper = customSettingHelper;
	}

	protected UserInfoProxy userInfoProxy;
	protected AssignmentEmailDeliveryUtils assignmentEmailUtils = AssignmentEmailDeliveryUtils
			.getInstance();

	public AbstractAssignmentEmailHandler() {
		super();
	}

	public void deliverCreation(APDDeliveryContextDocument apdContextDoc,
			boolean isOverrideToEmailAddress, String culture)
			throws MitchellException {
		mLogger.info("Entering in deliverCreation");
		if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
			mLogger.info("context document" + apdContextDoc);
		}

		try
		{
			checkProxies();

			String toAddress = null;
			String toAddressOveride = null;
			String toCCAddress = null;
			
			if (isOverrideToEmailAddress) {
				toAddressOveride = getOverriddenEmailAddress(apdContextDoc);
				toAddress = toAddressOveride;

				toCCAddress = getOverriddenCCEmailAddress(apdContextDoc);
			
			} else {
				toAddress = apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
						.getTargetUserInfo().getUserInfo().getEmail();
			}

			if ((toAddress != null && toAddress.trim().length() > 0 && !toAddress.isEmpty()) 
					|| (toCCAddress != null && toCCAddress.trim().length() > 0 && !toCCAddress.isEmpty()) ) {

				final String fromDisplayName = this.assignmentEmailUtils
						.getCompanyName(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo().getTargetUserInfo());

				final String fromAddress = systemConfigProxy
						.getStringValue(
								AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,
								"donot_reply@mitchell.com");

				final String messageBody = createEmailMessage4Creation(
						apdContextDoc, culture);

				final String subject = createEmailSubject4Creation(
						apdContextDoc, culture);

				final EmailData emailData = createEmailData(fromDisplayName,
						fromAddress, messageBody, subject, toAddress);
				
				if(toAddress==""|| toAddress.length()==0||toAddress.isEmpty())
					emailData.toAddress = null;
				
				// Done for Bug 540611 : Adding cc to email data
				if(toCCAddress==""|| toCCAddress.length()==0||toCCAddress.isEmpty())
					emailData.toCCAddress = null;
				else
					emailData.toCCAddress = toCCAddress;
						
				final UserInfoDocument userInfoDoc = this.assignmentEmailUtils
						.getUserInfo(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo());

				final String workItemId = this.assignmentEmailUtils
						.getWorkItemId(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo());

				sendEmail(emailData, userInfoDoc, workItemId);

				final AppLoggingDocument logDoc = createAppLogDoc(apdContextDoc
						.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo(),
						userInfoDoc);
				
				if(toAddress==null || toAddress.trim().length() < 1 ){
					toAddress = toCCAddress;
				}
						
				final AppLoggingNVPairs appLoggingNVPairs = createAppLogNVPairs4AssignmentEmail(
						apdContextDoc, toAddress);

				logDoc.getAppLogging()
						.setTransactionType(
								AssignmentEmailDeliveryConstants.EVENT_LOG_ASG_EMAIL_SENT);

				appLogProxy.logAppEvent(logDoc, userInfoDoc, workItemId,
						AssignmentEmailDeliveryConstants.APPLICATION_NAME,
						AssignmentEmailDeliveryConstants.MODULE_NAME,
						appLoggingNVPairs);

				final long userOrgId = Long.parseLong(apdContextDoc
						.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
						.getTargetUserInfo().getUserInfo().getOrgID());

				// TO-DO Control Fax send behaviour
				if (assignmentEmailUtils
						.isFaxOverrideReqdForRCConnect(apdContextDoc))
				{
					final String faxNumber = userInfoProxy
							.getUserDetail(userOrgId).getUserDetail().getFax();

					if (faxNumber != null && faxNumber.trim().length() > 0) {
						final String shopName = this.assignmentEmailUtils
								.getShopName(apdContextDoc
										.getAPDDeliveryContext()
										.getAPDAppraisalAssignmentInfo()
										.getAPDCommonInfo().getTargetUserInfo());

						// Fax Subject Limited in Notification Service so build
						// it separate from email subject.
						final String faxSubject = createFaxSubject4Creation(
								apdContextDoc, culture);

						// Fax from name is limited so truncate as needed.
						String faxFromDisplayName = fromDisplayName;
						if (faxFromDisplayName.length() > MAX_FAX_FROM_LENGTH) {
							faxFromDisplayName = fromDisplayName.substring(0,
									MAX_FAX_FROM_LENGTH);
						}

						final FaxRequestDocument faxReqDoc = notificationProxy
								.buildFaxRequest(faxFromDisplayName,
										(String) null, fromAddress,
										faxFromDisplayName, (String) null,
										(String) null, faxSubject, shopName,
										shopName, faxNumber, (String) null,
										(String) null, faxSubject, true,
										(String) null);

						notificationProxy.appendCoverTexts(faxReqDoc,
								new String[] { faxSubject });
						final String tempFolder = systemConfigProxy
								.getStringValue(AssignmentEmailDeliveryConstants.SYS_CONFIG_TEMP_FOLDER);

						final String faxMessage = createFaxMessage4Creation(
								apdContextDoc, culture);
						final String pdfPath = this.assignmentEmailUtils
								.getPdfPath(tempFolder);
						this.assignmentEmailUtils
								.createPDF(faxMessage, pdfPath);

						this.notificationProxy.stageFaxAttachment(faxReqDoc,
								pdfPath);
						this.notificationProxy
								.notifyByFax(
										faxReqDoc,
										userInfoDoc,
										workItemId,
										AssignmentEmailDeliveryConstants.APPLICATION_NAME,
										AssignmentEmailDeliveryConstants.MODULE_NAME);

						this.assignmentEmailUtils.deleteTempFile(pdfPath);

						logDoc.getAppLogging()
								.setTransactionType(
										AssignmentEmailDeliveryConstants.EVENT_LOG_ASG_FAX_SENT);
						final AppLoggingNVPairs appLoggingNVPairs4Fax = createAppLogNVPairs4AssignmentFax(
								apdContextDoc, faxNumber);
						appLogProxy
								.logAppEvent(
										logDoc,
										userInfoDoc,
										workItemId,
										AssignmentEmailDeliveryConstants.APPLICATION_NAME,
										AssignmentEmailDeliveryConstants.MODULE_NAME,
										appLoggingNVPairs4Fax);
					}
				} else {
					logger.info("Not sending RCConnect Fax as fax is overrided...");
				}
			} else {
				logError4NoEmail(apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
						.getTargetUserInfo());
			}

		} catch (MitchellException e) {
			if (e.getType() < AssignmentDeliveryErrorCodes.ERROR_START
					|| e.getType() > AssignmentDeliveryErrorCodes.ERROR_END) {
				e.setType(AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL);
			}
			errorLogProxy.logError(e);
			throw e;
		} catch (Throwable e) {

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
					getClassName(), "deliverCreation", e.getMessage(), e);

			errorLogProxy.logError(me);
			throw me;
		}
		mLogger.info("Exiting in deliverCreation");
	}

	protected abstract String createEmailMessage4Creation(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws Exception;

	public String createEmailSubject4Creation(
			final APDDeliveryContextDocument apdContextDoc, String culutre)
			throws MitchellException {

		final String xmlStr = this.assignmentEmailUtils
				.createEmailSubjectXml4Creation(apdContextDoc);
		String coCode = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode();
		culutre = getBillingualCode(culutre, coCode);
		final String subject = transformCustomXslt(xmlStr, culutre);
		return subject;
	}

	protected String createFaxSubject4Creation(
			final APDDeliveryContextDocument apdContextDoc, String culture)
			throws MitchellException {
		String xmlStr = this.assignmentEmailUtils
				.createEmailSubjectXml4Creation(apdContextDoc);
		String subject = transform(xmlStr, culture);

		// Just truncate if it is too long

		if (subject.length() > MAX_FAX_SUBJECT_LENGTH) {
			subject = subject.substring(0, MAX_FAX_SUBJECT_LENGTH);
		}

		return subject;
	}

	protected abstract String createFaxMessage4Creation(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws Exception;

	protected abstract String getClassName();

	private String getCultureCode(
			final APDDeliveryContextDocument aPDDeliveryContextDocument)
			throws MitchellException {
		logger.info("Entering in getCultureCode in AbstractAssignmentEmailHandler.");

		String coCode = null;
		if (aPDDeliveryContextDocument != null) {
			APDDeliveryContextType context = aPDDeliveryContextDocument
					.getAPDDeliveryContext();
			if (context != null) {
				if (context.isSetAPDAppraisalAssignmentInfo()) {
					APDAppraisalAssignmentInfoType apdAANInfo = context
							.getAPDAppraisalAssignmentInfo();
					if (apdAANInfo != null) {
						BaseAPDCommonType bAPDCType = apdAANInfo
								.getAPDCommonInfo();
						if (bAPDCType != null) {
							coCode = bAPDCType.getInsCoCode();
						}
					}

				} else if (context
						.isSetAPDAppraisalAssignmentNotificationInfo()) {
					APDAppraisalAssignmentNotificationInfoType aPDAANType = context
							.getAPDAppraisalAssignmentNotificationInfo();
					if (aPDAANType != null) {
						BaseAPDCommonType bAPDCType = aPDAANType
								.getAPDCommonInfo();
						if (bAPDCType != null) {
							coCode = bAPDCType.getInsCoCode();
						}
					}

				} else if (context.isSetAPDAlertInfo()) {
					logger.info("Getting Company code from AlertInfo");
					APDAlertInfoType apdAIType = context.getAPDAlertInfo();
					if (apdAIType != null) {
						BaseAPDCommonType bAPDCType = apdAIType
								.getAPDCommonInfo();
						if (bAPDCType != null) {
							coCode = bAPDCType.getInsCoCode();
						}
					}

				}

			}

		}

		logger.info("handleOriginalEmails:coCode:" + coCode);
		// getting culture from database
		String cultureCode = cultureDAO.getCultureByCompany(coCode);
		logger.info("cultue code is :" + coCode);
		if (cultureCode == null || cultureCode.isEmpty()) {
			cultureCode = AssignmentDeliveryConstants.DEFAULT_CULTURE_CODE;
			logger.info("CultureCode set to default :" + cultureCode);
		}

		logger.info("Exiting  getCultureCode in AbstractAssignmentEmailHandler.");
		return cultureCode;

	}

	public void deliverUploadSuccess(APDDeliveryContextDocument apdContextDoc)
			throws MitchellException {

		logger.info("Entering in deliverUploadSuccess in AbstractAssignmentEmailHandler");

		if (mLogger.isLoggable(Level.INFO)) {
			mLogger.info("**Debug - deliverUploadSuccess:: APDDeliveryContextDocument --->"
					+ apdContextDoc);
		}

		// Context variables for improved error handling.
		long claimID = -1;
		String coCode = "";
		String clientClaimNumber = "";
		String workItemID = "";

		APDDeliveryContextType apdDeliveryContextType = apdContextDoc
				.getAPDDeliveryContext();
		APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfoType = apdDeliveryContextType
				.getAPDAppraisalAssignmentInfo();
		BaseAPDCommonType baseAPDCommonType = null;
		if (apdDeliveryContextType.getMessageType().equalsIgnoreCase(
				ADP_DELV_CONST_ORIGINAL_ESTIMATE_ARTIFACT_TYPE)) {
			baseAPDCommonType = apdAppraisalAssignmentInfoType
					.getAPDCommonInfo();
		} else if (apdDeliveryContextType.getMessageType().equalsIgnoreCase(
				ADP_DELV_CONST_ALERT_ARTIFACT_TYPE)) {
			baseAPDCommonType = apdDeliveryContextType.getAPDAlertInfo()
					.getAPDCommonInfo();
		}
		if (apdDeliveryContextType != null
				&& apdAppraisalAssignmentInfoType != null
				&& baseAPDCommonType != null) {
			coCode = baseAPDCommonType.getInsCoCode();
			clientClaimNumber = baseAPDCommonType.getClientClaimNumber();
			claimID = baseAPDCommonType.getClaimId();
			workItemID = baseAPDCommonType.getWorkItemId();
		}
		long inboundTaskID = -1;
		if (apdAppraisalAssignmentInfoType != null) {
			inboundTaskID = apdAppraisalAssignmentInfoType.getTaskId();
		}

		try {
			checkProxies();
			String toAddress = null;

			if (this.assignmentEmailUtils
					.isOverrideRequiredForUpload(apdContextDoc)) {

				MitchellEnvelopeDocument meDoc = this.assignmentEmailUtils
						.getMEDocFromAPDDeliveryContext(apdContextDoc);
				if (meDoc != null) {
					AdditionalAppraisalAssignmentInfoDocument aaaDoc = this.assignmentEmailUtils
							.getAdditionalAppraisalAssignmentInfoDocumentFromME(new MitchellEnvelopeHelper(
									meDoc));
					if (aaaDoc.getAdditionalAppraisalAssignmentInfo() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo()
									.getEmailAddressArray() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo()
									.getEmailAddressArray().length > 0)
						toAddress = aaaDoc
								.getAdditionalAppraisalAssignmentInfo()
								.getNotificationDetails()
								.getNotificationEmailTo()
								.getEmailAddressArray(0);
				}
			}

			else {
				toAddress = apdContextDoc.getAPDDeliveryContext()
						.getAPDAlertInfo().getAPDCommonInfo()
						.getTargetUserInfo().getUserInfo().getEmail();
			}

			if (toAddress != null && toAddress.trim().length() > 0) {

				final String fromDisplayName = this.assignmentEmailUtils
						.getCompanyName(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo()
								.getTargetUserInfo());

				final String fromAddress = systemConfigProxy
						.getStringValue(
								AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,
								"donot_reply@mitchell.com");

				// //For internationalization
				String culture = getCultureCode(apdContextDoc);
				logger.info("Culture code returned from getCultureCode"
						+ culture);

				final String messageBody = createEmailMessage4UploadSuccess(
						apdContextDoc, culture);

				final String subject = createEmailSubject4UploadSuccess(
						apdContextDoc, culture);

				final EmailData emailData = createEmailData(fromDisplayName,
						fromAddress, messageBody, subject, toAddress);

				final UserInfoDocument userInfoDoc = this.assignmentEmailUtils
						.getUserInfo(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo());

				final String workItemId = this.assignmentEmailUtils
						.getWorkItemId(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo());

				sendEmail(emailData, userInfoDoc, workItemId);

				final AppLoggingDocument logDoc = createAppLogDoc(apdContextDoc
						.getAPDDeliveryContext().getAPDAlertInfo()
						.getAPDCommonInfo(), userInfoDoc);

				final AppLoggingNVPairs appLoggingNVPairs = createAppLogNVParis4EstimateUpload(
						apdContextDoc, toAddress);

				logDoc.getAppLogging()
						.setTransactionType(
								AssignmentEmailDeliveryConstants.EVENT_LOG_UPLOAD_SUCCESS_EMAIL_SENT);

				appLogProxy.logAppEvent(logDoc, userInfoDoc, workItemId,
						AssignmentEmailDeliveryConstants.APPLICATION_NAME,
						AssignmentEmailDeliveryConstants.MODULE_NAME,
						appLoggingNVPairs);
			} else {
				logError4NoEmail(apdContextDoc.getAPDDeliveryContext()
						.getAPDAlertInfo().getAPDCommonInfo()
						.getTargetUserInfo());
			}

		} catch (MitchellException e) {
			if (e.getType() < AssignmentDeliveryErrorCodes.ERROR_START
					|| e.getType() > AssignmentDeliveryErrorCodes.ERROR_END) {
				e.setType(AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL);

				// Add context to error return
				final StringBuffer descM = new StringBuffer("");
				descM.append("Context:: ");
				if (baseAPDCommonType != null) {
					e.setCompanyCode(coCode);
					e.setWorkItemId(workItemID);
					descM.append("  CompanyCode: ").append(coCode);
					descM.append(", ClaimNumber: ").append(clientClaimNumber);
					descM.append(", ClaimID: ").append(claimID);
					descM.append(", WorkItemID: ").append(workItemID);
				}
				if (apdAppraisalAssignmentInfoType != null) {
					descM.append(", TaskID: ").append(inboundTaskID);
				}
				e.setDescription(descM.toString());
			}

			errorLogProxy.logError(e);
			throw e;
		} catch (Throwable e) {

			// Add context to error return
			final StringBuffer descT = new StringBuffer("");
			descT.append("Context:: ");
			if (baseAPDCommonType != null) {
				descT.append("  CompanyCode: ").append(coCode);
				descT.append(", ClaimNumber: ").append(clientClaimNumber);
				descT.append(", ClaimID: ").append(claimID);
				descT.append(", WorkItemID: ").append(workItemID);
			}
			if (apdAppraisalAssignmentInfoType != null) {
				descT.append(", TaskID: ").append(inboundTaskID);
			}
			descT.append(e.getMessage());

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_UPLOAD_SUCCESS_EMAIL,
					getClassName(), "deliverCreation", descT.toString(), e);

			errorLogProxy.logError(me);
			throw me;
		}
		logger.info("Exiting deliverUploadSuccess AbstractAssignmentEmailHandler");
	}

	public abstract String createEmailMessage4UploadSuccess(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws Exception;

	public void deliverUploadFail(APDDeliveryContextDocument apdContextDoc)
			throws MitchellException {
		logger.info("Extering deliverUploadFail in AbstractAssignmentEmailHandler ");
		try {
			checkProxies();
			String overrideEmailId = null;
			String toAddress = null;

			if (this.assignmentEmailUtils
					.isOverrideRequiredForUpload(apdContextDoc)) {

				MitchellEnvelopeDocument meDoc = this.assignmentEmailUtils
						.getMEDocFromAPDDeliveryContext(apdContextDoc);
				if (meDoc != null) {
					AdditionalAppraisalAssignmentInfoDocument aaaDoc = this.assignmentEmailUtils
							.getAdditionalAppraisalAssignmentInfoDocumentFromME(new MitchellEnvelopeHelper(
									meDoc));
					if (aaaDoc.getAdditionalAppraisalAssignmentInfo() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo()
									.getEmailAddressArray() != null
							&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
									.getNotificationDetails()
									.getNotificationEmailTo()
									.getEmailAddressArray().length > 0)
						toAddress = aaaDoc
								.getAdditionalAppraisalAssignmentInfo()
								.getNotificationDetails()
								.getNotificationEmailTo()
								.getEmailAddressArray(0);
				}
			}

			else {
				toAddress = apdContextDoc.getAPDDeliveryContext()
						.getAPDAlertInfo().getAPDCommonInfo()
						.getTargetUserInfo().getUserInfo().getEmail();
			}

			if (toAddress != null && toAddress.trim().length() > 0) {

				final String fromDisplayName = this.assignmentEmailUtils
						.getCompanyName(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo()
								.getTargetUserInfo());

				final String fromAddress = systemConfigProxy
						.getStringValue(
								AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,
								"donot_reply@mitchell.com");

				// For internationalization
				String culture = getCultureCode(apdContextDoc);
				logger.info("Culture code returned from getCultureCode"
						+ culture);

				final String messageBody = createEmailMessage4UploadFail(
						apdContextDoc, culture);

				final String subject = createEmailSubject4UploadFail(
						apdContextDoc, culture);

				final EmailData emailData = createEmailData(fromDisplayName,
						fromAddress, messageBody, subject, toAddress);

				final UserInfoDocument userInfoDoc = this.assignmentEmailUtils
						.getUserInfo(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo());

				final String workItemId = this.assignmentEmailUtils
						.getWorkItemId(apdContextDoc.getAPDDeliveryContext()
								.getAPDAlertInfo().getAPDCommonInfo());

				sendEmail(emailData, userInfoDoc, workItemId);

				final AppLoggingDocument logDoc = createAppLogDoc(apdContextDoc
						.getAPDDeliveryContext().getAPDAlertInfo()
						.getAPDCommonInfo(), userInfoDoc);

				final AppLoggingNVPairs appLoggingNVPairs = createAppLogNVParis4EstimateUpload(
						apdContextDoc, toAddress);

				logDoc.getAppLogging()
						.setTransactionType(
								AssignmentEmailDeliveryConstants.EVENT_LOG_UPLOAD_FAIL_EMAIL_SENT);

				appLogProxy.logAppEvent(logDoc, userInfoDoc, workItemId,
						AssignmentEmailDeliveryConstants.APPLICATION_NAME,
						AssignmentEmailDeliveryConstants.MODULE_NAME,
						appLoggingNVPairs);
			} else {
				logError4NoEmail(apdContextDoc.getAPDDeliveryContext()
						.getAPDAlertInfo().getAPDCommonInfo()
						.getTargetUserInfo());
			}

		} catch (MitchellException e) {
			if (e.getType() < AssignmentDeliveryErrorCodes.ERROR_START
					|| e.getType() > AssignmentDeliveryErrorCodes.ERROR_END) {
				e.setType(AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL);
			}
			errorLogProxy.logError(e);
			throw e;
		} catch (Throwable e) {

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_UPLOAD_FAIL_EMAIL,
					getClassName(), "deliverCreation", e.getMessage(), e);

			errorLogProxy.logError(me);
			throw me;
		}
		logger.info("Exiting deliverUploadFail in AbstractAssignmentEmailHandler ");
	}

	public abstract String createEmailMessage4UploadFail(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws Exception;

	public AppraisalAssignmentProxy getAppraisalAssignmentProxy() {
		return appraisalAssignmentProxy;
	}

	public void setAppraisalAssignmentProxy(
			AppraisalAssignmentProxy appraisalAssignmentProxy) {
		this.appraisalAssignmentProxy = appraisalAssignmentProxy;
	}

	public XsltTransformer getXsltTransformer() {
		return xsltTransformer;
	}

	public void setXsltTransformer(XsltTransformer xsltTransformer) {
		this.xsltTransformer = xsltTransformer;
	}

	protected void checkProxies() {
		if (systemConfigProxy == null || errorLogProxy == null
				|| appLogProxy == null || notificationProxy == null
				|| xsltTransformer == null || userInfoProxy == null) {

			throw new IllegalStateException("One of the Proxies is not set.");
		}
	}

	public UserInfoProxy getUserInfoProxy() {
		return userInfoProxy;
	}

	public void setUserInfoProxy(UserInfoProxy userInfoProxy) {
		this.userInfoProxy = userInfoProxy;
	}

	protected EmailData createEmailData(final String fromDisplayName,
			final String fromAddress, final String messageBody,
			final String subject, final String toAddress) {
		final EmailData emailData = new EmailData();
		emailData.fromDisplayName = fromDisplayName;
		emailData.fromAddress = fromAddress;
		emailData.messageBody = messageBody;
		emailData.subject = subject;
		emailData.toAddress = toAddress;
		return emailData;
	}

	public void deliverCancelation(APDDeliveryContextDocument apdContextDoc)
			throws MitchellException {

		throw new UnsupportedOperationException(
				"This method is not implemented yet.");
	}

	public void sendEmail(final EmailData emailData,
			final UserInfoDocument userInfoDoc, final String workItemId)
			throws MitchellException {

		final EmailRequestDocument emailReqDoc = notificationProxy
				.buildEmailRequest(emailData.fromDisplayName,
						emailData.fromAddress, emailData.toAddress,
						emailData.subject, emailData.messageBody);
		
		// Done for Bug 540611 : Adding cc to emailReqDoc if cc is present
		if(emailData.toCCAddress != null){
			Notification.setEmailCCs(emailReqDoc, emailData.toCCAddress, null);
		}
		
		notificationProxy.notifyByEmail(emailReqDoc, userInfoDoc, workItemId,
				AssignmentEmailDeliveryConstants.APPLICATION_NAME,
				AssignmentEmailDeliveryConstants.MODULE_NAME,
				AssignmentEmailDeliveryConstants.MODULE_NAME);
	}

	protected void logError4NoEmail(final APDUserInfoType apdUserInfoType) {

		final StringBuffer strBuffer = new StringBuffer("No email for user ");
		strBuffer.append(apdUserInfoType.getUserInfo().getOrgCode())
				.append("/").append(apdUserInfoType.getUserInfo().getUserID());

		final MitchellException me = new MitchellException(
				AssignmentDeliveryErrorCodes.ERROR_NO_EMAIL, getClassName(),
				"logError4NoEmail", strBuffer.toString());

		me.setSeverity(SEVERITY.WARNING);
		errorLogProxy.logError(me);
	}

	protected AppLoggingDocument createAppLogDoc(
			final BaseAPDCommonType apdContextDoc,
			final UserInfoDocument userInfoDoc) {
		final AppLoggingDocument logDoc = AppLoggingDocument.Factory
				.newInstance();
		logDoc.addNewAppLogging();
		logDoc.getAppLogging().setClaimNumber(
				apdContextDoc.getClientClaimNumber());
		logDoc.getAppLogging().setModuleName(
				AssignmentEmailDeliveryConstants.MODULE_NAME);
		logDoc.getAppLogging().setClaimExposureId(apdContextDoc.getSuffixId());
		logDoc.getAppLogging().setMitchellUserId(
				userInfoDoc.getUserInfo().getUserID());
		return logDoc;
	}

	protected AppLoggingNVPairs createAppLogNVPairs4AssignmentEmail(
			final APDDeliveryContextDocument apdContextDoc,
			final String toAddress) {
		final AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();
		appLoggingNVPairs.addPair("CoCd", apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode());
		appLoggingNVPairs.addPair(
				"taskId",
				String.valueOf(apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getTaskId()));
		appLoggingNVPairs.addEmail("ToAddress", toAddress);
		return appLoggingNVPairs;
	}

	protected AppLoggingNVPairs createAppLogNVPairs4AssignmentFax(
			final APDDeliveryContextDocument apdContextDoc,
			final String faxNumber) {
		final AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();
		appLoggingNVPairs.addPair("CoCd", apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getInsCoCode());
		appLoggingNVPairs.addFax("FaxNumber", faxNumber);
		return appLoggingNVPairs;
	}

	protected String transform(String xmlBuilder, String culture)
			throws MitchellException {
		String methodName = "transform";
		logger.entering(CLASS_NAME, methodName);
		String styleSheetFileName = systemConfigProxy
				.getStringValue("/AssignmentDelivery/AssignmentEmail/XSLTPath");

		String language = extractLanguageFromCultureCode(culture);
		String emailMessage = callXsltTransformer(xmlBuilder, language);
		logger.info("Text After language transformation --> " + emailMessage);
		logger.exiting(CLASS_NAME, methodName);
		return emailMessage;
	}

	protected String transformCustomXslt(String xmlBuilder, String culture)
			throws MitchellException {
		String emailMessage = callXsltTransformer(xmlBuilder, culture);

		if (logger.isLoggable(Level.INFO)) {
			logger.info("Text After language transformation --> "
					+ emailMessage);
		}
		return emailMessage;

	}

	private String extractLanguageFromCultureCode(String cultureCode) {
		int pos = cultureCode
				.indexOf(AssignmentDeliveryConstants.LOCALE_SEPARATOR);
		if (pos > 0) {
			return cultureCode.substring(0, pos);
		} else {
			return cultureCode;
		}
	}

	private String callXsltTransformer(String xmlBuilder, String language)
			throws MitchellException {
		String styleSheetFileName = systemConfigProxy
				.getStringValue(AssignmentDeliveryConstants.SET_FILE_XSLT_PATH);
		StringBuilder styleSheetFileNameTemp = new StringBuilder(
				styleSheetFileName)
				.append(AssignmentDeliveryConstants.ASSIGNMENT_EMAIL_WITH_UNDERSCORE)
				.append(language).append(AssignmentDeliveryConstants.DOT_XSLT);

		if (FileUtils.isExistingFile(styleSheetFileNameTemp.toString())) {
			styleSheetFileName = styleSheetFileNameTemp.toString();
		} else {

			styleSheetFileName = styleSheetFileName
					+ AssignmentDeliveryConstants.ASSIGNMENT_EMAIL_XSLT;
		}

		logger.info("Text before language transformation --> " + xmlBuilder);

		String emailMessage = xsltTransformer.transformXmlString(
				styleSheetFileName, xmlBuilder).trim();

		logger.info("Text After language transformation --> " + emailMessage);
		return emailMessage;
	}

	protected AppLoggingNVPairs createAppLogNVParis4EstimateUpload(
			final APDDeliveryContextDocument apdContextDoc,
			String toEmailAddress) {
		final AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();
		appLoggingNVPairs.addPair("CoCd", apdContextDoc.getAPDDeliveryContext()
				.getAPDAlertInfo().getAPDCommonInfo().getInsCoCode());
		appLoggingNVPairs.addEmail("ToAddress", toEmailAddress);

		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetCorrectionNumber()) {
			appLoggingNVPairs.addPair("CorrectionNumber", apdContextDoc
					.getAPDDeliveryContext().getAPDAlertInfo()
					.getCorrectionNumber());
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetEclaimEstId()) {
			appLoggingNVPairs
					.addPair("EclaimEstId", apdContextDoc
							.getAPDDeliveryContext().getAPDAlertInfo()
							.getEclaimEstId());
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetEstimateDate()) {
			appLoggingNVPairs.addDate("EstimateDate", apdContextDoc
					.getAPDDeliveryContext().getAPDAlertInfo()
					.getEstimateDate());
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetFolderAI()) {
			appLoggingNVPairs.addPair(
					"FolderAI",
					String.valueOf(apdContextDoc.getAPDDeliveryContext()
							.getAPDAlertInfo().getFolderAI()));
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetMcfFileName()) {
			appLoggingNVPairs
					.addFilename("MCFFileName", apdContextDoc
							.getAPDDeliveryContext().getAPDAlertInfo()
							.getMcfFileName());
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetMcfPackageType()) {
			appLoggingNVPairs.addPair("MCFPackageType", apdContextDoc
					.getAPDDeliveryContext().getAPDAlertInfo()
					.getMcfPackageType().toString());
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetNoEstimateReviewFlag()) {
			appLoggingNVPairs.addPair(
					"NoEstimateReviewFlag",
					String.valueOf(apdContextDoc.getAPDDeliveryContext()
							.getAPDAlertInfo().getNoEstimateReviewFlag()));
		}
		if (apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.isSetSupplementNumber()) {
			appLoggingNVPairs
					.addSupplementNumber(apdContextDoc.getAPDDeliveryContext()
							.getAPDAlertInfo().getEclaimEstId());
		}

		return appLoggingNVPairs;
	}

	public String createEmailSubject4UploadSuccess(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws MitchellException {
		String methodName = "createEmailSubject4UploadSuccess";
		logger.entering(CLASS_NAME, methodName);

		culture = getBillingualCode(culture, apdContextDoc
				.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo()
				.getInsCoCode());

		final String xml = this.assignmentEmailUtils
				.createEmailSubjectXml4UploadSuccess(apdContextDoc);

		final String emailMessage = transformCustomXslt(xml, culture);
		logger.exiting(CLASS_NAME, methodName);

		return emailMessage;
	}

	public String createEmailSubject4UploadFail(
			APDDeliveryContextDocument apdContextDoc, String culture)
			throws MitchellException {

		final String xml = this.assignmentEmailUtils
				.createEmailSubjectXml4UploadFail(apdContextDoc);
		final String emailMessage = transform(xml, culture);
		return emailMessage;
	}

	public String createEmailSubject4NonDrpSuppEmail(
			final APDDeliveryContextDocument apdContextDoc, String pCulture)
			throws MitchellException {
		String subject = null;
		final String xmlStr = this.assignmentEmailUtils
				.createEmailSubjectXml4NonDrpSuppEmail(apdContextDoc);

		String culture = getBillingualCode(pCulture, apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getInsCoCode());

		if (culture != null) {
			subject = transformCustomXslt(xmlStr, culture);
		}
		return subject;
	}

	public abstract AssignmentServiceContext populateAdditionalInfoInAssignmentServiceContext(
			AssignmentServiceContext svcContext);

	public void deliverSupplementEmail(
			final APDDeliveryContextDocument apdContextDoc,
			final ArrayList partsListAttachment,
			boolean isOverrideSupplementEmailAddress, String culture)
			throws MitchellException {
		if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
			mLogger.info("Entering in deliverSupplementEmail in AbstractAssignmentEmailHandler");
			mLogger.info("APDDeliveryContextDocument --->" + apdContextDoc);
			mLogger.info("partList attachement " + partsListAttachment);
		}

		String toAddress = null;
		String toAddressOveride = null;

		toAddressOveride = getOverriddenEmailAddress(apdContextDoc);

		if (isOverrideSupplementEmailAddress) {
			toAddress = toAddressOveride;
		} else {
			toAddress = apdContextDoc.getAPDDeliveryContext()
					.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
					.getTargetUserInfo().getUserInfo().getEmail();
		}

		try {
			checkProxies();
			/*
			 * final String toAddress = apdContextDoc.getAPDDeliveryContext()
			 * .getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
			 * .getTargetUserInfo().getUserInfo().getEmail();
			 */

			if (toAddress != null && toAddress.trim().length() > 0) {

				final String fromDisplayName = this.assignmentEmailUtils
						.getCompanyName(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo().getTargetUserInfo());

				final String fromAddress = systemConfigProxy
						.getStringValue(
								AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_FROMADDRESS,
								"donot_reply@mitchell.com");

				final AssignmentServiceContext svcContext = this.assignmentEmailUtils
						.createAssignmentServiceContext(apdContextDoc,
								partsListAttachment);
				populateAdditionalInfoInAssignmentServiceContext(svcContext);

				final String workItemId = this.assignmentEmailUtils
						.getWorkItemId(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo());
				if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
					mLogger.info("WorkItem id :" + workItemId);
				}

				final AssignmentDeliveryServiceDTO dto = mapToAppraisalAssignmentDTO(svcContext);
				mLogger.info("calling appraisalAssignmentProxy");
				final MitchellEnvelopeDocument mitEnvDoc = appraisalAssignmentProxy
						.retrieveSupplementRequestXMLDocAsMEDoc(dto, workItemId);
				if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
					mLogger.info("created mitchell envelop:" + mitEnvDoc);
				}

				final String messageBody = this.assignmentEmailUtils
						.retrieveSuppAsgEmailMessageBody(mitEnvDoc);
				if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
					mLogger.info("Message body :" + messageBody);
				}

				final String subject = createEmailSubject4NonDrpSuppEmail(
						apdContextDoc, culture);
				if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
					mLogger.info("subject:" + subject);
				}
				final EmailData emailData = createEmailData(fromDisplayName,
						fromAddress, messageBody, subject, toAddress);
				if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
					mLogger.info("EmailData: " + emailData);
				}
				final UserInfoDocument userInfoDoc = this.assignmentEmailUtils
						.getUserInfo(apdContextDoc.getAPDDeliveryContext()
								.getAPDAppraisalAssignmentInfo()
								.getAPDCommonInfo());

				sendEmail(emailData, userInfoDoc, workItemId);

				final AppLoggingDocument logDoc = createAppLogDoc(apdContextDoc
						.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo(),
						userInfoDoc);

				final AppLoggingNVPairs appLoggingNVPairs = createAppLogNVPairs4AssignmentEmail(
						apdContextDoc, toAddress);

				logDoc.getAppLogging()
						.setTransactionType(
								AssignmentEmailDeliveryConstants.EVENT_LOG_SUPP_ASG_EMAIL_SENT);

				appLogProxy.logAppEvent(logDoc, userInfoDoc, workItemId,
						AssignmentEmailDeliveryConstants.APPLICATION_NAME,
						AssignmentEmailDeliveryConstants.MODULE_NAME,
						appLoggingNVPairs);
			} else {
				logError4NoEmail(apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
						.getTargetUserInfo());
			}

		} catch (MitchellException e) {
			if (e.getType() < AssignmentDeliveryErrorCodes.ERROR_START
					|| e.getType() > AssignmentDeliveryErrorCodes.ERROR_END) {
				e.setType(AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL);
			}
			errorLogProxy.logError(e);
			throw e;
		} catch (Throwable e) {

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_SUPP_ASG_EMAIL,
					getClassName(), "deliverSupplementEmail", e.getMessage(), e);

			errorLogProxy.logError(me);
			throw me;
		}

	}

	private AssignmentDeliveryServiceDTO mapToAppraisalAssignmentDTO(
			AssignmentServiceContext context) {

		AssignmentDeliveryServiceDTO dto = new AssignmentDeliveryServiceDTO();
		if (context.getDrpUserInfo() != null)
			dto.setDrpUserInfo(context.getDrpUserInfo());
		if (context.getUserInfo() != null)
			dto.setUserInfo(context.getUserInfo());
		if (context.getMitchellEnvDoc() != null)
			dto.setMitchellEnvDoc(context.getMitchellEnvDoc());
		if (context.getWorkAssignmentId() != null)
			dto.setWorkAssignmentId(context.getWorkAssignmentId());

		dto.setDrp(context.isDrp());
		dto.setNonNetWorkShop(context.isNonNetWorkShop());
		dto.setShopPremium(context.isShopPremium());

		return dto;
	}

	public void deliverIAEmail(APDDeliveryContextDocument apdContextDoc,
			String culture) throws MitchellException {
		deliverCreation(apdContextDoc, false, culture);
	}

	/***
	 * This method is overridden to provide overriding of email while creating
	 * assignment at step 4 to the IAs who are upgraded to RC Premium or basic.
	 */
	public void deliverIAEmail(APDDeliveryContextDocument apdContextDoc,
			boolean isOverrideToEmailAddress, String culture)
			throws MitchellException {
		deliverCreation(apdContextDoc, isOverrideToEmailAddress, culture);
	}

	public void deliverSTAFFEmail(APDDeliveryContextDocument apdContextDoc,
			String culture) throws MitchellException {
		deliverCreation(apdContextDoc, false, culture);
	}

	private String getOverriddenEmailAddress(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {

		StringBuilder toAddressOveride = new StringBuilder();
		String toEmailArray[]=null;
		AdditionalAppraisalAssignmentInfoDocument aaAdditionalInfoDoc = null;

		try {

			aaAdditionalInfoDoc = this.assignmentEmailUtils
					.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(apdContextDoc);
			
			if (aaAdditionalInfoDoc != null
					&& aaAdditionalInfoDoc
							.getAdditionalAppraisalAssignmentInfo() != null
					&& aaAdditionalInfoDoc
							.getAdditionalAppraisalAssignmentInfo()
							.getNotificationDetails() != null
					&& aaAdditionalInfoDoc
							.getAdditionalAppraisalAssignmentInfo()
							.getNotificationDetails().getNotificationEmailTo() != null
					&& aaAdditionalInfoDoc
							.getAdditionalAppraisalAssignmentInfo()
							.getNotificationDetails().getNotificationEmailTo()
							.getEmailAddressArray() != null
					&& aaAdditionalInfoDoc
							.getAdditionalAppraisalAssignmentInfo()
							.getNotificationDetails().getNotificationEmailTo()
							.getEmailAddressArray().length > 0) {

				toEmailArray=aaAdditionalInfoDoc.getAdditionalAppraisalAssignmentInfo().getNotificationDetails().getNotificationEmailTo().getEmailAddressArray();// Array of To Email ID
				
				if((!toEmailArray.toString().isEmpty())&&(toEmailArray.length > 0)) {
							for (int i = 0; i < toEmailArray.length; i++) {
							toAddressOveride.append(toEmailArray[i]);
							if (i < (toEmailArray.length - 1)) {
								toAddressOveride.append(",");
							}
						}
					
				}
				logger.info("Value of toAddressOveride is as follows "
						+ toAddressOveride);
			}

		} catch (Exception e) {

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
					getClassName(), "getOverriddenEmailAddress",
					e.getMessage(), e);

			errorLogProxy.logError(me);
			throw me;
		}
		return toAddressOveride.toString();
	}

	/**
	 * Get the CC Email Address from the APD Delivery Context Document. On overriden condition 
	 * @param apdContextDoc :<code>APDDeliveryContextDocument</code>
	 * @return String
	 * @throws MitchellException
	 */
	private String getOverriddenCCEmailAddress(
			final APDDeliveryContextDocument apdContextDoc) throws MitchellException {
		StringBuilder toCCAddressOveride = new StringBuilder();
		String ccEmailArray[]=null;
		AdditionalAppraisalAssignmentInfoDocument aaAdditionalInfoDoc = null;
		try {
			aaAdditionalInfoDoc = this.assignmentEmailUtils
					.getAdditionalAssignmentInfoDocFromAPDAssignmentContext(apdContextDoc);
			
			if(aaAdditionalInfoDoc != null){
				final AdditionalAppraisalAssignmentInfoType appraisalAssignmentInfoType = aaAdditionalInfoDoc
						.getAdditionalAppraisalAssignmentInfo();

				if (appraisalAssignmentInfoType.getNotificationDetails() != null 
						&& appraisalAssignmentInfoType.getNotificationDetails()
						.getNotificationEmailCC() != null
						&& appraisalAssignmentInfoType.getNotificationDetails()
						.getNotificationEmailCC().getEmailAddressArray().length > 0) {
					
					ccEmailArray=aaAdditionalInfoDoc.getAdditionalAppraisalAssignmentInfo().getNotificationDetails().getNotificationEmailCC().getEmailAddressArray();// Array of CC Email ID
					
					if ((!ccEmailArray.toString().isEmpty())&&(ccEmailArray.length > 0)) {
							for (int i = 0; i < ccEmailArray.length; i++) {
							toCCAddressOveride.append(ccEmailArray[i]);
							if (i < (ccEmailArray.length - 1)) {
								toCCAddressOveride.append(",");
							}
						}
					} 
					
					logger.info("Value of toAddressOveride is as follows "
							+ toCCAddressOveride.toString());

				}
			}
		} catch (Exception e) {

			final MitchellException me = new MitchellException(
					AssignmentDeliveryErrorCodes.ERROR_ASG_EMAIL,
					getClassName(), "getOverriddenCCEmailAddress",
					e.getMessage(), e);

			errorLogProxy.logError(me);
			throw me;
		}
		return toCCAddressOveride.toString();
	}
	
	protected String getBillingualCode(String culture, String coCode)
			throws MitchellException {

		String languageFromCustomSettings = customSettingHelper
				.getCompanyCustomSetting(
						coCode,
						AssignmentDeliveryConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME,
						AssignmentDeliveryConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME);

		if (languageFromCustomSettings != null
				&& !languageFromCustomSettings.isEmpty()) {

			culture = languageFromCustomSettings;
			logger.info("culture in getBillingualCode method if languageFromCustomSettings has value:"
					+ culture);
		} else {

			culture = extractLanguageFromCultureCode(culture);
		}
		return culture;
	}

}