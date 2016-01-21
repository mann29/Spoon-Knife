package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.service;

import java.util.ArrayList;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.dao.CultureDAO;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryHandler;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;
import com.mitchell.schemas.NameValuePairType;
import java.util.logging.Logger;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util.AssignmentEmailDeliveryUtils;

public class AssignmentEmailDeliveryServiceImpl implements AssignmentEmailDeliveryService {
	Logger logger = Logger.getLogger(AssignmentEmailDeliveryServiceImpl.class.getName());

	private AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandler;
	private AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandlerDRP;
	private CultureDAO cultureDAO;
	private static final String CLASS_NAME = AssignmentEmailDeliveryServiceImpl.class.getName();
    private static final String COMPANY_CODE_KEY = "MitchellCompanyCode";
    public static final String LANG_SPANISH_FRENCH = "language_xslt";
    public static final String SPANISH = "es-ES";
    public static final String FRENCH = "fr-FR";
    public static final String SPANISH_XSLT = "_es.xslt";
    public static final String FRENCH_XSLT = "_fr.xslt";
    public static final String ENGLISH_CC = "en";
	
    private static Logger mLogger = Logger.getLogger(CLASS_NAME);
    
	public AssignmentEmailDeliveryHandler getAssignmentEmailDeliveryHandler() {
		return assignmentEmailDeliveryHandler;
	}
	public void setAssignmentEmailDeliveryHandler(
			AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandler) {
		this.assignmentEmailDeliveryHandler = assignmentEmailDeliveryHandler;
	}
	
	public AssignmentEmailDeliveryHandler getAssignmentEmailDeliveryHandlerDRP() {
		return assignmentEmailDeliveryHandlerDRP;
	}
	public void setAssignmentEmailDeliveryHandlerDRP(
			AssignmentEmailDeliveryHandler assignmentEmailDeliveryHandlerDRP) {
		this.assignmentEmailDeliveryHandlerDRP = assignmentEmailDeliveryHandlerDRP;
	}

	public void deliveryAssignmentEmailNotification(final APDDeliveryContextDocument aPDDeliveryContextDocument,
			final ArrayList partsListAttachment, final String emailType) throws MitchellException {

		boolean isOverrideEmailAddress = false;

		logger.info("Mitchell Envelop XML is as follows "
				+ aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
						.getAssignmentMitchellEnvelope().getMitchellEnvelope().toString());
		isOverrideEmailAddress = isOverrideToEmailAddress(aPDDeliveryContextDocument);

		logger.info("Value of isOverrideToEmailAddress is as follows " + isOverrideEmailAddress);

		if (isOriginalAssignment(aPDDeliveryContextDocument)) {
			handleOriginalEmails(aPDDeliveryContextDocument, emailType, isOverrideEmailAddress);
		} else {
			handleSupplementEmails(aPDDeliveryContextDocument, partsListAttachment, emailType, isOverrideEmailAddress);
		}

	}

	private void handleOriginalEmails(final APDDeliveryContextDocument aPDDeliveryContextDocument,
			final String emailType, final boolean isOverrideToEmailAddress) throws MitchellException {
		mLogger.info("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...");
		
		String coCode = null;
		if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAppraisalAssignmentInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getInsCoCode();
		}else if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAppraisalAssignmentNotificationInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo().getAPDCommonInfo().getInsCoCode();
		}else if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAlertInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo().getInsCoCode();
		}
		
		logger.info("handleOriginalEmails:coCode:"+coCode);
		//getting culture from database
		String cultureCode = cultureDAO.getCultureByCompany(coCode);
		if(cultureCode == null || cultureCode == ""){
			cultureCode = "en-US";
		}
		
		//try {
			/*//preeti
			String companyCode = null;
			String countryCode = null;
			String cultureCode = null;
			countryCode = getCountryCode(aPDDeliveryContextDocument);
			aPDDeliveryContextDocument.getAPDDeliveryContext().
			mLogger.info((new StringBuilder())
					.append("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...countryCode")
					.append(countryCode).toString());
			companyCode = getCompanyCode(aPDDeliveryContextDocument);
			mLogger.info((new StringBuilder())
					.append("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...companyCode")
					.append(companyCode).toString());
			if (companyCode != null) {
				mLogger.info("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...companyCode i"
						+ "s not null");
				AssignmentEmailDeliveryUtils deliveryUtils = AssignmentEmailDeliveryUtils.getInstance();
				cultureCode = deliveryUtils.getCultureCode(countryCode.toLowerCase(), companyCode.toLowerCase());
				mLogger.info((new StringBuilder())
						.append("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...cultureCode")
						.append(cultureCode).toString());
				if (cultureCode != null) {
					if (cultureCode.equals("es-ES")) {
						aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
								.getAssignmentMitchellEnvelope().documentProperties().put("language_xslt", "_es.xslt");
						mLogger.info((new StringBuilder())
								.append("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...documentPrope"
										+ "rties")
								.append(aPDDeliveryContextDocument.getAPDDeliveryContext()
										.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
										.documentProperties().get("language_xslt")).toString());
					} else if (cultureCode.equals("fr-FR")) {
						aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
								.getAssignmentMitchellEnvelope().documentProperties().put("language_xslt", "_fr.xslt");
						mLogger.info((new StringBuilder())
								.append("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...documentPrope"
										+ "rties")
								.append(aPDDeliveryContextDocument.getAPDDeliveryContext()
										.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
										.documentProperties().get("language_xslt")).toString());
					} else {
						mLogger.info("Entering AssignmentEmailDeliveryServiceImpl#handleOriginalEmails...language is n"
								+ "ot spanish/french..");
					}
				}
			}
		} catch (Exception exception) {
			mLogger.info((new StringBuilder()).append("exception is ORGINAL NOTIFICATION:::::")
					.append(exception.getMessage()).toString());
		}
*///preeti
		if (AssignmentDeliveryConstants.STAFF_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandlerDRP.deliverSTAFFEmail(aPDDeliveryContextDocument,cultureCode);
		} else if (AssignmentDeliveryConstants.SHOP_PREMIUM_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandlerDRP.deliverCreation(aPDDeliveryContextDocument, isOverrideToEmailAddress,cultureCode);
		} else if (AssignmentDeliveryConstants.SHOP_BASIC_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandler.deliverCreation(aPDDeliveryContextDocument, isOverrideToEmailAddress,cultureCode);
		} else if (AssignmentDeliveryConstants.IA_PREMIUM_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandlerDRP.deliverIAEmail(aPDDeliveryContextDocument,isOverrideToEmailAddress,cultureCode);
		} else if (AssignmentDeliveryConstants.IA_BASIC_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandler.deliverIAEmail(aPDDeliveryContextDocument,isOverrideToEmailAddress,cultureCode);
		}
	}

	private void handleSupplementEmails(final APDDeliveryContextDocument aPDDeliveryContextDocument,
			final ArrayList partsListAttachment, final String emailType, final boolean isOverrideSupplementEmailAddress)
			throws MitchellException {
		
		//Not internationalize supplement emails for now so hardcoded the culture so that we will receive emails in english only
		//tring culture =  "en-US";
		
		
		String coCode = null;
		if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAppraisalAssignmentInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getInsCoCode();
		}else if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAppraisalAssignmentNotificationInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentNotificationInfo().getAPDCommonInfo().getInsCoCode();
		}else if(aPDDeliveryContextDocument.getAPDDeliveryContext().isSetAPDAlertInfo()){
			coCode = aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo().getInsCoCode();
		}
		
		logger.info("handleSupplementEmails:"+coCode);
		//getting culture from database
		String culture = cultureDAO.getCultureByCompany(coCode);
		if(culture == null || culture == ""){
			culture = "en-US";
		}
		
		if (AssignmentDeliveryConstants.STAFF_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			// TODO
		} else if (AssignmentDeliveryConstants.SHOP_PREMIUM_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandlerDRP.deliverSupplementEmail(aPDDeliveryContextDocument, partsListAttachment,
					isOverrideSupplementEmailAddress,culture);
		} else if (AssignmentDeliveryConstants.SHOP_BASIC_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			assignmentEmailDeliveryHandler.deliverSupplementEmail(aPDDeliveryContextDocument, partsListAttachment,
					isOverrideSupplementEmailAddress,culture);
		} else if (AssignmentDeliveryConstants.IA_PREMIUM_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			// TODO
		} else if (AssignmentDeliveryConstants.IA_BASIC_EMAIL_TYPE.equalsIgnoreCase(emailType)) {
			// TODO
		}
	}

	private boolean isOriginalAssignment(APDDeliveryContextDocument aPDDeliveryContextDocument) {
		boolean isOriginalAssignment = false;
		if (AssignmentDeliveryConstants.ASSIGNMENT_TYPE_ORIGINAL.equalsIgnoreCase(aPDDeliveryContextDocument
				.getAPDDeliveryContext().getMessageType())) {
			isOriginalAssignment = true;
		}
		return isOriginalAssignment;
	}

	public boolean isOverrideToEmailAddress(final APDDeliveryContextDocument aPDDeliveryContextDocument)
			throws MitchellException {

		logger.info("Entering isOverrideToEmailAddress method");
		boolean isOverrideToEmailAddress = false;
		String isOverride = null;

		final MitchellEnvelopeType mitchellEnvelope = aPDDeliveryContextDocument.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope();

		final MitchellEnvelopeHelper mitchellEnvHelper = MitchellEnvelopeHelper.newInstance();
		mitchellEnvHelper.getDoc().setMitchellEnvelope(mitchellEnvelope);

		isOverride = mitchellEnvHelper
				.getEnvelopeContextNVPairValue(AssignmentEmailDeliveryConstants.EMAIL_OVERRIDE_CHECK);

		logger.fine("isOverride value recieved as " + isOverride);
		if (("Y").equalsIgnoreCase(isOverride)) {

			isOverrideToEmailAddress = true;

		} else if (("N").equalsIgnoreCase(isOverride)) {
			isOverrideToEmailAddress = false;
		} else {
			logger.info("com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.service.AssignmentEmailDeliveryServiceImpl  "
					+ "isOverrideToEmailAddress  " + "Invalid flag is returned other than Y or N");
		}

		logger.info("Exiting isOverrideToEmailAddress method");

		return isOverrideToEmailAddress;

	}
	public CultureDAO getCultureDAO() {
		return cultureDAO;
	}
	public void setCultureDAO(CultureDAO cultureDAO) {
		this.cultureDAO = cultureDAO;
	}

	/*private String getCountryCode(APDDeliveryContextDocument aPDDeliveryContextDocument) throws Exception {
		String countryCode = null;
		mLogger.info((new StringBuilder())
				.append("Complete XML text is ORGINAL NOTIFICATION:::getCountryCode::")
				.append(aPDDeliveryContextDocument.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
						.getAssignmentMitchellEnvelope().getMitchellEnvelope().xmlText()).toString());
		AssignmentEmailDeliveryUtils deliveryUtils = AssignmentEmailDeliveryUtils.getInstance();
		CIECADocument ciecaDoc = deliveryUtils.getCIECADocumentFromAPDAssignmentContext(aPDDeliveryContextDocument);
		if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getInspectionSite().getParty().getOrgInfo()
				.getCommunicationsArray()[0].getAddress() != null) {
			countryCode = ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getInspectionSite().getParty()
					.getOrgInfo().getCommunicationsArray()[0].getAddress().getCountryCode();
		}
		if (countryCode == null) {
			countryCode = "en";
			mLogger.info("AssignmentEmailDeliveryServiceImpl#handleOriginalEmails# country code is null");
		}
		return countryCode;
	}*/

	/*private String getCompanyCode(APDDeliveryContextDocument aPDDeliveryContextDocument) {
		String companyCode = null;
		NameValuePairType nameValuePairType[] = aPDDeliveryContextDocument.getAPDDeliveryContext()
				.getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope().getMitchellEnvelope()
				.getEnvelopeContext().getNameValuePairArray();
		mLogger.info((new StringBuilder()).append("NameValuePairType is ORGINAL NOTIFICATION:::::")
				.append(nameValuePairType).toString());
		if (nameValuePairType != null && nameValuePairType.length != 0) {
			for (int i = 0; i < nameValuePairType.length; i++) {
				mLogger.info((new StringBuilder()).append("nameValuePairType[i] is ORGINAL NOTIFICATION:::::")
						.append(nameValuePairType[i]).toString());
				if (nameValuePairType[i].getName().equals("MitchellCompanyCode")) {
					mLogger.info((new StringBuilder())
							.append("nameValuePairType[i].getValueArray() is ORGINAL NOTIFICATION:::::")
							.append(nameValuePairType[i].getValueArray()).toString());
					companyCode = nameValuePairType[i].getValueArray()[0];
					mLogger.info((new StringBuilder())
							.append("nameValuePairType[i].getValueArray()[0] is ORGINAL NOTIFICATION:::::")
							.append(nameValuePairType[i].getValueArray()[0]).toString());
				}
			}

		}
		return companyCode;
	}*/

}
