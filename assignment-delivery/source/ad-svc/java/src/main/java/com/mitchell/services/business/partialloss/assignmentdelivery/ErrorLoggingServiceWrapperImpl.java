package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

public class ErrorLoggingServiceWrapperImpl implements ErrorLoggingServiceWrapper {

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
    
	public  String logError( int errorType, String correlationId,
                           String className, String methodName,
                           String severity,  String workItemID,
                           String description, String companyCode,
                           int orgID, String details, String detailType) {
        
		return ErrorLoggingService.logError(errorType, correlationId,
				className, methodName, severity, workItemID, description,
				companyCode, orgID, details, detailType);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
    
    
    
	public  String logError( int errorType, String correlationId,
                           String className, String methodName,
                           String severity,  String workItemID,
                           String description, String companyCode,
                           int orgID, String details, String detailType, String claimNumber) {

		return ErrorLoggingService.logError(errorType, correlationId, className, methodName, severity, workItemID, description, companyCode, orgID, details, detailType, claimNumber);
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Exception)
	 */
    
	public  String logError(int errorType, String correlationId,
                     String className, String methodName,
                     String severity, String workItemID,
                     String description, String companyCode, int orgID,
                     Exception exception) {
		return ErrorLoggingService.logError(errorType, correlationId,
				className, methodName, severity, workItemID, description,
				companyCode, orgID, exception);
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Exception, java.lang.String)
	 */
    /* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
     * Modified By: rb11546 (Rikki Bindra)
     * Modified Date: 3/5/2010
     */
    
	public  String logError(int errorType, String correlationId,
                     String className, String methodName,
                     String severity, String workItemID,
                     String description, String companyCode, int orgID,
                     Exception exception, String claimNumber) {
		return ErrorLoggingService.logError(errorType, correlationId,
				className, methodName, severity, workItemID, description,
				companyCode, orgID, exception, claimNumber);
}

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Exception)
	 */
    
	public  String logCommonError(int errorType, String correlationId,
            String className, String methodName,
            String severity, String applicationName,
            String moduleName, String workItemID,
            String description, String companyCode,
            int orgID, Exception exception) {
              
		return ErrorLoggingService.logCommonError(errorType, correlationId,
				className, methodName, severity, applicationName, moduleName,
				workItemID, description, companyCode, orgID, exception); 
}
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Exception, java.lang.String)
	 */
    /* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
     * Modified By: rb11546 (Rikki Bindra)
     * Modified Date: 03/05/2010
     */
    
	public  String logCommonError(int errorType, String correlationId,
            String className, String methodName,
            String severity, String applicationName,
            String moduleName, String workItemID,
            String description, String companyCode,
            int orgID, Exception exception, String claimNumber) {
		return ErrorLoggingService.logCommonError(errorType, correlationId,
				className, methodName, severity, applicationName, moduleName,
				workItemID, description, companyCode, orgID, exception,
				claimNumber);
    }


    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)
	 */
    
	public  String logCommonError(int errorType, String correlationId, String className, String methodName,
            String severity, String applicationName, String moduleName, String workItemID, String description, String companyCode, int orgID,
                String details, String detailType) {
		return ErrorLoggingService.logCommonError(errorType, correlationId,
				className, methodName, severity, applicationName, moduleName,
				workItemID, description, companyCode, orgID, details,
				detailType);
    }
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
    /* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
     * Modified By: rb11546 (Rikki Bindra)
     * Modified Date: 03/05/2010
     */
    
	public  String logCommonError(int errorType, String correlationId, String className, String methodName,
            String severity, String applicationName, String moduleName, String workItemID, String description, String companyCode, int orgID,
                String details, String detailType, String claimNumber) {
        
		return ErrorLoggingService.logCommonError(errorType, correlationId,
				className, methodName, severity, applicationName, moduleName,
				workItemID, description, companyCode, orgID, details,
				detailType, claimNumber);
        
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(com.mitchell.common.exception.MitchellException)
	 */
    
	public  String logError(MitchellException mitchellException){

    	return ErrorLoggingService.logError(mitchellException);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logError(com.mitchell.common.exception.MitchellException, java.lang.String)
	 */
    
    /* QuickBase request 1832: Request to add Claim Number and Company Code while writing Syslog 
     * Modified By: rb11546 (Rikki Bindra)
     * Modified Date: 12/31/2009
     */
    
    
	public  String logError(MitchellException mitchellException, 
                    String claimNumber){
    	return ErrorLoggingService.logError(mitchellException, claimNumber);
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(com.mitchell.common.exception.MitchellException)
	 */
    
	public  String logCommonError(MitchellException mitchellException){
    	return ErrorLoggingService.logCommonError(mitchellException);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.ErrorLoggingServiceWrapper#logCommonError(com.mitchell.common.exception.MitchellException, java.lang.String)
	 */
    
	public  String logCommonError(MitchellException mitchellException, String claimNumber){
    	return ErrorLoggingService.logCommonError(mitchellException, claimNumber);
    }

}
