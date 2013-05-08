package be.ehb.iwt.sidin.core;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Subscription implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9104710043135978616L;

	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String street;
	private String streetNumber;
	private int zip;
	private String city;
	private HashMap<String,String> interests;
	private Date timestamp;
	@Override
	public String toString() {
		return "Subscription [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", street="
				+ street + ", streetNumber=" + streetNumber + ", zip=" + zip
				+ ", city=" + city + ", interests=" + interests
				+ ", timestamp=" + timestamp + ", teacher=" + teacher
				+ ", event=" + event + ", isNew=" + isNew + "]";
	}

	private Teacher teacher;
	private Event event;
	private boolean isNew;
	
	
	
	
	public Subscription(Long id, String firstName, String lastName,
			String email, String street, String streetNumber, int zip,
			String city, HashMap<String, String> interests, Date timestamp,
			Teacher teacher, Event event, boolean isNew) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zip = zip;
		this.city = city;
		this.interests = interests;
		this.timestamp = timestamp;
		this.teacher = teacher;
		this.event = event;
		this.isNew = isNew;
	}



	public Subscription(String firstName, String lastName, String email,
			String street, String streetNumber, int zip, String city,
			HashMap<String, String> interests, Date timestamp, Teacher teacher,
			Event event, boolean isNew) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.street = street;
		this.streetNumber = streetNumber;
		this.zip = zip;
		this.city = city;
		this.interests = interests;
		this.timestamp = timestamp;
		this.teacher = teacher;
		this.event = event;
		this.isNew = isNew;
	}



	public Subscription() {
		super();
	}

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public HashMap<String, String> getInterests() {
		return interests;
	}

	public void setInterests(HashMap<String, String> interests) {
		this.interests = interests;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	
	public boolean isNew() {
		return isNew;
	}



	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}



	public Long getId() {
		return id;
	}
	
	
	

}
