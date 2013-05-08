package be.ehb.iwt.sidin.core;

import java.util.List;
import java.io.Serializable;

public class DeptList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 642599244962751113L;
	private List<Department> departments;
	
	public DeptList(List<Department> depts){
		this.departments = depts;
	}
	
	public DeptList(){
		
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}
	
	

}
