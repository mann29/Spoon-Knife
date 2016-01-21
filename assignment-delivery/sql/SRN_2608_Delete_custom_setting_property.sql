/*

-- Script for deleting an existing Custom Settings property  PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_PRIMARY_NOTIFICATION_TEMPLATE
-- Created By: SC10615
-- Created Date: 08/03/2011
-- to be executed as 
in QA - EPD@NMAT
in UAT - EPD@NMAU
in PROD - EPD@NMAP 

*/
declare 
l_prop_id  epd_extended_property.prop_id%TYPE := 0;
l_group_id EPD_EXTENDED_PROPERTY_GROUP.GROUP_ID%TYPE  := 0;
l_group_name      epd_extended_property_group.group_name%type := null;
l_prop_name epd_extended_property.prop_name%TYPE := null;

Begin
l_group_name      := 'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_SETTINGS';
l_prop_name       := 'PARTIALLOSS_SIP_ASSIGNMENT_DELIVERY_PRIMARY_NOTIFICATION_TEMPLATE';
select group_id into l_group_id from epd_extended_property_group where group_name = l_group_name;
select prop_id into l_prop_id from epd_extended_property where prop_name =  l_prop_name;


update epd_exp_group_to_prop set expiration_date = sysdate 
where group_id = l_group_id and prop_id = l_prop_id;

Commit;

Exception 
	WHEN OTHERS then
	dbms_output.put_line (SQLERRM);
Rollback; 
end;
/