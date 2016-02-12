
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
 *         &lt;element name="linkQuestionnaireEvaluationToClaimResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "linkQuestionnaireEvaluationToClaimResult"
})
@XmlRootElement(name = "linkQuestionnaireEvaluationToClaimResponse")
public class LinkQuestionnaireEvaluationToClaimResponse {

    protected int linkQuestionnaireEvaluationToClaimResult;

    /**
     * Gets the value of the linkQuestionnaireEvaluationToClaimResult property.
     * 
     */
    public int getLinkQuestionnaireEvaluationToClaimResult() {
        return linkQuestionnaireEvaluationToClaimResult;
    }

    /**
     * Sets the value of the linkQuestionnaireEvaluationToClaimResult property.
     * 
     */
    public void setLinkQuestionnaireEvaluationToClaimResult(int value) {
        this.linkQuestionnaireEvaluationToClaimResult = value;
    }

}
