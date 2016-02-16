package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryFacade;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ADSProxyUnitTest  {

    private ADSProxyShimImpl sut;
    private AssignmentDeliveryFacade mockAssignmentDeliveryClient;
    private APDDeliveryContextDocument mockContext;

    @Before
    public void setup() {
        sut = new ADSProxyShimImpl();
        InitializeMocks();
        SetupMocks();
    }

    private void InitializeMocks() {
        mockAssignmentDeliveryClient = mock(AssignmentDeliveryFacade.class);
        mockContext = mock(APDDeliveryContextDocument.class);
    }

    private void SetupMocks() {
        sut.setAssignmentDeliveryFacade(mockAssignmentDeliveryClient);
    }

    @Test
    public void When_deliverRCWebAssignment_is_called_it_should_call_deliverRCWebAssignment_on_a_new_Assignment_Delivery_Client() throws MitchellException {

        sut.deliverRCWebAssignment(mockContext);

        verify(mockAssignmentDeliveryClient).deliverRCWebAssignment(mockContext);

    }

}
