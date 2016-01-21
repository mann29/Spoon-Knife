package com.mitchell.services.business.partialloss.appraisalassignment.dto; 

import java.io.Serializable;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;

public class AssignmentDeliveryServiceDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private UserInfoDocument userInfo;
    
    private boolean isDRP = false;
    
    private UserInfoDocument drpUserInfo;

    private MitchellEnvelopeDocument mEnvDoc;
    
    // For TaskId - new in SIP2.
    private String workAssignmentId;
    
    private boolean isNonNetWorkShop;
    
    private boolean isShopPremium;
	
	 // The flag is not the ~NonNetWorkShop
    private boolean isNetWorkShop;
    
    /**
     * Default c'tor
     */
    public AssignmentDeliveryServiceDTO() {
    }
    
    /**
     * 
     * @param userInfo
     * @param workItemId
     * @param isDRP
     * @param drpUserInfo
     * @param mEnvDoc
     */
    public AssignmentDeliveryServiceDTO(UserInfoDocument userInfo, 
            boolean isDRP, UserInfoDocument drpUserInfo,
            MitchellEnvelopeDocument mEnvDoc) {
        this.userInfo = userInfo;
        this.isDRP = isDRP;
        this.drpUserInfo = drpUserInfo;
        this.mEnvDoc = mEnvDoc;
    }

    /**
     * 
     * @param userInfo
     * @param workItemId
     * @param isDRP
     * @param drpUserInfo
     * @param mEnvDoc
     * @param attachmentObjects
     */
    public AssignmentDeliveryServiceDTO(UserInfoDocument userInfo, 
            MitchellEnvelopeDocument mEnvDoc, 
            boolean isDRP, 
            UserInfoDocument drpUserInfo) {
        this.userInfo = userInfo;
        this.isDRP = isDRP;
        this.drpUserInfo = drpUserInfo;
        this.mEnvDoc = mEnvDoc;
    }

    /**
     * @return the mEnvDoc
     */
    public MitchellEnvelopeDocument getMitchellEnvDoc() {
        return mEnvDoc;
    }

    /**
     * @param mEnvDoc the mEnvDoc to set
     */
    public void setMitchellEnvDoc(MitchellEnvelopeDocument mEnvDoc) {
        this.mEnvDoc = mEnvDoc;
    }

    /**
     * @return the userInfo
     */
    public UserInfoDocument getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo the userInfo to set
     */
    public void setUserInfo(UserInfoDocument userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * @return the isDRP
     */
    public boolean isDrp() {
        return isDRP;
    }

    /**
     * @param isDRP the isDRP to set
     */
    public void setDrp(boolean isDRP) {
        this.isDRP = isDRP;
    }

    /**
     * @return the drpUserInfo
     */
    public UserInfoDocument getDrpUserInfo() {
        return drpUserInfo;
    }

    /**
     * @param drpUserInfo the drpUserInfo to set
     */
    public void setDrpUserInfo(UserInfoDocument drpUserInfo) {
        this.drpUserInfo = drpUserInfo;
    }
   /**
     * @return the workAssignmentId
     */
    public String getWorkAssignmentId() {
        return workAssignmentId;
    }

    /**
     * @param workAssignmentId the workAssignmentId to set
     */
    public void setWorkAssignmentId(String workAssignmentId) {
        this.workAssignmentId = workAssignmentId;
    }

    public boolean isNonNetWorkShop() {
        return isNonNetWorkShop;
    }

    public void setNonNetWorkShop(boolean isNonNetWorkShop) {
        this.isNonNetWorkShop = isNonNetWorkShop;
    }

    public boolean isShopPremium() {
        return isShopPremium;
    }

    public void setShopPremium(boolean isShopPremium) {
        this.isShopPremium = isShopPremium;
    }
	
	 public boolean isNetWorkShop() {
        return isNetWorkShop;
    }

    public void setNetWorkShop(boolean isNetWorkShop) {
        this.isNetWorkShop = isNetWorkShop;
    }
}
