package com.mitchell.services.core.partialloss.apddelivery.pojo; 

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.NodeType;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDRepairAssignmentInfoType;
import com.mitchell.schemas.apddelivery.RepairNotificationDocument;
import com.mitchell.schemas.apddelivery.RepairNotificationType;
import com.mitchell.services.core.notification.types.EmailRequestDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ClaimServiceProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.EstimatePackageProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.NotificationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.XsltTransformer;
import com.mitchell.services.technical.claim.dao.vo.ClaimExposure;
import com.mitchell.services.technical.claim.dao.vo.ClaimVehicle;
import com.mitchell.services.technical.claim.dao.vo.ExposureRole;
import com.mitchell.services.technical.claim.dao.vo.Party;
import com.mitchell.services.technical.claim.dao.vo.Phone;
import com.mitchell.services.technical.claim.dao.vo.VehicleRole;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.misc.AppUtilities;

/**
 * This class contains methods which are used to 
 * send email to non-network shop.
 * The email contains Repair Information related to
 * Vehicle, Claimant, Insured, Owner and Estimate
 * and Notes written by sender. 
 * @author Deepak Saxena
 * Date - December 1, 2010
 */
public class EmailDeliveryHandlerImpl implements EmailDeliveryHandler { 
    
    /**
     * Class Name.
     */
    private static final String CLASS_NAME = EmailDeliveryHandlerImpl.class.getName();
	
     /**
     * Logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private NotificationProxy notificationProxy;
    
    private EstimatePackageProxy estimatePackageProxy;
    
    private ClaimServiceProxy claimServiceProxy;
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private APDDeliveryUtil apdDeliveryUtil;
    
    private XsltTransformer xsltTransformer;
    
    private APDCommonUtilProxy apdCommonUtilProxy;
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.EmailDeliveryHandler#deliverRepairAssignment(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void deliverRepairAssignment(APDDeliveryContextDocument apdContextDoc) throws MitchellException {
        
        String methodName = "deliverRepairAssignment";
        
        logger.entering(CLASS_NAME, methodName); 
        
        RepairNotificationDocument repairNotificationDoc = this.populateEmailInfo(apdContextDoc);
        
        apdCommonUtilProxy.logINFOMessage("RepairNotification xml " + repairNotificationDoc.toString());
        
        if (repairNotificationDoc != null) { 
        	
            String htmlMessage = xsltTransformer.transformXmlString(repairNotificationDoc.toString());
            
            String recipientEmailId = apdContextDoc.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                                        .getAPDCommonInfo().getTargetUserInfo().getUserInfo().getEmail();
            
            String subject = this.getEmailSubject(repairNotificationDoc,
                                            apdContextDoc.getAPDDeliveryContext().getMessageType());
            
            // Fix 117663 : notificationProxy using  for Notification.         
            EmailRequestDocument emailRequest = notificationProxy.buildEmailRequest(
            		systemConfigurationProxy.getEmailSenderName(),
            		systemConfigurationProxy.getEmailSenderEmailId(),
                    recipientEmailId,
                    subject,
                    htmlMessage);         
            UserInfoDocument uiDoc = UserInfoDocument.Factory.newInstance();
            
            uiDoc.setUserInfo(apdContextDoc.getAPDDeliveryContext().getAPDRepairAssignmentInfo()
                              .getAPDCommonInfo().getSourceUserInfo().getUserInfo());
            
            // Fix 117663 : notificationProxy using  for Notification.
            notificationProxy.notifyByEmail(emailRequest, uiDoc, null,
                    APDDeliveryConstants.APP_NAME,
                    APDDeliveryConstants.MODULE_NAME,
                    CLASS_NAME); }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method is used to populate the text in email.
     * @param apdContext  APDDeliveryContextDocument
     * @return RepairNotificationDocument
     * @throws MitchellException MitchellException
     */
    private RepairNotificationDocument populateEmailInfo(APDDeliveryContextDocument apdContext) throws MitchellException {
        
        String methodName = "populateEmailInfo";
        
        logger.entering(CLASS_NAME, methodName); 
        
        RepairNotificationDocument doc = RepairNotificationDocument.Factory.newInstance();
        
        RepairNotificationType rnType = doc.addNewRepairNotification();
        
        APDRepairAssignmentInfoType repairInfoType = apdContext.getAPDDeliveryContext().getAPDRepairAssignmentInfo();
        
        if (repairInfoType.getAPDCommonInfo().isSetNotes()) {                            
            rnType.setNotes(repairInfoType.getAPDCommonInfo().getNotes());
        }
        
        rnType.setAssignedBy(this.getCarrierName(repairInfoType.getAPDCommonInfo().getSourceUserInfo().getUserInfo()));
        
        rnType.setRequestType(repairInfoType.getMessageStatus());
            
        rnType.setAssignmentType(apdContext.getAPDDeliveryContext().getMessageType());
        
        rnType.setClaimNumber(repairInfoType.getAPDCommonInfo().getClientClaimNumber());
        
        if (repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().isSetFirstName()
            || repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().isSetLastName()) {
                
            StringBuffer name = new StringBuffer();
            
            if (repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().isSetFirstName()) {
            
                name.append(repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().getFirstName());
                
            }
            
            name.append(" ");
            
            if (repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().isSetLastName()) {
                
                name.append(repairInfoType.getAPDCommonInfo().getTargetUserInfo().getUserInfo().getLastName());
                
            }
            
            rnType.setAssignedTo(name.toString());
        }
        
        // Set estimate amount in email.
        if (repairInfoType.isSetEstimateDocId()) {
            
            this.setEstimateDetails(repairInfoType.getEstimateDocId(), rnType);
            
        }
        
        this.setOtherDetailsInEmail(repairInfoType.getAPDCommonInfo().getInsCoCode(),
                                    Long.valueOf(repairInfoType.getAPDCommonInfo().getClaimId()),
                                    Long.valueOf(repairInfoType.getAPDCommonInfo().getSuffixId()),
                                    rnType);
            
        logger.exiting(CLASS_NAME, methodName);
        
        return doc;
        
    }
    
    /**
     * This method is used to save the owner and vehicle details in email.
     * @param coCode  Company code
     * @param claimId claim Id
     * @param suffixId suffix Id
     * @param rnType RepairNotificationType
     * @throws MitchellException MitchellException
     */
    private void setOtherDetailsInEmail(String coCode, Long claimId, Long suffixId,
                                        RepairNotificationType rnType) throws MitchellException {
                                            
        String methodName = "setOtherDetailsInEmail";
        
        try {
            
            ClaimExposure claimExposure = null;
            Set set = new HashSet();
            // Fix 97154 : Comment the unavailable enum values.
            // TODO : Remove comments for EXPOSURE and VEHICLE on clarification. 
            
            // set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.EXPOSURE);
            // set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.VEHICLE);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.EXPOSURE_ROLE_SET);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.VEHICLE_ROLE_SET);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.PHONE_SET);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.EXPOSURE_SET);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.VEHICLE_SET);
           // TODO
            // set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.EXPOSURE_NODE);
            set.add(com.mitchell.services.technical.claim.constant.InflatableObjectCodes.PARTY_NODE);
            
            // Fix 97154 : Call ClaimServiceClient
            // Fix 117663.
            /*claimExposure = ClaimServiceClient.getEjb().readExposureCustomGraph(
                                                APDDeliveryUtil.getClaimSystemUserInfoDoc(coCode), 
                                                claimId,
                                                suffixId,
                                                set); */
            // Fix 117663
            claimExposure = claimServiceProxy.readExposureCustomGraph(
            		apdDeliveryUtil.getClaimSystemUserInfoDoc(coCode), 
                    claimId,
                    suffixId,
                    set);
            if (claimExposure == null) {
                
                throw new MitchellException(CLASS_NAME,
                                            methodName,
                                            "ClaimExposure returned from readExposureCustomGraph is null ????");
                                            
            } else {
                // Commented below code because ClientClaimNumber has been added to APD xsd now.
                /*
                if (claimExposure.getClientClaimNumber() != null) {
                    
                    logger.info("**** Setting Claim Number in email " + claimExposure.getClientClaimNumber());
                    rnType.setClaimNumber(claimExposure.getClientClaimNumber());
                    
                }*/
                
                ClaimVehicle clmVehicle = (ClaimVehicle) claimExposure.getClaimVehicle();
                
                if (clmVehicle != null) {
                    
                    this.setVehicleDetails(clmVehicle, rnType); // Set Vehicle Details in email
                    
                    Set vehicleRoleSet = clmVehicle.getVehicleRoleSet();
                    
                    if (vehicleRoleSet != null) {
                        
                        Iterator vehicleRoleIter = vehicleRoleSet.iterator();
                        
                        if (vehicleRoleIter != null) {
                            
                            while (vehicleRoleIter.hasNext()) {
                                
                                VehicleRole vehicleRole = (VehicleRole) vehicleRoleIter.next();
                                
                                if (vehicleRole != null && vehicleRole.getVehicleRoleType() == 1) {
                                    Party ownerParty = (Party) vehicleRole.getParty();
                                    
                                    if (ownerParty != null) {
                                        
                                        if (ownerParty.getFirstName() != null || ownerParty.getLastName() != null) {
                                            
                                            rnType.addNewOwner();
                                            
                                            if (ownerParty.getFirstName() != null) {
                                                
                                            	apdCommonUtilProxy.logINFOMessage(" ***** Setting First name in Owner Party....");
                                                rnType.getOwner().setFirstName(ownerParty.getFirstName());
                                            }
                                            
                                            if (ownerParty.getLastName() != null) {
                                                
                                            	apdCommonUtilProxy.logINFOMessage(" ***** Setting Last name in Owner Party....");
                                                rnType.getOwner().setLastName(ownerParty.getLastName());
                                            }
                                        }
                                        
                                        Set phoneSet = ownerParty.getPhoneSet();
                                        
                                        if (phoneSet != null && phoneSet.size() != 0) {
                                            
                                            /* Iterate to get Phone object */
                                            Iterator phoneSetIterator = phoneSet.iterator();
                                            
                                            if (phoneSetIterator != null) {
                                                
                                                while (phoneSetIterator.hasNext()) {
                                                    
                                                    Phone phone = (Phone) phoneSetIterator.next();
                                                    
                                                    if (phone.getPhoneNumber() != null) {
                                                        
                                                        if (rnType.getOwner() == null) {
                                                        
                                                            rnType.addNewOwner();
                                                        }
                                                        apdCommonUtilProxy.logINFOMessage(" ***** Setting phone number in Owner Party....");
                                                        rnType.getOwner().setPhoneNumber(phone.getPhoneNumber());
                                                    }
                                                 
                                                    apdCommonUtilProxy.logINFOMessage(phone.getPhoneNumber());
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                // Set Claimant and Insured Info in email
                this.setClaimantAndInsuredDetails(claimExposure, rnType); 
            }
            
        } catch (MitchellException me) {
            throw me;
        } catch (Exception e) {
          throw new MitchellException(CLASS_NAME, methodName,
                                       "Exception occured in setting vehicle and owner details.."
                                        + "\n"
                                        + AppUtilities.getStackTraceString(e, true));
        }
    }
    /**
     * This method is used to set Claimant and Insured details in email.
     * @param claimExposure
     *              ClaimExposure
     * @param rnType
     *              RepairNotificationType
     */
    private void setClaimantAndInsuredDetails(ClaimExposure claimExposure, RepairNotificationType rnType) {
        
        String methodName = "setClaimantAndInsuredDetails";
        logger.entering(CLASS_NAME, methodName); 
        Set exposureRoleSet = claimExposure.getExposureRoleSet();
        Iterator exposureRoleIterator = exposureRoleSet.iterator();
        
        if (exposureRoleIterator != null) {
        
            while (exposureRoleIterator.hasNext()) {
        
            	
                ExposureRole exposureRole = (ExposureRole) exposureRoleIterator.next();
               
                if (exposureRole != null) {
                            
                    if (exposureRole.getExposureRoleType() == 1) {
                    	apdCommonUtilProxy.logINFOMessage("***** Claimant Party Found..");
                        Party claimantParty = (Party) exposureRole.getParty();
                        
                        if (claimantParty != null) {
                        
                            if (claimantParty.getFirstName() != null || claimantParty.getLastName() != null) {
                                
                                rnType.addNewClaimant();
                                
                                if (claimantParty.getFirstName() != null) {
                                	apdCommonUtilProxy.logINFOMessage("***** Setting First name in Claimant Party....");
                                    rnType.getClaimant().setFirstName(claimantParty.getFirstName());
                                }
                                
                                if (claimantParty.getLastName() != null) {
                                	apdCommonUtilProxy.logINFOMessage("***** Setting Last name in Claimant Party....");
                                    rnType.getClaimant().setLastName(claimantParty.getLastName());
                                }
                            }
                            
                            Set phoneSet = claimantParty.getPhoneSet();
                            
                            if (phoneSet != null && phoneSet.size() != 0) {
                                                            
                                /* Iterate to get Phone object */
                                Iterator phoneSetIterator = phoneSet.iterator();
                                
                                if (phoneSetIterator != null) {
                                    
                                	apdCommonUtilProxy.logINFOMessage("Phone set interator is not null !!!!");
                                    while (phoneSetIterator.hasNext()) {
                                        
                                        Phone phone = (Phone) phoneSetIterator.next();
                                        
                                        if (rnType.getClaimant() == null) {
                                                rnType.addNewClaimant();
                                        }
                                        
                                        if (phone.getPhoneNumber() != null) {
                                        	apdCommonUtilProxy.logINFOMessage("***** Setting phone number in Claimant Party....");
                                            rnType.getClaimant().setPhoneNumber(phone.getPhoneNumber());
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                    
                    if (exposureRole.getExposureRoleType() == 2) {    
                    	apdCommonUtilProxy.logINFOMessage("Insured Party Found..");
                        
                        Party insuredParty = (Party) exposureRole.getParty();
                        
                        if (insuredParty != null) {
                        
                            if (insuredParty.getFirstName() != null || insuredParty.getLastName() != null) {
                        
                                rnType.addNewInsured();
                        
                                if (insuredParty.getFirstName() != null) {
                                	apdCommonUtilProxy.logINFOMessage("***** Setting First name in Insured Party....");
                                    rnType.getInsured().setFirstName(insuredParty.getFirstName());
                                }
                        
                                if (insuredParty.getLastName() != null) {
                                	apdCommonUtilProxy.logINFOMessage("***** Setting Last name in Insured Party....");
                                    rnType.getInsured().setLastName(insuredParty.getLastName());
                                }
                            }
                            
                            Set phoneSet = insuredParty.getPhoneSet();
                            
                            if (phoneSet != null && phoneSet.size() != 0) {
                                
                                /* Iterate to get Phone object */
                                Iterator phoneSetIterator = phoneSet.iterator();
                                
                                if (phoneSetIterator != null) {
                                     
                                	apdCommonUtilProxy.logINFOMessage("Phone set interator is not null !!!!");
                                    while (phoneSetIterator.hasNext()) {
                                        
                                        Phone phone = (Phone) phoneSetIterator.next();
                            
                                        if (phone != null) {
                            
                                            if (rnType.getInsured() == null) {
                                                rnType.addNewInsured();
                                            }
                                            apdCommonUtilProxy.logINFOMessage(" ***** Setting phone number in Insured Party....");
                                            rnType.getInsured().setPhoneNumber(phone.getPhoneNumber());
                                        }
                                         
                                        apdCommonUtilProxy.logINFOMessage(phone.getPhoneNumber());
                                        
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method is used to set the value of Estimate in email.
     * @param estimateDocId long estimateDocId
     * @param rnType RepairNotificationType
     * @throws MitchellException MitchellException
     */
    private void setEstimateDetails(long estimateDocId, RepairNotificationType rnType)
                                                            throws MitchellException {
                                        
        String methodName = "setEstimateDetails";
        
        try {
            
                Estimate est = estimatePackageProxy.getEstimateByEstimateDocId(Long.valueOf(estimateDocId));
                if (est != null && est.getEstimateNetTotal() != null) {                    
                    // Set estimate value in email.
                    rnType.setEstimate(est.getEstimateNetTotal().toString());
                    
                }
          
                                                    
        } catch (MitchellException me) {
                
                throw me;
                
            } catch (Exception e) {
                
                throw new MitchellException(CLASS_NAME,
                                            methodName,
                                            "Error occured while setting Estimate Details in email"
                                            + "\n"
                                            + AppUtilities.getStackTraceString(e, true));
        }
        
    }
    
    /**
     * This method will return the subject of email.
     * @param repairNotificationDoc RepairNotificationDocument
     * @param requestType Request Type
     * @return Subject of email
     */    
    private String getEmailSubject(RepairNotificationDocument repairNotificationDoc,
                                    String requestType) {
        
        String methodName = "getEmailSubject";
        
        logger.entering(CLASS_NAME, methodName);
        
        
        StringBuffer subject = new StringBuffer();
            
        subject.append(repairNotificationDoc.getRepairNotification().getAssignedBy());
            
        subject.append(" ");
        
        if (APDDeliveryConstants.REPAIR_ASSIGNMENT_ARTIFACT_TYPE.equalsIgnoreCase(requestType)) {
                
            if (APDDeliveryConstants.MSG_STATUS_CREATE.equalsIgnoreCase(
                                repairNotificationDoc.getRepairNotification().getRequestType())) {
                                    
            	apdCommonUtilProxy.logINFOMessage("Getting subject for create Repair...");
                subject.append(systemConfigurationProxy.getSubjectForCreateRepair());
                
            } else if (APDDeliveryConstants.MSG_STATUS_CANCEL.equalsIgnoreCase(
                    
                repairNotificationDoc.getRepairNotification().getRequestType())) {
                
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Cancel Repair...");
                subject.append(systemConfigurationProxy.getSubjectForCancelRepair());
                
            } else if (APDDeliveryConstants.MSG_STATUS_UPDATE.equalsIgnoreCase(
                    
                repairNotificationDoc.getRepairNotification().getRequestType())) {
                
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Update Repair...");
                
                subject.append(systemConfigurationProxy.getSubjectForUpdateRepair());
                
            } else if (APDDeliveryConstants.MSG_STATUS_COMPLETE.equalsIgnoreCase(
                    
                repairNotificationDoc.getRepairNotification().getRequestType())) {
                
            	apdCommonUtilProxy.logINFOMessage("Getting subject for complete Repair...");
                
                subject.append(systemConfigurationProxy.getSubjectForCompleteRepair());
            }
                
        } else if (APDDeliveryConstants.REWORK_ASSIGNMENT_ARTIFACT_TYPE.equalsIgnoreCase(requestType)) {
                                
            if (APDDeliveryConstants.MSG_STATUS_CREATE.equalsIgnoreCase(
                                        repairNotificationDoc.getRepairNotification().getRequestType())) {
                    
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Create Rework...");
                subject.append(systemConfigurationProxy.getSubjectForCreateRework());
                
            } else if (APDDeliveryConstants.MSG_STATUS_CANCEL.equalsIgnoreCase(
                                        repairNotificationDoc.getRepairNotification().getRequestType())) {
                    
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Cancel Rework...");
                subject.append(systemConfigurationProxy.getSubjectForCancelRework());
                
            } else if (APDDeliveryConstants.MSG_STATUS_UPDATE.equalsIgnoreCase(
                                        repairNotificationDoc.getRepairNotification().getRequestType())) {
                    
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Update Rework...");
                subject.append(systemConfigurationProxy.getSubjectForUpdateRework());
                
            } else if (APDDeliveryConstants.MSG_STATUS_COMPLETE.equalsIgnoreCase(
                                        repairNotificationDoc.getRepairNotification().getRequestType())) {
                    
            	apdCommonUtilProxy.logINFOMessage("Getting subject for Cancel Rework...");
                subject.append(systemConfigurationProxy.getSubjectForCompleteRework());
                
            }
                
        }
            
        subject.append(" ");
            
        subject.append(repairNotificationDoc.getRepairNotification().getClaimNumber());
        
        apdCommonUtilProxy.logINFOMessage("***** Email Subject is " + subject.toString());
        
        logger.exiting(CLASS_NAME, methodName);
        
        return subject.toString();
    }
    
    /**
     * This method sets Vehicle details in email.
     * @param clmVehicle ClaimVehicle
     * @param rnType RepairNotificationType
     */
    private void setVehicleDetails(ClaimVehicle clmVehicle, RepairNotificationType rnType) {
        
        String methodName = "setVehicleDetails";
        
        logger.entering(CLASS_NAME, methodName);
        
        if (clmVehicle.getVcdMakeDescription() != null || clmVehicle.getModelYear() != null
            || clmVehicle.getVcdModelDescription() != null || clmVehicle.getMileage() != null) {
                
            rnType.addNewVehicle();        
        
            if (clmVehicle.getVcdMakeDescription() != null) {
                
            	apdCommonUtilProxy.logINFOMessage("**** Setting make in vehicle");        
                rnType.getVehicle().setMake(clmVehicle.getVcdMakeDescription());
            
            }
            if (clmVehicle.getModelYear() != null) {
                
            	apdCommonUtilProxy.logINFOMessage("**** Setting Year in vehicle");            
                rnType.getVehicle().setYear(clmVehicle.getModelYear().toString());
                        
            }
            if (clmVehicle.getVcdModelDescription() != null) {
                
            	apdCommonUtilProxy.logINFOMessage("**** Setting Model Description in vehicle");                
                rnType.getVehicle().setModel(clmVehicle.getVcdModelDescription());
                        
            }
            if (clmVehicle.getVcdMakeDescription() != null) {
                
            	apdCommonUtilProxy.logINFOMessage("**** Setting Sub model in vehicle");                
                rnType.getVehicle().setSubmodel(clmVehicle.getVcdSubmodelDescription());
                        
            }
            if (clmVehicle.getMileage() != null) {
                
            	apdCommonUtilProxy.logINFOMessage("**** Setting Mileage in vehicle");                
                rnType.getVehicle().setMileage(clmVehicle.getMileage().toString());
                        
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
    }
            
    /**
     * This method is used to derive carrier name from UserInfoType.
     * @param userInfoType UserInfoType
     * @return Carrier Name
     */
    private String getCarrierName(UserInfoType userInfoType) {
        
        String companyName = null;
        
        UserHierType userHier = userInfoType.getUserHier();
        
        NodeType node = userHier.getHierNode();
        
        String companyLevel = "COMPANY";
        
        if (companyLevel.equalsIgnoreCase(node.getLevel())) {
            
            companyName = node.getName();
            
        }
        
        return companyName;
    }

	/**
	 * @return the notificationProxy
	 */
	public NotificationProxy getNotificationProxy() {
		return notificationProxy;
	}

	/**
	 * @param notificationProxy the notificationProxy to set
	 */
	public void setNotificationProxy(NotificationProxy notificationProxy) {
		this.notificationProxy = notificationProxy;
	}

	/**
	 * @return the estimatePackageProxy
	 */
	public EstimatePackageProxy getEstimatePackageProxy() {
		return estimatePackageProxy;
	}

	/**
	 * @param estimatePackageProxy the estimatePackageProxy to set
	 */
	public void setEstimatePackageProxy(EstimatePackageProxy estimatePackageProxy) {
		this.estimatePackageProxy = estimatePackageProxy;
	}

	/**
	 * @return the claimServiceProxy
	 */
	public ClaimServiceProxy getClaimServiceProxy() {
		return claimServiceProxy;
	}

	/**
	 * @param claimServiceProxy the claimServiceProxy to set
	 */
	public void setClaimServiceProxy(ClaimServiceProxy claimServiceProxy) {
		this.claimServiceProxy = claimServiceProxy;
	}

	/**
	 * @return the systemConfigurationProxy
	 */
	public SystemConfigurationProxy getSystemConfigurationProxy() {
		return systemConfigurationProxy;
	}

	/**
	 * @param systemConfigurationProxy the systemConfigurationProxy to set
	 */
	public void setSystemConfigurationProxy(
			SystemConfigurationProxy systemConfigurationProxy) {
		this.systemConfigurationProxy = systemConfigurationProxy;
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
	 * @return the xsltTransformer
	 */
	public XsltTransformer getXsltTransformer() {
		return xsltTransformer;
	}

	/**
	 * @param xsltTransformer the xsltTransformer to set
	 */
	public void setXsltTransformer(XsltTransformer xsltTransformer) {
		this.xsltTransformer = xsltTransformer;
	}

	/**
	 * @return the apdCommonUtilProxy
	 */
	public APDCommonUtilProxy getApdCommonUtilProxy() {
		return apdCommonUtilProxy;
	}

	/**
	 * @param apdCommonUtilProxy the apdCommonUtilProxy to set
	 */
	public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy) {
		this.apdCommonUtilProxy = apdCommonUtilProxy;
	}

   
}