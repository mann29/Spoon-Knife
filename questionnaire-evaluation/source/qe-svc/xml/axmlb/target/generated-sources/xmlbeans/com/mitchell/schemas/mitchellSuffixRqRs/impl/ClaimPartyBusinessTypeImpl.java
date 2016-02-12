/*
 * XML Type:  ClaimPartyBusinessType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML ClaimPartyBusinessType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class ClaimPartyBusinessTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType
{
    private static final long serialVersionUID = 1L;
    
    public ClaimPartyBusinessTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BUSINESSNAME$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "BusinessName");
    private static final javax.xml.namespace.QName EMAIL$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Email");
    private static final javax.xml.namespace.QName ADDRESSDETAILS$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "AddressDetails");
    private static final javax.xml.namespace.QName CONTACTDETAILS$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ContactDetails");
    private static final javax.xml.namespace.QName USERID$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "UserID");
    
    
    /**
     * Gets the "BusinessName" element
     */
    public java.lang.String getBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "BusinessName" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(BUSINESSNAME$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "BusinessName" element
     */
    public boolean isSetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BUSINESSNAME$0) != 0;
        }
    }
    
    /**
     * Sets the "BusinessName" element
     */
    public void setBusinessName(java.lang.String businessName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSINESSNAME$0);
            }
            target.setStringValue(businessName);
        }
    }
    
    /**
     * Sets (as xml) the "BusinessName" element
     */
    public void xsetBusinessName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type businessName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(BUSINESSNAME$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(BUSINESSNAME$0);
            }
            target.set(businessName);
        }
    }
    
    /**
     * Unsets the "BusinessName" element
     */
    public void unsetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BUSINESSNAME$0, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$2, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$2, 0);
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
            return get_store().count_elements(EMAIL$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$2);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(EMAIL$2);
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
            get_store().remove_element(EMAIL$2, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESSDETAILS$4, 0);
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
            return get_store().count_elements(ADDRESSDETAILS$4) != 0;
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESSDETAILS$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESSDETAILS$4);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESSDETAILS$4);
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
            get_store().remove_element(ADDRESSDETAILS$4, 0);
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
            get_store().find_all_element_users(CONTACTDETAILS$6, targetList);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$6, i);
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
            return get_store().count_elements(CONTACTDETAILS$6);
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
            arraySetterHelper(contactDetailsArray, CONTACTDETAILS$6);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$6, i);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().insert_element_user(CONTACTDETAILS$6, i);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().add_element_user(CONTACTDETAILS$6);
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
            get_store().remove_element(CONTACTDETAILS$6, i);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$8, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(USERID$8, 0);
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
            return get_store().count_elements(USERID$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERID$8);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(USERID$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().add_element_user(USERID$8);
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
            get_store().remove_element(USERID$8, 0);
        }
    }
}
