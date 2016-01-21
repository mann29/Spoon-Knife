package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.ClaimInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASAppLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASErrorLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandler;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService.SEVERITY;
import com.mitchell.services.core.workassignment.bo.DispositionCode;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.dao.vo.Claim;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignmentUpdateFieldEnum;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class AppraisalAssignmentServiceHandlerImplTest
{
  protected AppraisalAssignmentServiceHandlerImpl testClass;
  protected IntegrationTestHelperClient itHelper;
  protected MitchellEnvelopeHandler mitchellEnvelopeHandler;
  protected IAppraisalAssignmentUtils appraisalAssignmentUtils;
  protected ClaimProxy claimProxy;
  protected IAppraisalAssignmentMandFieldUtils appraisalAssignmentMandFieldUtils;
  protected IAppraisalAssignmentConfig appraisalAssignmentConfig;
  protected AASCommonUtils commonUtils;
  protected UserInfoUtils userInfoUtils;
  protected WorkAssignmentHandler workAssignmentHandler;
  protected AASAppLogUtil appLogUtil;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AppraisalAssignmentServiceHandlerImpl.class);
    this.testClass.estimatePackageProxy = mock(EstimatePackageProxy.class);
    this.testClass.workAssignmentHandler = mock(WorkAssignmentHandler.class);
    
    mitchellEnvelopeHandler = mock(MitchellEnvelopeHandler.class);
    appraisalAssignmentUtils = mock(IAppraisalAssignmentUtils.class);
    claimProxy = mock(ClaimProxy.class);
    appraisalAssignmentMandFieldUtils = mock(IAppraisalAssignmentMandFieldUtils.class);
    appraisalAssignmentConfig = mock(IAppraisalAssignmentConfig.class);
    commonUtils = mock(AASCommonUtils.class);
    userInfoUtils = mock(UserInfoUtils.class);
    workAssignmentHandler = mock(WorkAssignmentHandler.class);
    appLogUtil = mock(AASAppLogUtil.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void updateScheduleMethodInDatabaseTestException()
      throws Exception
  {
    long appraisalAsgDocumentId = 1;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    String scheduleMethod = "";

    // Call method in test
    doCallRealMethod().when(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument, scheduleMethod);
    MitchellException me = null;
    try {
      this.testClass.updateScheduleMethodInDatabase(appraisalAsgDocumentId,
          loggedInUserInfoDocument, scheduleMethod);
    } catch (MitchellException e) {
      me = e;
    }
    assertNotNull(me);
  }

  @Test
  public void updateScheduleMethodInDatabaseTestManual()
      throws Exception
  {
    long appraisalAsgDocumentId = 55;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    loggedInUserInfoDocument.addNewUserInfo().setUserID("me");

    String scheduleMethod = "MANUAL";

    // Call method in test
    doCallRealMethod().when(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument, scheduleMethod);
    this.testClass.updateScheduleMethodInDatabase(appraisalAsgDocumentId,
        loggedInUserInfoDocument, scheduleMethod);

    ArgumentCaptor<Long> appAsgIdArg = ArgumentCaptor.forClass(Long.class);

    ArgumentCaptor<UserInfoDocument> uiarg = ArgumentCaptor
        .forClass(UserInfoDocument.class);

    ArgumentCaptor<com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment> appAsgArg = ArgumentCaptor
        .forClass(com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment.class);

    ArgumentCaptor<ArrayList> listArg = ArgumentCaptor
        .forClass(ArrayList.class);

    verify(this.testClass.estimatePackageProxy)
        .updateAppraisalAssignmentScheduleMethod(appAsgIdArg.capture(),
            uiarg.capture(), appAsgArg.capture(), listArg.capture());

    assertTrue(appraisalAsgDocumentId == appAsgIdArg.getValue().longValue());
    assertTrue("me".equals(uiarg.getValue().getUserInfo().getUserID()));
    assertTrue(scheduleMethod.equals(appAsgArg.getValue().getScheduleMethod()));
    assertTrue(listArg.getValue().size() == 1);
    assertTrue(((AppraisalAssignmentUpdateFieldEnum) listArg.getValue().get(0)) == AppraisalAssignmentUpdateFieldEnum.SCHEDULE_METHOD);

  }

  @Test
  public void updateScheduleMethodInDatabaseTestPreCommitted()
      throws Exception
  {
    long appraisalAsgDocumentId = 55;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();
    loggedInUserInfoDocument.addNewUserInfo().setUserID("me");

    String scheduleMethod = "PRE_COMMITTED";

    // Call method in test
    doCallRealMethod().when(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument, scheduleMethod);
    this.testClass.updateScheduleMethodInDatabase(appraisalAsgDocumentId,
        loggedInUserInfoDocument, scheduleMethod);

    ArgumentCaptor<Long> appAsgIdArg = ArgumentCaptor.forClass(Long.class);

    ArgumentCaptor<UserInfoDocument> uiarg = ArgumentCaptor
        .forClass(UserInfoDocument.class);

    ArgumentCaptor<com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment> appAsgArg = ArgumentCaptor
        .forClass(com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment.class);

    ArgumentCaptor<ArrayList> listArg = ArgumentCaptor
        .forClass(ArrayList.class);

    verify(this.testClass.estimatePackageProxy)
        .updateAppraisalAssignmentScheduleMethod(appAsgIdArg.capture(),
            uiarg.capture(), appAsgArg.capture(), listArg.capture());

    assertTrue(appraisalAsgDocumentId == appAsgIdArg.getValue().longValue());
    assertTrue("me".equals(uiarg.getValue().getUserInfo().getUserID()));
    assertTrue(scheduleMethod.equals(appAsgArg.getValue().getScheduleMethod()));
    assertTrue(listArg.getValue().size() == 1);
    assertTrue(((AppraisalAssignmentUpdateFieldEnum) listArg.getValue().get(0)) == AppraisalAssignmentUpdateFieldEnum.SCHEDULE_METHOD);

  }

  @Test
  public void doScheduleMethodUpdateForAssignTaskTestManual()
      throws Exception
  {
    AssignTaskType assignTaskType = AssignTaskType.Factory.newInstance();
    long appraisalAsgDocumentId = 66;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    // Call method in test
    doCallRealMethod().when(this.testClass)
        .doScheduleMethodUpdateForAssignTask(assignTaskType,
            appraisalAsgDocumentId, loggedInUserInfoDocument);
    this.testClass.doScheduleMethodUpdateForAssignTask(assignTaskType,
        appraisalAsgDocumentId, loggedInUserInfoDocument);

    verify(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument,
        AppraisalAssignmentServiceHandlerImpl.SCHEDULE_METHOD_MANUAL);

  }

  @Test
  public void doScheduleMethodUpdateForAssignTaskTestNull()
      throws Exception
  {
    AssignTaskType assignTaskType = AssignTaskType.Factory.newInstance();
    assignTaskType.setScheduleMethod(null);
    long appraisalAsgDocumentId = 66;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    // Call method in test
    doCallRealMethod().when(this.testClass)
        .doScheduleMethodUpdateForAssignTask(assignTaskType,
            appraisalAsgDocumentId, loggedInUserInfoDocument);
    this.testClass.doScheduleMethodUpdateForAssignTask(assignTaskType,
        appraisalAsgDocumentId, loggedInUserInfoDocument);

    verify(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument,
        AppraisalAssignmentServiceHandlerImpl.SCHEDULE_METHOD_MANUAL);

  }

  @Test
  public void doScheduleMethodUpdateForAssignTaskTestEmpty()
      throws Exception
  {
    AssignTaskType assignTaskType = AssignTaskType.Factory.newInstance();
    assignTaskType.setScheduleMethod("");
    long appraisalAsgDocumentId = 66;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    // Call method in test
    doCallRealMethod().when(this.testClass)
        .doScheduleMethodUpdateForAssignTask(assignTaskType,
            appraisalAsgDocumentId, loggedInUserInfoDocument);
    this.testClass.doScheduleMethodUpdateForAssignTask(assignTaskType,
        appraisalAsgDocumentId, loggedInUserInfoDocument);

    verify(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument,
        AppraisalAssignmentServiceHandlerImpl.SCHEDULE_METHOD_MANUAL);

  }

  @Test
  public void doScheduleMethodUpdateForAssignTaskTest()
      throws Exception
  {
    AssignTaskType assignTaskType = AssignTaskType.Factory.newInstance();
    assignTaskType.setScheduleMethod("bobo");
    long appraisalAsgDocumentId = 66;
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .newInstance();

    // Call method in test
    doCallRealMethod().when(this.testClass)
        .doScheduleMethodUpdateForAssignTask(assignTaskType,
            appraisalAsgDocumentId, loggedInUserInfoDocument);
    this.testClass.doScheduleMethodUpdateForAssignTask(assignTaskType,
        appraisalAsgDocumentId, loggedInUserInfoDocument);

    verify(this.testClass).updateScheduleMethodInDatabase(
        appraisalAsgDocumentId, loggedInUserInfoDocument, "bobo");

  }
  
  
  @Test
  public void testGetLatestEstimateForColloborativeWFForNonStaff() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setBusinessPartnerId(new Long(234));
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), 
    		  Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Integer(1), latestEstimate.getEstimateDocRevision());
      Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  @Test
  public void testGetLatestEstimateWFForNonStaff() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      Date commitDate = new java.util.Date(System.currentTimeMillis());

      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setCorrectionNumber(0L);
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(commitDate);
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(1L);
      estimate[1].setCorrectionNumber(2L);
      estimate[1].setBusinessPartnerId(new Long(234));
      estimate[1].setCommitDate(commitDate);
      estimate[2] = new Estimate();
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setCorrectionNumber(1L);
      estimate[2].setCommitDate(commitDate);
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME),
    		  Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("N");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(1), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Long(2), latestEstimate.getCorrectionNumber());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  @Test
  public void testGetLatestEstimateWFfromCommitDateForNonStaff() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setCorrectionNumber(0L);
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis() + 10000));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(1L);
      estimate[1].setCorrectionNumber(2L);
      estimate[1].setBusinessPartnerId(new Long(234));
      estimate[1].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[2] = new Estimate();
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setCorrectionNumber(1L);
      estimate[2].setCommitDate(new java.util.Date(System.currentTimeMillis() + 2000));
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME),
    		  Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("N");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Long(0), latestEstimate.getCorrectionNumber());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  
  
  @Test
  public void testGetLatestEstimateForColloborativeWFForNonStaffWithDiffBP() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setBusinessPartnerId(new Long(456));
      estimate[1].setCommitDate(new java.util.Date(System.currentTimeMillis() + 10000));
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(new java.util.Date(System.currentTimeMillis() + 3000));
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Integer(0), latestEstimate.getEstimateDocRevision());
      Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  

  
  @Test
  public void testGetLatestEstimateForColloborativeWFWithCopyEstimates() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("COPY");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("COPY");
      estimate[1].setBusinessPartnerId(new Long(456));
      estimate[1].setCommitDate(new java.util.Date(System.currentTimeMillis() + 10000));
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(new java.util.Date(System.currentTimeMillis() + 3000));
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertNull(latestEstimate);
     
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  
  @Test
  public void testGetLatestEstimatewithNoEstimates() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setBusinessPartnerId(new Long(456));
      estimate[1].setCommitDate(new java.util.Date(System.currentTimeMillis() + 10000));
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(new java.util.Date(System.currentTimeMillis() + 3000));
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(null);
     
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertNull(latestEstimate);

      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
        
  }
  
  
  
  
  @Test
  public void testGetLatestEstimateForColloborativeWFWithCommitDateForNonStaffWithDiffBP() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Date commitDate = new java.util.Date(System.currentTimeMillis());
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(commitDate);
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setBusinessPartnerId(new Long(456));
      estimate[1].setCommitDate(commitDate);
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(commitDate);
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Integer(0), latestEstimate.getEstimateDocRevision());
      Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  @Test
  public void testGetLatestEstimateForColloborativeWFWithCommitDateWithCommitDateForNonStaffWithDiffBP() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);

      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setBusinessPartnerId(new Long(456));
      estimate[1].setCommitDate(new java.util.Date(System.currentTimeMillis() + 20000));
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setBusinessPartnerId(new Long(234));
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(new java.util.Date(System.currentTimeMillis() + 40000));
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Integer(0), latestEstimate.getEstimateDocRevision());
      Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  @Test
  public void testGetLatestEstimateForWFForNonStaffWithDiffBP() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      UserInfoType userInfoType = UserInfoType.Factory.newInstance();
      userInfoType.setOrgID("234");
      userInfoDoc.setUserInfo(userInfoType);
      
      Date commitDate = new java.util.Date(System.currentTimeMillis());
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setCorrectionNumber(0L);
      estimate[0].setBusinessPartnerId(new Long(234));
      estimate[0].setCommitDate(commitDate);
      
      estimate[1] = new Estimate();
      estimate[1].setBusinessPartnerId(new Long(234));
      estimate[1].setSupplementNumber(0L);
      estimate[1].setCorrectionNumber(2L);
      estimate[1].setCommitDate(commitDate);
      
      estimate[2] = new Estimate();
      estimate[2].setSupplementNumber(0L);
      estimate[2].setCorrectionNumber(1L);
      estimate[2].setBusinessPartnerId(new Long(456));
      estimate[2].setCommitDate(commitDate);
   
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("N");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Long(2), latestEstimate.getCorrectionNumber());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
  
  @Test
  public void testGetLatestEstimateForColloborativeWFForStaff() throws MitchellException
  {
      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      UserInfoDocument userInfoDoc =null;
      Date commitDate = new java.util.Date(System.currentTimeMillis());
      
      
      Estimate estimate[] = new Estimate[3];
      estimate[0] = new Estimate();
      estimate[0].setSupplementNumber(0L);
      estimate[0].setEstimateDocRevision(0);
      estimate[0].setEstimateDocCategory("DRAFT");
      estimate[0].setCommitDate(commitDate);
      estimate[1] = new Estimate();
      estimate[1].setSupplementNumber(0L);
      estimate[1].setEstimateDocRevision(1);
      estimate[1].setEstimateDocCategory("DRAFT");
      estimate[1].setCommitDate(commitDate);
      estimate[2] = new Estimate();
      estimate[2].setEstimateDocCategory("COPY");
      estimate[2].setSupplementNumber(0L);
      estimate[2].setEstimateDocRevision(2);
      estimate[2].setCommitDate(commitDate);
      
    
      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
 
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertNull(latestEstimate.getBusinessPartnerId());
      Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
      Assert.assertEquals(new Integer(1), latestEstimate.getEstimateDocRevision());
      Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());
      
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);  
  }
  
  
@Test
  public void testGetLatestEstimateFromZeroEstimateList() throws MitchellException{

      String coCode = "L4";
      String claimSuffixNumber = "LDAL331-01";
      String userId = null;
      Estimate estimate[] = null;

      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class); 
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
   

      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,null);
      
      Assert.assertEquals(null,latestEstimate);
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
     
  }


@Test
  public void testGetLatestEstimateWithNoSupplementNo() throws MitchellException{

	 String coCode = "L4";
     String claimSuffixNumber = "LDAL331-01";
     String userId = null;
     UserInfoDocument userInfoDoc =null;
     String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
     UserInfoDocument userInfo = null;
     Date commitDate = new java.util.Date(System.currentTimeMillis());
     Estimate estimate[] = new Estimate[3];
     estimate[0] = new Estimate();
     estimate[0].setBusinessPartnerId(new Long(123));
     estimate[0].setEstimateDocRevision(0);
     estimate[0].setEstimateDocCategory("DRAFT");
     estimate[0].setCommitDate(commitDate);
     estimate[1] = new Estimate();
     estimate[1].setBusinessPartnerId(new Long(234));
     estimate[1].setEstimateDocRevision(1);
     estimate[1].setEstimateDocCategory("DRAFT");
     estimate[1].setCommitDate(commitDate);
     estimate[2] = new Estimate();
     estimate[2].setEstimateDocCategory("COPY");
     estimate[2].setBusinessPartnerId(new Long(345));
     estimate[2].setEstimateDocRevision(2);
     estimate[2].setCommitDate(commitDate);
     
     final String workItemID = "UNKNOWN";
     final int errorCode = AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE;
     final String errorDescription = AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE_MSG;
     
     String TEST_CLASS_NAME  = AppraisalAssignmentServiceHandlerImpl.class
     .getName();
     String TEST_METHOD_NAME = "getLatestEstimate";

     StringBuffer msgDetail = new StringBuffer();
     msgDetail.append("InsuranceCarrierCoCode : ")
         .append(coCode).append("\tClientClaimNumber : ")
         .append(claimSuffixNumber).append("\tEstimator UserInfoDocument : ")
         .append(userInfo);

      EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
      CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
      
      AASErrorLogUtil errorLogUtil = mock(AASErrorLogUtil.class);
      
      MitchellException me = new MitchellException(
			AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE,
  		"CLASSNAME", "methodName", "Error getting latest estimate");
      Mockito.doThrow(me).when(customSettingsProxy).getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME));    		  
      Mockito.doNothing().when(errorLogUtil).logAndThrowError(Mockito.eq(me),Mockito.eq(claimSuffixNumber), Mockito.eq(userInfoDoc));
      
    
      AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
      handler.setEstimatePackageProxy(estimatePackageProxy);
      handler.setCustomSettingProxy(customSettingsProxy);
      handler.setErrorLogUtil(errorLogUtil);
      
      when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
      Estimate latestEstimate = handler.getLatestEstimate(coCode,claimSuffixNumber,userInfoDoc);
      
      Assert.assertEquals(null, latestEstimate);
      verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
      verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME); 
      verify(errorLogUtil).logAndThrowError(Mockito.eq(me),Mockito.eq(claimSuffixNumber), Mockito.eq(userInfoDoc));
  }

    @Test
    public void testGetLatestEstimateNoFiltering() throws MitchellException {
        String coCode = "L4";
        String claimSuffixNumber = "LDAL331-01";
        String userId = null;

        Date commitDate = new java.util.Date(System.currentTimeMillis());
        Estimate[] estimates = new Estimate[3];
        estimates[0] = new Estimate();
        estimates[0].setSupplementNumber(0L);
        estimates[0].setCorrectionNumber(0L);
        estimates[0].setBusinessPartnerId(new Long(234));
        estimates[0].setCommitDate(commitDate);

        estimates[1] = new Estimate();
        estimates[1].setBusinessPartnerId(new Long(234));
        estimates[1].setSupplementNumber(0L);
        estimates[1].setCorrectionNumber(2L);
        estimates[1].setCommitDate(commitDate);

        estimates[2] = new Estimate();
        estimates[2].setSupplementNumber(2L);
        estimates[2].setCorrectionNumber(0L);
        estimates[2].setBusinessPartnerId(new Long(456));
        estimates[2].setCommitDate(commitDate);

        EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
        CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
        AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
        handler.setEstimatePackageProxy(estimatePackageProxy);
        handler.setCustomSettingProxy(customSettingsProxy);

        when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimates);
        when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("N");
        Estimate latestEstimate = handler.getLatestEstimateNoFiltering(coCode,claimSuffixNumber);

        Assert.assertEquals(new Long(456), latestEstimate.getBusinessPartnerId());
        Assert.assertEquals(new Long(2), latestEstimate.getSupplementNumber());
        Assert.assertEquals(new Long(0), latestEstimate.getCorrectionNumber());

        verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
        verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);

    }

    @Test
    public void testGetLatestEstimateNoFilteringWithCollaborativeWF() throws MitchellException {
        String coCode = "L4";
        String claimSuffixNumber = "LDAL331-01";
        String userId = null;

        Date commitDate = new java.util.Date(System.currentTimeMillis());
        Estimate estimate[] = new Estimate[3];
        estimate[0] = new Estimate();
        estimate[0].setSupplementNumber(0L);
        estimate[0].setEstimateDocRevision(0);
        estimate[0].setEstimateDocCategory("DRAFT");
        estimate[0].setBusinessPartnerId(new Long(234));
        estimate[0].setCommitDate(commitDate);
        estimate[1] = new Estimate();
        estimate[1].setSupplementNumber(0L);
        estimate[1].setEstimateDocRevision(1);
        estimate[1].setEstimateDocCategory("COPY");
        estimate[1].setBusinessPartnerId(new Long(456));
        estimate[1].setCommitDate(commitDate);
        estimate[2] = new Estimate();
        estimate[2].setEstimateDocCategory("COPY");
        estimate[2].setBusinessPartnerId(new Long(234));
        estimate[2].setSupplementNumber(0L);
        estimate[2].setEstimateDocRevision(2);
        estimate[2].setCommitDate(commitDate);


        EstimatePackageProxy estimatePackageProxy = mock(EstimatePackageProxy.class);
        CustomSettingProxy customSettingsProxy = mock(CustomSettingProxy.class);
        AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
        handler.setEstimatePackageProxy(estimatePackageProxy);
        handler.setCustomSettingProxy(customSettingsProxy);

        when(estimatePackageProxy.findEstimateByClaimNumber(Mockito.eq(coCode),Mockito.eq(userId), Mockito.eq(claimSuffixNumber))).thenReturn(estimate);
        when(customSettingsProxy.getCompanyCustomSettings(Mockito.eq(coCode),Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), Mockito.eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))).thenReturn("Y");
        Estimate latestEstimate = handler.getLatestEstimateNoFiltering(coCode,claimSuffixNumber);

        Assert.assertEquals(new Long(234), latestEstimate.getBusinessPartnerId());
        Assert.assertEquals(new Long(0), latestEstimate.getSupplementNumber());
        Assert.assertEquals(new Integer(0), latestEstimate.getEstimateDocRevision());
        Assert.assertEquals("DRAFT", latestEstimate.getEstimateDocCategory());

        verify(estimatePackageProxy).findEstimateByClaimNumber(coCode, null, claimSuffixNumber);
        verify(customSettingsProxy).getCompanyCustomSettings(coCode, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME, AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME);
    }

	@Test
	public void test_saveSSOAppraisalAssignment_shopEstimateAllowed_true()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
		// set shop estimate allowed true
		inputAppraisalAssignmentDTO.setShopEstimateAllowed("true");
		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();
		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(inputDTO);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, true);

		Assert.assertEquals("shop estimate allowed is true",
				inputDTO.getAssignmentPullFlag(), "true");

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}
	
	@Test(expected = NullPointerException.class)
	public void test_saveSSOAppraisalAssignment_shopEstimateAllowed_exception()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
		// set shop estimate allowed true
		inputAppraisalAssignmentDTO.setShopEstimateAllowed("true");
		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();
		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(inputDTO);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, false);

		Assert.assertEquals("shop estimate allowed is true",
				inputDTO.getAssignmentPullFlag(), "true");

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}

	@Test
	public void test_saveSSOAppraisalAssignment_shopEstimateAllowed_empty()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();

		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();

		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(inputDTO);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, true);

		Assert.assertNull("shop estimate allowed is empty",
				inputDTO.getAssignmentPullFlag());

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}

	@Test
	public void test_saveSSOAppraisalAssignment_suffixStatus_ON_HOLD()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
		// set suffix status ON_HOLD
		inputAppraisalAssignmentDTO.setSuffixStatus("ON_HOLD");
		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();

		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(inputDTO);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, true);

		Assert.assertEquals("suffix status is ON_HOLD",
				inputDTO.getSuffixStatus(), "ON_HOLD");

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}

	@Test
	public void test_saveSSOAppraisalAssignment_suffixStatus_empty()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();

		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();

		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(inputDTO);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, true);

		Assert.assertNull("suffix status is not set",
				inputDTO.getSuffixStatus());

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}

	@Test(expected = NullPointerException.class)
	public void test_saveSSOAppraisalAssignment_suffixStatus_exception()
			throws Exception {

		String message = readFileAsString("src/test/resources/WAS.XML");
		WorkAssignment wa = new WorkAssignment();
		wa.setTaskID(150503l);
		wa.setWorkAssignmentStatus("OPENED");
		wa.setWorkAssignmentCLOBB(message);
		wa.setTcn(123446L);
		wa.setId(987654L);

		DispositionCode assignmentDisposition = new DispositionCode();
		assignmentDisposition.setDispositionCode("dispositionCode");
		wa.setAssignmentDisposition(assignmentDisposition);

		AppraisalAssignmentServiceHandlerImpl handler = new AppraisalAssignmentServiceHandlerImpl();
		handler.setMitchellEnvelopeHandler(mitchellEnvelopeHandler);
		handler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
		handler.setClaimProxy(claimProxy);
		handler.setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
		handler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
		handler.setCommonUtils(commonUtils);
		handler.setUserInfoUtils(userInfoUtils);
		handler.setWorkAssignmentHandler(workAssignmentHandler);
		handler.setAppLogUtil(appLogUtil);
		File userInfoXml = new File("src/test/resources/userinfo.xml");
		UserInfoDocument userInfoDoc = UserInfoDocument.Factory
				.parse(userInfoXml);
		MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
				.parse(new File("src/test/resources/ME.XML"));

		AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
		// set suffix status ON_HOLD
		inputAppraisalAssignmentDTO.setSuffixStatus("ON_HOLD");
		inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

		AssignmentAddRqDocument assignmentAddRqDocument = AssignmentAddRqDocument.Factory
				.newInstance();
		AssignmentAddRq assignmentAddRq = assignmentAddRqDocument
				.addNewAssignmentAddRq();
		assignmentAddRq.addNewAdminInfo();

		ClaimInfoType claimInfoType = assignmentAddRq.addNewClaimInfo();
		claimInfoType.setClaimNum("TEST-01");

		BmsClmInputDTO inputDTO = new BmsClmInputDTO();
		inputDTO.setClaimNumber("TEST-CLAIM");
		McfClmOutDTO resultDTO = new McfClmOutDTO();
		resultDTO.setClaimID(123456L);
		resultDTO.setExposureID(123456789L);
		Claim claim = new Claim();
		claim.setClaimNumber("TEST-CLAIM");
		resultDTO.setClaim(claim);
		AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory
				.newInstance();

		AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = AdditionalTaskConstraintsDocument.Factory
				.newInstance();

		CIECADocument ciecaDocument = CIECADocument.Factory.newInstance();
		ciecaDocument.addNewCIECA();

		when(
				mitchellEnvelopeHandler
						.fetchAssignmentAddRqDocument((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(assignmentAddRqDocument);

		when(
				appraisalAssignmentUtils.retrieveCustomSettings(anyString(),
						anyString(), anyString(), anyString())).thenReturn(
				"claim-mask");
		when(
				claimProxy.createInputDTO(
						(AssignmentAddRqDocument) anyObject(),
						(UserInfoDocument) anyObject(), anyString(),
						(UserInfoDocument) anyObject())).thenReturn(null);

		when(
				mitchellEnvelopeHandler
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(null);

		when(claimProxy.saveClaimFromAssignmentBms(inputDTO)).thenReturn(
				resultDTO);

		when(
				mitchellEnvelopeHandler
						.getCiecaFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(ciecaDocument);

		when(
				mitchellEnvelopeHandler
						.getAdditionalTaskConstraintsFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(additionalTaskConstraintsDocument);

		when(
				appraisalAssignmentMandFieldUtils
						.getAdditionalAppraisalAssignmentInfoDocumentFromME((MitchellEnvelopeHelper) anyObject()))
				.thenReturn(aaaInfoDoc);

		when(
				appraisalAssignmentMandFieldUtils
						.getValidVehicleLocationAddress(
								(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
								anyString())).thenReturn("Yes");

		when(
				mitchellEnvelopeHandler.saveBMS(anyLong(), anyLong(),
						anyLong(), (MitchellEnvelopeDocument) anyObject(),
						(UserInfoDocument) anyObject())).thenReturn(123456L);

		when(appraisalAssignmentConfig.getCreateAAActivityLog()).thenReturn(
				"Test-Activity-Log");

		doNothing().when(commonUtils).saveExposureActivityLog(anyString(),
				anyLong(), anyLong(), anyString(),
				(UserInfoDocument) anyObject());

		when(userInfoUtils.getUserType(anyString(), anyString())).thenReturn(
				"AssineeType");
		when(
				workAssignmentHandler
						.saveWorkAssignment(
								anyLong(),
								anyLong(),
								anyLong(),
								anyString(),
								anyString(),
								(AppraisalAssignmentDTO) anyObject(),
								(UserInfoDocument) anyObject(),
								(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
								anyLong())).thenReturn(wa);

		doNothing().when(appLogUtil).appLog(anyInt(),
				(WorkAssignment) anyObject(), (UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

		handler.saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
				userInfoDoc, false);

		Assert.assertEquals("suffix status is ON_HOLD",
				inputDTO.getSuffixStatus(), "ON_HOLD");

		verify(mitchellEnvelopeHandler, times(2)).fetchAssignmentAddRqDocument(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentUtils).retrieveCustomSettings(anyString(),
				anyString(), anyString(), anyString());
		verify(claimProxy).createInputDTO(
				(AssignmentAddRqDocument) anyObject(),
				(UserInfoDocument) anyObject(), anyString(),
				(UserInfoDocument) anyObject());

		verify(mitchellEnvelopeHandler, times(2))
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(claimProxy).saveClaimFromAssignmentBms(inputDTO);

		verify(mitchellEnvelopeHandler).getCiecaFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(mitchellEnvelopeHandler).getAdditionalTaskConstraintsFromME(
				(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getAdditionalAppraisalAssignmentInfoDocumentFromME(
						(MitchellEnvelopeHelper) anyObject());

		verify(appraisalAssignmentMandFieldUtils)
				.getValidVehicleLocationAddress(
						(AdditionalAppraisalAssignmentInfoDocument) anyObject(),
						anyString());

		verify(mitchellEnvelopeHandler).saveBMS(anyLong(), anyLong(),
				anyLong(), (MitchellEnvelopeDocument) anyObject(),
				(UserInfoDocument) anyObject());

		verify(appraisalAssignmentConfig).getCreateAAActivityLog();

		verify(commonUtils).saveExposureActivityLog(anyString(), anyLong(),
				anyLong(), anyString(), (UserInfoDocument) anyObject());

		verify(userInfoUtils).getUserType(anyString(), anyString());
		verify(workAssignmentHandler).saveWorkAssignment(anyLong(), anyLong(),
				anyLong(), anyString(), anyString(),
				(AppraisalAssignmentDTO) anyObject(),
				(UserInfoDocument) anyObject(),
				(com.mitchell.schemas.workassignment.HoldInfoType) anyObject(),
				anyLong());

		verify(appLogUtil).appLog(anyInt(), (WorkAssignment) anyObject(),
				(UserInfoDocument) anyObject(),
				(MitchellEnvelopeDocument) anyObject(), anyLong());

	}
	
	private static String readFileAsString(String filePath)
			throws java.io.IOException {
		// determine the number of characters in file
		int fileLength = (int) new java.io.File(filePath).length();
		// get an empty byte array of number of characters
		// in given file as length
		byte[] buffer = new byte[fileLength];
		// create an input stream for given file
		InputStream f = new FileInputStream(filePath);
		// read stream to byte array
		f.read(buffer);
		// get string value out of byte array
		return new String(buffer);
	}

}
