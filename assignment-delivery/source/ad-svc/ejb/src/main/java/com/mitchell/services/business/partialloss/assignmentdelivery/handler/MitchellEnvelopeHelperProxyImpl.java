package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.util.HashMap;

import org.apache.xmlbeans.XmlObject;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class MitchellEnvelopeHelperProxyImpl implements
		MitchellEnvelopeHelperProxy {
	private MitchellEnvelopeHelper delegate;
	public void setEnvelope(MitchellEnvelopeDocument envelope){
		delegate = new MitchellEnvelopeHelper(envelope);
	}
	private void guard(MitchellEnvelopeHelperProxyImpl my){
		if (my.delegate == null ){
			throw new IllegalStateException("The delegate is not set.");
		}
	}
	public void addEnvelopeContextNVPair(String contextNVPairName,
			String contextNVPairValue) {
		delegate.addEnvelopeContextNVPair(contextNVPairName, contextNVPairValue);
	}

	public void addEnvelopeContextNVPair(String contextNVPairName,
			String[] contextNVPairValueArray) {
		delegate.addEnvelopeContextNVPair(contextNVPairName,
				contextNVPairValueArray);
	}

	public EnvelopeBodyType addNewEnvelopeBody(String identifier,
			XmlObject contentXmlObj, String mitchellDocumentType) {
		return delegate.addNewEnvelopeBody(identifier, contentXmlObj,
				mitchellDocumentType);
	}

	public boolean deleteEnvelopeBodies() {
		return delegate.deleteEnvelopeBodies();
	}

	public boolean deleteEnvelopeBody() {
		return delegate.deleteEnvelopeBody();
	}

	public boolean deleteEnvelopeBody(String identifier) {
		return delegate.deleteEnvelopeBody(identifier);
	}

	public void deleteEnvelopeContextNVPair(String contextNVPairName) {
		delegate.deleteEnvelopeContextNVPair(contextNVPairName);
	}

	public void deleteEnvelopeContextNVPairs() {
		delegate.deleteEnvelopeContextNVPairs();
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	public MitchellEnvelopeDocument getDoc() {
		return delegate.getDoc();
	}

	public MitchellEnvelopeType getEnvelope() {
		return delegate.getEnvelope();
	}

	public HashMap getEnvelopeBodies() {
		return delegate.getEnvelopeBodies();
	}

	public EnvelopeBodyType getEnvelopeBody() {
		return delegate.getEnvelopeBody();
	}

	public EnvelopeBodyType getEnvelopeBody(String identifier) {
		return delegate.getEnvelopeBody(identifier);
	}

	public String getEnvelopeBodyContentAsString(EnvelopeBodyType envelopeBody)
			throws MitchellException {
		return delegate.getEnvelopeBodyContentAsString(envelopeBody);
	}

	public XmlObject getEnvelopeBodyContentAsXmlBean(
			EnvelopeBodyType envelopeBody, Class xmlBeanClass)
			throws MitchellException {
		return delegate.getEnvelopeBodyContentAsXmlBean(envelopeBody,
				xmlBeanClass);
	}

	public XmlObject getEnvelopeBodyContentAsXmlBean(
			EnvelopeBodyType envelopeBody) throws MitchellException {
		return delegate.getEnvelopeBodyContentAsXmlBean(envelopeBody);
	}

	public String getEnvelopeContextNVPairValue(String contextNVPairName) {
		return delegate.getEnvelopeContextNVPairValue(contextNVPairName);
	}

	public String[] getEnvelopeContextNVPairValues(String contextNVPairName) {
		return delegate.getEnvelopeContextNVPairValues(contextNVPairName);
	}

	public HashMap getEnvelopeContextNVPairs() {
		return delegate.getEnvelopeContextNVPairs();
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public String toString() {
		return delegate.toString();
	}

	public void updateEnvelopeBodyContent(EnvelopeBodyType envelopeBody,
			XmlObject contentXmlObj) {
		delegate.updateEnvelopeBodyContent(envelopeBody, contentXmlObj);
	}
}
