package be.ehb.iwt.sidin.core;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface IDeptResource {
	@Get
	public Department retrieve();
	
	@Post
	public void store(Department t);
	
	@Delete
	public void remove();
}
