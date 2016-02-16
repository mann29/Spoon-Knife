/*
 * An XML document type.
 * Localname: RepairNotification
 * Namespace: http://www.mitchell.com/schemas/apddelivery
 * Java type: com.mitchell.schemas.apddelivery.RepairNotificationDocument
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.apddelivery.impl;
/**
 * A document containing one RepairNotification(@http://www.mitchell.com/schemas/apddelivery) element.
 *
 * This is a complex type.
 */
public class RepairNotificationDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.apddelivery.RepairNotificationDocument
{
    private static final long serialVersionUID = 1L;
    
    public RepairNotificationDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REPAIRNOTIFICATION$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/apddelivery", "RepairNotification");
    
    
    /**
     * Gets the "RepairNotification" element
     */
    public com.mitchell.schemas.apddelivery.RepairNotificationType getRepairNotification()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType)get_store().find_element_user(REPAIRNOTIFICATION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RepairNotification" element
     */
    public void setRepairNotification(com.mitchell.schemas.apddelivery.RepairNotificationType repairNotification)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType)get_store().find_element_user(REPAIRNOTIFICATION$0, 0);
            if (target == null)
            {
                target = (com.mitchell.schemas.apddelivery.RepairNotificationType)get_store().add_element_user(REPAIRNOTIFICATION$0);
            }
            target.set(repairNotification);
        }
    }
    
    /**
     * Appends and returns a new empty "RepairNotification" element
     */
    public com.mitchell.schemas.apddelivery.RepairNotificationType addNewRepairNotification()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.apddelivery.RepairNotificationType target = null;
            target = (com.mitchell.schemas.apddelivery.RepairNotificationType)get_store().add_element_user(REPAIRNOTIFICATION$0);
            return target;
        }
    }
}
