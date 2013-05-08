package be.ehb.iwt.sidinapp;

import java.util.List;

import be.ehb.iwt.sidin.core.Teacher;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerTeacherAdapter extends ArrayAdapter<String> {
	
	private List<Teacher> teachers;

	public SpinnerTeacherAdapter(Context context, int textViewResourceId,List<Teacher> names) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		this.teachers = names;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return teachers.size();
	}
	
	
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return teachers.get(position).getName();
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
		
        holder.text.setText(teachers.get(position).getName());
        
          
        return convertView;
        
		
	}
	
	static class SpinnerViewHolder {
		TextView text;
	}

	public Teacher getTeacherAt(int selectedItemPosition) {
		// TODO Auto-generated method stub
		return teachers.get(selectedItemPosition);
	}

}
