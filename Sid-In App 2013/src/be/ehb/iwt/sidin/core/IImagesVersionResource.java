package be.ehb.iwt.sidin.core;


import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface IImagesVersionResource {
	
	@Get
	public Integer retrieve();

}
