package com.mitchell.services.business.partialloss.assignmentdelivery.client;

import java.util.ArrayList;
import java.util.logging.Level;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;


public class AssignmentDeliveryClient extends BaseAssignmentDeliveryClient implements  AssignmentDeliveryAccessor{
	
	private static AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
            AssignmentDeliveryClient.class.getName());
	private static String CLASS_NAME = new String("AssignmentDeliveryClient");
	
	public void cancelAssignment(AssignmentServiceContext context) throws AssignmentDeliveryException {
		   	final String methodName = "cancelAssignment";
	        mLogger.entering(CLASS_NAME, methodName);
        	context.validate();
        	mLogger.fine("AssignmentServiceContext is valid.");
        	this.getEJB(mLogger,context.getWorkItemId()).cancelAssignment(context);
		
	}

	public void deliverAssignment(AssignmentServiceContext context) throws AssignmentDeliveryException {
			final String methodName =  "deliverAssignment";
			mLogger.entering(CLASS_NAME, methodName);
			context.validate();
			mLogger.fine("AssignmentServiceContext is valid.");
			this.getEJB(mLogger,context.getWorkItemId()).deliverAssignment(context);
	}

	public void deliverAssignment(APDDeliveryContextDocument context) throws AssignmentDeliveryException {
			final String methodName =  "deliverAssignment"; 
			mLogger.entering(CLASS_NAME, methodName);  
			context.validate();
			mLogger.fine("APDDeliveryContextContext is valid.");
			this.getEJB(mLogger,context.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId()).deliverAssignment(context);
		
	}

	public void deliverAssignmentEmail(APDDeliveryContextDocument context) throws MitchellException {
		 this.getEJB(mLogger,context.getAPDDeliveryContext()
	                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId()).deliverAssignmentEmail(context);
		
	}

	public void deliverEstimateUploadFailEmail(APDDeliveryContextDocument context) throws MitchellException {
		
		this.getEJB(mLogger,context.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo().getWorkItemId()).deliverEstimateUploadFailEmail(context);
	}

	public void deliverEstimateUploadSuccessEmail(APDDeliveryContextDocument context) throws MitchellException {
		this.getEJB(mLogger,context.getAPDDeliveryContext().getAPDAlertInfo().getAPDCommonInfo().getWorkItemId()).deliverEstimateUploadSuccessEmail(context);
		
	}

	public void deliverNonDrpShopSuppAssignmentEmail(APDDeliveryContextDocument context, ArrayList partsListAttachement) throws MitchellException {
		this.getEJB(mLogger,context.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId()).deliverNonDrpShopSuppAssignmentEmail(context, partsListAttachement);
		
	}

    public void deliverAssignmentEmail4DRP(APDDeliveryContextDocument context)
            throws MitchellException {
        this.getEJB(mLogger,context.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo()
                .getWorkItemId()).deliverAssignmentEmail4DRP(context);
    }

    public void deliverEstimateUploadFailEmailDRP(
            APDDeliveryContextDocument context) throws MitchellException {
        this.getEJB(mLogger,context.getAPDDeliveryContext()
                .getAPDAlertInfo().getAPDCommonInfo().getWorkItemId())
                .deliverEstimateUploadFailEmailDRP(context);
    }

    public void deliverEstimateUploadSuccessEmailDRP(
            APDDeliveryContextDocument context) throws MitchellException {
        this.getEJB(mLogger,context.getAPDDeliveryContext()
                .getAPDAlertInfo().getAPDCommonInfo().getWorkItemId())
                .deliverEstimateUploadSuccessEmailDRP(context);
    }

    public void deliverDrpShopSuppAssignmentEmail(
            APDDeliveryContextDocument context, ArrayList partsListAttachement)
            throws MitchellException {
        this.getEJB(mLogger,context.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId())
                .deliverDrpShopSuppAssignmentEmail(context, partsListAttachement);
    }
    
    public void deliverAssignmentEmailNotification(
    	    final APDDeliveryContextDocument context,
    	    final ArrayList partsListAttachment,
    	    final String emailtype
    	    ) throws MitchellException {
    			this.getEJB(mLogger, context.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId())
                .deliverAssignmentEmailNotification(context, partsListAttachment, emailtype);
    }

    public void deliverRCWebAssignment(APDDeliveryContextDocument context) throws MitchellException {

        final String methodName = "deliverRCWebAssignment";
        this.mLogger.entering(CLASS_NAME, methodName);

        context.validate();

        if (mLogger.isLoggable(Level.FINE)) {
            this.mLogger.fine("APDDeliveryContext is valid.");
        }

        boolean isRepairAssignment = context.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo() == null;

        String workItemId = isRepairAssignment ?
                context.getAPDDeliveryContext().getAPDRepairAssignmentInfo().getAPDCommonInfo().getWorkItemId() :
                context.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId();

        this.mLogger.exiting(CLASS_NAME, methodName);
        this.getEJB(mLogger, workItemId).deliverRCWebAssignment(context);
    }

}
