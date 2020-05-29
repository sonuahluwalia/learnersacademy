select s.subject_id, s.subject_name, s.class_id, s.teacher_id
from subjects s, classes c, teachers t
where s.class_id = c.class_id
and s.teacher_id = t.teacher_id;

insert into subjects(subject_id, subject_name, class_id, teacher_id)
values (SUBJECTS_SEQ.nextval, 'Testing null', null, null);

select s.subject_id, s.subject_name, c.class_name, t.teacher_name, s.class_id, s.teacher_id
from subjects s
left join classes c on s.class_id = c.class_id
left join  teachers t on s.teacher_id = t.teacher_id;

SET SERVEROUT ON;
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

DECLARE
v_next_subject_id NUMBER(10);
BEGIN
next_subject_id(v_next_subject_id);
dbms_output.put_line(v_next_subject_id);
END;


select * from subjects;

select * from classes where class_name = 'Class 1';
SELECT
    *
FROM classes;

insert into classes (class_id, class_name) values (?, ?);

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

DECLARE
v_next_class_id NUMBER(10);
BEGIN
next_class_id(v_next_class_id);
dbms_output.put_line(v_next_class_id);
END;


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

DECLARE
v_next_teacher_id NUMBER(10);
BEGIN
next_teacher_id(v_next_teacher_id);
dbms_output.put_line(v_next_teacher_id);
END;

select * from teachers;


select distinct teacher_name from teachers ;
         
         
select t.teacher_id, t.teacher_name, t.class_id, t.qualification, 
t.experience_years, t.age, t.gender, t.subject_id, t.class_id, c.class_name, s.subject_name from teachers t
left join classes c on t.class_id = c.class_id
left join  subjects s on s.subject_id = t.subject_id;


delete from classes where class_id = 1;


UPDATE students SET student_name = 'sonu', class_id = 1, age = 12, 
gender = 'M', maths_grade = 12, english_grade = 12, social_science_grade = 12, 
art_grade = 12 , science_grade = 12, final_grade = 12 WHERE student_id = 2000;

select * from students;