package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;

public class AssignmentEmailDeliveryHandlerImpl extends AbstractAssignmentEmailHandler {
	private static Logger mLogger = Logger.getLogger(AssignmentEmailDeliveryHandlerImpl.class.getName());
	
    static final String CLZ_NAME =
            AssignmentEmailDeliveryHandlerImpl.class.getName();
    
    public String createEmailMessage4Creation(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
        
        final String emailLink = systemConfigProxy.getStringValue(
                AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK);
        
        culture = getBillingualCode(culture, apdContextDoc.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getInsCoCode());
        
		String messageXml =
                this.assignmentEmailUtils
                .createMessageXml4EmailCreation(apdContextDoc, emailLink,culture,true);
                                   
		return  transformCustomXslt(messageXml,culture);
        
    }
    
    public String createFaxMessage4Creation(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
        
        final String emailLink = systemConfigProxy.getStringValue(
                AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK);
        final String messageXml =
                this.assignmentEmailUtils
                .createMessageXml4Fax(apdContextDoc, emailLink,culture);
        
        final String emailMessage = transform(messageXml,culture);
        return emailMessage;
    }
    
    public String createEmailMessage4UploadSuccess(
            APDDeliveryContextDocument apdContextDoc,String culture) throws MitchellException {
    	
    	String methodName="createEmailMessage4UploadSuccess";
    	mLogger.entering(CLZ_NAME, methodName);
        final String xml = this.assignmentEmailUtils
                .createEmailMessageXml4UploadSuccess(apdContextDoc);
        
        culture = getBillingualCode(culture, apdContextDoc.getAPDDeliveryContext()
                .getAPDAlertInfo().getAPDCommonInfo().getInsCoCode());
        
		final String emailMessage = transformCustomXslt(xml,culture);
        mLogger.exiting(CLZ_NAME, methodName);
        
        return emailMessage;
    }

    public String createEmailMessage4UploadFail(
            APDDeliveryContextDocument apdContextDoc,String culture) throws MitchellException {
        
        final String xml = this.assignmentEmailUtils
                .createEmailMessageXml4UploadFail(apdContextDoc);
        final String emailMessage = transform(xml,culture);
        return emailMessage;
    }
    
    protected String getClassName() {
        return CLZ_NAME;
    }

    public AssignmentServiceContext populateAdditionalInfoInAssignmentServiceContext(
            AssignmentServiceContext svcContext) {
        
        if (svcContext != null) {
            svcContext.setNonNetWorkShop(true);
        }
        return svcContext;
    }
    
   
}
