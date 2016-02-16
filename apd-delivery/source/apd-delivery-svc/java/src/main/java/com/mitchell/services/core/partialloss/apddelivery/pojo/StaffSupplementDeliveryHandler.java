package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDRequestStaffSupplementInfoType;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.utils.misc.AppUtilities;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class delivers Request for Staff Supplement.
 * @author Deepak Saxena
 * @see APDDeliveryHanler
 */
public class StaffSupplementDeliveryHandler implements APDDeliveryHandler { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = StaffSupplementDeliveryHandler.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private APDDeliveryUtil apdDeliveryUtil;
    
    private PlatformDeliveryHandler platformDeliveryHandler;
    
    /**
     * This method will deliver Request for Staff Supplement.
     * @param apdContext
     * XML bean of type APDDeliveryContextDocument.
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument apdContextDocument) 
                                            throws MitchellException {
                                                
        String methodName = "deliverArtifact";
        
        logger.entering(CLASS_NAME, methodName);
        
        APDRequestStaffSupplementInfoType staffSupplementInfo = 
                     apdContextDocument.getAPDDeliveryContext().getAPDRequestStaffSupplementInfo();
        
        if (staffSupplementInfo == null) {
            
            throw new MitchellException(CLASS_NAME,
                                        methodName,
                                        APDDeliveryConstants.REQ_STAFF_SUPPLEMENT_INFO_NULL);
        }
        
         
        try {
            String targetUserType =
            	apdDeliveryUtil.getUserType(
                    staffSupplementInfo.getAPDCommonInfo().getTargetUserInfo().getUserInfo().getOrgCode(), 
                    staffSupplementInfo.getAPDCommonInfo().getTargetUserInfo().getUserInfo().getUserID());
            
            // is user BodyShop?
            boolean isBodyShop = UserTypeConstants.SHOP_TYPE.equals(targetUserType) 
                                 || UserTypeConstants.DRP_SHOP_TYPE.equals(targetUserType);
            
            // Check if the user is platform user and body shop 
            
            if (apdDeliveryUtil.isEndpointPlatform2(staffSupplementInfo.getAPDCommonInfo()) && isBodyShop) {
                
                // Call PlatformDeliveryHandler to deliver request for Staff Supplement
                platformDeliveryHandler.deliverRequestForStaffSupplement(apdContextDocument);
                
            } else {
                
                throw new MitchellException(CLASS_NAME, methodName, "Unsupported user type for this operation");
                
            }
            
        } catch (MitchellException me) {
            
            throw me;
            
        } catch (Exception e) {
            
            throw new MitchellException(CLASS_NAME, methodName,
                                        "Exception occured in deliverArtifact"
                                        + "\n" 
                                        + AppUtilities.getStackTraceString(e, true));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
     /**
     * This method is never used.
     * 
     * @param context
     * An instance of APDDeliveryContextDocument.
     */
    public void deliverArtifactNotification(APDDeliveryContextDocument context) {
        //no-op
    }
    
    /**
     * @param context APDDeliveryContextDocument
     * @param attachments ArrayList
     * @throws MitchellException  MitchellException
     */
    public void deliverArtifact(APDDeliveryContextDocument context,
                                                   ArrayList attachments)
                                                        throws MitchellException {
        // No Operation.
        
    }

	/**
	 * @return the apdDeliveryUtil
	 */
	public APDDeliveryUtil getApdDeliveryUtil() {
		return apdDeliveryUtil;
	}

	/**
	 * @param apdDeliveryUtil the apdDeliveryUtil to set
	 */
	public void setApdDeliveryUtil(APDDeliveryUtil apdDeliveryUtil) {
		this.apdDeliveryUtil = apdDeliveryUtil;
	}

	/**
	 * @return the platformDeliveryHandler
	 */
	public PlatformDeliveryHandler getPlatformDeliveryHandler() {
		return platformDeliveryHandler;
	}

	/**
	 * @param platformDeliveryHandler the platformDeliveryHandler to set
	 */
	public void setPlatformDeliveryHandler(
			PlatformDeliveryHandler platformDeliveryHandler) {
		this.platformDeliveryHandler = platformDeliveryHandler;
	}
} 
