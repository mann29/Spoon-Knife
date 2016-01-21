package com.mitchell.services.business.partialloss.appraisalassignment;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASJMSParallelManagerTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASAssignScheduleHandlerBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASBaseHandlerTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelHandlerProcessorBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelRequestManagerBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASUnsupportedHandlerBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit.AASAssignScheduleSubmitEJBBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJBBeanTest;
import com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelErrorMDBTest;
import com.mitchell.services.business.partialloss.appraisalassignment.pmdb.AASParallelMDBTest;

/**
 * 
 */
@RunWith(value = Suite.class)
@SuiteClasses(value = { AASAssignScheduleHandlerBeanTest.class,
    AASBaseHandlerTest.class, AASParallelHandlerProcessorBeanTest.class,
    AASParallelRequestManagerBeanTest.class,
    AASUnsupportedHandlerBeanTest.class, AASParallelEJBBeanTest.class,
    AppraisalAssignmentServiceBeanTest.class, AASJMSParallelManagerTest.class,
    AASAssignScheduleSubmitEJBBeanTest.class, AASParallelErrorMDBTest.class,
    AASParallelMDBTest.class })
public class AllTests extends TestCase
{
}
