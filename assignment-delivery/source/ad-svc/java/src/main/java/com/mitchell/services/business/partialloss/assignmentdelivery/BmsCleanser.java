package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.cieca.bms.AddressType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.EstimatorType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.OtherPartyType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;

/**
 * Class to house BMS cleaning activities related to sending assignments.
 */
public class BmsCleanser {

	protected static final char MIE_CLEANSE_REPLACE_CHAR = ' ';

	/**
	 * Empty constructor.
	 */
	public BmsCleanser() {
	}

	/**
	 * Cleans For MIE Transformation. The intent of this method is to house
	 * "cleansing" operations that must occur on the BMS in order to transform
	 * to an assignment MIE. Ideally the cleansing would occur in the
	 * transformation but it is MUM at the point in time of the creation of this
	 * class... If an AssignmentAddRq exists in the provide CIECA Document then
	 * a cleansed version of the CIECADocument is returned, otherwise a copy of
	 * the provided Document is returned as is. The fields that are cleansed are
	 * based on the logic in the BMS to MIE transformation, not every single
	 * field in the BMS.
	 * 
	 * @param ciecaDoc
	 *            A CIECADocument. Nothing really happens unless the
	 *            AssignmentAddRq is populated.
	 * 
	 * @return Returns a copy of the input CIECADocument. In the case where the
	 *         input does include an AssignmentAddRq, that portion of the
	 *         Document is cleansed for "to MIE" transformation.
	 */
	public CIECADocument cleansForMIETransformation(CIECADocument ciecaDoc) {
		CIECADocument cDoc = null;
		if (ciecaDoc != null) {

			// Make a copy of the document so that the input is not modified
			cDoc = (CIECADocument) ciecaDoc.copy();

			// If there is an assignment then cleanse it
			AssignmentAddRq asgAddRq = cDoc.getCIECA().getAssignmentAddRq();
			if (asgAddRq != null) {

				asgAddRq = cleanseAsgAddRqForMIE(asgAddRq);

			}

		}
		return cDoc;
	}

	/**
	 * Cleanse Assignment AddRq. Manages the processing of cleansing the
	 * provided AssignmentAddRq for transformation to MIE.
	 */
	protected AssignmentAddRq cleanseAsgAddRqForMIE(AssignmentAddRq asgAddRq) {

		// AdminInfo CompanyName
		asgAddRq = cleanseAdminInfoForMIE(asgAddRq);

		//
		return asgAddRq;
	}

	/**
	 * Cleanse AdminInfo SubRecords.
	 */
	protected AssignmentAddRq cleanseAdminInfoForMIE(AssignmentAddRq asgAddRq) {
		AdminInfoType admType = asgAddRq.getAdminInfo();

		// PolicyHolder
		if (admType.isSetPolicyHolder()) {
			PartyType pt = cleansePartyForMIE(admType.getPolicyHolder()
					.getParty());
		}

		// Insured
		if (admType.isSetInsured()) {
			PartyType pt = cleansePartyForMIE(admType.getInsured().getParty());
		}

		// Claimant
		if (admType.isSetClaimant()) {
			PartyType pt = cleansePartyForMIE(admType.getClaimant().getParty());
		}

		// Owner
		if (admType.isSetOwner()) {
			PartyType pt = cleansePartyForMIE(admType.getOwner().getParty());
		}

		// Adjuster
		if (admType.isSetAdjuster()) {
			PartyType pt = cleansePartyForMIE(admType.getAdjuster().getParty());
		}

		// InsuranceCompany
		if (admType.isSetInsuranceCompany()) {
			PartyType pt = cleansePartyForMIE(admType.getInsuranceCompany()
					.getParty());
		}

		// RepairFacility
		if (admType.isSetRepairFacility()) {
			PartyType pt = cleansePartyForMIE(admType.getRepairFacility()
					.getParty());
		}

		// InspectionSite
		if (admType.isSetInspectionSite()) {
			PartyType pt = cleansePartyForMIE(admType.getInspectionSite()
					.getParty());
		}

		// OtherPartyArray
		OtherPartyType[] opa = admType.getOtherPartyArray();
		if (opa != null && opa.length > 0) {
			for (OtherPartyType opt : opa) {
				PartyType pt = cleansePartyForMIE(opt.getParty());
			}
		}

		// EstimatorArray
		EstimatorType[] estArray = admType.getEstimatorArray();
		if (estArray != null && estArray.length > 0) {
			for (EstimatorType et : estArray) {
				PartyType pt = cleansePartyForMIE(et.getParty());
			}
		}

		//
		return asgAddRq;
	}

	/**
	 * Cleanse Party For MIE.
	 */
	protected PartyType cleansePartyForMIE(PartyType pt) {
		if (pt != null) {

			// OrgInfo
			if (pt.isSetOrgInfo()) {
				OrgInfoType oit = cleanseOrgInfoForMIE(pt.getOrgInfo());
			} else if (pt.isSetPersonInfo()) {
				PersonInfoType pit = cleansePersonInfoForMIE(pt.getPersonInfo());
			}
		}

		//
		return pt;
	}

	/**
	 * Cleanse OrgInfo For MIE.
	 */
	protected OrgInfoType cleanseOrgInfoForMIE(OrgInfoType orgInfo) {
		if (orgInfo != null) {

			// Company Name
			if (orgInfo.isSetCompanyName()) {
				orgInfo.setCompanyName(orgInfo.getCompanyName());
			}

		}
		return orgInfo;
	}

	/**
	 * Cleanse PersonInfo For MIE.
	 */
	protected PersonInfoType cleansePersonInfoForMIE(PersonInfoType personInfo) {
		if (personInfo != null) {

			// Name
			if (personInfo.getPersonName() != null) {

				// FirstName
				if (personInfo.getPersonName().isSetFirstName()
						&& personInfo.getPersonName().getFirstName() != null) {
					personInfo.getPersonName().setFirstName(
							personInfo.getPersonName().getFirstName());
				}

				// LastName
				if (personInfo.getPersonName().isSetLastName()
						&& personInfo.getPersonName().getLastName() != null) {
					personInfo.getPersonName().setLastName(
							personInfo.getPersonName().getLastName());
				}

			}

			// Communications Array
			cleanseCommsForMIE(personInfo.getCommunicationsArray());

		}
		return personInfo;
	}

	/**
	 * Cleanse Comms For MIE.
	 */
	protected void cleanseCommsForMIE(CommunicationsType[] comms) {
		if (comms != null && comms.length > 0) {
			for (CommunicationsType ct : comms) {
				if (ct.isSetAddress()) {

					// Address
					cleanseAddress(ct.getAddress());

				}
			}
		}
	}

	/**
	 * Cleanse Address.
	 */
	protected AddressType cleanseAddress(AddressType address) {
		if (address != null) {

			if (address.isSetAddress1() && address.getAddress1() != null) {
				address.setAddress1(address.getAddress1());
			}

			if (address.isSetAddress2() && address.getAddress2() != null) {
				address.setAddress2(address.getAddress2());
			}

			if (address.isSetCity() && address.getCity() != null) {
				address.setCity(address.getCity());
			}

		}
		return address;
	}
}
