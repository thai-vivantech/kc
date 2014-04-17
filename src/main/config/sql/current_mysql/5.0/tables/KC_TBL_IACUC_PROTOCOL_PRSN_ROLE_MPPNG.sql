DELIMITER /
CREATE TABLE IACUC_PROTOCOL_PRSN_ROLE_MPPNG (
    ROLE_MAPPING_ID DECIMAL(12,0) NOT NULL,
    SOURCE_ROLE_ID VARCHAR(12) NOT NULL,
    TARGET_ROLE_ID VARCHAR(12) NOT NULL,
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR(60) NOT NULL, 
    VER_NBR DECIMAL(8,0) DEFAULT 1 NOT NULL,     
    OBJ_ID VARCHAR(36) NOT NULL
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/
ALTER TABLE IACUC_PROTOCOL_PRSN_ROLE_MPPNG 
ADD CONSTRAINT PK_IACUC_PRTCL_PRSN_ROLE_MPPNG
PRIMARY KEY (ROLE_MAPPING_ID) 
/
DELIMITER ;