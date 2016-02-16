/*
 * XML Type:  PersonDetailType
 * Namespace: http://www.mitchell.com/schemas/apddelivery
 * Java type: com.mitchell.schemas.apddelivery.PersonDetailType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.apddelivery.impl;
/**
 * An XML PersonDetailType(@http://www.mitchell.com/schemas/apddelivery).
 *
 * This is a complex type.
 */
public class PersonDetailTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.apddelivery.PersonDetailType
{
    private static final long serialVersionUID = 1L;
    
    public PersonDetailTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FIRSTNAME$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "FirstName");
    private static final javax.xml.namespace.QName LASTNAME$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "LastName");
    private static final javax.xml.namespace.QName PHONENUMBER$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "PhoneNumber");
    
    
    /**
     * Gets the "FirstName" element
     */
    public java.lang.String getFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FirstName" element
     */
    public org.apache.xmlbeans.XmlString xgetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "FirstName" element
     */
    public boolean isSetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FIRSTNAME$0) != 0;
        }
    }
    
    /**
     * Sets the "FirstName" element
     */
    public void setFirstName(java.lang.String firstName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$0);
            }
            target.setStringValue(firstName);
        }
    }
    
    /**
     * Sets (as xml) the "FirstName" element
     */
    public void xsetFirstName(org.apache.xmlbeans.XmlString firstName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$0);
            }
            target.set(firstName);
        }
    }
    
    /**
     * Unsets the "FirstName" element
     */
    public void unsetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FIRSTNAME$0, 0);
        }
    }
    
    /**
     * Gets the "LastName" element
     */
    public java.lang.String getLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LastName" element
     */
    public org.apache.xmlbeans.XmlString xgetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "LastName" element
     */
    public boolean isSetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTNAME$2) != 0;
        }
    }
    
    /**
     * Sets the "LastName" element
     */
    public void setLastName(java.lang.String lastName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$2);
            }
            target.setStringValue(lastName);
        }
    }
    
    /**
     * Sets (as xml) the "LastName" element
     */
    public void xsetLastName(org.apache.xmlbeans.XmlString lastName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$2);
            }
            target.set(lastName);
        }
    }
    
    /**
     * Unsets the "LastName" element
     */
    public void unsetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTNAME$2, 0);
        }
    }
    
    /**
     * Gets the "PhoneNumber" element
     */
    public java.lang.String getPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONENUMBER$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "PhoneNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "PhoneNumber" element
     */
    public boolean isSetPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHONENUMBER$4) != 0;
        }
    }
    
    /**
     * Sets the "PhoneNumber" element
     */
    public void setPhoneNumber(java.lang.String phoneNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONENUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PHONENUMBER$4);
            }
            target.setStringValue(phoneNumber);
        }
    }
    
    /**
     * Sets (as xml) the "PhoneNumber" element
     */
    public void xsetPhoneNumber(org.apache.xmlbeans.XmlString phoneNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONENUMBER$4);
            }
            target.set(phoneNumber);
        }
    }
    
    /**
     * Unsets the "PhoneNumber" element
     */
    public void unsetPhoneNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHONENUMBER$4, 0);
        }
    }
}
