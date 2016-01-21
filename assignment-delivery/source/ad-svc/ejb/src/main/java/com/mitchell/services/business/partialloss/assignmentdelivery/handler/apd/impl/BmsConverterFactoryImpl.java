package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;
import java.util.Map;

/**
 * Implementation for BmsConverter Factory determining a CIECA BMS converter.
 * @author <href a="mailto://randy.bird@mitchell.com">Randy Bird</a>
 *
 */
public class BmsConverterFactoryImpl implements BmsConverterFactory {

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory#createCiecaBmsConvertor(java.util.Map)
	 */
	public CiecaBmsConverter createCiecaBmsConvertor(Map context) {
		if ( context == null ){
			throw new IllegalArgumentException("Please provide a context, even if it's empty");
		}
		final String companyCode = (String) context.get(BmsConverterFactory.COMPANY_CODE);
		final boolean isShop = ((Boolean) context.get(BmsConverterFactory.SHOP)).booleanValue();
		final boolean isStaff = !isShop;
		if ( (companyCode.equals("SF") || companyCode.equals("S3") )&& isStaff) {
			return new SFStaffCiecaBmsConverter();
		} else if ( companyCode.equals("SF") && isShop) {
			return new SFShopCiecaBMSConverter();
		} else {
			// Catch-All Generic Converter for All other Carriers - Staff and Shop
			return new GenericCiecaBmsConverter();
		}
	}		
}
