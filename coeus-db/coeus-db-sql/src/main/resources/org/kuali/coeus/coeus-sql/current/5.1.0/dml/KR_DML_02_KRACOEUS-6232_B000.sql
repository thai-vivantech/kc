
INSERT INTO KRIM_ROLE_T (ROLE_ID,KIM_TYP_ID,NMSPC_CD,ROLE_NM,DESC_TXT,ACTV_IND,LAST_UPDT_DT,OBJ_ID,VER_NBR) 
    VALUES (KRIM_ROLE_ID_BS_S.NEXTVAL,(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KUALI' AND NM = 'Default'),'KC-WKFLW','IACUCWorkflowApprover',
    'IACUC Protocol document workflow approver','Y',SYSDATE,SYS_GUID(),1)
/

INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID,ROLE_ID,MBR_TYP_CD,MBR_ID,LAST_UPDT_DT,OBJ_ID,VER_NBR)
    VALUES (KRIM_ROLE_MBR_ID_BS_S.NEXTVAL,(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KC-WKFLW' AND ROLE_NM = 'IACUCWorkflowApprover'),'R',
    (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KC-UNT' AND ROLE_NM = 'IACUC Administrator'),SYSDATE,SYS_GUID(),1)
/

INSERT INTO KRIM_ROLE_MBR_T (ROLE_MBR_ID,ROLE_ID,MBR_TYP_CD,MBR_ID,LAST_UPDT_DT,OBJ_ID,VER_NBR)
    VALUES (KRIM_ROLE_MBR_ID_BS_S.NEXTVAL,(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KC-WKFLW' AND ROLE_NM = 'IACUCWorkflowApprover'),'R',
    (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KC-IACUC' AND ROLE_NM = 'IACUC ProtocolApprover'),SYSDATE,SYS_GUID(),1)
/

INSERT INTO KRIM_RSP_T (RSP_ID,RSP_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR) VALUES 
(KRIM_RSP_ID_BS_S.NEXTVAL,(SELECT RSP_TMPL_ID FROM KRIM_RSP_TMPL_T WHERE NMSPC_CD = 'KR-WKFLW' AND NM = 'Review'), 'KC-WKFLW',
'IACUC Approve','IACUC Protocol Document - IACUCApprove','Y',SYS_GUID(),1)
/


INSERT INTO KRIM_ROLE_RSP_T (ROLE_RSP_ID,ROLE_ID,RSP_ID,ACTV_IND,OBJ_ID,VER_NBR) 
    VALUES (KRIM_ROLE_RSP_ID_BS_S.NEXTVAL,(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE NMSPC_CD = 'KC-WKFLW' AND ROLE_NM = 'IACUCWorkflowApprover'),
    (SELECT RSP_ID FROM KRIM_RSP_T WHERE NMSPC_CD = 'KC-WKFLW' AND NM = 'IACUC Approve'),'Y',SYS_GUID(),1)
/


INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, OBJ_ID) VALUES 
    (KRIM_ATTR_DATA_ID_BS_S.NEXTVAL, (SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve'),
    (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM = 'Document Type, Routing Node & Action Information'), 
    (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'documentTypeName' AND NMSPC_CD = 'KR-WKFLW'), 'IacucProtocolDocument',
     SYS_GUID())
/
-- node name
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,RSP_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR) 
    VALUES (KRIM_ATTR_DATA_ID_BS_S.NEXTVAL,(SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-WKFLW' AND 
    NM = 'Document Type, Routing Node & Action Information'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KR-WKFLW' AND
    NM = 'routeNodeName'),'IACUCReview',SYS_GUID(),1)
/

-- if node is mandatory
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, OBJ_ID) VALUES 
(KRIM_ATTR_DATA_ID_BS_S.NEXTVAL, (SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-WKFLW' AND 
    NM = 'Document Type, Routing Node & Action Information'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'required'),
'false', SYS_GUID())
/
-- action
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID, RSP_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL, OBJ_ID) VALUES 
(KRIM_ATTR_DATA_ID_BS_S.NEXTVAL, (SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve'), (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KR-WKFLW' AND 
NM = 'Document Type, Routing Node & Action Information'), 
(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM = 'actionDetailsAtRoleMemberLevel' AND NMSPC_CD = 'KR-WKFLW'), 'false', SYS_GUID())
/

-- inserting action for responsibility
-- ACTN_TYP_CD = 'A' indicates "approve" action
INSERT INTO krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN) values 
(KRIM_ROLE_RSP_ACTN_ID_BS_S.NEXTVAL, sys_guid(), '1', 'A', '1', 'F', '*', (SELECT ROLE_RSP_ID FROM KRIM_ROLE_RSP_T 
WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC ProtocolApprover') AND RSP_ID = 
(SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve')),'Y')
/



INSERT INTO KRIM_ROLE_RSP_T (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND) VALUES 
(KRIM_ROLE_RSP_ID_BS_S.NEXTVAL, SYS_GUID(), '1', (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC Administrator') , 
(SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve'), 'Y')
/

INSERT INTO krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN) values 
(KRIM_ROLE_RSP_ACTN_ID_BS_S.NEXTVAL, sys_guid(), '1', 'A', '1', 'F', '*', (SELECT ROLE_RSP_ID FROM KRIM_ROLE_RSP_T 
WHERE ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUC Administrator') AND RSP_ID = (SELECT RSP_ID FROM KRIM_RSP_T 
WHERE NM = 'IACUC Approve')),'Y')
/


INSERT INTO krim_role_rsp_actn_t (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN) 
values (KRIM_ROLE_RSP_ACTN_ID_BS_S.NEXTVAL, sys_guid(), '1', 'A', '1', 'F', '*', (SELECT ROLE_RSP_ID FROM KRIM_ROLE_RSP_T WHERE ROLE_ID = 
(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'IACUCWorkflowApprover') AND RSP_ID = (SELECT RSP_ID FROM KRIM_RSP_T WHERE NM = 'IACUC Approve')),'Y')
/


