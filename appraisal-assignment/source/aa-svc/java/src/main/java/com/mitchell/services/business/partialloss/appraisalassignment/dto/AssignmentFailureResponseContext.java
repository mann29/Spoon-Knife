/**
 * 
 */
package com.mitchell.services.business.partialloss.appraisalassignment.dto;

import java.util.Map;

/**
 * @author rk104152
 *
 */
public class AssignmentFailureResponseContext {
	
	/**
     * claimNumber.
     */
    private String claimNumber;
    
    /**
     * userId.
     */
    private String userId;
    
    /**
     * map.
     */
    private Map map;
    
    /**
     * transactionType.
     */
    private String transactionType;
    
    /**
     * workItemId.
     */
    private String workItemId;
    
    /**
     * suffixId.
     */
    private long suffixId;
    
    /**
     * suffixNumber.
     */
    private String suffixNumber;
    
    /**
     * claimId.
     */
    private long claimId;
    
    /**
     * coCode.
     */
    private String coCode;
    
    /**
	 * @param claimNumberArg the claimNumber to set
	 */    
    public void setClaimNumber(String claimNumberArg) {
        this.claimNumber = claimNumberArg;    
    }
    
    /**
	 * @return the claimNumber
	 */
    public String getClaimNumber() {
        return this.claimNumber;
    }    
    
    /**
	 * @param userIdArg the userId to set
	 */
    public void setUserId(String userIdArg) {
        this.userId = userIdArg;    
    }
    
    /**
	 * @return the userId
	 */
    public String getUserId() {
        return this.userId;
    }   
    
    /**
	 * @param mapArg the map to set
	 */
    public void setMap(Map mapArg) {
        this.map = mapArg;    
    }
    
    /**
	 * @return the map
	 */
    public Map getMap() {
        return this.map;
    }    
    
    /**
	 * @param typeArg the transactionType to set
	 */
    public void setTransactiontype(String typeArg) {
        this.transactionType = typeArg;    
    }
    
    /**
	 * @return the transactionType
	 */
    public String getTransactiontype() {
        return this.transactionType;
    }
    
    /**
	 * @param idArg the workItemId to set
	 */
    public void setWorkItemId(String idArg) {
        this.workItemId = idArg;    
    }    
    
    /**
	 * @return the workItemId
	 */
    public String getWorkItemId() {
        return this.workItemId;
    }
    
    /**
	 * @param suffixIdArg the suffixId to set
	 */
    public void setSuffixId(long suffixIdArg) {
        this.suffixId = suffixIdArg;
    }
    
    /**
	 * @return the suffixId
	 */
    public long getSuffixId() {
        return this.suffixId;    
    }
    
	/**
	 * @param suffixNumberArg the suffixNumber to set
	 */
    public void setSuffixNumber(String suffixNumberArg) {
        this.suffixNumber = suffixNumberArg;
    }
    
    /**
	 * @return the suffixId
	 */
    public String getSuffixNumber() {
        return this.suffixNumber;    
    }
	
    
    /**
	 * @param claimIdArg the claimId to set
	 */
    public void setClaimId(long claimIdArg) {
        this.claimId = claimIdArg;
    }
    
    /**
	 * @return the claimId
	 */
    public long getClaimId() {
        return this.claimId;    
    }
    
    /**
	 * @param coCodeArg the coCode to set
	 */
    public void setCoCode(String coCodeArg) {
        this.coCode = coCodeArg;    
    }    
    
    /**
	 * @return the coCode
	 */
    public String getCoCode() {
        return this.coCode;    
    }
    
    
}
