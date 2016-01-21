package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.File;
import java.util.Date;
import java.util.Set;

import com.mitchell.services.core.mcfsvc.MCFBuildRequest;
import com.mitchell.services.core.mcfsvc.client.MCFServiceEJBClient;
import com.mitchell.services.core.mcfsvc.ejb.MCFServiceEJBRemote;
import com.mitchell.services.core.mcfsvc.exception.MCFServiceException;
import com.mitchell.services.schemas.mcf.MCFDocument;

public class MCFServiceEJBRemoteProxy implements MCFServiceEJBRemote {
	MCFServiceEJBRemote delegate;

	public void init() throws MCFServiceException{
		delegate = MCFServiceEJBClient.getMCFServiceEJB();
	}
	public File buildAssignmentMCF(MCFBuildRequest arg0, Set arg1)
			throws  MCFServiceException {
		return delegate.buildAssignmentMCF(arg0, arg1);
	}

	public File buildAssignmentMCF(MCFBuildRequest arg0)
			throws MCFServiceException {
		return delegate.buildAssignmentMCF(arg0);
	}
	
	public File buildAssignmentMCF(MCFBuildRequest arg0, Date arg1)
		throws MCFServiceException {
		return delegate.buildAssignmentMCF(arg0, arg1);
	}

	public File buildMCF(File arg0, File arg1) throws 
			MCFServiceException {
		return delegate.buildMCF(arg0, arg1);
	}

	public File buildMCF(String arg0, String arg1) throws 
			MCFServiceException {
		return delegate.buildMCF(arg0, arg1);
	}
	
    // new api added to MCF Service	- mcf-service - changeset 255803
	public File buildMCFWithoutArtifcats(String arg0, String arg1) throws 
	        MCFServiceException {
		return delegate.buildMCFWithoutArtifcats(arg0, arg1);
	}	
	
	public File buildNICBReportMCF(MCFBuildRequest arg0)
			throws  MCFServiceException {
		return delegate.buildNICBReportMCF(arg0);
	}

	public MCFDocument extractMCFPackage(File arg0, File arg1, boolean arg2)
			throws  MCFServiceException {
		return delegate.extractMCFPackage(arg0, arg1, arg2);
	}

	public MCFDocument extractMCFPackage(File arg0, File arg1)
			throws   MCFServiceException {
		return delegate.extractMCFPackage(arg0, arg1);
	}

	public MCFDocument extractMCFPackage(String arg0, String arg1, boolean arg2)
			throws   MCFServiceException {
		return delegate.extractMCFPackage(arg0, arg1, arg2);
	}

	public MCFDocument extractMCFPackage(String arg0, String arg1)
			throws   MCFServiceException {
		return delegate.extractMCFPackage(arg0, arg1);
	}

	public MCFDocument extractMCFXML(File arg0, File arg1, boolean arg2)
			throws   MCFServiceException {
		return delegate.extractMCFXML(arg0, arg1, arg2);
	}

	public MCFDocument extractMCFXML(File arg0, File arg1)
			throws   MCFServiceException {
		return delegate.extractMCFXML(arg0, arg1);
	}

	public MCFDocument extractMCFXML(String arg0, String arg1, boolean arg2)
			throws   MCFServiceException {
		return delegate.extractMCFXML(arg0, arg1, arg2);
	}

	public MCFDocument extractMCFXML(String arg0, String arg1)
			throws   MCFServiceException {
		return delegate.extractMCFXML(arg0, arg1);
	}

	
}
