package com.mitchell.services.core.partialloss.apddelivery.unittest;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryFacade;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ADSProxyImpl;

public class ADSProxyShimImpl extends ADSProxyImpl {

    private AssignmentDeliveryFacade client;

    public ADSProxyShimImpl() { }

    public void setAssignmentDeliveryFacade(AssignmentDeliveryFacade client)
    {
        this.client = client;
    }

    protected AssignmentDeliveryFacade getAssignmentDeliveryClient() {

        return this.client;
    }
}
