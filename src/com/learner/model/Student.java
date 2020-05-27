package com.learner.model;

public class Student {

	 int student_id;
	 String student_name ;
	 int class_id;
	 int age;
	 char gender;
	 int maths_grade;
	 int english_grade;
	 int social_science_grade;
	 int art_grade;
	 int science_grade;
	 int final_grade;
	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
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
	public int getMaths_grade() {
		return maths_grade;
	}
	public void setMaths_grade(int maths_grade) {
		this.maths_grade = maths_grade;
	}
	public int getEnglish_grade() {
		return english_grade;
	}
	public void setEnglish_grade(int english_grade) {
		this.english_grade = english_grade;
	}
	public int getSocial_science_grade() {
		return social_science_grade;
	}
	public void setSocial_science_grade(int social_science_grade) {
		this.social_science_grade = social_science_grade;
	}
	public int getArt_grade() {
		return art_grade;
	}
	public void setArt_grade(int art_grade) {
		this.art_grade = art_grade;
	}
	public int getScience_grade() {
		return science_grade;
	}
	public void setScience_grade(int science_grade) {
		this.science_grade = science_grade;
	}
	public int getFinal_grade() {
		return final_grade;
	}
	public void setFinal_grade(int final_grade) {
		this.final_grade = final_grade;
	}
	public Student(int student_id, String student_name, int class_id, int age, char gender, int maths_grade,
			int english_grade, int social_science_grade, int art_grade, int science_grade, int final_grade) {
		super();
		this.student_id = student_id;
		this.student_name = student_name;
		this.class_id = class_id;
		this.age = age;
		this.gender = gender;
		this.maths_grade = maths_grade;
		this.english_grade = english_grade;
		this.social_science_grade = social_science_grade;
		this.art_grade = art_grade;
		this.science_grade = science_grade;
		this.final_grade = final_grade;
	}
	 
	public Student() {
	}
	
	@Override
	public String toString() {
		return "Student [student_id=" + student_id + ", student_name=" + student_name + ", class_id=" + class_id
				+ ", age=" + age + ", gender=" + gender + ", maths_grade=" + maths_grade + ", english_grade="
				+ english_grade + ", social_science_grade=" + social_science_grade + ", art_grade=" + art_grade
				+ ", science_grade=" + science_grade + ", final_grade=" + final_grade + "]";
	}
	
	
	 
}
