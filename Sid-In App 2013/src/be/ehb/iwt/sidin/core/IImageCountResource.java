package be.ehb.iwt.sidin.core;

import org.restlet.resource.Get;

public interface IImageCountResource {
	

	
	@Get
	public Integer retrieve();

}
