/*
 * XML Type:  VehicleLocationType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML VehicleLocationType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class VehicleLocationTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType
{
    private static final long serialVersionUID = 1L;
    
    public VehicleLocationTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS1$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Address2");
    private static final javax.xml.namespace.QName CITY$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "City");
    private static final javax.xml.namespace.QName STATE$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "State");
    private static final javax.xml.namespace.QName ZIPPOSTALCODE$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ZipPostalCode");
    private static final javax.xml.namespace.QName COUNTRYCODE$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CountryCode");
    private static final javax.xml.namespace.QName LOCATIONNAME$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LocationName");
    private static final javax.xml.namespace.QName LOCATIONPHONE$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LocationPhone");
    
    
    /**
     * Gets the "Address1" element
     */
    public java.lang.String getAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Address1" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(ADDRESS1$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "Address1" element
     */
    public boolean isSetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS1$0) != 0;
        }
    }
    
    /**
     * Sets the "Address1" element
     */
    public void setAddress1(java.lang.String address1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS1$0);
            }
            target.setStringValue(address1);
        }
    }
    
    /**
     * Sets (as xml) the "Address1" element
     */
    public void xsetAddress1(com.mitchell.schemas.mitchellSuffixRqRs.String50Type address1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(ADDRESS1$0);
            }
            target.set(address1);
        }
    }
    
    /**
     * Unsets the "Address1" element
     */
    public void unsetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS1$0, 0);
        }
    }
    
    /**
     * Gets the "Address2" element
     */
    public java.lang.String getAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Address2" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(ADDRESS2$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "Address2" element
     */
    public boolean isSetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS2$2) != 0;
        }
    }
    
    /**
     * Sets the "Address2" element
     */
    public void setAddress2(java.lang.String address2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS2$2);
            }
            target.setStringValue(address2);
        }
    }
    
    /**
     * Sets (as xml) the "Address2" element
     */
    public void xsetAddress2(com.mitchell.schemas.mitchellSuffixRqRs.String50Type address2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(ADDRESS2$2);
            }
            target.set(address2);
        }
    }
    
    /**
     * Unsets the "Address2" element
     */
    public void unsetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS2$2, 0);
        }
    }
    
    /**
     * Gets the "City" element
     */
    public java.lang.String getCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "City" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(CITY$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "City" element
     */
    public boolean isSetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CITY$4) != 0;
        }
    }
    
    /**
     * Sets the "City" element
     */
    public void setCity(java.lang.String city)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITY$4);
            }
            target.setStringValue(city);
        }
    }
    
    /**
     * Sets (as xml) the "City" element
     */
    public void xsetCity(com.mitchell.schemas.mitchellSuffixRqRs.String50Type city)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(CITY$4);
            }
            target.set(city);
        }
    }
    
    /**
     * Unsets the "City" element
     */
    public void unsetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CITY$4, 0);
        }
    }
    
    /**
     * Gets the "State" element
     */
    public java.lang.String getState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "State" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(STATE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "State" element
     */
    public boolean isSetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATE$6) != 0;
        }
    }
    
    /**
     * Sets the "State" element
     */
    public void setState(java.lang.String state)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATE$6);
            }
            target.setStringValue(state);
        }
    }
    
    /**
     * Sets (as xml) the "State" element
     */
    public void xsetState(com.mitchell.schemas.mitchellSuffixRqRs.String2Type state)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(STATE$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(STATE$6);
            }
            target.set(state);
        }
    }
    
    /**
     * Unsets the "State" element
     */
    public void unsetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATE$6, 0);
        }
    }
    
    /**
     * Gets the "ZipPostalCode" element
     */
    public java.lang.String getZipPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIPPOSTALCODE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ZipPostalCode" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String15Type xgetZipPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String15Type)get_store().find_element_user(ZIPPOSTALCODE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "ZipPostalCode" element
     */
    public boolean isSetZipPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ZIPPOSTALCODE$8) != 0;
        }
    }
    
    /**
     * Sets the "ZipPostalCode" element
     */
    public void setZipPostalCode(java.lang.String zipPostalCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIPPOSTALCODE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ZIPPOSTALCODE$8);
            }
            target.setStringValue(zipPostalCode);
        }
    }
    
    /**
     * Sets (as xml) the "ZipPostalCode" element
     */
    public void xsetZipPostalCode(com.mitchell.schemas.mitchellSuffixRqRs.String15Type zipPostalCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String15Type)get_store().find_element_user(ZIPPOSTALCODE$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String15Type)get_store().add_element_user(ZIPPOSTALCODE$8);
            }
            target.set(zipPostalCode);
        }
    }
    
    /**
     * Unsets the "ZipPostalCode" element
     */
    public void unsetZipPostalCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ZIPPOSTALCODE$8, 0);
        }
    }
    
    /**
     * Gets the "CountryCode" element
     */
    public java.lang.String getCountryCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "CountryCode" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetCountryCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(COUNTRYCODE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "CountryCode" element
     */
    public boolean isSetCountryCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COUNTRYCODE$10) != 0;
        }
    }
    
    /**
     * Sets the "CountryCode" element
     */
    public void setCountryCode(java.lang.String countryCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COUNTRYCODE$10);
            }
            target.setStringValue(countryCode);
        }
    }
    
    /**
     * Sets (as xml) the "CountryCode" element
     */
    public void xsetCountryCode(com.mitchell.schemas.mitchellSuffixRqRs.String2Type countryCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(COUNTRYCODE$10, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(COUNTRYCODE$10);
            }
            target.set(countryCode);
        }
    }
    
    /**
     * Unsets the "CountryCode" element
     */
    public void unsetCountryCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COUNTRYCODE$10, 0);
        }
    }
    
    /**
     * Gets the "LocationName" element
     */
    public java.lang.String getLocationName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCATIONNAME$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LocationName" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetLocationName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LOCATIONNAME$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "LocationName" element
     */
    public boolean isSetLocationName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCATIONNAME$12) != 0;
        }
    }
    
    /**
     * Sets the "LocationName" element
     */
    public void setLocationName(java.lang.String locationName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCATIONNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCATIONNAME$12);
            }
            target.setStringValue(locationName);
        }
    }
    
    /**
     * Sets (as xml) the "LocationName" element
     */
    public void xsetLocationName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type locationName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String50Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().find_element_user(LOCATIONNAME$12, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String50Type)get_store().add_element_user(LOCATIONNAME$12);
            }
            target.set(locationName);
        }
    }
    
    /**
     * Unsets the "LocationName" element
     */
    public void unsetLocationName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCATIONNAME$12, 0);
        }
    }
    
    /**
     * Gets the "LocationPhone" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType getLocationPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(LOCATIONPHONE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "LocationPhone" element
     */
    public boolean isSetLocationPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCATIONPHONE$14) != 0;
        }
    }
    
    /**
     * Sets the "LocationPhone" element
     */
    public void setLocationPhone(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType locationPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().find_element_user(LOCATIONPHONE$14, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().add_element_user(LOCATIONPHONE$14);
            }
            target.set(locationPhone);
        }
    }
    
    /**
     * Appends and returns a new empty "LocationPhone" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PhoneType addNewLocationPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PhoneType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType)get_store().add_element_user(LOCATIONPHONE$14);
            return target;
        }
    }
    
    /**
     * Unsets the "LocationPhone" element
     */
    public void unsetLocationPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCATIONPHONE$14, 0);
        }
    }
}
