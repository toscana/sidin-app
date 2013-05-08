package be.ehb.iwt.sidinapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import be.ehb.iwt.sidin.core.Event;
import be.ehb.iwt.sidin.core.Subscription;
import be.ehb.iwt.sidin.core.Teacher;
import be.ehb.iwt.sidinapp.dialogs.NoInternetDialogFragment;
import be.ehb.iwt.sidinapp.dialogs.SynchronizeFailedDialogFragment;
import be.ehb.iwt.sidinapp.persistence.Db4oHelper;

public class LoginActivity extends FragmentActivity implements OnClickListener {

	public static final String INTENT_DATA_EVENT = "be.ehb.iwt.sidin.event";
	public static final String INTENT_DATA_TEACHER = "be.ehb.iwt.sidin.teacher";

	private boolean mTeachersDownloaded = false;
	private boolean mEventsDownloaded = false;
	private boolean mImagesDownloaded = false;

	private ProgressBar mProgressbar;
	private Button mButtonLogin;
	private Button mButtonSynchronize;
	private TextView mTextViewLoginLoading;

	private Spinner mSpinnerTeachers;
	private Spinner mSpinnerEvents;
	private Button mButtonTryAgain;
	private boolean mSubscriptionsDownloaded = false;
	private boolean mNetworkErrorHappened;
	private TextView mTextViewSynchronizeInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mTextViewLoginLoading = (TextView) findViewById(R.id.TextViewLoginLoading);
		mSpinnerEvents = (Spinner) findViewById(R.id.spinnerEvent);
		mSpinnerTeachers = (Spinner) findViewById(R.id.spinnerTeacher);

		mButtonLogin = (Button) findViewById(R.id.buttonLogIn);
		mButtonLogin.setOnClickListener(this);

		mButtonSynchronize = (Button) findViewById(R.id.buttonSynchronize);
		mButtonSynchronize.setOnClickListener(this);

		mButtonTryAgain = (Button) findViewById(R.id.buttonTryAgainLogin);
		
		mTextViewSynchronizeInfo = (TextView) findViewById(R.id.textViewSynchronizeInfo);

		mButtonTryAgain.setOnClickListener(this);

		mProgressbar = (ProgressBar) findViewById(R.id.progressBarLogin);

		if (Db4oHelper.getInstance(this).exists()) {
		
			TableLayout tl = (TableLayout) findViewById(R.id.tableLayoutLoginScreen);
			makeFieldsVisible(tl);
			loadSpinnerTeachers();
			loadSpinnerEvents();
			
		}

		else {
			downloadData();

		}
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String text = "";
		Resources r= getResources();
		if(Db4oHelper.getInstance(this).hasNewSubscription())
			text += r.getString(R.string.txtHasToSynchronize) + "\n" ;
		SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME,
				0);
		String synchtime = settings.getString(Utilities.SYNCH_TIME, null);
		if(synchtime != null){
			text += r.getString(R.string.txtSynchronizeTime) + " " + synchtime;
		}
		mTextViewSynchronizeInfo.setText(text);
		
		
	}



	private void downloadData() {
		mButtonSynchronize.setVisibility(View.INVISIBLE);
		mImagesDownloaded = false;
		mTeachersDownloaded = false;
		mSubscriptionsDownloaded = false;
		mEventsDownloaded = false;
		mNetworkErrorHappened = false;
		// getting data from webservice and storing them in the local
		// datastorage
		SharedPreferences sp = getSharedPreferences(Utilities.PREFS_NAME, 0);
		String url = sp.getString(Utilities.DEPT_URL, null);

		if (Utilities.hasInternet(this)) {
			mProgressbar.setVisibility(View.VISIBLE);
			mTextViewLoginLoading.setVisibility(View.VISIBLE);
			new GetTeachersASyncTask(this).execute(url);
			new GetEventsASyncTask(this).execute(url);
			new GetImagesASyncTask(this).execute(url);
			new GetSubscriptionsASyncTask(this).execute(url);

		} else {
			mButtonTryAgain.setVisibility(View.VISIBLE);

			NoInternetDialogFragment no = new NoInternetDialogFragment();
			no.show(getSupportFragmentManager(), "nointernet");

		}
	}

	public void putTeachers(List<Teacher> result) {
		if (result != null) {
			Db4oHelper.getInstance(this).storeTeachers(result);

			loadSpinnerTeachers();
			mTeachersDownloaded = true;
			changeProgressBarIfPossible();
		} else {
			synchFailed();
		}

	}

	private void synchFailed() {
		if(!mNetworkErrorHappened){
			mNetworkErrorHappened = true;
		
			makeFieldsVisible();
			mProgressbar.setVisibility(View.INVISIBLE);
			mTextViewLoginLoading.setVisibility(View.INVISIBLE);
			// mButtonTryAgain.setVisibility(View.VISIBLE);
			mButtonSynchronize.setVisibility(View.VISIBLE);

			SynchronizeFailedDialogFragment no = new SynchronizeFailedDialogFragment();
			no.show(getSupportFragmentManager(), "synch failed");
		}
		
	}

	private void changeProgressBarIfPossible() {
		if (mTeachersDownloaded && mEventsDownloaded && mImagesDownloaded
				&& mSubscriptionsDownloaded) {
			mProgressbar.setVisibility(View.INVISIBLE);
			mTextViewLoginLoading.setVisibility(View.INVISIBLE);

			TableLayout tl = (TableLayout) findViewById(R.id.tableLayoutLoginScreen);
			makeFieldsVisible(tl);
			
			mButtonSynchronize.setVisibility(View.VISIBLE);
			
			SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME,
					0);
			SharedPreferences.Editor editor = settings.edit();
			
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String dateString = sd.format(new Date());
			editor.putString(Utilities.SYNCH_TIME,dateString);
			editor.commit();
			Resources r = getResources();
			mTextViewSynchronizeInfo.setText(r.getString(R.string.txtSynchronizeTime) + " "+ dateString);

		}
	}

	private void makeFieldsVisible(View v) {
		if (v instanceof Spinner)
			v.setVisibility(View.VISIBLE);
		else if (v instanceof ViewGroup) {
			int nrChildren = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < nrChildren; i++) {
				makeFieldsVisible(((ViewGroup) v).getChildAt(i));
			}
		} else {
			v.setVisibility(View.VISIBLE);
		}

	}

	private void makeFieldsInVisible(View v) {
		if (v instanceof Spinner) {
			v.setVisibility(View.INVISIBLE);
		} else if (v instanceof ViewGroup) {
			int nrChildren = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < nrChildren; i++) {
				makeFieldsInVisible(((ViewGroup) v).getChildAt(i));

			}
		} else {
			v.setVisibility(View.INVISIBLE);
		}

	}

	private void loadSpinnerTeachers() {
		List<Teacher> teachers = Db4oHelper.getInstance(this)
				.retrieveAllTeachers();
		Collections.sort(teachers);

		Spinner spinner = (Spinner) findViewById(R.id.spinnerTeacher);
	
		SpinnerTeacherAdapter adapter = new SpinnerTeacherAdapter(this,
				R.layout.spinnerlayout, teachers);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	public void putEvents(List<Event> result) {
	
		if (result != null) {
			Db4oHelper.getInstance(this).storeEvents(result);
				loadSpinnerEvents();
			mEventsDownloaded = true;
			changeProgressBarIfPossible();
		} else {
			synchFailed();
		}

	}

	public void putSubscriptions(List<Subscription> result) {

		if (result != null) {
			Db4oHelper.getInstance(this).storeSubscriptions(result);
			
			mSubscriptionsDownloaded = true;
			changeProgressBarIfPossible();
		} else {
			synchFailed();
		}

	}

	private void loadSpinnerEvents() {

		List<Event> events = Db4oHelper.getInstance(this).retrieveAllEvents();
		Collections.sort(events);

		Spinner spinner = (Spinner) findViewById(R.id.spinnerEvent);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout

		SpinnerEventAdapter adapter = new SpinnerEventAdapter(this,
				R.layout.spinnerlayout, events);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.buttonLogIn:
			btnLogIn();
			break;
		case R.id.buttonTryAgainLogin:
			btnTryAgain();
			break;
		case R.id.buttonSynchronize:
			btnSynchronize();
			break;
		}
		// getting selected teacher and event

	}

	private void btnSynchronize() {

		mNetworkErrorHappened = false;
		if (Utilities.hasInternet(this)) {
			mButtonSynchronize.setVisibility(View.INVISIBLE);
			TableLayout tl = (TableLayout) findViewById(R.id.tableLayoutLoginScreen);
			makeFieldsInVisible(tl);

			mProgressbar.setVisibility(View.VISIBLE);
			Resources r = getResources();
			mTextViewLoginLoading.setText(r
					.getString(R.string.txtSynchronizing));
			mTextViewLoginLoading.setVisibility(View.VISIBLE);

			List<Subscription> list;

			list = Db4oHelper.getInstance(this).retrieveAllSubscriptions();

			SharedPreferences sp = getSharedPreferences(Utilities.PREFS_NAME, 0);
			String url = sp.getString(Utilities.DEPT_URL, null);
			new PutSubscriptionsASyncTask(this, list).execute(url);
		} else {
			NoInternetDialogFragment no = new NoInternetDialogFragment();
			no.show(getSupportFragmentManager(), "nointernet");
		}

	}

	private void btnTryAgain() {
		mButtonTryAgain.setVisibility(View.INVISIBLE);
		downloadData();

	}

	private void btnLogIn() {
		Event e = ((SpinnerEventAdapter) mSpinnerEvents.getAdapter())
				.getEventAt(mSpinnerEvents.getSelectedItemPosition());
		Teacher t = ((SpinnerTeacherAdapter) mSpinnerTeachers.getAdapter())
				.getTeacherAt(mSpinnerTeachers.getSelectedItemPosition());
		Intent i = new Intent(this, FormActivity.class);
		i.putExtra(INTENT_DATA_TEACHER, (Parcelable) t);
		i.putExtra(INTENT_DATA_EVENT, (Parcelable) e);

		startActivity(i);

	}

	public void imageDownloadFinished(Boolean result) {
		if (result.booleanValue()) {
			mImagesDownloaded = true;
			changeProgressBarIfPossible();
		} else {
			// foutmelding en afsluiten van programma
			
				synchFailed();

			
		}

	}

	public void subscriptionUploadFinished(Boolean result) {
		if(result.booleanValue()){
			Db4oHelper.getInstance(this).eraseDB();
			eraseImages();
			downloadData();
		}
		else{
			synchFailed();
		}
	

	}

	private void makeFieldsVisible() {
		TableLayout tl = (TableLayout) findViewById(R.id.tableLayoutLoginScreen);
		makeFieldsVisible(tl);
		
	}

	private void eraseImages() {
		// TODO Auto-generated method stub
		int currentImage = 0;

		while (true) {

			File file = getFileStreamPath("pic" + currentImage + "resized.jpg");
			File fileNormal = getFileStreamPath("pic" + currentImage + ".jpg");
			if (file.exists()) {
				file.delete();
				fileNormal.delete();
			} else
				break;
			currentImage++;
		}

	}

}
