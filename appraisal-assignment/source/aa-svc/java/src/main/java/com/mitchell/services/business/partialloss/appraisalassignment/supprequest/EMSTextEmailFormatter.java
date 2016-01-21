package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.utils.misc.FileUtils;

public class EMSTextEmailFormatter
{
  private static final Logger logger = Logger
      .getLogger(EMSTextEmailFormatter.class.getName());

  String createEMSTextEmail(SupplementRequestDocument suppRequestDoc)
      throws IOException
  {

    // Get the template data

    String emailBody = null;

    String file = SuppRequestSystemConfig.getEMSTextTemplateFile();
    String propsList = SuppRequestSystemConfig.getEMSTextTemplateProperties();
    String prefix = SuppRequestSystemConfig.getTextTemplatePrefix();
    String suffix = SuppRequestSystemConfig.getTextTemplateSuffix();

    // Get the Template Data        
    String templateData = FileUtils.readFileIntoString(file);

    // Get the list of properties
    String[] datas = propsList.split(",");
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("The properties are " + propsList);
    }

    // Buffer which holds the template data
    StringBuilder buffer = new StringBuilder();
    buffer.append(templateData);

    // Get the Deskreview Comments
    String deskReviewComments = null;
    if (suppRequestDoc.getSupplementRequest().isSetDeskReviewInfo()
        && suppRequestDoc.getSupplementRequest().getDeskReviewInfo()
            .isSetComments()) {
      deskReviewComments = suppRequestDoc.getSupplementRequest()
          .getDeskReviewInfo().getComments();
    }

    String values[] = {
        suppRequestDoc.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().getName(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getName(),
        SuppRequestUtils.formatDateToString(suppRequestDoc
            .getSupplementRequest().getAdminInfo().getSentDate()),
        suppRequestDoc.getSupplementRequest().getAdminInfo()
            .getClientEstimateId(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getInsuranceCo(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getClaimNumber(),
        // ^^^^^ ag11185 - SCR 3953
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getOwnerName(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getYear() + "",
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getMake(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getModel(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getColor(),
        deskReviewComments,
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getName(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getPhone(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getEmail(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getFax() };

    for (int i = 0; i < datas.length; i++) {
      // Replace the template with the actual data
      SuppRequestUtils.updateStringBuffer(buffer, prefix + datas[i] + suffix,
          values[i]);
    }

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("The Email text is " + buffer.toString());
    }
    return buffer.toString();
  }

}
