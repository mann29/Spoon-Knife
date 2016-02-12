/*
 * XML Type:  AssignmentScheduleType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML AssignmentScheduleType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class AssignmentScheduleTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.AssignmentScheduleType
{
    private static final long serialVersionUID = 1L;
    
    public AssignmentScheduleTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName URGENCY$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Urgency");
    private static final javax.xml.namespace.QName DURATION$2 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Duration");
    private static final javax.xml.namespace.QName ISTRAVELREQUIREDFLAG$4 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsTravelRequiredFlag");
    private static final javax.xml.namespace.QName ISCALLNEEDED$6 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "IsCallNeeded");
    private static final javax.xml.namespace.QName CONFIRMEDDATETIME$8 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ConfirmedDateTime");
    private static final javax.xml.namespace.QName PREFERREDSCHEDULEDATE$10 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PreferredScheduleDate");
    private static final javax.xml.namespace.QName PREFERREDSCHEDULESTARTTIME$12 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PreferredScheduleStartTime");
    private static final javax.xml.namespace.QName PREFERREDSCHEDULEENDTIME$14 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "PreferredScheduleEndTime");
    private static final javax.xml.namespace.QName SCHEDULEDDATETIME$16 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "ScheduledDateTime");
    
    
    /**
     * Gets the "Urgency" element
     */
    public java.math.BigInteger getUrgency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URGENCY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "Urgency" element
     */
    public org.apache.xmlbeans.XmlInteger xgetUrgency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(URGENCY$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "Urgency" element
     */
    public boolean isSetUrgency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(URGENCY$0) != 0;
        }
    }
    
    /**
     * Sets the "Urgency" element
     */
    public void setUrgency(java.math.BigInteger urgency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(URGENCY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(URGENCY$0);
            }
            target.setBigIntegerValue(urgency);
        }
    }
    
    /**
     * Sets (as xml) the "Urgency" element
     */
    public void xsetUrgency(org.apache.xmlbeans.XmlInteger urgency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(URGENCY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(URGENCY$0);
            }
            target.set(urgency);
        }
    }
    
    /**
     * Unsets the "Urgency" element
     */
    public void unsetUrgency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(URGENCY$0, 0);
        }
    }
    
    /**
     * Gets the "Duration" element
     */
    public java.math.BigInteger getDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATION$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "Duration" element
     */
    public org.apache.xmlbeans.XmlInteger xgetDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(DURATION$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "Duration" element
     */
    public boolean isSetDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DURATION$2) != 0;
        }
    }
    
    /**
     * Sets the "Duration" element
     */
    public void setDuration(java.math.BigInteger duration)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DURATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DURATION$2);
            }
            target.setBigIntegerValue(duration);
        }
    }
    
    /**
     * Sets (as xml) the "Duration" element
     */
    public void xsetDuration(org.apache.xmlbeans.XmlInteger duration)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInteger target = null;
            target = (org.apache.xmlbeans.XmlInteger)get_store().find_element_user(DURATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInteger)get_store().add_element_user(DURATION$2);
            }
            target.set(duration);
        }
    }
    
    /**
     * Unsets the "Duration" element
     */
    public void unsetDuration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DURATION$2, 0);
        }
    }
    
    /**
     * Gets the "IsTravelRequiredFlag" element
     */
    public boolean getIsTravelRequiredFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTRAVELREQUIREDFLAG$4, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "IsTravelRequiredFlag" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetIsTravelRequiredFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISTRAVELREQUIREDFLAG$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "IsTravelRequiredFlag" element
     */
    public boolean isSetIsTravelRequiredFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISTRAVELREQUIREDFLAG$4) != 0;
        }
    }
    
    /**
     * Sets the "IsTravelRequiredFlag" element
     */
    public void setIsTravelRequiredFlag(boolean isTravelRequiredFlag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISTRAVELREQUIREDFLAG$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISTRAVELREQUIREDFLAG$4);
            }
            target.setBooleanValue(isTravelRequiredFlag);
        }
    }
    
    /**
     * Sets (as xml) the "IsTravelRequiredFlag" element
     */
    public void xsetIsTravelRequiredFlag(org.apache.xmlbeans.XmlBoolean isTravelRequiredFlag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISTRAVELREQUIREDFLAG$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISTRAVELREQUIREDFLAG$4);
            }
            target.set(isTravelRequiredFlag);
        }
    }
    
    /**
     * Unsets the "IsTravelRequiredFlag" element
     */
    public void unsetIsTravelRequiredFlag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISTRAVELREQUIREDFLAG$4, 0);
        }
    }
    
    /**
     * Gets the "IsCallNeeded" element
     */
    public boolean getIsCallNeeded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCALLNEEDED$6, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "IsCallNeeded" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetIsCallNeeded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCALLNEEDED$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "IsCallNeeded" element
     */
    public boolean isSetIsCallNeeded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISCALLNEEDED$6) != 0;
        }
    }
    
    /**
     * Sets the "IsCallNeeded" element
     */
    public void setIsCallNeeded(boolean isCallNeeded)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISCALLNEEDED$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISCALLNEEDED$6);
            }
            target.setBooleanValue(isCallNeeded);
        }
    }
    
    /**
     * Sets (as xml) the "IsCallNeeded" element
     */
    public void xsetIsCallNeeded(org.apache.xmlbeans.XmlBoolean isCallNeeded)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ISCALLNEEDED$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ISCALLNEEDED$6);
            }
            target.set(isCallNeeded);
        }
    }
    
    /**
     * Unsets the "IsCallNeeded" element
     */
    public void unsetIsCallNeeded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISCALLNEEDED$6, 0);
        }
    }
    
    /**
     * Gets the "ConfirmedDateTime" element
     */
    public java.util.Calendar getConfirmedDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONFIRMEDDATETIME$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "ConfirmedDateTime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetConfirmedDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CONFIRMEDDATETIME$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "ConfirmedDateTime" element
     */
    public boolean isSetConfirmedDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONFIRMEDDATETIME$8) != 0;
        }
    }
    
    /**
     * Sets the "ConfirmedDateTime" element
     */
    public void setConfirmedDateTime(java.util.Calendar confirmedDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONFIRMEDDATETIME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONFIRMEDDATETIME$8);
            }
            target.setCalendarValue(confirmedDateTime);
        }
    }
    
    /**
     * Sets (as xml) the "ConfirmedDateTime" element
     */
    public void xsetConfirmedDateTime(org.apache.xmlbeans.XmlDateTime confirmedDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CONFIRMEDDATETIME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CONFIRMEDDATETIME$8);
            }
            target.set(confirmedDateTime);
        }
    }
    
    /**
     * Unsets the "ConfirmedDateTime" element
     */
    public void unsetConfirmedDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONFIRMEDDATETIME$8, 0);
        }
    }
    
    /**
     * Gets the "PreferredScheduleDate" element
     */
    public java.util.Calendar getPreferredScheduleDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULEDATE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "PreferredScheduleDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetPreferredScheduleDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(PREFERREDSCHEDULEDATE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "PreferredScheduleDate" element
     */
    public boolean isSetPreferredScheduleDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PREFERREDSCHEDULEDATE$10) != 0;
        }
    }
    
    /**
     * Sets the "PreferredScheduleDate" element
     */
    public void setPreferredScheduleDate(java.util.Calendar preferredScheduleDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULEDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREFERREDSCHEDULEDATE$10);
            }
            target.setCalendarValue(preferredScheduleDate);
        }
    }
    
    /**
     * Sets (as xml) the "PreferredScheduleDate" element
     */
    public void xsetPreferredScheduleDate(org.apache.xmlbeans.XmlDate preferredScheduleDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(PREFERREDSCHEDULEDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(PREFERREDSCHEDULEDATE$10);
            }
            target.set(preferredScheduleDate);
        }
    }
    
    /**
     * Unsets the "PreferredScheduleDate" element
     */
    public void unsetPreferredScheduleDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PREFERREDSCHEDULEDATE$10, 0);
        }
    }
    
    /**
     * Gets the "PreferredScheduleStartTime" element
     */
    public java.util.Calendar getPreferredScheduleStartTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULESTARTTIME$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "PreferredScheduleStartTime" element
     */
    public org.apache.xmlbeans.XmlTime xgetPreferredScheduleStartTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlTime target = null;
            target = (org.apache.xmlbeans.XmlTime)get_store().find_element_user(PREFERREDSCHEDULESTARTTIME$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "PreferredScheduleStartTime" element
     */
    public boolean isSetPreferredScheduleStartTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PREFERREDSCHEDULESTARTTIME$12) != 0;
        }
    }
    
    /**
     * Sets the "PreferredScheduleStartTime" element
     */
    public void setPreferredScheduleStartTime(java.util.Calendar preferredScheduleStartTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULESTARTTIME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREFERREDSCHEDULESTARTTIME$12);
            }
            target.setCalendarValue(preferredScheduleStartTime);
        }
    }
    
    /**
     * Sets (as xml) the "PreferredScheduleStartTime" element
     */
    public void xsetPreferredScheduleStartTime(org.apache.xmlbeans.XmlTime preferredScheduleStartTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlTime target = null;
            target = (org.apache.xmlbeans.XmlTime)get_store().find_element_user(PREFERREDSCHEDULESTARTTIME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlTime)get_store().add_element_user(PREFERREDSCHEDULESTARTTIME$12);
            }
            target.set(preferredScheduleStartTime);
        }
    }
    
    /**
     * Unsets the "PreferredScheduleStartTime" element
     */
    public void unsetPreferredScheduleStartTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PREFERREDSCHEDULESTARTTIME$12, 0);
        }
    }
    
    /**
     * Gets the "PreferredScheduleEndTime" element
     */
    public java.util.Calendar getPreferredScheduleEndTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULEENDTIME$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "PreferredScheduleEndTime" element
     */
    public org.apache.xmlbeans.XmlTime xgetPreferredScheduleEndTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlTime target = null;
            target = (org.apache.xmlbeans.XmlTime)get_store().find_element_user(PREFERREDSCHEDULEENDTIME$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "PreferredScheduleEndTime" element
     */
    public boolean isSetPreferredScheduleEndTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PREFERREDSCHEDULEENDTIME$14) != 0;
        }
    }
    
    /**
     * Sets the "PreferredScheduleEndTime" element
     */
    public void setPreferredScheduleEndTime(java.util.Calendar preferredScheduleEndTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PREFERREDSCHEDULEENDTIME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PREFERREDSCHEDULEENDTIME$14);
            }
            target.setCalendarValue(preferredScheduleEndTime);
        }
    }
    
    /**
     * Sets (as xml) the "PreferredScheduleEndTime" element
     */
    public void xsetPreferredScheduleEndTime(org.apache.xmlbeans.XmlTime preferredScheduleEndTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlTime target = null;
            target = (org.apache.xmlbeans.XmlTime)get_store().find_element_user(PREFERREDSCHEDULEENDTIME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlTime)get_store().add_element_user(PREFERREDSCHEDULEENDTIME$14);
            }
            target.set(preferredScheduleEndTime);
        }
    }
    
    /**
     * Unsets the "PreferredScheduleEndTime" element
     */
    public void unsetPreferredScheduleEndTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PREFERREDSCHEDULEENDTIME$14, 0);
        }
    }
    
    /**
     * Gets the "ScheduledDateTime" element
     */
    public java.util.Calendar getScheduledDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCHEDULEDDATETIME$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "ScheduledDateTime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetScheduledDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(SCHEDULEDDATETIME$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "ScheduledDateTime" element
     */
    public boolean isSetScheduledDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCHEDULEDDATETIME$16) != 0;
        }
    }
    
    /**
     * Sets the "ScheduledDateTime" element
     */
    public void setScheduledDateTime(java.util.Calendar scheduledDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SCHEDULEDDATETIME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SCHEDULEDDATETIME$16);
            }
            target.setCalendarValue(scheduledDateTime);
        }
    }
    
    /**
     * Sets (as xml) the "ScheduledDateTime" element
     */
    public void xsetScheduledDateTime(org.apache.xmlbeans.XmlDateTime scheduledDateTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(SCHEDULEDDATETIME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(SCHEDULEDDATETIME$16);
            }
            target.set(scheduledDateTime);
        }
    }
    
    /**
     * Unsets the "ScheduledDateTime" element
     */
    public void unsetScheduledDateTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCHEDULEDDATETIME$16, 0);
        }
    }
}
