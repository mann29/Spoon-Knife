package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.cieca.bms.AssignmentAddRqDocument;
import java.rmi.RemoteException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.BmsClmOutDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;
import com.mitchell.services.technical.claim.ejb.ClaimServiceRemote;
import com.mitchell.services.technical.claim.exception.ClaimException;

public class ClaimProxyImpl implements ClaimProxy {

    private static final String CLASS_NAME = ClaimProxyImpl.class.getName();
    
     // @Override
    public ClaimInfoDTO getSimpleClaimInfoByFullClaimNumber(final UserInfoDocument userInfoDoc,
            final String claimSuffixNumber, final String companyCode) throws RemoteException, MitchellException,
            ClaimException {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
        return claimServiceRemote.getSimpleClaimInfoByFullClaimNumber(userInfoDoc, claimSuffixNumber, companyCode);
    }

    // @Override
    public Long writeExposureActLog(final Long exposureID, final String comment, final UserInfoDocument userInfoDoc,
            final boolean isVisiableToUser) throws MitchellException {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
        return claimServiceRemote.writeExposureActivityLog(exposureID, comment, userInfoDoc);
    }

    /*
     * public static ClaimRemote getEJB() throws MitchellException {
     * ClaimRemoteHome claimRemoteHome = null; ClaimRemote claimRemote = null;
     * String claimEJBname =
     * SystemConfiguration.getSettingValue("/ClaimJavaClient/Remote/EJBJndi");
     * String providerURL =
     * SystemConfiguration.getSettingValue("/ClaimJavaClient/Remote/ProviderUrl"
     * ); try { claimRemoteHome =
     * (ClaimRemoteHome)EJBHomeFactory.lookup(providerURL, claimEJBname,
     * com.mitchell.services.technical.claim.ejb.ClaimRemoteHome.class);
     * claimRemote = claimRemoteHome.create(); } catch(NamingException e) {
     * throw new MitchellException(ErrorCodes.ERROR_CLIENT_EJB.getCode(),
     * "ClaimClientSupport", "getEjb", "Naming exception getting Claim EJB.",
     * e); } catch(CreateException e) { throw new
     * MitchellException(ErrorCodes.ERROR_CLIENT_EJB.getCode(),
     * "ClaimClientSupport", "getEjb", "Create exception getting Claim EJB.",
     * e); } catch(RemoteException e) { throw new
     * MitchellException(ErrorCodes.ERROR_CLIENT_EJB.getCode(),
     * "ClaimClientSupport", "getEjb", "Remote exception getting Claim EJB.",
     * e); } return claimRemote; }
     */

    public Long addVehLctnTrckngHist(final Long claimSuffixID, final String vehicleTrackingStatus,
            final UserInfoDocument userinfodocument) throws RemoteException, MitchellException, ClaimException {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
    	return claimServiceRemote.addVehLctnTrckngHist(vehicleTrackingStatus, claimSuffixID, userinfodocument);

    }

    public BmsClmInputDTO createInputDTO(AssignmentAddRqDocument assignAddRqDoc, UserInfoDocument adjusterUserInfo,String parsingRule,UserInfoDocument userInfoDocument) throws RemoteException, MitchellException
    {
            return ClaimServiceClient.createInputDTO(assignAddRqDoc, adjusterUserInfo, parsingRule,
                    userInfoDocument);
    }
    
    public McfClmOutDTO saveClaimFromAssignmentBms(BmsClmInputDTO inputDTO) throws RemoteException, MitchellException
    {
    		final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
            return claimServiceRemote.saveClaimFromAssignmentBms(inputDTO);
    }
    
    public void writeExposureActLog(Long exposureId,String comment,UserInfoDocument logdInUsrInfo,Long referenceId,String referenceTableName) throws RemoteException, MitchellException {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();  
    	claimServiceRemote.writeActivityLog(null,exposureId,comment,logdInUsrInfo,referenceId,referenceTableName);  
    }
    public String[] parseClaimNumber(final String claimNum, final String compCo, final String userId) throws RemoteException,MitchellException
    {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
        return claimServiceRemote.parseClaimNumber(claimNum,compCo,userId);
    }
    
    public void setManagedById(final UserInfoDocument userInfoDocument,final Long exposureId,final Long managedById) throws RemoteException, MitchellException {
         final String METHOD_NAME = "setManagedById";
         MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "Start Calling Claim.setManagedById");
         final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
         claimServiceRemote.setManagedById(userInfoDocument, exposureId, managedById);
         MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "End Calling Claim.setManagedById");
    }
    
    public BmsClmOutDTO readClaimInfo(UserInfoDocument assignorUserInfoDocument, String claimSuffix)throws RemoteException, MitchellException
    {
    	final ClaimServiceRemote claimServiceRemote = ClaimServiceClient.getEjb();
    	//Claim BMS info
    	BmsClmOutDTO bmsClmOutDTO = claimServiceRemote.readClaimInfo(assignorUserInfoDocument, claimSuffix);
		return bmsClmOutDTO;
    
    }
}
