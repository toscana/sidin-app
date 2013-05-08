package be.ehb.iwt.sidin.core;

import org.restlet.resource.Get;


public interface IEventsResource {
	
	@Get
	public EventList retrieve();

}
