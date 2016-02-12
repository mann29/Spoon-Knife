/*
 * XML Type:  OtherRefInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML OtherRefInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class OtherRefInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType
{
    private static final long serialVersionUID = 1L;
    
    public OtherRefInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OTHERREFERENCENAME$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "OtherReferenceName");
    private static final javax.xml.namespace.QName OTHERREFNUM$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "OtherRefNum");
    
    
    /**
     * Gets the "OtherReferenceName" element
     */
    public java.lang.String getOtherReferenceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OTHERREFERENCENAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OtherReferenceName" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetOtherReferenceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(OTHERREFERENCENAME$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OtherReferenceName" element
     */
    public void setOtherReferenceName(java.lang.String otherReferenceName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OTHERREFERENCENAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OTHERREFERENCENAME$0);
            }
            target.setStringValue(otherReferenceName);
        }
    }
    
    /**
     * Sets (as xml) the "OtherReferenceName" element
     */
    public void xsetOtherReferenceName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type otherReferenceName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(OTHERREFERENCENAME$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(OTHERREFERENCENAME$0);
            }
            target.set(otherReferenceName);
        }
    }
    
    /**
     * Gets the "OtherRefNum" element
     */
    public java.lang.String getOtherRefNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OTHERREFNUM$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OtherRefNum" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetOtherRefNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(OTHERREFNUM$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OtherRefNum" element
     */
    public void setOtherRefNum(java.lang.String otherRefNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OTHERREFNUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OTHERREFNUM$2);
            }
            target.setStringValue(otherRefNum);
        }
    }
    
    /**
     * Sets (as xml) the "OtherRefNum" element
     */
    public void xsetOtherRefNum(com.mitchell.schemas.mitchellSuffixRqRs.String50Type otherRefNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(OTHERREFNUM$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(OTHERREFNUM$2);
            }
            target.set(otherRefNum);
        }
    }
}
