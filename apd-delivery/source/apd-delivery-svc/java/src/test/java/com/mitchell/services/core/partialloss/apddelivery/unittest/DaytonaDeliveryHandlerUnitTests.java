package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDAlertInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.DaytonaDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.DaytonaDeliveryHandlerImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.CarrErrorDaoImpl;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DaytonaDeliveryHandlerUnitTests {

    private DaytonaDeliveryHandler sut;
    private APDDeliveryContextDocument mockContextDocument;
    private APDDeliveryContextType mockApdDeliveryContextType;
    private APDAlertInfoType apdAlertInfoType;
    private APDAlertInfoType apdAlertInfoTypeSuccess;
    private BaseAPDCommonType baseApdCommonType;
    private CarrErrorDaoImpl mockCarrErrorDao;
    private UserInfoProxy mockUserInfoProxy;
    private UserInfoType targetUserInfo;
    private String workItemId;

    private final String EXPECTED_CARR_SUCCESS_MESSAGE = "Upload received.";
    private final String EXPECTED_CLAIM_ID = "ZDS2C92X-01";
    private final long EXPECTED_FOLDER_ID = 100005231554L;
    private final long EXPECTED_TASK_ID = 100005037915L;
    private final String EXPECTED_CARR_ERROR_MESSAGE = "Missing UploadType - No MCF Type.";
    private final long EXPECTED_ERROR_CODE = 1;
    private final String EXPECTED_SHOP_CO_CODE = "TQ";
    private final String EXPECTED_SHOP_ORG_CODE = "401604"; //actual shop-user orgId in DEV

    @Before
    public void Setup() throws Exception {

        sut = new DaytonaDeliveryHandlerImpl();

        InitializeMocks();
        SetupMocks();
    }

    private void InitializeMocks() throws Exception, MitchellException, UnknownHostException {
        mockContextDocument = mock(APDDeliveryContextDocument.class);
        mockApdDeliveryContextType = mock(APDDeliveryContextType.class);
        mockCarrErrorDao = mock(CarrErrorDaoImpl.class);
        mockUserInfoProxy = mock(UserInfoProxy.class);
    }

    private void SetupMocks() throws MitchellException, Exception, UnknownHostException {

        APDDeliveryContextDocument apdContextSuccess = APDDeliveryContextDocument.Factory.parse(sampleSuccessApdDeliveryContext());
        apdAlertInfoTypeSuccess = apdContextSuccess.getAPDDeliveryContext().getAPDAlertInfo();
        APDDeliveryContextDocument apdContext = APDDeliveryContextDocument.Factory.parse(sampleFailureApdDeliveryContext());
        apdAlertInfoType = apdContext.getAPDDeliveryContext().getAPDAlertInfo();
        baseApdCommonType = apdAlertInfoType.getAPDCommonInfo();
        targetUserInfo = baseApdCommonType.getTargetUserInfo().getUserInfo();
        workItemId = baseApdCommonType.getWorkItemId();
        when(mockCarrErrorDao.getErrorCode(EXPECTED_CARR_ERROR_MESSAGE)).thenReturn(1L);
        when(mockUserInfoProxy.getCompanyCode(targetUserInfo.getOrgID(), workItemId)).thenReturn(EXPECTED_SHOP_CO_CODE);
        when(mockUserInfoProxy.getOrgCode(targetUserInfo.getOrgID(), workItemId)).thenReturn(EXPECTED_SHOP_ORG_CODE);

    }


    @Test
    public void it_should_return_correct_carr_success_message_from_context() throws Exception,  MitchellException, UnknownHostException {

        String carrMessage = apdAlertInfoTypeSuccess.getMessage();
        assertEquals(EXPECTED_CARR_SUCCESS_MESSAGE, carrMessage);
    }

    @Test
    public void it_should_return_correct_carr_error_message_from_context() throws Exception,  MitchellException, UnknownHostException {

        String carrMessage = apdAlertInfoType.getMessage();
        assertEquals(EXPECTED_CARR_ERROR_MESSAGE, carrMessage);
    }

    @Test
    public void it_should_return_correct_error_code_when_carr_error_message_is_passed() throws MitchellException {
        long errorCode = mockCarrErrorDao.getErrorCode(EXPECTED_CARR_ERROR_MESSAGE);
        assertEquals(EXPECTED_ERROR_CODE, errorCode);
    }

    @Test
    public void it_should_return_correct_claimNumber_from_context() {

        String claimNumber = baseApdCommonType.getClientClaimNumber();
        assertEquals(EXPECTED_CLAIM_ID, claimNumber);
    }

    @Test
    public void it_should_return_correct_folderId_from_context() {

        long folderID = apdAlertInfoType.getFolderAI();
        assertEquals(EXPECTED_FOLDER_ID, folderID);

    }

    @Test
    public void it_should_return_correct_taskId_from_context() {
        long taskId = Long.parseLong(apdAlertInfoType.getTaskID());
        assertEquals(EXPECTED_TASK_ID, taskId);
    }

    @Test
    public void it_should_return_correct_shopCoCd_from_context() throws MitchellException {

        String shopCoCd = mockUserInfoProxy.getCompanyCode(targetUserInfo.getOrgID(), workItemId);
        assertEquals(EXPECTED_SHOP_CO_CODE, shopCoCd);
    }

    @Test
    public void it_should_return_correct_shopOrdCd_from_context() throws MitchellException {

        String shopOrgCd = mockUserInfoProxy.getOrgCode(targetUserInfo.getOrgID(), workItemId);
        assertEquals(EXPECTED_SHOP_ORG_CODE, shopOrgCd);
    }

    private static String sampleSuccessApdDeliveryContext() {
        return "<apd:APDDeliveryContext Version=\"1.0\" xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\">\n" +
                "    <apd:MessageType>ALERT</apd:MessageType>\n" +
                "    <apd:APDAlertInfo>\n" +
                "        <apd:APDCommonInfo>\n" +
                "            <apd:InsCoCode>TQ</apd:InsCoCode>\n" +
                "            <apd:SourceUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401681</typ:OrgID>\n" +
                "                    <typ:OrgCode>BS</typ:OrgCode>\n" +
                "                    <typ:UserID>95183638</typ:UserID>\n" +
                "                    <typ:Guid>1234567890</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"\" Code=\"BS\" ID=\"307\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"Daytona TQ Shop 1\" Code=\"M95183638\" ID=\"401680\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"95183638\" ID=\"401681\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>MCMAD</typ:AppCode>\n" +
                "                    <typ:AppCode>MCMUPL</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>RCCOMM</typ:AppCode>\n" +
                "                    <typ:AppCode>RCWCOM</typ:AppCode>\n" +
                "                    <typ:AppCode>UMTYPB</typ:AppCode>\n" +
                "                    <typ:CrossOverInsuranceCompany>\n" +
                "                        <typ:CompanyID>TQ</typ:CompanyID>\n" +
                "                        <typ:CompanyName>Daytona Test</typ:CompanyName>\n" +
                "                        <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    </typ:CrossOverInsuranceCompany>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:SourceUserInfo>\n" +
                "            <apd:TargetUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401682</typ:OrgID>\n" +
                "                    <typ:OrgCode>TQ</typ:OrgCode>\n" +
                "                    <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    <typ:Guid>123456789</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"Daytona Test\" Code=\"TQ\" ID=\"368230\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"DEFAULT\" Code=\"99\" ID=\"401659\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"TQ066472\" ID=\"401682\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>CMAPES</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>WCAIRC</typ:AppCode>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:TargetUserInfo>\n" +
                "            <apd:TargetDRPUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401681</typ:OrgID>\n" +
                "                    <typ:OrgCode>BS</typ:OrgCode>\n" +
                "                    <typ:UserID>95183638</typ:UserID>\n" +
                "                    <typ:Guid>1234567890</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"\" Code=\"BS\" ID=\"307\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"Daytona TQ Shop 1\" Code=\"M95183638\" ID=\"401680\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"95183638\" ID=\"401681\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>MCMAD</typ:AppCode>\n" +
                "                    <typ:AppCode>MCMUPL</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>RCCOMM</typ:AppCode>\n" +
                "                    <typ:AppCode>RCWCOM</typ:AppCode>\n" +
                "                    <typ:AppCode>UMTYPB</typ:AppCode>\n" +
                "                    <typ:CrossOverInsuranceCompany>\n" +
                "                        <typ:CompanyID>TQ</typ:CompanyID>\n" +
                "                        <typ:CompanyName>Daytona Test</typ:CompanyName>\n" +
                "                        <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    </typ:CrossOverInsuranceCompany>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:TargetDRPUserInfo>\n" +
                "            <apd:ClaimNumber>ZDS2C92X-01</apd:ClaimNumber>\n" +
                "            <apd:ClientClaimNumber>ZDS2C92X-01</apd:ClientClaimNumber>\n" +
                "            <apd:ClaimId>100005596625</apd:ClaimId>\n" +
                "            <apd:WorkItemId>est_BS_95183638_20150128094943_ZDS2C92X-01_~0~781951</apd:WorkItemId>\n" +
                "            <apd:DateTime>2015-01-28T09:50:16.530-08:00</apd:DateTime>\n" +
                "        </apd:APDCommonInfo>\n" +
                "        <apd:Origin>CARR Standard Estimate Workflow</apd:Origin>\n" +
                "        <apd:AlertType>ACCEPTED</apd:AlertType>\n" +
                "        <apd:Message>Upload received.</apd:Message>\n" +
                "        <apd:EclaimEstId>ZDS2C92X-01</apd:EclaimEstId>\n" +
                "        <apd:SupplementNumber>0</apd:SupplementNumber>\n" +
                "        <apd:CorrectionNumber>0</apd:CorrectionNumber>\n" +
                "        <apd:FolderAI>100005231554</apd:FolderAI>\n" +
                "        <apd:McfFileName>est_BS_95183638_20150128094943_ZDS2C92X-01_~0~781951.MCF</apd:McfFileName>\n" +
                "        <apd:McfPackageType>MCF_XML_MIE_ESTIMATE</apd:McfPackageType>\n" +
                "        <apd:NoEstimateReviewFlag>false</apd:NoEstimateReviewFlag>\n" +
                "        <apd:EstimateDate>2015-01-20T10:14:48</apd:EstimateDate>\n" +
                "        <apd:TaskID>100005037915</apd:TaskID>\n" +
                "    </apd:APDAlertInfo>\n" +
                "</apd:APDDeliveryContext>";

    }


    private static String sampleFailureApdDeliveryContext() {
        return "<apd:APDDeliveryContext Version=\"1.0\" xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\">\n" +
                "    <apd:MessageType>ALERT</apd:MessageType>\n" +
                "    <apd:APDAlertInfo>\n" +
                "        <apd:APDCommonInfo>\n" +
                "            <apd:InsCoCode>TQ</apd:InsCoCode>\n" +
                "            <apd:SourceUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401681</typ:OrgID>\n" +
                "                    <typ:OrgCode>BS</typ:OrgCode>\n" +
                "                    <typ:UserID>95183638</typ:UserID>\n" +
                "                    <typ:Guid>1234567890</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"\" Code=\"BS\" ID=\"307\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"Daytona TQ Shop 1\" Code=\"M95183638\" ID=\"401680\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"95183638\" ID=\"401681\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>MCMAD</typ:AppCode>\n" +
                "                    <typ:AppCode>MCMUPL</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>RCCOMM</typ:AppCode>\n" +
                "                    <typ:AppCode>RCWCOM</typ:AppCode>\n" +
                "                    <typ:AppCode>UMTYPB</typ:AppCode>\n" +
                "                    <typ:CrossOverInsuranceCompany>\n" +
                "                        <typ:CompanyID>TQ</typ:CompanyID>\n" +
                "                        <typ:CompanyName>Daytona Test</typ:CompanyName>\n" +
                "                        <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    </typ:CrossOverInsuranceCompany>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:SourceUserInfo>\n" +
                "            <apd:TargetUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401682</typ:OrgID>\n" +
                "                    <typ:OrgCode>TQ</typ:OrgCode>\n" +
                "                    <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    <typ:Guid>123456789</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"Daytona Test\" Code=\"TQ\" ID=\"368230\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"DEFAULT\" Code=\"99\" ID=\"401659\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"TQ066472\" ID=\"401682\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>CMAPES</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>WCAIRC</typ:AppCode>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:TargetUserInfo>\n" +
                "            <apd:TargetDRPUserInfo>\n" +
                "                <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
                "                    <typ:OrgID>401681</typ:OrgID>\n" +
                "                    <typ:OrgCode>BS</typ:OrgCode>\n" +
                "                    <typ:UserID>95183638</typ:UserID>\n" +
                "                    <typ:Guid>1234567890</typ:Guid>\n" +
                "                    <typ:FirstName>Daytona</typ:FirstName>\n" +
                "                    <typ:LastName>TQ Shop 1</typ:LastName>\n" +
                "                    <typ:Email>Scott.Schmidl@mitchell.com</typ:Email>\n" +
                "                    <typ:UserHier>\n" +
                "                        <typ:HierNode Level=\"COMPANY\" Name=\"\" Code=\"BS\" ID=\"307\">\n" +
                "                            <typ:HierNode Level=\"OFFICE\" Name=\"Daytona TQ Shop 1\" Code=\"M95183638\" ID=\"401680\">\n" +
                "                                <typ:HierNode Level=\"USER\" Name=\"Daytona TQ Shop 1\" Code=\"95183638\" ID=\"401681\"/>\n" +
                "                            </typ:HierNode>\n" +
                "                        </typ:HierNode>\n" +
                "                    </typ:UserHier>\n" +
                "                    <typ:AppCode>MCMAD</typ:AppCode>\n" +
                "                    <typ:AppCode>MCMUPL</typ:AppCode>\n" +
                "                    <typ:AppCode>NETACT</typ:AppCode>\n" +
                "                    <typ:AppCode>RCCOMM</typ:AppCode>\n" +
                "                    <typ:AppCode>RCWCOM</typ:AppCode>\n" +
                "                    <typ:AppCode>UMTYPB</typ:AppCode>\n" +
                "                    <typ:CrossOverInsuranceCompany>\n" +
                "                        <typ:CompanyID>TQ</typ:CompanyID>\n" +
                "                        <typ:CompanyName>Daytona Test</typ:CompanyName>\n" +
                "                        <typ:UserID>TQ066472</typ:UserID>\n" +
                "                    </typ:CrossOverInsuranceCompany>\n" +
                "                    <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
                "                </apd:UserInfo>\n" +
                "            </apd:TargetDRPUserInfo>\n" +
                "            <apd:ClaimNumber>ZDS2C92X-01</apd:ClaimNumber>\n" +
                "            <apd:ClientClaimNumber>ZDS2C92X-01</apd:ClientClaimNumber>\n" +
                "            <apd:ClaimId>100005596625</apd:ClaimId>\n" +
                "            <apd:WorkItemId>est_BS_95183638_20150128094943_ZDS2C92X-01_~0~781951</apd:WorkItemId>\n" +
                "            <apd:DateTime>2015-01-28T09:50:16.530-08:00</apd:DateTime>\n" +
                "        </apd:APDCommonInfo>\n" +
                "        <apd:Origin>CARR Standard Estimate Workflow</apd:Origin>\n" +
                "        <apd:AlertType>REJECTED</apd:AlertType>\n" +
                "        <apd:Message>Missing UploadType - No MCF Type.</apd:Message>\n" +
                "        <apd:EclaimEstId>ZDS2C92X-01</apd:EclaimEstId>\n" +
                "        <apd:SupplementNumber>0</apd:SupplementNumber>\n" +
                "        <apd:CorrectionNumber>0</apd:CorrectionNumber>\n" +
                "        <apd:FolderAI>100005231554</apd:FolderAI>\n" +
                "        <apd:McfFileName>est_BS_95183638_20150128094943_ZDS2C92X-01_~0~781951.MCF</apd:McfFileName>\n" +
                "        <apd:McfPackageType>MCF_XML_MIE_ESTIMATE</apd:McfPackageType>\n" +
                "        <apd:NoEstimateReviewFlag>false</apd:NoEstimateReviewFlag>\n" +
                "        <apd:EstimateDate>2015-01-20T10:14:48</apd:EstimateDate>\n" +
                "        <apd:TaskID>100005037915</apd:TaskID>\n" +
                "    </apd:APDAlertInfo>\n" +
                "</apd:APDDeliveryContext>";
    }




}
