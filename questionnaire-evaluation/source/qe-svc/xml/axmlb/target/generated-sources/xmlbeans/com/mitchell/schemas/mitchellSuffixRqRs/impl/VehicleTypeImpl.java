/*
 * XML Type:  VehicleType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.VehicleType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML VehicleType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class VehicleTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.VehicleType
{
    private static final long serialVersionUID = 1L;
    
    public VehicleTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName VIN$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "VIN");
    private static final javax.xml.namespace.QName VEHICLETYPE$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "VehicleType");
    private static final javax.xml.namespace.QName YEAR$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Year");
    private static final javax.xml.namespace.QName MAKE$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Make");
    private static final javax.xml.namespace.QName MODEL$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Model");
    private static final javax.xml.namespace.QName SUBMODEL$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "SubModel");
    private static final javax.xml.namespace.QName BODYSTYLE$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "BodyStyle");
    private static final javax.xml.namespace.QName ENGINE$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Engine");
    private static final javax.xml.namespace.QName TRANSMISSION$16 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Transmission");
    private static final javax.xml.namespace.QName DRIVETRAIN$18 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "DriveTrain");
    private static final javax.xml.namespace.QName EXTERIORCOLOR$20 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ExteriorColor");
    private static final javax.xml.namespace.QName MILEAGE$22 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Mileage");
    private static final javax.xml.namespace.QName ISTRUEMILEAGEUNKNOWN$24 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsTrueMileageUnknown");
    private static final javax.xml.namespace.QName LICENSEPLATENUMBER$26 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LicensePlateNumber");
    private static final javax.xml.namespace.QName LICENSEPLATESTATE$28 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LicensePlateState");
    private static final javax.xml.namespace.QName LICENSEPLATEEXPIRATIONDATE$30 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "LicensePlateExpirationDate");
    private static final javax.xml.namespace.QName VEHICLELOCATIONDETAILS$32 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "VehicleLocationDetails");
    
    
    /**
     * Gets the "VIN" element
     */
    public java.lang.String getVIN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VIN$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "VIN" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetVIN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(VIN$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "VIN" element
     */
    public boolean isSetVIN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VIN$0) != 0;
        }
    }
    
    /**
     * Sets the "VIN" element
     */
    public void setVIN(java.lang.String vin)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VIN$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VIN$0);
            }
            target.setStringValue(vin);
        }
    }
    
    /**
     * Sets (as xml) the "VIN" element
     */
    public void xsetVIN(com.mitchell.schemas.mitchellSuffixRqRs.String20Type vin)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String20Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().find_element_user(VIN$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String20Type)get_store().add_element_user(VIN$0);
            }
            target.set(vin);
        }
    }
    
    /**
     * Unsets the "VIN" element
     */
    public void unsetVIN()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VIN$0, 0);
        }
    }
    
    /**
     * Gets the "VehicleType" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum.Enum getVehicleType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VEHICLETYPE$2, 0);
            if (target == null)
            {
                return null;
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "VehicleType" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum xgetVehicleType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum)get_store().find_element_user(VEHICLETYPE$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "VehicleType" element
     */
    public boolean isSetVehicleType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VEHICLETYPE$2) != 0;
        }
    }
    
    /**
     * Sets the "VehicleType" element
     */
    public void setVehicleType(com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum.Enum vehicleType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VEHICLETYPE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VEHICLETYPE$2);
            }
            target.setEnumValue(vehicleType);
        }
    }
    
    /**
     * Sets (as xml) the "VehicleType" element
     */
    public void xsetVehicleType(com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum vehicleType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum)get_store().find_element_user(VEHICLETYPE$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum)get_store().add_element_user(VEHICLETYPE$2);
            }
            target.set(vehicleType);
        }
    }
    
    /**
     * Unsets the "VehicleType" element
     */
    public void unsetVehicleType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VEHICLETYPE$2, 0);
        }
    }
    
    /**
     * Gets the "Year" element
     */
    public java.math.BigInteger getYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YEAR$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "Year" element
     */
    public org.apache.xmlbeans.XmlInteger xgetYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(YEAR$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "Year" element
     */
    public boolean isSetYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(YEAR$4) != 0;
        }
    }
    
    /**
     * Sets the "Year" element
     */
    public void setYear(java.math.BigInteger year)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YEAR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(YEAR$4);
            }
            target.setBigIntegerValue(year);
        }
    }
    
    /**
     * Sets (as xml) the "Year" element
     */
    public void xsetYear(org.apache.xmlbeans.XmlInteger year)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(YEAR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(YEAR$4);
            }
            target.set(year);
        }
    }
    
    /**
     * Unsets the "Year" element
     */
    public void unsetYear()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(YEAR$4, 0);
        }
    }
    
    /**
     * Gets the "Make" element
     */
    public java.lang.String getMake()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAKE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Make" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetMake()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(MAKE$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "Make" element
     */
    public boolean isSetMake()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAKE$6) != 0;
        }
    }
    
    /**
     * Sets the "Make" element
     */
    public void setMake(java.lang.String make)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAKE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAKE$6);
            }
            target.setStringValue(make);
        }
    }
    
    /**
     * Sets (as xml) the "Make" element
     */
    public void xsetMake(com.mitchell.schemas.mitchellSuffixRqRs.String80Type make)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(MAKE$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(MAKE$6);
            }
            target.set(make);
        }
    }
    
    /**
     * Unsets the "Make" element
     */
    public void unsetMake()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAKE$6, 0);
        }
    }
    
    /**
     * Gets the "Model" element
     */
    public java.lang.String getModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODEL$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Model" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(MODEL$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "Model" element
     */
    public boolean isSetModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MODEL$8) != 0;
        }
    }
    
    /**
     * Sets the "Model" element
     */
    public void setModel(java.lang.String model)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODEL$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODEL$8);
            }
            target.setStringValue(model);
        }
    }
    
    /**
     * Sets (as xml) the "Model" element
     */
    public void xsetModel(com.mitchell.schemas.mitchellSuffixRqRs.String80Type model)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(MODEL$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(MODEL$8);
            }
            target.set(model);
        }
    }
    
    /**
     * Unsets the "Model" element
     */
    public void unsetModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MODEL$8, 0);
        }
    }
    
    /**
     * Gets the "SubModel" element
     */
    public java.lang.String getSubModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBMODEL$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "SubModel" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetSubModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(SUBMODEL$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "SubModel" element
     */
    public boolean isSetSubModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUBMODEL$10) != 0;
        }
    }
    
    /**
     * Sets the "SubModel" element
     */
    public void setSubModel(java.lang.String subModel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBMODEL$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUBMODEL$10);
            }
            target.setStringValue(subModel);
        }
    }
    
    /**
     * Sets (as xml) the "SubModel" element
     */
    public void xsetSubModel(com.mitchell.schemas.mitchellSuffixRqRs.String80Type subModel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(SUBMODEL$10, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(SUBMODEL$10);
            }
            target.set(subModel);
        }
    }
    
    /**
     * Unsets the "SubModel" element
     */
    public void unsetSubModel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUBMODEL$10, 0);
        }
    }
    
    /**
     * Gets the "BodyStyle" element
     */
    public java.lang.String getBodyStyle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BODYSTYLE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "BodyStyle" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetBodyStyle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(BODYSTYLE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "BodyStyle" element
     */
    public boolean isSetBodyStyle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BODYSTYLE$12) != 0;
        }
    }
    
    /**
     * Sets the "BodyStyle" element
     */
    public void setBodyStyle(java.lang.String bodyStyle)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BODYSTYLE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BODYSTYLE$12);
            }
            target.setStringValue(bodyStyle);
        }
    }
    
    /**
     * Sets (as xml) the "BodyStyle" element
     */
    public void xsetBodyStyle(com.mitchell.schemas.mitchellSuffixRqRs.String80Type bodyStyle)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(BODYSTYLE$12, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(BODYSTYLE$12);
            }
            target.set(bodyStyle);
        }
    }
    
    /**
     * Unsets the "BodyStyle" element
     */
    public void unsetBodyStyle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BODYSTYLE$12, 0);
        }
    }
    
    /**
     * Gets the "Engine" element
     */
    public java.lang.String getEngine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ENGINE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Engine" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetEngine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(ENGINE$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "Engine" element
     */
    public boolean isSetEngine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ENGINE$14) != 0;
        }
    }
    
    /**
     * Sets the "Engine" element
     */
    public void setEngine(java.lang.String engine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ENGINE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ENGINE$14);
            }
            target.setStringValue(engine);
        }
    }
    
    /**
     * Sets (as xml) the "Engine" element
     */
    public void xsetEngine(com.mitchell.schemas.mitchellSuffixRqRs.String80Type engine)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(ENGINE$14, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(ENGINE$14);
            }
            target.set(engine);
        }
    }
    
    /**
     * Unsets the "Engine" element
     */
    public void unsetEngine()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ENGINE$14, 0);
        }
    }
    
    /**
     * Gets the "Transmission" element
     */
    public java.lang.String getTransmission()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSMISSION$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Transmission" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetTransmission()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(TRANSMISSION$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "Transmission" element
     */
    public boolean isSetTransmission()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANSMISSION$16) != 0;
        }
    }
    
    /**
     * Sets the "Transmission" element
     */
    public void setTransmission(java.lang.String transmission)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSMISSION$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSMISSION$16);
            }
            target.setStringValue(transmission);
        }
    }
    
    /**
     * Sets (as xml) the "Transmission" element
     */
    public void xsetTransmission(com.mitchell.schemas.mitchellSuffixRqRs.String80Type transmission)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(TRANSMISSION$16, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(TRANSMISSION$16);
            }
            target.set(transmission);
        }
    }
    
    /**
     * Unsets the "Transmission" element
     */
    public void unsetTransmission()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANSMISSION$16, 0);
        }
    }
    
    /**
     * Gets the "DriveTrain" element
     */
    public java.lang.String getDriveTrain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRIVETRAIN$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "DriveTrain" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetDriveTrain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(DRIVETRAIN$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "DriveTrain" element
     */
    public boolean isSetDriveTrain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DRIVETRAIN$18) != 0;
        }
    }
    
    /**
     * Sets the "DriveTrain" element
     */
    public void setDriveTrain(java.lang.String driveTrain)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DRIVETRAIN$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DRIVETRAIN$18);
            }
            target.setStringValue(driveTrain);
        }
    }
    
    /**
     * Sets (as xml) the "DriveTrain" element
     */
    public void xsetDriveTrain(com.mitchell.schemas.mitchellSuffixRqRs.String80Type driveTrain)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String80Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().find_element_user(DRIVETRAIN$18, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String80Type)get_store().add_element_user(DRIVETRAIN$18);
            }
            target.set(driveTrain);
        }
    }
    
    /**
     * Unsets the "DriveTrain" element
     */
    public void unsetDriveTrain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DRIVETRAIN$18, 0);
        }
    }
    
    /**
     * Gets the "ExteriorColor" element
     */
    public java.lang.String getExteriorColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERIORCOLOR$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ExteriorColor" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String100Type xgetExteriorColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String100Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().find_element_user(EXTERIORCOLOR$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "ExteriorColor" element
     */
    public boolean isSetExteriorColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXTERIORCOLOR$20) != 0;
        }
    }
    
    /**
     * Sets the "ExteriorColor" element
     */
    public void setExteriorColor(java.lang.String exteriorColor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXTERIORCOLOR$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXTERIORCOLOR$20);
            }
            target.setStringValue(exteriorColor);
        }
    }
    
    /**
     * Sets (as xml) the "ExteriorColor" element
     */
    public void xsetExteriorColor(com.mitchell.schemas.mitchellSuffixRqRs.String100Type exteriorColor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String100Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().find_element_user(EXTERIORCOLOR$20, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String100Type)get_store().add_element_user(EXTERIORCOLOR$20);
            }
            target.set(exteriorColor);
        }
    }
    
    /**
     * Unsets the "ExteriorColor" element
     */
    public void unsetExteriorColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXTERIORCOLOR$20, 0);
        }
    }
    
    /**
     * Gets the "Mileage" element
     */
    public java.math.BigInteger getMileage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MILEAGE$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "Mileage" element
     */
    public org.apache.xmlbeans.XmlInteger xgetMileage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(MILEAGE$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "Mileage" element
     */
    public boolean isSetMileage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MILEAGE$22) != 0;
        }
    }
    
    /**
     * Sets the "Mileage" element
     */
    public void setMileage(java.math.BigInteger mileage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MILEAGE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MILEAGE$22);
            }
            target.setBigIntegerValue(mileage);
        }
    }
    
    /**
     * Sets (as xml) the "Mileage" element
     */
    public void xsetMileage(org.apache.xmlbeans.XmlInteger mileage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(MILEAGE$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(MILEAGE$22);
            }
            target.set(mileage);
        }
    }
    
    /**
     * Unsets the "Mileage" element
     */
    public void unsetMileage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MILEAGE$22, 0);
        }
    }
    
    /**
     * Gets the "IsTrueMileageUnknown" element
     */
    public boolean getIsTrueMileageUnknown()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTRUEMILEAGEUNKNOWN$24, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "IsTrueMileageUnknown" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetIsTrueMileageUnknown()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISTRUEMILEAGEUNKNOWN$24, 0);
            return target;
        }
    }
    
    /**
     * True if has "IsTrueMileageUnknown" element
     */
    public boolean isSetIsTrueMileageUnknown()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISTRUEMILEAGEUNKNOWN$24) != 0;
        }
    }
    
    /**
     * Sets the "IsTrueMileageUnknown" element
     */
    public void setIsTrueMileageUnknown(boolean isTrueMileageUnknown)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTRUEMILEAGEUNKNOWN$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISTRUEMILEAGEUNKNOWN$24);
            }
            target.setBooleanValue(isTrueMileageUnknown);
        }
    }
    
    /**
     * Sets (as xml) the "IsTrueMileageUnknown" element
     */
    public void xsetIsTrueMileageUnknown(org.apache.xmlbeans.XmlBoolean isTrueMileageUnknown)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISTRUEMILEAGEUNKNOWN$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISTRUEMILEAGEUNKNOWN$24);
            }
            target.set(isTrueMileageUnknown);
        }
    }
    
    /**
     * Unsets the "IsTrueMileageUnknown" element
     */
    public void unsetIsTrueMileageUnknown()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISTRUEMILEAGEUNKNOWN$24, 0);
        }
    }
    
    /**
     * Gets the "LicensePlateNumber" element
     */
    public java.lang.String getLicensePlateNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATENUMBER$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LicensePlateNumber" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String10Type xgetLicensePlateNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String10Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().find_element_user(LICENSEPLATENUMBER$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "LicensePlateNumber" element
     */
    public boolean isSetLicensePlateNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LICENSEPLATENUMBER$26) != 0;
        }
    }
    
    /**
     * Sets the "LicensePlateNumber" element
     */
    public void setLicensePlateNumber(java.lang.String licensePlateNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATENUMBER$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LICENSEPLATENUMBER$26);
            }
            target.setStringValue(licensePlateNumber);
        }
    }
    
    /**
     * Sets (as xml) the "LicensePlateNumber" element
     */
    public void xsetLicensePlateNumber(com.mitchell.schemas.mitchellSuffixRqRs.String10Type licensePlateNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String10Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().find_element_user(LICENSEPLATENUMBER$26, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String10Type)get_store().add_element_user(LICENSEPLATENUMBER$26);
            }
            target.set(licensePlateNumber);
        }
    }
    
    /**
     * Unsets the "LicensePlateNumber" element
     */
    public void unsetLicensePlateNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LICENSEPLATENUMBER$26, 0);
        }
    }
    
    /**
     * Gets the "LicensePlateState" element
     */
    public java.lang.String getLicensePlateState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATESTATE$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "LicensePlateState" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetLicensePlateState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(LICENSEPLATESTATE$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "LicensePlateState" element
     */
    public boolean isSetLicensePlateState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LICENSEPLATESTATE$28) != 0;
        }
    }
    
    /**
     * Sets the "LicensePlateState" element
     */
    public void setLicensePlateState(java.lang.String licensePlateState)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATESTATE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LICENSEPLATESTATE$28);
            }
            target.setStringValue(licensePlateState);
        }
    }
    
    /**
     * Sets (as xml) the "LicensePlateState" element
     */
    public void xsetLicensePlateState(com.mitchell.schemas.mitchellSuffixRqRs.String2Type licensePlateState)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.String2Type target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().find_element_user(LICENSEPLATESTATE$28, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.String2Type)get_store().add_element_user(LICENSEPLATESTATE$28);
            }
            target.set(licensePlateState);
        }
    }
    
    /**
     * Unsets the "LicensePlateState" element
     */
    public void unsetLicensePlateState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LICENSEPLATESTATE$28, 0);
        }
    }
    
    /**
     * Gets the "LicensePlateExpirationDate" element
     */
    public java.util.Calendar getLicensePlateExpirationDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATEEXPIRATIONDATE$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "LicensePlateExpirationDate" element
     */
    public org.apache.xmlbeans.XmlGYearMonth xgetLicensePlateExpirationDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlGYearMonth target = null;
            target = (org.apache.xmlbeans.XmlGYearMonth)get_store().find_element_user(LICENSEPLATEEXPIRATIONDATE$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "LicensePlateExpirationDate" element
     */
    public boolean isSetLicensePlateExpirationDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LICENSEPLATEEXPIRATIONDATE$30) != 0;
        }
    }
    
    /**
     * Sets the "LicensePlateExpirationDate" element
     */
    public void setLicensePlateExpirationDate(java.util.Calendar licensePlateExpirationDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LICENSEPLATEEXPIRATIONDATE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LICENSEPLATEEXPIRATIONDATE$30);
            }
            target.setCalendarValue(licensePlateExpirationDate);
        }
    }
    
    /**
     * Sets (as xml) the "LicensePlateExpirationDate" element
     */
    public void xsetLicensePlateExpirationDate(org.apache.xmlbeans.XmlGYearMonth licensePlateExpirationDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlGYearMonth target = null;
            target = (org.apache.xmlbeans.XmlGYearMonth)get_store().find_element_user(LICENSEPLATEEXPIRATIONDATE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlGYearMonth)get_store().add_element_user(LICENSEPLATEEXPIRATIONDATE$30);
            }
            target.set(licensePlateExpirationDate);
        }
    }
    
    /**
     * Unsets the "LicensePlateExpirationDate" element
     */
    public void unsetLicensePlateExpirationDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LICENSEPLATEEXPIRATIONDATE$30, 0);
        }
    }
    
    /**
     * Gets the "VehicleLocationDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType getVehicleLocationDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType)get_store().find_element_user(VEHICLELOCATIONDETAILS$32, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "VehicleLocationDetails" element
     */
    public boolean isSetVehicleLocationDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VEHICLELOCATIONDETAILS$32) != 0;
        }
    }
    
    /**
     * Sets the "VehicleLocationDetails" element
     */
    public void setVehicleLocationDetails(com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType vehicleLocationDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType)get_store().find_element_user(VEHICLELOCATIONDETAILS$32, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType)get_store().add_element_user(VEHICLELOCATIONDETAILS$32);
            }
            target.set(vehicleLocationDetails);
        }
    }
    
    /**
     * Appends and returns a new empty "VehicleLocationDetails" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType addNewVehicleLocationDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType)get_store().add_element_user(VEHICLELOCATIONDETAILS$32);
            return target;
        }
    }
    
    /**
     * Unsets the "VehicleLocationDetails" element
     */
    public void unsetVehicleLocationDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VEHICLELOCATIONDETAILS$32, 0);
        }
    }
}
