package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.util.logging.Level;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.cieca.bms.CoverageInfoType;
import com.cieca.bms.CoverageInfoType.Coverage;
import com.cieca.bms.PolicyInfoType;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;

/**
 * Perform specific StateFarm Staff mappings of an input CIECA BMS to output CIECA BMS.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 *
 */
public class SFStaffCiecaBmsConverter implements CiecaBmsConverter {

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter#convert(com.cieca.bms.CIECADocument)
	 */
	public CIECADocument convert(CIECADocument input) {

	    /**
	     * Class mLogger.
	     */
	    final java.util.logging.Logger mLogger = java.util.logging.Logger
	            .getLogger(SFStaffCiecaBmsConverter.class.getName());

        if (mLogger.isLoggable(Level.FINE)) {
        	mLogger.fine("convert:: Cieca BMS input: [ " + input + " ]");
        }		

        // Return a "copy" of Transformed CiecaBms , Else return "copy" of the Original CiecaBms
		CIECADocument outCiecaDocument = input;
		
		// Perform "special" StateFarm mapping of CoverageCategory (LossType)
		outCiecaDocument = updateCoverageCategory(input, mLogger);
		
        if (mLogger.isLoggable(Level.FINE)) {
        	mLogger.fine("********* updateCoverageCategory:: Cieca BMS outCiecaDocument: [ " + outCiecaDocument + " *********]");
        }										        
	
		
		return outCiecaDocument;		
	}
	
	/**
	 * Perform mapping of CoverageCategory (LossType)
	 *    - C maps to G  - Collision
	 *    - P maps to A  - Property Damage
	 *    - L maps to A  - Property Damage	(new 6Jan14)    
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
										//              L to A (new 6Jan14) 
										//              A (Animal) maps to 0 Else no mapping 
										if (coverage.getCoverageCategory() != null) {
											final String inCoverageCategory = coverage.getCoverageCategory();
											if (inCoverageCategory.equalsIgnoreCase("C")) {
												coverage.setCoverageCategory("G");
											} else if (inCoverageCategory.equalsIgnoreCase("P")) {
												coverage.setCoverageCategory("A");
											} else if (inCoverageCategory.equalsIgnoreCase("L")) {  // (new 6Jan14) 
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
