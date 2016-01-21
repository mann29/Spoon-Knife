package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.compliance.LogsDocument;
import com.mitchell.schemas.estimate.AnnotatedEstimateDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

/**
 * BO which has the relevant details for processing supp request
 */
public class SupplementReqBO 
{ 
    
	private String toEmailAddress = null;
	private String ccEmailAddress = null;
	private String faxNumber = null;
	
	private long estimatorOrgId;
	private long reviewerOrgId;
	private long estimateDocId;
	
	private boolean isEmail = false;
	private boolean isFax = false;
	
	private UserInfoDocument estimatorUserInfo = null;
	private UserInfoDocument reviewerUserInfo = null;
	
	private UserDetailDocument estimatorUserDetail = null;
	private UserDetailDocument reviewerUserDetail = null;
    
    private String externalEstimateId = null;
    private AnnotatedEstimateDocument annotateDocument = null;
    private VehicleDamageEstimateAddRqDocument bmsDocument = null;
    private LogsDocument complianceDocument = null; 
    
    private long claimId;
    private long suffixId;
    
    private String taskId;
    private String emailLink;
    
    private String culture;
    // Client claim id formatted with Claim mask
    private String clientClaimId;
    // claim number splitted by claim mask
    private String claimNum;
    //suffix number splitted by claim mask
    private String suffixNum;
    //carrier specific static image URL 
    private String staticImageBaseUrl;
    //Carrier specific Signature image url
    private String signatureImage;
    //Carrier specific suffix label(heading)
    private String suffixLabel;
    private boolean isDaytonaShop;

    public boolean isDaytonaShop() {
        return isDaytonaShop;
    }

    public void setDaytonaShop(boolean isDaytonaShop) {
        this.isDaytonaShop = isDaytonaShop;
    }

	public String getSuffixLabel() {
		return suffixLabel;
	}
	public void setSuffixLabel(String suffixLabel) {
		this.suffixLabel = suffixLabel;
	}
	
	public String getSignatureImage() {
		return signatureImage;
	}
	public void setSignatureImage(String signatureImage) {
		this.signatureImage = signatureImage;
	}
	public String getClientClaimId() {
		return clientClaimId;
	}
	public void setClientClaimId(String clientClaimId) {
		this.clientClaimId = clientClaimId;
	}
	
	public String getClaimNum() {
		return claimNum;
	}
	public void setClaimNum(String claimNum) {
		this.claimNum = claimNum;
	}
	public String getSuffixNum() {
		return suffixNum;
	}
	public void setSuffixNum(String suffixNum) {
		this.suffixNum = suffixNum;
	}
	public String getCulture() {
		return culture;
	}
	public void setCulture(String culture) {
		this.culture = culture;
	}
	public String getEmailLink() {
        return emailLink;
    }
    public void setEmailLink(String emailLink) {
        this.emailLink = emailLink;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getCcEmailAddress() {
		return ccEmailAddress;
	}
	public void setCcEmailAddress(String ccEmailAddress) {
		this.ccEmailAddress = ccEmailAddress;
	}
	public long getEstimateDocId() {
		return estimateDocId;
	}
	public void setEstimateDocId(long estimateDocId) {
		this.estimateDocId = estimateDocId;
	}
	public long getEstimatorOrgId() {
		return estimatorOrgId;
	}
	public void setEstimatorOrgId(long estimatorOrgId) {
		this.estimatorOrgId = estimatorOrgId;
	}
	public UserDetailDocument getEstimatorUserDetail() {
		return estimatorUserDetail;
	}
	public void setEstimatorUserDetail(UserDetailDocument estimatorUserDetail) {
		this.estimatorUserDetail = estimatorUserDetail;
	}
	public UserInfoDocument getEstimatorUserInfo() {
		return estimatorUserInfo;
	}
	public void setEstimatorUserInfo(UserInfoDocument estimatorUserInfo) {
		this.estimatorUserInfo = estimatorUserInfo;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public boolean isEmail() {
		return isEmail;
	}
	public void setEmail(boolean isEmail) {
		this.isEmail = isEmail;
	}
	public boolean isFax() {
		return isFax;
	}
	public void setFax(boolean isFax) {
		this.isFax = isFax;
	}
	public long getReviewerOrgId() {
		return reviewerOrgId;
	}
	public void setReviewerOrgId(long reviewerOrgId) {
		this.reviewerOrgId = reviewerOrgId;
	}
	public UserDetailDocument getReviewerUserDetail() {
		return reviewerUserDetail;
	}
	public void setReviewerUserDetail(UserDetailDocument reviewerUserDetail) {
		this.reviewerUserDetail = reviewerUserDetail;
	}
	public UserInfoDocument getReviewerUserInfo() {
		return reviewerUserInfo;
	}
	public void setReviewerUserInfo(UserInfoDocument reviewerUserInfo) {
		this.reviewerUserInfo = reviewerUserInfo;
	}
	public String getToEmailAddress() {
		return toEmailAddress;
	}
	public void setToEmailAddress(String toEmailAddress) {
		this.toEmailAddress = toEmailAddress;
	}
	public String getExternalEstimateId() {
		return externalEstimateId;
	}
	public void setExternalEstimateId(String externalEstimateId) {
		this.externalEstimateId = externalEstimateId;
	}   
	public AnnotatedEstimateDocument getAnnotation() {
		return annotateDocument;
	}
	public void setAnnotation(AnnotatedEstimateDocument annotateDocument) {
		this.annotateDocument = annotateDocument;
	}    
	public LogsDocument getCompliance() {
		return complianceDocument;
	}
	public void setCompliance(LogsDocument complianceDocument) {
		this.complianceDocument = complianceDocument;
	}
	public VehicleDamageEstimateAddRqDocument getBMS() {
		return bmsDocument;
	}
	public void setBMS(VehicleDamageEstimateAddRqDocument bmsDocument) {
		this.bmsDocument = bmsDocument;
	}              
    public long getClaimId() {
        return claimId;    
    }     
    public void setClaimId(long id) {
        this.claimId = id;    
    }
    public long getSuffixId() {
        return suffixId;    
    }
    public void setSuffixId(long id) {
        this.suffixId = id;        
    }
    public String getStaticBaseUrl() {
		return staticImageBaseUrl;
	}
	public void setStaticBaseUrl(String staticBaseUrl) {
		this.staticImageBaseUrl = staticBaseUrl;
	}
	     
    
    
    
} 
