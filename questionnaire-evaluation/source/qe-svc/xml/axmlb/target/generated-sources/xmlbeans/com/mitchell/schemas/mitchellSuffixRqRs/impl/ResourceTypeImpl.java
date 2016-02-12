/*
 * XML Type:  ResourceType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ResourceType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML ResourceType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class ResourceTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.ResourceType
{
    private static final long serialVersionUID = 1L;
    
    public ResourceTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RESOURCEID$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ResourceID");
    private static final javax.xml.namespace.QName RESOURCETYPE$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ResourceType");
    private static final javax.xml.namespace.QName BUSINESSNAME$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "BusinessName");
    private static final javax.xml.namespace.QName FIRSTNAME$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "FirstName");
    private static final javax.xml.namespace.QName LASTNAME$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LastName");
    private static final javax.xml.namespace.QName EMAIL$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Email");
    private static final javax.xml.namespace.QName CONTACTDETAILS$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ContactDetails");
    private static final javax.xml.namespace.QName ADDRESS$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Address");
    
    
    /**
     * Gets the "ResourceID" element
     */
    public java.lang.String getResourceID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCEID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ResourceID" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type xgetResourceID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type)get_store().find_element_user(RESOURCEID$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ResourceID" element
     */
    public void setResourceID(java.lang.String resourceID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESOURCEID$0);
            }
            target.setStringValue(resourceID);
        }
    }
    
    /**
     * Sets (as xml) the "ResourceID" element
     */
    public void xsetResourceID(com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type resourceID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type)get_store().find_element_user(RESOURCEID$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type)get_store().add_element_user(RESOURCEID$0);
            }
            target.set(resourceID);
        }
    }
    
    /**
     * Gets the "ResourceType" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum.Enum getResourceType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCETYPE$2, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "ResourceType" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum xgetResourceType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum)get_store().find_element_user(RESOURCETYPE$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ResourceType" element
     */
    public void setResourceType(com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum.Enum resourceType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCETYPE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESOURCETYPE$2);
            }
            target.setEnumValue(resourceType);
        }
    }
    
    /**
     * Sets (as xml) the "ResourceType" element
     */
    public void xsetResourceType(com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum resourceType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum)get_store().find_element_user(RESOURCETYPE$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum)get_store().add_element_user(RESOURCETYPE$2);
            }
            target.set(resourceType);
        }
    }
    
    /**
     * Gets the "BusinessName" element
     */
    public java.lang.String getBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$4, 0);
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
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(BUSINESSNAME$4, 0);
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
            return get_store().count_elements(BUSINESSNAME$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSINESSNAME$4);
            }
            target.setStringValue(businessName);
        }
    }
    
    /**
     * Sets (as xml) the "BusinessName" element
     */
    public void xsetBusinessName(com.mitchell.schemas.mitchellSuffixRqRs.String80Type businessName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(BUSINESSNAME$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(BUSINESSNAME$4);
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
            get_store().remove_element(BUSINESSNAME$4, 0);
        }
    }
    
    /**
     * Gets the "FirstName" element
     */
    public java.lang.String getFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$6, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(FIRSTNAME$6, 0);
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
            return get_store().count_elements(FIRSTNAME$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$6);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(FIRSTNAME$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(FIRSTNAME$6);
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
            get_store().remove_element(FIRSTNAME$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$8, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LASTNAME$8, 0);
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
            return get_store().count_elements(LASTNAME$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$8);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LASTNAME$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(LASTNAME$8);
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
            get_store().remove_element(LASTNAME$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$10, 0);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$10, 0);
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
            return get_store().count_elements(EMAIL$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$10);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(EMAIL$10, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(EMAIL$10);
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
            get_store().remove_element(EMAIL$10, 0);
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
            get_store().find_all_element_users(CONTACTDETAILS$12, targetList);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$12, i);
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
            return get_store().count_elements(CONTACTDETAILS$12);
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
            arraySetterHelper(contactDetailsArray, CONTACTDETAILS$12);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(CONTACTDETAILS$12, i);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().insert_element_user(CONTACTDETAILS$12, i);
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
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().add_element_user(CONTACTDETAILS$12);
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
            get_store().remove_element(CONTACTDETAILS$12, i);
        }
    }
    
    /**
     * Gets the "Address" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType getAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESS$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Address" element
     */
    public boolean isSetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS$14) != 0;
        }
    }
    
    /**
     * Sets the "Address" element
     */
    public void setAddress(com.mitchell.schemas.mitchellSuffixRqRs.AddressType address)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().find_element_user(ADDRESS$14, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESS$14);
            }
            target.set(address);
        }
    }
    
    /**
     * Appends and returns a new empty "Address" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.AddressType addNewAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.AddressType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.AddressType)get_store().add_element_user(ADDRESS$14);
            return target;
        }
    }
    
    /**
     * Unsets the "Address" element
     */
    public void unsetAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS$14, 0);
        }
    }
}
