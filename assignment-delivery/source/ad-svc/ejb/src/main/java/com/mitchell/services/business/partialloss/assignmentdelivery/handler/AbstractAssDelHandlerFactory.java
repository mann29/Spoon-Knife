package com.mitchell.services.business.partialloss.assignmentdelivery.handler;

import java.util.Map;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;

/**
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Jul 26, 2010
 */
public abstract class AbstractAssDelHandlerFactory {
	private Map handlerMap;

	public Map getHandlerMap() {
		return handlerMap;
	}

	public void setHandlerMap(Map handlerMap) {
		this.handlerMap = handlerMap;
	}

	/**
	 * See if I can find it first, if not delegate to subclass.
	 * 
	 * @param key
	 * @return
	 * @throws AssignmentDeliveryException
	 */
	public AssignmentDeliveryHandler getInstance(String key)
			throws AssignmentDeliveryException {
		if (handlerMap == null || handlerMap.get(key) == null) {
			return delegatedGetInstance(key);
		} else {
			return (AssignmentDeliveryHandler) handlerMap.get(key);
		}
	}

	abstract public AssignmentDeliveryHandler delegatedGetInstance(String key)
			throws AssignmentDeliveryException;
}