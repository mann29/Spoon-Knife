/*
 * An XML document type.
 * Localname: MitchellSuffixSvcRqRs
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * A document containing one MitchellSuffixSvcRqRs(@http://www.mitchell.com/schemas/MitchellSuffixRqRs) element.
 *
 * This is a complex type.
 */
public class MitchellSuffixSvcRqRsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument
{
    private static final long serialVersionUID = 1L;
    
    public MitchellSuffixSvcRqRsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MITCHELLSUFFIXSVCRQRS$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "MitchellSuffixSvcRqRs");
    
    
    /**
     * Gets the "MitchellSuffixSvcRqRs" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs getMitchellSuffixSvcRqRs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs)get_store().find_element_user(MITCHELLSUFFIXSVCRQRS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "MitchellSuffixSvcRqRs" element
     */
    public void setMitchellSuffixSvcRqRs(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs mitchellSuffixSvcRqRs)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs)get_store().find_element_user(MITCHELLSUFFIXSVCRQRS$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs)get_store().add_element_user(MITCHELLSUFFIXSVCRQRS$0);
            }
            target.set(mitchellSuffixSvcRqRs);
        }
    }
    
    /**
     * Appends and returns a new empty "MitchellSuffixSvcRqRs" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs addNewMitchellSuffixSvcRqRs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs)get_store().add_element_user(MITCHELLSUFFIXSVCRQRS$0);
            return target;
        }
    }
    /**
     * An XML MitchellSuffixSvcRqRs(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
     *
     * This is a complex type.
     */
    public static class MitchellSuffixSvcRqRsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs
    {
        private static final long serialVersionUID = 1L;
        
        public MitchellSuffixSvcRqRsImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName EVALUATIONRQ$0 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationRq");
        private static final javax.xml.namespace.QName EVALUATIONRS$2 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationRs");
        private static final javax.xml.namespace.QName APPRAISALASSIGNMENTRQ$4 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AppraisalAssignmentRq");
        private static final javax.xml.namespace.QName APPRAISALASSIGNMENTRS$6 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AppraisalAssignmentRs");
        private static final javax.xml.namespace.QName FINALIZE$8 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Finalize");
        private static final javax.xml.namespace.QName COMPANYCODE$10 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CompanyCode");
        private static final javax.xml.namespace.QName RENTALASSIGNMENTRQ$12 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RentalAssignmentRq");
        private static final javax.xml.namespace.QName RENTALASSIGNMENTRS$14 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RentalAssignmentRs");
        private static final javax.xml.namespace.QName SALVAGEASSIGNMENTRQ$16 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SalvageAssignmentRq");
        private static final javax.xml.namespace.QName SALVAGEASSIGNMENTRS$18 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SalvageAssignmentRs");
        
        
        /**
         * Gets the "EvaluationRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq getEvaluationRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq)get_store().find_element_user(EVALUATIONRQ$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "EvaluationRq" element
         */
        public boolean isSetEvaluationRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(EVALUATIONRQ$0) != 0;
            }
        }
        
        /**
         * Sets the "EvaluationRq" element
         */
        public void setEvaluationRq(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq evaluationRq)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq)get_store().find_element_user(EVALUATIONRQ$0, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq)get_store().add_element_user(EVALUATIONRQ$0);
                }
                target.set(evaluationRq);
            }
        }
        
        /**
         * Appends and returns a new empty "EvaluationRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq addNewEvaluationRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq)get_store().add_element_user(EVALUATIONRQ$0);
                return target;
            }
        }
        
        /**
         * Unsets the "EvaluationRq" element
         */
        public void unsetEvaluationRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(EVALUATIONRQ$0, 0);
            }
        }
        
        /**
         * Gets the "EvaluationRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType getEvaluationRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType)get_store().find_element_user(EVALUATIONRS$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "EvaluationRs" element
         */
        public boolean isSetEvaluationRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(EVALUATIONRS$2) != 0;
            }
        }
        
        /**
         * Sets the "EvaluationRs" element
         */
        public void setEvaluationRs(com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType evaluationRs)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType)get_store().find_element_user(EVALUATIONRS$2, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType)get_store().add_element_user(EVALUATIONRS$2);
                }
                target.set(evaluationRs);
            }
        }
        
        /**
         * Appends and returns a new empty "EvaluationRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType addNewEvaluationRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType)get_store().add_element_user(EVALUATIONRS$2);
                return target;
            }
        }
        
        /**
         * Unsets the "EvaluationRs" element
         */
        public void unsetEvaluationRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(EVALUATIONRS$2, 0);
            }
        }
        
        /**
         * Gets the "AppraisalAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq getAppraisalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq)get_store().find_element_user(APPRAISALASSIGNMENTRQ$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "AppraisalAssignmentRq" element
         */
        public boolean isSetAppraisalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(APPRAISALASSIGNMENTRQ$4) != 0;
            }
        }
        
        /**
         * Sets the "AppraisalAssignmentRq" element
         */
        public void setAppraisalAssignmentRq(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq appraisalAssignmentRq)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq)get_store().find_element_user(APPRAISALASSIGNMENTRQ$4, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq)get_store().add_element_user(APPRAISALASSIGNMENTRQ$4);
                }
                target.set(appraisalAssignmentRq);
            }
        }
        
        /**
         * Appends and returns a new empty "AppraisalAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq addNewAppraisalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq)get_store().add_element_user(APPRAISALASSIGNMENTRQ$4);
                return target;
            }
        }
        
        /**
         * Unsets the "AppraisalAssignmentRq" element
         */
        public void unsetAppraisalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(APPRAISALASSIGNMENTRQ$4, 0);
            }
        }
        
        /**
         * Gets the "AppraisalAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs getAppraisalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs)get_store().find_element_user(APPRAISALASSIGNMENTRS$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "AppraisalAssignmentRs" element
         */
        public boolean isSetAppraisalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(APPRAISALASSIGNMENTRS$6) != 0;
            }
        }
        
        /**
         * Sets the "AppraisalAssignmentRs" element
         */
        public void setAppraisalAssignmentRs(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs appraisalAssignmentRs)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs)get_store().find_element_user(APPRAISALASSIGNMENTRS$6, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs)get_store().add_element_user(APPRAISALASSIGNMENTRS$6);
                }
                target.set(appraisalAssignmentRs);
            }
        }
        
        /**
         * Appends and returns a new empty "AppraisalAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs addNewAppraisalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs)get_store().add_element_user(APPRAISALASSIGNMENTRS$6);
                return target;
            }
        }
        
        /**
         * Unsets the "AppraisalAssignmentRs" element
         */
        public void unsetAppraisalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(APPRAISALASSIGNMENTRS$6, 0);
            }
        }
        
        /**
         * Gets the "Finalize" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize getFinalize()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize)get_store().find_element_user(FINALIZE$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "Finalize" element
         */
        public boolean isSetFinalize()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FINALIZE$8) != 0;
            }
        }
        
        /**
         * Sets the "Finalize" element
         */
        public void setFinalize(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize finalize)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize)get_store().find_element_user(FINALIZE$8, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize)get_store().add_element_user(FINALIZE$8);
                }
                target.set(finalize);
            }
        }
        
        /**
         * Appends and returns a new empty "Finalize" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize addNewFinalize()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize)get_store().add_element_user(FINALIZE$8);
                return target;
            }
        }
        
        /**
         * Unsets the "Finalize" element
         */
        public void unsetFinalize()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FINALIZE$8, 0);
            }
        }
        
        /**
         * Gets the "CompanyCode" element
         */
        public java.lang.String getCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODE$10, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "CompanyCode" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(COMPANYCODE$10, 0);
                return target;
            }
        }
        
        /**
         * Sets the "CompanyCode" element
         */
        public void setCompanyCode(java.lang.String companyCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODE$10, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODE$10);
                }
                target.setStringValue(companyCode);
            }
        }
        
        /**
         * Sets (as xml) the "CompanyCode" element
         */
        public void xsetCompanyCode(com.mitchell.schemas.mitchellSuffixRqRs.String2Type companyCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(COMPANYCODE$10, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(COMPANYCODE$10);
                }
                target.set(companyCode);
            }
        }
        
        /**
         * Gets the "RentalAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq getRentalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq)get_store().find_element_user(RENTALASSIGNMENTRQ$12, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "RentalAssignmentRq" element
         */
        public boolean isSetRentalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RENTALASSIGNMENTRQ$12) != 0;
            }
        }
        
        /**
         * Sets the "RentalAssignmentRq" element
         */
        public void setRentalAssignmentRq(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq rentalAssignmentRq)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq)get_store().find_element_user(RENTALASSIGNMENTRQ$12, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq)get_store().add_element_user(RENTALASSIGNMENTRQ$12);
                }
                target.set(rentalAssignmentRq);
            }
        }
        
        /**
         * Appends and returns a new empty "RentalAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq addNewRentalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq)get_store().add_element_user(RENTALASSIGNMENTRQ$12);
                return target;
            }
        }
        
        /**
         * Unsets the "RentalAssignmentRq" element
         */
        public void unsetRentalAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RENTALASSIGNMENTRQ$12, 0);
            }
        }
        
        /**
         * Gets the "RentalAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs getRentalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs)get_store().find_element_user(RENTALASSIGNMENTRS$14, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "RentalAssignmentRs" element
         */
        public boolean isSetRentalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RENTALASSIGNMENTRS$14) != 0;
            }
        }
        
        /**
         * Sets the "RentalAssignmentRs" element
         */
        public void setRentalAssignmentRs(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs rentalAssignmentRs)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs)get_store().find_element_user(RENTALASSIGNMENTRS$14, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs)get_store().add_element_user(RENTALASSIGNMENTRS$14);
                }
                target.set(rentalAssignmentRs);
            }
        }
        
        /**
         * Appends and returns a new empty "RentalAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs addNewRentalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs)get_store().add_element_user(RENTALASSIGNMENTRS$14);
                return target;
            }
        }
        
        /**
         * Unsets the "RentalAssignmentRs" element
         */
        public void unsetRentalAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RENTALASSIGNMENTRS$14, 0);
            }
        }
        
        /**
         * Gets the "SalvageAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq getSalvageAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq)get_store().find_element_user(SALVAGEASSIGNMENTRQ$16, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "SalvageAssignmentRq" element
         */
        public boolean isSetSalvageAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SALVAGEASSIGNMENTRQ$16) != 0;
            }
        }
        
        /**
         * Sets the "SalvageAssignmentRq" element
         */
        public void setSalvageAssignmentRq(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq salvageAssignmentRq)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq)get_store().find_element_user(SALVAGEASSIGNMENTRQ$16, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq)get_store().add_element_user(SALVAGEASSIGNMENTRQ$16);
                }
                target.set(salvageAssignmentRq);
            }
        }
        
        /**
         * Appends and returns a new empty "SalvageAssignmentRq" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq addNewSalvageAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq)get_store().add_element_user(SALVAGEASSIGNMENTRQ$16);
                return target;
            }
        }
        
        /**
         * Unsets the "SalvageAssignmentRq" element
         */
        public void unsetSalvageAssignmentRq()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SALVAGEASSIGNMENTRQ$16, 0);
            }
        }
        
        /**
         * Gets the "SalvageAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs getSalvageAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs)get_store().find_element_user(SALVAGEASSIGNMENTRS$18, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * True if has "SalvageAssignmentRs" element
         */
        public boolean isSetSalvageAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SALVAGEASSIGNMENTRS$18) != 0;
            }
        }
        
        /**
         * Sets the "SalvageAssignmentRs" element
         */
        public void setSalvageAssignmentRs(com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs salvageAssignmentRs)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs)get_store().find_element_user(SALVAGEASSIGNMENTRS$18, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs)get_store().add_element_user(SALVAGEASSIGNMENTRS$18);
                }
                target.set(salvageAssignmentRs);
            }
        }
        
        /**
         * Appends and returns a new empty "SalvageAssignmentRs" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs addNewSalvageAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs)get_store().add_element_user(SALVAGEASSIGNMENTRS$18);
                return target;
            }
        }
        
        /**
         * Unsets the "SalvageAssignmentRs" element
         */
        public void unsetSalvageAssignmentRs()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SALVAGEASSIGNMENTRS$18, 0);
            }
        }
        /**
         * An XML EvaluationRq(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class EvaluationRqImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.EvaluationRq
        {
            private static final long serialVersionUID = 1L;
            
            public EvaluationRqImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName EXTERNALEVALUATIONID$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalEvaluationID");
            private static final javax.xml.namespace.QName EVALUATIONANSWERS$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationAnswers");
            private static final javax.xml.namespace.QName CLAIMINFO$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimInfo");
            private static final javax.xml.namespace.QName SUFFIX$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Suffix");
            
            
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
             * Gets the "EvaluationAnswers" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType getEvaluationAnswers()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().find_element_user(EVALUATIONANSWERS$2, 0);
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
                    return get_store().count_elements(EVALUATIONANSWERS$2) != 0;
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
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().find_element_user(EVALUATIONANSWERS$2, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().add_element_user(EVALUATIONANSWERS$2);
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
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType)get_store().add_element_user(EVALUATIONANSWERS$2);
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
                    get_store().remove_element(EVALUATIONANSWERS$2, 0);
                }
            }
            
            /**
             * Gets the "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType getClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "ClaimInfo" element
             */
            public boolean isSetClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(CLAIMINFO$4) != 0;
                }
            }
            
            /**
             * Sets the "ClaimInfo" element
             */
            public void setClaimInfo(com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType claimInfo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$4);
                    }
                    target.set(claimInfo);
                }
            }
            
            /**
             * Appends and returns a new empty "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType addNewClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$4);
                    return target;
                }
            }
            
            /**
             * Unsets the "ClaimInfo" element
             */
            public void unsetClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(CLAIMINFO$4, 0);
                }
            }
            
            /**
             * Gets the "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType getSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "Suffix" element
             */
            public boolean isSetSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(SUFFIX$6) != 0;
                }
            }
            
            /**
             * Sets the "Suffix" element
             */
            public void setSuffix(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType suffix)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$6);
                    }
                    target.set(suffix);
                }
            }
            
            /**
             * Appends and returns a new empty "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType addNewSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$6);
                    return target;
                }
            }
            
            /**
             * Unsets the "Suffix" element
             */
            public void unsetSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(SUFFIX$6, 0);
                }
            }
        }
        /**
         * An XML AppraisalAssignmentRq(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class AppraisalAssignmentRqImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRq
        {
            private static final long serialVersionUID = 1L;
            
            public AppraisalAssignmentRqImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName EXTERNALASSIGNMENTID$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalAssignmentID");
            private static final javax.xml.namespace.QName CLAIMINFO$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimInfo");
            private static final javax.xml.namespace.QName SUFFIX$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Suffix");
            private static final javax.xml.namespace.QName EXTERNALEVALUATIONID$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalEvaluationID");
            private static final javax.xml.namespace.QName EVALUATIONANSWERS$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationAnswers");
            private static final javax.xml.namespace.QName EXPERTISELIST$10 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExpertiseList");
            private static final javax.xml.namespace.QName METHODOFINSPECTION$12 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "MethodOfInspection");
            private static final javax.xml.namespace.QName APPRAISERDETAILS$14 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AppraiserDetails");
            private static final javax.xml.namespace.QName DISPATCHCENTER$16 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DispatchCenter");
            
            
            /**
             * Gets the "ExternalAssignmentID" element
             */
            public java.lang.String getExternalAssignmentID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALASSIGNMENTID$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalAssignmentID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalAssignmentID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALASSIGNMENTID$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalAssignmentID" element
             */
            public void setExternalAssignmentID(java.lang.String externalAssignmentID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALASSIGNMENTID$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALASSIGNMENTID$0);
                    }
                    target.setStringValue(externalAssignmentID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalAssignmentID" element
             */
            public void xsetExternalAssignmentID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalAssignmentID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALASSIGNMENTID$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALASSIGNMENTID$0);
                    }
                    target.set(externalAssignmentID);
                }
            }
            
            /**
             * Gets the "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType getClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimInfo" element
             */
            public void setClaimInfo(com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType claimInfo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    }
                    target.set(claimInfo);
                }
            }
            
            /**
             * Appends and returns a new empty "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType addNewClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    return target;
                }
            }
            
            /**
             * Gets the "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType getSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "Suffix" element
             */
            public void setSuffix(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType suffix)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    }
                    target.set(suffix);
                }
            }
            
            /**
             * Appends and returns a new empty "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType addNewSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    return target;
                }
            }
            
            /**
             * Gets the "ExternalEvaluationID" element
             */
            public java.lang.String getExternalEvaluationID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALEVALUATIONID$6, 0);
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
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(EXTERNALEVALUATIONID$6, 0);
                    return target;
                }
            }
            
            /**
             * True if has "ExternalEvaluationID" element
             */
            public boolean isSetExternalEvaluationID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(EXTERNALEVALUATIONID$6) != 0;
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
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALEVALUATIONID$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALEVALUATIONID$6);
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
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(EXTERNALEVALUATIONID$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(EXTERNALEVALUATIONID$6);
                    }
                    target.set(externalEvaluationID);
                }
            }
            
            /**
             * Unsets the "ExternalEvaluationID" element
             */
            public void unsetExternalEvaluationID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(EXTERNALEVALUATIONID$6, 0);
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
            
            /**
             * Gets the "ExpertiseList" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType getExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().find_element_user(EXPERTISELIST$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "ExpertiseList" element
             */
            public boolean isSetExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(EXPERTISELIST$10) != 0;
                }
            }
            
            /**
             * Sets the "ExpertiseList" element
             */
            public void setExpertiseList(com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType expertiseList)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().find_element_user(EXPERTISELIST$10, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().add_element_user(EXPERTISELIST$10);
                    }
                    target.set(expertiseList);
                }
            }
            
            /**
             * Appends and returns a new empty "ExpertiseList" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType addNewExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().add_element_user(EXPERTISELIST$10);
                    return target;
                }
            }
            
            /**
             * Unsets the "ExpertiseList" element
             */
            public void unsetExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(EXPERTISELIST$10, 0);
                }
            }
            
            /**
             * Gets the "MethodOfInspection" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum getMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(METHODOFINSPECTION$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum)target.getEnumValue();
                }
            }
            
            /**
             * Gets (as xml) the "MethodOfInspection" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum xgetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().find_element_user(METHODOFINSPECTION$12, 0);
                    return target;
                }
            }
            
            /**
             * True if has "MethodOfInspection" element
             */
            public boolean isSetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(METHODOFINSPECTION$12) != 0;
                }
            }
            
            /**
             * Sets the "MethodOfInspection" element
             */
            public void setMethodOfInspection(com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum methodOfInspection)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(METHODOFINSPECTION$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(METHODOFINSPECTION$12);
                    }
                    target.setEnumValue(methodOfInspection);
                }
            }
            
            /**
             * Sets (as xml) the "MethodOfInspection" element
             */
            public void xsetMethodOfInspection(com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum methodOfInspection)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().find_element_user(METHODOFINSPECTION$12, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().add_element_user(METHODOFINSPECTION$12);
                    }
                    target.set(methodOfInspection);
                }
            }
            
            /**
             * Unsets the "MethodOfInspection" element
             */
            public void unsetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(METHODOFINSPECTION$12, 0);
                }
            }
            
            /**
             * Gets the "AppraiserDetails" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ResourceType getAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().find_element_user(APPRAISERDETAILS$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "AppraiserDetails" element
             */
            public boolean isSetAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(APPRAISERDETAILS$14) != 0;
                }
            }
            
            /**
             * Sets the "AppraiserDetails" element
             */
            public void setAppraiserDetails(com.mitchell.schemas.mitchellSuffixRqRs.ResourceType appraiserDetails)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().find_element_user(APPRAISERDETAILS$14, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().add_element_user(APPRAISERDETAILS$14);
                    }
                    target.set(appraiserDetails);
                }
            }
            
            /**
             * Appends and returns a new empty "AppraiserDetails" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ResourceType addNewAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().add_element_user(APPRAISERDETAILS$14);
                    return target;
                }
            }
            
            /**
             * Unsets the "AppraiserDetails" element
             */
            public void unsetAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(APPRAISERDETAILS$14, 0);
                }
            }
            
            /**
             * Gets the "DispatchCenter" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(DISPATCHCENTER$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "DispatchCenter" element
             */
            public boolean isSetDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(DISPATCHCENTER$16) != 0;
                }
            }
            
            /**
             * Sets the "DispatchCenter" element
             */
            public void setDispatchCenter(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType dispatchCenter)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(DISPATCHCENTER$16, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(DISPATCHCENTER$16);
                    }
                    target.set(dispatchCenter);
                }
            }
            
            /**
             * Appends and returns a new empty "DispatchCenter" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(DISPATCHCENTER$16);
                    return target;
                }
            }
            
            /**
             * Unsets the "DispatchCenter" element
             */
            public void unsetDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(DISPATCHCENTER$16, 0);
                }
            }
        }
        /**
         * An XML AppraisalAssignmentRs(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class AppraisalAssignmentRsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.AppraisalAssignmentRs
        {
            private static final long serialVersionUID = 1L;
            
            public AppraisalAssignmentRsImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CLAIMNUMBER$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimNumber");
            private static final javax.xml.namespace.QName ISDRAFTCLAIMFLAG$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsDraftClaimFlag");
            private static final javax.xml.namespace.QName SUFFIXNUMBER$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixNumber");
            private static final javax.xml.namespace.QName EXTERNALASSIGNMENTID$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalAssignmentID");
            private static final javax.xml.namespace.QName METHODOFINSPECTION$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "MethodOfInspection");
            private static final javax.xml.namespace.QName SCHEDULEPREFERENCES$10 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SchedulePreferences");
            private static final javax.xml.namespace.QName APPRAISERDETAILS$12 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AppraiserDetails");
            private static final javax.xml.namespace.QName EXPERTISELIST$14 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExpertiseList");
            private static final javax.xml.namespace.QName DISPATCHCENTER$16 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DispatchCenter");
            
            
            /**
             * Gets the "ClaimNumber" element
             */
            public java.lang.String getClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ClaimNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimNumber" element
             */
            public void setClaimNumber(java.lang.String claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.setStringValue(claimNumber);
                }
            }
            
            /**
             * Sets (as xml) the "ClaimNumber" element
             */
            public void xsetClaimNumber(com.mitchell.schemas.mitchellSuffixRqRs.String20Type claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.set(claimNumber);
                }
            }
            
            /**
             * Gets the "IsDraftClaimFlag" element
             */
            public boolean getIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "IsDraftClaimFlag" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "IsDraftClaimFlag" element
             */
            public void setIsDraftClaimFlag(boolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.setBooleanValue(isDraftClaimFlag);
                }
            }
            
            /**
             * Sets (as xml) the "IsDraftClaimFlag" element
             */
            public void xsetIsDraftClaimFlag(org.apache.xmlbeans.XmlBoolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.set(isDraftClaimFlag);
                }
            }
            
            /**
             * Gets the "SuffixNumber" element
             */
            public java.lang.String getSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SuffixNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType xgetSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SuffixNumber" element
             */
            public void setSuffixNumber(java.lang.String suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.setStringValue(suffixNumber);
                }
            }
            
            /**
             * Sets (as xml) the "SuffixNumber" element
             */
            public void xsetSuffixNumber(com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.set(suffixNumber);
                }
            }
            
            /**
             * Gets the "ExternalAssignmentID" element
             */
            public java.lang.String getExternalAssignmentID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALASSIGNMENTID$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalAssignmentID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalAssignmentID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALASSIGNMENTID$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalAssignmentID" element
             */
            public void setExternalAssignmentID(java.lang.String externalAssignmentID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALASSIGNMENTID$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALASSIGNMENTID$6);
                    }
                    target.setStringValue(externalAssignmentID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalAssignmentID" element
             */
            public void xsetExternalAssignmentID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalAssignmentID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALASSIGNMENTID$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALASSIGNMENTID$6);
                    }
                    target.set(externalAssignmentID);
                }
            }
            
            /**
             * Gets the "MethodOfInspection" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum getMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(METHODOFINSPECTION$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum)target.getEnumValue();
                }
            }
            
            /**
             * Gets (as xml) the "MethodOfInspection" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum xgetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().find_element_user(METHODOFINSPECTION$8, 0);
                    return target;
                }
            }
            
            /**
             * True if has "MethodOfInspection" element
             */
            public boolean isSetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(METHODOFINSPECTION$8) != 0;
                }
            }
            
            /**
             * Sets the "MethodOfInspection" element
             */
            public void setMethodOfInspection(com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum.Enum methodOfInspection)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(METHODOFINSPECTION$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(METHODOFINSPECTION$8);
                    }
                    target.setEnumValue(methodOfInspection);
                }
            }
            
            /**
             * Sets (as xml) the "MethodOfInspection" element
             */
            public void xsetMethodOfInspection(com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum methodOfInspection)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().find_element_user(METHODOFINSPECTION$8, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.MOIEnum)get_store().add_element_user(METHODOFINSPECTION$8);
                    }
                    target.set(methodOfInspection);
                }
            }
            
            /**
             * Unsets the "MethodOfInspection" element
             */
            public void unsetMethodOfInspection()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(METHODOFINSPECTION$8, 0);
                }
            }
            
            /**
             * Gets the "SchedulePreferences" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType getSchedulePreferences()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType)get_store().find_element_user(SCHEDULEPREFERENCES$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "SchedulePreferences" element
             */
            public boolean isSetSchedulePreferences()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(SCHEDULEPREFERENCES$10) != 0;
                }
            }
            
            /**
             * Sets the "SchedulePreferences" element
             */
            public void setSchedulePreferences(com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType schedulePreferences)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType)get_store().find_element_user(SCHEDULEPREFERENCES$10, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType)get_store().add_element_user(SCHEDULEPREFERENCES$10);
                    }
                    target.set(schedulePreferences);
                }
            }
            
            /**
             * Appends and returns a new empty "SchedulePreferences" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType addNewSchedulePreferences()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType)get_store().add_element_user(SCHEDULEPREFERENCES$10);
                    return target;
                }
            }
            
            /**
             * Unsets the "SchedulePreferences" element
             */
            public void unsetSchedulePreferences()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(SCHEDULEPREFERENCES$10, 0);
                }
            }
            
            /**
             * Gets the "AppraiserDetails" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ResourceType getAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().find_element_user(APPRAISERDETAILS$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "AppraiserDetails" element
             */
            public boolean isSetAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(APPRAISERDETAILS$12) != 0;
                }
            }
            
            /**
             * Sets the "AppraiserDetails" element
             */
            public void setAppraiserDetails(com.mitchell.schemas.mitchellSuffixRqRs.ResourceType appraiserDetails)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().find_element_user(APPRAISERDETAILS$12, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().add_element_user(APPRAISERDETAILS$12);
                    }
                    target.set(appraiserDetails);
                }
            }
            
            /**
             * Appends and returns a new empty "AppraiserDetails" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ResourceType addNewAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ResourceType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType)get_store().add_element_user(APPRAISERDETAILS$12);
                    return target;
                }
            }
            
            /**
             * Unsets the "AppraiserDetails" element
             */
            public void unsetAppraiserDetails()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(APPRAISERDETAILS$12, 0);
                }
            }
            
            /**
             * Gets the "ExpertiseList" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType getExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().find_element_user(EXPERTISELIST$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "ExpertiseList" element
             */
            public boolean isSetExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(EXPERTISELIST$14) != 0;
                }
            }
            
            /**
             * Sets the "ExpertiseList" element
             */
            public void setExpertiseList(com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType expertiseList)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().find_element_user(EXPERTISELIST$14, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().add_element_user(EXPERTISELIST$14);
                    }
                    target.set(expertiseList);
                }
            }
            
            /**
             * Appends and returns a new empty "ExpertiseList" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType addNewExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType)get_store().add_element_user(EXPERTISELIST$14);
                    return target;
                }
            }
            
            /**
             * Unsets the "ExpertiseList" element
             */
            public void unsetExpertiseList()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(EXPERTISELIST$14, 0);
                }
            }
            
            /**
             * Gets the "DispatchCenter" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(DISPATCHCENTER$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "DispatchCenter" element
             */
            public boolean isSetDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(DISPATCHCENTER$16) != 0;
                }
            }
            
            /**
             * Sets the "DispatchCenter" element
             */
            public void setDispatchCenter(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType dispatchCenter)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(DISPATCHCENTER$16, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(DISPATCHCENTER$16);
                    }
                    target.set(dispatchCenter);
                }
            }
            
            /**
             * Appends and returns a new empty "DispatchCenter" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(DISPATCHCENTER$16);
                    return target;
                }
            }
            
            /**
             * Unsets the "DispatchCenter" element
             */
            public void unsetDispatchCenter()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(DISPATCHCENTER$16, 0);
                }
            }
        }
        /**
         * An XML Finalize(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class FinalizeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.Finalize
        {
            private static final long serialVersionUID = 1L;
            
            public FinalizeImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName DRAFTCLAIMNUMBER$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DraftClaimNumber");
            private static final javax.xml.namespace.QName CLAIMINFO$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimInfo");
            private static final javax.xml.namespace.QName SUFFIX$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Suffix");
            private static final javax.xml.namespace.QName ISCLAIMCANCELREQUEST$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsClaimCancelRequest");
            private static final javax.xml.namespace.QName CLAIMADJUSTERID$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimAdjusterID");
            
            
            /**
             * Gets the "DraftClaimNumber" element
             */
            public java.lang.String getDraftClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRAFTCLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "DraftClaimNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetDraftClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(DRAFTCLAIMNUMBER$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "DraftClaimNumber" element
             */
            public void setDraftClaimNumber(java.lang.String draftClaimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRAFTCLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DRAFTCLAIMNUMBER$0);
                    }
                    target.setStringValue(draftClaimNumber);
                }
            }
            
            /**
             * Sets (as xml) the "DraftClaimNumber" element
             */
            public void xsetDraftClaimNumber(com.mitchell.schemas.mitchellSuffixRqRs.String20Type draftClaimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(DRAFTCLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(DRAFTCLAIMNUMBER$0);
                    }
                    target.set(draftClaimNumber);
                }
            }
            
            /**
             * Gets the "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType getClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimInfo" element
             */
            public void setClaimInfo(com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType claimInfo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    }
                    target.set(claimInfo);
                }
            }
            
            /**
             * Appends and returns a new empty "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType addNewClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    return target;
                }
            }
            
            /**
             * Gets array of all "Suffix" elements
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType[] getSuffixArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    java.util.List targetList = new java.util.ArrayList();
                    get_store().find_all_element_users(SUFFIX$4, targetList);
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.SuffixType[targetList.size()];
                    targetList.toArray(result);
                    return result;
                }
            }
            
            /**
             * Gets ith "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType getSuffixArray(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, i);
                    if (target == null)
                    {
                      throw new IndexOutOfBoundsException();
                    }
                    return target;
                }
            }
            
            /**
             * Returns number of "Suffix" element
             */
            public int sizeOfSuffixArray()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(SUFFIX$4);
                }
            }
            
            /**
             * Sets array of all "Suffix" element
             */
            public void setSuffixArray(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType[] suffixArray)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    arraySetterHelper(suffixArray, SUFFIX$4);
                }
            }
            
            /**
             * Sets ith "Suffix" element
             */
            public void setSuffixArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.SuffixType suffix)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, i);
                    if (target == null)
                    {
                      throw new IndexOutOfBoundsException();
                    }
                    target.set(suffix);
                }
            }
            
            /**
             * Inserts and returns a new empty value (as xml) as the ith "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType insertNewSuffix(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().insert_element_user(SUFFIX$4, i);
                    return target;
                }
            }
            
            /**
             * Appends and returns a new empty value (as xml) as the last "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType addNewSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    return target;
                }
            }
            
            /**
             * Removes the ith "Suffix" element
             */
            public void removeSuffix(int i)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(SUFFIX$4, i);
                }
            }
            
            /**
             * Gets the "IsClaimCancelRequest" element
             */
            public boolean getIsClaimCancelRequest()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCLAIMCANCELREQUEST$6, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "IsClaimCancelRequest" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetIsClaimCancelRequest()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCLAIMCANCELREQUEST$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "IsClaimCancelRequest" element
             */
            public void setIsClaimCancelRequest(boolean isClaimCancelRequest)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCLAIMCANCELREQUEST$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISCLAIMCANCELREQUEST$6);
                    }
                    target.setBooleanValue(isClaimCancelRequest);
                }
            }
            
            /**
             * Sets (as xml) the "IsClaimCancelRequest" element
             */
            public void xsetIsClaimCancelRequest(org.apache.xmlbeans.XmlBoolean isClaimCancelRequest)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCLAIMCANCELREQUEST$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISCLAIMCANCELREQUEST$6);
                    }
                    target.set(isClaimCancelRequest);
                }
            }
            
            /**
             * Gets the "ClaimAdjusterID" element
             */
            public java.lang.String getClaimAdjusterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMADJUSTERID$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ClaimAdjusterID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type xgetClaimAdjusterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(CLAIMADJUSTERID$8, 0);
                    return target;
                }
            }
            
            /**
             * True if has "ClaimAdjusterID" element
             */
            public boolean isSetClaimAdjusterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(CLAIMADJUSTERID$8) != 0;
                }
            }
            
            /**
             * Sets the "ClaimAdjusterID" element
             */
            public void setClaimAdjusterID(java.lang.String claimAdjusterID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMADJUSTERID$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMADJUSTERID$8);
                    }
                    target.setStringValue(claimAdjusterID);
                }
            }
            
            /**
             * Sets (as xml) the "ClaimAdjusterID" element
             */
            public void xsetClaimAdjusterID(com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type claimAdjusterID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(CLAIMADJUSTERID$8, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().add_element_user(CLAIMADJUSTERID$8);
                    }
                    target.set(claimAdjusterID);
                }
            }
            
            /**
             * Unsets the "ClaimAdjusterID" element
             */
            public void unsetClaimAdjusterID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(CLAIMADJUSTERID$8, 0);
                }
            }
        }
        /**
         * An XML RentalAssignmentRq(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class RentalAssignmentRqImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRq
        {
            private static final long serialVersionUID = 1L;
            
            public RentalAssignmentRqImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName EXTERNALRENTALID$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalRentalID");
            private static final javax.xml.namespace.QName CLAIMINFO$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimInfo");
            private static final javax.xml.namespace.QName SUFFIX$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Suffix");
            private static final javax.xml.namespace.QName POLICYRATE$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PolicyRate");
            private static final javax.xml.namespace.QName MAXPOLICYAMT$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "MaxPolicyAmt");
            
            
            /**
             * Gets the "ExternalRentalID" element
             */
            public java.lang.String getExternalRentalID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALRENTALID$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalRentalID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalRentalID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALRENTALID$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalRentalID" element
             */
            public void setExternalRentalID(java.lang.String externalRentalID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALRENTALID$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALRENTALID$0);
                    }
                    target.setStringValue(externalRentalID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalRentalID" element
             */
            public void xsetExternalRentalID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalRentalID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALRENTALID$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALRENTALID$0);
                    }
                    target.set(externalRentalID);
                }
            }
            
            /**
             * Gets the "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType getClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimInfo" element
             */
            public void setClaimInfo(com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType claimInfo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    }
                    target.set(claimInfo);
                }
            }
            
            /**
             * Appends and returns a new empty "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType addNewClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    return target;
                }
            }
            
            /**
             * Gets the "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType getSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "Suffix" element
             */
            public void setSuffix(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType suffix)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    }
                    target.set(suffix);
                }
            }
            
            /**
             * Appends and returns a new empty "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType addNewSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    return target;
                }
            }
            
            /**
             * Gets the "PolicyRate" element
             */
            public java.math.BigDecimal getPolicyRate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYRATE$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "PolicyRate" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.Money xgetPolicyRate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.Money target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().find_element_user(POLICYRATE$6, 0);
                    return target;
                }
            }
            
            /**
             * True if has "PolicyRate" element
             */
            public boolean isSetPolicyRate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(POLICYRATE$6) != 0;
                }
            }
            
            /**
             * Sets the "PolicyRate" element
             */
            public void setPolicyRate(java.math.BigDecimal policyRate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYRATE$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POLICYRATE$6);
                    }
                    target.setBigDecimalValue(policyRate);
                }
            }
            
            /**
             * Sets (as xml) the "PolicyRate" element
             */
            public void xsetPolicyRate(com.mitchell.schemas.mitchellSuffixRqRs.Money policyRate)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.Money target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().find_element_user(POLICYRATE$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().add_element_user(POLICYRATE$6);
                    }
                    target.set(policyRate);
                }
            }
            
            /**
             * Unsets the "PolicyRate" element
             */
            public void unsetPolicyRate()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(POLICYRATE$6, 0);
                }
            }
            
            /**
             * Gets the "MaxPolicyAmt" element
             */
            public java.math.BigDecimal getMaxPolicyAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXPOLICYAMT$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigDecimalValue();
                }
            }
            
            /**
             * Gets (as xml) the "MaxPolicyAmt" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.Money xgetMaxPolicyAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.Money target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().find_element_user(MAXPOLICYAMT$8, 0);
                    return target;
                }
            }
            
            /**
             * True if has "MaxPolicyAmt" element
             */
            public boolean isSetMaxPolicyAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(MAXPOLICYAMT$8) != 0;
                }
            }
            
            /**
             * Sets the "MaxPolicyAmt" element
             */
            public void setMaxPolicyAmt(java.math.BigDecimal maxPolicyAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXPOLICYAMT$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAXPOLICYAMT$8);
                    }
                    target.setBigDecimalValue(maxPolicyAmt);
                }
            }
            
            /**
             * Sets (as xml) the "MaxPolicyAmt" element
             */
            public void xsetMaxPolicyAmt(com.mitchell.schemas.mitchellSuffixRqRs.Money maxPolicyAmt)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.Money target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().find_element_user(MAXPOLICYAMT$8, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.Money)get_store().add_element_user(MAXPOLICYAMT$8);
                    }
                    target.set(maxPolicyAmt);
                }
            }
            
            /**
             * Unsets the "MaxPolicyAmt" element
             */
            public void unsetMaxPolicyAmt()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(MAXPOLICYAMT$8, 0);
                }
            }
        }
        /**
         * An XML RentalAssignmentRs(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class RentalAssignmentRsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.RentalAssignmentRs
        {
            private static final long serialVersionUID = 1L;
            
            public RentalAssignmentRsImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CLAIMNUMBER$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimNumber");
            private static final javax.xml.namespace.QName ISDRAFTCLAIMFLAG$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsDraftClaimFlag");
            private static final javax.xml.namespace.QName SUFFIXNUMBER$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixNumber");
            private static final javax.xml.namespace.QName EXTERNALRENTALID$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalRentalID");
            private static final javax.xml.namespace.QName MANAGEDBY$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ManagedBy");
            private static final javax.xml.namespace.QName RENTALVENDORNAME$10 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RentalVendorName");
            private static final javax.xml.namespace.QName RENTALVENDORCODE$12 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RentalVendorCode");
            private static final javax.xml.namespace.QName RENTALVENDORLOCATION$14 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RentalVendorLocation");
            private static final javax.xml.namespace.QName AUTHORIZEDDAYS$16 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AuthorizedDays");
            
            
            /**
             * Gets the "ClaimNumber" element
             */
            public java.lang.String getClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ClaimNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimNumber" element
             */
            public void setClaimNumber(java.lang.String claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.setStringValue(claimNumber);
                }
            }
            
            /**
             * Sets (as xml) the "ClaimNumber" element
             */
            public void xsetClaimNumber(com.mitchell.schemas.mitchellSuffixRqRs.String20Type claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.set(claimNumber);
                }
            }
            
            /**
             * Gets the "IsDraftClaimFlag" element
             */
            public boolean getIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "IsDraftClaimFlag" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "IsDraftClaimFlag" element
             */
            public void setIsDraftClaimFlag(boolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.setBooleanValue(isDraftClaimFlag);
                }
            }
            
            /**
             * Sets (as xml) the "IsDraftClaimFlag" element
             */
            public void xsetIsDraftClaimFlag(org.apache.xmlbeans.XmlBoolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.set(isDraftClaimFlag);
                }
            }
            
            /**
             * Gets the "SuffixNumber" element
             */
            public java.lang.String getSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SuffixNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType xgetSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SuffixNumber" element
             */
            public void setSuffixNumber(java.lang.String suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.setStringValue(suffixNumber);
                }
            }
            
            /**
             * Sets (as xml) the "SuffixNumber" element
             */
            public void xsetSuffixNumber(com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.set(suffixNumber);
                }
            }
            
            /**
             * Gets the "ExternalRentalID" element
             */
            public java.lang.String getExternalRentalID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALRENTALID$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalRentalID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalRentalID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALRENTALID$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalRentalID" element
             */
            public void setExternalRentalID(java.lang.String externalRentalID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALRENTALID$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALRENTALID$6);
                    }
                    target.setStringValue(externalRentalID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalRentalID" element
             */
            public void xsetExternalRentalID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalRentalID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALRENTALID$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALRENTALID$6);
                    }
                    target.set(externalRentalID);
                }
            }
            
            /**
             * Gets the "ManagedBy" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(MANAGEDBY$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "ManagedBy" element
             */
            public boolean isSetManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(MANAGEDBY$8) != 0;
                }
            }
            
            /**
             * Sets the "ManagedBy" element
             */
            public void setManagedBy(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty managedBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(MANAGEDBY$8, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(MANAGEDBY$8);
                    }
                    target.set(managedBy);
                }
            }
            
            /**
             * Appends and returns a new empty "ManagedBy" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(MANAGEDBY$8);
                    return target;
                }
            }
            
            /**
             * Unsets the "ManagedBy" element
             */
            public void unsetManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(MANAGEDBY$8, 0);
                }
            }
            
            /**
             * Gets the "RentalVendorName" element
             */
            public java.lang.String getRentalVendorName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RENTALVENDORNAME$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "RentalVendorName" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String100Type xgetRentalVendorName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String100Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().find_element_user(RENTALVENDORNAME$10, 0);
                    return target;
                }
            }
            
            /**
             * True if has "RentalVendorName" element
             */
            public boolean isSetRentalVendorName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(RENTALVENDORNAME$10) != 0;
                }
            }
            
            /**
             * Sets the "RentalVendorName" element
             */
            public void setRentalVendorName(java.lang.String rentalVendorName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RENTALVENDORNAME$10, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RENTALVENDORNAME$10);
                    }
                    target.setStringValue(rentalVendorName);
                }
            }
            
            /**
             * Sets (as xml) the "RentalVendorName" element
             */
            public void xsetRentalVendorName(com.mitchell.schemas.mitchellSuffixRqRs.String100Type rentalVendorName)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String100Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().find_element_user(RENTALVENDORNAME$10, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().add_element_user(RENTALVENDORNAME$10);
                    }
                    target.set(rentalVendorName);
                }
            }
            
            /**
             * Unsets the "RentalVendorName" element
             */
            public void unsetRentalVendorName()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(RENTALVENDORNAME$10, 0);
                }
            }
            
            /**
             * Gets the "RentalVendorCode" element
             */
            public java.lang.String getRentalVendorCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RENTALVENDORCODE$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "RentalVendorCode" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String10Type xgetRentalVendorCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String10Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().find_element_user(RENTALVENDORCODE$12, 0);
                    return target;
                }
            }
            
            /**
             * True if has "RentalVendorCode" element
             */
            public boolean isSetRentalVendorCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(RENTALVENDORCODE$12) != 0;
                }
            }
            
            /**
             * Sets the "RentalVendorCode" element
             */
            public void setRentalVendorCode(java.lang.String rentalVendorCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RENTALVENDORCODE$12, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RENTALVENDORCODE$12);
                    }
                    target.setStringValue(rentalVendorCode);
                }
            }
            
            /**
             * Sets (as xml) the "RentalVendorCode" element
             */
            public void xsetRentalVendorCode(com.mitchell.schemas.mitchellSuffixRqRs.String10Type rentalVendorCode)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String10Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().find_element_user(RENTALVENDORCODE$12, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().add_element_user(RENTALVENDORCODE$12);
                    }
                    target.set(rentalVendorCode);
                }
            }
            
            /**
             * Unsets the "RentalVendorCode" element
             */
            public void unsetRentalVendorCode()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(RENTALVENDORCODE$12, 0);
                }
            }
            
            /**
             * Gets the "RentalVendorLocation" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getRentalVendorLocation()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(RENTALVENDORLOCATION$14, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "RentalVendorLocation" element
             */
            public boolean isSetRentalVendorLocation()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(RENTALVENDORLOCATION$14) != 0;
                }
            }
            
            /**
             * Sets the "RentalVendorLocation" element
             */
            public void setRentalVendorLocation(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType rentalVendorLocation)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(RENTALVENDORLOCATION$14, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(RENTALVENDORLOCATION$14);
                    }
                    target.set(rentalVendorLocation);
                }
            }
            
            /**
             * Appends and returns a new empty "RentalVendorLocation" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewRentalVendorLocation()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(RENTALVENDORLOCATION$14);
                    return target;
                }
            }
            
            /**
             * Unsets the "RentalVendorLocation" element
             */
            public void unsetRentalVendorLocation()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(RENTALVENDORLOCATION$14, 0);
                }
            }
            
            /**
             * Gets the "AuthorizedDays" element
             */
            public java.math.BigInteger getAuthorizedDays()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHORIZEDDAYS$16, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getBigIntegerValue();
                }
            }
            
            /**
             * Gets (as xml) the "AuthorizedDays" element
             */
            public org.apache.xmlbeans.XmlInteger xgetAuthorizedDays()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInteger target = null;
                    target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(AUTHORIZEDDAYS$16, 0);
                    return target;
                }
            }
            
            /**
             * True if has "AuthorizedDays" element
             */
            public boolean isSetAuthorizedDays()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(AUTHORIZEDDAYS$16) != 0;
                }
            }
            
            /**
             * Sets the "AuthorizedDays" element
             */
            public void setAuthorizedDays(java.math.BigInteger authorizedDays)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHORIZEDDAYS$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTHORIZEDDAYS$16);
                    }
                    target.setBigIntegerValue(authorizedDays);
                }
            }
            
            /**
             * Sets (as xml) the "AuthorizedDays" element
             */
            public void xsetAuthorizedDays(org.apache.xmlbeans.XmlInteger authorizedDays)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlInteger target = null;
                    target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(AUTHORIZEDDAYS$16, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(AUTHORIZEDDAYS$16);
                    }
                    target.set(authorizedDays);
                }
            }
            
            /**
             * Unsets the "AuthorizedDays" element
             */
            public void unsetAuthorizedDays()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(AUTHORIZEDDAYS$16, 0);
                }
            }
        }
        /**
         * An XML SalvageAssignmentRq(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class SalvageAssignmentRqImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRq
        {
            private static final long serialVersionUID = 1L;
            
            public SalvageAssignmentRqImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName EXTERNALSALVAGEID$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalSalvageID");
            private static final javax.xml.namespace.QName CLAIMINFO$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimInfo");
            private static final javax.xml.namespace.QName SUFFIX$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Suffix");
            
            
            /**
             * Gets the "ExternalSalvageID" element
             */
            public java.lang.String getExternalSalvageID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALSALVAGEID$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalSalvageID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalSalvageID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALSALVAGEID$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalSalvageID" element
             */
            public void setExternalSalvageID(java.lang.String externalSalvageID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALSALVAGEID$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALSALVAGEID$0);
                    }
                    target.setStringValue(externalSalvageID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalSalvageID" element
             */
            public void xsetExternalSalvageID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalSalvageID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALSALVAGEID$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALSALVAGEID$0);
                    }
                    target.set(externalSalvageID);
                }
            }
            
            /**
             * Gets the "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType getClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimInfo" element
             */
            public void setClaimInfo(com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType claimInfo)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().find_element_user(CLAIMINFO$2, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    }
                    target.set(claimInfo);
                }
            }
            
            /**
             * Appends and returns a new empty "ClaimInfo" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType addNewClaimInfo()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType)get_store().add_element_user(CLAIMINFO$2);
                    return target;
                }
            }
            
            /**
             * Gets the "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType getSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * Sets the "Suffix" element
             */
            public void setSuffix(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType suffix)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().find_element_user(SUFFIX$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    }
                    target.set(suffix);
                }
            }
            
            /**
             * Appends and returns a new empty "Suffix" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType addNewSuffix()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType)get_store().add_element_user(SUFFIX$4);
                    return target;
                }
            }
        }
        /**
         * An XML SalvageAssignmentRs(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
         *
         * This is a complex type.
         */
        public static class SalvageAssignmentRsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument.MitchellSuffixSvcRqRs.SalvageAssignmentRs
        {
            private static final long serialVersionUID = 1L;
            
            public SalvageAssignmentRsImpl(org.apache.xmlbeans.SchemaType sType)
            {
                super(sType);
            }
            
            private static final javax.xml.namespace.QName CLAIMNUMBER$0 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimNumber");
            private static final javax.xml.namespace.QName ISDRAFTCLAIMFLAG$2 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsDraftClaimFlag");
            private static final javax.xml.namespace.QName SUFFIXNUMBER$4 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixNumber");
            private static final javax.xml.namespace.QName EXTERNALSALVAGEID$6 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExternalSalvageID");
            private static final javax.xml.namespace.QName SALVAGEASSIGNMENTTYPE$8 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SalvageAssignmentType");
            private static final javax.xml.namespace.QName MANAGEDBY$10 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ManagedBy");
            private static final javax.xml.namespace.QName SALVOR$12 = 
                new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Salvor");
            
            
            /**
             * Gets the "ClaimNumber" element
             */
            public java.lang.String getClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ClaimNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetClaimNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ClaimNumber" element
             */
            public void setClaimNumber(java.lang.String claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.setStringValue(claimNumber);
                }
            }
            
            /**
             * Sets (as xml) the "ClaimNumber" element
             */
            public void xsetClaimNumber(com.mitchell.schemas.mitchellSuffixRqRs.String20Type claimNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(CLAIMNUMBER$0, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(CLAIMNUMBER$0);
                    }
                    target.set(claimNumber);
                }
            }
            
            /**
             * Gets the "IsDraftClaimFlag" element
             */
            public boolean getIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      return false;
                    }
                    return target.getBooleanValue();
                }
            }
            
            /**
             * Gets (as xml) the "IsDraftClaimFlag" element
             */
            public org.apache.xmlbeans.XmlBoolean xgetIsDraftClaimFlag()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "IsDraftClaimFlag" element
             */
            public void setIsDraftClaimFlag(boolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.setBooleanValue(isDraftClaimFlag);
                }
            }
            
            /**
             * Sets (as xml) the "IsDraftClaimFlag" element
             */
            public void xsetIsDraftClaimFlag(org.apache.xmlbeans.XmlBoolean isDraftClaimFlag)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.XmlBoolean target = null;
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISDRAFTCLAIMFLAG$2, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISDRAFTCLAIMFLAG$2);
                    }
                    target.set(isDraftClaimFlag);
                }
            }
            
            /**
             * Gets the "SuffixNumber" element
             */
            public java.lang.String getSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "SuffixNumber" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType xgetSuffixNumber()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SuffixNumber" element
             */
            public void setSuffixNumber(java.lang.String suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.setStringValue(suffixNumber);
                }
            }
            
            /**
             * Sets (as xml) the "SuffixNumber" element
             */
            public void xsetSuffixNumber(com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType suffixNumber)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$4, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().add_element_user(SUFFIXNUMBER$4);
                    }
                    target.set(suffixNumber);
                }
            }
            
            /**
             * Gets the "ExternalSalvageID" element
             */
            public java.lang.String getExternalSalvageID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALSALVAGEID$6, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target.getStringValue();
                }
            }
            
            /**
             * Gets (as xml) the "ExternalSalvageID" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetExternalSalvageID()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALSALVAGEID$6, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "ExternalSalvageID" element
             */
            public void setExternalSalvageID(java.lang.String externalSalvageID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERNALSALVAGEID$6, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERNALSALVAGEID$6);
                    }
                    target.setStringValue(externalSalvageID);
                }
            }
            
            /**
             * Sets (as xml) the "ExternalSalvageID" element
             */
            public void xsetExternalSalvageID(com.mitchell.schemas.mitchellSuffixRqRs.String255Type externalSalvageID)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(EXTERNALSALVAGEID$6, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(EXTERNALSALVAGEID$6);
                    }
                    target.set(externalSalvageID);
                }
            }
            
            /**
             * Gets the "SalvageAssignmentType" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum.Enum getSalvageAssignmentType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALVAGEASSIGNMENTTYPE$8, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return (com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum.Enum)target.getEnumValue();
                }
            }
            
            /**
             * Gets (as xml) the "SalvageAssignmentType" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum xgetSalvageAssignmentType()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum)get_store().find_element_user(SALVAGEASSIGNMENTTYPE$8, 0);
                    return target;
                }
            }
            
            /**
             * Sets the "SalvageAssignmentType" element
             */
            public void setSalvageAssignmentType(com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum.Enum salvageAssignmentType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    org.apache.xmlbeans.SimpleValue target = null;
                    target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SALVAGEASSIGNMENTTYPE$8, 0);
                    if (target == null)
                    {
                      target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SALVAGEASSIGNMENTTYPE$8);
                    }
                    target.setEnumValue(salvageAssignmentType);
                }
            }
            
            /**
             * Sets (as xml) the "SalvageAssignmentType" element
             */
            public void xsetSalvageAssignmentType(com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum salvageAssignmentType)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum)get_store().find_element_user(SALVAGEASSIGNMENTTYPE$8, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.SalvageAssignmentTypeEnum)get_store().add_element_user(SALVAGEASSIGNMENTTYPE$8);
                    }
                    target.set(salvageAssignmentType);
                }
            }
            
            /**
             * Gets the "ManagedBy" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(MANAGEDBY$10, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "ManagedBy" element
             */
            public boolean isSetManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(MANAGEDBY$10) != 0;
                }
            }
            
            /**
             * Sets the "ManagedBy" element
             */
            public void setManagedBy(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty managedBy)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(MANAGEDBY$10, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(MANAGEDBY$10);
                    }
                    target.set(managedBy);
                }
            }
            
            /**
             * Appends and returns a new empty "ManagedBy" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(MANAGEDBY$10);
                    return target;
                }
            }
            
            /**
             * Unsets the "ManagedBy" element
             */
            public void unsetManagedBy()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(MANAGEDBY$10, 0);
                }
            }
            
            /**
             * Gets the "Salvor" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getSalvor()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(SALVOR$12, 0);
                    if (target == null)
                    {
                      return null;
                    }
                    return target;
                }
            }
            
            /**
             * True if has "Salvor" element
             */
            public boolean isSetSalvor()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    return get_store().count_elements(SALVOR$12) != 0;
                }
            }
            
            /**
             * Sets the "Salvor" element
             */
            public void setSalvor(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType salvor)
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(SALVOR$12, 0);
                    if (target == null)
                    {
                      target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(SALVOR$12);
                    }
                    target.set(salvor);
                }
            }
            
            /**
             * Appends and returns a new empty "Salvor" element
             */
            public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewSalvor()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(SALVOR$12);
                    return target;
                }
            }
            
            /**
             * Unsets the "Salvor" element
             */
            public void unsetSalvor()
            {
                synchronized (monitor())
                {
                    check_orphaned();
                    get_store().remove_element(SALVOR$12, 0);
                }
            }
        }
    }
}
