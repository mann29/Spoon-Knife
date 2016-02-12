/*
 * XML Type:  VehicleType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.VehicleType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML VehicleType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface VehicleType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(VehicleType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("vehicletype2c64type");
    
    /**
     * Gets the "VIN" element
     */
    java.lang.String getVIN();
    
    /**
     * Gets (as xml) the "VIN" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetVIN();
    
    /**
     * True if has "VIN" element
     */
    boolean isSetVIN();
    
    /**
     * Sets the "VIN" element
     */
    void setVIN(java.lang.String vin);
    
    /**
     * Sets (as xml) the "VIN" element
     */
    void xsetVIN(com.mitchell.schemas.mitchellSuffixRqRs.String20Type vin);
    
    /**
     * Unsets the "VIN" element
     */
    void unsetVIN();
    
    /**
     * Gets the "VehicleType" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum.Enum getVehicleType();
    
    /**
     * Gets (as xml) the "VehicleType" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum xgetVehicleType();
    
    /**
     * True if has "VehicleType" element
     */
    boolean isSetVehicleType();
    
    /**
     * Sets the "VehicleType" element
     */
    void setVehicleType(com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum.Enum vehicleType);
    
    /**
     * Sets (as xml) the "VehicleType" element
     */
    void xsetVehicleType(com.mitchell.schemas.mitchellSuffixRqRs.VehicleTypeEnum vehicleType);
    
    /**
     * Unsets the "VehicleType" element
     */
    void unsetVehicleType();
    
    /**
     * Gets the "Year" element
     */
    java.math.BigInteger getYear();
    
    /**
     * Gets (as xml) the "Year" element
     */
    org.apache.xmlbeans.XmlInteger xgetYear();
    
    /**
     * True if has "Year" element
     */
    boolean isSetYear();
    
    /**
     * Sets the "Year" element
     */
    void setYear(java.math.BigInteger year);
    
    /**
     * Sets (as xml) the "Year" element
     */
    void xsetYear(org.apache.xmlbeans.XmlInteger year);
    
    /**
     * Unsets the "Year" element
     */
    void unsetYear();
    
    /**
     * Gets the "Make" element
     */
    java.lang.String getMake();
    
    /**
     * Gets (as xml) the "Make" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetMake();
    
    /**
     * True if has "Make" element
     */
    boolean isSetMake();
    
    /**
     * Sets the "Make" element
     */
    void setMake(java.lang.String make);
    
    /**
     * Sets (as xml) the "Make" element
     */
    void xsetMake(com.mitchell.schemas.mitchellSuffixRqRs.String80Type make);
    
    /**
     * Unsets the "Make" element
     */
    void unsetMake();
    
    /**
     * Gets the "Model" element
     */
    java.lang.String getModel();
    
    /**
     * Gets (as xml) the "Model" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetModel();
    
    /**
     * True if has "Model" element
     */
    boolean isSetModel();
    
    /**
     * Sets the "Model" element
     */
    void setModel(java.lang.String model);
    
    /**
     * Sets (as xml) the "Model" element
     */
    void xsetModel(com.mitchell.schemas.mitchellSuffixRqRs.String80Type model);
    
    /**
     * Unsets the "Model" element
     */
    void unsetModel();
    
    /**
     * Gets the "SubModel" element
     */
    java.lang.String getSubModel();
    
    /**
     * Gets (as xml) the "SubModel" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetSubModel();
    
    /**
     * True if has "SubModel" element
     */
    boolean isSetSubModel();
    
    /**
     * Sets the "SubModel" element
     */
    void setSubModel(java.lang.String subModel);
    
    /**
     * Sets (as xml) the "SubModel" element
     */
    void xsetSubModel(com.mitchell.schemas.mitchellSuffixRqRs.String80Type subModel);
    
    /**
     * Unsets the "SubModel" element
     */
    void unsetSubModel();
    
    /**
     * Gets the "BodyStyle" element
     */
    java.lang.String getBodyStyle();
    
    /**
     * Gets (as xml) the "BodyStyle" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetBodyStyle();
    
    /**
     * True if has "BodyStyle" element
     */
    boolean isSetBodyStyle();
    
    /**
     * Sets the "BodyStyle" element
     */
    void setBodyStyle(java.lang.String bodyStyle);
    
    /**
     * Sets (as xml) the "BodyStyle" element
     */
    void xsetBodyStyle(com.mitchell.schemas.mitchellSuffixRqRs.String80Type bodyStyle);
    
    /**
     * Unsets the "BodyStyle" element
     */
    void unsetBodyStyle();
    
    /**
     * Gets the "Engine" element
     */
    java.lang.String getEngine();
    
    /**
     * Gets (as xml) the "Engine" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetEngine();
    
    /**
     * True if has "Engine" element
     */
    boolean isSetEngine();
    
    /**
     * Sets the "Engine" element
     */
    void setEngine(java.lang.String engine);
    
    /**
     * Sets (as xml) the "Engine" element
     */
    void xsetEngine(com.mitchell.schemas.mitchellSuffixRqRs.String80Type engine);
    
    /**
     * Unsets the "Engine" element
     */
    void unsetEngine();
    
    /**
     * Gets the "Transmission" element
     */
    java.lang.String getTransmission();
    
    /**
     * Gets (as xml) the "Transmission" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetTransmission();
    
    /**
     * True if has "Transmission" element
     */
    boolean isSetTransmission();
    
    /**
     * Sets the "Transmission" element
     */
    void setTransmission(java.lang.String transmission);
    
    /**
     * Sets (as xml) the "Transmission" element
     */
    void xsetTransmission(com.mitchell.schemas.mitchellSuffixRqRs.String80Type transmission);
    
    /**
     * Unsets the "Transmission" element
     */
    void unsetTransmission();
    
    /**
     * Gets the "DriveTrain" element
     */
    java.lang.String getDriveTrain();
    
    /**
     * Gets (as xml) the "DriveTrain" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetDriveTrain();
    
    /**
     * True if has "DriveTrain" element
     */
    boolean isSetDriveTrain();
    
    /**
     * Sets the "DriveTrain" element
     */
    void setDriveTrain(java.lang.String driveTrain);
    
    /**
     * Sets (as xml) the "DriveTrain" element
     */
    void xsetDriveTrain(com.mitchell.schemas.mitchellSuffixRqRs.String80Type driveTrain);
    
    /**
     * Unsets the "DriveTrain" element
     */
    void unsetDriveTrain();
    
    /**
     * Gets the "ExteriorColor" element
     */
    java.lang.String getExteriorColor();
    
    /**
     * Gets (as xml) the "ExteriorColor" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String100Type xgetExteriorColor();
    
    /**
     * True if has "ExteriorColor" element
     */
    boolean isSetExteriorColor();
    
    /**
     * Sets the "ExteriorColor" element
     */
    void setExteriorColor(java.lang.String exteriorColor);
    
    /**
     * Sets (as xml) the "ExteriorColor" element
     */
    void xsetExteriorColor(com.mitchell.schemas.mitchellSuffixRqRs.String100Type exteriorColor);
    
    /**
     * Unsets the "ExteriorColor" element
     */
    void unsetExteriorColor();
    
    /**
     * Gets the "Mileage" element
     */
    java.math.BigInteger getMileage();
    
    /**
     * Gets (as xml) the "Mileage" element
     */
    org.apache.xmlbeans.XmlInteger xgetMileage();
    
    /**
     * True if has "Mileage" element
     */
    boolean isSetMileage();
    
    /**
     * Sets the "Mileage" element
     */
    void setMileage(java.math.BigInteger mileage);
    
    /**
     * Sets (as xml) the "Mileage" element
     */
    void xsetMileage(org.apache.xmlbeans.XmlInteger mileage);
    
    /**
     * Unsets the "Mileage" element
     */
    void unsetMileage();
    
    /**
     * Gets the "IsTrueMileageUnknown" element
     */
    boolean getIsTrueMileageUnknown();
    
    /**
     * Gets (as xml) the "IsTrueMileageUnknown" element
     */
    org.apache.xmlbeans.XmlBoolean xgetIsTrueMileageUnknown();
    
    /**
     * True if has "IsTrueMileageUnknown" element
     */
    boolean isSetIsTrueMileageUnknown();
    
    /**
     * Sets the "IsTrueMileageUnknown" element
     */
    void setIsTrueMileageUnknown(boolean isTrueMileageUnknown);
    
    /**
     * Sets (as xml) the "IsTrueMileageUnknown" element
     */
    void xsetIsTrueMileageUnknown(org.apache.xmlbeans.XmlBoolean isTrueMileageUnknown);
    
    /**
     * Unsets the "IsTrueMileageUnknown" element
     */
    void unsetIsTrueMileageUnknown();
    
    /**
     * Gets the "LicensePlateNumber" element
     */
    java.lang.String getLicensePlateNumber();
    
    /**
     * Gets (as xml) the "LicensePlateNumber" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String10Type xgetLicensePlateNumber();
    
    /**
     * True if has "LicensePlateNumber" element
     */
    boolean isSetLicensePlateNumber();
    
    /**
     * Sets the "LicensePlateNumber" element
     */
    void setLicensePlateNumber(java.lang.String licensePlateNumber);
    
    /**
     * Sets (as xml) the "LicensePlateNumber" element
     */
    void xsetLicensePlateNumber(com.mitchell.schemas.mitchellSuffixRqRs.String10Type licensePlateNumber);
    
    /**
     * Unsets the "LicensePlateNumber" element
     */
    void unsetLicensePlateNumber();
    
    /**
     * Gets the "LicensePlateState" element
     */
    java.lang.String getLicensePlateState();
    
    /**
     * Gets (as xml) the "LicensePlateState" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetLicensePlateState();
    
    /**
     * True if has "LicensePlateState" element
     */
    boolean isSetLicensePlateState();
    
    /**
     * Sets the "LicensePlateState" element
     */
    void setLicensePlateState(java.lang.String licensePlateState);
    
    /**
     * Sets (as xml) the "LicensePlateState" element
     */
    void xsetLicensePlateState(com.mitchell.schemas.mitchellSuffixRqRs.String2Type licensePlateState);
    
    /**
     * Unsets the "LicensePlateState" element
     */
    void unsetLicensePlateState();
    
    /**
     * Gets the "LicensePlateExpirationDate" element
     */
    java.util.Calendar getLicensePlateExpirationDate();
    
    /**
     * Gets (as xml) the "LicensePlateExpirationDate" element
     */
    org.apache.xmlbeans.XmlGYearMonth xgetLicensePlateExpirationDate();
    
    /**
     * True if has "LicensePlateExpirationDate" element
     */
    boolean isSetLicensePlateExpirationDate();
    
    /**
     * Sets the "LicensePlateExpirationDate" element
     */
    void setLicensePlateExpirationDate(java.util.Calendar licensePlateExpirationDate);
    
    /**
     * Sets (as xml) the "LicensePlateExpirationDate" element
     */
    void xsetLicensePlateExpirationDate(org.apache.xmlbeans.XmlGYearMonth licensePlateExpirationDate);
    
    /**
     * Unsets the "LicensePlateExpirationDate" element
     */
    void unsetLicensePlateExpirationDate();
    
    /**
     * Gets the "VehicleLocationDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType getVehicleLocationDetails();
    
    /**
     * True if has "VehicleLocationDetails" element
     */
    boolean isSetVehicleLocationDetails();
    
    /**
     * Sets the "VehicleLocationDetails" element
     */
    void setVehicleLocationDetails(com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType vehicleLocationDetails);
    
    /**
     * Appends and returns a new empty "VehicleLocationDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleLocationType addNewVehicleLocationDetails();
    
    /**
     * Unsets the "VehicleLocationDetails" element
     */
    void unsetVehicleLocationDetails();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.VehicleType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.VehicleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
