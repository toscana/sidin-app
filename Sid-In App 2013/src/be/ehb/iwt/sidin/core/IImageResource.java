package be.ehb.iwt.sidin.core;


import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public interface IImageResource {
	
	@Post
	public void store(Image i);
	
	@Get
	public ImageList retrieve();
	
	@Delete
	public void remove();

}
