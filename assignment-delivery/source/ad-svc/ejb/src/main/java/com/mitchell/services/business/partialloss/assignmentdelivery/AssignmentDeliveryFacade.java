package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.util.ArrayList;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

public interface AssignmentDeliveryFacade {
	  public void cancelAssignment(com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext assignmentSvcContext)     throws  com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
	  public void deliverAssignment(com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext assignmentSvcContext)     throws  com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
	  public void deliverAssignment(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument context)     throws  com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
	  public void deliverAssignmentEmail(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument context)     throws  com.mitchell.common.exception.MitchellException;
	  public void deliverAssignmentEmail4DRP(
	          APDDeliveryContextDocument context) throws MitchellException;	  
	  public void deliverEstimateUploadFailEmail(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument context)     throws  com.mitchell.common.exception.MitchellException;
	  public void deliverEstimateUploadFailEmailDRP(
	          APDDeliveryContextDocument context) throws MitchellException;
	  public void deliverEstimateUploadSuccessEmail(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument context)     throws  com.mitchell.common.exception.MitchellException;
	  public void deliverEstimateUploadSuccessEmailDRP(
	          APDDeliveryContextDocument context) throws MitchellException;	  
	  public void deliverNonDrpShopSuppAssignmentEmail(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument context, java.util.ArrayList partsListAttachement)     throws  com.mitchell.common.exception.MitchellException;
	  public void deliverDrpShopSuppAssignmentEmail(
	          APDDeliveryContextDocument context, ArrayList partsListAttachement)
	           throws MitchellException;
	  public void deliverAssignmentEmailNotification(
	    	    final APDDeliveryContextDocument context, final ArrayList partsListAttachment, final String emailtype) throws MitchellException;
      public void deliverRCWebAssignment(APDDeliveryContextDocument context) throws MitchellException;
}
