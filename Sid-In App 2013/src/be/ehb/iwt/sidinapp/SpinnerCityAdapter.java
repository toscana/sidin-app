
package be.ehb.iwt.sidinapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerCityAdapter extends ArrayAdapter<String> {
	
	private List<String> zips;

	public List<String> getZips() {
		return zips;
	}

	public SpinnerCityAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		zips = new ArrayList<String>();
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zips.size();
	}
	
	
	public void setZips(List<String> zips) {
		this.zips = zips;
		notifyDataSetChanged();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return zips.get(position);
	}



	public View getView(int position,View convertView,ViewGroup parent){
		SpinnerViewHolder holder;
		
		if(convertView == null){
			LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.spinnerlayoutzips,parent,false);
			
			 holder = new SpinnerViewHolder();
             holder.text = (TextView) convertView.findViewById(R.id.textViewSpinner);
			convertView.setTag(holder);

		}
		else
		{
			holder = (SpinnerViewHolder) convertView.getTag();
		}
		
        holder.text.setText(zips.get(position));
        
          
        return convertView;
        
		
	}
	
	static class SpinnerViewHolder {
		TextView text;
	}


}
