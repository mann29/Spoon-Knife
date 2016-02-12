/*
 * XML Type:  SkillsListType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML SkillsListType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface SkillsListType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SkillsListType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("skillslisttype5edetype");
    
    /**
     * Gets array of all "Skill" elements
     */
    java.lang.String[] getSkillArray();
    
    /**
     * Gets ith "Skill" element
     */
    java.lang.String getSkillArray(int i);
    
    /**
     * Gets (as xml) array of all "Skill" elements
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SkillType[] xgetSkillArray();
    
    /**
     * Gets (as xml) ith "Skill" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SkillType xgetSkillArray(int i);
    
    /**
     * Returns number of "Skill" element
     */
    int sizeOfSkillArray();
    
    /**
     * Sets array of all "Skill" element
     */
    void setSkillArray(java.lang.String[] skillArray);
    
    /**
     * Sets ith "Skill" element
     */
    void setSkillArray(int i, java.lang.String skill);
    
    /**
     * Sets (as xml) array of all "Skill" element
     */
    void xsetSkillArray(com.mitchell.schemas.mitchellSuffixRqRs.SkillType[] skillArray);
    
    /**
     * Sets (as xml) ith "Skill" element
     */
    void xsetSkillArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.SkillType skill);
    
    /**
     * Inserts the value as the ith "Skill" element
     */
    void insertSkill(int i, java.lang.String skill);
    
    /**
     * Appends the value as the last "Skill" element
     */
    void addSkill(java.lang.String skill);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Skill" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SkillType insertNewSkill(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Skill" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.SkillType addNewSkill();
    
    /**
     * Removes the ith "Skill" element
     */
    void removeSkill(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
