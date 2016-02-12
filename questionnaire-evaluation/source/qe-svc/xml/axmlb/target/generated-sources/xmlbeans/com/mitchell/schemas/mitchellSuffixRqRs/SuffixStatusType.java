/*
 * XML Type:  SuffixStatusType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML SuffixStatusType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is an atomic type that is a restriction of com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.
 */
public interface SuffixStatusType extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SuffixStatusType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("suffixstatustype9b1btype");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum CANCELED = Enum.forString("CANCELED");
    static final Enum CLOSED = Enum.forString("CLOSED");
    static final Enum DELETED = Enum.forString("DELETED");
    static final Enum ON_HOLD = Enum.forString("ON_HOLD");
    static final Enum OPEN = Enum.forString("OPEN");
    static final Enum REOPENED = Enum.forString("REOPENED");
    
    static final int INT_CANCELED = Enum.INT_CANCELED;
    static final int INT_CLOSED = Enum.INT_CLOSED;
    static final int INT_DELETED = Enum.INT_DELETED;
    static final int INT_ON_HOLD = Enum.INT_ON_HOLD;
    static final int INT_OPEN = Enum.INT_OPEN;
    static final int INT_REOPENED = Enum.INT_REOPENED;
    
    /**
     * Enumeration value class for com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_CANCELED
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_CANCELED = 1;
        static final int INT_CLOSED = 2;
        static final int INT_DELETED = 3;
        static final int INT_ON_HOLD = 4;
        static final int INT_OPEN = 5;
        static final int INT_REOPENED = 6;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("CANCELED", INT_CANCELED),
                new Enum("CLOSED", INT_CLOSED),
                new Enum("DELETED", INT_DELETED),
                new Enum("ON_HOLD", INT_ON_HOLD),
                new Enum("OPEN", INT_OPEN),
                new Enum("REOPENED", INT_REOPENED),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType newValue(java.lang.Object obj) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) type.newValue( obj ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.SuffixStatusType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
