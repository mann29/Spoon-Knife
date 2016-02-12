/*
 * XML Type:  SuffixType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.SuffixType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML SuffixType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class SuffixTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.SuffixType
{
    private static final long serialVersionUID = 1L;
    
    public SuffixTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SUFFIXNUMBER$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixNumber");
    private static final javax.xml.namespace.QName VEHICLEDETAILS$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "VehicleDetails");
    private static final javax.xml.namespace.QName VEHICLEDAMAGE$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "VehicleDamage");
    private static final javax.xml.namespace.QName COVERAGETYPEOFLOSS$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CoverageTypeOfLoss");
    private static final javax.xml.namespace.QName PROPERTYINFO$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PropertyInfo");
    private static final javax.xml.namespace.QName OWNERDETAILS$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "OwnerDetails");
    private static final javax.xml.namespace.QName DEDUCTIBLEAMT$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DeductibleAmt");
    private static final javax.xml.namespace.QName DEDUCTIBLESTATUS$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DeductibleStatus");
    private static final javax.xml.namespace.QName CLAIMANTDETAILS$16 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimantDetails");
    private static final javax.xml.namespace.QName INSUREDDETAILS$18 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "InsuredDetails");
    private static final javax.xml.namespace.QName CLAIMADJUSTERID$20 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ClaimAdjusterID");
    private static final javax.xml.namespace.QName SUFFIXMEMO$22 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixMemo");
    private static final javax.xml.namespace.QName CUSTOMELEMENTS$24 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "CustomElements");
    private static final javax.xml.namespace.QName SUFFIXSTATUS$26 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SuffixStatus");
    private static final javax.xml.namespace.QName EVALUATIONRESULTCATEGORY$28 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "EvaluationResultCategory");
    private static final javax.xml.namespace.QName REFINFO$30 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "RefInfo");
    
    
    /**
     * Gets the "SuffixNumber" element
     */
    public java.lang.String getSuffixNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SuffixNumber" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType xgetSuffixNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "SuffixNumber" element
     */
    public void setSuffixNumber(java.lang.String suffixNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXNUMBER$0);
            }
            target.setStringValue(suffixNumber);
        }
    }
    
    /**
     * Sets (as xml) the "SuffixNumber" element
     */
    public void xsetSuffixNumber(com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType suffixNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().find_element_user(SUFFIXNUMBER$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType)get_store().add_element_user(SUFFIXNUMBER$0);
            }
            target.set(suffixNumber);
        }
    }
    
    /**
     * Gets the "VehicleDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleType getVehicleDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType)get_store().find_element_user(VEHICLEDETAILS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "VehicleDetails" element
     */
    public boolean isSetVehicleDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VEHICLEDETAILS$2) != 0;
        }
    }
    
    /**
     * Sets the "VehicleDetails" element
     */
    public void setVehicleDetails(com.mitchell.schemas.mitchellSuffixRqRs.VehicleType vehicleDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType)get_store().find_element_user(VEHICLEDETAILS$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType)get_store().add_element_user(VEHICLEDETAILS$2);
            }
            target.set(vehicleDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "VehicleDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleType addNewVehicleDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType)get_store().add_element_user(VEHICLEDETAILS$2);
            return target;
        }
    }
    
    /**
     * Unsets the "VehicleDetails" element
     */
    public void unsetVehicleDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VEHICLEDETAILS$2, 0);
        }
    }
    
    /**
     * Gets the "VehicleDamage" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage getVehicleDamage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage)get_store().find_element_user(VEHICLEDAMAGE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "VehicleDamage" element
     */
    public boolean isSetVehicleDamage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VEHICLEDAMAGE$4) != 0;
        }
    }
    
    /**
     * Sets the "VehicleDamage" element
     */
    public void setVehicleDamage(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage vehicleDamage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage)get_store().find_element_user(VEHICLEDAMAGE$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage)get_store().add_element_user(VEHICLEDAMAGE$4);
            }
            target.set(vehicleDamage);
        }
    }
    
    /**
     * Appends and returns a new empty "VehicleDamage" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage addNewVehicleDamage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage)get_store().add_element_user(VEHICLEDAMAGE$4);
            return target;
        }
    }
    
    /**
     * Unsets the "VehicleDamage" element
     */
    public void unsetVehicleDamage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VEHICLEDAMAGE$4, 0);
        }
    }
    
    /**
     * Gets the "CoverageTypeOfLoss" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum.Enum getCoverageTypeOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COVERAGETYPEOFLOSS$6, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "CoverageTypeOfLoss" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum xgetCoverageTypeOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum)get_store().find_element_user(COVERAGETYPEOFLOSS$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "CoverageTypeOfLoss" element
     */
    public boolean isSetCoverageTypeOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COVERAGETYPEOFLOSS$6) != 0;
        }
    }
    
    /**
     * Sets the "CoverageTypeOfLoss" element
     */
    public void setCoverageTypeOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum.Enum coverageTypeOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COVERAGETYPEOFLOSS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COVERAGETYPEOFLOSS$6);
            }
            target.setEnumValue(coverageTypeOfLoss);
        }
    }
    
    /**
     * Sets (as xml) the "CoverageTypeOfLoss" element
     */
    public void xsetCoverageTypeOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum coverageTypeOfLoss)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum)get_store().find_element_user(COVERAGETYPEOFLOSS$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum)get_store().add_element_user(COVERAGETYPEOFLOSS$6);
            }
            target.set(coverageTypeOfLoss);
        }
    }
    
    /**
     * Unsets the "CoverageTypeOfLoss" element
     */
    public void unsetCoverageTypeOfLoss()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COVERAGETYPEOFLOSS$6, 0);
        }
    }
    
    /**
     * Gets the "PropertyInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType getPropertyInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType)get_store().find_element_user(PROPERTYINFO$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "PropertyInfo" element
     */
    public boolean isSetPropertyInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROPERTYINFO$8) != 0;
        }
    }
    
    /**
     * Sets the "PropertyInfo" element
     */
    public void setPropertyInfo(com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType propertyInfo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType)get_store().find_element_user(PROPERTYINFO$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType)get_store().add_element_user(PROPERTYINFO$8);
            }
            target.set(propertyInfo);
        }
    }
    
    /**
     * Appends and returns a new empty "PropertyInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType addNewPropertyInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType)get_store().add_element_user(PROPERTYINFO$8);
            return target;
        }
    }
    
    /**
     * Unsets the "PropertyInfo" element
     */
    public void unsetPropertyInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROPERTYINFO$8, 0);
        }
    }
    
    /**
     * Gets the "OwnerDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getOwnerDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(OWNERDETAILS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "OwnerDetails" element
     */
    public boolean isSetOwnerDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OWNERDETAILS$10) != 0;
        }
    }
    
    /**
     * Sets the "OwnerDetails" element
     */
    public void setOwnerDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty ownerDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(OWNERDETAILS$10, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(OWNERDETAILS$10);
            }
            target.set(ownerDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "OwnerDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewOwnerDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(OWNERDETAILS$10);
            return target;
        }
    }
    
    /**
     * Unsets the "OwnerDetails" element
     */
    public void unsetOwnerDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OWNERDETAILS$10, 0);
        }
    }
    
    /**
     * Gets the "DeductibleAmt" element
     */
    public java.math.BigDecimal getDeductibleAmt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEDUCTIBLEAMT$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "DeductibleAmt" element
     */
    public org.apache.xmlbeans.XmlDecimal xgetDeductibleAmt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDecimal target = null;
            target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(DEDUCTIBLEAMT$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "DeductibleAmt" element
     */
    public boolean isSetDeductibleAmt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEDUCTIBLEAMT$12) != 0;
        }
    }
    
    /**
     * Sets the "DeductibleAmt" element
     */
    public void setDeductibleAmt(java.math.BigDecimal deductibleAmt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEDUCTIBLEAMT$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEDUCTIBLEAMT$12);
            }
            target.setBigDecimalValue(deductibleAmt);
        }
    }
    
    /**
     * Sets (as xml) the "DeductibleAmt" element
     */
    public void xsetDeductibleAmt(org.apache.xmlbeans.XmlDecimal deductibleAmt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDecimal target = null;
            target = (org.apache.xmlbeans.XmlDecimal)get_store().find_element_user(DEDUCTIBLEAMT$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDecimal)get_store().add_element_user(DEDUCTIBLEAMT$12);
            }
            target.set(deductibleAmt);
        }
    }
    
    /**
     * Unsets the "DeductibleAmt" element
     */
    public void unsetDeductibleAmt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEDUCTIBLEAMT$12, 0);
        }
    }
    
    /**
     * Gets the "DeductibleStatus" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum.Enum getDeductibleStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEDUCTIBLESTATUS$14, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "DeductibleStatus" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum xgetDeductibleStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum)get_store().find_element_user(DEDUCTIBLESTATUS$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "DeductibleStatus" element
     */
    public boolean isSetDeductibleStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEDUCTIBLESTATUS$14) != 0;
        }
    }
    
    /**
     * Sets the "DeductibleStatus" element
     */
    public void setDeductibleStatus(com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum.Enum deductibleStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEDUCTIBLESTATUS$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEDUCTIBLESTATUS$14);
            }
            target.setEnumValue(deductibleStatus);
        }
    }
    
    /**
     * Sets (as xml) the "DeductibleStatus" element
     */
    public void xsetDeductibleStatus(com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum deductibleStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum)get_store().find_element_user(DEDUCTIBLESTATUS$14, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum)get_store().add_element_user(DEDUCTIBLESTATUS$14);
            }
            target.set(deductibleStatus);
        }
    }
    
    /**
     * Unsets the "DeductibleStatus" element
     */
    public void unsetDeductibleStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEDUCTIBLESTATUS$14, 0);
        }
    }
    
    /**
     * Gets the "ClaimantDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getClaimantDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(CLAIMANTDETAILS$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "ClaimantDetails" element
     */
    public boolean isSetClaimantDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMANTDETAILS$16) != 0;
        }
    }
    
    /**
     * Sets the "ClaimantDetails" element
     */
    public void setClaimantDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty claimantDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(CLAIMANTDETAILS$16, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(CLAIMANTDETAILS$16);
            }
            target.set(claimantDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "ClaimantDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewClaimantDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(CLAIMANTDETAILS$16);
            return target;
        }
    }
    
    /**
     * Unsets the "ClaimantDetails" element
     */
    public void unsetClaimantDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMANTDETAILS$16, 0);
        }
    }
    
    /**
     * Gets the "InsuredDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getInsuredDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(INSUREDDETAILS$18, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "InsuredDetails" element
     */
    public boolean isSetInsuredDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INSUREDDETAILS$18) != 0;
        }
    }
    
    /**
     * Sets the "InsuredDetails" element
     */
    public void setInsuredDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty insuredDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().find_element_user(INSUREDDETAILS$18, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(INSUREDDETAILS$18);
            }
            target.set(insuredDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "InsuredDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewInsuredDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty)get_store().add_element_user(INSUREDDETAILS$18);
            return target;
        }
    }
    
    /**
     * Unsets the "InsuredDetails" element
     */
    public void unsetInsuredDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INSUREDDETAILS$18, 0);
        }
    }
    
    /**
     * Gets the "ClaimAdjusterID" element
     */
    public java.lang.String getClaimAdjusterID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMADJUSTERID$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ClaimAdjusterID" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type xgetClaimAdjusterID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(CLAIMADJUSTERID$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "ClaimAdjusterID" element
     */
    public boolean isSetClaimAdjusterID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMADJUSTERID$20) != 0;
        }
    }
    
    /**
     * Sets the "ClaimAdjusterID" element
     */
    public void setClaimAdjusterID(java.lang.String claimAdjusterID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMADJUSTERID$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMADJUSTERID$20);
            }
            target.setStringValue(claimAdjusterID);
        }
    }
    
    /**
     * Sets (as xml) the "ClaimAdjusterID" element
     */
    public void xsetClaimAdjusterID(com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type claimAdjusterID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().find_element_user(CLAIMADJUSTERID$20, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type)get_store().add_element_user(CLAIMADJUSTERID$20);
            }
            target.set(claimAdjusterID);
        }
    }
    
    /**
     * Unsets the "ClaimAdjusterID" element
     */
    public void unsetClaimAdjusterID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMADJUSTERID$20, 0);
        }
    }
    
    /**
     * Gets the "SuffixMemo" element
     */
    public java.lang.String getSuffixMemo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXMEMO$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SuffixMemo" element
     */
    public org.apache.xmlbeans.XmlString xgetSuffixMemo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUFFIXMEMO$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "SuffixMemo" element
     */
    public boolean isSetSuffixMemo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUFFIXMEMO$22) != 0;
        }
    }
    
    /**
     * Sets the "SuffixMemo" element
     */
    public void setSuffixMemo(java.lang.String suffixMemo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXMEMO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXMEMO$22);
            }
            target.setStringValue(suffixMemo);
        }
    }
    
    /**
     * Sets (as xml) the "SuffixMemo" element
     */
    public void xsetSuffixMemo(org.apache.xmlbeans.XmlString suffixMemo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUFFIXMEMO$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUFFIXMEMO$22);
            }
            target.set(suffixMemo);
        }
    }
    
    /**
     * Unsets the "SuffixMemo" element
     */
    public void unsetSuffixMemo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUFFIXMEMO$22, 0);
        }
    }
    
    /**
     * Gets array of all "CustomElements" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[] getCustomElementsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CUSTOMELEMENTS$24, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "CustomElements" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType getCustomElementsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType)get_store().find_element_user(CUSTOMELEMENTS$24, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "CustomElements" element
     */
    public int sizeOfCustomElementsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTOMELEMENTS$24);
        }
    }
    
    /**
     * Sets array of all "CustomElements" element
     */
    public void setCustomElementsArray(com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[] customElementsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(customElementsArray, CUSTOMELEMENTS$24);
        }
    }
    
    /**
     * Sets ith "CustomElements" element
     */
    public void setCustomElementsArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType customElements)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType)get_store().find_element_user(CUSTOMELEMENTS$24, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(customElements);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CustomElements" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType insertNewCustomElements(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType)get_store().insert_element_user(CUSTOMELEMENTS$24, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CustomElements" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType addNewCustomElements()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType)get_store().add_element_user(CUSTOMELEMENTS$24);
            return target;
        }
    }
    
    /**
     * Removes the ith "CustomElements" element
     */
    public void removeCustomElements(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTOMELEMENTS$24, i);
        }
    }
    
    /**
     * Gets the "SuffixStatus" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.Enum getSuffixStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXSTATUS$26, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "SuffixStatus" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType xgetSuffixStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType)get_store().find_element_user(SUFFIXSTATUS$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "SuffixStatus" element
     */
    public boolean isSetSuffixStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUFFIXSTATUS$26) != 0;
        }
    }
    
    /**
     * Sets the "SuffixStatus" element
     */
    public void setSuffixStatus(com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.Enum suffixStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUFFIXSTATUS$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUFFIXSTATUS$26);
            }
            target.setEnumValue(suffixStatus);
        }
    }
    
    /**
     * Sets (as xml) the "SuffixStatus" element
     */
    public void xsetSuffixStatus(com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType suffixStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType)get_store().find_element_user(SUFFIXSTATUS$26, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType)get_store().add_element_user(SUFFIXSTATUS$26);
            }
            target.set(suffixStatus);
        }
    }
    
    /**
     * Unsets the "SuffixStatus" element
     */
    public void unsetSuffixStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUFFIXSTATUS$26, 0);
        }
    }
    
    /**
     * Gets the "EvaluationResultCategory" element
     */
    public java.lang.String getEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULTCATEGORY$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "EvaluationResultCategory" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(EVALUATIONRESULTCATEGORY$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "EvaluationResultCategory" element
     */
    public boolean isSetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EVALUATIONRESULTCATEGORY$28) != 0;
        }
    }
    
    /**
     * Sets the "EvaluationResultCategory" element
     */
    public void setEvaluationResultCategory(java.lang.String evaluationResultCategory)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EVALUATIONRESULTCATEGORY$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EVALUATIONRESULTCATEGORY$28);
            }
            target.setStringValue(evaluationResultCategory);
        }
    }
    
    /**
     * Sets (as xml) the "EvaluationResultCategory" element
     */
    public void xsetEvaluationResultCategory(com.mitchell.schemas.mitchellSuffixRqRs.String20Type evaluationResultCategory)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(EVALUATIONRESULTCATEGORY$28, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(EVALUATIONRESULTCATEGORY$28);
            }
            target.set(evaluationResultCategory);
        }
    }
    
    /**
     * Unsets the "EvaluationResultCategory" element
     */
    public void unsetEvaluationResultCategory()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EVALUATIONRESULTCATEGORY$28, 0);
        }
    }
    
    /**
     * Gets the "RefInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType getRefInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType)get_store().find_element_user(REFINFO$30, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "RefInfo" element
     */
    public boolean isSetRefInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REFINFO$30) != 0;
        }
    }
    
    /**
     * Sets the "RefInfo" element
     */
    public void setRefInfo(com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType refInfo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType)get_store().find_element_user(REFINFO$30, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType)get_store().add_element_user(REFINFO$30);
            }
            target.set(refInfo);
        }
    }
    
    /**
     * Appends and returns a new empty "RefInfo" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType addNewRefInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType)get_store().add_element_user(REFINFO$30);
            return target;
        }
    }
    
    /**
     * Unsets the "RefInfo" element
     */
    public void unsetRefInfo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REFINFO$30, 0);
        }
    }
    /**
     * An XML VehicleDamage(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
     *
     * This is a complex type.
     */
    public static class VehicleDamageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage
    {
        private static final long serialVersionUID = 1L;
        
        public VehicleDamageImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName PRIMARYPOI$0 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PrimaryPOI");
        private static final javax.xml.namespace.QName DAMAGEDESCRIPTION$2 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DamageDescription");
        private static final javax.xml.namespace.QName DRIVABLE$4 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Drivable");
        private static final javax.xml.namespace.QName SECONDARYPOI$6 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SecondaryPOI");
        
        
        /**
         * Gets the "PrimaryPOI" element
         */
        public int getPrimaryPOI()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYPOI$0, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "PrimaryPOI" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum xgetPrimaryPOI()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().find_element_user(PRIMARYPOI$0, 0);
                return target;
            }
        }
        
        /**
         * True if has "PrimaryPOI" element
         */
        public boolean isSetPrimaryPOI()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(PRIMARYPOI$0) != 0;
            }
        }
        
        /**
         * Sets the "PrimaryPOI" element
         */
        public void setPrimaryPOI(int primaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYPOI$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIMARYPOI$0);
                }
                target.setIntValue(primaryPOI);
            }
        }
        
        /**
         * Sets (as xml) the "PrimaryPOI" element
         */
        public void xsetPrimaryPOI(com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum primaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().find_element_user(PRIMARYPOI$0, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().add_element_user(PRIMARYPOI$0);
                }
                target.set(primaryPOI);
            }
        }
        
        /**
         * Unsets the "PrimaryPOI" element
         */
        public void unsetPrimaryPOI()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(PRIMARYPOI$0, 0);
            }
        }
        
        /**
         * Gets the "DamageDescription" element
         */
        public java.lang.String getDamageDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DAMAGEDESCRIPTION$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "DamageDescription" element
         */
        public org.apache.xmlbeans.XmlString xgetDamageDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DAMAGEDESCRIPTION$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "DamageDescription" element
         */
        public boolean isSetDamageDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(DAMAGEDESCRIPTION$2) != 0;
            }
        }
        
        /**
         * Sets the "DamageDescription" element
         */
        public void setDamageDescription(java.lang.String damageDescription)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DAMAGEDESCRIPTION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DAMAGEDESCRIPTION$2);
                }
                target.setStringValue(damageDescription);
            }
        }
        
        /**
         * Sets (as xml) the "DamageDescription" element
         */
        public void xsetDamageDescription(org.apache.xmlbeans.XmlString damageDescription)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DAMAGEDESCRIPTION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DAMAGEDESCRIPTION$2);
                }
                target.set(damageDescription);
            }
        }
        
        /**
         * Unsets the "DamageDescription" element
         */
        public void unsetDamageDescription()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(DAMAGEDESCRIPTION$2, 0);
            }
        }
        
        /**
         * Gets the "Drivable" element
         */
        public java.lang.String getDrivable()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRIVABLE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Drivable" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.Indicator xgetDrivable()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.Indicator target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().find_element_user(DRIVABLE$4, 0);
                return target;
            }
        }
        
        /**
         * True if has "Drivable" element
         */
        public boolean isSetDrivable()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(DRIVABLE$4) != 0;
            }
        }
        
        /**
         * Sets the "Drivable" element
         */
        public void setDrivable(java.lang.String drivable)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRIVABLE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DRIVABLE$4);
                }
                target.setStringValue(drivable);
            }
        }
        
        /**
         * Sets (as xml) the "Drivable" element
         */
        public void xsetDrivable(com.mitchell.schemas.mitchellSuffixRqRs.Indicator drivable)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.Indicator target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().find_element_user(DRIVABLE$4, 0);
                if (target == null)
                {
                    target = (com.mitchell.schemas.mitchellSuffixRqRs.Indicator)get_store().add_element_user(DRIVABLE$4);
                }
                target.set(drivable);
            }
        }
        
        /**
         * Unsets the "Drivable" element
         */
        public void unsetDrivable()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(DRIVABLE$4, 0);
            }
        }
        
        /**
         * Gets array of all "SecondaryPOI" elements
         */
        public int[] getSecondaryPOIArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(SECONDARYPOI$6, targetList);
                int[] result = new int[targetList.size()];
                for (int i = 0, len = targetList.size() ; i < len ; i++)
                    result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getIntValue();
                return result;
            }
        }
        
        /**
         * Gets ith "SecondaryPOI" element
         */
        public int getSecondaryPOIArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECONDARYPOI$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) array of all "SecondaryPOI" elements
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[] xgetSecondaryPOIArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(SECONDARYPOI$6, targetList);
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[] result = new com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets (as xml) ith "SecondaryPOI" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum xgetSecondaryPOIArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().find_element_user(SECONDARYPOI$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)target;
            }
        }
        
        /**
         * Returns number of "SecondaryPOI" element
         */
        public int sizeOfSecondaryPOIArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SECONDARYPOI$6);
            }
        }
        
        /**
         * Sets array of all "SecondaryPOI" element
         */
        public void setSecondaryPOIArray(int[] secondaryPOIArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(secondaryPOIArray, SECONDARYPOI$6);
            }
        }
        
        /**
         * Sets ith "SecondaryPOI" element
         */
        public void setSecondaryPOIArray(int i, int secondaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SECONDARYPOI$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setIntValue(secondaryPOI);
            }
        }
        
        /**
         * Sets (as xml) array of all "SecondaryPOI" element
         */
        public void xsetSecondaryPOIArray(com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[]secondaryPOIArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(secondaryPOIArray, SECONDARYPOI$6);
            }
        }
        
        /**
         * Sets (as xml) ith "SecondaryPOI" element
         */
        public void xsetSecondaryPOIArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum secondaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().find_element_user(SECONDARYPOI$6, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(secondaryPOI);
            }
        }
        
        /**
         * Inserts the value as the ith "SecondaryPOI" element
         */
        public void insertSecondaryPOI(int i, int secondaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = 
                    (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(SECONDARYPOI$6, i);
                target.setIntValue(secondaryPOI);
            }
        }
        
        /**
         * Appends the value as the last "SecondaryPOI" element
         */
        public void addSecondaryPOI(int secondaryPOI)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SECONDARYPOI$6);
                target.setIntValue(secondaryPOI);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "SecondaryPOI" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum insertNewSecondaryPOI(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().insert_element_user(SECONDARYPOI$6, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "SecondaryPOI" element
         */
        public com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum addNewSecondaryPOI()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum target = null;
                target = (com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum)get_store().add_element_user(SECONDARYPOI$6);
                return target;
            }
        }
        
        /**
         * Removes the ith "SecondaryPOI" element
         */
        public void removeSecondaryPOI(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SECONDARYPOI$6, i);
            }
        }
    }
}
