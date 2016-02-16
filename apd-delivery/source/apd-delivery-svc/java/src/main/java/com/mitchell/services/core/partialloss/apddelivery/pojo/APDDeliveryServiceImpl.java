package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.delegator.APDBroadcastMessageHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtilImpl;
import com.mitchell.utils.misc.AppUtilities;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class serves as a POJO to APDDeliveryServiceEJB.
 * All Atrifact Delivery calls are channeled through this class.
 * 
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 */
public class APDDeliveryServiceImpl implements APDDeliveryService { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = APDDeliveryServiceImpl.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    
    // Fix 117663 : Properties
    private APDDeliveryUtilImpl apdDeliveryUtil;
    private AppraisalAssignmentDeliveryHandler appraisalAssignmentDeliveryHandler;
    private AppraisalAssignmentNotificationHandler appraisalAssignmentNotificationHandler;
    private EstimateStatusDeliveryHandler estimateStatusDeliveryHandler;
    private NICBReportHandler nicbReportHandler;
    private RepairAssignmentDeliveryHandler repairAssignmentDeliveryHandler;
    private StaffSupplementDeliveryHandler staffSupplementDeliveryHandler;
    private APDBroadcastMessageHandler apdBroadcastMessageHandler;
    private AlertDeliveryHandler alertDeliveryHandler;
    private APDCommonUtilProxyImpl apdCommonUtilProxy;
    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.APDDeliveryService#deliverArtifact(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverArtifact(APDDeliveryContextDocument apdContext) 
                                            throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
        apdCommonUtilProxy.logFINEMessage("APDDeliveryService: Artifact delivery");
        
        try {
            // check if APDDeliveryContext is valid or not
            boolean isValid = apdDeliveryUtil.isValid(apdContext);
            
            if (!isValid) {
                // throw ME if APDDeliveryContext is not conforming to schema
                throw new MitchellException(
                        APDDeliveryConstants.ERROR_INVALID_APD_DELIVERY_CONTEXT,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_INVALID_APD_DELIVERY_CONTEXT_MSG
                            + "\n"
                            + "Received APDDeliveryContext is:\n"
                            + apdContext);
            }
            
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: APDDeliveryContext received is:\n" 
                            + apdContext.toString());
            // get Message/Artifact type
            String artifactType = 
                    apdContext.getAPDDeliveryContext().getMessageType();
            // validate if Message/Artifact type is a valid one
            apdDeliveryUtil.isValidArtifactType(artifactType);
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: Message/Artifact type is: " 
                                                            + artifactType);
            // deliver artifact by specific handler.
            deliverArtifactByHandler(artifactType, apdContext);
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: Artifact delivery process succeeded");
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_DELIVER_ARTIFACT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.APDDeliveryService#deliverAppraisalAssignment(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument, java.util.ArrayList)
	 */
    public void deliverAppraisalAssignment(APDDeliveryContextDocument apdContext, 
                                                    ArrayList partsListAttachments)
                                                             throws MitchellException {
        String methodName = "deliverAppraisalAssignmentToEClaim";
        logger.entering(CLASS_NAME, methodName);
        try {
            // check if APDDeliveryContext is valid or not
            boolean isValid = apdDeliveryUtil.isValid(apdContext);
            
            if (!isValid) {
                // throw ME if APDDeliveryContext is not conforming to schema
                throw new MitchellException(
                        APDDeliveryConstants.ERROR_INVALID_APD_DELIVERY_CONTEXT,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_INVALID_APD_DELIVERY_CONTEXT_MSG
                            + "\n"
                            + "Received APDDeliveryContext is:\n"
                            + apdContext);
            }
            
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: APDDeliveryContext received is:\n" 
                            + apdContext.toString());
            // get Message/Artifact type
            String artifactType = 
                    apdContext.getAPDDeliveryContext().getMessageType();
            // validate if Message/Artifact type is a valid one
            apdDeliveryUtil.isValidArtifactType(artifactType);
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: Message/Artifact type is: " 
                                                            + artifactType);
            // deliver artifact
            deliverAppraisalAssignmentByHandler(artifactType, partsListAttachments, apdContext);
            apdCommonUtilProxy.logFINEMessage("Artifact delivery: Artifact delivery process succeeded");
            
            
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_DELIVER_APPRAISAL_ASMT,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_DELIVER_APPRAISAL_ASMT_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
    }
    
    /**
     * 
     * @param artifactType
     * @param apdContext
     * @throws MitchellException
     */
    private void deliverArtifactByHandler(String artifactType, APDDeliveryContextDocument apdContext) throws MitchellException {
        String methodName = "deliverArtifactByHandler"; 
        
        if (APDDeliveryConstants.REPAIR_ASSIGNMENT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.REWORK_ASSIGNMENT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            repairAssignmentDeliveryHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.ALERT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            alertDeliveryHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.ORIGINAL_ESTIMATE_ARTIFACT_TYPE.equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.SUPPLEMENT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            appraisalAssignmentDeliveryHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.ESTIMATE_STATUS_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            estimateStatusDeliveryHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.REQUEST_STAFF_SUPPLEMENT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            staffSupplementDeliveryHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.APPR_ASMT_NTFN_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            appraisalAssignmentNotificationHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.NICB_REPORT_TYPE.equalsIgnoreCase(artifactType)) {
            nicbReportHandler.deliverArtifact(apdContext);
            
        } else if (APDDeliveryConstants.BROADCAST_MESSAGE_TYPE.equalsIgnoreCase(artifactType)) {
            apdBroadcastMessageHandler.deliverArtifact(apdContext);
            
        } else {
            throw new MitchellException(CLASS_NAME, methodName, "Unsupported Artifact Type ??????");
        }
    }
    
   /**
    * 
    * @param artifactType
    * @param attachments
    * @param apdContext
    * @throws MitchellException
    */
    private void deliverAppraisalAssignmentByHandler(String artifactType, ArrayList attachments, APDDeliveryContextDocument apdContext)
                                                                throws MitchellException {
        String methodName = "deliverAppraisalAssignmentByHandler"; 
                
        if (APDDeliveryConstants.ORIGINAL_ESTIMATE_ARTIFACT_TYPE.equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.SUPPLEMENT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType)) {
            
            appraisalAssignmentDeliveryHandler.deliverArtifact(apdContext, attachments);
            
            
        } else {
            throw new MitchellException(CLASS_NAME, methodName, "Unsupported operation exception: " + artifactType);
        }
        
        
    }

/**
 * @return the apdDeliveryUtil
 */
public APDDeliveryUtilImpl getApdDeliveryUtil() {
	return apdDeliveryUtil;
}

/**
 * @param apdDeliveryUtil the apdDeliveryUtil to set
 */
public void setApdDeliveryUtil(APDDeliveryUtilImpl apdDeliveryUtil) {
	this.apdDeliveryUtil = apdDeliveryUtil;
}

/**
 * @return the appraisalAssignmentDeliveryHandler
 */
public AppraisalAssignmentDeliveryHandler getAppraisalAssignmentDeliveryHandler() {
	return appraisalAssignmentDeliveryHandler;
}

/**
 * @param appraisalAssignmentDeliveryHandler the appraisalAssignmentDeliveryHandler to set
 */
public void setAppraisalAssignmentDeliveryHandler(
		AppraisalAssignmentDeliveryHandler appraisalAssignmentDeliveryHandler) {
	this.appraisalAssignmentDeliveryHandler = appraisalAssignmentDeliveryHandler;
}

/**
 * @return the appraisalAssignmentNotificationHandler
 */
public AppraisalAssignmentNotificationHandler getAppraisalAssignmentNotificationHandler() {
	return appraisalAssignmentNotificationHandler;
}

/**
 * @param appraisalAssignmentNotificationHandler the appraisalAssignmentNotificationHandler to set
 */
public void setAppraisalAssignmentNotificationHandler(
		AppraisalAssignmentNotificationHandler appraisalAssignmentNotificationHandler) {
	this.appraisalAssignmentNotificationHandler = appraisalAssignmentNotificationHandler;
}

/**
 * @return the estimateStatusDeliveryHandler
 */
public EstimateStatusDeliveryHandler getEstimateStatusDeliveryHandler() {
	return estimateStatusDeliveryHandler;
}

/**
 * @param estimateStatusDeliveryHandler the estimateStatusDeliveryHandler to set
 */
public void setEstimateStatusDeliveryHandler(
		EstimateStatusDeliveryHandler estimateStatusDeliveryHandler) {
	this.estimateStatusDeliveryHandler = estimateStatusDeliveryHandler;
}

/**
 * @return the nicbReportHandler
 */
public NICBReportHandler getNicbReportHandler() {
	return nicbReportHandler;
}

/**
 * @param nicbReportHandler the nicbReportHandler to set
 */
public void setNicbReportHandler(NICBReportHandler nicbReportHandler) {
	this.nicbReportHandler = nicbReportHandler;
}

/**
 * @return the repairAssignmentDeliveryHandler
 */
public RepairAssignmentDeliveryHandler getRepairAssignmentDeliveryHandler() {
	return repairAssignmentDeliveryHandler;
}

/**
 * @param repairAssignmentDeliveryHandler the repairAssignmentDeliveryHandler to set
 */
public void setRepairAssignmentDeliveryHandler(
		RepairAssignmentDeliveryHandler repairAssignmentDeliveryHandler) {
	this.repairAssignmentDeliveryHandler = repairAssignmentDeliveryHandler;
}

/**
 * @return the staffSupplementDeliveryHandler
 */
public StaffSupplementDeliveryHandler getStaffSupplementDeliveryHandler() {
	return staffSupplementDeliveryHandler;
}

/**
 * @param staffSupplementDeliveryHandler the staffSupplementDeliveryHandler to set
 */
public void setStaffSupplementDeliveryHandler(
		StaffSupplementDeliveryHandler staffSupplementDeliveryHandler) {
	this.staffSupplementDeliveryHandler = staffSupplementDeliveryHandler;
}

/**
 * @return the apdBroadcastMessageHandler
 */
public APDBroadcastMessageHandler getApdBroadcastMessageHandler() {
	return apdBroadcastMessageHandler;
}

/**
 * @param apdBroadcastMessageHandler the apdBroadcastMessageHandler to set
 */
public void setApdBroadcastMessageHandler(
		APDBroadcastMessageHandler apdBroadcastMessageHandler) {
	this.apdBroadcastMessageHandler = apdBroadcastMessageHandler;
}

/**
 * @return the alertDeliveryHandler
 */
public AlertDeliveryHandler getAlertDeliveryHandler() {
	return alertDeliveryHandler;
}

/**
 * @param alertDeliveryHandler the alertDeliveryHandler to set
 */
public void setAlertDeliveryHandler(AlertDeliveryHandler alertDeliveryHandler) {
	this.alertDeliveryHandler = alertDeliveryHandler;
}

/**
 * @return the apdCommonUtilProxy
 */
public APDCommonUtilProxyImpl getApdCommonUtilProxy() {
	return apdCommonUtilProxy;
}

/**
 * @param apdCommonUtilProxy the apdCommonUtilProxy to set
 */
public void setApdCommonUtilProxy(APDCommonUtilProxyImpl apdCommonUtilProxy) {
	this.apdCommonUtilProxy = apdCommonUtilProxy;
}

    
} 
