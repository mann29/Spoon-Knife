package com.mitchell.services.business.questionnaireevaluation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cieca.bms.CIECADocument;
import com.google.gson.Gson;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.schemas.evaluationdetails.ResultType;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dao.QuestionnaireEvaluationDAOProxy;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDetails;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.AppLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ClaimServiceProxyImpl;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxyImpl;
import com.mitchell.services.business.questionnaireevaluation.proxy.DocStoreServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.ErrorLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.EstimatePackageServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.MSTransfromEngineProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.MSTransfromEngineProxyImpl;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxyImpl;
import com.mitchell.services.business.questionnaireevaluation.proxy.UserInfoProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.UserInfoProxyImpl;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;
import com.mitchell.services.core.documentstore.dto.GetDocResponse;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.dao.vo.Claim;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;

public class QuestionnaireEvaluationImplTest {

	protected ErrorLogProxy errorLogProxy;
	protected AppLogProxy appLogProxy;
	protected QuestionnaireEvaluationUtilsProxy qeUtilsProxy;
	protected QuestionnaireEvaluationDAOProxy evaluationDAO;
	protected ClaimServiceProxy claimServiceProxy;	
	protected CustomSettingProxy customSettingProxy;	
	protected MSTransfromEngineProxy mSTransfromEngineProxy;
	protected SystemConfigurationProxy systemConfigurationProxy;
	protected  UserInfoProxy userInfoProxy;
	QuestionnaireEvaluationImpl questionnaireEvaluationImpl = null;
	protected EstimatePackageServiceProxy estimatePackageServiceProxy=null;
	protected DocStoreServiceProxy docStoreServiceProxy = null;
	long claimID = 0;
	long suffixID = 0;
	String evaluationType = null;
	String evaluationDetailsXmlData = null;
	String evaluationDetailsOfRelatedQustnData = null;
	UserInfoDocument userInfoDoc = null;
	String workItemID = null;
	Long evaluationDocId = null;
	String companyCode = null;	
	Map miEvalResMap = null;	
	MitchellEvaluationDetailsDocument questionaireXml;
	MitchellEvaluationDetailsDocument distortedQstnnaireXml;
	MitchellEvaluationDetailsDocument evaluationDetailsOfRelatedQustn;
	
	ResultType  resultType = null;
	long docId = 0;
	long tcn = 0;
	String mitchellSuffixRqRsData;
	String inputSaveJson;
	String inputSaveJsonWithNullQustnnr;
	String inputUpdateJson;
	String InputJsonForRelatedQuestion;
	String coCd;
	String evaluationId;
	String userId;
	GetDocResponse getDocResponse;
	long qustnnrId = 100000006423L;
	Gson gson = new Gson();
	@Before
	public void setUp() throws Exception{
		
		questionnaireEvaluationImpl = new QuestionnaireEvaluationImpl();
		errorLogProxy = mock(ErrorLogProxy.class);
		appLogProxy = mock(AppLogProxy.class);
		qeUtilsProxy = mock(QuestionnaireEvaluationUtilsProxy.class);
		evaluationDAO = mock(QuestionnaireEvaluationDAOProxy.class);
		claimServiceProxy=mock(ClaimServiceProxyImpl.class);
		customSettingProxy=mock(CustomSettingProxyImpl.class);
		mSTransfromEngineProxy=mock(MSTransfromEngineProxyImpl.class);
		systemConfigurationProxy=mock(SystemConfigurationProxyImpl.class);
		userInfoProxy=mock(UserInfoProxyImpl.class);
		estimatePackageServiceProxy = mock(EstimatePackageServiceProxy.class);
		docStoreServiceProxy = mock(DocStoreServiceProxy.class);
		claimID =  726985;
		suffixID = 530276;
		evaluationType = "LOSSEVALUATION";
		questionaireXml = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXml.xml"));
		distortedQstnnaireXml = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("DistortedEvaluationXml.xml"));
		evaluationDetailsOfRelatedQustn = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlOfRelatedQustn.xml"));
		evaluationDetailsOfRelatedQustnData = evaluationDetailsOfRelatedQustn.toString();
		docId = 768687;
		tcn = 76789797;
		evaluationDetailsXmlData = questionaireXml.toString();
		userInfoDoc =   mockUserInfoDoc("IF","PCS01");
		companyCode = userInfoDoc.getUserInfo().getOrgCode();
		workItemID = "testing0001";
		miEvalResMap = mockResponseMap();
		resultType = mockResultType();
		inputSaveJson = readFileFromClassPath("InputSaveJson.txt");
		inputUpdateJson = readFileFromClassPath("InputUpdateJson.txt");
		inputSaveJsonWithNullQustnnr=readFileFromClassPath("InputSaveJsonWithNullQustnnr.txt");
		InputJsonForRelatedQuestion =readFileFromClassPath("InputJsonForRelatedQuestion.txt"); 
		coCd="OA";
		evaluationId= "271120152";
		userId = "RMADMIN";
		getDocResponse= getGetDocResponse();
		
	}
	
	/**
	 * 
	 */
	private void initialMock()
	{
		
		try
		{
		when(errorLogProxy.logError(any(MitchellException.class))).thenReturn("SUCCESS");		
		doNothing().when(qeUtilsProxy).logINFOMessage(anyString());
		doNothing().when(qeUtilsProxy).logFINEMessage(anyString());
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		
		when(evaluationDAO.saveEvaluationWithClaim(any(QuestionnaireEvaluationContext.class), any(MitchellEvaluationDetailsDocument.class))).thenReturn(docId);
		doNothing().when(evaluationDAO).updateCustomEvaluationFields(miEvalResMap, companyCode, resultType);
		questionnaireEvaluationImpl.setErrorLogProxy(errorLogProxy);
		questionnaireEvaluationImpl.setQeUtilsProxy(qeUtilsProxy);
		questionnaireEvaluationImpl.setEvaluationDAO(evaluationDAO);			
		questionnaireEvaluationImpl.setClaimServiceProxy(claimServiceProxy);
		questionnaireEvaluationImpl.setmSTransfromEngineProxy(mSTransfromEngineProxy);
		questionnaireEvaluationImpl.setSystemConfigProxy(systemConfigurationProxy);
		questionnaireEvaluationImpl.setCustomSettingProxy(customSettingProxy);
		questionnaireEvaluationImpl.setUserInfoProxyImpl(userInfoProxy);
		questionnaireEvaluationImpl.setDocStoreServiceProxy(docStoreServiceProxy);
		questionnaireEvaluationImpl.setEstimatePackageServiceProxy(estimatePackageServiceProxy);
		when(evaluationDAO.linkQuestionnaireEvaluationToClaim(any(QuestionnaireEvaluationContext.class))).thenReturn(1);;
		when(claimServiceProxy.getSimpleClaimInfoByFullClaimNumber(
						any(UserInfoDocument.class), anyString(),
						anyString())).thenReturn(getClaimInfoDTO(true));
		when(claimServiceProxy.saveClaimFromAssignmentBms(any(BmsClmInputDTO.class))).thenReturn(createMcfClmOutDTO());
		when(mSTransfromEngineProxy.getTransFormData(any(MitchellEnvelopeDocument.class))).thenReturn(getCiecaDoc());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn("111");
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfo());
		
		
		}
		catch(MitchellException Exception)
		{
			
		}
		
	}

	@After
	public void tearDown() throws Exception{
		
		errorLogProxy = null;
		appLogProxy = null;
		qeUtilsProxy = null;
		evaluationDAO = null;
		questionaireXml = null;
		claimID = 0;
		suffixID = 0;
		evaluationType = null;
		evaluationDetailsXmlData = null;
		userInfoDoc = null;
		workItemID = null;
		evaluationDocId = null;
		companyCode = null;
		miEvalResMap = null;
		resultType = null;
		docId = 0;
		tcn = 0;
	}
	
	
	@Test
	public void testSaveEvaluationAndLinkQEToClaim()
	{		
		String clientClaimNumber="000-00-000011";
		String evaluationId="Naz1000";
		evaluationType="LOSSEVALUATION";
		try {
			mitchellSuffixRqRsData=readFileFromClassPath("MSuffixSvcRqRsDoc.xml");
			initialMock();
			questionnaireEvaluationImpl.saveEvaluationAndLinkQEToClaim(clientClaimNumber, evaluationId, evaluationType, mitchellSuffixRqRsData, evaluationDetailsXmlData, userInfoDoc, "c989bc7a-4c86-463a-850e-01203152753b");
			verify(userInfoProxy,times(1)).getUserInfo(anyString(), anyString());
			
		} 
		catch (MitchellException e) {
		fail("Got Mitchell Exception");	
		} 
		
	}
	
	@Test
	public void testSaveEvaluationAndLinkQEToClaimWithDefaulAdjuster()
	{
		
		String clientClaimNumber="000-00-000011";
		String evaluationId="Naz1000";
		evaluationType="LOSSEVALUATION";
		try {
			mitchellSuffixRqRsData=readFileFromClassPath("MSuffixSvcRqRsDocWithoutAdjuster.xml");
			initialMock();
			questionnaireEvaluationImpl.saveEvaluationAndLinkQEToClaim(clientClaimNumber, evaluationId, evaluationType, mitchellSuffixRqRsData, evaluationDetailsXmlData, userInfoDoc, "c989bc7a-4c86-463a-850e-01203152753b");
			verify(userInfoProxy,times(1)).getUserInfo(anyString(), anyString());
		} 
		
		catch (MitchellException e) {			
			fail("Got Mitchell Exception");	
		}	
			
	}
	
	
	
	
	
	@Test
	public void testSaveEvaluationAndLinkQEToClaim_DefaulAdjuster_Exception()
	{
		
		String clientClaimNumber="000-00-000011";
		String evaluationId="Naz1000";
		evaluationType="LOSSEVALUATION";
		try {
			mitchellSuffixRqRsData=readFileFromClassPath("MSuffixSvcRqRsDocWithoutAdjuster.xml");
			initialMock();
			when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(null);
			questionnaireEvaluationImpl.saveEvaluationAndLinkQEToClaim(clientClaimNumber, evaluationId, evaluationType, mitchellSuffixRqRsData, evaluationDetailsXmlData, userInfoDoc, "c989bc7a-4c86-463a-850e-01203152753b");
		} 
		catch (MitchellException mitExcep) {			
			assertEquals(mitExcep.getType(), 157634);
		}				
	}
	
	private McfClmOutDTO createMcfClmOutDTO() {
		McfClmOutDTO mcfClmOutDTO = new McfClmOutDTO();
		mcfClmOutDTO.setClaimID(121L);
		Claim claim = new Claim();
		claim.setClaimNumber("NAZ-000-00136");
		mcfClmOutDTO.setClaim(claim);
		mcfClmOutDTO.setExposureID(1111L);

		return mcfClmOutDTO;
	}
	
	private UserInfoDocument createUserInfo() {
		UserInfoDocument userInfoDoc=UserInfoDocument.Factory.newInstance();
		UserInfoType  userInfoType= userInfoDoc.addNewUserInfo();
		userInfoType.addAppCode("IF");
		return userInfoDoc;
		
	}
	
	private UserInfoDocument createUserInfoDoc() {
		UserInfoDocument userInfoDoc=UserInfoDocument.Factory.newInstance();
		UserInfoType  userInfoType= userInfoDoc.addNewUserInfo();
		userInfoType.addAppCode("OA");
		return userInfoDoc;
	
	}

	public CIECADocument getCiecaDoc()
	{
		 try {
			return CIECADocument.Factory.parse(readFileFromClassPath("CiecaDoc.xml"));
		} catch (XmlException e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	
	@Test
	public void testSaveEvaluation() throws Exception {
		
        //Simulate a method
		when(errorLogProxy.logError(any(MitchellException.class))).thenReturn("SUCCESS");		
		doNothing().when(qeUtilsProxy).logINFOMessage(anyString());
		doNothing().when(qeUtilsProxy).logFINEMessage(anyString());
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		
		when(evaluationDAO.saveEvaluationWithClaim(any(QuestionnaireEvaluationContext.class), any(MitchellEvaluationDetailsDocument.class))).thenReturn(docId);
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.IS_QUESTIONNAIRE_REPORTING_ALLOWED)).thenReturn("Y");
		doNothing().when(evaluationDAO).updateCustomEvaluationFields(miEvalResMap, companyCode, resultType);
		
		questionnaireEvaluationImpl.setErrorLogProxy(errorLogProxy);
		questionnaireEvaluationImpl.setQeUtilsProxy(qeUtilsProxy);
		questionnaireEvaluationImpl.setEvaluationDAO(evaluationDAO);
		questionnaireEvaluationImpl.setSystemConfigProxy(systemConfigurationProxy);
		
		Map responseMap = questionnaireEvaluationImpl.saveEvaluation(claimID, suffixID, evaluationType, evaluationDetailsXmlData, userInfoDoc, workItemID);
		assertNotNull(responseMap.get(QuestionnaireEvaluationConstants.RESPONSE_KEY_DOCUMENTID));
		verify(evaluationDAO,times(1)).saveEvaluationWithClaim(any(QuestionnaireEvaluationContext.class), any(MitchellEvaluationDetailsDocument.class));
		verify(evaluationDAO,times(1)).updateCustomEvaluationFields(anyMap(), anyString(), any(ResultType.class));
	}
	
	
	private QuestionnaireRqRsDTO createQuestionnaireRqRsDTO(){
		String questionnaireRqRsDtoDetails = readFileFromClassPath("QustnQustnnrRqRsDtoDetails");
		QuestionnaireRqRsDTO questionnaireRqRsDTO = gson.fromJson(questionnaireRqRsDtoDetails, QuestionnaireRqRsDTO.class);
		return questionnaireRqRsDTO;
	}

	private QuestionnaireDetails createQuestionnaireDetails(){
		QuestionnaireDetails questionnaireDetails = new QuestionnaireDetails();
		questionnaireDetails.setCoCd("M2");
		questionnaireDetails.setQustnnreId(qustnnrId);
		questionnaireDetails.setQustnnreName("TEST");
		questionnaireDetails.setQustnnreVersion(01);
		questionnaireDetails.setScoringType("PERCENTAGE");
		return questionnaireDetails;
	}
	
	private Question getdbQuestion(){
		Question question = new Question(); 
		question.setQustnText("Was the driver's seat occupied at the time of the incident?");
		question.setQustnnreQustnId(100000113452L);
		question.setAnswrControlType("YES_NO_VALUE");
		List<Answer> ansList = new ArrayList<Answer>();
		ansList.add(createDbAnswer());
		question.setAnswerList(ansList);
		return question;
	}
	
	private Answer createDbAnswer(){
		Answer answer = new Answer();
		answer.setAnswerDisplayText("Yes");
		answer.setAnswerItemID(100000125941L);
		return answer;
	}
	
	
	
	
	
	@Test
	public void testSaveOrUpdateQtnnreEvaluationForSave() throws MitchellException{

		initialMock();
		
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		requestDto.getContextDto().setDocumentId(0L);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		assertNotNull(responseDto);
		
	}
	
	
	
	@Test
	public void testSaveOrUpdateQtnnreEvaluationForSaveWithPOINTScoringType() throws MitchellException{

		initialMock();
		
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		requestDto.getContextDto().setDocumentId(0L);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		QuestionnaireDetails.setScoringType("POINT");
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		assertNotNull(responseDto);
		
	}
	@Test
	public void testSaveOrUpdateQtnnreEvaluationForSaveWithNONEScoringType() throws MitchellException{

		initialMock();
		
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		requestDto.getContextDto().setDocumentId(0L);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		QuestionnaireDetails.setScoringType("NONE");
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		assertNotNull(responseDto);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected =MitchellException.class)
	public void testSaveOrUpdateQtnnreEvaluationForSaveThrowsException() throws MitchellException{

		initialMock();
		
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		requestDto.getContextDto().setDocumentId(0L);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenThrow(Exception.class);
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		assertNotNull(responseDto);
		
	}
	
	@Test
	public void testSaveOrUpdateQtnnreEvaluationUpdateParentQustn() throws MitchellException, IOException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
		when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
		when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
		verify(docStoreServiceProxy).getDocument(anyLong());
		verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
		assertNotNull(responseDto);
		
	
	}
	
	@Test(expected=MitchellException.class)
	public void testSaveOrUpdateQtnnreEvaluationUpdateThrowsException() throws MitchellException, IOException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =null;
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
		when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
		when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
		verify(docStoreServiceProxy).getDocument(anyLong());
		verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
		assertNotNull(responseDto);
		
	
	}
	@Test
	public void testSaveOrUpdateQtnnreEvaluationUpdateRelatedQustn() throws MitchellException, IOException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =getdbQuestion();
		dbQuestion.setAncstrQustnnrQustnId(100000108995L);
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
		when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
		when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
		verify(docStoreServiceProxy).getDocument(anyLong());
		verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
		
		assertNotNull(responseDto);
		
	
	}
	
		@Test
	public void testSaveOrUpdtQtnnreEvalParentQustnNtExst() throws MitchellException, IOException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =getdbQuestion();
		dbQuestion.setAncstrQustnnrQustnId(100000108997L);
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
		when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
		when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
		
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
		verify(docStoreServiceProxy).getDocument(anyLong());
		verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
		
		assertNotNull(responseDto);
		
	
	}
	
		@Test
		public void testSaveOrUpdtQtnnreEvalForSystemQuestion() throws MitchellException, IOException{

			initialMock();
			QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
			QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
			Question dbQuestion =getdbQuestion();
			dbQuestion.setAncstrQustnnrQustnId(100000108995L);
			dbQuestion.setSysQustnType("TypeOfLoss");
			Answer dbAnswer = createDbAnswer();
			
			when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
			when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
			when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
			doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
			when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
			
			
			QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
			
			verify(qeUtilsProxy).generateEvaluationID();
			verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
			verify(userInfoProxy).getUserInfo(anyString(), anyString());
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(responseDto);
			
		
		}
		
		@Test
		public void testSaveOrUpdtQtnnreEvalForNoAnsSelected() throws MitchellException, IOException{

			initialMock();
			QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
			QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
			Question dbQuestion =getdbQuestion();
			dbQuestion.setAncstrQustnnrQustnId(100000108995L);
			dbQuestion.setSysQustnType("TypeOfLoss");
			Answer dbAnswer = null;
			
			when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
			when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
			when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
			doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
			when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
			
			
			QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
			
			verify(qeUtilsProxy).generateEvaluationID();
			verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
			verify(userInfoProxy).getUserInfo(anyString(), anyString());
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(responseDto);
			
		
		}
		
		@Test
		public void testSaveOrUpdtQtnnreEvalForNoAnsText() throws MitchellException, IOException{

			initialMock();
			QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
			QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
			Question dbQuestion =getdbQuestion();
			dbQuestion.setAncstrQustnnrQustnId(100000108995L);
			dbQuestion.setSysQustnType("TypeOfLoss");
			Answer dbAnswer = createDbAnswer();
			dbAnswer.setAnswerDisplayText(null);
			when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
			when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
			when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
			doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
			when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
			
			
			QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
			
			verify(qeUtilsProxy).generateEvaluationID();
			verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
			verify(userInfoProxy).getUserInfo(anyString(), anyString());
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(responseDto);
			
		
		}
		
		
		@Test
		public void testGetEvaluationDocFromNAS() throws MitchellException, IOException{

			initialMock();
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(evaluationDetailsXmlData);
			
			
			MitchellEvaluationDetailsDocument evalDoc = questionnaireEvaluationImpl.getEvaluationDocFromNAS(Long.parseLong(evaluationId));
			
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(evalDoc);
			
		
		}
		
		@Test(expected=MitchellException.class)
		public void testGetEvaluationDocFromNASEvalDocIsNull() throws MitchellException, IOException{

			initialMock();
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(null);
			
			
			MitchellEvaluationDetailsDocument evalDoc = questionnaireEvaluationImpl.getEvaluationDocFromNAS(Long.parseLong(evaluationId));
			
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(evalDoc);
			
		
		}
		
		@Test(expected=MitchellException.class)
		public void testGetEvaluationDocFromNASthrowsIOException() throws MitchellException, IOException{

			initialMock();
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenThrow(IOException.class);
			
			
			MitchellEvaluationDetailsDocument evalDoc = questionnaireEvaluationImpl.getEvaluationDocFromNAS(Long.parseLong(evaluationId));
			
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			//assertNotNull(evalDoc);
			
		
		}
		
		@Test(expected=MitchellException.class)
		public void testGetEvaluationDocFromNASthrowsXMLException() throws MitchellException, IOException{

			initialMock();
			when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
			when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
			when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn("XMLExceptionTest");
			
			
			MitchellEvaluationDetailsDocument evalDoc = questionnaireEvaluationImpl.getEvaluationDocFromNAS(Long.parseLong(evaluationId));
			
			verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
			verify(docStoreServiceProxy).getDocument(anyLong());
			verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
			
			assertNotNull(evalDoc);
			
		
		}
		
	@Test(expected=MitchellException.class)
	public void testSaveOrUpdateQtnnreEvaluationUpdateParentQustnEvaluationDocNotExist() throws MitchellException, IOException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		when(systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_EVALUATION_TYPE)).thenReturn("LOSSEVALUATION");
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		when(userInfoProxy.getUserInfo(anyString(), anyString())).thenReturn(createUserInfoDoc());
		when(estimatePackageServiceProxy.getDocumentStoreIdByDocId(anyLong(), anyString())).thenReturn(anyLong());
		when(docStoreServiceProxy.getDocument(123L)).thenReturn(getDocResponse);
		when(evaluationDAO.getEvaluationDocumentOnNAS(getDocResponse.getfilenameondisk())).thenReturn(null);
		
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		verify(qeUtilsProxy).generateEvaluationID();
		verify(customSettingProxy).getCustomValue(anyString(), anyString(), anyString(), anyString());
		verify(userInfoProxy).getUserInfo(anyString(), anyString());
		verify(evaluationDAO).getEvaluationDocumentOnNAS(anyString());
		verify(docStoreServiceProxy).getDocument(anyLong());
		verify(estimatePackageServiceProxy).getDocumentStoreIdByDocId(anyLong(), anyString());
		assertNotNull(responseDto);
		
	
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=MitchellException.class)
	
	public void testSaveOrUpdateQtnnreEvaluationForMitcellException() throws MitchellException{

		initialMock();
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =getdbQuestion();
		Answer dbAnswer = createDbAnswer();
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenThrow(MitchellException.class);
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		
		assertNotNull(responseDto);
		
	}
	
	@Test(expected=Exception.class)
	public void testSaveOrUpdateQtnnreEvaluationForException() throws MitchellException{

		initialMock();
		
		QuestionnaireRqRsDTO requestDto = gson.fromJson(readFileFromClassPath("QustnQustnnrRqRsDtoDetails"), QuestionnaireRqRsDTO.class);
		QuestionnaireDetails QuestionnaireDetails = createQuestionnaireDetails();
		Question dbQuestion =null;
		Answer dbAnswer = createDbAnswer();
		when(qeUtilsProxy.jsonToDto(inputSaveJson)).thenReturn(requestDto);
		
		when(qeUtilsProxy.generateEvaluationID()).thenReturn(evaluationId);
		when(customSettingProxy.getCustomValue(anyString(), anyString(), anyString(), anyString())).thenReturn(anyString());
		
		QuestionnaireRqRsDTO responseDto = questionnaireEvaluationImpl.saveOrUpdateQtnnreEvaluation(requestDto,QuestionnaireDetails,dbQuestion,dbAnswer);
		
		assertNotNull(responseDto);
		
	}
	
	private GetDocResponse getGetDocResponse(){
		GetDocResponse getDocResponse = new GetDocResponse();
		getDocResponse.setcompany("OA");
		getDocResponse.setfilenameondisk("TEST");
		return getDocResponse;
		
	}
	
	
	
	
	@Test
	public void testUpdateEvaluation() throws Exception {
		when(errorLogProxy.logError(any(MitchellException.class))).thenReturn("SUCCESS");		
		doNothing().when(qeUtilsProxy).logINFOMessage(anyString());
		doNothing().when(qeUtilsProxy).logFINEMessage(anyString());
		doNothing().when(qeUtilsProxy).logAppEvent(anyMap(), any(UserInfoDocument.class), anyString());
		
		doNothing().when(evaluationDAO).updateEvaluation(any(QuestionnaireEvaluationContext.class), any(MitchellEvaluationDetailsDocument.class));
		doNothing().when(evaluationDAO).updateCustomEvaluationFields(miEvalResMap, companyCode, resultType);
		
		questionnaireEvaluationImpl.setErrorLogProxy(errorLogProxy);
		questionnaireEvaluationImpl.setQeUtilsProxy(qeUtilsProxy);
		questionnaireEvaluationImpl.setEvaluationDAO(evaluationDAO);
		
		Map responseMap = questionnaireEvaluationImpl.updateEvaluation(docId,evaluationType, evaluationDetailsXmlData, userInfoDoc,claimID, suffixID, workItemID, tcn);
		assertNotNull(responseMap);
		verify(evaluationDAO,times(1)).updateEvaluation(any(QuestionnaireEvaluationContext.class), any(MitchellEvaluationDetailsDocument.class));
		verify(evaluationDAO,times(1)).updateCustomEvaluationFields(anyMap(), anyString(), any(ResultType.class));
	}
	
	private UserInfoDocument mockUserInfoDoc(String companyCode, String userName){
		UserInfoDocument uDoc = UserInfoDocument.Factory.newInstance();
        uDoc.addNewUserInfo();
        uDoc.getUserInfo().setOrgCode(companyCode);
        uDoc.getUserInfo().setUserID(userName);
        uDoc.getUserInfo().setFirstName(userName);
        uDoc.getUserInfo().setLastName(userName);
        uDoc.getUserInfo().setOrgID("1234");
        return uDoc;
	
	}
	
	private Map<String, Object> mockResponseMap(){
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put(QuestionnaireEvaluationConstants.CLAIM_ID, claimID); 
		responseMap.put(QuestionnaireEvaluationConstants.SUFFIX_ID,suffixID);
		responseMap.put(QuestionnaireEvaluationConstants.WORKITEM_ID, workItemID);
		responseMap.put(QuestionnaireEvaluationConstants.DOCUMENT_ID, docId);
		responseMap.put(QuestionnaireEvaluationConstants.USER_ID,userInfoDoc.getUserInfo().getUserID());
		responseMap.put(QuestionnaireEvaluationConstants.CLAIM_NUMBER,questionaireXml.getMitchellEvaluationDetails().getEvaluationInfo().getClaimNumber());
		responseMap.put(QuestionnaireEvaluationConstants.RESPONSE_KEY_VERSION,questionaireXml.getMitchellEvaluationDetails().getQuestionnaireArray(0).getEvaluationVersion().toString());
		return responseMap;
		
	}
	
	private ResultType mockResultType() {
		ResultType resultType =questionaireXml.getMitchellEvaluationDetails().getQuestionnaireArray(0).getResult();
		//resultType.setEvaluationScoreOverride(null);
		//resultType.setEvaluationScoreOverrideComments("Test");
		return resultType;
	}
	
	private String readFileFromClassPath(String fileName) {

		java.io.InputStream inputStream = null;
		String content = null;
		try {
			inputStream = this.getClass().getClassLoader()
					.getResourceAsStream(fileName);

			content = readFileIntoString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			close(inputStream);
			
		}
		return content;

	}
	
	private String readFileIntoString(java.io.InputStream inputStream)
			throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));

		StringBuffer strMsg = new StringBuffer();

		String line;
		while ((line = br.readLine()) != null) {
			strMsg.append(line);
		}

		return strMsg.toString();
	}
	
	
	
	private void close(java.io.InputStream inputStream) {
		try {
			inputStream.close();
		} catch (Exception e) {
			// do nothing
		}

	}
	
	private ClaimInfoDTO getClaimInfoDTO(boolean exist) {
		ClaimInfoDTO claimInfoDTO = null;
		if (!exist) {
			claimInfoDTO = new ClaimInfoDTO();
			claimInfoDTO.setClaimId(100002052037L);
			claimInfoDTO.setClaimExposureId(1111L);

		}

		return claimInfoDTO;

	}
	
	 public QuestionnaireRqRsDTO jsonToDto(String inputJson){
			Gson gson = new Gson();
	      return gson.fromJson(inputJson, QuestionnaireRqRsDTO.class);
		}
	
	
}
