package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.exception.MitchellException;

public interface ErrorLoggingServiceWrapper {

	/**
	 *  Logs an error to the ErrorLog Service
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param details          Detail Description as XML or plain String.
	 * @param detailType       Detail Type "XML" or "STRING"
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String workItemID, String description, String companyCode,
			int orgID, String details, String detailType);

	/**
	 *  Logs an error to the ErrorLog Service
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param details          Detail Description as XML or plain String.
	 * @param detailType       Detail Type "XML" or "STRING"
	 * @param claimNumber      If applicable 
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */

	public abstract String logError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String workItemID, String description, String companyCode,
			int orgID, String details, String detailType, String claimNumber);

	/**
	 *  Logs an error to the ErrorLog Service
	 *  Accepts exception as a parameter instead of a detail string
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param exception        Actual exception that needs to be logged
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String workItemID, String description, String companyCode,
			int orgID, Exception exception);

	/**
	 *  Logs an error to the ErrorLog Service
	 *  Accepts exception as a parameter instead of a detail string
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param exception        Actual exception that needs to be logged
	 * @param claimNumber      Claim Number 
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	/* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
	 * Modified By: rb11546 (Rikki Bindra)
	 * Modified Date: 3/5/2010
	 */
	public abstract String logError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String workItemID, String description, String companyCode,
			int orgID, Exception exception, String claimNumber);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API.
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param applicationName  Application name where error occurred
	 * @param moduleName       Module name where error occurred
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param exception        Actual exception that needs to be logged
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logCommonError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String applicationName, String moduleName, String workItemID,
			String description, String companyCode, int orgID,
			Exception exception);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API.
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param applicationName  Application name where error occurred
	 * @param moduleName       Module name where error occurred
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param exception        Actual exception that needs to be logged
	 * @param claimNumber      Claim Number  
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	/* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
	 * Modified By: rb11546 (Rikki Bindra)
	 * Modified Date: 03/05/2010
	 */
	public abstract String logCommonError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String applicationName, String moduleName, String workItemID,
			String description, String companyCode, int orgID,
			Exception exception, String claimNumber);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API.
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param applicationName  Application name where error occurred
	 * @param moduleName       Module name where error occurred
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param details          Detail Description as XML or plain String.
	 * @param detailType       Detail Type "XML" or "STRING"
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logCommonError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String applicationName, String moduleName, String workItemID,
			String description, String companyCode, int orgID, String details,
			String detailType);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API.
	 *
	 * @param errorType        The error type previously registered in the
	 *                         error type database. The errorType determines
	 *                         the ApplicationName and ModuleName that is logged.
	 * @param correlationId    A unique identifier used to correlate multiple
	 *                         related errors. The Id is originally obtained from
	 *                         the return value of the first logged related
	 *                         error. Pass null if you do not have a correlationId.
	 * @param className        Class where error occurred
	 * @param methodName       Method name where error occurred
	 * @param severity         See ErrorLoggingService.SEVERITY
	 * @param applicationName  Application name where error occurred
	 * @param moduleName       Module name where error occurred
	 * @param workItemID       Associated work item id if applicable
	 * @param description      Short Description (max 256 characters)
	 * @param companyCode      If applicable
	 * @param orgID            if applicable
	 * @param details          Detail Description as XML or plain String.
	 * @param detailType       Detail Type "XML" or "STRING"
	 * @param claimNumber      Claim Number 
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	/* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
	 * Modified By: rb11546 (Rikki Bindra)
	 * Modified Date: 03/05/2010
	 */
	public abstract String logCommonError(int errorType, String correlationId,
			String className, String methodName, String severity,
			String applicationName, String moduleName, String workItemID,
			String description, String companyCode, int orgID, String details,
			String detailType, String claimNumber);

	/**
	 * Logs a MitchelException object to the ErrorLogging Service. In general
	 * it is best to use the logCommonError method when logging a
	 * MitchellException. This allows the application to provide context to
	 * exception that it may catch that have been thrown by lower level APIs.
	 * See the logCommonError method documentation for more information.
	 *
	 * @param mitchellException
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null. Also, sets the CorrelationId
	 *         of the supplied mitchellException object.
	 */
	public abstract String logError(MitchellException mitchellException);

	/**
	 * Logs a MitchelException object to the ErrorLogging Service.
	 * It is a overloaded method to which claim number can also be passed in 
	 * addition to MitchellException.
	 *
	 * @param mitchellException
	 * @param claimNumber
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null. Also, sets the CorrelationId
	 *         of the supplied mitchellException object.
	 */

	/* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
	 * Modified By: rb11546 (Rikki Bindra)
	 * Modified Date: 12/31/2009
	 */

	public abstract String logError(MitchellException mitchellException,
			String claimNumber);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API. For example you might have something like
	 *  the following:
	 *  <pre>
	 *    } catch (MitchellException e) {
	 *      e.setApplicationName( MY_APP_NAME );
	 *      e.setModuleName( MY_MODULE_NAME );
	 *      ErrorLoggingService.logCommonError( e );
	 *    }
	 *  </pre>
	 *
	 * @param mitchellException
	 *
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logCommonError(MitchellException mitchellException);

	/**
	 *  Logs an error to the ErrorLog Service. The provided applicationName
	 *  and moduleName override the ApplicationName and ModuleName that are
	 *  registered for the provided errorType. This allows the client of an
	 *  API to give its' context to an errorType that was thrown from within
	 *  a generic service or API. For example you might have something like
	 *  the following:
	 *  <pre>
	 *    } catch (MitchellException e) {
	 *      e.setApplicationName( MY_APP_NAME );
	 *      e.setModuleName( MY_MODULE_NAME );
	 *      ErrorLoggingService.logCommonError( e );
	 *    }
	 *  </pre>
	 *
	 * @param mitchellException
	 * @param claimNumber      Claim Number 
	 * @return Returns the input correlationId or a new correlationId if
	 *         the input correlationId was null.
	 */
	public abstract String logCommonError(MitchellException mitchellException,
			String claimNumber);

}