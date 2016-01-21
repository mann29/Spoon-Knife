/*
Description  : Script to create new value list and property for Workcenter appraisal assignemnt.
Author       : Samir Singh
Created Date : 22-FEB-2013

To be executed as: epd@epdd (in dev), epd@epdt (in QA), epd@epdu (in UAT), epd@epfp (In Prod)
*/

DECLARE
  sReqCoCode        VARCHAR2(30) := 'MX';
  sReqUserId        VARCHAR2(30) := 'DBACUS';
  sReqApplCd        VARCHAR2(30) := 'SECY';
  v_section         VARCHAR2(500) := NULL;
 
  v_list_id         NUMBER;

  nGroupID NUMBER := NULL;
  nPropID  NUMBER := NULL;
  nCnt     NUMBER := NULL;
BEGIN

 
    v_section := '1 - Get group Id ';
  
    SELECT group_id
      INTO nGroupID
      FROM epd_extended_property_group
     WHERE group_name = 'APPRAISAL_ASSIGNMENT_SERVICE_SETTINGS';
  
  
    v_section := '2- Create properties and assotiate them with a group';

    --Create property: 
    EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY(sReqCoCode,
                                                      sReqUserId,
                                                      sReqApplCd,
                                                      'TEXT',
                                                      'N',
                                                      'EVENT_ID_CREATE_SAN',
                                                      NULL,
                                           'Event ID for Staff Arrival Notification being created',
                                                      NULL,
                                                      NULL,
                                                      NULL,
                                                      NULL,
                                                      NULL);
    
    v_section := '3- Associate the property to the group';
    --associate the property to the group with Sort Order
    SELECT prop_id
      INTO nPropID
      FROM epd_extended_property
     WHERE prop_name = 'EVENT_ID_CREATE_SAN';
  
    EPD_EXTENDED_PROPERTIES_PKG.PUT_GROUP_TO_PROPERTY(nGroupID,
                                                      nPropID,
                                                      120);
													  
	
  /*------------------------------------------------------------------------------------------------------
  * Purpose: Synch newly added custom settings properties with existing company level profiles
  * To be executed as: epd@epdd (in dev), epd@epdt (in QA), epd@epdu (in UAT), epd@epfp (In Prod)
  *------------------------------------------------------------------------------------------------------*/
  Begin
    -- Call the procedure
    p_synchproptoprofile;
  
  End;

EXCEPTION
  WHEN OTHERS THEN
    dbms_output.put_line('Script failed at section -' || v_section);
    dbms_output.put_line(SQLERRM);
    ROLLBACK;
END;
/