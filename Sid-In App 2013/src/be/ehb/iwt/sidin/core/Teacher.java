package be.ehb.iwt.sidin.core;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Teacher implements Serializable, Parcelable,Comparable<Teacher> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6426267251685790899L;

	public Long getId() {
		return id;
	}

	private Long id;
	private String name;
	private int acadyear;

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

	public Teacher(String name, int acadyear) {
		super();
		this.name = name;
		this.acadyear = acadyear;
	}

	public Teacher(Long id, String name, int acadyear) {
		super();
		this.id = id;
		this.name = name;
		this.acadyear = acadyear;
	}

	public Teacher() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Teacher(Parcel in) {
		String[] data = new String[3];
		this.id = in.readLong();
		this.name = in.readString();
		this.acadyear = in.readInt();
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
		/*dest.writeStringArray(new String[] {String.valueOf(id), name,
				String.valueOf(acadyear) });*/

	}

	public static final Parcelable.Creator<Teacher> CREATOR = new Parcelable.Creator<Teacher>() {

		@Override
		public Teacher createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Teacher(source); // using parcelable constructor
		}

		@Override
		public Teacher[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Teacher[size];
		}
	};

	@Override
	public int compareTo(Teacher arg0) {
		return this.getName().compareTo((arg0).getName());
	}

}
