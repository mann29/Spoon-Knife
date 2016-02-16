package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Logger;
// Fix 97154 : Remove obsolete imports
import oracle.jdbc.OracleTypes;
//import oracle.jdbc.driver.OracleTypes;

import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.exception.MitchellException;


public class ApdJdbcDaoImpl extends CommonBaseDAO implements ApdJdbcDao {

	private static final String EPD_DATA_SOURCE = "EPDDataSource";
	/**
	 * class name.
	 */
	private static final String CLASS_NAME = ApdJdbcDaoImpl.class.getName();

	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);
	
	public String getNetworkFlag(String orgId) throws MitchellException {
		String methodName = "getNetworkFlag";
		logger.entering(CLASS_NAME, methodName);
		
        Connection dbConnection = null;
        CallableStatement stmt = null;
        ResultSet rSet = null;        
        
        String networkNonNetworkFlag = null;
        
        try {            
            String spCall = "{call EPD_Maxima_utils.Get_wc_resource_details(?,?)}";
           // dbConnection = this.getConnection(EPD_DATA_SOURCE);
            dbConnection = this.getConnection(EPD_DATA_SOURCE);
            stmt = dbConnection.prepareCall(spCall);                                    
            stmt.setString(1 , orgId);
            stmt.registerOutParameter(2 , OracleTypes.CURSOR);            
            stmt.execute();            
            rSet = (ResultSet) stmt.getObject(2);            
            if (rSet.next()) {                
                networkNonNetworkFlag = rSet.getString("in_network");                                
            }            
            
        } catch (Exception ex) {
            
            throw new MitchellException(ApdJdbcDaoImpl.class.getName(),
            		"getNetworkFlag", ex.getMessage(), ex);
        } finally {            
            this.cleanupConnection(dbConnection,stmt,rSet);
        }    
        logger.exiting(CLASS_NAME, methodName);
        return networkNonNetworkFlag;
	}

	protected int defaultDAOErrorType() {
		// TODO Use a default dao error later.
		return 0;
	}

}
