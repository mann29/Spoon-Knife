package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.xmlbeans.XmlException;

import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.DocumentInfoType;
import com.cieca.bms.LicenseType;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.OnlineInfoType;
import com.mitchell.common.types.OnlineUserType;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDUserInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EmailMessageDocument;
import com.mitchell.schemas.assignmentdelivery.AssignmentDeliveryEmailReqDocument;
import com.mitchell.schemas.assignmentdelivery.AssignmentDeliveryEmailReqType;
import com.mitchell.schemas.assignmentdelivery.EmailMessageDRPType;
import com.mitchell.schemas.assignmentdelivery.EmailMessageType;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxyImpl;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.core.workassignment.client.WorkAssignmentClient;
import com.mitchell.services.technical.claim.common.utils.ParseHelper;
import com.mitchell.services.technical.claim.exception.ClaimException;
import com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.sip.stdassignmentdelivery.util.StdAsgProcessUtilImpl;
import com.mitchell.sip.stdassignmentdelivery.util.StdAsgProcessingUtil;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.misc.XMLUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class AssignmentEmailDeliveryUtils {

	/** The Constant CLZ_NAME. */
	private static final String CLZ_NAME = AssignmentEmailDeliveryUtils.class
			.getName();

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(CLZ_NAME);

	private static final String SUPP_EMAIL_NAME = "supplementrequestemail";
	private static final String OPEN_BRACKET = "<";
	private static final String CLOSE_BRACKET = ">";
	private static final String OPEN_END_BRACKET = "</";

	private static final int RANGE = 9999;

	private static final String NONDRP_EMAIL_ROOT = "EmailMessage";
	private static final String DRP_EMAIL_ROOT = "EmailMessageDRP";

	private static final String NONDRP_UPLOAD_SUCCESS_ROOT = "UploadSuccess";
	private static final String DRP_UPLOAD_SUCCESS_ROOT = "UploadSuccessDRP";

	private static final String NONDRP_UPLOAD_FAIL_ROOT = "UploadFail";
	private static final String DRP_UPLOAD_FAIL_ROOT = "UploadFailDRP";

	private static final String INSURED = "Insured";
	private static final String CLAIMANT = "Claimant";

	private static CustomSettingHelperImpl customSettingHelper;
	private static SystemConfigProxyImpl systemConfigProxyImpl;

	private AssignmentEmailDeliveryUtils() {
		customSettingHelper = new CustomSettingHelperImpl();
		systemConfigProxyImpl = new SystemConfigProxyImpl();
	}

	private static AssignmentEmailDeliveryUtils instance = new AssignmentEmailDeliveryUtils();

	public static AssignmentEmailDeliveryUtils getInstance() {
		return instance;
	}

	private static StdAsgProcessingUtil procUtil = new StdAsgProcessUtilImpl();

	public static StdAsgProcessingUtil getProcUtilInstance() {
		return procUtil;
	}

	public AdditionalTaskConstraintsDocument getAdditionalTaskConstraintDocFromAPDAssignmentContext(
			final APDDeliveryContextDocument apdContextDoc) throws Exception {

		final MitchellEnvelopeType mitchellEnvelope = apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAssignmentMitchellEnvelope().getMitchellEnvelope();

		final MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper
				.newInstance();
		mitchellEnvHelper.getDoc().setMitchellEnvelope(mitchellEnvelope);

		final EnvelopeBodyType envelopeBody = mitchellEnvHelper
				.getEnvelopeBody(AssignmentDeliveryConstants.ME_ADDITIONAL_TASK_CONST_ID);

		AdditionalTaskConstraintsDocument additionalTaskConstraintDoc = null;

		if (envelopeBody != null) {
			final EnvelopeBodyMetadataType metadata = envelopeBody
					.getMetadata();

			final String xmlBeanClassname = metadata.getXmlBeanClassname();

			final String contentString = mitchellEnvHelper
					.getEnvelopeBodyContentAsString(envelopeBody);

			if (xmlBeanClassname == null
					|| xmlBeanClassname
							.equals(AdditionalTaskConstraintsDocument.class
									.getName())) {

				additionalTaskConstraintDoc = AdditionalTaskConstraintsDocument.Factory
						.parse(contentString);
			}
		}

		return additionalTaskConstraintDoc;
	}

	public CIECADocument getCIECADocumentFromAPDAssignmentContext(
			final APDDeliveryContextDocument apdContextDoc) throws Exception {

		final MitchellEnvelopeType mitchellEnvelope = apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAssignmentMitchellEnvelope().getMitchellEnvelope();

		final MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper
				.newInstance();
		mitchellEnvHelper.getDoc().setMitchellEnvelope(mitchellEnvelope);

		final EnvelopeBodyType envelopeBody = mitchellEnvHelper
				.getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER);

		final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
		final String xmlBeanClassname = metadata.getXmlBeanClassname();

		final String contentString = mitchellEnvHelper
				.getEnvelopeBodyContentAsString(envelopeBody);

		CIECADocument ciecaDoc = null;
		if (xmlBeanClassname == null
				|| xmlBeanClassname.equals(CIECADocument.class.getName())) {

			ciecaDoc = CIECADocument.Factory.parse(contentString);
		} else {
			throw new IllegalStateException("CIECADocument does not exist.");
		}

		return ciecaDoc;
	}

	public AdditionalAppraisalAssignmentInfoDocument getAdditionalAssignmentInfoDocFromAPDAssignmentContext(
			final APDDeliveryContextDocument apdContextDoc) throws Exception {

		final MitchellEnvelopeType mitchellEnvelope = apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAssignmentMitchellEnvelope().getMitchellEnvelope();

		final MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper
				.newInstance();
		mitchellEnvHelper.getDoc().setMitchellEnvelope(mitchellEnvelope);

		final EnvelopeBodyType envelopeBody = mitchellEnvHelper
				.getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER);

		AdditionalAppraisalAssignmentInfoDocument additionalAASInfoDoc = null;

		if (envelopeBody != null) {
			final EnvelopeBodyMetadataType metadata = envelopeBody
					.getMetadata();

			final String xmlBeanClassname = metadata.getXmlBeanClassname();

			final String contentString = mitchellEnvHelper
					.getEnvelopeBodyContentAsString(envelopeBody);

			if (xmlBeanClassname == null
					|| xmlBeanClassname
							.equals(AdditionalAppraisalAssignmentInfoDocument.class
									.getName())) {

				additionalAASInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
						.parse(contentString);
			}
		}

		return additionalAASInfoDoc;
	}

	public String getCompanyName(final APDUserInfoType apdUserInfoType) {

		// Get the company name from User Info
		String companyName = null;
		final UserHierType hierarchy = apdUserInfoType.getUserInfo()
				.getUserHier();
		if (UserInfoClient.ORG_TYPE_COMPANY.equalsIgnoreCase(hierarchy
				.getHierNode().getLevel())) {
			companyName = hierarchy.getHierNode().getName();
		}

		// set to NA if not available
		if (companyName == null || companyName.trim().length() <= 1) {
			companyName = "N/A";
		}

		return companyName;
	}

	public String getShopName(final APDUserInfoType apdUserInfoType) {
		final StringBuffer strBuf = new StringBuffer();

		if (apdUserInfoType.getUserInfo().getFirstName() != null) {
			strBuf.append(apdUserInfoType.getUserInfo().getFirstName());
		}

		if (apdUserInfoType.getUserInfo().getLastName() != null) {
			if (strBuf.length() > 0) {
				strBuf.append(" ");
			}
			strBuf.append(apdUserInfoType.getUserInfo().getLastName());
		}

		if (strBuf.length() <= 0) {
			strBuf.append("N/A");
		}

		return strBuf.toString();
	}

	public String createMessageXml4Fax(
			final APDDeliveryContextDocument apdContextDoc,
			final String emailLink, String culture) throws Exception {
		final StringBuffer buf = new StringBuffer("<Fax>");

		buf.append(createMessageXml4EmailCreation(apdContextDoc, emailLink,
				culture, false));
		buf.append("</Fax>");

		return buf.toString();
	}

	public String createMessageXml4FaxDRP(
			final APDDeliveryContextDocument apdContextDoc,
			final String emailLink, String culture) throws Exception {
		final StringBuffer buf = new StringBuffer("<FaxDRP>");

		buf.append(createMessageXml4EmailDRP(apdContextDoc, emailLink, culture,
				false));

		buf.append("</FaxDRP>");

		return buf.toString();
	}

	public String createMessageXml4EmailCreation(
			final APDDeliveryContextDocument apdContextDoc,
			final String emailLink, String culture, boolean isEmailNotify)
			throws Exception {
		return createMessageXml(apdContextDoc, emailLink, NONDRP_EMAIL_ROOT,
				culture, isEmailNotify);
	}

	public String createMessageXml4EmailDRP(
			final APDDeliveryContextDocument apdContextDoc,
			final String emailLink, String culture, boolean isEmailNotify)
			throws Exception {
		return createMessageXml(apdContextDoc, emailLink, DRP_EMAIL_ROOT,
				culture, isEmailNotify);
	}

	/**
	 * This method creates primary email body data of original assignment for
	 * DRP and non DRP both.
	 * 
	 * @param apdContextDoc
	 *            -contains all input data for email
	 * @param emailLink
	 *            -link for shops to navigate to write estimate page
	 * @param rootElement
	 * @param culture
	 * @param isEmailNotify
	 * @return email body data String
	 * @throws Exception
	 */
	public String createMessageXml(
			final APDDeliveryContextDocument apdContextDoc,
			final String emailLink, final String rootElement, String culture,
			boolean isEmailNotify) throws Exception {
		final String methodName = "createMessageXml";
		LOGGER.entering(CLZ_NAME, methodName);
		customSettingHelper.initialize();
		APDAppraisalAssignmentInfoType apdAppraisalAsgInfo = apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo();
		final String coCd = apdAppraisalAsgInfo.getAPDCommonInfo()
				.getInsCoCode();

		final String taskId = String.valueOf(apdAppraisalAsgInfo.getTaskId());

		final String companyName = getCompanyName(apdAppraisalAsgInfo
				.getAPDCommonInfo().getTargetUserInfo());

		final String shopName = getShopName(apdAppraisalAsgInfo
				.getAPDCommonInfo().getTargetUserInfo());
		UserInfoType userInfo = null;
		if (apdAppraisalAsgInfo.getAPDCommonInfo().getTargetUserInfo() != null) {
			userInfo = apdAppraisalAsgInfo.getAPDCommonInfo()
					.getTargetUserInfo().getUserInfo();
		}

		final CIECADocument ciecaDoc = getCIECADocumentFromAPDAssignmentContext(apdContextDoc);

		final String assignmentsubTypeDesc = getAssignmentSubTypeDescription(apdContextDoc);
		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("Assignment Sub Type Description in email body : "
					+ assignmentsubTypeDesc);
		}
		AssignmentAddRq asgAddReq = ciecaDoc.getCIECA().getAssignmentAddRq();
		/*
		 * To get assignment sub type desc for email template:- We may get asg
		 * sub type DESC itself in apdContextDoc instead of asg sub type CODE;
		 * In that case assignmentsubTypeDesc (above) will be returned as null
		 * So fetch DocumentSubType deom docInfo directly for this case.
		 */
		String docSubType = null;
		DocumentInfoType docInfo = asgAddReq.getDocumentInfo();
		if (docInfo != null) {
			docSubType = docInfo.getDocumentSubType();
		}

		String damageMemo = null;
		String causeOfLossDesc = null;
		String poi = null;
		String englishCauseOfLoss = null;
		String claimNum = null;
		String suffix = null;
		String claim = null;

		if (asgAddReq.getClaimInfo() != null) {
			claimNum = asgAddReq.getClaimInfo().getClaimNum();
			// To split claim number with claim mask setting
			// to get claimID and suffix(exposureId) distinctly
			String[] claimSuffix = splitClaimNumber(coCd, userInfo, claimNum);
			claim = claimSuffix[0];
			suffix = claimSuffix[1];

			if (asgAddReq.getClaimInfo().getLossInfo() != null) {

				if (asgAddReq.getClaimInfo().getLossInfo().getFacts() != null) {

					if (asgAddReq.getClaimInfo().getLossInfo().getFacts()
							.getDamageMemo() != null) {

						damageMemo = asgAddReq.getClaimInfo().getLossInfo()
								.getFacts().getDamageMemo();
					}

					if (asgAddReq.getClaimInfo().getLossInfo().getFacts()
							.getCauseOfLoss() != null) {
						final String causeOfLossCode = ciecaDoc.getCIECA()
								.getAssignmentAddRq().getClaimInfo()
								.getLossInfo().getFacts().getCauseOfLoss();

						causeOfLossDesc = AssignmentEmailDeliveryConstants
								.getCauseOfLossDescFromCode(causeOfLossCode);

					}

					if (asgAddReq.getClaimInfo().getLossInfo().getFacts()
							.getPrimaryPOI() != null) {

						if (asgAddReq.getClaimInfo().getLossInfo().getFacts()
								.getPrimaryPOI().getPOIDesc() != null) {

							poi = asgAddReq.getClaimInfo().getLossInfo()
									.getFacts().getPrimaryPOI().getPOIDesc();

						} else if (asgAddReq.getClaimInfo().getLossInfo()
								.getFacts().getPrimaryPOI().getPOICode() != null) {

							poi = AssignmentEmailDeliveryConstants
									.getPoiDesc(ciecaDoc.getCIECA()
											.getAssignmentAddRq()
											.getClaimInfo().getLossInfo()
											.getFacts().getPrimaryPOI()
											.getPOICode());
						}
					}
				}
			}
			if (causeOfLossDesc == null) {
				if (asgAddReq.getClaimInfo().getPolicyInfo() != null
						&& asgAddReq.getClaimInfo().getPolicyInfo()
								.getCoverageInfo() != null
						&& asgAddReq.getClaimInfo().getPolicyInfo()
								.getCoverageInfo().getCoverageArray() != null
						&& asgAddReq.getClaimInfo().getPolicyInfo()
								.getCoverageInfo().getCoverageArray().length > 0) {

					if (asgAddReq.getClaimInfo().getPolicyInfo()
							.getCoverageInfo().getCoverageArray(0) != null) {
						final String covCategory = ciecaDoc.getCIECA()
								.getAssignmentAddRq().getClaimInfo()
								.getPolicyInfo().getCoverageInfo()
								.getCoverageArray(0).getCoverageCategory();

						causeOfLossDesc = AssignmentEmailDeliveryConstants
								.getLossCategory(covCategory);

					}
				}
			}
		}

		if (causeOfLossDesc != null) {
			englishCauseOfLoss = causeOfLossDesc;
			causeOfLossDesc = causeOfLossDesc.replaceAll("\\s+", "_").trim();
			causeOfLossDesc = causeOfLossDesc.replaceAll("/", "_");
			causeOfLossDesc = causeOfLossDesc.replaceAll("-", "_");
		}

		String[] primaryContact = new String[4];
		String claimantName = null;
		String homePhone = null;
		String workPhone = null;
		String cellPhone = null;
		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("APD Delivery Context Document : "
					+ apdContextDoc.toString());
		}
		final AdditionalAppraisalAssignmentInfoDocument additionalAppAsgInfoDoc = getAdditionalAssignmentInfoDocFromAPDAssignmentContext(apdContextDoc);
		if (additionalAppAsgInfoDoc != null
				&& additionalAppAsgInfoDoc
						.getAdditionalAppraisalAssignmentInfo() != null) {
			if (additionalAppAsgInfoDoc.getAdditionalAppraisalAssignmentInfo()
					.getAssignmentDetails() != null
					&& additionalAppAsgInfoDoc
							.getAdditionalAppraisalAssignmentInfo()
							.getAssignmentDetails().getPrimaryContactType() != null) {

				final String primaryContactType = additionalAppAsgInfoDoc
						.getAdditionalAppraisalAssignmentInfo()
						.getAssignmentDetails().getPrimaryContactType();

				if (CLAIMANT.equalsIgnoreCase(primaryContactType)) {
					LOGGER.fine("Primary Contact is Claimant");
					primaryContact = getContactFromClaimant(ciecaDoc);
					if (primaryContact != null && primaryContact[0] != null) {
						claimantName = replaceChar(primaryContact[0], "^", " ");
						homePhone = primaryContact[1];
						workPhone = primaryContact[2];
						cellPhone = primaryContact[3];
					}
				} else if (INSURED.equalsIgnoreCase(primaryContactType)) {
					LOGGER.fine("Primary Contact is Policy Holder");
					primaryContact = getContactFromPolicyHolder(ciecaDoc);
					if (primaryContact != null && primaryContact[0] != null) {
						claimantName = replaceChar(primaryContact[0], "^", " ");
						homePhone = primaryContact[1];
						workPhone = primaryContact[2];
						cellPhone = primaryContact[3];
					}
				}
			}
		}

		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("Primary Contact Name : " + claimantName);
			LOGGER.fine("Primary Contact HomePhone : " + homePhone);
			LOGGER.fine("Primary Contact WorkPhone : " + workPhone);
			LOGGER.fine("Primary Contact CellPhone : " + cellPhone);
		}

		String vehMake = null;
		String vehModel = null;
		String vehYear = null;
		String assignmentMemo = null;
		String licensePlateNum = null;
		if (asgAddReq.getVehicleDamageAssignment() != null) {

			if (asgAddReq.getVehicleDamageAssignment().getAssignmentMemoArray() != null
					&& asgAddReq.getVehicleDamageAssignment()
							.getAssignmentMemoArray().length > 0) {
				assignmentMemo = asgAddReq.getVehicleDamageAssignment()
						.getAssignmentMemoArray(0);
			}

			if (asgAddReq.getVehicleDamageAssignment().getVehicleInfo() != null) {

				if (asgAddReq.getVehicleDamageAssignment().getVehicleInfo()
						.getVehicleDesc() != null) {

					final java.util.Calendar cal = ciecaDoc.getCIECA()
							.getAssignmentAddRq().getVehicleDamageAssignment()
							.getVehicleInfo().getVehicleDesc().getModelYear();
					if (cal != null) {
						vehYear = String.valueOf(cal
								.get(java.util.Calendar.YEAR));
					}

					vehMake = asgAddReq.getVehicleDamageAssignment()
							.getVehicleInfo().getVehicleDesc().getMakeDesc();

					vehModel = asgAddReq.getVehicleDamageAssignment()
							.getVehicleInfo().getVehicleDesc().getModelName();
				}
			}

			licensePlateNum = getLicensePlate(asgAddReq);

		}

		String preferYear = null;
		String preferMonth = null;
		String preferDay = null;
		final AdditionalTaskConstraintsDocument taskConstraintDoc = getAdditionalTaskConstraintDocFromAPDAssignmentContext(apdContextDoc);

		if (taskConstraintDoc != null
				&& taskConstraintDoc.getAdditionalTaskConstraints() != null
				&& taskConstraintDoc.getAdditionalTaskConstraints()
						.getScheduleConstraints() != null) {

			java.util.Calendar cal = taskConstraintDoc
					.getAdditionalTaskConstraints().getScheduleConstraints()
					.getPreferredScheduleDate();

			if (cal != null) {

				if (cal.toString().equals(
						AssignmentDeliveryConstants.DATE_PREFER)) {
					preferYear = "";
					preferMonth = "";
					preferDay = "";
				} else {
					preferYear = String.valueOf(cal
							.get(java.util.Calendar.YEAR));
					preferMonth = String.valueOf(cal
							.get(java.util.Calendar.MONTH) + 1);
					preferDay = String.valueOf(cal
							.get(java.util.Calendar.DAY_OF_MONTH));
				}
			}
		}
		final String claimNumber = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getClientClaimNumber();

		AssignmentDeliveryEmailReqDocument doc = null;

		doc = AssignmentDeliveryEmailReqDocument.Factory.newInstance();

		AssignmentDeliveryEmailReqType asgDelEmailReqType = doc
				.addNewAssignmentDeliveryEmailReq();

		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("rootElement:" + rootElement);
		}

		// To get the custom setting for Suffix Label
		String suffixLabel = getCustomSettingValue(coCd, userInfo,
				AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);

		// To get token values from SET file for header/footer/signature image
		// urls
		String staticImageBaseUrl = systemConfigProxyImpl
				.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
		// get current year
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String currentYear = String.valueOf(year);

		ResourceBundle rs = getResourceBundleForCulture(culture, isEmailNotify);
		if (NONDRP_EMAIL_ROOT.equals(rootElement)) {
			EmailMessageType emailMsgType = null;
			emailMsgType = asgDelEmailReqType.addNewEmailMessage();
			emailMsgType.setURLLink(emailLink);
			emailMsgType.setCoCd(coCd);
			emailMsgType.setCoName(XMLUtilities.encode(companyName));
			emailMsgType.setTaskId(taskId);
			emailMsgType.setClaimId(claim);
			emailMsgType.setStaticImageBaseUrl(staticImageBaseUrl);
			emailMsgType.setCurrentYear(currentYear);
			if (suffixLabel != null) {
				emailMsgType.setSuffixLabel(suffixLabel);
			}
			emailMsgType.setSuffix(suffix);
			if (assignmentsubTypeDesc != null) {
				emailMsgType.setAssignmentSubTypeCode(assignmentsubTypeDesc);
			}
			emailMsgType
					.setAssignmentSubType(getDocumentSubType(docSubType, rs));
			if (damageMemo != null) {
				emailMsgType.setDamageMemo(damageMemo);
				if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
					LOGGER.info("DamageMemo is EmailMsgType :  " + damageMemo);
					LOGGER.info("DamageMemo is EmailMsgType :  "
							+ emailMsgType.getDamageMemo());
				}
			}
			if (assignmentMemo != null) {
				emailMsgType.setAssignmentMemo(assignmentMemo);
				if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
					LOGGER.info("AssignmentMemo is EmailMsgType :  "
							+ assignmentMemo);
					LOGGER.info("AssignmentMemo is EmailMsgType :  "
							+ emailMsgType.getAssignmentMemo());
				}
			}
			if (causeOfLossDesc != null) {
				if (rs.containsKey(AssignmentDeliveryConstants.LOSS_TYPE
						+ causeOfLossDesc)) {
					String i18NLossType = null;
					i18NLossType = rs
							.getString(AssignmentDeliveryConstants.LOSS_TYPE
									+ causeOfLossDesc);
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("emailMsgType-i18NLossType:" + i18NLossType);
					}
					emailMsgType.setCauseOfLossDesc(i18NLossType);
				} else {
					emailMsgType.setCauseOfLossDesc(englishCauseOfLoss);
				}
			}
			if (claimantName != null && claimantName.trim() != "") {
				emailMsgType.setClaimantName(claimantName);
				if (homePhone != null && homePhone.trim() != "") {
					emailMsgType.setHomePhone(homePhone);
				}
				if (workPhone != null && workPhone.trim() != "") {
					emailMsgType.setWorkPhone(workPhone);
				}
				if (cellPhone != null && cellPhone.trim() != "") {
					emailMsgType.setCellPhone(cellPhone);
				}
			}

			if (vehMake != null) {
				emailMsgType.setVehMake(vehMake);
			}
			if (vehModel != null) {
				emailMsgType.setVehModel(XMLUtilities.encode(vehModel));
			}
			if (vehYear != null) {
				emailMsgType.setVehYear(vehYear);
			}
			if (licensePlateNum != null) {
				emailMsgType.setLicensePlate(licensePlateNum);
			}
			if (poi != null) {
				if (rs.containsKey(AssignmentDeliveryConstants.POI
						+ replaceChar(poi, " ", "_"))) {
					String i18Npoi = null;
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("poi:" + poi);
					}
					poi = replaceChar(poi, " ", "_");
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("poi:" + poi);
						LOGGER.info("Poi Key "
								+ AssignmentDeliveryConstants.POI + poi);
					}
					i18Npoi = rs.getString(AssignmentDeliveryConstants.POI
							+ poi);
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("i18Npoi:" + i18Npoi);
					}
					emailMsgType.setPoi(i18Npoi);
				} else {
					emailMsgType.setPoi(poi);
				}

			}
			if (preferYear != null) {
				emailMsgType.setPreferYear(preferYear);
				emailMsgType.setPreferMonth(preferMonth);
				emailMsgType.setPreferDay(preferDay);
			}
			emailMsgType.setClaimNumber(claimNumber);
			emailMsgType.setShopName(XMLUtilities.encode(shopName));

			if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
				LOGGER.fine("Email or Fax Message XML Constructed for XSLT processing : "
						+ emailMsgType.toString());
			}

		} else if (DRP_EMAIL_ROOT.equals(rootElement)) {
			EmailMessageDRPType emailMsgDRPType = null;
			emailMsgDRPType = asgDelEmailReqType.addNewEmailMessageDRP();
			emailMsgDRPType.setURLLink(emailLink);
			emailMsgDRPType.setCoCd(coCd);
			emailMsgDRPType.setCoName(XMLUtilities.encode(companyName));
			emailMsgDRPType.setTaskId(taskId);
			emailMsgDRPType.setClaimId(claim);
			emailMsgDRPType.setStaticImageBaseUrl(staticImageBaseUrl);
			emailMsgDRPType.setCurrentYear(currentYear);
			if (suffixLabel != null) {
				emailMsgDRPType.setSuffixLabel(suffixLabel);
			}
			emailMsgDRPType.setSuffix(suffix);
			if (assignmentsubTypeDesc != null) {
				emailMsgDRPType.setAssignmentSubTypeCode(assignmentsubTypeDesc);
			}
			emailMsgDRPType.setAssignmentSubType(getDocumentSubType(docSubType,
					rs));

			if (damageMemo != null) {
				emailMsgDRPType.setDamageMemo(damageMemo);
				if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
					LOGGER.info("DamageMemo is EmailMsgDrpType :  "
							+ damageMemo);
					LOGGER.info("DamageMemo is EmailMsgDrpType :  "
							+ emailMsgDRPType.getDamageMemo());
				}
			}
			if (assignmentMemo != null) {
				emailMsgDRPType.setAssignmentMemo(assignmentMemo);
				if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
					LOGGER.info("AssignmentMemo is EmailMsgDrpType :  "
							+ assignmentMemo);
					LOGGER.info("AssignmentMemo is EmailMsgDrpType :  "
							+ emailMsgDRPType.getAssignmentMemo());
				}
			}
			if (causeOfLossDesc != null) {
				if (rs.containsKey(AssignmentDeliveryConstants.LOSS_TYPE
						+ causeOfLossDesc)) {
					String i18NLossType = null;
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("causeOfLossDesc:2-" + causeOfLossDesc);
					}
					i18NLossType = rs
							.getString(AssignmentDeliveryConstants.LOSS_TYPE
									+ causeOfLossDesc);
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("i18NLossType:" + i18NLossType);
					}
					emailMsgDRPType.setCauseOfLossDesc(i18NLossType);
				} else {
					emailMsgDRPType.setCauseOfLossDesc(englishCauseOfLoss);
				}

			}
			if (claimantName != null && claimantName.trim() != "") {
				emailMsgDRPType.setClaimantName(claimantName);
				if (homePhone != null && homePhone.trim() != "") {
					emailMsgDRPType.setHomePhone(homePhone);
				}
				if (workPhone != null && workPhone.trim() != "") {
					emailMsgDRPType.setWorkPhone(workPhone);
				}
				if (cellPhone != null && cellPhone.trim() != "") {
					emailMsgDRPType.setCellPhone(cellPhone);
				}
			}

			if (vehMake != null) {
				emailMsgDRPType.setVehMake(vehMake);
			}
			if (vehModel != null) {
				emailMsgDRPType.setVehModel(XMLUtilities.encode(vehModel));
			}
			if (vehYear != null) {
				emailMsgDRPType.setVehYear(vehYear);
			}
			if (licensePlateNum != null) {
				emailMsgDRPType.setLicensePlate(licensePlateNum);
			}
			if (poi != null) {
				if (rs.containsKey(AssignmentDeliveryConstants.POI
						+ replaceChar(poi, " ", "_"))) {
					String i18Npoi = null;
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("poi:" + poi);
					}
					poi = replaceChar(poi, " ", "_");
					i18Npoi = rs.getString(AssignmentDeliveryConstants.POI
							+ poi);
					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("i18Npoi:" + i18Npoi);
					}
					emailMsgDRPType.setPoi(i18Npoi);
				} else {
					emailMsgDRPType.setPoi(poi);
				}
			}
			if (preferYear != null) {
				emailMsgDRPType.setPreferYear(preferYear);
				emailMsgDRPType.setPreferMonth(preferMonth);
				emailMsgDRPType.setPreferDay(preferDay);
			}
			emailMsgDRPType.setClaimNumber(claimNumber);
			emailMsgDRPType.setShopName(XMLUtilities.encode(shopName));

			if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
				LOGGER.fine("Email or Fax Message XML Constructed for XSLT processing : "
						+ emailMsgDRPType.toString());
			}

		}

		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("asgDelEmailReqType.toString()::"
					+ asgDelEmailReqType.toString());
		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return asgDelEmailReqType.xmlText();
	}

	/**
	 * This method is to get custom setting value at user level first if null
	 * found get setting value at company level
	 * 
	 * @param coCd
	 *            to get user/comp level custom setting
	 * @param userInfo
	 *            to get user level custom setting
	 * @param groupName
	 *            to get user/comp level custom setting
	 * @param settingName
	 *            to get user/comp level custom setting
	 * @throws MitchellException
	 * @return custom setting value
	 * @throws ClaimException
	 */
	private String getCustomSettingValue(final String coCd,
			UserInfoType userInfo, String groupName, String settingName)
			throws MitchellException {
		final String methodName = "getCustomSettingValue";
		LOGGER.entering(CLZ_NAME, methodName);
		String value = null;
		if (customSettingHelper != null) {
			if (userInfo != null && userInfo.getUserID() != null) {
				value = customSettingHelper.getUserCustomSetting(
						userInfo.getUserID(), coCd, groupName, settingName);

			}
			if (value == null) {
				value = customSettingHelper.getCompanyCustomSetting(coCd,
						groupName, settingName);
			}
		}

		LOGGER.exiting(CLZ_NAME, methodName);
		return value;
	}

	/**
	 * This method prepares key for resource bundle by replacing blank spaces
	 * with underscore and gets the value with this key from rs then populates
	 * asg sub type
	 * 
	 * @param asgSubType
	 *            -Document sub type
	 * @param rs
	 *            -property file which contains key-value pairs for asg sub
	 *            types
	 * @param emailMsgType
	 */
	private String getDocumentSubType(String docSubType, ResourceBundle rs)
			throws MitchellException {
		final String methodName = "getDocumentSubType";
		LOGGER.entering(CLZ_NAME, methodName);
		String docSubTypeValue = null;
		if (docSubType != null) {
			String subType = replaceChar(docSubType,
					AssignmentDeliveryConstants.BLANK_SPACE,
					AssignmentDeliveryConstants.UNDERSCORE);
			String keyAsgSubType = AssignmentDeliveryConstants.ASG_SUBTYPE
					+ subType;
			if (rs.containsKey(keyAsgSubType)) {
				docSubTypeValue = rs.getString(keyAsgSubType);
			}

		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return docSubTypeValue;
	}

	/**
	 * This method is to populate license plate num fetched from cieca doc
	 * 
	 * @param asgAddReq
	 * @return license plate number
	 */
	private String getLicensePlate(AssignmentAddRq asgAddReq)
			throws MitchellException {
		final String methodName = "getLicensePlate";
		LOGGER.entering(CLZ_NAME, methodName);
		String licensePlate = null;
		if (asgAddReq.getVehicleDamageAssignment().getVehicleInfo()
				.isSetLicense()) {
			LicenseType licenseType = asgAddReq.getVehicleDamageAssignment()
					.getVehicleInfo().getLicense();
			if (!licenseType.isNil()) {
				licensePlate = licenseType.getLicensePlateNum();
			}

		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return licensePlate;
	}

	/**
	 * This method is to split claim num with claim mast setting and to get
	 * claim id and exposure id separately
	 * 
	 * @param coCd
	 * @param claimNum
	 *            to be splitted
	 * @throws MitchellException
	 * @throws ClaimException
	 */
	private String[] splitClaimNumber(final String coCd, UserInfoType userInfo,
			String claimNum) throws MitchellException {
		final String methodName = "splitClaimNumber";
		LOGGER.entering(CLZ_NAME, methodName);
		String[] claimSuffix = new String[2];
		String claimParsingRule = getCustomSettingValue(coCd, userInfo,
				AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);

		if (claimParsingRule != null && null != claimNum) {
			claimSuffix = ParseHelper.parseClaimNo(claimNum, claimParsingRule);
		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return claimSuffix;
	}

	String[] getContactFromClaimant(final CIECADocument ciecaDoc) {

		String[] contactDetails = new String[4];
		String methodName = "getContactFromClaimant";
		LOGGER.entering(CLZ_NAME, methodName);
		AssignmentAddRq asgAddReq = ciecaDoc.getCIECA().getAssignmentAddRq();
		if (asgAddReq.getAdminInfo() != null) {
			if (asgAddReq.getAdminInfo().getPolicyHolder() != null
					&& asgAddReq.getAdminInfo().getClaimant() != null
					&& asgAddReq.getAdminInfo().getClaimant().getParty() != null
					&& asgAddReq.getAdminInfo().getClaimant().getParty()
							.getPersonInfo() != null
					&& asgAddReq.getAdminInfo().getClaimant().getParty()
							.getPersonInfo().getPersonName() != null) {

				final String firstName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getClaimant()
						.getParty().getPersonInfo().getPersonName()
						.getFirstName();
				final String lastName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getClaimant()
						.getParty().getPersonInfo().getPersonName()
						.getLastName();

				final String secondLastName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getClaimant()
						.getParty().getPersonInfo().getPersonName()
						.getSecondLastName();

				createCompleteName(contactDetails, firstName, lastName,
						secondLastName);

				if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
					LOGGER.fine("claiment Name---->" + contactDetails[0]);
				}

				if (asgAddReq.getAdminInfo().getClaimant().getParty()
						.getContactInfoArray() != null
						&& asgAddReq.getAdminInfo().getClaimant().getParty()
								.getContactInfoArray().length > 0) {

					CommunicationsType[] commArray = ciecaDoc.getCIECA()
							.getAssignmentAddRq().getAdminInfo().getClaimant()
							.getParty().getContactInfoArray(0)
							.getCommunicationsArray();

					if (commArray != null && commArray.length > 0) {
						for (int i = 0; i < commArray.length; i++) {
							if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("HP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[1] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("WP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[2] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("CP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[3] = commArray[i].getCommPhone();
							}
						}
					}
				}

				else if (asgAddReq.getAdminInfo().getClaimant().getParty()
						.getPersonInfo().getCommunicationsArray() != null
						&& asgAddReq.getAdminInfo().getClaimant().getParty()
								.getPersonInfo().getCommunicationsArray().length > 0) {

					CommunicationsType[] commArray = ciecaDoc.getCIECA()
							.getAssignmentAddRq().getAdminInfo().getClaimant()
							.getParty().getPersonInfo()
							.getCommunicationsArray();

					if (commArray != null && commArray.length > 0) {
						for (int i = 0; i < commArray.length; i++) {
							if (commArray[i].getCommQualifier() != null
									&& "HP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[1] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& "WP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[2] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& "CP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[3] = commArray[i].getCommPhone();
							}
						}
					}
				}

			}
		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return contactDetails;
	}

	private void createCompleteName(String[] contactDetails,
			final String firstName, final String lastName,
			final String secondLastName) {
		if (firstName != null && !firstName.isEmpty()) {
			contactDetails[0] = firstName;
		}

		if (lastName != null && !lastName.isEmpty()) {
			if (contactDetails[0] != null && !contactDetails[0].isEmpty()) {
				contactDetails[0] += " " + lastName;
			} else {
				contactDetails[0] = lastName;
			}
		}

		if (secondLastName != null && !secondLastName.isEmpty()) {
			if (contactDetails[0] != null && !contactDetails[0].isEmpty()) {
				contactDetails[0] += " " + secondLastName;
			} else {
				contactDetails[0] = secondLastName;
			}
		}
	}

	private String replaceChar(String name, String oldChar, String newChar) {
		if (name.contains(oldChar)) {
			name = name.replace(oldChar, newChar);
		}
		return name;
	}

	String[] getContactFromPolicyHolder(final CIECADocument ciecaDoc) {
		String[] contactDetails = new String[4];
		AssignmentAddRq asgAddReq = ciecaDoc.getCIECA().getAssignmentAddRq();
		if (asgAddReq.getAdminInfo() != null) {
			if (asgAddReq.getAdminInfo().getPolicyHolder() != null
					&& asgAddReq.getAdminInfo().getPolicyHolder().getParty() != null
					&& asgAddReq.getAdminInfo().getPolicyHolder().getParty()
							.getPersonInfo() != null
					&& asgAddReq.getAdminInfo().getPolicyHolder().getParty()
							.getPersonInfo().getPersonName() != null) {

				final String firstName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getPolicyHolder()
						.getParty().getPersonInfo().getPersonName()
						.getFirstName();
				final String lastName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getPolicyHolder()
						.getParty().getPersonInfo().getPersonName()
						.getLastName();
				final String secondLastName = ciecaDoc.getCIECA()
						.getAssignmentAddRq().getAdminInfo().getPolicyHolder()
						.getParty().getPersonInfo().getPersonName()
						.getSecondLastName();

				createCompleteName(contactDetails, firstName, lastName,
						secondLastName);
				if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
					LOGGER.fine("Insured Name---->" + contactDetails[0]);
				}

				if (asgAddReq.getAdminInfo().getPolicyHolder().getParty()
						.getContactInfoArray() != null
						&& asgAddReq.getAdminInfo().getPolicyHolder()
								.getParty().getContactInfoArray().length > 0) {

					CommunicationsType[] commArray = ciecaDoc.getCIECA()
							.getAssignmentAddRq().getAdminInfo()
							.getPolicyHolder().getParty()
							.getContactInfoArray(0).getCommunicationsArray();

					if (commArray != null && commArray.length > 0) {
						for (int i = 0; i < commArray.length; i++) {
							if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("HP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[1] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("WP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[2] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& commArray[i].getCommQualifier()
											.equalsIgnoreCase("CP")
									&& commArray[i].getCommPhone() != null) {
								contactDetails[3] = commArray[i].getCommPhone();
							}
						}
					}
				}

				else if (asgAddReq.getAdminInfo().getPolicyHolder().getParty()
						.getPersonInfo().getCommunicationsArray() != null
						&& asgAddReq.getAdminInfo().getPolicyHolder()
								.getParty().getPersonInfo()
								.getCommunicationsArray().length > 0) {

					CommunicationsType[] commArray = ciecaDoc.getCIECA()
							.getAssignmentAddRq().getAdminInfo()
							.getPolicyHolder().getParty().getPersonInfo()
							.getCommunicationsArray();

					if (commArray != null && commArray.length > 0) {
						for (int i = 0; i < commArray.length; i++) {
							if (commArray[i].getCommQualifier() != null
									&& "HP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[1] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& "WP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[2] = commArray[i].getCommPhone();
							} else if (commArray[i].getCommQualifier() != null
									&& "CP".equalsIgnoreCase(commArray[i]
											.getCommQualifier())
									&& commArray[i].getCommPhone() != null) {
								contactDetails[3] = commArray[i].getCommPhone();
							}
						}
					}
				}

			}
		}
		return contactDetails;
	}

	public String retrieveSuppAsgEmailMessageBody(
			final MitchellEnvelopeDocument mitEnvDoc) throws MitchellException,
			XmlException {
		final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
				mitEnvDoc);

		EmailMessageDocument supplementRequestDoc = null;
		String contentString = null;

		final EnvelopeBodyType envelopeBody = meHelper
				.getEnvelopeBody(SUPP_EMAIL_NAME);
		final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
		final String xmlBeanClassname = metadata.getXmlBeanClassname();

		contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);

		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("AssignmentEmailDeliveryUtils#retrieveSuppAsgEmailMessageBody#contentString......."
					+ contentString);
		}

		if (xmlBeanClassname == null
				|| xmlBeanClassname
						.equals(EmailMessageDocument.class.getName())) {

			supplementRequestDoc = EmailMessageDocument.Factory
					.parse(contentString);

			if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
				LOGGER.info("AssignmentEmailDeliveryUtils#retrieveSuppAsgEmailMessageBody#supplementRequestDoc......."
						+ supplementRequestDoc);
			}

		} else {
			final String errMsg = "MitchellEnvelope does not contains EmailMessageDocument";
			throw new IllegalStateException(errMsg);
		}

		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("AssignmentEmailDeliveryUtils#retrieveSuppAsgEmailMessageBody#supplementRequestDoc.getEmailMessage().getHTMLFormat()......."
					+ supplementRequestDoc.getEmailMessage().getHTMLFormat());
		}

		final String messageBody = supplementRequestDoc.getEmailMessage()
				.getHTMLFormat();

		return messageBody;
	}

	public AssignmentServiceContext createAssignmentServiceContext(
			final APDDeliveryContextDocument apdContextDoc,
			final ArrayList partsListAttachment) {
		final UserInfoDocument targetUserInfoDoc = UserInfoDocument.Factory
				.newInstance();
		targetUserInfoDoc.setUserInfo(apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getTargetUserInfo().getUserInfo());
		final String workItemId = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getWorkItemId();

		final MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.newInstance();
		meDoc.setMitchellEnvelope(apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo()
				.getAssignmentMitchellEnvelope().getMitchellEnvelope());

		final boolean isSetTargetDRPUserInfo = apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().isSetTargetDRPUserInfo();

		UserInfoDocument drpUserInfoDoc = null;

		if (apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.isSetTargetDRPUserInfo()) {
			drpUserInfoDoc = UserInfoDocument.Factory.newInstance();
			drpUserInfoDoc.setUserInfo(apdContextDoc.getAPDDeliveryContext()
					.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
					.getTargetDRPUserInfo().getUserInfo());
		}

		final AssignmentServiceContext assignmentServiceContext = new AssignmentServiceContext(
				targetUserInfoDoc, workItemId, meDoc, isSetTargetDRPUserInfo,
				drpUserInfoDoc, partsListAttachment);
		assignmentServiceContext.setWorkAssignmentId(String
				.valueOf(apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo().getTaskId()));
		return assignmentServiceContext;
	}

	public String createEmailMessageXml4UploadSuccess(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {
		return createEmailMessageXml4AlertSuccess(apdContextDoc,
				NONDRP_UPLOAD_SUCCESS_ROOT);
	}

	public String createEmailMessageXml4UploadSuccessDRP(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {
		return createEmailMessageXml4AlertSuccess(apdContextDoc,
				DRP_UPLOAD_SUCCESS_ROOT);
	}

	public String createEmailMessageXml4AlertSuccess(
			final APDDeliveryContextDocument apdContextDoc,
			final String messageRoot) throws MitchellException {

		final BaseAPDCommonType apdCommonInfo = apdContextDoc
				.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo();
		final String claimNumber = apdCommonInfo.getClientClaimNumber();
		final String companyName = getCompanyName(apdCommonInfo
				.getTargetUserInfo());

		// Getting the userInfo to fount the OrgCode and userId
		UserInfoType userInfoType = null;
		if (apdCommonInfo.getTargetUserInfo() != null
				&& !apdCommonInfo.getTargetUserInfo().isNil()) {
			userInfoType = apdCommonInfo.getTargetUserInfo().getUserInfo();
		}

		String coCode = null;
		if (userInfoType != null) {
			coCode = userInfoType.getOrgCode();
		}

		final StringBuilder xmlBuilder = new StringBuilder("<");
		xmlBuilder.append(messageRoot).append(">");

		// To populate company
		xmlBuilder("CoName", companyName, xmlBuilder);

		// To populate Suffix label
		String suffixLabel = getCustomSettingValue(coCode, null,
				AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);
		xmlBuilder("SuffixLabel", suffixLabel, xmlBuilder);

		// To populate claim and suffix
		String[] claimSuffix = getClaimSuffix(claimNumber, coCode, userInfoType);
		if (claimSuffix != null && claimSuffix.length > 0) {
			xmlBuilder("Claim", claimSuffix[0], xmlBuilder);
			xmlBuilder("Suffix", claimSuffix[1], xmlBuilder);
		}

		// To populate current year in email
		String currYear = String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR));
		xmlBuilder("CurrentYear", currYear, xmlBuilder);

		// To populate recipient name in email
		String recipientName = getShopName(apdCommonInfo.getTargetUserInfo());
		xmlBuilder("RecipientName", recipientName, xmlBuilder);

		// To populate static Image url
		String staticImageBaseUrl = systemConfigProxyImpl
				.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
		xmlBuilder("StaticImageBaseUrl", staticImageBaseUrl, xmlBuilder);

		if (messageRoot != null) {
			xmlBuilder.append("</").append(messageRoot).append(">");
		}

		return xmlBuilder.toString();
	}

	public String createEmailMessageXml4UploadFail(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {

		return createEmailMessageXml4AlertFail(apdContextDoc,
				NONDRP_UPLOAD_FAIL_ROOT);
	}

	public String createEmailMessageXml4UploadFailDRP(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {

		return createEmailMessageXml4AlertFail(apdContextDoc,
				DRP_UPLOAD_FAIL_ROOT);
	}

	public String createEmailMessageXml4AlertFail(
			final APDDeliveryContextDocument apdContextDoc,
			final String messageRoot) throws MitchellException {

		final BaseAPDCommonType apdCommonInfo = apdContextDoc
				.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo();
		final String claimNumber = apdCommonInfo.getClientClaimNumber();
		final String companyName = getCompanyName(apdCommonInfo
				.getTargetUserInfo());

		// Getting the userInfo to fount the OrgCode and userId
		UserInfoType userInfoType = null;
		if (apdCommonInfo.getTargetUserInfo() != null
				&& !apdCommonInfo.getTargetUserInfo().isNil()) {
			userInfoType = apdCommonInfo.getTargetUserInfo().getUserInfo();
		}

		String coCode = null;
		if (userInfoType != null) {
			coCode = userInfoType.getOrgCode();
		}

		final StringBuilder xmlBuilder = new StringBuilder("<");
		xmlBuilder.append(messageRoot).append(">");

		// To populate claim and suffix
		String[] claimSuffix = getClaimSuffix(claimNumber, coCode, userInfoType);
		if (claimSuffix != null && claimSuffix.length > 0) {
			xmlBuilder("Claim", claimSuffix[0], xmlBuilder);
			xmlBuilder("Suffix", claimSuffix[1], xmlBuilder);
		}

		// To populate Company
		xmlBuilder("CoName", companyName, xmlBuilder);
		// To populate Suffix label
		String suffixLabel = getCustomSettingValue(coCode, null,
				AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_SUFFIX_LABEL);
		xmlBuilder("SuffixLabel", suffixLabel, xmlBuilder);

		// To populate current year in email
		String currYear = String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR));
		xmlBuilder("CurrentYear", currYear, xmlBuilder);

		// To populate recipient name in email
		String recipientName = getShopName(apdCommonInfo.getTargetUserInfo());
		xmlBuilder("RecipientName", recipientName, xmlBuilder);

		// To populate static Image url
		String staticImageBaseUrl = systemConfigProxyImpl
				.getStringValue(AssignmentDeliveryConstants.STATIC_IMAGE_BASE_URL);
		xmlBuilder("StaticImageBaseUrl", staticImageBaseUrl, xmlBuilder);

		// To populate message
		final String message = apdContextDoc.getAPDDeliveryContext()
				.getAPDAlertInfo().getMessage();
		xmlBuilder("Message", message, xmlBuilder);

		xmlBuilder.append("</").append(messageRoot).append(">");
		return xmlBuilder.toString();
	}

	/*
	 * @node : nodeName
	 * 
	 * @value : value need to be populate
	 * 
	 * @xmlBuilder : it append with existing StringBuilderObject
	 */
	private void xmlBuilder(String node, String value, StringBuilder xmlBuilder) {
		if (value != null) {
			xmlBuilder.append("<" + node + ">")
					.append(XMLUtilities.encode(value))
					.append("</" + node + ">");
		}
	}

	public String createEmailSubjectXml4UploadSuccess(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {

		final String coName = getCompanyName(apdContextDoc
				.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo()
				.getTargetUserInfo());
		final String claimNumber = apdContextDoc.getAPDDeliveryContext()
				.getAPDAlertInfo().getAPDCommonInfo().getClientClaimNumber();

		final StringBuffer xmlBuilder = new StringBuffer(
				"<UploadSuccessSubject>");

		xmlBuilder.append("<CoName>").append(XMLUtilities.encode(coName))
				.append("</CoName>");
		xmlBuilder.append("<ClaimNumber>").append(claimNumber)
				.append("</ClaimNumber>");
		xmlBuilder.append("</UploadSuccessSubject>");

		return xmlBuilder.toString();
	}

	public String createEmailSubjectXml4UploadFail(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {

		final String coName = getCompanyName(apdContextDoc
				.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo()
				.getTargetUserInfo());
		final String claimNumber = apdContextDoc.getAPDDeliveryContext()
				.getAPDAlertInfo().getAPDCommonInfo().getClientClaimNumber();

		final StringBuffer xmlBuilder = new StringBuffer("<UploadFailSubject>");

		xmlBuilder.append("<CoName>").append(XMLUtilities.encode(coName))
				.append("</CoName>");
		xmlBuilder.append("<ClaimNumber>").append(claimNumber)
				.append("</ClaimNumber>");
		xmlBuilder.append("</UploadFailSubject>");

		return xmlBuilder.toString();
	}

	public String createEmailSubjectXml4Creation(
			final APDDeliveryContextDocument apdContextDoc)
			throws MitchellException {

		final String assignmentsubTypeDesc = getAssignmentSubTypeDescription(apdContextDoc);
		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("Assignment Sub Type Description in email subject : "
					+ assignmentsubTypeDesc);
		}

		final String coName = getCompanyName(apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getTargetUserInfo());

		final String claimNumber = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getClientClaimNumber();

		final StringBuffer xmlBuilder = new StringBuffer("<CreationSubject>");

		xmlBuilder.append("<CoName>").append(XMLUtilities.encode(coName))
				.append("</CoName>");
		if (assignmentsubTypeDesc != null) {
			xmlBuilder.append("<AssignmentSubTypeCode>")
					.append(XMLUtilities.encode(assignmentsubTypeDesc))
					.append("</AssignmentSubTypeCode>");
		}
		if (claimNumber != null) {
			xmlBuilder.append("<ClaimNumber>").append(claimNumber)
					.append("</ClaimNumber>");
		}
		xmlBuilder.append("</CreationSubject>");

		return xmlBuilder.toString();
	}

	public String createEmailSubjectXml4NonDrpSuppEmail(
			final APDDeliveryContextDocument apdContextDoc)
			throws MitchellException {

		final String coName = getCompanyName(apdContextDoc
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getTargetUserInfo());

		final String claimNumber = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getClientClaimNumber();

		final StringBuffer xmlBuilder = new StringBuffer("<NonDrpSuppSubject>");

		xmlBuilder.append("<CoName>").append(XMLUtilities.encode(coName))
				.append("</CoName>");
		xmlBuilder.append("<ClaimNumber>").append(claimNumber)
				.append("</ClaimNumber>");
		xmlBuilder.append("</NonDrpSuppSubject>");

		return xmlBuilder.toString();
	}

	public String getWorkItemId(final BaseAPDCommonType apdContextDoc) {
		String workItemId = apdContextDoc.getWorkItemId();

		if (workItemId == null) {
			workItemId = apdContextDoc.getClientClaimNumber();
		}
		return workItemId;
	}

	public UserInfoDocument getUserInfo(final BaseAPDCommonType apdContextDoc) {

		final UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.newInstance();

		if (apdContextDoc.getSourceUserInfo() != null) {
			userInfoDoc.setUserInfo(apdContextDoc.getSourceUserInfo()
					.getUserInfo());
		} else {
			userInfoDoc.setUserInfo(apdContextDoc.getTargetUserInfo()
					.getUserInfo());
		}
		return userInfoDoc;
	}

	public StringBuffer append(final StringBuffer strBuf, final String tagName,
			final String tagValue) {

		strBuf.append(OPEN_BRACKET).append(tagName).append(CLOSE_BRACKET)
				.append(tagValue).append(OPEN_END_BRACKET).append(tagName)
				.append(CLOSE_BRACKET);

		return strBuf;
	}

	public String getPdfPath(final String folderPath) {

		final StringBuffer buf = new StringBuffer(folderPath);
		if (!folderPath.endsWith(java.io.File.separator)) {

			buf.append(java.io.File.separator);
		}

		buf.append(new java.util.Random().nextInt(RANGE)).append("_")
				.append(UUIDFactory.getInstance().getUUID()).append("_")
				.append(System.currentTimeMillis()).append(".pdf");

		return buf.toString();
	}

	public void createPDF(final String content, final String path)
			throws Exception {

		// Create the PDF Document

		// step 1: creation of a document-object
		com.lowagie.text.Document document = new com.lowagie.text.Document(
				PageSize.LETTER, 36, 36, 36, 36);

		// step 2:
		// we create a writer that listens to the document
		// and directs a PDF-stream to a file
		PdfWriter.getInstance(document, new java.io.FileOutputStream(path));

		// step 3: we open the document
		document.open();

		// step 4:
		document.setMargins(36, 36, 36, 36);
		Paragraph paragraph = new Paragraph();
		paragraph.setAlignment(Element.ALIGN_LEFT);
		paragraph.add(content);
		document.add(paragraph);

		// step 5: we close the document
		document.close();
	}

	public void deleteTempFile(final String filePath) throws Exception {
		FileUtils.deleteFile(filePath);
	}

	public String getVIN(final APDDeliveryContextDocument apdContextDoc)
			throws Exception {

		String vin = null;

		final CIECADocument bms = this
				.getCIECADocumentFromAPDAssignmentContext(apdContextDoc);

		if (bms != null
				&& bms.getCIECA() != null
				&& bms.getCIECA().getAssignmentAddRq() != null
				&& bms.getCIECA().getAssignmentAddRq()
						.getVehicleDamageAssignment() != null
				&& bms.getCIECA().getAssignmentAddRq()
						.getVehicleDamageAssignment().getVehicleInfo() != null
				&& bms.getCIECA().getAssignmentAddRq()
						.getVehicleDamageAssignment().getVehicleInfo()
						.getValuationArray() != null
				&& bms.getCIECA().getAssignmentAddRq()
						.getVehicleDamageAssignment().getVehicleInfo()
						.getValuationArray().length > 0) {

			if (bms.getCIECA().getAssignmentAddRq()
					.getVehicleDamageAssignment().getVehicleInfo()
					.getVINInfoArray(0) != null
					&& bms.getCIECA().getAssignmentAddRq()
							.getVehicleDamageAssignment().getVehicleInfo()
							.getVINInfoArray(0).getVINArray() != null
					&& bms.getCIECA().getAssignmentAddRq()
							.getVehicleDamageAssignment().getVehicleInfo()
							.getVINInfoArray(0).getVINArray().length > 0) {

				if (bms.getCIECA().getAssignmentAddRq()
						.getVehicleDamageAssignment().getVehicleInfo()
						.getVINInfoArray(0).getVINArray(0) != null) {
					vin = bms.getCIECA().getAssignmentAddRq()
							.getVehicleDamageAssignment().getVehicleInfo()
							.getVINInfoArray(0).getVINArray(0).getVINNum();
				}
			}
		}
		return vin;
	}

	public boolean isOverrideRequiredForUpload(
			APDDeliveryContextDocument apdContextDoc) throws Exception {
		final String methodName = "isOverrideRequiredForUpload";
		LOGGER.entering(CLZ_NAME, methodName);
		boolean isOverrideRequied = false;
		MitchellEnvelopeDocument aaMEDoc = null;
		aaMEDoc = getMEDocFromAPDDeliveryContext(apdContextDoc);
		if (aaMEDoc != null) {
			UserInfoDocument userDoc = UserInfoDocument.Factory.newInstance();
			userDoc.setUserInfo(apdContextDoc.getAPDDeliveryContext()
					.getAPDAlertInfo().getAPDCommonInfo().getTargetUserInfo()
					.getUserInfo());
			String coCd = apdContextDoc.getAPDDeliveryContext()
					.getAPDAlertInfo().getAPDCommonInfo().getTargetUserInfo()
					.getUserInfo().getOrgCode();
			isOverrideRequied = isOverrideReqdForRCConnect(userDoc, coCd,
					new MitchellEnvelopeHelper(aaMEDoc));
		}
		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("returned from isOverrideRequiredForUpload"
					+ isOverrideRequied);
		}
		return isOverrideRequied;
	}

	public MitchellEnvelopeDocument getMEDocFromAPDDeliveryContext(
			APDDeliveryContextDocument apdContextDoc) throws Exception {

		MitchellEnvelopeDocument aaMEDoc = null;
		String taskId = apdContextDoc.getAPDDeliveryContext().getAPDAlertInfo()
				.getTaskID();
		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("getMEDocFromAPDDeliveryContext:: TASKID retrived from APDContext: [ "
					+ taskId + " ]");
		}
		if (taskId != null && taskId.trim().length() != 0) {
			WorkAssignment workAssignment = new WorkAssignmentClient()
					.getWorkAssignmentByTaskID(Long.valueOf(taskId));
			final String XmlW = workAssignment.getWorkAssignmentCLOBB();
			WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
					.parse(XmlW);
			if (workAssignmentDocument.getWorkAssignment()
					.getWorkAssignmentReference() != null
					&& workAssignmentDocument.getWorkAssignment()
							.getWorkAssignmentReference().getReferenceID()
							.getID() != null) {
				Long appraisalAssignDocId = Long.valueOf(workAssignmentDocument
						.getWorkAssignment().getWorkAssignmentReference()
						.getReferenceID().getID().longValue());
				AppraisalAssignmentDTO dto = new EstimatePackageClient()
						.getAppraisalAssignmentDocument(appraisalAssignDocId
								.longValue());
				aaMEDoc = MitchellEnvelopeDocument.Factory.parse(dto
						.getAppraisalAssignmentMEStr());
			}
		}
		return aaMEDoc;
	}

	public boolean overrideEmailAddressForCompany(String allCoCodes,
			String coCodeinRequest) {
		boolean overrideEmail = false;
		final String[] companyCode = allCoCodes.split(",");

		for (int i = 0; i < companyCode.length; i++) {
			if (companyCode[i].equals(coCodeinRequest)) { // If company code
															// matches return
															// true
				overrideEmail = true;
				break;
			}
		}
		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("overrideEmailAddressForCompany::" + overrideEmail);
		}
		return overrideEmail;
	}

	public boolean checkRCConnectUser(UserInfoDocument userInfoDoc)
			throws Exception {

		boolean isRCConnect = false;
		final UserInfoDocument xzUserInfo = this
				.getCrossOverUserInfo(userInfoDoc);

		if (xzUserInfo != null && xzUserInfo.getUserInfo() != null
				&& xzUserInfo.getUserInfo().getAppCodeArray() != null
				&& xzUserInfo.getUserInfo().getAppCodeArray().length > 0) {

			final String shopBasicAppCode = AssignmentDeliveryConfig
					.getRCConnectBasic();

			final String shopPremiumAppCode = AssignmentDeliveryConfig
					.getRCConnectPremium();

			boolean isShopBasic = false;
			boolean isShopPremium = false;
			for (int i = 0; i < xzUserInfo.getUserInfo().getAppCodeArray().length; i++) {

				// no break, in case the user has both appl codes (you never
				// know)
				// let the caller decides what to do
				if (shopBasicAppCode.equalsIgnoreCase(xzUserInfo.getUserInfo()
						.getAppCodeArray(i))) {
					isShopBasic = true;
				}

				if (shopPremiumAppCode.equalsIgnoreCase(xzUserInfo
						.getUserInfo().getAppCodeArray(i))) {
					isShopPremium = true;
				}
			}

			// only if there is such appl_code
			if (isShopBasic || isShopPremium) {
				isRCConnect = true;
			}
		}
		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("Returned checkRCConnectUser " + isRCConnect);
		}
		return isRCConnect;
	}

	private UserInfoDocument getCrossOverUserInfo(UserInfoDocument userInfo)
			throws Exception {

		String methodName = "getCrossOverUserInfo";
		LOGGER.entering(CLZ_NAME, methodName);

		CrossOverUserInfoDocument ciDoc = null;
		UserInfoDocument xzUserInfo = null;

		// UserInfoClient userInfoClient = new UserInfoClient();
		UserInfoServiceEJBRemote userInfoRemote = UserInfoClient
				.getUserInfoEJB();

		ciDoc = userInfoRemote.getCrossOverUserInfoByOrgID(Long
				.parseLong(userInfo.getUserInfo().getOrgID()));

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Cross Over UserInfo doc= " + ciDoc);
		}

		if (ciDoc != null) {

			OnlineInfoType onlineInfo = null;
			OnlineUserType[] onlineUsers = null;

			onlineInfo = ciDoc.getCrossOverUserInfo().getOnlineInfo();
			if (onlineInfo != null) {
				onlineUsers = onlineInfo.getOnlineOffice()
						.getOnlineUsersArray();

				OnlineUserType onlineUser = null;
				if (onlineUsers != null && onlineUsers.length > 0) {
					for (int i = 0; i < onlineUsers.length; i++) {
						if (onlineUsers[i].getOnlineUserOrgCode()
								.isSetIsPrimaryUser()
								&& onlineUsers[i].getOnlineUserOrgCode()
										.getIsPrimaryUser()) {
							onlineUser = onlineUsers[i];
							break;
						}
					}
				}

				if (onlineUser == null) {

					if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
						LOGGER.info("Could not get online user for org id: "
								+ userInfo.getUserInfo().getOrgID());
					}
				} else {
					long xzOrgId = onlineUser.getOnlineUserOrgID();
					xzUserInfo = userInfoRemote.getUserInfo(xzOrgId);
				}
			}
		}
		if (LOGGER.isLoggable(java.util.logging.Level.INFO)) {
			LOGGER.info("Returned From getCrossOverUserInfo" + xzUserInfo);
		}

		LOGGER.exiting(CLZ_NAME, methodName);
		return xzUserInfo;
	}

	public boolean checkIfSecondaryEmailExists(MitchellEnvelopeHelper meHelper)
			throws Exception {
		boolean secondaryEmailExist = false;

		AdditionalAppraisalAssignmentInfoDocument aaaDoc = this
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
		if (aaaDoc != null
				&& aaaDoc.getAdditionalAppraisalAssignmentInfo() != null
				&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
						.getNotificationDetails() != null
				&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
						.getNotificationDetails().getNotificationEmailTo() != null
				&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
						.getNotificationDetails().getNotificationEmailTo()
						.getEmailAddressArray() != null
				&& aaaDoc.getAdditionalAppraisalAssignmentInfo()
						.getNotificationDetails().getNotificationEmailTo()
						.getEmailAddressArray().length > 0) {
			secondaryEmailExist = true;
		}
		if (LOGGER.isLoggable(java.util.logging.Level.FINE)) {
			LOGGER.fine("checkIfSecondaryEmailExists::" + secondaryEmailExist);
		}
		return secondaryEmailExist;
	}

	public AdditionalAppraisalAssignmentInfoDocument getAdditionalAppraisalAssignmentInfoDocumentFromME(
			MitchellEnvelopeHelper meHelper) throws Exception,
			MitchellException {

		String methodName = "getAdditionalAppraisalAssignmentInfoDocumentFromME";
		LOGGER.entering(CLZ_NAME, methodName);

		AdditionalAppraisalAssignmentInfoDocument additionalnfoDocument = null;
		EnvelopeBodyType envelopeBody = meHelper
				.getEnvelopeBody(AssignmentEmailDeliveryConstants.ME_METADATA_ADDITIONAL_APPRAISAL_ASSIGNMENT_INFO_IDENTIFIER);
		if (envelopeBody != null) {
			EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
			String xmlBeanClassname = metadata.getXmlBeanClassname();

			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info("XmlBeanClassname = " + xmlBeanClassname);
			}

			String contentString = null;
			contentString = meHelper
					.getEnvelopeBodyContentAsString(meHelper
							.getEnvelopeBody(AssignmentEmailDeliveryConstants.ME_METADATA_ADDITIONAL_APPRAISAL_ASSIGNMENT_INFO_IDENTIFIER));

			if (xmlBeanClassname == null
					|| xmlBeanClassname
							.equals(AdditionalAppraisalAssignmentInfoDocument.class
									.getName())) {
				if (LOGGER.isLoggable(Level.INFO)) {
					LOGGER.info("Getting AdditionalAppraisalAssignmentInfoDocument from MitchellEnvelope");
				}
				additionalnfoDocument = AdditionalAppraisalAssignmentInfoDocument.Factory
						.parse(contentString);
			}
		}
		LOGGER.exiting(CLZ_NAME, methodName);
		return additionalnfoDocument;
	}

	public boolean isOverrideReqdForRCConnect(UserInfoDocument userInfo,
			String coCd, MitchellEnvelopeHelper meHelper) throws Exception {

		String companyCodes = AssignmentDeliveryConfig
				.getCoCdForOverrideEmailRC();
		return (this.overrideEmailAddressForCompany(companyCodes, coCd)
				&& this.checkRCConnectUser(userInfo) && this
					.checkIfSecondaryEmailExists(meHelper));
	}

	public boolean isFaxOverrideReqdForRCConnect(
			APDDeliveryContextDocument apdContextDoc) throws Exception {
		String coCd = apdContextDoc.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
				.getTargetUserInfo().getUserInfo().getOrgCode();
		String companyCodes = AssignmentDeliveryConfig
				.getCoCdForOverrideFaxRC();
		// using generic method to parse company code
		boolean faxOverride = this.overrideEmailAddressForCompany(companyCodes,
				coCd);
		return !faxOverride;

	}

	public Connection getEPDConnection() throws MitchellException {
		final String methodName = "getEPDConnection";

		Connection conn = null;
		String dataSourceName = null;
		try {
			dataSourceName = AssignmentDeliveryConfig.getEPDDataSource();
			Context ctx = new InitialContext();
			final javax.sql.DataSource ds = (javax.sql.DataSource) ctx
					.lookup(dataSourceName);
			conn = ds.getConnection();

		} catch (final NamingException ne) {
			throw new MitchellException(
					AssignmentDeliveryErrorCodes.JNDI_LOOKUP_ERROR, getClass()
							.getName(), methodName,
					"Error getting datasource: " + dataSourceName, ne);
		} catch (final SQLException sqle) {
			throw new MitchellException(
					AssignmentDeliveryErrorCodes.JNDI_LOOKUP_ERROR, getClass()
							.getName(), methodName,
					"Error getting datasource: " + dataSourceName, sqle);
		}

		return conn;
	}

	public String getAssignmentSubTypeDescription(
			APDDeliveryContextDocument apdContextDoc) throws MitchellException {
		String assignmentSubTypeDesc = null;
		MitchellEnvelopeType mitchellEnvelope = null;
		if (apdContextDoc != null
				&& apdContextDoc.getAPDDeliveryContext() != null) {
			if (apdContextDoc.getAPDDeliveryContext()
					.getAPDAppraisalAssignmentInfo() != null
					&& apdContextDoc.getAPDDeliveryContext()
							.getAPDAppraisalAssignmentInfo()
							.getAssignmentMitchellEnvelope() != null) {
				mitchellEnvelope = apdContextDoc.getAPDDeliveryContext()
						.getAPDAppraisalAssignmentInfo()
						.getAssignmentMitchellEnvelope().getMitchellEnvelope();
			}
		}
		if (mitchellEnvelope != null) {
			MitchellEnvelopeDocument mitchellDoc = MitchellEnvelopeDocument.Factory
					.newInstance();
			mitchellDoc.setMitchellEnvelope(mitchellEnvelope);
			assignmentSubTypeDesc = AssignmentEmailDeliveryUtils
					.getProcUtilInstance().retreiveAssignmentSubTypeDesc(
							mitchellDoc);
		}
		return assignmentSubTypeDesc;
	}

	private ResourceBundle getResourceBundleForCulture(String culture,
			boolean isEmailNotify) throws MitchellException {

		InternationlizeData intData = new InternationlizeDataImpl();
		Locale locale = null;
		if (isEmailNotify) {
			locale = new Locale(culture);
		} else {
			locale = intData.getLocaleByLanguage(culture);
		}

		return intData.getResourceBundle(locale);

	}

	public static void setCustomSettingHelper(
			CustomSettingHelperImpl customSettingHelper) {
		AssignmentEmailDeliveryUtils.customSettingHelper = customSettingHelper;
	}

	public static void setSystemConfigProxyImpl(
			SystemConfigProxyImpl systemConfigProxyImpl) {
		AssignmentEmailDeliveryUtils.systemConfigProxyImpl = systemConfigProxyImpl;
	}

	private String[] getClaimSuffix(String claimNumber, String coCode,
			UserInfoType userInfo) throws MitchellException {
		String claimParsingRule = getCustomSettingValue(coCode, userInfo,
				AssignmentDeliveryConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
				AssignmentDeliveryConstants.SETTING_CLAIM_MASK);
		String[] claimSuffix = null;
		if (claimParsingRule != null && null != claimNumber) {
			claimSuffix = ParseHelper.parseClaimNo(claimNumber,
					claimParsingRule);
		}
		return claimSuffix;
	}

}
