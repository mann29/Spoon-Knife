package com.mitchell.services.business.partialloss.appraisalassignment.proxy;


import java.util.List;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignmentUpdateFieldEnum;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public interface EstimatePackageProxy {

	public Estimate[] findEstimateByClaimNumber(String companyCode, String userID, String claimSuffixNumber)
            throws MitchellException;

    public Document getDocumentByEstimateId(long estimateId) throws MitchellException;

    public com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO getAppraisalAssignmentDocument(long documentId) throws MitchellException;

    public Long saveSuppAppraisalAssignmentEmailDoc(MitchellEnvelopeDocument mEnvDoc, UserInfoDocument userInfoDoc,String companyCode,
                   long claimId,long claimExposureId)throws MitchellException;

    public void updateAppraisalAssignmentDocument(long docId, MitchellEnvelopeDocument mitchellEnvelopeDocument,UserInfoDocument logdInUsrInfo, Long tcn) throws MitchellException;

    public Long saveAppraisalAssignmentDocument(MitchellEnvelopeDocument mitchellEnvelopeDocument,UserInfoDocument logdInUsrInfo,String companyCode,long claimId,long claimExposureId,Long relatedEstimateDocId)  throws MitchellException;

    public void updateAppraisalAssignmentScheduleMethod(long docId, UserInfoDocument userInfo, com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment appAsg, List<AppraisalAssignmentUpdateFieldEnum> updateFields)
                throws MitchellException;
    
    public Estimate getEstimateAndDocByDocId(long relatedEstimateDocumentId,boolean includeEstSubObject)throws MitchellException;

    public long getDocStoreIdByDocumentId(long documentId, String attachmentType) throws MitchellException;
    
}
