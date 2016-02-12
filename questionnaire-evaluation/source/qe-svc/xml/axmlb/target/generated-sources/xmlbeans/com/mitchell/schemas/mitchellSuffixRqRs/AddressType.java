/*
 * XML Type:  AddressType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.AddressType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML AddressType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface AddressType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AddressType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("addresstype5e9ctype");
    
    /**
     * Gets the "Address1" element
     */
    java.lang.String getAddress1();
    
    /**
     * Gets (as xml) the "Address1" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetAddress1();
    
    /**
     * True if has "Address1" element
     */
    boolean isSetAddress1();
    
    /**
     * Sets the "Address1" element
     */
    void setAddress1(java.lang.String address1);
    
    /**
     * Sets (as xml) the "Address1" element
     */
    void xsetAddress1(com.mitchell.schemas.mitchellSuffixRqRs.String50Type address1);
    
    /**
     * Unsets the "Address1" element
     */
    void unsetAddress1();
    
    /**
     * Gets the "Address2" element
     */
    java.lang.String getAddress2();
    
    /**
     * Gets (as xml) the "Address2" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetAddress2();
    
    /**
     * True if has "Address2" element
     */
    boolean isSetAddress2();
    
    /**
     * Sets the "Address2" element
     */
    void setAddress2(java.lang.String address2);
    
    /**
     * Sets (as xml) the "Address2" element
     */
    void xsetAddress2(com.mitchell.schemas.mitchellSuffixRqRs.String50Type address2);
    
    /**
     * Unsets the "Address2" element
     */
    void unsetAddress2();
    
    /**
     * Gets the "City" element
     */
    java.lang.String getCity();
    
    /**
     * Gets (as xml) the "City" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetCity();
    
    /**
     * True if has "City" element
     */
    boolean isSetCity();
    
    /**
     * Sets the "City" element
     */
    void setCity(java.lang.String city);
    
    /**
     * Sets (as xml) the "City" element
     */
    void xsetCity(com.mitchell.schemas.mitchellSuffixRqRs.String50Type city);
    
    /**
     * Unsets the "City" element
     */
    void unsetCity();
    
    /**
     * Gets the "State" element
     */
    java.lang.String getState();
    
    /**
     * Gets (as xml) the "State" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetState();
    
    /**
     * True if has "State" element
     */
    boolean isSetState();
    
    /**
     * Sets the "State" element
     */
    void setState(java.lang.String state);
    
    /**
     * Sets (as xml) the "State" element
     */
    void xsetState(com.mitchell.schemas.mitchellSuffixRqRs.String2Type state);
    
    /**
     * Unsets the "State" element
     */
    void unsetState();
    
    /**
     * Gets the "ZipPostalCode" element
     */
    java.lang.String getZipPostalCode();
    
    /**
     * Gets (as xml) the "ZipPostalCode" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String15Type xgetZipPostalCode();
    
    /**
     * True if has "ZipPostalCode" element
     */
    boolean isSetZipPostalCode();
    
    /**
     * Sets the "ZipPostalCode" element
     */
    void setZipPostalCode(java.lang.String zipPostalCode);
    
    /**
     * Sets (as xml) the "ZipPostalCode" element
     */
    void xsetZipPostalCode(com.mitchell.schemas.mitchellSuffixRqRs.String15Type zipPostalCode);
    
    /**
     * Unsets the "ZipPostalCode" element
     */
    void unsetZipPostalCode();
    
    /**
     * Gets the "CountryCode" element
     */
    java.lang.String getCountryCode();
    
    /**
     * Gets (as xml) the "CountryCode" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String2Type xgetCountryCode();
    
    /**
     * True if has "CountryCode" element
     */
    boolean isSetCountryCode();
    
    /**
     * Sets the "CountryCode" element
     */
    void setCountryCode(java.lang.String countryCode);
    
    /**
     * Sets (as xml) the "CountryCode" element
     */
    void xsetCountryCode(com.mitchell.schemas.mitchellSuffixRqRs.String2Type countryCode);
    
    /**
     * Unsets the "CountryCode" element
     */
    void unsetCountryCode();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AddressType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AddressType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
