package be.ehb.iwt.sidinapp;

import java.util.List;

import org.restlet.resource.ClientResource;

import be.ehb.iwt.sidin.core.Subscription;
import be.ehb.iwt.sidin.core.SubscriptionList;
import be.ehb.iwt.sidin.core.ISubscriptionsResource;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;
import android.os.AsyncTask;

public class GetSubscriptionsASyncTask extends AsyncTask<String, Void, List<Subscription>> {

	private LoginActivity mLoginActivity;
	
	public GetSubscriptionsASyncTask(LoginActivity la) {
		super();
		this.mLoginActivity= la;
	}

	//first parameter is the URL with which to connect
	@Override
	protected List<Subscription> doInBackground(String... arg0) {
		
				String url = arg0[0];
				
				EngineConfiguration.getInstance();
				ClientResource cr = new ClientResource("http://" + url + "/subscriptions");
				ISubscriptionsResource resource = cr.wrap(ISubscriptionsResource.class);

				try{
				// Get the remote contact
				SubscriptionList list = resource.retrieve();
				
				return list.getSubscriptions();
				}
				catch(Exception e){return null;}
	}

	@Override
	protected void onPostExecute(List<Subscription> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		mLoginActivity.putSubscriptions(result);
	}
	
	
	
	

}
