/*
 * XML Type:  SkillsListType
 * Namespace: http://www.mitchell.com/schemas/MitchellSuffixRqRs
 * Java type: com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType
 *
 * Automatically generated - do not modify.
 */
package com.mitchell.schemas.mitchellSuffixRqRs.impl;
/**
 * An XML SkillsListType(@http://www.mitchell.com/schemas/MitchellSuffixRqRs).
 *
 * This is a complex type.
 */
public class SkillsListTypeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.mitchell.schemas.mitchellSuffixRqRs.SkillsListType
{
    private static final long serialVersionUID = 1L;
    
    public SkillsListTypeImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SKILL$0 = 
        new javax.xml.namespace.QName("http://www.mitchell.com/schemas/MitchellSuffixRqRs", "Skill");
    
    
    /**
     * Gets array of all "Skill" elements
     */
    public java.lang.String[] getSkillArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SKILL$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "Skill" element
     */
    public java.lang.String getSkillArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SKILL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "Skill" elements
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SkillType[] xgetSkillArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SKILL$0, targetList);
            com.mitchell.schemas.mitchellSuffixRqRs.SkillType[] result = new com.mitchell.schemas.mitchellSuffixRqRs.SkillType[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "Skill" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SkillType xgetSkillArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SkillType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillType)get_store().find_element_user(SKILL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (com.mitchell.schemas.mitchellSuffixRqRs.SkillType)target;
        }
    }
    
    /**
     * Returns number of "Skill" element
     */
    public int sizeOfSkillArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SKILL$0);
        }
    }
    
    /**
     * Sets array of all "Skill" element
     */
    public void setSkillArray(java.lang.String[] skillArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(skillArray, SKILL$0);
        }
    }
    
    /**
     * Sets ith "Skill" element
     */
    public void setSkillArray(int i, java.lang.String skill)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SKILL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(skill);
        }
    }
    
    /**
     * Sets (as xml) array of all "Skill" element
     */
    public void xsetSkillArray(com.mitchell.schemas.mitchellSuffixRqRs.SkillType[]skillArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(skillArray, SKILL$0);
        }
    }
    
    /**
     * Sets (as xml) ith "Skill" element
     */
    public void xsetSkillArray(int i, com.mitchell.schemas.mitchellSuffixRqRs.SkillType skill)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SkillType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillType)get_store().find_element_user(SKILL$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(skill);
        }
    }
    
    /**
     * Inserts the value as the ith "Skill" element
     */
    public void insertSkill(int i, java.lang.String skill)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(SKILL$0, i);
            target.setStringValue(skill);
        }
    }
    
    /**
     * Appends the value as the last "Skill" element
     */
    public void addSkill(java.lang.String skill)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SKILL$0);
            target.setStringValue(skill);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Skill" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SkillType insertNewSkill(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SkillType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillType)get_store().insert_element_user(SKILL$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Skill" element
     */
    public com.mitchell.schemas.mitchellSuffixRqRs.SkillType addNewSkill()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.mitchell.schemas.mitchellSuffixRqRs.SkillType target = null;
            target = (com.mitchell.schemas.mitchellSuffixRqRs.SkillType)get_store().add_element_user(SKILL$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "Skill" element
     */
    public void removeSkill(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SKILL$0, i);
        }
    }
}
