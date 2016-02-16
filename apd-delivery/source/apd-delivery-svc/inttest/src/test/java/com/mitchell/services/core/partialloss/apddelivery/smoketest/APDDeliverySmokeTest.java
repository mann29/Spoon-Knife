package com.mitchell.services.core.partialloss.apddelivery.smoketest;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.client.APDDeliveryClient;
import com.mitchell.services.core.partialloss.apddelivery.ejb.APDDeliveryServiceEJBRemote;

/**
 * @author jk100838
 * 
 */
public class APDDeliverySmokeTest {

	/**
	 * default constructor.
	 */
	public APDDeliverySmokeTest() {
	}

	/**
     * 
     */
	APDDeliveryServiceEJBRemote ejb = null;

	@Before
	public void setup() throws Exception {
		ejb = APDDeliveryClient.getAPDDeliveryEJB();
	}

	@After
	public void tearDown() {
		ejb = null;

	}

	@Test
	public void test_AlertDelivery_NonPlatform_Global() throws Exception {
		String testMethod = "test_AlertDelivery_NonPlatform_Global";

		try {

			File file = new File(
					"src/test/resources/test_AlertDelivery_NonPlatform_Global.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory
					.parse(file);
			ejb.deliverArtifact(apdContext);
			logTestSuccess(testMethod);

		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void test_AlertDelivery_NonPlatform_Rejected() throws Exception {
		String testMethod = "test_AlertDelivery_NonPlatform_Rejected";
		try {

			File file = new File(
					"src/test/resources/test_AlertDelivery_NonPlatform_Rejected.xml");
			APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory
					.parse(file);
			// logOnConsole("apdContext :: " + apdContext.toString());
			ejb.deliverArtifact(apdContext);
			logTestSuccess(testMethod);

		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void test_AppraisalAssignmentDelivery_Platform() throws Exception {
		String testMethod = "test_AppraisalAssignmentDelivery_Platform";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;
		try {
			file = new File(
					"src/test/resources/test_AppraisalAssignmentDelivery_Platform.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
			logTestSuccess(testMethod);

		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void test_AppraisalAssignmentDelivery_Dispatch() throws Exception {
		String testMethod = "test_AppraisalAssignmentDelivery_Dispatch";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;
		try {
			file = new File(
					"src/test/resources/test_AppraisalAssignmentDelivery_Dispatch.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
			logTestSuccess(testMethod);
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}

	}

	@Test
	public void test_AppraisalAssignmentDelivery_Cancel() throws Exception {
		String testMethod = "test_AppraisalAssignmentDelivery_Cancel";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;
		try {
			file = new File("src/test/resources/test_AppraisalAssignmentDelivery_Cancel.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
			logTestSuccess(testMethod);
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}

	}

	@Test
	public void test_AppraisalAssignmentDelivery_Null() throws Exception {
		String testMethod = "test_AppraisalAssignmentDelivery_Null";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;

		// This will test negative case - when appraisalAssignmentContextDoc
		// does not have AppraisalAssignmentInfo

		try {
			file = new File("src/test/resources/test_AppraisalAssignmentDelivery_Null.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			ejb.deliverArtifact(appraisalAssignmentContextDoc);

		} catch (MitchellException me) {
			if (me.getDescription().indexOf(
					"APDAppraisalAssignmentInfo is null ????") >= 0) {
				logTestSuccess(testMethod);
			} else {
				logTestFail(testMethod);
				me.printStackTrace();
				throw me;
			}
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
		}
	}

	@Test
	public void test_EstimateStatusDelivery_NonPlatform() throws Exception {
		String testMethod = "test_EstimateStatusDelivery_NonPlatform";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;

		try {
			file = new File("src/test/resources/test_EstimateStatusDelivery_NonPlatform.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			// logOnConsole("Input - apdContext :: " +
			// appraisalAssignmentContextDoc.toString());
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
			logTestSuccess(testMethod);
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test_EstimateStatusDelivery_NoShopInfo() throws Exception {
		String testMethod = "test_EstimateStatusDelivery_NoShopInfo";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;

		try {
			file = new File("src/test/resources/test_EstimateStatusDelivery_NoShopInfo.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
					.parse(file);
			// logOnConsole("Input - apdContext :: " +
			// appraisalAssignmentContextDoc.toString());
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
			logTestSuccess(testMethod);
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void test_EstimateStatusDelivery_Null() throws Exception {
		String testMethod = "test_EstimateStatusDelivery_Null";
		File file = null;
		APDDeliveryContextDocument appraisalAssignmentContextDoc = null;
		try {
			file = new File("src/test/resources/test_EstimateStatusDelivery_Null.xml");
			appraisalAssignmentContextDoc = APDDeliveryContextDocument.Factory
			.parse(file);
			ejb.deliverArtifact(appraisalAssignmentContextDoc);
		} catch (MitchellException me) {
			if (me.getDescription().indexOf(
					"APDEstimateStatusInfo is null") >= 0) {
				logTestSuccess(testMethod);
				//me.printStackTrace();
			} else {
				logTestFail(testMethod);
				//me.printStackTrace();
				throw me;
			}
		} catch (Exception e) {
			logTestFail(testMethod);
			e.printStackTrace();
			throw e;
		}
	}
	@Test
	public void test_RepairAssignmentDelivery_CreateForPlatform() throws Exception {
		String testMethod = "test_RepairAssignmentDelivery_CreateForPlatform";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_RepairAssignmentDelivery_CreateForPlatform.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    } 
	
	@Test
	public void test_RepairAssignmentDelivery_CancelledForPlatform() throws Exception {
		String testMethod = "test_RepairAssignmentDelivery_CancelledForPlatform";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_RepairAssignmentDelivery_CancelledForPlatform.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    }
	
	@Test
	public void test_RepairAssignmentDelivery_UpdatedForPlatform() throws Exception {
		String testMethod = "test_RepairAssignmentDelivery_UpdatedForPlatform";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_RepairAssignmentDelivery_UpdatedForPlatform.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    }
	
	@Test
	public void test_AANotificationDelivery_Dispatch() throws Exception {
		String testMethod = "test_AANotificationDelivery_Dispatch";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_AANotificationDelivery_Dispatch.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    }
	
	@Test
	public void test_AANotificationDelivery_Cancelled() throws Exception {
		String testMethod = "test_AANotificationDelivery_Cancelled";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_AANotificationDelivery_Cancelled.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    }
	
	@Test
	public void test_BroadcastMessageDelivery() throws Exception {
		String testMethod = "testBroadcastMessageDelivery";
        APDDeliveryContextDocument apdContext = null;
        File file = null;
           
        try {
        	file =  new File("src/test/resources/test_BroadcastMessageDelivery.xml");
            apdContext = APDDeliveryContextDocument.Factory.parse(file);
            ejb.deliverArtifact(apdContext);
            logTestSuccess(testMethod);
        } catch (Exception e) {
        	logTestFail(testMethod);
            e.printStackTrace();
            throw e;
        }
    }

	private void logTestSuccess(String testMethod) {
		System.out.println(testMethod + " : Test Successfull! " + "\n");
	}

	private void logTestFail(String testMethod) {
		System.out.println(testMethod + " : Test Failed. " + "\n");
	}

}