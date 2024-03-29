package be.ehb.iwt.sidinapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.restlet.resource.ClientResource;

import be.ehb.iwt.sidin.core.Department;
import be.ehb.iwt.sidin.core.IImageResource;
import be.ehb.iwt.sidin.core.IImagesVersionResource;
import be.ehb.iwt.sidin.core.Image;
import be.ehb.iwt.sidin.core.ImageList;
import be.ehb.iwt.sidinapp.restlet.EngineConfiguration;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

public class GetImagesASyncTask extends AsyncTask<String, Void, Boolean> {

	private LoginActivity mLoginActivity;

	public GetImagesASyncTask(LoginActivity acti) {
		super();
		this.mLoginActivity = acti;
	}

	private int getImagesVersionFromSharedPrefs() {
		SharedPreferences settings = mLoginActivity.getSharedPreferences(Utilities.PREFS_NAME, 0);
		int result = settings.getInt(Utilities.IMAGES_VERSION, -1);
		return result;
	}

	private void saveImagesVersionToSharedPrefs(int version) {
		SharedPreferences settings = mLoginActivity.getSharedPreferences(Utilities.PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(Utilities.IMAGES_VERSION, version);
		editor.commit();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		mLoginActivity.imageDownloadFinished(result);
	}

	// first parameter is the URL with which to connect
	@Override
	protected Boolean doInBackground(String... arg0) {

		String url = arg0[0];

		EngineConfiguration.getInstance();
		ClientResource cr = new ClientResource("http://" + url + "/imagesversion");
		IImagesVersionResource vresource = cr.wrap(IImagesVersionResource.class);

		int versionNumber;
		int localVersion;

		// first check the version of the images, if not changed do not get
		// images
		try {
			// Get the remote version number
			versionNumber = vresource.retrieve();

			// get the local version number
			localVersion = getImagesVersionFromSharedPrefs();

		} catch (Exception e) {
			return Boolean.valueOf(false);
		}

		if (localVersion < versionNumber) {

			eraseImages();

			cr = new ClientResource("http://" + url + "/images");
			IImageResource resource = cr.wrap(IImageResource.class);

			try {
				// Get the remote contact

				ImageList list = resource.retrieve();
				List<Image> fotos = list.getImages();
				Collections.sort(fotos);

				for (int i = 0; i < fotos.size(); i++) {
					try {

						FileOutputStream fo = mLoginActivity.openFileOutput("pic" + i + ".jpg", Context.MODE_PRIVATE);
						fo.write(fotos.get(i).getImage());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
					}
				}

				for (int i = 0; i < fotos.size(); i++) {

					Bitmap bm = BitmapFactory
							.decodeFile(mLoginActivity.getFileStreamPath(("pic" + i + ".jpg")).getAbsolutePath());
					int width = bm.getWidth();

					int height = bm.getHeight();

					DisplayMetrics metrics = new DisplayMetrics();
					mLoginActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

					float scaleWidth = ((float) (int) (metrics.widthPixels * 0.45f)) / width;

					// float scaleHeight = ((float) newHeight) / height;

					// CREATE A MATRIX FOR THE MANIPULATION

					Matrix matrix = new Matrix();

					// RESIZE THE BIT MAP

					matrix.postScale(scaleWidth, scaleWidth);

					// RECREATE THE NEW BITMAP

					Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

					// Save
					FileOutputStream out;
					try {
						out = new FileOutputStream(mLoginActivity.getFileStreamPath("pic" + i + "resized.jpg"));
						resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
						// Log.d("bert", "creating compresSED NEW FUCK of " +
						// resizedBitmap.getWidth());
						out.close();

						File sdCard = Environment.getExternalStorageDirectory();
						File file = new File(sdCard.getAbsolutePath() + "pic" + i + "resized.jpg");
						out = new FileOutputStream(file);
						resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
						out.close();

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// store the new version number locally in shared storage
				// TODO
				saveImagesVersionToSharedPrefs(versionNumber);

			} catch (Exception e) {
				return Boolean.valueOf(false);
			}
		}
		return Boolean.valueOf(true);
	}

	private void eraseImages() {

		// TODO Auto-generated method stub
		int currentImage = 0;

		while (true) {

			File file = mLoginActivity.getFileStreamPath("pic" + currentImage + "resized.jpg");
			File fileNormal = mLoginActivity.getFileStreamPath("pic" + currentImage + ".jpg");
			if (file.exists()) {
				file.delete();
				fileNormal.delete();
			} else
				break;
			currentImage++;
		}

	}

}
