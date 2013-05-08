package be.ehb.iwt.sidin.core;

import java.io.Serializable;
import java.util.List;

public class TeacherList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5517300947491552916L;
	private List<Teacher> teachers;

	public TeacherList() {
		super();
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	

}
