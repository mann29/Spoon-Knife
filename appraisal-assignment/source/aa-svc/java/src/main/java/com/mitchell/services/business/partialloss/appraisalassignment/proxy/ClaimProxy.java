package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.cieca.bms.AssignmentAddRqDocument;
import java.rmi.RemoteException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.BmsClmOutDTO;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;
import com.mitchell.services.technical.claim.exception.ClaimException;

public interface ClaimProxy {
    public ClaimInfoDTO getSimpleClaimInfoByFullClaimNumber(UserInfoDocument userInfoDoc, String claimSuffixNumber,
            String companyCode) throws RemoteException, MitchellException, ClaimException;

    public Long writeExposureActLog(Long exposureID, String comment, UserInfoDocument userInfoDoc,
            boolean isVisiableToUser) throws MitchellException;

    public Long addVehLctnTrckngHist(Long claimSuffixID, String vehicleTrackingStatus, UserInfoDocument userinfodocument)
            throws RemoteException, MitchellException;
      
    public BmsClmInputDTO createInputDTO(AssignmentAddRqDocument assignAddRqDoc, UserInfoDocument adjusterUserInfo,String parsingRule,UserInfoDocument userInfoDocument) throws RemoteException, MitchellException;      

    public McfClmOutDTO saveClaimFromAssignmentBms(BmsClmInputDTO inputDTO)throws RemoteException, MitchellException;  

    public void writeExposureActLog(Long exposureId,String comment,UserInfoDocument logdInUsrInfo,Long referenceId,String referenceTableName) throws RemoteException, MitchellException ;

    public String[] parseClaimNumber(final String claimNum, final String compCo, final String userId) throws RemoteException,MitchellException;
    
    public void setManagedById(final UserInfoDocument userInfoDocument,final Long exposureId,final Long managedById) throws RemoteException, MitchellException;
    
    public BmsClmOutDTO readClaimInfo(UserInfoDocument assignorUserInfoDocument, String claimSuffix)throws RemoteException, MitchellException;
    
}
