package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

import org.apache.xmlbeans.XmlException;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.cieca.bms.ApplicationInfoType;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument.VehicleDamageEstimateAddRq;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.AdminInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LaborLineType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.LineItemType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleItemType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SimpleTotalType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.VehicleInfoType;
import com.mitchell.schemas.compliance.LogsDocument;
import com.mitchell.schemas.estimate.AnnotatedEstimateDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestDocBuildr;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public class SupplementRequestDocBuildrTest {

    private final String ESTIMATE_FILE = "Estimate.xml";
    private final String ANNOTATE_FILE = "Annoation.xml";
    private final String ESTIMATE_FILE_MODIFIED = "Estimate_Modified.xml";
    private final String ANNOTATE_FILE_MODIFIED = "Annoation_Modified.xml";
    private final String ANNOTATE_FILE_SANS_PROFILE = "Annoation-Sans-Profile.xml";
    private final String GTMOTIVE_SAMPLE_DAMAGE_ESTIMATE_FILE = "GTMOTIVE_Sample_Damage_Estimate.xml";
    private final String DESJARDIN_ANNOTATE_FILE_FOR_GST_E = "DESJARDIN_ANNOTATE.xml";
    private final String DESJARDIN_ANNOTATE_FILE_FOR_GST_I = "DESJARDIN_ANNOTATE_FOR_GST_I.xml";
    private final String DESJARDIN_DAMAGE_ESTIMATE_FILE_FOR_GST_E = "DESJARDIN_SAMPLE_DAMAGE_ESTIMATE_FILE.xml";
    private final String DESJARDIN_DAMAGE_ESTIMATE_FILE_FOR_GST_I = "DESJARDIN_SAMPLE_DAMAGE_ESTIMATE_FILE_FOR_GST_I.xml";
    private final String DAMAGE_ESTIMATE_FILE_WITHOUT_GST = "BMS_Without_GST.xml";
    private final String ANNOTATE_FILE_WITHOUT_GST = "Annotation_without_GST.xml";
    
    private final String MIE_SAMPLE_DAMAGE_ESTIMATE_FILE = "MIE_Sample_Damage_Estimate.xml";
    
    private final String BUG_545799_ANNOTATION_FILE = "BUG-545799-Annotation.xml";
    private final String BUG_545799_COMPLIANCE_FILE = "BUG-545799-Compliance.xml";
    private final String BUG_545799_ESTIMATE_FILE = "BUG-545799-Estimate.xml";

    private final String REVIEWER_USER_INFO = "/userinfo.xml";
    private final String REVIEWER_USER_DETAILS = "/UserDetail.xml";
    private final String DESJARDIN_DAMAGE_ESTIMATE_FILE_WITH_NULL_OTHER_CHARGES = "DESJARDIN_SAMPLE_DAMAGE_ESTIMATE_FILE_NULL_GST_OTHER.xml";
    private final String DESJARDIN_ANNOTATE_FILE_WITH_NULL_OTHER_CHARGES = "DESJARDIN_SAMPLE_ANNOTATE_FILE_NULL_GST_OTHER.xml";


    
    @Test
    public void createNameAllNull() throws Exception {
        runCreateNameTest(null, null, null, "");
    }

    @Test
    public void createNameTest() throws Exception {
        runCreateNameTest("Ajay", "Kumar", "Garg", "Ajay Garg Kumar");
    }

    @Test
    public void createNameWithNoName() throws Exception {
        runCreateNameTest("", "", "", "");
    }

    @Test
    public void createNameWithoutSecondNameTest() throws Exception {
        runCreateNameTest("Ajay", "", "Garg", "Ajay Garg");
    }

    @Test
    public void getEstimateFormatTypeTest() throws Exception {
        SupplementRequestDocBuildrTest sup = new SupplementRequestDocBuildrTest();
        VehicleDamageEstimateAddRq vehicleDamageEstimateAddRq = sup.createSampleDamageEstimate();
        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        String result = Whitebox.invokeMethod(supplementRequestDocBuildr, "getEstimateFormatType", vehicleDamageEstimateAddRq);
        Assert.assertEquals("GTMOTIVE", result);
    }

    @Test
    public void populateSupplementRequestBug545799Test() throws XmlException, IOException, MitchellException {

        VehicleDamageEstimateAddRqDocument estimate = getDamageEstimateBug545799();
        AnnotatedEstimateDocument annotate = getAnnotatonBug545799();
        LogsDocument compliance = getComplianceBug545799();

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, compliance);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        Assert.assertEquals("CCC", supplementRequestDoc.getSupplementRequest().getEstimateType().toString());
    }
        
    @Test
    public void populateSupplementRequestMIESampleTest() throws XmlException, IOException, MitchellException {

        VehicleDamageEstimateAddRqDocument estimate = getGTMOTIVESampleDamageEstimate();
        AnnotatedEstimateDocument annotate = getAnnotatedEstimate();

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        Assert.assertEquals("GTMOTIVE", supplementRequestDoc.getSupplementRequest().getEstimateType().toString());
    }
    
    @Test
    public void populateSupplementRequestGTMOTIVESampleTest() throws XmlException, IOException, MitchellException {

        VehicleDamageEstimateAddRqDocument estimate = getMIESampleDamageEstimate();
        AnnotatedEstimateDocument annotate = getAnnotatedEstimate();

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        Assert.assertEquals("MIE", supplementRequestDoc.getSupplementRequest().getEstimateType().toString());
    }

    @Test
    public void populateSupplementRequestUltraMateTest() throws MitchellException, XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = getDamageEstimate();
        AnnotatedEstimateDocument annotate = getAnnotatedEstimateSansProfile();
        
        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        Assert.assertEquals("GTMOTIVE", supplementRequestDoc.getSupplementRequest().getEstimateType().toString());
    }
    
    @Test
    public void populateSupplementRequestTest() throws MitchellException, XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = getDamageEstimate();
        AnnotatedEstimateDocument annotate = getAnnotatedEstimate();
        
        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        Assert.assertEquals("GTMOTIVE", supplementRequestDoc.getSupplementRequest().getEstimateType().toString());

        // New Test Cases

        Assert.assertEquals(3, supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray().length);
        LineItemType lineItemType1 = supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray(0)
                        .getLineItem();

        // Line item 1 test
        Assert.assertEquals("$572.47", lineItemType1.getPreviousAmount());
        Assert.assertEquals("$152.43", lineItemType1.getAmount());
        Assert.assertEquals("-$420.04", lineItemType1.getAmountDelta());

        Assert.assertEquals("$87.50", lineItemType1.getLaborInfo().getID());
        Assert.assertEquals("$87.50", lineItemType1.getLaborInfo().getIDDelta());
        Assert.assertEquals("$0.00", lineItemType1.getLaborInfo().getPreviousID());

        Assert.assertEquals("$5.20", lineItemType1.getLaborInfo().getIDPercent());
        Assert.assertEquals("$5.20", lineItemType1.getLaborInfo().getIDPercentDelta());
        Assert.assertEquals("$0.00", lineItemType1.getLaborInfo().getPreviousIDPercent());

        // Line item 2 and 3
        LineItemType lineItemType2 = supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray(1)
                        .getLineItem();

        Assert.assertEquals("$87.50", lineItemType2.getLaborInfo().getLaborCost());
        Assert.assertEquals("$37.50", lineItemType2.getLaborInfo().getLaborCostDelta());
        Assert.assertEquals("$50.00", lineItemType2.getLaborInfo().getPreviousLaborCost());

        Assert.assertEquals("$95.00", lineItemType2.getPreviousAmount());
        Assert.assertEquals("$2167.07", lineItemType2.getAmount());
        Assert.assertEquals("$2072.07", lineItemType2.getAmountDelta());

        LineItemType lineItemType3 = supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray(2)
                        .getLineItem();

        Assert.assertEquals("$87.50", lineItemType3.getLaborInfo().getLaborCost());
        Assert.assertEquals("$86.50", lineItemType3.getLaborInfo().getLaborCostDelta());
        Assert.assertEquals("$1.00", lineItemType3.getLaborInfo().getPreviousLaborCost());

        Assert.assertEquals("$0.10", lineItemType3.getPreviousAmount());
        Assert.assertEquals("$526.18", lineItemType3.getAmount());
        Assert.assertEquals("$526.08", lineItemType3.getAmountDelta());

        VehicleInfoType vehicleInfo = supplementRequestDoc.getSupplementRequest().getVehicleInfo();
        Assert.assertEquals(String.valueOf(5897), vehicleInfo.getLicensePlate());
        
        /*
         * New Testcases
         */

        AdminInfoType adminInfoType = supplementRequestDoc.getSupplementRequest().getAdminInfo();
        Assert.assertEquals("243343", adminInfoType.getClaimId());
        Assert.assertEquals("01", adminInfoType.getSuffix());
        Assert.assertEquals("SUFFIX_LABEL", adminInfoType.getSuffixLabel());

        SupplementRequestType supplementRqType = supplementRequestDoc.getSupplementRequest();
        Assert.assertEquals("staticUrl", supplementRqType.getStaticImageBaseUrl());
        Assert.assertEquals("SIGNATURE_IMAGE", supplementRqType.getSignatureImage());
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        Assert.assertEquals(String.valueOf(currYear), supplementRqType.getCurrentYear());

    }

    /*
     * ADDED BY PRASANT SAHU FOR TESTCASE ON 410435.
     */
    @Test
    public void populateSupplementRequestTest_InNegative() throws MitchellException, XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = getModifiedDamageEstimate();
        AnnotatedEstimateDocument annotate = getModifedAnnotatedEstimate();

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);


        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        LineItemType lineItemType1 = supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray(0)
                        .getLineItem();

        // bo.setAnnotation(annotate);
        Assert.assertEquals("$0.00", lineItemType1.getPreviousAmount());
        Assert.assertEquals("$152.43", lineItemType1.getAmountDelta());
        Assert.assertEquals("$152.43", lineItemType1.getAmount());

        LineItemType lineItemType2 = supplementRequestDoc.getSupplementRequest().getLineItemsInfo().getLineItemInfoArray(1)
                        .getLineItem();
        Assert.assertEquals("$0.00", lineItemType2.getPreviousAmount());
        Assert.assertEquals("$0.00", lineItemType2.getAmountDelta());
        Assert.assertEquals("$0.00", lineItemType2.getAmount());

        VehicleInfoType vehicleInfo = supplementRequestDoc.getSupplementRequest().getVehicleInfo();
        Assert.assertEquals(String.valueOf(5897), vehicleInfo.getLicensePlate());

        /*
         * New Testcases
         */

        AdminInfoType adminInfoType = supplementRequestDoc.getSupplementRequest().getAdminInfo();
        Assert.assertEquals("243343", adminInfoType.getClaimId());
        Assert.assertEquals("01", adminInfoType.getSuffix());
        Assert.assertEquals("SUFFIX_LABEL", adminInfoType.getSuffixLabel());

        SupplementRequestType supplementRqType = supplementRequestDoc.getSupplementRequest();
        Assert.assertEquals("staticUrl", supplementRqType.getStaticImageBaseUrl());
        Assert.assertEquals("SIGNATURE_IMAGE", supplementRqType.getSignatureImage());

        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        Assert.assertEquals(String.valueOf(currYear), supplementRqType.getCurrentYear());

    }
    
    @Test
    public void test_populateSupplementRequest_For_AnnotationEmail_For_GST_E() throws XmlException, IOException, MitchellException {

        VehicleDamageEstimateAddRqDocument estimate = 
        		VehicleDamageEstimateAddRqDocument.Factory.parse(
        				readFileIntoString(this.getClass()
        						.getClassLoader().getResourceAsStream(DESJARDIN_DAMAGE_ESTIMATE_FILE_FOR_GST_E)));
        AnnotatedEstimateDocument annotate = 
        		AnnotatedEstimateDocument.Factory.parse(readFileIntoString(this.getClass()
						.getClassLoader().getResourceAsStream(DESJARDIN_ANNOTATE_FILE_FOR_GST_E)));

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        
        LaborLineType [] laborLineArray = supplementRequestDoc.getSupplementRequest().getLaborSubTotals().getLaborLineArray();
        boolean laborGSTETaxPresent = false;
        for (LaborLineType line : laborLineArray) {
        	if ("GST-E Tax".equalsIgnoreCase(line.getType())){
        		laborGSTETaxPresent = true;
        	} 
        }
        
        SimpleTotalType total = supplementRequestDoc.getSupplementRequest().getPartsSubTotals();
        SimpleItemType [] itemArray = total.getItemArray();
        boolean partsGSTETaxPresent = false;
        for (SimpleItemType item : itemArray) {
        	if ("GST-E Tax".equalsIgnoreCase(item.getName()) && "$240.00".equalsIgnoreCase(item.getValue())) {
        		partsGSTETaxPresent = true;
        		break;
        	}
        }
        SimpleTotalType otherTotal = supplementRequestDoc.getSupplementRequest().getOtherChargesSubtotals();
        SimpleItemType [] otherTotalItemArray = otherTotal.getItemArray();
        boolean otherGSTETaxPresent = false;
        for (SimpleItemType item : otherTotalItemArray) {
        	if ("GST-E Tax".equalsIgnoreCase(item.getName()) && "$23.00".equalsIgnoreCase(item.getValue())) {
        		otherGSTETaxPresent = true;
        		break;
        	}
        }
        if (!(laborGSTETaxPresent && partsGSTETaxPresent && otherGSTETaxPresent)) {
        	Assert.fail("test_populateSupplementRequest_For_AnnotationEmail_For_GST_E failed");
        }
    }
    
    @Test
    public void test_populateSupplementRequest_For_AnnotationEmail_For_GST_I() throws XmlException, IOException, MitchellException {

        VehicleDamageEstimateAddRqDocument estimate = 
        		VehicleDamageEstimateAddRqDocument.Factory.parse(
        				readFileIntoString(this.getClass()
        						.getClassLoader().getResourceAsStream(DESJARDIN_DAMAGE_ESTIMATE_FILE_FOR_GST_I)));
        AnnotatedEstimateDocument annotate = 
        		AnnotatedEstimateDocument.Factory.parse(readFileIntoString(this.getClass()
						.getClassLoader().getResourceAsStream(DESJARDIN_ANNOTATE_FILE_FOR_GST_I)));

        SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        supplementRequestDocBuildr.setCurrency("$");

        SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
        
        LaborLineType [] laborLineArray = supplementRequestDoc.getSupplementRequest().getLaborSubTotals().getLaborLineArray();
        boolean laborGSTITaxPresent = false;
        for (LaborLineType line : laborLineArray) {
        	if ("GST-I Tax".equalsIgnoreCase(line.getType())){
        		laborGSTITaxPresent = true;
        	} 
        }
        
        SimpleTotalType total = supplementRequestDoc.getSupplementRequest().getPartsSubTotals();
        SimpleItemType [] itemArray = total.getItemArray();
        boolean partsGSTITaxPresent = false;
        for (SimpleItemType item : itemArray) {
        	if ("GST-I Tax".equalsIgnoreCase(item.getName()) && "$20500.00".equalsIgnoreCase(item.getValue()) && "10.0%".equalsIgnoreCase(item.getRatePercentage())) {
        		partsGSTITaxPresent = true;
        		break;
        	}
        }
        SimpleTotalType otherTotal = supplementRequestDoc.getSupplementRequest().getOtherChargesSubtotals();
        SimpleItemType [] otherTotalItemArray = otherTotal.getItemArray();
        boolean otherGSTITaxPresent = false;
        for (SimpleItemType item : otherTotalItemArray) {
        	if ("GST-I Tax".equalsIgnoreCase(item.getName())) {
        		otherGSTITaxPresent = true;
        		break;
        	}
        }
        if (!(laborGSTITaxPresent && partsGSTITaxPresent && otherGSTITaxPresent)) {
        	Assert.fail("test_populateSupplementRequest_For_AnnotationEmail_For_GST_I failed");
        }
    }
    
  @Test
  public void test_populateSupplementRequest_For_AnnotationEmail_WIthout_GST() throws XmlException, IOException, MitchellException {

      VehicleDamageEstimateAddRqDocument estimate = 
      		VehicleDamageEstimateAddRqDocument.Factory.parse(
      				readFileIntoString(this.getClass()
      						.getClassLoader().getResourceAsStream(DAMAGE_ESTIMATE_FILE_WITHOUT_GST)));
      AnnotatedEstimateDocument annotate = 
      		AnnotatedEstimateDocument.Factory.parse(readFileIntoString(this.getClass()
						.getClassLoader().getResourceAsStream(ANNOTATE_FILE_WITHOUT_GST)));

      SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

      SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
      supplementRequestDocBuildr.setCurrency("$");

      SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
      
      LaborLineType [] laborLineArray = supplementRequestDoc.getSupplementRequest().getLaborSubTotals().getLaborLineArray();
      boolean laborGSTITaxPresent = false;
      for (LaborLineType line : laborLineArray) {
      	if ("GST-I Tax".equalsIgnoreCase(line.getType())){
      		laborGSTITaxPresent = true;
      	} 
      }
      
      SimpleTotalType total = supplementRequestDoc.getSupplementRequest().getPartsSubTotals();
      SimpleItemType [] itemArray = total.getItemArray();
      boolean partsGSTITaxPresent = false;
      for (SimpleItemType item : itemArray) {
      	if ("GST-I Tax".equalsIgnoreCase(item.getName())) {
      		partsGSTITaxPresent = true;
      		break;
      	}
      }
      SimpleTotalType otherTotal = supplementRequestDoc.getSupplementRequest().getOtherChargesSubtotals();
      SimpleItemType [] otherTotalItemArray = otherTotal.getItemArray();
      boolean otherGSTITaxPresent = false;
      for (SimpleItemType item : otherTotalItemArray) {
      	if ("GST-I Tax".equalsIgnoreCase(item.getName())) {
      		otherGSTITaxPresent = true;
      		break;
      	}
      }
      if ((laborGSTITaxPresent && partsGSTITaxPresent && otherGSTITaxPresent)) {
      	Assert.fail("test_populateSupplementRequest_For_AnnotationEmail_WIthout_GST failed");
      }
  }
  
  @Test
  public void test_populateSupplementRequest_For_AnnotationEmail_For_GST_E_With_NullBMSOtherChargesGST() throws XmlException, IOException, MitchellException {

      VehicleDamageEstimateAddRqDocument estimate = 
      		VehicleDamageEstimateAddRqDocument.Factory.parse(
      				readFileIntoString(this.getClass()
      						.getClassLoader().getResourceAsStream(DESJARDIN_DAMAGE_ESTIMATE_FILE_WITH_NULL_OTHER_CHARGES)));
      AnnotatedEstimateDocument annotate = 
      		AnnotatedEstimateDocument.Factory.parse(readFileIntoString(this.getClass()
						.getClassLoader().getResourceAsStream(DESJARDIN_ANNOTATE_FILE_WITH_NULL_OTHER_CHARGES)));

      SupplementReqBO bo = buildSupplementRequestBO(estimate, annotate, null);

      SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
      supplementRequestDocBuildr.setCurrency("$");

      SupplementRequestDocument supplementRequestDoc = supplementRequestDocBuildr.populateSupplementRequest(bo);
      
      LaborLineType [] laborLineArray = supplementRequestDoc.getSupplementRequest().getLaborSubTotals().getLaborLineArray();
      boolean laborGSTETaxPresent = false;
      for (LaborLineType line : laborLineArray) {
      	if ("GST-E Tax".equalsIgnoreCase(line.getType())){
      		laborGSTETaxPresent = true;
      	} 
      }
      
      SimpleTotalType total = supplementRequestDoc.getSupplementRequest().getPartsSubTotals();
      SimpleItemType [] itemArray = total.getItemArray();
      boolean partsGSTETaxPresent = false;
      for (SimpleItemType item : itemArray) {
      	if ("GST-E Tax".equalsIgnoreCase(item.getName())) {
      		partsGSTETaxPresent = true;
      		break;
      	}
      }
      SimpleTotalType otherTotal = supplementRequestDoc.getSupplementRequest().getOtherChargesSubtotals();
      SimpleItemType [] otherTotalItemArray = otherTotal.getItemArray();
      boolean otherGSTETaxPresent = false;
      for (SimpleItemType item : otherTotalItemArray) {
      	if ("GST-E Tax".equalsIgnoreCase(item.getName())) {
      		otherGSTETaxPresent = true;
      		break;
      	}
      }
      if (!(laborGSTETaxPresent && partsGSTETaxPresent && otherGSTETaxPresent)) {
      	Assert.fail("test_populateSupplementRequest_For_AnnotationEmail_For_GST_E_With_NullBMSOtherChargesGST failed");
      }
  }

    
    private void runCreateNameTest(String firstName, String secondLastName, String lastname, String expectedResult)
                    throws Exception {
        SupplementRequestDocBuildr supplementRequestDocBuildr = new SupplementRequestDocBuildr();
        String result = Whitebox.invokeMethod(supplementRequestDocBuildr, "createName", firstName, lastname, secondLastName);
        Assert.assertEquals(expectedResult, result);
    }

    private SupplementReqBO buildSupplementRequestBO(VehicleDamageEstimateAddRqDocument damageEstimate,
                    AnnotatedEstimateDocument annotatedEstimate, LogsDocument compliance) throws XmlException, IOException {
        SupplementReqBO bo = new SupplementReqBO();

        bo.setBMS(damageEstimate);
        bo.setAnnotation(annotatedEstimate);
        bo.setCompliance(compliance);
        
        bo.setReviewerUserInfo(getUserInfoFromFile());
        bo.setReviewerUserDetail(getUserDetailsFromFile());
        bo.setEstimatorUserDetail(getUserDetailsFromFile());
        bo.setEstimatorUserInfo(getUserInfoFromFile());

        bo.setClaimNum("243343");
        bo.setSuffixNum("01");
        bo.setSuffixLabel("SUFFIX_LABEL");
        bo.setStaticBaseUrl("staticUrl");
        bo.setSignatureImage("SIGNATURE_IMAGE");

        return bo;
    }

    private VehicleDamageEstimateAddRq createSampleDamageEstimate() {

        VehicleDamageEstimateAddRq vehicleDamageEstimateAddRq = VehicleDamageEstimateAddRq.Factory.newInstance();
        ApplicationInfoType applicationInfoType1 = vehicleDamageEstimateAddRq.addNewApplicationInfo();
        applicationInfoType1.setApplicationType("Estimating");
        applicationInfoType1.setApplicationName("GTESTIMATE");
        applicationInfoType1.setApplicationVer("4.17.5197");

        ApplicationInfoType applicationInfoType2 = vehicleDamageEstimateAddRq.addNewApplicationInfo();
        applicationInfoType2.setApplicationType("Program");
        applicationInfoType2.setApplicationName("GTESTIMATE WEB");
        applicationInfoType2.setApplicationVer("4.17.5197");
        applicationInfoType2.setDatabaseVer("4.17.0");

        ApplicationInfoType applicationInfoType3 = vehicleDamageEstimateAddRq.addNewApplicationInfo();
        applicationInfoType3.setApplicationType("Conversion");
        applicationInfoType3.setApplicationName("GTTOCIECA");
        applicationInfoType3.setApplicationVer("1.2.5182");

        return vehicleDamageEstimateAddRq;

    }

    private VehicleDamageEstimateAddRqDocument getGTMOTIVESampleDamageEstimate() throws XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = VehicleDamageEstimateAddRqDocument.Factory.parse(
                        getResource(GTMOTIVE_SAMPLE_DAMAGE_ESTIMATE_FILE));
        return estimate;
    }
    private VehicleDamageEstimateAddRqDocument getMIESampleDamageEstimate() throws XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = VehicleDamageEstimateAddRqDocument.Factory.parse(
                        getResource(MIE_SAMPLE_DAMAGE_ESTIMATE_FILE));
        return estimate;
    }

    private VehicleDamageEstimateAddRqDocument getModifiedDamageEstimate() throws XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = VehicleDamageEstimateAddRqDocument.Factory.parse(
                        getResource(ESTIMATE_FILE_MODIFIED));
        return estimate;
    }

    private AnnotatedEstimateDocument getModifedAnnotatedEstimate() throws XmlException, IOException {
        AnnotatedEstimateDocument annotate = AnnotatedEstimateDocument.Factory.parse(getResource(ANNOTATE_FILE_MODIFIED));
        return annotate;
    }

    private AnnotatedEstimateDocument getAnnotatedEstimate() throws XmlException, IOException {
        AnnotatedEstimateDocument annotate = AnnotatedEstimateDocument.Factory.parse(getResource(ANNOTATE_FILE));
        return annotate;
    }

    private AnnotatedEstimateDocument getAnnotatedEstimateSansProfile() throws XmlException, IOException {
        AnnotatedEstimateDocument annotate = AnnotatedEstimateDocument.Factory.parse(getResource(ANNOTATE_FILE_SANS_PROFILE));
        return annotate;
    }

    private VehicleDamageEstimateAddRqDocument getDamageEstimate() throws XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = VehicleDamageEstimateAddRqDocument.Factory.parse(getResource(ESTIMATE_FILE));
        return estimate;
    }

    private VehicleDamageEstimateAddRqDocument getDamageEstimateBug545799() throws XmlException, IOException {
        VehicleDamageEstimateAddRqDocument estimate = VehicleDamageEstimateAddRqDocument.Factory.parse(getResource(BUG_545799_ESTIMATE_FILE));
        return estimate;
    }
 
    private AnnotatedEstimateDocument getAnnotatonBug545799() throws XmlException, IOException {
        AnnotatedEstimateDocument annotation = AnnotatedEstimateDocument.Factory.parse(getResource(BUG_545799_ANNOTATION_FILE));
       return annotation;
    }
 
   private LogsDocument getComplianceBug545799() throws XmlException, IOException {
        LogsDocument compliance = LogsDocument.Factory.parse(getResource(BUG_545799_COMPLIANCE_FILE));
        return compliance;
    }
 
    private UserDetailDocument getUserDetailsFromFile() throws XmlException, IOException {
        return UserDetailDocument.Factory.parse(getResource(REVIEWER_USER_DETAILS));
    }

    private UserInfoDocument getUserInfoFromFile() throws XmlException, IOException {
        UserInfoDocument userInfo = UserInfoDocument.Factory.parse(getResource(REVIEWER_USER_INFO));
        return userInfo;
    }

    private File getResource(String reourceName) throws NullPointerException {
        URL url = this.getClass().getResource(reourceName);
        return new File(url.getFile());
    }
    
    public static String readFileIntoString(InputStream inputStream)
			throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuffer strMsg = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			strMsg.append(line);
		}
		return strMsg.toString();
	}
    

}
