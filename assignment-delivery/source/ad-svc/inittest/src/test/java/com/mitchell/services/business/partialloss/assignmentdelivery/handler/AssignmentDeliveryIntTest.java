package com.mitchell.services.business.partialloss.assignmentdelivery.handler; 

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.client.AssignmentDeliveryClient;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.bo.EstimateDwnldHist;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;


public class AssignmentDeliveryIntTest 
{
    private static Logger logger = Logger.getLogger(AssignmentDeliveryIntTest.class.getName());
    private static final SystemConfiguration sysConfig = SystemConfiguration.getInstance();
	private static final String TEST_INPUT_FILE_BASEDIR = "/inputfiles/";
    private final String CLASS_NAME = "AssignmentDeliverySmokeTest";

    private String testUserId = null;
    private String testCompanyId = null;
    private String testGuid = null;
    private String testBMSFile = null;
    
    @SuppressWarnings("deprecation")
	public void init() {
        testUserId = sysConfig.getSetting("/AssignmentDeliverySmokeTest/TestUserId");
        testCompanyId = sysConfig.getSetting("/AssignmentDeliverySmokeTest/TestCompanyCode");
        testGuid = sysConfig.getSetting("/AssignmentDeliverySmokeTest/TestGuid");
        testBMSFile = sysConfig.getSetting("/AssignmentDeliverySmokeTest/TestBMSFile");
        
        if(testUserId == null || testUserId.length() == 0) {
            throw new IllegalArgumentException(" The TestUserId setting cannot be null");
        }

        if(testCompanyId == null || testCompanyId.length() == 0) {
            throw new IllegalArgumentException(" The TestCompanyId setting cannot be null");
        }
        
        if(testGuid == null || testGuid.length() == 0) {
            throw new IllegalArgumentException(" The TestGuid setting cannot be null");
        }

        if(testBMSFile == null || testBMSFile.length() == 0) {
            throw new IllegalArgumentException(" The TestBMSFile setting cannot be null");
        }
    }
    
    /**
     * This is the integration test to check assignment delivery service.
     * Presently, Assignment Delivery Service saves the download history while delivering Assignment.
     * With PBI 392334 onwards, changes are made so that it should not create record in estimate download history table.
     * This test verifies this change.
     */
    
    /**
     * Please Note : The test data is from dev database for client_estimate_id ('062520142-01').
     * Please ensure the data used for testing exists in the database in future.
     * Check if this record already exists in history table as it may exist due to  some other workflow.
     * If it exists, delete it from history table and then run the test. It should not create the record in history table.
     */
    
    public void testAssignmentDelivery() {
      
        final String METOD_NAME = "testAssignmentDelivery()";
        logger.entering(CLASS_NAME, METOD_NAME);

        try {
            //Get the userinfo
        	UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
            UserInfoDocument userInfo = remote.getUserInfo(testCompanyId, testUserId, testGuid);
            
            String workItemId = "001";
            
            //Call Assignment Delivery Client
            AssignmentDeliveryClient client = new AssignmentDeliveryClient();
            client.deliverAssignment(
                new AssignmentServiceContext(userInfo, workItemId, false, null, getMEDocFromTestFile()));
            
            //Assignment workflow is completed. Now call Estimate package to verify if record was saved in history table.
            //For this first retrieve the estimate for the given relatedEstimateDocId
            EstimatePackageClient estPkgClient = new EstimatePackageClient();
            
            Estimate est = estPkgClient.getEstimateAndDocByDocId(getRelatedEstimateDocIDFromMeDoc(getMEDocFromTestFile()));  //100004315548L
            logger.info("Calling Estimate Package to validate if the record is created in the history table for client_estimate_id " + est.getClientEstimateId());
            EstimateDwnldHist[] histArry = estPkgClient.findEstimateDwnldHistList(est.getCoCd(), est.getClientEstimateId(), est.getCommitDate());
            if (histArry!= null) {
            	logger.info("Record found in Estimate Download History Table for this client_estimate_id :  "+ histArry[0].toString());
            } else {
            	logger.info("No Record Found in  Estimate Download History Table for this client_estimate_id");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Assignment Delivery Test failed with exception: " + e.getMessage());
            logger.info("exception is: " + e.toString());
            logger.info("cause: " + e.getCause().toString());
            
            StackTraceElement [] elements = e.getStackTrace(); 
            
            for(int i = 0; i < elements.length; i++) {
                logger.info(elements[i].toString());
            }
        }
        finally {
            logger.exiting(CLASS_NAME, METOD_NAME);
        }
    }
    
    private MitchellEnvelopeDocument getMEDocFromTestFile() {
        MitchellEnvelopeDocument mitchellEnvDoc = null;
        try {
        	mitchellEnvDoc = MitchellEnvelopeDocument.Factory.parse(loadFileForTest("meDocument.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mitchellEnvDoc;       
    }    
    
    public static void main(String[] args) {
        logger.info("Starting Assignment Delivery Integration Test...");
        AssignmentDeliveryIntTest test = new AssignmentDeliveryIntTest();
        test.init();
        test.testAssignmentDelivery();
        logger.info("Finished call for Assignment Delivery Integration Test.");

    }
    
    private File loadFileForTest(String fileRelativePath)
    {
      return new File(makeTestFilePath(fileRelativePath));
    }

    private String makeTestFilePath(String fileRelativePath)
    {
      // URL put's %20 in place of space but need the space for the file system.
      URL url = this.getClass().getResource(
          TEST_INPUT_FILE_BASEDIR + fileRelativePath);
      return url.getFile().replace("%20", " ");
    }
    
    private long getRelatedEstimateDocIDFromMeDoc(MitchellEnvelopeDocument mitchellEnvDoc) throws Exception {
    	
    	long relatedEstDocId = 0L;
    	final MitchellEnvelopeHelper mitchellEnvHelper = new MitchellEnvelopeHelper(
    			mitchellEnvDoc);

    	    // Get AdditionalAppraisalAssignmentInfoDocument, if exists
    	    EnvelopeBodyType envelopeBody = null;
    	    envelopeBody = mitchellEnvHelper
    	        .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER);

    	    if (envelopeBody != null) {

    	      final String contentString = mitchellEnvHelper
    	          .getEnvelopeBodyContentAsString(mitchellEnvHelper
    	              .getEnvelopeBody(AssignmentDeliveryConstants.ME_METADATA_AAAINFO_IDENTIFIER));
    	      
    	      AdditionalAppraisalAssignmentInfoDocument  aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
    	            .parse(contentString);
    	      if (aaaInfoDoc != null && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()!= null && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails() != null ) {
    	    	  relatedEstDocId = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails().getRelatedEstimateDocumentID();
    	      }
    	    }
    	    //logger.info("relatedEstDocId ::: " + relatedEstDocId);
			return relatedEstDocId;
    }
    	
}
