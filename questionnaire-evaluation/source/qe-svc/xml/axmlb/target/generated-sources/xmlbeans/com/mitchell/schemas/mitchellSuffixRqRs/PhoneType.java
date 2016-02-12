/*
 * XML Type:  PhoneType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.PhoneType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs;


/**
 * An XML PhoneType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public interface PhoneType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PhoneType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("phonetypeba22type");
    
    /**
     * Gets the "Qualifier" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier.Enum getQualifier();
    
    /**
     * Gets (as xml) the "Qualifier" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier xgetQualifier();
    
    /**
     * Sets the "Qualifier" element
     */
    void setQualifier(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier.Enum qualifier);
    
    /**
     * Sets (as xml) the "Qualifier" element
     */
    void xsetQualifier(com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier qualifier);
    
    /**
     * Gets the "Number" element
     */
    java.lang.String getNumber();
    
    /**
     * Gets (as xml) the "Number" element
     */
    com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension xgetNumber();
    
    /**
     * Sets the "Number" element
     */
    void setNumber(java.lang.String number);
    
    /**
     * Sets (as xml) the "Number" element
     */
    void xsetNumber(com.mitchell.schemas.mitchellSuffixRqRs.PhoneNumberWith10DigitExtension number);
    
    /**
     * An XML Qualifier(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
     *
     * This is an atomic type that is a restriction of com.mitchell.schemas.mitchellSuffixRqRs.PhoneType$Qualifier.
     */
    public interface Qualifier extends org.apache.xmlbeans.XmlString
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Qualifier.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD1351227E070F6317EDD36450E0E5DD5").resolveHandle("qualifier006celemtype");
        
        org.apache.xmlbeans.StringEnumAbstractBase enumValue();
        void set(org.apache.xmlbeans.StringEnumAbstractBase e);
        
        static final Enum WP = Enum.forString("WP");
        static final Enum HP = Enum.forString("HP");
        static final Enum CP = Enum.forString("CP");
        static final Enum FX = Enum.forString("FX");
        
        static final int INT_WP = Enum.INT_WP;
        static final int INT_HP = Enum.INT_HP;
        static final int INT_CP = Enum.INT_CP;
        static final int INT_FX = Enum.INT_FX;
        
        /**
         * Enumeration value class for com.mitchell.schemas.mitchellSuffixRqRs.PhoneType$Qualifier.
         * These enum values can be used as follows:
         * <pre>
         * enum.toString(); // returns the string value of the enum
         * enum.intValue(); // returns an int value, useful for switches
         * // e.g., case Enum.INT_WP
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
            
            static final int INT_WP = 1;
            static final int INT_HP = 2;
            static final int INT_CP = 3;
            static final int INT_FX = 4;
            
            public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
                new org.apache.xmlbeans.StringEnumAbstractBase.Table
            (
                new Enum[]
                {
                    new Enum("WP", INT_WP),
                    new Enum("HP", INT_HP),
                    new Enum("CP", INT_CP),
                    new Enum("FX", INT_FX),
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
            public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier newValue(java.lang.Object obj) {
              return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier) type.newValue( obj ); }
            
            public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier newInstance() {
              return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType.Qualifier) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType newInstance() {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.mitchellSuffixRqRs.PhoneType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.mitchellSuffixRqRs.PhoneType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
