/*
 * XML Type:  RefInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML RefInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class RefInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType
{
    private static final long serialVersionUID = 1L;
    
    public RefInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OTHERREFERENCEINFO$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "OtherReferenceInfo");
    
    
    /**
     * Gets array of all "OtherReferenceInfo" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType[] getOtherReferenceInfoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(OTHERREFERENCEINFO$0, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "OtherReferenceInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType getOtherReferenceInfoArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType)get_store().find_element_user(OTHERREFERENCEINFO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "OtherReferenceInfo" element
     */
    public int sizeOfOtherReferenceInfoArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OTHERREFERENCEINFO$0);
        }
    }
    
    /**
     * Sets array of all "OtherReferenceInfo" element
     */
    public void setOtherReferenceInfoArray(com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType[] otherReferenceInfoArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(otherReferenceInfoArray, OTHERREFERENCEINFO$0);
        }
    }
    
    /**
     * Sets ith "OtherReferenceInfo" element
     */
    public void setOtherReferenceInfoArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType otherReferenceInfo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType)get_store().find_element_user(OTHERREFERENCEINFO$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(otherReferenceInfo);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "OtherReferenceInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType insertNewOtherReferenceInfo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType)get_store().insert_element_user(OTHERREFERENCEINFO$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "OtherReferenceInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType addNewOtherReferenceInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.OtherRefInfoType)get_store().add_element_user(OTHERREFERENCEINFO$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "OtherReferenceInfo" element
     */
    public void removeOtherReferenceInfo(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OTHERREFERENCEINFO$0, i);
        }
    }
}
