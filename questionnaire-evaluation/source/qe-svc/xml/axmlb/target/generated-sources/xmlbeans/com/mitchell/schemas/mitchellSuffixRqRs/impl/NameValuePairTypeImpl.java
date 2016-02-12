/*
 * XML Type:  NameValuePairType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML NameValuePairType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class NameValuePairTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType
{
    private static final long serialVersionUID = 1L;
    
    public NameValuePairTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NAME$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Name");
    private static final javax.xml.namespace.QName VALUE$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Value");
    
    
    /**
     * Gets the "Name" element
     */
    public java.lang.String getName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Name" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(NAME$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Name" element
     */
    public void setName(java.lang.String name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAME$0);
            }
            target.setStringValue(name);
        }
    }
    
    /**
     * Sets (as xml) the "Name" element
     */
    public void xsetName(com.mitchell.schemas.mitchellSuffixRqRs.String255Type name)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(NAME$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(NAME$0);
            }
            target.set(name);
        }
    }
    
    /**
     * Gets array of all "Value" elements
     */
    public java.lang.String[] getValueArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VALUE$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "Value" element
     */
    public java.lang.String getValueArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALUE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "Value" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String255Type[] xgetValueArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(VALUE$2, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type[] result = new com.mitchell.schemas.mitchellSuffixRqRs.String255Type[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "Value" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String255Type xgetValueArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(VALUE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)target;
        }
    }
    
    /**
     * Returns number of "Value" element
     */
    public int sizeOfValueArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VALUE$2);
        }
    }
    
    /**
     * Sets array of all "Value" element
     */
    public void setValueArray(java.lang.String[] valueArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(valueArray, VALUE$2);
        }
    }
    
    /**
     * Sets ith "Value" element
     */
    public void setValueArray(int i, java.lang.String value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VALUE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(value);
        }
    }
    
    /**
     * Sets (as xml) array of all "Value" element
     */
    public void xsetValueArray(com.mitchell.schemas.mitchellSuffixRqRs.String255Type[]valueArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(valueArray, VALUE$2);
        }
    }
    
    /**
     * Sets (as xml) ith "Value" element
     */
    public void xsetValueArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.String255Type value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().find_element_user(VALUE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(value);
        }
    }
    
    /**
     * Inserts the value as the ith "Value" element
     */
    public void insertValue(int i, java.lang.String value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(VALUE$2, i);
            target.setStringValue(value);
        }
    }
    
    /**
     * Appends the value as the last "Value" element
     */
    public void addValue(java.lang.String value)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VALUE$2);
            target.setStringValue(value);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Value" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String255Type insertNewValue(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().insert_element_user(VALUE$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Value" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String255Type addNewValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String255Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String255Type)get_store().add_element_user(VALUE$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "Value" element
     */
    public void removeValue(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VALUE$2, i);
        }
    }
}
