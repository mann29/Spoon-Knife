package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.List;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignmentUpdateFieldEnum;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;

public class EstimatePackageProxyImpl implements EstimatePackageProxy {

   
    // @Override
    public Estimate[] findEstimateByClaimNumber(final String companyCode, final String userID,
            final String claimSuffixNumber) throws MitchellException {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
        return estimatePackageClient.findEstimateByClaimNumber(companyCode, userID, claimSuffixNumber,true);
    }

    // @Override
    public Document getDocumentByEstimateId(final long estimateId) throws MitchellException {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
        return estimatePackageClient.getDocumentByEstimateId(estimateId);

    }
    public Estimate getEstimateAndDocByDocId(long relatedEstimateDocumentId,boolean includeEstSubObject)throws MitchellException{
		
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
        return estimatePackageClient.getEstimateAndDocByDocId(relatedEstimateDocumentId,includeEstSubObject);
     	
    }

    public long getDocStoreIdByDocumentId(long documentId, String attachmentType) throws MitchellException {

        final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();

        return estimatePackageClient.getDocumentStoreIdByDocId(documentId, attachmentType);
    }

    public com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO getAppraisalAssignmentDocument(long documentId) throws MitchellException
    {
     final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();	
     return  estimatePackageClient.getAppraisalAssignmentDocument(documentId);
}
    
    public Long saveSuppAppraisalAssignmentEmailDoc(MitchellEnvelopeDocument mEnvDoc, UserInfoDocument userInfoDoc,String companyCode,
                   long claimId,long claimExposureId)throws MitchellException
    {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
        return estimatePackageClient.saveSuppAppraisalAssignmentEmailDoc(mEnvDoc, userInfoDoc, companyCode,
                    claimId, claimExposureId);
    }               
    

    public Long saveAppraisalAssignmentDocument(MitchellEnvelopeDocument mitchellEnvelopeDocument, UserInfoDocument logdInUsrInfo, String companyCode, long claimId, long claimExposureId, Long relatedEstimateDocId)
    throws MitchellException {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
    	return estimatePackageClient.saveAppraisalAssignmentDocument(mitchellEnvelopeDocument, logdInUsrInfo, companyCode, claimId, claimExposureId, relatedEstimateDocId);
    }

    public void updateAppraisalAssignmentDocument(long docId, MitchellEnvelopeDocument mitchellEnvelopeDocument, UserInfoDocument logdInUsrInfo, Long tcn) 
    throws MitchellException {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();
    	estimatePackageClient.updateAppraisalAssignmentDocument(docId, mitchellEnvelopeDocument, logdInUsrInfo, tcn);
    }  
    
    public void updateAppraisalAssignmentScheduleMethod(long appAsgDocumentId, UserInfoDocument userInfo,
                      com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment appAsg,
                                                    List<AppraisalAssignmentUpdateFieldEnum> updateFields)
    throws MitchellException 
    {
    	final EstimatePackageClient estimatePackageClient = new EstimatePackageClient();    	
    	estimatePackageClient.updateAppraisalAssignment(appAsgDocumentId, userInfo, appAsg, updateFields);

    }
               
}

