package be.ehb.iwt.sidin.core;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Serializable, Parcelable,Comparable<Event> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2383629800536599520L;

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event(Long id, String name, int acadyear) {
		super();
		this.id = id;
		this.name = name;
		this.acadyear = acadyear;
	}

	private String name;
	private int acadyear;

	public Event(String name, int acadyear) {
		super();
		this.name = name;
		this.acadyear = acadyear;
	}

	public Event() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(int acadyear) {
		this.acadyear = acadyear;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeInt(acadyear);
	}

	public Event(Parcel in) {
		this.id = in.readLong();
		this.name = in.readString();
		this.acadyear = in.readInt();
	}
	
	@Override
	public int compareTo(Event arg0) {
		return this.getName().compareTo((arg0).getName());
	}

	public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

		@Override
		public Event createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Event(source); // using parcelable constructor
		}

		@Override
		public Event[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Event[size];
		}
	};

}
