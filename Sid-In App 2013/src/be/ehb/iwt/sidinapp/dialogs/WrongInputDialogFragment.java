package be.ehb.iwt.sidinapp.dialogs;

import be.ehb.iwt.sidinapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class WrongInputDialogFragment extends DialogFragment {
	
	private String mMessage;
	
	public WrongInputDialogFragment(){
		super();
	}
	
    public WrongInputDialogFragment(String mMessage) {
		super();
		this.mMessage = mMessage;
	}



	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
		Resources res = getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(res.getString(R.string.messageWrongInput) + mMessage)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}