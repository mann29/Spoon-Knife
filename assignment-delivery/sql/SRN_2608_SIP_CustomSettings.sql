/*
Description: Script to create a new Xtended Property REVIEW_ENABLED and associate it with group CARR_STANDARD_WORKFLOW_SETTINGS
Author: Alok Mishra
Created Date :   03-AUG-2011

To be executed as: epd@epdd (in dev), epd@epdt (in QA), epd@epdu (in UAT), epd@epfp (In Prod)
*/

DECLARE
  sReqCoCode VARCHAR2(30) := 'MX';
  sReqUserId VARCHAR2(30) := 'DBACUS';
  sReqApplCd VARCHAR2(30) := 'SIPAST';
  v_section  VARCHAR2(500):= NULL;

  nGroupID   NUMBER := NULL;
  nPropID    NUMBER := NULL;
  nCnt       NUMBER := NULL;
BEGIN
  v_section := '1- Get the ID of the group PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_SETTINGS';
  SELECT group_id
    INTO nGroupID
    FROM epd_extended_property_group
   WHERE group_name = 'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_SETTINGS';

  v_section := '2- Create properties and associate the property with group';
  --Create property: REVIEW_ENABLED
  EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY(
    sReqCoCode,
    sReqUserId,
    sReqApplCd,
    'TEXT',
    NULL,
    'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_WCAP_DISPATCH_RPT',
    NULL,
    'WCAP Dispatch Report XSLT Path',
    NULL,
    NULL,NULL,NULL,NULL);

  v_section := '3- Associate the property to the group';
  --associate the property to the group with Sort Order
  SELECT prop_id
    INTO nPropID
    FROM epd_extended_property
   WHERE prop_name = 'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_WCAP_DISPATCH_RPT';

  EPD_EXTENDED_PROPERTIES_PKG.PUT_GROUP_TO_PROPERTY(nGroupID, nPropID,80);

  v_section := '4- Create properties and associate the property with group';
  --Create property: REVIEW_ENABLED
  EPD_EXTENDED_PROPERTIES_PKG.PUT_EXTENDED_PROPERTY(
    sReqCoCode,
    sReqUserId,
    sReqApplCd,
    'TEXT',
    NULL,
    'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_RC_DISPATCH_RPT',
    NULL,
    'RC Dispatch Report XSLT Path',
    NULL,
    NULL,NULL,NULL,NULL);

  v_section := '5- Associate the property to the group';
  --associate the property to the group with Sort Order
  SELECT prop_id
    INTO nPropID
    FROM epd_extended_property
   WHERE prop_name = 'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_RC_DISPATCH_RPT';

  EPD_EXTENDED_PROPERTIES_PKG.PUT_GROUP_TO_PROPERTY(nGroupID, nPropID,90);

  COMMIT;

  Begin
    -- Call the procedure
    p_synchproptoprofile;
  End;

EXCEPTION
  WHEN OTHERS THEN
    dbms_output.put_line('Script failed at section -'||v_section);
    dbms_output.put_line(SQLERRM);
    ROLLBACK;
END;
/