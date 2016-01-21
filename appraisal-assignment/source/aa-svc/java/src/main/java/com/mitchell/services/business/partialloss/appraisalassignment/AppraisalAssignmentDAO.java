package com.mitchell.services.business.partialloss.appraisalassignment;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.logging.Logger;

import com.mitchell.common.dao.CommonBaseDAO;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * This class represent DAO methods of AppraisalAssignment Service.
 * 
 * @author Raminder.Singh
 * 
 */
public class AppraisalAssignmentDAO extends CommonBaseDAO {

    private static final String CLASS_NAME = AppraisalAssignmentDAO.class.getName();
    private static final Logger logger = Logger.getLogger(AppraisalAssignmentDAO.class.getName());
    private final String GET_ASSIGNMENT_EVER_DISPATCH = "{? = call clm.pkg_appraisal_assignments.check_if_claim_dispatched(?)}";
    private final String SAVE_ASSIGNMENT_HISTORY_LOGGING = "{ call clm.pkg_maxima_utils.put_wa_assignment_activity_log(?,?,?,?,?)}";
    private final String GET_WORKGROUP = "{ call PKG_APPRAISAL_ASSIGNMENTS.Get_Workgroup_Type(?,?,?)}";
    private String dataSource = "";

    /**
     * @return int 1
     */
    public int defaultDAOErrorType() {
        return 1;
    }

    /**
     * This method retrieves AppraisalAssignment System Configuration and reads
     * Datasource value.
     * 
     * @return DataSource Name.
     */
    public String getDataSource() {
        this.dataSource = SystemConfiguration.getSettingValue("/AppraisalAssignment/AppraisalAssignmentDAODataSource");
        return this.dataSource;
    }

    /**
     * This method takes ClaimExposureID and calls Oracle's
     * CLM.PKG_APPRAISAL_ASSIGNMENTS.CHECK_IF_CLAIM_DISPATCHED function to check
     * whether assignment has been dispatched ever or not.
     * 
     * Returns <code>true</code> in case, assignment is dispatched. Returns
     * <code>false</code> in case, assignment is never dispatched.
     * 
     * @param claimExposureID
     *            <code>long</code> ClaimExposureID.
     * @return Returns <code>true</code> in case, assignment is dispatched.
     *         Returns <code>false</code> in case, assignment is never
     *         dispatched.
     * @throws MitchellException
     *             in case if it is not able to connect to database.
     */
    public boolean isAssignmentEverDispatched(final long claimExposureID) throws MitchellException {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Entering AppraisalAssignmentDAO : isAssignmentEverDispatched method. Input ClaimExposureID : "
                    + claimExposureID);
        }
        boolean isAsmtEverDsptch = false;

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = this.getConnection(getDataSource());
            callableStatement = connection.prepareCall(GET_ASSIGNMENT_EVER_DISPATCH);
            callableStatement.registerOutParameter(1, java.sql.Types.INTEGER);
            callableStatement.setFloat(2, claimExposureID);
            callableStatement.executeQuery();
            final int retValue = callableStatement.getInt(1);

            if (retValue > 0) {
                isAsmtEverDsptch = true;
            } else {
                isAsmtEverDsptch = false;
            }

        } catch (final Exception exception) {
            logger.severe("Exception Occured while executing method isAssignmentEverDispatched()"
                    + exception.getMessage());
            throw new MitchellException(CLASS_NAME, "isAssignmentEverDispatched",
                    "Exception occured while executing/calling DAO and DB Function.", exception);
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (final Exception ex) {
                logger.severe("Exception occured while closing CallableStatement and/or Connection.");
            }
        }
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Exiting AppraisalAssignmentDAO : isAssignmentEverDispatched method. ClaimExposureID is = "
                    + claimExposureID + " and is Assignment Ever Dispatched = " + isAsmtEverDsptch);
        }
        return isAsmtEverDsptch;
    }

    /**
     * 
     * @param assignmentId
     *            WorkAssignment AssignmentId
     * @param eventName
     *            Name of the Event
     * @param eventDescription
     *            Description of the event
     * @param createdDate
     *            Created Date
     * @param createdBy
     *            Created By
     * @return boolean 'true' if successful else 'false'
     * @throws MitchellException
     *             Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public boolean writeAssignmentActivityLog(final long assignmentId, final String eventName,
            final String eventDescription, final java.util.Date createdDate, final String createdBy)
            throws MitchellException {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Entering AppraisalAssignmentDAO : writeAssignmentActivityLog method. Input parameters:: "
                    + "AssignmentId : " + assignmentId + "\tEventName : " + eventName + "\tEventDescription : "
                    + eventDescription + "CreatedDate : " + createdDate + "\tCreatedBy : " + createdBy);
        }
        boolean isLogSaved = false;
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = this.getConnection(getDataSource());
            callableStatement = connection.prepareCall(SAVE_ASSIGNMENT_HISTORY_LOGGING);
            callableStatement.setLong(1, assignmentId);
            callableStatement.setString(2, eventName);
            callableStatement.setString(3, eventDescription);
            callableStatement.setTimestamp(4, new java.sql.Timestamp(createdDate.getTime()));
            callableStatement.setString(5, createdBy);

            final int retValue = callableStatement.executeUpdate();

            if (retValue > 0) {
                isLogSaved = true;
            } else {
                isLogSaved = false;
            }
        } catch (final Exception exception) {
            logger.severe("Exception Occured while executing method writeAssignmentActivityLog()"
                    + exception.getMessage());
            throw new MitchellException(CLASS_NAME, "writeAssignmentActivityLog",
                    "Exception occured while executing/calling DAO and DB Function.", exception);
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (final Exception ex) {
                logger.severe("Exception occured while closing CallableStatement and/or Connection.");
            }
        }
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Exiting AppraisalAssignmentDAO : writeAssignmentActivityLog method. Input parameters:: "
                    + "AssignmentId : " + assignmentId + "\tEventName : " + eventName + "\tEventDescription : "
                    + eventDescription + "CreatedDate : " + createdDate + "\tCreatedBy : " + createdBy);
        }
        return isLogSaved;
    }

    /**
     * This Method returns the WorkGroupType.
     * 
     * @param orgCoCode
     *            orgCoCode
     * @param groupCode
     *            groupCode
     * @return workGroupType workGroupType
     * @throws MitchellException
     *             Throws MitchellException to the caller in case of any
     *             exception arise.
     * 
     */
    public String getWorkGroupType(final String orgCoCode, final String groupCode) throws MitchellException {
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Entering AppraisalAssignmentDAO : getWorkGroupType method. Input orgCoCode , groupCode : "
                    + orgCoCode + "," + groupCode);
        }
        Connection connection = null;
        CallableStatement callableStatement = null;
        String workGroupType = null;

        try {
            connection = this.getConnection(getDataSource());
            callableStatement = connection.prepareCall(GET_WORKGROUP);
            callableStatement.setString(1, groupCode);
            callableStatement.setString(2, orgCoCode);
            callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
            callableStatement.executeQuery();
            workGroupType = (String) callableStatement.getObject(3);

        } catch (final Exception exception) {
            logger.severe("Exception Occured while executing method getWorkGroupType()" + exception.getMessage());
            throw new MitchellException(CLASS_NAME, "getWorkGroup",
                    "Exception occured while executing/calling DAO and DB Function.", exception);
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (final Exception ex) {
                logger.severe("Exception occured while closing CallableStatement and/or Connection.");
            }
        }
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Exiting AppraisalAssignmentDAO : getWorkGroupType method. workGroupType is = " + workGroupType);
        }
        return workGroupType;
    }
}
