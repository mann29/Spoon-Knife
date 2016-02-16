package com.mitchell.services.core.partialloss.apddelivery.utils;

import com.mitchell.common.exception.MitchellException;

public interface CommonUtil {

	/**
	 * @param value
	 * String value
	 * @return boolean
	 * boolean
	 */
	public abstract boolean isNullOrEmpty(String value);

	/**
	 * This method hands over a message to Message Bus Service, 
	 * to publish it to JMS queue based on given Event ID.
	 * <p>
	 * 
	 * @param msgContext
	 * It holds all fields required to create an MWM doc
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void publishToMessageBus(MessagingContext msgContext)
			throws MitchellException;

}