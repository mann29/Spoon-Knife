/*
 * XML Type:  ClaimInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML ClaimInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class ClaimInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType
{
    private static final long serialVersionUID = 1L;
    
    public ClaimInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CLAIMNUMBER$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimNumber");
    private static final javax.xml.namespace.QName ISDRAFTCLAIMFLAG$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsDraftClaimFlag");
    private static final javax.xml.namespace.QName POLICYNUMBER$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PolicyNumber");
    private static final javax.xml.namespace.QName POLICYSTATEPROVINCE$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PolicyStateProvince");
    private static final javax.xml.namespace.QName DATEOFLOSS$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DateOfLoss");
    private static final javax.xml.namespace.QName DATEREPORTED$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DateReported");
    private static final javax.xml.namespace.QName CAUSEOFLOSS$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CauseOfLoss");
    private static final javax.xml.namespace.QName UNDERWRITINGCOMPANY$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "UnderwritingCompany");
    private static final javax.xml.namespace.QName CATFLAG$16 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CatFlag");
    private static final javax.xml.namespace.QName LOSSSTATEPROVINCE$18 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LossStateProvince");
    
    
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
     * Gets the "PolicyNumber" element
     */
    public java.lang.String getPolicyNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYNUMBER$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PolicyNumber" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType xgetPolicyNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType)get_store().find_element_user(POLICYNUMBER$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "PolicyNumber" element
     */
    public boolean isSetPolicyNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(POLICYNUMBER$4) != 0;
        }
    }
    
    /**
     * Sets the "PolicyNumber" element
     */
    public void setPolicyNumber(java.lang.String policyNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POLICYNUMBER$4);
            }
            target.setStringValue(policyNumber);
        }
    }
    
    /**
     * Sets (as xml) the "PolicyNumber" element
     */
    public void xsetPolicyNumber(com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType policyNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType)get_store().find_element_user(POLICYNUMBER$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType)get_store().add_element_user(POLICYNUMBER$4);
            }
            target.set(policyNumber);
        }
    }
    
    /**
     * Unsets the "PolicyNumber" element
     */
    public void unsetPolicyNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(POLICYNUMBER$4, 0);
        }
    }
    
    /**
     * Gets the "PolicyStateProvince" element
     */
    public java.lang.String getPolicyStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYSTATEPROVINCE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PolicyStateProvince" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetPolicyStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(POLICYSTATEPROVINCE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "PolicyStateProvince" element
     */
    public boolean isSetPolicyStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(POLICYSTATEPROVINCE$6) != 0;
        }
    }
    
    /**
     * Sets the "PolicyStateProvince" element
     */
    public void setPolicyStateProvince(java.lang.String policyStateProvince)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(POLICYSTATEPROVINCE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(POLICYSTATEPROVINCE$6);
            }
            target.setStringValue(policyStateProvince);
        }
    }
    
    /**
     * Sets (as xml) the "PolicyStateProvince" element
     */
    public void xsetPolicyStateProvince(com.mitchell.schemas.mitchellSuffixRqRs.String2Type policyStateProvince)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(POLICYSTATEPROVINCE$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(POLICYSTATEPROVINCE$6);
            }
            target.set(policyStateProvince);
        }
    }
    
    /**
     * Unsets the "PolicyStateProvince" element
     */
    public void unsetPolicyStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(POLICYSTATEPROVINCE$6, 0);
        }
    }
    
    /**
     * Gets the "DateOfLoss" element
     */
    public java.util.Calendar getDateOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEOFLOSS$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DateOfLoss" element
     */
    public org.apache.xmlbeans.XmlDate xgetDateOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DATEOFLOSS$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "DateOfLoss" element
     */
    public boolean isSetDateOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEOFLOSS$8) != 0;
        }
    }
    
    /**
     * Sets the "DateOfLoss" element
     */
    public void setDateOfLoss(java.util.Calendar dateOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEOFLOSS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEOFLOSS$8);
            }
            target.setCalendarValue(dateOfLoss);
        }
    }
    
    /**
     * Sets (as xml) the "DateOfLoss" element
     */
    public void xsetDateOfLoss(org.apache.xmlbeans.XmlDate dateOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DATEOFLOSS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DATEOFLOSS$8);
            }
            target.set(dateOfLoss);
        }
    }
    
    /**
     * Unsets the "DateOfLoss" element
     */
    public void unsetDateOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEOFLOSS$8, 0);
        }
    }
    
    /**
     * Gets the "DateReported" element
     */
    public java.util.Calendar getDateReported()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEREPORTED$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "DateReported" element
     */
    public org.apache.xmlbeans.XmlDate xgetDateReported()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DATEREPORTED$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "DateReported" element
     */
    public boolean isSetDateReported()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEREPORTED$10) != 0;
        }
    }
    
    /**
     * Sets the "DateReported" element
     */
    public void setDateReported(java.util.Calendar dateReported)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEREPORTED$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEREPORTED$10);
            }
            target.setCalendarValue(dateReported);
        }
    }
    
    /**
     * Sets (as xml) the "DateReported" element
     */
    public void xsetDateReported(org.apache.xmlbeans.XmlDate dateReported)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DATEREPORTED$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DATEREPORTED$10);
            }
            target.set(dateReported);
        }
    }
    
    /**
     * Unsets the "DateReported" element
     */
    public void unsetDateReported()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEREPORTED$10, 0);
        }
    }
    
    /**
     * Gets the "CauseOfLoss" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum.Enum getCauseOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSEOFLOSS$12, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CauseOfLoss" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum xgetCauseOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum)get_store().find_element_user(CAUSEOFLOSS$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "CauseOfLoss" element
     */
    public boolean isSetCauseOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CAUSEOFLOSS$12) != 0;
        }
    }
    
    /**
     * Sets the "CauseOfLoss" element
     */
    public void setCauseOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum.Enum causeOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CAUSEOFLOSS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CAUSEOFLOSS$12);
            }
            target.setEnumValue(causeOfLoss);
        }
    }
    
    /**
     * Sets (as xml) the "CauseOfLoss" element
     */
    public void xsetCauseOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum causeOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum)get_store().find_element_user(CAUSEOFLOSS$12, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum)get_store().add_element_user(CAUSEOFLOSS$12);
            }
            target.set(causeOfLoss);
        }
    }
    
    /**
     * Unsets the "CauseOfLoss" element
     */
    public void unsetCauseOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CAUSEOFLOSS$12, 0);
        }
    }
    
    /**
     * Gets the "UnderwritingCompany" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getUnderwritingCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(UNDERWRITINGCOMPANY$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "UnderwritingCompany" element
     */
    public boolean isSetUnderwritingCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(UNDERWRITINGCOMPANY$14) != 0;
        }
    }
    
    /**
     * Sets the "UnderwritingCompany" element
     */
    public void setUnderwritingCompany(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType underwritingCompany)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().find_element_user(UNDERWRITINGCOMPANY$14, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(UNDERWRITINGCOMPANY$14);
            }
            target.set(underwritingCompany);
        }
    }
    
    /**
     * Appends and returns a new empty "UnderwritingCompany" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewUnderwritingCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType)get_store().add_element_user(UNDERWRITINGCOMPANY$14);
            return target;
        }
    }
    
    /**
     * Unsets the "UnderwritingCompany" element
     */
    public void unsetUnderwritingCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(UNDERWRITINGCOMPANY$14, 0);
        }
    }
    
    /**
     * Gets the "CatFlag" element
     */
    public boolean getCatFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATFLAG$16, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "CatFlag" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetCatFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CATFLAG$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "CatFlag" element
     */
    public boolean isSetCatFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CATFLAG$16) != 0;
        }
    }
    
    /**
     * Sets the "CatFlag" element
     */
    public void setCatFlag(boolean catFlag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CATFLAG$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CATFLAG$16);
            }
            target.setBooleanValue(catFlag);
        }
    }
    
    /**
     * Sets (as xml) the "CatFlag" element
     */
    public void xsetCatFlag(org.apache.xmlbeans.XmlBoolean catFlag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CATFLAG$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(CATFLAG$16);
            }
            target.set(catFlag);
        }
    }
    
    /**
     * Unsets the "CatFlag" element
     */
    public void unsetCatFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CATFLAG$16, 0);
        }
    }
    
    /**
     * Gets the "LossStateProvince" element
     */
    public java.lang.String getLossStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSSTATEPROVINCE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LossStateProvince" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetLossStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(LOSSSTATEPROVINCE$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "LossStateProvince" element
     */
    public boolean isSetLossStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOSSSTATEPROVINCE$18) != 0;
        }
    }
    
    /**
     * Sets the "LossStateProvince" element
     */
    public void setLossStateProvince(java.lang.String lossStateProvince)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSSTATEPROVINCE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOSSSTATEPROVINCE$18);
            }
            target.setStringValue(lossStateProvince);
        }
    }
    
    /**
     * Sets (as xml) the "LossStateProvince" element
     */
    public void xsetLossStateProvince(com.mitchell.schemas.mitchellSuffixRqRs.String2Type lossStateProvince)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(LOSSSTATEPROVINCE$18, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(LOSSSTATEPROVINCE$18);
            }
            target.set(lossStateProvince);
        }
    }
    
    /**
     * Unsets the "LossStateProvince" element
     */
    public void unsetLossStateProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOSSSTATEPROVINCE$18, 0);
        }
    }
}
