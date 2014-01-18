DELIMITER /
INSERT INTO SEQ_NTFCTN_MODULE_ROLE_ID VALUES(NULL)
/
-- create new notification role
INSERT INTO NOTIFICATION_MODULE_ROLE ( NOTIFICATION_MODULE_ROLE_ID, OBJ_ID, VER_NBR, UPDATE_TIMESTAMP, UPDATE_USER, MODULE_CODE, ROLE_NAME )
VALUES ( (SELECT (MAX(ID)) FROM SEQ_NTFCTN_MODULE_ROLE_ID), UUID(), 1, NOW(), 'admin', 7, 'KC-PROTOCOL:Unit Correspondent' )
/
INSERT INTO SEQ_NTFCTN_MDL_ROLE_QLFR_ID VALUES(NULL)
/
-- specify required qualifier
INSERT INTO NOTIFICATION_MDL_ROLE_QLFR (NOTIFICATION_MDL_ROLE_QLFR_ID, NOTIFICATION_MODULE_ROLE_ID, OBJ_ID, VER_NBR, UPDATE_TIMESTAMP, UPDATE_USER, QUALIFIER)
values ( (SELECT (MAX(ID)) FROM SEQ_NTFCTN_MDL_ROLE_QLFR_ID), (select NOTIFICATION_MODULE_ROLE_ID from NOTIFICATION_MODULE_ROLE where MODULE_CODE = 7 and ROLE_NAME = 'KC-PROTOCOL:Unit Correspondent'), UUID(), 1, NOW(), 'admin', 'protocol')
/
COMMIT
/
DELIMITER ;