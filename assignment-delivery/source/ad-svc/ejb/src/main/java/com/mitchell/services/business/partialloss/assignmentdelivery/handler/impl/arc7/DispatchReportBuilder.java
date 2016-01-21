package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.arc7; 

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractDispatchReportBuilder;
import com.mitchell.services.core.mum.types.mie.MieDocument;
import com.mitchell.services.core.mum.types.mie.MieMum;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class DispatchReportBuilder extends AbstractDispatchReportBuilder 
{ 
	private final String CLASS_NAME = "DispatchReportBuilder";
    
	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
			this.getClass().getName());
    
    
    
    public File createDispatchReport(AssignmentServiceContext context) 
            throws Exception {
		final String methodName = "createDispatchReport(AssignmentServiceContext context)";
		mLogger.entering(CLASS_NAME, methodName);

        // Get XSLT file from custom setting
    	AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();
        final UserInfoDocument userInfo = context.getUserInfo();
		String dispatchReportXsltFilePath = assignDeliveryUtils.getUserCustomSetting(
                userInfo, 
                AssignmentDeliveryConstants.CUSTOM_SETTING_NAME_ECLAIM_DISPATCH_RP);
                
                
       // String dispatchReportXsltFilePath = "C:\\vandana\\StdMCMDispatchReport.xslt";
        mLogger.info("DispatchReportXsltFilePath = " + dispatchReportXsltFilePath);
        
        /**
         * arc7 Changes 
         * Check if Company Name is Present in Mitchell Envelope 
         * if Not add company Name from Context
         * Modified By:- Vandana Gautam
         */
        
        final MitchellEnvelopeDocument meDoc = context.getMitchellEnvDoc();
       
         MitchellEnvelopeHelper mitchellEnvHelper = 
             new MitchellEnvelopeHelper(meDoc);
         
         String coCode=mitchellEnvHelper.getEnvelopeContextNVPairValue("MitchellCompanyCode");
         mLogger.info("****coCode from Mitchell Envelope="+coCode);
         
         if(coCode==null|| coCode.equals("")){
         	mLogger.info("Company Code is absent so adding CoCode in Mitchell Envelope NVPair ");
         	mitchellEnvHelper.addEnvelopeContextNVPair("MitchellCompanyCode", userInfo.getUserInfo().getOrgCode());
         }
        
        // Create input stream from MitchellEnvelope
        String meStr = meDoc.xmlText();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(meStr.getBytes());
        StreamSource streamSrc = new StreamSource(inputStream);          

        // Create output file for dispatch report
        String dispatchReportXMLFileName = getTempDir() + File.separator
                        + userInfo.getUserInfo().getOrgCode()
                        + "." + context.getWorkItemId() + "."
                        + UUIDFactory.getInstance().getUUID() + ".xml";
        File dispatchReportXMLFile = new File(dispatchReportXMLFileName);
        mLogger.info("DispatchReportFilePath = " + dispatchReportXMLFile.getAbsolutePath());

        // Apply XSLT to MitchellEnvelope
        javax.xml.transform.TransformerFactory tFactory = 
                  javax.xml.transform.TransformerFactory.newInstance();        
        javax.xml.transform.Transformer transformer = tFactory.newTransformer
                (new javax.xml.transform.stream.StreamSource(dispatchReportXsltFilePath));  
        transformer.transform
            (streamSrc, new javax.xml.transform.stream.StreamResult(dispatchReportXMLFile));                
                                
        mLogger.info("DispatchReport = \n" + dispatchReportXMLFile.toString());
        
        String dispatchReportMIEFileName = getTempDir() + File.separator
                        + userInfo.getUserInfo().getOrgCode()
                        + "." + context.getWorkItemId() + "."
                        + UUIDFactory.getInstance().getUUID() + ".mie";
        
		File dispatchReportMIEFile = 
        
        convertXMLToMie(dispatchReportXMLFile,dispatchReportMIEFileName);
        FileUtils.deleteFile(dispatchReportXMLFileName);
        
        mLogger.info("******Deleted XML Dispatch Report***");
        // Return reference to result MIE file         
		mLogger.exiting(CLASS_NAME, methodName);
            
        return dispatchReportMIEFile;
    }
    
    private String getTempDir() throws AssignmentDeliveryException {
        String tempdir = AssignmentDeliveryConfig.getTempDir();
        if (tempdir == null || tempdir.length() == 0) {
            mLogger.warning("Temporary directory is not defined in System configuration, so using default one = "
                    + AssignmentDeliveryConstants.DEFAULT_TEMP_DIR);
            tempdir = AssignmentDeliveryConstants.DEFAULT_TEMP_DIR;      
        }
        
        return tempdir;       
    }
    
    private File convertXMLToMie(File dispatchReportXML,String dispatchReportMIEName)throws Exception{
        MieDocument mieDoc = MieDocument.Factory.parse(dispatchReportXML);
    	/* Create MIEMUM & marshal it to native MIE format*/
		MieMum mieMum = new MieMum();
		mieMum.setPadLength(128);
		mieMum.setNewline(false);
		mieMum.setDocument(mieDoc);
        File mieFile=new File(dispatchReportMIEName);
		
		mieMum.marshalNative(dispatchReportMIEName);
        return mieFile;
    }
    //Stub implementation.
	public File createDispatchReport(final UserInfoDocument userInfo, final String workItemId, final MitchellEnvelopeDocument mitchellEnvDoc)
			throws AssignmentDeliveryException, UnsupportedEncodingException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		return null;
	}
} 
