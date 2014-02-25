CREATE TABLE FIN_INT_ENTITY_STATUS ( 
    STATUS_CODE NUMBER(3,0) NOT NULL, 
    DESCRIPTION VARCHAR2(200) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2(60) NOT NULL, 
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2(36) NOT NULL)
/
ALTER TABLE FIN_INT_ENTITY_STATUS 
ADD CONSTRAINT PK_FIN_INT_ENTITY_STATUS 
PRIMARY KEY (STATUS_CODE)
/
