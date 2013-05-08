package be.ehb.iwt.sidin.core;


import java.io.Serializable;
import java.util.List;

public class EventList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5209920233972515221L;
	/**
	 * 
	 */
	
	private List<Event> events;

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public EventList(List<Event> events) {
		super();
		this.events = events;
	}

	public EventList() {
		super();
	}
	
	

}
