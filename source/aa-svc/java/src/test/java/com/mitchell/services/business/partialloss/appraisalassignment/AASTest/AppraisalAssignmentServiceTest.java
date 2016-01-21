package com.mitchell.services.business.partialloss.appraisalassignment.AASTest;



import java.io.File;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.jmock.util.Verifier;



import com.mitchell.common.types.EstPkgAppraisalAssignmentDocument;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.task.TaskType;
import com.mitchell.schemas.workassignment.HoldInfoType;
import com.mitchell.schemas.workassignment.PersonInfoType;
import com.mitchell.schemas.workassignment.PersonNameType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentStatusType;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentDAO;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.Constraints.EstimatePackageUncancelConstraint;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.Constraints.WorkAssignmentServiceUncancelConstraint;
import com.mitchell.services.business.partialloss.appraisalassignment.AASTest.helper.AASTestHelper;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AppraisalAssignmentServiceHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.MessageBusHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.TestAssignmentHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.mapping.WorkAssignmentMapper;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ErrorLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.MessageBusProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASAppLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASAppLogUtilImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtilsImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASErrorLogUtilImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandlerImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtilsImpl;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandlerImpl;
import com.mitchell.services.core.customsettings.types.xml.SettingValue;
import com.mitchell.services.core.customsettings.types.xml.SettingValues;
import com.mitchell.services.core.userinfo.types.AddressType;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.userinfo.types.UserDetailType;
import com.mitchell.services.core.workassignment.bo.AssignmentSubType;
import com.mitchell.services.core.workassignment.bo.AssignmentType;
import com.mitchell.services.core.workassignment.bo.DispositionCode;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class AppraisalAssignmentServiceTest extends MockObjectTestCase
{

  static final String WAS_FILE = AASTestHelper.getResourcePath("WAS.XML");
  static final String ME_FILE = AASTestHelper.getResourcePath("ME.XML");
  static final String ME_FILE_NON_SC = AASTestHelper
      .getResourcePath("ME_NON_SC.XML");
  static final String ME_ASSIGN_FILE = AASTestHelper
      .getResourcePath("MEASSIGN.XML");

  private static final WorkAssignment WorkAssignment = null;

  private AppraisalAssignmentServiceHandlerImpl aasHandler = null;

  Mock appraisalAssignmentDAOMoc = null;
  Mock workAssignmentProxyMoc = null;
  Mock aASAppLogUtilMoc = null;
  Mock aASCommonUtilsMoc = null;
  Mock systemConfigProxyMoc = null;
  Mock appLogProxyMoc = null;
  Mock claimProxyMoc = null;
  Mock errorLogProxyMoc = null;
  Mock carrHelperProxyMoc = null;
  Mock estimatePackageProxyMoc = null;
  Mock userInfoProxyMoc = null;
  Mock customSettingProxyMoc = null;
  Mock messageBusProxyMoc = null;
  Mock mitchellEnvHandler = null;

  MitchellEnvelopeHandlerImpl mitchellEnvelopeHandlerImpl = null;
  WorkAssignmentHandlerImpl workAssignmentHandler = null;
  IAppraisalAssignmentConfig appraisalAssignmentConfig = null;
  AASCommonUtilsImpl aASCommonUtils = null;
  AASAppLogUtilImpl aASAppLogUtil = null;
  AASErrorLogUtilImpl aASErrorLogUtil = null;
  UserInfoUtilsImpl userInfoUtils = null;
  WorkAssignmentMapper workAssignmentMapper = null;
  IAppraisalAssignmentUtils appraisalAssignmentUtils = null;
  IAppraisalAssignmentMandFieldUtils appraisalAssignmentMandFieldUtils = null;
  TestAssignmentHandlerImpl testAssignmentHandler = null;
  MessageBusHandlerImpl messageBusHandler = null;

  @Override
  protected void setUp()
  {

    Properties p = System.getProperties();
    Set s = p.keySet();
    Iterator it = s.iterator();
    while (it.hasNext()) {
      Object o = it.next();
      System.out.println(o + "::" + p.getProperty((String) o));
    }
    AppraisalAssignmentServiceTest.class.getClassLoader()
        .getResource("WAS.XML");
    aasHandler = new AppraisalAssignmentServiceHandlerImpl();
    mitchellEnvelopeHandlerImpl = new MitchellEnvelopeHandlerImpl();
    workAssignmentHandler = new WorkAssignmentHandlerImpl();
    appraisalAssignmentConfig = new AppraisalAssignmentConfig();
    aASCommonUtils = new AASCommonUtilsImpl();
    aASAppLogUtil = new AASAppLogUtilImpl();
    aASErrorLogUtil = new AASErrorLogUtilImpl();
    userInfoUtils = new UserInfoUtilsImpl();
    workAssignmentMapper = new WorkAssignmentMapper();
    appraisalAssignmentUtils = new AppraisalAssignmentUtils();
    appraisalAssignmentMandFieldUtils = new AppraisalAssignmentMandFieldUtils();
    testAssignmentHandler = new TestAssignmentHandlerImpl();
    messageBusHandler = new MessageBusHandlerImpl();
    // Mock for Proxies
    appLogProxyMoc = mock(AppLogProxy.class);
    claimProxyMoc = mock(ClaimProxy.class);
    errorLogProxyMoc = mock(ErrorLogProxy.class);
    workAssignmentProxyMoc = mock(WorkAssignmentProxy.class);
    systemConfigProxyMoc = mock(SystemConfigProxy.class);
    estimatePackageProxyMoc = mock(EstimatePackageProxy.class);
    userInfoProxyMoc = mock(UserInfoProxy.class);
    customSettingProxyMoc = mock(CustomSettingProxy.class);
    // Mock Objects
    appraisalAssignmentDAOMoc = mock(AppraisalAssignmentDAO.class);
    messageBusProxyMoc = mock(MessageBusProxy.class);
	mitchellEnvHandler = mock(MitchellEnvelopeHandler.class);

    // Add Mocks to objects
    // Populate AppLogUtil with Mocks
    aASAppLogUtil.setAppLogProxy((AppLogProxy) appLogProxyMoc.proxy());
    aASAppLogUtil.setSystemConfigProxy((SystemConfigProxy) systemConfigProxyMoc
        .proxy());

    // Populate AppraisalAssignmentConfig Mocks
    appraisalAssignmentConfig
        .setSystemConfigProxy((SystemConfigProxy) systemConfigProxyMoc.proxy());
    // Populate MandatoryFieldUtils Mocks
    appraisalAssignmentMandFieldUtils
        .setAppraisalAssignmentUtils(appraisalAssignmentUtils);
    appraisalAssignmentMandFieldUtils.setCommonUtils(aASCommonUtils);
    // Populate AppraisalAssignmentUtils Mocks
    appraisalAssignmentUtils.setUserInfoProxy((UserInfoProxy) userInfoProxyMoc
        .proxy());
    appraisalAssignmentUtils
        .setCustomSettingProxy((CustomSettingProxy) customSettingProxyMoc
            .proxy());
    // Populate AASCommonUtils with Mocks
    aASCommonUtils.setClaimProxy((ClaimProxy) claimProxyMoc.proxy());
    aASCommonUtils
        .setSystemConfigProxy((SystemConfigProxy) systemConfigProxyMoc.proxy());

    aASCommonUtils.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
    aASCommonUtils.setErrorLogUtil(aASErrorLogUtil);

    // PopulateMitchellEnvelopeHandler
    mitchellEnvelopeHandlerImpl.setErrorLogUtil(aASErrorLogUtil);
    mitchellEnvelopeHandlerImpl.setAasCommonUtils(aASCommonUtils);
    mitchellEnvelopeHandlerImpl
        .setEstimatePackageProxy((EstimatePackageProxy) estimatePackageProxyMoc
            .proxy());
    // Populate UserInfoUtils
    userInfoUtils.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
    userInfoUtils.setCommonUtils(aASCommonUtils);
    userInfoUtils.setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
    // Populate WorkAssignmentHandler
    workAssignmentHandler
        .setWorkAssignmentProxy((WorkAssignmentProxy) workAssignmentProxyMoc
            .proxy());
    workAssignmentHandler.setErrorLogUtil(aASErrorLogUtil);
    workAssignmentHandler.setUserInfoUtils(userInfoUtils);
    workAssignmentHandler.setWorkAssignmentMapper(workAssignmentMapper);
    // Populate MessageBusHandler
    messageBusHandler.setMessageBusProxy((MessageBusProxy) messageBusProxyMoc
        .proxy());
	// Add objects / Mocks to Handler
    aasHandler.setAppLogUtil(aASAppLogUtil);
    aasHandler.setAppraisalAssignmentConfig(appraisalAssignmentConfig);
    aasHandler
        .setAppraisalAssignmentDAO((AppraisalAssignmentDAO) appraisalAssignmentDAOMoc
            .proxy());
    aasHandler
        .setAppraisalAssignmentMandFieldUtils(appraisalAssignmentMandFieldUtils);
    aasHandler.setAppraisalAssignmentUtils(appraisalAssignmentUtils);
    aasHandler.setCarrHelperProxy(null);
    aasHandler.setClaimProxy((ClaimProxy) claimProxyMoc.proxy());
    aasHandler.setCommonUtils(aASCommonUtils);
    aasHandler.setErrorLogUtil(aASErrorLogUtil);
    aasHandler
        .setEstimatePackageProxy((EstimatePackageProxy) estimatePackageProxyMoc
            .proxy());
    aasHandler.setMitchellEnvelopeHandler(mitchellEnvelopeHandlerImpl);
    aasHandler.setUserInfoUtils(userInfoUtils);
    aasHandler.setWorkAssignmentHandler(workAssignmentHandler);
    aasHandler.setTestAssignmentHandler(testAssignmentHandler);
    aasHandler.setMessageBusHandler(messageBusHandler);
  }

  public void testCancelAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.cancellAppraisalAssignment..");

      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));

      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "CANCELLED");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      workAssignmentProxyMoc.expects(atLeastOnce())
          .method("saveWorkAssignmentHist").withAnyArguments()
          .will(returnValue(new Long(11)));
      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentScheduleMethod").withAnyArguments();

      final long taskID = wa.getTaskID().longValue();
      final String cancelReason = "OTHER";
      final long tcn = wa.getTcn().longValue();
      final String cancelReasonNotes = "notes";
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      System.out.println("Inputs for AAS.cancellAppraisalAssignment::");
      System.out.println("taskID ::" + taskID);
      System.out.println("cancelReason ::" + cancelReason);
      System.out.println("tcn ::" + tcn);
      System.out.println("cancelReasonNotes::" + cancelReasonNotes);
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      int result = aasHandler.cancelAppraisalAssignment(taskID, cancelReason,
          tcn, cancelReasonNotes, loggedInUserInfo);

      assertEquals(result, 0);
    } catch (Throwable e) {
      e.printStackTrace();
      System.out.println("Cause::" + e.getCause().getMessage());
      fail();
    }
  }

  public void testUnCancelAppraisalAssignment()
  {
    try {
      System.out.println("Testing UnCancelAppraisalAssignment..");
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("getWorkGroupType").withAnyArguments()
          .will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));

      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes")
          .withAnyArguments().will(returnValue("STAFF"));
      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));
      // EstimatePackageConstraints
      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(0);
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));
      appraisalAssignmentDTO.setAppraisalAssignmentMEStr(meDoc.toString());
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("getAppraisalAssignmentDocument").withAnyArguments()
          .will(returnValue(appraisalAssignmentDTO));
      //
      final Constraint[] constraints = new Constraint[] { ANYTHING,
          new EstimatePackageUncancelConstraint(), ANYTHING, ANYTHING };
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentDocument").with(constraints);
      final long taskID = wa.getTaskID().longValue();

      final long tcn = wa.getTcn().longValue();

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      System.out.println("Inputs for AAS.cancellAppraisalAssignment::");
      System.out.println("taskID ::" + taskID);
      System.out.println("tcn ::" + tcn);
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      int result = aasHandler.uncancelAppraisalAssignment(taskID, tcn,
          loggedInUserInfo);

      assertEquals(result, 0);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testDispatchAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.dispatchAppraisalAssignment..");

      // SetUp Mock objects

      //appraisalAssignmentDAOMoc.expects(atLeastOnce()).method("getWorkGroupType").withAnyArguments().will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      //customSettingProxyMoc.expects(atLeastOnce()).method("getDefaultProfile").withAnyArguments();
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      //setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentScheduleMethod").withAnyArguments();

      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "DISPATCHED");

      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));

      final long taskID = wa.getTaskID().longValue();
      final long tcn = wa.getTcn().longValue();

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      System.out.println("Inputs for AAS.dispatchAppraisalAssignment::");
      System.out.println("taskID ::" + taskID);
      System.out.println("tcn ::" + tcn);
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      int result = aasHandler.dispatchAppraisalAssignment(taskID, tcn,
          loggedInUserInfo);
      System.out.println("result=" + result);
      assertEquals(result, 0);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testSaveAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.saveAppraisalAssignment..");

      // Set Expectations
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("getWorkGroupType").withAnyArguments()
          .will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      claimProxyMoc.expects(atLeastOnce()).method("setManagedById")
          .withAnyArguments();
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes")
          .withAnyArguments().will(returnValue("STAFF"));
      // Set default profile
      customSettingProxyMoc
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      // Set Claim Mask
      SettingValue settingValue = com.mitchell.services.core.customsettings.types.xml.SettingValue.Factory
          .newInstance();
      settingValue.setValue("(.*)-(.*)");
      customSettingProxyMoc.expects(atLeastOnce()).method("getValue")
          .withAnyArguments().will(returnValue(settingValue));
      // Set Mandatory field
      SettingValues settingValues = SettingValues.Factory.newInstance();
      SettingValue[] settingValueArray = new SettingValue[1];
      settingValueArray[0] = SettingValue.Factory.newInstance();
      settingValueArray[0]
          .setValue(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_31);
      settingValues.setSettingValueArray(settingValueArray);
      customSettingProxyMoc.expects(atLeastOnce()).method("getValuesByGroup")
          .withAnyArguments().will(returnValue(settingValues));

      // Create WorkAssignment CustomSetting proxy to return the CREATED_EVENT_ID value
      SettingValues settingValues2 = SettingValues.Factory.newInstance();
      SettingValue[] settingValueArray2 = new SettingValue[1];
      settingValueArray2[0] = SettingValue.Factory.newInstance();
      settingValueArray2[0]
          .setPropertyName(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID);
      settingValueArray2[0].setValue("106903");
      settingValues2.setSettingValueArray(settingValueArray2);
      Mock waCustomSettingProxyMoc2 = new Mock(CustomSettingProxy.class);
      waCustomSettingProxyMoc2.expects(atLeastOnce())
          .method("getValuesByGroup").withAnyArguments()
          .will(returnValue(settingValues2));
      waCustomSettingProxyMoc2
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      //
      IAppraisalAssignmentUtils waAppraisalAssignmentUtils = new AppraisalAssignmentUtils();
      workAssignmentMapper
          .setAppraisalAssignmentUtils(waAppraisalAssignmentUtils);
      workAssignmentMapper.setUserInfoUtils(userInfoUtils);
      userInfoUtils.setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      waAppraisalAssignmentUtils
          .setCustomSettingProxy((CustomSettingProxy) waCustomSettingProxyMoc2
              .proxy());
      waAppraisalAssignmentUtils
          .setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo")
          .with(eq("ME"), eq("MITME"), eq(""))
          .will(returnValue(makeMockUserInfoDoc("ME", "MITME", "")));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo").with(eq("ME"), eq("AAMESA"), eq("")).will(returnValue( makeMockUserInfoDoc("ME", "AAMESA", "")));

      //
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("saveAppraisalAssignmentDocument").withAnyArguments()
          .will(returnValue(new Long(232559)));
      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(0);
      //
      Mock aASAppLogUtilImplMoc = new Mock(AASAppLogUtil.class);
      aASAppLogUtilImplMoc.expects(atLeastOnce()).method("appLog")
          .withAnyArguments();

      // Populate Input DTO
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
      inputAppraisalAssignmentDTO
          .setWorkItemID("aab5e952-ac17-0379-7dc1-a36f1fd7ecd4");
      inputAppraisalAssignmentDTO.setOriginalAssignment(true);
      inputAppraisalAssignmentDTO.setClaimId(527990);
      inputAppraisalAssignmentDTO.setClaimExposureId(336393);
      inputAppraisalAssignmentDTO.setDisposition("NOT READY");
      inputAppraisalAssignmentDTO.setStatus("OPENED");
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));

      inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");

      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "CREATE");

      System.out.println("Inputs for AAS.saveAppraisalAssignment::");
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      aasHandler.setAppLogUtil((AASAppLogUtil) aASAppLogUtilImplMoc.proxy());
      AppraisalAssignmentDTO outputAppraisalAssignmentDTO = aasHandler
          .saveAppraisalAssignment(inputAppraisalAssignmentDTO,
              loggedInUserInfo);

      assertEquals(527990, outputAppraisalAssignmentDTO.getClaimId());
      assertEquals(1, outputAppraisalAssignmentDTO.getAssignmentID());
      assertEquals(336393, outputAppraisalAssignmentDTO.getClaimExposureId());
      assertEquals("READY", outputAppraisalAssignmentDTO.getDisposition());
      assertEquals(0, outputAppraisalAssignmentDTO.getTcn());
      assertEquals(11, outputAppraisalAssignmentDTO.getWaTaskId());
      assertEquals(1, outputAppraisalAssignmentDTO.getWorkAssignmentTcn());
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testSaveSSOAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.testSaveSSOAppraisalAssignment..");

      // Set Expectations
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("getWorkGroupType").withAnyArguments()
          .will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes")
          .withAnyArguments().will(returnValue("STAFF"));
      // Set default profile
      customSettingProxyMoc
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      // Set Claim Mask
      claimProxyMoc.expects(atLeastOnce()).method("setManagedById")
          .withAnyArguments();
      SettingValue settingValue = com.mitchell.services.core.customsettings.types.xml.SettingValue.Factory
          .newInstance();
      settingValue.setValue("(.*)-(.*)");
      customSettingProxyMoc.expects(atLeastOnce()).method("getValue")
          .withAnyArguments().will(returnValue(settingValue));

      // Create WorkAssignment CustomSetting proxy to return the CREATED_EVENT_ID value
      SettingValues settingValues2 = SettingValues.Factory.newInstance();
      SettingValue[] settingValueArray2 = new SettingValue[1];
      settingValueArray2[0] = SettingValue.Factory.newInstance();
      settingValueArray2[0]
          .setPropertyName(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID);
      settingValueArray2[0].setValue("106903");
      settingValues2.setSettingValueArray(settingValueArray2);
      Mock waCustomSettingProxyMoc2 = new Mock(CustomSettingProxy.class);
      waCustomSettingProxyMoc2.expects(atLeastOnce())
          .method("getValuesByGroup").withAnyArguments()
          .will(returnValue(settingValues2));
      waCustomSettingProxyMoc2
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      //
      IAppraisalAssignmentUtils waAppraisalAssignmentUtils = new AppraisalAssignmentUtils();
      workAssignmentMapper
          .setAppraisalAssignmentUtils(waAppraisalAssignmentUtils);
      workAssignmentMapper.setUserInfoUtils(userInfoUtils);
      userInfoUtils.setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      waAppraisalAssignmentUtils
          .setCustomSettingProxy((CustomSettingProxy) waCustomSettingProxyMoc2
              .proxy());
      waAppraisalAssignmentUtils
          .setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo")
          .with(eq("ME"), eq("MITME"), eq(""))
          .will(returnValue(makeMockUserInfoDoc("ME", "MITME", "")));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo").with(eq("ME"), eq("AAMESA"), eq("")).will(returnValue( makeMockUserInfoDoc("ME", "AAMESA", "")));

      //
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("saveAppraisalAssignmentDocument").withAnyArguments()
          .will(returnValue(new Long(232559)));
      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(0);
      //
      Mock aASAppLogUtilImplMoc = new Mock(AASAppLogUtil.class);
      aASAppLogUtilImplMoc.expects(atLeastOnce()).method("appLog")
          .withAnyArguments();

      // Populate Input DTO
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
      inputAppraisalAssignmentDTO
          .setWorkItemID("aab5e952-ac17-0379-7dc1-a36f1fd7ecd4");
      inputAppraisalAssignmentDTO.setOriginalAssignment(true);
      inputAppraisalAssignmentDTO.setClaimId(527990);
      inputAppraisalAssignmentDTO.setClaimExposureId(336393);
      inputAppraisalAssignmentDTO.setDisposition("NOT READY");
      inputAppraisalAssignmentDTO.setStatus("OPENED");
      inputAppraisalAssignmentDTO.setSaveAndSendFlag(true);
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));

      inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");

      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "CREATE");

      System.out.println("Inputs for AAS.saveSSOAppraisalAssignment::");
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      aasHandler.setAppLogUtil((AASAppLogUtil) aASAppLogUtilImplMoc.proxy());
	  messageBusProxyMoc.expects(atLeastOnce()).method("postMessage")
          .withAnyArguments().will(returnValue("published to SAN"));
      AppraisalAssignmentDTO outputAppraisalAssignmentDTO = aasHandler
          .saveSSOAppraisalAssignment(inputAppraisalAssignmentDTO,
              loggedInUserInfo);

      assertEquals("Y",
          outputAppraisalAssignmentDTO.getReqAssociateDataCompletedInd());
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testUpdateAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.updateAppraisalAssignment..");

      // Set Expectations
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("getWorkGroupType").withAnyArguments()
          .will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes")
          .withAnyArguments().will(returnValue("STAFF"));
      // Set default profile
      customSettingProxyMoc
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      // Set Claim Mask
      claimProxyMoc.expects(atLeastOnce()).method("setManagedById")
          .withAnyArguments();
      SettingValue settingValue = com.mitchell.services.core.customsettings.types.xml.SettingValue.Factory
          .newInstance();
      settingValue.setValue("(.*)-(.*)");
      customSettingProxyMoc.expects(atLeastOnce()).method("getValue")
          .withAnyArguments().will(returnValue(settingValue));
      // Set Mandatory field
      SettingValues settingValues = SettingValues.Factory.newInstance();
      SettingValue[] settingValueArray = new SettingValue[1];
      settingValueArray[0] = SettingValue.Factory.newInstance();
      settingValueArray[0]
          .setValue(AppraisalAssignmentConstants.CSET_SETTING_WCA_REQD_FLDS_REFID_31);
      settingValues.setSettingValueArray(settingValueArray);
      customSettingProxyMoc.expects(atLeastOnce()).method("getValuesByGroup")
          .withAnyArguments().will(returnValue(settingValues));

      // Create WorkAssignment CustomSetting proxy to return the CREATED_EVENT_ID value
      SettingValues settingValues2 = SettingValues.Factory.newInstance();
      SettingValue[] settingValueArray2 = new SettingValue[1];
      settingValueArray2[0] = SettingValue.Factory.newInstance();
      settingValueArray2[0]
          .setPropertyName(AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATED_TASK_RECEIVER_EVENT_CODE_ID);
      settingValueArray2[0].setValue("106902");
      settingValues2.setSettingValueArray(settingValueArray2);
      Mock waCustomSettingProxyMoc2 = new Mock(CustomSettingProxy.class);
      waCustomSettingProxyMoc2.expects(atLeastOnce())
          .method("getValuesByGroup").withAnyArguments()
          .will(returnValue(settingValues2));
      waCustomSettingProxyMoc2
          .expects(atLeastOnce())
          .method("getDefaultProfile")
          .withAnyArguments()
          .will(
              returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory
                  .newInstance()));
      //
      IAppraisalAssignmentUtils waAppraisalAssignmentUtils = new AppraisalAssignmentUtils();
      workAssignmentMapper
          .setAppraisalAssignmentUtils(waAppraisalAssignmentUtils);
      workAssignmentMapper.setUserInfoUtils(userInfoUtils);
      userInfoUtils.setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      waAppraisalAssignmentUtils
          .setCustomSettingProxy((CustomSettingProxy) waCustomSettingProxyMoc2
              .proxy());
      waAppraisalAssignmentUtils
          .setUserInfoProxy((UserInfoProxy) userInfoProxyMoc.proxy());
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo")
          .with(eq("ME"), eq("MITME"), eq(""))
          .will(returnValue(makeMockUserInfoDoc("ME", "MITME", "")));

      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(0);

      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentDocument").withAnyArguments();

      //
      Mock aASAppLogUtilImplMoc = new Mock(AASAppLogUtil.class);
      aASAppLogUtilImplMoc.expects(atLeastOnce()).method("appLog")
          .withAnyArguments();

      // Populate Input DTO
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
      inputAppraisalAssignmentDTO
          .setWorkItemID("aab5e952-ac17-0379-7dc1-a36f1fd7ecd4");
      inputAppraisalAssignmentDTO.setOriginalAssignment(true);
      inputAppraisalAssignmentDTO.setClaimId(527990);
      inputAppraisalAssignmentDTO.setClaimExposureId(336393);
      inputAppraisalAssignmentDTO.setDisposition("NOT READY");
      inputAppraisalAssignmentDTO.setStatus("OPENED");
      inputAppraisalAssignmentDTO.setWaTaskId(11);
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));

      inputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meDoc);

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");

      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "UPDATED");

      System.out.println("Inputs for AAS.saveAppraisalAssignment::");
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      aasHandler.setAppLogUtil((AASAppLogUtil) aASAppLogUtilImplMoc.proxy());

      AppraisalAssignmentDTO outputAppraisalAssignmentDTO = aasHandler
          .updateAppraisalAssignment(inputAppraisalAssignmentDTO,
              loggedInUserInfo);

      assertEquals(527990, outputAppraisalAssignmentDTO.getClaimId());
      assertEquals(1, outputAppraisalAssignmentDTO.getAssignmentID());
      assertEquals(336393, outputAppraisalAssignmentDTO.getClaimExposureId());
      assertEquals("UPDATED", outputAppraisalAssignmentDTO.getDisposition());
      assertEquals(0, outputAppraisalAssignmentDTO.getTcn());
      assertEquals(11, outputAppraisalAssignmentDTO.getWaTaskId());
      assertEquals(1, outputAppraisalAssignmentDTO.getWorkAssignmentTcn());

    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testUnScheduleAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.unScheduleAppraisalAssignment..");
      //appraisalAssignmentDAOMoc.expects(atLeastOnce()).method("getWorkGroupType").withAnyArguments().will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      //workAssignmentProxyMoc.expects(atLeastOnce()).method("saveWorkAssignmentHist").withAnyArguments().will(returnValue(new Long(11)));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
      // Set default profile
      //customSettingProxyMoc.expects(atLeastOnce()).method("getDefaultProfile").withAnyArguments().will(returnValue(com.mitchell.services.core.customsettings.types.xml.Profile.Factory.newInstance() ));
      // Set Claim Mask
      /*
       * SettingValue settingValue=
       * com.mitchell.services.core.customsettings.types
       * .xml.SettingValue.Factory.newInstance();
       * settingValue.setValue("(.*)-(.*)");
       */
      //customSettingProxyMoc.expects(atLeastOnce()).method("getValue").withAnyArguments().will(returnValue(settingValue));

      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "NOT READY");

      // estimatePackageProxyMoc.expects(atLeastOnce()).method("getAppraisalAssignmentDocument").withAnyArguments();
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentScheduleMethod").withAnyArguments();

      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));

      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");

      int returnValue = aasHandler.unScheduleAppraisalAssignment(11, 1,
          loggedInUserInfo);

      assertEquals(0, returnValue);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testVehicleTrackingStatus()
  {
    try {

      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo")
          .withAnyArguments()
          .will(returnValue(makeMockUserInfoDoc("ME", "MITME", "")));
      claimProxyMoc.expects(atLeastOnce()).method("addVehLctnTrckngHist")
          .withAnyArguments();

      aasHandler.addVehLctnTrackingHist(new Long(123), "IN_PROGRESS", "ME",
          "123");
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  private void setUpWorkAssignmentProxyMoc(String assignmentType,
      String strDisposition)
  {
    if ("LUNCH".equals(assignmentType)) {

      WorkAssignment returnWA = new WorkAssignment();
      returnWA.setId(new Long(1));
      returnWA.setExternalID("L123456");
      returnWA.setCompanyCode("ME");
      returnWA.setTaskID(new Long(123));
      returnWA.setTcn(new Long(1));
      returnWA.setReferenceId(new Long(232559));
      returnWA.setClaimID(new Long(527990));
      returnWA.setClaimExposureID(new Long(336393));
      returnWA.setAssignmentType(new AssignmentType());
      returnWA.getAssignmentType().setAssignmentTypeCode(assignmentType);
      returnWA.setAssignmentDisposition(new DispositionCode());
      returnWA.getAssignmentDisposition().setDispositionCode(strDisposition);

      try {
        WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
            .newInstance();
        WorkAssignmentType workAssignmentType = workAssignmentDocument
            .addNewWorkAssignment();
        workAssignmentType.setDisposition(strDisposition);
        workAssignmentType.setStatus(WorkAssignmentStatusType.OPENED);
        workAssignmentDocument.getWorkAssignment().setType(assignmentType);
        workAssignmentType.addNewCurrentSchedule();
        PrimaryIDsType primaryIDsType = workAssignmentType.addNewPrimaryIDs();
        primaryIDsType.setWorkAssignmentID(123);
        returnWA.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
      } catch (Throwable we) {

      }

      workAssignmentProxyMoc.expects(atLeastOnce()).method("save")
          .withAnyArguments().will(returnValue(returnWA));

    } else {
      //1. getWorkAssignmentObject
      WorkAssignment wa = new WorkAssignment();
      wa.setId(new Long(1));
      wa.setCompanyCode("ME");
      wa.setTaskID(new Long(11));
      wa.setTcn(new Long(1));
      wa.setReferenceId(new Long(232559));
      wa.setClaimID(new Long(527990));
      wa.setClaimExposureID(new Long(336393));
      wa.setAssignmentType(new AssignmentType());
      wa.getAssignmentType().setAssignmentTypeCode(assignmentType);
      wa.setAssignmentDisposition(new DispositionCode());

      wa.getAssignmentDisposition().setDispositionCode("READY");
      wa.setWorkAssignmentStatus("OPENED");
      try {
        WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
            .parse(new File(WAS_FILE));
        workAssignmentDocument.getWorkAssignment().setDisposition("READY");
        workAssignmentDocument.getWorkAssignment().setStatus(
            WorkAssignmentStatusType.OPENED);

        workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
            .setAssigneeID("AAMESA");
        PersonInfoType personInfoType = workAssignmentDocument
            .getWorkAssignment().getCurrentSchedule().addNewAssignee();
        PersonNameType personNameType = personInfoType.addNewPersonName();
        personNameType.setFirstName("AA");
        personNameType.setFirstName("BB");
        if ("RemoveOnHold".equals(strDisposition)) {
          HoldInfoType holdInfoType = workAssignmentDocument
              .getWorkAssignment().addNewHoldInfo();
          holdInfoType.setHoldReasonCode("Put On Hold");
          holdInfoType.setHoldReasonCode("OTHER");
          holdInfoType.setHoldInd("Y");
          holdInfoType.setHoldUpdatedBy("MITME");
          holdInfoType.setHoldUpdatedByDateTime(Calendar.getInstance());
        }
        workAssignmentDocument.getWorkAssignment().setType(assignmentType);
        wa.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
      } catch (Throwable we) {

      }
      if (!"CREATE".equals(strDisposition))
        workAssignmentProxyMoc.expects(atLeastOnce())
            .method("getWorkAssignmentByTaskID").withAnyArguments()
            .will(returnValue(wa));

      //2. return workAssignmentObject
      if ("CREATE".equals(strDisposition)
          || "RemoveOnHold".equals(strDisposition))
        strDisposition = "READY";

      WorkAssignment returnWA = new WorkAssignment();
      returnWA.setId(new Long(1));
      returnWA.setExternalID("A12345");
      returnWA.setCompanyCode("ME");
      returnWA.setTaskID(new Long(11));
      returnWA.setTcn(new Long(1));
      returnWA.setReferenceId(new Long(232559));
      returnWA.setClaimID(new Long(527990));
      returnWA.setClaimExposureID(new Long(336393));
      returnWA.setAssignmentType(new AssignmentType());
      returnWA.getAssignmentType().setAssignmentTypeCode(assignmentType);
      returnWA.setAssignmentDisposition(new DispositionCode());
      returnWA.getAssignmentDisposition().setDispositionCode(strDisposition);

      try {
        WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
            .parse(new File(WAS_FILE));

        workAssignmentDocument.getWorkAssignment().setDisposition(
            strDisposition);
        if ("CANCELLED".equalsIgnoreCase(strDisposition)) {
          workAssignmentDocument.getWorkAssignment().setStatus(
              WorkAssignmentStatusType.CANCELLED);
        } else {
          workAssignmentDocument.getWorkAssignment().setStatus(
              WorkAssignmentStatusType.OPENED);
        }
        workAssignmentDocument.getWorkAssignment().setType(assignmentType);
        returnWA.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
      } catch (Throwable we) {

      }

      workAssignmentProxyMoc.expects(atLeastOnce()).method("save")
          .withAnyArguments().will(returnValue(returnWA));

      if ("UPDATED".equalsIgnoreCase(strDisposition)) {
        workAssignmentProxyMoc.expects(atLeastOnce())
            .method("saveAssignmentBeenUpdatedFlag").withAnyArguments()
            .will(returnValue(true));
      }
    }
  }

  private void setUpUncancelWorkAssignmentProxyMoc(String assignmentType)
  {
    //1. getWorkAssignmentObject
    WorkAssignment wa = new WorkAssignment();
    wa.setId(new Long(1));
    wa.setCompanyCode("ME");
    wa.setTaskID(new Long(11));
    wa.setTcn(new Long(1));
    wa.setReferenceId(new Long(232559));
    wa.setClaimID(new Long(527990));
    wa.setClaimExposureID(new Long(336393));
    wa.setAssignmentType(new AssignmentType());
    wa.getAssignmentType().setAssignmentTypeCode(assignmentType);
    wa.setAssignmentDisposition(new DispositionCode());

    wa.getAssignmentDisposition().setDispositionCode("CANCELLED");
    try {
      WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(new File(WAS_FILE));
      workAssignmentDocument.getWorkAssignment().setDisposition("CANCELLED");
      workAssignmentDocument.getWorkAssignment().setStatus(
          WorkAssignmentStatusType.CANCELLED);
      ;
      workAssignmentDocument.getWorkAssignment().getEventInfo()
          .setEventMemo("CANCELL REASON");
      workAssignmentDocument.getWorkAssignment().setEventReasonCode("OTHER");
      workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
          .setAssigneeID("MA11071");
      if (workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
          .getAssignee() == null) {
        PersonInfoType personInfoType = PersonInfoType.Factory.newInstance();

        PersonNameType personNameType = PersonNameType.Factory.newInstance();
        personNameType.setFirstName("ABC");
        personNameType.setLastName("CDE");
        personInfoType.setPersonName(personNameType);

        workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
            .setAssignee(personInfoType);
        workAssignmentDocument
            .getWorkAssignment()
            .getCurrentSchedule()
            .setAssigneeUserType(
                com.mitchell.schemas.workassignment.AssigneeUserType.STAFF);
      }
      wa.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
    } catch (Throwable we) {

    }
    workAssignmentProxyMoc.expects(atLeastOnce())
        .method("getWorkAssignmentByTaskID").withAnyArguments()
        .will(returnValue(wa));

    //2. return workAssignmentObject
    WorkAssignment returnWA = new WorkAssignment();
    returnWA.setId(new Long(1));
    returnWA.setCompanyCode("ME");
    returnWA.setTaskID(new Long(11));
    returnWA.setTcn(new Long(1));
    returnWA.setReferenceId(new Long(232559));
    returnWA.setClaimID(new Long(527990));
    returnWA.setClaimExposureID(new Long(336393));
    returnWA.setAssignmentType(new AssignmentType());
    returnWA.getAssignmentType().setAssignmentTypeCode(assignmentType);
    returnWA.setAssignmentDisposition(new DispositionCode());
    returnWA.getAssignmentDisposition().setDispositionCode("NOT READY");

    try {
      WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(new File(WAS_FILE));
      workAssignmentDocument.getWorkAssignment().setDisposition("NOT READY");

      workAssignmentDocument.getWorkAssignment().setStatus(
          WorkAssignmentStatusType.OPENED);
      ;

      workAssignmentDocument.getWorkAssignment().setType(assignmentType);
      returnWA.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
    } catch (Throwable we) {

    }

    workAssignmentProxyMoc
        .expects(atLeastOnce())
        .method("unCancel")
        .with(
            new Constraint[] { new WorkAssignmentServiceUncancelConstraint(),
                ANYTHING }).will(returnValue(returnWA));
    //workAssignmentProxyMoc.expects(atLeastOnce()).method("saveAssignmentBeenUpdatedFlag").withAnyArguments();
  }

  public void testOnHoldAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.OnHoldAppraisalAssignment..");

      // SetUp Mock objects

      //appraisalAssignmentDAOMoc.expects(atLeastOnce()).method("getWorkGroupType").withAnyArguments().will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      //customSettingProxyMoc.expects(atLeastOnce()).method("getDefaultProfile").withAnyArguments();
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      //setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      //claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog").withAnyArguments();
      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "READY");
      estimatePackageProxyMoc.expects(atLeastOnce())
          .method("updateAppraisalAssignmentScheduleMethod").withAnyArguments();

      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));

      final long taskID = wa.getTaskID().longValue();
      final long tcn = wa.getTcn().longValue();
      final String selectedOnHoldTypeFromCarrier = "OTHER";
      final String notes = "Put on Hold";
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      System.out.println("Inputs for AAS.dispatchAppraisalAssignment::");
      System.out.println("taskID ::" + taskID);
      System.out.println("tcn ::" + tcn);
      System.out.println("selectedOnHoldTypeFromCarrier ::"
          + selectedOnHoldTypeFromCarrier);
      System.out.println("notes ::" + notes);
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      int result = aasHandler.onHoldAppraisalAssignment(taskID, tcn,
          selectedOnHoldTypeFromCarrier, notes, loggedInUserInfo);
      System.out.println("result=" + result);
      assertEquals(0, result);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

  public void testRemoveOnHoldAppraisalAssignment()
  {
    try {
      System.out.println("Testing AAS.testRemoveOnHoldAppraisalAssignment..");

      // SetUp Mock objects

      //appraisalAssignmentDAOMoc.expects(atLeastOnce()).method("getWorkGroupType").withAnyArguments().will(returnValue(AppraisalAssignmentConstants.SERVICE_CENTER));
      //customSettingProxyMoc.expects(atLeastOnce()).method("getDefaultProfile").withAnyArguments();
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      //setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      //claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog").withAnyArguments();
      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      //userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "RemoveOnHold");

      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));

      final long taskID = wa.getTaskID().longValue();
      final long tcn = wa.getTcn().longValue();
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      System.out.println("Inputs for AAS.dispatchAppraisalAssignment::");
      System.out.println("taskID ::" + taskID);
      System.out.println("tcn ::" + tcn);
      System.out.println("loggedInUserInfo ::" + loggedInUserInfo);

      int result = aasHandler.removeOnHoldAppraisalAssignment(taskID, tcn,
          loggedInUserInfo);
      System.out.println("result=" + result);
      assertEquals(0, result);
    } catch (Throwable e) {
      e.printStackTrace();
      fail();
    }
  }

 public void testAssignmentStatusUpdate()
  {
    try {
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));
      //setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      appLogProxyMoc
          .expects(atLeastOnce())
          .method("logAppEvent")
          .withAnyArguments()
          .will(
              returnValue(MitchellWorkflowMessageDocument.Factory.newInstance()));
      userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo")
          .withAnyArguments()
          .will(returnValue(makeMockUserInfoDoc("ME", "MITME", "")));
      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "READY");

      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));

      final long taskID = wa.getTaskID().longValue();
      final long tcn = wa.getTcn().longValue();

      String disp[] = new String[] {
          AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS,
          AppraisalAssignmentConstants.DISPOSITION_CANCELLED,
          AppraisalAssignmentConstants.DISPOSITION_REJECTED,
          AppraisalAssignmentConstants.DISPOSITION_COMPLETED };
      for (String element : disp) {
        int result = aasHandler.assignmentStatusUpdate(taskID, "IN_PROGRESS",
            "other", "comment", tcn, makeMockUserInfoDoc("ME", "MITME", ""),null);
        assertEquals(0, result);
      }
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  public void testWorkAssignmentStatusUpdate()
  {
    try {
      setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "READY");

      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));

      final long taskID = wa.getTaskID().longValue();
      final long tcn = wa.getTcn().longValue();

      int result = aasHandler.workAssignmentStatusUpdate(taskID, "IN_PROGRESS",
          "other", "comment", tcn, makeMockUserInfoDoc("ME", "MITME", ""));
      assertEquals(0, result);
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  public void testGetLatestEstimate()
  {
    try {
      String coCode = "ME";
      String claimSuffixNumber = "11-334455-2222";
      Estimate estimate[] = new Estimate[2];
      estimate[0] = new Estimate();
      estimate[0].setBusinessPartnerId(new Long(123));
      estimate[0].setCommitDate(new java.util.Date(System.currentTimeMillis()));
      estimate[1] = new Estimate();
      estimate[1].setBusinessPartnerId(new Long(345));
      estimate[1].setCommitDate(new java.util.Date(
          System.currentTimeMillis() + 500));
      
      UserInfoDocument userInfoDoc = UserInfoDocument.Factory.newInstance();
      userInfoDoc.addNewUserInfo().setOrgID("123");
    
      Mock estimatePackageProxy = mock(EstimatePackageProxy.class);
      Mock customSettingsProxy = mock(CustomSettingProxy.class);
      
      aasHandler.setEstimatePackageProxy((EstimatePackageProxy)estimatePackageProxy.proxy());
      aasHandler.setCustomSettingProxy((CustomSettingProxy)customSettingsProxy.proxy());
      
      estimatePackageProxy
      .expects(atLeastOnce())
      .method("findEstimateByClaimNumber")
      .with(eq(coCode), eq(null), eq(claimSuffixNumber))
      .will(
          returnValue(estimate));
      
      customSettingsProxy
      .expects(atLeastOnce())
      .method("getCompanyCustomSettings")
      .with(eq(coCode), eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME), eq(AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME))
      .will(
          returnValue("N"));
     
      Estimate latestEstimate =  aasHandler.getLatestEstimate("ME", "11-334455-2222",
    		  userInfoDoc);
     
      assertNotNull(latestEstimate);
      assertEquals(new Long(123), latestEstimate.getBusinessPartnerId());
      
	  Verifier.verifyObject(estimatePackageProxy);
	  Verifier.verifyObject(customSettingsProxy);

    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }
  
  
 
  
  
  

  public void testSaveLunchType()
  {
    try {
      setUpWorkAssignmentProxyMoc("LUNCH", "NEW");

      TaskDocument taskDocumentXmlObject = TaskDocument.Factory.newInstance();
      TaskType taskType = taskDocumentXmlObject.addNewTask();
      taskType.setClaimNumber("11-222-22222");
      taskType.setDisposition("New");
      taskType.setAssignmentType("LUNCH");
      taskType.setDispatchCenterName("MLCT");
      long taskID = aasHandler.saveLunchAssignmentType(taskDocumentXmlObject,
          makeMockUserInfoDoc("ME", "MITME", ""));
      assertEquals(123, taskID);

    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  //	    public void testAssignResourceSchedule() {
  //
  //	    	try{
  //			   	 appraisalAssignmentDAOMoc.expects(atLeastOnce()).method("writeAssignmentActivityLog").withAnyArguments().will(returnValue(true));
  //				 //setUpUncancelWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE");
  //				 systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue").withAnyArguments().will(returnValue("cancelling assignment"));
  //				 claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog").withAnyArguments();
  //				 appLogProxyMoc.expects(atLeastOnce()).method("logAppEvent").withAnyArguments().will(returnValue (MitchellWorkflowMessageDocument.Factory.newInstance()));
  //				 //userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
  //				 setUpWorkAssignmentProxyMoc("ORIGINAL_ESTIMATE", "READY");
  //			 	 WorkAssignment wa = ((WorkAssignmentProxy)workAssignmentProxyMoc.proxy()).getWorkAssignmentByTaskID(new Long(11));
  //
  //			 	 //userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo").with(eq("ME"), eq("MITME"), eq("")).will(returnValue( makeMockUserInfoDoc("ME", "MITME", "")));
  //			 	 userInfoProxyMoc.expects(atLeastOnce()).method("getUserInfo").with(eq("ME"), eq("AAMESA"), eq("")).will(returnValue( makeMockUserInfoDoc("ME", "AAMESA", "")));
  //			 	 userInfoProxyMoc.expects(atLeastOnce()).method("getUserDetails").withAnyArguments().will(returnValue( makeMockUserDetailDoc()));
  //			 	 userInfoProxyMoc.expects(atLeastOnce()).method("getUserTypes").withAnyArguments().will(returnValue("STAFF"));
  //				 com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
  //				 EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument= EstPkgAppraisalAssignmentDocument.Factory.newInstance();
  //				 appraisalAssignmentDTO.setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
  //				 estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment().setTCN(0);
  //				 MitchellEnvelopeDocument mitchellEnvelopeDocument = MitchellEnvelopeDocument.Factory.parse(new File(ME_ASSIGN_FILE));
  //				 System.out.println(mitchellEnvelopeDocument);
  //				 appraisalAssignmentDTO.setAppraisalAssignmentMEStr(mitchellEnvelopeDocument.toString());
  //			 	// estimatePackageProxyMoc.expects(atLeastOnce()).method("getAppraisalAssignmentDocument").withAnyArguments().will(returnValue(appraisalAssignmentDTO));
  //			 	// estimatePackageProxyMoc.expects(atLeastOnce()).method("updateAppraisalAssignmentDocument").withAnyArguments();
  //
  //			 	 final long taskID = wa.getTaskID().longValue();
  //				 final long tcn  = wa.getTcn().longValue();
  //
  //			    	ScheduleInfoXMLDocument scheduleInfoXMLDocument = ScheduleInfoXMLDocument.Factory.newInstance();
  //			    	ScheduleInfoXMLDocument.ScheduleInfoXML scheduleInfoType = scheduleInfoXMLDocument.addNewScheduleInfoXML();
  //			    	AssignTaskType assignTaskType = scheduleInfoType.addNewAssignTask();
  //			    	assignTaskType.setAppraisalAssignmentTCN(tcn);
  //			    	assignTaskType.setAssigneeId("AAMESA");
  //			    	assignTaskType.setScheduleDateTime(Calendar.getInstance());
  //			    	assignTaskType.setWorkGroupCode("MLCT");
  //			    	assignTaskType.setTaskId(taskID);
  //			    	assignTaskType.setDispatchedAfterAutoScheduleInd(true);
  //			    	int result = aasHandler.assignScheduleAppraisalAssignment(assignTaskType, makeMockUserInfoDoc("ME", "MITME", ""));
  //			    	assertEquals(0,result);
  //	    	} catch (Throwable t) {
  //	    		t.printStackTrace();
  //	    		fail();
  //	    	}
  //	    }

  private UserInfoDocument makeMockUserInfoDoc(String companyCode,
      String userName, String methodName)
  {

    UserInfoDocument uDoc = UserInfoDocument.Factory.newInstance();
    uDoc.addNewUserInfo();
    uDoc.getUserInfo().setOrgCode(companyCode);
    uDoc.getUserInfo().setUserID(userName);
    uDoc.getUserInfo().setFirstName(userName);
    uDoc.getUserInfo().setLastName(userName);
    uDoc.getUserInfo().setOrgID("1234");

    return uDoc;

  }

  public void testDispatchSupplementAppraisalAssignment()
  {
    try {
      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(3);
      MitchellEnvelopeDocument mitchellEnvelopeDocument = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));
      System.out.println(mitchellEnvelopeDocument);
      appraisalAssignmentDTO
          .setAppraisalAssignmentMEStr(mitchellEnvelopeDocument.toString());
      systemConfigProxyMoc.expects(atLeastOnce()).method("getSettingValue")
          .withAnyArguments().will(returnValue("cancelling assignment"));
      claimProxyMoc.expects(atLeastOnce()).method("writeExposureActLog")
          .withAnyArguments();
      // appLogProxyMoc.expects(atLeastOnce()).method("logAppEvent").withAnyArguments().will(returnValue (MitchellWorkflowMessageDocument.Factory.newInstance()));
      appraisalAssignmentDAOMoc.expects(atLeastOnce())
          .method("writeAssignmentActivityLog").withAnyArguments()
          .will(returnValue(true));

      setUpWorkAssignmentProxyMoc("SUPPLEMENT", "READY");
      WorkAssignment wa = ((WorkAssignmentProxy) workAssignmentProxyMoc.proxy())
          .getWorkAssignmentByTaskID(new Long(11));
      boolean returnVal = aasHandler.dispatchSupplementAppraisalAssignment(wa
          .getTaskID().longValue(), wa.getReferenceId().longValue(),
          makeMockUserInfoDoc("ME", "MITME", ""));
      assertEquals(true, returnVal);
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  private UserDetailDocument makeMockUserDetailDoc()
  {

    UserDetailDocument userDetailDocument = UserDetailDocument.Factory
        .newInstance();
    UserDetailType userDetailType = userDetailDocument.addNewUserDetail();
    AddressType addressType = userDetailType.addNewAddress();
    addressType.setAddress1("A1");
    addressType.setAddress2("A2");
    addressType.setCity("CITY");
    addressType.setCountry("COUNTRY");
    addressType.setLongitude(111.22f);
    addressType.setLatitude(111.22f);
    addressType.setState("State");
    addressType.setZip("1240");
    userDetailType.setAddress(addressType);
    userDetailType.setEmail("abc@example.com");
    userDetailType.setFax("9999999999");
    userDetailType.setPhone("8888888888");

    return userDetailDocument;
  }

  public void testManagedByOrgIdForServiceCenter()
  {
    try {
      boolean isOriginalAssignment = true;
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      long claimExposureId = 336393;
      MitchellEnvelopeHelper meHelper = null;
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));
      meHelper = new MitchellEnvelopeHelper(meDoc);
      claimProxyMoc.expects(atLeastOnce()).method("setManagedById")
          .withAnyArguments();
      long moiOrgid = aasHandler.updateManagedByIdInClaim(isOriginalAssignment,
          loggedInUserInfo, claimExposureId, meHelper);
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandlerImpl
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      assertEquals(moiOrgid, addAppAsmtInfo
          .getAdditionalAppraisalAssignmentInfo().getMOIDetails()
          .getTempUserSelectedMOI().getMOIOrgID());
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  public void testManagedByOrgIdForNonServiceCenter()
  {
    try {
      boolean isOriginalAssignment = true;
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      long claimExposureId = 336393;
      MitchellEnvelopeHelper meHelper = null;
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE_NON_SC));
      meHelper = new MitchellEnvelopeHelper(meDoc);
      claimProxyMoc.expects(atLeastOnce()).method("setManagedById")
          .withAnyArguments();
      long moiOrgid = aasHandler.updateManagedByIdInClaim(isOriginalAssignment,
          loggedInUserInfo, claimExposureId, meHelper);
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandlerImpl
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      assertEquals(moiOrgid, -1);
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  public void testManagedByOrgIdForNonAnchorTasks()
  {
    try {
      boolean isOriginalAssignment = false;
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      long claimExposureId = 336393;
      MitchellEnvelopeHelper meHelper = null;
      MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE));
      meHelper = new MitchellEnvelopeHelper(meDoc);
      long moiOrgid = aasHandler.updateManagedByIdInClaim(isOriginalAssignment,
          loggedInUserInfo, claimExposureId, meHelper);
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandlerImpl
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      assertEquals(moiOrgid, -2);
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  public void testDoAppraisalAssignmentAppLog()
  {
    try {
      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      final MitchellEnvelopeDocument mitchellEnvelopeDocument = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE_NON_SC));
      setUpGetWorkAssignmentMock();
      Mock aASAppLogUtilImplMoc = new Mock(AASAppLogUtil.class);
      aASAppLogUtilImplMoc.expects(atLeastOnce()).method("appLog")
          .withAnyArguments();
      aasHandler.setAppLogUtil((AASAppLogUtil) aASAppLogUtilImplMoc.proxy());
      aasHandler.doAppraisalAssignmentAppLog(123, 222, loggedInUserInfo,
          mitchellEnvelopeDocument);
    } catch (Throwable t) {
      t.printStackTrace();
      fail();
    }
  }

  private void setUpGetWorkAssignmentMock()
  {
    WorkAssignment wa = retrieveWorkAssignment();
    workAssignmentProxyMoc.expects(atLeastOnce())
        .method("getWorkAssignmentByTaskID").withAnyArguments()
        .will(returnValue(wa));
  }

  private WorkAssignment retrieveWorkAssignment()
  {
    WorkAssignment wa = new WorkAssignment();
    wa.setId(new Long(1));
    wa.setCompanyCode("ME");
    wa.setTaskID(new Long(11));
    wa.setTcn(new Long(1));
    wa.setReferenceId(new Long(232559));
    wa.setClaimID(new Long(527990));
    wa.setClaimExposureID(new Long(336393));
    wa.setAssignmentType(new AssignmentType());
    wa.getAssignmentType().setAssignmentTypeCode("ORIGINAL_ESTIMATE");
    wa.setAssignmentDisposition(new DispositionCode());

    wa.getAssignmentDisposition().setDispositionCode("READY");
    wa.setWorkAssignmentStatus("OPENED");
    try {
      WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(new File(WAS_FILE));
      workAssignmentDocument.getWorkAssignment().setDisposition("READY");
      workAssignmentDocument.getWorkAssignment().setStatus(
          WorkAssignmentStatusType.OPENED);

      workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
          .setAssigneeID("AAMESA");
      PersonInfoType personInfoType = workAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().addNewAssignee();
      PersonNameType personNameType = personInfoType.addNewPersonName();
      personNameType.setFirstName("AA");
      personNameType.setFirstName("BB");

      workAssignmentDocument.getWorkAssignment().setType("ORIGINAL_ESTIMATE");
      wa.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
    } catch (Throwable we) {

    }
    return wa;
  }

  public void testSubType()
  {
    try {
      final Class appraisalAssignmentServiceHandlerImpl = Class
          .forName("com.mitchell.services.business.partialloss.appraisalassignment.delegator.AppraisalAssignmentServiceHandlerImpl");

      final Method populateDTO = appraisalAssignmentServiceHandlerImpl
          .getDeclaredMethod(
              "populateDTO",
              new Class[] {
                  AppraisalAssignmentDTO.class,
                  com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO.class,
                  MitchellEnvelopeHelper.class, Long.class, Long.TYPE,
                  Long.TYPE, WorkAssignment.class, String.class, String.class,
                  UserInfoDocument.class });
      populateDTO.setAccessible(true);
      final WorkAssignment workAssignment = retrieveWorkAssignment();
      AssignmentSubType assignmentSubType = new AssignmentSubType();
      assignmentSubType.setAssignmentSubTypeCode("TL");
      workAssignment.setAssignmentSubType(assignmentSubType);
      workAssignment.setTaskPriority(new Integer(99));
      com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentEstimateDTO = new com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO();
      EstPkgAppraisalAssignmentDocument estpkgappraisalassignmentdocument = EstPkgAppraisalAssignmentDocument.Factory
          .newInstance();
      appraisalAssignmentEstimateDTO
          .setEstPkgAppAsgDoc(estpkgappraisalassignmentdocument);
      estpkgappraisalassignmentdocument.addNewEstPkgAppraisalAssignment()
          .setTCN(0);

      final UserInfoDocument loggedInUserInfo = makeMockUserInfoDoc("ME",
          "MITME", "");
      final MitchellEnvelopeDocument mitchellEnvelopeDocument = MitchellEnvelopeDocument.Factory
          .parse(new File(ME_FILE_NON_SC));
      final MitchellEnvelopeHelper mitchellEnvelopeHelper = new MitchellEnvelopeHelper(
          mitchellEnvelopeDocument);

      final WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignment.getWorkAssignmentCLOBB());
      AppraisalAssignmentDTO appraisalAssignmentDTO = (AppraisalAssignmentDTO) populateDTO
          .invoke(appraisalAssignmentServiceHandlerImpl.newInstance(),
              new Object[] { new AppraisalAssignmentDTO(),
                  appraisalAssignmentEstimateDTO, mitchellEnvelopeHelper,
                  new Long(1), new Long(1), new Long(1), workAssignment, "",
                  "", loggedInUserInfo });

      //1. Testing the values for Duration/priority/subType
      assertEquals("Duration did not match", workAssignmentDocument
          .getWorkAssignment().getScheduleConstraints().getDuration(),
          appraisalAssignmentDTO.getDuration());
      assertEquals("Priority did not match", workAssignment.gettaskPriority(),
          appraisalAssignmentDTO.getPriority());
      assertEquals("SubType did not match", workAssignment
          .getAssignmentSubType().getAssignmentSubTypeCode(),
          appraisalAssignmentDTO.getSubType());
      //2. Testing the null values for Duration/priority/subType
      workAssignment.setAssignmentSubType(null);
      workAssignment.setTaskPriority(null);
      workAssignmentDocument.getWorkAssignment().getScheduleConstraints()
          .unsetDuration();
      workAssignment.setWorkAssignmentCLOBB(workAssignmentDocument.toString());
      appraisalAssignmentDTO = (AppraisalAssignmentDTO) populateDTO.invoke(
          appraisalAssignmentServiceHandlerImpl.newInstance(), new Object[] {
              new AppraisalAssignmentDTO(), appraisalAssignmentEstimateDTO,
              mitchellEnvelopeHelper, new Long(1), new Long(1), new Long(1),
              workAssignment, "", "", loggedInUserInfo });
      assertEquals("Duration did not match", null,
          appraisalAssignmentDTO.getDuration());
      assertEquals("Priority did not match", null,
          appraisalAssignmentDTO.getPriority());
      assertEquals("SubType did not match", null,
          appraisalAssignmentDTO.getSubType());

    } catch (Exception e) {
      fail();
    }
  }

}
