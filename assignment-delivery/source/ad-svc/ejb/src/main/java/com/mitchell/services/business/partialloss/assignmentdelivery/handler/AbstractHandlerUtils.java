package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.io.File;
import java.rmi.RemoteException;

import org.apache.xmlbeans.XmlException;
import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public abstract class AbstractHandlerUtils {

	public AbstractHandlerUtils() {
		super();
	}

	public abstract String[] getClaimExposureIdsResult(AssignmentServiceContext context);

	public abstract void sendNonStaffSuppNotification(AssignmentServiceContext context,
			String workItemId, boolean returnException) throws Exception;

	public abstract File getSupplementRequestDocFile(String suppNotificationDocText, String workItemId)
			throws Exception;

	public abstract File writeSupplementRequestDocFile(String suppNotificationDoc,
			String filePrefix, String fileExtension, String workItemId) throws Exception;

	public abstract String createSupplementRequestDoc(AssignmentServiceContext context, String workItemId)
			throws Exception;

	public abstract MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(AssignmentDeliveryServiceDTO dto ,
			String workItemId) throws Exception;

	public abstract void sendCancellationECAlert(String ciecaClaimNumber, UserInfoDocument userInfoDoc,
			boolean isOriginal, String workItemId) throws Exception;

	public abstract File getsuppToOrigTextFile(UserInfoDocument userInfoDoc, String workItemId)
			throws Exception;

	public abstract File getAttachFileFromDocStore(Long attachementDocStoreId, String workItemId)
			throws Exception;

	public abstract File [] addFileAttachementsToADContext(AssignmentServiceContext context,
			AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc, String workItemId, boolean returnException) throws Exception;

	public abstract Estimate getEstimateFromCCDB(long estimateDocId)
			throws MitchellException;

	public abstract File getOrigEstimateMieFileFromDocStore(Long orgEstimateDocumentID,
			String workItemId) throws Exception;

	public abstract AdditionalAppraisalAssignmentInfoDocument getAAAInfoDocFromMitchellEnv(MitchellEnvelopeDocument mEnvDoc,
			String workItemId, boolean returnException) throws Exception;

	public abstract boolean isOriginalOrSupplementAssignment(CIECADocument ciecaDoc);

	public abstract boolean isOriginalAssignment(CIECADocument ciecaDoc);

	public abstract void logWarning(UserInfoDocument uiDoc, int errorCode, String message,
			String className, String methodName, String workItemId);

	public abstract CIECADocument getCiecaDocFromMitchellEnv(MitchellEnvelopeDocument mEnvDoc, String workItemId)
			throws Exception;

	public abstract long archieveAssignment(UserInfoDocument userInfo, File mcfFile,
			String workItemId);

	public abstract CIECADocument getCiecaDocumentFromMitchellEnvelope(MitchellEnvelopeDocument document,
			String workItemId) throws MitchellException, XmlException;
	public abstract CIECADocument getCiecaDocumentFromMitchellEnvelope(MitchellEnvelopeDocument document, String ciecaId,
			String workItemId) throws MitchellException, XmlException;

    public abstract void writeRequestSupplementHistory(final long originalEstimateDocumentId, final String userId, final String companyCode)
            throws RemoteException, MitchellException;
    /**
     * get Subject on basis of EmailType and NotificatinType
     * @param userInfo
     * @param emailType
     * @param sendEmailNotification
     * @return
     * @throws MitchellException
     */
    
    public  abstract String getSubject(UserInfoDocument userInfo, String emailType, boolean sendEmailNotification) throws MitchellException;
}