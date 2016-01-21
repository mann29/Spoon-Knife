package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;

public abstract class AbstractDispatchReportBuilder {

	public AbstractDispatchReportBuilder() {
		super();
	}

	public abstract File createDispatchReport(AssignmentServiceContext context) throws Exception;

	public abstract File createDispatchReport(final UserInfoDocument userInfo, final String workItemId,
			final MitchellEnvelopeDocument mitchellEnvDoc) throws AssignmentDeliveryException,
			UnsupportedEncodingException, TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException;

}