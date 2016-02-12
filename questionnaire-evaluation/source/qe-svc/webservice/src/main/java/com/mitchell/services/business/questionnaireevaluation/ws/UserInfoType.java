
package com.mitchell.services.business.questionnaireevaluation.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrgID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrgCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Guid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserHier" type="{http://www.mitchell.com/common/types}UserHierType"/>
 *         &lt;sequence>
 *           &lt;element name="AppCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *         &lt;sequence minOccurs="0">
 *           &lt;element name="CrossOverInsuranceCompany" type="{http://www.mitchell.com/common/types}CrossOverInsuranceCompanyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;element name="StaffType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SchemaVersion" type="{http://www.mitchell.com/common/types}VersionNumberType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserInfoType", namespace = "http://www.mitchell.com/common/types", propOrder = {
    "orgID",
    "orgCode",
    "userID",
    "guid",
    "firstName",
    "lastName",
    "email",
    "userHier",
    "appCode",
    "crossOverInsuranceCompany",
    "staffType"
})
public class UserInfoType {

    @XmlElement(name = "OrgID", namespace = "http://www.mitchell.com/common/types", required = true)
    protected String orgID;
    @XmlElement(name = "OrgCode", namespace = "http://www.mitchell.com/common/types", required = true)
    protected String orgCode;
    @XmlElement(name = "UserID", namespace = "http://www.mitchell.com/common/types", required = true)
    protected String userID;
    @XmlElement(name = "Guid", namespace = "http://www.mitchell.com/common/types")
    protected String guid;
    @XmlElement(name = "FirstName", namespace = "http://www.mitchell.com/common/types")
    protected String firstName;
    @XmlElement(name = "LastName", namespace = "http://www.mitchell.com/common/types")
    protected String lastName;
    @XmlElement(name = "Email", namespace = "http://www.mitchell.com/common/types")
    protected String email;
    @XmlElement(name = "UserHier", namespace = "http://www.mitchell.com/common/types", required = true)
    protected UserHierType userHier;
    @XmlElement(name = "AppCode", namespace = "http://www.mitchell.com/common/types", required = true)
    protected List<String> appCode;
    @XmlElement(name = "CrossOverInsuranceCompany", namespace = "http://www.mitchell.com/common/types")
    protected List<CrossOverInsuranceCompanyType> crossOverInsuranceCompany;
    @XmlElement(name = "StaffType", namespace = "http://www.mitchell.com/common/types")
    protected String staffType;
    @XmlAttribute(name = "SchemaVersion")
    protected String schemaVersion;

    /**
     * Gets the value of the orgID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgID() {
        return orgID;
    }

    /**
     * Sets the value of the orgID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgID(String value) {
        this.orgID = value;
    }

    /**
     * Gets the value of the orgCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * Sets the value of the orgCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgCode(String value) {
        this.orgCode = value;
    }

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the userHier property.
     * 
     * @return
     *     possible object is
     *     {@link UserHierType }
     *     
     */
    public UserHierType getUserHier() {
        return userHier;
    }

    /**
     * Sets the value of the userHier property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserHierType }
     *     
     */
    public void setUserHier(UserHierType value) {
        this.userHier = value;
    }

    /**
     * Gets the value of the appCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAppCode() {
        if (appCode == null) {
            appCode = new ArrayList<String>();
        }
        return this.appCode;
    }

    /**
     * Gets the value of the crossOverInsuranceCompany property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the crossOverInsuranceCompany property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCrossOverInsuranceCompany().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CrossOverInsuranceCompanyType }
     * 
     * 
     */
    public List<CrossOverInsuranceCompanyType> getCrossOverInsuranceCompany() {
        if (crossOverInsuranceCompany == null) {
            crossOverInsuranceCompany = new ArrayList<CrossOverInsuranceCompanyType>();
        }
        return this.crossOverInsuranceCompany;
    }

    /**
     * Gets the value of the staffType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStaffType() {
        return staffType;
    }

    /**
     * Sets the value of the staffType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStaffType(String value) {
        this.staffType = value;
    }

    /**
     * Gets the value of the schemaVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchemaVersion() {
        return schemaVersion;
    }

    /**
     * Sets the value of the schemaVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchemaVersion(String value) {
        this.schemaVersion = value;
    }

}
