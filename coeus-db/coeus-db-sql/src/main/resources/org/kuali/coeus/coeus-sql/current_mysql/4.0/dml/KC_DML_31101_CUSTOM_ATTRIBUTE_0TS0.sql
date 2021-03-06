DELIMITER /

insert into SEQ_CUSTOM_ATTRIBUTE values(NULL)
/
insert into CUSTOM_ATTRIBUTE (ID,NAME,LABEL,DATA_TYPE_CODE,DATA_LENGTH,DEFAULT_VALUE,LOOKUP_CLASS,LOOKUP_RETURN,GROUP_NAME,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR,OBJ_ID)
values (last_insert_id(),'ARRA_FUNDING','ARRA Funding','1',3,null,'org.kuali.kra.bo.ArgValueLookup','yes_no_flag','Other',NOW(),'admin',0,UUID())
/
insert into CUSTOM_ATTRIBUTE_DOCUMENT (DOCUMENT_TYPE_CODE,CUSTOM_ATTRIBUTE_ID,TYPE_NAME,IS_REQUIRED,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR,ACTIVE_FLAG,OBJ_ID)
 values ('PRDV',last_insert_id(),null,'N',NOW(),'admin',0,'Y',UUID())
/
insert into CUSTOM_ATTRIBUTE_DOCUMENT(DOCUMENT_TYPE_CODE,CUSTOM_ATTRIBUTE_ID,TYPE_NAME,IS_REQUIRED,UPDATE_TIMESTAMP,UPDATE_USER,VER_NBR,ACTIVE_FLAG,OBJ_ID)
 values ('INPR',last_insert_id(),null,'N',NOW(),'admin',0,'Y',UUID())
/
commit
/

DELIMITER ;
