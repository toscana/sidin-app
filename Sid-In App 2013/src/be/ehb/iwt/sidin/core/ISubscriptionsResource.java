package be.ehb.iwt.sidin.core;

import org.restlet.resource.Get;

public interface ISubscriptionsResource {
	@Get
	public SubscriptionList retrieve();

}
