CREATE TABLE IACUC_SUBMISSION_TYPE_QUALIF (
    SUBMISSION_TYPE_QUAL_CODE NUMBER(3,0) NOT NULL,
    DESCRIPTION VARCHAR2(200) NOT NULL,
    UPDATE_TIMESTAMP DATE NOT NULL,
    UPDATE_USER VARCHAR2(60) NOT NULL,
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL,
    OBJ_ID VARCHAR2(36) NOT NULL)
/
ALTER TABLE IACUC_SUBMISSION_TYPE_QUALIF
ADD CONSTRAINT PK_IACUC_SUBMISSION_TYPE_QUALI
PRIMARY KEY (SUBMISSION_TYPE_QUAL_CODE)
/
