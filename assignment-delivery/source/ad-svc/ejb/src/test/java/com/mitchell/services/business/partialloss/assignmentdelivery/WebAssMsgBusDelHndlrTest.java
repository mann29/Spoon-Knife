//package com.mitchell.services.business.partialloss.assignmentdelivery;
//
//import com.cieca.bms.CIECADocument;
//import com.mitchell.common.types.*;
//import com.mitchell.schemas.MitchellEnvelopeDocument;
//import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
//import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
//import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractDispatchReportBuilder;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.HandlerUtils;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.DocStoreClientProxy;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.WorkAssignmentProxy;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.BmsConverterFactoryImpl;
//import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl.WebAssMsgBusDelHndlr;
//import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
//import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
//import com.mitchell.utils.misc.UUIDFactory;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//
//import java.util.Calendar;
//
//import static junit.framework.Assert.assertEquals;
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({WebAssMsgBusDelHndlr.class, WorkflowMsgUtil.class, Calendar.class, UUIDFactory.class})
//
//public class WebAssMsgBusDelHndlrTest {
//
//    public static final long ANY_REFERENCE_ID = 101010L;
//    public static final long ANY_CLAIM_EXPOSURE_ID = 202020L;
//    public static final String ANY_UUID = "99999";
//
//    private WebAssMsgBusDelHndlr sut;
//
//    private HandlerUtils mockHandlerUtils;
//    private AbstractAssignmentDeliveryLogger mockMLogger;
//    private AbstractDispatchReportBuilder mockDrBuilder;
//    private DocStoreClientProxy mockDocumentStoreClientProxy;
//    private MockMessagePostingAgent mockMessagePostingAgent;
//    private AppLoggerBridge mockAppLoggerBridge;
//    private ErrorLoggingServiceWrapper mockErrorLoggingService;
//    private UserInfoServiceEJBRemote mockUserInfoClient;
//    private BmsConverterFactory mockBmsConverterFactory;
//    private WorkAssignmentProxy mockWorkAssignmentProxy;
//    private AssignmentDeliveryUtils mockAssignmentDeliveryUtils;
//    private AssignmentDeliveryConfigBridge mockAssignmentDeliveryConfigBridge;
//    private UUIDFactory mockUUIDFactory;
//
//    private APDDeliveryContextDocument mockAPDDeliveryContextDocument;
//    private APDDeliveryContextType mockAPDDeliveryContextType;
//
//    @Before
//    public void setup() {
//
//        sut = new WebAssMsgBusDelHndlr();
//
//        mockHandlerUtils = mock(HandlerUtils.class);
//        mockMLogger = mock(AbstractAssignmentDeliveryLogger.class);
//        mockDrBuilder = mock(AbstractDispatchReportBuilder.class);
//        mockDocumentStoreClientProxy = mock(DocStoreClientProxy.class);
//        mockMessagePostingAgent = new MockMessagePostingAgent();
//        mockBmsConverterFactory = new BmsConverterFactoryImpl();
//        mockAppLoggerBridge = mock(AppLoggerBridge.class);
//        mockErrorLoggingService = mock(ErrorLoggingServiceWrapper.class);
//        mockUserInfoClient = mock(UserInfoServiceEJBRemote.class);
//        mockWorkAssignmentProxy = mock(WorkAssignmentProxy.class);
//        mockAssignmentDeliveryUtils = mock(AssignmentDeliveryUtils.class);
//        mockAssignmentDeliveryConfigBridge = mock(AssignmentDeliveryConfigBridge.class);
//        mockUUIDFactory = mock(UUIDFactory.class);
//
//        mockStatic(Calendar.class);
//        mockStatic(UUIDFactory.class);
//
//        sut.setHandlerUtils(mockHandlerUtils);
//        sut.setLogger(mockMLogger);
//        sut.setDrBuilder(mockDrBuilder);
//        sut.setDocumentStoreClientProxy(mockDocumentStoreClientProxy);
//        sut.setMessagePostingAgent(mockMessagePostingAgent);
//        sut.setAppLoggerBridge(mockAppLoggerBridge);
//        sut.setErrorLoggingService(mockErrorLoggingService);
//        sut.setUserInfoClient(mockUserInfoClient);
//        sut.setBmsConverterFactory(mockBmsConverterFactory);
//        sut.setWorkAssignmentProxy(mockWorkAssignmentProxy);
//        sut.setAssignmentDeliveryUtils(mockAssignmentDeliveryUtils);
//        sut.setAssignmentDeliveryConfigBridge(mockAssignmentDeliveryConfigBridge);
//    }
//
//    @Test
//    public void when_deliver_Assignment_is_called_with_a_dispatch_assignment() throws Exception {
//
//        APDDeliveryContextDocument contextDocument = APDDeliveryContextDocument.Factory.parse(unparsedContextDocument);
//        AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory.parse(unparsedAAAInfoDoc);
//        CIECADocument ciecaDoc = CIECADocument.Factory.parse(unparsedCiecaDoc);
//        CIECADocument transformedCiecaDoc = CIECADocument.Factory.parse(unparsedTransformedCiecaDoc);
//        CrossOverUserInfoDocument crossOverUserInfoDocument = CrossOverUserInfoDocument.Factory.parse(unparsedCrossOverUserInfoDoc);
//        OrgInfoDocument orgInfoDocument = OrgInfoDocument.Factory.parse(unparsedOrgInfoDocument);
//        String expectedMWMDoc = MitchellWorkflowMessageDocument.Factory.parse(unparsedExpectedMWMDoc).toString();
//
//        when(mockHandlerUtils.getAAAInfoDocFromMitchellEnv(any(MitchellEnvelopeDocument.class), anyString(), anyBoolean())).thenReturn(aaaInfoDoc);
//        when(mockHandlerUtils.getCiecaDocumentFromMitchellEnvelope(any(MitchellEnvelopeDocument.class), anyString(), anyString())).thenReturn(ciecaDoc, transformedCiecaDoc);
//        when(mockUserInfoClient.getCrossOverUserInfo(anyString(), anyString())).thenReturn(crossOverUserInfoDocument);
//        when(mockUserInfoClient.getOrgInfo(anyString(), anyString(), anyString())).thenReturn(orgInfoDocument);
//        when(mockWorkAssignmentProxy.getWorkAssignmentReferenceID( anyLong() )).thenReturn(ANY_REFERENCE_ID);
//        when(mockWorkAssignmentProxy.getWorkAssignmentClaimExposureID( anyLong() )).thenReturn(ANY_CLAIM_EXPOSURE_ID);
//
//        when(UUIDFactory.getInstance()).thenReturn(mockUUIDFactory);
//        when(mockUUIDFactory.getUUID()).thenReturn(ANY_UUID);
//
//        sut.deliverAssignment(contextDocument);
//
//        String actualMWMDoc = mockMessagePostingAgent.getPayload().toString();
//
//        assertEquals(expectedMWMDoc, actualMWMDoc);
//    }
//
//    private String unparsedContextDocument = "<apd:APDDeliveryContext Version=\"1.0\" xmlns:typ=\"http://www.mitchell.com/common/types\" xmlns:sch=\"http://www.mitchell.com/schemas\" xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\">\n" +
//            "  <apd:MessageType>ORIGINAL_ESTIMATE</apd:MessageType>\n" +
//            "  <apd:APDAppraisalAssignmentInfo>\n" +
//            "    <apd:APDCommonInfo>\n" +
//            "      <apd:InsCoCode>IF</apd:InsCoCode>\n" +
//            "      <apd:SourceUserInfo>\n" +
//            "        <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "          <typ:OrgID>345976</typ:OrgID>\n" +
//            "          <typ:OrgCode>IF</typ:OrgCode>\n" +
//            "          <typ:UserID>PCS01</typ:UserID>\n" +
//            "          <typ:FirstName>PCS</typ:FirstName>\n" +
//            "          <typ:LastName>User</typ:LastName>\n" +
//            "          <typ:Email>russell.peters@mitchell.com</typ:Email>\n" +
//            "          <typ:UserHier>\n" +
//            "            <typ:HierNode ID=\"72586\" Code=\"IF\" Name=\"PROGRESSIVE INSURANCE\" Level=\"COMPANY\">\n" +
//            "              <typ:HierNode ID=\"75875\" Code=\"99\" Name=\"DEFAULT\" Level=\"REGION\">\n" +
//            "                <typ:HierNode ID=\"75876\" Code=\"99\" Name=\"DEFAULT\" Level=\"DIVISION\">\n" +
//            "                  <typ:HierNode ID=\"79939\" Code=\"9999\" Name=\"DEFAULT\" Level=\"OFFICE\">\n" +
//            "                    <typ:HierNode ID=\"345976\" Code=\"PCS01\" Name=\"PCS User\" Level=\"USER\"/>\n" +
//            "                  </typ:HierNode>\n" +
//            "                </typ:HierNode>\n" +
//            "              </typ:HierNode>\n" +
//            "            </typ:HierNode>\n" +
//            "          </typ:UserHier>\n" +
//            "          <typ:AppCode>ASGNWS</typ:AppCode>\n" +
//            "          <typ:AppCode>BRMMRL</typ:AppCode>\n" +
//            "          <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
//            "        </apd:UserInfo>\n" +
//            "      </apd:SourceUserInfo>\n" +
//            "      <apd:TargetUserInfo>\n" +
//            "        <apd:UserInfo>\n" +
//            "          <typ:OrgID>350785</typ:OrgID>\n" +
//            "          <typ:OrgCode>IF</typ:OrgCode>\n" +
//            "          <typ:UserID>IFWCRC02</typ:UserID>\n" +
//            "          <typ:FirstName>WCRC</typ:FirstName>\n" +
//            "          <typ:LastName>TEST</typ:LastName>\n" +
//            "          <typ:Email>sathish.venkatesan@mitchell.com</typ:Email>\n" +
//            "          <typ:UserHier>\n" +
//            "            <typ:HierNode ID=\"72586\" Code=\"IF\" Name=\"PROGRESSIVE INSURANCE\" Level=\"COMPANY\">\n" +
//            "              <typ:HierNode ID=\"75875\" Code=\"99\" Name=\"DEFAULT\" Level=\"REGION\">\n" +
//            "                <typ:HierNode ID=\"75876\" Code=\"99\" Name=\"DEFAULT\" Level=\"DIVISION\">\n" +
//            "                  <typ:HierNode ID=\"79939\" Code=\"9999\" Name=\"DEFAULT\" Level=\"OFFICE\">\n" +
//            "                    <typ:HierNode ID=\"350785\" Code=\"IFWCRC02\" Name=\"WCRC TEST\" Level=\"USER\"/>\n" +
//            "                  </typ:HierNode>\n" +
//            "                </typ:HierNode>\n" +
//            "              </typ:HierNode>\n" +
//            "            </typ:HierNode>\n" +
//            "          </typ:UserHier>\n" +
//            "          <typ:AppCode>CMAPES</typ:AppCode>\n" +
//            "          <typ:AppCode>NETACT</typ:AppCode>\n" +
//            "          <typ:StaffType>ADMINISTRATOR</typ:StaffType>\n" +
//            "        </apd:UserInfo>\n" +
//            "      </apd:TargetUserInfo>\n" +
//            "      <apd:TargetDRPUserInfo>\n" +
//            "        <apd:UserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "          <typ:OrgID>144475</typ:OrgID>\n" +
//            "          <typ:OrgCode>BS</typ:OrgCode>\n" +
//            "          <typ:UserID>841610</typ:UserID>\n" +
//            "          <typ:FirstName>CALIBER COLL</typ:FirstName>\n" +
//            "          <typ:LastName>- ANAHEIM</typ:LastName>\n" +
//            "          <typ:UserHier>\n" +
//            "            <typ:HierNode ID=\"307\" Code=\"BS\" Name=\"\" Level=\"COMPANY\">\n" +
//            "              <typ:HierNode ID=\"144474\" Code=\"P326\" Name=\"CALIBER COLL CNTR\" Level=\"OFFICE\">\n" +
//            "                <typ:HierNode ID=\"144475\" Code=\"841610\" Name=\"CALIBER COLL - ANAHEIM\" Level=\"USER\"/>\n" +
//            "              </typ:HierNode>\n" +
//            "            </typ:HierNode>\n" +
//            "          </typ:UserHier>\n" +
//            "          <typ:AppCode>ATG</typ:AppCode>\n" +
//            "          <typ:AppCode>ATGREF</typ:AppCode>\n" +
//            "          <typ:AppCode>CEGCLS</typ:AppCode>\n" +
//            "          <typ:AppCode>RCCOMM</typ:AppCode>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>AC</typ:CompanyID>\n" +
//            "            <typ:CompanyName>THE CLUB</typ:CompanyName>\n" +
//            "            <typ:UserID>ACWCRC02</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>MY</typ:CompanyID>\n" +
//            "            <typ:CompanyName>MERCURY INSURANCE GROUP</typ:CompanyName>\n" +
//            "            <typ:UserID>SVMYBS</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>IF</typ:CompanyID>\n" +
//            "            <typ:CompanyName>PROGRESSIVE INSURANCE</typ:CompanyName>\n" +
//            "            <typ:UserID>BW0663</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>GC</typ:CompanyID>\n" +
//            "            <typ:CompanyName>GMAC INSURANCE</typ:CompanyName>\n" +
//            "            <typ:UserID>RCGC01</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>IF</typ:CompanyID>\n" +
//            "            <typ:CompanyName>PROGRESSIVE INSURANCE</typ:CompanyName>\n" +
//            "            <typ:UserID>IFWCRC02</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>SF</typ:CompanyID>\n" +
//            "            <typ:CompanyName>STATE FARM INSURANCE COMPANIES</typ:CompanyName>\n" +
//            "            <typ:UserID>SFWCRC02</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>GC</typ:CompanyID>\n" +
//            "            <typ:CompanyName>GMAC INSURANCE</typ:CompanyName>\n" +
//            "            <typ:UserID>GCWCRC02</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>SF</typ:CompanyID>\n" +
//            "            <typ:CompanyName>STATE FARM INSURANCE COMPANIES</typ:CompanyName>\n" +
//            "            <typ:UserID>SFWCRC01</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:CrossOverInsuranceCompany>\n" +
//            "            <typ:CompanyID>MY</typ:CompanyID>\n" +
//            "            <typ:CompanyName>MERCURY INSURANCE GROUP</typ:CompanyName>\n" +
//            "            <typ:UserID>MYRDBS01</typ:UserID>\n" +
//            "          </typ:CrossOverInsuranceCompany>\n" +
//            "          <typ:StaffType>ESTIMATOR</typ:StaffType>\n" +
//            "        </apd:UserInfo>\n" +
//            "      </apd:TargetDRPUserInfo>\n" +
//            "      <apd:ClaimNumber>99-2861203-01</apd:ClaimNumber>\n" +
//            "      <apd:ClientClaimNumber>99-2861203-01</apd:ClientClaimNumber>\n" +
//            "      <apd:ClaimId>1000062878</apd:ClaimId>\n" +
//            "      <apd:WorkItemId>32826f0a-ffff-ffa6-2077-e893ab219f88</apd:WorkItemId>\n" +
//            "      <apd:DateTime>2012-06-28T02:59:56.701-07:00</apd:DateTime>\n" +
//            "    </apd:APDCommonInfo>\n" +
//            "    <apd:TaskId>1000045194</apd:TaskId>\n" +
//            "    <apd:MessageStatus>DISPATCHED</apd:MessageStatus>\n" +
//            "    <apd:AssignmentMitchellEnvelope>\n" +
//            "      <apd:MitchellEnvelope Version=\"1.0\">\n" +
//            "        <sch:EnvelopeContext>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>MitchellCompanyCode</sch:Name>\n" +
//            "            <sch:Value>IF</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>ReceivedDateTime</sch:Name>\n" +
//            "            <sch:Value>2012-06-28T02:53:25.260-07:00</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>MitchellWorkItemId</sch:Name>\n" +
//            "            <sch:Value>32826f0a-ffff-ffa6-2077-e893ab219f88</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>ClaimId</sch:Name>\n" +
//            "            <sch:Value>1000062878</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>ExposureId</sch:Name>\n" +
//            "            <sch:Value>1000073269</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "          <sch:NameValuePair>\n" +
//            "            <sch:Name>SystemUserId</sch:Name>\n" +
//            "            <sch:Value>PCS01</sch:Value>\n" +
//            "          </sch:NameValuePair>\n" +
//            "        </sch:EnvelopeContext>\n" +
//            "        <sch:EnvelopeBodyList>\n" +
//            "          <sch:EnvelopeBody>\n" +
//            "            <sch:Metadata>\n" +
//            "              <sch:Identifier>CIECABMSAssignmentAddRq</sch:Identifier>\n" +
//            "              <sch:MitchellDocumentType>CIECA_BMS_ASG_XML</sch:MitchellDocumentType>\n" +
//            "              <sch:XmlBeanClassname>com.cieca.bms.CIECADocument</sch:XmlBeanClassname>\n" +
//            "            </sch:Metadata>\n" +
//            "            <sch:Content>\n" +
//            "              <bms:CIECA xmlns:bms=\"http://www.cieca.com/BMS\" xmlns:sch=\"http://www.mitchell.com/schemas\">\n" +
//            "                <bms:AssignmentAddRq>\n" +
//            "                  <bms:RqUID>32826f0a-ffff-ffa6-2077-e893ab219f88</bms:RqUID>\n" +
//            "                  <bms:DocumentInfo>\n" +
//            "                    <bms:BMSVer>2.10.0</bms:BMSVer>\n" +
//            "                    <bms:DocumentType>A</bms:DocumentType>\n" +
//            "                    <bms:DocumentID>0</bms:DocumentID>\n" +
//            "                    <bms:DocumentVer>\n" +
//            "                      <bms:DocumentVerCode>EM</bms:DocumentVerCode>\n" +
//            "                      <bms:DocumentVerNum>0</bms:DocumentVerNum>\n" +
//            "                    </bms:DocumentVer>\n" +
//            "                    <bms:CreateDateTime>2012-06-28T02:53:25.261-07:00</bms:CreateDateTime>\n" +
//            "                  </bms:DocumentInfo>\n" +
//            "                  <bms:EventInfo>\n" +
//            "                    <bms:AssignmentEvent>\n" +
//            "                      <bms:CreateDateTime>2012-06-28T02:53:25.261-07:00</bms:CreateDateTime>\n" +
//            "                    </bms:AssignmentEvent>\n" +
//            "                  </bms:EventInfo>\n" +
//            "                  <bms:AdminInfo>\n" +
//            "                    <bms:InsuranceCompany>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:OrgInfo>\n" +
//            "                          <bms:CompanyName>PROGRESSIVE INSURANCE</bms:CompanyName>\n" +
//            "                        </bms:OrgInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:InsuranceCompany>\n" +
//            "                    <bms:PolicyHolder>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName>\n" +
//            "                            <bms:LastName>jack</bms:LastName>\n" +
//            "                          </bms:PersonName>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:PolicyHolder>\n" +
//            "                    <bms:Estimator>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName>\n" +
//            "                            <bms:FirstName>WCRC</bms:FirstName>\n" +
//            "                            <bms:LastName>TEST</bms:LastName>\n" +
//            "                          </bms:PersonName>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                            <bms:Address>\n" +
//            "                              <bms:Address1>6220 Greenwich Dr</bms:Address1>\n" +
//            "                              <bms:City>San Diego</bms:City>\n" +
//            "                              <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                              <bms:PostalCode>92122-5913</bms:PostalCode>\n" +
//            "                            </bms:Address>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                        <bms:ContactInfo>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>WP</bms:CommQualifier>\n" +
//            "                            <bms:CommPhone>800-1234546</bms:CommPhone>\n" +
//            "                          </bms:Communications>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>EM</bms:CommQualifier>\n" +
//            "                            <bms:CommEmail>sathish.venkatesan@mitchell.com</bms:CommEmail>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:ContactInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                      <bms:Affiliation>69</bms:Affiliation>\n" +
//            "                    </bms:Estimator>\n" +
//            "                    <bms:InspectionSite>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:OrgInfo>\n" +
//            "                          <bms:CompanyName>WCRC TEST</bms:CompanyName>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                            <bms:Address>\n" +
//            "                              <bms:Address1>6220 Greenwich Dr</bms:Address1>\n" +
//            "                              <bms:City>San Diego</bms:City>\n" +
//            "                              <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                              <bms:PostalCode>92122</bms:PostalCode>\n" +
//            "                            </bms:Address>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:OrgInfo>\n" +
//            "                        <bms:ContactInfo>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>CP</bms:CommQualifier>\n" +
//            "                            <bms:CommPhone>800-1234546</bms:CommPhone>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:ContactInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:InspectionSite>\n" +
//            "                    <bms:Submitter>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName/>\n" +
//            "                          <bms:IDInfo>\n" +
//            "                            <bms:IDQualifierCode>UserID</bms:IDQualifierCode>\n" +
//            "                            <bms:IDNum>PCS01</bms:IDNum>\n" +
//            "                          </bms:IDInfo>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:Submitter>\n" +
//            "                  </bms:AdminInfo>\n" +
//            "                  <bms:ClaimInfo>\n" +
//            "                    <bms:ClaimNum>99-2861203-01</bms:ClaimNum>\n" +
//            "                    <bms:PolicyInfo>\n" +
//            "                      <bms:CoverageInfo>\n" +
//            "                        <bms:Coverage>\n" +
//            "                          <bms:CoverageCategory>A</bms:CoverageCategory>\n" +
//            "                          <bms:DeductibleInfo>\n" +
//            "                            <bms:DeductibleStatus>FK</bms:DeductibleStatus>\n" +
//            "                            <bms:DeductibleAmt>0</bms:DeductibleAmt>\n" +
//            "                          </bms:DeductibleInfo>\n" +
//            "                        </bms:Coverage>\n" +
//            "                      </bms:CoverageInfo>\n" +
//            "                    </bms:PolicyInfo>\n" +
//            "                    <bms:LossInfo>\n" +
//            "                      <bms:Facts>\n" +
//            "                        <bms:LossDateTime>2012-06-27T00:00:00</bms:LossDateTime>\n" +
//            "                      </bms:Facts>\n" +
//            "                      <bms:TotalLossInd>N</bms:TotalLossInd>\n" +
//            "                    </bms:LossInfo>\n" +
//            "                  </bms:ClaimInfo>\n" +
//            "                  <bms:VehicleDamageAssignment>\n" +
//            "                    <bms:EstimatorIDs>\n" +
//            "                      <bms:CurrentEstimatorID>IFWCRC02</bms:CurrentEstimatorID>\n" +
//            "                      <bms:RoutingIDInfo>\n" +
//            "                        <bms:IDQualifierCode>WAGroupID</bms:IDQualifierCode>\n" +
//            "                        <bms:IDNum>IF003893</bms:IDNum>\n" +
//            "                      </bms:RoutingIDInfo>\n" +
//            "                    </bms:EstimatorIDs>\n" +
//            "                    <bms:VehicleInfo>\n" +
//            "                      <bms:VINInfo>\n" +
//            "                        <bms:VINAvailabilityCode>Unavailable</bms:VINAvailabilityCode>\n" +
//            "                      </bms:VINInfo>\n" +
//            "                      <bms:VehicleDesc>\n" +
//            "                        <bms:OdometerInfo>\n" +
//            "                          <bms:OdometerInd>false</bms:OdometerInd>\n" +
//            "                          <bms:OdometerReadingCode>false</bms:OdometerReadingCode>\n" +
//            "                        </bms:OdometerInfo>\n" +
//            "                      </bms:VehicleDesc>\n" +
//            "                      <bms:Condition>\n" +
//            "                        <bms:DrivableInd>U</bms:DrivableInd>\n" +
//            "                      </bms:Condition>\n" +
//            "                    </bms:VehicleInfo>\n" +
//            "                  </bms:VehicleDamageAssignment>\n" +
//            "                </bms:AssignmentAddRq>\n" +
//            "              </bms:CIECA>\n" +
//            "            </sch:Content>\n" +
//            "          </sch:EnvelopeBody>\n" +
//            "          <sch:EnvelopeBody>\n" +
//            "            <sch:Metadata>\n" +
//            "              <sch:Identifier>AdditionalAppraisalAssignmentInfo</sch:Identifier>\n" +
//            "              <sch:MitchellDocumentType>AdditionalAppraisalAssignmentInfo</sch:MitchellDocumentType>\n" +
//            "              <sch:XmlBeanClassname>com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument</sch:XmlBeanClassname>\n" +
//            "            </sch:Metadata>\n" +
//            "            <sch:Content>\n" +
//            "              <app:AdditionalAppraisalAssignmentInfo xmlns:app=\"http://www.mitchell.com/schemas/appraisalassignment\" xmlns:sch=\"http://www.mitchell.com/schemas\">\n" +
//            "                <app:AssignmentDetails>\n" +
//            "                  <app:InspectionSiteType>SHOP</app:InspectionSiteType>\n" +
//            "                  <app:SiteLocationId>350785</app:SiteLocationId>\n" +
//            "                  <app:InspectionSiteGeoCode>\n" +
//            "                    <app:AddressValidStatus>Valid</app:AddressValidStatus>\n" +
//            "                    <app:Latitude>32.8515243530273</app:Latitude>\n" +
//            "                    <app:Longitude>-117.18310546875</app:Longitude>\n" +
//            "                  </app:InspectionSiteGeoCode>\n" +
//            "                  <app:PrimaryContactType>Insured</app:PrimaryContactType>\n" +
//            "                  <app:IsTravelRequiredFlag>true</app:IsTravelRequiredFlag>\n" +
//            "                  <app:VehicleLocationZIPCodeSource>Primary</app:VehicleLocationZIPCodeSource>\n" +
//            "                </app:AssignmentDetails>\n" +
//            "                <app:NotificationDetails>\n" +
//            "                  <app:NotificationEmailTo NotifyRecipients=\"true\">\n" +
//            "                    <app:EmailAddress>sathish.venkatesan@mitchell.com</app:EmailAddress>\n" +
//            "                  </app:NotificationEmailTo>\n" +
//            "                </app:NotificationDetails>\n" +
//            "                <app:MOIDetails>\n" +
//            "                  <app:TempUserSelectedMOI>\n" +
//            "                    <app:MethodOfInspection>SCTW</app:MethodOfInspection>\n" +
//            "                    <app:ResourceSelectionDeferredFlag>false</app:ResourceSelectionDeferredFlag>\n" +
//            "                    <app:MOIOrgID>349136</app:MOIOrgID>\n" +
//            "                  </app:TempUserSelectedMOI>\n" +
//            "                  <app:UserSelectedMOI>\n" +
//            "                    <app:MethodOfInspection>SCTW</app:MethodOfInspection>\n" +
//            "                    <app:ResourceSelectionDeferredFlag>false</app:ResourceSelectionDeferredFlag>\n" +
//            "                    <app:MOIOrgID>349136</app:MOIOrgID>\n" +
//            "                  </app:UserSelectedMOI>\n" +
//            "                </app:MOIDetails>\n" +
//            "                <app:AdditionalAssignmentDetails>\n" +
//            "                  <app:IsCAT>false</app:IsCAT>\n" +
//            "                </app:AdditionalAssignmentDetails>\n" +
//            "                <app:AssignmentSaveStep>ASSIGNMENT_DETAILS</app:AssignmentSaveStep>\n" +
//            "                <app:PropertyInfo>\n" +
//            "                  <app:PropertyDamageAssignment>\n" +
//            "                    <app:PropertyType>MTR_VEH</app:PropertyType>\n" +
//            "                  </app:PropertyDamageAssignment>\n" +
//            "                </app:PropertyInfo>\n" +
//            "              </app:AdditionalAppraisalAssignmentInfo>\n" +
//            "            </sch:Content>\n" +
//            "          </sch:EnvelopeBody>\n" +
//            "          <sch:EnvelopeBody>\n" +
//            "            <sch:Metadata>\n" +
//            "              <sch:Identifier>AdditionalTaskConstraints</sch:Identifier>\n" +
//            "              <sch:MitchellDocumentType>AdditionalTaskConstraints</sch:MitchellDocumentType>\n" +
//            "              <sch:XmlBeanClassname>com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument</sch:XmlBeanClassname>\n" +
//            "            </sch:Metadata>\n" +
//            "            <sch:Content>\n" +
//            "              <wor:AdditionalTaskConstraints Version=\"1.0\" xmlns:wor=\"http://www.mitchell.com/schemas/workassignment\" xmlns:sch=\"http://www.mitchell.com/schemas\">\n" +
//            "                <wor:ScheduleConstraints>\n" +
//            "                  <wor:Priority PriorityValue=\"99\">STANDARD_PRIORITY</wor:Priority>\n" +
//            "                  <wor:Duration>PT45M</wor:Duration>\n" +
//            "                  <wor:PreferredScheduleDate>2012-06-29</wor:PreferredScheduleDate>\n" +
//            "                </wor:ScheduleConstraints>\n" +
//            "              </wor:AdditionalTaskConstraints>\n" +
//            "            </sch:Content>\n" +
//            "          </sch:EnvelopeBody>\n" +
//            "        </sch:EnvelopeBodyList>\n" +
//            "      </apd:MitchellEnvelope>\n" +
//            "    </apd:AssignmentMitchellEnvelope>\n" +
//            "  </apd:APDAppraisalAssignmentInfo>\n" +
//            "</apd:APDDeliveryContext>";
//
//    String unparsedAAAInfoDoc = "<app:AdditionalAppraisalAssignmentInfo xmlns:app=\"http://www.mitchell.com/schemas/appraisalassignment\" xmlns:sch=\"http://www.mitchell.com/schemas\" xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\">\n" +
//            "  <app:AssignmentDetails>\n" +
//            "    <app:InspectionSiteType>SHOP</app:InspectionSiteType>\n" +
//            "    <app:SiteLocationId>377834</app:SiteLocationId>\n" +
//            "    <app:InspectionSiteGeoCode>\n" +
//            "      <app:AddressValidStatus>Valid</app:AddressValidStatus>\n" +
//            "      <app:Latitude>32.83204</app:Latitude>\n" +
//            "      <app:Longitude>-117.2048</app:Longitude>\n" +
//            "    </app:InspectionSiteGeoCode>\n" +
//            "    <app:PrimaryContactType>Insured</app:PrimaryContactType>\n" +
//            "    <app:IsTravelRequiredFlag>true</app:IsTravelRequiredFlag>\n" +
//            "    <app:VehicleLocationZIPCode>92117</app:VehicleLocationZIPCode>\n" +
//            "    <app:VehicleLocationZIPCodeSource>Primary</app:VehicleLocationZIPCodeSource>\n" +
//            "  </app:AssignmentDetails>\n" +
//            "  <app:VehicleDetails>\n" +
//            "    <app:VehicleType>\n" +
//            "      <app:ID>1</app:ID>\n" +
//            "      <app:Value>Car</app:Value>\n" +
//            "    </app:VehicleType>\n" +
//            "    <app:Year>2006</app:Year>\n" +
//            "    <app:Make>\n" +
//            "      <app:ID>98</app:ID>\n" +
//            "      <app:Value>Aston Martin</app:Value>\n" +
//            "    </app:Make>\n" +
//            "    <app:Model>\n" +
//            "      <app:ID>1802</app:ID>\n" +
//            "      <app:Value>DB9</app:Value>\n" +
//            "    </app:Model>\n" +
//            "    <app:SubModel>\n" +
//            "      <app:ID>1090</app:ID>\n" +
//            "      <app:Value>Volante</app:Value>\n" +
//            "    </app:SubModel>\n" +
//            "    <app:BodyStyle>\n" +
//            "      <app:ID>12</app:ID>\n" +
//            "      <app:Value>2 Door Conv</app:Value>\n" +
//            "    </app:BodyStyle>\n" +
//            "    <app:Engine>\n" +
//            "      <app:ID>234</app:ID>\n" +
//            "      <app:Value>6.0L 12 Cyl Gas Injected</app:Value>\n" +
//            "    </app:Engine>\n" +
//            "    <app:Transmission>\n" +
//            "      <app:ID>19</app:ID>\n" +
//            "      <app:Value>6 Speed Auto Trans</app:Value>\n" +
//            "    </app:Transmission>\n" +
//            "    <app:DriveTrain>\n" +
//            "      <app:ID>7</app:ID>\n" +
//            "      <app:Value>RWD</app:Value>\n" +
//            "    </app:DriveTrain>\n" +
//            "    <app:MitchellVID>127412</app:MitchellVID>\n" +
//            "  </app:VehicleDetails>\n" +
//            "  <app:NotificationDetails>\n" +
//            "    <app:NotificationEmailTo NotifyRecipients=\"false\">\n" +
//            "      <app:EmailAddress>asaf.ptahia@mitchell.com</app:EmailAddress>\n" +
//            "    </app:NotificationEmailTo>\n" +
//            "  </app:NotificationDetails>\n" +
//            "  <app:AssociatedAttachments>\n" +
//            "    <app:FileAttachments>\n" +
//            "      <app:FileAttachment>\n" +
//            "        <app:AttachmentType>IMG</app:AttachmentType>\n" +
//            "        <app:AltAttachmentType>2</app:AltAttachmentType>\n" +
//            "        <app:FileType>IMG</app:FileType>\n" +
//            "        <app:FileName>92-obj-15.JPG</app:FileName>\n" +
//            "        <app:FileTitle>Photos</app:FileTitle>\n" +
//            "        <app:DocStoreID>357670722</app:DocStoreID>\n" +
//            "      </app:FileAttachment>\n" +
//            "    </app:FileAttachments>\n" +
//            "  </app:AssociatedAttachments>\n" +
//            "  <app:MOIDetails>\n" +
//            "    <app:TempUserSelectedMOI>\n" +
//            "      <app:MethodOfInspection>NNS</app:MethodOfInspection>\n" +
//            "      <app:ResourceSelectionDeferredFlag>false</app:ResourceSelectionDeferredFlag>\n" +
//            "      <app:MOIOrgID>377834</app:MOIOrgID>\n" +
//            "    </app:TempUserSelectedMOI>\n" +
//            "    <app:UserSelectedMOI>\n" +
//            "      <app:MethodOfInspection>NNS</app:MethodOfInspection>\n" +
//            "      <app:ResourceSelectionDeferredFlag>false</app:ResourceSelectionDeferredFlag>\n" +
//            "      <app:MOIOrgID>377834</app:MOIOrgID>\n" +
//            "    </app:UserSelectedMOI>\n" +
//            "  </app:MOIDetails>\n" +
//            "  <app:AdditionalAssignmentDetails>\n" +
//            "    <app:IsCAT>false</app:IsCAT>\n" +
//            "  </app:AdditionalAssignmentDetails>\n" +
//            "  <app:AssignmentSaveStep>ASSIGNMENT_DETAILS</app:AssignmentSaveStep>\n" +
//            "  <app:PropertyInfo>\n" +
//            "    <app:PropertyDamageAssignment>\n" +
//            "      <app:PropertyType>MTR_VEH</app:PropertyType>\n" +
//            "    </app:PropertyDamageAssignment>\n" +
//            "  </app:PropertyInfo>\n" +
//            "</app:AdditionalAppraisalAssignmentInfo>";
//
//    private String unparsedCiecaDoc = "<bms:CIECA xmlns:bms=\"http://www.cieca.com/BMS\">\n" +
//            "                <bms:AssignmentAddRq>\n" +
//            "                  <bms:RqUID>d8b7ec6f-ac18-371c-26e9-31878c92eb77</bms:RqUID>\n" +
//            "                  <bms:DocumentInfo>\n" +
//            "                    <bms:BMSVer>2.10.0</bms:BMSVer>\n" +
//            "                    <bms:DocumentType>A</bms:DocumentType>\n" +
//            "                    <bms:DocumentID>0</bms:DocumentID>\n" +
//            "                    <bms:DocumentVer>\n" +
//            "                      <bms:DocumentVerCode>EM</bms:DocumentVerCode>\n" +
//            "                      <bms:DocumentVerNum>0</bms:DocumentVerNum>\n" +
//            "                    </bms:DocumentVer>\n" +
//            "                    <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "                  </bms:DocumentInfo>\n" +
//            "                  <bms:EventInfo>\n" +
//            "                    <bms:AssignmentEvent>\n" +
//            "                      <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "                    </bms:AssignmentEvent>\n" +
//            "                  </bms:EventInfo>\n" +
//            "                  <bms:AdminInfo>\n" +
//            "                    <bms:InsuranceCompany>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:OrgInfo>\n" +
//            "                          <bms:CompanyName>AAA Northern CA</bms:CompanyName>\n" +
//            "                        </bms:OrgInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:InsuranceCompany>\n" +
//            "                    <bms:PolicyHolder>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName>\n" +
//            "                            <bms:FirstName>Colin</bms:FirstName>\n" +
//            "                            <bms:LastName>Christ</bms:LastName>\n" +
//            "                          </bms:PersonName>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                            <bms:Address>\n" +
//            "                              <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                            </bms:Address>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:PolicyHolder>\n" +
//            "                    <bms:Estimator>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName>\n" +
//            "                            <bms:FirstName>a</bms:FirstName>\n" +
//            "                            <bms:LastName>p</bms:LastName>\n" +
//            "                          </bms:PersonName>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                            <bms:Address>\n" +
//            "                              <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                              <bms:City>San Diego</bms:City>\n" +
//            "                              <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                              <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                              <bms:CountryCode>United States</bms:CountryCode>\n" +
//            "                            </bms:Address>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                        <bms:ContactInfo>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>EM</bms:CommQualifier>\n" +
//            "                            <bms:CommEmail>asaf.ptahia@mitchell.com</bms:CommEmail>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:ContactInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                      <bms:Affiliation>69</bms:Affiliation>\n" +
//            "                    </bms:Estimator>\n" +
//            "                    <bms:InspectionSite>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:OrgInfo>\n" +
//            "                          <bms:CompanyName>a p</bms:CompanyName>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                            <bms:Address>\n" +
//            "                              <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                              <bms:City>San Diego</bms:City>\n" +
//            "                              <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                              <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                              <bms:CountryCode>US</bms:CountryCode>\n" +
//            "                            </bms:Address>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:OrgInfo>\n" +
//            "                        <bms:ContactInfo>\n" +
//            "                          <bms:Communications>\n" +
//            "                            <bms:CommQualifier>CP</bms:CommQualifier>\n" +
//            "                            <bms:CommPhone>777-7777777</bms:CommPhone>\n" +
//            "                          </bms:Communications>\n" +
//            "                        </bms:ContactInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:InspectionSite>\n" +
//            "                    <bms:Submitter>\n" +
//            "                      <bms:Party>\n" +
//            "                        <bms:PersonInfo>\n" +
//            "                          <bms:PersonName/>\n" +
//            "                          <bms:IDInfo>\n" +
//            "                            <bms:IDQualifierCode>UserID</bms:IDQualifierCode>\n" +
//            "                            <bms:IDNum>OAAJDIS1</bms:IDNum>\n" +
//            "                          </bms:IDInfo>\n" +
//            "                        </bms:PersonInfo>\n" +
//            "                      </bms:Party>\n" +
//            "                    </bms:Submitter>\n" +
//            "                  </bms:AdminInfo>\n" +
//            "                  <bms:ClaimInfo>\n" +
//            "                    <bms:ClaimNum>4547.01</bms:ClaimNum>\n" +
//            "                    <bms:PolicyInfo>\n" +
//            "                      <bms:CoverageInfo>\n" +
//            "                        <bms:Coverage>\n" +
//            "                          <bms:CoverageCategory>C</bms:CoverageCategory>\n" +
//            "                          <bms:DeductibleInfo>\n" +
//            "                            <bms:DeductibleStatus>FK</bms:DeductibleStatus>\n" +
//            "                            <bms:DeductibleAmt>0</bms:DeductibleAmt>\n" +
//            "                          </bms:DeductibleInfo>\n" +
//            "                        </bms:Coverage>\n" +
//            "                      </bms:CoverageInfo>\n" +
//            "                    </bms:PolicyInfo>\n" +
//            "                    <bms:LossInfo>\n" +
//            "                      <bms:Facts>\n" +
//            "                        <bms:LossDateTime>2014-05-06T00:00:00</bms:LossDateTime>\n" +
//            "                      </bms:Facts>\n" +
//            "                      <bms:TotalLossInd>N</bms:TotalLossInd>\n" +
//            "                    </bms:LossInfo>\n" +
//            "                  </bms:ClaimInfo>\n" +
//            "                  <bms:VehicleDamageAssignment>\n" +
//            "                    <bms:EstimatorIDs>\n" +
//            "                      <bms:CurrentEstimatorID>OAAPBS01</bms:CurrentEstimatorID>\n" +
//            "                      <bms:RoutingIDInfo>\n" +
//            "                        <bms:IDQualifierCode>WAGroupID</bms:IDQualifierCode>\n" +
//            "                        <bms:IDNum>SHOPHOLDING</bms:IDNum>\n" +
//            "                      </bms:RoutingIDInfo>\n" +
//            "                    </bms:EstimatorIDs>\n" +
//            "                    <bms:VehicleInfo>\n" +
//            "                      <bms:VINInfo>\n" +
//            "                        <bms:VINAvailabilityCode>Unavailable</bms:VINAvailabilityCode>\n" +
//            "                      </bms:VINInfo>\n" +
//            "                      <bms:VehicleDesc>\n" +
//            "                        <bms:ModelYear>2006</bms:ModelYear>\n" +
//            "                        <bms:MakeDesc>Aston Martin</bms:MakeDesc>\n" +
//            "                        <bms:ModelName>DB9</bms:ModelName>\n" +
//            "                        <bms:SubModelDesc>Volante</bms:SubModelDesc>\n" +
//            "                        <bms:VehicleType>Car</bms:VehicleType>\n" +
//            "                        <bms:OdometerInfo>\n" +
//            "                          <bms:OdometerInd>true</bms:OdometerInd>\n" +
//            "                          <bms:OdometerReading>456345</bms:OdometerReading>\n" +
//            "                          <bms:OdometerReadingCode>false</bms:OdometerReadingCode>\n" +
//            "                        </bms:OdometerInfo>\n" +
//            "                      </bms:VehicleDesc>\n" +
//            "                      <bms:Body>\n" +
//            "                        <bms:BodyStyle>2 Door Conv</bms:BodyStyle>\n" +
//            "                      </bms:Body>\n" +
//            "                      <bms:Powertrain>\n" +
//            "                        <bms:EngineDesc>6.0L 12 Cyl Gas Injected</bms:EngineDesc>\n" +
//            "                        <bms:TransmissionInfo>\n" +
//            "                          <bms:TransmissionCode>19</bms:TransmissionCode>\n" +
//            "                          <bms:TransmissionDesc>6 Speed Auto Trans</bms:TransmissionDesc>\n" +
//            "                        </bms:TransmissionInfo>\n" +
//            "                        <bms:Configuration>RWD</bms:Configuration>\n" +
//            "                      </bms:Powertrain>\n" +
//            "                      <bms:Condition>\n" +
//            "                        <bms:DrivableInd>U</bms:DrivableInd>\n" +
//            "                      </bms:Condition>\n" +
//            "                    </bms:VehicleInfo>\n" +
//            "                    <bms:AssignmentMemo>please go to Daytona!</bms:AssignmentMemo>\n" +
//            "                  </bms:VehicleDamageAssignment>\n" +
//            "                </bms:AssignmentAddRq>\n" +
//            "              </bms:CIECA>";
//
//    private String unparsedTransformedCiecaDoc = "<bms:CIECA xmlns:bms=\"http://www.cieca.com/BMS\">\n" +
//            "  <bms:AssignmentAddRq>\n" +
//            "    <bms:RqUID>d8b7ec6f-ac18-371c-26e9-31878c92eb77</bms:RqUID>\n" +
//            "    <bms:DocumentInfo>\n" +
//            "      <bms:BMSVer>2.10.0</bms:BMSVer>\n" +
//            "      <bms:DocumentType>A</bms:DocumentType>\n" +
//            "      <bms:DocumentID>0</bms:DocumentID>\n" +
//            "      <bms:DocumentVer>\n" +
//            "        <bms:DocumentVerCode>EM</bms:DocumentVerCode>\n" +
//            "        <bms:DocumentVerNum>0</bms:DocumentVerNum>\n" +
//            "      </bms:DocumentVer>\n" +
//            "      <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "    </bms:DocumentInfo>\n" +
//            "    <bms:EventInfo>\n" +
//            "      <bms:AssignmentEvent>\n" +
//            "        <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "      </bms:AssignmentEvent>\n" +
//            "    </bms:EventInfo>\n" +
//            "    <bms:AdminInfo>\n" +
//            "      <bms:InsuranceCompany>\n" +
//            "        <bms:Party>\n" +
//            "          <bms:OrgInfo>\n" +
//            "            <bms:CompanyName>AAA Northern CA</bms:CompanyName>\n" +
//            "          </bms:OrgInfo>\n" +
//            "        </bms:Party>\n" +
//            "      </bms:InsuranceCompany>\n" +
//            "      <bms:PolicyHolder>\n" +
//            "        <bms:Party>\n" +
//            "          <bms:PersonInfo>\n" +
//            "            <bms:PersonName>\n" +
//            "              <bms:FirstName>Colin</bms:FirstName>\n" +
//            "              <bms:LastName>Christ</bms:LastName>\n" +
//            "            </bms:PersonName>\n" +
//            "            <bms:Communications>\n" +
//            "              <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "              <bms:Address>\n" +
//            "                <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "              </bms:Address>\n" +
//            "            </bms:Communications>\n" +
//            "          </bms:PersonInfo>\n" +
//            "        </bms:Party>\n" +
//            "      </bms:PolicyHolder>\n" +
//            "      <bms:Estimator>\n" +
//            "        <bms:Party>\n" +
//            "          <bms:PersonInfo>\n" +
//            "            <bms:PersonName>\n" +
//            "              <bms:FirstName>a</bms:FirstName>\n" +
//            "              <bms:LastName>p</bms:LastName>\n" +
//            "            </bms:PersonName>\n" +
//            "            <bms:Communications>\n" +
//            "              <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "              <bms:Address>\n" +
//            "                <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                <bms:City>San Diego</bms:City>\n" +
//            "                <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                <bms:CountryCode>United States</bms:CountryCode>\n" +
//            "              </bms:Address>\n" +
//            "            </bms:Communications>\n" +
//            "          </bms:PersonInfo>\n" +
//            "          <bms:ContactInfo>\n" +
//            "            <bms:Communications>\n" +
//            "              <bms:CommQualifier>EM</bms:CommQualifier>\n" +
//            "              <bms:CommEmail>asaf.ptahia@mitchell.com</bms:CommEmail>\n" +
//            "            </bms:Communications>\n" +
//            "          </bms:ContactInfo>\n" +
//            "        </bms:Party>\n" +
//            "        <bms:Affiliation>69</bms:Affiliation>\n" +
//            "      </bms:Estimator>\n" +
//            "      <bms:InspectionSite>\n" +
//            "        <bms:Party>\n" +
//            "          <bms:OrgInfo>\n" +
//            "            <bms:CompanyName>a p</bms:CompanyName>\n" +
//            "            <bms:Communications>\n" +
//            "              <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "              <bms:Address>\n" +
//            "                <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                <bms:City>San Diego</bms:City>\n" +
//            "                <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                <bms:CountryCode>US</bms:CountryCode>\n" +
//            "              </bms:Address>\n" +
//            "            </bms:Communications>\n" +
//            "          </bms:OrgInfo>\n" +
//            "          <bms:ContactInfo>\n" +
//            "            <bms:Communications>\n" +
//            "              <bms:CommQualifier>CP</bms:CommQualifier>\n" +
//            "              <bms:CommPhone>777-7777777</bms:CommPhone>\n" +
//            "            </bms:Communications>\n" +
//            "          </bms:ContactInfo>\n" +
//            "        </bms:Party>\n" +
//            "      </bms:InspectionSite>\n" +
//            "      <bms:Submitter>\n" +
//            "        <bms:Party>\n" +
//            "          <bms:PersonInfo>\n" +
//            "            <bms:PersonName/>\n" +
//            "            <bms:IDInfo>\n" +
//            "              <bms:IDQualifierCode>UserID</bms:IDQualifierCode>\n" +
//            "              <bms:IDNum>OAAJDIS1</bms:IDNum>\n" +
//            "            </bms:IDInfo>\n" +
//            "          </bms:PersonInfo>\n" +
//            "        </bms:Party>\n" +
//            "      </bms:Submitter>\n" +
//            "    </bms:AdminInfo>\n" +
//            "    <bms:ClaimInfo>\n" +
//            "      <bms:ClaimNum>4547.01</bms:ClaimNum>\n" +
//            "      <bms:PolicyInfo>\n" +
//            "        <bms:CoverageInfo>\n" +
//            "          <bms:Coverage>\n" +
//            "            <bms:CoverageCategory>C</bms:CoverageCategory>\n" +
//            "            <bms:DeductibleInfo>\n" +
//            "              <bms:DeductibleStatus>FK</bms:DeductibleStatus>\n" +
//            "              <bms:DeductibleAmt>0</bms:DeductibleAmt>\n" +
//            "            </bms:DeductibleInfo>\n" +
//            "          </bms:Coverage>\n" +
//            "        </bms:CoverageInfo>\n" +
//            "      </bms:PolicyInfo>\n" +
//            "      <bms:LossInfo>\n" +
//            "        <bms:Facts>\n" +
//            "          <bms:LossDateTime>2014-05-06T00:00:00</bms:LossDateTime>\n" +
//            "        </bms:Facts>\n" +
//            "        <bms:TotalLossInd>N</bms:TotalLossInd>\n" +
//            "      </bms:LossInfo>\n" +
//            "    </bms:ClaimInfo>\n" +
//            "    <bms:VehicleDamageAssignment>\n" +
//            "      <bms:EstimatorIDs>\n" +
//            "        <bms:CurrentEstimatorID>OAAPBS01</bms:CurrentEstimatorID>\n" +
//            "        <bms:RoutingIDInfo>\n" +
//            "          <bms:IDQualifierCode>WAGroupID</bms:IDQualifierCode>\n" +
//            "          <bms:IDNum>SHOPHOLDING</bms:IDNum>\n" +
//            "        </bms:RoutingIDInfo>\n" +
//            "      </bms:EstimatorIDs>\n" +
//            "      <bms:VehicleInfo>\n" +
//            "        <bms:VINInfo>\n" +
//            "          <bms:VINAvailabilityCode>Unavailable</bms:VINAvailabilityCode>\n" +
//            "        </bms:VINInfo>\n" +
//            "        <bms:VehicleDesc>\n" +
//            "          <bms:ModelYear>2006</bms:ModelYear>\n" +
//            "          <bms:MakeDesc>Aston Martin</bms:MakeDesc>\n" +
//            "          <bms:ModelName>DB9</bms:ModelName>\n" +
//            "          <bms:SubModelDesc>Volante</bms:SubModelDesc>\n" +
//            "          <bms:VehicleType>Car</bms:VehicleType>\n" +
//            "          <bms:OdometerInfo>\n" +
//            "            <bms:OdometerInd>true</bms:OdometerInd>\n" +
//            "            <bms:OdometerReading>456345</bms:OdometerReading>\n" +
//            "            <bms:OdometerReadingCode>false</bms:OdometerReadingCode>\n" +
//            "          </bms:OdometerInfo>\n" +
//            "        </bms:VehicleDesc>\n" +
//            "        <bms:Body>\n" +
//            "          <bms:BodyStyle>2 Door Conv</bms:BodyStyle>\n" +
//            "        </bms:Body>\n" +
//            "        <bms:Powertrain>\n" +
//            "          <bms:EngineDesc>6.0L 12 Cyl Gas Injected</bms:EngineDesc>\n" +
//            "          <bms:TransmissionInfo>\n" +
//            "            <bms:TransmissionCode>19</bms:TransmissionCode>\n" +
//            "            <bms:TransmissionDesc>6 Speed Auto Trans</bms:TransmissionDesc>\n" +
//            "          </bms:TransmissionInfo>\n" +
//            "          <bms:Configuration>RWD</bms:Configuration>\n" +
//            "        </bms:Powertrain>\n" +
//            "        <bms:Condition>\n" +
//            "          <bms:DrivableInd>U</bms:DrivableInd>\n" +
//            "        </bms:Condition>\n" +
//            "      </bms:VehicleInfo>\n" +
//            "      <bms:AssignmentMemo>please go to Daytona!</bms:AssignmentMemo>\n" +
//            "    </bms:VehicleDamageAssignment>\n" +
//            "  </bms:AssignmentAddRq>\n" +
//            "</bms:CIECA>";
//
//    private String unparsedCrossOverUserInfoDoc = "<typ:CrossOverUserInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "  <typ:NonStaffInfo>\n" +
//            "    <typ:NonStaffOrgID>399503</typ:NonStaffOrgID>\n" +
//            "    <typ:NonStaffOrgCode>1000121</typ:NonStaffOrgCode>\n" +
//            "    <typ:NonStaffCompanyCode>BS</typ:NonStaffCompanyCode>\n" +
//            "    <typ:NonStaffBusinessType>BS</typ:NonStaffBusinessType>\n" +
//            "  </typ:NonStaffInfo>\n" +
//            "  <typ:OnlineInfo>\n" +
//            "    <typ:OnlineOffice>\n" +
//            "      <typ:OnlineOfficeOrgID>384713</typ:OnlineOfficeOrgID>\n" +
//            "      <typ:OnlineOfficeOrgCode>55307</typ:OnlineOfficeOrgCode>\n" +
//            "      <typ:OnlineOfficeCoCode>XZ</typ:OnlineOfficeCoCode>\n" +
//            "      <typ:OnlineUsers>\n" +
//            "        <typ:OnlineUserOrgID>384714</typ:OnlineUserOrgID>\n" +
//            "        <typ:OnlineUserOrgCode IsPrimaryUser=\"true\">55307_1000121</typ:OnlineUserOrgCode>\n" +
//            "      </typ:OnlineUsers>\n" +
//            "    </typ:OnlineOffice>\n" +
//            "  </typ:OnlineInfo>\n" +
//            "  <typ:ReviewerInfo>\n" +
//            "    <typ:ReviewerOrgID>377834</typ:ReviewerOrgID>\n" +
//            "    <typ:ReviewerOrgCode>OAAPBS01</typ:ReviewerOrgCode>\n" +
//            "    <typ:ReviewerCompanyCode>OA</typ:ReviewerCompanyCode>\n" +
//            "  </typ:ReviewerInfo>\n" +
//            "</typ:CrossOverUserInfo>";
//
//    private String unparsedOrgInfoDocument = "<typ:OrgInfo xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "  <typ:OrgID>377834</typ:OrgID>\n" +
//            "  <typ:OrgCode>OAAPBS01</typ:OrgCode>\n" +
//            "  <typ:CompanyCode>OA</typ:CompanyCode>\n" +
//            "  <typ:OrgName>a p</typ:OrgName>\n" +
//            "  <typ:OrgType>USER</typ:OrgType>\n" +
//            "</typ:OrgInfo>";
//
//    private String unparsedExpectedMWMDoc = "<MitchellWorkflowMessage CreateTimestamp=\"\" xmlns=\"http://www.mitchell.com/common/types\">\n" +
//            "  <TrackingInfo>\n" +
//            "    <OriginatingSource>\n" +
//            "      <Product>PARTIALLOSS</Product>\n" +
//            "      <Component>ASSIGNMENT_DELIVERY_SERVICE</Component>\n" +
//            "      <Server>cc102462.mitchell.com</Server>\n" +
//            "    </OriginatingSource>\n" +
//            "    <BusinessService>AssignmentDeliveryService</BusinessService>\n" +
//            "    <WorkflowUserInfo>\n" +
//            "      <UserInfo xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\" xmlns:sch=\"http://www.mitchell.com/schemas\" xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "        <typ:OrgID>350785</typ:OrgID>\n" +
//            "        <typ:OrgCode>IF</typ:OrgCode>\n" +
//            "        <typ:UserID>IFWCRC02</typ:UserID>\n" +
//            "        <typ:FirstName>WCRC</typ:FirstName>\n" +
//            "        <typ:LastName>TEST</typ:LastName>\n" +
//            "        <typ:Email>sathish.venkatesan@mitchell.com</typ:Email>\n" +
//            "        <typ:UserHier>\n" +
//            "          <typ:HierNode ID=\"72586\" Code=\"IF\" Name=\"PROGRESSIVE INSURANCE\" Level=\"COMPANY\">\n" +
//            "            <typ:HierNode ID=\"75875\" Code=\"99\" Name=\"DEFAULT\" Level=\"REGION\">\n" +
//            "              <typ:HierNode ID=\"75876\" Code=\"99\" Name=\"DEFAULT\" Level=\"DIVISION\">\n" +
//            "                <typ:HierNode ID=\"79939\" Code=\"9999\" Name=\"DEFAULT\" Level=\"OFFICE\">\n" +
//            "                  <typ:HierNode ID=\"350785\" Code=\"IFWCRC02\" Name=\"WCRC TEST\" Level=\"USER\"/>\n" +
//            "                </typ:HierNode>\n" +
//            "              </typ:HierNode>\n" +
//            "            </typ:HierNode>\n" +
//            "          </typ:HierNode>\n" +
//            "        </typ:UserHier>\n" +
//            "        <typ:AppCode>CMAPES</typ:AppCode>\n" +
//            "        <typ:AppCode>NETACT</typ:AppCode>\n" +
//            "        <typ:StaffType>ADMINISTRATOR</typ:StaffType>\n" +
//            "      </UserInfo>\n" +
//            "    </WorkflowUserInfo>\n" +
//            "    <WorkItemID>32826f0a-ffff-ffa6-2077-e893ab219f88</WorkItemID>\n" +
//            "  </TrackingInfo>\n" +
//            "  <Data Type=\"171602\">\n" +
//            "    <MitchellEnvelope Version=\"1.0\" xmlns=\"http://www.mitchell.com/schemas\" xmlns:app=\"http://www.mitchell.com/schemas/appraisalassignment\" xmlns:typ=\"http://www.mitchell.com/common/types\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
//            "      <EnvelopeContext>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>MessageType</Name>\n" +
//            "          <Value>ORIGINAL_ESTIMATE</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>MessageStatus</Name>\n" +
//            "          <Value>DISPATCHED</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>MitchellCompanyCode</Name>\n" +
//            "          <Value>IF</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ClaimNumber</Name>\n" +
//            "          <Value>99-2861203-01</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>MitchellWorkItemId</Name>\n" +
//            "          <Value>32826f0a-ffff-ffa6-2077-e893ab219f88</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>TaskId</Name>\n" +
//            "          <Value>1000045194</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ReferenceId</Name>\n" +
//            "          <Value>101010</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ClaimExposureId</Name>\n" +
//            "          <Value>202020</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ReviewerCoCd</Name>\n" +
//            "          <Value>IF</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ShopId</Name>\n" +
//            "          <Value>384714</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>ReviewerId</Name>\n" +
//            "          <Value>IFWCRC02</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>UserCoCd</Name>\n" +
//            "          <Value>BS</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>UserId</Name>\n" +
//            "          <Value>841610</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>PrimaryContactType</Name>\n" +
//            "          <Value>INSURED</Value>\n" +
//            "        </NameValuePair>\n" +
//            "        <NameValuePair>\n" +
//            "          <Name>Notes</Name>\n" +
//            "          <Value>please go to Daytona!</Value>\n" +
//            "        </NameValuePair>\n" +
//            "      </EnvelopeContext>\n" +
//            "      <EnvelopeBodyList>\n" +
//            "        <EnvelopeBody>\n" +
//            "          <Metadata>\n" +
//            "            <Identifier>AssignmentBMS</Identifier>\n" +
//            "            <MitchellDocumentType>CIECA_BMS_ASG_XML</MitchellDocumentType>\n" +
//            "            <XmlBeanClassname>com.cieca.bms.CIECADocument</XmlBeanClassname>\n" +
//            "          </Metadata>\n" +
//            "          <Content>\n" +
//            "            <bms:CIECA xmlns:bms=\"http://www.cieca.com/BMS\">\n" +
//            "              <bms:AssignmentAddRq>\n" +
//            "                <bms:RqUID>d8b7ec6f-ac18-371c-26e9-31878c92eb77</bms:RqUID>\n" +
//            "                <bms:DocumentInfo>\n" +
//            "                  <bms:BMSVer>2.10.0</bms:BMSVer>\n" +
//            "                  <bms:DocumentType>A</bms:DocumentType>\n" +
//            "                  <bms:DocumentID>0</bms:DocumentID>\n" +
//            "                  <bms:DocumentVer>\n" +
//            "                    <bms:DocumentVerCode>EM</bms:DocumentVerCode>\n" +
//            "                    <bms:DocumentVerNum>0</bms:DocumentVerNum>\n" +
//            "                  </bms:DocumentVer>\n" +
//            "                  <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "                </bms:DocumentInfo>\n" +
//            "                <bms:EventInfo>\n" +
//            "                  <bms:AssignmentEvent>\n" +
//            "                    <bms:CreateDateTime>2014-05-07T15:05:03.472-07:00</bms:CreateDateTime>\n" +
//            "                  </bms:AssignmentEvent>\n" +
//            "                </bms:EventInfo>\n" +
//            "                <bms:AdminInfo>\n" +
//            "                  <bms:InsuranceCompany>\n" +
//            "                    <bms:Party>\n" +
//            "                      <bms:OrgInfo>\n" +
//            "                        <bms:CompanyName>AAA Northern CA</bms:CompanyName>\n" +
//            "                      </bms:OrgInfo>\n" +
//            "                    </bms:Party>\n" +
//            "                  </bms:InsuranceCompany>\n" +
//            "                  <bms:PolicyHolder>\n" +
//            "                    <bms:Party>\n" +
//            "                      <bms:PersonInfo>\n" +
//            "                        <bms:PersonName>\n" +
//            "                          <bms:FirstName>Colin</bms:FirstName>\n" +
//            "                          <bms:LastName>Christ</bms:LastName>\n" +
//            "                        </bms:PersonName>\n" +
//            "                        <bms:Communications>\n" +
//            "                          <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                          <bms:Address>\n" +
//            "                            <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                          </bms:Address>\n" +
//            "                        </bms:Communications>\n" +
//            "                      </bms:PersonInfo>\n" +
//            "                    </bms:Party>\n" +
//            "                  </bms:PolicyHolder>\n" +
//            "                  <bms:Estimator>\n" +
//            "                    <bms:Party>\n" +
//            "                      <bms:PersonInfo>\n" +
//            "                        <bms:PersonName>\n" +
//            "                          <bms:FirstName>a</bms:FirstName>\n" +
//            "                          <bms:LastName>p</bms:LastName>\n" +
//            "                        </bms:PersonName>\n" +
//            "                        <bms:Communications>\n" +
//            "                          <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                          <bms:Address>\n" +
//            "                            <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                            <bms:City>San Diego</bms:City>\n" +
//            "                            <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                            <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                            <bms:CountryCode>United States</bms:CountryCode>\n" +
//            "                          </bms:Address>\n" +
//            "                        </bms:Communications>\n" +
//            "                      </bms:PersonInfo>\n" +
//            "                      <bms:ContactInfo>\n" +
//            "                        <bms:Communications>\n" +
//            "                          <bms:CommQualifier>EM</bms:CommQualifier>\n" +
//            "                          <bms:CommEmail>asaf.ptahia@mitchell.com</bms:CommEmail>\n" +
//            "                        </bms:Communications>\n" +
//            "                      </bms:ContactInfo>\n" +
//            "                    </bms:Party>\n" +
//            "                    <bms:Affiliation>69</bms:Affiliation>\n" +
//            "                  </bms:Estimator>\n" +
//            "                  <bms:InspectionSite>\n" +
//            "                    <bms:Party>\n" +
//            "                      <bms:OrgInfo>\n" +
//            "                        <bms:CompanyName>a p</bms:CompanyName>\n" +
//            "                        <bms:Communications>\n" +
//            "                          <bms:CommQualifier>AL</bms:CommQualifier>\n" +
//            "                          <bms:Address>\n" +
//            "                            <bms:Address1>4839 Clairemont Dr</bms:Address1>\n" +
//            "                            <bms:City>San Diego</bms:City>\n" +
//            "                            <bms:StateProvince>CA</bms:StateProvince>\n" +
//            "                            <bms:PostalCode>92117</bms:PostalCode>\n" +
//            "                            <bms:CountryCode>US</bms:CountryCode>\n" +
//            "                          </bms:Address>\n" +
//            "                        </bms:Communications>\n" +
//            "                      </bms:OrgInfo>\n" +
//            "                      <bms:ContactInfo>\n" +
//            "                        <bms:Communications>\n" +
//            "                          <bms:CommQualifier>CP</bms:CommQualifier>\n" +
//            "                          <bms:CommPhone>777-7777777</bms:CommPhone>\n" +
//            "                        </bms:Communications>\n" +
//            "                      </bms:ContactInfo>\n" +
//            "                    </bms:Party>\n" +
//            "                  </bms:InspectionSite>\n" +
//            "                  <bms:Submitter>\n" +
//            "                    <bms:Party>\n" +
//            "                      <bms:PersonInfo>\n" +
//            "                        <bms:PersonName/>\n" +
//            "                        <bms:IDInfo>\n" +
//            "                          <bms:IDQualifierCode>UserID</bms:IDQualifierCode>\n" +
//            "                          <bms:IDNum>OAAJDIS1</bms:IDNum>\n" +
//            "                        </bms:IDInfo>\n" +
//            "                      </bms:PersonInfo>\n" +
//            "                    </bms:Party>\n" +
//            "                  </bms:Submitter>\n" +
//            "                </bms:AdminInfo>\n" +
//            "                <bms:ClaimInfo>\n" +
//            "                  <bms:ClaimNum>4547.01</bms:ClaimNum>\n" +
//            "                  <bms:PolicyInfo>\n" +
//            "                    <bms:CoverageInfo>\n" +
//            "                      <bms:Coverage>\n" +
//            "                        <bms:CoverageCategory>C</bms:CoverageCategory>\n" +
//            "                        <bms:DeductibleInfo>\n" +
//            "                          <bms:DeductibleStatus>FK</bms:DeductibleStatus>\n" +
//            "                          <bms:DeductibleAmt>0</bms:DeductibleAmt>\n" +
//            "                        </bms:DeductibleInfo>\n" +
//            "                      </bms:Coverage>\n" +
//            "                    </bms:CoverageInfo>\n" +
//            "                  </bms:PolicyInfo>\n" +
//            "                  <bms:LossInfo>\n" +
//            "                    <bms:Facts>\n" +
//            "                      <bms:LossDateTime>2014-05-06T00:00:00</bms:LossDateTime>\n" +
//            "                    </bms:Facts>\n" +
//            "                    <bms:TotalLossInd>N</bms:TotalLossInd>\n" +
//            "                  </bms:LossInfo>\n" +
//            "                </bms:ClaimInfo>\n" +
//            "                <bms:VehicleDamageAssignment>\n" +
//            "                  <bms:EstimatorIDs>\n" +
//            "                    <bms:CurrentEstimatorID>OAAPBS01</bms:CurrentEstimatorID>\n" +
//            "                    <bms:RoutingIDInfo>\n" +
//            "                      <bms:IDQualifierCode>WAGroupID</bms:IDQualifierCode>\n" +
//            "                      <bms:IDNum>SHOPHOLDING</bms:IDNum>\n" +
//            "                    </bms:RoutingIDInfo>\n" +
//            "                  </bms:EstimatorIDs>\n" +
//            "                  <bms:VehicleInfo>\n" +
//            "                    <bms:VINInfo>\n" +
//            "                      <bms:VINAvailabilityCode>Unavailable</bms:VINAvailabilityCode>\n" +
//            "                    </bms:VINInfo>\n" +
//            "                    <bms:VehicleDesc>\n" +
//            "                      <bms:ModelYear>2006</bms:ModelYear>\n" +
//            "                      <bms:MakeDesc>Aston Martin</bms:MakeDesc>\n" +
//            "                      <bms:ModelName>DB9</bms:ModelName>\n" +
//            "                      <bms:SubModelDesc>Volante</bms:SubModelDesc>\n" +
//            "                      <bms:VehicleType>Car</bms:VehicleType>\n" +
//            "                      <bms:OdometerInfo>\n" +
//            "                        <bms:OdometerInd>true</bms:OdometerInd>\n" +
//            "                        <bms:OdometerReading>456345</bms:OdometerReading>\n" +
//            "                        <bms:OdometerReadingCode>false</bms:OdometerReadingCode>\n" +
//            "                      </bms:OdometerInfo>\n" +
//            "                    </bms:VehicleDesc>\n" +
//            "                    <bms:Body>\n" +
//            "                      <bms:BodyStyle>2 Door Conv</bms:BodyStyle>\n" +
//            "                    </bms:Body>\n" +
//            "                    <bms:Powertrain>\n" +
//            "                      <bms:EngineDesc>6.0L 12 Cyl Gas Injected</bms:EngineDesc>\n" +
//            "                      <bms:TransmissionInfo>\n" +
//            "                        <bms:TransmissionCode>19</bms:TransmissionCode>\n" +
//            "                        <bms:TransmissionDesc>6 Speed Auto Trans</bms:TransmissionDesc>\n" +
//            "                      </bms:TransmissionInfo>\n" +
//            "                      <bms:Configuration>RWD</bms:Configuration>\n" +
//            "                    </bms:Powertrain>\n" +
//            "                    <bms:Condition>\n" +
//            "                      <bms:DrivableInd>U</bms:DrivableInd>\n" +
//            "                    </bms:Condition>\n" +
//            "                  </bms:VehicleInfo>\n" +
//            "                  <bms:AssignmentMemo>please go to Daytona!</bms:AssignmentMemo>\n" +
//            "                </bms:VehicleDamageAssignment>\n" +
//            "              </bms:AssignmentAddRq>\n" +
//            "            </bms:CIECA>\n" +
//            "          </Content>\n" +
//            "        </EnvelopeBody>\n" +
//            "        <EnvelopeBody>\n" +
//            "          <Metadata>\n" +
//            "            <Identifier>VehicleDetailsInfo</Identifier>\n" +
//            "            <MitchellDocumentType>AdditionalAppraisalAssignmentInfo</MitchellDocumentType>\n" +
//            "            <XmlBeanClassname>com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument</XmlBeanClassname>\n" +
//            "          </Metadata>\n" +
//            "          <Content>\n" +
//            "            <app:AdditionalAppraisalAssignmentInfo>\n" +
//            "              <app:VehicleDetails xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\" xmlns:sch=\"http://www.mitchell.com/schemas\">\n" +
//            "                <app:VehicleType>\n" +
//            "                  <app:ID>1</app:ID>\n" +
//            "                  <app:Value>Car</app:Value>\n" +
//            "                </app:VehicleType>\n" +
//            "                <app:Year>2006</app:Year>\n" +
//            "                <app:Make>\n" +
//            "                  <app:ID>98</app:ID>\n" +
//            "                  <app:Value>Aston Martin</app:Value>\n" +
//            "                </app:Make>\n" +
//            "                <app:Model>\n" +
//            "                  <app:ID>1802</app:ID>\n" +
//            "                  <app:Value>DB9</app:Value>\n" +
//            "                </app:Model>\n" +
//            "                <app:SubModel>\n" +
//            "                  <app:ID>1090</app:ID>\n" +
//            "                  <app:Value>Volante</app:Value>\n" +
//            "                </app:SubModel>\n" +
//            "                <app:BodyStyle>\n" +
//            "                  <app:ID>12</app:ID>\n" +
//            "                  <app:Value>2 Door Conv</app:Value>\n" +
//            "                </app:BodyStyle>\n" +
//            "                <app:Engine>\n" +
//            "                  <app:ID>234</app:ID>\n" +
//            "                  <app:Value>6.0L 12 Cyl Gas Injected</app:Value>\n" +
//            "                </app:Engine>\n" +
//            "                <app:Transmission>\n" +
//            "                  <app:ID>19</app:ID>\n" +
//            "                  <app:Value>6 Speed Auto Trans</app:Value>\n" +
//            "                </app:Transmission>\n" +
//            "                <app:DriveTrain>\n" +
//            "                  <app:ID>7</app:ID>\n" +
//            "                  <app:Value>RWD</app:Value>\n" +
//            "                </app:DriveTrain>\n" +
//            "                <app:MitchellVID>127412</app:MitchellVID>\n" +
//            "              </app:VehicleDetails>\n" +
//            "            </app:AdditionalAppraisalAssignmentInfo>\n" +
//            "          </Content>\n" +
//            "        </EnvelopeBody>\n" +
//            "        <EnvelopeBody>\n" +
//            "          <Metadata>\n" +
//            "            <Identifier>WCAttachmentInfo</Identifier>\n" +
//            "            <MitchellDocumentType>AttachmentInfo</MitchellDocumentType>\n" +
//            "            <XmlBeanClassname>com.mitchell.types.AttachmentInfoDocument</XmlBeanClassname>\n" +
//            "          </Metadata>\n" +
//            "          <Content>\n" +
//            "            <typ:AttachmentInfo>\n" +
//            "              <typ:AttachmentInfoList>\n" +
//            "                <typ:AttachmentItem>\n" +
//            "                  <typ:AttachmentId>99999</typ:AttachmentId>\n" +
//            "                  <typ:AttachmentType>IMAGE</typ:AttachmentType>\n" +
//            "                  <typ:DateAdded xsi:nil=\"true\"/>\n" +
//            "                  <typ:DocStoreFileReference>357670722</typ:DocStoreFileReference>\n" +
//            "                  <typ:ActualFileName>92-obj-15.JPG</typ:ActualFileName>\n" +
//            "                  <typ:Status>0</typ:Status>\n" +
//            "                </typ:AttachmentItem>\n" +
//            "              </typ:AttachmentInfoList>\n" +
//            "            </typ:AttachmentInfo>\n" +
//            "          </Content>\n" +
//            "        </EnvelopeBody>\n" +
//            "      </EnvelopeBodyList>\n" +
//            "    </MitchellEnvelope>\n" +
//            "  </Data>\n" +
//            "</MitchellWorkflowMessage>";
//
//    String unparsedUserInfoDoc = "<typ:UserInfo xmlns:apd=\"http://www.mitchell.com/schemas/apddelivery\" xmlns:sch=\"http://www.mitchell.com/schemas\" xmlns:typ=\"http://www.mitchell.com/common/types\">\n" +
//            "  <typ:OrgID>350785</typ:OrgID>\n" +
//            "  <typ:OrgCode>IF</typ:OrgCode>\n" +
//            "  <typ:UserID>IFWCRC02</typ:UserID>\n" +
//            "  <typ:FirstName>WCRC</typ:FirstName>\n" +
//            "  <typ:LastName>TEST</typ:LastName>\n" +
//            "  <typ:Email>sathish.venkatesan@mitchell.com</typ:Email>\n" +
//            "  <typ:UserHier>\n" +
//            "    <typ:HierNode ID=\"72586\" Code=\"IF\" Name=\"PROGRESSIVE INSURANCE\" Level=\"COMPANY\">\n" +
//            "      <typ:HierNode ID=\"75875\" Code=\"99\" Name=\"DEFAULT\" Level=\"REGION\">\n" +
//            "        <typ:HierNode ID=\"75876\" Code=\"99\" Name=\"DEFAULT\" Level=\"DIVISION\">\n" +
//            "          <typ:HierNode ID=\"79939\" Code=\"9999\" Name=\"DEFAULT\" Level=\"OFFICE\">\n" +
//            "            <typ:HierNode ID=\"350785\" Code=\"IFWCRC02\" Name=\"WCRC TEST\" Level=\"USER\"/>\n" +
//            "          </typ:HierNode>\n" +
//            "        </typ:HierNode>\n" +
//            "      </typ:HierNode>\n" +
//            "    </typ:HierNode>\n" +
//            "  </typ:UserHier>\n" +
//            "  <typ:AppCode>CMAPES</typ:AppCode>\n" +
//            "  <typ:AppCode>NETACT</typ:AppCode>\n" +
//            "  <typ:StaffType>ADMINISTRATOR</typ:StaffType>\n" +
//            "</typ:UserInfo>";
//}