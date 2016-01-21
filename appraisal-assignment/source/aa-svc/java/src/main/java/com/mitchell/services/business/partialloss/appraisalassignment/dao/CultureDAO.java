package com.mitchell.services.business.partialloss.appraisalassignment.dao;

import com.mitchell.common.exception.MitchellException;

public interface CultureDAO {

	
	public String getCultureByCompany(String coCode)throws MitchellException;
	
	public String getCurrency(String company) throws MitchellException;
	
}
