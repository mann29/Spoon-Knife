/*
 * XML Type:  QuestionnairesAssociationsType
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire.impl;
/**
 * An XML QuestionnairesAssociationsType(@http://www.mitchell.com/schemas/questionnaire).
 *
 * This is a complex type.
 */
public class QuestionnairesAssociationsTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType
{
    private static final long serialVersionUID = 1L;
    
    public QuestionnairesAssociationsTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName QUESTIONNAIREINFO$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "QuestionnaireInfo");
    
    
    /**
     * Gets array of all "QuestionnaireInfo" elements
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireInfoType[] getQuestionnaireInfoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(QUESTIONNAIREINFO$0, targetList);
            com.mitchell.schemas.questionnaire.QuestionnaireInfoType[] result = new com.mitchell.schemas.questionnaire.QuestionnaireInfoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "QuestionnaireInfo" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireInfoType getQuestionnaireInfoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireInfoType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireInfoType)get_store().find_element_user(QUESTIONNAIREINFO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "QuestionnaireInfo" element
     */
    public int sizeOfQuestionnaireInfoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUESTIONNAIREINFO$0);
        }
    }
    
    /**
     * Sets array of all "QuestionnaireInfo" element
     */
    public void setQuestionnaireInfoArray(com.mitchell.schemas.questionnaire.QuestionnaireInfoType[] questionnaireInfoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(questionnaireInfoArray, QUESTIONNAIREINFO$0);
        }
    }
    
    /**
     * Sets ith "QuestionnaireInfo" element
     */
    public void setQuestionnaireInfoArray(int i, com.mitchell.schemas.questionnaire.QuestionnaireInfoType questionnaireInfo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireInfoType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireInfoType)get_store().find_element_user(QUESTIONNAIREINFO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(questionnaireInfo);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "QuestionnaireInfo" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireInfoType insertNewQuestionnaireInfo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireInfoType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireInfoType)get_store().insert_element_user(QUESTIONNAIREINFO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "QuestionnaireInfo" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireInfoType addNewQuestionnaireInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireInfoType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireInfoType)get_store().add_element_user(QUESTIONNAIREINFO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "QuestionnaireInfo" element
     */
    public void removeQuestionnaireInfo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUESTIONNAIREINFO$0, i);
        }
    }
}
