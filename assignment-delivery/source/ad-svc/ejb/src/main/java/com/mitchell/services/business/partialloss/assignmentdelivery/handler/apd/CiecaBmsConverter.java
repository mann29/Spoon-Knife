package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.cieca.bms.CIECADocument;

/**
 * Interface for converting a CIECA BMS based on a context.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 *
 */

public interface CiecaBmsConverter {
	public CIECADocument convert (CIECADocument input);

}
