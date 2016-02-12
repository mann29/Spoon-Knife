
package com.mitchell.services.business.questionnaireevaluation.ws;

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
 *         &lt;element name="evaluationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claimId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="exposureId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="claimNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "evaluationID",
    "claimId",
    "exposureId",
    "claimNumber",
    "userInfo"
})
@XmlRootElement(name = "linkQuestionnaireEvaluationToClaim")
public class LinkQuestionnaireEvaluationToClaim {

    protected String evaluationID;
    protected long claimId;
    protected long exposureId;
    protected String claimNumber;
    protected UserInfoType userInfo;

    /**
     * Gets the value of the evaluationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvaluationID() {
        return evaluationID;
    }

    /**
     * Sets the value of the evaluationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvaluationID(String value) {
        this.evaluationID = value;
    }

    /**
     * Gets the value of the claimId property.
     * 
     */
    public long getClaimId() {
        return claimId;
    }

    /**
     * Sets the value of the claimId property.
     * 
     */
    public void setClaimId(long value) {
        this.claimId = value;
    }

    /**
     * Gets the value of the exposureId property.
     * 
     */
    public long getExposureId() {
        return exposureId;
    }

    /**
     * Sets the value of the exposureId property.
     * 
     */
    public void setExposureId(long value) {
        this.exposureId = value;
    }

    /**
     * Gets the value of the claimNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaimNumber() {
        return claimNumber;
    }

    /**
     * Sets the value of the claimNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaimNumber(String value) {
        this.claimNumber = value;
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
