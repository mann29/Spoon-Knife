package com.mitchell.services.business.questionnaireevaluation.dto;

import java.io.Serializable;
import java.util.List;

public class QuestionnaireDTO implements Serializable {
       private static final long serialVersionUID = 1L;
       private Questionnaire questionnaire;
       private List<Question> questions ;
       private List<Answer> answers ;
      
       public Questionnaire getQuestionnaire() {
          return questionnaire;
         }
     public void setQuestionnaire(Questionnaire questionnaire) {
          this.questionnaire = questionnaire;
     }
     public List<Question> getQuestions() {
          return questions;
     }
     public void setQuestions(List<Question> questions) {
          this.questions = questions;
     }
     public List<Answer> getAnswers() {
          return answers;
     }
     public void setAnswers(List<Answer> answers) {
          this.answers =  answers;
     }
     

}
