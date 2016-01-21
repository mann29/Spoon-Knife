package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cieca.bms.ApplicationInfoType;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.DamageLineInfoType;
import com.cieca.bms.ExteriorInteriorType.Color;
import com.cieca.bms.LaborInfoType;
import com.cieca.bms.PartInfoType;
import com.cieca.bms.PriceAdjustmentType;
import com.cieca.bms.RepairTotalsInfoType;
import com.cieca.bms.TotalsInfoType;
import com.cieca.bms.TotalsInfoType.TotalTaxInfo;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument.VehicleDamageEstimateAddRq;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EstimatePlatformType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemsInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineTypeTyp;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.OperationLineDescType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.schemas.compliance.LogType;
import com.mitchell.schemas.compliance.LogsDocument;
import com.mitchell.schemas.compliance.LogsType;
import com.mitchell.schemas.estimate.AnnotatedEstimateDocument;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.StringUtilities;

// public class AppraisalAssignmentSupplementRequestHelper
public class SupplementRequestDocBuildr
{
  private SupplementRequestDocument suppRequestDocument = null;

  private AnnotatedEstimateDocument annotateDocument = null;
  private VehicleDamageEstimateAddRqDocument bmsDocument = null;
  private LogsDocument complianceDocument = null;
  
  // Even though suppReqBO is unused we want to keep it because it should be used to better effect.
  @SuppressWarnings("unused")
  private SupplementReqBO suppReqBO = null;
  
  private String estimateFormat = null;

  private static final String BMS_APP_TYPE = "Conversion";
  private static final String EMS_TO_BMS = "EMSToCiecaBMS";
  private static final String EMS_TO_BMS_MUM_VERSION = "MUM1";

  private static final String CCC_APP_TYPE = "Estimating";
  private static final String CCC_APP_NAME = "Pathways";
  private static final String AUDATEX_APP_TYPE = "Estimating";
  private static final String AUDATEX_APP_NAME = "Shoplink/PenPro/Estimating";
  
  
  private static final String GTM_APP_TYPE = "Estimating";
  private static final String GTM__APP_NAME = "GTESTIMATE";
  

  private static final String LABOR_PART_BODY = "Body";
  private static final String LABOR_PART_PAINT = "Paint";
  private static final String LABOR_PART_MECHANICAL = "Mechanical";
  private static final String LABOR_PART_FRAME = "Frame";
  private static final String LABOR_PART_STRUCTURAL = "Structural";
  private static final String LABOR_PART_DIAGNOSTIC = "Diagnostic"; 
  private static final String LABOR_PART_GLASS = "Glass";
  private static final String LABOR_PART_GLASS_ADJUSTMENTS = "Glass Adjustments";
  private static final String LABOR_PART_PDR = "PDR";
  private static final String LABOR_PART_BDY_S = "Bdy-S";
  private static final String LABOR_PART_REFINISH = "Refinish";
  private static final String LABOR_TAXABLE_LABEL = "Taxable Labor";
  private static final String LABOR_GSTE_TAXABLE_LABEL = "GST E Tax";
  private static final String LABOR_GSTE_NON_TAXABLE_LABEL = "GST E Non-Tax";
  private static final String LABOR_TAX_LABEL = "Labor Tax";
  private static final String LABOR_PART_PARTS = "Parts";
  private static final String LABOR_NON_TAXABLE_LABEL = "Non-Taxable Labor";
  private static final String LABOR_PART_ELECTRICAL = "Electrical";
  private static final String LABOR_PART_TRIM = "Trim";

  private static final String LABOR_PART_ANNOT_TYPE_BODY = "BDY";
  private static final String LABOR_PART_ANNOT_TYPE_BODY_S = "BDS";
  private static final String LABOR_PART_ANNOT_TYPE_REFINISH = "REF";
  private static final String LABOR_PART_ANNOT_TYPE_GLASS_LABOR = "GLS";
  private static final String LABOR_PART_ANNOT_TYPE_FRAME = "FRM";
  private static final String LABOR_PART_ANNOT_TYPE_MECHANICAL = "MCH";
  private static final String LABOR_PART_ANNOT_TYPE_LABOR = "LAT";
  private static final String LABOR_PART_ANNOT_TYPE_ELECTRICAL = "ELE";
  private static final String LABOR_PART_ANNOT_TYPE_TRIM = "UD1";

  private static final String LABOR_PART_BMS_TYPE_BODY = "LAB";
  private static final String LABOR_PART_BMS_TYPE_BODY_S = "LAS";
  private static final String LABOR_PART_BMS_TYPE_REFINISH = "LAR";
  private static final String LABOR_PART_BMS_TYPE_GLASS_LABOR = "LAG";
  private static final String LABOR_PART_BMS_TYPE_FRAME = "LAF";
  private static final String LABOR_PART_BMS_TYPE_MECHANICAL = "LAM";  
  private static final String LABOR_PART_BMS_TYPE_LABOR = "LA";
  
  private static final String GS = "GS";
  private static final String NTC = "NTC";
  private static final String OTAC = "OTAC";
  private static final String NTL = "NTL";
  private static final String GST_I_TAX = "GST-I Tax";
  private static final String GST_E_TAX = "GST-E Tax";
  
  private static final String LABOR_PART_BMS_TYPE_PARTS = "PA";
  private static final String LABOR_PART_BMS_TYPE_GLASS = "PAG";
  private static final String LABOR_PART_BMS_TYPE_ELECTRICAL = "LAE";
  private static final String LABOR_PART_BMS_TYPE_TRIM = "LA1";
  
  private String currency;

  public String getCurrency() {
	return currency;
 }

 public void setCurrency(String currency) {
	this.currency = currency;
 }


//private static final String 
  private static final Logger logger = Logger
      .getLogger(SupplementRequestDocBuildr.class.getName());

  /**
   * Populate the SupplementReqEmail Document
   */
  public SupplementRequestDocument populateSupplementRequest(
      SupplementReqBO suppReqBO)
      throws MitchellException
  {

    try {
      logger.info(" Populating the SuppEmailRequest XML Document ...");

      this.suppReqBO = suppReqBO;

      // populate the BMS, Compliance, Annotation
      bmsDocument = suppReqBO.getBMS();
      complianceDocument = suppReqBO.getCompliance();
      annotateDocument = suppReqBO.getAnnotation();
      if (logger.isLoggable(java.util.logging.Level.INFO) && bmsDocument != null && annotateDocument != null) {
    	  logger.info("bmsDocument " +bmsDocument.toString());
    	  logger.info("annotateDocument " +annotateDocument.toString());
      }
      
    
      // get the estimate format
      estimateFormat = getEstimateFormatType(bmsDocument
          .getVehicleDamageEstimateAddRq());

      // init the supp request doc
      suppRequestDocument = SupplementRequestDocument.Factory.newInstance();
      suppRequestDocument.addNewSupplementRequest();
      // set the estimate format
      if ("EMS".equals(estimateFormat)) {
        suppRequestDocument.getSupplementRequest().setEstimateType(
            EstimatePlatformType.EMS);
      } else if ("MIE".equals(estimateFormat)) {
        suppRequestDocument.getSupplementRequest().setEstimateType(
            EstimatePlatformType.MIE);
      } else if ("CCC".equals(estimateFormat)) {
        suppRequestDocument.getSupplementRequest().setEstimateType(
            EstimatePlatformType.CCC);
      }else if ("GTMOTIVE".equals(estimateFormat)) {
    	  logger.info(" Setting estimate Format to  ..." +EstimatePlatformType.GTMOTIVE);
          suppRequestDocument.getSupplementRequest().setEstimateType(
                  EstimatePlatformType.GTMOTIVE); 
      }else {
        suppRequestDocument.getSupplementRequest().setEstimateType(
            EstimatePlatformType.AUDATEX);
      }

      // init the suppRequest with known info
      suppRequestDocument.getSupplementRequest().addNewAdminInfo();

      // sent date
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .setSentDate(Calendar.getInstance());

      if (!StringUtilities.isEmpty(suppReqBO.getCcEmailAddress())) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .setEmailCC(suppReqBO.getCcEmailAddress());
      }

      // Set flag true for DaytonaShop
      suppRequestDocument.getSupplementRequest().setIsDaytonaShop(suppReqBO.isDaytonaShop());

      // Get the company name from the reviewer User Info
      String companyName = getCompanyName(suppReqBO.getReviewerUserInfo());
      if (companyName.equalsIgnoreCase("N/A")) {
        companyName = getCompanyName(suppReqBO.getEstimatorUserInfo());
      }
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .setInsuranceCo(companyName);

      // set the external estimate Id
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .setClientEstimateId(suppReqBO.getExternalEstimateId());

      // Add the Reviewer Info
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .addNewSenderInfo();
      if(currency!=null && !currency.isEmpty()){
      logger.info("currency set to" +currency);	  
      suppRequestDocument.getSupplementRequest().getAdminInfo().setCurrency(currency);
      }

      // set name
      String name = "";
      String temp = "";
      temp = suppReqBO.getReviewerUserDetail().getUserDetail().getFirstName();
      if (!StringUtilities.isEmpty(temp)) {
        name = temp;
      }
      temp = suppReqBO.getReviewerUserDetail().getUserDetail().getLastName();
      if (!StringUtilities.isEmpty(temp)) {
        name += " " + temp;
      }
      suppRequestDocument.getSupplementRequest().getAdminInfo().getSenderInfo()
          .setName(name);

      // set phone
      temp = formatPhoneNumber(suppReqBO.getReviewerUserDetail()
          .getUserDetail().getPhone(), suppReqBO.getReviewerUserDetail()
          .getUserDetail().getPhoneExt());
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getSenderInfo().setPhone(temp);
      }

      // set email
      temp = suppReqBO.getReviewerUserInfo().getUserInfo().getEmail();
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getSenderInfo().setEmail(temp);
      }

      // set fax
      temp = formatPhoneNumber(suppReqBO.getReviewerUserDetail()
          .getUserDetail().getFax(), null);
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getSenderInfo().setFax(temp);
      }

      // Populate the EstimatorInfo

      // add the Estimator
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .addNewReceipientInfo();
      name = "";
      temp = "";

      temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getFirstName();
      if (!StringUtilities.isEmpty(temp)) {
        name = temp;
      }
      temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getLastName();
      if (!StringUtilities.isEmpty(temp)) {
        name += " " + temp;
      }
      // set name
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .getReceipientInfo().setName(name);

      // set phone
      temp = formatPhoneNumber(suppReqBO.getEstimatorUserDetail()
          .getUserDetail().getPhone(), suppReqBO.getEstimatorUserDetail()
          .getUserDetail().getPhoneExt());
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setPhone(temp);
      }

      // set fax
      temp = formatPhoneNumber(suppReqBO.getEstimatorUserDetail()
          .getUserDetail().getFax(), null);
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setFax(temp);
      }

      // set email
      temp = suppReqBO.getEstimatorUserInfo().getUserInfo().getEmail();
      if (!StringUtilities.isEmpty(temp)) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setEmail(temp);
      }

      // set co name
      temp = getCompanyName(suppReqBO.getEstimatorUserInfo());
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .getReceipientInfo().setCoName(temp);

      if (suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress() != null) {

        temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress()
            .getAddress1();
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setAddress1(temp);

        if (suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress()
            .isSetAddress2()) {
          temp = suppReqBO.getEstimatorUserDetail().getUserDetail()
              .getAddress().getAddress2();
          suppRequestDocument.getSupplementRequest().getAdminInfo()
              .getReceipientInfo().setAddress2(temp);
        }

        temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress()
            .getCity();
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setCity(temp);

        temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress()
            .getState();
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setState(temp);

        temp = suppReqBO.getEstimatorUserDetail().getUserDetail().getAddress()
            .getZip();
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().setZip(temp);
      }

      // populate the admin, vehicle data
      populateAdminVehicleData();

      // process Estimate
      processEstimate();

			// set claimId and suffix number by splitting the claim number with
			// claim mask
			// to get claimID and suffix(exposureId) distinctly
           	suppRequestDocument.getSupplementRequest().getAdminInfo()
					.setClaimId(suppReqBO.getClaimNum());
			suppRequestDocument.getSupplementRequest().getAdminInfo()
					.setSuffix(suppReqBO.getSuffixNum());
			suppRequestDocument.getSupplementRequest().getAdminInfo()
					.setSuffixLabel(suppReqBO.getSuffixLabel());

			// set image urls for header/footer and signature
			suppRequestDocument.getSupplementRequest().setStaticImageBaseUrl(
					suppReqBO.getStaticBaseUrl());
			suppRequestDocument.getSupplementRequest().setSignatureImage(
					suppReqBO.getSignatureImage());
			//set current year
			suppRequestDocument.getSupplementRequest().setCurrentYear(
					String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
      
      /**
       * Add for non-network shop supplment email.
       */
      if (suppReqBO.getTaskId() != null) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .setTaskId(suppReqBO.getTaskId());
      }
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .setCoCd(suppReqBO.getEstimatorUserInfo().getUserInfo().getOrgCode());
      if (suppReqBO.getEmailLink() != null) {
        suppRequestDocument.getSupplementRequest().getAdminInfo()
            .setURLLink(suppReqBO.getEmailLink());
      }

      if (logger.isLoggable(Level.FINE)) {
        logger.fine("The populated Request is " + suppRequestDocument);
      }
      logger.info(" Poppulating the SuppEmailRequest XML Document ... done");
      return suppRequestDocument;
    } catch (Exception e) {
      MitchellException me = new MitchellException(105200,
          SupplementRequestDocBuildr.class.getName(),
          "populateSupplementRequest",
          "Unknown Error occurred in populating the SuppEmailReq Document", e);
      logger.severe(AppUtilities.getStackTraceString(e));
      throw me;
    }
  }

  /**
   * Gets the EstimateFormat type from BMS
   */
  private String getEstimateFormatType(VehicleDamageEstimateAddRq bms)
  {
    String estimateFormatType = "MIE";
    ApplicationInfoType applicationInfo = null;
    boolean isEMS = false;

    if (bms.getApplicationInfoArray() != null
        && bms.getApplicationInfoArray().length > 0) {

      ApplicationInfoType tempAppInfo = null;

      // Check for if EMS is present
      // For Handling Legacy (Pre WCR 1.3) - EMS Mapping implementation
      for (int i = 0; i < bms.getApplicationInfoArray().length; i++) {

        tempAppInfo = bms.getApplicationInfoArray()[i];

        if (tempAppInfo.getApplicationType().equals(BMS_APP_TYPE)) {
          break;
        }
      }

      if (tempAppInfo != null
          && tempAppInfo.getApplicationType().equals(BMS_APP_TYPE)
          && tempAppInfo.getApplicationName().equals(EMS_TO_BMS)
          && tempAppInfo.getApplicationVer().startsWith(EMS_TO_BMS_MUM_VERSION)) {
        isEMS = true;
      }

      if (isEMS) {
        estimateFormatType = "EMS";
      } else {
        tempAppInfo = null;

        for (int i = 0; i < bms.getApplicationInfoArray().length; i++) {

          tempAppInfo = bms.getApplicationInfoArray()[i];

          if (tempAppInfo.getApplicationType().equals(CCC_APP_TYPE)
              || tempAppInfo.getApplicationType().equals(AUDATEX_APP_TYPE)) {

            applicationInfo = tempAppInfo;
            break;
          }
        }

        if (applicationInfo != null) {

          if (applicationInfo != null
              && applicationInfo.getApplicationType().equals(CCC_APP_TYPE)
              && applicationInfo.getApplicationName().equals(CCC_APP_NAME)) {

            estimateFormatType = "CCC";
          } else if (applicationInfo != null
              && applicationInfo.getApplicationType().equals(AUDATEX_APP_TYPE)
              && applicationInfo.getApplicationName().equals(AUDATEX_APP_NAME)) {

            estimateFormatType = "AUDATEX";
          }
          
          if (applicationInfo != null
                  && applicationInfo.getApplicationType().equals(GTM_APP_TYPE)
                  && applicationInfo.getApplicationName().equals(GTM__APP_NAME)) {

                estimateFormatType = "GTMOTIVE";
              }
          
        }
      }
    }

    return estimateFormatType;
  }

  private void populateAdminVehicleData()
  {
   
	logger.info("Entering in populateAdminVehicleData");
    // Parse BMS XML
    String claimNumber = "";
    ClaimInfoType clmInfo = bmsDocument.getVehicleDamageEstimateAddRq()
        .getClaimInfo();
    
    claimNumber = clmInfo.getClaimNum();
    logger.fine("ClaimNumber is  " + claimNumber);

    if (!StringUtilities.isEmpty(claimNumber)) {
      suppRequestDocument.getSupplementRequest().getAdminInfo()
          .setClaimNumber(claimNumber);
    }
    String fName = null;
    String lName = null;
    String SecondLastName=null;

    suppRequestDocument.getSupplementRequest().addNewVehicleInfo();
	if(bmsDocument!=null) {
    	if(bmsDocument.getVehicleDamageEstimateAddRq()!=null){
    		if(bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo()!=null){
    			if(bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo().getOwner()!=null){
    				if(bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo().getOwner().getParty()!=null){
    					if(bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo().getOwner().getParty().getPersonInfo()!=null){
    						if(bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo().getOwner().getParty().getPersonInfo().getPersonName()!=null){
    							
    							  if (bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo().isSetOwner()
    								        && bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo()
    								            .getOwner().getParty().isSetPersonInfo()) {
    								    	
    								      fName = bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo()
    								          .getOwner().getParty().getPersonInfo().getPersonName().getFirstName();
    								      lName = bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo()
    								          .getOwner().getParty().getPersonInfo().getPersonName().getLastName();
    								      
    								      SecondLastName=bmsDocument.getVehicleDamageEstimateAddRq().getAdminInfo()
    								              .getOwner().getParty().getPersonInfo().getPersonName().getSecondLastName();

    								      if (logger.isLoggable(Level.FINE)) {
    								        logger.fine("OwnerName is  " + fName + " " + lName);
    								      }
    								      String ownerName = createName(fName, lName, SecondLastName);
    								      
    								      
    								      suppRequestDocument.getSupplementRequest().getVehicleInfo()
    								          .setOwnerName(ownerName);
    								    }
    							  

    							
    						}
    					}
    				}
    			}
    		}
    		
    		//set license plate number
    		VehicleDamageEstimateAddRq vehicleDamageEstimateAddRqDoc = bmsDocument.getVehicleDamageEstimateAddRq();
    		
    		if(vehicleDamageEstimateAddRqDoc.getVehicleInfo() != null && vehicleDamageEstimateAddRqDoc.getVehicleInfo().isSetLicense()){
    			String licensePlateNum = vehicleDamageEstimateAddRqDoc.getVehicleInfo().getLicense().getLicensePlateNum();
    			suppRequestDocument.getSupplementRequest().getVehicleInfo().setLicensePlate(licensePlateNum);
    		}
    	}
    	
    }

    // year, make & model
    if (bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
        .isSetVehicleDesc()) {

      String year = null;
      if (bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
          .getVehicleDesc().isSetModelYear()) {
        year = bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
            .getVehicleDesc().getModelYear().toString();
        logger.fine("Year is  " + year);
        suppRequestDocument.getSupplementRequest().getVehicleInfo()
            .setYear(Integer.parseInt(year));
      }

      String make = null;
      if (bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
          .getVehicleDesc().isSetMakeDesc()) {
        make = bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
            .getVehicleDesc().getMakeDesc();
        logger.fine("Make is  " + make);
        suppRequestDocument.getSupplementRequest().getVehicleInfo()
            .setMake(make);
      }

      String model = null;
      if (bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
          .getVehicleDesc().isSetModelName()) {
        model = bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
            .getVehicleDesc().getModelName();
        logger.fine("Model is  " + model);
        suppRequestDocument.getSupplementRequest().getVehicleInfo()
            .setModel(model);
      }
    }

    // Color
    String colorName = null;
    if (bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
        .isSetPaint()
        && bmsDocument.getVehicleDamageEstimateAddRq().getVehicleInfo()
            .getPaint().isSetExterior()) {
      Color [] colors = bmsDocument.getVehicleDamageEstimateAddRq()
          .getVehicleInfo().getPaint().getExterior().getColorArray();

      if (colors != null) {
        if (colors[0].isSetColorName()) {
          colorName = colors[0].getColorName();
          logger.fine("Color is  " + colorName);
          suppRequestDocument.getSupplementRequest().getVehicleInfo()
              .setColor(colorName);
        }
      }
    
    
      logger.info("Exit in populateAdminVehicleData");
      }
  }
private String createName(String fName, String lName, String SecondLastName) {
	String ownerName = "";
	
      if (fName != null) {
        ownerName += fName;
      }
      if (lName != null) {
        if (lName.trim().length() > 0) {
          ownerName += SupplementRequestConstants.SPACE+lName;
        }
        
      }
      
      if (SecondLastName != null) {
    	  if (SecondLastName.trim().length() > 0) {
              ownerName +=  SupplementRequestConstants.SPACE+SecondLastName;
            }
           
        }
	return ownerName;
}

  /*
   * 
   * For MIE estimates - BMSDocument is always available
   * ComplianceDoc is always available [We have a rule which validates that]
   * Annotation may/may not be available - But for supplementation, we are
   * storing this in
   * compliance comments. So we would have it.
   */
  private void processEstimate()
  {

   logger.info("Entering in processEstimate");
    if (annotateDocument != null) {

      // If Estimate Changes are present
      if (annotateDocument.getAnnotatedEstimate().isSetEstimateSection()) {

        com.mitchell.schemas.estimate.DamageLineInfoType[] annotationDamagedLines = null;
        com.mitchell.schemas.estimate.RateInfoType[] annotationProfileRateLines = null;
        com.mitchell.schemas.estimate.RepairTotalsInfoType[] annotationRepairTotalsInfo = null;

        // Get the list of Line Items
        if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
            .isSetEstimateChanges()
            && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                .getEstimateChanges().isSetDamageLines()) {

          // add the supp - lineitem
          suppRequestDocument.getSupplementRequest().addNewLineItemsInfo();
          
          annotationDamagedLines = annotateDocument.getAnnotatedEstimate()
              .getEstimateSection().getEstimateChanges().getDamageLines()
              .getDamageLineInfoArray();

          for (int i = 0; i < annotationDamagedLines.length; i++) {
            processDamagedLine(annotationDamagedLines[i], suppRequestDocument
                .getSupplementRequest().getLineItemsInfo());
          }
        }

        if (estimateFormat.equals("CCC")) {
          ArrayList<Hashtable<String, String>> laborRateArrayLines = new ArrayList<Hashtable<String, String>>();
          //Get the list of Estimate Profile Information
          if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .isSetEstimateChanges()
              && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().isSetProfileInfo()) {

            // add the supp - EstimateProfile lineitem
            suppRequestDocument.getSupplementRequest().addNewEstimateProfile();

            annotationProfileRateLines = annotateDocument
                .getAnnotatedEstimate().getEstimateSection()
                .getEstimateChanges().getProfileInfo().getRateInfoArray();

            getLaborPartRateValue(LABOR_PART_BODY, annotationProfileRateLines,
                laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_PAINT, annotationProfileRateLines,
                laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_MECHANICAL,
                annotationProfileRateLines, laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_FRAME, annotationProfileRateLines,
                laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_STRUCTURAL,
                annotationProfileRateLines, laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_DIAGNOSTIC,
                annotationProfileRateLines, laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_ELECTRICAL,
                annotationProfileRateLines, laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_GLASS, annotationProfileRateLines,
                laborRateArrayLines);
            getLaborPartRateValue(LABOR_PART_PDR, annotationProfileRateLines,
                laborRateArrayLines);

          } else {
            generateBMSLaborRateArray(laborRateArrayLines);
          }

          if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .isSetEstimateChanges()
              && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().getRepairTotalsInfoArray().length > 0) {

            annotationRepairTotalsInfo = annotateDocument
                .getAnnotatedEstimate().getEstimateSection()
                .getEstimateChanges().getRepairTotalsInfoArray();

            if (annotationRepairTotalsInfo != null) {

              for (int i = 0; i < annotationRepairTotalsInfo.length; i++) {
                processRepairTotalsInfoLine(annotationRepairTotalsInfo[i]);
              }
              populateLaborSubTotalForPartValue(LABOR_PART_BODY,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_PAINT,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_MECHANICAL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_FRAME,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_STRUCTURAL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_DIAGNOSTIC,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_ELECTRICAL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_GLASS,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateLaborSubTotalForPartValue(LABOR_PART_PDR,
                  annotationRepairTotalsInfo, laborRateArrayLines);
            }

          }
        } else if (estimateFormat.equals("MIE")) {
          ArrayList<Hashtable<String, String>> laborRateArrayLines = new ArrayList<Hashtable<String, String>>();
          //Get the list of Estimate Profile Information
          if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .isSetEstimateChanges()
              && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().isSetProfileInfo()) {

            // add the supp - EstimateProfile lineitem
            suppRequestDocument.getSupplementRequest().addNewEstimateProfile();

            annotationProfileRateLines = annotateDocument
                .getAnnotatedEstimate().getEstimateSection()
                .getEstimateChanges().getProfileInfo().getRateInfoArray();

            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_BODY,
                LABOR_PART_ANNOT_TYPE_BODY, LABOR_PART_BODY,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_BODY_S,
                LABOR_PART_ANNOT_TYPE_BODY_S, LABOR_PART_BDY_S,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_REFINISH,
                LABOR_PART_ANNOT_TYPE_REFINISH, LABOR_PART_REFINISH,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_GLASS_LABOR,
                LABOR_PART_ANNOT_TYPE_GLASS_LABOR, LABOR_PART_GLASS,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_FRAME,
                LABOR_PART_ANNOT_TYPE_FRAME, LABOR_PART_FRAME,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_MECHANICAL,
                LABOR_PART_ANNOT_TYPE_MECHANICAL, LABOR_PART_MECHANICAL,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_LABOR,
                LABOR_PART_ANNOT_TYPE_LABOR, LABOR_TAX_LABEL,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_PARTS,
                LABOR_PART_BMS_TYPE_PARTS, LABOR_PART_PARTS,
                annotationProfileRateLines, laborRateArrayLines);
            getUltraMateLaborPartRateValue(LABOR_PART_BMS_TYPE_GLASS,
                LABOR_PART_BMS_TYPE_GLASS, LABOR_PART_GLASS_ADJUSTMENTS,
                annotationProfileRateLines, laborRateArrayLines);
          } else {
            generateBMSLaborRateArrayForUltraMate(laborRateArrayLines);
          }

          if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .isSetEstimateChanges()
              && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().getRepairTotalsInfoArray().length > 0) {

            annotationRepairTotalsInfo = annotateDocument
                .getAnnotatedEstimate().getEstimateSection()
                .getEstimateChanges().getRepairTotalsInfoArray();

            logger.info("annotateDocument getEstimateChanges "+annotateDocument
                    .getAnnotatedEstimate().getEstimateSection()
                    .getEstimateChanges());
            
            logger.info("annotationRepairTotalsInfo"+annotationRepairTotalsInfo);
            if (annotationRepairTotalsInfo != null) {
            	logger.info("laborRateArrayLines"+laborRateArrayLines);
              for (int i = 0; i < annotationRepairTotalsInfo.length; i++) {
                processRepairTotalsInfoLine(annotationRepairTotalsInfo[i],
                    laborRateArrayLines);
              }
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_BODY, LABOR_PART_BODY,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_BODY_S, LABOR_PART_BDY_S,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_FRAME, LABOR_PART_FRAME,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_GLASS_LABOR, LABOR_PART_GLASS,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_MECHANICAL, LABOR_PART_MECHANICAL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_REFINISH, LABOR_PART_REFINISH,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_LABOR, LABOR_TAXABLE_LABEL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_LABOR, LABOR_TAX_LABEL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                      LABOR_PART_BMS_TYPE_LABOR, LABOR_GSTE_TAXABLE_LABEL,
                      annotationRepairTotalsInfo, laborRateArrayLines);
              
              populateUltraMateLaborSubTotalForPartValue(
                  LABOR_PART_BMS_TYPE_LABOR, LABOR_NON_TAXABLE_LABEL,
                  annotationRepairTotalsInfo, laborRateArrayLines);
              populateUltraMateLaborSubTotalForPartValue(
                      LABOR_PART_BMS_TYPE_LABOR, LABOR_GSTE_NON_TAXABLE_LABEL,
                      annotationRepairTotalsInfo, laborRateArrayLines);
            }

          }
        }
        else if(estimateFormat.equals("GTMOTIVE")){
        	
        	logger.info("GT annotation section");

            ArrayList<Hashtable<String, String>> laborRateArrayLines = new ArrayList<Hashtable<String, String>>();
            //Get the list of Estimate Profile Information
            if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
                .isSetEstimateChanges()
                && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                    .getEstimateChanges().isSetProfileInfo()) {

              // add the supp - EstimateProfile lineitem
              suppRequestDocument.getSupplementRequest().addNewEstimateProfile();

              annotationProfileRateLines = annotateDocument
                  .getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().getProfileInfo().getRateInfoArray();

              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_BODY,
                  LABOR_PART_ANNOT_TYPE_BODY, LABOR_PART_BODY,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_BODY_S,
                  LABOR_PART_ANNOT_TYPE_BODY_S, LABOR_PART_BDY_S,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_REFINISH,
                  LABOR_PART_ANNOT_TYPE_REFINISH, LABOR_PART_REFINISH,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_GLASS_LABOR,
                  LABOR_PART_ANNOT_TYPE_GLASS_LABOR, LABOR_PART_GLASS,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_FRAME,
                  LABOR_PART_ANNOT_TYPE_FRAME, LABOR_PART_FRAME,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_MECHANICAL,
                  LABOR_PART_ANNOT_TYPE_MECHANICAL, LABOR_PART_MECHANICAL,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_ELECTRICAL, 
            	  LABOR_PART_ANNOT_TYPE_ELECTRICAL, LABOR_PART_ELECTRICAL,
            	  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_TRIM, 
                	  LABOR_PART_ANNOT_TYPE_TRIM, LABOR_PART_TRIM,
                	  annotationProfileRateLines, laborRateArrayLines);
              
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_LABOR,
                  LABOR_PART_ANNOT_TYPE_LABOR, LABOR_TAX_LABEL,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_PARTS,
                  LABOR_PART_BMS_TYPE_PARTS, LABOR_PART_PARTS,
                  annotationProfileRateLines, laborRateArrayLines);
              getGTMLaborPartRateValue(LABOR_PART_BMS_TYPE_GLASS,
                  LABOR_PART_BMS_TYPE_GLASS, LABOR_PART_GLASS_ADJUSTMENTS,
                  annotationProfileRateLines, laborRateArrayLines);
            } else {
              generateBMSLaborRateArrayForUltraMate(laborRateArrayLines);
            }

            if (annotateDocument.getAnnotatedEstimate().getEstimateSection()
                .isSetEstimateChanges()
                && annotateDocument.getAnnotatedEstimate().getEstimateSection()
                    .getEstimateChanges().getRepairTotalsInfoArray().length > 0) {

              annotationRepairTotalsInfo = annotateDocument
                  .getAnnotatedEstimate().getEstimateSection()
                  .getEstimateChanges().getRepairTotalsInfoArray();

              logger.info("annotateDocument getEstimateChanges "+annotateDocument
                      .getAnnotatedEstimate().getEstimateSection()
                      .getEstimateChanges());
              
              logger.info("annotationRepairTotalsInfo"+annotationRepairTotalsInfo);
              if (annotationRepairTotalsInfo != null) {
              	logger.info("laborRateArrayLines"+laborRateArrayLines);
                for (int i = 0; i < annotationRepairTotalsInfo.length; i++) {
                  processRepairTotalsInfoLine(annotationRepairTotalsInfo[i],
                      laborRateArrayLines);
                }
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_BODY, LABOR_PART_BODY,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_BODY_S, LABOR_PART_BDY_S,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_FRAME, LABOR_PART_FRAME,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_GLASS_LABOR, LABOR_PART_GLASS,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_MECHANICAL, LABOR_PART_MECHANICAL,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_REFINISH, LABOR_PART_REFINISH,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_LABOR, LABOR_TAXABLE_LABEL,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_LABOR, LABOR_TAX_LABEL,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_LABOR, LABOR_NON_TAXABLE_LABEL,
                    annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                	LABOR_PART_BMS_TYPE_ELECTRICAL, LABOR_PART_ELECTRICAL,
                	annotationRepairTotalsInfo, laborRateArrayLines);
                populateUltraMateLaborSubTotalForPartValue(
                    LABOR_PART_BMS_TYPE_TRIM, LABOR_PART_TRIM,
                    annotationRepairTotalsInfo, laborRateArrayLines);
              }

            }
          
        }
      }

      // get the supplementation comments
      String suppComments = null;

      /**
       * Get from the review comments section.
       * If its missing or empty - get from compliance comments.
       */

      if (annotateDocument.getAnnotatedEstimate().isSetEstimateSection()
          && annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .isSetEstimateChanges()
          && annotateDocument.getAnnotatedEstimate().getEstimateSection()
              .getEstimateChanges().isSetReviewComments()) {

        suppComments = annotateDocument.getAnnotatedEstimate()
            .getEstimateSection().getEstimateChanges().getReviewComments();

        if (!StringUtilities.isEmpty(suppComments)) {

          if (suppRequestDocument.getSupplementRequest().getDeskReviewInfo() == null) {
            suppRequestDocument.getSupplementRequest().addNewDeskReviewInfo();
          }

          suppRequestDocument.getSupplementRequest().getDeskReviewInfo()
              .setComments(suppComments);
        }
      }
    }
    
    logger.info("Exit in processEstimate");
  }

  /**
   * 
   * Process each RepairTotalsInfo from Annotation XML
   * 
   */
  private void processRepairTotalsInfoLine(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine)
  {
    if (annotationRepairTotalsInfoLine != null) {
      if (estimateFormat == "CCC") {
        //get BMS Repair Totals Info
        com.cieca.bms.RepairTotalsInfoType [] bmsRepairTotalsInfoArray = bmsDocument
            .getVehicleDamageEstimateAddRq().getRepairTotalsInfoArray();
        com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine = null;

        if (bmsRepairTotalsInfoArray != null) {
          for (int i = 0; i < bmsRepairTotalsInfoArray.length; i++) {
            bmsRepairTotalsInfoLine = bmsRepairTotalsInfoArray[i];
            if (bmsRepairTotalsInfoLine.isSetRepairTotalsType()
                && bmsRepairTotalsInfoLine.getRepairTotalsType() != null
                && bmsRepairTotalsInfoLine.getRepairTotalsType().equals(
                    "Current Claim")) {
              populatePartsSubTotalForDataChangeCCC(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
              populateLaborSubTotalForDataChangeCCC(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
              populateSummarySubTotalForDataChangeCCC(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
              populateOtherChargesSubTotalForDataChangeCCC(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
            }
          }//End of for i
        }//End of null check for bmsRepairTotalsInfo

      }
    }//End of null check for annotationRepairTotalsInfoLine
  }//End of processRepairTotalsInfoLine

  private void processRepairTotalsInfoLine(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {
    if (annotationRepairTotalsInfoLine != null) {
      if (estimateFormat == "MIE") {

        //get BMS Repair Totals Info
        com.cieca.bms.RepairTotalsInfoType [] bmsRepairTotalsInfoArray = bmsDocument
            .getVehicleDamageEstimateAddRq().getRepairTotalsInfoArray();
        com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine = null;

        if (bmsRepairTotalsInfoArray != null) {
          for (int i = 0; i < bmsRepairTotalsInfoArray.length; i++) {
            bmsRepairTotalsInfoLine = bmsRepairTotalsInfoArray[i];
            if ((bmsRepairTotalsInfoLine.isSetRepairTotalsType()
                && bmsRepairTotalsInfoLine.getRepairTotalsType() != null && bmsRepairTotalsInfoLine
                .getRepairTotalsType().equals("Current Claim"))
                || (!bmsRepairTotalsInfoLine.isSetRepairTotalsType())) {
              populatePartsSubTotalForDataChangeMIE(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine,
                  laborRateArrayLines);
              populateLaborSubTotalForDataChangeMIE(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
              populateSummarySubTotalForDataChangeMIE(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
              populateAddittionalCostsSubTotalForDataChangeMIE(
                  annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine,
                  laborRateArrayLines);
            }
          }//End of for i
        }//End of null check for bmsRepairTotalsInfo
      }
      if((estimateFormat == "GTMOTIVE")){


          //get BMS Repair Totals Info
          com.cieca.bms.RepairTotalsInfoType [] bmsRepairTotalsInfoArray = bmsDocument
              .getVehicleDamageEstimateAddRq().getRepairTotalsInfoArray();
          com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine = null;

          if (bmsRepairTotalsInfoArray != null) {
            for (int i = 0; i < bmsRepairTotalsInfoArray.length; i++) {
              bmsRepairTotalsInfoLine = bmsRepairTotalsInfoArray[i];
              if ((bmsRepairTotalsInfoLine.isSetRepairTotalsType()
                  && bmsRepairTotalsInfoLine.getRepairTotalsType() != null && bmsRepairTotalsInfoLine
                  .getRepairTotalsType().equals("Current Claim"))
                  || (!bmsRepairTotalsInfoLine.isSetRepairTotalsType())) {
                populatePartsSubTotalForDataChangeMIE(
                    annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine,
                    laborRateArrayLines);
                populateLaborSubTotalForDataChangeMIE(
                    annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
                populateSummarySubTotalForDataChangeMIE(
                    annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine);
                populateAddittionalCostsSubTotalForDataChangeMIE(
                    annotationRepairTotalsInfoLine, bmsRepairTotalsInfoLine,
                    laborRateArrayLines);
              }
            }//End of for i
          }//End of null check for bmsRepairTotalsInfo
        
    	  
      }
    }
  }//End of processRepairTotalsInfoLine

  /**
   * 
   * Populate Other Charges section from Annotation XML
   * 
   */
  private void populateOtherChargesSubTotalForDataChangeCCC(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType [] annotationOtherChargesTotalInfoArray = annotationRepairTotalsInfoLine
        .getOtherChargesTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationOtherChargesTotalInfo = null;

    com.cieca.bms.TotalsInfoType [] bmsOtherChargesTotalInfoArray = bmsRepairTotalsInfoLine
        .getOtherChargesTotalsInfoArray();
    com.cieca.bms.TotalsInfoType bmsOtherChargesTotalInfo = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementOtherChargesSubTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType supplementOtherChargesTotalLine = null;

    double bmsOtherChargesTotalAmt = 0;
    double annotationOtherChargesTotalAmt = 0;

    // FIXME: Add check for null point access to bmsOtherChargesTotalDesc
    String bmsOtherChargesTotalDesc = null;
    String annotationOtherChargesTotalDesc = null;

    if (bmsOtherChargesTotalInfoArray != null
        && annotationOtherChargesTotalInfoArray != null) {
      supplementOtherChargesSubTotalLine = suppRequestDocument
          .getSupplementRequest().addNewOtherChargesSubtotals();
      for (int j = 0; j < bmsOtherChargesTotalInfoArray.length; j++) {
        bmsOtherChargesTotalInfo = bmsOtherChargesTotalInfoArray[j];

        if (bmsOtherChargesTotalInfo.getTotalTypeDesc() != null)
          bmsOtherChargesTotalDesc = bmsOtherChargesTotalInfo
              .getTotalTypeDesc();

        if (bmsOtherChargesTotalInfo.getTotalAmt() != null)
          bmsOtherChargesTotalAmt = bmsOtherChargesTotalInfo.getTotalAmt()
              .doubleValue();

        for (int k = 0; k < annotationOtherChargesTotalInfoArray.length; k++) {
          annotationOtherChargesTotalInfo = annotationOtherChargesTotalInfoArray[k];

          if (annotationOtherChargesTotalInfo.getTotalTypeDesc() != null)
            annotationOtherChargesTotalDesc = annotationOtherChargesTotalInfo
                .getTotalTypeDesc();

          if (annotationOtherChargesTotalInfo.isSetTotalAmt()
              && annotationOtherChargesTotalInfo.getTotalAmt() != null
              && bmsOtherChargesTotalDesc
                  .equals(annotationOtherChargesTotalDesc))
            annotationOtherChargesTotalAmt = annotationOtherChargesTotalInfo
                .getTotalAmt().doubleValue();
          if (annotationOtherChargesTotalDesc != null
              && bmsOtherChargesTotalDesc
                  .equals(annotationOtherChargesTotalDesc)) {
            if (annotationOtherChargesTotalDesc.equals("Total")) {
              supplementOtherChargesSubTotalLine
                  .setTotal(formatNumber(annotationOtherChargesTotalAmt));
              if (bmsOtherChargesTotalAmt != annotationOtherChargesTotalAmt) {
                supplementOtherChargesSubTotalLine
                    .setPreviousTotal(formatNumber(bmsOtherChargesTotalAmt));
                supplementOtherChargesSubTotalLine
                    .setTotalDelta(formatNumber(annotationOtherChargesTotalAmt
                        - bmsOtherChargesTotalAmt));
              }
            } else {
              supplementOtherChargesTotalLine = suppRequestDocument
                  .getSupplementRequest().getOtherChargesSubtotals()
                  .addNewItem();
              supplementOtherChargesTotalLine
                  .setName(annotationOtherChargesTotalDesc);
              supplementOtherChargesTotalLine
                  .setValue(formatNumber(annotationOtherChargesTotalAmt));
              if (bmsOtherChargesTotalAmt != annotationOtherChargesTotalAmt) {
                supplementOtherChargesTotalLine
                    .setPreviousValue(formatNumber(bmsOtherChargesTotalAmt));
                supplementOtherChargesTotalLine
                    .setValueDelta(formatNumber(annotationOtherChargesTotalAmt
                        - bmsOtherChargesTotalAmt));
              }
            }
          }
        }
      }
    }
  }

  /**
   * 
   * Populate Additiional Costs Total section from Annotation XML
   * 
   */
  private void populateAddittionalCostsSubTotalForDataChangeMIE(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {
    com.mitchell.schemas.estimate.TotalsInfoType [] annotationOtherChargesTotalInfoArray = annotationRepairTotalsInfoLine
        .getOtherChargesTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationOtherChargesTotalInfo = null;

    com.cieca.bms.TotalsInfoType [] bmsOtherChargesTotalInfoArray = bmsRepairTotalsInfoLine
        .getOtherChargesTotalsInfoArray();
    com.cieca.bms.TotalsInfoType bmsOtherChargesTotalInfo = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementOtherChargesSubTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType supplementOtherChargesTotalLine = null;

    double bmsAdditionalTotalAmt = 0;
    double bmsTaxableCosts = 0;
    double bmsSalesTaxTotalAmt = 0;
    double bmsNonTaxableCosts = 0;
    double bmsGstTaxableTotalAmt = 0;
    double bmsGstNonTaxableTotalAmt = 0;

    double annotationOtherChargesTotalAmt = 0;
    double annotationTaxableCosts = 0;
    double annotationSalesTaxTotalAmt = 0;
    double annotationNonTaxableCosts = 0;
    double annotationGstTaxTotalAmt = 0;
    double annotationGstNonTaxTotalAmt = 0;

    double bmsAdditionalCostPercntRate = 0;
    String bmsOtherChargesType = null;
    if (bmsOtherChargesTotalInfoArray != null
        && annotationOtherChargesTotalInfoArray != null) {
      supplementOtherChargesSubTotalLine = suppRequestDocument
          .getSupplementRequest().addNewOtherChargesSubtotals();
      for (int j = 0; j < bmsOtherChargesTotalInfoArray.length; j++) {
        bmsOtherChargesTotalInfo = bmsOtherChargesTotalInfoArray[j];

        if (bmsOtherChargesTotalInfo.getTotalTypeDesc() != null)
          bmsOtherChargesType = bmsOtherChargesTotalInfo.getTotalType();

        if (bmsOtherChargesTotalInfo.isSetTotalAmt()
            && bmsOtherChargesTotalInfo.getTotalAmt() != null)
          bmsAdditionalTotalAmt = bmsOtherChargesTotalInfo.getTotalAmt()
              .doubleValue();

        if (bmsOtherChargesTotalInfo.isSetTaxableAmt()
            && bmsOtherChargesTotalInfo.getTaxableAmt() != null)
          bmsTaxableCosts = bmsOtherChargesTotalInfo.getTaxableAmt()
              .doubleValue();

        if (bmsOtherChargesTotalInfo.isSetTaxTotalAmt()
            && bmsOtherChargesTotalInfo.getTaxTotalAmt() != null)
          bmsSalesTaxTotalAmt = bmsOtherChargesTotalInfo.getTaxTotalAmt()
              .doubleValue();
        if (OTAC.equalsIgnoreCase(bmsOtherChargesTotalInfo.getTotalType())) {
	        TotalTaxInfo [] taxInfo = bmsOtherChargesTotalInfo.getTotalTaxInfoArray();
	        if (taxInfo != null) {
		        for (int i = 0; i < taxInfo.length; i++) {
		        	if (GS.equalsIgnoreCase(taxInfo[i].getTaxType())) {
		        		bmsGstTaxableTotalAmt = taxInfo[i].getTaxAmt().doubleValue();
		        		break;
		        	}
		        }
	        }
        }
        
        if (NTC.equalsIgnoreCase(bmsOtherChargesTotalInfo.getTotalType())) {
	        TotalTaxInfo [] taxInfo = bmsOtherChargesTotalInfo.getTotalTaxInfoArray();
	        if (taxInfo != null) {
		        for (int i = 0; i < taxInfo.length; i++) {
		        	if (GS.equalsIgnoreCase(taxInfo[i].getTaxType())) {
		        		bmsGstNonTaxableTotalAmt = taxInfo[i].getTaxAmt().doubleValue();
		        		break;
		        	}
		        }
	        }
        }
        	
        
        
        if (bmsOtherChargesTotalInfo.isSetNonTaxableAmt()
            && bmsOtherChargesTotalInfo.getNonTaxableAmt() != null)
          bmsNonTaxableCosts = bmsOtherChargesTotalInfo.getNonTaxableAmt()
              .doubleValue();

        for (int k = 0; k < annotationOtherChargesTotalInfoArray.length; k++) {
          annotationOtherChargesTotalInfo = annotationOtherChargesTotalInfoArray[k];
          
          if (annotationOtherChargesTotalInfo.getTotalType() != null
              && annotationOtherChargesTotalInfo.getTotalType()
                  .equalsIgnoreCase(bmsOtherChargesType)) {
            if (annotationOtherChargesTotalInfo.isSetTotalAmt()
                && annotationOtherChargesTotalInfo.getTotalAmt() != null)
              annotationOtherChargesTotalAmt = annotationOtherChargesTotalInfo
                  .getTotalAmt().doubleValue();

            if (annotationOtherChargesTotalInfo.isSetTaxableAmt()
                && annotationOtherChargesTotalInfo.getTaxableAmt() != null)
              annotationTaxableCosts = annotationOtherChargesTotalInfo
                  .getTaxableAmt().doubleValue();

            if (annotationOtherChargesTotalInfo.isSetTaxTotalAmt()
                && annotationOtherChargesTotalInfo.getTaxTotalAmt() != null)
              annotationSalesTaxTotalAmt = annotationOtherChargesTotalInfo
                  .getTaxTotalAmt().doubleValue();
            
            if (annotationOtherChargesTotalInfo.isSetGSTTaxableTotalAmt()
                    && annotationOtherChargesTotalInfo.getGSTTaxableTotalAmt() != null)
                  annotationGstTaxTotalAmt = annotationOtherChargesTotalInfo
                      .getGSTTaxableTotalAmt().doubleValue();

            if (annotationOtherChargesTotalInfo.isSetNonTaxableAmt()
                && annotationOtherChargesTotalInfo.getNonTaxableAmt() != null)
              annotationNonTaxableCosts = annotationOtherChargesTotalInfo
                  .getNonTaxableAmt().doubleValue();
            
            if (annotationOtherChargesTotalInfo.isSetGSTNonTaxableTotalAmt()
                    && annotationOtherChargesTotalInfo.getGSTNonTaxableTotalAmt() != null)
                  annotationGstNonTaxTotalAmt = annotationOtherChargesTotalInfo
                      .getGSTNonTaxableTotalAmt().doubleValue();

            if (bmsAdditionalTotalAmt != annotationOtherChargesTotalAmt) {
              supplementOtherChargesSubTotalLine
                  .setTotal(formatNumber(annotationOtherChargesTotalAmt));
              supplementOtherChargesSubTotalLine
                  .setPreviousTotal(formatNumber(bmsAdditionalTotalAmt));
              supplementOtherChargesSubTotalLine
                  .setTotalDelta(formatNumber(annotationOtherChargesTotalAmt
                      - bmsAdditionalTotalAmt));
            }/*
              * else{
              * supplementOtherChargesSubTotalLine.setTotal(formatNumber(
              * annotationOtherChargesTotalAmt));
              * }
              */

            if (bmsTaxableCosts != annotationTaxableCosts) {
              supplementOtherChargesTotalLine = suppRequestDocument
                  .getSupplementRequest().getOtherChargesSubtotals()
                  .addNewItem();
              supplementOtherChargesTotalLine.setName("Taxable Cost");
              supplementOtherChargesTotalLine
                  .setValue(formatNumber(annotationTaxableCosts));
              supplementOtherChargesTotalLine
                  .setPreviousValue(formatNumber(bmsTaxableCosts));
              supplementOtherChargesTotalLine
                  .setValueDelta(formatNumber(annotationTaxableCosts
                      - bmsTaxableCosts));
            }

            for (int x = 0; x < laborRateArrayLines.size(); x++) {
                Hashtable<String, String> laborValues = laborRateArrayLines.get(x);
              if (LABOR_PART_PARTS.equalsIgnoreCase(laborValues.get("TYPE")
                  .toString())) {
                if (laborValues.get("PERCENTAGE") != null)
                  bmsAdditionalCostPercntRate = new Double(laborValues.get(
                      "PERCENTAGE").toString()).doubleValue();
              }
            }

            if (bmsSalesTaxTotalAmt != annotationSalesTaxTotalAmt) {
              supplementOtherChargesTotalLine = suppRequestDocument
                  .getSupplementRequest().getOtherChargesSubtotals()
                  .addNewItem();
              supplementOtherChargesTotalLine.setName("Sales Tax");
              supplementOtherChargesTotalLine
                  .setValue(formatNumber(annotationSalesTaxTotalAmt));
              supplementOtherChargesTotalLine
                  .setPreviousValue(formatNumber(bmsSalesTaxTotalAmt));
              supplementOtherChargesTotalLine
                  .setValueDelta(formatNumber(annotationSalesTaxTotalAmt
                      - bmsSalesTaxTotalAmt));
              if (bmsAdditionalCostPercntRate != 0
                  && bmsAdditionalCostPercntRate != 0.00)
                supplementOtherChargesTotalLine
                    .setRatePercentage(bmsAdditionalCostPercntRate + "%");
            }
            
            if (bmsGstTaxableTotalAmt != annotationGstTaxTotalAmt) {
                supplementOtherChargesTotalLine = suppRequestDocument
                    .getSupplementRequest().getOtherChargesSubtotals()
                    .addNewItem();
                if (annotationRepairTotalsInfoLine.getGSTTaxableIndicator()) {
                	supplementOtherChargesTotalLine.setName(GST_I_TAX);
                } else { 
                    	supplementOtherChargesTotalLine.setName(GST_E_TAX);
                }
                supplementOtherChargesTotalLine
                    .setValue(formatNumber(annotationGstTaxTotalAmt));
                supplementOtherChargesTotalLine
                    .setPreviousValue(formatNumber(bmsGstTaxableTotalAmt));
                supplementOtherChargesTotalLine
                    .setValueDelta(formatNumber(annotationGstTaxTotalAmt
                        - bmsGstTaxableTotalAmt));
                double gstTaxRate = getGSTTaxRate();
                if (gstTaxRate != 0
                        && gstTaxRate != 0.00) {
                	supplementOtherChargesTotalLine
                    .setRatePercentage(gstTaxRate + "%");
                }
              }

            if (bmsNonTaxableCosts != annotationNonTaxableCosts) {
              supplementOtherChargesTotalLine = suppRequestDocument
                  .getSupplementRequest().getOtherChargesSubtotals()
                  .addNewItem();
              supplementOtherChargesTotalLine.setName("Non-Taxable Costs");
              supplementOtherChargesTotalLine
                  .setValue(formatNumber(annotationNonTaxableCosts));
              supplementOtherChargesTotalLine
                  .setPreviousValue(formatNumber(bmsNonTaxableCosts));
              supplementOtherChargesTotalLine
                  .setValueDelta(formatNumber(annotationNonTaxableCosts
                      - bmsNonTaxableCosts));
            }
          }
        }//end of for loop k
      }//End of for loop j
			if (bmsGstNonTaxableTotalAmt != annotationGstNonTaxTotalAmt) {
				supplementOtherChargesTotalLine = suppRequestDocument
						.getSupplementRequest().getOtherChargesSubtotals()
						.addNewItem();
				if (annotationRepairTotalsInfoLine.getGSTTaxableIndicator()) {
					supplementOtherChargesTotalLine.setName(GST_I_TAX);
				} else {
					supplementOtherChargesTotalLine.setName(GST_E_TAX);
				}
				supplementOtherChargesTotalLine
						.setValue(formatNumber(annotationGstNonTaxTotalAmt));
				supplementOtherChargesTotalLine
						.setPreviousValue(formatNumber(bmsGstNonTaxableTotalAmt));
				supplementOtherChargesTotalLine
						.setValueDelta(formatNumber(annotationGstNonTaxTotalAmt
								- bmsGstNonTaxableTotalAmt));
				double gstTaxRate = getGSTTaxRate();
				if (gstTaxRate != 0 && gstTaxRate != 0.00) {
					supplementOtherChargesTotalLine
							.setRatePercentage(gstTaxRate + "%");
				}

			}
		}
  }//End of populateAddittionalCostsSubTotalForDataChangeMIE

  /**
   * 
   * Populate UltraMate Summary/Adjustments Subtotal section from Annotation XML
   * 
   */
  private void populateSummarySubTotalForDataChangeMIE(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType [] annotationSummaryTotalInfoArray = annotationRepairTotalsInfoLine
        .getSummaryTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType.TotalAdjustmentInfo [] annotationAdustmentTotalInfoArray = null;
    com.mitchell.schemas.estimate.TotalsInfoType.TotalAdjustmentInfo annotationAdustmentTotalInfo = null;
    com.mitchell.schemas.estimate.TotalsInfoType annotationSummaryTotalInfo = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementAdjustmentTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementGrossTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementNetTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType supplementAdjustmentsLine = null;

    com.cieca.bms.TotalsInfoType [] bmsSummarySubTotalInfoArray = bmsRepairTotalsInfoLine
        .getSummaryTotalsInfoArray();
    com.cieca.bms.TotalsInfoType.TotalAdjustmentInfo [] bmsAdjustmentTotalInfoArray = null;
    com.cieca.bms.TotalsInfoType.TotalAdjustmentInfo bmsAdjustmentTotalInfo = null;
    com.cieca.bms.TotalsInfoType bmsSummarySubTotalInfo = null;

    double bmsAdjustmentTotal = 0;
    double annotationAdjustmentTotal = 0;

    double bmsNetTotal = 0;
    double annotationNetTotal = 0;
    double bmsGrossTotal = 0;
    double annotationGrossTotal = 0;
    double bmsDeductibleTotal = 0;
    double annotationDeductibleTotal = 0;
    double bmsInsPrepaidAmtTotal = 0;
    double annotationInsPrePaidAmtTotal = 0;

    double bmsRelatedPriorDemage = 0;
    double annotationRelatedPriorDemage = 0;
    double bmsBetterment = 0;
    double annotationBetterment = 0;
    double bmsAppearanceAllowance = 0;
    double annotationAppearanceAllowance = 0;

    String bmsAdjustmentType = null;
    String annotationAdjustmentType = null;

    if (bmsSummarySubTotalInfoArray != null
        && annotationSummaryTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      for (int j = 0; j < bmsSummarySubTotalInfoArray.length; j++) {
        bmsSummarySubTotalInfo = bmsSummarySubTotalInfoArray[j];

        if (bmsSummarySubTotalInfo.getTotalType() != null)
          bmsAdjustmentType = bmsSummarySubTotalInfo.getTotalType();

        if (bmsAdjustmentType != null && bmsAdjustmentType.equals("TOT")) {

          if (bmsSummarySubTotalInfo.getTotalSubType().equalsIgnoreCase("CE")) {
            if (bmsSummarySubTotalInfo.isSetTotalAmt()
                && bmsSummarySubTotalInfo.getTotalAmt() != null)
              bmsGrossTotal = bmsSummarySubTotalInfo.getTotalAmt()
                  .doubleValue();
          }

          if (bmsSummarySubTotalInfo.getTotalSubType().equalsIgnoreCase("D2")) {
            if (bmsSummarySubTotalInfo.isSetTotalAmt()
                && bmsSummarySubTotalInfo.getTotalAmt() != null)
              bmsDeductibleTotal = bmsSummarySubTotalInfo.getTotalAmt()
                  .doubleValue();
          }

          if (bmsSummarySubTotalInfo.getTotalSubType().equalsIgnoreCase("18")) {
            if (bmsSummarySubTotalInfo.isSetTotalAmt()
                && bmsSummarySubTotalInfo.getTotalAmt() != null)
              bmsInsPrepaidAmtTotal = bmsSummarySubTotalInfo.getTotalAmt()
                  .doubleValue();
          }

          if (bmsSummarySubTotalInfo.getTotalSubType() != null
              && bmsSummarySubTotalInfo.getTotalSubType()
                  .equalsIgnoreCase("TT")) {
            if (bmsSummarySubTotalInfo.isSetTotalAmt()
                && bmsSummarySubTotalInfo.getTotalAmt() != null)
              bmsNetTotal = bmsSummarySubTotalInfo.getTotalAmt().doubleValue();

            bmsAdjustmentTotalInfoArray = bmsSummarySubTotalInfo
                .getTotalAdjustmentInfoArray();
            if (bmsAdjustmentTotalInfoArray != null) {
              for (int l = 0; l < bmsAdjustmentTotalInfoArray.length; l++) {
                if (l == 0) {
                  bmsAdjustmentTotalInfo = bmsAdjustmentTotalInfoArray[l];

                  if (bmsAdjustmentTotalInfo.getAdjustmentType() != null)
                    bmsAdjustmentType = bmsAdjustmentTotalInfo
                        .getAdjustmentType();

                  if (bmsAdjustmentType != null
                      && bmsAdjustmentType.equalsIgnoreCase("Adjustment")
                      && bmsAdjustmentTotalInfo.getTotalAdjustmentAmt() != null)
                    bmsAdjustmentTotal = bmsAdjustmentTotalInfo
                        .getTotalAdjustmentAmt().doubleValue();
                }
              }
            }
          }
        }

        if (bmsAdjustmentType != null
            && bmsAdjustmentType.equalsIgnoreCase("RPD")) {
          if (bmsSummarySubTotalInfo.isSetTotalAmt()
              && bmsSummarySubTotalInfo.getTotalAmt() != null)
            bmsRelatedPriorDemage = bmsSummarySubTotalInfo.getTotalAmt()
                .doubleValue();
        }

        if (bmsAdjustmentType != null
            && bmsAdjustmentType.equalsIgnoreCase("BE")) {
          if (bmsSummarySubTotalInfo.isSetTotalAmt()
              && bmsSummarySubTotalInfo.getTotalAmt() != null)
            bmsBetterment = bmsSummarySubTotalInfo.getTotalAmt().doubleValue();
        }

        if (bmsAdjustmentType != null
            && bmsAdjustmentType.equalsIgnoreCase("OTAA")) {
          if (bmsSummarySubTotalInfo.isSetTotalAmt()
              && bmsSummarySubTotalInfo.getTotalAmt() != null)
            bmsAppearanceAllowance = bmsSummarySubTotalInfo.getTotalAmt()
                .doubleValue();
        }
      }//End of for j                    

      for (int k = 0; k < annotationSummaryTotalInfoArray.length; k++) {
        annotationSummaryTotalInfo = annotationSummaryTotalInfoArray[k];

        if (annotationSummaryTotalInfo.getTotalType() != null)
          annotationAdjustmentType = annotationSummaryTotalInfo.getTotalType();

        if (annotationAdjustmentType != null
            && annotationAdjustmentType.equals("TOT")) {

          if (annotationSummaryTotalInfo.getTotalSubType().equalsIgnoreCase(
              "CE")) {
            supplementGrossTotalLine = suppRequestDocument
                .getSupplementRequest().addNewGrossTotal();
            if (annotationSummaryTotalInfo.isSetTotalAmt()
                && annotationSummaryTotalInfo.getTotalAmt() != null)
              annotationGrossTotal = annotationSummaryTotalInfo.getTotalAmt()
                  .doubleValue();

            if ((bmsGrossTotal != annotationGrossTotal)) {
              supplementGrossTotalLine
                  .setTotal(formatNumber(annotationGrossTotal));
              supplementGrossTotalLine
                  .setPreviousTotal(formatNumber(bmsGrossTotal));
              supplementGrossTotalLine
                  .setTotalDelta(formatNumber(annotationGrossTotal
                      - bmsGrossTotal));
            } else {
              supplementGrossTotalLine
                  .setTotal(formatNumber(annotationGrossTotal));
            }
          }

          if (annotationSummaryTotalInfo.getTotalSubType().equalsIgnoreCase(
              "D2")) {
            if (annotationSummaryTotalInfo.isSetTotalAmt()
                && annotationSummaryTotalInfo.getTotalAmt() != null)
              annotationDeductibleTotal = annotationSummaryTotalInfo
                  .getTotalAmt().doubleValue();
          }

          if (annotationSummaryTotalInfo.getTotalSubType().equalsIgnoreCase(
              "18")) {
            if (annotationSummaryTotalInfo.isSetTotalAmt()
                && annotationSummaryTotalInfo.getTotalAmt() != null)
              annotationInsPrePaidAmtTotal = annotationSummaryTotalInfo
                  .getTotalAmt().doubleValue();
          }

          if (annotationSummaryTotalInfo.getTotalSubType() != null
              && annotationSummaryTotalInfo.getTotalSubType().equalsIgnoreCase(
                  "TT")) {
            supplementNetTotalLine = suppRequestDocument.getSupplementRequest()
                .addNewNetTotal();
            if (annotationSummaryTotalInfo.isSetTotalAmt()
                && annotationSummaryTotalInfo.getTotalAmt() != null) {
              annotationNetTotal = annotationSummaryTotalInfo.getTotalAmt()
                  .doubleValue();
            }
            if ((bmsNetTotal != annotationNetTotal)) {
              supplementNetTotalLine.setTotal(formatNumber(annotationNetTotal));
              supplementNetTotalLine
                  .setPreviousTotal(formatNumber(bmsNetTotal));
              supplementNetTotalLine
                  .setTotalDelta(formatNumber(annotationNetTotal - bmsNetTotal));
            } else {
              supplementNetTotalLine.setTotal(formatNumber(annotationNetTotal));
            }

            annotationAdustmentTotalInfoArray = annotationSummaryTotalInfo
                .getTotalAdjustmentInfoArray();
            if (annotationAdustmentTotalInfoArray != null) {
              for (int l = 0; l < annotationAdustmentTotalInfoArray.length; l++) {
                if (l == 0) {
                  annotationAdustmentTotalInfo = annotationAdustmentTotalInfoArray[l];

                  if (annotationAdustmentTotalInfo.getAdjustmentType() != null)
                    annotationAdjustmentType = annotationAdustmentTotalInfo
                        .getAdjustmentType();

                  if (annotationAdjustmentType != null
                      && annotationAdjustmentType
                          .equalsIgnoreCase("Adjustment")
                      && annotationAdustmentTotalInfo.getTotalAdjustmentAmt() != null)
                    annotationAdjustmentTotal = annotationAdustmentTotalInfo
                        .getTotalAdjustmentAmt().doubleValue();
                }
              }
              if (bmsAdjustmentTotal != annotationAdjustmentTotal) {
                supplementAdjustmentTotalLine = suppRequestDocument
                    .getSupplementRequest().addNewAdjustments();
                if (annotationAdjustmentTotal > 0) {
                  annotationAdjustmentTotal = -annotationAdjustmentTotal;
                } else {
                  annotationAdjustmentTotal = Math
                      .abs(annotationAdjustmentTotal);
                }
                if (bmsAdjustmentTotal > 0) {
                  bmsAdjustmentTotal = -bmsAdjustmentTotal;
                } else {
                  bmsAdjustmentTotal = Math.abs(bmsAdjustmentTotal);
                }
                supplementAdjustmentTotalLine
                    .setTotal(formatNumber(annotationAdjustmentTotal));
                supplementAdjustmentTotalLine
                    .setPreviousTotal(formatNumber(bmsAdjustmentTotal));
                supplementAdjustmentTotalLine
                    .setTotalDelta(formatNumber(annotationAdjustmentTotal
                        - bmsAdjustmentTotal));
              }/*
                * else if(bmsAdjustmentTotal != 0 || annotationAdjustmentTotal
                * != 0){
                * supplementAdjustmentTotalLine =
                * suppRequestDocument.getSupplementRequest
                * ().addNewAdjustments();
                * supplementAdjustmentTotalLine.setTotal(formatNumber(
                * annotationAdjustmentTotal));
                * }
                */
            }
          }
        }

        if (annotationAdjustmentType != null
            && annotationAdjustmentType.equalsIgnoreCase("RPD")) {
          if (annotationSummaryTotalInfo.isSetTotalAmt()
              && annotationSummaryTotalInfo.getTotalAmt() != null)
            annotationRelatedPriorDemage = annotationSummaryTotalInfo
                .getTotalAmt().doubleValue();
        }

        if (annotationAdjustmentType != null
            && annotationAdjustmentType.equalsIgnoreCase("BE")) {
          if (annotationSummaryTotalInfo.isSetTotalAmt()
              && annotationSummaryTotalInfo.getTotalAmt() != null)
            annotationBetterment = annotationSummaryTotalInfo.getTotalAmt()
                .doubleValue();
        }

        if (annotationAdjustmentType != null
            && annotationAdjustmentType.equalsIgnoreCase("OTAA")) {
          if (annotationSummaryTotalInfo.isSetTotalAmt()
              && annotationSummaryTotalInfo.getTotalAmt() != null)
            annotationAppearanceAllowance = annotationSummaryTotalInfo
                .getTotalAmt().doubleValue();
        }
      }//End of for k

      if (supplementAdjustmentTotalLine != null) {
        if (bmsBetterment != annotationBetterment) {
          supplementAdjustmentsLine = suppRequestDocument
              .getSupplementRequest().getAdjustments().addNewItem();
          supplementAdjustmentsLine.setName("Betterment");
          supplementAdjustmentsLine
              .setValue(formatNumber(annotationBetterment));
          supplementAdjustmentsLine
              .setPreviousValue(formatNumber(bmsBetterment));
          supplementAdjustmentsLine
              .setValueDelta(formatNumber(annotationBetterment - bmsBetterment));
        }

        if (bmsDeductibleTotal != annotationDeductibleTotal) {
          supplementAdjustmentsLine = suppRequestDocument
              .getSupplementRequest().getAdjustments().addNewItem();
          supplementAdjustmentsLine.setName("Insurance Deductible");
          supplementAdjustmentsLine
              .setValue(formatNumber(annotationDeductibleTotal));
          supplementAdjustmentsLine
              .setPreviousValue(formatNumber(bmsDeductibleTotal));
          supplementAdjustmentsLine
              .setValueDelta(formatNumber(annotationDeductibleTotal
                  - bmsDeductibleTotal));
        }

        if (bmsInsPrepaidAmtTotal != annotationInsPrePaidAmtTotal) {
          supplementAdjustmentsLine = suppRequestDocument
              .getSupplementRequest().getAdjustments().addNewItem();
          if (annotationInsPrePaidAmtTotal > 0)
            annotationInsPrePaidAmtTotal = -annotationInsPrePaidAmtTotal;
          else
            annotationInsPrePaidAmtTotal = Math
                .abs(annotationInsPrePaidAmtTotal);

          if (bmsInsPrepaidAmtTotal > 0)
            bmsInsPrepaidAmtTotal = -bmsInsPrepaidAmtTotal;
          else
            bmsInsPrepaidAmtTotal = Math.abs(bmsInsPrepaidAmtTotal);

          supplementAdjustmentsLine.setName("Insurance Prepaid Amount");
          supplementAdjustmentsLine
              .setValue(formatNumber(annotationInsPrePaidAmtTotal));
          supplementAdjustmentsLine
              .setPreviousValue(formatNumber(bmsInsPrepaidAmtTotal));
          supplementAdjustmentsLine
              .setValueDelta(formatNumber(annotationInsPrePaidAmtTotal
                  - bmsInsPrepaidAmtTotal));
        }

        if (bmsAppearanceAllowance != annotationAppearanceAllowance) {
          supplementAdjustmentsLine = suppRequestDocument
              .getSupplementRequest().getAdjustments().addNewItem();
          supplementAdjustmentsLine.setName("Appereance Allowance");
          supplementAdjustmentsLine
              .setValue(formatNumber(annotationAppearanceAllowance));
          supplementAdjustmentsLine
              .setPreviousValue(formatNumber(bmsAppearanceAllowance));
          supplementAdjustmentsLine
              .setValueDelta(formatNumber(annotationAppearanceAllowance
                  - bmsAppearanceAllowance));
        }

        if (bmsRelatedPriorDemage != annotationRelatedPriorDemage) {
          supplementAdjustmentsLine = suppRequestDocument
              .getSupplementRequest().getAdjustments().addNewItem();
          supplementAdjustmentsLine.setName("Related Prior Demange");
          supplementAdjustmentsLine
              .setValue(formatNumber(annotationRelatedPriorDemage));
          supplementAdjustmentsLine
              .setPreviousValue(formatNumber(bmsRelatedPriorDemage));
          supplementAdjustmentsLine
              .setValueDelta(formatNumber(annotationRelatedPriorDemage
                  - bmsRelatedPriorDemage));
        }
      }
    }//End of null check 

  }//End of populateSummarySubTotalForDataChangeMIE

  /**
   * 
   * Populate Summary/Adjustment Subtotal section from Annotation XML
   * 
   */
  private void populateSummarySubTotalForDataChangeCCC(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType [] annotationSummaryTotalInfoArray = annotationRepairTotalsInfoLine
        .getSummaryTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType.TotalAdjustmentInfo [] annotationAdustmentTotalInfoArray = null;
    com.mitchell.schemas.estimate.TotalsInfoType.TotalAdjustmentInfo annotationAdustmentTotalInfo = null;
    com.mitchell.schemas.estimate.TotalsInfoType annotationSummaryTotalInfo = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementTotalLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementTotalTaxLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementAdjustmentTotalLine = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType supplementAdjustmentsLine = null;

    com.cieca.bms.TotalsInfoType [] bmsSummarySubTotalInfoArray = bmsRepairTotalsInfoLine
        .getSummaryTotalsInfoArray();
    com.cieca.bms.TotalsInfoType.TotalAdjustmentInfo [] bmsAdjustmentTotalInfoArray = null;
    com.cieca.bms.TotalsInfoType.TotalAdjustmentInfo bmsAdjustmentTotalInfo = null;

    com.cieca.bms.TotalsInfoType bmsSummarySubTotalInfo = null;

    double bmsSummarySubTotal = 0;
    double annotationSummarySubTotal = 0;

    double bmsTaxTotal = 0;
    double annotationTaxTotal = 0;

    double bmsAdjustmentTotal = 0;
    double annotationAdjustmentTotal = 0;

    // FIXME: Add check for null point access to bmsSummaryTotalDesc
    String bmsSummaryTotalDesc = null;
    String annotationSummaryTotalDesc = null;

    String bmsAdjustmentType = null;
    String annotationAdjustmentType = null;

    if (bmsSummarySubTotalInfoArray != null
        && annotationSummaryTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      supplementAdjustmentTotalLine = suppRequestDocument
          .getSupplementRequest().addNewAdjustments();
      for (int j = 0; j < bmsSummarySubTotalInfoArray.length; j++) {
        bmsSummarySubTotalInfo = bmsSummarySubTotalInfoArray[j];

        if (bmsSummarySubTotalInfo.getTotalTypeDesc() != null)
          bmsSummaryTotalDesc = bmsSummarySubTotalInfo.getTotalTypeDesc();

        if (bmsSummaryTotalDesc != null
            && bmsSummaryTotalDesc.equals("Net Total")) {
          bmsAdjustmentTotalInfoArray = bmsSummarySubTotalInfo
              .getTotalAdjustmentInfoArray();
          if (bmsAdjustmentTotalInfoArray != null) {
            for (int l = 0; l < bmsAdjustmentTotalInfoArray.length; l++) {
              if (l == 0) {
                bmsAdjustmentTotalInfo = bmsAdjustmentTotalInfoArray[l];

                if (bmsAdjustmentTotalInfo.getAdjustmentType() != null)
                  bmsAdjustmentType = bmsAdjustmentTotalInfo
                      .getAdjustmentType();

                if (bmsAdjustmentType != null
                    && bmsAdjustmentType.equals("Adjustment")
                    && bmsAdjustmentTotalInfo.getTotalAdjustmentAmt() != null)
                  bmsAdjustmentTotal = bmsAdjustmentTotalInfo
                      .getTotalAdjustmentAmt().doubleValue();
              }
            }
          }
        } else if (bmsSummaryTotalDesc != null
            && bmsSummaryTotalDesc.equals("Gross Total")) {
          if (bmsSummarySubTotalInfo.getTaxTotalAmt() != null)
            bmsTaxTotal = bmsSummarySubTotalInfo.getTaxTotalAmt().doubleValue();
        }

        if (bmsSummarySubTotalInfo.isSetTotalAmt()
            && bmsSummarySubTotalInfo.getTotalAmt() != null)
          bmsSummarySubTotal = bmsSummarySubTotalInfo.getTotalAmt()
              .doubleValue();

        for (int k = 0; k < annotationSummaryTotalInfoArray.length; k++) {
          annotationSummaryTotalInfo = annotationSummaryTotalInfoArray[k];

          if (annotationSummaryTotalInfo.getTotalTypeDesc() != null)
            annotationSummaryTotalDesc = annotationSummaryTotalInfo
                .getTotalTypeDesc();

          if (annotationSummaryTotalInfo.isSetTotalAmt()
              && annotationSummaryTotalInfo.getTotalAmt() != null
              && bmsSummaryTotalDesc.equals(annotationSummaryTotalDesc))
            annotationSummarySubTotal = annotationSummaryTotalInfo
                .getTotalAmt().doubleValue();

          if ((bmsSummaryTotalDesc != null && annotationSummaryTotalDesc != null)
              && (bmsSummaryTotalDesc.equals(annotationSummaryTotalDesc))) {

            if (annotationSummaryTotalDesc.equals("Materials")) {
              supplementTotalLine = suppRequestDocument.getSupplementRequest()
                  .addNewMaterialsSubtotals();
            } else if (annotationSummaryTotalDesc.equals("Gross Total")) {
              supplementTotalLine = suppRequestDocument.getSupplementRequest()
                  .addNewGrossTotal();
              if (annotationSummaryTotalInfo.isSetTaxTotalAmt()) {
                supplementTotalTaxLine = suppRequestDocument
                    .getSupplementRequest().addNewTaxes();

                if (annotationSummaryTotalInfo.getTaxTotalAmt() != null)
                  annotationTaxTotal = annotationSummaryTotalInfo
                      .getTaxTotalAmt().doubleValue();

                supplementTotalTaxLine
                    .setTotal(formatNumber(annotationTaxTotal));
                if (bmsTaxTotal != annotationTaxTotal) {
                  supplementTotalTaxLine
                      .setPreviousTotal(formatNumber(bmsTaxTotal));
                  supplementTotalTaxLine
                      .setTotalDelta(formatNumber(annotationTaxTotal
                          - bmsTaxTotal));
                }
              }
            } else if (annotationSummaryTotalDesc.equals("Net Total")) {
              supplementTotalLine = suppRequestDocument.getSupplementRequest()
                  .addNewNetTotal();
              annotationAdustmentTotalInfoArray = annotationSummaryTotalInfo
                  .getTotalAdjustmentInfoArray();
              if (annotationAdustmentTotalInfoArray != null) {
                for (int l = 0; l < annotationAdustmentTotalInfoArray.length; l++) {
                  if (l == 0) {
                    annotationAdustmentTotalInfo = annotationAdustmentTotalInfoArray[l];

                    if (annotationAdustmentTotalInfo.getAdjustmentType() != null)
                      annotationAdjustmentType = annotationAdustmentTotalInfo
                          .getAdjustmentType();

                    if (annotationAdjustmentType != null
                        && annotationAdjustmentType.equals("Adjustment")
                        && annotationAdustmentTotalInfo.getTotalAdjustmentAmt() != null)
                      annotationAdjustmentTotal = annotationAdustmentTotalInfo
                          .getTotalAdjustmentAmt().doubleValue();

                    if (bmsAdjustmentTotal != annotationAdjustmentTotal) {
                      supplementAdjustmentTotalLine
                          .setTotal(formatNumber(annotationAdjustmentTotal));
                      supplementAdjustmentTotalLine
                          .setPreviousTotal(formatNumber(bmsAdjustmentTotal));
                      supplementAdjustmentTotalLine
                          .setTotalDelta(formatNumber(annotationAdjustmentTotal
                              - bmsAdjustmentTotal));
                    } else {
                      supplementAdjustmentTotalLine
                          .setTotal(formatNumber(annotationAdjustmentTotal));
                    }
                  }
                }
              }
            } else {
              supplementAdjustmentsLine = suppRequestDocument
                  .getSupplementRequest().getAdjustments().addNewItem();
              supplementAdjustmentsLine.setName(annotationSummaryTotalDesc);
              if (bmsSummarySubTotal != annotationSummarySubTotal) {
                supplementAdjustmentsLine
                    .setValue(formatNumber(annotationSummarySubTotal));
                supplementAdjustmentsLine
                    .setPreviousValue(formatNumber(bmsSummarySubTotal));
                supplementAdjustmentsLine
                    .setValueDelta(formatNumber(annotationSummarySubTotal
                        - bmsSummarySubTotal));
              } else {
                supplementAdjustmentsLine
                    .setValue(formatNumber(annotationSummarySubTotal));
              }
            }

            if ((bmsSummarySubTotal != annotationSummarySubTotal)
                && (supplementTotalLine != null)) {
              supplementTotalLine
                  .setTotal(formatNumber(annotationSummarySubTotal));
              supplementTotalLine
                  .setPreviousTotal(formatNumber(bmsSummarySubTotal));
              supplementTotalLine
                  .setTotalDelta(formatNumber(annotationSummarySubTotal
                      - bmsSummarySubTotal));
            } else if ((bmsSummarySubTotal == annotationSummarySubTotal)
                && supplementTotalLine != null)
              supplementTotalLine
                  .setTotal(formatNumber(annotationSummarySubTotal));
          }
        }//End of for k
      }//End of for j
    }//End of null check 

  }

  /**
   * 
   * Populate Labor Subtotal section from Annotation XML
   * 
   */
  private void populateUltraMateLaborSubTotalForPartValue(
      String laborType,
      String annotLaborDesc,
      com.mitchell.schemas.estimate.RepairTotalsInfoType[] annotationRepairTotalsInfo,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {

    com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine = null;
    com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoArray[] = null;
    com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine = null;

    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfoArray[] = null;
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborLineType supplementLaborLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementLaborTotal = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementLaborRateInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.HourInfoType supplementHourInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.AdditionalInfoType supplementAdditionalInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SubletInfoType supplementSubletInfo = null;
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfoArray[] = null;
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfo = null;

    double bmsLaborSubTotal = 0;
    double annotationLaborSubTotal = 0;
    double deltaLaborTotal = 0;

    double bmsLaborRate = 0;
    double annotationLaborRate = 0;
    double deltaLaborRate = 0;

    double bmsLaborPercentage = 0;

    double bmsLaborAddl = 0;
    double annotationLaborAddl = 0;
    double deltaLaborAddl = 0;

    double bmsLaborSublet = 0;
    double annotationLaborSublet = 0;
    double deltaLaborSublet = 0;
    
    boolean use_GST_I_Indicator = false;

    String bmsLaborTotalType = null;
    // FIXME: Add check for null point access to annotationLaborTotalType
    String annotationLaborTotalType = null;

    double bmsLaborHours = 0;
    double annotationLaborHours = 0;
    double deltaLaborHours = 0;

    for (int a = 0; a < annotationRepairTotalsInfo.length; a++) {
    	if (annotationRepairTotalsInfo[a].getGSTTaxableIndicator()) {
        	use_GST_I_Indicator = true;
        }
      annotationRepairTotalsInfoLine = annotationRepairTotalsInfo[a];
      bmsRepairTotalsInfoArray = bmsDocument.getVehicleDamageEstimateAddRq()
          .getRepairTotalsInfoArray();

      if (bmsRepairTotalsInfoArray != null) {

        for (int b = 0; b < bmsRepairTotalsInfoArray.length; b++) {
          bmsRepairTotalsInfoLine = bmsRepairTotalsInfoArray[b];
          if ((bmsRepairTotalsInfoLine.isSetRepairTotalsType()
              && bmsRepairTotalsInfoLine.getRepairTotalsType() != null && bmsRepairTotalsInfoLine
              .getRepairTotalsType().equals("Current Claim"))
              || (!bmsRepairTotalsInfoLine.isSetRepairTotalsType())) {

            bmsLaborSubTotalInfoArray = bmsRepairTotalsInfoLine
                .getLaborTotalsInfoArray();
            annotationLaborTotalInfoArray = annotationRepairTotalsInfoLine
                .getLaborTotalsInfoArray();

            if (bmsLaborSubTotalInfoArray != null
                && annotationLaborTotalInfoArray != null) {
              //Add the supp PartsSubTotal Info
              if (bmsLaborSubTotalInfoArray.length > 0) {
                bmsLaborHours = 0;
                bmsLaborSubTotal = 0;
                bmsLaborSublet = 0;
                bmsLaborAddl = 0;
              }

              for (int j = 0; j < bmsLaborSubTotalInfoArray.length; j++) {
                bmsLaborSubTotalInfo = bmsLaborSubTotalInfoArray[j];

                if (bmsLaborSubTotalInfo.getTotalType() != null)
                  bmsLaborTotalType = bmsLaborSubTotalInfo.getTotalType();

                if (bmsLaborTotalType != null
                    && bmsLaborTotalType.equalsIgnoreCase(laborType)
                    && !laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)) {
                  if (bmsLaborSubTotalInfo.isSetTotalHours()
                      && bmsLaborSubTotalInfo.getTotalHours() != null)
                    bmsLaborHours = bmsLaborSubTotalInfo.getTotalHours()
                        .doubleValue();

                  if (bmsLaborSubTotalInfo.isSetTotalAmt()
                      && bmsLaborSubTotalInfo.getTotalAmt() != null)
                    bmsLaborSubTotal = bmsLaborSubTotalInfo.getTotalAmt()
                        .doubleValue();
                }

                if (bmsLaborTotalType != null
                    && bmsLaborTotalType.equalsIgnoreCase(laborType)
                    && laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)) {
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_TAXABLE_LABEL)) {
                    if (bmsLaborSubTotalInfo.isSetTaxableAmt()
                        && bmsLaborSubTotalInfo.getTaxableAmt() != null)
                      bmsLaborSubTotal = bmsLaborSubTotalInfo.getTaxableAmt()
                          .doubleValue();
                  }

                  if (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)) {
                    if (bmsLaborSubTotalInfo.isSetTaxTotalAmt()
                        && bmsLaborSubTotalInfo.getTaxTotalAmt() != null)
                      bmsLaborSubTotal = bmsLaborSubTotalInfo.getTaxTotalAmt()
                          .doubleValue();
                  }

                  if (annotLaborDesc.equalsIgnoreCase(LABOR_NON_TAXABLE_LABEL)) {
                    if (bmsLaborSubTotalInfo.isSetNonTaxableAmt()
                        && bmsLaborSubTotalInfo.getNonTaxableAmt() != null)
                      bmsLaborSubTotal = bmsLaborSubTotalInfo
                          .getNonTaxableAmt().doubleValue();
                  }
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_GSTE_TAXABLE_LABEL)) {
                	  TotalTaxInfo [] totalLaborTaxInfo = bmsLaborSubTotalInfo.getTotalTaxInfoArray();
                	  
                	  if (totalLaborTaxInfo != null) {
	                	  for (int i = 0; i < totalLaborTaxInfo.length; i++) {
	                		  
	                		  if (GS.equalsIgnoreCase(totalLaborTaxInfo[i].getTaxType())) {
	                			  bmsLaborSubTotal = totalLaborTaxInfo[i].getTaxAmt().doubleValue();
	                		  }
	                	  }
                	  }
                      
                  }

                }

                if (bmsLaborTotalType != null
                    && bmsLaborTotalType.equalsIgnoreCase(laborType + "A")) {
                  if (bmsLaborSubTotalInfo.isSetTotalAmt()
                      && bmsLaborSubTotalInfo.getTotalAmt() != null)
                    bmsLaborAddl = bmsLaborSubTotalInfo.getTotalAmt()
                        .doubleValue();
                }

                if (bmsLaborTotalType != null
                    && !laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)
                    && bmsLaborTotalType.equalsIgnoreCase(laborType + "S")) {
                  if (bmsLaborSubTotalInfo.isSetTotalAmt()
                      && bmsLaborSubTotalInfo.getTotalAmt() != null)
                    bmsLaborSublet = bmsLaborSubTotalInfo.getTotalAmt()
                        .doubleValue();
                }

              }//End of for j

              for (int k = 0; k < annotationLaborTotalInfoArray.length; k++) {
                annotationLaborTotalInfo = annotationLaborTotalInfoArray[k];

                if ((annotationLaborTotalInfo.getTotalType() != null)
                    && (annotationLaborTotalInfo.getTotalType()
                        .equalsIgnoreCase(laborType)
                        || annotationLaborTotalInfo.getTotalType()
                            .equalsIgnoreCase(laborType + "A")
                        || annotationLaborTotalInfo.getTotalType()
                            .equalsIgnoreCase(laborType + "S") || (annotationLaborTotalInfo
                        .getTotalType().equalsIgnoreCase(
                            LABOR_PART_ANNOT_TYPE_LABOR) && laborType
                        .equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR))))
                  annotationLaborTotalType = annotationLaborTotalInfo
                      .getTotalType();
                else
                  annotationLaborTotalType = null;

                for (int x = 0; x < laborRateArrayLines.size(); x++) {
                    Hashtable<String, String> laborValues = laborRateArrayLines
                      .get(x);
                 
                  if(laborValues.get("TYPE") != null)
                  if (annotLaborDesc.equalsIgnoreCase(laborValues.get("TYPE")
                      .toString())
                      && annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)) {
                    if (laborValues.get("PERCENTAGE") != null)
                      bmsLaborPercentage = new Double(laborValues.get(
                          "PERCENTAGE").toString()).doubleValue();
                  } else if (laborValues.get("TYPE") != null && annotLaborDesc.equalsIgnoreCase(laborValues.get(
                      "TYPE").toString())) {
                    if (laborValues.get("PREVIOUSRATE") != null)
                      bmsLaborRate = new Double(laborValues.get("PREVIOUSRATE")
                          .toString()).doubleValue();
                    if (laborValues.get("RATE") != null)
                      annotationLaborRate = new Double(laborValues.get("RATE")
                          .toString()).doubleValue();
                    if (laborValues.get("RATEDELTA") != null)
                      deltaLaborRate = new Double(laborValues.get("RATEDELTA")
                          .toString()).doubleValue();
                  }
                }

                
                logger.info("populateUltraMateLaborSubTotalForPartValue:::");
                //Add the supp LaborSubTotal LaborLine Info
                //supplementLaborLine = suppRequestDocument.getSupplementRequest().getLaborSubTotals().addNewLaborLine();                            
                //supplementLaborLine.setType(annotationLaborTotalDesc);
                if (annotationLaborTotalType != null
                    && annotationLaborTotalType.equalsIgnoreCase(laborType)) {
                  if (annotationLaborTotalInfo.isSetTotalHours()
                      && annotationLaborTotalInfo.getTotalHours() != null)
                    annotationLaborHours = annotationLaborTotalInfo
                        .getTotalHours().doubleValue();
                  else
                    annotationLaborHours = 0;

                  if (annotationLaborTotalInfo.isSetTotalAmt()
                      && annotationLaborTotalInfo.getTotalAmt() != null)
                    annotationLaborSubTotal = annotationLaborTotalInfo
                        .getTotalAmt().doubleValue();
                  else
                    annotationLaborSubTotal = 0;
                }

                if (annotationLaborTotalType != null
                    && laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)
                    && annotationLaborTotalType
                        .equalsIgnoreCase(LABOR_PART_ANNOT_TYPE_LABOR)) {
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_TAXABLE_LABEL)) {
                    if (annotationLaborTotalInfo.isSetTaxableAmt()
                        && annotationLaborTotalInfo.getTaxableAmt() != null)
                      annotationLaborSubTotal = annotationLaborTotalInfo
                          .getTaxableAmt().doubleValue();
                    else
                      annotationLaborSubTotal = 0;
                  }

                  if (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)) {
                    if (annotationLaborTotalInfo.isSetTaxTotalAmt()
                        && annotationLaborTotalInfo.getTaxTotalAmt() != null)
                      annotationLaborSubTotal = annotationLaborTotalInfo
                          .getTaxTotalAmt().doubleValue();
                    else
                      annotationLaborSubTotal = 0;
                  }
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_GSTE_TAXABLE_LABEL)) {
                      if (annotationLaborTotalInfo.isSetGSTTaxableTotalAmt()
                          && annotationLaborTotalInfo.getGSTTaxableTotalAmt() != null){
                    	  
                        annotationLaborSubTotal = annotationLaborTotalInfo
                            .getGSTTaxableTotalAmt().doubleValue();
                      }
                      else
                        annotationLaborSubTotal = 0;
                    }
                  
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_NON_TAXABLE_LABEL)) {
                    if (annotationLaborTotalInfo.isSetNonTaxableAmt()
                        && annotationLaborTotalInfo.getNonTaxableAmt() != null)
                      annotationLaborSubTotal = annotationLaborTotalInfo
                          .getNonTaxableAmt().doubleValue();
                    else
                      annotationLaborSubTotal = 0;
                  }
                  if (annotLaborDesc.equalsIgnoreCase(LABOR_GSTE_NON_TAXABLE_LABEL)) {
                      if (annotationLaborTotalInfo.isSetGSTNonTaxableTotalAmt()
                          && annotationLaborTotalInfo.getGSTNonTaxableTotalAmt() != null){
                    	  
                    	  annotationLaborSubTotal = annotationLaborTotalInfo
                            .getGSTNonTaxableTotalAmt().doubleValue();
                      }
                      else {
                        annotationLaborSubTotal = 0;
                      }
                      
                      for (int i = 0; i < bmsRepairTotalsInfoArray.length; i++) {
    			          RepairTotalsInfoType bmsRepairTotalsInfoLines = bmsRepairTotalsInfoArray[i];
    			          TotalsInfoType [] totalInfo = bmsRepairTotalsInfoLines.getLaborTotalsInfoArray();
    			          for (int j = 0; j < totalInfo.length; j++) {
    							if (NTL.equalsIgnoreCase(totalInfo[j].getTotalType())) {
    								TotalTaxInfo [] taxArray = totalInfo[j].getTotalTaxInfoArray();
    								for (int temp = 0; temp< taxArray.length; temp++) {
    									if (GS.equalsIgnoreCase(taxArray[temp].getTaxType())) {
    										bmsLaborSubTotal = taxArray[temp]
    												.getTaxAmt()
    												.doubleValue();
    										break;
    									}
    								}
    							}
    						}
    			          
    			        }
                    }
                }

                if (annotationLaborTotalType != null
                    && annotationLaborTotalType.equalsIgnoreCase(laborType
                        + "A")) {

                  if (annotationLaborTotalInfo.isSetTotalAmt()
                      && annotationLaborTotalInfo.getTotalAmt() != null)
                    annotationLaborAddl = annotationLaborTotalInfo
                        .getTotalAmt().doubleValue();
                  else
                    annotationLaborAddl = 0;

                }
                if (annotationLaborTotalType != null
                    && !laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)
                    && annotationLaborTotalType.equalsIgnoreCase(laborType
                        + "S")) {
                  if (annotationLaborTotalInfo.isSetTotalAmt()
                      && annotationLaborTotalInfo.getTotalAmt() != null)
                    annotationLaborSublet = annotationLaborTotalInfo
                        .getTotalAmt().doubleValue();
                  else
                    annotationLaborSublet = 0;
                }
                //}         
              }//End of for k

              deltaLaborAddl = annotationLaborAddl - bmsLaborAddl;
              deltaLaborSublet = annotationLaborSublet - bmsLaborSublet;
              deltaLaborHours = annotationLaborHours - bmsLaborHours;
              deltaLaborTotal = annotationLaborSubTotal - bmsLaborSubTotal;

              if (laborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)
                  && annotationLaborTotalType
                      .equalsIgnoreCase(LABOR_PART_ANNOT_TYPE_LABOR)) {
                if (bmsLaborSubTotal != annotationLaborSubTotal
                    && deltaLaborTotal != 0) {
                  supplementLaborLine = suppRequestDocument
                      .getSupplementRequest().getLaborSubTotals()
                      .addNewLaborLine();
                  if (LABOR_GSTE_TAXABLE_LABEL.equalsIgnoreCase(annotLaborDesc) || LABOR_GSTE_NON_TAXABLE_LABEL.equalsIgnoreCase(annotLaborDesc)) {
                	  if (use_GST_I_Indicator) {
                		  supplementLaborLine.setType(GST_I_TAX);
                	  } else {
                		  supplementLaborLine.setType(GST_E_TAX);
                	  }
                	  
                	  double gstTaxRate = getGSTTaxRate();
                      if (gstTaxRate != 0
                              && gstTaxRate != 0.00) {
                    	  supplementLaborRateInfo = supplementLaborLine.addNewRate();
                          supplementLaborRateInfo.setRate(gstTaxRate + "%");
                          supplementLaborLine.setRate(supplementLaborRateInfo);
                      }
                	  
                  } else {
                	  supplementLaborLine.setType(annotLaborDesc);  
                  }
                  

                  if (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)) {
                    supplementLaborRateInfo = supplementLaborLine.addNewRate();
                    supplementLaborRateInfo.setRate(formatPercentage(String
                        .valueOf(bmsLaborPercentage)));
                    supplementLaborLine.setRate(supplementLaborRateInfo);
                  }

                  supplementLaborTotal = supplementLaborLine.addNewTotal();
                  supplementLaborTotal
                      .setTotal(formatNumber(annotationLaborSubTotal));
                  supplementLaborTotal
                      .setPreviousTotal(formatNumber(bmsLaborSubTotal));
                  deltaLaborTotal = annotationLaborSubTotal - bmsLaborSubTotal;
                  supplementLaborTotal
                      .setTotalDelta(formatNumber(deltaLaborTotal));
                  supplementLaborLine.setTotal(supplementLaborTotal);
                }
              } else if (((annotationLaborTotalType != null) && (annotationLaborTotalType
                  .equalsIgnoreCase(laborType)
                  || annotationLaborTotalType.equalsIgnoreCase(laborType + "A") || annotationLaborTotalType
                  .equalsIgnoreCase(laborType + "S")))
                  || (deltaLaborRate != 0 || deltaLaborTotal != 0
                      || deltaLaborHours != 0 || deltaLaborAddl != 0 || deltaLaborSublet != 0)) {
                //Add the supp LaborSubTotal LaborLine Info
                supplementLaborLine = suppRequestDocument
                    .getSupplementRequest().getLaborSubTotals()
                    .addNewLaborLine();
                supplementLaborLine.setType(annotLaborDesc);

                if (bmsLaborHours != annotationLaborHours) {
                  supplementHourInfo = supplementLaborLine.addNewHours();
                  supplementHourInfo
                      .setHour(formatToTwoDecimalNumber(annotationLaborHours));
                  deltaLaborHours = annotationLaborHours - bmsLaborHours;
                  supplementHourInfo
                      .setHourDelta(formatToTwoDecimalNumber(deltaLaborHours));
                  supplementHourInfo
                      .setPreviousHour(formatToTwoDecimalNumber(bmsLaborHours));
                  supplementLaborLine.setHours(supplementHourInfo);
                } else {
                  supplementHourInfo = supplementLaborLine.addNewHours();
                  supplementHourInfo
                      .setHour(formatToTwoDecimalNumber(bmsLaborHours));
                  supplementLaborLine.setHours(supplementHourInfo);
                }

                if (bmsLaborSubTotal != annotationLaborSubTotal) {
                  supplementLaborTotal = supplementLaborLine.addNewTotal();
                  supplementLaborTotal
                      .setTotal(formatNumber(annotationLaborSubTotal));
                  supplementLaborTotal
                      .setPreviousTotal(formatNumber(bmsLaborSubTotal));
                  deltaLaborTotal = annotationLaborSubTotal - bmsLaborSubTotal;
                  supplementLaborTotal
                      .setTotalDelta(formatNumber(deltaLaborTotal));
                  supplementLaborLine.setTotal(supplementLaborTotal);
                } else {
                  supplementLaborTotal = supplementLaborLine.addNewTotal();
                  supplementLaborTotal.setTotal(formatNumber(bmsLaborSubTotal));
                  supplementLaborLine.setTotal(supplementLaborTotal);
                }

                if (bmsLaborRate != annotationLaborRate && deltaLaborRate != 0) {
                  supplementLaborRateInfo = supplementLaborLine.addNewRate();
                  supplementLaborRateInfo
                      .setRate(formatNumber(annotationLaborRate));
                  supplementLaborRateInfo
                      .setPreviousRate(formatNumber(bmsLaborRate));
                  supplementLaborRateInfo
                      .setRateDelta(formatNumber(deltaLaborRate));
                  supplementLaborLine.setRate(supplementLaborRateInfo);
                } else {
                  supplementLaborRateInfo = supplementLaborLine.addNewRate();
                  supplementLaborRateInfo.setRate(formatNumber(bmsLaborRate));
                  supplementLaborLine.setRate(supplementLaborRateInfo);
                }

                if (bmsLaborAddl != annotationLaborAddl && deltaLaborAddl != 0) {
                  supplementAdditionalInfo = supplementLaborLine
                      .addNewAdditional();
                  supplementAdditionalInfo
                      .setAdditional(formatNumber(annotationLaborAddl));
                  supplementAdditionalInfo
                      .setPreviousAdditional(formatNumber(bmsLaborAddl));
                  supplementAdditionalInfo
                      .setAdditionalDelta(formatNumber(deltaLaborAddl));
                  supplementLaborLine.setAdditional(supplementAdditionalInfo);
                } else {
                  supplementAdditionalInfo = supplementLaborLine
                      .addNewAdditional();
                  supplementAdditionalInfo
                      .setAdditional(formatNumber(bmsLaborAddl));
                  supplementLaborLine.setAdditional(supplementAdditionalInfo);
                }

                if (bmsLaborSublet != annotationLaborSublet
                    && deltaLaborSublet != 0) {
                  supplementSubletInfo = supplementLaborLine.addNewSublet();
                  supplementSubletInfo
                      .setSublet(formatNumber(annotationLaborSublet));
                  supplementSubletInfo
                      .setPreviousSublet(formatNumber(bmsLaborSublet));
                  supplementSubletInfo
                      .setSubletDelta(formatNumber(deltaLaborSublet));
                  supplementLaborLine.setSublet(supplementSubletInfo);
                } else {
                  supplementSubletInfo = supplementLaborLine.addNewSublet();
                  supplementSubletInfo.setSublet(formatNumber(bmsLaborSublet));
                  supplementLaborLine.setSublet(supplementSubletInfo);
                }

              } //End of if annotationLaborTotalDesc
            }//End of null check bmsPartsSubTotalInfoArray
          }//End of if check for Current Claim
        }//End of for b
      }//End of null check bmsRepairTotalsInfoArray
    }//End of for a

  }//End of populateUltraMateLaborSubTotalForPartValue

  /**
   * 
   * Populate Labor Subtotal section from Annotation XML
   * 
   */
  private void populateLaborSubTotalForPartValue(
      String laborPartValue,
      com.mitchell.schemas.estimate.RepairTotalsInfoType[] annotationRepairTotalsInfo,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {

    com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine = null;
    com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoArray[] = null;
    com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine = null;

    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfoArray[] = null;
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborLineType supplementLaborLine = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementLaborTotal = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementLaborRateInfo = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.HourInfoType supplementHourInfo = null;
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfoArray[] = null;
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfo = null;

    double bmsLaborSubTotal = 0;
    double annotationLaborSubTotal = 0;
    double deltaLaborTotal = 0;

    double bmsLaborRate = 0;
    double annotationLaborRate = 0;

    double deltaLaborRate = 0;

    String bmsLaborTotalDesc = null;
    // FIXME: Add check for null point access to annotationLaborTotalDesc
   String annotationLaborTotalDesc = null;

    double bmsLaborHours = 0;
    double annotationLaborHours = 0;
    double deltaLaborHours = 0;

    for (int a = 0; a < annotationRepairTotalsInfo.length; a++) {
      annotationRepairTotalsInfoLine = annotationRepairTotalsInfo[a];
      bmsRepairTotalsInfoArray = bmsDocument.getVehicleDamageEstimateAddRq()
          .getRepairTotalsInfoArray();

      if (bmsRepairTotalsInfoArray != null) {

        for (int b = 0; b < bmsRepairTotalsInfoArray.length; b++) {
          bmsRepairTotalsInfoLine = bmsRepairTotalsInfoArray[b];
          if (bmsRepairTotalsInfoLine.isSetRepairTotalsType()
              && bmsRepairTotalsInfoLine.getRepairTotalsType() != null
              && bmsRepairTotalsInfoLine.getRepairTotalsType().equals(
                  "Current Claim")) {

            annotationLaborTotalInfoArray = annotationRepairTotalsInfoLine
                .getLaborTotalsInfoArray();
            bmsLaborSubTotalInfoArray = bmsRepairTotalsInfoLine
                .getLaborTotalsInfoArray();

            if (bmsLaborSubTotalInfoArray != null
                && annotationLaborTotalInfoArray != null) {
              //Add the supp PartsSubTotal Info

              for (int j = 0; j < bmsLaborSubTotalInfoArray.length; j++) {

                bmsLaborSubTotalInfo = bmsLaborSubTotalInfoArray[j];

                if (bmsLaborSubTotalInfo.getTotalTypeDesc() != null)
                  bmsLaborTotalDesc = bmsLaborSubTotalInfo.getTotalTypeDesc();

                if (bmsLaborTotalDesc != null
                    && bmsLaborTotalDesc.equalsIgnoreCase(laborPartValue)) {
                  if (bmsLaborSubTotalInfo.isSetTotalHours()
                      && bmsLaborSubTotalInfo.getTotalHours() != null)
                    bmsLaborHours = bmsLaborSubTotalInfo.getTotalHours()
                        .doubleValue();

                  if (bmsLaborSubTotalInfo.isSetTotalAmt()
                      && bmsLaborSubTotalInfo.getTotalAmt() != null)
                    bmsLaborSubTotal = bmsLaborSubTotalInfo.getTotalAmt()
                        .doubleValue();
                  break;
                } else {
                  bmsLaborHours = 0;
                  bmsLaborSubTotal = 0;
                  bmsLaborTotalDesc = laborPartValue;
                }
              }//End of for j

              for (int k = 0; k < annotationLaborTotalInfoArray.length; k++) {
                annotationLaborTotalInfo = annotationLaborTotalInfoArray[k];

                if (annotationLaborTotalInfo.getTotalTypeDesc() != null)
                  annotationLaborTotalDesc = annotationLaborTotalInfo
                      .getTotalTypeDesc();

                for (int x = 0; x < laborRateArrayLines.size(); x++) {
                    Hashtable<String, String> laborValues = laborRateArrayLines
                      .get(x);
                  if (annotationLaborTotalDesc.equalsIgnoreCase(laborPartValue)
                      && laborPartValue.equalsIgnoreCase(laborValues
                          .get("TYPE").toString())) {

                    if (laborValues.get("PREVIOUSRATE") != null)
                      bmsLaborRate = new Double(laborValues.get("PREVIOUSRATE")
                          .toString()).doubleValue();
                    if (laborValues.get("RATE") != null)
                      annotationLaborRate = new Double(laborValues.get("RATE")
                          .toString()).doubleValue();
                    if (laborValues.get("RATEDELTA") != null)
                      deltaLaborRate = new Double(laborValues.get("RATEDELTA")
                          .toString()).doubleValue();
                  }
                }

                if (annotationLaborTotalInfo.isSetTotalHours()
                    && annotationLaborTotalInfo.getTotalHours() != null)
                  annotationLaborHours = annotationLaborTotalInfo
                      .getTotalHours().doubleValue();
                else
                  annotationLaborHours = 0;

                if (annotationLaborTotalInfo.isSetTotalAmt()
                    && annotationLaborTotalInfo.getTotalAmt() != null)
                  annotationLaborSubTotal = annotationLaborTotalInfo
                      .getTotalAmt().doubleValue();
                else
                  annotationLaborSubTotal = 0;

                deltaLaborHours = annotationLaborHours - bmsLaborHours;
                deltaLaborTotal = annotationLaborSubTotal - bmsLaborSubTotal;

                if (annotationLaborTotalDesc.equalsIgnoreCase(laborPartValue)
                    && ((deltaLaborRate != 0 || deltaLaborTotal != 0 || deltaLaborHours != 0))) {

                  //Add the supp LaborSubTotal LaborLine Info
                  supplementLaborLine = suppRequestDocument
                      .getSupplementRequest().getLaborSubTotals()
                      .addNewLaborLine();
                  supplementLaborLine.setType(annotationLaborTotalDesc);

                  if (bmsLaborHours != annotationLaborHours) {
                    supplementHourInfo = supplementLaborLine.addNewHours();
                    supplementHourInfo
                        .setHour(formatToTwoDecimalNumber(annotationLaborHours));
                    deltaLaborHours = annotationLaborHours - bmsLaborHours;
                    supplementHourInfo
                        .setHourDelta(formatToTwoDecimalNumber(deltaLaborHours));
                    supplementHourInfo
                        .setPreviousHour(formatToTwoDecimalNumber(bmsLaborHours));
                    supplementLaborLine.setHours(supplementHourInfo);
                  } else {
                    supplementHourInfo = supplementLaborLine.addNewHours();
                    supplementHourInfo
                        .setHour(formatToTwoDecimalNumber(bmsLaborHours));
                    supplementLaborLine.setHours(supplementHourInfo);
                  }

                  if (bmsLaborSubTotal != annotationLaborSubTotal) {
                    supplementLaborTotal = supplementLaborLine.addNewTotal();
                    supplementLaborTotal
                        .setTotal(formatNumber(annotationLaborSubTotal));
                    supplementLaborTotal
                        .setPreviousTotal(formatNumber(bmsLaborSubTotal));
                    deltaLaborTotal = annotationLaborSubTotal
                        - bmsLaborSubTotal;
                    supplementLaborTotal
                        .setTotalDelta(formatNumber(deltaLaborTotal));
                    supplementLaborLine.setTotal(supplementLaborTotal);
                  } else {
                    supplementLaborTotal = supplementLaborLine.addNewTotal();
                    supplementLaborTotal
                        .setTotal(formatNumber(bmsLaborSubTotal));
                    supplementLaborLine.setTotal(supplementLaborTotal);
                  }

                  if (bmsLaborRate != annotationLaborRate
                      && deltaLaborRate != 0) {
                    supplementLaborRateInfo = supplementLaborLine.addNewRate();
                    supplementLaborRateInfo
                        .setRate(formatNumber(annotationLaborRate));
                    supplementLaborRateInfo
                        .setPreviousRate(formatNumber(bmsLaborRate));
                    supplementLaborRateInfo
                        .setRateDelta(formatNumber(deltaLaborRate));
                    supplementLaborLine.setRate(supplementLaborRateInfo);
                  } else {
                    supplementLaborRateInfo = supplementLaborLine.addNewRate();
                    supplementLaborRateInfo.setRate(formatNumber(bmsLaborRate));
                    supplementLaborLine.setRate(supplementLaborRateInfo);
                  }

                }//End of if annotationLaborTotalDesc
              }//End of for k
            }//End of null check bmsPartsSubTotalInfoArray
          }//End of if check for Current Claim
        }//End of for b
      }//End of null check bmsRepairTotalsInfoArray
    }//End of for a

  }//End of populateLaborSubTotalForPartValue

  /**
   * 
   * Populate UltraMate Labor Subtotal section from Annotation XML
   * 
   */
  private void populateLaborSubTotalForDataChangeMIE(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfoArray[] = annotationRepairTotalsInfoLine
        .getLaborTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfo = null;

    com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborTotalsType supplementLaborSubTotal = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementLaborTotal = null;
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfoArray[] = bmsRepairTotalsInfoLine
        .getLaborTotalsInfoArray();
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfo = null;

    double bmsLaborSubTotal = 0;
    double annotationLaborSubTotal = 0;

    String bmsLaborTotalType = null;
    if (bmsLaborSubTotalInfoArray != null
        && annotationLaborTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      supplementLaborSubTotal = suppRequestDocument.getSupplementRequest()
          .addNewLaborSubTotals();

      for (int j = 0; j < bmsLaborSubTotalInfoArray.length; j++) {

        bmsLaborSubTotalInfo = bmsLaborSubTotalInfoArray[j];

        if (bmsLaborSubTotalInfo.getTotalType() != null
            && bmsLaborSubTotalInfo.getTotalType().equalsIgnoreCase("LA")) {
          bmsLaborTotalType = bmsLaborSubTotalInfo.getTotalType();

          if (bmsLaborSubTotalInfo.isSetTotalAmt()
              && bmsLaborSubTotalInfo.getTotalAmt() != null)
            bmsLaborSubTotal = bmsLaborSubTotalInfo.getTotalAmt().doubleValue();
        }

        for (int k = 0; k < annotationLaborTotalInfoArray.length; k++) {
          annotationLaborTotalInfo = annotationLaborTotalInfoArray[k];

          if (bmsLaborTotalType != null
              && bmsLaborTotalType.equals(LABOR_PART_BMS_TYPE_LABOR)
              && annotationLaborTotalInfo.getTotalType() != null
              && annotationLaborTotalInfo.getTotalType().equalsIgnoreCase(
                  LABOR_PART_ANNOT_TYPE_LABOR)) {
            if (annotationLaborTotalInfo.isSetTotalAmt()
                && annotationLaborTotalInfo.getTotalAmt() != null)
              annotationLaborSubTotal = annotationLaborTotalInfo.getTotalAmt()
                  .doubleValue();

            if (bmsLaborSubTotal != annotationLaborSubTotal) {
              supplementLaborTotal = supplementLaborSubTotal.addNewLaborCost();
              supplementLaborTotal
                  .setTotal(formatNumber(annotationLaborSubTotal));
              supplementLaborTotal
                  .setPreviousTotal(formatNumber(bmsLaborSubTotal));
              supplementLaborTotal
                  .setTotalDelta(formatNumber(annotationLaborSubTotal
                      - bmsLaborSubTotal));
              supplementLaborSubTotal.setLaborCost(supplementLaborTotal);
            } else {
              supplementLaborTotal = supplementLaborSubTotal.addNewLaborCost();
              supplementLaborTotal
                  .setTotal(formatNumber(annotationLaborSubTotal));
              supplementLaborSubTotal.setLaborCost(supplementLaborTotal);
            }
          }//LABOR TOTAL

        }//End of for k
      }//End of for j
    }//End of null check bmsPartsSubTotalInfoArray    
  }//End of populateLaborSubTotalForDataChangeMIE

  /**
   * 
   * Populate Labor Subtotal section from Annotation XML
   * 
   */
  private void populateLaborSubTotalForDataChangeCCC(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfoArray[] = annotationRepairTotalsInfoLine
        .getLaborTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationLaborTotalInfo = null;

    logger.info("annotationLaborTotalInfoArray::::"+annotationLaborTotalInfoArray);
    
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborTotalsType supplementLaborSubTotal = null;
    com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementLaborTotal = null;

    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfoArray[] = bmsRepairTotalsInfoLine
        .getLaborTotalsInfoArray();
    com.cieca.bms.TotalsInfoType bmsLaborSubTotalInfo = null;

    double bmsLaborSubTotal = 0;
    double annotationLaborSubTotal = 0;

    // FIXME: Add check for null point access to bmsLaborTotalDesc
    String bmsLaborTotalDesc = null;
    String annotationLaborTotalDesc = null;

    if (bmsLaborSubTotalInfoArray != null
        && annotationLaborTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      supplementLaborSubTotal = suppRequestDocument.getSupplementRequest()
          .addNewLaborSubTotals();

      for (int j = 0; j < bmsLaborSubTotalInfoArray.length; j++) {

        bmsLaborSubTotalInfo = bmsLaborSubTotalInfoArray[j];

        if (bmsLaborSubTotalInfo.getTotalTypeDesc() != null)
          bmsLaborTotalDesc = bmsLaborSubTotalInfo.getTotalTypeDesc();

        if (bmsLaborSubTotalInfo.isSetTotalAmt()
            && bmsLaborSubTotalInfo.getTotalAmt() != null)
          bmsLaborSubTotal = bmsLaborSubTotalInfo.getTotalAmt().doubleValue();

        for (int k = 0; k < annotationLaborTotalInfoArray.length; k++) {
          annotationLaborTotalInfo = annotationLaborTotalInfoArray[k];

          if (annotationLaborTotalInfo.getTotalTypeDesc() != null)
            annotationLaborTotalDesc = annotationLaborTotalInfo
                .getTotalTypeDesc();

          if (annotationLaborTotalInfo.isSetTotalAmt()
              && annotationLaborTotalInfo.getTotalAmt() != null
              && bmsLaborTotalDesc.equals(annotationLaborTotalDesc))
            annotationLaborSubTotal = annotationLaborTotalInfo.getTotalAmt()
                .doubleValue();

          if ((bmsLaborTotalDesc != null && annotationLaborTotalDesc != null)
              && (bmsLaborTotalDesc.equals(annotationLaborTotalDesc))
              && annotationLaborTotalDesc.equals("Labor - Total")
              && (bmsLaborSubTotal != annotationLaborSubTotal)) {
            supplementLaborTotal = supplementLaborSubTotal.addNewLaborCost();
            supplementLaborTotal
                .setTotal(formatNumber(annotationLaborSubTotal));
            supplementLaborTotal
                .setPreviousTotal(formatNumber(bmsLaborSubTotal));
            supplementLaborTotal
                .setTotalDelta(formatNumber(annotationLaborSubTotal
                    - bmsLaborSubTotal));
            supplementLaborSubTotal.setLaborCost(supplementLaborTotal);
          } else if ((bmsLaborTotalDesc != null && annotationLaborTotalDesc != null)
              && (bmsLaborTotalDesc.equals(annotationLaborTotalDesc))
              && annotationLaborTotalDesc.equals("Labor - Total")) {
            supplementLaborTotal = supplementLaborSubTotal.addNewLaborCost();
            supplementLaborTotal
                .setTotal(formatNumber(annotationLaborSubTotal));
            supplementLaborSubTotal.setLaborCost(supplementLaborTotal);
          }

        }//End of for k
      }//End of for j
    }//End of null check bmsPartsSubTotalInfoArray    
  }//End of populateLaborSubTotalForDataChangeCCC

  /**
   * 
   * Populate UltaMate Parts Subtotal section from Annotation XML
   * 
   */
  private void populatePartsSubTotalForDataChangeMIE(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {
    com.mitchell.schemas.estimate.TotalsInfoType annotationPartsTotalInfoArray[] = annotationRepairTotalsInfoLine
        .getPartsTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationPartsTotalInfoLine = null;
    com.mitchell.schemas.estimate.TotalsInfoType.TotalAdjustmentInfo annotationTotalAdjustmentArray[] = null;
    com.cieca.bms.TotalsInfoType bmsPartsSubTotalInfoArray[] = null;
    com.cieca.bms.TotalsInfoType.TotalTaxInfo bmsPartsTaxInfoArray[] = null;
    com.cieca.bms.TotalsInfoType bmsPartsSubTotalInfoLine = null;
    com.cieca.bms.TotalsInfoType.TotalAdjustmentInfo bmsTotalAdjustmentArray[] = null;
    double bmsPartsSubTotal = 0;
    String bmsPartsTotalDesc = null;

    double bmsTaxableParts = 0;
    double bmsAdjustmentParts = 0;
    double bmsGlassTaxAdjustment = 0;
    double bmsNonTaxableGlassAdjustment = 0;
    double bmsSalesTaxTotal = 0;
    double bmsNonTaxableParts = 0;
    double bmsNonTaxablePartsAdjustments = 0;

    double bmsPartPercentageRate = 0;
    double bmsGlassPercentageRate = 0;

    double annotationPartsSubTotal = 0;
    String annotationPartsTotalDesc = null;

    double annotationTaxableParts = 0;
    double annotationAdjustmentParts = 0;
    double annotationGlassTaxAdjustment = 0;
    double annotationNonTaxableGlassAdjustment = 0;
    double annotationNonTaxableParts = 0;
    double annotationNonTaxablePartsAdjustments = 0;
    double annotationSalesTaxTotal = 0;
    double bmsGstSalesTaxTotal = 0;
    double annotationGstSalesTaxTotal = 0;
    double bmsGstNonTaxableTotal = 0;
    double annotationGstNonTaxableTotal = 0;

    bmsPartsSubTotalInfoArray = bmsRepairTotalsInfoLine
        .getPartsTotalsInfoArray();
    if (bmsPartsSubTotalInfoArray != null
        && annotationPartsTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementPartsSubTotal = suppRequestDocument
          .getSupplementRequest().addNewPartsSubTotals();
      com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType partSimpleItem = null;

      for (int j = 0; j < bmsPartsSubTotalInfoArray.length; j++) {
        bmsPartsSubTotalInfoLine = bmsPartsSubTotalInfoArray[j];

        if (bmsPartsSubTotalInfoLine.getTotalTypeDesc() != null)
          bmsPartsTotalDesc = bmsPartsSubTotalInfoLine.getTotalTypeDesc();

        if (bmsPartsTotalDesc != null && bmsPartsTotalDesc.equals("Parts")
            && bmsPartsSubTotalInfoLine.getTotalAmt() != null)
          bmsPartsSubTotal = bmsPartsSubTotalInfoLine.getTotalAmt()
              .doubleValue();

        if (bmsPartsTotalDesc != null && bmsPartsTotalDesc.equals("Parts")
            && bmsPartsSubTotalInfoLine.getTaxableAmt() != null)
          bmsTaxableParts = bmsPartsSubTotalInfoLine.getTaxableAmt()
              .doubleValue();

        if (bmsPartsTotalDesc != null && bmsPartsTotalDesc.equals("Parts")
            && bmsPartsSubTotalInfoLine.getTotalTaxInfoArray() != null) {
          bmsPartsTaxInfoArray = bmsPartsSubTotalInfoLine
              .getTotalTaxInfoArray();
          for (int i = 0; i < bmsPartsTaxInfoArray.length; i++) {
            if (i == 0) {
              if (bmsPartsTaxInfoArray[i].getTaxAmt() != null)
                bmsSalesTaxTotal = bmsPartsTaxInfoArray[i].getTaxAmt()
                    .doubleValue();
            }
            if (GS.equalsIgnoreCase(bmsPartsTaxInfoArray[i].getTaxType())) {
            	bmsGstSalesTaxTotal = bmsPartsTaxInfoArray[i].getTaxAmt()
                        .doubleValue();
            }
            
          }
        }

        if (bmsPartsSubTotalInfoLine.getTotalAdjustmentInfoArray() != null) {
          bmsTotalAdjustmentArray = bmsPartsSubTotalInfoLine
              .getTotalAdjustmentInfoArray();
          for (int i = 0; i < bmsTotalAdjustmentArray.length; i++) {
            if (bmsPartsTotalDesc != null
                && bmsPartsTotalDesc.equals("Parts")
                && bmsTotalAdjustmentArray[i].getAdjustmentType() != null
                && bmsTotalAdjustmentArray[i].getAdjustmentType()
                    .equalsIgnoreCase("Adjustment")) {
              if (bmsTotalAdjustmentArray[i].getTaxAdjustmentAmt() != null)
                bmsAdjustmentParts = bmsTotalAdjustmentArray[i]
                    .getTaxAdjustmentAmt().doubleValue();
              if (bmsTotalAdjustmentArray[i].getNonTaxAdjustmentAmt() != null)
                bmsNonTaxablePartsAdjustments = bmsTotalAdjustmentArray[i]
                    .getNonTaxAdjustmentAmt().doubleValue();
            }
            if (bmsPartsTotalDesc != null
                && bmsPartsTotalDesc.equals("Glass Discount")
                && bmsTotalAdjustmentArray[i].getAdjustmentType() != null
                && bmsTotalAdjustmentArray[i].getAdjustmentType()
                    .equalsIgnoreCase("Discount")) {
              if (bmsTotalAdjustmentArray[i].getTaxAdjustmentAmt() != null)
                bmsGlassTaxAdjustment = bmsTotalAdjustmentArray[i]
                    .getTaxAdjustmentAmt().doubleValue();
              if (bmsTotalAdjustmentArray[i].getNonTaxAdjustmentAmt() != null)
                bmsNonTaxableGlassAdjustment = bmsTotalAdjustmentArray[i]
                    .getNonTaxAdjustmentAmt().doubleValue();
            }
          }
        }

        if (bmsPartsTotalDesc != null && bmsPartsTotalDesc.equals("Parts")
            && bmsPartsSubTotalInfoLine.getNonTaxableAmt() != null)
          bmsNonTaxableParts = bmsPartsSubTotalInfoLine.getNonTaxableAmt()
              .doubleValue();
        
        if (bmsPartsTotalDesc != null && bmsPartsTotalDesc.equals("Non Taxabale Parts GST")
                && bmsPartsSubTotalInfoLine.getTotalTaxInfoArray() != null) {
              bmsPartsTaxInfoArray = bmsPartsSubTotalInfoLine
                  .getTotalTaxInfoArray();
              if (bmsPartsTaxInfoArray != null) {
	              for (int i = 0; i < bmsPartsTaxInfoArray.length; i++) {
	                if (GS.equalsIgnoreCase(bmsPartsTaxInfoArray[i].getTaxType())) {
	                	bmsGstNonTaxableTotal = bmsPartsTaxInfoArray[i].getTaxAmt()
	                            .doubleValue();
	                }
	              }
              }
         }
        
      }//End of for j

      for (int k = 0; k < annotationPartsTotalInfoArray.length; k++) {
        annotationPartsTotalInfoLine = annotationPartsTotalInfoArray[k];

        if (annotationPartsTotalInfoLine.getTotalTypeDesc() != null
            && annotationPartsTotalInfoLine.getTotalTypeDesc()
                .equalsIgnoreCase("Parts"))
          annotationPartsTotalDesc = annotationPartsTotalInfoLine
              .getTotalTypeDesc();

        if (annotationPartsTotalInfoLine.isSetTotalAmt()
            && annotationPartsTotalInfoLine.getTotalAmt() != null)
          annotationPartsSubTotal = annotationPartsTotalInfoLine.getTotalAmt()
              .doubleValue();

        if (annotationPartsTotalInfoLine.isSetTaxableAmt()
            && annotationPartsTotalInfoLine.getTaxableAmt() != null)
          annotationTaxableParts = annotationPartsTotalInfoLine.getTaxableAmt()
              .doubleValue();

        if (annotationPartsTotalInfoLine.isSetTaxTotalAmt()
            && annotationPartsTotalInfoLine.getTaxTotalAmt() != null)
          annotationSalesTaxTotal = annotationPartsTotalInfoLine
              .getTaxTotalAmt().doubleValue();
        
        if (annotationPartsTotalInfoLine.isSetGSTTaxableTotalAmt()
                && annotationPartsTotalInfoLine.getGSTTaxableTotalAmt() != null)
              annotationGstSalesTaxTotal = annotationPartsTotalInfoLine
                  .getGSTTaxableTotalAmt().doubleValue();

        if (annotationPartsTotalInfoLine.getTotalAdjustmentInfoArray() != null) {
          annotationTotalAdjustmentArray = annotationPartsTotalInfoLine
              .getTotalAdjustmentInfoArray();
          for (int i = 0; i < annotationTotalAdjustmentArray.length; i++) {
            if (annotationTotalAdjustmentArray[i].getAdjustmentType() != null
                && annotationTotalAdjustmentArray[i].getAdjustmentType()
                    .equalsIgnoreCase("PartsAdjustments")) {
              if (annotationTotalAdjustmentArray[i].isSetTaxAdjustmentAmt()
                  && annotationTotalAdjustmentArray[i].getTaxAdjustmentAmt() != null)
                annotationAdjustmentParts = annotationTotalAdjustmentArray[i]
                    .getTaxAdjustmentAmt().doubleValue();

              if (annotationTotalAdjustmentArray[i].isSetNonTaxAdjustmentAmt()
                  && annotationTotalAdjustmentArray[i].getNonTaxAdjustmentAmt() != null)
                annotationNonTaxablePartsAdjustments = annotationTotalAdjustmentArray[i]
                    .getNonTaxAdjustmentAmt().doubleValue();
            }
            if (annotationTotalAdjustmentArray[i].getAdjustmentType() != null
                && annotationTotalAdjustmentArray[i].getAdjustmentType()
                    .equalsIgnoreCase("GlassAdjustments")) {
              if (annotationTotalAdjustmentArray[i].isSetTaxAdjustmentAmt()
                  && annotationTotalAdjustmentArray[i].getTaxAdjustmentAmt() != null)
                annotationGlassTaxAdjustment = annotationTotalAdjustmentArray[i]
                    .getTaxAdjustmentAmt().doubleValue();

              if (annotationTotalAdjustmentArray[i].isSetNonTaxAdjustmentAmt()
                  && annotationTotalAdjustmentArray[i].getNonTaxAdjustmentAmt() != null)
                annotationNonTaxableGlassAdjustment = annotationTotalAdjustmentArray[i]
                    .getNonTaxAdjustmentAmt().doubleValue();
            }
          }
        }

        if (annotationPartsTotalInfoLine.getNonTaxableAmt() != null)
          annotationNonTaxableParts = annotationPartsTotalInfoLine
              .getNonTaxableAmt().doubleValue();
        
        if (annotationPartsTotalInfoLine.getGSTNonTaxableTotalAmt() != null)
            annotationGstNonTaxableTotal = annotationPartsTotalInfoLine
                .getGSTNonTaxableTotalAmt().doubleValue();

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsPartsSubTotal != annotationPartsSubTotal)) {
          supplementPartsSubTotal
              .setTotal(formatNumber(annotationPartsSubTotal));
          supplementPartsSubTotal
              .setPreviousTotal(formatNumber(bmsPartsSubTotal));
          supplementPartsSubTotal
              .setTotalDelta(formatNumber(annotationPartsSubTotal
                  - bmsPartsSubTotal));
        }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsTaxableParts != annotationTaxableParts)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Taxable Parts");
          partSimpleItem.setValue(formatNumber(annotationTaxableParts));
          partSimpleItem.setPreviousValue(formatNumber(bmsTaxableParts));
          partSimpleItem.setValueDelta(formatNumber(annotationTaxableParts
              - bmsTaxableParts));
        }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsAdjustmentParts != annotationAdjustmentParts)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Parts Adjustments");
          partSimpleItem.setValue(formatNumber(annotationAdjustmentParts));
          partSimpleItem.setPreviousValue(formatNumber(bmsAdjustmentParts));
          partSimpleItem.setValueDelta(formatNumber(annotationAdjustmentParts
              - bmsAdjustmentParts));
        }

        
        logger.info("laborRateArrayLines"+laborRateArrayLines);
        for (int x = 0; x < laborRateArrayLines.size(); x++) {
          
        
          Hashtable<String, String> laborValues = laborRateArrayLines.get(x);
          logger.info("laborValues"+laborValues);
        
          if(laborValues.get("TYPE") != null){
          //String s = laborValues.get("TYPE");
          if (LABOR_PART_PARTS.equalsIgnoreCase(laborValues.get("TYPE")
              .toString())) {
            if (laborValues.get("PERCENTAGE") != null)
              bmsPartPercentageRate = new Double(laborValues.get("PERCENTAGE")
                  .toString()).doubleValue();
          }
          if (LABOR_PART_GLASS_ADJUSTMENTS.equalsIgnoreCase(laborValues.get(
              "TYPE").toString())) {
            if (laborValues.get("PERCENTAGE") != null)
              bmsGlassPercentageRate = new Double(laborValues.get("PERCENTAGE")
                  .toString()).doubleValue();
          }
          }
          
        }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsGlassTaxAdjustment != annotationGlassTaxAdjustment)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Glass Adjustments");
          partSimpleItem.setValue(formatNumber(annotationGlassTaxAdjustment));
          partSimpleItem.setPreviousValue(formatNumber(bmsGlassTaxAdjustment));
          partSimpleItem
              .setValueDelta(formatNumber(annotationGlassTaxAdjustment
                  - bmsGlassTaxAdjustment));
          if (bmsGlassPercentageRate != 0.0 && bmsGlassPercentageRate != 0)
            partSimpleItem.setRatePercentage(bmsGlassPercentageRate + "%");
        }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsSalesTaxTotal != annotationSalesTaxTotal)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Sales Tax");
          partSimpleItem.setValue(formatNumber(annotationSalesTaxTotal));
          partSimpleItem.setPreviousValue(formatNumber(bmsSalesTaxTotal));
          partSimpleItem.setValueDelta(formatNumber(annotationSalesTaxTotal
              - bmsSalesTaxTotal));
          if (bmsPartPercentageRate != 0.0 && bmsPartPercentageRate != 0)
            partSimpleItem.setRatePercentage(bmsPartPercentageRate + "%");
        }/*
          * else if(annotationSalesTaxTotal != 0){
          * partSimpleItem = supplementPartsSubTotal.addNewItem();
          * partSimpleItem.setName("Sales Tax");
          * partSimpleItem.setValue(formatNumber(annotationSalesTaxTotal));
          * if(bmsPartPercentageRate != 0.0 && bmsPartPercentageRate != 0)
          * partSimpleItem.setRatePercentage(bmsPartPercentageRate+"%");
          * }
          */
        
        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
                && (bmsGstSalesTaxTotal != annotationGstSalesTaxTotal)) {
              partSimpleItem = supplementPartsSubTotal.addNewItem();
              if (annotationRepairTotalsInfoLine.getGSTTaxableIndicator()) {
                  partSimpleItem.setName(GST_I_TAX);
                  } else { 
                	  partSimpleItem.setName(GST_E_TAX);
              }
              partSimpleItem.setValue(formatNumber(annotationGstSalesTaxTotal));
              partSimpleItem.setPreviousValue(formatNumber(bmsGstSalesTaxTotal));
              partSimpleItem.setValueDelta(formatNumber(annotationGstSalesTaxTotal
                  - bmsGstSalesTaxTotal));
              double gstTaxRate = getGSTTaxRate();
              if (gstTaxRate != 0
                      && gstTaxRate != 0.00) {
            	  partSimpleItem
                  .setRatePercentage(gstTaxRate + "%");
              }
              
         }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsNonTaxableParts != annotationNonTaxableParts)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Non-Taxable Parts");
          partSimpleItem.setValue(formatNumber(annotationNonTaxableParts));
          partSimpleItem.setPreviousValue(formatNumber(bmsNonTaxableParts));
          partSimpleItem.setValueDelta(formatNumber(annotationNonTaxableParts
              - bmsNonTaxableParts));
        }
        
        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
                && (bmsGstNonTaxableTotal != annotationGstNonTaxableTotal)) {
              partSimpleItem = supplementPartsSubTotal.addNewItem();
              if (annotationRepairTotalsInfoLine.getGSTTaxableIndicator()) {
              partSimpleItem.setName(GST_I_TAX);
              } else { 
            	  partSimpleItem.setName(GST_E_TAX);
              }
              partSimpleItem.setValue(formatNumber(annotationGstNonTaxableTotal));
              partSimpleItem.setPreviousValue(formatNumber(bmsGstNonTaxableTotal));
              partSimpleItem.setValueDelta(formatNumber(annotationGstNonTaxableTotal
                  - bmsGstNonTaxableTotal));
              double gstTaxRate = getGSTTaxRate();
              if (gstTaxRate != 0
                      && gstTaxRate != 0.00) {
            	  partSimpleItem
                  .setRatePercentage(gstTaxRate + "%");
              }
            }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsNonTaxablePartsAdjustments != annotationNonTaxablePartsAdjustments)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Parts Adjustments");
          partSimpleItem
              .setValue(formatNumber(annotationNonTaxablePartsAdjustments));
          partSimpleItem
              .setPreviousValue(formatNumber(bmsNonTaxablePartsAdjustments));
          partSimpleItem
              .setValueDelta(formatNumber(annotationNonTaxablePartsAdjustments
                  - bmsNonTaxablePartsAdjustments));
        }

        if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
            && (bmsNonTaxableGlassAdjustment != annotationNonTaxableGlassAdjustment)) {
          partSimpleItem = supplementPartsSubTotal.addNewItem();
          partSimpleItem.setName("Glass Adjustments");
          partSimpleItem
              .setValue(formatNumber(annotationNonTaxableGlassAdjustment));
          partSimpleItem
              .setPreviousValue(formatNumber(bmsNonTaxableGlassAdjustment));
          partSimpleItem
              .setValueDelta(formatNumber(annotationNonTaxableGlassAdjustment
                  - bmsNonTaxableGlassAdjustment));
          if (bmsGlassPercentageRate != 0.0 && bmsGlassPercentageRate != 0)
            partSimpleItem.setRatePercentage(bmsGlassPercentageRate + "%");
        }

      }//End of for k
    }//End of null check bmsPartsSubTotalInfoArray
  }//End of populatePartsSubTotalForDataChangeMIE

  /**
   * 
   * Populate Parts Subtotal section from Annotation XML
   * 
   */
  private void populatePartsSubTotalForDataChangeCCC(
      com.mitchell.schemas.estimate.RepairTotalsInfoType annotationRepairTotalsInfoLine,
      com.cieca.bms.RepairTotalsInfoType bmsRepairTotalsInfoLine)
  {
    com.mitchell.schemas.estimate.TotalsInfoType annotationPartsTotalInfoArray[] = annotationRepairTotalsInfoLine
        .getPartsTotalsInfoArray();
    com.mitchell.schemas.estimate.TotalsInfoType annotationPartsTotalInfoLine = null;

    com.cieca.bms.TotalsInfoType bmsPartsSubTotalInfoArray[] = null;
    com.cieca.bms.TotalsInfoType bmsPartsSubTotalInfoLine = null;

    double bmsPartsSubTotal = 0;
    String bmsPartsTotalDesc = null;
    double annotationPartsSubTotal = 0;
    String annotationPartsTotalDesc = null;

    bmsPartsSubTotalInfoArray = bmsRepairTotalsInfoLine
        .getPartsTotalsInfoArray();
    if (bmsPartsSubTotalInfoArray != null
        && annotationPartsTotalInfoArray != null) {
      //Add the supp PartsSubTotal Info
      com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType supplementPartsSubTotal = suppRequestDocument
          .getSupplementRequest().addNewPartsSubTotals();

      for (int j = 0; j < bmsPartsSubTotalInfoArray.length; j++) {
        bmsPartsSubTotalInfoLine = bmsPartsSubTotalInfoArray[j];

        if (bmsPartsSubTotalInfoLine.getTotalTypeDesc() != null
            && bmsPartsSubTotalInfoLine.getTotalTypeDesc().equalsIgnoreCase(
                "Parts"))
          bmsPartsTotalDesc = bmsPartsSubTotalInfoLine.getTotalTypeDesc();

        if (bmsPartsSubTotalInfoLine.getTotalAmt() != null)
          bmsPartsSubTotal = bmsPartsSubTotalInfoLine.getTotalAmt()
              .doubleValue();

        for (int k = 0; k < annotationPartsTotalInfoArray.length; k++) {
          annotationPartsTotalInfoLine = annotationPartsTotalInfoArray[k];

          if (annotationPartsTotalInfoLine.getTotalTypeDesc() != null
              && annotationPartsTotalInfoLine.getTotalTypeDesc()
                  .equalsIgnoreCase("Parts"))
            annotationPartsTotalDesc = annotationPartsTotalInfoLine
                .getTotalTypeDesc();

          if (annotationPartsTotalInfoLine.getTotalAmt() != null)
            annotationPartsSubTotal = annotationPartsTotalInfoLine
                .getTotalAmt().doubleValue();

          if ((bmsPartsTotalDesc != null && annotationPartsTotalDesc != null)
              && (bmsPartsSubTotal != annotationPartsSubTotal)) {
            supplementPartsSubTotal
                .setTotal(formatNumber(annotationPartsSubTotal));
            supplementPartsSubTotal
                .setPreviousTotal(formatNumber(bmsPartsSubTotal));
            supplementPartsSubTotal
                .setTotalDelta(formatNumber(annotationPartsSubTotal
                    - bmsPartsSubTotal));
          } else {
            supplementPartsSubTotal
                .setTotal(formatNumber(annotationPartsSubTotal));
          }
        }//End of for k
      }//End of for j
    }//End of null check bmsPartsSubTotalInfoArray
  }

  
  
  private void getGTMLaborPartRateValue(String BMSLaborType,
	      String annotLaborType, String annotLaborDesc,
	      com.mitchell.schemas.estimate.RateInfoType[] annotationProfileRateLines,
	      ArrayList<Hashtable<String, String>> laborRateArrayLines){


	    com.mitchell.schemas.estimate.RateInfoType annotationProcessRateInfoLine = null;
	    Hashtable<String, String> laborRateInfo = new Hashtable<String, String>();
	    boolean isLaborPartFound = false;
	    double defaultValue = 0;
	    //com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo =   suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();

	    double bmsRate = 0;
	    double bmsPercentage = 0.0;
	    // FIXME: Add check for null point access to annotationRateType
	    String annotationRateType = null;
	    double annotationRate = 0;

	    for (int a = 0; a < annotationProfileRateLines.length; a++) {
	      annotationProcessRateInfoLine = annotationProfileRateLines[a];
	      com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
	          .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();
	      if (annotationProcessRateInfoLine.getRateType() != null)
	        annotationRateType = annotationProcessRateInfoLine.getRateType();

	      com.mitchell.schemas.estimate.TierInfoType annotationTierInfoArray[] = annotationProcessRateInfoLine
	          .getRateTierInfoArray();
	      com.mitchell.schemas.estimate.TierInfoType annotationTierInfoLine = null;

	      if (bmsRateInfoArray != null) {

	        com.cieca.bms.RateInfoType bmsRateInfoLine = null;
	        String bmsRateType = null;
	        annotationRate = 0;

	        for (int i = 0; i < bmsRateInfoArray.length; i++) {
	          bmsRateInfoLine = bmsRateInfoArray[i];
	          bmsRateType = bmsRateInfoLine.getRateType();

	          if (bmsRateType.equalsIgnoreCase(BMSLaborType)) {
	            com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
	                .getRateTierInfoArray();

	            com.cieca.bms.TierInfoType bmsTierInfoLine = null;

	            com.cieca.bms.TaxInfoType bmsTaxInfoArray[] = null;
	            com.cieca.bms.TierInfoType bmsTaxTierInfoArray[] = null;
	            com.cieca.bms.TaxInfoType bmsTaxInfoLine = null;
	            com.cieca.bms.TierInfoType bmsTaxTierInfoLine = null;
	            com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoArray[] = null;
	            com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoLine = null;
	            if (bmsRateInfoLine.isSetTaxableInd()
	                && (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL) || annotLaborDesc
	                    .equalsIgnoreCase(LABOR_PART_PARTS))) {
	              bmsTaxInfoArray = bmsRateInfoLine.getTaxInfoArray();
	              if (bmsTaxInfoArray != null) {
	                for (int j = 0; j < bmsTaxInfoArray.length; j++) {
	                  bmsTaxInfoLine = bmsTaxInfoArray[j];
	                  bmsTaxTierInfoArray = bmsTaxInfoLine.getTaxTierInfoArray();
	                  if (bmsTaxTierInfoArray != null) {
	                    for (int k = 0; k < bmsTaxTierInfoArray.length; k++) {
	                      bmsTaxTierInfoLine = bmsTaxTierInfoArray[k];
	                      if (bmsTaxTierInfoLine.isSetPercentage()
	                          && bmsTaxTierInfoLine.getPercentage() != null)
	                        bmsPercentage = bmsTaxTierInfoLine.getPercentage()
	                            .doubleValue();
	                    }
	                  }
	                }
	              }
	            }

	            if (BMSLaborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS)) {
	              bmsAdjustmentInfoArray = bmsRateInfoLine.getAdjustmentInfoArray();
	              if (bmsAdjustmentInfoArray != null) {
	                for (int j = 0; j < bmsAdjustmentInfoArray.length; j++) {
	                  bmsAdjustmentInfoLine = bmsAdjustmentInfoArray[j];
	                  if (bmsAdjustmentInfoLine.getAdjustmentType() != null
	                      && bmsAdjustmentInfoLine.getAdjustmentType()
	                          .equalsIgnoreCase("Markup")) {
	                    bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
	                        .doubleValue();
	                  } else if (bmsAdjustmentInfoLine.getAdjustmentType() != null
	                      && bmsAdjustmentInfoLine.getAdjustmentType()
	                          .equalsIgnoreCase("Discount")) {
	                    bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
	                        .doubleValue();
	                    bmsPercentage = -bmsPercentage;
	                  }
	                }
	              }

	            }

	            if (!isLaborPartFound
	                && (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)
	                    || annotLaborDesc.equalsIgnoreCase(LABOR_PART_PARTS) || BMSLaborType
	                    .equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS))) {
	              isLaborPartFound = true;
	              laborRateInfo.put("TYPE", annotLaborDesc);
	              laborRateInfo.put("PERCENTAGE", String.valueOf(bmsPercentage));
	              //laborRateArrayLines.add(laborRateInfo);        
	              break;
	            }

	            for (int j = 0; j < bmsTierInfoArray.length; j++) {
	              if(j == 0) 
	              {
	                bmsTierInfoLine = bmsTierInfoArray[j];
	                if (bmsTierInfoLine.getRate() != null)
	                  bmsRate = bmsTierInfoLine.getRate().doubleValue();

	                if (annotationRateType.equalsIgnoreCase(annotLaborType)) {
	                  
	                  isLaborPartFound = true;   
	               
					
                	  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo1 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
	                  supplementRateInfo1.setRate(formatNumber(-9999999));
	                  supplementRateInfo1.setType(annotLaborDesc);
	                  supplementRateInfo1.setTierNum(String.valueOf(1));
                      supplementRateInfo1.setPreviousRate(formatNumber(-9999999));
                      supplementRateInfo1.setRateDelta(formatNumber(-9999999));

	                  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo2 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
	                  supplementRateInfo2.setRate(formatNumber(-9999999));
	                  supplementRateInfo2.setType(annotLaborDesc);
	                  supplementRateInfo2.setTierNum(String.valueOf(2));
	                  supplementRateInfo2.setPreviousRate(formatNumber(-9999999));
	                  supplementRateInfo2.setRateDelta(formatNumber(-9999999));

	                  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo3 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
	                  supplementRateInfo3.setRate(formatNumber(-9999999));
	                  supplementRateInfo3.setType(annotLaborDesc);
	                  supplementRateInfo3.setTierNum(String.valueOf(3));
	                  supplementRateInfo3.setPreviousRate(formatNumber(-9999999));
	                  supplementRateInfo3.setRateDelta(formatNumber(-9999999));


	                  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType rateInfoArray[] = {supplementRateInfo1,
	                		  																		supplementRateInfo2,supplementRateInfo3};
	                  
	                  

	                  
	                  for (int k = 0; k < annotationTierInfoArray.length; k++) {
	                
	                      annotationTierInfoLine = annotationTierInfoArray[k];
	                      com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = rateInfoArray[annotationTierInfoLine.getTierNum()-1];
	                      //annotationTierNum = annotationTierInfoLine.getTierNum();
	                      if (annotationTierInfoLine.getRate() != null)
	                        annotationRate = annotationTierInfoLine.getRate()
	                            .doubleValue();

	                      laborRateInfo.put("RATE", String.valueOf(annotationRate));
	                      supplementRateInfo.setRate(formatNumber(annotationRate));
	                      supplementRateInfo.setTierNum(String.valueOf(annotationTierInfoLine.getTierNum()));
	                      laborRateInfo.put("TYPE", annotLaborDesc);
	                      supplementRateInfo.setType(annotLaborDesc);

	                      if (bmsRate != annotationRate) {

	                        laborRateInfo.put("PREVIOUSRATE",
	                            String.valueOf(bmsRate));
	                        supplementRateInfo
	                            .setPreviousRate(formatNumber(bmsRate));

	                        laborRateInfo.put("RATEDELTA",
	                            String.valueOf(annotationRate - bmsRate));
	                        supplementRateInfo.setRateDelta(formatNumber(String
	                            .valueOf(annotationRate - bmsRate)));
	                        
	                      }
	                   // }//End of if for k equal
	                  }//End of k
	                }//End of laborPart Match
	              }//End of for j equal
	            }//End of for bmsTierInfoArray
	          }//End of laborPart if condition
	        }//End of bmsRateInfoArray null check
	      }//End of annotationProcessRateInfoLine null check
	      if (!isLaborPartFound
	          && annotationRateType.equalsIgnoreCase(annotLaborType)) {
	    	  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo1 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
              supplementRateInfo1.setRate(formatNumber(-9999999));
              supplementRateInfo1.setType(annotLaborDesc);
              supplementRateInfo1.setTierNum(String.valueOf(1));
              supplementRateInfo1.setPreviousRate(formatNumber(-9999999));
              supplementRateInfo1.setRateDelta(formatNumber(-9999999));

              com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo2 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
              supplementRateInfo2.setRate(formatNumber(-9999999));
              supplementRateInfo2.setType(annotLaborDesc);
              supplementRateInfo2.setTierNum(String.valueOf(2));
              supplementRateInfo2.setPreviousRate(formatNumber(-9999999));
              supplementRateInfo2.setRateDelta(formatNumber(-9999999));

              com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo3 = suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();
              supplementRateInfo3.setRate(formatNumber(-9999999));
              supplementRateInfo3.setType(annotLaborDesc);
              supplementRateInfo3.setTierNum(String.valueOf(3));
              supplementRateInfo3.setPreviousRate(formatNumber(-9999999));
              supplementRateInfo3.setRateDelta(formatNumber(-9999999));


              com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType rateInfoArray[] = {supplementRateInfo1,
            		  																		supplementRateInfo2,supplementRateInfo3};
	        //com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = suppRequestDocument
	          //  .getSupplementRequest().getEstimateProfile().addNewRateInfo();
	        isLaborPartFound = true;
	        annotationRate = 0;
	        bmsRate = 0;
	        for (int k = 0; k < annotationTierInfoArray.length; k++) {
	          {
	        	  
	            annotationTierInfoLine = annotationTierInfoArray[k];
	            com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = rateInfoArray[annotationTierInfoLine.getTierNum()-1];
	            //annotationTierNum = annotationTierInfoLine.getTierNum();
	            if (annotationTierInfoLine.getRate() != null)
	              annotationRate = annotationTierInfoLine.getRate().doubleValue();
	            laborRateInfo.put("RATE", String.valueOf(annotationRate));
	            supplementRateInfo.setRate(formatNumber(annotationRate));
	            laborRateInfo.put("TYPE", annotLaborDesc);
	            supplementRateInfo.setType(annotLaborDesc);
	            laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
	            supplementRateInfo.setPreviousRate(formatNumber(bmsRate));
	            laborRateInfo.put("RATEDELTA",
	                String.valueOf(annotationRate - bmsRate));
	            supplementRateInfo.setRateDelta(formatNumber(String
	                .valueOf(annotationRate - bmsRate)));
	          }//End of if for k equal
	        }//End of k                
	      }
	    }//End of for annotationProfileRateLines

	    if (isLaborPartFound) {
	      laborRateArrayLines.add(laborRateInfo);
	    } else {// if(! annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL) && ! annotLaborDesc.equalsIgnoreCase(LABOR_PART_PARTS)){
	      //supplementRateInfo.setRate(formatNumber(bmsRate));
	      //supplementRateInfo.setType(laborPart);
	      laborRateInfo.put("TYPE", annotLaborDesc);
	      laborRateInfo.put("RATE", String.valueOf(defaultValue));
	      laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
	      laborRateInfo.put("RATEDELTA", String.valueOf(defaultValue));
	      laborRateArrayLines.add(laborRateInfo);
	    }
	  
  }
  
  /**
   * 
   * Process each UltraMate ProcessLaborPartRateValue from Annotation XML
   * 
   */
  
  
  private void getUltraMateLaborPartRateValue(String BMSLaborType,
      String annotLaborType, String annotLaborDesc,
      com.mitchell.schemas.estimate.RateInfoType[] annotationProfileRateLines,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {

    com.mitchell.schemas.estimate.RateInfoType annotationProcessRateInfoLine = null;
    Hashtable<String, String> laborRateInfo = new Hashtable<String, String>();
    boolean isLaborPartFound = false;
    double defaultValue = 0;
    //com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo =   suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();

    double bmsRate = 0;
    double bmsPercentage = 0.0;
    // FIXME: Add check for null point access to annotationRateType
    String annotationRateType = null;
    double annotationRate = 0;

    for (int a = 0; a < annotationProfileRateLines.length; a++) {
      annotationProcessRateInfoLine = annotationProfileRateLines[a];
      com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
          .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();
      if (annotationProcessRateInfoLine.getRateType() != null)
        annotationRateType = annotationProcessRateInfoLine.getRateType();

      com.mitchell.schemas.estimate.TierInfoType annotationTierInfoArray[] = annotationProcessRateInfoLine
          .getRateTierInfoArray();
      com.mitchell.schemas.estimate.TierInfoType annotationTierInfoLine = null;

      if (bmsRateInfoArray != null) {

        com.cieca.bms.RateInfoType bmsRateInfoLine = null;
        String bmsRateType = null;
        annotationRate = 0;

        for (int i = 0; i < bmsRateInfoArray.length; i++) {
          bmsRateInfoLine = bmsRateInfoArray[i];
          bmsRateType = bmsRateInfoLine.getRateType();

          if (bmsRateType.equalsIgnoreCase(BMSLaborType)) {
            com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
                .getRateTierInfoArray();

            com.cieca.bms.TierInfoType bmsTierInfoLine = null;

            com.cieca.bms.TaxInfoType bmsTaxInfoArray[] = null;
            com.cieca.bms.TierInfoType bmsTaxTierInfoArray[] = null;
            com.cieca.bms.TaxInfoType bmsTaxInfoLine = null;
            com.cieca.bms.TierInfoType bmsTaxTierInfoLine = null;
            com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoArray[] = null;
            com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoLine = null;
            if (bmsRateInfoLine.isSetTaxableInd()
                && (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL) || annotLaborDesc
                    .equalsIgnoreCase(LABOR_PART_PARTS))) {
              bmsTaxInfoArray = bmsRateInfoLine.getTaxInfoArray();
              if (bmsTaxInfoArray != null) {
                for (int j = 0; j < bmsTaxInfoArray.length; j++) {
                  bmsTaxInfoLine = bmsTaxInfoArray[j];
                  bmsTaxTierInfoArray = bmsTaxInfoLine.getTaxTierInfoArray();
                  if (bmsTaxTierInfoArray != null) {
                    for (int k = 0; k < bmsTaxTierInfoArray.length; k++) {
                      bmsTaxTierInfoLine = bmsTaxTierInfoArray[k];
                      if (bmsTaxTierInfoLine.isSetPercentage()
                          && bmsTaxTierInfoLine.getPercentage() != null)
                        bmsPercentage = bmsTaxTierInfoLine.getPercentage()
                            .doubleValue();
                    }
                  }
                }
              }
            }

            if (BMSLaborType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS)) {
              bmsAdjustmentInfoArray = bmsRateInfoLine.getAdjustmentInfoArray();
              if (bmsAdjustmentInfoArray != null) {
                for (int j = 0; j < bmsAdjustmentInfoArray.length; j++) {
                  bmsAdjustmentInfoLine = bmsAdjustmentInfoArray[j];
                  if (bmsAdjustmentInfoLine.getAdjustmentType() != null
                      && bmsAdjustmentInfoLine.getAdjustmentType()
                          .equalsIgnoreCase("Markup")) {
                    bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
                        .doubleValue();
                  } else if (bmsAdjustmentInfoLine.getAdjustmentType() != null
                      && bmsAdjustmentInfoLine.getAdjustmentType()
                          .equalsIgnoreCase("Discount")) {
                    bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
                        .doubleValue();
                    bmsPercentage = -bmsPercentage;
                  }
                }
              }

            }

            if (!isLaborPartFound
                && (annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL)
                    || annotLaborDesc.equalsIgnoreCase(LABOR_PART_PARTS) || BMSLaborType
                    .equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS))) {
              isLaborPartFound = true;
              laborRateInfo.put("TYPE", annotLaborDesc);
              laborRateInfo.put("PERCENTAGE", String.valueOf(bmsPercentage));
              //laborRateArrayLines.add(laborRateInfo);        
              break;
            }

            for (int j = 0; j < bmsTierInfoArray.length; j++) {
              if (j == 0) {
                bmsTierInfoLine = bmsTierInfoArray[j];
                if (bmsTierInfoLine.getRate() != null)
                  bmsRate = bmsTierInfoLine.getRate().doubleValue();

                if (annotationRateType.equalsIgnoreCase(annotLaborType)) {
                  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = suppRequestDocument
                      .getSupplementRequest().getEstimateProfile()
                      .addNewRateInfo();
                  isLaborPartFound = true;
                  for (int k = 0; k < annotationTierInfoArray.length; k++) {
                    if (k == 0) {

                      annotationTierInfoLine = annotationTierInfoArray[k];
                      //annotationTierNum = annotationTierInfoLine.getTierNum();
                      if (annotationTierInfoLine.getRate() != null)
                        annotationRate = annotationTierInfoLine.getRate()
                            .doubleValue();

                      laborRateInfo.put("RATE", String.valueOf(annotationRate));
                      supplementRateInfo.setRate(formatNumber(annotationRate));

                      laborRateInfo.put("TYPE", annotLaborDesc);
                      supplementRateInfo.setType(annotLaborDesc);

                      if (bmsRate != annotationRate) {

                        laborRateInfo.put("PREVIOUSRATE",
                            String.valueOf(bmsRate));
                        supplementRateInfo
                            .setPreviousRate(formatNumber(bmsRate));

                        laborRateInfo.put("RATEDELTA",
                            String.valueOf(annotationRate - bmsRate));
                        supplementRateInfo.setRateDelta(formatNumber(String
                            .valueOf(annotationRate - bmsRate)));
                      }
                    }//End of if for k equal
                  }//End of k
                }//End of laborPart Match
              }//End of for j equal
            }//End of for bmsTierInfoArray
          }//End of laborPart if condition
        }//End of bmsRateInfoArray null check
      }//End of annotationProcessRateInfoLine null check
      if (!isLaborPartFound
          && annotationRateType.equalsIgnoreCase(annotLaborType)) {
        com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = suppRequestDocument
            .getSupplementRequest().getEstimateProfile().addNewRateInfo();
        isLaborPartFound = true;
        annotationRate = 0;
        bmsRate = 0;
        for (int k = 0; k < annotationTierInfoArray.length; k++) {
          if (k == 0) {
            annotationTierInfoLine = annotationTierInfoArray[k];
            //annotationTierNum = annotationTierInfoLine.getTierNum();
            if (annotationTierInfoLine.getRate() != null)
              annotationRate = annotationTierInfoLine.getRate().doubleValue();
            laborRateInfo.put("RATE", String.valueOf(annotationRate));
            supplementRateInfo.setRate(formatNumber(annotationRate));
            laborRateInfo.put("TYPE", annotLaborDesc);
            supplementRateInfo.setType(annotLaborDesc);
            laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
            supplementRateInfo.setPreviousRate(formatNumber(bmsRate));
            laborRateInfo.put("RATEDELTA",
                String.valueOf(annotationRate - bmsRate));
            supplementRateInfo.setRateDelta(formatNumber(String
                .valueOf(annotationRate - bmsRate)));
          }//End of if for k equal
        }//End of k                
      }
    }//End of for annotationProfileRateLines

    if (isLaborPartFound) {
      laborRateArrayLines.add(laborRateInfo);
    } else {// if(! annotLaborDesc.equalsIgnoreCase(LABOR_TAX_LABEL) && ! annotLaborDesc.equalsIgnoreCase(LABOR_PART_PARTS)){
      //supplementRateInfo.setRate(formatNumber(bmsRate));
      //supplementRateInfo.setType(laborPart);
      laborRateInfo.put("TYPE", annotLaborDesc);
      laborRateInfo.put("RATE", String.valueOf(defaultValue));
      laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
      laborRateInfo.put("RATEDELTA", String.valueOf(defaultValue));
      laborRateArrayLines.add(laborRateInfo);
    }
  }//End of getUltraMateLaborPartRateValue

  /**
   * 
   * Process each ProcessLaborPartRateValue from Annotation XML
   * 
   */
  private void getLaborPartRateValue(String laborPart,
      com.mitchell.schemas.estimate.RateInfoType[] annotationProfileRateLines,
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {

    com.mitchell.schemas.estimate.RateInfoType annotationProcessRateInfoLine = null;
    Hashtable<String, String> laborRateInfo = new Hashtable<String, String>();
    boolean isLaborPartFound = false;
    double defaultValue = 0;
    //com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo =   suppRequestDocument.getSupplementRequest().getEstimateProfile().addNewRateInfo();

    double bmsRate = 0;

    for (int a = 0; a < annotationProfileRateLines.length; a++) {
      annotationProcessRateInfoLine = annotationProfileRateLines[a];
      com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
          .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();

      if (bmsRateInfoArray != null) {

        com.cieca.bms.RateInfoType bmsRateInfoLine = null;
        String bmsRateDesc = null;
        String annotationRateDesc = null;
        double annotationRate = 0;

        for (int i = 0; i < bmsRateInfoArray.length; i++) {
          bmsRateInfoLine = bmsRateInfoArray[i];
          annotationRateDesc = annotationProcessRateInfoLine.getRateDesc();
          bmsRateDesc = bmsRateInfoLine.getRateDesc();

          if (bmsRateDesc.equalsIgnoreCase(laborPart)) {

            com.mitchell.schemas.estimate.TierInfoType annotationTierInfoArray[] = annotationProcessRateInfoLine
                .getRateTierInfoArray();
            com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
                .getRateTierInfoArray();
            com.mitchell.schemas.estimate.TierInfoType annotationTierInfoLine = null;
            com.cieca.bms.TierInfoType bmsTierInfoLine = null;

            for (int j = 0; j < bmsTierInfoArray.length; j++) {
              if (j == 0) {
                bmsTierInfoLine = bmsTierInfoArray[j];
                if (bmsTierInfoLine.getRate() != null)
                  bmsRate = bmsTierInfoLine.getRate().doubleValue();

                if (annotationRateDesc.equalsIgnoreCase(laborPart)) {
                  com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType supplementRateInfo = suppRequestDocument
                      .getSupplementRequest().getEstimateProfile()
                      .addNewRateInfo();
                  isLaborPartFound = true;
                  for (int k = 0; k < annotationTierInfoArray.length; k++) {
                    if (k == 0) {

                      annotationTierInfoLine = annotationTierInfoArray[k];
                      //annotationTierNum = annotationTierInfoLine.getTierNum();
                      if (annotationTierInfoLine.getRate() != null)
                        annotationRate = annotationTierInfoLine.getRate()
                            .doubleValue();

                      laborRateInfo.put("RATE", String.valueOf(annotationRate));
                      supplementRateInfo.setRate(formatNumber(annotationRate));

                      laborRateInfo.put("TYPE", laborPart);
                      supplementRateInfo.setType(laborPart);
                      if (bmsRate != annotationRate) {

                        laborRateInfo.put("PREVIOUSRATE",
                            String.valueOf(bmsRate));
                        supplementRateInfo
                            .setPreviousRate(formatNumber(bmsRate));

                        laborRateInfo.put("RATEDELTA",
                            String.valueOf(annotationRate - bmsRate));
                        supplementRateInfo.setRateDelta(formatNumber(String
                            .valueOf(annotationRate - bmsRate)));
                      }
                    }//End of if for k equal
                  }//End of k
                }//End of laborPart Match
              }//End of for j equal
            }//End of for bmsTierInfoArray
          }//End of laborPart if condition
        }//End of bmsRateInfoArray null check
      }//End of annotationProcessRateInfoLine null check
    }//End of for annotationProfileRateLines

    if (isLaborPartFound) {
      laborRateArrayLines.add(laborRateInfo);
    } else {
      //supplementRateInfo.setRate(formatNumber(bmsRate));
      //supplementRateInfo.setType(laborPart);
      laborRateInfo.put("TYPE", laborPart);
      laborRateInfo.put("RATE", String.valueOf(defaultValue));
      laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
      laborRateInfo.put("RATEDELTA", String.valueOf(defaultValue));
      laborRateArrayLines.add(laborRateInfo);
    }
  }//End of getLaborPartRateValue

  /**
   * 
   * Generate each UltarMate LaborRateArray from BMS XML
   * 
   */
  private void generateBMSLaborRateArrayForUltraMate(
      ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {
    Hashtable<String, String> laborRateInfo = null;
    com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
        .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();
    if (bmsRateInfoArray != null) {

      com.cieca.bms.RateInfoType bmsRateInfoLine = null;
      String bmsRateType = null;

      for (int i = 0; i < bmsRateInfoArray.length; i++) {
        bmsRateInfoLine = bmsRateInfoArray[i];
        bmsRateType = bmsRateInfoLine.getRateType();
        com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
            .getRateTierInfoArray();
        com.cieca.bms.TierInfoType bmsTierInfoLine = null;

        com.cieca.bms.TaxInfoType bmsTaxInfoArray[] = bmsRateInfoLine
            .getTaxInfoArray();
        com.cieca.bms.TaxInfoType bmsTaxInfoLine = null;

        com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoArray[] = null;
        com.cieca.bms.RateInfoType.AdjustmentInfo bmsAdjustmentInfoLine = null;

        if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_BODY)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_BODY);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_BODY_S)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_BDY_S);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_FRAME)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_FRAME);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType
            .equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS_LABOR)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_GLASS);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_MECHANICAL)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_MECHANICAL);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_ELECTRICAL)) {
            laborRateInfo = new Hashtable<String, String>();
            populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
                laborRateInfo, LABOR_PART_ELECTRICAL);
            laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_TRIM)) {
            laborRateInfo = new Hashtable<String, String>();
            populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
                laborRateInfo, LABOR_PART_TRIM);
            laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_REFINISH)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, LABOR_PART_REFINISH);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR)) {
          laborRateInfo = new Hashtable<String, String>();
          populatePercentageInfo(bmsTaxInfoLine, bmsTaxInfoArray,
              laborRateInfo, LABOR_PART_BMS_TYPE_LABOR);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_PARTS)) {
          laborRateInfo = new Hashtable<String, String>();
          populatePercentageInfo(bmsTaxInfoLine, bmsTaxInfoArray,
              laborRateInfo, LABOR_PART_BMS_TYPE_PARTS);
          laborRateArrayLines.add(laborRateInfo);
        } else if (bmsRateType.equalsIgnoreCase(LABOR_PART_BMS_TYPE_GLASS)) {
          laborRateInfo = new Hashtable<String, String>();
          double bmsPercentage = 0.00;
          bmsAdjustmentInfoArray = bmsRateInfoLine.getAdjustmentInfoArray();
          if (bmsAdjustmentInfoArray != null) {
            for (int j = 0; j < bmsAdjustmentInfoArray.length; j++) {
              bmsAdjustmentInfoLine = bmsAdjustmentInfoArray[j];
              if (bmsAdjustmentInfoLine.getAdjustmentType() != null
                  && bmsAdjustmentInfoLine.getAdjustmentType()
                      .equalsIgnoreCase("Markup")) {
                bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
                    .doubleValue();
              } else if (bmsAdjustmentInfoLine.getAdjustmentType() != null
                  && bmsAdjustmentInfoLine.getAdjustmentType()
                      .equalsIgnoreCase("Discount")) {
                bmsPercentage = bmsAdjustmentInfoLine.getAdjustmentPct()
                    .doubleValue();
                bmsPercentage = -bmsPercentage;
              }
            }
          }
          laborRateInfo.put("TYPE", LABOR_PART_GLASS_ADJUSTMENTS);
          laborRateInfo.put("PERCENTAGE", String.valueOf(bmsPercentage));
          laborRateArrayLines.add(laborRateInfo);
        }
      }//End of i
    }

  }

  /**
   * 
   * Populate Labor Rate Information from BMS XML
   * 
   */
  private void generateBMSLaborRateArray(ArrayList<Hashtable<String, String>> laborRateArrayLines)
  {
    Hashtable<String, String> laborRateInfo = null;
    com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
        .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();
    if (bmsRateInfoArray != null) {

      com.cieca.bms.RateInfoType bmsRateInfoLine = null;
      String bmsRateDesc = null;

      for (int i = 0; i < bmsRateInfoArray.length; i++) {
        bmsRateInfoLine = bmsRateInfoArray[i];
        bmsRateDesc = bmsRateInfoLine.getRateDesc();
        com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
            .getRateTierInfoArray();
        com.cieca.bms.TierInfoType bmsTierInfoLine = null;

        if (bmsRateDesc.equalsIgnoreCase(LABOR_PART_BODY)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_DIAGNOSTIC)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_ELECTRICAL)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_FRAME)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_GLASS)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_MECHANICAL)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_TRIM)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_PAINT)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_PDR)
            || bmsRateDesc.equalsIgnoreCase(LABOR_PART_STRUCTURAL)) {
          laborRateInfo = new Hashtable<String, String>();
          populateBMSRateTierInfo(bmsTierInfoLine, bmsTierInfoArray,
              laborRateInfo, bmsRateDesc);
          laborRateArrayLines.add(laborRateInfo);
        }
      }//End of i
    }

  }

  /**
   * 
   * Populate Rate Information from BMS XML
   * 
   */
  private void populateBMSRateTierInfo(
      com.cieca.bms.TierInfoType bmsTierInfoLine,
      com.cieca.bms.TierInfoType bmsTierInfoArray[], Hashtable<String, String> laborRateInfo,
      String laborPart)
  {
    double bmsRate = 0;
    double defaultValue = 0;

    for (int j = 0; j < bmsTierInfoArray.length; j++) {
      if (j == 0) {
        bmsTierInfoLine = bmsTierInfoArray[j];
        if (bmsTierInfoLine.getRate() != null)
          bmsRate = bmsTierInfoLine.getRate().doubleValue();

        laborRateInfo.put("TYPE", laborPart);
        laborRateInfo.put("RATE", String.valueOf(defaultValue));
        laborRateInfo.put("PREVIOUSRATE", String.valueOf(bmsRate));
        laborRateInfo.put("RATEDELTA", String.valueOf(defaultValue));
      }//End of j equal
    }//End of for j                
  }

  /**
   * 
   * Populate Perecentage Info for Labor, Parts from BMS XML
   * 
   */
  private void populatePercentageInfo(com.cieca.bms.TaxInfoType bmsTaxInfoLine,
      com.cieca.bms.TaxInfoType bmsTaxInfoArray[], Hashtable<String, String> laborRateInfo,
      String laborPart)
  {
    double bmsPercentage = 0;
    com.cieca.bms.TierInfoType bmsTaxTierInfoArray[] = null;
    com.cieca.bms.TierInfoType bmsTaxTierInfoLine = null;

    if (bmsTaxInfoArray != null) {
      for (int j = 0; j < bmsTaxInfoArray.length; j++) {
        bmsTaxInfoLine = bmsTaxInfoArray[j];
        bmsTaxTierInfoArray = bmsTaxInfoLine.getTaxTierInfoArray();
        if (bmsTaxTierInfoArray != null) {
          for (int k = 0; k < bmsTaxTierInfoArray.length; k++) {
            bmsTaxTierInfoLine = bmsTaxTierInfoArray[k];
            if (bmsTaxTierInfoLine.isSetPercentage()
                && bmsTaxTierInfoLine.getPercentage() != null)
              bmsPercentage = bmsTaxTierInfoLine.getPercentage().doubleValue();
          }
        }
        if (laborPart.equalsIgnoreCase(LABOR_PART_BMS_TYPE_LABOR))
          laborRateInfo.put("TYPE", LABOR_TAX_LABEL);
        if (laborPart.equalsIgnoreCase(LABOR_PART_BMS_TYPE_PARTS))
          laborRateInfo.put("TYPE", LABOR_PART_PARTS);

        laborRateInfo.put("PERCENTAGE", String.valueOf(bmsPercentage));
      }//End of for j
    }
  }

  /**
   * 
   * Process each DamageLine from Annotation XML
   * 
   */
  private void processDamagedLine(
      com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
      LineItemsInfoType suppRequestlineItems)
  {
	  
    if (annotationDamageLine != null && suppRequestlineItems != null) {

      if (estimateFormat.equalsIgnoreCase("MIE")
          && annotationDamageLine.getLineType() != null
          && !annotationDamageLine.getLineType().equalsIgnoreCase(
              "CurrentClaim")) {
        //Skip for Related Prior Damage
      } else {
        LineItemInfoType suppRequestLineItem = suppRequestlineItems
            .addNewLineItemInfo();

        // add a new line item
        suppRequestLineItem.addNewLineItem();

        // get the LineNum
        com.mitchell.schemas.estimate.LineNumType annotationLineNum = annotationDamageLine
            .getLineNum();
        // set the lineNum
        suppRequestLineItem.getLineItem().setLineNum(
            Integer.parseInt(annotationLineNum.getStringValue()));

        // check if its strike thru'
        if (annotationLineNum.getStrikeThru()) {
          suppRequestLineItem.getLineItem().setStrikeThru(true);
          suppRequestLineItem.getLineItem().setChangeRecommended("Delete"); 
        }

        // added to get the BMSDamagedLines using ArrayList
        ArrayList<DamageLineInfoType> list = getBMSDamagedLines(annotationLineNum.getStringValue(),
            annotationDamageLine.getLineType());

        // isFirstLine
        boolean isFirstLine = true;

        for (int i = 0; i < list.size(); i++) {

          if (i > 0) {
            isFirstLine = false;
          }

          com.cieca.bms.DamageLineInfoType damagedLine = ((com.cieca.bms.DamageLineInfoType) list
              .get(i));

          if (annotationLineNum.getDataChanged()) {
            if (estimateFormat.equalsIgnoreCase("CCC")) {
              populateSuppRequestLineItemForDataChangeCCC(annotationDamageLine,
                  suppRequestLineItem, damagedLine);
            } else if (estimateFormat.equalsIgnoreCase("MIE")) {
              populateSuppRequestLineItemForDataChangeMIE(annotationDamageLine,
                  suppRequestLineItem, damagedLine);
            }else if (estimateFormat.equalsIgnoreCase("GTMOTIVE")) {
                populateSuppRequestLineItemForDataChangeGTM(annotationDamageLine,
                        suppRequestLineItem, damagedLine);
                  } 
            else {
              populateSuppRequestLineItemForDataChange(annotationDamageLine,
                  suppRequestLineItem);
            }
          } else {

            if (estimateFormat.equalsIgnoreCase("MIE")) {
              populateSuppRequestLineItemForNonDataChange(suppRequestLineItem,
                  damagedLine);
            }
            if (estimateFormat.equalsIgnoreCase("GTMOTIVE")) {
                populateSuppRequestLineItemForNonDataChange(suppRequestLineItem,
                    damagedLine);
              }
            if (estimateFormat.equalsIgnoreCase("CCC")) {
              populateSuppRequestLineItemForNonDataChangeCCC(
                  suppRequestLineItem, damagedLine);
            }
            if (estimateFormat.equalsIgnoreCase("AUDATEX")) {
              populateSuppRequestLineItemForNonDataChangeAudatex(
                  suppRequestLineItem, damagedLine, isFirstLine);
            }
          }
        }
        // Populate Compliance Summary
        populateSuppRequestLineItemWithComplianceData(annotationDamageLine,
            suppRequestLineItem, annotationLineNum);
      }
    }
  }

  /**
   * Populate the SuppReqEMail with Compliance data
   */
  private void populateSuppRequestLineItemWithComplianceData(
      com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
      LineItemInfoType suppRequestLineItem,
      com.mitchell.schemas.estimate.LineNumType annotationLineNum)
  {

    if (annotationDamageLine != null && suppRequestLineItem != null
        && annotationLineNum != null) {

      // get the compliance data
      com.mitchell.schemas.compliance.ExceptionType complianceException = getComplianceException(suppRequestLineItem
          .getLineItem().getLineNum() + "");
      if (complianceException != null) {

        // Add the compliance section to the supp request line item
        if (suppRequestLineItem.getCompliance() == null) {
          suppRequestLineItem.addNewCompliance();
        }
        StringBuffer descBuffer = new StringBuffer();

        suppRequestLineItem.getCompliance().setOperation(
            complianceException.getSeverity().toString());

        descBuffer.append("Line ");
        descBuffer.append(complianceException.getLineNumber());
        descBuffer.append("-");
        descBuffer.append(complianceException.getDescription());
        descBuffer.append(";");

        descBuffer.append("Actual: ");
       
        if(complianceException.getActual() != null && complianceException.getActual().getUnit() != null){
        if (complianceException.getActual().getUnit().toString()
            .equals("CURRENCY")) {
          descBuffer.append(currency);
        }
        descBuffer.append(complianceException.getActual().getStringValue());
        descBuffer.append("-");
        }
        descBuffer.append("Threshold: ");
        
        if(complianceException.getGuideline() != null && complianceException.getGuideline().getUnit() != null){
        if (complianceException.getGuideline().getUnit().toString()
            .equals("CURRENCY")) {
          descBuffer.append(currency);
        }
        descBuffer.append(complianceException.getGuideline().getStringValue());
        descBuffer.append("-");
        }
        suppRequestLineItem.getCompliance().setLineDesc(descBuffer.toString());
      }

      // Store the comments in order
      Map<Integer, String[]> commentsMap = new TreeMap<Integer, String[]>();

      // check if the comments are present
      if (annotationLineNum.getHasComments()) {

        OperationLineDescType annotation = null;

        com.mitchell.schemas.estimate.CommentType commentsArray[] = annotationDamageLine
            .getComments().getCommentArray();
        com.mitchell.schemas.estimate.CommentType annotationComment = null;

        for (int j = 0; j < commentsArray.length; j++) {
          annotationComment = commentsArray[j];
          //annotation = suppRequestLineItem.addNewAnnotation();

          String[] mapData = new String[2];

          mapData[0] = BMSMIECodesMapper
              .getExceptionDescription(annotationComment.getExceptionCode()
                  .toString());
          mapData[1] = annotationComment.getStringValue();

          // add it to the map
          commentsMap.put(Integer.valueOf(annotationComment.getCid()), mapData);
        }

        // Get the data from Map
        if (commentsMap.size() > 0) {

          // Add the recommended changes
          if (!suppRequestLineItem.getLineItem().isSetChangeRecommended()) {
            suppRequestLineItem.getLineItem().setChangeRecommended(
                "Comment Only");
          }

          Iterator<Integer> iterator = commentsMap.keySet().iterator();

          while (iterator.hasNext()) {

            Integer key = (Integer) iterator.next();
            String[] data = (String[]) commentsMap.get(key);

            annotation = suppRequestLineItem.addNewAnnotation();

            annotation.setLineDesc(data[0]);
            annotation.setOperation(data[1]);
          }
        }
      }
    }
  }

  /**
   * Populates the SuppReqEmail LineItem from MIE BMS
   * 
   */
  private void populateSuppRequestLineItemForNonDataChange(
      LineItemInfoType suppRequestLineItem,
      com.cieca.bms.DamageLineInfoType bmsDamageLine)
  {

    if (suppRequestLineItem != null && bmsDamageLine != null) {
      // set the operation
      suppRequestLineItem.getLineItem().setOperation(
          getBMSLineOperation(bmsDamageLine));

      // set the description          
      if (bmsDamageLine.isSetLineDesc()) {
        suppRequestLineItem.getLineItem().setLineDesc(
            bmsDamageLine.getLineDesc());
      }

      // set parts related data
      String miePartType = null;
      String bmsPartType = null;
      double partPrice = 0;
      String tempPartPrice = null;
      String tempPartQuantity = null;
      boolean partSubletIndicator = false;
      boolean priceIncludeIndicator = false;
      boolean clearCoatIncludeIndicator = false;
      double laborCost = 0;
      double amount = 0;
      double partQuantity = 0;
      double unitPartPrice = 0;
      String lineType = null;

      if (bmsDamageLine.isSetPartInfo()) {

        suppRequestLineItem.getLineItem().addNewPartInfo();

        // get the bms part type
        bmsPartType = bmsDamageLine.getPartInfo().getPartType();

        // get the part sublet indicator
        if (bmsDamageLine.isSetSubletInfo()
            && bmsDamageLine.getSubletInfo().isSetPartSubletInd()) {
          partSubletIndicator = bmsDamageLine.getSubletInfo()
              .getPartSubletInd();
        }

        // get the MIE part type to display
        miePartType = BMSMIECodesMapper.getPartTypeCode(bmsPartType,
            partSubletIndicator);

        // add part type
        suppRequestLineItem.getLineItem().getPartInfo()
            .setPartType(miePartType);

        /*
         * Get the Part Price
         */
        // get the temp Part Price
        if (bmsDamageLine.getPartInfo().isSetPartPrice()) {
          tempPartPrice = bmsDamageLine.getPartInfo().getPartPrice().toString();
        }

        // get the PriceIncludeIndicator
        if (bmsDamageLine.getPartInfo().isSetPriceInclInd()
            && bmsDamageLine.getPartInfo().getPriceInclInd()) {
          priceIncludeIndicator = true;
        }

        // get labor clear coat indicator
        if (bmsDamageLine.isSetLaborInfo()
            && bmsDamageLine.getLaborInfo().isSetPaintStagesNum()) {

          if (bmsDamageLine.getLaborInfo().getPaintStagesNum() == 2) {
            clearCoatIncludeIndicator = true;
          }
        }

        if (priceIncludeIndicator == false
            && clearCoatIncludeIndicator == false) {
          // check if the tempPartPrice = 0
          if ("0".equals(tempPartPrice) && "0.0".equals(tempPartPrice)) {

            // get the line adjustment amt
            if (bmsDamageLine.isSetLineAdjustment()
                && bmsDamageLine.getLineAdjustment().isSetAdjustmentAmt()) {

              // set the temp part price to adjustment amount
              tempPartPrice = bmsDamageLine.getLineAdjustment()
                  .getAdjustmentAmt().toString();

              // check if the price = 0
              if ("0".equals(tempPartPrice) && "0.0".equals(tempPartPrice)) {

                // get the other charges
                if (bmsDamageLine.isSetOtherChargesInfo()) {
                  tempPartPrice = bmsDamageLine.getOtherChargesInfo()
                      .getPrice().toString();

                  if ("0".equals(tempPartPrice) && "0.0".equals(tempPartPrice)) {
                    tempPartPrice = null;
                  }
                }
              }
            }
          }
        }

        // set the part price
        if (tempPartPrice != null && !("0".equals(tempPartPrice))) {
          partPrice = Double.parseDouble(tempPartPrice);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPrice(formatNumber(partPrice));
        }

        // get the the part Quantity and Unit Part Price
        if (bmsDamageLine.getPartInfo().isSetQuantity()
            && bmsDamageLine.getPartInfo().getQuantity() != null) {
          tempPartQuantity = bmsDamageLine.getPartInfo().getQuantity()
              .toString();
        }

        if (tempPartQuantity != null && tempPartQuantity.length() > 0
            && !("0".equals(tempPartQuantity))) {
          partQuantity = Integer.parseInt(tempPartQuantity);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartQuantity(partQuantity + "");
          unitPartPrice = partPrice / partQuantity;
        } else {
          unitPartPrice = partPrice;
        }

        if (unitPartPrice > 0)
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPrice(formatNumber(unitPartPrice));

      }

      // Added LineType, Related Damage, Unrelated Damage for CCC Estimates
      if (bmsDamageLine.isSetLineType() && bmsDamageLine.getLineType() != null) {
        lineType = bmsDamageLine.getLineType();
      }

      if (lineType != null) {

        if (lineType.equals(LineTypeTyp.CURRENT_CLAIM.toString())) {
          suppRequestLineItem.getLineItem().setLineType(
              LineTypeTyp.CURRENT_CLAIM);
          logger.info("MIE: set Line Type for Current Claim");
        }

        else if (lineType.equals(LineTypeTyp.RELATED.toString())) {
          suppRequestLineItem.getLineItem().setLineType(LineTypeTyp.RELATED);
          logger.info("MIE: set Line Type for RELATED Damage");
        }

        else if (lineType.equals(LineTypeTyp.UNRELATED.toString())) {
          suppRequestLineItem.getLineItem().setLineType(LineTypeTyp.UNRELATED);
          logger.info("MIE: set Line Type for UNRELATED Damage");
        }

        else {
          lineType = LineTypeTyp.CURRENT_CLAIM.toString();
        }
      }

      /*
       * Get the Labor related data
       */

      boolean laborIncludeIndicator = false;
      String mieLaborType = null;
      String bmsLaborType = null;
      String tempLaborHours = null;

      // Get the VendorRefNum
      String vendorRefNumber = null;
      if (bmsDamageLine.isSetVendorRefNum()) {
        vendorRefNumber = bmsDamageLine.getVendorRefNum();
      }

      if (bmsDamageLine.isSetLaborInfo()) {

        // add the labor info
        suppRequestLineItem.getLineItem().addNewLaborInfo();

        // get Labor Include Indicator
        if (bmsDamageLine.getLaborInfo().isSetLaborInclInd()
            && bmsDamageLine.getLaborInfo().getLaborInclInd()) {
          laborIncludeIndicator = true;
        }

        // get the MIE labor type
        bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();
        mieLaborType = BMSMIECodesMapper.getLaborType(bmsLaborType);

        // If the Labor cost is not included in the Parts/Other Price
        if (!laborIncludeIndicator) {

          // get the labor hrs
          if (bmsDamageLine.getLaborInfo().isSetLaborHours()) {
            tempLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                .toString();
          }

          // apply business rules
          if (tempLaborHours != null && tempLaborHours.length() > 0
              && mieLaborType != null && mieLaborType.length() > 0) {

            // if the vendor ref number is 900500 and the labor hrs is 0
            if ("0".equals(tempLaborHours) && "900500".equals(vendorRefNumber)) {
              tempLaborHours = null;
            } else {
              // get the labor rate
              String laborRate = getLaborRate(bmsDamageLine.getLaborInfo()
                  .getLaborType());

              if (laborRate != null && laborRate.length() > 0) {
                // calculate the labor cost
                double lRate = Double.parseDouble(laborRate);
                double lHours = Double.parseDouble(tempLaborHours);

                laborCost = lRate * lHours;

                // set the labor cost
                suppRequestLineItem.getLineItem().getLaborInfo()
                    .setLaborCost(formatNumber(laborCost));
                // set the labor hours
                suppRequestLineItem.getLineItem().getLaborInfo()
                    .setLaborHours(tempLaborHours + "");
              }
            }
          }
        } else {
          suppRequestLineItem.getLineItem().getLaborInfo().setLaborHours("INC");
        }
      }

      if (bmsDamageLine.isSetPartInfo() == false
          && bmsDamageLine.isSetLaborInfo() == false
          && bmsDamageLine.isSetOtherChargesInfo()) {

        if (bmsDamageLine.getOtherChargesInfo().getPrice() != null)
          laborCost = bmsDamageLine.getOtherChargesInfo().getPrice()
              .doubleValue();

        // set the labor cost
        if (suppRequestLineItem.getLineItem().getLaborInfo() == null) {
          suppRequestLineItem.getLineItem().addNewLaborInfo();
        }
        suppRequestLineItem.getLineItem().getLaborInfo()
            .setLaborCost(laborCost + "");
      } else if (bmsDamageLine.isSetPartInfo() == false
          && bmsDamageLine.isSetOtherChargesInfo()) {
        if (bmsDamageLine.getOtherChargesInfo().getPrice() != null) {
          tempPartPrice = bmsDamageLine.getOtherChargesInfo().getPrice()
              .toString();

          // set the part price for Other Charges
          if (tempPartPrice != null) {
            partPrice = Double.parseDouble(tempPartPrice);

            suppRequestLineItem.getLineItem().addNewPartInfo();
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartPrice(formatNumber(partPrice));
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPrice(formatNumber(partPrice));
          }
        }
      } else if (bmsDamageLine.isSetPartInfo() == false
          && bmsDamageLine.isSetLineAdjustment()) {
        if (bmsDamageLine.getLineAdjustment().getAdjustmentAmt() != null) {
          tempPartPrice = bmsDamageLine.getLineAdjustment().getAdjustmentAmt()
              .toString();
          // get the Previous part price
          if (tempPartPrice != null) {
            partPrice = Double.parseDouble(tempPartPrice);
            //prevUnitPartPrice = partPrice;
          }
          suppRequestLineItem.getLineItem().addNewPartInfo();
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPrice(formatNumber(partPrice));
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPrice(formatNumber(partPrice));
        }
      }

      // calculate the amount
      amount = partPrice + laborCost;
      if (amount != 0) {
        suppRequestLineItem.getLineItem().setAmount(formatNumber(amount));
      }
    }
  }

  /**
   * WCR 1.3 - Populates the SuppReqEmail LineItem from CCC BMS
   * 
   */
  private void populateSuppRequestLineItemForNonDataChangeCCC(
      LineItemInfoType suppRequestLineItem,
      com.cieca.bms.DamageLineInfoType bmsDamageLine)
  {

    // set parts related data
    String cccPartType = null;
    String bmsPartType = null;
    double partPrice = 0;
    String tempPartPrice = null;
    boolean partSubletIndicator = false;
    boolean priceIncludeIndicator = false;
    boolean clearCoatIncludeIndicator = false;
    double laborCost = 0;
    double amount = 0;
    int partQuantity = 0;
    String temppartQuantity = null;
    double unitpartPrice = 0;
    String paintLaborHour = null;
    double paintAmount = 0;
    String lineType = null;
    boolean laborIncludeIndicator = false;
    String cccLaborType = null;
    String bmsLaborType = null;
    String tempLaborHours = null;
    String laborHour = null;
    String laborAmt = null;
    String desc = null;
    if (suppRequestLineItem != null && bmsDamageLine != null) {

      // Added LineType, Related Damage, Unrelated Damage for CCC Estimates
      if (bmsDamageLine.isSetLineType() && bmsDamageLine.getLineType() != null) {
        lineType = bmsDamageLine.getLineType();
      }

      if (lineType != null) {

        if (lineType.equals(LineTypeTyp.RELATED.toString())) {
          suppRequestLineItem.getLineItem().setLineType(LineTypeTyp.RELATED);
          logger.info("CCC: set Line Type for RELATED Damage");
        } else if (lineType.equals(LineTypeTyp.UNRELATED.toString())) {
          suppRequestLineItem.getLineItem().setLineType(LineTypeTyp.UNRELATED);
          logger.info("CCC: set Line Type for UNRELATED Damage");
        } else {
          lineType = LineTypeTyp.CURRENT_CLAIM.toString();
          suppRequestLineItem.getLineItem().setLineType(
              LineTypeTyp.CURRENT_CLAIM);
        }
      } else {
        lineType = LineTypeTyp.CURRENT_CLAIM.toString();
        suppRequestLineItem.getLineItem()
            .setLineType(LineTypeTyp.CURRENT_CLAIM);
      }

      // set the operation
      suppRequestLineItem.getLineItem().setOperation(
          getBMSLineOperation(bmsDamageLine));

      // get & set the description     
      if (bmsDamageLine.isSetLineDesc()) {
        suppRequestLineItem.getLineItem().setLineDesc(
            bmsDamageLine.getLineDesc());
      }

      // check if Other Charges are found
      if (bmsDamageLine.isSetOtherChargesInfo()) {
        // Implementation of code according to CCC BMS SRS - 2.1.19    

        // get & set the line Number for Other Charges
        suppRequestLineItem.getLineItem()
            .setLineNum(bmsDamageLine.getLineNum());

        // get & set the labor Operation for Other Charges
        /**
         * Verify if we would need to set to Addt'l cost
         */
        if (bmsDamageLine.getLaborInfo() != null
            && bmsDamageLine.getLaborInfo().isSetLaborOperation()) {
          suppRequestLineItem.getLineItem().setOperation(
              bmsDamageLine.getLaborInfo().getLaborOperation());
        }

        // check the parts Info for Other Charges
        if (bmsDamageLine.isSetPartInfo()) {

          // get the part Price for Other Charges
          if (bmsDamageLine.getPartInfo().isSetPartPrice()
              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
            tempPartPrice = bmsDamageLine.getOtherChargesInfo().getPrice()
                .toString();

            // set the part price for Other Charges
            if (tempPartPrice != null && !("0".equals(tempPartPrice))) {
              partPrice = Double.parseDouble(tempPartPrice);
              suppRequestLineItem.getLineItem().getPartInfo()
                  .setPartPrice(formatNumber(partPrice));
            }
          }

          // get & set the part Quantity for Other Charges
          if (bmsDamageLine.getPartInfo().isSetQuantity()
              && bmsDamageLine.getPartInfo().getQuantity() != null) {
            temppartQuantity = bmsDamageLine.getPartInfo().getQuantity()
                .toString();
          }

          if (temppartQuantity != null && temppartQuantity.length() > 0
              && !("0".equals(temppartQuantity))) {
            partQuantity = Integer.parseInt(temppartQuantity);
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartQuantity(partQuantity + "");
            unitpartPrice = partPrice / partQuantity;
          } else {
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartQuantity("1");
            unitpartPrice = partPrice;
          }
        }

      } else {

        // Handle the Refinish repair line SRS: 2.1.7
        if (bmsDamageLine.isSetRefinishLaborInfo()
            && bmsDamageLine.isSetLaborInfo() == false) {

          // set the operation for Refinish
          suppRequestLineItem.getLineItem().setOperation(
              bmsDamageLine.getRefinishLaborInfo().getLaborOperation());

          // get the labor hours for Refinish
          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborHours()
              && bmsDamageLine.getRefinishLaborInfo().getLaborHours() != null) {
            laborHour = bmsDamageLine.getRefinishLaborInfo().getLaborHours()
                .toString();
          }

          // get the labor Amount for Refinish 
          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborAmt()
              && bmsDamageLine.getRefinishLaborInfo().getLaborAmt() != null) {
            laborAmt = bmsDamageLine.getRefinishLaborInfo().getLaborAmt()
                .toString();
          }

          // set the labor hours & labor amount for Refinish
          if (laborHour != null || laborAmt != null) {
            suppRequestLineItem.getLineItem().addNewLaborInfo();

            if (laborHour != null && laborHour.length() > 0
                && !("0".equals(laborHour))) {
              suppRequestLineItem.getLineItem().getLaborInfo()
                  .setLaborHours(laborHour);
            }

            //Setting the labor cost here, since it is used for setting to total
            if (laborAmt != null && laborAmt.length() > 0
                && !("0".equals(laborAmt))) {
              suppRequestLineItem.getLineItem().getLaborInfo()
                  .setLaborCost(laborAmt + "");
            }
          }
        }

        // Handle the Labor Info Details
        if (bmsDamageLine.isSetLaborInfo()) {

          /*
           * Get the Labor related data
           */

          // Get the VendorRefNum
          String vendorRefNumber = null;
          if (bmsDamageLine.isSetVendorRefNum()) {
            vendorRefNumber = bmsDamageLine.getVendorRefNum();
          }

          // add the labor info
          suppRequestLineItem.getLineItem().addNewLaborInfo();

          // get Labor Include Indicator
          if (bmsDamageLine.getLaborInfo().isSetLaborInclInd()
              && bmsDamageLine.getLaborInfo().getLaborInclInd()) {
            laborIncludeIndicator = true;
          }

          // get the CCC labor type
          bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();
          cccLaborType = BMSMIECodesMapper.getLaborType(bmsLaborType);

          // If the Labor cost is not included in the Parts/Other Price
          if (!laborIncludeIndicator) {

            // get the labor hrs
            if (bmsDamageLine.getLaborInfo().isSetLaborHours()) {
              tempLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                  .toString();
            }

            // apply business rules
            if (tempLaborHours != null && tempLaborHours.length() > 0
                && cccLaborType != null && cccLaborType.length() > 0) {

              // if the vendor ref number is 900500 and the labor hrs is 0
              if ("0".equals(tempLaborHours)
                  && "900500".equals(vendorRefNumber)) {
                tempLaborHours = null;
              } else {
                // get the labor cost
                if (bmsDamageLine.getLaborInfo().isSetLaborAmt()) {

                  laborCost = bmsDamageLine.getLaborInfo().getLaborAmt()
                      .doubleValue();
                  // set the labor cost
                  suppRequestLineItem.getLineItem().getLaborInfo()
                      .setLaborCost(formatNumber(laborCost));
                  // set the labor hours
                  suppRequestLineItem.getLineItem().getLaborInfo()
                      .setLaborHours(tempLaborHours + "");

                }
              }
            }
          }
        }

        // Handles Paint Repair  
        if (bmsDamageLine.isSetLaborInfo()
            && bmsDamageLine.isSetRefinishLaborInfo()) {

          // added paint Labor hours & Paint Amount to append to line description for CCC
          paintLaborHour = bmsDamageLine.getRefinishLaborInfo().getLaborHours()
              .toString();
          paintAmount = bmsDamageLine.getRefinishLaborInfo().getLaborAmt()
              .doubleValue();

          if (bmsDamageLine.isSetLineDesc()) {
            desc = bmsDamageLine.getLineDesc();
            suppRequestLineItem.getLineItem().setLineDesc(
                desc + " Paint " + paintLaborHour + "/" + currency + paintAmount);
          }
        }

        // Handle the PartsInfo Data
        if (bmsDamageLine.isSetPartInfo()) {

          suppRequestLineItem.getLineItem().addNewPartInfo();

          // get the bms part type
          bmsPartType = bmsDamageLine.getPartInfo().getPartType();

          // get the part sublet indicator
          if (bmsDamageLine.isSetSubletInfo()
              && bmsDamageLine.getSubletInfo().isSetPartSubletInd()) {
            partSubletIndicator = bmsDamageLine.getSubletInfo()
                .getPartSubletInd();
          }

          // get the CCC part type to display
          cccPartType = BMSMIECodesMapper.getPartTypeCode(bmsPartType,
              partSubletIndicator);

          // add part type
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartType(cccPartType);

          /*
           * Get the Part Price
           */
          // get the temp Part Price
          if (bmsDamageLine.getPartInfo().isSetPartPrice()
              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
            tempPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
                .toString();
          }

          // get the PriceIncludeIndicator
          if (bmsDamageLine.getPartInfo().isSetPriceInclInd()
              && bmsDamageLine.getPartInfo().getPriceInclInd()) {
            priceIncludeIndicator = true;
          }

          // get labor clear coat indicator
          if (bmsDamageLine.isSetLaborInfo()
              && bmsDamageLine.getLaborInfo().isSetPaintStagesNum()) {
            if (bmsDamageLine.getLaborInfo().getPaintStagesNum() == 2) {
              clearCoatIncludeIndicator = true;
            }
          }

          if (priceIncludeIndicator == false
              && clearCoatIncludeIndicator == false) {

            // check if the tempPartPrice = 0 and tempPartPrice = 0.0
            if ("0".equals(tempPartPrice) && "0.0".equals(tempPartPrice)) {

              // get the line adjustment amt
              if (bmsDamageLine.isSetLineAdjustment()
                  && bmsDamageLine.getLineAdjustment().isSetAdjustmentAmt()) {

                // set the temp part price to adjustment amount
                tempPartPrice = bmsDamageLine.getLineAdjustment()
                    .getAdjustmentAmt().toString();

                // check if the price = 0
                if ("0".equals(tempPartPrice) && "0.0".equals(tempPartPrice)) {

                  // get the other charges
                  if (bmsDamageLine.isSetOtherChargesInfo()) {
                    tempPartPrice = bmsDamageLine.getOtherChargesInfo()
                        .getPrice().toString();

                    if ("0".equals(tempPartPrice)
                        && "0.0".equals(tempPartPrice)) {
                      tempPartPrice = null;
                    }
                  }
                }
              }
            }
          }

          // set the part price
          if (tempPartPrice != null && !("0".equals(tempPartPrice))) {
            partPrice = Double.parseDouble(tempPartPrice);
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartPrice(formatNumber(partPrice));
          }

          // Added Part Quantity & Unit Part Price for CCC estimate
          if (bmsDamageLine.getPartInfo().isSetQuantity()
              && bmsDamageLine.getPartInfo().getQuantity() != null) {
            temppartQuantity = bmsDamageLine.getPartInfo().getQuantity()
                .toString();
          }

          if (temppartQuantity != null && temppartQuantity.length() > 0
              && !("0".equals(temppartQuantity))) {
            partQuantity = Integer.parseInt(temppartQuantity);
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartQuantity(partQuantity + "");

            unitpartPrice = partPrice / partQuantity;
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPrice(formatNumber(unitpartPrice));
          } else {
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartQuantity("1");
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPrice(formatNumber(partPrice));
          }

          if (bmsDamageLine.getPartInfo().isSetPartNum()) {
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartNumber(bmsDamageLine.getPartInfo().getPartNum());
          }
        }
      }

      // calculate the amount
      amount = partPrice + laborCost + paintAmount;
      if (amount != 0) {
        suppRequestLineItem.getLineItem().setAmount(formatNumber(amount));
      }
    }
  }

  /**
   * WCR 1.3 - Populates the SuppReqEmail LineItem from Audatex BMS
   * 
   */
  private void populateSuppRequestLineItemForNonDataChangeAudatex(
      LineItemInfoType suppRequestLineItem,
      com.cieca.bms.DamageLineInfoType bmsDamageLine, boolean isFirstLine)
  {

    String lineAdj = null;
    String adjPct = null;
    // set parts related data
    String audatexPartType = null;
    String bmsPartType = null;
    double partPrice = 0;
    String tempPartPrice = null;
    boolean partSubletIndicator = false;
    boolean priceIncludeIndicator = false;
    boolean clearCoatIncludeIndicator = false;
    double laborCost = 0;
    double amount = 0;
    int partQuantity = 0;
    String temppartQuantity = null;
    String lineType = null;
    // get labor related data    
    boolean laborIncludeIndicator = false;
    String audatexLaborType = null;
    String bmsLaborType = null;
    String laborHours = null;
    String databaseLaborHours = null;
    String laborAmount = null;
    boolean isPartPricePresent = false;
    boolean isQtyPresent = false;

    if (suppRequestLineItem != null && bmsDamageLine != null) {

      // line desc
      StringBuffer newlineDesc = new StringBuffer();

      //get the line description
      if (bmsDamageLine.isSetLineDesc() && bmsDamageLine.getLineDesc() != null) {
        newlineDesc.append(bmsDamageLine.getLineDesc());
      } else {

        if (!isFirstLine) {
          newlineDesc.append(suppRequestLineItem.getLineItem().getLineDesc());
        }

      }

      if (bmsDamageLine.getLineAdjustment() != null
          && bmsDamageLine.getLineAdjustment().isSetAdjustmentPct()
          && bmsDamageLine.getLineAdjustment().getAdjustmentPct() != null) {
        adjPct = bmsDamageLine.getLineAdjustment().getAdjustmentPct()
            .toString();
      } else {
        adjPct = "0";
      }

      if (bmsDamageLine.getLineAdjustment() != null
          && bmsDamageLine.getLineAdjustment().isSetAdjustmentType()) {

        if (bmsDamageLine.getLineAdjustment().getAdjustmentType() != null) {
          lineAdj = bmsDamageLine.getLineAdjustment().getAdjustmentType();
        }

        //check if line Adjustment type is "BettermentParts"
        //set the description & append BettermentParts to line description
        if ("Betterment Parts".equals(lineAdj)) {
          newlineDesc.append(" B" + adjPct + "%");
        }

        //check if line Adjustment type is "Discount"
        //set the description & append discount to line description
        if ("Discount".equals(lineAdj)) {
          newlineDesc.append(" -" + adjPct + "%");
        }

        //check if line Adjustment type is "Markup"
        //set the description & append markup percentage to line description
        if ("Markup".equals(lineAdj)) {
          newlineDesc.append(" +" + adjPct + "%");
        }
      }

      // set the desc
      suppRequestLineItem.getLineItem().setLineDesc(newlineDesc.toString());

      if (isFirstLine) {

        // set the operation
        suppRequestLineItem.getLineItem().setOperation(
            getBMSLineOperation(bmsDamageLine));

        /* checks parts Info data */
        if (bmsDamageLine.isSetPartInfo()) {

          /*
           * If the PartPrice is 0, unitPartPrice is 0 as well, hence the
           * PartQuantity should
           * not be set in the suppRequest XML
           */

          suppRequestLineItem.getLineItem().addNewPartInfo();

          // get the bms part type
          bmsPartType = bmsDamageLine.getPartInfo().getPartType();

          // get the part sublet indicator
          if (bmsDamageLine.isSetSubletInfo()
              && bmsDamageLine.getSubletInfo().isSetPartSubletInd()) {
            partSubletIndicator = bmsDamageLine.getSubletInfo()
                .getPartSubletInd();
          }

          // get the Audatex part type to display
          audatexPartType = BMSMIECodesMapper.getPartTypeCode(bmsPartType,
              partSubletIndicator);

          // add part type
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartType(audatexPartType);

          /*
           * Get the Part Price
           */
          // get the temp Part Price
          if (bmsDamageLine.getPartInfo().isSetPartPrice()
              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
            tempPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
                .toString();
          }

          // get the PriceIncludeIndicator
          if (bmsDamageLine.getPartInfo().isSetPriceInclInd()
              && bmsDamageLine.getPartInfo().getPriceInclInd()) {
            priceIncludeIndicator = true;
          }

          // get labor clear coat indicator
          if (bmsDamageLine.isSetLaborInfo()
              && bmsDamageLine.getLaborInfo().isSetPaintStagesNum()) {
            if (bmsDamageLine.getLaborInfo().getPaintStagesNum() == 2) {
              clearCoatIncludeIndicator = true;
            }
          }

          if (priceIncludeIndicator == false
              && clearCoatIncludeIndicator == false) {

            // check if the tempPartPrice = 0 and tempPartPrice = 0.0
            if ("0".equals(tempPartPrice) || "0.0".equals(tempPartPrice)) {

              // get the line adjustment amt
              if (bmsDamageLine.isSetLineAdjustment()
                  && bmsDamageLine.getLineAdjustment().isSetAdjustmentAmt()) {

                // set the temp part price to adjustment amount
                tempPartPrice = bmsDamageLine.getLineAdjustment()
                    .getAdjustmentAmt().toString();

                // check if the price = 0 and price = 0.0
                if ("0".equals(tempPartPrice) || "0.0".equals(tempPartPrice)) {

                  // get the other charges
                  if (bmsDamageLine.isSetOtherChargesInfo()) {
                    tempPartPrice = bmsDamageLine.getOtherChargesInfo()
                        .getPrice().toString();

                    if ("0".equals(tempPartPrice)
                        || "0.0".equals(tempPartPrice)) {
                      tempPartPrice = null;
                    }
                  }
                }
              }
            }
          }

          // Added Part Quantity & Unit Part Price for Audatex estimate 
          // No need to calculate unit part price for Audatex BMS
          if (bmsDamageLine.getPartInfo().isSetQuantity()
              && bmsDamageLine.getPartInfo().getQuantity() != null) {
            temppartQuantity = bmsDamageLine.getPartInfo().getQuantity()
                .toString();
          }

          // set the part price
          if (isValidNonZeroNumber(tempPartPrice)) {
            partPrice = Double.parseDouble(tempPartPrice);
            isPartPricePresent = true;
          }

          if (isValidNonZeroNumber(temppartQuantity)) {
            partQuantity = Integer.parseInt(temppartQuantity);
            isQtyPresent = true;
          }

          if (isPartPricePresent && isQtyPresent) {
            // set the price    
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartPrice(formatNumber(partPrice));

            // set qty
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartQuantity(partQuantity + "");

            // set the unit price
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPrice(formatNumber(partPrice / partQuantity));
          } else if (isPartPricePresent && (isQtyPresent == false)) {

            // set the price    
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPartPrice(formatNumber(partPrice));

            // set the unit price
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPrice(formatNumber(partPrice));
          }

          // Added LineType, Related Damage, Unrelated Damage for Audatex Estimates
          if (bmsDamageLine.isSetLineType()
              && bmsDamageLine.getLineType() != null) {
            lineType = bmsDamageLine.getLineType();
          }

          if (lineType != null) {

            if (lineType.equals(LineTypeTyp.CURRENT_CLAIM.toString())) {
              suppRequestLineItem.getLineItem().setLineType(
                  LineTypeTyp.CURRENT_CLAIM);
              logger.info("AUDATEX: set Line Type for Current Claim");
            }

            else if (lineType.equals(LineTypeTyp.RELATED.toString())) {
              suppRequestLineItem.getLineItem()
                  .setLineType(LineTypeTyp.RELATED);
              logger.info("AUDATEX: set Line Type for RELATED Damage");
            }

            else if (lineType.equals(LineTypeTyp.UNRELATED.toString())) {
              suppRequestLineItem.getLineItem().setLineType(
                  LineTypeTyp.UNRELATED);
              logger.info("AUDATEX: set Line Type for UNRELATED Damage");
            }

            else {
              lineType = LineTypeTyp.CURRENT_CLAIM.toString();
            }
          }
        }

        // Get the VendorRefNum
        String vendorRefNumber = null;
        if (bmsDamageLine.isSetVendorRefNum()) {
          vendorRefNumber = bmsDamageLine.getVendorRefNum();
        }

        if (bmsDamageLine.isSetLaborInfo()) {

          // add the labor info
          suppRequestLineItem.getLineItem().addNewLaborInfo();

          // added for Audatex to determine labor included with labor hours & database labor hours
          // get the labor hrs
          if (bmsDamageLine.getLaborInfo().isSetLaborHours()
              && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
            laborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                .toString();
          }

          // get the database labor hrs
          if (bmsDamageLine.getLaborInfo().isSetDatabaseLaborHours()
              && bmsDamageLine.getLaborInfo().getDatabaseLaborHours() != null) {
            databaseLaborHours = bmsDamageLine.getLaborInfo()
                .getDatabaseLaborHours().toString();
          }

          if (("0".equals(laborHours) || "0.0".equals(laborHours))
              && ("0" != databaseLaborHours)) {
            //set labor hours to included
            laborHours = "INC";
            laborCost = 0;
            laborIncludeIndicator = true;
          } else {

            // get the Audatex labor type
            bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();
            audatexLaborType = BMSMIECodesMapper.getLaborType(bmsLaborType);

            // If the Labor cost is not included in the Parts/Other Price
            if (!laborIncludeIndicator) {

              // get the labor hrs
              if (bmsDamageLine.getLaborInfo().isSetLaborHours()
                  && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
                laborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                    .toString();
              }

              // apply business rules
              if (laborHours != null && laborHours.length() > 0
                  && audatexLaborType != null && audatexLaborType.length() > 0) {

                // if the vendor ref number is 900500 and the labor hrs is 0
                if ("0".equals(laborHours) && "900500".equals(vendorRefNumber)) {
                  laborHours = null;
                } else {

                  if (bmsDamageLine.getLaborInfo().isSetLaborAmt()
                      && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
                    laborAmount = bmsDamageLine.getLaborInfo().getLaborAmt()
                        .toString();
                  }

                  if (laborAmount != null && !("0".equals(laborAmount))) {

                    try {
                      laborCost = Double.parseDouble(laborAmount);
                    } catch (Exception e) {
                      laborCost = 0;
                    }
                  } else {

                    //get the labor rate
                    String laborRate = getLaborRate(bmsDamageLine
                        .getLaborInfo().getLaborType());

                    if (laborRate != null && laborRate.length() > 0) {
                      // calculate the labor cost
                      double lRate = Double.parseDouble(laborRate);
                      double lHours = Double.parseDouble(laborHours);

                      laborCost = lRate * lHours;
                    }
                  }
                }
              }
            }
          }

          // set the labor cost
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborCost(formatNumber(laborCost));

          // set the labor hours
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborHours(laborHours + "");

        }

        // calculate the amount
        amount = partPrice + laborCost;
        if (amount != 0) {
          suppRequestLineItem.getLineItem().setAmount(formatNumber(amount));
        }

      }

    }
  }

  /**
   * Populates the SuppReqEmail LineItem from GTM
   * 
   */
  private void populateSuppRequestLineItemForDataChangeGTM(
	      com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
	      LineItemInfoType suppRequestLineItem,
	      com.cieca.bms.DamageLineInfoType bmsDamageLine)
	  {
	  


	    if (annotationDamageLine != null && suppRequestLineItem != null
	        && bmsDamageLine != null) {

	      /*
	       * Previous Parts related variables
	       */
	      double prevPartPrice = 0;
	      double prevUnitPartPrice = 0;
	      String tempPrevPartPrice = null;
	      String tempPrevPartQuantity = null;
	      
	      
	      int prevPartQuantity = 0;
	      /*
	       * Previous Labor related variables
	       */
	      String prevLaborHours = null;
	      String prevLaborCost = null;
	      /*
	       * Previous Id and Id%
	       * 
	       */
	      double prevId = 0;
	      double preIDPercentage=0;
	      bmsDamageLine.isSetPartInfo();
	      // check if Previous Other Charges are found
	      if (bmsDamageLine.isSetOtherChargesInfo()) {
	        //get Previous Part Details
	        if (bmsDamageLine.isSetPartInfo()) {

	          // get the Previous part Price
	          if (bmsDamageLine.getPartInfo().isSetPartPrice()
	              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
	            tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
	                .toString();

	            // get the Previous part price
	            if (tempPrevPartPrice != null) {//&& !("0".equals(tempPrevPartPrice))) {
	              prevPartPrice = Double.parseDouble(tempPrevPartPrice);
	            }
	          }

	          // get the Previous the part Quantity and Previous Unit Part Price
	          if (bmsDamageLine.getPartInfo().isSetQuantity()
	              && bmsDamageLine.getPartInfo().getQuantity() != null) {
	            tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity()
	                .toString();
	          }

	          if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0
	              && !("0".equals(tempPrevPartQuantity))) {
	            prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
	            prevUnitPartPrice = prevPartPrice / prevPartQuantity;
	          } else {
	            prevUnitPartPrice = prevPartPrice;
	          }
	          
	      	        
	          
	        }

	        if (bmsDamageLine.getOtherChargesInfo().getPrice() != null) {
	          tempPrevPartPrice = bmsDamageLine.getOtherChargesInfo().getPrice()
	              .toString();

	          // set the part price for Other Charges
	          if (tempPrevPartPrice != null) {//&& !("0".equals(tempPrevPartPrice))) {
	            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
	          }
	        }

	        if (bmsDamageLine.isSetLaborInfo()) {
	        	
	          // get the Previous labor hrs
	          if (bmsDamageLine.getLaborInfo().isSetLaborHours()
	              && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
	            prevLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
	                .toString();
	          }
	          // get the Previous labor Amount
	          if (bmsDamageLine.getLaborInfo().isSetLaborAmt()
	              && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
	            prevLaborCost = bmsDamageLine.getLaborInfo().getLaborAmt()
	                .toString();
	          } else {
	            String bmsLaborType = "";
	            double bmsLaborRate = 0;
	            if (bmsDamageLine.getLaborInfo().getLaborType() != null)
	              bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();

	            com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
	                .getVehicleDamageEstimateAddRq().getProfileInfo()
	                .getRateInfoArray();
	            if (bmsRateInfoArray != null) {
	              com.cieca.bms.RateInfoType bmsRateInfoLine = null;
	              // FIXME: Add check for null point access to bmsRateType
	              String bmsRateType = null;
	              
	              for (int i = 0; i < bmsRateInfoArray.length; i++) {
	                bmsRateInfoLine = bmsRateInfoArray[i];
	                if (bmsRateInfoLine.getRateType() != null)
	                  bmsRateType = bmsRateInfoLine.getRateType();

	                if (bmsRateType.equalsIgnoreCase(bmsLaborType)) {
	                  com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
	                      .getRateTierInfoArray();
	                  com.cieca.bms.TierInfoType bmsTierInfoLine = null;
	                  for (int j = 0; j < bmsTierInfoArray.length; j++) {
	                    if (j == 0) {
	                      bmsTierInfoLine = bmsTierInfoArray[j];
	                      if (bmsTierInfoLine.getRate() != null)
	                        bmsLaborRate = bmsTierInfoLine.getRate().doubleValue();
	                    }
	                  }
	                }
	              }
	            }

	            if ((prevLaborCost == null || new Double(prevLaborCost)
	                .doubleValue() == 0)
	                && (prevLaborHours != null && new Double(prevLaborHours)
	                    .doubleValue() != 0)) {
	              prevLaborCost = (new Double(bmsLaborRate).doubleValue())
	                  * (new Double(prevLaborHours).doubleValue()) + "";
	            }
	          }
	        }
	      } else if (bmsDamageLine.isSetLineAdjustment()) {
	    	  
	        if (bmsDamageLine.getLineAdjustment().isSetAdjustmentAmt()
	            && bmsDamageLine.getLineAdjustment().getAdjustmentAmt() != null) {
	          tempPrevPartPrice = bmsDamageLine.getLineAdjustment()
	              .getAdjustmentAmt().toString();
	          // get the Previous part price
	          if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
	            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
	            prevUnitPartPrice = prevPartPrice;
	          }
	        }
	      } else {
	        // Handle the Refinish repair line SRS: 2.1.7
	        if (bmsDamageLine.isSetRefinishLaborInfo()
	            && bmsDamageLine.isSetLaborInfo() == false) {
	          // get the previous labor hours for Refinish
	          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborHours()
	              && bmsDamageLine.getRefinishLaborInfo().getLaborHours() != null) {
	            prevLaborHours = bmsDamageLine.getRefinishLaborInfo()
	                .getLaborHours().toString();
	          }
	          // get the previous labor Amount for Refinish 
	          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborAmt()
	              && bmsDamageLine.getRefinishLaborInfo().getLaborAmt() != null) {
	            prevLaborCost = bmsDamageLine.getRefinishLaborInfo().getLaborAmt()
	                .toString();
	          }
	        }

	        //get Previous Part Details
	        if (bmsDamageLine.isSetPartInfo()) {

	          // get the Previous part Price
	          if (bmsDamageLine.getPartInfo().isSetPartPrice()
	              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
	            tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
	                .toString();

	            // get the Previous part price
	            if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
	              prevPartPrice = Double.parseDouble(tempPrevPartPrice);
	            }
	          }

	          // get the Previous the part Quantity and Previous Unit Part Price
	          if (bmsDamageLine.getPartInfo().isSetQuantity()
	              && bmsDamageLine.getPartInfo().getQuantity() != null) {
	            tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity()
	                .toString();
	          }

	          if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0
	              && !("0".equals(tempPrevPartQuantity))) {
	            prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
	            prevUnitPartPrice = prevPartPrice / prevPartQuantity;
	          } else {
	            prevUnitPartPrice = prevPartPrice;
	          }
	        }
	       
	        if (bmsDamageLine.isSetLaborInfo()) {
	          // get the Previous labor hrs
	          if (bmsDamageLine.getLaborInfo().isSetLaborHours()
	              && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
	            prevLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
	                .toString();
	          }
	          // get the Previous labor Amount
	          if (bmsDamageLine.getLaborInfo().isSetLaborAmt()
	              && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
	            prevLaborCost = bmsDamageLine.getLaborInfo().getLaborAmt()
	                .toString();
	          } else {
	            String bmsLaborType = "";
	            double bmsLaborRate = 0;
	            if (bmsDamageLine.getLaborInfo().getLaborType() != null)
	              bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();

	            com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
	                .getVehicleDamageEstimateAddRq().getProfileInfo()
	                .getRateInfoArray();
	            if (bmsRateInfoArray != null) {
	              com.cieca.bms.RateInfoType bmsRateInfoLine = null;
	              // FIXME: Add check for null point access to bmsRateType
	              String bmsRateType = null;
	              
	              for (int i = 0; i < bmsRateInfoArray.length; i++) {
	                bmsRateInfoLine = bmsRateInfoArray[i];
	                if (bmsRateInfoLine.getRateType() != null)
	                  bmsRateType = bmsRateInfoLine.getRateType();

	                if (bmsRateType.equalsIgnoreCase(bmsLaborType)) {
	                  com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
	                      .getRateTierInfoArray();
	                  com.cieca.bms.TierInfoType bmsTierInfoLine = null;
	                  for (int j = 0; j < bmsTierInfoArray.length; j++) {
	                    if (j == 0) {
	                      bmsTierInfoLine = bmsTierInfoArray[j];
	                      if (bmsTierInfoLine.getRate() != null)
	                        bmsLaborRate = bmsTierInfoLine.getRate().doubleValue();
	                    }
	                  }
	                }
	              }
	            }

	            if ((prevLaborCost == null || new Double(prevLaborCost)
	                .doubleValue() == 0)
	                && (prevLaborHours != null && new Double(prevLaborHours)
	                    .doubleValue() != 0)) {
	              prevLaborCost = (new Double(bmsLaborRate).doubleValue())
	                  * (new Double(prevLaborHours).doubleValue()) + "";
	            }
	          }
	        }
	      }

	      /*
	       * Set Parts Info related data
	       */
	      String partType = null;
	      double partPrice = 0;
	      double unitPartPrice = 0;
	      String tempPartPrice = null;
	      String temp = null;
	      String tempUnitPartPrice = null;
	      int partQuantity = 0;
	     

	      // set the operation
	      suppRequestLineItem.getLineItem().setOperation(
	          annotationDamageLine.getOperation());

	      // set the description
	      suppRequestLineItem.getLineItem().setLineDesc(
	          annotationDamageLine.getDescription());

	      // set the change description
	      suppRequestLineItem.getLineItem().setChangeRecommended("Update");

	      // get part type                                				
	      temp = annotationDamageLine.getPartType();
	      if (temp != null && temp.length() > 0) {
	        partType = temp;
	      }

	      // get part price
	      if (annotationDamageLine.isSetPartPrice()
	          && annotationDamageLine.getPartPrice() != null) {
	        tempPartPrice = annotationDamageLine.getPartPrice().getStringValue();
	      }

	      // get unit part price
	      if (annotationDamageLine.isSetUnitPartPrice()
	          && annotationDamageLine.getUnitPartPrice() != null) {
	        tempUnitPartPrice = annotationDamageLine.getUnitPartPrice().toString();
	      }

	      // get part quantity
	      if (annotationDamageLine.isSetPartQuantity()) {
	        partQuantity = annotationDamageLine.getPartQuantity();
	      }

	      // set the part info data
	      
	      if(partType != null 
	    		  || tempPartPrice != null 
	    		  		|| tempUnitPartPrice != null 
	    		  				|| (partQuantity > 0) 
	    		  						|| prevPartPrice != partPrice)
	      {

	        // add part info
	        suppRequestLineItem.getLineItem().addNewPartInfo();

	        // set part type
	        if (!StringUtilities.isEmpty(partType)) {
	          suppRequestLineItem.getLineItem().getPartInfo().setPartType(partType);
	        }
	   
	        // set part quantity
	        //if(partQuantity > 0) {
	        if (!StringUtilities.isEmpty(partType) && partQuantity > 0)
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPartQuantity(partQuantity + "");
	        //}

	        // set part price
	        if (!StringUtilities.isEmpty(tempPartPrice)) {
	          partPrice = Double.parseDouble(tempPartPrice);
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPartPrice(formatNumber(partPrice));
	        } 	        

	        // set unit part price
	        // set part price
	        if (!StringUtilities.isEmpty(tempUnitPartPrice)) {
	          unitPartPrice = Double.parseDouble(tempUnitPartPrice);
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setUnitPartPrice(formatNumber(unitPartPrice));
	        }   

	        // set part number
	        if (annotationDamageLine.isSetPartNumber()) {
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPartNumber(annotationDamageLine.getPartNumber());
	        }

	        // set the previous part info data
	        // set part quantity
	        if (prevPartQuantity != partQuantity) {
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPreviousPartQuantity(prevPartQuantity + "");
	          int partQuantityDelta = partQuantity - prevPartQuantity;
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPartQuantityDelta(partQuantityDelta + "");
	        }
	        // set part price
	        if (prevPartPrice != partPrice) {
	          //if(bmsDamageLine.isSetOtherChargesInfo() && unitPartPrice > 0) {
	          if (bmsDamageLine.isSetOtherChargesInfo()) {
	            suppRequestLineItem.getLineItem().getPartInfo()
	                .setPreviousUnitPartPrice(formatNumber(prevPartPrice));
	            double unitPartPriceDelta = partPrice - prevPartPrice;
	            suppRequestLineItem.getLineItem().getPartInfo()
	                .setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
	          }
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPreviousPartPrice(formatNumber(prevPartPrice));
	          double partPriceDelta = partPrice - prevPartPrice;
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPartPriceDelta(formatNumber(partPriceDelta));
	        }
	        // set unit part price
	        if (prevUnitPartPrice != unitPartPrice
	            && !bmsDamageLine.isSetOtherChargesInfo()) {
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setPreviousUnitPartPrice(formatNumber(prevUnitPartPrice));
	          double unitPartPriceDelta = unitPartPrice - prevUnitPartPrice;
	          suppRequestLineItem.getLineItem().getPartInfo()
	              .setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
	        }
	      }
	      
	      //ID and ID% set
	      //Id and IDPercentage
	      double id=0;
	      double idPercentage=0;
             	
	      if(annotationDamageLine!=null){
	    	  if(annotationDamageLine.getLaborAdjustment() != null){
	    		  if(annotationDamageLine.getLaborAdjustment().getAdjustmentPct()!=null){
	    			  idPercentage= annotationDamageLine.getLaborAdjustment().getAdjustmentPct().doubleValue();
	    		  }
	    		  if(annotationDamageLine.getLaborAdjustment().getAdjustmentHours()!=null){
	    			id= annotationDamageLine.getLaborAdjustment().getAdjustmentHours().bigDecimalValue().doubleValue();
	    		  }
	    	  }
	      }
	    	
	      
        if(bmsDamageLine!=null){
        	if(bmsDamageLine.getPartInfo()!=null){
	        if(bmsDamageLine.getPartInfo().getPriceAdjustmentArray()!=null && bmsDamageLine.getPartInfo().getPriceAdjustmentArray().length!=0){
		      	 
	        	for(PriceAdjustmentType priceAdjustment :bmsDamageLine.getPartInfo().getPriceAdjustmentArray()){
	        	if(priceAdjustment!=null){
	        	if( priceAdjustment.getAdjustmentAmt()!=null){
		      	  prevId= priceAdjustment.getAdjustmentAmt().doubleValue();
		      	  }
		      	  if(priceAdjustment.getAdjustmentPct()!=null){
		      	  preIDPercentage=priceAdjustment.getAdjustmentPct().doubleValue();
		      	  }
	        	}
	        }
        	}
        }
        }
	     double idDelta=0;
	     double idPercentageDelta=0;
	     
	     
	     		 
	      /*
	       * Set the Labor related data
	       */
	      String laborHours = null;
	      String laborCost = null;
	      String laborType = null;

	      if (annotationDamageLine.isSetLaborType()
	          && annotationDamageLine.getLaborHours() != null) {
	        laborType = annotationDamageLine.getLaborType();
	      }

	      if (annotationDamageLine.isSetLaborHours()
	          && annotationDamageLine.getLaborHours() != null) {
	        temp = annotationDamageLine.getLaborHours().getStringValue();

	        if (!(annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine
	            .getLaborHours().getIncludeInd())) {
	          laborHours = temp;
	        }

	        if ((annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine
	            .getLaborHours().getIncludeInd())) {
	          laborHours = "INC";
	        }

	      }

	      if (annotationDamageLine.isSetLaborCost()
	          && annotationDamageLine.getLaborCost() != null) {
	        temp = annotationDamageLine.getLaborCost().toString();

	        if ((laborHours != null)) {// && Double.parseDouble(laborHours) != 0 ) || !("0".equals(temp)) ) {
	          laborCost = temp;
	        }
	      }
	      
	      LineItemType lineItem = null;	  
	      
			/*
			 * This block is checking whenever the price adjustment element is
			 * available in AnnotionEstimate,then setting Price adjustment (is
			 * used to set the ID and ID%) in supplement document Labor (BUG -
			 * 410439)
			 */
			if (annotationDamageLine.isSetPriceAdjustment()) {
					lineItem = suppRequestLineItem.getLineItem();
					lineItem.addNewLaborInfo();
					com.mitchell.schemas.estimate.PriceAdjustmentType priceAdjustment = annotationDamageLine
							.getPriceAdjustment();
					if (priceAdjustment != null) {
						com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborInfoType laborInfoType = lineItem.getLaborInfo();
						
						double priceAdjustmentAmount = priceAdjustment.getAdjustmentAmt() != null ? priceAdjustment.getAdjustmentAmt().doubleValue() : 0.00;
						laborInfoType
								.setID(formatNumber(priceAdjustmentAmount));
						
						laborInfoType.setPreviousID(formatNumber(prevId));
						
						
						double priceAdjustmentdelta = priceAdjustmentAmount != prevId? priceAdjustmentAmount - prevId  : prevId;
						
						
						laborInfoType
								.setIDDelta(
										formatNumber(priceAdjustmentdelta));
						
						double priceAdjustmentPct = priceAdjustment.getAdjustmentPct() != null ? priceAdjustment.getAdjustmentPct().doubleValue() : 0.00;	
						laborInfoType
								.setIDPercent(
										formatNumber(priceAdjustmentPct));
						
						laborInfoType.setPreviousIDPercent(
								formatNumber(preIDPercentage));
						
						double priceAdjustmentPctDelta = priceAdjustmentPct != preIDPercentage ? priceAdjustmentPct - preIDPercentage : 0.00;
						laborInfoType
								.setIDPercentDelta(
										formatNumber(priceAdjustmentPctDelta));
					}
				}

				//PRICE ADJUSTMENT END
			
	      double laborHourlyRate =  annotationDamageLine.getLaborHourlyRate() != null ? annotationDamageLine.getLaborHourlyRate().doubleValue() : 0.00;
	      
	      if ((laborHours != null || laborCost != null) && laborHourlyRate == 0.00) {
	        suppRequestLineItem.getLineItem().addNewLaborInfo();

	        if (laborType != null) {//&& ( ( laborHours!= null && Double.parseDouble(laborHours) != 0 ) || ( laborCost!= null && Double.parseDouble(laborCost) != 0 ) ) ) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborType(laborType);
	        }

	        if (laborHours != null && !laborHours.equals("INC")) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborHours(formatToTwoDecimalNumber(laborHours));
	        } else if (laborHours != null && laborHours.equals("INC")) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborHours(laborHours);
	        }

	        if (laborCost != null) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborCost(formatNumber(laborCost));
	        }

	        if (prevLaborHours != null
	            && laborHours != null
	            && !laborHours.equals("INC")
	            && Double.parseDouble(prevLaborHours) != Double
	                .parseDouble(laborHours)) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setPreviousLaborHours(formatToTwoDecimalNumber(prevLaborHours));
	          double laborHoursDelta = Double.parseDouble(laborHours)
	              - Double.parseDouble(prevLaborHours);
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborHoursDelta(formatToTwoDecimalNumber(laborHoursDelta));
	        }

	        if (prevLaborCost != null
	            && laborCost != null
	            && Double.parseDouble(prevLaborCost) != Double
	                .parseDouble(laborCost)) {
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setPreviousLaborCost(formatNumber(prevLaborCost));
	          double laborCostDelta = Double.parseDouble(laborCost)
	              - Double.parseDouble(prevLaborCost);
	          suppRequestLineItem.getLineItem().getLaborInfo()
	              .setLaborCostDelta(formatNumber(laborCostDelta));
	        }   
	        
	      }
	      
	      
	      
	      
	      	/*
			 * This code is written for flush the existing LaborInfo and add new
			 * LaborInfo, if the LABOR_HOURLY_RATE is present. So it rearrange the
			 * total LaborInfo as per criteria (BUG - 410439)
			 */
	      
	      if(laborHourlyRate != 0.00){  	  
	    	  
	    	  suppRequestLineItem.getLineItem().addNewLaborInfo();
	    	  com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborInfoType laborInfoTypeSuppliment = suppRequestLineItem.getLineItem().getLaborInfo();
	    	  laborInfoTypeSuppliment.setLaborType(laborType != null ? laborType : "");
	    	  LaborInfoType laborInfoType = bmsDamageLine.getLaborInfo();
	    	  if(laborInfoType != null){
	    		  BigDecimal laborHourlyRateSuppliment = laborInfoType.getLaborHourlyRate();
		    	  double previouslabourHourlyRate = laborHourlyRateSuppliment!= null ? laborHourlyRateSuppliment.doubleValue() : 0.00; 	    	  
		    	  laborInfoTypeSuppliment.setLaborCost(formatNumber(laborHourlyRate));
		    	  laborInfoTypeSuppliment.setPreviousLaborCost(formatNumber(previouslabourHourlyRate));
		    	  laborInfoTypeSuppliment.setLaborCostDelta(formatNumber(laborHourlyRate - previouslabourHourlyRate));
	    	  }
	    	  
	    	  if (prevLaborHours != null
		  	            && laborHours != null
		  	            && !"INC".equals(laborHours)
		  	            && Double.parseDouble(prevLaborHours) != Double
		  	                .parseDouble(laborHours)) {
	    		  	laborInfoTypeSuppliment.setLaborHours(formatToTwoDecimalNumber(laborHours));	
	    		  	laborInfoTypeSuppliment
		  	              .setPreviousLaborHours(formatToTwoDecimalNumber(prevLaborHours));
		  	          double laborHoursDelta = Double.parseDouble(laborHours)
		  	              - Double.parseDouble(prevLaborHours);
		  	        laborInfoTypeSuppliment
		  	              .setLaborHoursDelta(formatToTwoDecimalNumber(laborHoursDelta));
		  	      }
	      }
	    
	      
	      
	      	//ID AND ID%
	        if(id!=prevId){
		    	 idDelta=id-prevId;
		    	 if(suppRequestLineItem!=null && suppRequestLineItem.getLineItem() !=null && suppRequestLineItem.getLineItem().getLaborInfo()!=null){
		   	      suppRequestLineItem.getLineItem().getLaborInfo().setID(formatNumber(id));	     
		   	      suppRequestLineItem.getLineItem().getLaborInfo().setPreviousID(formatNumber(prevId));
		   	      suppRequestLineItem.getLineItem().getLaborInfo().setIDDelta(formatNumber(idDelta));	
		   	      
		     }else{
		    	 if(suppRequestLineItem!=null && suppRequestLineItem.getLineItem() !=null && suppRequestLineItem.getLineItem().getLaborInfo()!=null){
		    		 suppRequestLineItem.getLineItem().getLaborInfo().setPreviousID(formatNumber(prevId));
		    	 }
		    	 
		     }
		    	 
		     }
		     
		     if(idPercentage!=preIDPercentage){
		    	 idPercentageDelta= idPercentage-preIDPercentage;
		    	 if(suppRequestLineItem!=null && suppRequestLineItem.getLineItem() !=null && suppRequestLineItem.getLineItem().getLaborInfo()!=null){
			   	      suppRequestLineItem.getLineItem().getLaborInfo().setIDPercent(formatNumber(idPercentage));	     
			   	      suppRequestLineItem.getLineItem().getLaborInfo().setPreviousIDPercent(formatNumber(preIDPercentage));
			   	      suppRequestLineItem.getLineItem().getLaborInfo().setIDPercentDelta(formatNumber(idPercentageDelta));	
			   	      
			     }
		     }else{
		    	 if(suppRequestLineItem!=null && suppRequestLineItem.getLineItem() !=null && suppRequestLineItem.getLineItem().getLaborInfo()!=null){
		    		 suppRequestLineItem.getLineItem().getLaborInfo().setPreviousIDPercent(formatNumber(preIDPercentage));
		    	 }		    	
		     }
		     
		    //ADDING FINAL AMOUNT FOR EACH LINE ITEM IN REPORT (BUG - 410439)
	        setLineItemAmount(bmsDamageLine, annotationDamageLine, suppRequestLineItem);
	      
	    } 
  
	  }
  
  
  
  /**
   * Populate the SuppReqEmail LineItem CCC from Annotations
   */
  private void populateSuppRequestLineItemForDataChangeMIE(
      com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
      LineItemInfoType suppRequestLineItem,
      com.cieca.bms.DamageLineInfoType bmsDamageLine)
  {

    if (annotationDamageLine != null && suppRequestLineItem != null
        && bmsDamageLine != null) {

      /*
       * Previous Parts related variables
       */
      double prevPartPrice = 0;
      double prevUnitPartPrice = 0;
      String tempPrevPartPrice = null;
      String tempPrevPartQuantity = null;
      int prevPartQuantity = 0;
      /*
       * Previous Labor related variables
       */
      String prevLaborHours = null;
      String prevLaborCost = null;
      // check if Previous Other Charges are found
      if (bmsDamageLine.isSetOtherChargesInfo()) {
        //get Previous Part Details
        if (bmsDamageLine.isSetPartInfo()) {

          // get the Previous part Price
          if (bmsDamageLine.getPartInfo().isSetPartPrice()
              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
            tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
                .toString();

            // get the Previous part price
            if (tempPrevPartPrice != null) {//&& !("0".equals(tempPrevPartPrice))) {
              prevPartPrice = Double.parseDouble(tempPrevPartPrice);
            }
          }

          // get the Previous the part Quantity and Previous Unit Part Price
          if (bmsDamageLine.getPartInfo().isSetQuantity()
              && bmsDamageLine.getPartInfo().getQuantity() != null) {
            tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity()
                .toString();
          }

          if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0
              && !("0".equals(tempPrevPartQuantity))) {
            prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
            prevUnitPartPrice = prevPartPrice / prevPartQuantity;
          } else {
            prevUnitPartPrice = prevPartPrice;
          }
        }

        if (bmsDamageLine.getOtherChargesInfo().getPrice() != null) {
          tempPrevPartPrice = bmsDamageLine.getOtherChargesInfo().getPrice()
              .toString();

          // set the part price for Other Charges
          if (tempPrevPartPrice != null) {//&& !("0".equals(tempPrevPartPrice))) {
            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
          }
        }

        if (bmsDamageLine.isSetLaborInfo()) {
          // get the Previous labor hrs
          if (bmsDamageLine.getLaborInfo().isSetLaborHours()
              && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
            prevLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                .toString();
          }
          // get the Previous labor Amount
          if (bmsDamageLine.getLaborInfo().isSetLaborAmt()
              && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
            prevLaborCost = bmsDamageLine.getLaborInfo().getLaborAmt()
                .toString();
          } else {
            String bmsLaborType = "";
            double bmsLaborRate = 0;
            if (bmsDamageLine.getLaborInfo().getLaborType() != null)
              bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();

            com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
                .getVehicleDamageEstimateAddRq().getProfileInfo()
                .getRateInfoArray();
            if (bmsRateInfoArray != null) {
              com.cieca.bms.RateInfoType bmsRateInfoLine = null;
              // FIXME: Add check for null point access to bmsRateType
             String bmsRateType = null;
              for (int i = 0; i < bmsRateInfoArray.length; i++) {
                bmsRateInfoLine = bmsRateInfoArray[i];
                if (bmsRateInfoLine.getRateType() != null)
                  bmsRateType = bmsRateInfoLine.getRateType();

                if (bmsRateType.equalsIgnoreCase(bmsLaborType)) {
                  com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
                      .getRateTierInfoArray();
                  com.cieca.bms.TierInfoType bmsTierInfoLine = null;
                  for (int j = 0; j < bmsTierInfoArray.length; j++) {
                    if (j == 0) {
                      bmsTierInfoLine = bmsTierInfoArray[j];
                      if (bmsTierInfoLine.getRate() != null)
                        bmsLaborRate = bmsTierInfoLine.getRate().doubleValue();
                    }
                  }
                }
              }
            }

            if ((prevLaborCost == null || new Double(prevLaborCost)
                .doubleValue() == 0)
                && (prevLaborHours != null && new Double(prevLaborHours)
                    .doubleValue() != 0)) {
              prevLaborCost = (new Double(bmsLaborRate).doubleValue())
                  * (new Double(prevLaborHours).doubleValue()) + "";
            }
          }
        }
      } else if (bmsDamageLine.isSetLineAdjustment()) {
        if (bmsDamageLine.getLineAdjustment().isSetAdjustmentAmt()
            && bmsDamageLine.getLineAdjustment().getAdjustmentAmt() != null) {
          tempPrevPartPrice = bmsDamageLine.getLineAdjustment()
              .getAdjustmentAmt().toString();
          // get the Previous part price
          if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
            prevUnitPartPrice = prevPartPrice;
          }
        }
      } else {
        // Handle the Refinish repair line SRS: 2.1.7
        if (bmsDamageLine.isSetRefinishLaborInfo()
            && bmsDamageLine.isSetLaborInfo() == false) {
          // get the previous labor hours for Refinish
          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborHours()
              && bmsDamageLine.getRefinishLaborInfo().getLaborHours() != null) {
            prevLaborHours = bmsDamageLine.getRefinishLaborInfo()
                .getLaborHours().toString();
          }
          // get the previous labor Amount for Refinish 
          if (bmsDamageLine.getRefinishLaborInfo().isSetLaborAmt()
              && bmsDamageLine.getRefinishLaborInfo().getLaborAmt() != null) {
            prevLaborCost = bmsDamageLine.getRefinishLaborInfo().getLaborAmt()
                .toString();
          }
        }

        //get Previous Part Details
        if (bmsDamageLine.isSetPartInfo()) {

          // get the Previous part Price
          if (bmsDamageLine.getPartInfo().isSetPartPrice()
              && bmsDamageLine.getPartInfo().getPartPrice() != null) {
            tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice()
                .toString();

            // get the Previous part price
            if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
              prevPartPrice = Double.parseDouble(tempPrevPartPrice);
            }
          }

          // get the Previous the part Quantity and Previous Unit Part Price
          if (bmsDamageLine.getPartInfo().isSetQuantity()
              && bmsDamageLine.getPartInfo().getQuantity() != null) {
            tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity()
                .toString();
          }

          if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0
              && !("0".equals(tempPrevPartQuantity))) {
            prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
            prevUnitPartPrice = prevPartPrice / prevPartQuantity;
          } else {
            prevUnitPartPrice = prevPartPrice;
          }
        }

        if (bmsDamageLine.isSetLaborInfo()) {
          // get the Previous labor hrs
          if (bmsDamageLine.getLaborInfo().isSetLaborHours()
              && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
            prevLaborHours = bmsDamageLine.getLaborInfo().getLaborHours()
                .toString();
          }
          // get the Previous labor Amount
          if (bmsDamageLine.getLaborInfo().isSetLaborAmt()
              && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
            prevLaborCost = bmsDamageLine.getLaborInfo().getLaborAmt()
                .toString();
          } else {
            String bmsLaborType = "";
            double bmsLaborRate = 0;
            if (bmsDamageLine.getLaborInfo().getLaborType() != null)
              bmsLaborType = bmsDamageLine.getLaborInfo().getLaborType();

            com.cieca.bms.RateInfoType bmsRateInfoArray[] = bmsDocument
                .getVehicleDamageEstimateAddRq().getProfileInfo()
                .getRateInfoArray();
            if (bmsRateInfoArray != null) {
              com.cieca.bms.RateInfoType bmsRateInfoLine = null;
              // FIXME: Add check for null point access to bmsRateType
             String bmsRateType = null;
              for (int i = 0; i < bmsRateInfoArray.length; i++) {
                bmsRateInfoLine = bmsRateInfoArray[i];
                if (bmsRateInfoLine.getRateType() != null)
                  bmsRateType = bmsRateInfoLine.getRateType();

                if (bmsRateType.equalsIgnoreCase(bmsLaborType)) {
                  com.cieca.bms.TierInfoType bmsTierInfoArray[] = bmsRateInfoLine
                      .getRateTierInfoArray();
                  com.cieca.bms.TierInfoType bmsTierInfoLine = null;
                  for (int j = 0; j < bmsTierInfoArray.length; j++) {
                    if (j == 0) {
                      bmsTierInfoLine = bmsTierInfoArray[j];
                      if (bmsTierInfoLine.getRate() != null)
                        bmsLaborRate = bmsTierInfoLine.getRate().doubleValue();
                    }
                  }
                }
              }
            }

            if ((prevLaborCost == null || new Double(prevLaborCost)
                .doubleValue() == 0)
                && (prevLaborHours != null && new Double(prevLaborHours)
                    .doubleValue() != 0)) {
              prevLaborCost = (new Double(bmsLaborRate).doubleValue())
                  * (new Double(prevLaborHours).doubleValue()) + "";
            }
          }
        }
      }

      /*
       * Set Parts Info related data
       */
      String partType = null;
      double partPrice = 0;
      double unitPartPrice = 0;
      String tempPartPrice = null;
      String temp = null;
      String tempUnitPartPrice = null;
      int partQuantity = 0;

      // set the operation
      suppRequestLineItem.getLineItem().setOperation(
          annotationDamageLine.getOperation());

      // set the description
      suppRequestLineItem.getLineItem().setLineDesc(
          annotationDamageLine.getDescription());

      // set the change description
      suppRequestLineItem.getLineItem().setChangeRecommended("Update");

      // get part type                                				
      temp = annotationDamageLine.getPartType();
      if (temp != null && temp.length() > 0) {
        partType = temp;
      }

      // get part price
      if (annotationDamageLine.isSetPartPrice()
          && annotationDamageLine.getPartPrice() != null) {
        tempPartPrice = annotationDamageLine.getPartPrice().getStringValue();
      }

      // get unit part price
      if (annotationDamageLine.isSetUnitPartPrice()
          && annotationDamageLine.getUnitPartPrice() != null) {
        tempUnitPartPrice = annotationDamageLine.getUnitPartPrice().toString();
      }

      // get part quantity
      if (annotationDamageLine.isSetPartQuantity()) {
        partQuantity = annotationDamageLine.getPartQuantity();
      }

      // set the part info data
      if (!StringUtilities.isEmpty(partType)
          || !(StringUtilities.isEmpty(tempPartPrice))
          || !(StringUtilities.isEmpty(tempUnitPartPrice))
          || (partQuantity > 0) || prevPartPrice != partPrice) {

        // add part info
        suppRequestLineItem.getLineItem().addNewPartInfo();

        // set part type
        if (!StringUtilities.isEmpty(partType)) {
          suppRequestLineItem.getLineItem().getPartInfo().setPartType(partType);
        }

        // set part quantity
        //if(partQuantity > 0) {
        if (!StringUtilities.isEmpty(partType))
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartQuantity(partQuantity + "");
        //}

        // set part price
        if (!StringUtilities.isEmpty(tempPartPrice)) {
          partPrice = Double.parseDouble(tempPartPrice);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPrice(formatNumber(partPrice));
        } else {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPrice(formatNumber(0.00));
        }

        // set unit part price
        // set part price
        if (!StringUtilities.isEmpty(tempUnitPartPrice)) {
          unitPartPrice = Double.parseDouble(tempUnitPartPrice);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPrice(formatNumber(unitPartPrice));
        } else {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPrice(formatNumber(0.00));
        }

        // set part number
        if (annotationDamageLine.isSetPartNumber()) {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartNumber(annotationDamageLine.getPartNumber());
        }

        // set the previous part info data
        // set part quantity
        if (prevPartQuantity != partQuantity) {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPreviousPartQuantity(prevPartQuantity + "");
          int partQuantityDelta = partQuantity - prevPartQuantity;
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartQuantityDelta(partQuantityDelta + "");
        }
        // set part price
        if (prevPartPrice != partPrice) {
          //if(bmsDamageLine.isSetOtherChargesInfo() && unitPartPrice > 0) {
          if (bmsDamageLine.isSetOtherChargesInfo()) {
            suppRequestLineItem.getLineItem().getPartInfo()
                .setPreviousUnitPartPrice(formatNumber(prevPartPrice));
            double unitPartPriceDelta = partPrice - prevPartPrice;
            suppRequestLineItem.getLineItem().getPartInfo()
                .setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
          }
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPreviousPartPrice(formatNumber(prevPartPrice));
          double partPriceDelta = partPrice - prevPartPrice;
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPriceDelta(formatNumber(partPriceDelta));
        }
        // set unit part price
        if (prevUnitPartPrice != unitPartPrice
            && !bmsDamageLine.isSetOtherChargesInfo()) {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPreviousUnitPartPrice(formatNumber(prevUnitPartPrice));
          double unitPartPriceDelta = unitPartPrice - prevUnitPartPrice;
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
        }
      }

      /*
       * Set the Labor related data
       */
      String laborHours = null;
      String laborCost = null;
      String laborType = null;

      if (annotationDamageLine.isSetLaborType()
          && annotationDamageLine.getLaborHours() != null) {
        laborType = annotationDamageLine.getLaborType();
      }

      if (annotationDamageLine.isSetLaborHours()
          && annotationDamageLine.getLaborHours() != null) {
        temp = annotationDamageLine.getLaborHours().getStringValue();

        if (!(annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine
            .getLaborHours().getIncludeInd())) {
          laborHours = temp;
        }

        if ((annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine
            .getLaborHours().getIncludeInd())) {
          laborHours = "INC";
        }

      }

      if (annotationDamageLine.isSetLaborCost()
          && annotationDamageLine.getLaborCost() != null) {
        temp = annotationDamageLine.getLaborCost().toString();

        if ((laborHours != null)) {// && Double.parseDouble(laborHours) != 0 ) || !("0".equals(temp)) ) {
          laborCost = temp;
        }
      }

      if (laborHours != null || laborCost != null) {
        suppRequestLineItem.getLineItem().addNewLaborInfo();

        if (laborType != null) {//&& ( ( laborHours!= null && Double.parseDouble(laborHours) != 0 ) || ( laborCost!= null && Double.parseDouble(laborCost) != 0 ) ) ) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborType(laborType);
        }

        if (laborHours != null && !laborHours.equals("INC")) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborHours(formatToTwoDecimalNumber(laborHours));
        } else if (laborHours != null && laborHours.equals("INC")) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborHours(laborHours);
        }

        if (laborCost != null) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborCost(formatNumber(laborCost));
        }

        if (prevLaborHours != null
            && laborHours != null
            && !laborHours.equals("INC")
            && Double.parseDouble(prevLaborHours) != Double
                .parseDouble(laborHours)) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setPreviousLaborHours(formatToTwoDecimalNumber(prevLaborHours));
          double laborHoursDelta = Double.parseDouble(laborHours)
              - Double.parseDouble(prevLaborHours);
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborHoursDelta(formatToTwoDecimalNumber(laborHoursDelta));
        }

        if (prevLaborCost != null
            && laborCost != null
            && Double.parseDouble(prevLaborCost) != Double
                .parseDouble(laborCost)) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setPreviousLaborCost(formatNumber(prevLaborCost));
          double laborCostDelta = Double.parseDouble(laborCost)
              - Double.parseDouble(prevLaborCost);
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborCostDelta(formatNumber(laborCostDelta));
        }

      }
      
     

      // check the previous amount
      if (annotationDamageLine.isSetOriginalAmount()) {
        suppRequestLineItem.getLineItem().setPreviousAmount(
            formatNumber(annotationDamageLine.getOriginalAmount().toString()));
        double deltaAmount = annotationDamageLine.getAmount().doubleValue()
            - annotationDamageLine.getOriginalAmount().doubleValue();
        if (annotationDamageLine.isSetAmount())
          suppRequestLineItem.getLineItem().setAmountDelta(
              formatNumber(deltaAmount));
      }

      // check the amount
      if (annotationDamageLine.isSetAmount()) {
        suppRequestLineItem.getLineItem().setAmount(
            formatNumber(annotationDamageLine.getAmount().toString()));
      }
    }
  }//End of populateSuppRequestLineItemForDataChangeMIE

  /**
   * Populate the SuppReqEmail LineItem CCC from Annotations
   */
    private void populateSuppRequestLineItemForDataChangeCCC(
                    com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine, LineItemInfoType suppRequestLineItem,
                    com.cieca.bms.DamageLineInfoType bmsDamageLine) {
        if (annotationDamageLine != null && suppRequestLineItem != null && bmsDamageLine != null) {

            /*
             * Previous Parts related variables
             */
            double prevPartPrice = 0;
            double prevUnitPartPrice = 0;
            String tempPrevPartPrice = null;
            String tempPrevPartQuantity = null;
            int prevPartQuantity = 0;
            /*
             * Previous Labor related variables
             */
            String prevLaborHours = null;
            String prevLaborCost = null;
            // check if Previous Other Charges are found
            if (bmsDamageLine.isSetOtherChargesInfo()) {
                // get Previous Part Details
                if (bmsDamageLine.isSetPartInfo()) {

                    // get the Previous part Price
                    if (bmsDamageLine.getPartInfo().isSetPartPrice() && bmsDamageLine.getPartInfo().getPartPrice() != null) {
                        tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice().toString();

                        // get the Previous part price
                        if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
                            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
                        }
                    }

                    // get the Previous the part Quantity and Previous Unit Part Price
                    if (bmsDamageLine.getPartInfo().isSetQuantity() && bmsDamageLine.getPartInfo().getQuantity() != null) {
                        tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity().toString();
                    }

                    if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0 && !("0".equals(tempPrevPartQuantity))) {
                        prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
                        prevUnitPartPrice = prevPartPrice / prevPartQuantity;
                    } else {
                        prevUnitPartPrice = prevPartPrice;
                    }
                }

                if (bmsDamageLine.getOtherChargesInfo().getPrice() != null) {
                    tempPrevPartPrice = bmsDamageLine.getOtherChargesInfo().getPrice().toString();

                    // set the part price for Other Charges
                    if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
                        prevPartPrice = Double.parseDouble(tempPrevPartPrice);
                    }
                }
            } else {
                // Handle the Refinish repair line SRS: 2.1.7
                if (bmsDamageLine.isSetRefinishLaborInfo() && bmsDamageLine.isSetLaborInfo() == false) {
                    // get the previous labor hours for Refinish
                    if (bmsDamageLine.getRefinishLaborInfo().isSetLaborHours()
                                    && bmsDamageLine.getRefinishLaborInfo().getLaborHours() != null) {
                        prevLaborHours = bmsDamageLine.getRefinishLaborInfo().getLaborHours().toString();
                    }
                    // get the previous labor Amount for Refinish
                    if (bmsDamageLine.getRefinishLaborInfo().isSetLaborAmt()
                                    && bmsDamageLine.getRefinishLaborInfo().getLaborAmt() != null) {
                        prevLaborCost = bmsDamageLine.getRefinishLaborInfo().getLaborAmt().toString();
                    }
                }

                // get Previous Part Details
                if (bmsDamageLine.isSetPartInfo()) {

                    // get the Previous part Price
                    if (bmsDamageLine.getPartInfo().isSetPartPrice() && bmsDamageLine.getPartInfo().getPartPrice() != null) {
                        tempPrevPartPrice = bmsDamageLine.getPartInfo().getPartPrice().toString();

                        // get the Previous part price
                        if (tempPrevPartPrice != null) {// && !("0".equals(tempPrevPartPrice))) {
                            prevPartPrice = Double.parseDouble(tempPrevPartPrice);
                        }
                    }

                    // get the Previous the part Quantity and Previous Unit Part Price
                    if (bmsDamageLine.getPartInfo().isSetQuantity() && bmsDamageLine.getPartInfo().getQuantity() != null) {
                        tempPrevPartQuantity = bmsDamageLine.getPartInfo().getQuantity().toString();
                    }

                    if (tempPrevPartQuantity != null && tempPrevPartQuantity.length() > 0 && !("0".equals(tempPrevPartQuantity))) {
                        prevPartQuantity = Integer.parseInt(tempPrevPartQuantity);
                        prevUnitPartPrice = prevPartPrice / prevPartQuantity;
                    } else {
                        prevUnitPartPrice = prevPartPrice;
                    }
                }

                if (bmsDamageLine.isSetLaborInfo()) {

                    // get the Previous labor hrs
                    if (bmsDamageLine.getLaborInfo().isSetLaborHours() && bmsDamageLine.getLaborInfo().getLaborHours() != null) {
                        prevLaborHours = bmsDamageLine.getLaborInfo().getLaborHours().toString();
                    }

                    // get the Previous labor Amount
                    if (bmsDamageLine.getLaborInfo().isSetLaborAmt() && bmsDamageLine.getLaborInfo().getLaborAmt() != null) {
                        prevLaborCost = bmsDamageLine.getLaborInfo().getLaborAmt().toString();
                    }
                }
            }

            /*
             * Set Parts Info related data
             */
            String partType = null;
            double partPrice = 0;
            double unitPartPrice = 0;
            String tempPartPrice = null;
            String temp = null;
            String tempUnitPartPrice = null;
            int partQuantity = 0;

            // set the operation
            suppRequestLineItem.getLineItem().setOperation(annotationDamageLine.getOperation());

            // set the description
            suppRequestLineItem.getLineItem().setLineDesc(annotationDamageLine.getDescription());

            // set the change description
            suppRequestLineItem.getLineItem().setChangeRecommended("Update");

            // get part type
            temp = annotationDamageLine.getPartType();
            if (temp != null && temp.length() > 0) {
                partType = temp;
            }

            // get part price
            if (annotationDamageLine.isSetPartPrice() && annotationDamageLine.getPartPrice() != null) {
                tempPartPrice = annotationDamageLine.getPartPrice().getStringValue();
            }

            // get unit part price
            if (annotationDamageLine.isSetUnitPartPrice() && annotationDamageLine.getUnitPartPrice() != null) {
                tempUnitPartPrice = annotationDamageLine.getUnitPartPrice().toString();
            }

            // get part quantity
            if (annotationDamageLine.isSetPartQuantity()) {
                partQuantity = annotationDamageLine.getPartQuantity();
            }

            // set the part info data
            if (!StringUtilities.isEmpty(partType) || !(StringUtilities.isEmpty(tempPartPrice))
                            || !(StringUtilities.isEmpty(tempUnitPartPrice)) || (partQuantity > 0)) {

                // add part info
                suppRequestLineItem.getLineItem().addNewPartInfo();

                // set part type
                if (!StringUtilities.isEmpty(partType)) {
                    suppRequestLineItem.getLineItem().getPartInfo().setPartType(partType);
                }

                // set part quantity
                // if(partQuantity > 0) {
                if (!StringUtilities.isEmpty(partType))
                    suppRequestLineItem.getLineItem().getPartInfo().setPartQuantity(partQuantity + "");
                // }

                // set part price
                if (!StringUtilities.isEmpty(tempPartPrice)) {
                    partPrice = Double.parseDouble(tempPartPrice);
                    suppRequestLineItem.getLineItem().getPartInfo().setPartPrice(formatNumber(partPrice));
                }

                // set unit part price
                // set part price
                if (!StringUtilities.isEmpty(tempUnitPartPrice)) {
                    unitPartPrice = Double.parseDouble(tempUnitPartPrice);
                    suppRequestLineItem.getLineItem().getPartInfo().setUnitPartPrice(formatNumber(unitPartPrice));
                } else {
                    suppRequestLineItem.getLineItem().getPartInfo().setUnitPartPrice(formatNumber(0.00));
                }

                // set part number
                if (annotationDamageLine.isSetPartNumber()) {
                    suppRequestLineItem.getLineItem().getPartInfo().setPartNumber(annotationDamageLine.getPartNumber());
                }

                // set the previous part info data
                // set part quantity
                if (prevPartQuantity != partQuantity) {
                    suppRequestLineItem.getLineItem().getPartInfo().setPreviousPartQuantity(prevPartQuantity + "");
                    int partQuantityDelta = partQuantity - prevPartQuantity;
                    suppRequestLineItem.getLineItem().getPartInfo().setPartQuantityDelta(partQuantityDelta + "");
                }
                // set part price
                if (prevPartPrice != partPrice) {
                    if (bmsDamageLine.isSetOtherChargesInfo() && unitPartPrice > 0) {
                        suppRequestLineItem.getLineItem().getPartInfo().setPreviousUnitPartPrice(formatNumber(prevPartPrice));
                        double unitPartPriceDelta = partPrice - prevPartPrice;
                        suppRequestLineItem.getLineItem().getPartInfo().setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
                    }
                    suppRequestLineItem.getLineItem().getPartInfo().setPreviousPartPrice(formatNumber(prevPartPrice));
                    double partPriceDelta = partPrice - prevPartPrice;
                    suppRequestLineItem.getLineItem().getPartInfo().setPartPriceDelta(formatNumber(partPriceDelta));
                }
                // set unit part price
                if (prevUnitPartPrice != unitPartPrice && !bmsDamageLine.isSetOtherChargesInfo()) {
                    suppRequestLineItem.getLineItem().getPartInfo().setPreviousUnitPartPrice(formatNumber(prevUnitPartPrice));
                    double unitPartPriceDelta = unitPartPrice - prevUnitPartPrice;
                    suppRequestLineItem.getLineItem().getPartInfo().setUnitPartPriceDelta(formatNumber(unitPartPriceDelta));
                }
            }

            /*
             * Set the Labor related data
             */
            String laborHours = null;
            String laborCost = null;
            String laborType = null;

            if (annotationDamageLine.isSetLaborType() && annotationDamageLine.getLaborHours() != null) {
                laborType = annotationDamageLine.getLaborType();
            }

            if (annotationDamageLine.isSetLaborHours() && annotationDamageLine.getLaborHours() != null) {
                temp = annotationDamageLine.getLaborHours().getStringValue();

                if (!(annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine.getLaborHours()
                                .getIncludeInd())) {
                    laborHours = temp;
                }

                if ((annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine.getLaborHours()
                                .getIncludeInd())) {
                    laborHours = "INC";
                }

            }

            if (annotationDamageLine.isSetLaborCost() && annotationDamageLine.getLaborCost() != null) {
                temp = annotationDamageLine.getLaborCost().toString();

                if ((laborHours != null)) {// && Double.parseDouble(laborHours) != 0 ) || !("0".equals(temp)) ) {
                    laborCost = temp;
                }
            }

            if (laborHours != null || laborCost != null) {
                suppRequestLineItem.getLineItem().addNewLaborInfo();

                if (laborType != null) {// && ( ( laborHours!= null && Double.parseDouble(laborHours) != 0 ) || ( laborCost!= null
                                        // && Double.parseDouble(laborCost) != 0 ) ) ) {
                    suppRequestLineItem.getLineItem().getLaborInfo().setLaborType(laborType);
                }

                if (laborHours != null && !laborHours.equals("INC")) {
                    suppRequestLineItem.getLineItem().getLaborInfo().setLaborHours(formatToTwoDecimalNumber(laborHours));
                } else if (laborHours != null && laborHours.equals("INC")) {
                    suppRequestLineItem.getLineItem().getLaborInfo().setLaborHours(laborHours);
                }

                if (laborCost != null) {
                    suppRequestLineItem.getLineItem().getLaborInfo().setLaborCost(formatNumber(laborCost));
                }

                if (prevLaborHours != null && laborHours != null && !laborHours.equals("INC")
                                && Double.parseDouble(prevLaborHours) != Double.parseDouble(laborHours)) {
                    suppRequestLineItem.getLineItem().getLaborInfo()
                                    .setPreviousLaborHours(formatToTwoDecimalNumber(prevLaborHours));
                    double laborHoursDelta = Double.parseDouble(laborHours) - Double.parseDouble(prevLaborHours);
                    suppRequestLineItem.getLineItem().getLaborInfo()
                                    .setLaborHoursDelta(formatToTwoDecimalNumber(laborHoursDelta));
                }

                if (prevLaborCost != null && laborCost != null
                                && Double.parseDouble(prevLaborCost) != Double.parseDouble(laborCost)) {
                    suppRequestLineItem.getLineItem().getLaborInfo().setPreviousLaborCost(formatNumber(prevLaborCost));
                    double laborCostDelta = Double.parseDouble(laborCost) - Double.parseDouble(prevLaborCost);
                    suppRequestLineItem.getLineItem().getLaborInfo().setLaborCostDelta(formatNumber(laborCostDelta));
                }

            }

            // check the previous amount
            if (annotationDamageLine.isSetOriginalAmount()) {
                suppRequestLineItem.getLineItem().setPreviousAmount(
                                formatNumber(annotationDamageLine.getOriginalAmount().toString()));
                double deltaAmount = annotationDamageLine.getAmount().doubleValue()
                                - annotationDamageLine.getOriginalAmount().doubleValue();
                if (annotationDamageLine.isSetAmount())
                    suppRequestLineItem.getLineItem().setAmountDelta(formatNumber(deltaAmount));
            }

            // check the amount
            if (annotationDamageLine.isSetAmount()) {
                suppRequestLineItem.getLineItem().setAmount(formatNumber(annotationDamageLine.getAmount().toString()));
            }
        }
    }

  /**
   * Populate the SuppReqEmail LineItem from Annotations
   */
  private void populateSuppRequestLineItemForDataChange(
      com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
      LineItemInfoType suppRequestLineItem)
  {

    if (annotationDamageLine != null && suppRequestLineItem != null) {

      /*
       * Set Parts Info related data
       */
      String partType = null;
      double partPrice = 0;
      double unitPartPrice = 0;
      String tempPartPrice = null;
      String temp = null;
      String tempUnitPartPrice = null;
      int partQuantity = 0;

      // set the operation
      suppRequestLineItem.getLineItem().setOperation(
          annotationDamageLine.getOperation());

      // set the description
      suppRequestLineItem.getLineItem().setLineDesc(
          annotationDamageLine.getDescription());

      // set the change description
      suppRequestLineItem.getLineItem().setChangeRecommended("Update");

      // get part type                                				
      temp = annotationDamageLine.getPartType();
      if (temp != null && temp.length() > 0) {
        partType = temp;
      }

      // get part price
      if (annotationDamageLine.isSetPartPrice()
          && annotationDamageLine.getPartPrice() != null) {
        tempPartPrice = annotationDamageLine.getPartPrice().getStringValue();
      }

      // get unit part price
      if (annotationDamageLine.isSetUnitPartPrice()
          && annotationDamageLine.getUnitPartPrice() != null) {
        tempUnitPartPrice = annotationDamageLine.getUnitPartPrice().toString();
      }

      // get part quantity
      if (annotationDamageLine.isSetPartQuantity()) {
        partQuantity = annotationDamageLine.getPartQuantity();
      }

      // set the part info data
      if (!StringUtilities.isEmpty(partType)
          || !(StringUtilities.isEmpty(tempPartPrice))
          || !(StringUtilities.isEmpty(tempUnitPartPrice))
          || (partQuantity > 0)) {

        // add part info
        suppRequestLineItem.getLineItem().addNewPartInfo();

        // set part type
        if (!StringUtilities.isEmpty(partType)) {
          suppRequestLineItem.getLineItem().getPartInfo().setPartType(partType);
        }

        // set part quantity
        if (partQuantity > 0) {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartQuantity(partQuantity + "");
        }

        // set part price
        if (!StringUtilities.isEmpty(tempPartPrice)) {
          partPrice = Double.parseDouble(tempPartPrice);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartPrice(formatNumber(partPrice));
        }

        // set unit part price
        // set part price
        if (!StringUtilities.isEmpty(tempUnitPartPrice)) {
          unitPartPrice = Double.parseDouble(tempUnitPartPrice);
          suppRequestLineItem.getLineItem().getPartInfo()
              .setUnitPartPrice(formatNumber(unitPartPrice));
        }

        // set part number
        if (annotationDamageLine.isSetPartNumber()) {
          suppRequestLineItem.getLineItem().getPartInfo()
              .setPartNumber(annotationDamageLine.getPartNumber());
        }
      }

      /*
       * Set the Labor related data
       */
      String laborHours = null;
      String laborCost = null;

      if (annotationDamageLine.isSetLaborCost()
          && annotationDamageLine.getLaborCost() != null) {
        temp = annotationDamageLine.getLaborCost().toString();

        if (!("0".equals(temp))) {
          laborCost = temp;
        }
      }

      if (annotationDamageLine.isSetLaborHours()
          && annotationDamageLine.getLaborHours() != null) {
        temp = annotationDamageLine.getLaborHours().getStringValue();

        if (!(annotationDamageLine.getLaborHours().isSetIncludeInd() && annotationDamageLine
            .getLaborHours().getIncludeInd())) {
          laborHours = temp;
        }
      }

      if (laborHours != null || laborCost != null) {
        suppRequestLineItem.getLineItem().addNewLaborInfo();

        if (laborHours != null) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborHours(laborHours);
        }

        if (laborCost != null) {
          suppRequestLineItem.getLineItem().getLaborInfo()
              .setLaborCost(formatNumber(laborCost));
        }

      }

      // check the amount
      if (annotationDamageLine.isSetAmount()) {
        suppRequestLineItem.getLineItem().setAmount(
            annotationDamageLine.getAmount().toString());
      }
    }
  }

  // Added method getBMSDamagedLines() to get the LineItems from BMS, based on line numbers
  private ArrayList<DamageLineInfoType> getBMSDamagedLines(String lineNumber,
      String annotationLineType)
  {

    com.cieca.bms.DamageLineInfoType damagedLine = null;
    String bmsLineType = null;

    ArrayList<DamageLineInfoType> list = new ArrayList<DamageLineInfoType>();

    com.cieca.bms.DamageLineInfoType damageLineArray[] = bmsDocument
        .getVehicleDamageEstimateAddRq().getDamageLineInfoArray();

    for (int i = 0; i < damageLineArray.length; i++) {
      damagedLine = damageLineArray[i];

      bmsLineType = damagedLine.getLineType();

      if (bmsLineType == null || "Current Claim".equals(bmsLineType)) {
        bmsLineType = "CurrentClaim";
      }

      if (annotationLineType.equals(bmsLineType)) {
        if (damagedLine.getLineNum() == Integer.parseInt(lineNumber)) {
          list.add(damagedLine);
        }
      }

    }

    return list;
  }

  // Get the Operation for DamageLine
  private String getBMSLineOperation(com.cieca.bms.DamageLineInfoType damageLine)
  {

    String operation = null;

    if (damageLine.isSetLaborInfo()
        && damageLine.getLaborInfo().isSetLaborOperation()) {
      operation = BMSMIECodesMapper.getLaborOperation(damageLine.getLaborInfo()
          .getLaborOperation());
    } else if (damageLine.isSetVendorRefNum()) {
      operation = BMSMIECodesMapper.getVendorReference(damageLine
          .getVendorRefNum());
    }

    return operation;
  }

  // Get the Labor rate
  private String getLaborRate(String laborType)
  {

    String laborRate = null;

    if (bmsDocument != null && laborType != null) {
      com.cieca.bms.RateInfoType[] rateInfoArray = bmsDocument
          .getVehicleDamageEstimateAddRq().getProfileInfo().getRateInfoArray();
      com.cieca.bms.RateInfoType rateInfo = null;

      if (rateInfoArray != null) {

        for (int i = 0; i < rateInfoArray.length; i++) {

          rateInfo = rateInfoArray[i];

          if (laborType.equals(rateInfo.getRateType())
              && rateInfo.getRateTierInfoArray() != null) {
            laborRate = rateInfo.getRateTierInfoArray(0).getRate().toString();
            break;
          }
        }
      }
    }

    return laborRate;
  }
  
	/**
	 * This method gets the GST Tax rate from BMS
	 * @return
	 */
	private double getGSTTaxRate() {

		double gstTaxRate = 0;

		if (bmsDocument != null) {
			com.cieca.bms.ProfileInfoType profileInfo = bmsDocument
					.getVehicleDamageEstimateAddRq().getProfileInfo();

			if (profileInfo != null
					&& profileInfo.isSetCanadianTax()
					&& profileInfo.getCanadianTax().isSetGoodsServicesTaxRate()) {
				gstTaxRate = profileInfo.getCanadianTax()
						.getGoodsServicesTaxRate().doubleValue();
			}
		}

		return gstTaxRate;
	}

  private com.mitchell.schemas.compliance.ExceptionType getComplianceException(
      String lineNumber)
  {
    com.mitchell.schemas.compliance.ExceptionType complianceException = null;

    if (complianceDocument != null) {

      // Logstype
      LogsType logsType = complianceDocument.getLogs();

      // get the logs array
      LogType[] logs = logsType.getLogArray();

      if (logs != null) {
        LogType complianceLog = logsType.getLogArray(logs.length - 1);
        com.mitchell.schemas.compliance.ExceptionsType expType = complianceLog
            .getExceptions();
        com.mitchell.schemas.compliance.ExceptionType exceptions[] = expType
            .getExceptionArray();

        for (int i = 0; i < exceptions.length; i++) {

          if (exceptions[i].getLineNumber().equals(lineNumber)) {
            complianceException = exceptions[i];
            break;
          }
        }
      }
    }

    return complianceException;
  }

  private String formatPhoneNumber(String number, String extn)
  {
    String temp = "";
    StringBuffer phoneBuffer = new StringBuffer();

    // get the clean numbers for phone
    temp = getOnlyDigits(number);
    if (!StringUtilities.isEmpty(temp) && temp.length() == 10) {
      phoneBuffer.append(StringUtilities.formatPhoneNumber(temp, 0));
    }

    // get the clean numbers for extn
    temp = getOnlyDigits(extn);
    if (!StringUtilities.isEmpty(temp)) {
      phoneBuffer.append(" ext. ");
      phoneBuffer.append(temp);
    }

    if (phoneBuffer.length() >= 10) {
      return phoneBuffer.toString().trim();
    }

    return null;
  }

  private String getOnlyDigits(String string)
  {
    String formattednumber = null;

    if (!StringUtilities.isEmpty(string)) {
      char[] charPhoneArray = string.toCharArray();
      StringBuffer buffer = new StringBuffer();

      for (int i = 0; i < charPhoneArray.length; i++) {
        if (Character.isDigit(charPhoneArray[i])) {
          buffer.append(charPhoneArray[i]);
        }
      }

      if (buffer.toString().trim().length() >= 1) {
        formattednumber = buffer.toString().trim();
      }
    }

    return formattednumber;
  }

  private String getCompanyName(UserInfoDocument userInfo)
  {

    // Get the company name from User Info
    String companyName = " ";
    UserHierType hierarchy = userInfo.getUserInfo().getUserHier();
    if ("COMPANY".equals(hierarchy.getHierNode().getLevel())) {
      companyName = hierarchy.getHierNode().getName();
    }

    // set to NA if not available
    if (companyName.trim().length() <= 1) {
      companyName = "N/A";
    }

    return companyName;
  }

  private String formatToTwoDecimalNumber(double number)
  {
    String formattedNumber = "";

    DecimalFormat nf = new DecimalFormat("0.00");
    formattedNumber = nf.format(number);

    return formattedNumber;
  }

  private String formatToTwoDecimalNumber(String number)
  {
    String formattedNumber = "";
    double tempNumber = Double.parseDouble(number);
    DecimalFormat nf = new DecimalFormat("0.00");
    formattedNumber = nf.format(tempNumber);
    return formattedNumber;
  }

  private String formatNumber(double number)
  {
    String formattedNumber = "";

    //        if(number != 0) {
    DecimalFormat nf = new DecimalFormat(currency+"0.00");
    formattedNumber = nf.format(number);
    //            formattedNumber = StringUtilities.formatNumber(number);

    //            if(!(formattedNumber.indexOf(".") >= 0)) {
    //                return formattedNumber + ".00";
    //            }
    //        }

    return formattedNumber;
  }

  private String formatPercentage(String number)
  {
    String formattedNumber = number + "%";

    return formattedNumber;
  }

  private String formatNumber(String number)
  {
    String formattedNumber = "";
    double tempNumber = Double.parseDouble(number);
    DecimalFormat nf = new DecimalFormat(currency+"0.00");
    formattedNumber = nf.format(tempNumber);
/*
 * if(tempNumber != 0) {
 * formattedNumber = StringUtilities.formatNumber(tempNumber);
 * 
 * if(!(formattedNumber.indexOf(".") >= 0)) {
 * return formattedNumber + ".00";
 * }
 * }
 */
    return formattedNumber;
  }

  private boolean isValidNonZeroNumber(String value)
  {

    boolean status = true;

    if (value != null && value.length() > 0) {
      if (("0".equals(value)) || ("0.00".equals(value))
          || ("0.0".equals(value))) {
        status = false;
      }
    } else {
      status = false;
    }

    return status;
  }

  
  
	/**
	 * THIS METHOD IS CREATED FOR ADDING TOTAL AMOUNT FOR EACH LINEITEM IN
	 * SUPPLIMENT DOC. (BUG - 410439)
	 * 
	 * @param bmsDamageLine
	 * @param annotationDamageLine
	 * @param suppRequestLineItem
	 */
	private void setLineItemAmount(
			DamageLineInfoType bmsDamageLine,
			com.mitchell.schemas.estimate.DamageLineInfoType annotationDamageLine,
			LineItemInfoType suppRequestLineItem) {
		PartInfoType bmsPartInfoType = bmsDamageLine.getPartInfo();
		LaborInfoType bmsLaborInfoType = bmsDamageLine.getLaborInfo();
		
		
		double bmsPartPrice = 0;
		if(bmsPartInfoType != null){
			BigDecimal bmsGetPartPrice = bmsPartInfoType.getPartPrice();
			if(bmsGetPartPrice != null){
				bmsPartPrice = bmsGetPartPrice.doubleValue();
			}
		}
		
		BigDecimal annotdOrignalAmount = annotationDamageLine.getOriginalAmount() ;
		double annotatedOriginalAmount = annotdOrignalAmount != null ? annotdOrignalAmount.doubleValue() : bmsPartPrice;
		
		
		
		double setPreviousAmount;
		if(bmsLaborInfoType != null){
			BigDecimal bmslabourAmount = bmsLaborInfoType.getLaborAmt();			
			setPreviousAmount = bmslabourAmount != null ?  bmslabourAmount.doubleValue() : 0;
		}else{
			setPreviousAmount = annotatedOriginalAmount;
		}
		
		suppRequestLineItem
				.getLineItem()
				.setPreviousAmount(
						formatNumber(setPreviousAmount));

		double annotatedDeltaAmount;
		BigDecimal annotDamgeAmount = annotationDamageLine.getAmount();
		annotatedDeltaAmount = annotDamgeAmount != null ? annotDamgeAmount.doubleValue() - setPreviousAmount : 0;

		suppRequestLineItem
				.getLineItem()
				.setAmountDelta(
						formatNumber(annotatedDeltaAmount));

		suppRequestLineItem
				.getLineItem()
				.setAmount(formatNumber(annotDamgeAmount != null ? annotDamgeAmount.doubleValue() : 0));

	}

}
