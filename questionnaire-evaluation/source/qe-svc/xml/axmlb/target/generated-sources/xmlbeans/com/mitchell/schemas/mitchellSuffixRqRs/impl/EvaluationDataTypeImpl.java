/*
 * XML Type:  EvaluationDataType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML EvaluationDataType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class EvaluationDataTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType
{
    private static final long serialVersionUID = 1L;
    
    public EvaluationDataTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EXTERNALEVALUATIONID$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalEvaluationID");
    private static final javax.xml.namespace.QName EVALUATIONRESULTCATEGORY$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationResultCategory");
    private static final javax.xml.namespace.QName EVALUATIONSCORE$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationScore");
    private static final javax.xml.namespace.QName EVALUATIONRESULT$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationResult");
    private static final javax.xml.namespace.QName EVALUATIONANSWERS$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationAnswers");
    
    
    /**
     * Gets the "ExternalEvaluationID" element
     */
    public java.lang.String getExternalEvaluationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALEVALUATIONID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ExternalEvaluationID" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetExternalEvaluationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(EXTERNALEVALUATIONID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ExternalEvaluationID" element
     */
    public void setExternalEvaluationID(java.lang.String externalEvaluationID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALEVALUATIONID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALEVALUATIONID$0);
            }
            target.setStringValue(externalEvaluationID);
        }
    }
    
    /**
     * Sets (as xml) the "ExternalEvaluationID" element
     */
    public void xsetExternalEvaluationID(com.mitchell.schemas.mitchellSuffixRqRs.String50Type externalEvaluationID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(EXTERNALEVALUATIONID$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(EXTERNALEVALUATIONID$0);
            }
            target.set(externalEvaluationID);
        }
    }
    
    /**
     * Gets the "EvaluationResultCategory" element
     */
    public java.lang.String getEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULTCATEGORY$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "EvaluationResultCategory" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(EVALUATIONRESULTCATEGORY$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "EvaluationResultCategory" element
     */
    public boolean isSetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EVALUATIONRESULTCATEGORY$2) != 0;
        }
    }
    
    /**
     * Sets the "EvaluationResultCategory" element
     */
    public void setEvaluationResultCategory(java.lang.String evaluationResultCategory)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULTCATEGORY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EVALUATIONRESULTCATEGORY$2);
            }
            target.setStringValue(evaluationResultCategory);
        }
    }
    
    /**
     * Sets (as xml) the "EvaluationResultCategory" element
     */
    public void xsetEvaluationResultCategory(com.mitchell.schemas.mitchellSuffixRqRs.String20Type evaluationResultCategory)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(EVALUATIONRESULTCATEGORY$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(EVALUATIONRESULTCATEGORY$2);
            }
            target.set(evaluationResultCategory);
        }
    }
    
    /**
     * Unsets the "EvaluationResultCategory" element
     */
    public void unsetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EVALUATIONRESULTCATEGORY$2, 0);
        }
    }
    
    /**
     * Gets the "EvaluationScore" element
     */
    public int getEvaluationScore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONSCORE$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "EvaluationScore" element
     */
    public org.apache.xmlbeans.XmlInt xgetEvaluationScore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EVALUATIONSCORE$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "EvaluationScore" element
     */
    public boolean isSetEvaluationScore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EVALUATIONSCORE$4) != 0;
        }
    }
    
    /**
     * Sets the "EvaluationScore" element
     */
    public void setEvaluationScore(int evaluationScore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONSCORE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EVALUATIONSCORE$4);
            }
            target.setIntValue(evaluationScore);
        }
    }
    
    /**
     * Sets (as xml) the "EvaluationScore" element
     */
    public void xsetEvaluationScore(org.apache.xmlbeans.XmlInt evaluationScore)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(EVALUATIONSCORE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(EVALUATIONSCORE$4);
            }
            target.set(evaluationScore);
        }
    }
    
    /**
     * Unsets the "EvaluationScore" element
     */
    public void unsetEvaluationScore()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EVALUATIONSCORE$4, 0);
        }
    }
    
    /**
     * Gets the "EvaluationResult" element
     */
    public boolean getEvaluationResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULT$6, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "EvaluationResult" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetEvaluationResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EVALUATIONRESULT$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "EvaluationResult" element
     */
    public boolean isSetEvaluationResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EVALUATIONRESULT$6) != 0;
        }
    }
    
    /**
     * Sets the "EvaluationResult" element
     */
    public void setEvaluationResult(boolean evaluationResult)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULT$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EVALUATIONRESULT$6);
            }
            target.setBooleanValue(evaluationResult);
        }
    }
    
    /**
     * Sets (as xml) the "EvaluationResult" element
     */
    public void xsetEvaluationResult(org.apache.xmlbeans.XmlBoolean evaluationResult)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EVALUATIONRESULT$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(EVALUATIONRESULT$6);
            }
            target.set(evaluationResult);
        }
    }
    
    /**
     * Unsets the "EvaluationResult" element
     */
    public void unsetEvaluationResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EVALUATIONRESULT$6, 0);
        }
    }
    
    /**
     * Gets the "EvaluationAnswers" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType getEvaluationAnswers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().find_element_user(EVALUATIONANSWERS$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "EvaluationAnswers" element
     */
    public boolean isSetEvaluationAnswers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EVALUATIONANSWERS$8) != 0;
        }
    }
    
    /**
     * Sets the "EvaluationAnswers" element
     */
    public void setEvaluationAnswers(com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType evaluationAnswers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().find_element_user(EVALUATIONANSWERS$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().add_element_user(EVALUATIONANSWERS$8);
            }
            target.set(evaluationAnswers);
        }
    }
    
    /**
     * Appends and returns a new empty "EvaluationAnswers" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType addNewEvaluationAnswers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().add_element_user(EVALUATIONANSWERS$8);
            return target;
        }
    }
    
    /**
     * Unsets the "EvaluationAnswers" element
     */
    public void unsetEvaluationAnswers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EVALUATIONANSWERS$8, 0);
        }
    }
}
