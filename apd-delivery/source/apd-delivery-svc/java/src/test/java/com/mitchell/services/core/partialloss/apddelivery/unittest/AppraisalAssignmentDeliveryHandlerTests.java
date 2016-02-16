package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.*;
import com.mitchell.services.core.partialloss.apddelivery.pojo.AppraisalAssignmentDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.mockito.Mockito.*;

public class AppraisalAssignmentDeliveryHandlerTests {

    private AppraisalAssignmentDeliveryHandler sut;
    private APDDeliveryContextDocument mockContextDocument;
    private APDDeliveryContextType mockContextType;
    private APDAppraisalAssignmentInfoType mockAppAssignmentInfoType;
    private BaseAPDCommonType mockBaseAPDCommonType;
    private APDUserInfoType mockAPDUserInfoType;
    private UserInfoType mockUserInfoType;
    private APDDeliveryUtil mockAPDDeliveryUtil;
    private APDCommonUtilProxy mockAPDCommonUtilProxy;
    private ADSProxy mockADSProxy;

    private final String ANY_ORG_CODE = "AnyOrgCode";
    private final String ANY_USER_ID = "AnyUserID";
    private final String ANY_USER_TYPE = "AnyUserType";
    private final String ANY_MESSAGE_TYPE = "AnyMessageType";
    private final ArrayList<String> ANY_PARTS_LIST_ATTACHMENTS = new ArrayList<String>();
    private final String COMPLETED_STATUS = "COMPLETED";


    @Before
    public void setup() throws Exception {

        sut = new AppraisalAssignmentDeliveryHandler();

        InitializeMocks();

        SetupMocks();
    }

    private void InitializeMocks() {

        mockContextDocument = mock(APDDeliveryContextDocument.class);
        mockContextType = mock(APDDeliveryContextType.class);
        mockAppAssignmentInfoType = mock(APDAppraisalAssignmentInfoType.class);
        mockBaseAPDCommonType = mock(BaseAPDCommonType.class);
        mockAPDUserInfoType = mock(APDUserInfoType.class);
        mockUserInfoType = mock(UserInfoType.class);
        mockAPDDeliveryUtil = mock(APDDeliveryUtil.class);
        mockAPDCommonUtilProxy = mock(APDCommonUtilProxy.class);
        mockADSProxy = mock(ADSProxy.class);
    }

    private void SetupMocks() throws Exception {

        sut.setApdDeliveryUtil(mockAPDDeliveryUtil);
        sut.setApdCommonUtilProxy(mockAPDCommonUtilProxy);
        sut.setAdsProxy(mockADSProxy);

        when(mockContextDocument.getAPDDeliveryContext()).thenReturn(mockContextType);
        when(mockContextType.getAPDAppraisalAssignmentInfo()).thenReturn(mockAppAssignmentInfoType);
        when(mockAppAssignmentInfoType.getAPDCommonInfo()).thenReturn(mockBaseAPDCommonType);
        when(mockBaseAPDCommonType.getTargetUserInfo()).thenReturn(mockAPDUserInfoType);
        when(mockAPDUserInfoType.getUserInfo()).thenReturn(mockUserInfoType);
        when(mockUserInfoType.getOrgCode()).thenReturn(ANY_ORG_CODE);
        when(mockUserInfoType.getUserID()).thenReturn(ANY_USER_ID);
        when(mockAPDDeliveryUtil.getUserType(ANY_ORG_CODE, ANY_USER_ID)).thenReturn(ANY_USER_TYPE);
        when(mockAPDDeliveryUtil.isStaffUser(ANY_USER_TYPE)).thenReturn(false);
        when(mockAPDDeliveryUtil.isShopUser(ANY_USER_TYPE)).thenReturn(true);
        when(mockAPDDeliveryUtil.isEndpointPlatformWithOverride(mockBaseAPDCommonType)).thenReturn(true);
        when(mockContextType.getMessageType()).thenReturn(ANY_MESSAGE_TYPE);

    }

    @Test
    public void When_deliverArtifact_is_called_it_should_deliver_the_WorkAssignment_through_the_ADSProxy_when_isEndpointRCWeb_is_true() throws Exception {

        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType)).thenReturn(true);

        sut.deliverArtifact(mockContextDocument, ANY_PARTS_LIST_ATTACHMENTS);

        verify(mockAPDDeliveryUtil).isEndpointRCWeb(mockBaseAPDCommonType);

        verify(mockADSProxy).deliverRCWebAssignment(mockContextDocument);
    }

    @Test
    public void When_deliverArtifact_is_called_it_should_not_deliver_the_WorkAssignment_through_the_ADSProxy_when_isEndpointRCWeb_is_false() throws Exception {

        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType)).thenReturn(false);

        sut.deliverArtifact(mockContextDocument, ANY_PARTS_LIST_ATTACHMENTS);

        verify(mockAPDDeliveryUtil).isEndpointRCWeb(mockBaseAPDCommonType);

        verify(mockADSProxy, never()).deliverRCWebAssignment(mockContextDocument);
    }

    @Test
    public void When_deliverArtifact_is_called_and_the_message_status_is_not_complete_it_should_deliver_WorkAssignment_through_ADSProxy() throws Exception {
        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType))
                .thenReturn(true);
        when(mockAppAssignmentInfoType.getMessageStatus())
                .thenReturn(ANY_MESSAGE_TYPE);

        sut.deliverArtifact(mockContextDocument, ANY_PARTS_LIST_ATTACHMENTS);

        verify(mockADSProxy, atLeastOnce()).deliverRCWebAssignment(mockContextDocument);
    }

    @Test
    public void When_DeliverArtifact_is_called_and_the_message_status_is_complete_it_should_not_deliver_WorkAssignment_through_ADSProxy() throws Exception {
        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType))
                .thenReturn(true);
        when(mockAppAssignmentInfoType.getMessageStatus())
                .thenReturn(COMPLETED_STATUS);

        sut.deliverArtifact(mockContextDocument, ANY_PARTS_LIST_ATTACHMENTS);

        verify(mockADSProxy, never()).deliverRCWebAssignment(mockContextDocument);
    }



}
