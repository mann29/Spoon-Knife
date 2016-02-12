package com.mitchell.services.business.questionnaireevaluation.ejb.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.evaluationdetails.MitchellEvaluationDetailsDocument;
import com.mitchell.services.business.questionnaireevaluation.dto.Answer;
import com.mitchell.services.business.questionnaireevaluation.dto.AnswerRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.ContextDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.Question;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnairInfo;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireTree;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJB;
import com.mitchell.services.business.questionnaireevaluation.impl.QuestionnaireEvaluationImplProxy;
import com.mitchell.services.business.questionnaireevaluation.impl.QuestionnaireManager;

public class QuestionnaireEvaluationEJBTest {

	private QuestionnaireEvaluationEJB questionnaireEvaluationEJB = null;
	String inputSaveJson;
	QuestionnaireManager mockManager = null;
	QuestionnaireEvaluationImplProxy qustnnrEvalimpl = null;
	Question question=null;
	@Before
	public void setUp() throws IllegalAccessException, MitchellException
	{
		questionnaireEvaluationEJB = new QuestionnaireEvaluationEJB();
		questionnaireEvaluationEJB.initializeResources();
		inputSaveJson = readFileFromClassPath("InputSaveJson.txt");
		mockManager = mock(QuestionnaireManager.class);
		questionnaireEvaluationEJB.setManager(mockManager);
		qustnnrEvalimpl = mock(QuestionnaireEvaluationImplProxy.class);
		questionnaireEvaluationEJB.setQustnnrEvalImpl(qustnnrEvalimpl);
		
		question = new Question();
		question.setAnswrControlType("RANGE_VALUE");
		question.setQustnnreQustnId(1000015309L);
	}
			
	@Test
	public void testSaveUpdateEvalAndGetNxtQustn() throws Exception{
		
		String inputJson = readFileFromClassPath("InputSaveJson.txt");
		
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo = mock(QuestionnairInfo.class);
		Question question = new Question();
		Answer ans = new Answer();
		List<AnswerRqRsDto> ansList = new ArrayList<AnswerRqRsDto>();
		AnswerRqRsDto	answer= new AnswerRqRsDto();
		ansList.add(answer);
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(mockManager.getCurrQustnQustnnr(questionnaireTree, ques)).thenReturn(question);
		when(mockManager.getSelectedAnswer(question, answer)).thenReturn(ans);
		when(mockManager.getQuestionnairInfo(any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenReturn(Mockito.anyString());
		String str = questionnaireEvaluationEJB.saveUpdateEvalAndGetNxtQustn(inputJson);
		Mockito.verify(mockManager).getCurrQustnQustnnr(Mockito.any(QuestionnaireTree.class), Mockito.any(QuestionRqRsDto.class));
		Mockito.verify(mockManager).getSelectedAnswer(Mockito.any(Question.class),Mockito.any(AnswerRqRsDto.class));
		Mockito.verify(mockManager).getQuestionnairInfo(Mockito.any(ContextDTO.class));
		Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
		assertNotNull(str);
	}

	@Test
	public void testSaveUpdateEvalAndGetNxtQustnWithNoAns() throws Exception{
		
		String inputJson = readFileFromClassPath("InputSaveJsonWithNoAnsSelected.txt");
		
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo = mock(QuestionnairInfo.class);
		Question question = new Question();
		Answer ans = new Answer();
		List<AnswerRqRsDto> ansList = new ArrayList<AnswerRqRsDto>();
		AnswerRqRsDto	answer= new AnswerRqRsDto();
		ansList.add(answer);
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(mockManager.getCurrQustnQustnnr(questionnaireTree, ques)).thenReturn(question);
		when(mockManager.getQuestionnairInfo(any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenReturn(Mockito.anyString());
		String str = questionnaireEvaluationEJB.saveUpdateEvalAndGetNxtQustn(inputJson);
		Mockito.verify(mockManager).getCurrQustnQustnnr(Mockito.any(QuestionnaireTree.class), Mockito.any(QuestionRqRsDto.class));
		Mockito.verify(mockManager).getQuestionnairInfo(Mockito.any(ContextDTO.class));
		Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
		assertNotNull(str);
	}
	
	
	@Test
	public void testSaveUpdateEvalAndGetNxtQustnWithNullQustnnr() throws Exception{
		
		String inputJson = readFileFromClassPath("InputSaveJsonWithoutQustnnr");
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenReturn(Mockito.anyString());
		String str = questionnaireEvaluationEJB.saveUpdateEvalAndGetNxtQustn(inputJson);
		Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
		assertNotNull(str);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=MitchellException.class)
	public void testSaveUpdateEvalAndGetNxtQustnThrowException() throws Exception{
		
		String inputJson = readFileFromClassPath("InputSaveJson.txt");
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo= null;
		questionnairInfo = mock(QuestionnairInfo.class);
		Question question = new Question();
		Answer ans = new Answer();
		List<AnswerRqRsDto> ansList = new ArrayList<AnswerRqRsDto>();
		AnswerRqRsDto	answer= new AnswerRqRsDto();
		ansList.add(answer);
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(mockManager.getCurrQustnQustnnr(questionnaireTree, ques)).thenReturn(question);
		when(mockManager.getSelectedAnswer(question, answer)).thenReturn(ans);
		when(mockManager.getQuestionnairInfo(Mockito.any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenThrow(Exception.class);
		
		String str = questionnaireEvaluationEJB.saveUpdateEvalAndGetNxtQustn(inputJson);
		assertNull(str);
		Mockito.verify(mockManager).getCurrQustnQustnnr(Mockito.any(QuestionnaireTree.class), Mockito.any(QuestionRqRsDto.class));
		Mockito.verify(mockManager).getSelectedAnswer(Mockito.any(Question.class),Mockito.any(AnswerRqRsDto.class));
		Mockito.verify(mockManager).getQuestionnairInfo(Mockito.any(ContextDTO.class));
		Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
		
	}
	@SuppressWarnings("unchecked")
	@Test(expected=MitchellException.class)
	public void testSaveUpdateEvalAndGetNxtQustnThrowMitchellException() throws Exception{
		
		String inputJson = readFileFromClassPath("InputSaveJson.txt");
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo= null;
		questionnairInfo = mock(QuestionnairInfo.class);
		Question question = new Question();
		Answer ans = new Answer();
		List<AnswerRqRsDto> ansList = new ArrayList<AnswerRqRsDto>();
		AnswerRqRsDto	answer= new AnswerRqRsDto();
		ansList.add(answer);
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(mockManager.getCurrQustnQustnnr(questionnaireTree, ques)).thenReturn(question);
		when(mockManager.getSelectedAnswer(question, answer)).thenReturn(ans);
		when(mockManager.getQuestionnairInfo(Mockito.any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenThrow(MitchellException.class);
		String str = questionnaireEvaluationEJB.saveUpdateEvalAndGetNxtQustn(inputJson);
		assertNull(str);
		Mockito.verify(mockManager).getCurrQustnQustnnr(Mockito.any(QuestionnaireTree.class), Mockito.any(QuestionRqRsDto.class));
		Mockito.verify(mockManager).getSelectedAnswer(Mockito.any(Question.class),Mockito.any(AnswerRqRsDto.class));
		Mockito.verify(mockManager).getQuestionnairInfo(Mockito.any(ContextDTO.class));
		Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
		
	}
	
	@Test
	public void testGetNextPartialSavedQuestionWithNoQustnnr() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlWithNoQustnnr.xml"));
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenReturn(anyString());
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNotNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	@Test
	public void testGetNextPartialSavedQuestionWithNullQustnList() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlWithNullQustnList.xml"));
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenReturn(anyString());
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNotNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	@Test(expected=MitchellException.class)
	public void testGetNextPartialSavedQuestionWithInvalidInput() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlWithNoQustInList.xml"));
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNull(str);
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	
	@Test(expected=MitchellException.class)
	public void testGetNextPartialSavedQuestionNumberFormateException() throws Exception
	{
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "c0");
		assertNotNull(str);
	}
	
	
	
	@Test
	public void testGetNextPartialSavedQuestionWithQusnInList() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXml.xml"));
		QuestionnairInfo questionnairInfo = mock(QuestionnairInfo.class);
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getQuestionnairInfo(any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenReturn(anyString());
		
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		
		assertNotNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		verify(mockManager).getQuestionnairInfo(any(ContextDTO.class));
		
	}
	@Test
	public void testGetNextPartialSavedQuestionWithRANGEQusnInList() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlWithRangeValue.xml"));
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo = new QuestionnairInfo();
		
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getQuestionnairInfo(any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getCurrQustnQustnnr(any(QuestionnaireTree.class), any(QuestionRqRsDto.class))).thenReturn(question);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenReturn(anyString());
		
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		
		assertNotNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		verify(mockManager).getQuestionnairInfo(any(ContextDTO.class));
		verify(mockManager).getCurrQustnQustnnr(any(QuestionnaireTree.class), any(QuestionRqRsDto.class));
		
	}
	
	@Test
	public void testGetNextPartialSavedQuestionWithRelatedQustnSaved() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXmlWithRelatedQues.xml"));
		QuestionnaireTree  questionnaireTree  = new QuestionnaireTree ();
		QuestionnairInfo questionnairInfo = mock(QuestionnairInfo.class);
		Question question = new Question();
		QuestionRqRsDto ques= new QuestionRqRsDto();
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getQuestionnairInfo(any(ContextDTO.class))).thenReturn(questionnairInfo);
		when(mockManager.getCurrQustnQustnnr(questionnaireTree, ques)).thenReturn(question);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenReturn(anyString());
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNotNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=MitchellException.class)
	public void testGetNextPartialSavedQuestionThrowsMitchellException() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXml.xml"));
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenThrow(MitchellException.class);
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(expected=Exception.class)
	public void testGetNextPartialSavedQuestionThrowsException() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData = MitchellEvaluationDetailsDocument.Factory.parse(readFileFromClassPath("EvaluationXml.xml"));
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		when(mockManager.getNextQustn(any(QuestionnaireRqRsDTO.class))).thenThrow(Exception.class);
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNull(str);
		verify(mockManager).getNextQustn(any(QuestionnaireRqRsDTO.class));
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	@Test(expected=MitchellException.class)
	public void testGetNextPartialSavedQuestionThrowsNumberFormatException() throws Exception
	{	
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "qw");
		assertNull(str);
	
		
	}
	@Test(expected=MitchellException.class)
	public void testGetNextPartialSavedQuestionEvalDocNull() throws Exception
	{	
		MitchellEvaluationDetailsDocument evaluationDetailsDocData =null;
		when(qustnnrEvalimpl.getEvaluationDocFromNAS(anyLong())).thenReturn(evaluationDetailsDocData);
		String str = questionnaireEvaluationEJB.getNextPartialSavedQuestion("M2", "0");
		assertNull(str);
		verify(qustnnrEvalimpl).getEvaluationDocFromNAS(anyLong());
		
		
	}
	
	@Test
	public void testGetFirstQuestion() throws Exception
	{
		questionnaireEvaluationEJB.setQustnnrEvalImpl(qustnnrEvalimpl);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenReturn(Mockito.anyString());
	    String str = questionnaireEvaluationEJB.getFirstQuestion("M2");
	    Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
	    assertNotNull(str);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=MitchellException.class)
	public void testGetFirstQuestionThrowsMitchellException() throws Exception
	{
		questionnaireEvaluationEJB.setQustnnrEvalImpl(qustnnrEvalimpl);
		when(mockManager.getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class))).thenThrow(MitchellException.class);
	    String str = questionnaireEvaluationEJB.getFirstQuestion("M2");
	    Mockito.verify(mockManager).getNextQustn(Mockito.any(QuestionnaireRqRsDTO.class));
	    assertNotNull(str);
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
}
