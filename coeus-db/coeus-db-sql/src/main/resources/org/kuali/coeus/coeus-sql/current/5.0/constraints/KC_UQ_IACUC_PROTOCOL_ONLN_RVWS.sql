ALTER TABLE IACUC_PROTOCOL_ONLN_RVWS
    ADD ( CONSTRAINT UQ1_IACUC_PROTOCOL_ONLN_RVWS
    UNIQUE (PROTOCOL_ID, SUBMISSION_ID_FK, PROTOCOL_REVIEWER_FK)
)
/
