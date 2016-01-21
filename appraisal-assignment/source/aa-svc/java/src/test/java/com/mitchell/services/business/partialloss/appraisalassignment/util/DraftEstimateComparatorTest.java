package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.junit.Test;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public class DraftEstimateComparatorTest {

	
	@Test
	public void compareTestSupplementNumLesserValCollaborative() throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(1L);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(-1, retVal);
	}
	
	@Test
	public void compareTestSupplementNumMaxValCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(1L);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNum1NullSupplementNum2NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(null);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(null);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(0, retVal);		
	}
	
	@Test
	public void compareTestSupplementNum1NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(null);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(-1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNum2NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(null);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNumEqualLessDocRevValCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(1);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(-1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNumEqualMaxDocRevValCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(1);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNumEqualDocRevVal1NullDocRevVal2NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(null);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(null);
		int retVal = estCompTest.compare(estimate1, estimate2);
		
		Assert.assertEquals(0, retVal);		
	}
	
	@Test
	public void compareTestSupplementNumEqualDocRevVal1NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(null);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(0);
		int retVal = estCompTest.compare(estimate1, estimate2);
				
		Assert.assertEquals(1, retVal);		
	}
	
	@Test
	public void compareTestSupplementNumEqualDocRevVal2NullCollaborative()throws Exception{
		
		DraftEstimateComparator estCompTest = new DraftEstimateComparator();
		Estimate estimate1 = getTestEstimate();
		estimate1.setSupplementNumber(0L);
		estimate1.setEstimateDocRevision(0);
		
		Estimate estimate2 = getTestEstimate();
		estimate2.setSupplementNumber(0L);
		estimate2.setEstimateDocRevision(null);
		int retVal = estCompTest.compare(estimate1, estimate2);
				
		Assert.assertEquals(-1, retVal);		
	}


	private Estimate getTestEstimate() {
		Estimate est = new Estimate();

		est.setCoCd("L4");
		est.setCommitDate(buildTestCal().getTime());
		est.setServiceProviderName("LDAL4DR");
		est.setTotalLossFlag("0");
		est.setClaimNumber("34-435345");
		est.setCreatedDate(buildTestCal().getTime());
		est.setCreatedBy("LDAL4DR");
		est.setClientEstimateId("6788888");
		est.setBusinessPartnerId(null);
		est.setClientClaimNumber("34-435345-01");
		est.setCorrectionNumber(0L);
		est.setDateOfLoss(buildTestCal().getTime());
		est.setEstimateCalcDate(buildTestCal().getTime());
		est.setEstimateComplianceStatus("0");
		est.setEstimateInvoiceType("1");
		est.setEstimateNetTotal(new BigDecimal(1234.0));
		est.setEstimateReceivedDate(buildTestCal().getTime());
		est.setEstimateTotal(new BigDecimal(4321.0));
		est.setEstimateVersionNumber(1L);
		est.setExposureNumber("1");
		est.setInsuredClaimantFirstName("UnitTestInsFName");
		est.setInsuredClaimantLastName("UnitTestInsLName");
		est.setOwnerFirstName("UnitTestOwnFName");
		est.setOwnerLastName("UnitTestOwnLName");
		est.setServiceProviderName("UnitTestSPName");
		est.setSupplementedFlag("0");
		est.setSupplementNumber(0L);
		est.setVehicleLocationState("CA");
		est.setVin("1M8GDM9A_KP042788");

		return est;
	}
	
	private Calendar buildTestCal() {
		GregorianCalendar source = new GregorianCalendar();
		GregorianCalendar cal = new GregorianCalendar();
		cal.clear();

		cal.set(Calendar.YEAR, source.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, source.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, source.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, source.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, source.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, source.get(Calendar.SECOND));

		return cal;

	}

}
