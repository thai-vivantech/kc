-- Foreign Key Constraint(s) 
ALTER TABLE QUESTIONNAIRE_ANSWER 
    ADD CONSTRAINT FK_QUESTIONNAIRE_ANS_COMP_ID FOREIGN KEY (QUESTIONNAIRE_AH_ID_FK) 
                REFERENCES QUESTIONNAIRE_ANSWER_HEADER (QUESTIONNAIRE_ANSWER_HEADER_ID) ;