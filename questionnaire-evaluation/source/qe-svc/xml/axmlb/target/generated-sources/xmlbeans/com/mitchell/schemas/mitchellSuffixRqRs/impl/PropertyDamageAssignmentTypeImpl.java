/*
 * XML Type:  PropertyDamageAssignmentType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML PropertyDamageAssignmentType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class PropertyDamageAssignmentTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType
{
    private static final long serialVersionUID = 1L;
    
    public PropertyDamageAssignmentTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PROPERTYTYPE$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PropertyType");
    private static final javax.xml.namespace.QName PROPERTYDESCRIPTION$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PropertyDescription");
    private static final javax.xml.namespace.QName PROPERTYADDRESS$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PropertyAddress");
    
    
    /**
     * Gets the "PropertyType" element
     */
    public java.lang.String getPropertyType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROPERTYTYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PropertyType" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetPropertyType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(PROPERTYTYPE$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "PropertyType" element
     */
    public void setPropertyType(java.lang.String propertyType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROPERTYTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROPERTYTYPE$0);
            }
            target.setStringValue(propertyType);
        }
    }
    
    /**
     * Sets (as xml) the "PropertyType" element
     */
    public void xsetPropertyType(com.mitchell.schemas.mitchellSuffixRqRs.String20Type propertyType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(PROPERTYTYPE$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(PROPERTYTYPE$0);
            }
            target.set(propertyType);
        }
    }
    
    /**
     * Gets the "PropertyDescription" element
     */
    public java.lang.String getPropertyDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROPERTYDESCRIPTION$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PropertyDescription" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String4000Type xgetPropertyDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String4000Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String4000Type)get_store().find_element_user(PROPERTYDESCRIPTION$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "PropertyDescription" element
     */
    public boolean isSetPropertyDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROPERTYDESCRIPTION$2) != 0;
        }
    }
    
    /**
     * Sets the "PropertyDescription" element
     */
    public void setPropertyDescription(java.lang.String propertyDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROPERTYDESCRIPTION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROPERTYDESCRIPTION$2);
            }
            target.setStringValue(propertyDescription);
        }
    }
    
    /**
     * Sets (as xml) the "PropertyDescription" element
     */
    public void xsetPropertyDescription(com.mitchell.schemas.mitchellSuffixRqRs.String4000Type propertyDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String4000Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String4000Type)get_store().find_element_user(PROPERTYDESCRIPTION$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String4000Type)get_store().add_element_user(PROPERTYDESCRIPTION$2);
            }
            target.set(propertyDescription);
        }
    }
    
    /**
     * Unsets the "PropertyDescription" element
     */
    public void unsetPropertyDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROPERTYDESCRIPTION$2, 0);
        }
    }
    
    /**
     * Gets the "PropertyAddress" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType getPropertyAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(PROPERTYADDRESS$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "PropertyAddress" element
     */
    public boolean isSetPropertyAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROPERTYADDRESS$4) != 0;
        }
    }
    
    /**
     * Sets the "PropertyAddress" element
     */
    public void setPropertyAddress(com.mitchell.schemas.mitchellSuffixRqRs.AddressType propertyAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(PROPERTYADDRESS$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(PROPERTYADDRESS$4);
            }
            target.set(propertyAddress);
        }
    }
    
    /**
     * Appends and returns a new empty "PropertyAddress" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType addNewPropertyAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(PROPERTYADDRESS$4);
            return target;
        }
    }
    
    /**
     * Unsets the "PropertyAddress" element
     */
    public void unsetPropertyAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROPERTYADDRESS$4, 0);
        }
    }
}
