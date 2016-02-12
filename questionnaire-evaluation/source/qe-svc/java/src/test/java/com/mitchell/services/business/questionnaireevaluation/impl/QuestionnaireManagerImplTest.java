package com.mitchell.services.business.questionnaireevaluation.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.dao.QuestionnaireEvaluationDAOProxy;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.AnswerRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.Node;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnairInfo;
import com.mitchell.services.business.questionnaireevaluation.dto.Questionnaire;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireDetails;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireQuestionDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireTree;
import com.mitchell.services.business.questionnaireevaluation.dto.QustnnrQustnRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.proxy.CachingServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;

public class QuestionnaireManagerImplTest {

	QuestionnaireManagerImpl questionnaireManagerImpl = null;
	protected CustomSettingProxy customSettingProxy;
	protected CachingServiceProxy cachingServiceProxy;
	protected QuestionnaireEvaluationDAOProxy evaluationDAO;
	long qustnnrId =100000005875L;
	String coCd ="M2";
	String methodName = "getQuestionnairInfo";
	Object[] params = new Object[1];
	String cacheKey = "123456";
	QuestionnaireTree qstnrreTree = new QuestionnaireTree();
	Gson gson = new Gson();
	private SystemConfigurationProxy systemConfigurationProxy;
	
	@Before
	public void setUp() throws Exception{
		
		params[0] = qustnnrId;
		
		questionnaireManagerImpl = new QuestionnaireManagerImpl();
		evaluationDAO = mock(QuestionnaireEvaluationDAOProxy.class);
		customSettingProxy=mock(CustomSettingProxy.class);
		cachingServiceProxy = mock(CachingServiceProxy.class);
		systemConfigurationProxy = mock(SystemConfigurationProxy.class);
		questionnaireManagerImpl.setEvaluationDAO(evaluationDAO);
		questionnaireManagerImpl.setCustomSettingProxy(customSettingProxy);
		questionnaireManagerImpl.setCachingServiceProxy(cachingServiceProxy);
		questionnaireManagerImpl.setSystemConfigurationProxy(systemConfigurationProxy);
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
	
	private QuestionnaireRqRsDTO createRequestDto(){
		String questionnaireRqRsDtoDetails = readFileFromClassPath("QustnQustnnrRqRsDtoDetails");
		QuestionnaireRqRsDTO questionnaireRqRsDTO = gson.fromJson(questionnaireRqRsDtoDetails, QuestionnaireRqRsDTO.class);
		return questionnaireRqRsDTO;
	}
	
	
	
	protected ContextDTO creteContextDto(){
		ContextDTO contextDto = new ContextDTO();
		contextDto.setCoCode(coCd);
		return contextDto;
	}
	
	protected Questionnaire createQuestionnaire(){
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setCoCd(coCd);
		questionnaire.setScoringType("PERCENTAGE");
		questionnaire.setQustnnreId(qustnnrId);
		questionnaire.setQustnnreName("MFNOL");
		questionnaire.setQustnnreVersion(007);
		questionnaire.setQustnnreDesc("MFNOL");
		questionnaire.setEvltnCategory("LOSSEVALUATION");
		questionnaire.setB6(84);
		
		return questionnaire;
	}
	
	
	protected QuestionnaireQuestionDTO createQuestionnaireQuestionDTO(){
		QuestionnaireQuestionDTO questionnaireQuestionDTO = new QuestionnaireQuestionDTO();
		questionnaireQuestionDTO.setCoCd(coCd);
		questionnaireQuestionDTO.setScoringType("PERCENTAGE");
		questionnaireQuestionDTO.setQustnnreId(qustnnrId);
		questionnaireQuestionDTO.setQustnnreName("MFNOL");
		questionnaireQuestionDTO.setQustnnreVersion(007);
		questionnaireQuestionDTO.setQustnnreDesc("MFNOL");
		questionnaireQuestionDTO.setEvltnCategory("LOSSEVALUATION");
		questionnaireQuestionDTO.setB6(84);
		ArrayList<Question> questionList = new ArrayList<Question> ();
		questionList.add(createRootQuestion());
		questionList.add(createChildQuestion());
		questionnaireQuestionDTO.setQuestionsList(questionList);
		return questionnaireQuestionDTO;
	}
	
	protected Question createRootQuestion(){
		Question rootQuestion = new Question();
		rootQuestion.setAncstrAnswerItemId(0L);
		rootQuestion.setIsLeaf("F");
		rootQuestion.setAncstrQustnnrQustnId(0L);
		rootQuestion.setQustnnreQustnId(100000097403L);
		rootQuestion.setQustnId(100000012603L);
		rootQuestion.setSiblingOrder(1);
		rootQuestion.setLevelNumber(1);
		
		ArrayList<Answer> answer = new ArrayList<Answer> ();
		
		Answer ans1 = new Answer();
		ans1.setQustnnerQustnId(100000097403L);
		ans1.setAnswerItemID(100000113159L);
		ans1.setIsLeaf("F");
		
		Answer ans2 = new Answer();
		ans2.setQustnnerQustnId(100000097403L);
		ans2.setAnswerItemID(100000113160L);
		ans2.setIsLeaf("T");
		answer.add(ans1);
		answer.add(ans2);
		rootQuestion.setAnswerList(answer);
		return rootQuestion;
		
	}
	protected Question createChildQuestion(){
		Question childQuestion = new Question();
		childQuestion.setAncstrAnswerItemId(100000113159L);
		childQuestion.setIsLeaf("F");
		childQuestion.setAncstrQustnnrQustnId(100000097403L);
		childQuestion.setQustnnreQustnId(100000097404L);
		childQuestion.setQustnId(100000012604L);
		childQuestion.setSiblingOrder(1);
		childQuestion.setLevelNumber(2);
		
		ArrayList<Answer> answer = new ArrayList<Answer> ();
		
		Answer ans1 = new Answer();
		ans1.setQustnnerQustnId(100000097404L);
		ans1.setAnswerItemID(100000113168L);
		ans1.setIsLeaf("F");
		
		Answer ans2 = new Answer();
		ans2.setQustnnerQustnId(100000097404L);
		ans2.setAnswerItemID(100000113169L);
		ans2.setAnswerDisplayText("Rear End");
		ans2.setIsLeaf("F");
		answer.add(ans1);
		answer.add(ans2);	
		childQuestion.setAnswerList(answer);
		return childQuestion;
		
	}
	
	
		
	protected ArrayList<Answer>	createAnswerList(){
	ArrayList<Answer> answer = new ArrayList<Answer> ();
	
	Answer ans1 = new Answer();
	ans1.setQustnnerQustnId(100000097404L);
	ans1.setAnswerItemID(100000113159L);
	ans1.setAnswerDisplayText("Rear End");
	ans1.setIsLeaf("F");
	
	Answer ans2 = new Answer();
	ans2.setQustnnerQustnId(100000097404L);
	ans2.setAnswerItemID(100000113160L);
	ans2.setIsLeaf("T");
	ans2.setAnswerDisplayText("back End");
	Answer ans3 = new Answer();
	ans3.setQustnnerQustnId(100000097404L);
	ans3.setAnswerItemID(100000113159L);
	ans3.setIsLeaf("F");
	ans3.setAnswerDisplayText("front End");
	Answer ans4 = new Answer();
	ans4.setQustnnerQustnId(100000097404L);
	ans4.setAnswerItemID(100000113160L);
	ans4.setIsLeaf("T");
	ans4.setAnswerDisplayText("right End");
	answer.add(ans1);
	answer.add(ans2);
	answer.add(ans3);
	answer.add(ans4);
	return answer;
	
	}
	
	protected QuestionnaireDTO createDbDto(){
		QuestionnaireDTO dbDto = new QuestionnaireDTO();
		dbDto.setAnswers(createAnswerList());
		dbDto.setQuestionnaire(createQuestionnaire());
		ArrayList<Question> questionList = new ArrayList<Question> ();
		questionList.add(createRootQuestion());
		questionList.add(createChildQuestion());
		dbDto.setQuestions(questionList);
		return dbDto;
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
	
	private Question createRootNode() {
		Question question = new Question();
		question.setQustnnreQustnId(0L);
		return question;
	}
	
	private QuestionnairInfo getQuestionnairInfo(){
		QuestionnairInfo questionnairInfo = new QuestionnairInfo();
		QuestionnaireDetails questionnaireDetails = createQuestionnaireDetails();
		
		Node<Question> rootElement = new Node<Question>();
		rootElement.setData(createRootNode());
		Node<Question> children = new Node<Question>();
		children.setData(createChildQuestion());
		List<Node<Question>> childList = new ArrayList<Node<Question> >();
		childList.add(children);
		rootElement.setChildren(childList);
		
		
		questionnairInfo.setQuestionnaireTree(qstnrreTree);
		questionnairInfo.setQuestionnaireDetails(questionnaireDetails);
		return questionnairInfo;
		
	}
	
	@Test 
	public void testGetFirstQuestion() throws MitchellException{
		QuestionnairInfo questionnairInfo = getQuestionnairInfo() ;
		QustnnrQustnRqRsDto qustnnrQustnRqRsDto =questionnaireManagerImpl.getFirstQuestion(questionnairInfo);
		assertNotNull(qustnnrQustnRqRsDto);
	}
	
	@Test 
	public void testGetFirstQuestionWithQuestionnaireTree() throws MitchellException{
		QuestionnairInfo questionnairInfo = getQuestionnairInfo() ;
		Node<Question> rootElement = new Node<Question>();
		rootElement.setData(createRootNode());
		Question question = createChildQuestion();
		question.setSiblingOrder(1);
		question.setLevelNumber(1);
		Node<Question> children = new Node<Question>();
		children.setData(question);
		
		List<Node<Question>> childList = new ArrayList<Node<Question> >();
		childList.add(children);
		rootElement.setChildren(childList);
		qstnrreTree.setRootElement(rootElement);
		questionnairInfo.setQuestionnaireTree(qstnrreTree);
		QustnnrQustnRqRsDto qustnnrQustnRqRsDto =questionnaireManagerImpl.getFirstQuestion(questionnairInfo);
		assertNotNull(qustnnrQustnRqRsDto);
	}
	
	
	
	@Test 
	public void testGetFirstQuestionWithQuestionnaireTreeWithQustnFormattedText() throws MitchellException{
		QuestionnairInfo questionnairInfo = getQuestionnairInfo() ;
		Node<Question> rootElement = new Node<Question>();
		rootElement.setData(createRootNode());
		Question question = createChildQuestion();
		question.setSiblingOrder(1);
		question.setLevelNumber(1);
		question.setQustnFormattedText(QuestionnaireEvaluationConstants.FORMATTED_TEXT);
		ArrayList<Answer> answer = new ArrayList<Answer> ();
		question.setAnswerList(answer);
		Node<Question> children = new Node<Question>();
		children.setData(question);
		List<Node<Question>> childList = new ArrayList<Node<Question> >();
		childList.add(children);
		rootElement.setChildren(childList);
		qstnrreTree.setRootElement(rootElement);
		questionnairInfo.setQuestionnaireTree(qstnrreTree);
		QustnnrQustnRqRsDto qustnnrQustnRqRsDto =questionnaireManagerImpl.getFirstQuestion(questionnairInfo);
		assertNotNull(qustnnrQustnRqRsDto);
	}
	@Test
	public void testGetQuestionnairInfoWhenExstInCache() throws MitchellException{
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		ContextDTO contextDto = creteContextDto();
		when(systemConfigurationProxy.getSettingValue(anyString())).thenReturn(Long.toString(qustnnrId));
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(true);
		when(cachingServiceProxy.get(cacheKey)).thenReturn(questionnairIn);
		QuestionnairInfo questionnairInfo =questionnaireManagerImpl.getQuestionnairInfo(contextDto);
		assertNotNull(questionnairInfo);
		verify(systemConfigurationProxy).getSettingValue(anyString());
		verify(cachingServiceProxy).generateKey(methodName, params);
		verify(cachingServiceProxy).get(cacheKey);
		verify(cachingServiceProxy).isExist(cacheKey);
		
		
	}
	
	@Test
	public void testGetQuestionnairInfoWhenNotExstInCache() throws MitchellException{
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		ContextDTO contextDto = creteContextDto();
		QuestionnaireDTO dbDto = createDbDto();
		when(systemConfigurationProxy.getSettingValue(anyString())).thenReturn(Long.toString(qustnnrId));
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		QuestionnairInfo questionnairInfo =questionnaireManagerImpl.getQuestionnairInfo(contextDto);
		assertNotNull(questionnairInfo);
		verify(systemConfigurationProxy).getSettingValue(anyString());
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
	}
	@Test(expected = MitchellException.class)
	public void testGetQuestionnairInfoQustnnrNotExstInDb() throws MitchellException{
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		ContextDTO contextDto = creteContextDto();
		
		when(systemConfigurationProxy.getSettingValue(anyString())).thenReturn(Long.toString(qustnnrId));
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(null);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		QuestionnairInfo questionnairInfo =questionnaireManagerImpl.getQuestionnairInfo(contextDto);
		assertNotNull(questionnairInfo);
		verify(systemConfigurationProxy).getSettingValue(anyString());
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		
	}
	
	@Test (expected = MitchellException.class)
	public void testGetQuestionnairInfoQustnnrIdIsNull() throws MitchellException{
		ContextDTO contextDto = creteContextDto();
		when(systemConfigurationProxy.getSettingValue(anyString())).thenReturn(null);
		QuestionnairInfo questionnairInfo =questionnaireManagerImpl.getQuestionnairInfo(contextDto);
		assertNull(questionnairInfo);
		verify(systemConfigurationProxy).getSettingValue(anyString());
	}
	@Test
	public void testGetQuestionnairInfoWithQustnnrIdinCntxtDto() throws MitchellException{
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		ContextDTO contextDto = creteContextDto();
		contextDto.setQuestionnaireID(qustnnrId);
		QuestionnaireDTO dbDto = createDbDto();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		QuestionnairInfo questionnairInfo =questionnaireManagerImpl.getQuestionnairInfo(contextDto);
		assertNotNull(questionnairInfo);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		
	}
	
	
	@Test
	public void testGetCurrQustnQustnnrWhenQustnNodeExist(){
		Node<Question> rootElement = new Node<Question>();
		rootElement.setData(createRootNode());
		Node<Question> children = new Node<Question>();
		children.setData(createChildQuestion());
		List<Node<Question>> childList = new ArrayList<Node<Question> >();
		childList.add(children);
		rootElement.setChildren(childList);
		qstnrreTree.setRootElement(rootElement);
		QuestionnaireRqRsDTO questionnaireRqRsDTO =createRequestDto();
		QuestionRqRsDto  currQuestion  =questionnaireRqRsDTO.getQustnnrQustnRqRsDto().getQuestionsList().get(0);
		Question question =questionnaireManagerImpl.getCurrQustnQustnnr(qstnrreTree, currQuestion);
		assertNotNull(question);
	}
	
	
	@Test
	public void testGetCurrQustnQustnnrWhenQustnNodeNotExist(){
		Node<Question> rootElement = new Node<Question>();
		rootElement.setData(createRootNode());
		Node<Question> children = new Node<Question>();
		children.setData(createChildQuestion());
		List<Node<Question>> childList = new ArrayList<Node<Question> >();
		childList.add(children);
		rootElement.setChildren(childList);
		qstnrreTree.setRootElement(rootElement);
		QuestionnaireRqRsDTO questionnaireRqRsDTO =createRequestDto();
		QuestionRqRsDto  currQuestion  =questionnaireRqRsDTO.getQustnnrQustnRqRsDto().getQuestionsList().get(0);
		currQuestion.setQustnnreQustnId(1234L);
		Question question =questionnaireManagerImpl.getCurrQustnQustnnr(qstnrreTree, currQuestion);
		assertNull(question);
	}
	
	
	@Test 
	public void testGetNextQustnWithNullQustnnr () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		requestDto.setQustnnrQustnRqRsDto(null);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		when(systemConfigurationProxy.getSettingValue(anyString())).thenReturn(Long.toString(qustnnrId));
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(true);
		when(cachingServiceProxy.get(cacheKey)).thenReturn(questionnairIn);
		
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		verify(systemConfigurationProxy).getSettingValue(anyString());
		verify(cachingServiceProxy).generateKey(methodName, params);
		verify(cachingServiceProxy).get(cacheKey);
		verify(cachingServiceProxy).isExist(cacheKey);
		
		
	}
	@Test 
	public void testGetNextQustnWithQustnnr () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		QuestionnaireDTO dbDto = createDbDto();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		verify(cachingServiceProxy).put(any(String.class),any(QuestionnairInfo.class));
		
	}
	
	
	
	@Test(expected=MitchellException.class)
	public void testGetNextQustnWithQustnnrWithMithchellException () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		requestDto.getContextDto().setCoCode(null);
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
				
	}
	
	@Test(expected=MitchellException.class)
	public void testGetNextQustnWithQustnnrWithNullRequestDto () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = null;
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		
		
	}
	
	
	
	@Test (expected = MitchellException.class)
	public void testGetNextQustnWithQustnNodeNotExist () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		requestDto.getQustnnrQustnRqRsDto().getQuestionsList().get(0).setQustnnreQustnId(123L);
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		QuestionnaireDTO dbDto = createDbDto();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNull(resJson);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		verify(cachingServiceProxy).put(any(String.class),any(QuestionnairInfo.class));
		
	}
	
	@Test 
	public void testGetNextQustnWithEmptyAnsList () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		List<AnswerRqRsDto> ansList = new ArrayList<AnswerRqRsDto>();
		requestDto.getQustnnrQustnRqRsDto().getQuestionsList().get(0).setAnswerList(ansList);
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		QuestionnaireDTO dbDto = createDbDto();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		verify(cachingServiceProxy).put(any(String.class),any(QuestionnairInfo.class));
		
	}
	
	@Test 
	public void testGetNextQustnWithSelectedAnsNotNull () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		List<Answer> ansList =createAnswerList();
		QuestionnaireDTO dbDto = createDbDto();
		dbDto.setAnswers(ansList);
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		verify(cachingServiceProxy).put(any(String.class),any(QuestionnairInfo.class));
		
	}
	
	
	@Test 
	public void testGetNextQustnNullDbAnsList () throws MitchellException{
		QuestionnaireRqRsDTO requestDto = createRequestDto();
		List<Answer> ansList =new ArrayList<Answer>();
		QuestionnaireDTO dbDto = createDbDto();
		dbDto.setAnswers(ansList);
		requestDto.getContextDto().setQuestionnaireID(100000005875L);
		QuestionnairInfo  questionnairIn = getQuestionnairInfo();
		when(cachingServiceProxy.generateKey(methodName, params)).thenReturn(cacheKey);
		when(cachingServiceProxy.isExist(cacheKey)).thenReturn(false);
		when(evaluationDAO.getQuestionnaire(any(Long.class),any(String.class))).thenReturn(dbDto);
		when(cachingServiceProxy.put(cacheKey,questionnairIn)).thenReturn(true);
		String resJson = questionnaireManagerImpl.getNextQustn(requestDto);
		assertNotNull(resJson);
		verify(cachingServiceProxy,times(2)).generateKey(methodName, params);
		verify(cachingServiceProxy).isExist(cacheKey);
		verify(evaluationDAO).getQuestionnaire(any(Long.class),any(String.class));
		verify(cachingServiceProxy).put(any(String.class),any(QuestionnairInfo.class));
		
	}
	
	
	
	public String dtoToJson(QuestionnaireRqRsDTO requestDto) {
		Gson gson = new Gson();
		String responseJson = gson.toJson(requestDto);
		return responseJson;

	}
	
	
	
	private QuestionnaireRqRsDTO jsonToDto(String jsonStr) {
		Gson gson = new Gson();
		QuestionnaireRqRsDTO  responseJson =  gson.fromJson(jsonStr, QuestionnaireRqRsDTO.class) ; 
		return responseJson;

	}
}
