package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.dao.MICommonDAOException;
import com.mitchell.common.exception.MitchellException;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

/**
 * Created by ss101449 on 1/21/2015.
 */
public class CarrErrorDaoImpl extends CommonBaseDAO implements CarrErrorDao {

    private final String NMA_DATA_SOURCE = "CCDBDataSource";

    private static Logger logger = Logger.getLogger(CarrErrorDaoImpl.class.getName());

    @Override
    public long getErrorCode(String carrMessage) throws MitchellException {
        final String methodName = "getErrorCode";
        logger.entering(CarrErrorDaoImpl.class.getName(), methodName);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rSet = null;

        try {
            final String query = "SELECT ERR_CODE FROM CLM.SHOP_CARR_SHARE_STATUS WHERE CARR_MESSAGE = UPPER(?)";
            conn = this.getConnection(NMA_DATA_SOURCE);

            ps = conn.prepareStatement(query);
            ps.setString(1, carrMessage);
            rSet = ps.executeQuery();

            if (rSet.next()){
                return rSet.getLong("ERR_CODE");
            }

        } catch (Exception ex) {
            throw new MitchellException(
                    CarrErrorDaoImpl.class.getName(),
                    methodName,
                    ex.getMessage(),
                    ex);
        } finally {
            this.cleanupConnection(conn, ps, rSet);
        }

        return -1; // -1 instead of 0, since 0 would be default for success.
    }

    @Override
    public void cleanupConnection(Connection conn, PreparedStatement ps, ResultSet rs) throws MICommonDAOException {
        super.cleanupConnection(conn,ps,rs);
    }

    @Override
    protected int defaultDAOErrorType() {
        return 0;
    }
}
