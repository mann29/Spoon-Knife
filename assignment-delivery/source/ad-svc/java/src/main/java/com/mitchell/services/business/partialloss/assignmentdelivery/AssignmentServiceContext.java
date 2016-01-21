package com.mitchell.services.business.partialloss.assignmentdelivery; 

import java.io.Serializable;
import java.util.ArrayList;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;

public class AssignmentServiceContext implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private UserInfoDocument userInfo;
    
    private boolean isDRP = false;
    
    private UserInfoDocument drpUserInfo;

    private String workItemId;

    private MitchellEnvelopeDocument mEnvDoc;
    
    @SuppressWarnings("rawtypes")
    private ArrayList attachmentObjects = new ArrayList(0);

    // For TaskId - new in SIP2.
    private String workAssignmentId;
    
    private boolean isNonNetWorkShop;
	
	// The flag is not the ~NonNetWorkShop
    private boolean isNetWorkShop;
    
    private boolean isShopPremium;
    
    /**
     * Default c'tor
     */
    public AssignmentServiceContext() {
    }
    
    /**
     * 
     * @param userInfo
     * @param workItemId
     * @param isDRP
     * @param drpUserInfo
     * @param mEnvDoc
     */
    public AssignmentServiceContext(UserInfoDocument userInfo, 
            String workItemId, boolean isDRP, UserInfoDocument drpUserInfo,
            MitchellEnvelopeDocument mEnvDoc) {
        this.workItemId = workItemId;
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
    public AssignmentServiceContext(UserInfoDocument userInfo, 
            String workItemId, 
            MitchellEnvelopeDocument mEnvDoc, 
            boolean isDRP, 
            UserInfoDocument drpUserInfo, 
            ArrayList attachmentObjects) {
        this.workItemId = workItemId;
        this.userInfo = userInfo;
        this.isDRP = isDRP;
        this.drpUserInfo = drpUserInfo;
        this.mEnvDoc = mEnvDoc;
        this.attachmentObjects = attachmentObjects;
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
     * @return the workItemId
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * @param workItemId the workItemId to set
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
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
     * @return The attachment objects
     */
    public ArrayList getAttachmentObjects() {
        return attachmentObjects;
    }
    
    /**
     * @param attachmentObjects
     */
    public void setAttachmentObjects(ArrayList attachmentObjects) {
        this.attachmentObjects = attachmentObjects;
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

    /**
     * Validates the state of this object. It expects the
     * members to be non-null in order for the object to be valid.
     */
    public void validate() {
        StringBuffer msg = new StringBuffer();
        if (getAttachmentObjects() == null)
            msg.append("AttachmentObjects is null. ");
        if (getMitchellEnvDoc() == null)
            msg.append("MitchellEnvelopeDoc is null. ");
        if (getUserInfo() == null)
            msg.append("UserInfo is null. ");
        if (getWorkItemId() == null)
            msg.append("WorkItemId is null. ");
        if(isDRP == true) {
            if(getDrpUserInfo() == null) {
                msg.append("DrpUserInfo is null. ");
            }
        }
        if (msg.length() > 0) 
            throw new IllegalArgumentException(
                "AssignmentServiceContext is invalid: "+msg.toString());
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
