/*
Description  : Script to create property for carrier global.
Author       : Samir Singh
Created Date : 18-APR-2013

To be executed as: epd@epdd (in dev), epd@epdt (in QA), epd@epdu (in UAT), epd@epfp (In Prod)
*/
DECLARE
  sReqCoCode        VARCHAR2(30) := 'MX';
  sReqUserId        VARCHAR2(30) := 'DBACUS';
  sReqApplCd        VARCHAR2(30) := 'SECY';
  v_section         VARCHAR2(500) := NULL;
  nGroupID NUMBER := NULL;
  nPropID  NUMBER := NULL;
  v_value_list_name VARCHAR2(200) := 'ESTIMATING PLATFORM';
  v_value_list_desc VARCHAR2(250) := 'GTE';
  v_list_id         NUMBER;
BEGIN


  v_section := '1 - Create new list  Vehicle Equipment Language';

  EPD_EXTENDED_PROPERTIES_PKG.PUT_EPD_EXP_VALUE_LIST(sReqCoCode,
                                                     sReqUserId,
                                                     sReqApplCd,
                                                     v_value_list_name,
                                                     v_value_list_desc,
                                                     NULL,
                                                     NULL);


  EPD_CUSTOMSETTINGS_UTILITY_PKG.PUT_EPD_EXP_VALUE_LIST(  sReqUserId,
                                                          v_value_list_name,
                                                          v_value_list_desc,
                                                          'GTE','GTE', '1',NULL);
														  
														  
 v_section := '2 - Get group Id ';
  
    SELECT group_id
      INTO nGroupID
      FROM epd_extended_property_group
     WHERE group_name = 'CARRIER_GLOBAL_SETTINGS';

  v_section := '3- Create property';

    /* Create property: GENERIC_NONDRP_USER
	*/				   

    EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY(sReqCoCode,
                                                      sReqUserId,
                                                      sReqApplCd,
                                                      'TEXT',
                                                      'Y',
                                                      'EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION',
                                                      NULL,
                                                      'External Estimating Platform Integration',
                                                      NULL,
                                                      NULL,
                                                      NULL,
                                                      NULL,
                                                      v_value_list_name);
		
													  
 v_section := '4- Associate the property to the group';
    --associate the property to the group with Sort Order
    SELECT prop_id
      INTO nPropID
      FROM epd_extended_property
     WHERE prop_name = 'EXTERNAL_ESTIMATING_PLATFORM_INTEGRATION';
  
    EPD_EXTENDED_PROPERTIES_PKG.PUT_GROUP_TO_PROPERTY(nGroupID,
                                                      nPropID,
                                                      910);

    
													  
--all activities passed successfully; commit the changes
  COMMIT;

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


