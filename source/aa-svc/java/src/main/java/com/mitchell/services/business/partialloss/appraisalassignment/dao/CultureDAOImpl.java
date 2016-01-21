package com.mitchell.services.business.partialloss.appraisalassignment.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Logger;
import oracle.jdbc.OracleTypes;
import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;
import com.mitchell.utils.misc.AppUtilities;

public class CultureDAOImpl extends CommonBaseDAO implements CultureDAO{
	
	private static final String CLASS_NAME = CultureDAOImpl.class.getName();	
    private static final Logger logger = Logger.getLogger(CLASS_NAME);
    private static final String GET_CURRENCY = "{call pkgorgutils.get_currency_by_company(?,?,?)}";
    
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
					systemConfigProxy.getSettingValue("/AssignmentDelivery/Database/EPDDataSource"),
					systemConfigProxy.getSettingValue("/AppraisalAssignmentClient/Remote/JNDIFactory"),
					systemConfigProxy.getSettingValue("/AppraisalAssignmentClient/Remote/ProviderUrl"));
			
			
			
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
				MitchellException me = new MitchellException(AppraisalAssignmentConstants.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AppraisalAssignmentConstants.ERROR_GETTING_CULTURE_MSG+
				"\n" +
				AppUtilities.getStackTraceString(sqlEx));
				throw me;
		
		}catch(javax.naming.NamingException namExp){
				MitchellException me = new MitchellException(AppraisalAssignmentConstants.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AppraisalAssignmentConstants.ERROR_GETTING_CULTURE_MSG+
				"\n" +
				AppUtilities.getStackTraceString(namExp));
				throw me;
		
		}catch(Exception e){
				MitchellException me = new MitchellException(AppraisalAssignmentConstants.ERROR_GETTING_CULTURE,
				CLASS_NAME,methodName, 
				"",
				AppraisalAssignmentConstants.ERROR_GETTING_CULTURE_MSG+
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


	public String getCurrency(String company)
			throws MitchellException {
		
		String methodName = "getCurrency";
		logger.entering(CLASS_NAME,methodName); 
		CallableStatement stmt = null;
		ResultSet rs=null;
		String currency=null;
		Connection dbConnection = null;
		try {
			dbConnection = this.getConnection(
					systemConfigProxy.getSettingValue("/AssignmentDelivery/Database/EPDDataSource"),
					systemConfigProxy.getSettingValue("/AppraisalAssignmentClient/Remote/JNDIFactory"),
					systemConfigProxy.getSettingValue("/AppraisalAssignmentClient/Remote/ProviderUrl"));
			stmt = dbConnection.prepareCall(GET_CURRENCY);			
			stmt.setString(1, "");//country code
			stmt.setString(2, company);//Company code
			stmt.registerOutParameter(3, OracleTypes.CURSOR);
			stmt.execute();
			rs = (ResultSet) stmt.getObject(3);
			while(rs.next()){	
				currency=rs.getString("CURRENCY_SYMBOL");
				logger.info("Currency for"+company  + currency);
			}
		}catch(SQLException sqlEx){
			MitchellException me = new MitchellException(AppraisalAssignmentConstants.ERROR_GETTING_CURRENCY,
					CLASS_NAME,methodName, 
					"",
					AppraisalAssignmentConstants.ERROR_GETTING_CURRENCY_MSG+
					"\n" +
					AppUtilities.getStackTraceString(sqlEx));
			throw me;
		}catch(Exception e){
			MitchellException me = new MitchellException(AppraisalAssignmentConstants.ERROR_GETTING_CURRENCY,
					CLASS_NAME,methodName, 
					"",
					AppraisalAssignmentConstants.ERROR_GETTING_CURRENCY_MSG+
					"\n" +
					AppUtilities.getStackTraceString(e));
			throw me;

		}finally{            
			cleanupConnection(dbConnection,stmt,rs);
		}
		logger.exiting(CLASS_NAME,methodName); 		
		return currency;
			}
			
}
