
SET SERVEROUT ON;
--drop tables
DROP TABLE CLASSES CASCADE CONSTRAINTS;
DROP TABLE TEACHERS CASCADE CONSTRAINTS;
DROP TABLE SUBJECTS CASCADE CONSTRAINTS ;
DROP TABLE STUDENTS CASCADE CONSTRAINTS;
DROP TABLE LOGIN CASCADE CONSTRAINTS;

-- create tables
CREATE TABLE LOGIN 
(
username VARCHAR2(20),
password VARCHAR2(20)
);

CREATE TABLE CLASSES
( class_id number(10) NOT NULL,  
  class_name varchar2(50) NOT NULL,
  CONSTRAINT classes_pk PRIMARY KEY ( class_id)
);

CREATE TABLE TEACHERS
( teacher_id number(10) NOT NULL,
  teacher_name varchar2(50) NOT NULL,
  class_id number(10) NULL,
 subject_id number(10) NULL,
  qualification varchar2(50),
  experience_years number(10),
  age number(10),
  gender char(1),
  CONSTRAINT teacher_id_pk PRIMARY KEY (teacher_id),
  CONSTRAINT fk_teacher_classes
    FOREIGN KEY ( class_id)
    REFERENCES CLASSES( class_id)
  ON DELETE SET NULL
);

CREATE TABLE SUBJECTS
( subject_id number(10) NOT NULL,
  subject_name varchar2(50) NOT NULL,
  class_id number(10) NULL ,
  CONSTRAINT subject_id_pk PRIMARY KEY (subject_id),
  CONSTRAINT fk_subjects_classes
    FOREIGN KEY (class_id)
    REFERENCES CLASSES(class_id)
  ON DELETE SET NULL
);



CREATE TABLE STUDENTS
( student_id number(10) NOT NULL,
  student_name varchar2(50) NOT NULL,
  class_id number(10) NULL,
  age number(10),
  gender char(1),
  maths_grade number(10),
  english_grade number(10),
  social_science_grade number(10),
  art_grade number(10),
  science_grade number(10),
  final_grade number(10),
  CONSTRAINT student_id PRIMARY KEY (student_id),
  CONSTRAINT fk_students_classes
    FOREIGN KEY ( class_id)
    REFERENCES CLASSES( class_id)
  ON DELETE SET NULL
);

-- login table data
INSERT INTO login(username, password) VALUES ('admin', 'admin');


-- classes table data
DROP SEQUENCE classes_seq;
CREATE SEQUENCE classes_seq
INCREMENT BY 1
START WITH 1
nocache nocycle;

DROP SEQUENCE classes_name_seq;
CREATE SEQUENCE classes_name_seq
INCREMENT BY 1
START WITH 1
nocache nocycle;


DELETE FROM classes;

CREATE OR REPLACE  PROCEDURE insert_classes     
IS
BEGIN
FOR i IN 1..12 LOOP
INSERT INTO classes (class_id, class_name) VALUES (CLASSES_SEQ.nextval, 'Class ' || classes_name_seq.nextval);
END LOOP;
END insert_classes;
/

exec insert_classes;

-- teachers table data

DELETE FROM teachers;
DROP SEQUENCE teachers_seq;
CREATE SEQUENCE teachers_seq
INCREMENT BY 1
START WITH 1000
nocache nocycle;

DROP SEQUENCE teacher_classes_seq;
CREATE SEQUENCE teacher_classes_seq
INCREMENT BY 1
START WITH 1
nocache nocycle;


DROP SEQUENCE teacher_subjects_seq;
CREATE SEQUENCE teacher_subjects_seq
INCREMENT BY 1
START WITH 100 
nocache nocycle;


CREATE OR REPLACE  PROCEDURE insert_teachers     
IS
BEGIN
FOR i IN 1..12 LOOP

INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, qualification, experience_years, age, gender)
VALUES (teachers_seq.nextval, 'Santosh Singh', TEACHER_CLASSES_SEQ.nextval,TEACHER_SUBJECTS_SEQ.nextval  , 'M.Tech', 10, 42, 'M' );

INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, qualification, experience_years, age, gender)
VALUES (teachers_seq.nextval, 'Mary Dsoza', TEACHER_CLASSES_SEQ.currval,TEACHER_SUBJECTS_SEQ.nextval  , 'B.Tech', 8, 24, 'F' );

INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, qualification, experience_years, age, gender)
VALUES (teachers_seq.nextval, 'Sachin Kumar', TEACHER_CLASSES_SEQ.currval,TEACHER_SUBJECTS_SEQ.nextval  , 'M.Tech', 6, 36, 'M' );

INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, qualification, experience_years, age, gender)
VALUES (teachers_seq.nextval, 'Susan Farallel', TEACHER_CLASSES_SEQ.currval,TEACHER_SUBJECTS_SEQ.nextval  , 'B.Tech', 11, 29, 'F' );

INSERT INTO teachers(teacher_id, teacher_name, class_id, subject_id, qualification, experience_years, age, gender)
VALUES (teachers_seq.nextval, 'John Doe', TEACHER_CLASSES_SEQ.currval,TEACHER_SUBJECTS_SEQ.nextval  , 'M.Tech', 4, 32, 'M' );

END LOOP;
END insert_teachers;
/
exec insert_teachers;


-- subjects table data

DELETE FROM subjects;
DROP SEQUENCE subjects_seq;
CREATE SEQUENCE subjects_seq
INCREMENT BY 1
START WITH 100 
nocache nocycle;

DROP SEQUENCE subject_name_seq;
CREATE SEQUENCE subject_name_seq
INCREMENT BY 1
START WITH 101 
nocache nocycle;

DROP SEQUENCE subject_classes_seq;
CREATE SEQUENCE subject_classes_seq
INCREMENT BY 1
START WITH 1
nocache nocycle;

CREATE OR REPLACE  PROCEDURE insert_subjects     
IS
BEGIN
FOR i IN 1..12 LOOP
INSERT INTO subjects (subject_id, subject_name, class_id) 
VALUES (SUBJECTS_SEQ.nextval, 'Maths' || SUBJECT_NAME_SEQ.nextval, SUBJECT_CLASSES_SEQ.nextval);

INSERT INTO subjects (subject_id, subject_name, class_id) 
VALUES (SUBJECTS_SEQ.nextval, 'Science' || SUBJECT_NAME_SEQ.currval, SUBJECT_CLASSES_SEQ.currval);

INSERT INTO subjects (subject_id, subject_name, class_id) 
VALUES (SUBJECTS_SEQ.nextval, 'English' || SUBJECT_NAME_SEQ.currval, SUBJECT_CLASSES_SEQ.currval);

INSERT INTO subjects (subject_id, subject_name, class_id) 
VALUES (SUBJECTS_SEQ.nextval, 'Art' || SUBJECT_NAME_SEQ.currval, SUBJECT_CLASSES_SEQ.currval);

INSERT INTO subjects (subject_id, subject_name, class_id) 
VALUES (SUBJECTS_SEQ.nextval, 'Social Science' || SUBJECT_NAME_SEQ.currval, SUBJECT_CLASSES_SEQ.currval);
END LOOP;
END insert_subjects;
/

exec insert_subjects;

-- students table data

DELETE FROM students;
DROP SEQUENCE students_seq;
CREATE SEQUENCE students_seq
INCREMENT BY 1
START WITH 2000
nocache nocycle;

DROP SEQUENCE student_classes_seq;
CREATE SEQUENCE student_classes_seq
INCREMENT BY 1
START WITH 1
nocache nocycle;

--  ,maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade


CREATE OR REPLACE  PROCEDURE insert_students     
IS
BEGIN
FOR i IN 1..12 LOOP
INSERT INTO students (student_id, student_name, class_id,  age, gender, maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade) 
VALUES (STUDENTS_SEQ.nextval, 'John Doe' , STUDENT_CLASSES_SEQ.nextval, 12, 'M', 82, 74, 56, 89, 48, 90);

INSERT INTO students (student_id, student_name, class_id,  age, gender ,maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade) 
VALUES (STUDENTS_SEQ.nextval, 'Mary Jane' , STUDENT_CLASSES_SEQ.currval, 14, 'F', 68, 98, 54, 28, 18, 74);

INSERT INTO students (student_id, student_name, class_id,  age, gender ,maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade) 
VALUES (STUDENTS_SEQ.nextval, 'Sachin' , STUDENT_CLASSES_SEQ.currval, 11, 'M', 45 ,64, 89, 74, 65, 80);

INSERT INTO students (student_id, student_name, class_id,  age, gender ,maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade) 
VALUES (STUDENTS_SEQ.nextval, 'Gaurav' , STUDENT_CLASSES_SEQ.currval, 16, 'M', 79, 14, 11, 55, 77, 87);

INSERT INTO students (student_id, student_name, class_id,  age, gender ,maths_grade,  english_grade, social_science_grade, art_grade, science_grade, final_grade) 
VALUES (STUDENTS_SEQ.nextval, 'Smith' , STUDENT_CLASSES_SEQ.currval, 12, 'M', 52, 25, 64, 56, 87, 48);

END LOOP;
END insert_students;
/

exec insert_students;

CREATE OR REPLACE PROCEDURE next_subject_id (
    v_next_subject_id OUT NUMBER
) IS
BEGIN
    SELECT
        MAX(subject_id)
    INTO v_next_subject_id
    FROM
        subjects;
    v_next_subject_id := v_next_subject_id + 1;
END;
/

CREATE OR REPLACE PROCEDURE next_class_id (
    v_next_class_id OUT NUMBER
) IS
BEGIN
    SELECT
        MAX(class_id)
    INTO v_next_class_id
    FROM
        classes;
    v_next_class_id := v_next_class_id + 1;
END;
/

CREATE OR REPLACE PROCEDURE next_teacher_id (
    v_next_teacher_id OUT NUMBER
) IS
BEGIN
    SELECT
        MAX(teacher_id)
    INTO v_next_teacher_id
    FROM
        teachers;
    v_next_teacher_id := v_next_teacher_id + 1;
END;
/

CREATE OR REPLACE PROCEDURE next_student_id (
    v_next_student_id OUT NUMBER
) IS
BEGIN
    SELECT
        MAX(student_id)
    INTO v_next_student_id
    FROM
        students;
    v_next_student_id := v_next_student_id + 1;
END;
/

commit;

