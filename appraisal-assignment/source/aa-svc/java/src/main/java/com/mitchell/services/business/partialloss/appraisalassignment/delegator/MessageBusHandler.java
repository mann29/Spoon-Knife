package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import javax.jms.JMSException;
import org.apache.xmlbeans.XmlException;


import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.MessageBusProxy;

public interface MessageBusHandler {
	 MessageBusProxy getMessageBusProxy();

	 void setMessageBusProxy(MessageBusProxy messageBusProxy);

	 void publishEventToMessageBus(final int subscribedEvent,
			final WorkAssignmentDocument workAssignmentDocument,
			final UserInfoDocument userInfoDocument) throws MitchellException, XmlException, JMSException;
	/**
	 * This method is overloaded method to add name value pair to
	 * mwm doc if additional appraisal assignment info doc is present.
	 * @param subscribedEvent
	 * @param workAssignmentDocument
	 * @param userInfoDocument
	 * @param additionalAprAsgnmentInfoDoc
	 * @throws MitchellException
	 * @throws XmlException
	 * @throws JMSException
	 */		
	 void publishEventToMessageBus(final int subscribedEvent,
			final WorkAssignmentDocument workAssignmentDocument,
			final UserInfoDocument userInfoDocument, AdditionalAppraisalAssignmentInfoDocument additionalAprAsgnmentInfoDoc) throws MitchellException, XmlException, JMSException;		
	
}
