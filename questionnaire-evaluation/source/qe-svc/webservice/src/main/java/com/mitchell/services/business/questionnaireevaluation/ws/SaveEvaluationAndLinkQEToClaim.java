
package com.mitchell.services.business.questionnaireevaluation.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mitchell.schemas.mitchellSuffixRqRs.MitchellSuffixSvcRqRsDocument;
import com.mitchell.schemas.mitchellSuffixRqRs.impl.MitchellSuffixSvcRqRsDocumentImpl;


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
 *        
 *          &lt;element name="evaluationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clientClaimNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluationType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="evaluationDetailsXmlData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;element name="workItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *        &lt;element name="mSuffixSvcRqRsDoc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "evaluationID",
    "clientClaimNumber",
    "evaluationType",
    "evaluationDetailsXmlData",
    "workItemId",
    "mSuffixSvcRqRsDoc",
    "userInfoType"
})
@XmlRootElement(name = "saveEvaluationAndLinkQEToClaim")
public class SaveEvaluationAndLinkQEToClaim {

    public String getClientClaimNumber() {
		return clientClaimNumber;
	}

	public void setClientClaimNumber(String clientClaimNumber) {
		this.clientClaimNumber = clientClaimNumber;
	}

	public String getWorkItemId() {
		return workItemId;
	}

	public void setWorkItemId(String workItemId) {
		this.workItemId = workItemId;
	}

	public String getMSuffixSvcRqRsDoc() {
		return mSuffixSvcRqRsDoc;
	}

	public void setMSuffixSvcRqRsDoc(String mSuffixSvcRqRsDoc) {
		this.mSuffixSvcRqRsDoc = mSuffixSvcRqRsDoc;
	}

	protected String evaluationID;
    protected String clientClaimNumber;
    protected String evaluationType;
    protected String evaluationDetailsXmlData;
    protected String workItemId;
    protected String mSuffixSvcRqRsDoc;
    protected UserInfoType userInfoType;

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
