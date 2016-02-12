/*
 * XML Type:  PropertyInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML PropertyInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class PropertyInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType
{
    private static final long serialVersionUID = 1L;
    
    public PropertyInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PROPERTYDAMAGEASSIGNMENT$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PropertyDamageAssignment");
    
    
    /**
     * Gets the "PropertyDamageAssignment" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType getPropertyDamageAssignment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType)get_store().find_element_user(PROPERTYDAMAGEASSIGNMENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "PropertyDamageAssignment" element
     */
    public void setPropertyDamageAssignment(com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType propertyDamageAssignment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType)get_store().find_element_user(PROPERTYDAMAGEASSIGNMENT$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType)get_store().add_element_user(PROPERTYDAMAGEASSIGNMENT$0);
            }
            target.set(propertyDamageAssignment);
        }
    }
    
    /**
     * Appends and returns a new empty "PropertyDamageAssignment" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType addNewPropertyDamageAssignment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyDamageAssignmentType)get_store().add_element_user(PROPERTYDAMAGEASSIGNMENT$0);
            return target;
        }
    }
}
