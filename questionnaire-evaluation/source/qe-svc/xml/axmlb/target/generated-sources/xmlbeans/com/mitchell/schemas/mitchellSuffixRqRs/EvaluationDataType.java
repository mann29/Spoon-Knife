/*
 * XML Type:  EvaluationDataType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML EvaluationDataType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface EvaluationDataType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(EvaluationDataType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("evaluationdatatype5398type");
    
    /**
     * Gets the "ExternalEvaluationID" element
     */
    java.lang.String getExternalEvaluationID();
    
    /**
     * Gets (as xml) the "ExternalEvaluationID" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.String50Type xgetExternalEvaluationID();
    
    /**
     * Sets the "ExternalEvaluationID" element
     */
    void setExternalEvaluationID(java.lang.String externalEvaluationID);
    
    /**
     * Sets (as xml) the "ExternalEvaluationID" element
     */
    void xsetExternalEvaluationID(com.mitchell.schemas.mitchellSuffixRqRs.String50Type externalEvaluationID);
    
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
     * Gets the "EvaluationScore" element
     */
    int getEvaluationScore();
    
    /**
     * Gets (as xml) the "EvaluationScore" element
     */
    org.apache.xmlbeans.XmlInt xgetEvaluationScore();
    
    /**
     * True if has "EvaluationScore" element
     */
    boolean isSetEvaluationScore();
    
    /**
     * Sets the "EvaluationScore" element
     */
    void setEvaluationScore(int evaluationScore);
    
    /**
     * Sets (as xml) the "EvaluationScore" element
     */
    void xsetEvaluationScore(org.apache.xmlbeans.XmlInt evaluationScore);
    
    /**
     * Unsets the "EvaluationScore" element
     */
    void unsetEvaluationScore();
    
    /**
     * Gets the "EvaluationResult" element
     */
    boolean getEvaluationResult();
    
    /**
     * Gets (as xml) the "EvaluationResult" element
     */
    org.apache.xmlbeans.XmlBoolean xgetEvaluationResult();
    
    /**
     * True if has "EvaluationResult" element
     */
    boolean isSetEvaluationResult();
    
    /**
     * Sets the "EvaluationResult" element
     */
    void setEvaluationResult(boolean evaluationResult);
    
    /**
     * Sets (as xml) the "EvaluationResult" element
     */
    void xsetEvaluationResult(org.apache.xmlbeans.XmlBoolean evaluationResult);
    
    /**
     * Unsets the "EvaluationResult" element
     */
    void unsetEvaluationResult();
    
    /**
     * Gets the "EvaluationAnswers" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType getEvaluationAnswers();
    
    /**
     * True if has "EvaluationAnswers" element
     */
    boolean isSetEvaluationAnswers();
    
    /**
     * Sets the "EvaluationAnswers" element
     */
    void setEvaluationAnswers(com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType evaluationAnswers);
    
    /**
     * Appends and returns a new empty "EvaluationAnswers" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.AdditionalInfoType addNewEvaluationAnswers();
    
    /**
     * Unsets the "EvaluationAnswers" element
     */
    void unsetEvaluationAnswers();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.EvaluationDataType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
