package com.mitchell.services.business.partialloss.assignmentdelivery.dao;

import com.mitchell.common.exception.MitchellException;

public interface CultureDAO {

	
	public String getCultureByCompany(String coCode)throws MitchellException;
	
	
}
