package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;

public class XsltTransformer
{
  private static final Logger logger = Logger.getLogger(XsltTransformer.class
      .getName());

  private String fileName;
  private Templates template;
  private Transformer transformer;

  public XsltTransformer(final String styleSheetFileName)
      throws MitchellException
  {

    try {

      this.fileName = styleSheetFileName;
      final TransformerFactory factory = TransformerFactory.newInstance();
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          new FileInputStream(fileName),
          AppraisalAssignmentConstants.DEFAULT_ENCODING));
      this.template = factory.newTemplates(new StreamSource(reader));
    } catch (final Exception e) {

      final String errorDesc = AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION_MSG
          + " : " + styleSheetFileName;

      AASCommonUtils aasCommonUtil = (AASCommonUtils) getSpringBean("AASCommonUtils");

      aasCommonUtil.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION, this
              .getClass().getName(), "Constructor", errorDesc, e, logger);
    }
  }

  public Transformer createTransformer()
      throws MitchellException
  {

    try {

      this.transformer = this.template.newTransformer();

    } catch (final Exception e) {

      final String errorDesc = AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION_MSG
          + " : " + this.fileName;

      AASCommonUtils aasCommonUtil = (AASCommonUtils) getSpringBean("AASCommonUtils");
      aasCommonUtil.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION, this
              .getClass().getName(), "createTransformer", errorDesc, e, logger);
    }
    return transformer;
  }

  public String transformXmlString(final String xmlString)
      throws MitchellException
  {

    String result = null;
    this.createTransformer();

    try {

      final StringWriter writer = new StringWriter();

      this.transformer.transform(new StreamSource(new StringReader(xmlString)),
          new StreamResult(writer));

      result = writer.getBuffer().toString();
    } catch (final Exception e) {

      final String errorDesc = AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION_MSG
          + " : " + this.fileName + " with : " + xmlString;

      AASCommonUtils aasCommonUtil = (AASCommonUtils) getSpringBean("AASCommonUtils");
      aasCommonUtil
          .logAndThrowError(
              AppraisalAssignmentConstants.ERROR_IN_XSL_TRANSFORMATION, this
                  .getClass().getName(), "transformXmlString", errorDesc, e,
              logger);
    }
    return result;
  }

  protected Object getSpringBean(String beanName)
      throws MitchellException
  {
    Object o = null;
    try {
      o = BeanLocator.getBean(beanName);
    } catch (IllegalAccessException e) {
      throw new MitchellException(
          this.getClass().getName(),
          "getSpringBean",
          "Exception getting Spring bean, likely not properly initialized/reference counted.",
          e);
    }
    return o;
  }

}
