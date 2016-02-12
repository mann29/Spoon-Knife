/*
 * XML Type:  PhoneType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.PhoneType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML PhoneType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class PhoneTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.PhoneType
{
    private static final long serialVersionUID = 1L;
    
    public PhoneTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName QUALIFIER$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Qualifier");
    private static final javax.xml.namespace.QName NUMBER$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Number");
    
    
    /**
     * Gets the "Qualifier" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier.Enum getQualifier()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUALIFIER$0, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "Qualifier" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier xgetQualifier()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier)get_store().find_element_user(QUALIFIER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Qualifier" element
     */
    public void setQualifier(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier.Enum qualifier)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(QUALIFIER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(QUALIFIER$0);
            }
            target.setEnumValue(qualifier);
        }
    }
    
    /**
     * Sets (as xml) the "Qualifier" element
     */
    public void xsetQualifier(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier qualifier)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier)get_store().find_element_user(QUALIFIER$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier)get_store().add_element_user(QUALIFIER$0);
            }
            target.set(qualifier);
        }
    }
    
    /**
     * Gets the "Number" element
     */
    public java.lang.String getNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMBER$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Number" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension xgetNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension)get_store().find_element_user(NUMBER$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Number" element
     */
    public void setNumber(java.lang.String number)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUMBER$2);
            }
            target.setStringValue(number);
        }
    }
    
    /**
     * Sets (as xml) the "Number" element
     */
    public void xsetNumber(com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension number)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension)get_store().find_element_user(NUMBER$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension)get_store().add_element_user(NUMBER$2);
            }
            target.set(number);
        }
    }
    /**
     * An XML Qualifier(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
     *
     * This is an atomic type that is a restriction of com.mitchell.schemas.mitchellSuffixRqRs.PhoneType$Qualifier.
     */
    public static class QualifierImpl extends org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx implements com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier
    {
        private static final long serialVersionUID = 1L;
        
        public QualifierImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType, false);
        }
        
        protected QualifierImpl(org.apache.xmlbeans.SchemaType sType, boolean b)
        {
            super(sType, b);
        }
    }
}
