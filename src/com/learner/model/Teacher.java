package com.learner.model;

public class Teacher {

	int teacher_id;
	String teacher_name;
	int class_id;
	int subject_id;
	String qualification;
	int experience_years;
	int age;
	char gender;

	
	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public int getExperience_years() {
		return experience_years;
	}

	public void setExperience_years(int experience_years) {
		this.experience_years = experience_years;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}


	public Teacher() {
		
	}

	public Teacher(int teacher_id, String teacher_name, int class_id, int subject_id, String qualification,
			int experience_years, int age, char gender) {
		super();
		this.teacher_id = teacher_id;
		this.teacher_name = teacher_name;
		this.class_id = class_id;
		this.subject_id = subject_id;
		this.qualification = qualification;
		this.experience_years = experience_years;
		this.age = age;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Teacher [teacher_id=" + teacher_id + ", teacher_name=" + teacher_name + ", class_id=" + class_id
				+ ", subject_id=" + subject_id + ", qualification=" + qualification + ", experience_years="
				+ experience_years + ", age=" + age + ", gender=" + gender + "]";
	}

	
}
