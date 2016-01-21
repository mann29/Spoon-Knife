package com.mitchell.services.business.partialloss.appraisalassignment.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.exception.MitchellException;


public class InternationlizeDataTest {
	InternationlizeDataUtil intDataImpl =null;
	@Before
	public void setUp() throws Exception {
		intDataImpl = new InternationlizeDataUtil();
	}

	@Test
	public void testTranslatedValue_fr_WithKey() throws MitchellException {

		
		String value = intDataImpl.getTranslatedValue("REMOVE/ REPLACE /PARTIAL",
				"fr-ca_en-us");
		assertEquals("RETIRER / REMPLACER / PARTIELLE", value);

	}

	@Test
	public void testTranslatedValue_fr_Without_Key() throws MitchellException {

		
		String value = intDataImpl.getTranslatedValue("PENASSIGNEN", "fr-ca_en-us");
		assertEquals("PENASSIGNEN", value);

	}

	@Test
	public void testTranslatedValue_en_With_Key() throws MitchellException {

	
		String value = intDataImpl.getTranslatedValue("ADD\'LLABOR","en");
		assertEquals("ADD'L LABOR", value);

	}

	@Test
	public void testTranslatedValue_en_Without_Key() throws MitchellException {

		
		String value = intDataImpl.getTranslatedValue("en_without_key", "en");
		assertEquals("en_without_key", value);

	}
	
	@Test
	public void testTranslatedValue_en_Without_Key1() throws MitchellException {

		
		String value = intDataImpl.getTranslatedValue("REMOVE/REPLACE", "en");
		assertEquals("REMOVE/REPLACE", value);

	}

}
