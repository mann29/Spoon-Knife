/*
 * XML Type:  QuestionnaireInfoType
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnaireInfoType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire;


/**
 * An XML QuestionnaireInfoType(@http://www.mitchell.com/schemas/questionnaire).
 *
 * This is a complex type.
 */
public interface QuestionnaireInfoType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(QuestionnaireInfoType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("questionnaireinfotypeccfdtype");
    
    /**
     * Gets the "QuestionnaireID" element
     */
    long getQuestionnaireID();
    
    /**
     * Gets (as xml) the "QuestionnaireID" element
     */
    org.apache.xmlbeans.XmlLong xgetQuestionnaireID();
    
    /**
     * Sets the "QuestionnaireID" element
     */
    void setQuestionnaireID(long questionnaireID);
    
    /**
     * Sets (as xml) the "QuestionnaireID" element
     */
    void xsetQuestionnaireID(org.apache.xmlbeans.XmlLong questionnaireID);
    
    /**
     * Gets the "QuestionnaireDocID" element
     */
    long getQuestionnaireDocID();
    
    /**
     * Gets (as xml) the "QuestionnaireDocID" element
     */
    org.apache.xmlbeans.XmlLong xgetQuestionnaireDocID();
    
    /**
     * True if has "QuestionnaireDocID" element
     */
    boolean isSetQuestionnaireDocID();
    
    /**
     * Sets the "QuestionnaireDocID" element
     */
    void setQuestionnaireDocID(long questionnaireDocID);
    
    /**
     * Sets (as xml) the "QuestionnaireDocID" element
     */
    void xsetQuestionnaireDocID(org.apache.xmlbeans.XmlLong questionnaireDocID);
    
    /**
     * Unsets the "QuestionnaireDocID" element
     */
    void unsetQuestionnaireDocID();
    
    /**
     * Gets the "ClaimExposureID" element
     */
    long getClaimExposureID();
    
    /**
     * Gets (as xml) the "ClaimExposureID" element
     */
    org.apache.xmlbeans.XmlLong xgetClaimExposureID();
    
    /**
     * True if has "ClaimExposureID" element
     */
    boolean isSetClaimExposureID();
    
    /**
     * Sets the "ClaimExposureID" element
     */
    void setClaimExposureID(long claimExposureID);
    
    /**
     * Sets (as xml) the "ClaimExposureID" element
     */
    void xsetClaimExposureID(org.apache.xmlbeans.XmlLong claimExposureID);
    
    /**
     * Unsets the "ClaimExposureID" element
     */
    void unsetClaimExposureID();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType newInstance() {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.questionnaire.QuestionnaireInfoType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireInfoType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
