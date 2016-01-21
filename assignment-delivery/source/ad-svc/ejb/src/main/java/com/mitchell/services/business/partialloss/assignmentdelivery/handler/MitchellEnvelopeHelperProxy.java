package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.util.HashMap;

import org.apache.xmlbeans.XmlObject;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;

public interface MitchellEnvelopeHelperProxy {

	public abstract void updateEnvelopeBodyContent(EnvelopeBodyType envelopeBody, XmlObject contentXmlObj);

	public abstract HashMap getEnvelopeContextNVPairs();

	public abstract String[] getEnvelopeContextNVPairValues(String contextNVPairName);

	public abstract String getEnvelopeContextNVPairValue(String contextNVPairName);

	public abstract XmlObject getEnvelopeBodyContentAsXmlBean(EnvelopeBodyType envelopeBody)
			throws MitchellException;

	public abstract XmlObject getEnvelopeBodyContentAsXmlBean(EnvelopeBodyType envelopeBody,
			Class xmlBeanClass) throws MitchellException;

	public abstract String getEnvelopeBodyContentAsString(EnvelopeBodyType envelopeBody)
			throws MitchellException;

	public abstract EnvelopeBodyType getEnvelopeBody(String identifier);

	public abstract EnvelopeBodyType getEnvelopeBody();

	public abstract HashMap getEnvelopeBodies();

	public abstract MitchellEnvelopeType getEnvelope();

	public abstract MitchellEnvelopeDocument getDoc();

	public abstract void deleteEnvelopeContextNVPairs();

	public abstract void deleteEnvelopeContextNVPair(String contextNVPairName);

	public abstract boolean deleteEnvelopeBody(String identifier);

	public abstract boolean deleteEnvelopeBody();

	public abstract boolean deleteEnvelopeBodies();

	public abstract EnvelopeBodyType addNewEnvelopeBody(String identifier, XmlObject contentXmlObj,
			String mitchellDocumentType);

	public abstract void addEnvelopeContextNVPair(String contextNVPairName, String[] contextNVPairValueArray);

	public abstract void addEnvelopeContextNVPair(String contextNVPairName, String contextNVPairValue);
//	public MitchellEnve

	public abstract void setEnvelope(MitchellEnvelopeDocument envelope);
}
