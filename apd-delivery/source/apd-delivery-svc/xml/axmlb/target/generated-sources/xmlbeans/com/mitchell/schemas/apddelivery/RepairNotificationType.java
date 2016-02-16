/*
 * XML Type:  RepairNotificationType
 * Namespace: http://www.mitchell.com/schemas/apddelivery
 * Java type: com.mitchell.schemas.apddelivery.RepairNotificationType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.apddelivery;


/**
 * An XML RepairNotificationType(@http://www.mitchell.com/schemas/apddelivery).
 *
 * This is a complex type.
 */
public interface RepairNotificationType extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RepairNotificationType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE50513EEB1AA0450D143D08D0542B1B9").resolveHandle("repairnotificationtypee6bctype");
    
    /**
     * Gets the "ClaimNumber" element
     */
    java.lang.String getClaimNumber();
    
    /**
     * Gets (as xml) the "ClaimNumber" element
     */
    org.apache.xmlbeans.XmlString xgetClaimNumber();
    
    /**
     * Sets the "ClaimNumber" element
     */
    void setClaimNumber(java.lang.String claimNumber);
    
    /**
     * Sets (as xml) the "ClaimNumber" element
     */
    void xsetClaimNumber(org.apache.xmlbeans.XmlString claimNumber);
    
    /**
     * Gets the "Vehicle" element
     */
    com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle getVehicle();
    
    /**
     * True if has "Vehicle" element
     */
    boolean isSetVehicle();
    
    /**
     * Sets the "Vehicle" element
     */
    void setVehicle(com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle vehicle);
    
    /**
     * Appends and returns a new empty "Vehicle" element
     */
    com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle addNewVehicle();
    
    /**
     * Unsets the "Vehicle" element
     */
    void unsetVehicle();
    
    /**
     * Gets the "Owner" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType getOwner();
    
    /**
     * True if has "Owner" element
     */
    boolean isSetOwner();
    
    /**
     * Sets the "Owner" element
     */
    void setOwner(com.mitchell.schemas.apddelivery.PersonDetailType owner);
    
    /**
     * Appends and returns a new empty "Owner" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType addNewOwner();
    
    /**
     * Unsets the "Owner" element
     */
    void unsetOwner();
    
    /**
     * Gets the "Insured" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType getInsured();
    
    /**
     * True if has "Insured" element
     */
    boolean isSetInsured();
    
    /**
     * Sets the "Insured" element
     */
    void setInsured(com.mitchell.schemas.apddelivery.PersonDetailType insured);
    
    /**
     * Appends and returns a new empty "Insured" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType addNewInsured();
    
    /**
     * Unsets the "Insured" element
     */
    void unsetInsured();
    
    /**
     * Gets the "Claimant" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType getClaimant();
    
    /**
     * True if has "Claimant" element
     */
    boolean isSetClaimant();
    
    /**
     * Sets the "Claimant" element
     */
    void setClaimant(com.mitchell.schemas.apddelivery.PersonDetailType claimant);
    
    /**
     * Appends and returns a new empty "Claimant" element
     */
    com.mitchell.schemas.apddelivery.PersonDetailType addNewClaimant();
    
    /**
     * Unsets the "Claimant" element
     */
    void unsetClaimant();
    
    /**
     * Gets the "Estimate" element
     */
    java.lang.String getEstimate();
    
    /**
     * Gets (as xml) the "Estimate" element
     */
    org.apache.xmlbeans.XmlString xgetEstimate();
    
    /**
     * True if has "Estimate" element
     */
    boolean isSetEstimate();
    
    /**
     * Sets the "Estimate" element
     */
    void setEstimate(java.lang.String estimate);
    
    /**
     * Sets (as xml) the "Estimate" element
     */
    void xsetEstimate(org.apache.xmlbeans.XmlString estimate);
    
    /**
     * Unsets the "Estimate" element
     */
    void unsetEstimate();
    
    /**
     * Gets the "AssignmentType" element
     */
    java.lang.String getAssignmentType();
    
    /**
     * Gets (as xml) the "AssignmentType" element
     */
    org.apache.xmlbeans.XmlString xgetAssignmentType();
    
    /**
     * Sets the "AssignmentType" element
     */
    void setAssignmentType(java.lang.String assignmentType);
    
    /**
     * Sets (as xml) the "AssignmentType" element
     */
    void xsetAssignmentType(org.apache.xmlbeans.XmlString assignmentType);
    
    /**
     * Gets the "RequestType" element
     */
    java.lang.String getRequestType();
    
    /**
     * Gets (as xml) the "RequestType" element
     */
    org.apache.xmlbeans.XmlString xgetRequestType();
    
    /**
     * Sets the "RequestType" element
     */
    void setRequestType(java.lang.String requestType);
    
    /**
     * Sets (as xml) the "RequestType" element
     */
    void xsetRequestType(org.apache.xmlbeans.XmlString requestType);
    
    /**
     * Gets the "AssignedBy" element
     */
    java.lang.String getAssignedBy();
    
    /**
     * Gets (as xml) the "AssignedBy" element
     */
    org.apache.xmlbeans.XmlString xgetAssignedBy();
    
    /**
     * True if has "AssignedBy" element
     */
    boolean isSetAssignedBy();
    
    /**
     * Sets the "AssignedBy" element
     */
    void setAssignedBy(java.lang.String assignedBy);
    
    /**
     * Sets (as xml) the "AssignedBy" element
     */
    void xsetAssignedBy(org.apache.xmlbeans.XmlString assignedBy);
    
    /**
     * Unsets the "AssignedBy" element
     */
    void unsetAssignedBy();
    
    /**
     * Gets the "AssignedTo" element
     */
    java.lang.String getAssignedTo();
    
    /**
     * Gets (as xml) the "AssignedTo" element
     */
    org.apache.xmlbeans.XmlString xgetAssignedTo();
    
    /**
     * True if has "AssignedTo" element
     */
    boolean isSetAssignedTo();
    
    /**
     * Sets the "AssignedTo" element
     */
    void setAssignedTo(java.lang.String assignedTo);
    
    /**
     * Sets (as xml) the "AssignedTo" element
     */
    void xsetAssignedTo(org.apache.xmlbeans.XmlString assignedTo);
    
    /**
     * Unsets the "AssignedTo" element
     */
    void unsetAssignedTo();
    
    /**
     * Gets the "Notes" element
     */
    java.lang.String getNotes();
    
    /**
     * Gets (as xml) the "Notes" element
     */
    org.apache.xmlbeans.XmlString xgetNotes();
    
    /**
     * True if has "Notes" element
     */
    boolean isSetNotes();
    
    /**
     * Sets the "Notes" element
     */
    void setNotes(java.lang.String notes);
    
    /**
     * Sets (as xml) the "Notes" element
     */
    void xsetNotes(org.apache.xmlbeans.XmlString notes);
    
    /**
     * Unsets the "Notes" element
     */
    void unsetNotes();
    
    /**
     * An XML Vehicle(@http://www.mitchell.com/schemas/apddelivery).
     *
     * This is a complex type.
     */
    public interface Vehicle extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Vehicle.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sE50513EEB1AA0450D143D08D0542B1B9").resolveHandle("vehicle4e44elemtype");
        
        /**
         * Gets the "Year" element
         */
        java.lang.String getYear();
        
        /**
         * Gets (as xml) the "Year" element
         */
        org.apache.xmlbeans.XmlString xgetYear();
        
        /**
         * True if has "Year" element
         */
        boolean isSetYear();
        
        /**
         * Sets the "Year" element
         */
        void setYear(java.lang.String year);
        
        /**
         * Sets (as xml) the "Year" element
         */
        void xsetYear(org.apache.xmlbeans.XmlString year);
        
        /**
         * Unsets the "Year" element
         */
        void unsetYear();
        
        /**
         * Gets the "Make" element
         */
        java.lang.String getMake();
        
        /**
         * Gets (as xml) the "Make" element
         */
        org.apache.xmlbeans.XmlString xgetMake();
        
        /**
         * True if has "Make" element
         */
        boolean isSetMake();
        
        /**
         * Sets the "Make" element
         */
        void setMake(java.lang.String make);
        
        /**
         * Sets (as xml) the "Make" element
         */
        void xsetMake(org.apache.xmlbeans.XmlString make);
        
        /**
         * Unsets the "Make" element
         */
        void unsetMake();
        
        /**
         * Gets the "Model" element
         */
        java.lang.String getModel();
        
        /**
         * Gets (as xml) the "Model" element
         */
        org.apache.xmlbeans.XmlString xgetModel();
        
        /**
         * True if has "Model" element
         */
        boolean isSetModel();
        
        /**
         * Sets the "Model" element
         */
        void setModel(java.lang.String model);
        
        /**
         * Sets (as xml) the "Model" element
         */
        void xsetModel(org.apache.xmlbeans.XmlString model);
        
        /**
         * Unsets the "Model" element
         */
        void unsetModel();
        
        /**
         * Gets the "Submodel" element
         */
        java.lang.String getSubmodel();
        
        /**
         * Gets (as xml) the "Submodel" element
         */
        org.apache.xmlbeans.XmlString xgetSubmodel();
        
        /**
         * True if has "Submodel" element
         */
        boolean isSetSubmodel();
        
        /**
         * Sets the "Submodel" element
         */
        void setSubmodel(java.lang.String submodel);
        
        /**
         * Sets (as xml) the "Submodel" element
         */
        void xsetSubmodel(org.apache.xmlbeans.XmlString submodel);
        
        /**
         * Unsets the "Submodel" element
         */
        void unsetSubmodel();
        
        /**
         * Gets the "Mileage" element
         */
        java.lang.String getMileage();
        
        /**
         * Gets (as xml) the "Mileage" element
         */
        org.apache.xmlbeans.XmlString xgetMileage();
        
        /**
         * True if has "Mileage" element
         */
        boolean isSetMileage();
        
        /**
         * Sets the "Mileage" element
         */
        void setMileage(java.lang.String mileage);
        
        /**
         * Sets (as xml) the "Mileage" element
         */
        void xsetMileage(org.apache.xmlbeans.XmlString mileage);
        
        /**
         * Unsets the "Mileage" element
         */
        void unsetMileage();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle newInstance() {
              return (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.mitchell.schemas.apddelivery.RepairNotificationType newInstance() {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.mitchell.schemas.apddelivery.RepairNotificationType parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.mitchell.schemas.apddelivery.RepairNotificationType) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
