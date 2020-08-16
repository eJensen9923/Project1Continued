INSERT INTO DEPARTMENTS VALUES(
    1,
    'HR',
    null
);

INSERT INTO DEPARTMENTS VALUES(
    2,
    'TestDept1',
    null
);

INSERT INTO DEPARTMENTS VALUES(
    3,
    'TestDept2',
    null
);

INSERT INTO USER_LIST VALUES(
    10,
    'headbencotest',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    'headbenco@fakemail.com',
    '1-111-111-1111',
    null,
    1
);

INSERT INTO PERMISSIONS VALUES(
    10,0
);
INSERT INTO PERMISSIONS VALUES(
    10,1
);
INSERT INTO PERMISSIONS VALUES(
    10,2
);

UPDATE DEPARTMENTS SET emp_id = 10 WHERE dept_id = 1;

INSERT INTO USER_LIST VALUES(
    11,
    'bencotest',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    'benco@fakemail.com',
    '1-111-111-1111',
    10,
    1
);

INSERT INTO PERMISSIONS VALUES(
    11,0
);
INSERT INTO PERMISSIONS VALUES(
    11,2
);

INSERT INTO USER_LIST VALUES(
    12,
    'deptheadtest1',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    'depthead1@fakemail.com',
    '1-111-111-1111',
    null,
    2
);

INSERT INTO PERMISSIONS VALUES(
    12,0
);
INSERT INTO PERMISSIONS VALUES(
    12,1
);

UPDATE DEPARTMENTS SET emp_id = 12 WHERE dept_id = 2;

INSERT INTO USER_LIST VALUES(
    13,
    'deptheadtest2',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    'depthead2@fakemail.com',
    '1-111-111-1111',
    null,
    3
);

INSERT INTO PERMISSIONS VALUES(
    13,0
);
INSERT INTO PERMISSIONS VALUES(
    13,1
);

UPDATE DEPARTMENTS SET emp_id = 13 WHERE dept_id = 3;

INSERT INTO USER_LIST VALUES(
    14,
    'test1',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    '1@fakemail.com',
    '1-111-111-1111',
    12,
    2
);

INSERT INTO PERMISSIONS VALUES(
    14,0
);

INSERT INTO USER_LIST VALUES(
    15,
    'test2',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    '2@fakemail.com',
    '1-111-111-1111',
    12,
    2
);

INSERT INTO PERMISSIONS VALUES(
    15,0
);

INSERT INTO USER_LIST VALUES(
    16,
    'test3',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    '3@fakemail.com',
    '1-111-111-1111',
    13,
    3
);

INSERT INTO PERMISSIONS VALUES(
    16,0
);

INSERT INTO USER_LIST VALUES(
    17,
    'test4',
    1216985755,
    'ltest',
    'ftest',
    'mtest',
    '4@fakemail.com',
    '1-111-111-1111',
    16,
    3
);

INSERT INTO PERMISSIONS VALUES(
    17,0
);

--------------------------------------