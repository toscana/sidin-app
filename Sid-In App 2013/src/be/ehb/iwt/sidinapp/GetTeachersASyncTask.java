package be.ehb.iwt.sidinapp;

import java.util.List;

import org.restlet.resource.ClientResource;

import be.ehb.iwt.sidin.core.ITeachersResource;
import be.ehb.iwt.sidin.core.Teacher;
import be.ehb.iwt.sidin.core.TeacherList;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;
import android.os.AsyncTask;

public class GetTeachersASyncTask extends
		AsyncTask<String, Void, List<Teacher>> {

	private LoginActivity mLoginActivity;

	public GetTeachersASyncTask(LoginActivity la) {
		super();
		this.mLoginActivity = la;
	}

	// first parameter is the URL with which to connect
	@Override
	protected List<Teacher> doInBackground(String... arg0) {

		String url = arg0[0];

		EngineConfiguration.getInstance();
		ClientResource cr = new ClientResource("http://" + url + "/teachers");
		ITeachersResource resource = cr.wrap(ITeachersResource.class);
		try {
			TeacherList list = resource.retrieve();
			return list.getTeachers();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<Teacher> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		mLoginActivity.putTeachers(result);
	}

}
