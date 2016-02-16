package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDUserInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtilImpl;
import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class APDDeliveryUtilRCWebUnitTest {

    private APDDeliveryUtilImpl sut;
    private BaseAPDCommonType mockApdCommonType;
    private APDUserInfoType mockTargetUserInfo;
    private APDUserInfoType mockTargetDRPUserInfo;
    private UserInfoType mockUserInfoTypeForTargetUser;
    private UserInfoType mockUserInfoTypeForTargetDRPUser;
    private APDCommonUtilProxy mockAPDCommonUtilProxy;

    String [] userAppCodesWithIgnoreConnectAndComms = {"CMAPES","WCAIRC","NETACT"};
    String [] userAppCodesWithoutIgnoreConnectAndComms = {"CMAPES","NETACT"};
    String [] shopAppCodesWithPreferRCWeb = {"CRSAE","CRSAPP","CRSAPP","CRSBRK","CRSDIM","RCWCOM","CRSAB"};
    String [] shopAppCodesWithoutPreferRCWeb = {"CRSAE","CRSAPP","CRSAPP","CRSBRK","CRSDIM","CRSAB"};

    @Before
    public void setup() throws XmlException {

        sut = new APDDeliveryUtilImpl();

        InitializeMocks();

        SetupMocks();
    }

    private void InitializeMocks() {
        mockTargetUserInfo = mock(APDUserInfoType.class);
        mockTargetDRPUserInfo = mock(APDUserInfoType.class);
        mockUserInfoTypeForTargetUser = mock(UserInfoType.class);
        mockUserInfoTypeForTargetDRPUser = mock(UserInfoType.class);
        mockApdCommonType = mock(BaseAPDCommonType.class);
        mockAPDCommonUtilProxy = mock(APDCommonUtilProxy.class);
    }

    private void SetupMocks() {

        sut.setApdCommonUtilProxy(mockAPDCommonUtilProxy);

        when(mockApdCommonType.isSetTargetDRPUserInfo()).thenReturn(true);
        when(mockApdCommonType.getTargetUserInfo()).thenReturn(mockTargetUserInfo);
        when(mockApdCommonType.getTargetDRPUserInfo()).thenReturn(mockTargetDRPUserInfo);

        when(mockTargetUserInfo.getUserInfo()).thenReturn(mockUserInfoTypeForTargetUser);
        when(mockTargetDRPUserInfo.getUserInfo()).thenReturn(mockUserInfoTypeForTargetDRPUser);

        when(mockUserInfoTypeForTargetUser.getAppCodeArray()).thenReturn(userAppCodesWithIgnoreConnectAndComms);
        when(mockUserInfoTypeForTargetDRPUser.getAppCodeArray()).thenReturn(shopAppCodesWithPreferRCWeb);
    }

    @Test
    public void When_isEndpointRCWeb_is_called_it_should_return_false_when_TargetDRPUserInfo_is_not_set_in_BaseAPDCommonType() throws Exception {
        when(mockApdCommonType.isSetTargetDRPUserInfo()).thenReturn(false);

        boolean actualIsEndpointRcWeb = this.sut.isEndpointRCWeb(mockApdCommonType);

        assertFalse(actualIsEndpointRcWeb);
    }

    @Test
    public void When_isEndpointRCWeb_is_called_it_should_return_false_when_TargetUserInfo_is_not_set_in_BaseAPDCommonType() throws Exception {
        when(mockApdCommonType.getTargetUserInfo()).thenReturn(null);

        boolean actualIsEndpointRcWeb = this.sut.isEndpointRCWeb(mockApdCommonType);

        assertFalse(actualIsEndpointRcWeb);
    }

    @Test
    public void When_isEndpointRCWeb_is_called_it_should_return_false_when_the_User_does_not_have_the_ignore_RCConnect_and_RCComms_app_code() throws Exception {
        when(mockUserInfoTypeForTargetUser.getAppCodeArray()).thenReturn(userAppCodesWithoutIgnoreConnectAndComms);

        boolean actualIsEndpointRcWeb = this.sut.isEndpointRCWeb(mockApdCommonType);

        assertFalse(actualIsEndpointRcWeb);
    }

    @Test
    public void When_isEndpointRCWeb_is_called_it_should_return_false_when_the_Shop_does_not_have_the_prefer_RCWeb_app_code() throws Exception {
        when(mockUserInfoTypeForTargetDRPUser.getAppCodeArray()).thenReturn(shopAppCodesWithoutPreferRCWeb);

        boolean actualIsEndpointRcWeb = this.sut.isEndpointRCWeb(mockApdCommonType);

        assertFalse(actualIsEndpointRcWeb);
    }

    @Test
    public void When_isEndpointRCWeb_is_called_it_should_return_true_when_the_User_and_Shop_have_the_correct_app_codes() throws Exception {
        boolean actualIsEndpointRcWeb = this.sut.isEndpointRCWeb(mockApdCommonType);

        assertTrue(actualIsEndpointRcWeb);
    }
}
