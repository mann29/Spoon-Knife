package com.mitchell.services.core.partialloss.apddelivery.ejb;

import java.util.ArrayList;
import javax.ejb.Remote;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

@Remote
public interface APDDeliveryServiceEJBRemote {
	
	public void deliverArtifact(APDDeliveryContextDocument apdCcontext) throws MitchellException;
	public void deliverAppraisalAssignment(APDDeliveryContextDocument apdContext, ArrayList attachments) throws MitchellException;
	
}
