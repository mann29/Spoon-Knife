set echo on;

begin

ERR_LOG_PKG.SAV_ERR_TYPE(
159600, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error getting APDDeliveryServiceEjb EJB', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159601, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Insufficient Alert i/p to complete the request', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159602, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred while delivering Alert', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159603, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error while delivering Appraisal Assignment', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159604, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred while delivering Repair/Rework Assignment', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159605, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error In Estimate Status Event  i/p to complete the request', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159606, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error in Creating the Mitchell Envelope', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159607, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Mandatory Fields are Missing in BaseAPDCommmonType', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159608, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Invalid APDDeliveryContext', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159609, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred in delivering Artifact', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159610, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred in UserInfoService', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159612, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Usupported user type', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159613, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'APDAppraisalAssignmentNotificationInfoType found null', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159614, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred while delivering Appraisal Assignment Notification', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159615, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred while delivering NICB report', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159616, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occurred in parsing the JMS message', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159617, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occured in Broadcast Message MDB', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159618, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occured in Custom Settings', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159619, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'An unexpected error has occured', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159620, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error occured in XSLT transformation', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159621, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error while sending message to Broadcast Message JMS Queue', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159622, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Correlated Error message', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159623, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Runtime Error in Send BroadCast Message', 'Y'
);

ERR_LOG_PKG.SAV_ERR_TYPE(
159624, 'CORESERVICES', 'APD_DELIVERY_SERVICE',
'Error in RecipientUserInfo : found Null', 'Y'
);

commit;
end;
/
