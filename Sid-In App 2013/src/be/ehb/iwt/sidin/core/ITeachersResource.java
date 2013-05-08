package be.ehb.iwt.sidin.core;

import org.restlet.resource.Get;



public interface ITeachersResource {
	
	@Get
	public TeacherList retrieve();

}
