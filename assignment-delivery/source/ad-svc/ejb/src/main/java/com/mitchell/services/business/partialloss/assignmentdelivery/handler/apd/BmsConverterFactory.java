package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import java.util.Map;

/**
 * Interface for getting a CIECA BMS converter.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 *
 */
public interface BmsConverterFactory {
	public static final String SHOP = "SHOP";
	public  static final String COMPANY_CODE = "COMPANY_CODE";

	public CiecaBmsConverter createCiecaBmsConvertor(Map context);
}
