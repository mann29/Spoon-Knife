package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.*;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.AlertDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.DaytonaDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxyImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ECAlertProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserData;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelperImpl;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtilImpl;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Created by ss103327 on 1/26/2015.
 */
public class AlertDeliveryRCWebUnitTest {

    private AlertDeliveryHandler sut;

    private APDDeliveryUtil mockAPDDeliveryUtil;
    private BaseAPDCommonType mockBaseAPDCommonType;
    private APDDeliveryContextDocument mockContextDocument;
    private APDDeliveryContextType mockApdDeliveryContextType;
    private APDAlertInfoType mockApdAlertInfoType;
    private UserInfoType mockUserInfoType;
    private APDUserInfoType mockApdUserInfoType;
    private DaytonaDeliveryHandler mockDaytonaDeliveryHandler;


    @Before
    public void Setup() throws Exception {

        sut = new AlertDeliveryHandler();
        InitializeMocks();

        SetupMocks();
    }

    private void InitializeMocks() {

        mockAPDDeliveryUtil = mock(APDDeliveryUtil.class);
        mockBaseAPDCommonType = mock(BaseAPDCommonType.class);
        mockContextDocument = mock(APDDeliveryContextDocument.class);
        mockDaytonaDeliveryHandler = mock(DaytonaDeliveryHandler.class);
        mockApdAlertInfoType = mock(APDAlertInfoType.class);
        mockApdDeliveryContextType = mock(APDDeliveryContextType.class);
        mockApdUserInfoType = mock(APDUserInfoType.class);
        mockUserInfoType = mock(UserInfoType.class);

    }

    private void SetupMocks() throws Exception {
        sut.setApdDeliveryUtil(mockAPDDeliveryUtil);
        sut.setDaytonaDeliveryHandler(mockDaytonaDeliveryHandler);

        when(mockAPDDeliveryUtil.isEndpointPlatformWithOverride(mockBaseAPDCommonType)).thenReturn(true);
        when(mockContextDocument.getAPDDeliveryContext()).thenReturn(mockApdDeliveryContextType);
        when(mockApdDeliveryContextType.getAPDAlertInfo()).thenReturn(mockApdAlertInfoType);
        when(mockApdAlertInfoType.getAPDCommonInfo()).thenReturn(mockBaseAPDCommonType);
        when(mockApdAlertInfoType.getAPDCommonInfo().getTargetUserInfo()).thenReturn(mockApdUserInfoType);
        when(mockApdUserInfoType.getUserInfo()).thenReturn(mockUserInfoType);

    }

    @Test
    public void When_deliverArtifact_is_called_it_should_call_DaytonaDeliveryHandler_if_Endpoint_is_RCWEb() throws Exception {

        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType)).thenReturn(true);
        sut.deliverArtifact(mockContextDocument);
        verify(mockAPDDeliveryUtil).isEndpointRCWeb(mockBaseAPDCommonType);
        verify(mockDaytonaDeliveryHandler).deliverAlert(mockContextDocument);
    }
    
    /*@Test
    public void When_deliverArtifact_is_called_it_should_not_call_DaytonaDeliveryHandler_if_Endpoint_is_not_RCWeb() throws Exception {

        when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType)).thenReturn(false);

        verify(mockDaytonaDeliveryHandler, never());
    }*/

	@Test
	public void When_deliverArtifact_is_called_it_should_call_DaytonaDeliveryHandler_if_Endpoint_is_RCWEb1_True()
			throws Exception {
		AlertDeliveryHandler alertDeliveryHandler = new AlertDeliveryHandler();
		AlertDeliveryHandler alertDeliveryHandlerSpy = Mockito
				.spy(alertDeliveryHandler);

		when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType))
				.thenReturn(true);

		APDDeliveryUtilImpl apdDeliveryUtilImpl = Mockito
				.mock(APDDeliveryUtilImpl.class);
		alertDeliveryHandlerSpy.setApdDeliveryUtil(apdDeliveryUtilImpl);
		when(
				alertDeliveryHandlerSpy.getApdDeliveryUtil().getUserType(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				UserTypeConstants.SHOP_TYPE);

		alertDeliveryHandlerSpy
				.setApdCommonUtilProxy(new APDCommonUtilProxyImpl());

		UserHelperImpl userHelperImpl = new UserHelperImpl();

		UserHelperImpl userHelperImplSpy = Mockito.spy(userHelperImpl);
		doReturn(false).when(userHelperImplSpy).isHideNonNetworkSolution();
		UserData userData = Mockito.mock(UserData.class);
		doReturn(userData).when(userHelperImplSpy).checkUserData(
				Mockito.any(UserInfoDocument.class));
		doReturn(true).when(userData).isShopBasic();
		doReturn(true).when(userData).isShopPremium();

		alertDeliveryHandlerSpy.setUserHelper(userHelperImplSpy);

		APDDeliveryContextDocument apdDeliveryContextDocument = APDDeliveryContextDocument.Factory
				.newInstance();
		APDDeliveryContextType apdDeliveryContextType = apdDeliveryContextDocument
				.addNewAPDDeliveryContext();
		APDAlertInfoType apdAlertInfoType = apdDeliveryContextType
				.addNewAPDAlertInfo();
		BaseAPDCommonType bApdCommonType = apdAlertInfoType
				.addNewAPDCommonInfo();
		bApdCommonType.addNewTargetUserInfo().addNewUserInfo();

		apdAlertInfoType.setAlertType(APDAlertType.ACCEPTED);
		apdAlertInfoType.setMcfPackageType(APDMCFPackageTyp.Enum
				.forString("MCF_XML_MIE_ESTIMATE"));

		ADSProxy adsProxy = Mockito.mock(ADSProxy.class);
		alertDeliveryHandlerSpy.setAdsProxy(adsProxy);
		Mockito.doNothing()
				.when(adsProxy)
				.deliverUploadSuccessEmail4DRP(
						Mockito.any(APDDeliveryContextDocument.class));

		ECAlertProxy ecAlertProxy = Mockito.mock(ECAlertProxy.class);
		alertDeliveryHandlerSpy.setEcAlertProxy(ecAlertProxy);
		Mockito.doNothing()
				.when(ecAlertProxy)
				.sendUploadAcceptedAlert(
						Mockito.any(EClaimAlertRequestDocument.class));

		alertDeliveryHandlerSpy.deliverArtifact(apdDeliveryContextDocument);

		verify(adsProxy, Mockito.times(1)).deliverUploadSuccessEmail4DRP(
				Mockito.any(APDDeliveryContextDocument.class));

	}

	@Test
	public void When_deliverArtifact_is_called_it_should_call_DaytonaDeliveryHandler_if_Endpoint_is_RCWEb1_False()
			throws Exception {
		AlertDeliveryHandler alertDeliveryHandler = new AlertDeliveryHandler();
		AlertDeliveryHandler alertDeliveryHandlerSpy = Mockito
				.spy(alertDeliveryHandler);

		when(mockAPDDeliveryUtil.isEndpointRCWeb(mockBaseAPDCommonType))
				.thenReturn(true);

		APDDeliveryUtilImpl apdDeliveryUtilImpl = Mockito
				.mock(APDDeliveryUtilImpl.class);
		alertDeliveryHandlerSpy.setApdDeliveryUtil(apdDeliveryUtilImpl);
		when(
				alertDeliveryHandlerSpy.getApdDeliveryUtil().getUserType(
						Mockito.anyString(), Mockito.anyString())).thenReturn(
				UserTypeConstants.SHOP_TYPE);

		alertDeliveryHandlerSpy
				.setApdCommonUtilProxy(new APDCommonUtilProxyImpl());

		UserHelperImpl userHelperImpl = new UserHelperImpl();

		UserHelperImpl userHelperImplSpy = Mockito.spy(userHelperImpl);
		doReturn(false).when(userHelperImplSpy).isHideNonNetworkSolution();
		UserData userData = Mockito.mock(UserData.class);
		doReturn(userData).when(userHelperImplSpy).checkUserData(
				Mockito.any(UserInfoDocument.class));
		doReturn(true).when(userData).isShopBasic();
		doReturn(false).when(userData).isShopPremium();

		alertDeliveryHandlerSpy.setUserHelper(userHelperImplSpy);

		APDDeliveryContextDocument apdDeliveryContextDocument = APDDeliveryContextDocument.Factory
				.newInstance();
		APDDeliveryContextType apdDeliveryContextType = apdDeliveryContextDocument
				.addNewAPDDeliveryContext();
		APDAlertInfoType apdAlertInfoType = apdDeliveryContextType
				.addNewAPDAlertInfo();
		BaseAPDCommonType bApdCommonType = apdAlertInfoType
				.addNewAPDCommonInfo();
		bApdCommonType.addNewTargetUserInfo().addNewUserInfo();

		apdAlertInfoType.setAlertType(APDAlertType.ACCEPTED);
		apdAlertInfoType.setMcfPackageType(APDMCFPackageTyp.Enum
				.forString("MCF_XML_MIE_ESTIMATE"));

		ADSProxy adsProxy = Mockito.mock(ADSProxy.class);
		alertDeliveryHandlerSpy.setAdsProxy(adsProxy);
		Mockito.doNothing()
				.when(adsProxy)
				.deliverUploadSuccessEmail(
						Mockito.any(APDDeliveryContextDocument.class));

		ECAlertProxy ecAlertProxy = Mockito.mock(ECAlertProxy.class);
		alertDeliveryHandlerSpy.setEcAlertProxy(ecAlertProxy);
		Mockito.doNothing()
				.when(ecAlertProxy)
				.sendUploadAcceptedAlert(
						Mockito.any(EClaimAlertRequestDocument.class));

		alertDeliveryHandlerSpy.deliverArtifact(apdDeliveryContextDocument);

		verify(adsProxy, Mockito.times(1)).deliverUploadSuccessEmail(
				Mockito.any(APDDeliveryContextDocument.class));

	}
   
    
    


}
