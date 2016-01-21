package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.Map;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;

public class AASUtilsImpl implements AASUtils {

 private IAppraisalAssignmentUtils appraisalAssignmentUtils ;
   


    public void setAppraisalAssignmentUtils(IAppraisalAssignmentUtils appraisalAssignmentUtils){
         this.appraisalAssignmentUtils = appraisalAssignmentUtils;
    }

    // @Override
    public Map retrieveCarrierSettings(final String coCode) throws MitchellException {
      //  final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();

        final java.util.Map map = appraisalAssignmentUtils.retrieveCarrierSettings(coCode);

        return map;
    }

}
