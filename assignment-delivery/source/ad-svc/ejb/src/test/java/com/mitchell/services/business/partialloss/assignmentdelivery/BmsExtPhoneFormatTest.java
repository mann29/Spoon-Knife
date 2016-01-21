package com.mitchell.services.business.partialloss.assignmentdelivery;

import junit.framework.TestCase;
/**
 * 
 * @author <a href="mailto:prashant.khanwale@mitchell.com"> Prashant Sadashiv Khanwale </a></br>
 * Created Jul 16, 2010
 */
public class BmsExtPhoneFormatTest extends TestCase {
	final private BmsExtPhoneFormat target  = new BmsExtPhoneFormat();
	public void testWhatUsedToBeMain() throws Exception {
		final String expected = "8584446551A";
		final String input = "+001-858-4446551A+1234567891";
		assertEquals(expected,target.formatCieca210PhoneNo(input));
	}
}
