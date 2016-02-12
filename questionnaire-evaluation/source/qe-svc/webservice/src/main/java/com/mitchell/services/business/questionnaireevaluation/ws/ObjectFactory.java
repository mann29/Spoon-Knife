
package com.mitchell.services.business.questionnaireevaluation.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mitchell.services.business.questionnaireevaluation.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserInfo_QNAME = new QName("http://www.mitchell.com/common/types", "UserInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mitchell.services.business.questionnaireevaluation.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NodeType }
     * 
     */
    public NodeType createNodeType() {
        return new NodeType();
    }

    /**
     * Create an instance of {@link UpdateEvaluation }
     * 
     */
    public UpdateEvaluation createUpdateEvaluation() {
        return new UpdateEvaluation();
    }

    /**
     * Create an instance of {@link LinkQuestionnaireEvaluationToClaimResponse }
     * 
     */
    public LinkQuestionnaireEvaluationToClaimResponse createLinkQuestionnaireEvaluationToClaimResponse() {
        return new LinkQuestionnaireEvaluationToClaimResponse();
    }

    /**
     * Create an instance of {@link SaveEvaluationResponse }
     * 
     */
    public SaveEvaluationResponse createSaveEvaluationResponse() {
        return new SaveEvaluationResponse();
    }

    /**
     * Create an instance of {@link DeleteEvaluationResponse }
     * 
     */
    public DeleteEvaluationResponse createDeleteEvaluationResponse() {
        return new DeleteEvaluationResponse();
    }

    /**
     * Create an instance of {@link LinkQuestionnaireEvaluationToClaim }
     * 
     */
    public LinkQuestionnaireEvaluationToClaim createLinkQuestionnaireEvaluationToClaim() {
        return new LinkQuestionnaireEvaluationToClaim();
    }

    /**
     * Create an instance of {@link SaveEvaluation }
     * 
     */
    public SaveEvaluation createSaveEvaluation() {
        return new SaveEvaluation();
    }

    /**
     * Create an instance of {@link CrossOverInsuranceCompanyType }
     * 
     */
    public CrossOverInsuranceCompanyType createCrossOverInsuranceCompanyType() {
        return new CrossOverInsuranceCompanyType();
    }

    /**
     * Create an instance of {@link UserHierType }
     * 
     */
    public UserHierType createUserHierType() {
        return new UserHierType();
    }

    /**
     * Create an instance of {@link UpdateEvaluationResponse }
     * 
     */
    public UpdateEvaluationResponse createUpdateEvaluationResponse() {
        return new UpdateEvaluationResponse();
    }

    /**
     * Create an instance of {@link SaveEvaluationWithClaimResponse }
     * 
     */
    public SaveEvaluationWithClaimResponse createSaveEvaluationWithClaimResponse() {
        return new SaveEvaluationWithClaimResponse();
    }

    /**
     * Create an instance of {@link SaveEvaluationWithClaim }
     * 
     */
    public SaveEvaluationWithClaim createSaveEvaluationWithClaim() {
        return new SaveEvaluationWithClaim();
    }

    /**
     * Create an instance of {@link UserInfoType }
     * 
     */
    public UserInfoType createUserInfoType() {
        return new UserInfoType();
    }

    /**
     * Create an instance of {@link DeleteEvaluation }
     * 
     */
    public SaveEvaluationAndLinkQEToClaim createSaveEvaluationAndLinkQEToClaim() {
        return new SaveEvaluationAndLinkQEToClaim();
    }

    
    public SaveEvaluationAndLinkQEToClaimResponse createSaveEvaluationAndLinkQEToClaimResponse() {
        return new SaveEvaluationAndLinkQEToClaimResponse();
    }
    public DeleteEvaluation createDeleteEvaluation() {
        return new DeleteEvaluation();
    }
    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.mitchell.com/common/types", name = "UserInfo")
    public JAXBElement<UserInfoType> createUserInfo(UserInfoType value) {
        return new JAXBElement<UserInfoType>(_UserInfo_QNAME, UserInfoType.class, null, value);
    }

}
