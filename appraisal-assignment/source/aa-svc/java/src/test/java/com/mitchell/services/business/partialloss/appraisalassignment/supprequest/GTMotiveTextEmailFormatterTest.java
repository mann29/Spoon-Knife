package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import com.mitchell.common.exception.MitchellException;

public class GTMotiveTextEmailFormatterTest {


	GTMotiveTextEmailFormatter gtm = null;
	static final String filePath = "src/test/resources";

	@Before
	public void setUp() {

		gtm = new GTMotiveTextEmailFormatter();
		

	}

	@Test
	public void cultureNotNullWithFilePathTest() throws MitchellException{
		String culture = "es-ES";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(filePath, fileName, culture);
		assertNotNull(result);

	}

	@Test
	public void cultureNotNullEqualsWithFilePathTest()  throws MitchellException{
		String culture = "es";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(filePath, fileName, culture);
		assertNotNull(result);
		assertEquals(filePath + File.separator
				+ "GTE_Supp_Request_Template_es.txt", result);
	}


	@Test
	public void cultureFileNotFoundWithFilePathTest()  throws MitchellException{
		String culture = "fr-FR";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(filePath, fileName, culture);
		assertNotNull(result);
		assertEquals(filePath + File.separator
				+ "GTE_Supp_Request_Template.txt", result);
	}

	@Test(expected=MitchellException.class)
	public void cultureNotNullWithoutFilePathTest() throws MitchellException{
		String culture = "es-ES";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(null, fileName, culture);
		assertNotNull(result);

	}

	@Test(expected=MitchellException.class)
	public void cultureNotNullEqualsWithoutFilePathTest() throws MitchellException{
		String culture = "es-ES";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(null, fileName, culture);
		assertNotNull(result);
		assertEquals("GTE_Supp_Request_Template.txt", result);
	}

	

	@Test(expected=MitchellException.class)
	public void cultureFileNotFoundWithoutFilePathTest() throws MitchellException{
		String culture = "fr-FR";
		String fileName = "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(null, fileName, culture);
		assertNotNull(result);
		assertEquals("GTE_Supp_Request_Template.txt", result);
	}
	
	@Test(expected=MitchellException.class)
	public void cultureFileNotFoundExceptionTest() throws MitchellException{
		String culture = "fr-FR";
		String fileName = "GTE_Supp_XYZ";

		String result = gtm.getFileNameWithCulture(filePath, fileName, culture);
		assertNotNull(result);
		assertEquals(filePath + File.separator
				+ "GTE_Supp_Request_Template.txt", result);
	}
	
	@Test
	public void cultureFileFoundWithPathTest() throws MitchellException{
		String culture = "fr-FR";
		String fileName = filePath + File.separator + "GTE_Supp_Request_Template";

		String result = gtm.getFileNameWithCulture(null, fileName, culture);
		assertNotNull(result);
		assertEquals(filePath + File.separator
				+ "GTE_Supp_Request_Template.txt", result);
	}
}
