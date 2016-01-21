package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 *  Class which caches the lookup codes for MIE , BMS
 * 
 */

public class BMSMIECodesMapper 
{ 

	private static Map vendorRefMap = null;
	private static Map laborOperationMap = null;
	private static Map partTypeMap = null;
	private static Map laborTypeMap = null;
	private static Map exceptionCodesMap = null;
    
	static {
		loadVendorMap();
		loadLaborOperationMap();
		loadPartTypeMap();
		loadLaborType();
		loadExceptionCodesMap();
	} 
    
	static String getVendorReference(String code) {
		String value = "";
		
		if(vendorRefMap.containsKey(code)) {
			value = (String)vendorRefMap.get(code);
		}
		
		return value;
	}
	
	static String getLaborOperation(String code) {
		String value = "";
		
		if(laborOperationMap.containsKey(code)) {
			value = (String)laborOperationMap.get(code);
		}
		
		return value;
	}
	
	static String getPartTypeCode(String code, boolean subletIndicator) {
		String value = "";
		
		if(partTypeMap.containsKey(code)) {
			value = (String)partTypeMap.get(code);
			
			if(subletIndicator && "PAE".equals(code)) {
				value = "SL";
			}
		}
		
		return value;
	}
	
	static String getLaborType(String code) {
		String value = "";
		
		if(laborTypeMap.containsKey(code)) {
			value = (String)laborTypeMap.get(code);
		}
		
		return value;
	}
	
	static String getExceptionDescription(String code) {
		String value = "";
		
		if(exceptionCodesMap.containsKey(code)) {
			value = (String)exceptionCodesMap.get(code);
		}
		
		return value;		
	}    
    
	private static void loadVendorMap() {
		
		if(vendorRefMap == null) {
			vendorRefMap = new HashMap();
		}
		
		vendorRefMap.put("900506", "BETTERMENT-P");
		vendorRefMap.put("900507", "BETTERMENT-L");
		vendorRefMap.put("900505", "BETTERMENT-PL");
		vendorRefMap.put("900510", "LINE MARKUP");
		vendorRefMap.put("900511", "LINE DISCOUNT");
		
		vendorRefMap.put("936000", "ADD'L COST");
		vendorRefMap.put("936001", "ADD'L COST");
		vendorRefMap.put("936002", "ADD'L COST");
		vendorRefMap.put("936003", "ADD'L COST");
		vendorRefMap.put("936004", "ADD'L COST");
		vendorRefMap.put("936005", "ADD'L COST");
		vendorRefMap.put("936006", "ADD'L COST");
		vendorRefMap.put("936007", "ADD'L COST");
		vendorRefMap.put("936008", "ADD'L COST");
		vendorRefMap.put("936009", "ADD'L COST");
		vendorRefMap.put("936010", "ADD'L COST");
		vendorRefMap.put("936011", "ADD'L COST");
		vendorRefMap.put("936012", "ADD'L COST");
		vendorRefMap.put("936013", "ADD'L COST");
		vendorRefMap.put("936014", "ADD'L COST");
		vendorRefMap.put("936015", "ADD'L COST");
		vendorRefMap.put("936016", "ADD'L COST");
		vendorRefMap.put("936017", "ADD'L COST");
		vendorRefMap.put("936018", "ADD'L COST");
	}
	
	private static void loadLaborOperationMap() {
		
		if(laborOperationMap == null) {
			laborOperationMap = new HashMap();
		}
		
		laborOperationMap.put("OP1", "REFINISH");
		laborOperationMap.put("OP6", "REFINISH");
		laborOperationMap.put("OP2", "REMOVE/INSTALL");
		laborOperationMap.put("OP3", "ADD'L LABOR");
		laborOperationMap.put("OP4", "ALIGN");
		laborOperationMap.put("OP5", "OVERHAUL");
		laborOperationMap.put("OP7", "ACCESS/INSPECT");
		laborOperationMap.put("OP8", "CHECK/ADJUST");
		laborOperationMap.put("OP9", "REPAIR");
		laborOperationMap.put("OP13", "ADD'L COST");
		laborOperationMap.put("OP11", "REMOVE/REPLACE");
		laborOperationMap.put("OP14", "ADD'L OPR");
		laborOperationMap.put("OP15", "BLEND");
		laborOperationMap.put("OP17", "RELATED PRIOR");
		laborOperationMap.put("OP19", "UNRELATED PRIOR");
		laborOperationMap.put("OP26", "PAINTLESS REPAIR");
        
        laborOperationMap.put("OP0", " ");
        laborOperationMap.put("OP10", "REPAIR/PARTIAL");
        laborOperationMap.put("OP12", "REMOVE/REPLACE/PARTIAL");
        laborOperationMap.put("OP16", "SUBLET");
        laborOperationMap.put("OP18", "APPEARANCE ALLOWANCE");
        laborOperationMap.put("OP24", "CHIP GUARD");
        laborOperationMap.put("OP25", "TWO TONE");
	}
	
	private static void loadPartTypeMap() {
		
		if(partTypeMap == null) {
			partTypeMap = new HashMap();
		}
		
		partTypeMap.put("PAN", "NW");
		partTypeMap.put("PAA", "AM");
		partTypeMap.put("PAE", "EX");
		partTypeMap.put("PAL", "LK");
		partTypeMap.put("PAR", "RC");
		partTypeMap.put("PAC", "RH");
		partTypeMap.put("PAM", "RM");
        
        partTypeMap.put("PAD", "RH");
        partTypeMap.put("PAND", "OED");
        partTypeMap.put("PAO", "OT");
        partTypeMap.put("PAP", "NP");
		
	}
	
	private static void loadLaborType() {
		
		if(laborTypeMap == null) {
			laborTypeMap = new HashMap();
		}
		
		laborTypeMap.put("LAB", "BDY");
		laborTypeMap.put("LAF", "FRM");
		laborTypeMap.put("LAG", "GLS");
		laborTypeMap.put("LAM", "MCH");
		laborTypeMap.put("LAR", "REF");
		laborTypeMap.put("LAS", "BDS");
        
        laborTypeMap.put("LAE", "ELE");
        laborTypeMap.put("LAD", "DIA");
        laborTypeMap.put("LAP", "PDR");
        laborTypeMap.put("LA1", "UD1");
        laborTypeMap.put("LA2", "UD2");
        laborTypeMap.put("LA3", "UD3");
        laborTypeMap.put("LA4", "UD4");
	}
	
	private static void loadExceptionCodesMap() {
		
		if(exceptionCodesMap == null) {
			exceptionCodesMap = new HashMap();
		}
		
		exceptionCodesMap.put("AP", "ALTERNATE PART");
		exceptionCodesMap.put("AA", "APPERANCE ALLOWANCE");
		exceptionCodesMap.put("BD", "BETTERMENT/DEPRECIATION");
		exceptionCodesMap.put("BL", "BODY LABOR");
		exceptionCodesMap.put("FR", "FLAT RATE");
		exceptionCodesMap.put("MD", "MISSED DAMAGE");
		exceptionCodesMap.put("ND", "NO DAMAGE");
		exceptionCodesMap.put("NR", "NOT REQUIRED");
		exceptionCodesMap.put("ODI", "OLD DAMAGE INC");
		exceptionCodesMap.put("ODN", "OLD DAMAGE NOTED");
		exceptionCodesMap.put("PP", "PART PRICE INCORRECT");
		exceptionCodesMap.put("PE", "PROFILE EXCEPTIONS");
		exceptionCodesMap.put("QC", "QUALITY COMMENTS");
		exceptionCodesMap.put("RL", "REFINISH LABOR");
		exceptionCodesMap.put("RR", "REPAIR/REPLACE DECISION");
		exceptionCodesMap.put("SB", "SPOT/BLEND");
		exceptionCodesMap.put("SL", "STRUCTURAL LABOR");
		exceptionCodesMap.put("SU", "SUPPLEMENT EXCEPTION");
		exceptionCodesMap.put("VI", "VEHICLE IDENTIFICATION");
	}
        
} 
