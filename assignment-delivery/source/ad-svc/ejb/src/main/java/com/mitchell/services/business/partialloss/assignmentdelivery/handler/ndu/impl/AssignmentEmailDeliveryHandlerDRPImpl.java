package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.impl;

import java.util.logging.Logger;

import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.AssignmentEmailDeliveryConstants;

public class AssignmentEmailDeliveryHandlerDRPImpl extends
        AbstractAssignmentEmailHandler {

    static final String CLZ_NAME =
            AssignmentEmailDeliveryHandlerDRPImpl.class.getName();
    private static Logger mLogger = Logger.getLogger(CLZ_NAME);
    
    
    public String createEmailMessage4Creation(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
        final String emailLink = systemConfigProxy.getStringValue(
                AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK_DRP);
        
        culture = getBillingualCode(culture, apdContextDoc.getAPDDeliveryContext()
                .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getInsCoCode());
        
		String messageXml =
                this.assignmentEmailUtils
                .createMessageXml4EmailDRP(apdContextDoc, emailLink,culture,true);
         
                   
        final String emailMessage = transformCustomXslt(messageXml,culture);
        
		  mLogger.info("Exiting AssignmentEmailDeliveryHandlerDRPImpl#createEmailMessage4Creation+emailMessage"+emailMessage);

        return emailMessage;
    }

    protected String createFaxMessage4Creation(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
    	
    	String methodName="createFaxMessage4Creation";
    	mLogger.entering(CLZ_NAME, methodName);
        final String emailLink = systemConfigProxy.getStringValue(
                AssignmentEmailDeliveryConstants.SYS_CONFIG_EMAIL_URLLINK_DRP);
        final String messageXml =
                this.assignmentEmailUtils
                .createMessageXml4FaxDRP(apdContextDoc, emailLink,culture);
        
        final String emailMessage = transform(messageXml,culture);
        mLogger.exiting(CLZ_NAME, methodName);
        
        return emailMessage;
    }

    protected String getClassName() {
        return CLZ_NAME;
    }

    public String createEmailMessage4UploadSuccess(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
    	
    	String methodName="createEmailMessage4UploadSuccess";
    	mLogger.entering(CLZ_NAME, methodName);
        final String xml = this.assignmentEmailUtils
                .createEmailMessageXml4UploadSuccessDRP(apdContextDoc);
        
        culture = getBillingualCode(culture, apdContextDoc.getAPDDeliveryContext()
                .getAPDAlertInfo().getAPDCommonInfo().getInsCoCode());
        
		final String emailMessage = transformCustomXslt(xml,culture);
        mLogger.exiting(CLZ_NAME, methodName);
        
        return emailMessage;
    }

    public String createEmailMessage4UploadFail(
            APDDeliveryContextDocument apdContextDoc,String culture) throws Exception {
        final String xml = this.assignmentEmailUtils
                .createEmailMessageXml4UploadFailDRP(apdContextDoc);
        final String emailMessage = transform(xml,culture);
        return emailMessage;
    }

    public AssignmentServiceContext populateAdditionalInfoInAssignmentServiceContext(
            AssignmentServiceContext svcContext) {
        
        if (svcContext != null) {
            svcContext.setShopPremium(true);
        }
        return svcContext;
    }
}
