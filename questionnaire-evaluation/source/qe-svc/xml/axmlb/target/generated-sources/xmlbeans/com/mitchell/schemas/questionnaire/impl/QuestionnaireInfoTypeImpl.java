/*
 * XML Type:  QuestionnaireInfoType
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnaireInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire.impl;
/**
 * An XML QuestionnaireInfoType(@http://www.mitchell.com/schemas/questionnaire).
 *
 * This is a complex type.
 */
public class QuestionnaireInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.questionnaire.QuestionnaireInfoType
{
    private static final long serialVersionUID = 1L;
    
    public QuestionnaireInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName QUESTIONNAIREID$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "QuestionnaireID");
    private static final javax.xml.namespace.QName QUESTIONNAIREDOCID$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "QuestionnaireDocID");
    private static final javax.xml.namespace.QName CLAIMEXPOSUREID$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/questionnaire", "ClaimExposureID");
    
    
    /**
     * Gets the "QuestionnaireID" element
     */
    public long getQuestionnaireID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUESTIONNAIREID$0, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "QuestionnaireID" element
     */
    public org.apache.xmlbeans.XmlLong xgetQuestionnaireID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUESTIONNAIREID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "QuestionnaireID" element
     */
    public void setQuestionnaireID(long questionnaireID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUESTIONNAIREID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUESTIONNAIREID$0);
            }
            target.setLongValue(questionnaireID);
        }
    }
    
    /**
     * Sets (as xml) the "QuestionnaireID" element
     */
    public void xsetQuestionnaireID(org.apache.xmlbeans.XmlLong questionnaireID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUESTIONNAIREID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(QUESTIONNAIREID$0);
            }
            target.set(questionnaireID);
        }
    }
    
    /**
     * Gets the "QuestionnaireDocID" element
     */
    public long getQuestionnaireDocID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUESTIONNAIREDOCID$2, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "QuestionnaireDocID" element
     */
    public org.apache.xmlbeans.XmlLong xgetQuestionnaireDocID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUESTIONNAIREDOCID$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "QuestionnaireDocID" element
     */
    public boolean isSetQuestionnaireDocID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(QUESTIONNAIREDOCID$2) != 0;
        }
    }
    
    /**
     * Sets the "QuestionnaireDocID" element
     */
    public void setQuestionnaireDocID(long questionnaireDocID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUESTIONNAIREDOCID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUESTIONNAIREDOCID$2);
            }
            target.setLongValue(questionnaireDocID);
        }
    }
    
    /**
     * Sets (as xml) the "QuestionnaireDocID" element
     */
    public void xsetQuestionnaireDocID(org.apache.xmlbeans.XmlLong questionnaireDocID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(QUESTIONNAIREDOCID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(QUESTIONNAIREDOCID$2);
            }
            target.set(questionnaireDocID);
        }
    }
    
    /**
     * Unsets the "QuestionnaireDocID" element
     */
    public void unsetQuestionnaireDocID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(QUESTIONNAIREDOCID$2, 0);
        }
    }
    
    /**
     * Gets the "ClaimExposureID" element
     */
    public long getClaimExposureID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMEXPOSUREID$4, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "ClaimExposureID" element
     */
    public org.apache.xmlbeans.XmlLong xgetClaimExposureID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMEXPOSUREID$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "ClaimExposureID" element
     */
    public boolean isSetClaimExposureID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMEXPOSUREID$4) != 0;
        }
    }
    
    /**
     * Sets the "ClaimExposureID" element
     */
    public void setClaimExposureID(long claimExposureID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMEXPOSUREID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMEXPOSUREID$4);
            }
            target.setLongValue(claimExposureID);
        }
    }
    
    /**
     * Sets (as xml) the "ClaimExposureID" element
     */
    public void xsetClaimExposureID(org.apache.xmlbeans.XmlLong claimExposureID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMEXPOSUREID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(CLAIMEXPOSUREID$4);
            }
            target.set(claimExposureID);
        }
    }
    
    /**
     * Unsets the "ClaimExposureID" element
     */
    public void unsetClaimExposureID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMEXPOSUREID$4, 0);
        }
    }
}
