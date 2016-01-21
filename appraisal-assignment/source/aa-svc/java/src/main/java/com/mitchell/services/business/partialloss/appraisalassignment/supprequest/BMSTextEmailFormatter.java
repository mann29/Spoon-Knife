package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineTypeTyp;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.OperationLineDescType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.StringUtilities;

public class BMSTextEmailFormatter
{
  private static final Logger logger = Logger
      .getLogger(BMSTextEmailFormatter.class.getName());

  String currentClaimText = null;
  String relatedPDText = null;
  String unrelatedPDText = null;

  /**
   * Create the text email for MIE BMS
   */
  String createTextEmail(SupplementRequestDocument suppRequestDoc)
      throws IOException
  {

    // Get the template data

    String emailBody = null;

    String file = SuppRequestSystemConfig.getMieTextTemplateFile();
    String propsList = SuppRequestSystemConfig.getMieTextTemplateProperties();
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

    String comments = " ";
    if (suppRequestDoc.getSupplementRequest().isSetDeskReviewInfo()) {
      comments = suppRequestDoc.getSupplementRequest().getDeskReviewInfo()
          .getComments();
    }

    // populate the line item
    this.poulateDamageLinesAnnotation(suppRequestDoc);

    // get the company name
    String companyName = null;

    String values[] = {
        suppRequestDoc.getSupplementRequest().getAdminInfo()
            .getReceipientInfo().getName(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getSenderInfo()
            .getName(),
        SuppRequestUtils.formatDateToString(suppRequestDoc
            .getSupplementRequest().getAdminInfo().getSentDate()),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getClaimNumber(),
        suppRequestDoc.getSupplementRequest().getAdminInfo().getInsuranceCo(),
        suppRequestDoc.getSupplementRequest().getAdminInfo()
            .getClientEstimateId(),
        // ^^^^^ ag11185 - SCR 3953
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getOwnerName(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getYear() + "",
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getMake(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getModel(),
        suppRequestDoc.getSupplementRequest().getVehicleInfo().getColor(),
        comments,
        currentClaimText, //implemented Recommended changes template
        getPriorDamage(), //implemented related & unrelated prior damage
        getSupplementInstructions(suppRequestDoc), //implemented supplement Instructions steps
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
    return buffer.toString();
  }

  /**
   * WCR 1.3 - Create the related and unrelated prior damage
   */
  private void poulateDamageLinesAnnotation(
      SupplementRequestDocument suppRequestDoc)
      throws IOException
  {

    ArrayList relatedPDList = new ArrayList();
    ArrayList unrelatedPDList = new ArrayList();
    ArrayList currentClaimList = new ArrayList();

    String lineType = null;

    if (suppRequestDoc.getSupplementRequest().isSetLineItemsInfo()) {

      LineItemInfoType[] lineItems = suppRequestDoc.getSupplementRequest()
          .getLineItemsInfo().getLineItemInfoArray();
      LineItemInfoType lineItem = null;

      for (int i = 0; i < lineItems.length; i++) {
        lineItem = lineItems[i];

        // check if the line type is related or unrelated prior damage
        if (lineItem.getLineItem().isSetLineType()
            && lineItem.getLineItem().getLineType() != null) {
          lineType = lineItem.getLineItem().getLineType().toString();

          if (lineType.equals(LineTypeTyp.RELATED.toString())) {
            relatedPDList.add(lineItem);
          } else if (lineType.equals(LineTypeTyp.UNRELATED.toString())) {
            unrelatedPDList.add(lineItem);
          } else {
            currentClaimList.add(lineItem);
          }
        } else {
          currentClaimList.add(lineItem);
        }
      }

      // Get the related Prior Damage changes
      if (relatedPDList.size() > 0) {
        relatedPDText = this.processLineItems(relatedPDList);
      }

      // Add the unrelated Prior Damage
      if (unrelatedPDList.size() > 0) {
        unrelatedPDText = this.processLineItems(unrelatedPDList);
      }

      // Add the unrelated Prior Damage
      if (currentClaimList.size() > 0) {
        currentClaimText = this.processLineItems(currentClaimList);
      }
    }

  }

  private String processLineItems(ArrayList list)
  {

    LineItemInfoType lineItem = null;
    StringBuffer lineItemBuffer = new StringBuffer();

    if (list.size() > 0) {

      for (int i = 0; i < list.size(); i++) {
        lineItem = (LineItemInfoType) list.get(i);
        String temp = processLineItem(lineItem);
        lineItemBuffer.append(temp);
        lineItemBuffer.append("\n");
      }
    }

    return lineItemBuffer.toString();

  }

  private String processLineItem(LineItemInfoType lineItem)
  {

    StringBuffer lineItemBuffer = new StringBuffer();

    String partType = " ";
    String partQuantity = " ";
    String unitPartPrice = " ";
    String partPrice = " ";
    String laborHours = " ";
    String laborCost = " ";
    String amount = " ";
    String operation = " ";
    String description = " ";
    String commentCode[] = null;
    String commentDescription[] = null;

    // check the operation
    if (!StringUtilities.isEmpty(lineItem.getLineItem().getOperation())) {
      operation = lineItem.getLineItem().getOperation();
    }

    // check the description
    if (!StringUtilities.isEmpty(lineItem.getLineItem().getLineDesc())) {
      description = lineItem.getLineItem().getLineDesc();
    }

    // check the parts information for partPrice, partType, partQuantity, unitPartPrice
    if (lineItem.getLineItem().isSetPartInfo()) {

      if (lineItem.getLineItem().getPartInfo().isSetPartType()
          && lineItem.getLineItem().getPartInfo().getPartType() != null) {
        partType = lineItem.getLineItem().getPartInfo().getPartType();
      }

      // WCR 1.3 - Added part Quantity and unit part price
      if (lineItem.getLineItem().getPartInfo().isSetPartQuantity()
          && isValidNonZeroNumber(lineItem.getLineItem().getPartInfo()
              .getPartQuantity())) {
        partQuantity = lineItem.getLineItem().getPartInfo().getPartQuantity();
      }

      if (lineItem.getLineItem().getPartInfo().isSetUnitPartPrice()
          && isValidNonZeroNumber(lineItem.getLineItem().getPartInfo()
              .getUnitPartPrice())) {
        unitPartPrice = "$"
            + lineItem.getLineItem().getPartInfo().getUnitPartPrice();
      }

      if (lineItem.getLineItem().getPartInfo().isSetPartPrice()
          && isValidNonZeroNumber(lineItem.getLineItem().getPartInfo()
              .getPartPrice())) {
        partPrice = "$"
            + lineItem.getLineItem().getPartInfo().getPartPrice().toString();
      }
    }

    // check the labor information for laborHours, laborCost
    if (lineItem.getLineItem().isSetLaborInfo()) {

      if (lineItem.getLineItem().getLaborInfo().isSetLaborHours()
          && isValidNonZeroNumber(lineItem.getLineItem().getLaborInfo()
              .getLaborHours())) {
        laborHours = lineItem.getLineItem().getLaborInfo().getLaborHours() + "";
      }

      if (lineItem.getLineItem().getLaborInfo().isSetLaborCost()
          && isValidNonZeroNumber(lineItem.getLineItem().getLaborInfo()
              .getLaborCost())) {
        laborCost = "$" + lineItem.getLineItem().getLaborInfo().getLaborCost();
      }
    }
    // amount
    if (!StringUtilities.isEmpty(lineItem.getLineItem().getAmount())) {
      amount = "$" + lineItem.getLineItem().getAmount();
    }

    OperationLineDescType[] annotations = lineItem.getAnnotationArray();
    OperationLineDescType annotation = null;

    if (annotations != null && annotations.length > 0) {

      commentCode = new String[annotations.length];
      commentDescription = new String[annotations.length];

      for (int k = 0; k < annotations.length; k++) {
        annotation = annotations[k];
        commentCode[k] = annotation.getLineDesc();
        commentDescription[k] = annotation.getOperation();
      }
    }

    HashMap map = new HashMap();

    map.put("LI", lineItem.getLineItem().getLineNum() + "");
    map.put("CR", lineItem.getLineItem().getChangeRecommended());
    map.put("OP", operation);
    map.put("DE", description);
    map.put("PT", partType);
    map.put("PQ", partQuantity);
    map.put("PU", unitPartPrice);
    map.put("PP", partPrice);
    map.put("LH", laborHours);
    map.put("LC", laborCost);
    map.put("AMT", amount);
    map.put("CC", commentCode);
    map.put("CD", commentDescription);

    lineItemBuffer.append(populateLineItemText(map));
    //lineItemBuffer.append("\n");

    return lineItemBuffer.toString();
  }

  private StringBuffer populateLineItemText(HashMap lineMap)
  {

    StringBuffer lineBuffer = new StringBuffer();

    if (lineMap != null && lineMap.size() > 0) {

      // line
      lineBuffer.append("Line: ");
      lineBuffer.append(lineMap.get("LI"));
      lineBuffer.append("\n");

      // Change Recomended
      lineBuffer.append("Change recommended: ");
      lineBuffer.append(lineMap.get("CR"));
      lineBuffer.append("\n");

      // Operation
      lineBuffer.append("Operation: ");
      lineBuffer.append(lineMap.get("OP"));
      lineBuffer.append("\n");

      // Description
      lineBuffer.append("Description: ");
      lineBuffer.append(lineMap.get("DE"));
      lineBuffer.append("\n");

      // Parts Type
      lineBuffer.append("Parts Type: ");
      lineBuffer.append(lineMap.get("PT"));
      lineBuffer.append("\n");

      // Parts Quantity for CCC & Audatex
      lineBuffer.append("Parts Quantity: ");
      lineBuffer.append(lineMap.get("PQ"));
      lineBuffer.append("\n");

      // Unit PartPrice for CCC & Audatex
      lineBuffer.append("Unit Part Price: ");
      lineBuffer.append(lineMap.get("PU"));
      lineBuffer.append("\n");

      // Parts Price
      lineBuffer.append("Parts Price: ");
      lineBuffer.append(lineMap.get("PP"));
      lineBuffer.append("\n");

      // Labor Hours
      lineBuffer.append("Labor Hours: ");
      lineBuffer.append(lineMap.get("LH"));
      lineBuffer.append("\n");

      // Labor Cost
      lineBuffer.append("Labor Cost: ");
      lineBuffer.append(lineMap.get("LC"));
      lineBuffer.append("\n");

      // Amount
      lineBuffer.append("Amount: ");
      lineBuffer.append(lineMap.get("AMT"));
      lineBuffer.append("\n");

      // Get the comments array
      String[] comments = null;
      String[] operations = null;

      if (lineMap.get("CC") != null) {
        comments = (String[]) lineMap.get("CC");
      }

      if (lineMap.get("CD") != null) {
        operations = (String[]) lineMap.get("CD");
      }

      if (comments != null && operations != null) {
        for (int l = 0; l < comments.length; l++) {

          // Comment code
          lineBuffer.append("Comment Code: ");
          lineBuffer.append(comments[l]);
          lineBuffer.append("\n");

          // Comment description
          lineBuffer.append("Comment Description: ");
          lineBuffer.append(operations[l]);
          lineBuffer.append("\n");
        }
      } else {
        // Comment code
        lineBuffer.append("Comment Code: ");
        lineBuffer.append(" ");
        lineBuffer.append("\n");

        // Comment description
        lineBuffer.append("Comment Description: ");
        lineBuffer.append(" ");
        lineBuffer.append("\n");
      }
    }
    return lineBuffer;
  }

  /**
   * WCR 1.3 - Create Supplement Instructions templates for MIE BMS or
   * CCC/Audatex BMS
   */
  private String getSupplementInstructions(
      SupplementRequestDocument suppRequestDoc)
      throws IOException
  {

    String templateData = null;

    if ("MIE".equals(suppRequestDoc.getSupplementRequest().getEstimateType()
        .toString())) {

      String file = SuppRequestSystemConfig
          .getMieSuppInstructionsTextTemplateFile();
      templateData = FileUtils.readFileIntoString(file);

    } else if ("CCC".equals(suppRequestDoc.getSupplementRequest()
        .getEstimateType().toString())
        || "AUDATEX".equals(suppRequestDoc.getSupplementRequest()
            .getEstimateType().toString())) {

      String file = SuppRequestSystemConfig
          .getCCCAudatexSuppInstructionsTextTemplateFile();
      templateData = FileUtils.readFileIntoString(file);
    }

    return templateData;
  }

  private String getPriorDamage()
  {

    StringBuffer lineItemBuffer = new StringBuffer("");

    if (relatedPDText != null && relatedPDText.length() > 0) {
      lineItemBuffer.append("Related Prior Damage Recommended Changes: \n");
      lineItemBuffer.append(relatedPDText);
    }

    if (unrelatedPDText != null && unrelatedPDText.length() > 0) {
      lineItemBuffer.append("Unrelated Prior Damage Recommended Changes: \n");
      lineItemBuffer.append(unrelatedPDText);
    }

    return lineItemBuffer.toString();
  }

  private boolean isValidNonZeroNumber(String value)
  {

    boolean status = true;

    if (value != null && value.length() > 0) {
      if (("0".equals(value)) || ("0.00".equals(value))
          || ("0.0".equals(value))) {
        status = false;
      }
    } else {
      status = false;
    }

    return status;
  }

}
