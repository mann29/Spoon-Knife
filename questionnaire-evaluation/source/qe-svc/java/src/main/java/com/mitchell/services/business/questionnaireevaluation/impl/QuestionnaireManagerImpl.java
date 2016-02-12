package com.mitchell.services.business.questionnaireevaluation.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireTree;
import com.mitchell.services.business.questionnaireevaluation.dto.QustnnrQustnRqRsDto;
import com.mitchell.services.business.questionnaireevaluation.proxy.CachingServiceProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.CustomSettingProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtils;
import com.mitchell.services.business.questionnaireevaluation.util.QuestionnaireEvaluationUtilsProxy;

public class QuestionnaireManagerImpl implements QuestionnaireManager {

   private static final String CLASS_NAME = QuestionnaireManagerImpl.class
         .getName();
   private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

   private QuestionnaireEvaluationDAOProxy evaluationDAO;

   private CustomSettingProxy customSettingProxy;

   private CachingServiceProxy cachingServiceProxy;

   private SystemConfigurationProxy systemConfigurationProxy ;
   
   
   public SystemConfigurationProxy getSystemConfigurationProxy() {
      return systemConfigurationProxy;
   }

   public void setSystemConfigurationProxy(
         SystemConfigurationProxy systemConfigurationProxy) {
      this.systemConfigurationProxy = systemConfigurationProxy;
   }

   public CustomSettingProxy getCustomSettingProxy() {
      return customSettingProxy;
   }

   public void setCustomSettingProxy(CustomSettingProxy customSettingProxy) {
      this.customSettingProxy = customSettingProxy;
   }

   public QuestionnaireEvaluationDAOProxy getEvaluationDAO() {
      return evaluationDAO;
   }

   public void setEvaluationDAO(QuestionnaireEvaluationDAOProxy evaluationDAO) {
      this.evaluationDAO = evaluationDAO;
   }

   public CachingServiceProxy getCachingServiceProxy() {
      return cachingServiceProxy;
   }

   public void setCachingServiceProxy(CachingServiceProxy cachingServiceProxy) {
      this.cachingServiceProxy = cachingServiceProxy;
   }

   /**
    * This API is used to get the first question of the FNOL questionnaire
    * @param QuestionnairInfo : contains details of the questionnaire and cached QuestionTree 
    * @return QustnnrQustnRqRsDto : Response string will contain the next question details that used needs to answer.
    * @throws MitchellException
    */
   public QustnnrQustnRqRsDto getFirstQuestion(QuestionnairInfo questionnairInfo)
         throws MitchellException {
      if(LOGGER.isInfoEnabled()){
         LOGGER.info(CLASS_NAME +"getFirstQuestion" );
      }
      QustnnrQustnRqRsDto responseQstnnrQues= null;
      QuestionnaireTree questnrtree = questionnairInfo.getQuestionnaireTree();
      Question qustn = getCurrQustnQustnnr( questnrtree ,  null);
      ArrayList<QuestionRqRsDto> qustnList = populateNxtQustnList(qustn) ;
      responseQstnnrQues = populateResQustnnrQustn(qustnList, questionnairInfo.getQuestionnaireDetails());
      return responseQstnnrQues;
   }

   /**
    * This method is used to get the cached QuestionnairInfo  which contains Questionnaire tree and QuestionnaireDetails
    * If QuestionnairInfo is present in the Cache it will return the cached Object else it will create 
    * the QuestionnairInfo object and will put it in cache
    * 
    * @param ContextDTO - it contains coCd and Questionnaire Id
    * @return QuestionnairInfo
    * @throws MitchellException
    * 
    * */
   
   public QuestionnairInfo getQuestionnairInfo(ContextDTO contextDto) throws MitchellException{
       QuestionnairInfo questionnairInfo = null;
      String questionnairId = null;
      String methodName = "getQuestionnairInfo";
      if(LOGGER.isInfoEnabled()){
         LOGGER.info(CLASS_NAME +methodName);
      }
      try{
         long qustnnreId = contextDto.getQuestionnaireID();
         if(qustnnreId <= 0L){
            questionnairId = systemConfigurationProxy.getSettingValue(QuestionnaireEvaluationConstants.SYSTEM_CONFIG_QUSTNNR_ID);
            if (questionnairId == null){
               throw new MitchellException(CLASS_NAME, "getFirstQuestion","Questionnair ID not present in the set file");
            }
            qustnnreId = Long.parseLong(questionnairId);
            contextDto.setQuestionnaireID(qustnnreId);
         }
         questionnairInfo = getQustnnreInfoFromCache(qustnnreId, methodName);
         if (questionnairInfo == null) {
         QuestionnaireDTO dbQuestionnaireEvaluationDTO = getQuestionnaire(contextDto);
         QuestionnaireDetails questionnaireDetails = populateQuestionnaireDetails(dbQuestionnaireEvaluationDTO.getQuestionnaire());
         QuestionnaireTree questnrtree = createQustnrTree(dbQuestionnaireEvaluationDTO);
         if(LOGGER.isInfoEnabled()){
            LOGGER.info("questnrtree" +questnrtree);
         }
         questionnairInfo = new QuestionnairInfo();
         questionnairInfo.setQuestionnaireDetails(questionnaireDetails);
         questionnairInfo.setQuestionnaireTree(questnrtree);
         putQuestionniareInfoInCache(questionnairInfo, qustnnreId, methodName);
      }
   }catch (NumberFormatException nfe) {
               throw new MitchellException(QuestionnaireEvaluationConstants.NUMBER_FORMATE_EXP,CLASS_NAME, "getQuestionnaire",
                     QuestionnaireEvaluationConstants.NUMBER_FORMATE_EXCEPTION);
            } 
      return questionnairInfo;
      
   }
   
   /**
    * This method is used to populate the QuestionnaireDetails dto from db Questionnaire
    * @param Questionnaire Db Questionnaire
    * @return QuestionnaireDetails Dto
    * 
    * */
   private QuestionnaireDetails populateQuestionnaireDetails(Questionnaire dbQuestionnaire){
      QuestionnaireDetails questionnaireDetails = new QuestionnaireDetails();
      questionnaireDetails.setB6(dbQuestionnaire.getB6());
      questionnaireDetails.setCoCd(dbQuestionnaire.getCoCd());
      questionnaireDetails.setEvltnCategory(dbQuestionnaire.getEvltnCategory());
      questionnaireDetails.setIsCustomScore(dbQuestionnaire.getIsCustomScore());
      questionnaireDetails.setScoringType(dbQuestionnaire.getScoringType());
      questionnaireDetails.setMaxPoints(dbQuestionnaire.getMaxPoints());
      questionnaireDetails.setQustnnreDesc(dbQuestionnaire.getQustnnreDesc());
      questionnaireDetails.setQustnnreId(dbQuestionnaire.getQustnnreId());
      questionnaireDetails.setQustnnreName(dbQuestionnaire.getQustnnreName());
      questionnaireDetails.setQustnnreVersion(dbQuestionnaire.getQustnnreVersion());
      
      return questionnaireDetails;
   }
   
   /**
    * This method is used to get the node position of the requested question in the questionnaire tree
    * 
    * @param QuestionnaireTree , QuestionRqRsDto
    *  @return Question
    *  
    * */
   
   public Question getCurrQustnQustnnr(QuestionnaireTree questnrtree , QuestionRqRsDto currQuestion){
      if(LOGGER.isInfoEnabled()){
         LOGGER.info(CLASS_NAME +"getCurrQustnQustnnr" );
      }
      Question currQustn = null;
      Node<Question> rootQuest = questnrtree.getRootElement();
      if(LOGGER.isInfoEnabled()){
         LOGGER.info("currQustnNode" +rootQuest );
      }
      Node<Question>   currQustnNode = questnrtree.findNode(rootQuest, currQuestion);
      if(LOGGER.isInfoEnabled()){
         LOGGER.info("currQustnNode" +currQustnNode );
      }
      if(currQustnNode !=null){
         currQustn = currQustnNode.getData();
      }
      return currQustn;
   }
   
   
   /**
    * This Method is used to get the next question.
    * 
    * @param requestDto
    *            : this is a QuestionnaireRqRsDTO object. which contains info
    *            of current answered question
    * @return String : Details of next question to be answered in JSON String
    *         formats
    * @throws MitchellException
    * 
    * */
   public String getNextQustn(QuestionnaireRqRsDTO requestDto)
         throws MitchellException {
      final String methodName = "getNextQustn";
      if (LOGGER.isInfoEnabled()) {
         LOGGER.info(CLASS_NAME + methodName);
      }
      QuestionnaireRqRsDTO responseDto = new QuestionnaireRqRsDTO();
      String responseJson = null;
      inputValidation(requestDto);
      ContextDTO contextDto = requestDto.getContextDto();
      QustnnrQustnRqRsDto currQustnQustnnrDto = requestDto.getQustnnrQustnRqRsDto();
            
      QustnnrQustnRqRsDto resQuestionnaire = null;
      try {
         QuestionnairInfo questionnairInfo = getQuestionnairInfo(contextDto);
         if(currQustnQustnnrDto == null){
            resQuestionnaire = getFirstQuestion( questionnairInfo);
         }else{
            resQuestionnaire = createNextQuestion(currQustnQustnnrDto,
                  questionnairInfo);
         }
            responseDto.setContextDto(contextDto);
            responseDto.setQustnnrQustnRqRsDto(resQuestionnaire);
            QuestionnaireEvaluationUtilsProxy qeUtilsProxy = new QuestionnaireEvaluationUtils();
            responseJson = qeUtilsProxy.dtoToJson(responseDto);
         
      } catch (MitchellException me) {
         throw me;
      } catch (Exception e) {
         throw new MitchellException(
               QuestionnaireEvaluationConstants.ERROR_UNKNOWN, CLASS_NAME,
               methodName,
               QuestionnaireEvaluationConstants.ERROR_UNKNOWN_MSG, e);
      }
      return responseJson;
   }

   /**
    * This method validates the input request dto
    *  @param QuestionnaireRqRsDTO : contains the details of the current question
    * 
    * */
   private void inputValidation(QuestionnaireRqRsDTO requestDto)
         throws MitchellException {
      final String methodName = "inputValidation";
      if (requestDto == null || requestDto.getContextDto() == null) {
         throw new MitchellException(
               QuestionnaireEvaluationConstants.INVALID_INPUT, CLASS_NAME,
               methodName,
               QuestionnaireEvaluationConstants.INVALID_INPUT_MSG);
      }
      if (requestDto.getContextDto().getCoCode() == null) {
         throw new MitchellException(
               QuestionnaireEvaluationConstants.INVALID_COMPANY_CODE,
               CLASS_NAME, methodName,
               QuestionnaireEvaluationConstants.INVALID_COMPANY_CODE_MSG);
      }

   }

   /**
    * This Method is used to create DTO of next question's
    * 
    * @param reqQstnnrQues
    *            : QustnnrQustnRqRsDto object, contains info of the
    *            current question contextDto : contains info of coCd, contextID
    *            etc.
    * @return QustnnrQustnRqRsDto : next questions questionnaire dto
    * @throws MitchellException
    * 
    * */
   private QustnnrQustnRqRsDto createNextQuestion(
         QustnnrQustnRqRsDto reqQstnnrQues,QuestionnairInfo questionnairInfo)
         throws MitchellException {
      final String methodName = "createNextQuestion";
      QustnnrQustnRqRsDto responseQstnnrQues = new QustnnrQustnRqRsDto() ;
      try {
         QuestionRqRsDto currQuestion = reqQstnnrQues.getQuestionsList().get(0);
         QuestionnaireTree questnrtree = questionnairInfo.getQuestionnaireTree();
         Node<Question> rootQuest = questnrtree.getRootElement();
         Node<Question> nxtQustnNode = getNxtQustnNode(rootQuest, currQuestion,questnrtree);
         ArrayList<QuestionRqRsDto> qustnList = new ArrayList<QuestionRqRsDto>();
         if(nxtQustnNode !=null ){
             qustnList = populateNxtQustnList(nxtQustnNode.getData()) ;
         }
         responseQstnnrQues = populateResQustnnrQustn(qustnList, questionnairInfo.getQuestionnaireDetails() );
      } catch (MitchellException me) {
         throw me;
      } catch (Exception e) {
         throw new MitchellException(
               CLASS_NAME,
               methodName,
               QuestionnaireEvaluationConstants.ERROR_GETTING_NXT_QUSTNNRE,
               e);

      }

      return responseQstnnrQues;
   }
private Node<Question> getNxtQustnNode(Node<Question> rootQuest,QuestionRqRsDto currQuestion  , QuestionnaireTree questnrTree) throws MitchellException{
   Node<Question> nxtQustnNode = null;
   List<AnswerRqRsDto> answerList = currQuestion.getAnswerList();
   Node<Question>  currQustnNode = questnrTree.findNode(rootQuest, currQuestion);
   if(currQustnNode == null){
      throw new MitchellException(CLASS_NAME, "getNxtQustnNode","Question Questionnair Id is not present");
   }
   Answer selectedAnswer =null;
   if(!answerList.isEmpty()){
      AnswerRqRsDto   answer = answerList.get(0);
      selectedAnswer = getSelectedAnswer(currQustnNode.getData(),answer );
   }
   if (selectedAnswer == null || ("T".equalsIgnoreCase(selectedAnswer.getIsLeaf()))) {
      nxtQustnNode = questnrTree.findSibling(currQustnNode);
   } else {
      nxtQustnNode = questnrTree.findQuestOnSelectedAnswer(
            rootQuest, selectedAnswer);
   }
   return nxtQustnNode;
}
   
   private QustnnrQustnRqRsDto populateResQustnnrQustn(ArrayList<QuestionRqRsDto> qustnList , QuestionnaireDetails questionnaireDetails){
      QustnnrQustnRqRsDto responseQstnnrQues = new QustnnrQustnRqRsDto();
      responseQstnnrQues.setQuestionsList(qustnList);
      responseQstnnrQues.setQustnnreId(questionnaireDetails.getQustnnreId());
      responseQstnnrQues.setQustnnreVersion(questionnaireDetails.getQustnnreVersion());
      return responseQstnnrQues;
   }
   
   public Answer getSelectedAnswer(Question currentQuestion , AnswerRqRsDto answer ){
      Answer selectedAnswer = null;
      List<Answer> dbAnswerList = currentQuestion.getAnswerList();
      if(dbAnswerList.isEmpty()){
         selectedAnswer = new Answer();
         selectedAnswer.setAnswerDisplayText(answer.getAnswerDisplayText());
         selectedAnswer.setIsLeaf(QuestionnaireEvaluationConstants.IS_LEAF);
      }else{
         for(int i =0;i < dbAnswerList.size(); i++){
            Answer dbAnswer = dbAnswerList.get(i);
            if((QuestionnaireEvaluationConstants.ANSWR_CONTL_TYPE.equalsIgnoreCase(currentQuestion.getAnswrControlType()) 
            		&& (answer.getLowRangeValue()== dbAnswer.getLowRangeValue())
            		&& (answer.getHighRangeValue()== dbAnswer.getHighRangeValue()))
                  ||( dbAnswer.getAnswerDisplayText() != null 
                		  && dbAnswer.getAnswerDisplayText().equalsIgnoreCase(answer.getAnswerDisplayText()))){
               selectedAnswer = dbAnswer;
               break;
            }
         }
      }
      return selectedAnswer;
   }
   
   private ArrayList<QuestionRqRsDto> populateNxtQustnList(Question dbQuestion){
      ArrayList<QuestionRqRsDto> qustnList = new ArrayList<QuestionRqRsDto>(); 
      if(dbQuestion != null){
          List<Answer> answerList = dbQuestion.getAnswerList();
            QuestionRqRsDto resQuestion = new QuestionRqRsDto();
            AnswerRqRsDto resAnswer = null;
            List<AnswerRqRsDto> resAnswerList = new ArrayList<AnswerRqRsDto>();
            
            resQuestion.setAnswrControlType(dbQuestion.getAnswrControlType());
            resQuestion.setQustnnreQustnId(dbQuestion.getQustnnreQustnId());
            resQuestion.setQustnText(dbQuestion.getQustnText());
            if(dbQuestion.getQustnFormattedText() == null){
               resQuestion.setQustnFormattedText(QuestionnaireEvaluationConstants.FORMATTED_TEXT);
            }else{
               resQuestion.setQustnFormattedText(dbQuestion.getQustnFormattedText());
            }
            
            if(answerList.isEmpty()){
               resQuestion.setAnswerList(resAnswerList);
            }else{
               for(int i =0;i<answerList.size();i++){
                  resAnswer = new AnswerRqRsDto();
                  resAnswer.setAnswerDisplayOrder(answerList.get(i).getAnswerDisplayOrder());
                  resAnswer.setAnswerDisplayText(answerList.get(i).getAnswerDisplayText());
                  resAnswer.setAnswerItemID(answerList.get(i).getAnswerItemID());
                  resAnswer.setHighRangeValue(answerList.get(i).getHighRangeValue());
                  resAnswer.setLowRangeValue(answerList.get(i).getLowRangeValue());
                  resAnswer.setQustnnreQustnId(answerList.get(i).getQustnnerQustnId());
                  resAnswerList.add(resAnswer);
               }
               resQuestion.setAnswerList(resAnswerList);
            }
            qustnList.add(resQuestion);
      }
      return qustnList;
   
   }
   

   /**
    * This method fetches the questionniareInfo from cache, if it exists.
    * 
    * @param dbQuestionnaireEvaluationDTO
    * @return
    * @throws MitchellException
    */
   
   private QuestionnairInfo getQustnnreInfoFromCache(Long qustnnreId,
         String methodName) throws MitchellException {


      QuestionnairInfo questionnairInfo = null;

      Object[] params = new Object[1];
      params[0] = qustnnreId;
      // call proxy to generate key
      String cacheKey = cachingServiceProxy.generateKey(methodName, params);
      // call proxy to check if key exists
      if (cachingServiceProxy.isExist(cacheKey)) {
         // if key exists, return value i.e tree . if not return null
         Object returnVal = cachingServiceProxy.get(cacheKey);
         
         if (returnVal != null) {
            questionnairInfo = (QuestionnairInfo) returnVal;
         }
      }
      
      return questionnairInfo;
   }

   /**
    * This method is used to put QuestionnaireInfo  in Cache
    * 
    * @param questnrInfo
    * @param qustnnreId
    * @param methodName
    * @throws MitchellException
    */
   
   private void putQuestionniareInfoInCache(QuestionnairInfo questnrInfo,
         Long qustnnreId, String methodName) throws MitchellException {

      if(LOGGER.isInfoEnabled()){
         LOGGER.info(CLASS_NAME +"putQuestionniareInfoInCache" );
      }
      boolean cacheSuccess = false;
      Object[] params = new Object[1];
      params[0] = qustnnreId;
      String cacheKey = cachingServiceProxy.generateKey(methodName, params);
      if (cacheKey != null) {
         cacheSuccess = cachingServiceProxy.put(cacheKey, questnrInfo);
      }
      if (!cacheSuccess && LOGGER.isEnabledFor(Level.WARN)) {
            LOGGER.warn("Failed to cache Questionnaire Tree");
      }
   }
   
   

   /**
    * This Method is used to creates the Questionnaire Tree
    * 
    * @param dbQuestionnaireEvaluationDTO
    *            : Original Questionnaire DTO saved in DB
    * @return QuestionnaireTree
    * 
    * */
   private QuestionnaireTree createQustnrTree(
         QuestionnaireDTO dbQuestionnaireEvaluationDTO) {
      if(LOGGER.isInfoEnabled()){
         LOGGER.info(CLASS_NAME +"createQustnrTree");
      }
      QuestionnaireTree questnnrTree = new QuestionnaireTree();
      Node<Question> rootElement = new Node<Question>();
      rootElement.setData(createRootNode());
      createTree(rootElement, dbQuestionnaireEvaluationDTO);
      questnnrTree.setRootElement(rootElement);
      return questnnrTree;
   }

   /**
    * This Method is used to create the top root dummy node of the
    * questionnaire tree
    * 
    * @return Question : root question
    * */
   private Question createRootNode() {
      Question question = new Question();
      question.setQustnnreQustnId(0L);
      return question;
   }

   /**
    * This method is used to get the original Questionnaire from the database
    * which is configured for the carrier.
    * 
    * @param currntQustnnreId
    * @param coCd
    * @return QuestionnaireEvaluationDBDTO
    * @throws MitchellException
    */
   private QuestionnaireDTO getQuestionnaire(ContextDTO contextDTO)
         throws MitchellException {
      long qustnnrId = contextDTO.getQuestionnaireID();
      QuestionnaireDTO dbDto = null;
      String coCd = contextDTO.getCoCode();
      try {
         dbDto = evaluationDAO.getQuestionnaire(qustnnrId, coCd);
         if (dbDto == null) {
            throw new MitchellException(
                  QuestionnaireEvaluationConstants.ERROR_GETTING_QUESTIONNAIRE_FROM_DB,
                  CLASS_NAME,
                  "getQuestionnaire",
                  QuestionnaireEvaluationConstants.ERROR_GETTING_QUESTIONNAIRE_MSG);
         }

      }catch (MitchellException e) {
         throw e;
      }
      return dbDto;

   }

   private void createTree(Node<Question> taskElement,
         QuestionnaireDTO dbQuestionnaireEvaluationDTO) {

      List<Question> children = findChildByParentId(taskElement.getData(),
            dbQuestionnaireEvaluationDTO);
      List<Node<Question>> childElements = new ArrayList<Node<Question>>();
      for (Iterator<Question> it = children.iterator(); it.hasNext();) {
         Question childTask = it.next();
         Node<Question> childElement = new Node<Question>();
         childElement.setData(childTask);
         childElement.setParent(taskElement);
         childElements.add(childElement);
         createTree(childElement, dbQuestionnaireEvaluationDTO);
      }
      taskElement.setChildren(childElements);
   }

   /**
    * This Method is used to get the list of all children of the parent
    * question
    * 
    * @param dbQuestionnaireEvaluationDTO
    *            : Original Questionnaire DTO saved in DB
    * 
    * @return List<Question>
    * */
   private List<Question> findChildByParentId(Question questn,
         QuestionnaireDTO dbQuestionnaireEvaluationDTO) {

      List<Question> childList = new ArrayList<Question>();
      for (int i = 0; i < dbQuestionnaireEvaluationDTO.getQuestions().size(); i++) {
         Question addQuestion = dbQuestionnaireEvaluationDTO.getQuestions()
               .get(i);
         if (questn.getQustnnreQustnId() == addQuestion
               .getAncstrQustnnrQustnId()) {

            attachAnswer(addQuestion, dbQuestionnaireEvaluationDTO);
            childList.add(addQuestion);

         }
      }

      return childList;
   }

   /**
    * This Method is used to attach the answers with their respective question
    * 
    * @param dbQuestionnaireEvaluationDTO
    *            : Oringinal Questionnair DTO saved in DB
    * 
    * */
   private void attachAnswer(Question question,
         QuestionnaireDTO dbQuestionnaireEvaluationDTO) {
      List<Answer> answerList = new ArrayList<Answer>();

      for (Answer answer : dbQuestionnaireEvaluationDTO.getAnswers()) {
         if (question.getQustnnreQustnId() == answer.getQustnnerQustnId()) {
            answerList.add(answer);
         }
      }
      question.setAnswerList(answerList);
   }

}
