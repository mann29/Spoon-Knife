/*
 * XML Type:  QuestionnaireClaimLinkType
 * Namespace: http://www.mitchell.com/schemas/questionnaire
 * Java type: com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.questionnaire;


/**
 * An XML QuestionnaireClaimLinkType(@http://www.mitchell.com/schemas/questionnaire).
 *
 * This is a complex type.
 */
public interface QuestionnaireClaimLinkType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(QuestionnaireClaimLinkType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("questionnaireclaimlinktype6031type");
    
    /**
     * Gets the "CoCd" element
     */
    java.lang.String getCoCd();
    
    /**
     * Gets (as xml) the "CoCd" element
     */
    org.apache.xmlbeans.XmlString xgetCoCd();
    
    /**
     * Sets the "CoCd" element
     */
    void setCoCd(java.lang.String coCd);
    
    /**
     * Sets (as xml) the "CoCd" element
     */
    void xsetCoCd(org.apache.xmlbeans.XmlString coCd);
    
    /**
     * Gets the "ClaimID" element
     */
    long getClaimID();
    
    /**
     * Gets (as xml) the "ClaimID" element
     */
    org.apache.xmlbeans.XmlLong xgetClaimID();
    
    /**
     * Sets the "ClaimID" element
     */
    void setClaimID(long claimID);
    
    /**
     * Sets (as xml) the "ClaimID" element
     */
    void xsetClaimID(org.apache.xmlbeans.XmlLong claimID);
    
    /**
     * Gets the "QuestionnairesAssociations" element
     */
    com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType getQuestionnairesAssociations();
    
    /**
     * Sets the "QuestionnairesAssociations" element
     */
    void setQuestionnairesAssociations(com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType questionnairesAssociations);
    
    /**
     * Appends and returns a new empty "QuestionnairesAssociations" element
     */
    com.mitchell.schemas.questionnaire.QuestionnairesAssociationsType addNewQuestionnairesAssociations();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType newInstance() {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.questionnaire.QuestionnaireClaimLinkType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
