package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc7;

import java.io.File;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.ExtractClassName;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractMCFAsgnDelHandler;
import com.mitchell.services.core.mcfsvc.AttachmentObject;

/**
 * Handles assignment delivery for clients using the ARC7 protocol. Clients
 * should consume it via the
 * {@link com.mitchell.services.business.partialloss.assignmentdelivery.handler.AssignmentDeliveryHandler
 * implemented interface}.
 * 
 */
public class ARC7DeliveryHandler extends AbstractMCFAsgnDelHandler {

    private static final String ARC7_CANT_HAVE_ADDITIONAL_ATTACHMENTS = "ARC7 can't have additional attachments";
    private static final String CLASS_NAME = ExtractClassName.from(ARC7DeliveryHandler.class);

    protected File attachSupplimentNotificationDocumentTextIfNeeded(final AssignmentServiceContext context,
            final String workItemId, final AbstractHandlerUtils handlerUtils, final File supplementRequestDocFile)
            throws Exception {
        throw new UnsupportedOperationException(ARC7_CANT_HAVE_ADDITIONAL_ATTACHMENTS);
    }

    // -- New method for Jetta/SIP3.5 - stub return for now.
    // TODO Implement someday :) ? ~prashant.khanwale@mitchell.com, Jul 28, 2010

    public void cancelAssignment(final AssignmentServiceContext context) throws AssignmentDeliveryException {

        final String methodName = "cancelAssignment(AssignmentServiceContext context)";
        entering(methodName);

        mLogger.info("Returning from ARC7DeliveryHandler - cancelAssignment stub. "
                + "This method is being called but not yet implemented.");
    }

    protected boolean doesProtocolAcceptAdditionAttachments() {
        return false;
    }

    protected File extractSupplimentToOriginalNoticeFileIfRequired(final String workItemId,
            final UserInfoDocument userInfo, final AbstractHandlerUtils handlerUtils) throws Exception {
        throw new UnsupportedOperationException(ARC7_CANT_HAVE_ADDITIONAL_ATTACHMENTS);
    }

    protected String getClassName() {
        return CLASS_NAME;
    }

    protected int getDispatchReportType() {
        return AttachmentObject.OBJ_TYPE_DISPATCH_REPORT_MIE;
    }

    protected String getEstimateDescription() {
        return "Assignment";
    }

    protected String getHandlerType(final AssignmentServiceContext context) throws AssignmentDeliveryException {
        final String handlerType = assignmentDeliveryUtils.getUserCustomSetting(context.getUserInfo(),
                AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_DESTINATION);
        return handlerType;
    }

    protected boolean isMcfDestinationRequired() {// Default implementation,
                                                  // subclasses may override.
        return true;
    }

    protected File[] prepareAdditionalAttachments(final AssignmentServiceContext context, final String workItemId,
            final File suppToOrigNoticeFile, final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
            throws Exception {
        throw new UnsupportedOperationException(ARC7_CANT_HAVE_ADDITIONAL_ATTACHMENTS);
    }

    
    /*
     * @modified : pm101823 
     * PBI 392334, Task Id 396599 : modified the method to return false;
     * The supplement history will now be maintained through assignment event handler service. 
     * Hence, Removing the call from assignment delivery service 
     */
    protected boolean requiresSupplementHistory() {
        //return true;
    	return false;
    }

}
