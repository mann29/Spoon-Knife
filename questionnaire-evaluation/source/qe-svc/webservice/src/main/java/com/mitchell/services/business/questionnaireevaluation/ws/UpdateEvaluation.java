
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
 *         &lt;element name="documentID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="evaluationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluationDetailsXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userInfo" type="{http://www.mitchell.com/common/types}UserInfoType" minOccurs="0"/>
 *         &lt;element name="claimId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="suffixId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="tcn" type="{http://www.w3.org/2001/XMLSchema}long"/>
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
    "documentID",
    "evaluationType",
    "evaluationDetailsXmlData",
    "userInfo",
    "claimId",
    "suffixId",
    "tcn"
})
@XmlRootElement(name = "updateEvaluation")
public class UpdateEvaluation {

    protected long documentID;
    protected String evaluationType;
    protected String evaluationDetailsXmlData;
    protected UserInfoType userInfo;
    protected long claimId;
    protected long suffixId;
    protected long tcn;

    /**
     * Gets the value of the documentID property.
     * 
     */
    public long getDocumentID() {
        return documentID;
    }

    /**
     * Sets the value of the documentID property.
     * 
     */
    public void setDocumentID(long value) {
        this.documentID = value;
    }

    /**
     * Gets the value of the evaluationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvaluationType() {
        return evaluationType;
    }

    /**
     * Sets the value of the evaluationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvaluationType(String value) {
        this.evaluationType = value;
    }

    /**
     * Gets the value of the evaluationDetailsXmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvaluationDetailsXmlData() {
        return evaluationDetailsXmlData;
    }

    /**
     * Sets the value of the evaluationDetailsXmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvaluationDetailsXmlData(String value) {
        this.evaluationDetailsXmlData = value;
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
     * Gets the value of the suffixId property.
     * 
     */
    public long getSuffixId() {
        return suffixId;
    }

    /**
     * Sets the value of the suffixId property.
     * 
     */
    public void setSuffixId(long value) {
        this.suffixId = value;
    }

    /**
     * Gets the value of the tcn property.
     * 
     */
    public long getTcn() {
        return tcn;
    }

    /**
     * Sets the value of the tcn property.
     * 
     */
    public void setTcn(long value) {
        this.tcn = value;
    }

}
