ALTER TABLE FIN_ENTITIES_DATA_MATRIX 
ADD CONSTRAINT FK1_FIN_ENTITIES_DATA_MATRIX 
FOREIGN KEY (DATA_GROUP_ID) 
REFERENCES FIN_ENTITIES_DATA_GROUPS (DATA_GROUP_ID)
/