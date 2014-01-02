package be.ehb.iwt.sidinapp;

import java.util.List;

import be.ehb.iwt.sidin.core.Department;
import be.ehb.iwt.sidinapp.dialogs.DeptNotFoundDialogFragment;
import be.ehb.iwt.sidinapp.dialogs.NoInternetDialogFragment;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DeptCodeStartActivity extends FragmentActivity implements
		OnClickListener {
	private ProgressBar mProgressBar;
	private TextView mTextViewDeptCode;
	private EditText mEditTextDeptCode;
	private Button mButtonNext;
	private List<Department> mDepts;
	private Button mButtonTryAgain;
	private TextView mTextViewDeptLoading;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//instantiate year for database access
		String year;
		Time now = new Time();
		now.setToNow();
		
		Time startAcadYear = new Time();
		startAcadYear.year = now.year;
		startAcadYear.month = 9;
		startAcadYear.monthDay = 22;
			
		String lastTwo = (Integer.toString(now.year)).substring(2,4);
		if(now.before(startAcadYear)){
			//still in acadyear before september
			year = (Integer.valueOf(lastTwo)-1) + lastTwo;
			Log.d("bert","acadyear to us is" + year);
		}
		else{
			//still in acadyear before september			
			year = lastTwo + (Integer.valueOf(lastTwo)+1);
			Log.d("bert","acadyear to us is" + year);
		}
		
		Utilities.year = year;

		// instantiate datamembers for gui
		mProgressBar = (ProgressBar) findViewById(R.id.progressBarDeptScreen);
		mTextViewDeptCode = (TextView) findViewById(R.id.textViewDeptCode);
		mEditTextDeptCode = (EditText) findViewById(R.id.editTextDeptCode);
		mTextViewDeptLoading = (TextView) findViewById(R.id.textViewDeptScreenLoadText);
		mButtonNext = (Button) findViewById(R.id.buttonDeptCodeNext);
		mButtonNext.setOnClickListener(this);
		mProgressBar.setIndeterminate(true);
		mButtonTryAgain = (Button) findViewById(R.id.buttonTryAgain);

		mButtonTryAgain.setOnClickListener(this);

		// check if a departmental code has already been entered
		String deptCode = getDeptCodeFromSharedPrefs();

		if (deptCode != null) {
			// code has already been previously entered
			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			finish();
		} else {
			downloadData();
		}

	}

	private void downloadData() {
		if (Utilities.hasInternet(this)) {
			mProgressBar.setVisibility(View.VISIBLE);
			mTextViewDeptLoading.setVisibility(View.VISIBLE);
			new GetDeptCodeASyncTask(this).execute();
		} else {
			mButtonTryAgain.setVisibility(View.VISIBLE);

			NoInternetDialogFragment no = new NoInternetDialogFragment();
			no.show(getSupportFragmentManager(), "nointernet");
		}
	}

	private String getDeptCodeFromSharedPrefs() {
		SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME,
				0);
		return settings.getString(Utilities.DEPT_URL, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void putDepartments(List<Department> result) {
		if (result != null) {
			mProgressBar.setVisibility(View.INVISIBLE);
			mTextViewDeptLoading.setVisibility(View.INVISIBLE);
			mTextViewDeptCode.setVisibility(View.VISIBLE);
			mEditTextDeptCode.setVisibility(View.VISIBLE);
			mButtonNext.setVisibility(View.VISIBLE);
			mDepts = result;
		} else {
			mProgressBar.setVisibility(View.INVISIBLE);
			mButtonTryAgain.setVisibility(View.VISIBLE);
			NoInternetDialogFragment no = new NoInternetDialogFragment();
			no.show(getSupportFragmentManager(), "nointernet");
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.buttonDeptCodeNext:
			btnDeptCodeNext();
			break;
		case R.id.buttonTryAgain:
			btnTryAgain();
			break;
		}

	}

	private void btnDeptCodeNext() {
		String deptcode = mEditTextDeptCode.getText().toString();
		int idx = -1;
		for (Department d : mDepts) {
			if (d.getCode().equals(deptcode)) {
				idx = mDepts.indexOf(d);
			}
		}

		if (idx >= 0) {
			saveDeptCodeToSharedPrefs(mDepts.get(idx));

			Intent i = new Intent(this, LoginActivity.class);
			startActivity(i);
			this.finish();

		} else {
			DeptNotFoundDialogFragment fm = new DeptNotFoundDialogFragment();
			fm.show(getSupportFragmentManager(), "wrongdeptcode");
		}
	}

	private void saveDeptCodeToSharedPrefs(Department department) {
		SharedPreferences settings = getSharedPreferences(Utilities.PREFS_NAME,
				0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(Utilities.DEPT_URL, department.getUrl());
		editor.putString(Utilities.DEPT_FRIENDLY_NAME, department.getName());
		editor.putString(Utilities.DEPT_CODE, department.getCode());
		editor.commit();

	}

	private void btnTryAgain() {
		mButtonTryAgain.setVisibility(View.INVISIBLE);
		downloadData();

	}

}
