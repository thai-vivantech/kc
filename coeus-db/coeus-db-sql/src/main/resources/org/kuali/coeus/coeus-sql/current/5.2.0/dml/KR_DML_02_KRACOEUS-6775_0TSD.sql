INSERT INTO KRIM_GRP_MBR_T ( GRP_MBR_ID, VER_NBR, OBJ_ID, GRP_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)VALUES (CONCAT ('KC', KRIM_GRP_MBR_ID_S.NEXTVAL),'1',SYS_GUID(),(SELECT GRP_ID FROM KRIM_GRP_T WHERE GRP_NM='AwardAdmin' AND NMSPC_CD='KC-WKFLW'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM='quickstart'), 'P', NULL, NULL, SYSDATE)
/
INSERT INTO KRIM_GRP_MBR_T ( GRP_MBR_ID, VER_NBR, OBJ_ID, GRP_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)VALUES (CONCAT ('KC', KRIM_GRP_MBR_ID_S.NEXTVAL),'1',SYS_GUID(),(SELECT GRP_ID FROM KRIM_GRP_T WHERE GRP_NM='AwardAdmin' AND NMSPC_CD='KC-WKFLW'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM='awdmodifier'), 'P', NULL, NULL, SYSDATE)
/
