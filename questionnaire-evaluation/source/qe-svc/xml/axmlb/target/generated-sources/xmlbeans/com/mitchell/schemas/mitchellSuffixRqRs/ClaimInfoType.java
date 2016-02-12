/*
 * XML Type:  ClaimInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML ClaimInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface ClaimInfoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ClaimInfoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("claiminfotype2886type");
    
    /**
     * Gets the "ClaimNumber" element
     */
    java.lang.String getClaimNumber();
    
    /**
     * Gets (as xml) the "ClaimNumber" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String20Type xgetClaimNumber();
    
    /**
     * Sets the "ClaimNumber" element
     */
    void setClaimNumber(java.lang.String claimNumber);
    
    /**
     * Sets (as xml) the "ClaimNumber" element
     */
    void xsetClaimNumber(com.mitchell.schemas.mitchellSuffixRqRs.String20Type claimNumber);
    
    /**
     * Gets the "IsDraftClaimFlag" element
     */
    boolean getIsDraftClaimFlag();
    
    /**
     * Gets (as xml) the "IsDraftClaimFlag" element
     */
    org.apache.xmlbeans.XmlBoolean xgetIsDraftClaimFlag();
    
    /**
     * Sets the "IsDraftClaimFlag" element
     */
    void setIsDraftClaimFlag(boolean isDraftClaimFlag);
    
    /**
     * Sets (as xml) the "IsDraftClaimFlag" element
     */
    void xsetIsDraftClaimFlag(org.apache.xmlbeans.XmlBoolean isDraftClaimFlag);
    
    /**
     * Gets the "PolicyNumber" element
     */
    java.lang.String getPolicyNumber();
    
    /**
     * Gets (as xml) the "PolicyNumber" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType xgetPolicyNumber();
    
    /**
     * True if has "PolicyNumber" element
     */
    boolean isSetPolicyNumber();
    
    /**
     * Sets the "PolicyNumber" element
     */
    void setPolicyNumber(java.lang.String policyNumber);
    
    /**
     * Sets (as xml) the "PolicyNumber" element
     */
    void xsetPolicyNumber(com.mitchell.schemas.mitchellSuffixRqRs.MitchellPolicyNumType policyNumber);
    
    /**
     * Unsets the "PolicyNumber" element
     */
    void unsetPolicyNumber();
    
    /**
     * Gets the "PolicyStateProvince" element
     */
    java.lang.String getPolicyStateProvince();
    
    /**
     * Gets (as xml) the "PolicyStateProvince" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetPolicyStateProvince();
    
    /**
     * True if has "PolicyStateProvince" element
     */
    boolean isSetPolicyStateProvince();
    
    /**
     * Sets the "PolicyStateProvince" element
     */
    void setPolicyStateProvince(java.lang.String policyStateProvince);
    
    /**
     * Sets (as xml) the "PolicyStateProvince" element
     */
    void xsetPolicyStateProvince(com.mitchell.schemas.mitchellSuffixRqRs.String2Type policyStateProvince);
    
    /**
     * Unsets the "PolicyStateProvince" element
     */
    void unsetPolicyStateProvince();
    
    /**
     * Gets the "DateOfLoss" element
     */
    java.util.Calendar getDateOfLoss();
    
    /**
     * Gets (as xml) the "DateOfLoss" element
     */
    org.apache.xmlbeans.XmlDate xgetDateOfLoss();
    
    /**
     * True if has "DateOfLoss" element
     */
    boolean isSetDateOfLoss();
    
    /**
     * Sets the "DateOfLoss" element
     */
    void setDateOfLoss(java.util.Calendar dateOfLoss);
    
    /**
     * Sets (as xml) the "DateOfLoss" element
     */
    void xsetDateOfLoss(org.apache.xmlbeans.XmlDate dateOfLoss);
    
    /**
     * Unsets the "DateOfLoss" element
     */
    void unsetDateOfLoss();
    
    /**
     * Gets the "DateReported" element
     */
    java.util.Calendar getDateReported();
    
    /**
     * Gets (as xml) the "DateReported" element
     */
    org.apache.xmlbeans.XmlDate xgetDateReported();
    
    /**
     * True if has "DateReported" element
     */
    boolean isSetDateReported();
    
    /**
     * Sets the "DateReported" element
     */
    void setDateReported(java.util.Calendar dateReported);
    
    /**
     * Sets (as xml) the "DateReported" element
     */
    void xsetDateReported(org.apache.xmlbeans.XmlDate dateReported);
    
    /**
     * Unsets the "DateReported" element
     */
    void unsetDateReported();
    
    /**
     * Gets the "CauseOfLoss" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum.Enum getCauseOfLoss();
    
    /**
     * Gets (as xml) the "CauseOfLoss" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum xgetCauseOfLoss();
    
    /**
     * True if has "CauseOfLoss" element
     */
    boolean isSetCauseOfLoss();
    
    /**
     * Sets the "CauseOfLoss" element
     */
    void setCauseOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum.Enum causeOfLoss);
    
    /**
     * Sets (as xml) the "CauseOfLoss" element
     */
    void xsetCauseOfLoss(com.mitchell.schemas.mitchellSuffixRqRs.CauseOfLossEnum causeOfLoss);
    
    /**
     * Unsets the "CauseOfLoss" element
     */
    void unsetCauseOfLoss();
    
    /**
     * Gets the "UnderwritingCompany" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType getUnderwritingCompany();
    
    /**
     * True if has "UnderwritingCompany" element
     */
    boolean isSetUnderwritingCompany();
    
    /**
     * Sets the "UnderwritingCompany" element
     */
    void setUnderwritingCompany(com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType underwritingCompany);
    
    /**
     * Appends and returns a new empty "UnderwritingCompany" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ClaimPartyBusinessType addNewUnderwritingCompany();
    
    /**
     * Unsets the "UnderwritingCompany" element
     */
    void unsetUnderwritingCompany();
    
    /**
     * Gets the "CatFlag" element
     */
    boolean getCatFlag();
    
    /**
     * Gets (as xml) the "CatFlag" element
     */
    org.apache.xmlbeans.XmlBoolean xgetCatFlag();
    
    /**
     * True if has "CatFlag" element
     */
    boolean isSetCatFlag();
    
    /**
     * Sets the "CatFlag" element
     */
    void setCatFlag(boolean catFlag);
    
    /**
     * Sets (as xml) the "CatFlag" element
     */
    void xsetCatFlag(org.apache.xmlbeans.XmlBoolean catFlag);
    
    /**
     * Unsets the "CatFlag" element
     */
    void unsetCatFlag();
    
    /**
     * Gets the "LossStateProvince" element
     */
    java.lang.String getLossStateProvince();
    
    /**
     * Gets (as xml) the "LossStateProvince" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetLossStateProvince();
    
    /**
     * True if has "LossStateProvince" element
     */
    boolean isSetLossStateProvince();
    
    /**
     * Sets the "LossStateProvince" element
     */
    void setLossStateProvince(java.lang.String lossStateProvince);
    
    /**
     * Sets (as xml) the "LossStateProvince" element
     */
    void xsetLossStateProvince(com.mitchell.schemas.mitchellSuffixRqRs.String2Type lossStateProvince);
    
    /**
     * Unsets the "LossStateProvince" element
     */
    void unsetLossStateProvince();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ClaimInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
