package com.mitchell.services.business.partialloss.assignmentdelivery;

import junit.framework.TestCase;
/**
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv Khanwale </a>
 * Created/Modified on Jul 20, 2010
 */
public class ClassNameExttrationText extends TestCase {
	public void testClassNameExtractionWhenAllIsWell() throws Exception {
		final  String expected = "ClassNameExttrationText";
		assertEquals(expected, ExtractClassName.from(this.getClass()));
	}
}
