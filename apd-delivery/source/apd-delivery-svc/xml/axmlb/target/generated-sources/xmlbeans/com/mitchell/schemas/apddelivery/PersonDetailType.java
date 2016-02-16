/*
 * XML Type:  PersonDetailType
 * Namespace: http://www.mitchell.com/schemas/apddelivery
 * Java type: com.mitchell.schemas.apddelivery.PersonDetailType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.apddelivery;


/**
 * An XML PersonDetailType(@http://www.mitchell.com/schemas/apddelivery).
 *
 * This is a complex type.
 */
public interface PersonDetailType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PersonDetailType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE50513EEB1AA0450D143D08D0542B1B9").resolveHandle("persondetailtype386atype");
    
    /**
     * Gets the "FirstName" element
     */
    java.lang.String getFirstName();
    
    /**
     * Gets (as xml) the "FirstName" element
     */
    org.apache.xmlbeans.XmlString xgetFirstName();
    
    /**
     * True if has "FirstName" element
     */
    boolean isSetFirstName();
    
    /**
     * Sets the "FirstName" element
     */
    void setFirstName(java.lang.String firstName);
    
    /**
     * Sets (as xml) the "FirstName" element
     */
    void xsetFirstName(org.apache.xmlbeans.XmlString firstName);
    
    /**
     * Unsets the "FirstName" element
     */
    void unsetFirstName();
    
    /**
     * Gets the "LastName" element
     */
    java.lang.String getLastName();
    
    /**
     * Gets (as xml) the "LastName" element
     */
    org.apache.xmlbeans.XmlString xgetLastName();
    
    /**
     * True if has "LastName" element
     */
    boolean isSetLastName();
    
    /**
     * Sets the "LastName" element
     */
    void setLastName(java.lang.String lastName);
    
    /**
     * Sets (as xml) the "LastName" element
     */
    void xsetLastName(org.apache.xmlbeans.XmlString lastName);
    
    /**
     * Unsets the "LastName" element
     */
    void unsetLastName();
    
    /**
     * Gets the "PhoneNumber" element
     */
    java.lang.String getPhoneNumber();
    
    /**
     * Gets (as xml) the "PhoneNumber" element
     */
    org.apache.xmlbeans.XmlString xgetPhoneNumber();
    
    /**
     * True if has "PhoneNumber" element
     */
    boolean isSetPhoneNumber();
    
    /**
     * Sets the "PhoneNumber" element
     */
    void setPhoneNumber(java.lang.String phoneNumber);
    
    /**
     * Sets (as xml) the "PhoneNumber" element
     */
    void xsetPhoneNumber(org.apache.xmlbeans.XmlString phoneNumber);
    
    /**
     * Unsets the "PhoneNumber" element
     */
    void unsetPhoneNumber();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.apddelivery.PersonDetailType newInstance() {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.apddelivery.PersonDetailType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.apddelivery.PersonDetailType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
