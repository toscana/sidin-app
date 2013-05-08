package be.ehb.iwt.sidinapp;

import java.util.ArrayList;
import java.util.List;

import be.ehb.iwt.sidin.core.Subscription;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StudentSearchAdapter extends ArrayAdapter<String> {

	private List<Subscription> lijst;

	public StudentSearchAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		lijst = new ArrayList<Subscription>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lijst.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return lijst.get(position).getEmail();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		SubscriptionViewHolder holder;

		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.subscription_search_layout,
					parent, false);

			holder = new SubscriptionViewHolder();
			holder.emailField = (TextView) convertView
					.findViewById(R.id.textViewSearchFieldEmail);
			holder.nameField = (TextView) convertView
					.findViewById(R.id.textViewSearchFieldName);
			convertView.setTag(holder);

		} else {
			holder = (SubscriptionViewHolder) convertView.getTag();
		}

		holder.emailField.setText(lijst.get(position).getEmail());
		holder.nameField.setText(lijst.get(position).getFirstName() + " " + lijst.get(position).getLastName());

		return convertView;

	}

	static class SubscriptionViewHolder {
		TextView emailField;
		TextView nameField;
	}

	public Subscription getSubscriptionAt(int selectedItemPosition) {
		// TODO Auto-generated method stub
		return lijst.get(selectedItemPosition);
	}

	public void setList(List<Subscription> subList) {
		this.lijst = subList;
		this.notifyDataSetChanged();
		
	}

}
