/*
 * XML Type:  ClaimParty
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML ClaimParty(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class ClaimPartyImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty
{
    private static final long serialVersionUID = 1L;
    
    public ClaimPartyImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FIRSTNAME$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "FirstName");
    private static final javax.xml.namespace.QName LASTNAME$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LastName");
    private static final javax.xml.namespace.QName EMAIL$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Email");
    private static final javax.xml.namespace.QName ADDRESSDETAILS$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AddressDetails");
    private static final javax.xml.namespace.QName CONTACTDETAILS$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ContactDetails");
    private static final javax.xml.namespace.QName USERID$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "UserID");
    
    
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
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(FIRSTNAME$0, 0);
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
    public void xsetFirstName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type firstName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(FIRSTNAME$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(FIRSTNAME$0);
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
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LASTNAME$2, 0);
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
    public void xsetLastName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type lastName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LASTNAME$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(LASTNAME$2);
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
     * Gets the "Email" element
     */
    public java.lang.String getEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Email" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "Email" element
     */
    public boolean isSetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$4) != 0;
        }
    }
    
    /**
     * Sets the "Email" element
     */
    public void setEmail(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$4);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "Email" element
     */
    public void xsetEmail(com.mitchell.schemas.mitchellSuffixRqRs.String80Type email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(EMAIL$4);
            }
            target.set(email);
        }
    }
    
    /**
     * Unsets the "Email" element
     */
    public void unsetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$4, 0);
        }
    }
    
    /**
     * Gets the "AddressDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType getAddressDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESSDETAILS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "AddressDetails" element
     */
    public boolean isSetAddressDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESSDETAILS$6) != 0;
        }
    }
    
    /**
     * Sets the "AddressDetails" element
     */
    public void setAddressDetails(com.mitchell.schemas.mitchellSuffixRqRs.AddressType addressDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESSDETAILS$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESSDETAILS$6);
            }
            target.set(addressDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "AddressDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType addNewAddressDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESSDETAILS$6);
            return target;
        }
    }
    
    /**
     * Unsets the "AddressDetails" element
     */
    public void unsetAddressDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESSDETAILS$6, 0);
        }
    }
    
    /**
     * Gets array of all "ContactDetails" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[] getContactDetailsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONTACTDETAILS$8, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "ContactDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType getContactDetailsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "ContactDetails" element
     */
    public int sizeOfContactDetailsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTACTDETAILS$8);
        }
    }
    
    /**
     * Sets array of all "ContactDetails" element
     */
    public void setContactDetailsArray(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[] contactDetailsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(contactDetailsArray, CONTACTDETAILS$8);
        }
    }
    
    /**
     * Sets ith "ContactDetails" element
     */
    public void setContactDetailsArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.PhoneType contactDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$8, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(contactDetails);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "ContactDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType insertNewContactDetails(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().insert_element_user(CONTACTDETAILS$8, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "ContactDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType addNewContactDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().add_element_user(CONTACTDETAILS$8);
            return target;
        }
    }
    
    /**
     * Removes the ith "ContactDetails" element
     */
    public void removeContactDetails(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTACTDETAILS$8, i);
        }
    }
    
    /**
     * Gets the "UserID" element
     */
    public java.lang.String getUserID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "UserID" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type xgetUserID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(USERID$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "UserID" element
     */
    public boolean isSetUserID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERID$10) != 0;
        }
    }
    
    /**
     * Sets the "UserID" element
     */
    public void setUserID(java.lang.String userID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERID$10);
            }
            target.setStringValue(userID);
        }
    }
    
    /**
     * Sets (as xml) the "UserID" element
     */
    public void xsetUserID(com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type userID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().add_element_user(USERID$10);
            }
            target.set(userID);
        }
    }
    
    /**
     * Unsets the "UserID" element
     */
    public void unsetUserID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERID$10, 0);
        }
    }
}
