/*
 * XML Type:  AdditionalInfoType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML AdditionalInfoType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface AdditionalInfoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AdditionalInfoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("additionalinfotype4a49type");
    
    /**
     * Gets array of all "NameValuePair" elements
     */
    com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[] getNameValuePairArray();
    
    /**
     * Gets ith "NameValuePair" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType getNameValuePairArray(int i);
    
    /**
     * Returns number of "NameValuePair" element
     */
    int sizeOfNameValuePairArray();
    
    /**
     * Sets array of all "NameValuePair" element
     */
    void setNameValuePairArray(com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType[] nameValuePairArray);
    
    /**
     * Sets ith "NameValuePair" element
     */
    void setNameValuePairArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType nameValuePair);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "NameValuePair" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType insertNewNameValuePair(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "NameValuePair" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.NameValuePairType addNewNameValuePair();
    
    /**
     * Removes the ith "NameValuePair" element
     */
    void removeNameValuePair(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
