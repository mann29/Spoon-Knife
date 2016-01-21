package com.mitchell.services.business.partialloss.assignmentdelivery.handler.daytona;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.PowertrainType;
import com.cieca.bms.VehicleDamageAssignmentType;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.*;
import com.mitchell.services.business.partialloss.assignmentdelivery.AppLoggerBridge;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.ClaimServiceProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.MessagePostingAgent;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Enclosed;
import javax.jms.JMSException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class DaytonaRAHandlerTest {

    public static class When_DeliverAssignment_Is_Called extends TestSetup {

        @Test
        public void it_should_call_getAssignmentBms_on_the_claimServiceProxy() throws MitchellException {
            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockClaimServiceProxy).getAssignmentBms(any(UserInfoDocument.class), eq(expectedClientClaimNumber));
        }

        @Test
        public void it_should_postMessage_to_messageBus() throws ServiceLocatorException, JMSException, BadXmlFormatException, AssignmentDeliveryException {
            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }

        @Test
        public void it_should_postMessage_to_messageBus_when_vehicleDamageAssignment_is_null() throws MitchellException, JMSException {
            expectedAssignmentAddRq.getAssignmentAddRq().unsetVehicleDamageAssignment();
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }

        @Test
        public void it_should_postMessage_to_messageBus_when_vehicleInfo_is_null() throws MitchellException, JMSException {
            expectedAssignmentAddRq.getAssignmentAddRq().getVehicleDamageAssignment().setVehicleInfo(null);
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }

        @Test
        public void it_should_postMessage_to_messageBus_when_powerTrain_is_null() throws MitchellException, JMSException {
            expectedAssignmentAddRq.getAssignmentAddRq().getVehicleDamageAssignment().getVehicleInfo().unsetPowertrain();
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }

        @Test
        public void it_should_postMessage_to_messageBus_when_transmissionInfo_is_null() throws MitchellException, JMSException {
            expectedAssignmentAddRq.getAssignmentAddRq().getVehicleDamageAssignment().getVehicleInfo().getPowertrain().unsetTransmissionInfo();
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }

        @Test
        public void it_should_postMessage_to_messageBus_when_transmissionDesc_is_null() throws MitchellException, JMSException {
            expectedAssignmentAddRq.getAssignmentAddRq().getVehicleDamageAssignment().getVehicleInfo().getPowertrain().getTransmissionInfo().setTransmissionDesc(null);
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            sut.deliverAssignment(expectedApdDeliveryContextDocument);

            verify(mockMessagePostingAgent).postMessage(any(MitchellWorkflowMessageDocument.class));
        }
    }

    public static class TestSetup {

        protected DaytonaRAHandlerImpl sut;
        protected ClaimServiceProxy mockClaimServiceProxy;
        protected MessagePostingAgent mockMessagePostingAgent;
        protected AppLoggerBridge mockAppLoggerBridge;

        protected APDDeliveryContextDocument expectedApdDeliveryContextDocument;
        protected AssignmentAddRqDocument expectedAssignmentAddRq;
        protected String expectedClientClaimNumber = "CLIENT_CLAIM_NUMBER";
        protected String expectedAssignmentType = "REPAIR_ASSIGNMENT";
        protected String expectedStatus = "Created";
        protected long expectedTaskId = 123;
        protected long expectedEstimateDocumentId = 456;
        protected long expectedClaimId = 789;
        protected String expectedWorkItemId = "EXPECTED_WORK_ITEM_ID";

        @Before
        public void setup() throws MitchellException, JMSException {
            expectedApdDeliveryContextDocument = APDDeliveryContextDocument.Factory.newInstance();
            APDDeliveryContextType apdDeliveryContextType = expectedApdDeliveryContextDocument.addNewAPDDeliveryContext();
            apdDeliveryContextType.setMessageType(expectedAssignmentType);
            APDRepairAssignmentInfoType apdRepairAssignmentInfoType = apdDeliveryContextType.addNewAPDRepairAssignmentInfo();
            apdRepairAssignmentInfoType.setMessageStatus(expectedStatus);
            apdRepairAssignmentInfoType.setTaskId(expectedTaskId);
            apdRepairAssignmentInfoType.setEstimateDocId(expectedEstimateDocumentId);
            BaseAPDCommonType baseAPDCommonType = apdRepairAssignmentInfoType.addNewAPDCommonInfo();
            baseAPDCommonType.setWorkItemId(expectedWorkItemId);
            baseAPDCommonType.setClientClaimNumber(expectedClientClaimNumber);
            baseAPDCommonType.setClaimId(expectedClaimId);

            APDUserInfoType apdUserInfoType = baseAPDCommonType.addNewSourceUserInfo();
            UserInfoType userInfoType = apdUserInfoType.addNewUserInfo();
            userInfoType.setUserID("USER_ID");
            userInfoType.setOrgCode("ORG_CODE");

            APDUserInfoType targetUserInfoType = baseAPDCommonType.addNewTargetUserInfo();
            UserInfoType targetUserInfo = targetUserInfoType.addNewUserInfo();

            expectedAssignmentAddRq = AssignmentAddRqDocument.Factory.newInstance();
            AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq = expectedAssignmentAddRq.addNewAssignmentAddRq();
            VehicleDamageAssignmentType vehicleDamageAssignmentType = assignmentAddRq.addNewVehicleDamageAssignment();
            VehicleInfoType vehicleInfoType = vehicleDamageAssignmentType.addNewVehicleInfo();
            PowertrainType powertrainType = vehicleInfoType.addNewPowertrain();
            PowertrainType.TransmissionInfo transMissionInfo = powertrainType.addNewTransmissionInfo();

            mockClaimServiceProxy = mock(ClaimServiceProxy.class);
            when(mockClaimServiceProxy.getAssignmentBms(any(UserInfoDocument.class), anyString()))
                    .thenReturn(expectedAssignmentAddRq);

            mockMessagePostingAgent = mock(MessagePostingAgent.class);
            when(mockMessagePostingAgent.postMessage(any(MitchellWorkflowMessageDocument.class)))
                    .thenReturn("BLAH");

            mockAppLoggerBridge = mock(AppLoggerBridge.class);
            when(mockAppLoggerBridge.logEvent(any(AppLoggingDocument.class), any(UserInfoDocument.class), anyString(), anyString(), anyString(), any(AppLoggingNVPairs.class)))
                    .thenReturn(MitchellWorkflowMessageDocument.Factory.newInstance());

            sut = new DaytonaRAHandlerImpl();
            sut.setClaimServiceProxy(mockClaimServiceProxy);
            sut.setMessagePostingAgent(mockMessagePostingAgent);
            sut.setAppLoggerBridge(mockAppLoggerBridge);
        }

        @Test
        public void ignoreTest() { } // to make sonar happy
    }
}
