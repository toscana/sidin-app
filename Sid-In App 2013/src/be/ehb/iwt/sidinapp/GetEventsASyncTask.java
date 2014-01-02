package be.ehb.iwt.sidinapp;

import java.util.List;

import org.restlet.resource.ClientResource;

import be.ehb.iwt.sidin.core.Event;
import be.ehb.iwt.sidin.core.EventList;
import be.ehb.iwt.sidin.core.IEventsResource;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;
import android.os.AsyncTask;

public class GetEventsASyncTask extends AsyncTask<String, Void, List<Event>> {

	private LoginActivity mLoginActivity;

	public GetEventsASyncTask(LoginActivity la) {
		super();
		this.mLoginActivity = la;
	}

	// first parameter is the URL with which to connect
	@Override
	protected List<Event> doInBackground(String... arg0) {

		String url = arg0[0];

		EngineConfiguration.getInstance();
		ClientResource cr = new ClientResource("http://" + url + "/events/" + Utilities.year);
		IEventsResource resource = cr.wrap(IEventsResource.class);

		try {
			// Get the remote contact
			EventList list = resource.retrieve();
			return list.getEvents();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<Event> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		mLoginActivity.putEvents(result);
	}

}
