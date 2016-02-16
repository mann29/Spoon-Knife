/*------------------------------------------------------------------------------------
 -- Deployment Instruction: run this script as EPD on EPDD, EPDT, EPDU, EPFP
 -----------------------------------------------------------------------------------*/
DECLARE

  sReqCoCode VARCHAR2(30) := 'MX';
  sReqUserId VARCHAR2(30) := 'DBACUS';
  sReqApplCd VARCHAR2(30) := 'SECY';
  lv_group_id epd_extended_property_group.group_id%TYPE;

  nGroupID   NUMBER := NULL;
  nPropID    NUMBER := NULL;
  nCnt       NUMBER := NULL;


BEGIN

BEGIN
    /*----------------------------------------------------------------------------------------
     * Create Custom Settings for Group: REPAIR_WORKFLOW_SETTINGS
     *----------------------------------------------------------------------------------------*/

     -- 1. Create REPAIR_WORKFLOW_SETTINGS group
     EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY_GROUP(sReqCoCode, sReqUserId, sReqApplCd, 
      'REPAIR_WORKFLOW_SETTINGS', 'APPLICATION', 'Repair Settings', NULL, NULL, NULL, NULL, NULL);

       SELECT group_id INTO nGroupID FROM epd_extended_property_group
       WHERE group_name = 'REPAIR_WORKFLOW_SETTINGS';


     -- 2. Create properties and assotiate the property with group
       -- 2.1 Create property: 1) REPAIR_STATUS_EMAIL_NOTIFICATION_TO_SHOP_CUSTOMERS
       EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY(sReqCoCode, sReqUserId, sReqApplCd, 'TEXT', 'Y',
       'REPAIR_STATUS_EMAIL_NOTIFICATION_TO_SHOP_CUSTOMERS', NULL, 'Send Repair Status Email Notification to Shop Customers', 'Y', NULL, NULL, NULL, NULL);

       -- 2.2 Now, assotiate the propertie to the group with Sort Order:
       SELECT prop_id INTO nPropID FROM epd_extended_property
       WHERE prop_name = 'REPAIR_STATUS_EMAIL_NOTIFICATION_TO_SHOP_CUSTOMERS';
       EPD_EXTENDED_PROPERTIES_PKG.PUT_GROUP_TO_PROPERTY(nGroupID, nPropID, 90);


     
       --2.3  Now, assotiate the value list with property :
       SELECT prop_id INTO nPropID FROM epd_extended_property
       WHERE prop_name = 'REPAIR_STATUS_EMAIL_NOTIFICATION_TO_SHOP_CUSTOMERS';
       EPD_EXTENDED_PROPERTIES_PKG.PUT_VALUE_LIST_TO_PROP('REPAIR_STATUS_EMAIL_NOTIFICATION_TO_SHOP_CUSTOMERS' ,'YES/NO' );




     COMMIT;

     EXCEPTION WHEN OTHERS THEN
           dbms_output.put_line('Error in Custom Settings (groups and properties) !!! - '|| SUBSTR(SQLERRM,1,120));

  End;



 /*------------------------------------------------------------------------------------------------------
  * Activate custom settings for application. This will add the appl_pkg_cd and link it with CS groups
  *------------------------------------------------------------------------------------------------------*/

  Begin
     -- Create the appl_pkg_cd: CARR
       pkgUpdateTablesForSetup.EPDApplPkgTable('CLAIM', 'Claim Module');

     -- Link application CARR with group REPAIR_WORKFLOW_SETTINGS
     Select group_id Into ngroupId
     From epd_extended_property_group t
     Where t.group_name = 'REPAIR_WORKFLOW_SETTINGS';

     pkgUpdateTablesForSetup.EPDExpDefApplRoleGroupTable('CLAIM', ngroupId);


    COMMIT;

    dbms_output.put_line('Activated the custom settings for application!');

    EXCEPTION WHEN OTHERS THEN
        dbms_output.put_line('Error in Activate custom settings for application!!! - '|| SUBSTR(SQLERRM,1,120));


  End;
 /*------------------------------------------------------------------------------------------------------
   * Purpose: Synch newly added custom settings properties with existing company level profiles
   * To be executed as: epd@epdd (in dev), epd@epdt (in QA), epd@epdu (in UAT), epd@epfp (In Prod)
   *------------------------------------------------------------------------------------------------------*/
  Begin
    -- Call the procedure
    p_synchproptoprofile;
  End;
  
begin
  -- Call the procedure
 EPD_DATA_CONVERSION_PKG.p_synchgrouptoprofile;
end;
END;
/
