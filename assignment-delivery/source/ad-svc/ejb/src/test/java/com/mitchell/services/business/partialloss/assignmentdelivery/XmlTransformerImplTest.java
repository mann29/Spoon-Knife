package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.IOException;
import org.junit.Test;
import org.springframework.util.Assert;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.XsltTransformerImpl;

public class XmlTransformerImplTest {
	
	@Test
	public void testTrasnformMethod() throws MitchellException, IOException{
		XsltTransformerImpl xsltTransformerImpl= new XsltTransformerImpl();		
		String xsltPath="src/test/resources/AssignmentEmail_es.xslt";
		String subject="<CreationSubject><CoName>AAA Northern CA</CoName><ClaimNumber>RS3271423-01</ClaimNumber></CreationSubject>"; 	
	    Assert.notNull(xsltTransformerImpl.transformXmlString(xsltPath,subject));
	}
	
}
