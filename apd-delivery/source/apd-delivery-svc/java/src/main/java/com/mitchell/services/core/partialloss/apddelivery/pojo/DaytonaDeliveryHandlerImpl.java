package com.mitchell.services.core.partialloss.apddelivery.pojo;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.*;
import com.mitchell.schemas.apddelivery.APDAlertInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.partialloss.apddelivery.events.Event;
import com.mitchell.services.core.partialloss.apddelivery.events.EventPublisher;
import com.mitchell.services.core.partialloss.apddelivery.events.UploadCompletedEventPayload;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CarrErrorDao;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import javax.jms.JMSException;
import javax.xml.crypto.dsig.XMLObject;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.logging.Logger;

public class DaytonaDeliveryHandlerImpl implements DaytonaDeliveryHandler {

    private CarrErrorDao carrErrorDao;
    private UserInfoProxy userInfoProxy;
    private EventPublisher eventPublisher;

    private static final String CLASS_NAME = "com.mitchell.services.core.partialloss.apddelivery.pojo.DaytonaDeliveryHandlerImpl";
    private static final String UPLOAD_COMPLETED_EVENT = "UploadCompletedEvent";
    private static final String CARR_SUCCESS_MESSAGE = "Upload received.";

    private static Logger logger = Logger.getLogger(CLASS_NAME);

    public void deliverAlert(APDDeliveryContextDocument context) throws MitchellException, UnknownHostException, JMSException, XmlException {

        String methodName = "deliverAlert";
        logger.entering(CLASS_NAME, methodName);

        APDAlertInfoType apdAlertInfoType = context
                .getAPDDeliveryContext()
                .getAPDAlertInfo();

        String carrMessage = apdAlertInfoType.getMessage();

        long statusCode = carrMessage.equalsIgnoreCase(CARR_SUCCESS_MESSAGE) ? 0 : carrErrorDao.getErrorCode(carrMessage);

        BaseAPDCommonType baseAPDCommonType = apdAlertInfoType.getAPDCommonInfo();

        String claimNumber = baseAPDCommonType.getClientClaimNumber();

        long folderId = apdAlertInfoType.getFolderAI();

        String carrierCode = baseAPDCommonType
                .getInsCoCode()
                .toUpperCase();

        UserInfoType targetUserInfo = baseAPDCommonType
                .getTargetUserInfo()
                .getUserInfo();

        String workItemId = baseAPDCommonType.getWorkItemId();

        String shopCoCd =  userInfoProxy.getCompanyCode(targetUserInfo.getOrgID(), workItemId);

        String shopOrgCd = userInfoProxy.getOrgCode(targetUserInfo.getOrgID(), workItemId);

        Event event = createUploadCompleteEvent(claimNumber, carrierCode, shopCoCd, shopOrgCd, folderId, statusCode);

        MitchellWorkflowMessageDocument mitchellWorkflowMessageDocument = createdMessage(event, targetUserInfo, workItemId);

        eventPublisher.postMessage(mitchellWorkflowMessageDocument);
    }

    private MitchellWorkflowMessageDocument createdMessage(Event event, UserInfoType userInfoType, String workItemId) throws XmlException {
        MitchellWorkflowMessageDocument mitchellWorkflowMessageDocument = MitchellWorkflowMessageDocument
                .Factory
                .newInstance();

        MitchellWorkflowMessageType mitchellWorkflowMessageType = mitchellWorkflowMessageDocument.addNewMitchellWorkflowMessage();

        mitchellWorkflowMessageType.setCreateTimestamp(Calendar.getInstance());

        MitchellWorkflowMessageDataType data = mitchellWorkflowMessageType.addNewData();

        String eventString = event.toXmlString();

        XmlObject eventAsXml = XmlObject.Factory.parse(eventString);

        data.set(eventAsXml);

        data.setType(event.getEventCode());

        MitchellWorkflowMessageTrackingInfoType trackingInfoType = mitchellWorkflowMessageType.addNewTrackingInfo();

        trackingInfoType.setWorkItemID(workItemId);

        WorkflowUserInfoType workflowUserInfoType = trackingInfoType.addNewWorkflowUserInfo();

        workflowUserInfoType.setUserInfo(userInfoType);

        MitchellWorkflowMessageSourceType originatingSource = trackingInfoType.addNewOriginatingSource();
        originatingSource.setComponent("APD_Delivery");
        originatingSource.setProduct("WORKCENTER");
        originatingSource.setServer("SERVER");

        trackingInfoType.setBusinessService("APD_Delivery");

        return mitchellWorkflowMessageDocument;
    }

    private Event createUploadCompleteEvent(String claimNumber, String carrierCode, String shopCoCd, String shopOrgCd, long folderId, long statusCode) {

        UploadCompletedEventPayload eventPayload = new UploadCompletedEventPayload();

        eventPayload.setClaimNumber(claimNumber);
        eventPayload.setCarrierCode(carrierCode);
        eventPayload.setStatusCode(statusCode);
        eventPayload.setFolderId(folderId);
        eventPayload.setShopCoCd(shopCoCd);
        eventPayload.setShopOrgCd(shopOrgCd);

        Event event = new Event();
        event.setEventPayload(eventPayload);
        event.setEventType(UPLOAD_COMPLETED_EVENT);

        return event;
    }

    public void setEventPublisher(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setUserInfoProxy(UserInfoProxy userInfoProxy) {
        this.userInfoProxy = userInfoProxy;
    }

    public void setCarrErrorDao(CarrErrorDao carrErrorDao) {
        this.carrErrorDao = carrErrorDao;
    }
}


