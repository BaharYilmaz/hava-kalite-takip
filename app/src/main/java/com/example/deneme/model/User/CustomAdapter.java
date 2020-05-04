package com.example.deneme.model.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deneme.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Family> {
    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Family> families;

    public CustomAdapter(Context context, ArrayList<Family> families) {
        super(context,0, families);
        this.context = context;
        this.families = families;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return families.size();
    }

    @Override
    public Family getItem(int position) {
        return families.get(position);
    }

    @Override
    public long getItemId(int position) {
        return families.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.family_record, null);

            holder = new ViewHolder();
           // holder.personImage = (ImageView) convertView.findViewById(R.id.person_image);
            holder.name = (TextView) convertView.findViewById(R.id.record_name);
            holder.city = (TextView) convertView.findViewById(R.id.record_city);
            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        Family person = families.get(position);
        if(person != null){
            holder.name.setText(person.getName());
            holder.city.setText(person.getCity());
            //holder.personAddressLabel.setText(person.getAddress());

        }
        return convertView;
    }

    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView name;
        TextView city;

    }

}
