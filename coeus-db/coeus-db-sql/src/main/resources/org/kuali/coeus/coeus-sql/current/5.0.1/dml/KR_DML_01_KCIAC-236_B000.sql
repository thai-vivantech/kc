INSERT INTO KRIM_ROLE_T (ACTV_IND,DESC_TXT,KIM_TYP_ID,LAST_UPDT_DT,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR)
       VALUES ('Y','IACUC Approver',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='UnitHierarchy'),SYSDATE,'KC-UNT',SYS_GUID(),KRIM_ROLE_ID_BS_S.NEXTVAL,'IACUCApprover',1)
/
