package com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit;

import java.util.logging.Logger;

import javax.ejb.EJB;

import com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJB;

/**
 * Parallel submit base class.
 * 
 */
public class AASParallelSubmitBase
{

  @EJB
  protected AASParallelEJB pEjb;

  protected static Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit.AASParallelSubmitBase");

}
