package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Comparator;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

/* This Class will Compare the Estimates on the basis of Supplement No and Draft Revision No 
 * For Collaborative WF.First it will give priority to Supplement No and if it is equal then
 * compare it on the basis of doc Revision No to fetch the latest Estimate
 * 
 */
public class DraftEstimateComparator implements Comparator<Estimate>{

	 public DraftEstimateComparator(){

	 }
	 
	  public int compare(Estimate estimate1, Estimate estimate2){
	    Long supplementNumber1 = estimate1.getSupplementNumber();
	    Integer docRevisionNumber1 = estimate1.getEstimateDocRevision();
	    Long supplementNumber2 = estimate2.getSupplementNumber();
	    Integer docRevisionNumber2 = estimate2.getEstimateDocRevision();

	    if(supplementNumber1 == null && supplementNumber2 == null){
	    	return 0;
	    }else if(supplementNumber1 == null){
	    	return -1;
	    }else if(supplementNumber2 == null){
	    	return 1;
	    }else{	    
		      if (!(supplementNumber1.equals(supplementNumber2))) {
		        return supplementNumber1.compareTo(supplementNumber2);
		      } else {
		    	  /* 
		    	   * If Draft Estimate Revision number is null then the
		    	   * Draft estimate becomes the committed estimate.
		    	   * The draft estimate having null value will always be maximum from 
		    	   * any draft estimate created for that supplement.
		    	   * Due to this reason we are returning 1 if docRevisionNumber1 is null
		    	   * and returning -1 if docRevisionNumber2 is null. 
		    	   */
		    	  if(docRevisionNumber1 == null && docRevisionNumber2 == null){
		    		  return 0;
		    	  }
		    	  if (docRevisionNumber1 == null){
		    		  return 1;
		    	  }
		    	  else if (docRevisionNumber2 == null){
		    		  return -1;
		    	  }else{		    	  
			    	return docRevisionNumber1.compareTo(docRevisionNumber2);
			    	  
		    	  }
		      }
	    }
	  }
}
