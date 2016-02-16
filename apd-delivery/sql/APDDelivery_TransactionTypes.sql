set echo on;

begin

SAV_TXN_TYPE( '159691', 'APD Delivery Service- Not a shop user', 'USER_IS_NOT_SHOP_USER', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159692', 'APD Delivery Service- Delivery end point is non-platform', 'DLVRY_END_POINT_NON_PLATFORM', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159693', 'APD Delivery Service- Email id not found for shop', 'EMAIL_ID_NOT_FOUND_FOR_SHOP', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159694', 'APD Delivery Service- NICB Report is successfully delivered', 'NICB_RPT_DLVD_TO_NON_PLATFORM', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159695', 'APD Delivery Service- BC Message recipient is not WCAP/Estimator', 'BC_MSG_RCPNT_NOT_WCAP_ESMTR', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159696', 'APD Delivery Service- BC Message recipient is WCAP user', 'BC_MSG_WCAP_RECIPIENT', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );

SAV_TXN_TYPE( '159697', 'APD Delivery Service- BC Message recipient is Estimator user', 'BC_MSG_ESTIMATOR_RECIPIENT', 'NA', NULL, 0, 0, NULL, NULL, 'Y', 'C' );
 
commit;

end;
/