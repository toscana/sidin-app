package be.ehb.iwt.sidin.core;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;


public interface ITeacherResource {
	@Get
	public Teacher retrieve();
	
	@Post
	public void store(Teacher t);
	
	@Delete
	public void remove();
}
