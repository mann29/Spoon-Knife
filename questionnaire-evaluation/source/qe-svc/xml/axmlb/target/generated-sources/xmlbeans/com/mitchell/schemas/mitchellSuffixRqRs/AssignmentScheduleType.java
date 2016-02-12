/*
 * XML Type:  AssignmentScheduleType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML AssignmentScheduleType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface AssignmentScheduleType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AssignmentScheduleType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("assignmentscheduletype66datype");
    
    /**
     * Gets the "Urgency" element
     */
    java.math.BigInteger getUrgency();
    
    /**
     * Gets (as xml) the "Urgency" element
     */
    org.apache.xmlbeans.XmlInteger xgetUrgency();
    
    /**
     * True if has "Urgency" element
     */
    boolean isSetUrgency();
    
    /**
     * Sets the "Urgency" element
     */
    void setUrgency(java.math.BigInteger urgency);
    
    /**
     * Sets (as xml) the "Urgency" element
     */
    void xsetUrgency(org.apache.xmlbeans.XmlInteger urgency);
    
    /**
     * Unsets the "Urgency" element
     */
    void unsetUrgency();
    
    /**
     * Gets the "Duration" element
     */
    java.math.BigInteger getDuration();
    
    /**
     * Gets (as xml) the "Duration" element
     */
    org.apache.xmlbeans.XmlInteger xgetDuration();
    
    /**
     * True if has "Duration" element
     */
    boolean isSetDuration();
    
    /**
     * Sets the "Duration" element
     */
    void setDuration(java.math.BigInteger duration);
    
    /**
     * Sets (as xml) the "Duration" element
     */
    void xsetDuration(org.apache.xmlbeans.XmlInteger duration);
    
    /**
     * Unsets the "Duration" element
     */
    void unsetDuration();
    
    /**
     * Gets the "IsTravelRequiredFlag" element
     */
    boolean getIsTravelRequiredFlag();
    
    /**
     * Gets (as xml) the "IsTravelRequiredFlag" element
     */
    org.apache.xmlbeans.XmlBoolean xgetIsTravelRequiredFlag();
    
    /**
     * True if has "IsTravelRequiredFlag" element
     */
    boolean isSetIsTravelRequiredFlag();
    
    /**
     * Sets the "IsTravelRequiredFlag" element
     */
    void setIsTravelRequiredFlag(boolean isTravelRequiredFlag);
    
    /**
     * Sets (as xml) the "IsTravelRequiredFlag" element
     */
    void xsetIsTravelRequiredFlag(org.apache.xmlbeans.XmlBoolean isTravelRequiredFlag);
    
    /**
     * Unsets the "IsTravelRequiredFlag" element
     */
    void unsetIsTravelRequiredFlag();
    
    /**
     * Gets the "IsCallNeeded" element
     */
    boolean getIsCallNeeded();
    
    /**
     * Gets (as xml) the "IsCallNeeded" element
     */
    org.apache.xmlbeans.XmlBoolean xgetIsCallNeeded();
    
    /**
     * True if has "IsCallNeeded" element
     */
    boolean isSetIsCallNeeded();
    
    /**
     * Sets the "IsCallNeeded" element
     */
    void setIsCallNeeded(boolean isCallNeeded);
    
    /**
     * Sets (as xml) the "IsCallNeeded" element
     */
    void xsetIsCallNeeded(org.apache.xmlbeans.XmlBoolean isCallNeeded);
    
    /**
     * Unsets the "IsCallNeeded" element
     */
    void unsetIsCallNeeded();
    
    /**
     * Gets the "ConfirmedDateTime" element
     */
    java.util.Calendar getConfirmedDateTime();
    
    /**
     * Gets (as xml) the "ConfirmedDateTime" element
     */
    org.apache.xmlbeans.XmlDateTime xgetConfirmedDateTime();
    
    /**
     * True if has "ConfirmedDateTime" element
     */
    boolean isSetConfirmedDateTime();
    
    /**
     * Sets the "ConfirmedDateTime" element
     */
    void setConfirmedDateTime(java.util.Calendar confirmedDateTime);
    
    /**
     * Sets (as xml) the "ConfirmedDateTime" element
     */
    void xsetConfirmedDateTime(org.apache.xmlbeans.XmlDateTime confirmedDateTime);
    
    /**
     * Unsets the "ConfirmedDateTime" element
     */
    void unsetConfirmedDateTime();
    
    /**
     * Gets the "PreferredScheduleDate" element
     */
    java.util.Calendar getPreferredScheduleDate();
    
    /**
     * Gets (as xml) the "PreferredScheduleDate" element
     */
    org.apache.xmlbeans.XmlDate xgetPreferredScheduleDate();
    
    /**
     * True if has "PreferredScheduleDate" element
     */
    boolean isSetPreferredScheduleDate();
    
    /**
     * Sets the "PreferredScheduleDate" element
     */
    void setPreferredScheduleDate(java.util.Calendar preferredScheduleDate);
    
    /**
     * Sets (as xml) the "PreferredScheduleDate" element
     */
    void xsetPreferredScheduleDate(org.apache.xmlbeans.XmlDate preferredScheduleDate);
    
    /**
     * Unsets the "PreferredScheduleDate" element
     */
    void unsetPreferredScheduleDate();
    
    /**
     * Gets the "PreferredScheduleStartTime" element
     */
    java.util.Calendar getPreferredScheduleStartTime();
    
    /**
     * Gets (as xml) the "PreferredScheduleStartTime" element
     */
    org.apache.xmlbeans.XmlTime xgetPreferredScheduleStartTime();
    
    /**
     * True if has "PreferredScheduleStartTime" element
     */
    boolean isSetPreferredScheduleStartTime();
    
    /**
     * Sets the "PreferredScheduleStartTime" element
     */
    void setPreferredScheduleStartTime(java.util.Calendar preferredScheduleStartTime);
    
    /**
     * Sets (as xml) the "PreferredScheduleStartTime" element
     */
    void xsetPreferredScheduleStartTime(org.apache.xmlbeans.XmlTime preferredScheduleStartTime);
    
    /**
     * Unsets the "PreferredScheduleStartTime" element
     */
    void unsetPreferredScheduleStartTime();
    
    /**
     * Gets the "PreferredScheduleEndTime" element
     */
    java.util.Calendar getPreferredScheduleEndTime();
    
    /**
     * Gets (as xml) the "PreferredScheduleEndTime" element
     */
    org.apache.xmlbeans.XmlTime xgetPreferredScheduleEndTime();
    
    /**
     * True if has "PreferredScheduleEndTime" element
     */
    boolean isSetPreferredScheduleEndTime();
    
    /**
     * Sets the "PreferredScheduleEndTime" element
     */
    void setPreferredScheduleEndTime(java.util.Calendar preferredScheduleEndTime);
    
    /**
     * Sets (as xml) the "PreferredScheduleEndTime" element
     */
    void xsetPreferredScheduleEndTime(org.apache.xmlbeans.XmlTime preferredScheduleEndTime);
    
    /**
     * Unsets the "PreferredScheduleEndTime" element
     */
    void unsetPreferredScheduleEndTime();
    
    /**
     * Gets the "ScheduledDateTime" element
     */
    java.util.Calendar getScheduledDateTime();
    
    /**
     * Gets (as xml) the "ScheduledDateTime" element
     */
    org.apache.xmlbeans.XmlDateTime xgetScheduledDateTime();
    
    /**
     * True if has "ScheduledDateTime" element
     */
    boolean isSetScheduledDateTime();
    
    /**
     * Sets the "ScheduledDateTime" element
     */
    void setScheduledDateTime(java.util.Calendar scheduledDateTime);
    
    /**
     * Sets (as xml) the "ScheduledDateTime" element
     */
    void xsetScheduledDateTime(org.apache.xmlbeans.XmlDateTime scheduledDateTime);
    
    /**
     * Unsets the "ScheduledDateTime" element
     */
    void unsetScheduledDateTime();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
