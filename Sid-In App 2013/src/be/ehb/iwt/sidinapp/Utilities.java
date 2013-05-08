package be.ehb.iwt.sidinapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utilities {

	public static boolean hasInternet(Context context) {

		boolean available = false;
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							available = true;
						}
					}
				}
			}
			if (available == false) {
				NetworkInfo wiMax = connectivity.getNetworkInfo(6);

				if (wiMax != null && wiMax.isConnected()) {
					available = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return available;

	}

	static final String PREFS_NAME = "deptcode";
	static final String DEPT_URL = "DEPTURL";
	static final String DEPT_CODE = "DEPTCODE";
	static final String DEPT_FRIENDLY_NAME = "DEPTFRIENDLYNAME";
	static final String SYNCH_TIME = "SYNCHTIME";
	
}
