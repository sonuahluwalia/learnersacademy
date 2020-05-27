package com.learner.model;

public class Subject {
	int subject_id;
	String subject_name;
	int class_id;

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public int getClass_id() {
		return class_id;
	}

	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}

	@Override
	public String toString() {
		return "Subject [subject_id=" + subject_id + 
				", subject_name=" + subject_name + ", class_id=" + class_id
				+ "]";
	}

	public Subject(int subject_id, String subject_name, int class_id) {
		super();
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.class_id = class_id;
	}

	public Subject() {
		
	}
}
