ALTER TABLE SUBAWARD_AMT_RELEASED
  ADD CONSTRAINT FK_SUBAWARD_AMT_RELEASED 
  FOREIGN KEY (SUBAWARD_ID)  REFERENCES SUBAWARD (SUBAWARD_ID)
/

