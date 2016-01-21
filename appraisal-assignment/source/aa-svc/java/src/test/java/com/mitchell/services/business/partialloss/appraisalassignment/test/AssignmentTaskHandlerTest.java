package com.mitchell.services.business.partialloss.appraisalassignment.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.helper.AASTestHelper;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AssignmentTaskHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;

public class AssignmentTaskHandlerTest extends TestCase
{

  private UserInfoDocument userInfoDoc;

  /**
	 * 
	 */
  public AssignmentTaskHandlerTest()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @param name
   */
  public AssignmentTaskHandlerTest(final String name)
  {
    super(name);
    // TODO Auto-generated constructor stub
  }

  public static void main(final String[] args)
  {
    junit.textui.TestRunner.run(suite());
  }

  public static Test suite()
  {
    final TestSuite suite = new TestSuite("AssignmentTaskHandlerTest");
    // suite.addTest(new
    // AssignmentTaskHandlerTest("testCreateSupplementTask"));
    suite.addTest(new AssignmentTaskHandlerTest("testCancelSupplementTask"));
    // suite.addTest(new
    // AssignmentTaskHandlerTest("testRejectSupplementTask"));

    // suite.addTest(new
    // AssignmentTaskHandlerTest("testEJBCreateSupplementTask"));

    return suite;
  }

  protected void setUp()
      throws Exception
  {

    // need to set for junit from running from cm build
    String testFileRoot = System.getProperty("TestData");

    // for coverage to run from cm build
    /*
     * if (testFileRoot == null) {
     * testFileRoot = new java.io.File(".").getCanonicalPath() +
     * java.io.File.separator + "";
     * }
     */

    final String userInfoDocFilePath = AASTestHelper
        .getResourcePath("userinfo.xml");
    userInfoDoc = UserInfoDocument.Factory.parse(new java.io.File(
        userInfoDocFilePath));
  }

  public void testCreateSupplementTask()
      throws Exception
  {
    BeanLocator.getBean("AssignmentTaskHandler");

    com.mitchell.utils.misc.UUIDFactory.getInstance().getCeicaUUID();

    // handler.createSupplementTask(claimSuffixNumber,
    // userInfoDoc,workItemID);
  }

  public void testCancelSupplementTask()
      throws Exception
  {
    // BeanLocator.getBean("AssignmentTaskHandler");

    // handler.cancelSupplementTask(claimSuffixNumber, userInfoDoc);
  }

  public void testRejectSupplementTask()
      throws Exception
  {

    final AssignmentTaskHandler handler = (AssignmentTaskHandler) BeanLocator
        .getBean("AssignmentTaskHandler");
    final long taskId = 79673;
    handler.rejectSupplementTask(taskId, userInfoDoc);

  }

  public void testEJBCreateSupplementTask()
      throws Exception
  {
    // AssignmentTaskHandler handler =
    // AssignmentTaskHandlerImpl.getHandler();
    // AssignmentTaskHandler handler =
    // (AssignmentTaskHandler)BeanLocator.getLocator().getBean("AssignmentTaskHandler");

    // AppraisalAssignmentClient.getAppraisalAssignmentEJB();
    // com.mitchell.utils.misc.UUIDFactory.getInstance().getCeicaUUID();

    // remote.createSupplementTask(claimSuffixNumber,
    // userInfoDoc,workItemID);
  }

}
