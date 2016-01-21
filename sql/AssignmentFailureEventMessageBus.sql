set serveroutput on

DECLARE

  v_listener_id       NUMBER := 108300;
  v_message_type_id   NUMBER := 108302;
  v_association_count NUMBER := 0;

BEGIN

  SRV.MSB_ADMIN_PKG.SAV_LISTENER (
    P_LISTENER_ID     => v_listener_id
   ,P_QUEUE_NM        => 'PARTIALLOSS.ASSIGNMENT_FAILURE_RESPONSE_INPUT'
   ,P_MODULE_NM       => 'APPRAISAL_ASSIGNMENT_SERVICE'
   ,P_APP_NM          => 'PARTIALLOSS'
   ,P_DESCRIPTION     => 'Appraisal Assignment - Asynchronous MDB that handles Assignment Failure Response messages delivered by MessageBus requests' );

  SRV.MSB_ADMIN_PKG.SAV_MESSAGE_TYPE (
    P_MESSAGE_TYPE_ID => v_message_type_id
   ,P_DESCRIPTION     => 'Associated to Appraisal Assignment Service Failure Response Document'
   ,P_SCHEMA_LOCATION => ''  );

  v_association_count := 0;

  SELECT
    count(*) INTO v_association_count
  FROM
    message_type_listener
  WHERE
    message_type_id = v_message_type_id
  AND listener_id   = v_listener_id;

  dbms_output.put_line('v_association_count ' ||
                       v_association_count ||
                       ' for v_message_type_id ' ||
                       v_message_type_id ||
                       ' and v_listener_id ' ||
                       v_listener_id);

  IF v_association_count = 0 THEN
    dbms_output.put_line('INSERT association for v_message_type_id ' ||
                         v_message_type_id ||
                         ' and v_listener_id ' || v_listener_id);

    SRV.MSB_ADMIN_PKG.PUT_MESSAGE_TYPE_LISTENER (
      P_MESSAGE_TYPE_ID => v_message_type_id
     ,P_LISTENER_ID     => v_listener_id );

  END IF;

END;
/
