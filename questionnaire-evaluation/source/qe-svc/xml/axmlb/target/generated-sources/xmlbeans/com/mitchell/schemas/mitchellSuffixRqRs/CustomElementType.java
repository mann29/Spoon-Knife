/*
 * XML Type:  CustomElementType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML CustomElementType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface CustomElementType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CustomElementType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("customelementtype4fa5type");
    
    /**
     * Gets the "CustomElementID" element
     */
    java.lang.String getCustomElementID();
    
    /**
     * Gets (as xml) the "CustomElementID" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetCustomElementID();
    
    /**
     * Sets the "CustomElementID" element
     */
    void setCustomElementID(java.lang.String customElementID);
    
    /**
     * Sets (as xml) the "CustomElementID" element
     */
    void xsetCustomElementID(com.mitchell.schemas.mitchellSuffixRqRs.String80Type customElementID);
    
    /**
     * Gets the "CustomElementText" element
     */
    java.lang.String getCustomElementText();
    
    /**
     * Gets (as xml) the "CustomElementText" element
     */
    org.apache.xmlbeans.XmlString xgetCustomElementText();
    
    /**
     * True if has "CustomElementText" element
     */
    boolean isSetCustomElementText();
    
    /**
     * Sets the "CustomElementText" element
     */
    void setCustomElementText(java.lang.String customElementText);
    
    /**
     * Sets (as xml) the "CustomElementText" element
     */
    void xsetCustomElementText(org.apache.xmlbeans.XmlString customElementText);
    
    /**
     * Unsets the "CustomElementText" element
     */
    void unsetCustomElementText();
    
    /**
     * Gets the "CustomElementCurrency" element
     */
    java.math.BigDecimal getCustomElementCurrency();
    
    /**
     * Gets (as xml) the "CustomElementCurrency" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.Currency xgetCustomElementCurrency();
    
    /**
     * True if has "CustomElementCurrency" element
     */
    boolean isSetCustomElementCurrency();
    
    /**
     * Sets the "CustomElementCurrency" element
     */
    void setCustomElementCurrency(java.math.BigDecimal customElementCurrency);
    
    /**
     * Sets (as xml) the "CustomElementCurrency" element
     */
    void xsetCustomElementCurrency(com.mitchell.schemas.mitchellSuffixRqRs.Currency customElementCurrency);
    
    /**
     * Unsets the "CustomElementCurrency" element
     */
    void unsetCustomElementCurrency();
    
    /**
     * Gets the "CustomElementDecimal" element
     */
    java.math.BigDecimal getCustomElementDecimal();
    
    /**
     * Gets (as xml) the "CustomElementDecimal" element
     */
    org.apache.xmlbeans.XmlDecimal xgetCustomElementDecimal();
    
    /**
     * True if has "CustomElementDecimal" element
     */
    boolean isSetCustomElementDecimal();
    
    /**
     * Sets the "CustomElementDecimal" element
     */
    void setCustomElementDecimal(java.math.BigDecimal customElementDecimal);
    
    /**
     * Sets (as xml) the "CustomElementDecimal" element
     */
    void xsetCustomElementDecimal(org.apache.xmlbeans.XmlDecimal customElementDecimal);
    
    /**
     * Unsets the "CustomElementDecimal" element
     */
    void unsetCustomElementDecimal();
    
    /**
     * Gets the "CustomElementDate" element
     */
    java.util.Calendar getCustomElementDate();
    
    /**
     * Gets (as xml) the "CustomElementDate" element
     */
    org.apache.xmlbeans.XmlDate xgetCustomElementDate();
    
    /**
     * True if has "CustomElementDate" element
     */
    boolean isSetCustomElementDate();
    
    /**
     * Sets the "CustomElementDate" element
     */
    void setCustomElementDate(java.util.Calendar customElementDate);
    
    /**
     * Sets (as xml) the "CustomElementDate" element
     */
    void xsetCustomElementDate(org.apache.xmlbeans.XmlDate customElementDate);
    
    /**
     * Unsets the "CustomElementDate" element
     */
    void unsetCustomElementDate();
    
    /**
     * Gets the "CustomElementDateTime" element
     */
    java.util.Calendar getCustomElementDateTime();
    
    /**
     * Gets (as xml) the "CustomElementDateTime" element
     */
    org.apache.xmlbeans.XmlDateTime xgetCustomElementDateTime();
    
    /**
     * True if has "CustomElementDateTime" element
     */
    boolean isSetCustomElementDateTime();
    
    /**
     * Sets the "CustomElementDateTime" element
     */
    void setCustomElementDateTime(java.util.Calendar customElementDateTime);
    
    /**
     * Sets (as xml) the "CustomElementDateTime" element
     */
    void xsetCustomElementDateTime(org.apache.xmlbeans.XmlDateTime customElementDateTime);
    
    /**
     * Unsets the "CustomElementDateTime" element
     */
    void unsetCustomElementDateTime();
    
    /**
     * Gets the "CustomElementInd" element
     */
    java.lang.String getCustomElementInd();
    
    /**
     * Gets (as xml) the "CustomElementInd" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.Indicator xgetCustomElementInd();
    
    /**
     * True if has "CustomElementInd" element
     */
    boolean isSetCustomElementInd();
    
    /**
     * Sets the "CustomElementInd" element
     */
    void setCustomElementInd(java.lang.String customElementInd);
    
    /**
     * Sets (as xml) the "CustomElementInd" element
     */
    void xsetCustomElementInd(com.mitchell.schemas.mitchellSuffixRqRs.Indicator customElementInd);
    
    /**
     * Unsets the "CustomElementInd" element
     */
    void unsetCustomElementInd();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.CustomElementType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
