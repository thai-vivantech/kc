CREATE SEQUENCE SEQ_PERSON_EDITABLE_FIELD
    INCREMENT BY 1
    START WITH 1001
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    NOCACHE
    ORDER;

CREATE SEQUENCE SEQ_BGT_SUM_PER_CALC_AMT_ID 
    INCREMENT BY 1 
    START WITH 1
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    NOCACHE
    ORDER;

CREATE SEQUENCE SEQ_AWRD_BDGT_LMT_ID 
    INCREMENT BY 1 
    START WITH 1 
    MAXVALUE 9999999999999999999999999999
    NOMINVALUE
    NOCYCLE
    NOCACHE
    ORDER;

CREATE SEQUENCE SEQ_NOTIFICATION_TYPE_ID
    INCREMENT BY 1 
    START WITH 10000 
    MINVALUE 1 
    MAXVALUE 9999999999999999999999999999
    NOCACHE 
    NOORDER 
    NOCYCLE;