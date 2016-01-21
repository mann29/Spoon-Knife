package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Comparator;
import java.util.Date;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

/* This Class will Compare the Estimates on the basis of Commit Date,Supplement No and Correction no 
 * For NON-Collaborative WF to fetch the latest Estimate.
 * 
 */
public class EstimateComparator implements Comparator<Estimate>{

	 public EstimateComparator(){ 
	 }
	 
	  public int compare(Estimate estimate1, Estimate estimate2)
	  {
		    Long supplNbr1 = estimate1.getSupplementNumber();
		    Long corrNbr1 = estimate1.getCorrectionNumber();
		    Long supplNbr2 = estimate2.getSupplementNumber();
		    Long corrNbr2 = estimate2.getCorrectionNumber();
		    Date commitDate1 = estimate1.getCommitDate();
		    Date commitDate2 = estimate2.getCommitDate();
		    if(!commitDate1.equals(commitDate2)){
		    	return commitDate1.compareTo(commitDate2);
		    } 
		    if (!(supplNbr1.equals(supplNbr2))) {
	        return supplNbr1.compareTo(supplNbr2);
		    } else {
	        return corrNbr1.compareTo(corrNbr2);
		    } 
		}
}
