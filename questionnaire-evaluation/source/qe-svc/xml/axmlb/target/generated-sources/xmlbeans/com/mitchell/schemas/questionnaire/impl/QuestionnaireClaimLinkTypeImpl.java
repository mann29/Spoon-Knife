/*
 * XML Type:  QuestionnaireClaimLinkType
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire.impl;
/**
 * An XML QuestionnaireClaimLinkType(@http://www.mitchell.com/schemas/questionnaire).
 *
 * This is a complex type.
 */
public class QuestionnaireClaimLinkTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType
{
    private static final long serialVersionUID = 1L;
    
    public QuestionnaireClaimLinkTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COCD$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "CoCd");
    private static final javax.xml.namespace.QName CLAIMID$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "ClaimID");
    private static final javax.xml.namespace.QName QUESTIONNAIRESASSOCIATIONS$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "QuestionnairesAssociations");
    
    
    /**
     * Gets the "CoCd" element
     */
    public java.lang.String getCoCd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COCD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CoCd" element
     */
    public org.apache.xmlbeans.XmlString xgetCoCd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COCD$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CoCd" element
     */
    public void setCoCd(java.lang.String coCd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COCD$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COCD$0);
            }
            target.setStringValue(coCd);
        }
    }
    
    /**
     * Sets (as xml) the "CoCd" element
     */
    public void xsetCoCd(org.apache.xmlbeans.XmlString coCd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COCD$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COCD$0);
            }
            target.set(coCd);
        }
    }
    
    /**
     * Gets the "ClaimID" element
     */
    public long getClaimID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ClaimID" element
     */
    public org.apache.xmlbeans.XmlLong xgetClaimID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ClaimID" element
     */
    public void setClaimID(long claimID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMID$2);
            }
            target.setLongValue(claimID);
        }
    }
    
    /**
     * Sets (as xml) the "ClaimID" element
     */
    public void xsetClaimID(org.apache.xmlbeans.XmlLong claimID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(CLAIMID$2);
            }
            target.set(claimID);
        }
    }
    
    /**
     * Gets the "QuestionnairesAssociations" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType getQuestionnairesAssociations()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType)get_store().find_element_user(QUESTIONNAIRESASSOCIATIONS$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "QuestionnairesAssociations" element
     */
    public void setQuestionnairesAssociations(com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType questionnairesAssociations)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType)get_store().find_element_user(QUESTIONNAIRESASSOCIATIONS$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType)get_store().add_element_user(QUESTIONNAIRESASSOCIATIONS$4);
            }
            target.set(questionnairesAssociations);
        }
    }
    
    /**
     * Appends and returns a new empty "QuestionnairesAssociations" element
     */
    public com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType addNewQuestionnairesAssociations()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType target = null;
            target = (com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType)get_store().add_element_user(QUESTIONNAIRESASSOCIATIONS$4);
            return target;
        }
    }
}
