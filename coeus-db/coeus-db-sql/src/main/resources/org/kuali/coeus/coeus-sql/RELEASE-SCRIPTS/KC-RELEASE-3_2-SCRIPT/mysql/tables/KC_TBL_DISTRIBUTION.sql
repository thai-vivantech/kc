DELIMITER /
ALTER TABLE DISTRIBUTION
	ADD ACTIVE_FLAG CHAR(1) DEFAULT 'Y' NOT NULL
/
DELIMITER ;
