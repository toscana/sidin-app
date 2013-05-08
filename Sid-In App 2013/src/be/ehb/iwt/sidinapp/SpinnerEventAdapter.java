package be.ehb.iwt.sidinapp;

import java.util.List;

import be.ehb.iwt.sidin.core.Event;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerEventAdapter extends ArrayAdapter<String> {
	
	private List<Event> events;

	public SpinnerEventAdapter(Context context, int textViewResourceId,List<Event> names) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		this.events = names;
	}
	


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return events.size();
	}
	
	
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return events.get(position).getName();
	}

	
	public Event getEventAt(int position) {
		// TODO Auto-generated method stub
		return events.get(position);
	}


	public View getView(int position,View convertView,ViewGroup parent){
		SpinnerViewHolder holder;
		
		if(convertView == null){
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.spinnerlayout,parent,false);
			
			 holder = new SpinnerViewHolder();
             holder.text = (TextView) convertView.findViewById(R.id.textViewSpinner);
			convertView.setTag(holder);

		}
		else
		{
			holder = (SpinnerViewHolder) convertView.getTag();
		}
		
        holder.text.setText(events.get(position).getName());
        
          
        return convertView;
        
		
	}
	
	static class SpinnerViewHolder {
		TextView text;
	}

}
