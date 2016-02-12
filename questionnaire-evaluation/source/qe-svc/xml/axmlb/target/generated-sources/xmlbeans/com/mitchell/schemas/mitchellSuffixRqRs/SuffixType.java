/*
 * XML Type:  SuffixType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.SuffixType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML SuffixType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface SuffixType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SuffixType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("suffixtyped7edtype");
    
    /**
     * Gets the "SuffixNumber" element
     */
    java.lang.String getSuffixNumber();
    
    /**
     * Gets (as xml) the "SuffixNumber" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType xgetSuffixNumber();
    
    /**
     * Sets the "SuffixNumber" element
     */
    void setSuffixNumber(java.lang.String suffixNumber);
    
    /**
     * Sets (as xml) the "SuffixNumber" element
     */
    void xsetSuffixNumber(com.mitchell.schemas.mitchellSuffixRqRs.SuffixNumberType suffixNumber);
    
    /**
     * Gets the "VehicleDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleType getVehicleDetails();
    
    /**
     * True if has "VehicleDetails" element
     */
    boolean isSetVehicleDetails();
    
    /**
     * Sets the "VehicleDetails" element
     */
    void setVehicleDetails(com.mitchell.schemas.mitchellSuffixRqRs.VehicleType vehicleDetails);
    
    /**
     * Appends and returns a new empty "VehicleDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.VehicleType addNewVehicleDetails();
    
    /**
     * Unsets the "VehicleDetails" element
     */
    void unsetVehicleDetails();
    
    /**
     * Gets the "VehicleDamage" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage getVehicleDamage();
    
    /**
     * True if has "VehicleDamage" element
     */
    boolean isSetVehicleDamage();
    
    /**
     * Sets the "VehicleDamage" element
     */
    void setVehicleDamage(com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage vehicleDamage);
    
    /**
     * Appends and returns a new empty "VehicleDamage" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage addNewVehicleDamage();
    
    /**
     * Unsets the "VehicleDamage" element
     */
    void unsetVehicleDamage();
    
    /**
     * Gets the "CoverageTypeOfLoss" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum.Enum getCoverageTypeOfLoss();
    
    /**
     * Gets (as xml) the "CoverageTypeOfLoss" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum xgetCoverageTypeOfLoss();
    
    /**
     * True if has "CoverageTypeOfLoss" element
     */
    boolean isSetCoverageTypeOfLoss();
    
    /**
     * Sets the "CoverageTypeOfLoss" element
     */
    void setCoverageTypeOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum.Enum coverageTypeOfLoss);
    
    /**
     * Sets (as xml) the "CoverageTypeOfLoss" element
     */
    void xsetCoverageTypeOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CoverageEnum coverageTypeOfLoss);
    
    /**
     * Unsets the "CoverageTypeOfLoss" element
     */
    void unsetCoverageTypeOfLoss();
    
    /**
     * Gets the "PropertyInfo" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType getPropertyInfo();
    
    /**
     * True if has "PropertyInfo" element
     */
    boolean isSetPropertyInfo();
    
    /**
     * Sets the "PropertyInfo" element
     */
    void setPropertyInfo(com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType propertyInfo);
    
    /**
     * Appends and returns a new empty "PropertyInfo" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PropertyInfoType addNewPropertyInfo();
    
    /**
     * Unsets the "PropertyInfo" element
     */
    void unsetPropertyInfo();
    
    /**
     * Gets the "OwnerDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getOwnerDetails();
    
    /**
     * True if has "OwnerDetails" element
     */
    boolean isSetOwnerDetails();
    
    /**
     * Sets the "OwnerDetails" element
     */
    void setOwnerDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty ownerDetails);
    
    /**
     * Appends and returns a new empty "OwnerDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewOwnerDetails();
    
    /**
     * Unsets the "OwnerDetails" element
     */
    void unsetOwnerDetails();
    
    /**
     * Gets the "DeductibleAmt" element
     */
    java.math.BigDecimal getDeductibleAmt();
    
    /**
     * Gets (as xml) the "DeductibleAmt" element
     */
    org.apache.xmlbeans.XmlDecimal xgetDeductibleAmt();
    
    /**
     * True if has "DeductibleAmt" element
     */
    boolean isSetDeductibleAmt();
    
    /**
     * Sets the "DeductibleAmt" element
     */
    void setDeductibleAmt(java.math.BigDecimal deductibleAmt);
    
    /**
     * Sets (as xml) the "DeductibleAmt" element
     */
    void xsetDeductibleAmt(org.apache.xmlbeans.XmlDecimal deductibleAmt);
    
    /**
     * Unsets the "DeductibleAmt" element
     */
    void unsetDeductibleAmt();
    
    /**
     * Gets the "DeductibleStatus" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum.Enum getDeductibleStatus();
    
    /**
     * Gets (as xml) the "DeductibleStatus" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum xgetDeductibleStatus();
    
    /**
     * True if has "DeductibleStatus" element
     */
    boolean isSetDeductibleStatus();
    
    /**
     * Sets the "DeductibleStatus" element
     */
    void setDeductibleStatus(com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum.Enum deductibleStatus);
    
    /**
     * Sets (as xml) the "DeductibleStatus" element
     */
    void xsetDeductibleStatus(com.mitchell.schemas.mitchellSuffixRqRs.DeductibleStatusEnum deductibleStatus);
    
    /**
     * Unsets the "DeductibleStatus" element
     */
    void unsetDeductibleStatus();
    
    /**
     * Gets the "ClaimantDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getClaimantDetails();
    
    /**
     * True if has "ClaimantDetails" element
     */
    boolean isSetClaimantDetails();
    
    /**
     * Sets the "ClaimantDetails" element
     */
    void setClaimantDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty claimantDetails);
    
    /**
     * Appends and returns a new empty "ClaimantDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewClaimantDetails();
    
    /**
     * Unsets the "ClaimantDetails" element
     */
    void unsetClaimantDetails();
    
    /**
     * Gets the "InsuredDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty getInsuredDetails();
    
    /**
     * True if has "InsuredDetails" element
     */
    boolean isSetInsuredDetails();
    
    /**
     * Sets the "InsuredDetails" element
     */
    void setInsuredDetails(com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty insuredDetails);
    
    /**
     * Appends and returns a new empty "InsuredDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimParty addNewInsuredDetails();
    
    /**
     * Unsets the "InsuredDetails" element
     */
    void unsetInsuredDetails();
    
    /**
     * Gets the "ClaimAdjusterID" element
     */
    java.lang.String getClaimAdjusterID();
    
    /**
     * Gets (as xml) the "ClaimAdjusterID" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type xgetClaimAdjusterID();
    
    /**
     * True if has "ClaimAdjusterID" element
     */
    boolean isSetClaimAdjusterID();
    
    /**
     * Sets the "ClaimAdjusterID" element
     */
    void setClaimAdjusterID(java.lang.String claimAdjusterID);
    
    /**
     * Sets (as xml) the "ClaimAdjusterID" element
     */
    void xsetClaimAdjusterID(com.mitchell.schemas.mitchellSuffixRqRs.UserID15Type claimAdjusterID);
    
    /**
     * Unsets the "ClaimAdjusterID" element
     */
    void unsetClaimAdjusterID();
    
    /**
     * Gets the "SuffixMemo" element
     */
    java.lang.String getSuffixMemo();
    
    /**
     * Gets (as xml) the "SuffixMemo" element
     */
    org.apache.xmlbeans.XmlString xgetSuffixMemo();
    
    /**
     * True if has "SuffixMemo" element
     */
    boolean isSetSuffixMemo();
    
    /**
     * Sets the "SuffixMemo" element
     */
    void setSuffixMemo(java.lang.String suffixMemo);
    
    /**
     * Sets (as xml) the "SuffixMemo" element
     */
    void xsetSuffixMemo(org.apache.xmlbeans.XmlString suffixMemo);
    
    /**
     * Unsets the "SuffixMemo" element
     */
    void unsetSuffixMemo();
    
    /**
     * Gets array of all "CustomElements" elements
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[] getCustomElementsArray();
    
    /**
     * Gets ith "CustomElements" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType getCustomElementsArray(int i);
    
    /**
     * Returns number of "CustomElements" element
     */
    int sizeOfCustomElementsArray();
    
    /**
     * Sets array of all "CustomElements" element
     */
    void setCustomElementsArray(com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType[] customElementsArray);
    
    /**
     * Sets ith "CustomElements" element
     */
    void setCustomElementsArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType customElements);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "CustomElements" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType insertNewCustomElements(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "CustomElements" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType addNewCustomElements();
    
    /**
     * Removes the ith "CustomElements" element
     */
    void removeCustomElements(int i);
    
    /**
     * Gets the "SuffixStatus" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.Enum getSuffixStatus();
    
    /**
     * Gets (as xml) the "SuffixStatus" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType xgetSuffixStatus();
    
    /**
     * True if has "SuffixStatus" element
     */
    boolean isSetSuffixStatus();
    
    /**
     * Sets the "SuffixStatus" element
     */
    void setSuffixStatus(com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.Enum suffixStatus);
    
    /**
     * Sets (as xml) the "SuffixStatus" element
     */
    void xsetSuffixStatus(com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType suffixStatus);
    
    /**
     * Unsets the "SuffixStatus" element
     */
    void unsetSuffixStatus();
    
    /**
     * Gets the "EvaluationResultCategory" element
     */
    java.lang.String getEvaluationResultCategory();
    
    /**
     * Gets (as xml) the "EvaluationResultCategory" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetEvaluationResultCategory();
    
    /**
     * True if has "EvaluationResultCategory" element
     */
    boolean isSetEvaluationResultCategory();
    
    /**
     * Sets the "EvaluationResultCategory" element
     */
    void setEvaluationResultCategory(java.lang.String evaluationResultCategory);
    
    /**
     * Sets (as xml) the "EvaluationResultCategory" element
     */
    void xsetEvaluationResultCategory(com.mitchell.schemas.mitchellSuffixRqRs.String20Type evaluationResultCategory);
    
    /**
     * Unsets the "EvaluationResultCategory" element
     */
    void unsetEvaluationResultCategory();
    
    /**
     * Gets the "RefInfo" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType getRefInfo();
    
    /**
     * True if has "RefInfo" element
     */
    boolean isSetRefInfo();
    
    /**
     * Sets the "RefInfo" element
     */
    void setRefInfo(com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType refInfo);
    
    /**
     * Appends and returns a new empty "RefInfo" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.RefInfoType addNewRefInfo();
    
    /**
     * Unsets the "RefInfo" element
     */
    void unsetRefInfo();
    
    /**
     * An XML VehicleDamage(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
     *
     * This is a complex type.
     */
    public interface VehicleDamage extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(VehicleDamage.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("vehicledamagef3f4elemtype");
        
        /**
         * Gets the "PrimaryPOI" element
         */
        int getPrimaryPOI();
        
        /**
         * Gets (as xml) the "PrimaryPOI" element
         */
        com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum xgetPrimaryPOI();
        
        /**
         * True if has "PrimaryPOI" element
         */
        boolean isSetPrimaryPOI();
        
        /**
         * Sets the "PrimaryPOI" element
         */
        void setPrimaryPOI(int primaryPOI);
        
        /**
         * Sets (as xml) the "PrimaryPOI" element
         */
        void xsetPrimaryPOI(com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum primaryPOI);
        
        /**
         * Unsets the "PrimaryPOI" element
         */
        void unsetPrimaryPOI();
        
        /**
         * Gets the "DamageDescription" element
         */
        java.lang.String getDamageDescription();
        
        /**
         * Gets (as xml) the "DamageDescription" element
         */
        org.apache.xmlbeans.XmlString xgetDamageDescription();
        
        /**
         * True if has "DamageDescription" element
         */
        boolean isSetDamageDescription();
        
        /**
         * Sets the "DamageDescription" element
         */
        void setDamageDescription(java.lang.String damageDescription);
        
        /**
         * Sets (as xml) the "DamageDescription" element
         */
        void xsetDamageDescription(org.apache.xmlbeans.XmlString damageDescription);
        
        /**
         * Unsets the "DamageDescription" element
         */
        void unsetDamageDescription();
        
        /**
         * Gets the "Drivable" element
         */
        java.lang.String getDrivable();
        
        /**
         * Gets (as xml) the "Drivable" element
         */
        com.mitchell.schemas.mitchellSuffixRqRs.Indicator xgetDrivable();
        
        /**
         * True if has "Drivable" element
         */
        boolean isSetDrivable();
        
        /**
         * Sets the "Drivable" element
         */
        void setDrivable(java.lang.String drivable);
        
        /**
         * Sets (as xml) the "Drivable" element
         */
        void xsetDrivable(com.mitchell.schemas.mitchellSuffixRqRs.Indicator drivable);
        
        /**
         * Unsets the "Drivable" element
         */
        void unsetDrivable();
        
        /**
         * Gets array of all "SecondaryPOI" elements
         */
        int[] getSecondaryPOIArray();
        
        /**
         * Gets ith "SecondaryPOI" element
         */
        int getSecondaryPOIArray(int i);
        
        /**
         * Gets (as xml) array of all "SecondaryPOI" elements
         */
        com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[] xgetSecondaryPOIArray();
        
        /**
         * Gets (as xml) ith "SecondaryPOI" element
         */
        com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum xgetSecondaryPOIArray(int i);
        
        /**
         * Returns number of "SecondaryPOI" element
         */
        int sizeOfSecondaryPOIArray();
        
        /**
         * Sets array of all "SecondaryPOI" element
         */
        void setSecondaryPOIArray(int[] secondaryPOIArray);
        
        /**
         * Sets ith "SecondaryPOI" element
         */
        void setSecondaryPOIArray(int i, int secondaryPOI);
        
        /**
         * Sets (as xml) array of all "SecondaryPOI" element
         */
        void xsetSecondaryPOIArray(com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum[] secondaryPOIArray);
        
        /**
         * Sets (as xml) ith "SecondaryPOI" element
         */
        void xsetSecondaryPOIArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum secondaryPOI);
        
        /**
         * Inserts the value as the ith "SecondaryPOI" element
         */
        void insertSecondaryPOI(int i, int secondaryPOI);
        
        /**
         * Appends the value as the last "SecondaryPOI" element
         */
        void addSecondaryPOI(int secondaryPOI);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "SecondaryPOI" element
         */
        com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum insertNewSecondaryPOI(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "SecondaryPOI" element
         */
        com.mitchell.schemas.mitchellSuffixRqRs.PointofImpactCodeEnum addNewSecondaryPOI();
        
        /**
         * Removes the ith "SecondaryPOI" element
         */
        void removeSecondaryPOI(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage newInstance() {
              return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType.VehicleDamage) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
