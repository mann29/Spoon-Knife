/*
 * XML Type:  CustomElementType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML CustomElementType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class CustomElementTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType
{
    private static final long serialVersionUID = 1L;
    
    public CustomElementTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CUSTOMELEMENTID$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementID");
    private static final javax.xml.namespace.QName CUSTOMELEMENTTEXT$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementText");
    private static final javax.xml.namespace.QName CUSTOMELEMENTCURRENCY$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementCurrency");
    private static final javax.xml.namespace.QName CUSTOMELEMENTDECIMAL$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementDecimal");
    private static final javax.xml.namespace.QName CUSTOMELEMENTDATE$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementDate");
    private static final javax.xml.namespace.QName CUSTOMELEMENTDATETIME$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementDateTime");
    private static final javax.xml.namespace.QName CUSTOMELEMENTIND$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElementInd");
    
    
    /**
     * Gets the "CustomElementID" element
     */
    public java.lang.String getCustomElementID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementID" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetCustomElementID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(CUSTOMELEMENTID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "CustomElementID" element
     */
    public void setCustomElementID(java.lang.String customElementID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTID$0);
            }
            target.setStringValue(customElementID);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementID" element
     */
    public void xsetCustomElementID(com.mitchell.schemas.mitchellSuffixRqRs.String80Type customElementID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(CUSTOMELEMENTID$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(CUSTOMELEMENTID$0);
            }
            target.set(customElementID);
        }
    }
    
    /**
     * Gets the "CustomElementText" element
     */
    public java.lang.String getCustomElementText()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTTEXT$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementText" element
     */
    public org.apache.xmlbeans.XmlString xgetCustomElementText()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTOMELEMENTTEXT$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementText" element
     */
    public boolean isSetCustomElementText()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTTEXT$2) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementText" element
     */
    public void setCustomElementText(java.lang.String customElementText)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTTEXT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTTEXT$2);
            }
            target.setStringValue(customElementText);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementText" element
     */
    public void xsetCustomElementText(org.apache.xmlbeans.XmlString customElementText)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTOMELEMENTTEXT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTOMELEMENTTEXT$2);
            }
            target.set(customElementText);
        }
    }
    
    /**
     * Unsets the "CustomElementText" element
     */
    public void unsetCustomElementText()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTTEXT$2, 0);
        }
    }
    
    /**
     * Gets the "CustomElementCurrency" element
     */
    public java.math.BigDecimal getCustomElementCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTCURRENCY$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementCurrency" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.Currency xgetCustomElementCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.Currency target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.Currency)get_store().find_element_user(CUSTOMELEMENTCURRENCY$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementCurrency" element
     */
    public boolean isSetCustomElementCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTCURRENCY$4) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementCurrency" element
     */
    public void setCustomElementCurrency(java.math.BigDecimal customElementCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTCURRENCY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTCURRENCY$4);
            }
            target.setBigDecimalValue(customElementCurrency);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementCurrency" element
     */
    public void xsetCustomElementCurrency(com.mitchell.schemas.mitchellSuffixRqRs.Currency customElementCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.Currency target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.Currency)get_store().find_element_user(CUSTOMELEMENTCURRENCY$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.Currency)get_store().add_element_user(CUSTOMELEMENTCURRENCY$4);
            }
            target.set(customElementCurrency);
        }
    }
    
    /**
     * Unsets the "CustomElementCurrency" element
     */
    public void unsetCustomElementCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTCURRENCY$4, 0);
        }
    }
    
    /**
     * Gets the "CustomElementDecimal" element
     */
    public java.math.BigDecimal getCustomElementDecimal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDECIMAL$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementDecimal" element
     */
    public org.apache.xmlbeans.XmlDecimal xgetCustomElementDecimal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDecimal target = null;
            target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(CUSTOMELEMENTDECIMAL$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementDecimal" element
     */
    public boolean isSetCustomElementDecimal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTDECIMAL$6) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementDecimal" element
     */
    public void setCustomElementDecimal(java.math.BigDecimal customElementDecimal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDECIMAL$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTDECIMAL$6);
            }
            target.setBigDecimalValue(customElementDecimal);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementDecimal" element
     */
    public void xsetCustomElementDecimal(org.apache.xmlbeans.XmlDecimal customElementDecimal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDecimal target = null;
            target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(CUSTOMELEMENTDECIMAL$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(CUSTOMELEMENTDECIMAL$6);
            }
            target.set(customElementDecimal);
        }
    }
    
    /**
     * Unsets the "CustomElementDecimal" element
     */
    public void unsetCustomElementDecimal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTDECIMAL$6, 0);
        }
    }
    
    /**
     * Gets the "CustomElementDate" element
     */
    public java.util.Calendar getCustomElementDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDATE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetCustomElementDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(CUSTOMELEMENTDATE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementDate" element
     */
    public boolean isSetCustomElementDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTDATE$8) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementDate" element
     */
    public void setCustomElementDate(java.util.Calendar customElementDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTDATE$8);
            }
            target.setCalendarValue(customElementDate);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementDate" element
     */
    public void xsetCustomElementDate(org.apache.xmlbeans.XmlDate customElementDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(CUSTOMELEMENTDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(CUSTOMELEMENTDATE$8);
            }
            target.set(customElementDate);
        }
    }
    
    /**
     * Unsets the "CustomElementDate" element
     */
    public void unsetCustomElementDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTDATE$8, 0);
        }
    }
    
    /**
     * Gets the "CustomElementDateTime" element
     */
    public java.util.Calendar getCustomElementDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDATETIME$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementDateTime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetCustomElementDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CUSTOMELEMENTDATETIME$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementDateTime" element
     */
    public boolean isSetCustomElementDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTDATETIME$10) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementDateTime" element
     */
    public void setCustomElementDateTime(java.util.Calendar customElementDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTDATETIME$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTDATETIME$10);
            }
            target.setCalendarValue(customElementDateTime);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementDateTime" element
     */
    public void xsetCustomElementDateTime(org.apache.xmlbeans.XmlDateTime customElementDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CUSTOMELEMENTDATETIME$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CUSTOMELEMENTDATETIME$10);
            }
            target.set(customElementDateTime);
        }
    }
    
    /**
     * Unsets the "CustomElementDateTime" element
     */
    public void unsetCustomElementDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTDATETIME$10, 0);
        }
    }
    
    /**
     * Gets the "CustomElementInd" element
     */
    public java.lang.String getCustomElementInd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTIND$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CustomElementInd" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.Indicator xgetCustomElementInd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.Indicator target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().find_element_user(CUSTOMELEMENTIND$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "CustomElementInd" element
     */
    public boolean isSetCustomElementInd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTIND$12) != 0;
        }
    }
    
    /**
     * Sets the "CustomElementInd" element
     */
    public void setCustomElementInd(java.lang.String customElementInd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTOMELEMENTIND$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTOMELEMENTIND$12);
            }
            target.setStringValue(customElementInd);
        }
    }
    
    /**
     * Sets (as xml) the "CustomElementInd" element
     */
    public void xsetCustomElementInd(com.mitchell.schemas.mitchellSuffixRqRs.Indicator customElementInd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.Indicator target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().find_element_user(CUSTOMELEMENTIND$12, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().add_element_user(CUSTOMELEMENTIND$12);
            }
            target.set(customElementInd);
        }
    }
    
    /**
     * Unsets the "CustomElementInd" element
     */
    public void unsetCustomElementInd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTIND$12, 0);
        }
    }
}
