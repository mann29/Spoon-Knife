package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryFacade;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.client.AssignmentDeliveryClient;

public class ADSProxyImpl implements ADSProxy {

	/**
	 * class name.
	 */
	private static final String CLASS_NAME = ADSProxyImpl.class.getName();

	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverAssignmentEmail
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverAssignmentEmail(final APDDeliveryContextDocument context)
			throws MitchellException {
		String methodName = "deliverAssignmentEmail";
		logger.entering(CLASS_NAME, methodName);

		AssignmentDeliveryClient client = new AssignmentDeliveryClient();

		client.deliverAssignmentEmail(context);
		logger.exiting(CLASS_NAME, methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverUploadSuccessEmail
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverUploadSuccessEmail(APDDeliveryContextDocument context)
			throws MitchellException {
		String methodName = "deliverUploadSuccessEmail";
		logger.entering(CLASS_NAME, methodName);

		AssignmentDeliveryClient client = new AssignmentDeliveryClient();
		client.deliverEstimateUploadSuccessEmail(context);
		logger.exiting(CLASS_NAME, methodName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverUploadFailEmail
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverUploadFailEmail(APDDeliveryContextDocument context)
			throws MitchellException {
		String methodName = "deliverUploadFailEmail";
		logger.entering(CLASS_NAME, methodName);
		AssignmentDeliveryClient client = new AssignmentDeliveryClient();
		client.deliverEstimateUploadFailEmail(context);
		logger.exiting(CLASS_NAME, methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverAssignment
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverAssignment(APDDeliveryContextDocument context)
			throws AssignmentDeliveryException, MitchellException {
		String methodName = "deliverAssignment";
		logger.entering(CLASS_NAME, methodName);
		AssignmentDeliveryClient client = new AssignmentDeliveryClient();
		client.deliverAssignment(context);
		logger.exiting(CLASS_NAME, methodName);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverAssignment
	 * (com.mitchell.services.business.partialloss.assignmentdelivery
	 * .AssignmentServiceContext)
	 */
	public void deliverAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException, MitchellException {
		String methodName = "deliverAssignment";
		logger.entering(CLASS_NAME, methodName);
		AssignmentDeliveryClient client = new AssignmentDeliveryClient();
		client.deliverAssignment(context);
		logger.exiting(CLASS_NAME, methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #cancelAssignment
	 * (com.mitchell.services.business.partialloss.assignmentdelivery
	 * .AssignmentServiceContext)
	 */
	public void cancelAssignment(AssignmentServiceContext context)
			throws AssignmentDeliveryException, MitchellException {
		String methodName = "cancelAssignment";
		logger.entering(CLASS_NAME, methodName);

		AssignmentDeliveryClient client = new AssignmentDeliveryClient();
		client.cancelAssignment(context);
		logger.exiting(CLASS_NAME, methodName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverAssignmentEmail4DRP
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverAssignmentEmail4DRP(APDDeliveryContextDocument context)
			throws MitchellException {
		new AssignmentDeliveryClient().deliverAssignmentEmail4DRP(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverUploadSuccessEmail4DRP
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverUploadSuccessEmail4DRP(APDDeliveryContextDocument context)
			throws MitchellException {
		new AssignmentDeliveryClient()
				.deliverEstimateUploadSuccessEmailDRP(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverUploadFailEmail4DRP
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public void deliverUploadFailEmail4DRP(APDDeliveryContextDocument context)
			throws MitchellException {
		new AssignmentDeliveryClient()
				.deliverEstimateUploadFailEmailDRP(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverNetworkShopSuppAsgEmail
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument,
	 * java.util.ArrayList)
	 */
	public void deliverNetworkShopSuppAsgEmail(
			APDDeliveryContextDocument context, ArrayList partsListAttachment)
			throws MitchellException {
		new AssignmentDeliveryClient().deliverDrpShopSuppAssignmentEmail(
				context, partsListAttachment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverNonNetworkShopSuppAsgEmail
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument,
	 * java.util.ArrayList)
	 */
	public void deliverNonNetworkShopSuppAsgEmail(
			APDDeliveryContextDocument context, ArrayList partsListAttachment)
			throws MitchellException {

		new AssignmentDeliveryClient().deliverNonDrpShopSuppAssignmentEmail(
				context, partsListAttachment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverAssignmentEmailNotification
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument,
	 * java.util.ArrayList, java.lang.String)
	 */
	public void deliverAssignmentEmailNotification(
			APDDeliveryContextDocument apdDelContext,
			ArrayList partsListAttachment, String emailType)
			throws MitchellException {
		new AssignmentDeliveryClient().deliverAssignmentEmailNotification(
				apdDelContext, partsListAttachment, emailType);
	}

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy
	 * #deliverRCWebAssignment
	 * (com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverRCWebAssignment(APDDeliveryContextDocument context) throws AssignmentDeliveryException, MitchellException {
        String methodName = "deliverRCWebAssignment";
        this.logger.entering(CLASS_NAME, methodName);

        AssignmentDeliveryFacade client = getAssignmentDeliveryClient();

        this.logger.exiting(CLASS_NAME, methodName);

        client.deliverRCWebAssignment(context);

    }

   protected AssignmentDeliveryFacade getAssignmentDeliveryClient() {

        return new AssignmentDeliveryClient();

    }

}
