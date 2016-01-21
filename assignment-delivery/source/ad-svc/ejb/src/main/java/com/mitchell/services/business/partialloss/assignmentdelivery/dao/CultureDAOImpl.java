package com.mitchell.services.business.partialloss.assignmentdelivery.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;
import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy.SystemConfigProxy;
import com.mitchell.utils.misc.AppUtilities;

public class CultureDAOImpl extends CommonBaseDAO implements CultureDAO{
	
	private static final String CLASS_NAME = CultureDAOImpl.class.getName();	
    private static final Logger logger = Logger.getLogger(CLASS_NAME);    
    
    protected SystemConfigProxy systemConfigProxy;
    
    public String getCultureByCompany(String coCode)throws MitchellException{

			String methodName = "getCultureByCompany";
			logger.entering(CLASS_NAME,methodName);
					
			CallableStatement stmt = null;
			Connection dbConnection = null;
			ResultSet rsResult = null;
			String language = null;
			
			String spCall = "{call PkgOrgUtils.get_culture_by_company(?, ?, ? ) }";
						
			try{
			
			dbConnection = this.getConnection(
					systemConfigProxy.getStringValue("/AssignmentDelivery/Database/EPDDataSource"),
					systemConfigProxy.getStringValue("/AssignmentDeliveryJavaClient/JNDI/JNDIFactory"),
					systemConfigProxy.getStringValue("/AssignmentDeliveryJavaClient/JNDI/ProviderUrl"));
			logger.info("language returned :connection created: "+systemConfigProxy.getStringValue("/AssignmentDelivery/Database/EPDDataSource"));
			
			stmt = dbConnection.prepareCall(spCall);            
			
			stmt.setNull(1,Types.VARCHAR);
			stmt.setString(2, coCode);		
			stmt.registerOutParameter(3,OracleTypes.CURSOR);		
			stmt.execute();
			rsResult = (ResultSet)stmt.getObject(3);
			logger.info("rsResult :"+rsResult);
			
			while(rsResult.next()){			
				language = rsResult.getString(1);
			}			
			logger.info("language returned : "+language);
		
		}catch(SQLException sqlEx){
				MitchellException me = new MitchellException(AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE_MSG+
				"\n" +
				AppUtilities.getStackTraceString(sqlEx));
				throw me;
		
		}catch(javax.naming.NamingException namExp){
				MitchellException me = new MitchellException(AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE_MSG+
				"\n" +
				AppUtilities.getStackTraceString(namExp));
				throw me;
		
		}catch(Exception e){
				MitchellException me = new MitchellException(AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AssignmentDeliveryErrorCodes.ERROR_GETTING_CULTURE_MSG+
				"\n" +
				AppUtilities.getStackTraceString(e));
				throw me;
		
		}finally{            
				cleanupConnection(dbConnection,stmt,rsResult);
		}
		
		logger.exiting(CLASS_NAME,methodName);
		
		return language;

    }
    protected int defaultDAOErrorType()
    {
        return 0;
    }
	public SystemConfigProxy getSystemConfigProxy() {
		return systemConfigProxy;
	}
	public void setSystemConfigProxy(SystemConfigProxy systemConfigProxy) {
		this.systemConfigProxy = systemConfigProxy;
	}
		
}
