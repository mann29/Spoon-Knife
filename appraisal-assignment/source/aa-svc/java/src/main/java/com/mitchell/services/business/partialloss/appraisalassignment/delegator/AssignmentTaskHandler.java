package com.mitchell.services.business.partialloss.appraisalassignment.delegator; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface AssignmentTaskHandler 
{ 
    void createSupplementTask(String claimSuffixNumber,
            UserInfoDocument bodyShopUserInfo,String workItemId,
            String note, String reviewCoCd, String reviewUserId) throws MitchellException;

    void cancelSupplementTask(String claimSuffixNumber,
            UserInfoDocument bodyShopUserInfo,
            String note, String reviewCoCd, String reviewUserId) throws MitchellException;
    
    void rejectSupplementTask(long taskId, UserInfoDocument estimatorUserInfo) throws MitchellException;

    void createAssignSupplementTaskToNCRTUSer(String claimSuffixNumber,
            UserInfoDocument bodyShopUserInfo,String workItemId,
            String note, String reviewCoCd, String reviewUserId) throws MitchellException;
    
    
} 
