package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mitchell.schemas.appraisalassignment.supplementrequestemail.HourInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineTypeTyp;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.OperationLineDescType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.RateInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.StringUtilities;

public class CCCBMSTextEmailFormatter
{
  //Commented to resolve the codepro comments.
  //private static final Logger logger = Logger.getLogger(BMSTextEmailFormatter.class.getName());

  private String currentClaimText = null;
  private String relatedPDText = null;
  private String unrelatedPDText = null;

  /**
   * Create the text email for MIE BMS
   * 
   * @throws IllegalAccessException
   */
  public String createTextEmail(SupplementRequestDocument suppRequestDoc)
      throws IOException, IllegalAccessException
  {

    String emailBody = null;
    AASCommonUtils aasCommonUtil = (AASCommonUtils) BeanLocator
        .getBean("AASCommonUtils");

    String file = aasCommonUtil.getSystemConfigurationSettingValue(
        "/AppraisalAssignment/SuppRequest/TextFormat/MIE/CCCTemplateFile",
        "CCC_BMS_Supp_Request_Template.txt");
    file = SuppRequestSystemConfig.getTemplateBaseDir()
        + java.io.File.separator + file;
    String propsList = aasCommonUtil
        .getSystemConfigurationSettingValue(
            "/AppraisalAssignment/SuppRequest/TextFormat/MIE/CCCTemplateProps",
            "APPRAISER_NAME,NAME,SUPPLEMENT_DATE,CLAIM_NUMBER,COMPANY_NAME,EXTERNAL_ESTIMATE_ID,OWNERNAME,YEAR,MAKE,MODEL,COLOR,SUPPLEMENT_COMMENTS,RECOMMENDED_CHANGES_TEMPLATE,PRIOR_DAMAGE_CHANGES_TEMPLATE,SUPPLEMENT_INSTRUCTIONS_STEPS,NAME,TELEPHONE,EMAIL,FAX,ESTIMATE_PROFILE,PARTS_SUBTOTALS,LABOR_SUBTOTALS,MATERIALS_SUBTOTALS,OTHER_CHARGES_SUBTOTALS,TAXES,GROSS_TOTAL,ADJUSTMENTS,NET_TOTAL");
    String prefix = SuppRequestSystemConfig.getTextTemplatePrefix();
    String suffix = SuppRequestSystemConfig.getTextTemplateSuffix();

    // Get the Template Data        
    String templateData = FileUtils.readFileIntoString(file);

    // Get the list of properties
    String[] datas = propsList.split(",");

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
            .getFax(), this.getEstimateProfile(suppRequestDoc),
        this.getPartsSubtotals(suppRequestDoc),
        this.getLaborSubtotals(suppRequestDoc),
        this.getMaterialsSubtotals(suppRequestDoc),
        this.getOtherChargesSubtotals(suppRequestDoc),
        this.getTaxes(suppRequestDoc), this.getGrossTotal(suppRequestDoc),
        this.getAdjustments(suppRequestDoc), this.getNetTotal(suppRequestDoc) };

    int len = Math.min(datas.length, values.length);

    for (int i = 0; i < len; i++) {
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
    lineItemBuffer.append("Line Item Annotations :\n\n");
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
    String partNumber = " ";

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

        if (lineItem.getLineItem().getPartInfo().isSetPreviousPartQuantity()) {
          partQuantity = "(Previous:"
              + lineItem.getLineItem().getPartInfo().getPreviousPartQuantity()
              + ") ";
          partQuantity += lineItem.getLineItem().getPartInfo()
              .getPartQuantity();
        } else {
          partQuantity = lineItem.getLineItem().getPartInfo().getPartQuantity();
        }
        if (lineItem.getLineItem().getPartInfo().isSetPartQuantityDelta()) {
          partQuantity += " (Delta:"
              + lineItem.getLineItem().getPartInfo().getPartQuantityDelta()
              + ")";
        }
      }

      if (lineItem.getLineItem().getPartInfo().isSetUnitPartPrice()
          && isValidNonZeroNumber(lineItem.getLineItem().getPartInfo()
              .getUnitPartPrice())) {
        //                unitPartPrice = "$" + lineItem.getLineItem().getPartInfo().getUnitPartPrice();
        if (lineItem.getLineItem().getPartInfo().isSetPreviousUnitPartPrice()) {
          unitPartPrice = "(Previous:"
              + lineItem.getLineItem().getPartInfo().getPreviousUnitPartPrice()
              + ") ";
          unitPartPrice += lineItem.getLineItem().getPartInfo()
              .getUnitPartPrice();
        } else {
          unitPartPrice = lineItem.getLineItem().getPartInfo()
              .getUnitPartPrice();
        }
        if (lineItem.getLineItem().getPartInfo().isSetUnitPartPriceDelta()) {
          unitPartPrice += " (Delta:"
              + lineItem.getLineItem().getPartInfo().getUnitPartPriceDelta()
              + ")";
        }
      }

      if (lineItem.getLineItem().getPartInfo().isSetPartPrice()
          && isValidNonZeroNumber(lineItem.getLineItem().getPartInfo()
              .getPartPrice())) {
        //                partPrice = "$" + lineItem.getLineItem().getPartInfo().getPartPrice().toString();
        if (lineItem.getLineItem().getPartInfo().isSetPreviousPartPrice()) {
          partPrice = "(Previous:"
              + lineItem.getLineItem().getPartInfo().getPreviousPartPrice()
              + ") ";
          partPrice += lineItem.getLineItem().getPartInfo().getPartPrice();
        } else {
          partPrice = lineItem.getLineItem().getPartInfo().getPartPrice();
        }
        if (lineItem.getLineItem().getPartInfo().isSetPartPriceDelta()) {
          partPrice += " (Delta:"
              + lineItem.getLineItem().getPartInfo().getPartPriceDelta() + ")";
        }
      }

      if (lineItem.getLineItem().getPartInfo().isSetPartNumber()) {
        partNumber = lineItem.getLineItem().getPartInfo().getPartNumber();
      }
    }

    // check the labor information for laborHours, laborCost
    if (lineItem.getLineItem().isSetLaborInfo()) {

      if ((lineItem.getLineItem().getLaborInfo().isSetLaborHours() && isValidNonZeroNumber(lineItem
          .getLineItem().getLaborInfo().getLaborHours()))
          || (lineItem.getLineItem().getLaborInfo().isSetLaborHoursDelta() && isValidNonZeroNumber(lineItem
              .getLineItem().getLaborInfo().getLaborHoursDelta()))) {

        if (lineItem.getLineItem().getLaborInfo().isSetPreviousLaborHours()) {
          laborHours = "(Previous:"
              + lineItem.getLineItem().getLaborInfo().getPreviousLaborHours()
              + ") ";
          laborHours += lineItem.getLineItem().getLaborInfo().getLaborHours();
        } else {
          laborHours = lineItem.getLineItem().getLaborInfo().getLaborHours();
        }

        if (lineItem.getLineItem().getLaborInfo().isSetLaborHoursDelta()) {
          laborHours += " (Delta:"
              + lineItem.getLineItem().getLaborInfo().getLaborHoursDelta()
              + ")";
        }
      } else {
        laborHours = "0.0";
      }

      if ((lineItem.getLineItem().getLaborInfo().isSetLaborCost() && isValidNonZeroNumber(lineItem
          .getLineItem().getLaborInfo().getLaborCost()))
          || (lineItem.getLineItem().getLaborInfo().isSetLaborCostDelta() && isValidNonZeroNumber(lineItem
              .getLineItem().getLaborInfo().getLaborCostDelta()))) {
        //                laborCost = "$" + lineItem.getLineItem().getLaborInfo().getLaborCost();
        if (lineItem.getLineItem().getLaborInfo().isSetPreviousLaborCost()) {
          laborCost = "(Previous:"
              + lineItem.getLineItem().getLaborInfo().getPreviousLaborCost()
              + ") ";
          laborCost += lineItem.getLineItem().getLaborInfo().getLaborCost();
        } else {
          laborCost = lineItem.getLineItem().getLaborInfo().getLaborCost();
        }

        if (lineItem.getLineItem().getLaborInfo().isSetLaborCostDelta()) {
          laborCost += " (Delta:"
              + lineItem.getLineItem().getLaborInfo().getLaborCostDelta() + ")";
        }
      } else {
        laborCost = "$0.00";
      }
    } else {
      laborHours = "0.0";
      laborCost = "$0.00";
    }
    // amount
    if (!StringUtilities.isEmpty(lineItem.getLineItem().getAmount())) {
      //            amount = "$" + lineItem.getLineItem().getAmount();
      if (lineItem.getLineItem().isSetPreviousAmount()) {
        amount = "(Previous:" + lineItem.getLineItem().getPreviousAmount()
            + ") ";
        amount += lineItem.getLineItem().getAmount();
      } else {
        amount = lineItem.getLineItem().getAmount();
      }

      if (lineItem.getLineItem().isSetAmountDelta()) {
        amount += " (Delta:" + lineItem.getLineItem().getAmountDelta() + ")";
      }
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
    map.put("PN", partNumber);

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

      // Part Number
      lineBuffer.append("Part Number: ");
      lineBuffer.append(lineMap.get("PN"));
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

  private String getEstimateProfile(SupplementRequestDocument suppRequestDoc)
  {
    String part = "";

    if (suppRequestDoc != null && suppRequestDoc.getSupplementRequest() != null
        && suppRequestDoc.getSupplementRequest().getEstimateProfile() != null) {

      String title = "Estimate Profile:";

      if (suppRequestDoc.getSupplementRequest().getEstimateProfile()
          .isSetProfileName()) {
        title += suppRequestDoc.getSupplementRequest().getEstimateProfile()
            .getProfileName();
      }
      title += "\n";

      String head = "Labor Rate ($/hr)\n";

      String rateLine = "";
      if (suppRequestDoc.getSupplementRequest().getEstimateProfile()
          .getRateInfoArray() != null) {
        for (int i = 0; i < suppRequestDoc.getSupplementRequest()
            .getEstimateProfile().getRateInfoArray().length; i++) {

          rateLine += suppRequestDoc.getSupplementRequest()
              .getEstimateProfile().getRateInfoArray(i).getType();
          rateLine += ":\t";

          if (suppRequestDoc.getSupplementRequest().getEstimateProfile()
              .getRateInfoArray(i).isSetPreviousRate()) {
            rateLine += "(Previous Rate:"
                + suppRequestDoc.getSupplementRequest().getEstimateProfile()
                    .getRateInfoArray(i).getPreviousRate() + ")";
            rateLine += "\t";
          }
          rateLine += suppRequestDoc.getSupplementRequest()
              .getEstimateProfile().getRateInfoArray(i).getRate();
          rateLine += "\t";

          if (suppRequestDoc.getSupplementRequest().getEstimateProfile()
              .getRateInfoArray(i).isSetRateDelta()) {
            rateLine += "(Delta:"
                + suppRequestDoc.getSupplementRequest().getEstimateProfile()
                    .getRateInfoArray(i).getRateDelta() + ")";
            rateLine += "\t";
          }
          rateLine += "\n";
        }
      }

      part = title + head + rateLine;
    }

    return part;
  }

  private String getSubtotal(String type, SimpleTotalType totalType)
  {

    String part = "";
    if (totalType != null) {

      part = type + ": ";
      if (totalType.isSetPreviousTotal()
          && totalType.getPreviousTotal() != null) {

        part += "(Previous:" + totalType.getPreviousTotal() + ") ";

      }

      if (totalType.getTotal() != null)
        part += totalType.getTotal();

      if (totalType.isSetTotalDelta() && totalType.getTotalDelta() != null) {
        part += " (Delta:" + totalType.getTotalDelta() + ")";
      }

      part += "\n";

      if (totalType.getItemArray() != null) {

        for (int i = 0; i < totalType.getItemArray().length; i++) {
          part += "\t";
          if (totalType.getItemArray(i).getName() != null)
            part += totalType.getItemArray(i).getName();
          if (totalType.getItemArray(i).getPreviousValue() != null)
            part += "(Previous:" + totalType.getItemArray(i).getPreviousValue()
                + ") ";
          ;
          if (totalType.getItemArray(i).getValue() != null)
            part += totalType.getItemArray(i).getValue();
          if (totalType.getItemArray(i).getValueDelta() != null)
            part += " (Delta:" + totalType.getItemArray(i).getValueDelta()
                + ")";
          part += "\n";
        }
      }
    }

    return part;
  }

  private String getRateInfo(RateInfoType rateInfoType)
  {

    String part = "";
    if (rateInfoType != null) {

      if (rateInfoType.isSetPreviousRate()) {

        part += "(Previous:" + rateInfoType.getPreviousRate() + ") ";
      }

      part += rateInfoType.getRate();

      if (rateInfoType.isSetRateDelta()) {
        part += " (Delta:" + rateInfoType.getRateDelta() + ")";
      }
    }

    return part;
  }

  private String getHourInfo(HourInfoType hourInfoType)
  {

    String part = "";
    if (hourInfoType != null) {

      if (hourInfoType.isSetPreviousHour()) {

        part += "(Previous:" + hourInfoType.getPreviousHour() + ") ";
      }

      part += hourInfoType.getHour();

      if (hourInfoType.isSetHourDelta()) {
        part += " (Delta:" + hourInfoType.getHourDelta() + ")";
      }
    }

    return part;
  }

  private String getPartsSubtotals(SupplementRequestDocument suppRequestDoc)
  {
    return getSubtotal("Parts SubTotals", suppRequestDoc.getSupplementRequest()
        .getPartsSubTotals());
  }

  private String getMaterialsSubtotals(SupplementRequestDocument suppRequestDoc)
  {

    return getSubtotal("Materials SubTotals", suppRequestDoc
        .getSupplementRequest().getMaterialsSubtotals());
  }

  private String getOtherChargesSubtotals(
      SupplementRequestDocument suppRequestDoc)
  {

    return getSubtotal("Other Charges SubTotals", suppRequestDoc
        .getSupplementRequest().getOtherChargesSubtotals());
  }

  private String getTaxes(SupplementRequestDocument suppRequestDoc)
  {

    return getSubtotal("Taxes", suppRequestDoc.getSupplementRequest()
        .getTaxes());
  }

  private String getGrossTotal(SupplementRequestDocument suppRequestDoc)
  {

    return getSubtotal("Gross Total", suppRequestDoc.getSupplementRequest()
        .getGrossTotal());
  }

  private String getAdjustments(SupplementRequestDocument suppRequestDoc)
  {

    return getSubtotal("Adjustments", suppRequestDoc.getSupplementRequest()
        .getAdjustments());
  }

  private String getNetTotal(SupplementRequestDocument suppRequestDoc)
  {
    return getSubtotal("Net Total", suppRequestDoc.getSupplementRequest()
        .getNetTotal());
  }

  private String getLaborSubtotals(SupplementRequestDocument suppRequestDoc)
  {

    String part = "";
    if (suppRequestDoc.getSupplementRequest().getLaborSubTotals() != null) {

      part = getSubtotal("Labor SubTotals", suppRequestDoc
          .getSupplementRequest().getLaborSubTotals().getLaborCost());

      part += "\n";

      if (suppRequestDoc.getSupplementRequest().getLaborSubTotals()
          .getLaborLineArray() != null) {

        for (int i = 0; i < suppRequestDoc.getSupplementRequest()
            .getLaborSubTotals().getLaborLineArray().length; i++) {
          part += "\t";
          part += suppRequestDoc.getSupplementRequest().getLaborSubTotals()
              .getLaborLineArray(i).getType()
              + ":";
          part += " Hours:"
              + getHourInfo(suppRequestDoc.getSupplementRequest()
                  .getLaborSubTotals().getLaborLineArray(i).getHours());
          part += " Rate: "
              + getRateInfo(suppRequestDoc.getSupplementRequest()
                  .getLaborSubTotals().getLaborLineArray(i).getRate());
          part += getSubtotal(" Total", suppRequestDoc.getSupplementRequest()
              .getLaborSubTotals().getLaborLineArray(i).getTotal());
          part += "\n";
        }
      }
    }

    return part;
  }
}
