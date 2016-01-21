package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.util.logging.Level;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.ContactInfoType;
import com.cieca.bms.CoverageInfoType;
import com.cieca.bms.GenericPartyType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PolicyInfoType;
import com.cieca.bms.CoverageInfoType.Coverage;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;

/**
 * Perform specific StateFarm Shop mappings of an input CIECA BMS to output CIECA BMS.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 * 
 */
public class SFShopCiecaBMSConverter implements CiecaBmsConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.business.partialloss.assignmentdelivery.handler
	 * .apd.CiecaBmsConverter#convert(com.cieca.bms.CIECADocument)
	 */
	public CIECADocument convert(CIECADocument inDoc) {

		/**
		 * Class mLogger.
		 */
		final java.util.logging.Logger mLogger = java.util.logging.Logger
				.getLogger(SFShopCiecaBMSConverter.class.getName());

		// Return a "copy" of Transformed CiecaBms , Else return "copy" of the Original CiecaBms
		CIECADocument outCiecaDocument = inDoc;

		if (mLogger.isLoggable(Level.FINE)) {
			mLogger.fine("convert:: Cieca BMS inDoc: [" + inDoc + "]");
		}
		// Perform re-mapping of InspectionSite/Orginfo aggregate, 
		//       relocate CommPhone in new ContactInfo
		outCiecaDocument = updateInspectionSiteOrgInfoAddContactInfo(inDoc,mLogger);

		// Perform "special" StateFarm mapping of CoverageCategory (LossType)
		outCiecaDocument = updateCoverageCategory(outCiecaDocument, mLogger);

		if (mLogger.isLoggable(Level.FINE)) {
			mLogger.fine("convert:: Cieca BMS outCiecaDocument: [" + outCiecaDocument + "]");
		}
		return outCiecaDocument;
	}

	/**
	 * @param inDoc
	 * @param mLogger
	 */
	private CIECADocument updateInspectionSiteOrgInfoAddContactInfo(
			CIECADocument inDoc, final java.util.logging.Logger mLogger) {

		if (inDoc != null && inDoc.getCIECA() != null
				&& inDoc.getCIECA().getAssignmentAddRq() != null) {

			final AdminInfoType adminInfo = inDoc.getCIECA().getAssignmentAddRq().getAdminInfo();

			if (adminInfo != null) {
				if (adminInfo.isSetInspectionSite()) {
					final GenericPartyType inspectSite = adminInfo.getInspectionSite();
					final PartyType inspectSiteParty = inspectSite.getParty();
					final OrgInfoType orgInfo = inspectSiteParty.getOrgInfo();

					if (inspectSiteParty.isSetOrgInfo()) {
						if (orgInfo != null) {
							CommunicationsType[] communicationsTypeArray = orgInfo
									.getCommunicationsArray();
							final int lenCommunicationsTypeArr = communicationsTypeArray.length;
							int removeCommArrElement = -1;
							if (orgInfo.getCommunicationsArray() != null) {
								for (int i = 0; i < lenCommunicationsTypeArr; i++) {
									CommunicationsType orgInfoCommArrayN = orgInfo.getCommunicationsArray(i);

									// Temp Debug - Remove after testing
									if (mLogger.isLoggable(Level.FINE)) {
										mLogger.fine("convert::  i=[ " + i + "]"  
												+ "  --- 1) Initial orgInfo: [ " + orgInfo + " ] ");
									}
									if (orgInfoCommArrayN != null) {
										if (orgInfoCommArrayN.isSetCommPhone()) {
											ContactInfoType contactInfoTyp = inspectSiteParty.addNewContactInfo();
											CommunicationsType addContactInfoCommType = contactInfoTyp.addNewCommunications();
											addContactInfoCommType.setCommPhone(orgInfoCommArrayN.getCommPhone());
											addContactInfoCommType.setCommQualifier(orgInfoCommArrayN.getCommQualifier());
											removeCommArrElement = i;
											// Temp Debug
											if (mLogger.isLoggable(Level.FINE)) {
												mLogger.fine("convert:: 2) i= [ " + i + " ], lenCommunicationsTypeArr: [ "
														+ (lenCommunicationsTypeArr) + " ]");
												mLogger.fine("Have CommPhone!! -- \nconvert:: 2) orgInfo: [ " + orgInfo + " ]");
											}
											// Add addContactInfoCommType to OrgInfo Aggregate
											orgInfo.setCommunicationsArray((lenCommunicationsTypeArr - 2),	addContactInfoCommType);

											// Temp Debug
											if (mLogger.isLoggable(Level.FINE)) {
												mLogger.fine("After set addContactInfoCommType (lenCommunicationsTypeArr-2)"
														+ " --- \nconvert:: 3) orgInfo: [ " + orgInfo + " ]");
											}
										}
									}
									// Temp Debug
									if (mLogger.isLoggable(Level.FINE)) {
										mLogger.fine("Before removeCommunications CommPhone --- \nconvert:: 4) orgInfo: [ " + orgInfo + " ]");
										mLogger.fine("convert:: 4) removeCommArrElement: [ " + removeCommArrElement + " ]");
									}

									// Remove CommPhone Aggregate from OrgInfo
									if (removeCommArrElement >= 0) {
										orgInfo.removeCommunications(removeCommArrElement);
									}
									// Temp Debug
									if (mLogger.isLoggable(Level.FINE)) {
										mLogger.fine("convert:: After removeCommunications CommPhone, removeCommArrElement: [ " + removeCommArrElement + " ]");
										mLogger.fine("After removeCommunications --- \nconvert:: 5) orgInfo: [ " + orgInfo + " ]");
									}
									break;
								}
							}
						}
					} 
				}
			}
		}
		
		return inDoc;  // Return updated inDoc
	}
	
	/**
	 * Perform mapping of CoverageCategory (LossType)
	 *    - C maps to G  - Collision
	 *    - P maps to A  - Property Damage
	 *    - M maps to D  - Comprehensive
	 *    - A maps to O  - A-Animal to O-Other
	 * @param inDoc
	 * @param mLogger
	 * @return
	 */
	private CIECADocument updateCoverageCategory(final CIECADocument inDoc, final java.util.logging.Logger mLogger) {
		
		if (inDoc != null && inDoc.getCIECA() != null && inDoc.getCIECA().getAssignmentAddRq() != null) {
			
			//
			// <ClaimInfo>/<PolicyInfo>/<CoverageInfo>/<Coverage>/<CoverageCategory>
			//
			final ClaimInfoType claimInfo = inDoc.getCIECA().getAssignmentAddRq().getClaimInfo();
			if (claimInfo != null) {
				if (claimInfo.isSetPolicyInfo()) {
					final PolicyInfoType policyInfo = claimInfo.getPolicyInfo();
					if (policyInfo != null) {
						if (policyInfo.isSetCoverageInfo()) {
							final CoverageInfoType coverageInfo = policyInfo.getCoverageInfo();
							if (coverageInfo != null && coverageInfo.sizeOfCoverageArray() > 0) {
								final Coverage coverage = coverageInfo.getCoverageArray(0);
								if (coverage != null) {
									if (coverage.isSetCoverageCategory()) {
								        if (mLogger.isLoggable(Level.FINE)) {
								        	mLogger.fine("********* BEFORE  updateCoverageCategory:: coverage.getCoverageCategory(): [ " + coverage.getCoverageCategory() + " ] *********");
								        }										        
										// TypeOfLoss - C maps to G, P maps to A, M maps to D, 
										//              A (Animal) maps to 0 Else no mapping 
										if (coverage.getCoverageCategory() != null) {
											final String inCoverageCategory = coverage.getCoverageCategory();
											if (inCoverageCategory.equalsIgnoreCase("C")) {
												coverage.setCoverageCategory("G");
											} else if (inCoverageCategory.equalsIgnoreCase("P")) {
												coverage.setCoverageCategory("A");
											} else if (inCoverageCategory.equalsIgnoreCase("M")) {
												coverage.setCoverageCategory("D");
											} else if (inCoverageCategory.equalsIgnoreCase("A")) {
												// Map A (Animal) to O (Other) - preventing collision with A (Property)
												coverage.setCoverageCategory("0");
											} 
											// else NO MAPPING of remaining LossTypes
										}
								        if (mLogger.isLoggable(Level.FINE)) {
								        	mLogger.fine("********* AFTER  updateCoverageCategory:: coverage.getCoverageCategory(): [ " + coverage.getCoverageCategory() + " ] *********");
								        }										        
									}
								}
							}
						}
					}
				}
			}
		}
		return inDoc;
	}
	
}
