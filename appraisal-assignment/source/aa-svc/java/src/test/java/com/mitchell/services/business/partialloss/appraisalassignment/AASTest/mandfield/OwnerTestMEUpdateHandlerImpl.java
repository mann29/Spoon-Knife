package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield;

import com.cieca.bms.AdminInfoType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;

public class OwnerTestMEUpdateHandlerImpl extends BaseTestMEUpdateHandlerImpl {


	protected PartyType getPartyType(AdminInfoType adminInfoType) {
		// TODO Auto-generated method stub
		return adminInfoType.getOwner().getParty();
	}


	protected PersonInfoType getPersonInfoType(AdminInfoType adminInfoType) {
		// TODO Auto-generated method stub
		return adminInfoType.getOwner().getParty().getPersonInfo();
	}

}