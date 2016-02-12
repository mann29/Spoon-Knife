
package com.mitchell.services.business.questionnaireevaluation.ws;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claimQuestionnaireAssociation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userInfo" type="{http://www.mitchell.com/common/types}UserInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "claimQuestionnaireAssociation",
    "userInfo"
})
@XmlRootElement(name = "associateQuestionnaireToClaimSuffix")
public class AssociateQuestionnaireToClaimSuffix
    implements Serializable
{

    private final static long serialVersionUID = 20130128L;
    protected String claimQuestionnaireAssociation;
    protected UserInfoType userInfo;

    /**
     * Gets the value of the claimQuestionnaireAssociation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimQuestionnaireAssociation() {
        return claimQuestionnaireAssociation;
    }

    /**
     * Sets the value of the claimQuestionnaireAssociation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimQuestionnaireAssociation(String value) {
        this.claimQuestionnaireAssociation = value;
    }

    /**
     * Gets the value of the userInfo property.
     * 
     * @return
     *     possible object is
     *     {@link UserInfoType }
     *     
     */
    public UserInfoType getUserInfo() {
        return userInfo;
    }

    /**
     * Sets the value of the userInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfoType }
     *     
     */
    public void setUserInfo(UserInfoType value) {
        this.userInfo = value;
    }

}
