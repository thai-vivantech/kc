DELIMITER /
INSERT INTO SEQ_COMMITTEE_ID VALUES(NULL)
/
INSERT INTO COMM_RESEARCH_AREAS (ID,COMMITTEE_ID_FK,RESEARCH_AREA_CODE,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
VALUES ((SELECT (MAX(ID)) FROM SEQ_COMMITTEE_ID),(SELECT ID FROM COMMITTEE WHERE COMMITTEE_NAME = 'KC IRB 1'),'000001','quickstart',NOW(),UUID(),0)
/
DELIMITER ;
