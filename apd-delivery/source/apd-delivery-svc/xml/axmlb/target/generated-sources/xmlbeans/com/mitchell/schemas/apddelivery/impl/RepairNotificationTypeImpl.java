/*
 * XML Type:  RepairNotificationType
 * Namespace: http://www.mitchell.com/schemas/apddelivery
 * Java type: com.mitchell.schemas.apddelivery.RepairNotificationType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.apddelivery.impl;
/**
 * An XML RepairNotificationType(@http://www.mitchell.com/schemas/apddelivery).
 *
 * This is a complex type.
 */
public class RepairNotificationTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.apddelivery.RepairNotificationType
{
    private static final long serialVersionUID = 1L;
    
    public RepairNotificationTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CLAIMNUMBER$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "ClaimNumber");
    private static final javax.xml.namespace.QName VEHICLE$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Vehicle");
    private static final javax.xml.namespace.QName OWNER$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Owner");
    private static final javax.xml.namespace.QName INSURED$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Insured");
    private static final javax.xml.namespace.QName CLAIMANT$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Claimant");
    private static final javax.xml.namespace.QName ESTIMATE$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Estimate");
    private static final javax.xml.namespace.QName ASSIGNMENTTYPE$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "AssignmentType");
    private static final javax.xml.namespace.QName REQUESTTYPE$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "RequestType");
    private static final javax.xml.namespace.QName ASSIGNEDBY$16 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "AssignedBy");
    private static final javax.xml.namespace.QName ASSIGNEDTO$18 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "AssignedTo");
    private static final javax.xml.namespace.QName NOTES$20 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Notes");
    
    
    /**
     * Gets the "ClaimNumber" element
     */
    public java.lang.String getClaimNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ClaimNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMNUMBER$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ClaimNumber" element
     */
    public void setClaimNumber(java.lang.String claimNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMNUMBER$0);
            }
            target.setStringValue(claimNumber);
        }
    }
    
    /**
     * Sets (as xml) the "ClaimNumber" element
     */
    public void xsetClaimNumber(org.apache.xmlbeans.XmlString claimNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMNUMBER$0);
            }
            target.set(claimNumber);
        }
    }
    
    /**
     * Gets the "Vehicle" element
     */
    public com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle getVehicle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle)get_store().find_element_user(VEHICLE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Vehicle" element
     */
    public boolean isSetVehicle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VEHICLE$2) != 0;
        }
    }
    
    /**
     * Sets the "Vehicle" element
     */
    public void setVehicle(com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle vehicle)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle)get_store().find_element_user(VEHICLE$2, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle)get_store().add_element_user(VEHICLE$2);
            }
            target.set(vehicle);
        }
    }
    
    /**
     * Appends and returns a new empty "Vehicle" element
     */
    public com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle addNewVehicle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle)get_store().add_element_user(VEHICLE$2);
            return target;
        }
    }
    
    /**
     * Unsets the "Vehicle" element
     */
    public void unsetVehicle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VEHICLE$2, 0);
        }
    }
    
    /**
     * Gets the "Owner" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType getOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(OWNER$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Owner" element
     */
    public boolean isSetOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OWNER$4) != 0;
        }
    }
    
    /**
     * Sets the "Owner" element
     */
    public void setOwner(com.mitchell.schemas.apddelivery.PersonDetailType owner)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(OWNER$4, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(OWNER$4);
            }
            target.set(owner);
        }
    }
    
    /**
     * Appends and returns a new empty "Owner" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType addNewOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(OWNER$4);
            return target;
        }
    }
    
    /**
     * Unsets the "Owner" element
     */
    public void unsetOwner()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OWNER$4, 0);
        }
    }
    
    /**
     * Gets the "Insured" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType getInsured()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(INSURED$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Insured" element
     */
    public boolean isSetInsured()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INSURED$6) != 0;
        }
    }
    
    /**
     * Sets the "Insured" element
     */
    public void setInsured(com.mitchell.schemas.apddelivery.PersonDetailType insured)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(INSURED$6, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(INSURED$6);
            }
            target.set(insured);
        }
    }
    
    /**
     * Appends and returns a new empty "Insured" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType addNewInsured()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(INSURED$6);
            return target;
        }
    }
    
    /**
     * Unsets the "Insured" element
     */
    public void unsetInsured()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INSURED$6, 0);
        }
    }
    
    /**
     * Gets the "Claimant" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType getClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(CLAIMANT$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "Claimant" element
     */
    public boolean isSetClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMANT$8) != 0;
        }
    }
    
    /**
     * Sets the "Claimant" element
     */
    public void setClaimant(com.mitchell.schemas.apddelivery.PersonDetailType claimant)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().find_element_user(CLAIMANT$8, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(CLAIMANT$8);
            }
            target.set(claimant);
        }
    }
    
    /**
     * Appends and returns a new empty "Claimant" element
     */
    public com.mitchell.schemas.apddelivery.PersonDetailType addNewClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.PersonDetailType target = null;
            target = (com.mitchell.schemas.apddelivery.PersonDetailType)get_store().add_element_user(CLAIMANT$8);
            return target;
        }
    }
    
    /**
     * Unsets the "Claimant" element
     */
    public void unsetClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMANT$8, 0);
        }
    }
    
    /**
     * Gets the "Estimate" element
     */
    public java.lang.String getEstimate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESTIMATE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Estimate" element
     */
    public org.apache.xmlbeans.XmlString xgetEstimate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ESTIMATE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "Estimate" element
     */
    public boolean isSetEstimate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ESTIMATE$10) != 0;
        }
    }
    
    /**
     * Sets the "Estimate" element
     */
    public void setEstimate(java.lang.String estimate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ESTIMATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ESTIMATE$10);
            }
            target.setStringValue(estimate);
        }
    }
    
    /**
     * Sets (as xml) the "Estimate" element
     */
    public void xsetEstimate(org.apache.xmlbeans.XmlString estimate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ESTIMATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ESTIMATE$10);
            }
            target.set(estimate);
        }
    }
    
    /**
     * Unsets the "Estimate" element
     */
    public void unsetEstimate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ESTIMATE$10, 0);
        }
    }
    
    /**
     * Gets the "AssignmentType" element
     */
    public java.lang.String getAssignmentType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNMENTTYPE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "AssignmentType" element
     */
    public org.apache.xmlbeans.XmlString xgetAssignmentType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNMENTTYPE$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "AssignmentType" element
     */
    public void setAssignmentType(java.lang.String assignmentType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNMENTTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ASSIGNMENTTYPE$12);
            }
            target.setStringValue(assignmentType);
        }
    }
    
    /**
     * Sets (as xml) the "AssignmentType" element
     */
    public void xsetAssignmentType(org.apache.xmlbeans.XmlString assignmentType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNMENTTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ASSIGNMENTTYPE$12);
            }
            target.set(assignmentType);
        }
    }
    
    /**
     * Gets the "RequestType" element
     */
    public java.lang.String getRequestType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUESTTYPE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "RequestType" element
     */
    public org.apache.xmlbeans.XmlString xgetRequestType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQUESTTYPE$14, 0);
            return target;
        }
    }
    
    /**
     * Sets the "RequestType" element
     */
    public void setRequestType(java.lang.String requestType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUESTTYPE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REQUESTTYPE$14);
            }
            target.setStringValue(requestType);
        }
    }
    
    /**
     * Sets (as xml) the "RequestType" element
     */
    public void xsetRequestType(org.apache.xmlbeans.XmlString requestType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REQUESTTYPE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REQUESTTYPE$14);
            }
            target.set(requestType);
        }
    }
    
    /**
     * Gets the "AssignedBy" element
     */
    public java.lang.String getAssignedBy()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNEDBY$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "AssignedBy" element
     */
    public org.apache.xmlbeans.XmlString xgetAssignedBy()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNEDBY$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "AssignedBy" element
     */
    public boolean isSetAssignedBy()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ASSIGNEDBY$16) != 0;
        }
    }
    
    /**
     * Sets the "AssignedBy" element
     */
    public void setAssignedBy(java.lang.String assignedBy)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNEDBY$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ASSIGNEDBY$16);
            }
            target.setStringValue(assignedBy);
        }
    }
    
    /**
     * Sets (as xml) the "AssignedBy" element
     */
    public void xsetAssignedBy(org.apache.xmlbeans.XmlString assignedBy)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNEDBY$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ASSIGNEDBY$16);
            }
            target.set(assignedBy);
        }
    }
    
    /**
     * Unsets the "AssignedBy" element
     */
    public void unsetAssignedBy()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ASSIGNEDBY$16, 0);
        }
    }
    
    /**
     * Gets the "AssignedTo" element
     */
    public java.lang.String getAssignedTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNEDTO$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "AssignedTo" element
     */
    public org.apache.xmlbeans.XmlString xgetAssignedTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNEDTO$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "AssignedTo" element
     */
    public boolean isSetAssignedTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ASSIGNEDTO$18) != 0;
        }
    }
    
    /**
     * Sets the "AssignedTo" element
     */
    public void setAssignedTo(java.lang.String assignedTo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSIGNEDTO$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ASSIGNEDTO$18);
            }
            target.setStringValue(assignedTo);
        }
    }
    
    /**
     * Sets (as xml) the "AssignedTo" element
     */
    public void xsetAssignedTo(org.apache.xmlbeans.XmlString assignedTo)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSIGNEDTO$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ASSIGNEDTO$18);
            }
            target.set(assignedTo);
        }
    }
    
    /**
     * Unsets the "AssignedTo" element
     */
    public void unsetAssignedTo()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ASSIGNEDTO$18, 0);
        }
    }
    
    /**
     * Gets the "Notes" element
     */
    public java.lang.String getNotes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOTES$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Notes" element
     */
    public org.apache.xmlbeans.XmlString xgetNotes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOTES$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "Notes" element
     */
    public boolean isSetNotes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NOTES$20) != 0;
        }
    }
    
    /**
     * Sets the "Notes" element
     */
    public void setNotes(java.lang.String notes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NOTES$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NOTES$20);
            }
            target.setStringValue(notes);
        }
    }
    
    /**
     * Sets (as xml) the "Notes" element
     */
    public void xsetNotes(org.apache.xmlbeans.XmlString notes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NOTES$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NOTES$20);
            }
            target.set(notes);
        }
    }
    
    /**
     * Unsets the "Notes" element
     */
    public void unsetNotes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NOTES$20, 0);
        }
    }
    /**
     * An XML Vehicle(@http://www.mitchell.com/schemas/apddelivery).
     *
     * This is a complex type.
     */
    public static class VehicleImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.apddelivery.RepairNotificationType.Vehicle
    {
        private static final long serialVersionUID = 1L;
        
        public VehicleImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName YEAR$0 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Year");
        private static final javax.xml.namespace.QName MAKE$2 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Make");
        private static final javax.xml.namespace.QName MODEL$4 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Model");
        private static final javax.xml.namespace.QName SUBMODEL$6 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Submodel");
        private static final javax.xml.namespace.QName MILEAGE$8 = 
            new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "Mileage");
        
        
        /**
         * Gets the "Year" element
         */
        public java.lang.String getYear()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YEAR$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Year" element
         */
        public org.apache.xmlbeans.XmlString xgetYear()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YEAR$0, 0);
                return target;
            }
        }
        
        /**
         * True if has "Year" element
         */
        public boolean isSetYear()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(YEAR$0) != 0;
            }
        }
        
        /**
         * Sets the "Year" element
         */
        public void setYear(java.lang.String year)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YEAR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(YEAR$0);
                }
                target.setStringValue(year);
            }
        }
        
        /**
         * Sets (as xml) the "Year" element
         */
        public void xsetYear(org.apache.xmlbeans.XmlString year)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YEAR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(YEAR$0);
                }
                target.set(year);
            }
        }
        
        /**
         * Unsets the "Year" element
         */
        public void unsetYear()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(YEAR$0, 0);
            }
        }
        
        /**
         * Gets the "Make" element
         */
        public java.lang.String getMake()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAKE$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Make" element
         */
        public org.apache.xmlbeans.XmlString xgetMake()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MAKE$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "Make" element
         */
        public boolean isSetMake()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(MAKE$2) != 0;
            }
        }
        
        /**
         * Sets the "Make" element
         */
        public void setMake(java.lang.String make)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAKE$2);
                }
                target.setStringValue(make);
            }
        }
        
        /**
         * Sets (as xml) the "Make" element
         */
        public void xsetMake(org.apache.xmlbeans.XmlString make)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MAKE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MAKE$2);
                }
                target.set(make);
            }
        }
        
        /**
         * Unsets the "Make" element
         */
        public void unsetMake()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(MAKE$2, 0);
            }
        }
        
        /**
         * Gets the "Model" element
         */
        public java.lang.String getModel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODEL$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Model" element
         */
        public org.apache.xmlbeans.XmlString xgetModel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODEL$4, 0);
                return target;
            }
        }
        
        /**
         * True if has "Model" element
         */
        public boolean isSetModel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(MODEL$4) != 0;
            }
        }
        
        /**
         * Sets the "Model" element
         */
        public void setModel(java.lang.String model)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MODEL$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MODEL$4);
                }
                target.setStringValue(model);
            }
        }
        
        /**
         * Sets (as xml) the "Model" element
         */
        public void xsetModel(org.apache.xmlbeans.XmlString model)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MODEL$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MODEL$4);
                }
                target.set(model);
            }
        }
        
        /**
         * Unsets the "Model" element
         */
        public void unsetModel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(MODEL$4, 0);
            }
        }
        
        /**
         * Gets the "Submodel" element
         */
        public java.lang.String getSubmodel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBMODEL$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Submodel" element
         */
        public org.apache.xmlbeans.XmlString xgetSubmodel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUBMODEL$6, 0);
                return target;
            }
        }
        
        /**
         * True if has "Submodel" element
         */
        public boolean isSetSubmodel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SUBMODEL$6) != 0;
            }
        }
        
        /**
         * Sets the "Submodel" element
         */
        public void setSubmodel(java.lang.String submodel)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUBMODEL$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUBMODEL$6);
                }
                target.setStringValue(submodel);
            }
        }
        
        /**
         * Sets (as xml) the "Submodel" element
         */
        public void xsetSubmodel(org.apache.xmlbeans.XmlString submodel)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUBMODEL$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUBMODEL$6);
                }
                target.set(submodel);
            }
        }
        
        /**
         * Unsets the "Submodel" element
         */
        public void unsetSubmodel()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SUBMODEL$6, 0);
            }
        }
        
        /**
         * Gets the "Mileage" element
         */
        public java.lang.String getMileage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MILEAGE$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Mileage" element
         */
        public org.apache.xmlbeans.XmlString xgetMileage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MILEAGE$8, 0);
                return target;
            }
        }
        
        /**
         * True if has "Mileage" element
         */
        public boolean isSetMileage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(MILEAGE$8) != 0;
            }
        }
        
        /**
         * Sets the "Mileage" element
         */
        public void setMileage(java.lang.String mileage)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MILEAGE$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MILEAGE$8);
                }
                target.setStringValue(mileage);
            }
        }
        
        /**
         * Sets (as xml) the "Mileage" element
         */
        public void xsetMileage(org.apache.xmlbeans.XmlString mileage)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MILEAGE$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MILEAGE$8);
                }
                target.set(mileage);
            }
        }
        
        /**
         * Unsets the "Mileage" element
         */
        public void unsetMileage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(MILEAGE$8, 0);
            }
        }
    }
}
