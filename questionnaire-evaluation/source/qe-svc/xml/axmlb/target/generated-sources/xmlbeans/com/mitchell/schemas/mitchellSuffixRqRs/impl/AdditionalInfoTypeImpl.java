/*
 * XML Type:  AdditionalInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML AdditionalInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class AdditionalInfoTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType
{
    private static final long serialVersionUID = 1L;
    
    public AdditionalInfoTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NAMEVALUEPAIR$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "NameValuePair");
    
    
    /**
     * Gets array of all "NameValuePair" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[] getNameValuePairArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(NAMEVALUEPAIR$0, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "NameValuePair" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType getNameValuePairArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType)get_store().find_element_user(NAMEVALUEPAIR$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "NameValuePair" element
     */
    public int sizeOfNameValuePairArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NAMEVALUEPAIR$0);
        }
    }
    
    /**
     * Sets array of all "NameValuePair" element
     */
    public void setNameValuePairArray(com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[] nameValuePairArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(nameValuePairArray, NAMEVALUEPAIR$0);
        }
    }
    
    /**
     * Sets ith "NameValuePair" element
     */
    public void setNameValuePairArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType nameValuePair)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType)get_store().find_element_user(NAMEVALUEPAIR$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(nameValuePair);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "NameValuePair" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType insertNewNameValuePair(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType)get_store().insert_element_user(NAMEVALUEPAIR$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "NameValuePair" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType addNewNameValuePair()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType)get_store().add_element_user(NAMEVALUEPAIR$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "NameValuePair" element
     */
    public void removeNameValuePair(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NAMEVALUEPAIR$0, i);
        }
    }
}
