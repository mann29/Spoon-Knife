package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;

public interface MSTransfromEngineProxy {

	public XmlObject getTransFormData(MitchellEnvelopeDocument meDoc)
			throws MitchellException ;
}
