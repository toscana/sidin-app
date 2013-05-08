package be.ehb.iwt.sidinapp.dialogs;

import be.ehb.iwt.sidinapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class NoInternetDialogFragment extends DialogFragment {
	
	
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.noInternet)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}