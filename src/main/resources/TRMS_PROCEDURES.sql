CREATE OR REPLACE PROCEDURE CREATE_USER(USERNAME VARCHAR2, PASSWORDHASH NUMBER, LNAME VARCHAR2, FNAME VARCHAR2, MNAME VARCHAR2, EMAIL VARCHAR2, PHONE VARCHAR2, SUPER_ID NUMBER, DEPT_ID NUMBER)
IS
BEGIN
INSERT INTO USER_LIST VALUES (id_maker.nextval, USERNAME, PASSWORDHASH, LNAME, FNAME, MNAME, EMAIL, PHONE, SUPER_ID, DEPT_ID);
END;

CREATE OR REPLACE PROCEDURE CREATE_TRANSACTION(FY NUMBER, EMP_ID NUMBER, TRANSACTION_VALUE NUMBER, STATUS NUMBER, TIMEVAL NUMBER, ADDRESS VARCHAR2, START_DATE VARCHAR2, GRADE VARCHAR2)
IS
BEGIN
INSERT INTO ACCOUNTS VALUES (id_maker.nextval, FY, EMP_ID, TRANSACTION_VALUE, STATUS, TIMEVAL, ADDRESS, START_DATE, GRADE);
END;

