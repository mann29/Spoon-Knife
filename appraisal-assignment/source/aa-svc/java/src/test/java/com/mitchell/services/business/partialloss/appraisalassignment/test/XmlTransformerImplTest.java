package com.mitchell.services.business.partialloss.appraisalassignment.test;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.Assert;

import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.XsltTransformer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BeanLocator.class })
public class XmlTransformerImplTest
{

  @Test
  public void testTrasnformMethod()
      throws Exception
  {
    AASCommonUtils aasCommonUtil = mock(AASCommonUtils.class);
    PowerMockito.mockStatic(BeanLocator.class);
    String param = "AASCommonUtils";
    when(BeanLocator.getBean(param)).thenReturn(aasCommonUtil);
    String xsltPath = "src/test/resources/MIEHTMLAnnotation_es.xsl";
    XsltTransformer xsltTransformer = new XsltTransformer(xsltPath);
    String subject = "<CreationSubject><CoName>AAA Northern CA</CoName><ClaimNumber>RS3271423-01</ClaimNumber></CreationSubject>";
    System.out.println(xsltTransformer.transformXmlString(subject));
    Assert.notNull(xsltTransformer.transformXmlString(subject));
  }

}
