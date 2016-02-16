package com.mitchell.services.core.partialloss.apddelivery.utils; 

import java.util.Map;

/**
 * It contains all fields required for AppLogging in APDDelivery.
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class APDDeliveryContext { 
    /**
     * claim number.
     */
    private String claimNumber;
    /**
     * suffix id.
     */
    private long suffixId;
    /**
     * claim id.
     */
    private long claimId;
    /**
     * user id.
     */
    private String userId;
    /**
     * contains key-value pairs for AppLogging.
     */
    private Map map;
    /**
     * app code.
     */
    private String transactionType;
    /**
     * work item id.
     */
    private String workItemId;
    
    /**
     * Set claim number.
     * @param claimNumberArg
     * claim number
     */
    public void setClaimNumber(String claimNumberArg) {
        this.claimNumber = claimNumberArg;    
    }
    /**
     * Get claim number.
     * @return String
     */
    public String getClaimNumber() {
        return this.claimNumber;
    }    
    /**
     * Set suffix id.
     * @param suffixIdArg
     * suffix id
     */
    public void setSuffixId(long suffixIdArg) {
        this.suffixId = suffixIdArg;
    }
    /**
     * Get suffix id.
     * @return long
     */
    public long getSuffixId() {
        return this.suffixId;    
    }
    /**
     * Set claim id.
     * @param claimIdArg
     * claim id
     */
    public void setClaimId(long claimIdArg) {
        this.claimId = claimIdArg;
    }
    /**
     * Get claim id.
     * @return long
     */
    public long getClaimId() {
        return this.claimId;    
    }
    /**
     * Set user id.
     * @param userIdArg
     * user id
     */
    public void setUserId(String userIdArg) {
        this.userId = userIdArg;    
    }
    /**
     * Get user id.
     * @return String
     */
    public String getUserId() {
        return this.userId;
    }   
    /**
     * Set map containing key-value pairs for AppLogging.
     * @param mapArg
     * Map
     */
    public void setMap(Map mapArg) {
        this.map = mapArg;    
    }
    /**
     * get map containing key-value pairs for AppLogging.
     * @return Map
     */
    public Map getMap() {
        return this.map;
    }    
    /**
     * Set app code.
     * @param typeArg
     * app code
     */
    public void setTransactiontype(String typeArg) {
        this.transactionType = typeArg;    
    }
    /**
     * Get app code.
     * @return String
     */
    public String getTransactiontype() {
        return this.transactionType;
    }
    /**
     * Set work item id.
     * @param idArg
     * work item id
     */
    public void setWorkItemId(String idArg) {
        this.workItemId = idArg;    
    }    
    /**
     * Get work item id.
     * @return String
     */
    public String getWorkItemId() {
        return this.workItemId;
    }
   
} 
