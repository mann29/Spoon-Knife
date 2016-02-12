/*
 * An XML document type.
 * Localname: QuestionnaireClaimLink
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire.impl;
/**
 * A document containing one QuestionnaireClaimLink(@http://www.mitchell.com/schemas/questionnaire) element.
 *
 * This is a complex type.
 */
public class QuestionnaireClaimLinkDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkDocument
{
    private static final long serialVersionUID = 1L;
    
    public QuestionnaireClaimLinkDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName QUESTIONNAIRECLAIMLINK$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "QuestionnaireClaimLink");
    
    
    /**
     * Gets the "QuestionnaireClaimLink" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType getQuestionnaireClaimLink()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType)get_store().find_element_user(QUESTIONNAIRECLAIMLINK$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "QuestionnaireClaimLink" element
     */
    public void setQuestionnaireClaimLink(com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType questionnaireClaimLink)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType)get_store().find_element_user(QUESTIONNAIRECLAIMLINK$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType)get_store().add_element_user(QUESTIONNAIRECLAIMLINK$0);
            }
            target.set(questionnaireClaimLink);
        }
    }
    
    /**
     * Appends and returns a new empty "QuestionnaireClaimLink" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType addNewQuestionnaireClaimLink()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType)get_store().add_element_user(QUESTIONNAIRECLAIMLINK$0);
            return target;
        }
    }
}
