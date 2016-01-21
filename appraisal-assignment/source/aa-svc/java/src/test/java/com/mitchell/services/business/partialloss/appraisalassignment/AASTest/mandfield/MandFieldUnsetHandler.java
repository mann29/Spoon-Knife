package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield;

import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;

public interface MandFieldUnsetHandler {

	PersonInfoType unsetAddress1(PersonInfoType personInfo);
	PersonInfoType unsetAddress2(PersonInfoType personInfo);
	PersonInfoType unsetCity(PersonInfoType personInfo);
	PersonInfoType unsetState(PersonInfoType personInfo);
	PersonInfoType unsetZip(PersonInfoType personInfo);
	PartyType unsetCellPhone(PartyType partyType);
	PartyType unsetEmail(PartyType partyType);
	PartyType unsetFax(PartyType partyType);
	PersonInfoType unsetLastName(PersonInfoType personInfo);
	PersonInfoType unsetFirstName(PersonInfoType personInfo);
	PartyType unsetHomePhone(PartyType partyType);
	PartyType unsetWorkPhone(PartyType partyType);
}
