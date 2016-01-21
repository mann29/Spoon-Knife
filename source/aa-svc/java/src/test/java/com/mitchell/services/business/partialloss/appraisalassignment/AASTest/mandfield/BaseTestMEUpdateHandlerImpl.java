package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.mandfield;

import com.cieca.bms.AdminInfoType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.CIECADocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandlerImpl;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public abstract class BaseTestMEUpdateHandlerImpl {
	
	private MandFieldUnsetHandler mandFieldUnsetHandler;

	public void setMandFieldUnsetHandler(
			MandFieldUnsetHandler mandFieldUnsetHandler) {
		this.mandFieldUnsetHandler = mandFieldUnsetHandler;
	}
	
	public MitchellEnvelopeDocument unsetFirstName(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetFirstName(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	
	public MitchellEnvelopeDocument unsetLastName(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetLastName(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}

	public MitchellEnvelopeDocument unsetAddress1(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetAddress1(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}

	public MitchellEnvelopeDocument unsetAddress2(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetAddress2(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetCity(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetCity(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetState(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetState(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetZip(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PersonInfoType personInfo = getPersonInfoType(adminInfo);
		
		personInfo = this.mandFieldUnsetHandler.unsetZip(personInfo);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetWorkPhone(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PartyType partyType = getPartyType(adminInfo);
		
		partyType = this.mandFieldUnsetHandler.unsetWorkPhone(partyType);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetCellPhone(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PartyType partyType = getPartyType(adminInfo);
		
		partyType = this.mandFieldUnsetHandler.unsetCellPhone(partyType);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetHomePhone(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PartyType partyType = getPartyType(adminInfo);
		
		partyType = this.mandFieldUnsetHandler.unsetHomePhone(partyType);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetFax(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PartyType partyType = getPartyType(adminInfo);
		
		partyType = this.mandFieldUnsetHandler.unsetFax(partyType);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	public MitchellEnvelopeDocument unsetEmail(MitchellEnvelopeDocument meDoc) throws Exception {
		MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
		
		CIECADocument cIECADocument = new MitchellEnvelopeHandlerImpl().getCiecaFromME(meHelper);
		
		AdminInfoType adminInfo= cIECADocument.getCIECA().getAssignmentAddRq().getAdminInfo();
		
		PartyType partyType = getPartyType(adminInfo);
		
		partyType = this.mandFieldUnsetHandler.unsetEmail(partyType);
        
		final EnvelopeBodyType envBodyType = meHelper.getEnvelopeBody("CIECABMSAssignmentAddRq");
        meHelper.updateEnvelopeBodyContent(envBodyType, cIECADocument);
		
		return meDoc;
	}
	abstract protected PersonInfoType getPersonInfoType(AdminInfoType adminInfoType);
	abstract protected PartyType getPartyType(AdminInfoType adminInfoType);
}
