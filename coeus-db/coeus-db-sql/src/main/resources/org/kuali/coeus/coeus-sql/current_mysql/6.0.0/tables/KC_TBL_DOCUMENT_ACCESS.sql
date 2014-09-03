DELIMITER /
CREATE TABLE DOCUMENT_ACCESS (
    DOC_ACCESS_ID DECIMAL(12,0) NOT NULL,
    DOC_HDR_ID varchar(40) NOT NULL,
    PRNCPL_ID varchar(40) NOT NULL,
    ROLE_NM varchar(80) NOT NULL,
    NMSPC_CD varchar(40) NOT NULL,
    UPDATE_TIMESTAMP DATE NOT NULL,
    UPDATE_USER VARCHAR(60) NOT NULL,
    VER_NBR DECIMAL(8,0) DEFAULT 1 NOT NULL,
    OBJ_ID VARCHAR(36) NOT NULL)
/

ALTER TABLE DOCUMENT_ACCESS
ADD CONSTRAINT PK_DOCUMENT_ACCESS
PRIMARY KEY (DOC_ACCESS_ID)
/

ALTER TABLE DOCUMENT_ACCESS
ADD CONSTRAINT UQ_DOCUMENT_ACCESS1
UNIQUE (DOC_HDR_ID, PRNCPL_ID, ROLE_NM, NMSPC_CD)
/

ALTER TABLE DOCUMENT_ACCESS
ADD CONSTRAINT UQ_DOCUMENT_ACCESS2
UNIQUE (OBJ_ID)
/

DELIMITER ;


