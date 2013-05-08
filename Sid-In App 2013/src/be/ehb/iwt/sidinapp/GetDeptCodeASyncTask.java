package be.ehb.iwt.sidinapp;

import java.util.List;

import org.restlet.resource.ClientResource;

import be.ehb.iwt.sidin.core.Department;
import be.ehb.iwt.sidin.core.DeptList;
import be.ehb.iwt.sidin.core.IDeptsResource;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;
import android.os.AsyncTask;

public class GetDeptCodeASyncTask extends
		AsyncTask<String, Void, List<Department>> {

	private DeptCodeStartActivity dcsa;

	public GetDeptCodeASyncTask(DeptCodeStartActivity dcsa) {
		super();
		this.dcsa = dcsa;
	}

	@Override
	protected List<Department> doInBackground(String... arg0) {
		// connect to webservice
		// Initialize the resource proxy.

		EngineConfiguration.getInstance();
		ClientResource cr = new ClientResource("http://"
				+ dcsa.getString(R.string.deptCodeURL) + "/depts");
		IDeptsResource resource = cr.wrap(IDeptsResource.class);

		// Get the remote contact
		try {
			DeptList list = resource.retrieve();
			return list.getDepartments();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	protected void onPostExecute(List<Department> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dcsa.putDepartments(result);
	}

}
