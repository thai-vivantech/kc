ALTER TABLE PROTOCOL_ATTACHMENT_PROTOCOL 
  ADD ( 
    DOCUMENT_STATUS_CODE VARCHAR ( 3 ) ,
    CREATE_TIMESTAMP DATETIME NOT NULL ,
    ATTACHMENT_VERSION DECIMAL ( 4, 0 ) 
    ) ;