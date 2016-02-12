
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
 *         &lt;element name="evaluationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userInfoType" type="{http://www.mitchell.com/common/types}UserInfoType" minOccurs="0"/>
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
    "evaluationId",
    "userInfoType"
})
@XmlRootElement(name = "deleteEvaluation")
public class DeleteEvaluation {

    protected String evaluationId;
    protected UserInfoType userInfoType;

    /**
     * Gets the value of the evaluationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvaluationId() {
        return evaluationId;
    }

    /**
     * Sets the value of the evaluationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvaluationId(String value) {
        this.evaluationId = value;
    }

    /**
     * Gets the value of the userInfoType property.
     * 
     * @return
     *     possible object is
     *     {@link UserInfoType }
     *     
     */
    public UserInfoType getUserInfoType() {
        return userInfoType;
    }

    /**
     * Sets the value of the userInfoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfoType }
     *     
     */
    public void setUserInfoType(UserInfoType value) {
        this.userInfoType = value;
    }

}
