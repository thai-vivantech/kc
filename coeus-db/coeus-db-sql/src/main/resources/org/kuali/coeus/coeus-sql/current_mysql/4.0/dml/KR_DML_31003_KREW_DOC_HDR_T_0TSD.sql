DELIMITER /
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID,DOC_TYP_ID,DOC_HDR_STAT_CD,TTL,INITR_PRNCPL_ID,RTE_PRNCPL_ID,DOC_VER_NBR,RTE_LVL,CRTE_DT,RTE_STAT_MDFN_DT,APRV_DT,FNL_DT,STAT_MDFN_DT,OBJ_ID,VER_NBR) 
    VALUES (3094,(SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'CommitteeDocument'),'F','Committee Document - IRB Committee',(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'),(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'),1,0,NOW(),NOW(),NOW(),NOW(),NOW(),UUID(),24)
/
DELIMITER ;
