/*
 * XML Type:  ResourceType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.ResourceType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML ResourceType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface ResourceType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ResourceType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("resourcetype90d0type");
    
    /**
     * Gets the "ResourceID" element
     */
    java.lang.String getResourceID();
    
    /**
     * Gets (as xml) the "ResourceID" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type xgetResourceID();
    
    /**
     * Sets the "ResourceID" element
     */
    void setResourceID(java.lang.String resourceID);
    
    /**
     * Sets (as xml) the "ResourceID" element
     */
    void xsetResourceID(com.mitchell.schemas.mitchellSuffixRqRs.UserID8Type resourceID);
    
    /**
     * Gets the "ResourceType" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum.Enum getResourceType();
    
    /**
     * Gets (as xml) the "ResourceType" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum xgetResourceType();
    
    /**
     * Sets the "ResourceType" element
     */
    void setResourceType(com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum.Enum resourceType);
    
    /**
     * Sets (as xml) the "ResourceType" element
     */
    void xsetResourceType(com.mitchell.schemas.mitchellSuffixRqRs.ResourceTypeEnum resourceType);
    
    /**
     * Gets the "BusinessName" element
     */
    java.lang.String getBusinessName();
    
    /**
     * Gets (as xml) the "BusinessName" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetBusinessName();
    
    /**
     * True if has "BusinessName" element
     */
    boolean isSetBusinessName();
    
    /**
     * Sets the "BusinessName" element
     */
    void setBusinessName(java.lang.String businessName);
    
    /**
     * Sets (as xml) the "BusinessName" element
     */
    void xsetBusinessName(com.mitchell.schemas.mitchellSuffixRqRs.String80Type businessName);
    
    /**
     * Unsets the "BusinessName" element
     */
    void unsetBusinessName();
    
    /**
     * Gets the "FirstName" element
     */
    java.lang.String getFirstName();
    
    /**
     * Gets (as xml) the "FirstName" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetFirstName();
    
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
    void xsetFirstName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type firstName);
    
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
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetLastName();
    
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
    void xsetLastName(com.mitchell.schemas.mitchellSuffixRqRs.String50Type lastName);
    
    /**
     * Unsets the "LastName" element
     */
    void unsetLastName();
    
    /**
     * Gets the "Email" element
     */
    java.lang.String getEmail();
    
    /**
     * Gets (as xml) the "Email" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String80Type xgetEmail();
    
    /**
     * True if has "Email" element
     */
    boolean isSetEmail();
    
    /**
     * Sets the "Email" element
     */
    void setEmail(java.lang.String email);
    
    /**
     * Sets (as xml) the "Email" element
     */
    void xsetEmail(com.mitchell.schemas.mitchellSuffixRqRs.String80Type email);
    
    /**
     * Unsets the "Email" element
     */
    void unsetEmail();
    
    /**
     * Gets array of all "ContactDetails" elements
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[] getContactDetailsArray();
    
    /**
     * Gets ith "ContactDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType getContactDetailsArray(int i);
    
    /**
     * Returns number of "ContactDetails" element
     */
    int sizeOfContactDetailsArray();
    
    /**
     * Sets array of all "ContactDetails" element
     */
    void setContactDetailsArray(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType[] contactDetailsArray);
    
    /**
     * Sets ith "ContactDetails" element
     */
    void setContactDetailsArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.PhoneType contactDetails);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "ContactDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType insertNewContactDetails(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "ContactDetails" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType addNewContactDetails();
    
    /**
     * Removes the ith "ContactDetails" element
     */
    void removeContactDetails(int i);
    
    /**
     * Gets the "Address" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.AddressType getAddress();
    
    /**
     * True if has "Address" element
     */
    boolean isSetAddress();
    
    /**
     * Sets the "Address" element
     */
    void setAddress(com.mitchell.schemas.mitchellSuffixRqRs.AddressType address);
    
    /**
     * Appends and returns a new empty "Address" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.AddressType addNewAddress();
    
    /**
     * Unsets the "Address" element
     */
    void unsetAddress();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.ResourceType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.ResourceType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
