package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield;


import com.cieca.bms.CommunicationsType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;

public class MandFieldUnsetHandlerImpl implements MandFieldUnsetHandler{

	
	public PersonInfoType unsetAddress1(PersonInfoType personInfo) {
		 
		 if(personInfo.getCommunicationsArray(0).getAddress().isSetAddress1()) {
			 personInfo.getCommunicationsArray(0).getAddress().unsetAddress1();
		 }	
		 return personInfo;
	}

	
	public PersonInfoType unsetAddress2(PersonInfoType personInfo) {
		 
		 if(personInfo.getCommunicationsArray(0).getAddress().isSetAddress2()) {
			 personInfo.getCommunicationsArray(0).getAddress().unsetAddress2();
		 }	
		 return personInfo;
	}

	
	public PartyType unsetCellPhone(PartyType partyType) {
		return unsetContact("CP", partyType);
	
	}

	
	public PersonInfoType unsetCity(PersonInfoType personInfo) {
		 
		 if(personInfo.getCommunicationsArray(0).getAddress().isSetCity()) {
			 personInfo.getCommunicationsArray(0).getAddress().unsetCity();
		 }	
		 return personInfo;
	}

	
	public PartyType unsetEmail(PartyType partyType) {
		return unsetContact("EM", partyType);
	
	}

	
	public PartyType unsetFax(PartyType partyType) {
		return unsetContact("FX", partyType);
	
	}

	
	public PersonInfoType unsetFirstName(PersonInfoType personInfo) {
		 
		 if(personInfo.getPersonName().getFirstName() != null) {
			 personInfo.getPersonName().unsetFirstName();
		 }	
		 return personInfo;
	}

	
	public PartyType unsetHomePhone(PartyType partyType) {
		return unsetContact("HP", partyType);
	
	}

	
	public PersonInfoType unsetLastName(PersonInfoType personInfo) {
		 
		 if(personInfo.getPersonName().getLastName() != null) {
			 personInfo.getPersonName().unsetLastName();
		 }	
		 return personInfo;
	}

	
	public PersonInfoType unsetState(PersonInfoType personInfo) {
		 
		 if(personInfo.getCommunicationsArray(0).getAddress().isSetStateProvince()) {
			 personInfo.getCommunicationsArray(0).getAddress().unsetStateProvince();
		 }	
		 return personInfo;
	}

	
	public PartyType unsetWorkPhone(PartyType partyType) {
		return unsetContact("WP", partyType);
	}

	
	public PersonInfoType unsetZip(PersonInfoType personInfo) {
		 
		 if(personInfo.getCommunicationsArray(0).getAddress().isSetPostalCode()) {
			 personInfo.getCommunicationsArray(0).getAddress().unsetPostalCode();
		 }	
		 return personInfo;
	}

	private PartyType unsetContact(String qualifier, PartyType partyType) {
		CommunicationsType[] commArr = partyType.getContactInfoArray(0).getCommunicationsArray();
		CommunicationsType[] newcommArr = new CommunicationsType[commArr.length-1];
		for (int i=0,k=0;i<commArr.length;i++) {
			if(!qualifier.equalsIgnoreCase(commArr[i].getCommQualifier())) {
				newcommArr[k++] = commArr[i];
			}
		}
		partyType.getContactInfoArray(0).setCommunicationsArray(newcommArr);
		return partyType;
	}
}
