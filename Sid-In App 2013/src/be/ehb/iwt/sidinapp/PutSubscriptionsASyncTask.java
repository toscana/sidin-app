package be.ehb.iwt.sidinapp;

import java.util.List;

import org.restlet.resource.ClientResource;

import android.os.AsyncTask;
import be.ehb.iwt.sidin.core.ISubscriptionResource;
import be.ehb.iwt.sidin.core.Subscription;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;


public class PutSubscriptionsASyncTask extends AsyncTask<String, Void, Boolean> {

	private LoginActivity mLoginActivity;
	private List<Subscription> mSubList;

	public PutSubscriptionsASyncTask(LoginActivity acti, List<Subscription> subs) {
		super();
		this.mLoginActivity = acti;
		this.mSubList = subs;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mLoginActivity.subscriptionUploadFinished(result);
	}

	// first parameter is the URL with which to connect
	@Override
	protected Boolean doInBackground(String... arg0) {

		String url = arg0[0];

		EngineConfiguration.getInstance();

		ClientResource cr = new ClientResource("http://" + url
				+ "/subscription");
		cr.setRequestEntityBuffering(true);
		try {
			
			ISubscriptionResource resource = cr
					.wrap(ISubscriptionResource.class);
			for (Subscription s : mSubList) {
				if (s.isNew()) {

					s.setNew(false);
					resource.store(s);
					
					}

			}
			return Boolean.valueOf(true);
		} catch (Exception e) {
			return Boolean.valueOf(false);
		}

	}
}
