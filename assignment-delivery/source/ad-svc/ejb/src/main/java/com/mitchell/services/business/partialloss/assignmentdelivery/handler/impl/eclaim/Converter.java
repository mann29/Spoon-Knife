package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim;

import java.io.File;

import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
/**
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv Khanwale </a>
 * Created/Modified on Aug 18, 2010
 */
public interface Converter {

	public abstract File transformBmsAsgToMieAsg(
			AssignmentServiceContext assignmentSvcContext) throws Exception;

	public abstract File transformBmsAsgToMieAsg(
			APDDeliveryContextType context, MitchellEnvelopeDocument med, String ciecaId)
			throws Exception;

}