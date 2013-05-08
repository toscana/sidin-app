package be.ehb.iwt.sidinapp.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import be.ehb.iwt.sidin.core.Event;
import be.ehb.iwt.sidin.core.Subscription;
import be.ehb.iwt.sidin.core.Teacher;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class Db4oHelper {

	private static Db4oHelper uniqueInstance = new Db4oHelper();
	private static final String SIDINDB_DB4O_TEACH = "sidindbteach.db4o";
	private static final String SIDINDB_DB4O_SUBS = "sidindbsubs.db4o";
	private static final String SIDINDB_DB4O_EVENT = "sidindbevents.db4o";


	private ObjectContainer oc = null;

	private Context context;

	public static Db4oHelper getInstance(Context ctx) {
		uniqueInstance.context = ctx;
		return uniqueInstance;
	}

	/**
	 * Create, open and close the database
	 */
	private void openTeach() {

		try {
			if (oc == null || oc.ext().isClosed()) {
				oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
						db4oDBFullPathTeacher(context));
			}
		} catch (Exception ie) {
			Log.e(Db4oHelper.class.getName(), ie.toString());
		}
	}
	
	private String db4oDBFullPathTeacher(Context ctx) {
		return ctx.getDir("data", 0) + "/" + SIDINDB_DB4O_TEACH;
	}

	/**
	 * Create, open and close the database
	 */
	private void openSubscription() {

		try {
			if (oc == null || oc.ext().isClosed()) {
				oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
						db4oDBFullPathSubscription(context));
			}
		} catch (Exception ie) {
			Log.e(Db4oHelper.class.getName(), ie.toString());
		}
	}
	
	private String db4oDBFullPathSubscription(Context context) {
		return context.getDir("data", 0) + "/" + SIDINDB_DB4O_SUBS;
	}

	/**
	 * Create, open and close the database
	 */
	private void openEvent() {

		try {
			if (oc == null || oc.ext().isClosed()) {
				oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),
						db4oDBFullPathEvents(context));
			}
		} catch (Exception ie) {
			Log.e(Db4oHelper.class.getName(), ie.toString());
		}
	}

	private String db4oDBFullPathEvents(Context context) {
		return context.getDir("data", 0) + "/" + SIDINDB_DB4O_EVENT;
	}

	public boolean exists() {
		File file = new File(context.getDir("data", 0), SIDINDB_DB4O_TEACH);
		return file.exists();
	}



	/**
	 * Closes the database
	 */

	private void close() {
		if (oc != null)
			oc.close();

	}

	public List<Teacher> retrieveAllTeachers() {
		openTeach();
		ObjectSet<Teacher> result = oc.queryByExample(Teacher.class);
		int size = result.size();
		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < size; i++)
			teachers.add(result.get(i));
		close();
		return teachers;
	}

	public void storeTeachers(List<Teacher> teachers) {
		openTeach();
		for (Teacher t : teachers) {
			oc.store(t);
			
		}
		close();
	}

	public List<Event> retrieveAllEvents() {
		openEvent();
		ObjectSet<Event> result = oc.queryByExample(Event.class);
		int size = result.size();
		List<Event> events = new ArrayList<Event>();
		for (int i = 0; i < size; i++)
			events.add(result.get(i));
		close();
		return events;
	}

	public void storeEvents(List<Event> result) {
		openEvent();
		for (Event e : result) {
			oc.store(e);
			
		}
		close();

	}

	public void storeSubscription(Subscription subscription) {
		openSubscription();
		oc.store(subscription);
		close();
	}

	public List<Subscription> retrieveAllSubscriptions() {
		openSubscription();
		ObjectSet<Subscription> result = oc.queryByExample(Subscription.class);
		int size = result.size();
		List<Subscription> subs = new ArrayList<Subscription>();
		for (int i = 0; i < size; i++)
			subs.add(result.get(i));
		close();
		return subs;
	}
	
	public boolean hasNewSubscription() {
		
		openSubscription();
		ObjectSet<Subscription> result = oc.queryByExample(Subscription.class);
		for(Subscription s: result)
			if(s.isNew())
				return true;
		close();
		return false;
	}

	public void storeSubscriptions(List<Subscription> subs) {
		openSubscription();
		for (Subscription s : subs)
			oc.store(s);
		close();

	}

	public void eraseDB() {
		openEvent();
		ObjectSet<Object> objects = oc.queryByExample(null);
		for (Object object : objects) {
			oc.delete(object);
		}
		close();
		
		openTeach();
		objects = oc.queryByExample(null);
		for (Object object : objects) {
			oc.delete(object);
		}
		close();
		
		openSubscription();
		objects = oc.queryByExample(null);
		for (Object object : objects) {
			oc.delete(object);
		}
		close();

	}

	public List<Subscription> querySubscriptions(final String email) {
		openSubscription();
		ObjectSet<Subscription> subs = oc.query(new Predicate<Subscription>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2489327875277810856L;

			public boolean match(Subscription s) {
				return s.getEmail().toLowerCase(Locale.getDefault()).startsWith(email.toLowerCase());
			}
		});
		ArrayList<Subscription> subslist = new ArrayList<Subscription>();
		for (Subscription s : subs)
			subslist.add(s);
		close();
		return subslist;
	}
}
