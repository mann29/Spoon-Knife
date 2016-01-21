package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EstimatePlatformType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.services.core.notification.client.Notification;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.notification.types.FaxRequestDocument;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.StringUtilities;

public class SupplementNotification
{

  private static final Logger logger = Logger
      .getLogger(SupplementNotification.class.getName());
  private String subject = null;
  private SupplementReqBO suppReqBO = null;
  private SupplementRequestDocument suppRequestDoc = null;
  private String EMS_ESTIMATE_TYPE = "EMS";
  private String MIE_ESTIMATE_TYPE = "MIE";
  private String CCC_ESTIMATE_TYPE = "CCC";
  private String AUDATEX_ESTIMATE_TYPE = "AUDATEX";
  private String GTMOTIVE_ESTIMATING_TYPE="GTMOTIVE";
   

  
  /**
   * Retrieve Supplement Request Notification Document (Email Body)
   */
  public String retrieveNotificationDoc(SupplementReqBO suppReqBO,
      SupplementRequestDocument suppRequestDoc)
      throws MitchellException
  {

    String suppNotificationDocText = null;

    logger
        .info("About to generate the Supplement Request Notification Document ...");
    this.suppReqBO = suppReqBO;
    this.suppRequestDoc = suppRequestDoc;
    populateSubject();

    logger.info("********* retrieveNotificationDoc, suppReqBO.isEmail()="
        + suppReqBO.isEmail());
    // logger.info("********* retrieveNotificationDoc, suppReqBO.isFax()="+suppReqBO.isFax() );

    if (suppReqBO.isEmail()) {
      logger.fine("About to generate email Notification Document ...");
      suppNotificationDocText = retrieveEmailNotificationDoc();
      logger.fine("About to generate email Notification Document ... done");
    }

    if (suppReqBO.isFax()) {
      logger.fine("About to generate Fax Notification Document ...");
      // For now , only text email is supported
      // processFaxNotification();            
      logger.fine("About to generate Fax Notification Document ... done");
    }
    logger.info("About to generate the Notification Document ... done");

    // if(logger.isLoggable(Level.INFO)) {
    //     logger.info(" **************** The suppNotificationDocText is \n " + suppNotificationDocText);
    // }

    return suppNotificationDocText;
  }

  /**
   * Retrieve Supplement Request Notification document - email body
   */
  private String retrieveEmailNotificationDoc()
      throws MitchellException
  {

    try {

      // For now , only text email is supported
      String emailText = null;
      String toEmailAddress = null;
      boolean isToEmailAddressFound = false;
      String culture = this.suppReqBO.getCulture();
      
      // check if its EMS
      if ("EMS".equals(suppRequestDoc.getSupplementRequest().getEstimateType()
          .toString())) {
        EMSTextEmailFormatter emsTextFormatter = new EMSTextEmailFormatter();
        emailText = emsTextFormatter.createEMSTextEmail(suppRequestDoc);
      } else if (EstimatePlatformType.CCC == suppRequestDoc
          .getSupplementRequest().getEstimateType()) {
        CCCBMSTextEmailFormatter formatter = new CCCBMSTextEmailFormatter();
        emailText = formatter.createTextEmail(this.suppRequestDoc);
      } else if (EstimatePlatformType.MIE == suppRequestDoc
          .getSupplementRequest().getEstimateType()) {
        MIEBMSTextEmailFormatter formatter = new MIEBMSTextEmailFormatter();
        emailText = formatter.createTextEmail(this.suppRequestDoc);
      } else if ("AUDATEX".equals(suppRequestDoc.getSupplementRequest()
          .getEstimateType().toString())) {
        BMSTextEmailFormatter bmsTextFormatter = new BMSTextEmailFormatter();
        emailText = bmsTextFormatter.createTextEmail(suppRequestDoc);
      }else if (GTMOTIVE_ESTIMATING_TYPE.equals(suppRequestDoc.getSupplementRequest()
              .getEstimateType().toString())) {
    	  logger.info("Creating GTMotiveTextEmail formattor");
    	  GTMotiveTextEmailFormatter gtTextFormatter = new GTMotiveTextEmailFormatter();    
    	  logger.info("created GTMotiveTextEmail formattor");
    	 
          emailText = gtTextFormatter.createTextEmail(suppRequestDoc, culture);
          logger.info("genrated GTMotiveTextEmail emailText" +emailText);
        }
      else {
        throw new RuntimeException("Unsupported Estimate Format "
            + suppRequestDoc.getSupplementRequest().getEstimateType()
                .toString());
      }

      return emailText;

    } catch (Exception e) {
      throw new MitchellException(105200,
          SupplementNotification.class.getName(), "processEmailNotification",
          "Unknown Error occurred in sending the email", e);
    }
  }

  public void sendNotification(SupplementReqBO suppReqBO,
      SupplementRequestDocument suppRequestDoc)
      throws MitchellException
  {

    logger.info("About the send the Notification ...");
    this.suppReqBO = suppReqBO;
    logger.info(suppRequestDoc.toString());
    this.suppRequestDoc = suppRequestDoc;
    populateSubject();

    if (suppReqBO.isEmail()) {
      logger.fine("About to send email ...");
      processEmailNotification();
      logger.fine("About to send email ... done");
    }

    if (suppReqBO.isFax()) {
      logger.fine("About to send fax ...");
      processFaxNotification();
      logger.fine("About to send fax ... done");
    }
    logger.info("About the send the Notification ... done");
  }

  /**
   * Handle email notification
   */
  private void processEmailNotification()
      throws MitchellException
  {

    try {
      // For now , only text email is supported
      String emailText = null;
      String toEmailAddress = null;
      boolean isToEmailAddressFound = false;

      // check if its EMS
      if ("EMS".equals(suppRequestDoc.getSupplementRequest().getEstimateType()
          .toString())) {
        EMSTextEmailFormatter emsTextFormatter = new EMSTextEmailFormatter();
        emailText = emsTextFormatter.createEMSTextEmail(suppRequestDoc);
      }

      else if ("MIE".equals(suppRequestDoc.getSupplementRequest()
          .getEstimateType().toString())
          || "CCC".equals(suppRequestDoc.getSupplementRequest()
              .getEstimateType().toString())
          || "AUDATEX".equals(suppRequestDoc.getSupplementRequest().getEstimateType().toString())  ||"GTMOTIVE".equals(suppRequestDoc.getSupplementRequest().getEstimateType().toString()) ) {
        BMSTextEmailFormatter bmsTextFormatter = new BMSTextEmailFormatter();
        emailText = bmsTextFormatter.createTextEmail(suppRequestDoc);
      } else {
        throw new RuntimeException("Unsupported Estimate Format "
            + suppRequestDoc.getSupplementRequest().getEstimateType()
                .toString());
      }

      // if(logger.isLoggable(Level.INFO)) {
      //    logger.info(" **************** The emailText is \n " + emailText);
      // }

      if (!StringUtilities.isEmpty(suppReqBO.getToEmailAddress())) {
        toEmailAddress = suppReqBO.getToEmailAddress();
        isToEmailAddressFound = true;
      } else {
        toEmailAddress = suppReqBO.getCcEmailAddress();
      }

      // create the notification request
      EmailRequestDocument emailDoc = Notification.buildEmailRequest(
          SuppRequestSystemConfig.getFromEmailDisplayName(),
          SuppRequestSystemConfig.getFromEmailAddress(), toEmailAddress,
          subject, emailText);

      // Set CC if present                
      if (!StringUtilities.isEmpty(suppReqBO.getCcEmailAddress())
          && isToEmailAddressFound) {
        Notification.setEmailCCs(emailDoc, suppReqBO.getCcEmailAddress(), null);
      }

      if (logger.isLoggable(Level.FINE)) {
        logger.fine(" **************** The EmailDoc is \n " + emailDoc);
      }

      // Notify via email
      Notification
          .notifyByEmail(emailDoc, suppReqBO.getReviewerUserInfo(),
              suppRequestDoc.getSupplementRequest().getAdminInfo()
                  .getClaimNumber(), "AppraisalAssignment", // "CARR",
              "SupplementNotification", "SupplementRequest"); // "CARRHelper");            
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(105200,
          SupplementNotification.class.getName(), "processEmailNotification",
          "Unknown Error occurred in sending the email", e);
    }
  }

  /**
   * Process Fax Notification - Only "Text" formatter is supported - HTML is not
   */
  private void processFaxNotification()
      throws MitchellException
  {

    String emailText = null;
    String pdfPath = null;

    try {

      // check if its EMS
      if (EMS_ESTIMATE_TYPE.equals(suppRequestDoc.getSupplementRequest()
          .getEstimateType().toString())) {
        EMSTextEmailFormatter emsTextFormatter = new EMSTextEmailFormatter();
        emailText = emsTextFormatter.createEMSTextEmail(suppRequestDoc);
      }

      else if (MIE_ESTIMATE_TYPE.equals(suppRequestDoc.getSupplementRequest()
          .getEstimateType().toString())
          || CCC_ESTIMATE_TYPE.equals(suppRequestDoc.getSupplementRequest()
              .getEstimateType().toString())
          || AUDATEX_ESTIMATE_TYPE.equals(suppRequestDoc.getSupplementRequest()
              .getEstimateType().toString())) {
        BMSTextEmailFormatter bmsTextFormatter = new BMSTextEmailFormatter();
        emailText = bmsTextFormatter.createTextEmail(suppRequestDoc);
      } else {
        throw new RuntimeException("Unsupported Estimate Format "
            + suppRequestDoc.getSupplementRequest().getEstimateType()
                .toString());
      }

      // create the pdf
      pdfPath = SuppRequestUtils.createPDF(emailText);

      // create the Fax Notification Request
      FaxRequestDocument faxDoc = Notification.buildFaxRequestDoc(
          SuppRequestSystemConfig.getFromEmailDisplayName(), null,
          SuppRequestSystemConfig.getFromEmailAddress(),
          "Mitchell International", null, null, subject, suppRequestDoc
              .getSupplementRequest().getAdminInfo().getReceipientInfo()
              .getName(), suppRequestDoc.getSupplementRequest().getAdminInfo()
              .getReceipientInfo().getCoName(), suppReqBO.getFaxNumber(), null,
          null, subject, // send the subject as body content
          true, null);

      // Copy the attachment

      if (pdfPath != null) {
        Notification.stageFaxAttachment(faxDoc, pdfPath);
      }

      if (logger.isLoggable(Level.FINE)) {
        logger.fine(" **************** The FaxDoc is \n " + faxDoc);
      }

      // send the Fax
      Notification
          .notifyByFax(faxDoc, suppReqBO.getReviewerUserInfo(), suppRequestDoc
              .getSupplementRequest().getAdminInfo().getClaimNumber(),
              "AppraisalAssignment", // "CARR",
              "SupplementRequest"); // "CARRHelper");                    
    } catch (MitchellException me) {
      throw me;
    } catch (Exception e) {
      throw new MitchellException(105200,
          SupplementNotification.class.getName(), "processFaxNotification",
          "Unknown Error occurred in processing the Fax Notification", e);
    } finally {

      try {
        // delete the temp file created
        if (pdfPath != null) {
          FileUtils.deleteFile(pdfPath);
        }
      } catch (Exception e) {
        logger.warning("Could not delete the tmp file " + pdfPath);
      }
    }
  }

  private void populateSubject()
  {

    String subjectTemplate = SuppRequestSystemConfig
        .getTextEmailSubjectTemplate();
    String subjectPropsList = SuppRequestSystemConfig
        .getTextEmailSubjectTemplateProps();

    String[] propsData = subjectPropsList.split(",");
    String[] values = { suppRequestDoc.getSupplementRequest().getAdminInfo()
        .getClaimNumber() };

    String prefix = SuppRequestSystemConfig.getTextTemplatePrefix();
    String suffix = SuppRequestSystemConfig.getTextTemplateSuffix();

    StringBuilder subBuffer = new StringBuilder();
    subBuffer.append(subjectTemplate);

    for (int i = 0; i < propsData.length; i++) {
      // Replace the template with the actual data
      SuppRequestUtils.updateStringBuffer(subBuffer, prefix + propsData[i]
          + suffix, values[i]);
    }
    subject = subBuffer.toString();

  }
}
