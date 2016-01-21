package com.mitchell.services.business.partialloss.appraisalassignment;

import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.xmlbeans.XmlException;

import com.cieca.bms.AddressType;
import com.cieca.bms.AdjusterType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.BodyType;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CIECADocument.CIECA;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.ClaimantType;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.ConditionType;
import com.cieca.bms.ContactInfoType;
import com.cieca.bms.CoverageInfoType;
import com.cieca.bms.CoverageInfoType.Coverage;
import com.cieca.bms.DeductibleInfoType;
import com.cieca.bms.ExteriorInteriorType;
import com.cieca.bms.ExteriorInteriorType.Color;
import com.cieca.bms.FactsType;
import com.cieca.bms.GenericPartyType;
import com.cieca.bms.LicenseType;
import com.cieca.bms.LossInfoType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.PaintType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.PersonNameType;
import com.cieca.bms.PointOfImpactType;
import com.cieca.bms.PolicyInfoType;
import com.cieca.bms.PowertrainType;
import com.cieca.bms.PowertrainType.TransmissionInfo;
import com.cieca.bms.VINInfoType;
import com.cieca.bms.VINInfoType.VIN;
import com.cieca.bms.VehicleDamageAssignmentType;
import com.cieca.bms.VehicleDescType;
import com.cieca.bms.VehicleDescType.OdometerInfo;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.CommonTypeDefIntStr;
import com.mitchell.schemas.appraisalassignment.VehicleLocationGeoCodeType;
import com.mitchell.schemas.appraisalassignment.VehicleLocationGeoCodeType.AddressValidStatus;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsType;
import com.mitchell.schemas.dispatchservice.ScheduleConstraintsType;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class is utility class for handling Appraisal Assignment of Mandatory
 * fields specified in the WCAA Carrier Custom Settings.
 * 
 */
public final class AppraisalAssignmentMandFieldUtils implements IAppraisalAssignmentMandFieldUtils
{

  /**
   * Class mLogger.
   */
  private final java.util.logging.Logger mLogger = java.util.logging.Logger
      .getLogger(AppraisalAssignmentMandFieldUtils.class.getName());
  /**
   * Class Name.
   */
  private static final String CLASS_NAME = AppraisalAssignmentMandFieldUtils.class
      .getName();

  static final String COMPANY = "COMPANY";
  static final String USER = "USER";
  static final String OFFICE = "OFFICE";

  private IAppraisalAssignmentUtils appraisalAssignmentUtils;
  private AASCommonUtils commonUtils;

public void setAppraisalAssignmentUtils(
      IAppraisalAssignmentUtils appraisalAssignmentUtils)
  {
    this.appraisalAssignmentUtils = appraisalAssignmentUtils;
  }

 public void setCommonUtils(final AASCommonUtils commonUtils)
  {

    this.commonUtils = commonUtils;
  }

  // ------------------------------------------------------------------------------------------------------
  // New public method for Maxima - Pre-Estimate Project (Q3-2010)

  /**
   * <p>
   * This method performs business-rule validation of the input
   * MitchellEnvelopeDoc to a set of mandatory WCAA Custom Setting fields
   * </p>
   * <ul>
   * <br>
   * ** NOTE: <br>
   * ** Mandatory field business-rule validation is based on comparison of XML
   * fields <br>
   * ** in the provided Appraisal Assignment MitchellEnvelope with the <br>
   * ** WCAA Company-Level Custom Settings for confirmation that all <br>
   * ** required/mandatory fields are present.
   * </ul>
   * 
   * @param mitchellEnvDoc
   *          Object of MitchellEnvelopeDocument
   * @return boolean - returns TRUE if all mandatory fields are present.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public boolean hasAllMandatoryAppraisalAssignmentFields(
      final MitchellEnvelopeHelper meHelper,
      AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc,
      AdditionalTaskConstraintsDocument additionalTaskConstraintsDoc)
      throws MitchellException
  {

    boolean bREADYFLAG = true;

    final String METHOD_NAME = "hasAllMandatoryAppraisalAssignmentFields";
    mLogger.entering(CLASS_NAME, METHOD_NAME);
    boolean bAssignmentReadyHasMandatoryFields = false;
    ArrayList arr = new ArrayList();
    CIECADocument ciecaDocument = null;

    String companyCode = "";
    String wcaCSETConst = "";
    String currentCSETPropName = "";
    String clientClaimNumber_errlog = "";
    String workItemId_errlog = "";

    String vehLocTypeStr = null;
    AdminInfoType adminInfo = null;

    // Get Company Code from ME Doc
    companyCode = meHelper
        .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_COMPANY_CODE);

    // TODO - ERROR IF NO COMPANY CODE PRESENT!!!
    if (!checkNullEmptyString(companyCode)) {
      final String addErrInfo = "  Missing Company Code in Mitchell Envelope N/V pairs!!";
      final int errorCode = AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS_MSG
          + addErrInfo;

      final MitchellException mitchellExcep = new MitchellException(CLASS_NAME,
          METHOD_NAME, errorDescription);
      mitchellExcep.setType(errorCode);
      throw mitchellExcep;
    }

    // Retrieve Mandatory Custom Settings
    arr = retrieveCarrierSettingsForMandatoryFieldsInArrayList(companyCode);

    // If there are required custom fields,
    // Extract (3) XML Docs from ME Doc: ciecaDocument,
    // AdditionalAppraisalAssignmentInfoDocument and
    // additionalTaskConstraintsDoc

    if (arr.size() > 0) {

      try {
        // Getting ciecaDocument from MitchellEnvelope
        ciecaDocument = getCiecaFromME(meHelper);

        adminInfo = getCiecaAdminInfo(ciecaDocument);

        vehLocTypeStr = determineVehLocType(adminInfo, additionalAAInfoDoc);
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info("InspectionSiteType (VehLoc) is: " + vehLocTypeStr);
        }
        // ** Get available IDs for error logging **

        // get ClaimNumber from ciecaDocument
        clientClaimNumber_errlog = ciecaDocument.getCIECA()
            .getAssignmentAddRq().getClaimInfo().getClaimNum();

        workItemId_errlog = meHelper
            .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_MITCHELL_WORKITEMID);

        // Now cycle thru Mandatory Fields Array and validate each field
        // is Present in the MitchellEnvelopeDoc
        //
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info("Total Mandatory Field Count: " + arr.size());
          mLogger.info("Initially bREADYFLAG: " + bREADYFLAG);

          mLogger.info("clientClaimNumber: " + clientClaimNumber_errlog);
          mLogger.info("workItemId: " + workItemId_errlog);
        }

        // ==========================================================================================================
        //
        // Special Requirement [<WCA 100-08>] - Either Claimant Last
        // Name OR Insured Last Name is ALWAYS required.
        //
        boolean isPresentClaimantLastName = true;
        boolean isPresentInsuredLastName = true;

        // -------------------------------------------------------------------------
        // Claimant Fields - AssignmentAddRq/AdminInfo/Insured
        //
        currentCSETPropName = "";
        isPresentClaimantLastName = validateAdminInfoClaimantReqVals(adminInfo,
            "CONFIG_CLMT_LAST_NAME");
        if (!isPresentClaimantLastName) {
          currentCSETPropName = "CONFIG_CLMT_LAST_NAME";
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("CONFIG_CLMT_LAST_NAME is missing");
            mLogger.info("currentCSETPropName = " + currentCSETPropName);
          }
        }

        // -------------------------------------------------------------------------
        // Insured Fields - AssignmentAddRq/AdminInfo/Insured
        //
        isPresentInsuredLastName = validateAdminInfoInsuredReqVals(adminInfo,
            "CONFIG_INSD_LAST_NAME");
        if (!isPresentInsuredLastName) {
          currentCSETPropName = "CONFIG_INSD_LAST_NAME" + " or "
              + currentCSETPropName;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("CONFIG_INSD_LAST_NAME is missing");
            mLogger.info("currentCSETPropName =  " + currentCSETPropName);
          }
        }

        if ((!isPresentClaimantLastName) && (!isPresentInsuredLastName)) {
          bREADYFLAG = false;
          // return bREADYFLAG; // return after issueing a WARNING
          // ERRLOG
        }

        else {

          // ==========================================================================================================
          // Continue with all Mandatory fields based on WCAA Carrier
          // CustomSettings

          boolean isPresent = true;
          for (int i = 0; i < arr.size(); i++) {

            wcaCSETConst = arr.get(i).toString();
            String wcaCSETConstUpper = wcaCSETConst.toUpperCase();

            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger.info("a) Loop Counter: " + i + "  wcaCSETConst= "
                  + wcaCSETConst);
            }
            currentCSETPropName = wcaCSETConst;

            // -------------------------------------------------------------------------
            // Validate InspectionSite Type (Vehicle Location) is
            // present
            //
            if (wcaCSETConstUpper.equals("CONFIG_INSPEC_TYPE")) {
              isPresent = validateAAInfoReqVals(adminInfo, additionalAAInfoDoc);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSPEC_TYPE is missing");
              }
            }

            // -------------------------------------------------------------------------
            // AdditionalTaskConstraints fields
            else if (wcaCSETConstUpper.equals("CONFIG_ASSIGN_DUR")) {
              isPresent = validateAddTskConstrReqVals(
                  additionalTaskConstraintsDoc, wcaCSETConst);
              // isPresent = false; //debug -- SAVE!! test break
              // out
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_ASSIGN_DUR is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_URG")) {
              isPresent = validateAddTskConstrReqVals(
                  additionalTaskConstraintsDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_URG is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // ClaimInfo Fields - AssignmentAddRq/ClaimInfo/
            else if (wcaCSETConstUpper.equals("CONFIG_POLICY_NUM")) {
              isPresent = validateClaimInfoReqVals(ciecaDocument, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_POLICY_NUM is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_DEDUCTIBLE_AMT")) {
              isPresent = validateClaimInfoReqVals(ciecaDocument, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_DEDUCTIBLE_AMT is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_PRIMARY_POI")) {
              isPresent = validateClaimInfoReqVals(ciecaDocument, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_PRIMARY_POI is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_SECONDARY_POI")) {
              isPresent = validateClaimInfoReqVals(ciecaDocument, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_SECONDARY_POI is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // Insured Fields - AssignmentAddRq/AdminInfo/Insured
            else if ((wcaCSETConstUpper.equals("CONFIG_INSD_FIRST_NAME"))
                || (wcaCSETConstUpper.equals("CONFIG_INSD_LAST_NAME"))) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_FIRST_NAME is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_ADDRESS1")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_ADDRESS1 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_ADDRESS2")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_ADDRESS2 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_CITY")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_CITY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_STATE")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_STATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_ZIP")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_ZIP is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_HOME_PHONE")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_HOME_PHONE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_WORK_PH")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_WORK_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_CELL_PH")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_CELL_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_FAX_PH")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_FAX_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSD_EMAIL")) {
              isPresent = validateAdminInfoInsuredReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSD_EMAIL is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // Claimant Fields - AssignmentAddRq/AdminInfo/Insured
            else if ((wcaCSETConstUpper.equals("CONFIG_CLMT_FIRST_NAME"))
                || (wcaCSETConstUpper.equals("CONFIG_CLMT_LAST_NAME"))) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_FIRST_NAME is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_ADDRESS1")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent) {
                mLogger.info("CONFIG_CLMT_ADDRESS1 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_ADDRESS2")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_ADDRESS2 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_CITY")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_CITY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_STATE")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_STATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_ZIP")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_ZIP is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_HOME_PH")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_HOME_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_WORK_PH")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_WORK_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_CELL_PH")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_CELL_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_FAX")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_FAX is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_CLMT_EMAIL")) {
              isPresent = validateAdminInfoClaimantReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_CLMT_EMAIL is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // Owner Fields - AssignmentAddRq/AdminInfo/Owner/Party/
            else if ((wcaCSETConstUpper.equals("CONFIG_OWNR_FIRST_NAME"))) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_FIRST_NAME is missing ");
              }
            } else if ((wcaCSETConstUpper.equals("CONFIG_OWNR_LAST_NAME"))) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_LAST_NAME is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_ADDRESS1")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_ADDRESS1 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_ADDRESS2")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_ADDRESS2 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_CITY")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_CITY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_STATE")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_STATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_ZIP")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_ZIP is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_HOME_PH")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_HOME_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_WORK_PH")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_WORK_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_CELL_PH")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_CELL_PH is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_FAX")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_FAX is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_OWNR_EMAIL")) {
              isPresent = validateAdminInfoOwnerReqVals(adminInfo, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_OWNR_EMAIL is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // AssignmentMemo Field -
            // AssignmentAddRq/VehicleDamageAssignment/AssignmentMemo
            else if (wcaCSETConstUpper.equals("CONFIG_NOTES")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              // isPresent =
              // test_validateVehInfoReqVals(ciecaDocument,
              // additionalAAInfoDoc, wcaCSETConst);
              // isPresent = false; //debug -- SAVE!! test break
              // out
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_NOTES is missing ");
              }
            }
            // -------------------------------------------------------------------------
            // VehicleInfo Fields -
            // AssignmentAddRq/VehicleDamageAssignment/VehicleInfo

            else if (wcaCSETConstUpper.equals("CONFIG_VIN_NUM")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VIN_NUM is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_YEAR")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_YEAR is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_MAKE")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_MAKE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_MODEL")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_MODEL is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_SUB_MDL")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_SUB_MDL is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_TYPE")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_TYPE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_BODY_STY")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_BODY_STY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_ENG")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_ENG is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_TRANS")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_TRANS is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_DRV_TRAIN")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_DRV_TRAIN is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_MIL")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_MIL is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_EXT_COLOR")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_EXT_COLOR is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_LIC_PLATE")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_LIC_PLATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_VEHICLE_LIC_PLATE_ST")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_VEHICLE_LIC_PLATE_ST is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_DRIV_STATUS")) {
              isPresent = validateVehInfoReqVals(ciecaDocument,
                  additionalAAInfoDoc, wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_DRIV_STATUS is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // Adjuster Fields - AssignmentAddRq/AdminInfo/Adjuster
            else if (wcaCSETConstUpper.equals("CONFIG_ADJUSTER")) {
              isPresent = validateAdminInfoAdjusterReqVals(adminInfo,
                  wcaCSETConst);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_ADJUSTER is missing ");
              }
            }
            // -------------------------------------------------------------------------
            // InspectionSite Contact Fields -
            // AdminInfo/InspectionSite/Party/ContactInfo
            else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_CONT_FIRST")) {
              isPresent = validateInspectSiteContactReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSPEC_CONT_FIRST is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_CONT_LAST")) {
              isPresent = validateInspectSiteContactReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSPEC_CONT_LAST is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_CONT_PH")) {
              isPresent = validateInspectSiteContactReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("CONFIG_INSPEC_CONT_PH is missing ");
              }
            }

            // -------------------------------------------------------------------------
            // InspectionSite PersonInfo Fields -
            // AdminInfo/InspectionSite/Party/PersonInfo
            else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ADDRESS1")
                && vehLocTypeStr.equalsIgnoreCase("HOME")) {
              isPresent = validateInspectSitePersonInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("HOME- CONFIG_INSPEC_ADDRESS1 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ADDRESS2")
                && vehLocTypeStr.equalsIgnoreCase("HOME")) {
              isPresent = validateInspectSitePersonInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("HOME- CONFIG_INSPEC_ADDRESS2 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_CITY")
                && vehLocTypeStr.equalsIgnoreCase("HOME")) {
              isPresent = validateInspectSitePersonInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("HOME- CONFIG_INSPEC_CITY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_STATE")
                && vehLocTypeStr.equalsIgnoreCase("HOME")) {
              isPresent = validateInspectSitePersonInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("HOME- CONFIG_INSPEC_STATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ZIP")
                && vehLocTypeStr.equalsIgnoreCase("HOME")) {
              isPresent = validateInspectSitePersonInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("HOME- CONFIG_INSPEC_ZIP is missing ");
              }
            }
            // -------------------------------------------------------------------------
            // InspectionSite OrgInfo Fields -
            // AdminInfo/InspectionSite/Party/OrgInfo
            else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_NAME")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_NAME is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ADDRESS1")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_ADDRESS1 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ADDRESS2")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_ADDRESS2 is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_CITY")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_CITY is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_STATE")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_STATE is missing ");
              }
            } else if (wcaCSETConstUpper.equals("CONFIG_INSPEC_ZIP")
                && vehLocTypeStr.equalsIgnoreCase("OTHER")) {
              isPresent = validateInspectSiteOrgInfoReqVals(adminInfo,
                  wcaCSETConst, vehLocTypeStr);
              if (!isPresent
                  && mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("OTHER - CONFIG_INSPEC_ZIP is missing ");
              }
            }

            if (!isPresent) {
              bREADYFLAG = false;
            }
            if (!bREADYFLAG) {
              break;
            }

          } // End For Loop for Mandatory Field CustomSetting checking
        }

      } catch (final java.lang.Exception e) {

        final MitchellException mitchellExcep = new MitchellException(
            AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS,
            CLASS_NAME, METHOD_NAME,
            "Unexpected exception processing Mandatory Field Settings", e);
        throw mitchellExcep;

      }

    }

    // Log a WARNING Error log for first instance where a Required/Mandatory
    // Fields is missing
    //
    if (!bREADYFLAG) {
      final StringBuffer addWarningMsg = new StringBuffer();
      addWarningMsg
          .append(
              "WARNING!! Missing value for mandatory WCA Carrier CustomSetting Prop Name:[ ")
          .append(currentCSETPropName).append(" ]").append(", CompanyCode= ")
          .append(companyCode).append(", clientClaimNumber= ")
          .append(clientClaimNumber_errlog).append(", and workItemID= ")
          .append(workItemId_errlog);
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {

        mLogger.info(addWarningMsg.toString());
      }
      final int errorCode = AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS_MSG
          + addWarningMsg;

      MitchellException mitchellExcep = new MitchellException(CLASS_NAME,
          METHOD_NAME, errorDescription);
      mitchellExcep.setCompanyCode(companyCode);
      mitchellExcep.setWorkItemId(workItemId_errlog);
      mitchellExcep.setType(errorCode);
      mitchellExcep.setSeverity(MitchellException.SEVERITY.WARNING);
      commonUtils.logError(errorCode, CLASS_NAME, METHOD_NAME,
          errorDescription, mitchellExcep);
      mitchellExcep = null;
    }

    bAssignmentReadyHasMandatoryFields = bREADYFLAG;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("hasAllMandatoryAppraisalAssignmentFields(), Return value, bREADYFLAG= "
              + bREADYFLAG);
    }

    return bAssignmentReadyHasMandatoryFields;
  }

  /**
   * This utility method retrieves WCA Custom Settings for Mandatory Fields
   * based on Company Code.
   * 
   * @param coCode
   *          Mitchell Company Code for the carrier.
   * @return Returns Array of Mandatory Carrier Settings (having a required
   *         value of "Y")
   * @throws MitchellException
   *           Throws an Exception in case of unable to retrieve carrier
   *           custom settings.
   */
  public java.util.ArrayList retrieveCarrierSettingsForMandatoryFieldsInArrayList(
      final String coCode)
      throws MitchellException
  {
    final String methodName = "retrieveCarrierSettingsForMandatoryFieldsInArrayList";
    mLogger.entering(CLASS_NAME, methodName);
    long startTime = System.currentTimeMillis();
    long subStartTime = 0;

    final ArrayList arr = new ArrayList();
    final String posResp = "Y";

    try {

      subStartTime = System.currentTimeMillis();
      final SettingValue[] valueArray = appraisalAssignmentUtils
          .getSettingsByGroupAndProfile(coCode, coCode, COMPANY,
              AppraisalAssignmentConstants.CSET_GROUP_NAME_WCA_REQD_FLDS);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                methodName,
                "retrieveCarrierSettingsForMandatoryFieldsInArrayList.getSettingsByGroupAndProfile:"
                    + coCode, System.currentTimeMillis() - subStartTime);
      }

      // Note: Setting for RefID 1-4 - Not currently included in Mandatory
      // Fields checking

      subStartTime = System.currentTimeMillis();

      for (int i = 4; i < valueArray.length; i++) {
        if (valueArray[i] == null || null == valueArray[i].getPropertyName()) {
          continue;
        }

        String propertyName = valueArray[i].getPropertyName().toUpperCase();

        // Retrieve Setting for RefID 5 - CONFIG_ADJUSTER
        if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_5)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_5);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info("CSET_SETTING_WCA_REQD_FLDS_REFID_5 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_5
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }

        }
        // Retrieve Setting for RefID 6 - CONFIG_POLICY_NUM
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_6)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_6);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_6 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_6
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 7 - CONFIG_DEDUCTIBLE_AMT
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_7)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_7);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_7 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_7
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 8 - CONFIG_INSD_FIRST_NAME
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_8)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_8);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_8 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_8
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 9 - CONFIG_INSD_ADDRESS1
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_9)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_9);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_9 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_9
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 10 - CONFIG_INSD_ADDRESS2
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_10)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_10);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_10 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_10
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 11 - CONFIG_INSD_CITY
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_11)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_11);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_11 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_11
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 12 - CONFIG_INSD_STATE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_12)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_12);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_12 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_12
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 13 - CONFIG_INSD_ZIP
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_13)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_13);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_13 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_13
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 14 - CONFIG_HOME_PHONE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_14)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_14);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_14 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_14
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 15 - CONFIG_INSD_WORK_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_15)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_15);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_15 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_15
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 16 - CONFIG_INSD_CELL_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_16)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_16);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_16 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_16
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 17 - CONFIG_INSD_FAX_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_17)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_17);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_17 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_17
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 18 - CONFIG_INSD_EMAIL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_18)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_18);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_18 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_18
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 19 - CONFIG_CLMT_FIRST_NAME
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_19)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_19);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_19 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_19
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 20 - CONFIG_CLMT_ADDRESS1
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_20)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_20);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_20 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_20
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 21 - CONFIG_CLMT_ADDRESS2
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_21)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_21);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_21 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_21
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 22 - CONFIG_CLMT_CITY
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_22)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_22);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_22 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_22
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 23 - CONFIG_CLMT_STATE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_23)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_23);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_23 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_23
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 24 - CONFIG_CLMT_ZIP
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_24)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_24);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_24 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_24
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 25 - CONFIG_CLMT_HOME_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_25)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_25);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_25 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_25
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 26 - CONFIG_CLMT_WORK_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_26)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_26);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_26 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_26
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 27 - CONFIG_CLMT_CELL_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_27)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_27);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_27 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_27
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 28 - CONFIG_CLMT_FAX
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_28)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_28);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_28 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_28
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 29 - CONFIG_CLMT_EMAIL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_29)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_29);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_29 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_29
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 30 - CONFIG_OWNR_FIRST_NAME
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_30)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_30);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_30 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_30
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 31 - CONFIG_OWNR_LAST_NAME
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_31)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_31);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_31 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_31
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 32 - CONFIG_OWNR_ADDRESS1
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_32)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_32);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_32 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_32
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 33 - CONFIG_OWNR_ADDRESS2
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_33)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_33);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_33 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_33
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 34 - CONFIG_OWNR_CITY
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_34)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_34);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_34 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_34
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 35 - CONFIG_OWNR_STATE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_35)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_35);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_35 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_35
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 36 - CONFIG_OWNR_ZIP
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_36)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_36);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_36 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_36
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 37 - CONFIG_OWNR_HOME_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_37)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_37);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_37 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_37
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 38 - CONFIG_OWNR_WORK_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_38)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_38);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_38 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_38
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 39 - CONFIG_OWNR_CELL_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_39)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_39);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_39 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_39
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 40 - CONFIG_OWNR_FAX
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_40)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_40);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_40 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_40
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 41 - CONFIG_OWNR_EMAIL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_41)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_41);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_41 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_41
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 42 - CONFIG_VIN_NUM
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_42)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_42);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_42 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_42
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 43 - CONFIG_VEHICLE_TYPE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_43)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_43);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_43 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_43
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 44 - CONFIG_VEHICLE_YEAR
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_44)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_44);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_44 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_44
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 45 - CONFIG_VEHICLE_MAKE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_45)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_45);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_45 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_45
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 46 - CONFIG_VEHICLE_MODEL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_46)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_46);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_46 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_46
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 47 - CONFIG_VEHICLE_SUB_MDL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_47)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_47);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_47 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_47
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 48 - CONFIG_VEHICLE_BODY_STY
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_48)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_48);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_48 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_48
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 49 - CONFIG_VEHICLE_ENG
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_49)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_49);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_49 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_49
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 50 - CONFIG_VEHICLE_TRANS
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_50)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_50);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_50 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_50
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 51 - CONFIG_VEHICLE_DRV_TRAIN
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_51)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_51);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_51 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_51
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 52 - CONFIG_VEHICLE_MIL
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_52)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_52);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_52 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_52
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 53 - CONFIG_EXT_COLOR
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_53)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_53);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_53 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_53
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 54 - CONFIG_VEHICLE_LIC_PLATE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_54)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_54);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_54 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_54
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 55 - CONFIG_VEHICLE_LIC_PLATE_ST
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_55)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_55);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_55 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_55
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 56 - CONFIG_DRIV_STATUS
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_56)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_56);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_56 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_56
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 57 - CONFIG_PRIMARY_POI
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_57)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_57);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_57 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_57
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 58 - CONFIG_SECONDARY_POI
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_58)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_58);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_58 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_58
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 59 - CONFIG_DT_REPORTED
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_59)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_59);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_59 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_59
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 60 - CONFIG_URG
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_60)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_60);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_60 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_60
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 61 - CONFIG_ASSIGN_DUR
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_61)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_61);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_61 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_61
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 62 - CONFIG_INSPEC_ADDRESS1
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_62)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_62);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_62 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_62
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 63 - CONFIG_INSPEC_ADDRESS2
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_63)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_63);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_63 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_63
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 64 - CONFIG_INSPEC_CITY
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_64)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_64);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_64 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_64
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 65 - CONFIG_INSPEC_STATE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_65)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_65);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_65 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_65
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 66 - CONFIG_INSPEC_ZIP
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_66)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_66);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_66 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_66
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 67 - CONFIG_INSPEC_TYPE
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_67)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_67);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_67 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_67
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 68 - CONFIG_INSPEC_NAME
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_68)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_68);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_68 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_68
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 69 - CONFIG_INSPEC_CONT_FIRST
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_69)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_69);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_69 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_69
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

        // Retrieve Setting for RefID 70 - CONFIG_INSPEC_CONT_LAST
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_70)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_70);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_70 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_70
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 71 - CONFIG_INSPEC_CONT_PH
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_71)) {

          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_71);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_71 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_71
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }
        // Retrieve Setting for RefID 72 - CONFIG_NOTES
        else if (propertyName
            .equals(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_72)) {
          // Add mandatory setting to arraylist
          if (valueArray[i].getValue() != null
              && (valueArray[i].getValue().toUpperCase().equals(posResp))) {
            arr.add(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_72);
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info(" CSET_SETTING_WCA_REQD_FLDS_REFID_72 = [ "
                      + AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_72
                      + " ] and has value = " + valueArray[i].getValue());
            }
          }
        }

      }

      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, methodName,
            "retrieveCarrierSettingsForMandatoryFieldsInArrayList.processSettings:"
                + coCode, System.currentTimeMillis() - subStartTime);
      }

    } catch (final java.lang.NumberFormatException numberFormatException) {
      throw new MitchellException(CLASS_NAME,
          "retrieveCarrierSettingsForMandatoryFieldsInArrayList",
          "Unable to retrieve settings for carrrier (NumberFormatException).",
          numberFormatException);
    } catch (final Exception exception) {
      throw new MitchellException(CLASS_NAME,
          "retrieveCarrierSettingsForMandatoryFieldsInArrayList",
          "Unable to retrieve settings for carrrier.", exception);
    }

    if (MonitoringLogger.isLoggableTimer()) {
      MonitoringLogger.doLog(CLASS_NAME, methodName,
          "retrieveCarrierSettingsForMandatoryFieldsInArrayList.fullProcessingTime:"
              + coCode, System.currentTimeMillis() - startTime);
    }

    mLogger.exiting(CLASS_NAME, methodName);
    return arr;
  }

  /**
   * Checks for null and empty String objects
   * 
   * @param inputString
   * @return boolean
   */
  private static boolean checkNullEmptyString(final String inputString)
  {
    if (inputString != null && !inputString.trim().equals("")) {
      return true;
    } else {
      return false;
    }
  }

  private String determineVehLocType(final AdminInfoType adminInfo,
      final AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME + " :: determineVehLocType  ");
    }
    String vehLocTypeStr = "";
    String locationType = null;

    AdditionalAppraisalAssignmentInfoType additionalAAInfoType = null;
    if (additionalAAInfoDoc != null) {
      additionalAAInfoType = additionalAAInfoDoc
          .getAdditionalAppraisalAssignmentInfo();
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: determineVehLocType  AdditionalAppraisalAssignmentInfoDocument is missing");
      }
      return vehLocTypeStr;
    }

    if (additionalAAInfoType != null) {
      locationType = additionalAAInfoType.getAssignmentDetails()
          .getInspectionSiteType();
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: determineVehLocType  AdditionalAppraisalAssignmentInfoType is missing");
      }
      return vehLocTypeStr;
    }

    if (adminInfo.isSetInspectionSite()) {
      final GenericPartyType inspectSite = adminInfo.getInspectionSite();
      final PartyType inspectSiteParty = inspectSite.getParty();

      if (locationType != null) {
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info("GOT locationType");
        }
        if (locationType.equalsIgnoreCase("Home")) {
          if (inspectSiteParty.isSetPersonInfo()) {
            vehLocTypeStr = "HOME";
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger.info("GOT PersonInfo");
            }
          }
        } else {
          if (inspectSiteParty.isSetOrgInfo()) {
            vehLocTypeStr = "OTHER";
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger.info("GOT OrgInfo ");
            }
          }
        }
      } else {
        /**
         * This is the case when AdditionalInfo is not present in ME
         * then we need to populate the following tags from ME
         * AssignmentAddRq/AdminInfo/InspectionSite/ into UIXML
         */
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info("MISSING locationType() ");
        }

        // AdminInfo/InspectionSite/Party/PersonInfo
        if (inspectSiteParty.isSetPersonInfo()) {
          vehLocTypeStr = "HOME";
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("GOT PersonInfo");
          }
        } else if (inspectSiteParty.isSetOrgInfo()) {
          vehLocTypeStr = "OTHER";
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("GOT OrgInfo");
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("vehLocTypeStr = " + vehLocTypeStr);
      mLogger.info("Exiting " + CLASS_NAME + " :: determineVehLocType");
    }
    return vehLocTypeStr;
  }

  private boolean validateAAInfoReqVals(final AdminInfoType adminInfo,
      final AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME + " :: validateAAInfoReqVals");
    }
    boolean isPresent = false;
    String locationType = null;

    AdditionalAppraisalAssignmentInfoType additionalAAInfoType = null;
    if (additionalAAInfoDoc != null) {
      additionalAAInfoType = additionalAAInfoDoc
          .getAdditionalAppraisalAssignmentInfo();
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateAAInfoReqVals  AdditionalAppraisalAssignmentInfoDocument is missing");
      }
      return isPresent;
    }

    if (additionalAAInfoType != null) {
      locationType = additionalAAInfoType.getAssignmentDetails()
          .getInspectionSiteType();
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateAAInfoReqVals  AdditionalAppraisalAssignmentInfoType is missing");
      }
      return isPresent;
    }

    if (adminInfo.isSetInspectionSite()) {
      final GenericPartyType inspectSite = adminInfo.getInspectionSite();
      inspectSite.getParty();

      if (locationType != null) {
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger
              .info("validateAAInfoReqVals - GOT InspectionSite locationType");
        }
        isPresent = true;
      }
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger.info("Exiting " + CLASS_NAME
            + " :: validateAAInfoReqVals  AdminInfoType is missing");
      }
      return isPresent;
    }

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME + " :: validateAAInfoReqVals");
    }
    return isPresent;
  }

  private boolean validateAddTskConstrReqVals(
      final AdditionalTaskConstraintsDocument additionalTaskConstraintsDoc,
      final String wcaCSETConst)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("Entering " + CLASS_NAME + " :: validateAddTskConstrReqVals");
    }
    boolean isPresent = false;

    AdditionalTaskConstraintsType additionalTaskConstraintsType = null;
    if (additionalTaskConstraintsDoc != null) {
      additionalTaskConstraintsType = additionalTaskConstraintsDoc
          .getAdditionalTaskConstraints();
    } else {
      return isPresent;
    }

    // case 1 duration - CONFIG_ASSIGN_DUR
    // case 2 priority - CONFIG_URG

    if (additionalTaskConstraintsType != null) {
      if (additionalTaskConstraintsType.isSetScheduleConstraints()) {
        final ScheduleConstraintsType scheduleConstraintsTypeME = additionalTaskConstraintsType
            .getScheduleConstraints();
        if (scheduleConstraintsTypeME != null) {
          if (scheduleConstraintsTypeME.isSetDuration()
              && wcaCSETConst.equalsIgnoreCase("CONFIG_ASSIGN_DUR")) {
            isPresent = true;
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger.info("validateAddTskConstrReqVals-  GOT Duration");
            }
            return isPresent;
          }

          if (scheduleConstraintsTypeME.isSetPriority()
              && wcaCSETConst.equalsIgnoreCase("CONFIG_URG")) {
            isPresent = true;
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger.info("validateAddTskConstrReqVals - GOT Priority");
            }
            return isPresent;
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME + " :: validateAddTskConstrReqVals");
    }
    return isPresent;
  }

  private boolean validateClaimInfoReqVals(final CIECADocument ciecaDoc,
      final String wcaCSETConst)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME + " :: validateClaimInfoReqVals");
    }
    boolean isPresent = false;

    // case 1 - CONFIG_POLICY_NUM
    // case 2 - CONFIG_DEDUCTIBLE_AMT
    // case 3 - CONFIG_PRIMARY_POI
    // case 4 - CONFIG_SECONDARY_POI

    final CIECA cieca = ciecaDoc.getCIECA();
    final AssignmentAddRq assignmentRq = cieca.getAssignmentAddRq();

    if (assignmentRq.isSetClaimInfo()) {
      final ClaimInfoType claimInfo = assignmentRq.getClaimInfo();
      if (claimInfo != null) {
        if (claimInfo.isSetPolicyInfo()
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_POLICY_NUM") || wcaCSETConst
                .equalsIgnoreCase("CONFIG_DEDUCTIBLE_AMT"))) {
          final PolicyInfoType policyInfo = claimInfo.getPolicyInfo();
          if (policyInfo != null) {
            if (policyInfo.isSetPolicyNum()
                && wcaCSETConst.equalsIgnoreCase("CONFIG_POLICY_NUM")) {
              isPresent = true;
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("validateClaimInfoReqVals - GOT PolicyNumber");
              }
              return isPresent;
            }

            if (policyInfo.isSetCoverageInfo()) {
              final CoverageInfoType coverageInfo = policyInfo
                  .getCoverageInfo();

              if (coverageInfo != null
                  && coverageInfo.sizeOfCoverageArray() > 0) {
                final Coverage coverage = coverageInfo.getCoverageArray(0);

                if (coverage != null) {
                  if (coverage.isSetDeductibleInfo()) {
                    // DeductibleAmt
                    final DeductibleInfoType deductibleInfo = coverage
                        .getDeductibleInfo();
                    if (deductibleInfo != null) {
                      if (deductibleInfo.isSetDeductibleAmt()
                          && wcaCSETConst
                              .equalsIgnoreCase("CONFIG_DEDUCTIBLE_AMT")) {
                        if (deductibleInfo.getDeductibleAmt() != null) {
                          isPresent = true;
                          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                            mLogger
                                .info("validateClaimInfoReqVals - GOT Deductible Amount");
                          }
                          return isPresent;
                        }
                      }
                    }
                  }
                }
              }
            }
          } // if(policyInfo != null)

        } // if(claimInfo.isSetPolicyInfo())

        // ------------------------------------------
        if (claimInfo.isSetLossInfo()
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_PRIMARY_POI") || wcaCSETConst
                .equalsIgnoreCase("CONFIG_SECONDARY_POI"))) {
          final LossInfoType lossInfo = claimInfo.getLossInfo();

          if (lossInfo != null) {
            if (lossInfo.isSetFacts()) {
              final FactsType facts = lossInfo.getFacts();

              if (facts != null) {
                if (facts.isSetPrimaryPOI()) {
                  if (facts.getPrimaryPOI() != null) {
                    if ((facts.getPrimaryPOI().getPOICode() != null)
                        && (wcaCSETConst.equalsIgnoreCase("CONFIG_PRIMARY_POI"))) {
                      isPresent = true;
                      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                        mLogger
                            .info("validateClaimInfoReqVals - GOT Primary POI");
                      }
                      return isPresent;
                    }
                  }
                }

                if (facts.sizeOfSecondaryPOIArray() > 0) {
                  final PointOfImpactType[] secondaryPOIType = facts
                      .getSecondaryPOIArray();
                  final String[] secondaryPOICode = new String[secondaryPOIType.length];
                  for (int i = 0; i < secondaryPOIType.length; i++) {
                    final PointOfImpactType sec = facts.getSecondaryPOIArray(i);
                    secondaryPOICode[i] = sec.getPOICode();
                  }
                  if ((secondaryPOICode.toString().length() > 0)
                      && (wcaCSETConst.equalsIgnoreCase("CONFIG_SECONDARY_POI"))) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateClaimInfoReqVals - GOT Secondary POI");
                    }
                    return isPresent;
                  }
                }
              }
            }

          } // if(lossInfo != null)

        } // if(claimInfo.isSetLossInfo()) {

      } // if(claimInfo != null)
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME + " :: validateClaimInfoReqVals");
    }
    return isPresent;
  }

  private boolean validateAdminInfoInsuredReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateAdminInfoInsuredReqVals");
    }
    boolean isPresent = false;
    PartyType insuredParty = null;

    if (adminInfo.isSetPolicyHolder())
      insuredParty = adminInfo.getPolicyHolder().getParty();
    else if (adminInfo.isSetInsured())
      insuredParty = adminInfo.getInsured().getParty();

    if (insuredParty != null)
      isPresent = validateInsuredPartyType(insuredParty, wcaCSETConst);

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME
          + " :: validateAdminInfoInsuredReqVals");
    }
    return isPresent;
  }

  private boolean validateInsuredPartyType(final PartyType insuredParty,
      final String wcaCSETConst)
      throws Exception
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME + " validateInsuredPartyType");
    }
    boolean isPresent = false;

    String phoneNumber = null;
    String emailAddress = null;
    AddressType communicationAddress = null;
    String qualifierString = null;
    CommunicationsType communication = null;
    CommunicationsType[] communicationArray = null;

    final PersonInfoType insuredPersonInfo = insuredParty.getPersonInfo();
    if (insuredPersonInfo != null) {
      final PersonNameType personName = insuredPersonInfo.getPersonName();

      if (personName != null) {

        if (checkNullEmptyString(personName.getFirstName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_FIRST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateInsuredPartyType - GOT Insured First Name");
          }
          return isPresent;
        }

        if (checkNullEmptyString(personName.getLastName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_LAST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateInsuredPartyType - GOT Insured Last Name");
          }
          return isPresent;
        }
      }
      communicationArray = insuredPersonInfo.getCommunicationsArray();
    }

    boolean isSetAddressValues = false;
    boolean isSetContactDetails = false;
    // SCR  0031584 :: Picking the Address from PersonInfo/ComunicationsArray
    if (((communicationArray != null && communicationArray.length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_INSD_ADDRESS1"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_ADDRESS2"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_CITY"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_STATE")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_INSD_ZIP"))))) {

      isSetAddressValues = true;
    }
    // SCR  0031584 :: Picking the Contact details from Party/ContactInfo/ComunicationsArray
    if (((insuredParty.getContactInfoArray() != null && insuredParty
        .getContactInfoArray().length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_HOME_PHONE"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_WORK_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_CELL_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_FAX_PH")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_INSD_EMAIL"))))) {
      isSetContactDetails = true;
      communicationArray = insuredParty.getContactInfoArray(0)
          .getCommunicationsArray();
    }

    if (isSetAddressValues || isSetContactDetails) {
      for (CommunicationsType element : communicationArray) {
        communication = element;

        if (communication != null) {
          /* Get the Qualifier for the communication */
          qualifierString = communication.getCommQualifier();

          if (qualifierString != null) {
            /*
             * Check the qualifier value for AL, HP, WP, CP,
             * FX, EM
             */
            if ("AL".equalsIgnoreCase(qualifierString)) {
              communicationAddress = communication.getAddress();

              if (communicationAddress != null) {
                /*
                 * Set Address,City,State, Zip where
                 * qualifier value is AL
                 */
                if (checkNullEmptyString(communicationAddress.getAddress1())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_ADDRESS1"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInsuredPartyType - GOT Insured Address_1");
                  }
                  return isPresent;
                }
                if (checkNullEmptyString(communicationAddress.getAddress2())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_ADDRESS2"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInsuredPartyType - GOT Insured Address_2");
                  }
                  return isPresent;
                }
                if (checkNullEmptyString(communicationAddress.getCity())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_CITY"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger.info("validateInsuredPartyType - GOT Insured City");
                  }
                  return isPresent;
                }
                if (checkNullEmptyString(communicationAddress
                    .getStateProvince())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_STATE"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInsuredPartyType - GOT Insured State");
                  }
                  return isPresent;
                }
                if (checkNullEmptyString(communicationAddress.getPostalCode())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_ZIP"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInsuredPartyType - GOT Insured ZipCode");
                  }
                  return isPresent;
                }
              }
            } else if ("HP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_HOME_PHONE"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateInsuredPartyType - GOT Home Phone");
                }
                return isPresent;
              }
            } else if ("WP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_WORK_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateInsuredPartyType - GOT INSD WORK Phone");
                }
                return isPresent;
              }
            } else if ("CP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_CELL_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateInsuredPartyType - GOT INSD_CELL Phone");
                }
                return isPresent;
              }
            } else if ("FX".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_FAX_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateInsuredPartyType - GOT INSD FAX Phone");
                }
                return isPresent;
              }
            } else if ("EM".equalsIgnoreCase(qualifierString)) {
              emailAddress = communication.getCommEmail();
              if (checkNullEmptyString(emailAddress)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSD_EMAIL"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateInsuredPartyType - GOT INSD EMAIL Phone");
                }
                return isPresent;
              }
            }

          }
        } // if(communication != null)
      } // for(int i=0;i<communicationArray.length;i++)
    } // if(personInfo.sizeOfCommunicationsArray() > 0)

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting " + CLASS_NAME + " validateInsuredPartyType");
    }
    return isPresent;
  }

  private boolean validateAdminInfoClaimantReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateAdminInfoClaimantReqVals");
    }
    if (adminInfo.isSetClaimant()) {
      final ClaimantType claimant = adminInfo.getClaimant();
      if (claimant != null && claimant.getParty() != null) {
        isPresent = validateClaimantPartyType(claimant.getParty(), wcaCSETConst);
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME
          + " :: validateAdminInfoClaimantReqVals");
    }
    return isPresent;
  }

  private boolean validateClaimantPartyType(final PartyType claimantParty,
      final String wcaCSETConst)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("Entering " + CLASS_NAME
              + " validateClaimantPartyType wcaCSETConst= [ " + wcaCSETConst
              + " ]");
    }
    String phoneNumber = null;
    String emailAddress = null;
    AddressType communicationAddress = null;
    String qualifierString = null;
    CommunicationsType communication = null;
    CommunicationsType[] communicationArray = null;

    final PersonInfoType claimantPersonInfo = claimantParty.getPersonInfo();
    if (claimantPersonInfo != null) {
      final PersonNameType personName = claimantPersonInfo.getPersonName();

      if (personName != null) {
        if (checkNullEmptyString(personName.getFirstName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_FIRST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateClaimantPartyType - GOT Claimant First Name");
          }
          return isPresent;
        }

        if (checkNullEmptyString(personName.getLastName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_LAST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateClaimantPartyType - GOT Claimant Last Name");
          }
          return isPresent;
        }
      }
      communicationArray = claimantPersonInfo.getCommunicationsArray();
    }

    boolean isSetAddressValues = false;
    boolean isSetContactDetails = false;
    // SCR  0031584 :: Picking the Address from PersonInfo/ComunicationsArray
    if (((communicationArray != null && communicationArray.length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_CLMT_ADDRESS1"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_ADDRESS2"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_CITY"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_STATE")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_CLMT_ZIP"))))) {
      isSetAddressValues = true;

    }

    // SCR  0031584 :: Picking the Contact details from Party/ContactInfo/ComunicationsArray
    if (((claimantParty.getContactInfoArray() != null && claimantParty
        .getContactInfoArray().length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_CLMT_HOME_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_WORK_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_CELL_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_FAX")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_CLMT_EMAIL"))))) {
      isSetContactDetails = true;
      communicationArray = claimantParty.getContactInfoArray(0)
          .getCommunicationsArray();
    }
    if (isSetAddressValues || isSetContactDetails) {
      for (CommunicationsType element : communicationArray) {
        communication = element;
        if (communication != null) {
          /* Get the Qualifier for the communication */
          qualifierString = communication.getCommQualifier();

          if (qualifierString != null) {
            /*
             * Check the qualifier value for AL, HP, WP, CP,
             * FX, EM
             */
            if ("AL".equalsIgnoreCase(qualifierString)) {
              communicationAddress = communication.getAddress();

              if (communicationAddress != null) {
                /*
                 * Set Address,City,State, Zip where
                 * qualifier value is AL
                 */
                // com.mitchell.schemas.AddressType
                // addressDetailsUIXML =
                // claimPartyUIXML.addNewAddressDetails();
                if (checkNullEmptyString(communicationAddress.getAddress1())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_ADDRESS1"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateClaimantPartyType - GOT Claimant Address_1");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setAddress1(communicationAddress.getAddress1());
                }
                if (checkNullEmptyString(communicationAddress.getAddress2())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_ADDRESS2"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateClaimantPartyType - GOT Claimant Address_2");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setAddress2(communicationAddress.getAddress2());
                }
                if (checkNullEmptyString(communicationAddress.getCity())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_CITY"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateClaimantPartyType - GOT Claimant City");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setCity(communicationAddress.getCity());
                }
                if (checkNullEmptyString(communicationAddress
                    .getStateProvince())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_STATE"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateClaimantPartyType - GOT Claimant State");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setState(communicationAddress.getStateProvince());
                }
                if (checkNullEmptyString(communicationAddress.getPostalCode())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_ZIP"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateClaimantPartyType - GOT Claimant ZipCode");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setZIP(communicationAddress.getPostalCode());
                }
              }
            } else if ("HP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_HOME_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateClaimantPartyType - GOT Claimant Home Phone");
                }
                return isPresent;
              }
            } else if ("WP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_WORK_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateClaimantPartyType - GOT Claimant WORK Phone");
                }
                return isPresent;
              }
            } else if ("CP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_CELL_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateClaimantPartyType - GOT Claimant Cell Phone");
                }
                return isPresent;
              }
            } else if ("FX".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_FAX"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateClaimantPartyType - GOT Claimant FAX Phone");
                }
                return isPresent;
              }
            } else if ("EM".equalsIgnoreCase(qualifierString)) {
              emailAddress = communication.getCommEmail();
              if (checkNullEmptyString(emailAddress)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_CLMT_EMAIL"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateClaimantPartyType - GOT Claimant EMAIL Phone");
                }
                return isPresent;
              }
            }

          }
        } // if(communication != null)
      } // for(int i=0;i<communicationArray.length;i++)
    } // if(personInfo.sizeOfCommunicationsArray() > 0)
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting " + CLASS_NAME + " validateClaimantPartyType");
    }
    return isPresent;
  }

  private boolean validateAdminInfoOwnerReqVals(final AdminInfoType adminInfo,
      final String wcaCSETConst)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateAdminInfoOwnerReqVals\n wcaCSETConst= [ " + wcaCSETConst
          + " ]");
    }
    if (adminInfo.isSetOwner()) {
      final GenericPartyType owner = adminInfo.getOwner();
      if (owner != null && owner.getParty() != null) {
        isPresent = validateOwnerPartyType(owner.getParty(), wcaCSETConst);
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("isPresent = " + isPresent);
      mLogger.info("Exiting " + CLASS_NAME
          + " :: validateAdminInfoOwnerReqVals");
    }
    return isPresent;
  }

  private boolean validateOwnerPartyType(final PartyType ownerParty,
      final String wcaCSETConst)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateOwnerPartyType  wcaCSETConst= [ " + wcaCSETConst + " ]");
    }
    String phoneNumber = null;
    String emailAddress = null;
    AddressType communicationAddress = null;
    String qualifierString = null;
    CommunicationsType communication = null;
    CommunicationsType[] communicationArray = null;

    final PersonInfoType ownerPersonInfo = ownerParty.getPersonInfo();

    if (ownerPersonInfo != null) {
      final PersonNameType personName = ownerPersonInfo.getPersonName();

      if (personName != null) {
        if (checkNullEmptyString(personName.getFirstName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_FIRST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateOwnerPartyType - GOT Owner First Name");
          }
          return isPresent;
        }

        if (checkNullEmptyString(personName.getLastName())
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_LAST_NAME"))) {
          isPresent = true;
          if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
            mLogger.info("validateOwnerPartyType - GOT Owner Last Name");
          }
          return isPresent;
        }
      }
      communicationArray = ownerPersonInfo.getCommunicationsArray();
    }

    boolean isSetAddressValues = false;
    boolean isSetContactDetails = false;
    // SCR  0031584 :: Picking the Address from PersonInfo/ComunicationsArray
    if (((communicationArray != null && communicationArray.length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_OWNR_ADDRESS1"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_ADDRESS2"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_CITY"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_STATE")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_OWNR_ZIP"))))) {
      isSetAddressValues = true;

    }
    // SCR  0031584 :: Picking the Contact details from Party/ContactInfo/ComunicationsArray
    if (((ownerParty.getContactInfoArray() != null && ownerParty
        .getContactInfoArray().length > 0) && ((wcaCSETConst
        .equalsIgnoreCase("CONFIG_OWNR_HOME_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_WORK_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_CELL_PH"))
        || (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_FAX")) || (wcaCSETConst
        .equalsIgnoreCase("CONFIG_OWNR_EMAIL"))))) {
      isSetContactDetails = true;
      communicationArray = ownerParty.getContactInfoArray(0)
          .getCommunicationsArray();
    }

    if (isSetAddressValues || isSetContactDetails) {

      for (CommunicationsType element : communicationArray) {
        communication = element;
        if (communication != null) {
          /* Get the Qualifier for the communication */
          qualifierString = communication.getCommQualifier();

          if (qualifierString != null) {
            /*
             * Check the qualifier value for AL, HP, WP, CP,
             * FX, EM
             */
            if ("AL".equalsIgnoreCase(qualifierString)) {
              communicationAddress = communication.getAddress();

              if (communicationAddress != null) {
                /*
                 * Set Address,City,State, Zip where
                 * qualifier value is AL
                 */
                // com.mitchell.schemas.AddressType
                // addressDetailsUIXML =
                // claimPartyUIXML.addNewAddressDetails();
                if (checkNullEmptyString(communicationAddress.getAddress1())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_ADDRESS1"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateOwnerPartyType - GOT Owner Address_1");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setAddress1(communicationAddress.getAddress1());
                }
                if (checkNullEmptyString(communicationAddress.getAddress2())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_ADDRESS2"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateOwnerPartyType - GOT Owner Address_2");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setAddress2(communicationAddress.getAddress2());
                }
                if (checkNullEmptyString(communicationAddress.getCity())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_CITY"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger.info("validateOwnerPartyType - GOT Owner City");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setCity(communicationAddress.getCity());
                }
                if (checkNullEmptyString(communicationAddress
                    .getStateProvince())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_STATE"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger.info("validateOwnerPartyType - GOT Owner State");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setState(communicationAddress.getStateProvince());
                }
                if (checkNullEmptyString(communicationAddress.getPostalCode())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_ZIP"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger.info("validateOwnerPartyType - GOT Owner ZipCode");
                  }
                  return isPresent;
                  // addressDetailsUIXML.setZIP(communicationAddress.getPostalCode());
                }
              }
            } else if ("HP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_HOME_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateOwnerPartyType - GOT Owner Home Phone");
                }
                return isPresent;
              }
            } else if ("WP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_WORK_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateOwnerPartyType - GOT Owner WORK Phone");
                }
                return isPresent;
              }
            } else if ("CP".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_CELL_PH"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateOwnerPartyType - GOT Owner Cell Phone");
                }
                return isPresent;
              }
            } else if ("FX".equalsIgnoreCase(qualifierString)) {
              phoneNumber = communication.getCommPhone();
              if (checkNullEmptyString(phoneNumber)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_FAX"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateOwnerPartyType - GOT Owner FAX Phone");
                }
                return isPresent;
              }
            } else if ("EM".equalsIgnoreCase(qualifierString)) {
              emailAddress = communication.getCommEmail();
              if (checkNullEmptyString(emailAddress)
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_OWNR_EMAIL"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateOwnerPartyType - GOT Owner EMAIL Phone");
                }
                return isPresent;
              }
            }

          }
        } // if(communication != null)
      } // for(int i=0;i<communicationArray.length;i++)
    } // if(personInfo.sizeOfCommunicationsArray() > 0)

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting " + CLASS_NAME + " validateOwnerPartyType");
    }
    return isPresent;
  }

  private boolean validateVehInfoReqVals(final CIECADocument ciecaDoc,
      final AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc,
      final String wcaCSETConst)
      throws Exception
  {
    boolean isPresent = false;

    final String CLASS_NAME = "MandatoryFieldTest";
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("Entering " + CLASS_NAME
              + " validateVehicleDetails \n wcaCSETConst= [ " + wcaCSETConst
              + " ]");
    }
    final CIECA cieca = ciecaDoc.getCIECA();
    final AssignmentAddRq assignmentRq = cieca.getAssignmentAddRq();
    String assignmentMemo = null;

    AdditionalAppraisalAssignmentInfoType additionalAAInfoType = null;
    if (additionalAAInfoDoc != null) {
      additionalAAInfoType = additionalAAInfoDoc
          .getAdditionalAppraisalAssignmentInfo();
    } else {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateVehInfoReqVals  AdditionalAppraisalAssignmentInfoDocument is missing");
      }
      return isPresent;
    }

    /**
     * IDs from AdditionalAAInfo -- as Needed??
     */
    BigInteger additionalAAInfoVehicleTypeId = null;
    com.mitchell.schemas.appraisalassignment.VehicleType additionalAAInfoVehicleType = null;

    if (additionalAAInfoType != null) {
      additionalAAInfoVehicleType = additionalAAInfoType.getVehicleDetails();
    }

    final VehicleDamageAssignmentType vehicleDmgAsmt = assignmentRq
        .getVehicleDamageAssignment();

    if (vehicleDmgAsmt != null) {
      // AssignmentMemo Notes
      if (assignmentRq.isSetVehicleDamageAssignment()
          && (wcaCSETConst.equalsIgnoreCase("CONFIG_NOTES"))) {
        final VehicleDamageAssignmentType vehicleDamageAssignment = assignmentRq
            .getVehicleDamageAssignment();
        if (vehicleDamageAssignment != null) {
          if (vehicleDamageAssignment.sizeOfAssignmentMemoArray() > 0) {
            assignmentMemo = vehicleDamageAssignment.getAssignmentMemoArray(0);
            if (checkNullEmptyString(assignmentMemo)) {
              isPresent = true;
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger
                    .info("validateVehInfoReqVals - GOT AssignmentMemo Notes");
              }
              return isPresent;
              // assignmentDetailsUIXML.setNotes(assignmentMemo);
            }
          }
        }
      }

      final VehicleInfoType vehInfo = vehicleDmgAsmt.getVehicleInfo();

      if (vehInfo != null) {
        if ((vehInfo.getVINInfoArray() != null)
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_VIN_NUM"))) {
          if (vehInfo.sizeOfVINInfoArray() > 0) {
            final VINInfoType vinInfo = vehInfo.getVINInfoArray(0);

            if (vinInfo.sizeOfVINArray() > 0) {
              final VIN vin = vinInfo.getVINArray(0);
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("Got VIN:" + vin.getVINNum());
              }
              if (checkNullEmptyString(vin.getVINNum())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateVehInfoReqVals - GOT Vehicle VIN NUM");
                }
                return isPresent;
                // vehicleDetailsUIXML.setVIN(vin.getVINNum());
              }

              // Here VIN is present in ME so VINNotAvailableFlag
              // should be false
              // vehicleDetailsUIXML.setVINNotAvailableFlag(new
              // Boolean(false).booleanValue());
            } else {
              return isPresent;
              // If either of the VIN or VINAvailabilityCode is
              // not present in BMS then set false in UI
              // vehicleDetailsUIXML.setVINNotAvailableFlag(new
              // Boolean(false).booleanValue());
            }
          }
        }

        if (vehInfo.isSetVehicleDesc()) {
          final VehicleDescType vehDesc = vehInfo.getVehicleDesc();

          if (vehDesc != null) {
            // Getting Value from BMS
            if (vehDesc.isSetVehicleType()) {
              CommonTypeDefIntStr.Factory.newInstance();

              if (vehDesc.isSetVehicleType()
                  && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_TYPE"))) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateVehInfoReqVals, GOT Vehicle Type");
                }
                return isPresent;
                // vehicleType.setValue(vehDesc.getVehicleType());
              }

            }

            // ** TODO - This part of VehicleType algorithm is not
            // same as in AAH, possible bug in AAH....

            // Else, Getting from AdditionalAAInfo
            final CommonTypeDefIntStr vehicleType = CommonTypeDefIntStr.Factory
                .newInstance();
            if (additionalAAInfoVehicleType != null) {
              if (additionalAAInfoVehicleType.isSetVehicleType()) {
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("Inside additionalAAInfoVehicleType.isSetVehicleType()");
                }
                additionalAAInfoVehicleTypeId = additionalAAInfoVehicleType
                    .getVehicleType().getID();
                if (additionalAAInfoVehicleTypeId != null) {
                  vehicleType.setID(additionalAAInfoVehicleTypeId);
                }

                if (checkNullEmptyString(additionalAAInfoVehicleType
                    .getVehicleType().getValue())) {
                  vehicleType.setValue((additionalAAInfoVehicleType
                      .getVehicleType().getValue()));
                }

              }
            } else {
              vehicleType.setID(BigInteger.ZERO);
            }

            // Checking that both ID and VALUE are set
            if (vehicleType.getID() != null && vehicleType.getValue() != null
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_TYPE"))) {
              isPresent = true;
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("validateVehInfoReqVals - GOT Vehicle Type");
              }
              return isPresent;
              // vehicleDetailsUIXML.setVehicleType(vehicleType);
            }

            if (vehDesc.isSetMakeDesc()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_MAKE"))) {
              // ------------------------------Make---------------------------------
              // CommonTypeDefIntStr make =
              // CommonTypeDefIntStr.Factory.newInstance();

              // Getting Value from BMS
              if (checkNullEmptyString(vehDesc.getMakeDesc())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT Vehicle Make Desc");
                }
                return isPresent;
                // make.setValue(vehDesc.getMakeDesc());
              }

              /**
               * TODO verify it this is required?
               * 
               * //Getting ID from AdditionalAAInfo
               * if(additionalAAInfoVehicleType != null) {
               * if(additionalAAInfoVehicleType.isSetMake()) {
               * additionalAAInfoMakeId =
               * additionalAAInfoVehicleType.getMake().getID();
               * if(additionalAAInfoMakeId != null)
               * make.setID(additionalAAInfoMakeId); } } else {
               * make.setID(new BigInteger("0")); }
               * 
               * // Checking that both ID and VALUE are set
               * if(make.getID()!= null && make.getValue()!= null)
               * vehicleDetailsUIXML.setMake(make);
               * 
               */

            }

            if (vehDesc.isSetModelName()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_MODEL"))) {
              // --------------------------------Model----------------------
              // CommonTypeDefIntStr model =
              // CommonTypeDefIntStr.Factory.newInstance();

              // Getting Value from BMS
              if (checkNullEmptyString(vehDesc.getModelName())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateVehInfoReqVals - GOT Vehicle Model");
                }
                return isPresent;
                // model.setValue(vehDesc.getModelName());
              }
              // else
              // model.setValue("");

              /**
               * 
               //Getting Value from BMS
               * if(checkNullEmptyString(vehDesc.getMakeDesc()) &&
               * (wcaCSETConst.equalsIgnoreCase(
               * "CONFIG_VEHICLE_MAKE")))
               * 
               * //Getting ID from AdditionalAAInfo
               * if(additionalAAInfoVehicleType != null) {
               * if(additionalAAInfoVehicleType.isSetModel()) {
               * additionalAAInfoModelId =
               * additionalAAInfoVehicleType.getModel().getID();
               * if(additionalAAInfoModelId != null)
               * model.setID(additionalAAInfoModelId); } } else {
               * model.setID(new BigInteger("0")); }
               * 
               * // Checking that both ID and VALUE are set
               * if(model.getID() != null && model.getValue() !=
               * null) vehicleDetailsUIXML.setModel(model);
               * 
               */
            }

            if (vehDesc.isSetSubModelDesc()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_SUB_MDL"))) {
              // --------------------------------SubModel----------------------
              // CommonTypeDefIntStr subModel =
              // CommonTypeDefIntStr.Factory.newInstance();

              // Getting Value from BMS
              if (checkNullEmptyString(vehDesc.getSubModelDesc())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger.info("validateVehInfoReqVals - GOT Vehicle SubModel");
                }
                return isPresent;
                // subModel.setValue(vehDesc.getSubModelDesc());
              }

              /**
               * 
               * 
               //Getting ID from AdditionalAAInfo
               * if(additionalAAInfoVehicleType != null) {
               * if(additionalAAInfoVehicleType.isSetSubModel()) {
               * additionalAAInfoSubModelId =
               * additionalAAInfoVehicleType
               * .getSubModel().getID();
               * if(additionalAAInfoSubModelId != null)
               * subModel.setID(additionalAAInfoSubModelId); } }
               * else { subModel.setID(new BigInteger("0")); } //
               * Checking that both ID and VALUE are set
               * if(subModel.getID() != null &&
               * subModel.getValue() != null)
               * 
               * vehicleDetailsUIXML.setSubModel(subModel);
               * 
               */

            }

            // Vehicle Year
            if (vehDesc.isSetModelYear()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_YEAR"))) {
              isPresent = true;
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("validateVehInfoReqVals - GOT Vehicle SubModel");
              }
              return isPresent;

              // Calendar calendar = vehDesc.getModelYear();
              // BigInteger b =
              // BigInteger.valueOf(calendar.get(Calendar.YEAR));
              // vehicleDetailsUIXML.setYear(b);
            }

            if (vehDesc.isSetOdometerInfo()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_MIL"))) {
              final OdometerInfo odometerInfo = vehDesc.getOdometerInfo();
              if (odometerInfo != null) {
                if (odometerInfo.isSetOdometerReading()) {
                  if (checkNullEmptyString(odometerInfo
                      .getOdometerReadingCode())) {
                    isPresent = true;
                    // TODO - is odometer reading TRUE
                    // required
                    if ("true".equals(odometerInfo.getOdometerReadingCode())) {
                      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                        mLogger
                            .info("validateVehInfoReqVals - GOT True Vehicle Mileage");
                      }
                      // vehicleDetailsUIXML.setIsTrueMileageUnknown(true);
                    } else {
                      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                        mLogger
                            .info("validateVehInfoReqVals - GOT non-True Vehicle Mileage");
                        // vehicleDetailsUIXML.setIsTrueMileageUnknown(false);
                      }
                    }
                    return isPresent;

                  }
                }
              }
            }
          }
        }

        // --------------------------------BodyStyle----------------------
        if (vehInfo.isSetBody()
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_BODY_STY"))) {
          final BodyType body = vehInfo.getBody();

          if (body != null) {
            // CommonTypeDefIntStr bodyStyle =
            // CommonTypeDefIntStr.Factory.newInstance();
            if (vehInfo.isSetBody()) {
              // Getting Value from BMS
              if (checkNullEmptyString(body.getBodyStyle())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT True Vehicle Mileage");
                }
                return isPresent;
                // bodyStyle.setValue(body.getBodyStyle());
              }
            }

            /**
             * 
             * 
             //Getting ID from AdditionalAAInfo
             * if(additionalAAInfoVehicleType != null) {
             * if(additionalAAInfoVehicleType.isSetBodyStyle()) {
             * additionalAAInfoBodyStyleId =
             * additionalAAInfoVehicleType.getBodyStyle().getID();
             * if(additionalAAInfoBodyStyleId != null)
             * bodyStyle.setID(additionalAAInfoBodyStyleId); } }
             * else { bodyStyle.setID(new BigInteger("0")); }
             * 
             * // Checking that both ID and VALUE are set
             * if(bodyStyle.getID() != null && bodyStyle.getValue()
             * != null) vehicleDetailsUIXML.setBodyStyle(bodyStyle);
             * 
             */
          }
        }

        if (vehInfo.isSetPowertrain()) {
          final PowertrainType powertrain = vehInfo.getPowertrain();

          if (powertrain != null) {
            // /
            // -------------------------------DriveTrain----------------------
            if (powertrain.isSetConfiguration()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_DRV_TRAIN"))) {
              // CommonTypeDefIntStr driveTrain =
              // CommonTypeDefIntStr.Factory.newInstance();

              // Getting Value from BMS
              if (checkNullEmptyString(powertrain.getConfiguration())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT Vehicle Drive Train");
                }
                return isPresent;
                // driveTrain.setValue(powertrain.getConfiguration());
              }

              /**
               * 
               //Getting ID from AdditionalAAInfo
               * if(additionalAAInfoVehicleType != null) {
               * if(additionalAAInfoVehicleType.isSetDriveTrain())
               * { additionalAAInfoDriveTrainId =
               * additionalAAInfoVehicleType
               * .getDriveTrain().getID();
               * if(additionalAAInfoDriveTrainId != null)
               * driveTrain.setID(additionalAAInfoDriveTrainId); }
               * } else { driveTrain.setID(new BigInteger("0")); }
               * 
               * 
               * // Checking that both ID and VALUE are set
               * if(driveTrain.getID() != null &&
               * driveTrain.getValue() != null)
               * vehicleDetailsUIXML.setDriveTrain(driveTrain);
               * 
               */

            }

            // --------------------------------Engine----------------------
            if (powertrain.isSetEngineDesc()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_ENG"))) {
              // CommonTypeDefIntStr engine =
              // CommonTypeDefIntStr.Factory.newInstance();

              // Getting Value from BMS
              if (checkNullEmptyString(powertrain.getEngineDesc())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT Vehicle Engine Desc");
                }
                return isPresent;
                // engine.setValue(powertrain.getEngineDesc());
              }

              /**
               * 
               * 
               //Getting ID from AdditionalAAInfo
               * if(additionalAAInfoVehicleType != null) {
               * if(additionalAAInfoVehicleType.isSetEngine()) {
               * additionalAAInfoEngineId =
               * additionalAAInfoVehicleType.getEngine().getID();
               * if(additionalAAInfoEngineId != null)
               * engine.setID(additionalAAInfoEngineId); } } else
               * { engine.setID(new BigInteger("0")); }
               * 
               * // Checking that both ID and VALUE are set
               * if(engine.getID() != null && engine.getValue() !=
               * null) vehicleDetailsUIXML.setEngine(engine);
               * 
               */

            }

            /*--------------------------------Transmission----------------------*/
            if (powertrain.isSetTransmissionInfo()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_TRANS"))) {
              final TransmissionInfo transmissionInfo = powertrain
                  .getTransmissionInfo();

              if (transmissionInfo != null) {
                if (transmissionInfo.isSetTransmissionDesc()) {
                  // CommonTypeDefIntStr transmission =
                  // CommonTypeDefIntStr.Factory.newInstance();

                  // Getting Value from BMS
                  if (checkNullEmptyString(transmissionInfo
                      .getTransmissionDesc())) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateVehInfoReqVals - GOT Vehicle Tramsmission");
                    }
                    return isPresent;
                    // transmission.setValue(transmissionInfo.getTransmissionDesc());
                  }

                  /**
                   * 
                   * 
                   //Getting ID from AdditionalAAInfo
                   * if(additionalAAInfoVehicleType != null) {
                   * if(additionalAAInfoVehicleType.
                   * isSetTransmission()) {
                   * additionalAAInfoTransmissionId =
                   * additionalAAInfoVehicleType
                   * .getTransmission().getID();
                   * if(additionalAAInfoTransmissionId !=
                   * null) transmission.setID(
                   * additionalAAInfoTransmissionId); } } else
                   * { transmission.setID(new
                   * BigInteger("0")); }
                   * 
                   * // Checking that both ID and VALUE are
                   * set if(transmission.getID() != null &&
                   * transmission.getValue() != null)
                   * vehicleDetailsUIXML
                   * .setTransmission(transmission);
                   * 
                   */
                }
              }
            }
          }
        }

        if (vehInfo.isSetPaint()
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_EXT_COLOR"))) {
          final PaintType paint = vehInfo.getPaint();
          if (paint != null) {
            if (paint.isSetExterior()) {
              final ExteriorInteriorType exterior = paint.getExterior();
              if (exterior != null && exterior.getColorArray() != null) {
                final Color color = exterior.getColorArray(0);
                if (color.isSetColorName()) {
                  if (checkNullEmptyString(color.getColorName())) {
                    isPresent = true;
                  }
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateVehInfoReqVals - GOT Vehicle Exterior Color");
                  }
                  return isPresent;
                  // vehicleDetailsUIXML.setExteriorColor(color.getColorName());
                }
              }
            }
          }
        }

        if (vehInfo.isSetLicense()) {
          final LicenseType license = vehInfo.getLicense();
          if (license != null) {
            if (license.isSetLicensePlateNum()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_VEHICLE_LIC_PLATE"))) {
              if (checkNullEmptyString(license.getLicensePlateNum())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT Vehicle License Plate Number");
                }
                return isPresent;
                // vehicleDetailsUIXML.setLicensePlateNumber(license.getLicensePlateNum());
              }
            }

            if (license.isSetLicensePlateStateProvince()
                && (wcaCSETConst
                    .equalsIgnoreCase("CONFIG_VEHICLE_LIC_PLATE_ST"))) {
              if (checkNullEmptyString(license.getLicensePlateStateProvince())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateVehInfoReqVals - GOT Vehicle License Plate State");
                }
                return isPresent;
                // vehicleDetailsUIXML.setLicensePlateState(license.getLicensePlateStateProvince());
              }
            }
          }
        }
        if (vehInfo.isSetCondition()
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_DRIV_STATUS"))) {
          final ConditionType condition = vehInfo.getCondition();
          if (condition != null) {
            if (condition.isSetDrivableInd()) {
              isPresent = true;
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger.info("condition.getDrivableInd()"
                    + condition.getDrivableInd());
                mLogger
                    .info("validateVehInfoReqVals - GOT Vehicle License Plate State");
              }
              return isPresent;

              // if(condition.getDrivableInd().equalsIgnoreCase("Y"))
              // vehicleDetailsUIXML.setDrivable("Yes");
              // else
              // if(condition.getDrivableInd().equalsIgnoreCase("N"))
              // vehicleDetailsUIXML.setDrivable("No");
              // else
              // if(condition.getDrivableInd().equalsIgnoreCase("U"))
              // vehicleDetailsUIXML.setDrivable("Unknown");
            }
          }
        }

      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting validateVehInfoReqVals");
    }
    return isPresent;

  }

  // 11
  private boolean validateAdminInfoAdjusterReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst)
      throws Exception
  {

    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateAdminInfoAdjusterReqVals  wcaCSETConst= [ "
          + wcaCSETConst + " ]");
    }
    if (adminInfo.isSetAdjuster()) {
      final AdjusterType adjuster = adminInfo.getAdjuster();

      if (adjuster != null) {
        final PartyType adjusterParty = adjuster.getParty();
        PersonInfoType adjusterPersonInfo = null;
        PersonNameType adjusterPersonName = null;
        if (adjusterParty.isSetPersonInfo()) {
          adjusterPersonInfo = adjusterParty.getPersonInfo();

          if (adjusterPersonInfo != null) {
            adjusterPersonName = adjusterPersonInfo.getPersonName();

            if (adjusterPersonName != null) {
              if (adjusterPersonName.isSetFirstName()) {
                if (checkNullEmptyString(adjusterPersonName.getFirstName())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_ADJUSTER"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateAdminInfoAdjusterReqVals - GOT Adjuster First Name");
                  }
                  return isPresent;
                  // lookupResourceUIXML.setFirstName(adjusterPersonName.getFirstName());
                }
              }

              if (adjusterPersonName.isSetLastName()) {
                if (checkNullEmptyString(adjusterPersonName.getLastName())
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_ADJUSTER"))) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateAdminInfoAdjusterReqVals - GOT Adjuster Last Name");
                  }
                  return isPresent;
                  // lookupResourceUIXML.setFirstName(adjusterPersonName.getFirstName());
                }
              }
            }
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting validateAdminInfoAdjusterReqVals");
    }
    return isPresent;

  }

  // 12
  private boolean validateInspectSiteContactReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst,
      final String vehLocType_Const)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateInspectSiteContactReqVals wcaCSETConst= [ "
          + wcaCSETConst + " ]");
    }
    String commPhone = null;
    String qualifier = null;

    // InspectionSite Contact must have the required "default" Vehicle
    // Location Type
    if ((!(vehLocType_Const.equalsIgnoreCase("HOME")) && (!vehLocType_Const
        .equalsIgnoreCase("OTHER")))) {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateInspectSiteContactReqVals  Invalid Vehicle Location Type found ");
      }
      return isPresent;
    }

    if (adminInfo.isSetInspectionSite()) {
      final GenericPartyType inspectSite = adminInfo.getInspectionSite();
      final PartyType inspectSiteParty = inspectSite.getParty();
      ContactInfoType inspectSiteContactInfo = null;

      // AdminInfo/InspectionSite/Party/ContactInfo
      if (inspectSiteParty.getContactInfoArray() != null) {
        if (inspectSiteParty.sizeOfContactInfoArray() > 0) {
          inspectSiteContactInfo = inspectSiteParty.getContactInfoArray(0);
        }

        if ((inspectSiteContactInfo != null)
            && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CONT_FIRST"))
            || (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CONT_LAST"))) {
          final PersonNameType inspectSiteContactPersonName = inspectSiteContactInfo
              .getContactName();
          if (inspectSiteContactPersonName != null) {

            // FirstName $ LastName are common for all
            // vehicleLocation Type
            if (inspectSiteContactPersonName.isSetFirstName()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CONT_FIRST"))) {
              if (checkNullEmptyString(inspectSiteContactPersonName
                  .getFirstName())) {
                isPresent = true;
                if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                  mLogger
                      .info("validateInspectSiteContactReqVals - GOT InspectionSite Contact First Name");
                }
                return isPresent;
                // claimPartyUIXML.setFirstName(inspectSiteContactPersonName.getFirstName());
              }
            }

            if (inspectSiteContactPersonName.isSetLastName()
                && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CONT_LAST"))) {
              if (checkNullEmptyString(inspectSiteContactPersonName
                  .getLastName())) {
                isPresent = true;
              }
              if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                mLogger
                    .info("validateInspectSiteContactReqVals - GOT InspectionSite Contact Last Name");
              }
              return isPresent;
              // claimPartyUIXML.setLastName(inspectSiteContactPersonName.getLastName());
            }
          }
        }
      }

      if ((inspectSiteContactInfo != null)
          && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CONT_PH"))) {
        if (inspectSiteContactInfo.getCommunicationsArray() != null) {
          final CommunicationsType[] contactInfoCommArray = inspectSiteContactInfo
              .getCommunicationsArray();

          if (inspectSiteContactInfo.sizeOfCommunicationsArray() > 0) {
            final CommunicationsType comm = contactInfoCommArray[0];
            qualifier = comm.getCommQualifier();
            commPhone = comm.getCommPhone();

            if (checkNullEmptyString(commPhone)) {
              if (checkNullEmptyString(qualifier)) {
                if (qualifier.equals("WP") || qualifier.equals("HP")
                    || qualifier.equals("CP") || qualifier.equals("PC")
                    || qualifier.equals("FX")) {
                  isPresent = true;
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInspectSiteContactReqVals - GOT InspectionSite Contact Phone");
                  }
                  if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                    mLogger
                        .info("validateInspectSiteContactReqVals - Contact Phone qualifier= [ "
                            + qualifier + " ]");
                  }
                  return isPresent;
                }
              }
            }
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting validateInspectSiteContactReqVals");
    }
    return isPresent;
  }

  // 13
  private boolean validateInspectSitePersonInfoReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst,
      final String vehLocType_Const)
      throws Exception
  {
    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateInspectSitePersonInfoReqVals wcaCSETConst= [ "
          + wcaCSETConst + " ]");
      mLogger.info("Vehicle Location Type = [ " + vehLocType_Const + " ]");
    }
    PersonInfoType inspectSitePersonInfo = null;
    CommunicationsType personInfoComm = null;
    AddressType inspectSitePersonInfoAddress = null;

    // InspectionSite Contact must have the required "default" Vehicle
    // Location Type - HOME
    //
    if (!(vehLocType_Const.equalsIgnoreCase("HOME"))) {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateInspectSitePersonInfoReqVals  Invalid Vehicle Location Type found");
      }
      return isPresent;
    }

    if (adminInfo.isSetInspectionSite()) {
      final GenericPartyType inspectSite = adminInfo.getInspectionSite();
      final PartyType inspectSiteParty = inspectSite.getParty();

      // AdminInfo/InspectionSite/Party/PersonInfo
      inspectSitePersonInfo = inspectSiteParty.getPersonInfo();
      if (inspectSitePersonInfo != null) {
        // AdminInfo/InspectionSite/Party/PersonInfo/Communication
        if (inspectSitePersonInfo.getCommunicationsArray() != null) {
          if (inspectSitePersonInfo.sizeOfCommunicationsArray() > 0) {
            personInfoComm = inspectSitePersonInfo.getCommunicationsArray(0);
            if (personInfoComm.isSetAddress()) {
              inspectSitePersonInfoAddress = personInfoComm.getAddress();
              if (inspectSitePersonInfoAddress != null) {
                if (inspectSitePersonInfoAddress.isSetAddress1()
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_ADDRESS1"))) {
                  if (checkNullEmptyString(inspectSitePersonInfoAddress
                      .getAddress1())) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateInspectSitePersonInfoReqVals - GOT Home InspectionSite Address1");
                    }
                    return isPresent;
                    // claimPartyAddressUIXML.setAddress1(inspectSitePersonInfoAddress.getAddress1());
                  }
                }
                if (inspectSitePersonInfoAddress.isSetAddress2()
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_ADDRESS2"))) {
                  if (checkNullEmptyString(inspectSitePersonInfoAddress
                      .getAddress2())) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateInspectSitePersonInfoReqVals - GOT Home InspectionSite Address2");
                    }
                    return isPresent;
                    // claimPartyAddressUIXML.setAddress2(inspectSitePersonInfoAddress.getAddress2());
                  }
                }
                if (inspectSitePersonInfoAddress.isSetCity()
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CITY"))) {
                  if (checkNullEmptyString(inspectSitePersonInfoAddress
                      .getCity())) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateInspectSitePersonInfoReqVals - GOT Home InspectionSite City");
                    }
                    return isPresent;
                    // claimPartyAddressUIXML.setCity(inspectSitePersonInfoAddress.getCity());
                  }
                }
                if (inspectSitePersonInfoAddress.isSetStateProvince()
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_STATE"))) {
                  if (checkNullEmptyString(inspectSitePersonInfoAddress
                      .getStateProvince())) {
                    isPresent = true;
                    mLogger
                        .info("validateInspectSitePersonInfoReqVals - GOT Home InspectionSite State");
                    return isPresent;
                    // claimPartyAddressUIXML.setState(inspectSitePersonInfoAddress.getStateProvince());
                  }
                }
                if (inspectSitePersonInfoAddress.isSetPostalCode()
                    && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_ZIP"))) {
                  if (checkNullEmptyString(inspectSitePersonInfoAddress
                      .getPostalCode())) {
                    isPresent = true;
                    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                      mLogger
                          .info("validateInspectSitePersonInfoReqVals - GOT Home InspectionSite ZipCode");
                    }
                    return isPresent;
                    // claimPartyAddressUIXML.setZIP(inspectSitePersonInfoAddress.getPostalCode());
                  }
                }
              }
            }
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting validateInspectSitePersonInfoReqVals");
    }
    return isPresent;
  }

  // 14
  private boolean validateInspectSiteOrgInfoReqVals(
      final AdminInfoType adminInfo, final String wcaCSETConst,
      final String vehLocType_Const)
      throws Exception
  {

    boolean isPresent = false;
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering " + CLASS_NAME
          + " validateInspectSiteOrgInfoReqVals wcaCSETConst= [ "
          + wcaCSETConst + " ]");
      mLogger.info("Vehicle Location Type = [ " + vehLocType_Const + " ]");
    }
    // InspectionSite Contact must have the required "default" Vehicle
    // Location Type - OTHER
    //
    if (!(vehLocType_Const.equalsIgnoreCase("OTHER"))) {
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("Exiting "
                + CLASS_NAME
                + " :: validateInspectSiteOrgInfoReqVals  Invalid Vehicle Location Type found ");
      }
      return isPresent;
    }

    if (adminInfo.isSetInspectionSite()) {
      final GenericPartyType inspectSite = adminInfo.getInspectionSite();
      final PartyType inspectSiteParty = inspectSite.getParty();
      final OrgInfoType orgInfo = inspectSiteParty.getOrgInfo();

      if (inspectSiteParty.isSetOrgInfo()) {
        if (orgInfo != null) {
          if (checkNullEmptyString(orgInfo.getCompanyName())
              && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_NAME"))) {
            isPresent = true;
            if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
              mLogger
                  .info("validateInspectSiteOrgInfoReqVals - GOT InspectionSite CompanyName");
            }
            return isPresent;
            // claimPartyUIXML.setAliasName(orgInfo.getCompanyName());
          }
          // else
          // claimPartyUIXML.setAliasName("Not Available");

          if (orgInfo.getCommunicationsArray() != null) {
            if (orgInfo.sizeOfCommunicationsArray() > 0) {
              final CommunicationsType orgInfoComm = orgInfo
                  .getCommunicationsArray(0);

              if (orgInfoComm != null) {
                if (orgInfoComm.isSetAddress()) {
                  final AddressType orgInfoAddress = orgInfoComm.getAddress();

                  if (orgInfoAddress != null) {
                    if (orgInfoAddress.isSetAddress1()
                        && (wcaCSETConst
                            .equalsIgnoreCase("CONFIG_INSPEC_ADDRESS1"))) {
                      if (checkNullEmptyString(orgInfoAddress.getAddress1())) {
                        isPresent = true;
                        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                          mLogger
                              .info("validateInspectSiteOrgInfoReqVals - GOT Other InspectionSite Address1");
                        }
                        return isPresent;
                        // claimPartyAddressUIXML.setAddress1(orgInfoAddress.getAddress1());
                      }
                    }
                    if (orgInfoAddress.isSetAddress2()
                        && (wcaCSETConst
                            .equalsIgnoreCase("CONFIG_INSPEC_ADDRESS2"))) {
                      if (checkNullEmptyString(orgInfoAddress.getAddress2())) {
                        isPresent = true;
                        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                          mLogger
                              .info("validateInspectSiteOrgInfoReqVals - GOT Other InspectionSite Address2");
                        }
                        return isPresent;
                        // claimPartyAddressUIXML.setAddress1(orgInfoAddress.getAddress2());
                      }
                    }
                    if (orgInfoAddress.isSetCity()
                        && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_CITY"))) {
                      if (checkNullEmptyString(orgInfoAddress.getCity())) {
                        isPresent = true;
                        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                          mLogger
                              .info("validateInspectSiteOrgInfoReqVals - GOT Other InspectionSite City");
                        }
                        return isPresent;
                        // claimPartyAddressUIXML.setCity(orgInfoAddress.getCity());
                      }
                    }
                    if (orgInfoAddress.isSetStateProvince()
                        && (wcaCSETConst
                            .equalsIgnoreCase("CONFIG_INSPEC_STATE"))) {
                      if (checkNullEmptyString(orgInfoAddress
                          .getStateProvince())) {
                        isPresent = true;
                        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                          mLogger
                              .info("validateInspectSiteOrgInfoReqVals - GOT Other InspectionSite State");
                        }
                        return isPresent;
                        // claimPartyAddressUIXML.setState(orgInfoAddress.getStateProvince());
                      }
                    }

                    if (orgInfoAddress.isSetPostalCode()
                        && (wcaCSETConst.equalsIgnoreCase("CONFIG_INSPEC_ZIP"))) {
                      if (checkNullEmptyString(orgInfoAddress.getPostalCode())) {
                        isPresent = true;
                        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
                          mLogger
                              .info("validateInspectSiteOrgInfoReqVals - GOT Other InspectionSite ZipCode");
                        }
                        return isPresent;
                        // claimPartyAddressUIXML.setZIP(orgInfoAddress.getPostalCode());
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting validateInspectSiteOrgInfoReqVals");
    }
    return isPresent;
  }

  /**
   * This method is a helper method which extracts CIECADocument from
   * MitchellEnvelope Document and returns CIECADocument.
   * 
   * @param meHelper
   *          MitchellEnvelopeHelper
   * @return CIECADocument
   * @throws Exception
   *           in case MitchellEnvelope Document doesn't contain
   *           CIECADocument.
   */
  private CIECADocument getCiecaFromME(final MitchellEnvelopeHelper meHelper)
      throws Exception
  {

    final String METHOD_NAME = "getCiecaFromME";
    mLogger.entering(CLASS_NAME, METHOD_NAME);
    if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
      mLogger.fine("Input Received :: MitchellEnvelopeDocument: "
          + meHelper.getDoc());
    }
    String contentString = null;
    CIECADocument ciecaDocument = null;

    final EnvelopeBodyType envelopeBody = meHelper
        .getEnvelopeBody("CIECABMSAssignmentAddRq");
    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();

    try {
      contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);
      if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
        mLogger
            .fine("Retrieved CIECABMSAssignmentAddRq from meHelper as String is:"
                + contentString);
      }
    } catch (final MitchellException mitchellException) {
      final String errMsg = "Error getting CIECABMSAssignmentAddRq ContentString from MithcellEnvelope";
      throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg,
          mitchellException);
    }

    if (xmlBeanClassname == null
        || xmlBeanClassname.equals(CIECADocument.class.getName())) {
      try {
        ciecaDocument = CIECADocument.Factory.parse(contentString);
        if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
          mLogger.fine("CIECADocument obtained by parsing contectString is :"
              + ciecaDocument);
        }
      } catch (final Exception exception) {
        final String errMsg = "Error parsing and creating object of CIECADocument";
        throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg,
            exception);
      }
    } else if (xmlBeanClassname == null
        || xmlBeanClassname.equals(AssignmentAddRqDocument.class.getName())) {
      AssignmentAddRqDocument assignmentAddRqDoc = null;
      try {
        assignmentAddRqDoc = AssignmentAddRqDocument.Factory
            .parse(contentString);
        if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
          mLogger
              .fine("AssignmentAddRqDocument obtained by parsing contectString is :"
                  + assignmentAddRqDoc);
        }
      } catch (final Exception exception) {
        final String errMsg = "Error parsing and creating object of AssignmentAddRqDocument";
        throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg,
            exception);
      }
      ciecaDocument = CIECADocument.Factory.newInstance();
      final CIECADocument.CIECA cieca = ciecaDocument.addNewCIECA();
      final AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = assignmentAddRqDoc
          .getAssignmentAddRq();
      cieca.setAssignmentAddRq(assignmentAddRq);
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger.info("Updated CIECA with assignmentAddRq");
      }
    } else {
      final String errMsg = "MithcellEnvelope does not contains CIECA or AssignmentAddRq.";
      throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg);
    }

    mLogger.exiting(CLASS_NAME, METHOD_NAME);

    return ciecaDocument;
  }

  /**
   * This method is a helper method which extracts
   * AdditionalAppraisalAssignmentInfo from MitchellEnvelope Document.
   * 
   * @return AdditionalAppraisalAssignmentInfoDocument
   * @throws Exception
   *           in case MitchellEnvelope Document doesn't contains
   *           AdditionalAppraisalAssignmentInfoDocument
   */
  public AdditionalAppraisalAssignmentInfoDocument getAdditionalAppraisalAssignmentInfoDocumentFromME(
      final MitchellEnvelopeHelper meHelper)
      throws Exception
  {

    final String METHOD_NAME = "getAdditionalAppraisalAssignmentInfoDocumentFromME";
    mLogger.entering(CLASS_NAME, METHOD_NAME);

    String contentString = null;
    AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument = null;

    final EnvelopeBodyType envelopeBody = meHelper
        .getEnvelopeBody("AdditionalAppraisalAssignmentInfo");
    
    if (envelopeBody == null) {
        return additionalAppraisalAssignmentInfoDocument;
      }
    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    
    final String xmlBeanClassname = metadata.getXmlBeanClassname();

    contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);
    if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
      mLogger
          .fine("Retrieved AdditionalAppraisalAssignmentInfo from meHelper as String is:"
              + contentString);
    }
    if (xmlBeanClassname == null
        || xmlBeanClassname
            .equals(AdditionalAppraisalAssignmentInfoDocument.class.getName())) {
      additionalAppraisalAssignmentInfoDocument = AdditionalAppraisalAssignmentInfoDocument.Factory
          .parse(contentString);
      if (mLogger.isLoggable(java.util.logging.Level.FINE)) {
        mLogger
            .fine("Retrieved AdditionalAppraisalAssignmentInfoDocument by parsing ContentString:"
                + additionalAppraisalAssignmentInfoDocument);
      }
    } else {
      final String errMsg = "MithcellEnvelope does not contains getAdditionalAppraisalAssignmentInfoDocumentFromME";
      throw new MitchellException(CLASS_NAME,
          "getAdditionalAppraisalAssignmentInfoDocumentFromME", errMsg);
    }
    
    mLogger.exiting(CLASS_NAME, METHOD_NAME);
    return additionalAppraisalAssignmentInfoDocument;
  }
  
  
  

  /**
   * Gets AdditionalTaskConstraintsDocument from MitchellEnvelope
   * 
   * @param meHelper
   * @return AdditionalTaskConstraintsDocument
   * @throws MitchellException
   */
  public AdditionalTaskConstraintsDocument getAdditionalTaskConstraintsFromME(
      final MitchellEnvelopeHelper meHelper)
      throws MitchellException
  {
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Entering test_getAdditionalTaskConstraintsFromME "
          + CLASS_NAME);
    }
    String contentString = null;
    AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = null;

    final EnvelopeBodyType envelopeBody = meHelper
        .getEnvelopeBody("AdditionalTaskConstraints");

    if (envelopeBody == null) {
      return additionalTaskConstraintsDocument;
    }
    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();

    try {
      contentString = meHelper.getEnvelopeBodyContentAsString(meHelper
          .getEnvelopeBody("AdditionalTaskConstraints"));
    } catch (final MitchellException mitchellException) {
      final String errMsg = "Error getting AdditionalAppraisalAssignmentInfo ContentString from MithcellEnvelope. MitchellEnvelopeDocument is: "
          + meHelper.getDoc().toString();
      throw new MitchellException(CLASS_NAME,
          "getAdditionalAppraisalAssignmentInfoFromME", errMsg,
          mitchellException);
    }

    try {
      if (xmlBeanClassname == null
          || xmlBeanClassname.equals(AdditionalTaskConstraintsDocument.class
              .getName())) {
        additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
            .parse(contentString);
      } else {
        final String errMsg = "MithcellEnvelope does not contains AdditionalTaskConstraintsDocument. MitchellEnvelopeDocument is: "
            + meHelper.getDoc().toString();
        mLogger.severe(errMsg + "\nMitchellEnvelopeDocument is: "
            + meHelper.getDoc().toString());
        throw new MitchellException(CLASS_NAME, "getCiecaFromME", errMsg);
      }
    } catch (final XmlException xmle) {
      mLogger
          .severe("Unable to get AdditionalTaskConstraintsDocument from MitchellEnvelopeDocument. MitchellEnvelopeDocument is: "
              + meHelper.getDoc().toString());
      throw new MitchellException(
          CLASS_NAME,
          "getAdditionalTaskConstraintsFromME",
          "Unable to get AdditionalTaskConstraintsDocument from MitchellEnvelopeDocument. MitchellEnvelopeDocument is: "
              + meHelper.getDoc().toString(), xmle);
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("Exiting getAdditionalTaskConstraintsFromME " + CLASS_NAME);
    }
    return additionalTaskConstraintsDocument;
  }

  private AdminInfoType getCiecaAdminInfo(final CIECADocument ciecaDoc)
      throws Exception
  {

    AdminInfoType adminInfo = null;

    final CIECA cieca = ciecaDoc.getCIECA();

    if (cieca.isSetAssignmentAddRq()) {
      final AssignmentAddRq assignmentRq = cieca.getAssignmentAddRq();
      adminInfo = assignmentRq.getAdminInfo();
    }

    return adminInfo;

  }

  // /////////////////////////////////////////////////////////////////////////////////////////////////////////
  //
  /**
   * Utility Method for handling Business Rule Logic for Dispatch with or
   * without a Valid Vehicle Inspection Site Address
   * 
   * @param mitchellEnvDoc
   *          MitchellEnvelope Document containing BMS and AAAInfo xml
   * @return boolean return True, if dispatch assignment with invalid address
   *         custom setting is yes.
   * 
   */
  public boolean isVehicleReadyForDispatch(
      final MitchellEnvelopeHelper meHelper,
      AdditionalAppraisalAssignmentInfoDocument addAppAssignInfoDoc)
      throws Exception
  {

    boolean isVehicleReady = true;
    boolean travelReqFlag = false;  // Change default for carrier feed assignments

    String hasValidVehicleLocationAddress = "";
    String dispatchWithInvalidAddressAASCustomSettingStr = "";

    final String companyCode = meHelper
        .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_COMPANY_CODE);

    if(addAppAssignInfoDoc !=null && addAppAssignInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails() != null &&
    		addAppAssignInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().isSetIsTravelRequiredFlag()) {
    	travelReqFlag = addAppAssignInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().getIsTravelRequiredFlag();
    	
    }

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger.info(" isVehicleReadyForDispatch: AAAInfo.getIsTravelRequiredFlag,  travelReqFlag = [ "+travelReqFlag+" ]");
      }
    
     
    
    // We want to check the custom settings and vehicle address only if travel is required, if it's not then the vehicle is ready
    if (travelReqFlag) {
      // retrieve AAS CustomSetting for DISPATCH_WITH_INVALID_ADDRESS
      dispatchWithInvalidAddressAASCustomSettingStr = getDispatchWithInvalidAddressAASCustomSetting(companyCode);

      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger.info(" isVehicleReadyForDispatch: AAAInfo.getIsTravelRequiredFlag,  dispatchWithInvalidAddressAASCustomSettingStr = [ "
        		  +dispatchWithInvalidAddressAASCustomSettingStr+" ]");
      }      
      // retrieve getInspectionSiteGeoCode Valid/Invalid Status in
      // additionalAAInfoDoc
      hasValidVehicleLocationAddress = getValidVehicleLocationAddress(addAppAssignInfoDoc,companyCode);
      if ((hasValidVehicleLocationAddress.equalsIgnoreCase("No"))
          && (dispatchWithInvalidAddressAASCustomSettingStr
              .equalsIgnoreCase("Yes"))) {
        isVehicleReady = true;
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger
              .info(" isVehicleReadyForDispatch: OVERRIDE Dispatching with INVALID ADDRESS  ");
        }
      } else if ((hasValidVehicleLocationAddress.equalsIgnoreCase("No"))
          && (dispatchWithInvalidAddressAASCustomSettingStr
              .equalsIgnoreCase("No"))) {
        isVehicleReady = false;
        if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
          mLogger
              .info(" isVehicleReadyForDispatch: Not Dispatching Has INVALID ADDRESS and CUSTOM SETTING is NO  ");
        }
      } else {
        isVehicleReady = true;
      }
    } else {
      isVehicleReady = true;
    }

    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("isVehicleReadyForDispatch: Return value of isVehicleReady= "
              + isVehicleReady + "");
    }
    return isVehicleReady;
  }

  // Utility Method for retrieving the new AAS CustomSetting for
  // DISPATCH_WITH_INVALID_ADDRESS (i.e. "Dispatch Override" )
  //
  private String getDispatchWithInvalidAddressAASCustomSetting(
      final String orgCoCode)
      throws Exception
  {

    final String METHOD_NAME = "getDispatchWithInvalidAddressAASCustomSetting";
    mLogger.entering(CLASS_NAME, METHOD_NAME);

    String retval_dispatchWithInvalidAddress = "Yes";
    String dispatchWithInvalidAddressAASCustomSettingStr = "";

    // final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();

    // Retrieve new AAS CustomSetting - DISPATCH_WITH_INVALID_ADDRESS
    // ADDRESS
    // Returns String "Y" or "N" (Default is "Y")
    dispatchWithInvalidAddressAASCustomSettingStr = appraisalAssignmentUtils
        .retrieveCustomSettings(
            orgCoCode,
            orgCoCode,
            AppraisalAssignmentConstants.CSET_GROUP_NAME,
            AppraisalAssignmentConstants.CSET_SETTING_AAS_DISPATCH_WITH_INVALID_ADDRESS);

    if ((checkNullEmptyString(dispatchWithInvalidAddressAASCustomSettingStr))
        && (dispatchWithInvalidAddressAASCustomSettingStr.equalsIgnoreCase("N"))) {
      retval_dispatchWithInvalidAddress = "No";
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger.info("getDispatchWithInvalidAddressAASCustomSetting: "
          + retval_dispatchWithInvalidAddress + "");
    }
    mLogger.exiting(CLASS_NAME, METHOD_NAME);

    return retval_dispatchWithInvalidAddress;
  }

  //
  // Retrieve getInspectionSiteGeoCode Valid/Invalid Status in
  // additionalAAInfoDoc
  //
  public String getValidVehicleLocationAddress(
      AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc, String companyCode) throws Exception
  {

    String hasValidVehicleLocationAddress = "No";
    String dispatchWithInvalidAddressAASCustomSettingStr ="";
    if (additionalAAInfoDoc == null) {
    // If
      // AdditionalAppraisalAssignmentInfo/AssignmentDetails/InspectionSiteGeoCode/AddressValidStatus
      // missing in ME Doc
      hasValidVehicleLocationAddress = "No";
      if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
        mLogger
            .info("getValidVehicleLocationAddress:  additionalAAInfoDoc is missing! ");
      }

    } else {

      // additionalAAInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().getInspectionSiteGeoCode().getAddressValidStatus()
      VehicleLocationGeoCodeType additionalAAInfoGeoCodeType = null;
      final AdditionalAppraisalAssignmentInfoType additionalAAInfoType = additionalAAInfoDoc
          .getAdditionalAppraisalAssignmentInfo();
  		dispatchWithInvalidAddressAASCustomSettingStr = getDispatchWithInvalidAddressAASCustomSetting(companyCode);
      if (additionalAAInfoType.isSetAssignmentDetails()
          && additionalAAInfoType.getAssignmentDetails()
              .isSetInspectionSiteGeoCode()) {
        if (additionalAAInfoType != null) {
          additionalAAInfoGeoCodeType = additionalAAInfoType
              .getAssignmentDetails().getInspectionSiteGeoCode();
        }
        if (additionalAAInfoGeoCodeType != null) {
          // Check AddressValidStatus
          if ((AddressValidStatus.VALID == additionalAAInfoGeoCodeType
              .getAddressValidStatus()) || ("Yes".equalsIgnoreCase(dispatchWithInvalidAddressAASCustomSettingStr))) {
            hasValidVehicleLocationAddress = "Yes";
          } else if (AddressValidStatus.INVALID == additionalAAInfoGeoCodeType
              .getAddressValidStatus()) {
            hasValidVehicleLocationAddress = "No";
          }
        }
      }
    }
    if (mLogger.isLoggable(java.util.logging.Level.INFO)) {
      mLogger
          .info("getValidVehicleLocationAddress: Return value of hasValidVehicleLocationAddress= "
              + hasValidVehicleLocationAddress + "");
    }
    return hasValidVehicleLocationAddress;
  }

}
