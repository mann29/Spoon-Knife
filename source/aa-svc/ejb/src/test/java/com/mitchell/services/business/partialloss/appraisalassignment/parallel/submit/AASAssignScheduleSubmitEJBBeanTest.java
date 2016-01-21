package com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelHandlerType;
import com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJB;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASAssignScheduleSubmitEJBBeanTest
{
  protected AASAssignScheduleSubmitEJBBean testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASAssignScheduleSubmitEJBBean.class);
    this.testClass.extAccess = mock(AASExternalAccessor.class);
    this.testClass.pEjb = mock(AASParallelEJB.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void buildReturnMapTest()
      throws Exception
  {
    List<String> responseList = new ArrayList<String>();

    MitchellEnvelopeDocument meDoc1 = MitchellEnvelopeDocument.Factory
        .newInstance();
    MitchellEnvelopeType meType1 = meDoc1.addNewMitchellEnvelope();
    responseList.add(meDoc1.toString());

    MitchellEnvelopeDocument meDoc2 = MitchellEnvelopeDocument.Factory
        .newInstance();
    MitchellEnvelopeType meType2 = meDoc2.addNewMitchellEnvelope();
    responseList.add(meDoc2.toString());

    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    when(
        this.testClass.extAccess
            .buildMEHelper((MitchellEnvelopeDocument) anyObject())).thenReturn(
        meHelper);

    AssignTaskType assignTaskType1 = AssignTaskType.Factory.newInstance();
    assignTaskType1.setTaskId(22);
    AssignTaskType assignTaskType2 = AssignTaskType.Factory.newInstance();
    assignTaskType2.setTaskId(23);

    when(this.testClass.extractAssignTaskFromResponseME(meHelper)).thenReturn(
        assignTaskType1).thenReturn(assignTaskType2);

    when(this.testClass.extractResultIndicatorFromResponseME(meHelper))
        .thenReturn(new Integer(2)).thenReturn(new Integer(3));

    // Call method in test
    when(this.testClass.buildReturnMap(responseList)).thenCallRealMethod();
    java.util.HashMap<Long, Integer> retval = this.testClass
        .buildReturnMap(responseList);
    assertNotNull(retval);

    // Verify
    verify(this.testClass.extAccess, times(2)).buildMEHelper(
        (MitchellEnvelopeDocument) anyObject());
    verify(this.testClass, times(2)).extractAssignTaskFromResponseME(meHelper);
    verify(this.testClass, times(2)).extractResultIndicatorFromResponseME(
        meHelper);

    assertTrue(retval.size() == 2);
    assertTrue(retval.get(new Long(22)).intValue() == 2);
    assertTrue(retval.get(new Long(23)).intValue() == 3);

  }

  @Test
  public void extractResultIndicatorFromResponseMETest()
      throws Exception
  {
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    when(
        meHelper
            .getEnvelopeContextNVPairValue(AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT))
        .thenReturn("22");

    // Call method in test
    when(this.testClass.extractResultIndicatorFromResponseME(meHelper))
        .thenCallRealMethod();
    int retval = this.testClass.extractResultIndicatorFromResponseME(meHelper);
    assertTrue(22 == retval);

  }

  @Test
  public void extractAssignTaskFromResponseMETest()
      throws Exception
  {
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    EnvelopeBodyType envelopeBodyAT = mock(EnvelopeBodyType.class);
    when(meHelper.getEnvelopeBody(AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID))
        .thenReturn(envelopeBodyAT);

    AssignTaskType att = AssignTaskType.Factory.newInstance();
    att.setTaskId(44);
    when(meHelper.getEnvelopeBodyContentAsString(envelopeBodyAT)).thenReturn(
        att.toString());

    // Call method in test
    when(this.testClass.extractAssignTaskFromResponseME(meHelper))
        .thenCallRealMethod();
    AssignTaskType retval = this.testClass
        .extractAssignTaskFromResponseME(meHelper);

    assertTrue(44 == retval.getTaskId());

  }

  @Test
  public void reconcileMissingResponsesTestNone()
      throws Exception
  {
    AssignTaskType[] tdoc = new AssignTaskType[2];
    java.util.HashMap<Long, Integer> mapResultSet = new java.util.HashMap<Long, Integer>();

    mapResultSet.put(new Long(11), new Integer(12));
    mapResultSet.put(new Long(13), new Integer(14));

    // Call method in test
    when(this.testClass.reconcileMissingResponses(tdoc, mapResultSet))
        .thenCallRealMethod();
    java.util.HashMap<Long, Integer> retval = this.testClass
        .reconcileMissingResponses(tdoc, mapResultSet);
    assertNotNull(retval);

    // Verify
    assertTrue(retval.size() == 2);
    assertTrue(retval.get(new Long(11)).intValue() == 12);
    assertTrue(retval.get(new Long(13)).intValue() == 14);

  }

  @Test
  public void reconcileMissingResponsesTest()
      throws Exception
  {
    AssignTaskType[] tdoc = new AssignTaskType[2];
    java.util.HashMap<Long, Integer> mapResultSet = new java.util.HashMap<Long, Integer>();

    mapResultSet.put(new Long(11), new Integer(12));

    tdoc[0] = AssignTaskType.Factory.newInstance();
    tdoc[0].setTaskId(11);
    tdoc[1] = AssignTaskType.Factory.newInstance();
    tdoc[1].setTaskId(13);

    // Call method in test
    when(this.testClass.reconcileMissingResponses(tdoc, mapResultSet))
        .thenCallRealMethod();
    java.util.HashMap<Long, Integer> retval = this.testClass
        .reconcileMissingResponses(tdoc, mapResultSet);
    assertNotNull(retval);

    // Verify
    assertTrue(retval.size() == 2);
    assertTrue(retval.get(new Long(11)).intValue() == 12);
    assertTrue(retval.get(new Long(13)).intValue() == AppraisalAssignmentConstants.RESULT_UNKNOWN);

  }

  @Test
  public void addMECommonTest()
      throws Exception
  {
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    String workItemId = "123abc";
    String companyCode = "FF";

    // Call method in test
    doCallRealMethod().when(this.testClass).addMECommon(meHelper, workItemId,
        companyCode);
    this.testClass.addMECommon(meHelper, workItemId, companyCode);

    verify(meHelper).addEnvelopeContextNVPair(
        AASParallelConstants.ME_WORK_ITEM_ID, workItemId);
    verify(meHelper).addEnvelopeContextNVPair(
        AASParallelConstants.ME_COMPANY_CODE, companyCode);

  }

  @Test
  public void buildAssignScheduleItemMETest()
      throws Exception
  {
    AssignTaskType assignTask = AssignTaskType.Factory.newInstance();
    UserInfoDocument assignorUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    assignorUserInfoDocument.addNewUserInfo().setOrgCode("MYORG");
    String workItemId = "abc123";

    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    when(
        this.testClass.extAccess
            .buildMEHelper((MitchellEnvelopeDocument) anyObject())).thenReturn(
        meHelper);

    // Call method in test
    when(
        this.testClass.buildAssignScheduleItemME(assignTask,
            assignorUserInfoDocument, workItemId)).thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.buildAssignScheduleItemME(
        assignTask, assignorUserInfoDocument, workItemId);
    assertNotNull(retval);

    // Verify

    verify(meHelper).addNewEnvelopeBody(
        AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID, assignTask,
        AASParallelConstants.ME_ASSIGN_TASK_TYPE_TYPE);
    verify(meHelper).addNewEnvelopeBody(
        AASParallelConstants.ME_LOGGED_IN_USERINFO_ID,
        assignorUserInfoDocument,
        AASParallelConstants.ME_LOGGED_IN_USERINFO_TYPE);
    verify(meHelper).addEnvelopeContextNVPair(
        AASParallelConstants.ME_PROCESSING_TYPE,
        AASParallelHandlerType.ASSIGN_SCHEDULE.name());

    verify(this.testClass).addMECommon(meHelper, workItemId,
        assignorUserInfoDocument.getUserInfo().getOrgCode());

  }

  @Test
  public void buildAssignScheduleListTest()
      throws Exception
  {
    AssignTaskType[] tdoc = new AssignTaskType[2];
    UserInfoDocument assignorUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    tdoc[0] = AssignTaskType.Factory.newInstance();
    tdoc[1] = AssignTaskType.Factory.newInstance();

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    when(
        this.testClass.buildAssignScheduleItemME((AssignTaskType) anyObject(),
            (UserInfoDocument) anyObject(), (String) anyObject())).thenReturn(
        meDoc);

    // Call method in test
    when(this.testClass.buildAssignScheduleList(tdoc, assignorUserInfoDocument))
        .thenCallRealMethod();
    List<String> retval = this.testClass.buildAssignScheduleList(tdoc,
        assignorUserInfoDocument);
    assertNotNull(retval);
    assertTrue(retval.size() == 2);

    verify(this.testClass, times(2)).buildAssignScheduleItemME(
        (AssignTaskType) anyObject(), (UserInfoDocument) anyObject(),
        (String) anyObject());

  }

  @Test
  public void submitAssignScheduleAppraisalAssignmentTest()
      throws Exception
  {
    AssignTaskType[] tdoc = new AssignTaskType[2];
    UserInfoDocument assignorUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    ArrayList<String> arrayList = new ArrayList<String>();
    arrayList.add("a");
    arrayList.add("b");
    arrayList.add("c");

    when(this.testClass.buildAssignScheduleList(tdoc, assignorUserInfoDocument))
        .thenReturn(arrayList);

    String groupId = "mygroup";
    when(this.testClass.pEjb.submitRequests(arrayList)).thenReturn(groupId);

    ArrayList<String> arrayList2 = new ArrayList<String>();
    arrayList2.add("q");
    arrayList2.add("r");
    arrayList2.add("s");
    arrayList2.add("t");
    when(this.testClass.pEjb.retrieveResponses(groupId, arrayList.size()))
        .thenReturn(arrayList2);

    java.util.HashMap<Long, Integer> mapResultSet = new java.util.HashMap<Long, Integer>();
    mapResultSet.put(new Long(100), new Integer(101));
    mapResultSet.put(new Long(200), new Integer(201));
    mapResultSet.put(new Long(300), new Integer(301));
    mapResultSet.put(new Long(400), new Integer(401));
    mapResultSet.put(new Long(500), new Integer(501));
    when(this.testClass.buildReturnMap(arrayList2)).thenReturn(mapResultSet);

    when(this.testClass.reconcileMissingResponses(tdoc, mapResultSet))
        .thenReturn(mapResultSet);

    // Call method in test
    when(
        this.testClass.submitAssignScheduleAppraisalAssignment(tdoc,
            assignorUserInfoDocument)).thenCallRealMethod();
    HashMap retval = this.testClass.submitAssignScheduleAppraisalAssignment(
        tdoc, assignorUserInfoDocument);
    assertNotNull(retval);

    verify(this.testClass).buildAssignScheduleList(tdoc,
        assignorUserInfoDocument);
    verify(this.testClass.pEjb).submitRequests(arrayList);
    verify(this.testClass.pEjb).retrieveResponses(groupId, arrayList.size());
    verify(this.testClass).buildReturnMap(arrayList2);
    verify(this.testClass).reconcileMissingResponses(tdoc, mapResultSet);

    assertTrue(retval.size() == 5);

  }

}
