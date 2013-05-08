package be.ehb.iwt.sidin.core;

import java.io.Serializable;
import java.util.List;

public class SubscriptionList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3498952619618662678L;
	private List<Subscription> subscriptions;

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public SubscriptionList(List<Subscription> subscriptions) {
		super();
		this.subscriptions = subscriptions;
	}

	public SubscriptionList() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
