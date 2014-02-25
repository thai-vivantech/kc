CREATE TABLE NOTIFICATION (
  NOTIFICATION_ID      NUMBER(20,0) NOT NULL,
  NOTIFICATION_TYPE_ID NUMBER(6,0) NOT NULL,
  DOCUMENT_NUMBER      VARCHAR2(40 BYTE) NOT NULL,
  SUBJECT              VARCHAR2(1000 BYTE),
  MESSAGE              VARCHAR2(4000 BYTE),
  UPDATE_USER          VARCHAR2(60 BYTE) NOT NULL,
  UPDATE_TIMESTAMP     DATE NOT NULL,
  VER_NBR              NUMBER(8, 0) DEFAULT 1 NOT NULL,
  OBJ_ID               VARCHAR2(36 BYTE) NOT NULL 
);

ALTER TABLE NOTIFICATION
  ADD CONSTRAINT PK_NOTIFICATION PRIMARY KEY (NOTIFICATION_ID);