package be.ehb.iwt.sidin.core;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;



public interface ISubscriptionResource {
	@Get
	public Subscription retrieve();
	
	@Post
	public void store(Subscription e);
	
	@Delete
	public void remove();

	
}
