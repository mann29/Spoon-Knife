package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc5;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlObject;
import com.cieca.bms.AddressType;
import com.cieca.bms.BodyType;
import com.cieca.bms.C;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimNumType;
import com.cieca.bms.ClosedEnum;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.ConditionType;
import com.cieca.bms.EstimatorIDType;
import com.cieca.bms.FactsType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.PersonNameType;
import com.cieca.bms.VehicleDescType;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.mum.types.mie.DS01Type;
import com.mitchell.services.core.mum.types.mie.DS02Type;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * Utility methods for processing DS01 and DS02 records (Dispatch Report for StateFarm [SF] MIE).
 * 
 * @author cg97025
 *
 */
public class DSRecordsUtils {

    private final static String CLASS_NAME = "DSRecordsUtils";
    
    private static String MAPPING_XPATH_BMS_NS = "declare namespace bms='http://www.cieca.com/BMS'";

	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(this.getClass()
			.getName());

	private final Logger log = Logger.getLogger(DSRecordsUtils.class.getName());

    // For maintaining current line number in DS02 records.
    private int m_lineNum = 1;
    // Is stored as a 3-digit value, with leading zeroes.
    private static DecimalFormat df = new DecimalFormat("000");

    private String getLeadingZeroLineNumAndIncrement() {

        String retVal = "" + df.format(m_lineNum);
        m_lineNum++;

        return retVal;
    }
    
    private String getMaxLengthDS02Record(String value, int maxLength) {

        String retVal = "";

        if (value != null && value.length() > 0) {

            if (value.length() > maxLength) {
                retVal = value.substring(0, maxLength);
            }
            else {
                retVal = value;
            }
        }

        return retVal;
    }
    
    protected CIECADocument getCiecaDocFromMitchellEnv(MitchellEnvelopeDocument meDoc,
            String workItemId, String coCd, int orgId) throws AssignmentDeliveryException {

        final String methodName = "getCiecaDocFromMitchellEnv";

        CIECADocument retVal = null;

        HandlerUtils handlerUtils = new HandlerUtils();

        try {
            retVal = handlerUtils.getCiecaDocFromMitchellEnv(meDoc, workItemId);
        }
        catch (Exception e) {
            int errorCode = AssignmentDeliveryErrorCodes.ERROR_GETTING_CIECA_BMS_DOC_FROM_MITCHELLENVELOPE;

            ErrorLoggingService.logError(errorCode, null, CLASS_NAME, methodName,
                    ErrorLoggingService.SEVERITY.FATAL, workItemId, e.getMessage(), coCd, orgId, e);

            throw mLogger.createException(errorCode, workItemId,
                    "Error Getting CIECA BMS Doc From MitchellEnvelope:\n" + meDoc, e);
        }

        return retVal;
    }
    
    /*
"CONCATENATE (AssignmentAddRq/AdminInfo/Claimant/Party/PersonInfo/PersonName/FirstName AND AssignmentAddRq/AdminInfo/Claimant/Party/PersonInfo/PersonName/LastName)
- - - population logic follows - - -
IFLength of (LastName) > 24
THEN remove characters from the right of LastName until the Length of (LastName) = 24 and populate the MIE field with the shortened LastName
ELSE
  IF (Length of LastName = 23)    OR    (Length of LastName = 24)    OR
      (Length of LastName < 23 AND FirstName is Blank)
  THEN populate the MIE field with LastName
  ELSE 
    IF (LastName is not Blank AND Length of LastName < 23    AND   FirstName is not Blank) 
    THEN Shorten FirstName by removing characters from the right until 
        The Length of (FirstName) = 23 � Length of (LastName)
        Populate the MIE field with the concatenation of Shortened FirstName +
        1 space + LastName
    ELSE
      IF (LastName is Blank)    AND   (FirstName is not Blank)    AND
           (Length of (FirstName) is less than or equal to 24)
      THEN Populate the MIE field with FirstName
      ELSE
        IF (LastName is Blank)    AND   (FirstName is not Blank)    AND
             (Length of (FirstName) >24)
        THEN Shorten FirstName by removing characters from the right until
              Length of (FirstName) = 24 and Populate the MIE field with FirstName
        ELSE
          IF (LastName is Blank)    AND   (FirstName is Blank)
          THEN do not populate the MIE field"

     */

    /**
     * Determines customer name string, from above algorithm (originally contained in:
     * "State Farm Staff-ARC5-Mitchell Envelope to DS01*.xls" document).
     */
    public static String determineCustomerName(String firstName, String lastName) {

        String retVal = null;
        
        int firstNameLen = firstName.length();
        int lastNameLen = lastName.length();
        
        if (lastNameLen > 24) {
            retVal = leftTruncateString(lastName, 24);
        }
        else if (lastNameLen == 23 || lastNameLen == 24 || (lastNameLen < 23 && firstNameLen == 0)) {
            retVal = lastName;
        }
        else if (lastNameLen > 0 && lastNameLen < 23 && firstNameLen > 0) {
            int shorterLen = 23 - lastNameLen;
            String firstNameTrunc = leftTruncateString(firstName, shorterLen);
            retVal = firstNameTrunc + " " + lastName;
        }
        else if (lastNameLen == 0 && firstNameLen > 0 && firstNameLen <= 24) {
            retVal = firstName;
        }
        else if (lastNameLen == 0 && firstNameLen > 0 && firstNameLen > 24) {
            retVal = leftTruncateString(firstName, 24);
        }

        return retVal;
    }

    /**
     * Creates DS01 record, based on passed-in document. Originally defined in:
     * 'State Farm Staff-ARC5-Mitchell Envelope to DS01*.xls' document.
     * 
     * @param meDoc
     * @param workItemId
     * @param numDS02Records
     * @param coCd
     * @param orgId
     * @return DS01 record, from passed-in MitchellEnvelope.
     * @throws AssignmentDeliveryException
     */
    public DS01Type getDS01Record(MitchellEnvelopeDocument meDoc, String workItemId,
            int numDS02Records, String coCd, int orgId) throws AssignmentDeliveryException {

        final String METHOD_NAME = "getDS01Record";
        
        DS01Type retVal = DS01Type.Factory.newInstance();
        
        CIECADocument bmsDoc = getCiecaDocFromMitchellEnv(meDoc, workItemId, coCd, orgId);
        
        // Company ID.
        XmlObject[] xmlObject1 = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                + ".//bms:AssignmentAddRq/bms:AdminInfo/bms:Estimator/bms:Affiliation");

        if (xmlObject1 != null && xmlObject1.length > 0) {

            ClosedEnum coId = (ClosedEnum) xmlObject1[0];
            String coIdStr = coId.getStringValue();

            if (coIdStr != null) {
                if (coIdStr.equals("IN")) {
                    retVal.setCompanyID("SF");
                }
                else if (coIdStr.equals("69")) {
                    retVal.setCompanyID("BS");
                }
            }
        }
        
        // User ID.
        XmlObject[] xmlObject2 = bmsDoc
                .selectPath(MAPPING_XPATH_BMS_NS
                        + ".//bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:EstimatorIDs/bms:CurrentEstimatorID");

        if (xmlObject2 != null && xmlObject2.length > 0) {

            EstimatorIDType userId = (EstimatorIDType) xmlObject2[0];
            String userIdStr = userId.getStringValue();

            retVal.setUserID(userIdStr);
        }

        // CreateDateTime.
        Date now = new Date();
        
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyHHmmss");
        String formattedDate = formatter.format(now);
        
        retVal.setCreateDateTime(formattedDate);

        // Estimate ID.
        XmlObject[] xmlObject3 = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                + ".//bms:AssignmentAddRq/bms:ClaimInfo/bms:ClaimNum");

        if (xmlObject3 != null && xmlObject3.length > 0) {

            ClaimNumType estimateId = (ClaimNumType) xmlObject3[0];
            String estimateIdStr = estimateId.getStringValue();

            retVal.setEstimateID(estimateIdStr);
        }
        
        // Customer Name.
        XmlObject[] xmlObject4 = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                + ".//bms:AssignmentAddRq/bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:PersonName");

        if (xmlObject4 != null && xmlObject4.length > 0) {

            PersonNameType person = (PersonNameType) xmlObject4[0];
            
            String firstName = "";
            String lastName = "";

            // Only determine longer name if at least one of FirstName or LastName is populated.
            if (person.getFirstName() != null || person.getLastName() != null) {

                if (person.getFirstName() != null) {
                    firstName = person.getFirstName();
                }

                if (person.getLastName() != null) {
                    lastName = person.getLastName();
                }

                String customerName = determineCustomerName(firstName, lastName);

                retVal.setCustomerName(customerName);
            }
        }

        // Number Detail Records.
        retVal.setNbrDetailRecords("" + numDS02Records);
        
        // State Farm constant values.
        retVal.setInsCompanyID(DSRecordsConstants.DS01_SF_INS_COMPANY_ID);
        retVal.setMEDSOperatorID(DSRecordsConstants.DS01_MEDS_OPERATOR_ID);

        
        // Assignment Type.
        final String DOC_VER_CODE_EM = "EM";
		final String DOC_VER_CODE_SV = "SV";
     	
		if (!bmsDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().isSetDocumentVerCode()) {
        
			if (!bmsDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().getDocumentVerArray()[0].isSetDocumentVerCode()) {
				retVal.setAssignmentType("EM");
			}else{
				String vc = bmsDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().getDocumentVerArray()[0].getDocumentVerCode();
				if (vc.equals(DOC_VER_CODE_SV)) {
					retVal.setAssignmentType("6S");
				}else 
				retVal.setAssignmentType("EM");
			}
    	
		}else {
			
			String vc = bmsDoc.getCIECA().getAssignmentAddRq().getDocumentInfo().getDocumentVerCode();
				if (vc.equals(DOC_VER_CODE_SV)) {
					retVal.setAssignmentType("6S");
				} else {
					retVal.setAssignmentType("EM");
				}
		}
        
        return retVal;
    }

    /**
	 * Helper method to create a new DS02 entry for adding to the global list of
	 * entries.
	 * 
	 * @param line
	 * @param ds02RecordList
	 */
	private void createAndAddDS02Entry(String line, ArrayList ds02RecordList) {

		DS02Type nextDS02 = DS02Type.Factory.newInstance();
		nextDS02.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
		nextDS02.setDispatchDta(line);

		if (log.isLoggable(Level.FINER)) {
			log.finer("Created DS02 entry: " + nextDS02.toString());
		}

		ds02RecordList.add(nextDS02);
	}

	/**
	 * Creates DS02 records, based on passed-in document. Originally defined in:
	 * 'State Farm Staff-ARC5-EDI and Mitchell Envelope*.doc' document.
	 * 
	 * @param meDoc
	 * @param workItemId
	 * @param coCd
	 * @param orgId
	 * @return
	 * @throws AssignmentDeliveryException
	 */
	public DS02Type[] getDS02Records(MitchellEnvelopeDocument meDoc, String workItemId,
			String coCd, int orgId) throws AssignmentDeliveryException {

		final String METHOD_NAME = "getDS02Records";

		DS02Type[] retVal = null;
		ArrayList ds02RecordList = new ArrayList();

		CIECADocument bmsDoc = getCiecaDocFromMitchellEnv(meDoc, workItemId, coCd, orgId);

		XmlObject[] objArray = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
				+ ".//bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:AssignmentMemo");

		// Do we have any AssignmentMemo entries?
		if (objArray != null && objArray.length > 0) {

			C[] memoArray = (C[]) objArray;

			if (memoArray != null && memoArray.length > 0) {

				// If so, get the contents of the first entry.
				C memo0 = memoArray[0];
				String memoText = memo0.getStringValue();

				if (memoText != null && memoText.length() > 0) {

					StringTokenizer st = new StringTokenizer(memoText, "\n\r");

					// Glab each line in the original AssignmentMemo[0] field.
					while (st.hasMoreTokens()) {

						String nextLine = st.nextToken();

						if (nextLine != null) {

							// If the line is 80 chars or fewer, copy as-is.
							if (nextLine.length() <= 80) {

								createAndAddDS02Entry(nextLine, ds02RecordList);
							}
							else {

								// If the line is > 80 chars, split into lines
								// of 80 chars (or less for final line).
								while (nextLine != null && nextLine.length() > 0) {

									if (nextLine.length() > 80) {
										createAndAddDS02Entry(nextLine.substring(0, 80),
												ds02RecordList);

										// Move on to the next 80 chars.
										nextLine = nextLine.substring(80);
									}
									else {
										// Now have 80 chars or fewer: create
										// the last DS02 entry, then set up for
										// termination.
										createAndAddDS02Entry(nextLine, ds02RecordList);

										// Terminate while loop on next
										// iteration.
										nextLine = null;
									}
								}
							}
						}
					}
				}
			}
		}

		// Convert from ListArray back to array of objects.
		retVal = new DS02Type[m_lineNum - 1];
		ds02RecordList.toArray(retVal);

		return retVal;
	}

	/**
	 * Creates DS02 records, based on passed-in document. Originally defined in:
	 * 'State Farm Staff-ARC5-EDI and Mitchell Envelope*.doc' document.
	 * 
	 * @param meDoc
	 * @param workItemId
	 * @param coCd
	 * @param orgId
	 * @return
	 * @throws AssignmentDeliveryException
	 */
	// 2009-05-01: CG Renamed to '_old', and re-implemented above from AssignmentMemo[0] field.
    public DS02Type[] getDS02Records_old(MitchellEnvelopeDocument meDoc, String workItemId,
            String coCd, int orgId) throws AssignmentDeliveryException {

        final String METHOD_NAME = "getDS02Records";

        DS02Type[] retVal = null;
        ArrayList ds02RecordList = new ArrayList();

        CIECADocument bmsDoc = getCiecaDocFromMitchellEnv(meDoc, workItemId, coCd, orgId);
        MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);

        // Vehicle Info.
        VehicleInfoType vehInfo = bmsDoc.getCIECA().getAssignmentAddRq()
                .getVehicleDamageAssignment().getVehicleInfo();
        VehicleDescType vehType = vehInfo.getVehicleDesc();

        StringBuffer vehDS02Line = new StringBuffer();

        String vehYear = "" + vehType.getModelYear();
        vehDS02Line.append(getMaxLengthDS02Record(vehYear,
                DSRecordsConstants.DS02_VEH_YEAR_MAX_LENGTH));

        String make = vehType.getMakeDesc();
        vehDS02Line.append(" ").append(
                getMaxLengthDS02Record(make, DSRecordsConstants.DS02_VEH_MAKE_MAX_LENGTH));

        String model = vehType.getModelName();
        vehDS02Line.append(" ").append(
                getMaxLengthDS02Record(model, DSRecordsConstants.DS02_VEH_MODEL_MAX_LENGTH));

        BodyType type = vehInfo.getBody();
        if (type != null) {
            String style = type.getBodyStyle();
            vehDS02Line.append(" ").append(
                    getMaxLengthDS02Record(style,
                            DSRecordsConstants.DS02_VEH_BODY_STYLE_MAX_LENGTH));
        }
        
        String vehDS02LineStr = vehDS02Line.toString().trim();

        if (vehDS02LineStr.length() > 0) {

            DS02Type ds02Type1 = DS02Type.Factory.newInstance();
            ds02Type1.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
            ds02Type1.setDispatchDta(vehDS02Line.toString());
            ds02RecordList.add(ds02Type1);
        }

        // Principal Damage.
        String principalDamage = meHelper
                .getEnvelopeContextNVPairValue(DSRecordsConstants.NVP_SF_PRINCIPAL_DAMAGE_DESCRIPTION);

        if (principalDamage != null && principalDamage.trim().length() > 0) {

            // Add "Blank" line.
            DS02Type ds02Type2 = createDS02TypeBlankLine();
            ds02RecordList.add(ds02Type2);
            DS02Type ds02Type3 = DS02Type.Factory.newInstance();

            ds02Type3.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
            ds02Type3.setDispatchDta(DSRecordsConstants.DS02_HEADER_PRINCIPAL_DAMAGE);
            ds02RecordList.add(ds02Type3);

            DS02Type ds02Type4 = DS02Type.Factory.newInstance();
            ds02Type4.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
            ds02Type4.setDispatchDta(getMaxLengthDS02Record(principalDamage,
                    DSRecordsConstants.DS02_PRINCIPAL_DAMAGE_MAX_LENGTH));
            ds02RecordList.add(ds02Type4);
        }

        // Prior Damage.
        //mg99961: fixed to avoid direct type casting
        

       /* ConditionType[] conditionArray = (ConditionType[]) bmsDoc
                .selectPath(MAPPING_XPATH_BMS_NS
                        + ".//bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Condition");
        */

        XmlObject[] xmlObject0 =  bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                        + ".//bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Condition");
        
        if ( xmlObject0 != null && xmlObject0.length > 0) {
            
            ConditionType[] conditionArray = (ConditionType[]) xmlObject0;    
      
            if (conditionArray.length > 0) {
    
                String priorDamageMemo = conditionArray[0].getPriorDamageMemo();
    
                if (priorDamageMemo != null && priorDamageMemo.trim().length() > 0) {
    
                    // Add "Blank" line.
                    DS02Type ds02Type5 = createDS02TypeBlankLine();
                    ds02RecordList.add(ds02Type5);
    
                    DS02Type ds02Type6 = DS02Type.Factory.newInstance();
                    ds02Type6.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type6.setDispatchDta(DSRecordsConstants.DS02_HEADER_PRIOR_DAMAGE);
                    ds02RecordList.add(ds02Type6);
    
                    DS02Type ds02Type7 = DS02Type.Factory.newInstance();
                    ds02Type7.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type7.setDispatchDta(getMaxLengthDS02Record(priorDamageMemo,
                            DSRecordsConstants.DS02_PRIOR_DAMAGE_MAX_LENGTH));
                    ds02RecordList.add(ds02Type7);
                }
            }
        }
        // Vehicle Location.
        XmlObject[] xmlObject1 = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                + ".//bms:AssignmentAddRq/bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo");

        if (xmlObject1 != null && xmlObject1.length > 0) {

            OrgInfoType[] orgInfoTypeVehLocationArray = (OrgInfoType[]) xmlObject1;

            boolean isAddVehLocation = false;

            StringBuffer vehLocationLine2Str = new StringBuffer(
                    DSRecordsConstants.DS02_VEH_LOCATION_LEADING_SPACES);

            String vehLocationLine1 = null;

            CommunicationsType[] communicationsTypeArray = orgInfoTypeVehLocationArray[0]
                    .getCommunicationsArray();

            for (int i = 0; i < communicationsTypeArray.length; i++) {

                CommunicationsType nextCommunicationsType = communicationsTypeArray[i];

                if (nextCommunicationsType.getCommQualifier().equals("AL")) {

                    AddressType addressType = nextCommunicationsType.getAddress();

                    if (addressType != null) {
                        String vehLocationAddr1 = addressType.getAddress1();
                        String vehLocationCity = addressType.getCity();
                        String vehState = addressType.getStateProvince();
                        String vehZip = addressType.getPostalCode();

                        // Any vehicle location address fields present?
                        if (vehLocationAddr1 != null || vehLocationCity != null || vehState != null
                                || vehZip != null) {
                            isAddVehLocation = true;
                        }

                        // Address 1.
                        if (vehLocationAddr1 != null && vehLocationAddr1.length() > 0) {

                            vehLocationLine1 = DSRecordsConstants.DS02_VEH_LOCATION_LEADING_SPACES
                                    + getMaxLengthDS02Record(
                                            vehLocationAddr1,
                                            DSRecordsConstants.DS02_VEH_LOCATION_ADDR_LINE1_MAX_LENGTH);
                        }

                        // Remaining address fields.
                        if (vehLocationCity != null || vehState != null || vehZip != null) {

                            // City.
                            if (vehLocationCity != null && vehLocationCity.length() > 0) {

                                vehLocationLine2Str.append(getMaxLengthDS02Record(vehLocationCity,
                                        DSRecordsConstants.DS02_VEH_LOCATION_CITY_MAX_LENGTH));
                            }

                            // StateProvince.
                            if (vehState != null && vehState.length() > 0) {

                                vehLocationLine2Str
                                        .append(", ")
                                        .append(
                                                getMaxLengthDS02Record(
                                                        vehState,
                                                        DSRecordsConstants.DS02_VEH_LOCATION_STATE_MAX_LENGTH));
                            }

                            // PostalCode.
                            if (vehZip != null && vehZip.length() > 0) {

                                vehLocationLine2Str
                                        .append(" ")
                                        .append(
                                                getMaxLengthDS02Record(
                                                        vehZip,
                                                        DSRecordsConstants.DS02_VEH_LOCATION_ZIP_MAX_LENGTH));
                            }
                        }
                    }
                }
            }

            // Add full Vehicle Location record.
            if (isAddVehLocation) {

                // Add "Blank" line.
                DS02Type ds02Type8 = createDS02TypeBlankLine();
                ds02RecordList.add(ds02Type8);

                DS02Type ds02Type9 = DS02Type.Factory.newInstance();
                ds02Type9.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type9.setDispatchDta(DSRecordsConstants.DS02_HEADER_VEH_LOCATION);
                ds02RecordList.add(ds02Type9);

                if (vehLocationLine1 != null) {
                    DS02Type ds02Type10 = DS02Type.Factory.newInstance();
                    ds02Type10.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type10.setDispatchDta(vehLocationLine1.toString());
                    ds02RecordList.add(ds02Type10);
                }

                if (vehLocationLine2Str.length() > 0) {
                    DS02Type ds02Type11 = DS02Type.Factory.newInstance();
                    ds02Type11.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type11.setDispatchDta(vehLocationLine2Str.toString());
                    ds02RecordList.add(ds02Type11);
                }
            }
        }

        // Accident Location.
        // Look at Accident Location Description in ME.
        String accidentLocationDescriptionInME = meHelper
                .getEnvelopeContextNVPairValue(DSRecordsConstants.NVP_SF_ACCIDENT_LOCATION_DESCRIPTION);

        // If in ME, remember it.
        boolean isAddAccidentLocation = false;
        String accidentLocationDescription = null;

        if (accidentLocationDescriptionInME != null
                && accidentLocationDescriptionInME.trim().length() > 0) {

            accidentLocationDescription = DSRecordsConstants.DS02_ACCIDENT_LOCATION_LEADING_SPACES
                    + getMaxLengthDS02Record(accidentLocationDescriptionInME,
                            DSRecordsConstants.DS02_ACCIDENT_LOCATION_DESCRIPTION_MAX_LENGTH);
            isAddAccidentLocation = true;
        }
        
        XmlObject[] xmlObject2 = bmsDoc
                .selectPath(MAPPING_XPATH_BMS_NS
                        + ".//bms:AssignmentAddRq/bms:AdminInfo/bms:LossLocationSite/bms:Party/bms:OrgInfo");

        if (xmlObject2 != null && xmlObject2.length > 0) {

            OrgInfoType[] orgInfoTypeAccidentLocationArray = (OrgInfoType[]) xmlObject2;

            CommunicationsType[] communicationsTypeArray = orgInfoTypeAccidentLocationArray[0]
                    .getCommunicationsArray();

            StringBuffer accidentLocationLine3Str = new StringBuffer(
                    DSRecordsConstants.DS02_ACCIDENT_LOCATION_LEADING_SPACES);

            String accidentLocationLine1 = null;
            String accidentLocationLine2 = null;

            for (int i = 0; i < communicationsTypeArray.length; i++) {

                CommunicationsType nextCommunicationsType = communicationsTypeArray[i];

                if (nextCommunicationsType.getCommQualifier().equals("AL")) {

                    AddressType addressType = nextCommunicationsType.getAddress();

                    if (addressType != null) {
                        String accidentLocationAddr1 = addressType.getAddress1();
                        String accidentLocationAddr2 = addressType.getAddress2();
                        String accidentLocationCity = addressType.getCity();
                        String accidentState = addressType.getStateProvince();
                        String accidentZip = addressType.getPostalCode();

                        // Any accident location address fields present?
                        if (accidentLocationAddr1 != null || accidentLocationAddr2 != null
                                || accidentLocationCity != null || accidentState != null
                                || accidentZip != null) {
                            isAddAccidentLocation = true;
                        }

                        // Address 1.
                        if (accidentLocationAddr1 != null && accidentLocationAddr1.length() > 0) {

                            accidentLocationLine1 = DSRecordsConstants.DS02_ACCIDENT_LOCATION_LEADING_SPACES
                                    + getMaxLengthDS02Record(
                                            accidentLocationAddr1,
                                            DSRecordsConstants.DS02_ACCIDENT_LOCATION_ADDR_LINE1_MAX_LENGTH);
                        }

                        // Address 2.
                        if (accidentLocationAddr2 != null && accidentLocationAddr2.length() > 0) {

                            if (accidentLocationAddr2 != null && accidentLocationAddr2.length() > 0) {

                                accidentLocationLine2 = DSRecordsConstants.DS02_ACCIDENT_LOCATION_LEADING_SPACES
                                        + getMaxLengthDS02Record(
                                                accidentLocationAddr2,
                                                DSRecordsConstants.DS02_ACCIDENT_LOCATION_ADDR_LINE2_MAX_LENGTH);
                            }
                        }

                        // Remaining address fields.
                        if (accidentLocationCity != null || accidentState != null
                                || accidentZip != null) {

                            // City.
                            if (accidentLocationCity != null && accidentLocationCity.length() > 0) {

                                accidentLocationLine3Str.append(getMaxLengthDS02Record(
                                        accidentLocationCity,
                                        DSRecordsConstants.DS02_VEH_LOCATION_CITY_MAX_LENGTH));
                            }

                            // StateProvince.
                            if (accidentState != null && accidentState.length() > 0) {

                                accidentLocationLine3Str
                                        .append(", ")
                                        .append(
                                                getMaxLengthDS02Record(
                                                        accidentState,
                                                        DSRecordsConstants.DS02_VEH_LOCATION_STATE_MAX_LENGTH));
                            }

                            // PostalCode.
                            if (accidentZip != null && accidentZip.length() > 0) {

                                accidentLocationLine3Str
                                        .append(" ")
                                        .append(
                                                getMaxLengthDS02Record(
                                                        accidentZip,
                                                        DSRecordsConstants.DS02_VEH_LOCATION_ZIP_MAX_LENGTH));
                            }
                        }
                    }
                }
            }
            
            // Add full Accident Location record.
            if (isAddAccidentLocation) {

                // Add "Blank" line.
                DS02Type ds02Type12 = createDS02TypeBlankLine();
                ds02RecordList.add(ds02Type12);

                DS02Type ds02Type13 = DS02Type.Factory.newInstance();
                ds02Type13.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type13.setDispatchDta(DSRecordsConstants.DS02_HEADER_ACCIDENT_LOCATION);
                ds02RecordList.add(ds02Type13);

                // SCR 11292 support.
                if (accidentLocationDescription != null) {
                    DS02Type ds02Type14a = DS02Type.Factory.newInstance();
                    ds02Type14a.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type14a.setDispatchDta(accidentLocationDescription.toString());
                    ds02RecordList.add(ds02Type14a);
                }
                // End SCR 11292 support.

                if (accidentLocationLine1 != null) {
                    DS02Type ds02Type14 = DS02Type.Factory.newInstance();
                    ds02Type14.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type14.setDispatchDta(accidentLocationLine1.toString());
                    ds02RecordList.add(ds02Type14);
                }

                if (accidentLocationLine2 != null) {
                    DS02Type ds02Type15 = DS02Type.Factory.newInstance();
                    ds02Type15.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type15.setDispatchDta(accidentLocationLine2.toString());
                    ds02RecordList.add(ds02Type15);
                }

                if (accidentLocationLine3Str.length() > 0) {
                    DS02Type ds02Type16 = DS02Type.Factory.newInstance();
                    ds02Type16.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type16.setDispatchDta(accidentLocationLine3Str.toString());
                    ds02RecordList.add(ds02Type16);
                }
            }
        }

        // Facts of Loss.
        XmlObject[] xmlObject3 = bmsDoc.selectPath(MAPPING_XPATH_BMS_NS
                + ".//bms:AssignmentAddRq/bms:ClaimInfo/bms:LossInfo/bms:Facts");

        if (xmlObject3 != null && xmlObject3.length > 0) {

            FactsType[] lossDescTypeArray = (FactsType[]) xmlObject3;

            String lossDescTypeFull = lossDescTypeArray[0].getLossMemo();

            if (lossDescTypeFull != null && lossDescTypeFull.length() > 0) {

                String factsOfLossLine1 = getFactsOfLossLine1(lossDescTypeFull);
                String factsOfLossLine2 = getFactsOfLossLine2(lossDescTypeFull);
                String factsOfLossLine3 = getFactsOfLossLine3(lossDescTypeFull);
                String factsOfLossLine4 = getFactsOfLossLine4(lossDescTypeFull);

                // Add "Blank" line.
                DS02Type ds02Type16 = createDS02TypeBlankLine();
                ds02RecordList.add(ds02Type16);

                // Must be at least one line.
                DS02Type ds02Type17 = DS02Type.Factory.newInstance();
                ds02Type17.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type17.setDispatchDta(DSRecordsConstants.DS02_HEADER_FACTS_OF_LOSS);
                ds02RecordList.add(ds02Type17);

                DS02Type ds02Type18 = DS02Type.Factory.newInstance();
                ds02Type18.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type18.setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                        + factsOfLossLine1.toString());
                ds02RecordList.add(ds02Type18);

                if (factsOfLossLine2 != null && factsOfLossLine2.length() > 0) {
                    DS02Type ds02Type19 = DS02Type.Factory.newInstance();
                    ds02Type19.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type19
                            .setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                                    + factsOfLossLine2.toString());
                    ds02RecordList.add(ds02Type19);
                }

                if (factsOfLossLine3 != null && factsOfLossLine3.length() > 0) {
                    DS02Type ds02Type20 = DS02Type.Factory.newInstance();
                    ds02Type20.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type20
                            .setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                                    + factsOfLossLine3.toString());
                    ds02RecordList.add(ds02Type20);
                }

                if (factsOfLossLine4 != null && factsOfLossLine4.length() > 0) {
                    DS02Type ds02Type21 = DS02Type.Factory.newInstance();
                    ds02Type21.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                    ds02Type21
                            .setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                                    + factsOfLossLine4.toString());
                    ds02RecordList.add(ds02Type21);
                }
            }
        }

        // Special Instructions.
        String specialInstructionsFull = meHelper
                .getEnvelopeContextNVPairValue(DSRecordsConstants.NVP_SF_ESTIMATE_ASSIGNMENT_FACTS);

        if (specialInstructionsFull != null && specialInstructionsFull.length() > 0) {

            String specialInstructionsLine1 = getSpecialInstructionsLine1(specialInstructionsFull);
            String specialInstructionsLine2 = getSpecialInstructionsLine2(specialInstructionsFull);
            String specialInstructionsLine3 = getSpecialInstructionsLine3(specialInstructionsFull);
            String specialInstructionsLine4 = getSpecialInstructionsLine4(specialInstructionsFull);

            // Add "Blank" line.
            DS02Type ds02Type22 = createDS02TypeBlankLine();
            ds02RecordList.add(ds02Type22);

            // Must be at least one line of special instructions.
            DS02Type ds02Type23 = DS02Type.Factory.newInstance();
            ds02Type23.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
            ds02Type23.setDispatchDta(DSRecordsConstants.DS02_HEADER_SPECIAL_INSTRUCTIONS);
            ds02RecordList.add(ds02Type23);

            DS02Type ds02Type24 = DS02Type.Factory.newInstance();
            ds02Type24.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
            ds02Type24.setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                    + specialInstructionsLine1);
            ds02RecordList.add(ds02Type24);

            if (specialInstructionsLine2 != null) {
                DS02Type ds02Type25 = DS02Type.Factory.newInstance();
                ds02Type25.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type25.setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                        + specialInstructionsLine2);
                ds02RecordList.add(ds02Type25);
            }

            if (specialInstructionsLine3 != null) {
                DS02Type ds02Type26 = DS02Type.Factory.newInstance();
                ds02Type26.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type26.setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                        + specialInstructionsLine3);
                ds02RecordList.add(ds02Type26);
            }

            if (specialInstructionsLine4 != null) {
                DS02Type ds02Type27 = DS02Type.Factory.newInstance();
                ds02Type27.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
                ds02Type27.setDispatchDta(DSRecordsConstants.DS02_FACTS_OF_LOSS_LEADING_SPACES
                        + specialInstructionsLine4);
                ds02RecordList.add(ds02Type27);
            }
        }
            
        // Convert from ListArray back to array of objects.
        retVal = new DS02Type[m_lineNum - 1];
        ds02RecordList.toArray(retVal);

        return retVal;
    }

    private String getFactsOfLossLine1(String lossDesc) {

        String retVal = null;

        retVal = getMaxLengthDS02Record(lossDesc,
                DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH);

        return retVal;
    }

    private String getFactsOfLossLine2(String lossDesc) {

        String retVal = null;

        if (lossDesc.length() > DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH) {

            retVal = getMaxLengthDS02Record(lossDesc
                    .substring(DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH),
                    DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE2_MAX_LENGTH);
        }

        return retVal;
    }

    private String getFactsOfLossLine3(String lossDesc) {

        String retVal = null;

        int line1PlusLine2 = DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH
                + DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE2_MAX_LENGTH;

        if (lossDesc.length() > line1PlusLine2) {

            retVal = getMaxLengthDS02Record(lossDesc.substring(line1PlusLine2),
                    DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE3_MAX_LENGTH);
        }

        return retVal;
    }

    private String getFactsOfLossLine4(String lossDesc) {

        String retVal = null;

        int line1PlusLine2PlusLine3 = DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE1_MAX_LENGTH
                + DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE2_MAX_LENGTH
                + DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE3_MAX_LENGTH;

        if (lossDesc.length() > line1PlusLine2PlusLine3) {

            retVal = getMaxLengthDS02Record(lossDesc.substring(line1PlusLine2PlusLine3),
                    DSRecordsConstants.DS02_FACTS_OF_LOSS_LINE4_MAX_LENGTH);
        }

        return retVal;
    }

    private String getSpecialInstructionsLine1(String specInstr) {

        String retVal = null;

        retVal = getMaxLengthDS02Record(specInstr,
                DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH);

        return retVal;
    }

    private String getSpecialInstructionsLine2(String specInstr) {

        String retVal = null;

        if (specInstr.length() > DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH) {

            retVal = getMaxLengthDS02Record(specInstr
                    .substring(DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH),
                    DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE2_MAX_LENGTH);
        }

        return retVal;
    }

    private String getSpecialInstructionsLine3(String specInstr) {

        String retVal = null;

        int line1PlusLine2 = DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH
                + DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE2_MAX_LENGTH;

        if (specInstr.length() > line1PlusLine2) {

            retVal = getMaxLengthDS02Record(specInstr.substring(line1PlusLine2),
                    DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE3_MAX_LENGTH);
        }

        return retVal;
    }

    private String getSpecialInstructionsLine4(String specInstr) {

        String retVal = null;

        int line1PlusLine2plusLine3 = DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE1_MAX_LENGTH
                + DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE2_MAX_LENGTH
                + DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE3_MAX_LENGTH;

        if (specInstr.length() > line1PlusLine2plusLine3) {

            retVal = getMaxLengthDS02Record(specInstr.substring(line1PlusLine2plusLine3),
                    DSRecordsConstants.DS02_SPECIAL_INSTRUCTIONS_LINE4_MAX_LENGTH);
        }

        return retVal;
    }

    private DS02Type createDS02TypeBlankLine() {

        // "Blank" line.
        DS02Type retVal = DS02Type.Factory.newInstance();
        retVal.setDispatchLineNbr(getLeadingZeroLineNumAndIncrement());
        retVal.setDispatchDta("");

        return retVal;
    }
    
    // Helper Method.
    protected static String rightTruncateString(String val, int maxLength) {
        
        String retVal = val;

        if (val != null) {
            int valLen = val.length();

            // Truncate from right, if too big.
            if (valLen > maxLength) {

                int startIndex = valLen - maxLength;
                retVal = val.substring(startIndex);
            }
        }

        return retVal;
    }

    // Helper Method.
    protected static String leftTruncateString(String val, int maxLength) {
        
        String retVal = val;

        if (val != null) {
            int valLen = val.length();

            // Truncate from left, if too big.
            if (valLen > maxLength) {
                retVal = val.substring(0, maxLength);
            }
        }

        return retVal;
    }
}
