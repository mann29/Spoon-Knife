package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield;

import com.cieca.bms.AdminInfoType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;


public class InsuredTestMEUpdateHandlerImpl extends BaseTestMEUpdateHandlerImpl{
	
	protected PersonInfoType getPersonInfoType(AdminInfoType adminInfoType) {
		return adminInfoType.getPolicyHolder().getParty().getPersonInfo();
	}

	protected PartyType getPartyType(AdminInfoType adminInfoType) {
		return adminInfoType.getPolicyHolder().getParty();
	}
}
