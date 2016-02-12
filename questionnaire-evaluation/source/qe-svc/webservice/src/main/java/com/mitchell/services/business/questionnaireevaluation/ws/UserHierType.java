
package com.mitchell.services.business.questionnaireevaluation.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserHierType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserHierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HierNode" type="{http://www.mitchell.com/common/types}NodeType"/>
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
@XmlType(name = "UserHierType", namespace = "http://www.mitchell.com/common/types", propOrder = {
    "hierNode"
})
public class UserHierType {

    @XmlElement(name = "HierNode", namespace = "http://www.mitchell.com/common/types", required = true)
    protected NodeType hierNode;
    @XmlAttribute(name = "SchemaVersion")
    protected String schemaVersion;

    /**
     * Gets the value of the hierNode property.
     * 
     * @return
     *     possible object is
     *     {@link NodeType }
     *     
     */
    public NodeType getHierNode() {
        return hierNode;
    }

    /**
     * Sets the value of the hierNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeType }
     *     
     */
    public void setHierNode(NodeType value) {
        this.hierNode = value;
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
