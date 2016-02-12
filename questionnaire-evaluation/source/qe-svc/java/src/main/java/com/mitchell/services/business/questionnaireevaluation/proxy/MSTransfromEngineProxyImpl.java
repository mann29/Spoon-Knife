package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.suffix.transformation.client.MSSEATransEngineClient;
import com.mitchell.suffix.transformation.engine.XslTransformEngineRemote;
import com.mitchell.utils.misc.AppUtilities;

public class MSTransfromEngineProxyImpl implements MSTransfromEngineProxy{


	private static final String CLASS_NAME = MSTransfromEngineProxyImpl.class.getName();
	private Logger logger = Logger.getLogger(CLASS_NAME);

	
	public XmlObject getTransFormData(MitchellEnvelopeDocument meDoc)
			throws MitchellException {
		
		
		XslTransformEngineRemote xslTransformEngineRemote= null;
        XmlObject transformedData=null; 
		try {	
			xslTransformEngineRemote= MSSEATransEngineClient.getXslTransformEngineRemote();
			transformedData=xslTransformEngineRemote.transformDocument(meDoc);
			
		} catch (MitchellException me) {
			final String desc = "Exception from MSTransfromEngineProxy:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			MitchellException mitchellException = new MitchellException(
					QuestionnaireEvaluationConstants.QE_CALL_TRANS_ENGINE_SERVICE_ERROR,
					CLASS_NAME, "getTransFormData", desc, me);
			throw mitchellException;
		}
	
			catch (Exception me) {
			final String desc = "Exception from MSTransfromEngineProxy:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));	
			MitchellException mitchellException = new MitchellException(
					QuestionnaireEvaluationConstants.QE_CALL_SERVICE_UNKNOWN_ERROR,
					CLASS_NAME, "getTransFormData", desc, me);
			throw mitchellException;
		}
		return transformedData;
	
	}	
}
