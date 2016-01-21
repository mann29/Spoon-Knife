package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.schemas.compliance.LogsDocument;
import com.mitchell.schemas.estimate.AnnotatedEstimateDocument;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.utils.misc.StringUtilities;
import com.mitchell.utils.misc.UUIDFactory;

// TODO - Verify that itext-1.4.81.jar is required for supplement request
// ONLY Needed for SuppRequestUtils.createPDF used by
// SupplementNotification.processFaxNotification
// import com.lowagie.text.Element;
// import com.lowagie.text.PageSize;
// import com.lowagie.text.Paragraph;
// import com.lowagie.text.pdf.PdfWriter;

public class SuppRequestUtils
{
  private static final Logger logger = Logger.getLogger(SuppRequestUtils.class
      .getName());

  /**
   * Gets the BMS, Annotation and Compliance documents & adds it to suppReqBO
   */
  public static void populateEstimateInfo(SupplementReqBO suppReqBO)
      throws Exception
  {

    AnnotatedEstimateDocument annotateDocument = null;
    VehicleDamageEstimateAddRqDocument bmsDocument = null;
    LogsDocument complianceDocument = null;

    String complianceData = null;
    String bmsData = null;
    String annotationData = null;

    logger.fine("Entering  .... ");

    //EstimatePackageRemote ejb = EstimatePackageClient.getEstimatePackageEJB();
    Estimate estimate = new EstimatePackageClient().getEstimateAndDocByDocId(
        Long.valueOf(suppReqBO.getEstimateDocId()), true);

    if (estimate != null) {

      // set the claim id
      if (estimate.getClaimId() != null) {
        suppReqBO.setClaimId(estimate.getClaimId().longValue());
      }

      // set the suffix id
      if (estimate.getClaimExposureId() != null) {
        suppReqBO.setSuffixId(estimate.getClaimExposureId().longValue());
      }
      // set client claim num
      if (estimate.getClientClaimNumber() != null) {
        suppReqBO.setClientClaimId(estimate.getClientClaimNumber());
      }
      
      // get the bms
      if (estimate.getEstimateXmlEstimate() != null) {
        bmsData = estimate.getEstimateXmlEstimate().getXmlData();

        VehicleDamageEstimateAddRqDocument bmsDoc = null;

        if (bmsData != null) {
          bmsDocument = VehicleDamageEstimateAddRqDocument.Factory
              .parse(bmsData);
          suppReqBO.setBMS(bmsDocument);
        } else {
          throw new RuntimeException(
              "Could not retrieve BMS document for the estimateDocId "
                  + suppReqBO.getEstimateDocId());
        }
      }

      // get the compliance
      if (estimate.getEstimateComplianceResult() != null) {
        complianceData = estimate.getEstimateComplianceResult().getXmlData();

        if (complianceData != null) {
          complianceDocument = LogsDocument.Factory.parse(complianceData);
          suppReqBO.setCompliance(complianceDocument);
        }
      }

      // get the annotation
      if (estimate.getEstimateXmlAnnotation() != null) {
        annotationData = estimate.getEstimateXmlAnnotation().getXmlData();

        if (annotationData != null) {
          annotateDocument = AnnotatedEstimateDocument.Factory
              .parse(annotationData);
          suppReqBO.setAnnotation(annotateDocument);
        }
      }

      // inject the mock up, remove it once real thing saved in DB
      //injectAnnotationXml(suppReqBO);

      // get the client estimateId
      suppReqBO.setExternalEstimateId(estimate.getClientEstimateId());

      logger.info("Got the Estimate data ...");
    } else {
      throw new RuntimeException(
          "Could not retrieve Estimate for the estimateDocId "
              + suppReqBO.getEstimateDocId());
    }

    logger.fine("Exiting .... ");
  }

  public static String formatDateToString(Calendar calendar)
  {

    String formattedDate = null;
    String datePattern = "MM-dd-yyyy";

    if (calendar != null) {
      SimpleDateFormat dateFormatter = new SimpleDateFormat();
      dateFormatter.applyPattern(datePattern);
      formattedDate = dateFormatter.format(calendar.getTime());
    }
    return formattedDate;
  }

  public static String getSubmitSupplementComment(
      SupplementRequestDocument suppReqDocument)
  {

    String comments = null;

    String submitSuppCommentsTemplate = SuppRequestSystemConfig
        .getSubmitSupplementCommentTemplate();
    String submitSuppCommentsTemplateProps = SuppRequestSystemConfig
        .getSubmitSupplementCommentTemplateProps();

    String prefix = SuppRequestSystemConfig.getTextTemplatePrefix();
    String suffix = SuppRequestSystemConfig.getTextTemplateSuffix();

    String[] datas = submitSuppCommentsTemplateProps.split(",");
    String[] values = {
        suppReqDocument.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().getName(),
        suppReqDocument.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getName() };

    StringBuilder subBuffer = new StringBuilder();
    subBuffer.append(submitSuppCommentsTemplate);

    for (int i = 0; i < datas.length; i++) {
      // Replace the template with the actual data
      updateStringBuffer(subBuffer, prefix + datas[i] + suffix, values[i]);
    }

    comments = subBuffer.toString();

    return comments;
  }

  static void updateStringBuffer(StringBuilder buffer, String pattern,
      String value)
  {
    String string = buffer.toString();
    int position = -1;

    position = string.indexOf(pattern);
    int length = pattern.length();

    if (position >= 0 && !StringUtilities.isEmpty(value)) {
      buffer.replace(position, (position + length), value);
    } else {
      buffer.replace(position, (position + length), " ");
    }
  }

  static void updateStringBuffer(StringBuilder buffer, String pattern,
      String value, String prefixChar)
  {
    String string = buffer.toString();
    int position = -1;

    position = string.indexOf(pattern);
    int length = pattern.length();

    if (position >= 0 && !StringUtilities.isEmpty(value)) {
      buffer.replace(position, (position + length), (prefixChar + value));
    } else {
      buffer.replace(position, (position + length), " ");
    }
  }

  /**
   * Creates the Passed in String as PDF, using iText
   * 
   * Based on the code from TxFarmBureau - Special processing
   */
  static String createPDF(String emailBody)
      throws Exception
  {

    String pdfPath = SuppRequestSystemConfig.getTempDir() + File.separator
        + UUIDFactory.getInstance().getUUID() + "__"
        + System.currentTimeMillis() + ".pdf";
    /***
     * 
     * 
     // Create the PDF Document
     * 
     * // step 1: creation of a document-object
     * com.lowagie.text.Document document = new
     * com.lowagie.text.Document(PageSize.LETTER, 36, 36, 36, 36);
     * 
     * // step 2:
     * // we create a writer that listens to the document
     * // and directs a PDF-stream to a file
     * PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
     * 
     * // step 3: we open the document
     * document.open();
     * 
     * // step 4:
     * document.setMargins(36, 36, 36, 36);
     * Paragraph paragraph = new Paragraph();
     * paragraph.setAlignment(Element.ALIGN_LEFT);
     * paragraph.add(emailBody);
     * document.add(paragraph);
     * 
     * // step 5: we close the document
     * document.close();
     * 
     * 
     */

    return pdfPath;
  }

  /**
   * Returns the activity log comments based on the Supp Request options chosen
   */
  public static String getActivityLogComment(SupplementReqBO suppReqBO)
  {

    String baseComment = "The Supplement Request : ";
    String emailComment = null;
    String faxComment = null;

    if (suppReqBO.isEmail()) {
      String emailComments = "Email sent to ";
      String toEmailAddress = suppReqBO.getToEmailAddress();
      String ccEmailAddress = suppReqBO.getCcEmailAddress();

      if (StringUtilities.isEmpty(suppReqBO.getToEmailAddress())
          && StringUtilities.isEmpty(suppReqBO.getCcEmailAddress())) {
      } else {
        if (!StringUtilities.isEmpty(suppReqBO.getToEmailAddress())
            && !StringUtilities.isEmpty(suppReqBO.getCcEmailAddress())) {
          emailComment = "Email sent to " + toEmailAddress + " and "
              + ccEmailAddress;
        } else if (!StringUtilities.isEmpty(suppReqBO.getCcEmailAddress())) {
          emailComment = "Email sent to " + ccEmailAddress;
        } else if (!StringUtilities.isEmpty(suppReqBO.getToEmailAddress())) {
          emailComment = "Email sent to " + toEmailAddress;
        }
      }
    }

    if (suppReqBO.isFax()) {
      faxComment = " Fax sent to " + suppReqBO.getFaxNumber();
    }

    if (emailComment != null && faxComment != null) {
      baseComment += emailComment;
      baseComment += "; " + faxComment;
    } else if (emailComment != null) {
      baseComment += emailComment;
    } else if (faxComment != null) {
      baseComment += faxComment;
    }

    return baseComment;
  }

  public static void injectAnnotationXml(SupplementReqBO suppReqBO)
      throws Exception
  {

    AnnotatedEstimateDocument annotateDocument = AnnotatedEstimateDocument.Factory
        .parse(new java.io.File("c:\\download\\annot.xml"));
    suppReqBO.setAnnotation(annotateDocument);
  }

}
