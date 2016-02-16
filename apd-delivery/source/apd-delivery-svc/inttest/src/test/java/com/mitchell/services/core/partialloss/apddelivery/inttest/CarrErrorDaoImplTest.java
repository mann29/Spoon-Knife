package com.mitchell.services.core.partialloss.apddelivery.inttest;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CarrErrorDaoImpl;
import com.mitchell.systemconfiguration.SystemConfiguration;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.naming.NamingException;
import java.sql.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static junit.framework.Assert.assertEquals;

/**
 * Created by ss101449 on 1/21/2015.
 */
public class CarrErrorDaoImplTest {

    public CarrErrorDaoImplTest(){ }

    private final String EXPECTED_NMA_DATA_SOURCE = "CCDBDataSource";
    private final String CARR_ERROR_MESSAGE = "Missing UploadType - No MCF Type.";
    private final long EXPECTED_ERROR_CODE = 1;

    private CarrErrorDaoImpl sut;
    private Connection substituteNmaConnection;

    @Before
    public void setUp() throws SQLException, MitchellException, NamingException {
        //DriverManager.registerDriver(new OracleDriver());
        substituteNmaConnection = DriverManager.getConnection(
                "jdbc:oracle:thin:@//nmad:1521/nmad.mitchell.com",
                "clm",
                "clmdev");

        setUpMocks();
    }

    @After
    public void tearDown(){
        if (substituteNmaConnection != null){
            try {
                substituteNmaConnection.close();
            } catch (Exception ex){

            }
        }

        substituteNmaConnection = null;
        sut = null;
    }

    public void setUpMocks() throws MitchellException, SQLException, NamingException {
        sut = mock(CarrErrorDaoImpl.class);

        when(sut.getErrorCode(anyString())).thenCallRealMethod();
        when(sut.getConnection(EXPECTED_NMA_DATA_SOURCE)).thenReturn(substituteNmaConnection);

        doNothing().when(sut).cleanupConnection(any(Connection.class),any(PreparedStatement.class),any(ResultSet.class));
    }

    @Test
    public void it_should_get_the_error_code_using_the_message() throws MitchellException {
        long errorCode = sut.getErrorCode("Missing UploadType - No MCF Type.");

        assertEquals(EXPECTED_ERROR_CODE, errorCode);
    }

    @Test
    public void it_should_return_the_proper_value_when_nothing_is_found() throws MitchellException{
        long errorCode = sut.getErrorCode("bogus");

        assertEquals(-1, errorCode);
    }
}
