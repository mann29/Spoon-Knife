/**
 * 
 */
package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.rmi.RemoteException;
import java.util.logging.Level;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.business.partialloss.assignmentdelivery.AbstractAssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.PlatformDelRouter;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;

/**
 * @author pk100311
 * 
 */
public class PlatformDelRouterImpl implements PlatformDelRouter {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.mitchell.services.business.partialloss.assignmentdelivery.handler
     * .apd.PlatformDelRouter#route(com.mitchell.schemas.apddelivery.
     * APDDeliveryContextDocument)
     */

    public void route(final APDDeliveryContextDocument payload) throws  MitchellException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Entering APD integration router ");
        }
        final BaseAPDCommonType apdCommonInfo = payload.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
                .getAPDCommonInfo();
        final String targetUserId = apdCommonInfo
                .getTargetUserInfo().getUserInfo().getUserID();
        final String userType = userInfoClient.getUserType(apdCommonInfo.getInsCoCode(), targetUserId);
        if (UserTypeConstants.STAFF_TYPE.equals(userType)) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Routing to WCAP for user[" + targetUserId
                        + "], company code[" + apdCommonInfo.getInsCoCode() + "]");
            }
            wcapDeliveryHandler.deliverAssignment(payload);
        } else {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Routing to RC for user[" + targetUserId
                        + "], company code[" + apdCommonInfo.getInsCoCode() + "]");
            }
            repairCenterDeliveryHandler.deliverAssignment(payload);
        }
    }

    // ---- Boilerplate code--------
    private UserInfoServiceEJBRemote userInfoClient;
    private MsgBusDelHandler repairCenterDeliveryHandler;
    private MsgBusDelHandler wcapDeliveryHandler;
    private AbstractAssignmentDeliveryLogger logger;

    public UserInfoServiceEJBRemote getUserInfoClient() {
        return userInfoClient;
    }

    public void setUserInfoClient(final UserInfoServiceEJBRemote userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    public MsgBusDelHandler getRepairCenterDeliveryHandler() {
        return repairCenterDeliveryHandler;
    }

    public void setRepairCenterDeliveryHandler(final MsgBusDelHandler repairCenterDeliveryHandler) {
        this.repairCenterDeliveryHandler = repairCenterDeliveryHandler;
    }

    public MsgBusDelHandler getWcapDeliveryHandler() {
        return wcapDeliveryHandler;
    }

    public void setWcapDeliveryHandler(final MsgBusDelHandler wcapDeliveryHandler) {
        this.wcapDeliveryHandler = wcapDeliveryHandler;
    }

    public AbstractAssignmentDeliveryLogger getLogger() {
        return logger;
    }

    public void setLogger(final AbstractAssignmentDeliveryLogger logger) {
        this.logger = logger;
    }

}
