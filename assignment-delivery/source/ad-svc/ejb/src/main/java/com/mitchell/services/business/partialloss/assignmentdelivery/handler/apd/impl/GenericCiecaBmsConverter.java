package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.util.logging.Level;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.CoverageInfoType;
import com.cieca.bms.PolicyInfoType;
import com.cieca.bms.CoverageInfoType.Coverage;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;

/**
 * Perform generic mappings of an input CIECA BMS to output CIECA BMS, else return copy of input unchanged.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 *
 */
public class GenericCiecaBmsConverter implements CiecaBmsConverter {

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter#convert(com.cieca.bms.CIECADocument)
	 */
	public CIECADocument convert(CIECADocument input) {
		
		 final java.util.logging.Logger mLogger = java.util.logging.Logger
         .getLogger(GenericCiecaBmsConverter.class.getName());
		
		// Return a "copy" of Transformed CiecaBms , Else return "copy" of the Original CiecaBms
		CIECADocument outCiecaDocument = input;

		// Perform "special" StateFarm mapping of CoverageCategory (LossType)
		outCiecaDocument = updateCoverageCategory(input, mLogger);
		
        if (mLogger.isLoggable(Level.INFO)) {
        	mLogger.info("********* GenericCiecaBmsConverter updateCoverageCategory:: Cieca BMS outCiecaDocument: [ " + outCiecaDocument + " *********]");
        }										        
	
		
		return outCiecaDocument;	
		
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
								        if (mLogger.isLoggable(Level.INFO)) {
								        	mLogger.info("********* BEFORE  updateCoverageCategory:: coverage.getCoverageCategory(): [ " + coverage.getCoverageCategory() + " ] *********");
								        }										        
										/* Work Center Code	Description	WCAP
								        A	Animal	O
								        C	Collision	C,G
								        E	Mechanical Inspection	O
								        F	Fire	F
								        H	Hail	O
								        I	Single Interest	O
								        L	Liability	O
								        M	Comprehensive	D,M
								        O	Other	O
								        P	Property	A,P
								        S	Water/Flood/Submersion	O
								        T	Theft	T
								        U	Unknown	O
								        V	Vandalism	O */

										if (coverage.getCoverageCategory() != null) {
											final String inCoverageCategory = coverage.getCoverageCategory();
											if (inCoverageCategory.equalsIgnoreCase("C")) {
												coverage.setCoverageCategory("C");
											} else if (inCoverageCategory.equalsIgnoreCase("P")) {
												coverage.setCoverageCategory("P");
											} else if (inCoverageCategory.equalsIgnoreCase("M")) {
												coverage.setCoverageCategory("M");
											} else if (inCoverageCategory.equalsIgnoreCase("F")) {
												coverage.setCoverageCategory("F");
											}  else if (inCoverageCategory.equalsIgnoreCase("T")) {
												coverage.setCoverageCategory("T");
											}else
												coverage.setCoverageCategory("O");
										}	
											
								        if (mLogger.isLoggable(Level.INFO)) {
								        	mLogger.info("********* AFTER  updateCoverageCategory:: coverage.getCoverageCategory(): [ " + coverage.getCoverageCategory() + " ] *********");
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
