package be.ehb.iwt.sidinapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.ehb.iwt.sidin.core.Event;
import be.ehb.iwt.sidin.core.Subscription;
import be.ehb.iwt.sidin.core.Teacher;
import be.ehb.iwt.sidinapp.dialogs.WrongInputDialogFragment;
import be.ehb.iwt.sidinapp.persistence.Db4oHelper;
import be.ehb.iwt.sidinapp.persistence.ZipDB;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import android.view.MotionEvent;

public class FormActivity extends FragmentActivity implements TextWatcher,
		ViewFactory, OnClickListener, OnFocusChangeListener {
	private EditText mEditTextZip;
	// private ArrayList<String> mCityNames;
	private Spinner mSpinnerCity;
	private ImageSwitcher mImageSwitcher;
	private int currentImage = 0;
	private ImageChangeTask mImageTask;
	private EditText mEditTextEmail;
	private EditText mEditTextFirstName;
	private EditText mEditTextLastName;
	private EditText mEditTextStreet;
	private EditText mEditTextStreetNumber;
	private CheckBox mCheckBoxDigX;
	private CheckBox mCheckBoxMultec;
	private CheckBox mCheckBoxWorkStudent;
	private Button mButtonReset;
	private Button mButtonSave;
	private Teacher mCurrentTeacher;
	private Event mCurrentEvent;
	private String mErrorMessageFields;
	private AutoCompleteTextView mSearchField;
	private StudentSearchAdapter mSearchAdapter;
	private SpinnerCityAdapter mSpinnerCityAdapter;
	private ScrollView mScrollView;
	private int mScreenHeight;
	private View mActivityRootView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentform);

		// getting data from intent
		Intent intent = getIntent();
		mCurrentEvent = intent.getExtras().getParcelable(
				LoginActivity.INTENT_DATA_EVENT);
		mCurrentTeacher = intent.getExtras().getParcelable(
				LoginActivity.INTENT_DATA_TEACHER);

		initializeGuiFields();

		mSpinnerCityAdapter = new SpinnerCityAdapter(this,
				R.layout.spinnerlayoutzips);
		mSpinnerCity.setAdapter(mSpinnerCityAdapter);

		mSearchAdapter = new StudentSearchAdapter(this,
				R.id.autoCompleteTextViewSearchStudent);
		mSearchField.setAdapter(mSearchAdapter);

		// TODO added code
		mEditTextLastName.setOnFocusChangeListener(this);
		mEditTextStreet.setOnFocusChangeListener(this);
		mEditTextZip.setOnFocusChangeListener(this);
		mEditTextFirstName.setOnFocusChangeListener(this);
		mEditTextStreetNumber.setOnFocusChangeListener(this);

		// actionlisteners for buttons
		mButtonReset.setOnClickListener(this);
		mButtonSave.setOnClickListener(this);

		mEditTextZip.addTextChangedListener(this);
		addListenerForSearch();

		mImageSwitcher.setFactory(this);
		mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right));

		Bitmap bmp = BitmapFactory.decodeFile(FormActivity.this
				.getFileStreamPath(("pic" + currentImage + "resized.jpg"))
				.getAbsolutePath());
		// DisplayMetrics metrics = null;
		// FormActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		// bmp.setDensity(metrics.densityDpi);

		BitmapDrawable d = new BitmapDrawable(FormActivity.this.getResources(),
				bmp);

		mImageSwitcher.setImageDrawable(d);

		try {
			ZipDB.createDatabaseIfNotExists(this);
		} catch (IOException e) {
		}
		
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenHeight = metrics.heightPixels;

		// setting imageswitcher properties for switching between images

		// is.setImageResource(R.drawable.show2);

		// set scrolling view to not refocus when image is changed

		ScrollView view = (ScrollView) findViewById(R.id.scrollView1);
		view.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return false;
			}
		});

		
		//code to scroll up the scrollview when keyboard is hidden
		
		mActivityRootView = findViewById(android.R.id.content);
		mActivityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
		    @Override
		    public void onGlobalLayout() {
		    	 Rect r = new Rect();
		    	    //r will be populated with the coordinates of your view that area still visible.
		    	    mActivityRootView.getWindowVisibleDisplayFrame(r);
		    	    
		    	    int heightDiff = mActivityRootView.getRootView().getHeight() - (r.bottom - r.top);
		    	    Log.d("bert","getheight is " + mActivityRootView.getRootView().getHeight()+ "other is "  +(r.bottom - r.top)); 
		    	    if (heightDiff == 0) { // if more than 100 pixels, its probably a keyboard...
		    	        //... do something here
		    	    	mScrollView.scrollTo(0, 0);
		    	    }
		     }
		});
		
	}


	
	private void addListenerForSearch() {
		mSearchField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String email = mSearchField.getText().toString();
				if (email.length() >= 3) {
					// looking in database email adresses for match

					List<Subscription> subList = Db4oHelper.getInstance(
							FormActivity.this).querySubscriptions(email);
					mSearchAdapter.setList(subList);
					if (subList != null && subList.size() != 0)
						if (subList
								.get(0)
								.getEmail()
								.equalsIgnoreCase(
										mSearchField.getText().toString())) {
							fillFormWithSubscription(subList.get(0));
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(
									mSearchField.getWindowToken(), 0);
						}

				}

			}
		});

	}

	protected void fillFormWithSubscription(Subscription subscription) {
		// TODO Auto-generated method stub
		mEditTextEmail.setText(subscription.getEmail());
		mEditTextFirstName.setText(subscription.getFirstName());
		mEditTextLastName.setText(subscription.getLastName());
		mEditTextStreet.setText(subscription.getStreet());
		mEditTextStreetNumber.setText(subscription.getStreetNumber());
		if (subscription.getZip() != 0) {
			mEditTextZip.setText(String.valueOf(subscription.getZip()));
			List<String> zips = mSpinnerCityAdapter.getZips();
			for (int i = 0; i < zips.size(); i++)
				if (zips.get(i).equalsIgnoreCase(subscription.getCity()))
					mSpinnerCity.setSelection(i);
		}
		mSearchField.setText("");

	}

	private void resetForm(View v) {
		if (v instanceof ViewGroup) {
			int nrChildren = ((ViewGroup) v).getChildCount();
			for (int i = 0; i < nrChildren; i++) {
				resetForm(((ViewGroup) v).getChildAt(i));
			}
		} else {
			if (v instanceof EditText)
				((EditText) v).setText("");
			else if (v instanceof CheckBox)
				((CheckBox) v).setChecked(false);
		}
	}

	private void resetForm() {
		ViewGroup l = (ViewGroup) findViewById(R.id.scrollView1);
		resetForm(l);
		mSpinnerCityAdapter.setZips(new ArrayList<String>());
	}

	private void initializeGuiFields() {
		mEditTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
		mEditTextLastName = (EditText) findViewById(R.id.editTextLastName);
		mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
		mEditTextStreet = (EditText) findViewById(R.id.editTextStreet);
		mEditTextStreetNumber = (EditText) findViewById(R.id.editTextStreetNumber);

		mCheckBoxDigX = (CheckBox) findViewById(R.id.checkBoxDigx);
		mCheckBoxMultec = (CheckBox) findViewById(R.id.checkBoxMultec);
		mCheckBoxWorkStudent = (CheckBox) findViewById(R.id.checkBoxWerkstudent);

		mEditTextZip = (EditText) findViewById(R.id.editTextZip);
		mSpinnerCity = (Spinner) findViewById(R.id.spinnerCity);
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

		mButtonReset = (Button) findViewById(R.id.buttonEraseData);
		mButtonSave = (Button) findViewById(R.id.buttonSaveData);

		mSearchField = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewSearchStudent);

		mScrollView = (ScrollView) findViewById(R.id.scrollView1);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mImageTask = new ImageChangeTask();
		mImageTask.execute();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mImageTask.cancel(true);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mImageTask.cancel(true);
	}

	@Override
	public void afterTextChanged(Editable s) {
		String postcode = mEditTextZip.getText().toString();
		if (postcode.length() == 4) {
			ArrayList<String> cityNames = new ArrayList<String>();
			SQLiteDatabase db = ZipDB.getStaticDb(this);
			Cursor c = db.query("postcodes", new String[] { "postcode",
					"gemeente" }, "postcode='" + postcode + "'", null, null,
					null, null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				String naam = c.getString(1);
				cityNames.add(naam);
				while (!c.isLast()) {
					c.moveToNext();
					naam = c.getString(1);
					cityNames.add(naam);
				}

				if (c.getCount() > 1)
					mSpinnerCity.setPressed(true);
				else
					mSpinnerCity.setPressed(false);

				mSpinnerCityAdapter.setZips(cityNames);
			}
			c.close();
			db.close();

		} else {
			mSpinnerCity.setPressed(false);
			mSpinnerCityAdapter.setZips(new ArrayList<String>());
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public View makeView() {
		ImageView iv = new ImageView(this);
		// iv.setImageResource(R.drawable.show1);
		iv.setBackgroundColor(Color.WHITE);
		return iv;
	}

	private class ImageChangeTask extends AsyncTask<Void, Void, Void> {

		protected void onProgressUpdate(Void... params) {

			currentImage++;
			File file = FormActivity.this.getFileStreamPath("pic"
					+ currentImage + "resized.jpg");
			if (!file.exists()) {
				currentImage = 0;
				file = FormActivity.this.getFileStreamPath("pic" + currentImage
						+ "resized.jpg");
			}

			Bitmap bmp = BitmapFactory.decodeFile(FormActivity.this
					.getFileStreamPath(("pic" + currentImage + "resized.jpg"))
					.getAbsolutePath());
			// DisplayMetrics metrics = null;
			// FormActivity.this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			// bmp.setDensity(metrics.densityDpi);

			BitmapDrawable d = new BitmapDrawable(
					FormActivity.this.getResources(), bmp);

			// Log.d("bert","intrinsic width is "+ d.getIntrinsicWidth());
			mImageSwitcher.setImageDrawable(d);
		}

		protected void onPostExecute(Void result) {
			// showDialog("Downloaded " + result + " bytes");
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (!this.isCancelled()) {
				try {
					Thread.sleep(5000);
					publishProgress();
				} catch (InterruptedException e) {
				}
			}
			return null;
		}
	}

	private boolean getInputOk() {
		mErrorMessageFields = "";
		boolean ok = true;

		String temp = mEditTextFirstName.getText().toString();
		if (temp.length() == 0) {
			ok = false;
			mErrorMessageFields += "\n naam";
		}

		temp = mEditTextLastName.getText().toString();
		if (temp.length() == 0) {
			ok = false;
			mErrorMessageFields += "\n voornaam";
		}

		temp = mEditTextZip.getText().toString();
		if (temp.length() != 4 && temp.length() != 0) {
			ok = false;
			mErrorMessageFields += "\n postcode";
		}

		temp = mEditTextEmail.getText().toString();
		if (temp.length() == 0) {
			ok = false;
			mErrorMessageFields += "\n email";
		}

		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(temp);
		if (!m.matches()) {
			ok = false;
			mErrorMessageFields += "\n email (geen geldig email adres)";
		}

		/*
		 * temp = mPostCodeEditText.getText().toString(); if (temp.length() ==
		 * 0) { ok = false; mErrorMessageFields += "\n postcode"; }
		 */

		// check keuze opleiding
		if (!(mCheckBoxDigX.isChecked() || mCheckBoxMultec.isChecked() || mCheckBoxWorkStudent
				.isChecked())) {
			ok = false;
			mErrorMessageFields += "\n kies minstens 1 opleiding waarin je interesse hebt";
		}
		return ok;
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.buttonSaveData:
			btnSaveData();
			break;

		case R.id.buttonEraseData:
			btnEraseData();
			break;
		}

	}

	private void btnEraseData() {
		resetForm();
		mScrollView.scrollTo(0, 0);
		mEditTextEmail.requestFocus();

	}

	private void btnSaveData() {
		if (!getInputOk()) {
			WrongInputDialogFragment w = new WrongInputDialogFragment(
					mErrorMessageFields);
			w.show(getSupportFragmentManager(), "forminputwrong");
		} else {
			HashMap<String, String> interests = new HashMap<String, String>();
			interests
					.put("multec", String.valueOf(mCheckBoxMultec.isChecked()));
			interests.put("digx", String.valueOf(mCheckBoxDigX.isChecked()));
			interests.put("werkstudent",
					String.valueOf(mCheckBoxWorkStudent.isChecked()));

			int zip = 0;
			try {
				zip = Integer.valueOf(mEditTextZip.getText().toString())
						.intValue();
			} catch (Exception e) {
			}

			String street = "";
			try {
				street = mEditTextStreet.getText().toString();
			} catch (Exception e) {
			}

			String streetNumber = "";
			try {
				streetNumber = mEditTextStreetNumber.getText().toString();
			} catch (Exception e) {
			}

			String city = "";
			try {
				city = mSpinnerCity.getSelectedItem().toString();
			} catch (Exception e) {
			}

			Db4oHelper.getInstance(this).storeSubscription(
					new Subscription(mEditTextFirstName.getText().toString(),
							mEditTextLastName.getText().toString(),
							mEditTextEmail.getText().toString(), street,
							streetNumber, zip, city, interests, new Date(),
							mCurrentTeacher, mCurrentEvent, true));
			Resources r = getResources();

			Toast t = Toast.makeText(this,
					r.getString(R.string.formSavedMessage), Toast.LENGTH_LONG);
			t.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
					0, 0);
			t.show();
			resetForm();
			// scrollview naar boven scrollen
			// op grote tablet is er geen scroll dus check
			ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
			if (sv != null)
				sv.smoothScrollTo(0, 0);

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(mButtonSave.getWindowToken(), 0);
		}

	}

	
	@Override
	public void onFocusChange(final View v, boolean hasFocus) {
		// TODO Auto-generated method stub

		
		
		if (hasFocus) {
			Log.d("bert", "scroling to " +((TableRow)v.getParent()).getTop());
			new Handler().post(new Runnable() {
				@Override
				public void run() {
					
					mScrollView.smoothScrollTo(0, ((TableRow)v.getParent()).getTop());
					//mScrollView.scrollBy(0, 50);
				}
			});
		}
	}

}
