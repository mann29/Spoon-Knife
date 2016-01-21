package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.XmlObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.task.TaskType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AppraisalAssignmentServiceHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASAssignScheduleHandlerBeanTest
{
  protected AASAssignScheduleHandlerBean testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASAssignScheduleHandlerBean.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void assignScheduleAppraisalAssignmentTest()
      throws Exception
  {
    XmlObject assignTaskXmlObject = mock(XmlObject.class);
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    AppraisalAssignmentServiceHandler svcHandler = mock(AppraisalAssignmentServiceHandler.class);
    when(this.testClass.getServiceHandler()).thenReturn(svcHandler);

    when(
        svcHandler.assignScheduleAppraisalAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenReturn(new Integer(42));

    // Call method in test
    when(
        this.testClass.assignScheduleAppraisalAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenCallRealMethod();
    int retval = this.testClass.assignScheduleAppraisalAssignment(
        assignTaskXmlObject, loggedInUserInfoDocument);
    assertTrue(retval == 42);

    // Verify
    verify(this.testClass).getServiceHandler();
    verify(svcHandler).assignScheduleAppraisalAssignment(assignTaskXmlObject,
        loggedInUserInfoDocument);

  }

  @Test
  public void processRequestErrorTest()
      throws Exception
  {
    AASParallelContext ctx = mock(AASParallelContext.class);
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    meDoc.addNewMitchellEnvelope().setVersion("UnitTest");

    when(meHelper.getDoc()).thenReturn(meDoc);

    // Call method in test.
    when(this.testClass.processRequestError(ctx, meHelper))
        .thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.processRequestError(ctx,
        meHelper);
    assertNotNull(retval);

    verify(meHelper).addEnvelopeContextNVPair(
        AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT,
        String.valueOf(AppraisalAssignmentConstants.FAILURE));
    verify(meHelper).getDoc();

    assertTrue(meDoc.getMitchellEnvelope().getVersion()
        .equals(retval.getMitchellEnvelope().getVersion()));

  }

  @Test
  public void processRequestTest()
      throws Exception
  {
    AASParallelContext ctx = mock(AASParallelContext.class);
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    loggedInUserInfoDocument.addNewUserInfo().setUserID("me");

    when(this.testClass.extractLoggedInUserInfo(meHelper)).thenReturn(
        loggedInUserInfoDocument);

    EnvelopeBodyType envelopeBodyAT = mock(EnvelopeBodyType.class);
    when(meHelper.getEnvelopeBody(AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID))
        .thenReturn(envelopeBodyAT);

    AssignTaskType assignTasType = AssignTaskType.Factory.newInstance();
    assignTasType.setTaskId(42);
    String assignTaskStr = assignTasType.toString();
    when(meHelper.getEnvelopeBodyContentAsString(envelopeBodyAT)).thenReturn(
        assignTaskStr);

    when(
        this.testClass.doAssignScheduleOrLunch((XmlObject) anyObject(),
            (UserInfoDocument) anyObject())).thenReturn(new Integer(55));

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    meDoc.addNewMitchellEnvelope().setVersion("UnitTest");
    when(meHelper.getDoc()).thenReturn(meDoc);

    // Call method in test
    when(this.testClass.processRequest(ctx, meHelper)).thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.processRequest(ctx,
        meHelper);
    assertNotNull(retval);

    // Verify

    verify(this.testClass).extractLoggedInUserInfo(meHelper);
    verify(meHelper).getEnvelopeBody(
        AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID);
    verify(meHelper).getEnvelopeBodyContentAsString(envelopeBodyAT);
    verify(this.testClass).doAssignScheduleOrLunch((XmlObject) anyObject(),
        (UserInfoDocument) anyObject());
    verify(meHelper).addEnvelopeContextNVPair(
        AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT, String.valueOf(55));

    verify(meHelper).getDoc();

    assertTrue(meDoc.getMitchellEnvelope().getVersion()
        .equals(retval.getMitchellEnvelope().getVersion()));

  }

  @Test
  public void doAssignScheduleOrLunchTestNotLunch()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    when(
        this.testClass.assignScheduleAppraisalAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenReturn(44);

    // Call method in test
    when(
        this.testClass.doAssignScheduleOrLunch(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenCallRealMethod();
    int retval = this.testClass.doAssignScheduleOrLunch(assignTaskXmlObject,
        loggedInUserInfoDocument);
    assertTrue(retval == 44);

  }

  @Test
  public void doAssignScheduleOrLunchTestLunch()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    assignTaskXmlObject.setAssignmentType("lUnCh");
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    when(
        this.testClass.assignScheduleLunchAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenReturn(33);

    // Call method in test
    when(
        this.testClass.doAssignScheduleOrLunch(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenCallRealMethod();
    int retval = this.testClass.doAssignScheduleOrLunch(assignTaskXmlObject,
        loggedInUserInfoDocument);
    assertTrue(retval == 33);

  }

  @Test
  public void assignScheduleLunchAssignmentTest()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    assignTaskXmlObject.setAssignmentType("lUnCh");
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    loggedInUserInfoDocument.addNewUserInfo().setOrgCode("abc");

    TaskDocument taskDocument = TaskDocument.Factory.newInstance();
    taskDocument.addNewTask();
    when(
        this.testClass.buildTaskDocumentForLunch(assignTaskXmlObject,
            loggedInUserInfoDocument.getUserInfo().getOrgCode())).thenReturn(
        taskDocument);

    AppraisalAssignmentServiceHandler serviceHandler = mock(AppraisalAssignmentServiceHandler.class);
    when(this.testClass.getServiceHandler()).thenReturn(serviceHandler);

    when(
        serviceHandler.saveLunchAssignmentType(taskDocument,
            loggedInUserInfoDocument)).thenReturn(new Long(86));

    // Call method in test
    when(
        this.testClass.assignScheduleLunchAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenCallRealMethod();
    int retval = this.testClass.assignScheduleLunchAssignment(
        assignTaskXmlObject, loggedInUserInfoDocument);
    assertTrue(retval == AppraisalAssignmentConstants.SUCCESS);

  }

  @Test
  public void assignScheduleLunchAssignmentTestFail()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    assignTaskXmlObject.setAssignmentType("lUnCh");
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    loggedInUserInfoDocument.addNewUserInfo().setOrgCode("abc");

    TaskDocument taskDocument = TaskDocument.Factory.newInstance();
    taskDocument.addNewTask();
    when(
        this.testClass.buildTaskDocumentForLunch(assignTaskXmlObject,
            loggedInUserInfoDocument.getUserInfo().getOrgCode())).thenReturn(
        taskDocument);

    AppraisalAssignmentServiceHandler serviceHandler = mock(AppraisalAssignmentServiceHandler.class);
    when(this.testClass.getServiceHandler()).thenReturn(serviceHandler);

    when(
        serviceHandler.saveLunchAssignmentType(taskDocument,
            loggedInUserInfoDocument)).thenReturn(new Long(0));

    // Call method in test
    when(
        this.testClass.assignScheduleLunchAssignment(assignTaskXmlObject,
            loggedInUserInfoDocument)).thenCallRealMethod();
    int retval = this.testClass.assignScheduleLunchAssignment(
        assignTaskXmlObject, loggedInUserInfoDocument);
    assertTrue(retval == AppraisalAssignmentConstants.FAILURE);

  }

  @Test
  public void buildTaskDocumentForLunchTest()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    assignTaskXmlObject.setAssignmentType("lUnCh");
    assignTaskXmlObject.setAssigneeId("AssigneeId");
    assignTaskXmlObject.setWorkGroupCode("WorkGroupCode");
    assignTaskXmlObject.setDisposition("Disposition");
    assignTaskXmlObject.setScheduleDateTime(this.itHelper.buildTestCal());

    GDuration gdd = new GDuration();
    assignTaskXmlObject.setDuration(gdd);

    String companyCode = "aavv6";

    // Call method in test
    when(
        this.testClass.buildTaskDocumentForLunch(assignTaskXmlObject,
            companyCode)).thenCallRealMethod();
    TaskDocument retval = this.testClass.buildTaskDocumentForLunch(
        assignTaskXmlObject, companyCode);
    assertNotNull(retval);

    TaskType taskType = retval.getTask();
    assertNotNull(taskType);

    assertTrue(assignTaskXmlObject.getAssigneeId().equals(
        taskType.getAssigneeId()));
    assertTrue(AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE
        .equals(taskType.getAssignmentType()));
    assertTrue(companyCode.equals(taskType.getCompanyCode()));
    assertTrue(assignTaskXmlObject.getWorkGroupCode().equals(
        taskType.getDispatchCenterName()));
    assertTrue(assignTaskXmlObject.getDisposition().equals(
        taskType.getDisposition()));
    assertTrue(assignTaskXmlObject.getDuration().equals(taskType.getDuration()));
    assertTrue(this.itHelper.compareCalendars(
        assignTaskXmlObject.getScheduleDateTime(),
        taskType.getScheduleDateTime()));
    assertTrue("Lunch".equals(taskType.getClaimNumber()));

    assertFalse(taskType.isSetTaskId());

  }

  @Test
  public void buildTaskDocumentForLunchTestTaskId()
      throws Exception
  {
    AssignTaskType assignTaskXmlObject = AssignTaskType.Factory.newInstance();
    assignTaskXmlObject.setAssignmentType("lUnCh");
    assignTaskXmlObject.setTaskId(new Long(44));

    String companyCode = "aavv6";

    // Call method in test
    when(
        this.testClass.buildTaskDocumentForLunch(assignTaskXmlObject,
            companyCode)).thenCallRealMethod();
    TaskDocument retval = this.testClass.buildTaskDocumentForLunch(
        assignTaskXmlObject, companyCode);
    assertNotNull(retval);

    TaskType taskType = retval.getTask();
    assertNotNull(taskType);

    assertTrue(taskType.getTaskId() == 44);

  }

}
