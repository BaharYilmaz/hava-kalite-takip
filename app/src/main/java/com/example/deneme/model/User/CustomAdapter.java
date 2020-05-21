package com.example.deneme.model.User;

import android.app.DownloadManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.deneme.JSONParser;
import com.example.deneme.R;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CustomAdapter extends ArrayAdapter<Family> {
    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final ArrayList<Family> families;
    ArrayList<String> myArrray =new ArrayList<>();
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
            holder.airIndex = (TextView) convertView.findViewById(R.id.record_air_quality);



            convertView.setTag(holder);

        }
        else{
            //Get viewholder we already created
            holder = (ViewHolder)convertView.getTag();
        }

        Family person = families.get(position);
        if(person != null) {

            holder.name.setText(person.getName());
            holder.city.setText(person.getCity());
            holder.airIndex.setText(person.getIndex());

           // gd = new GetData();
            //gd.execute(person.getCity());



            //holder.personAddressLabel.setText(person.getCity());

        }
        return convertView;
    }





    //View Holder Pattern for better performance
    private static class ViewHolder {
        TextView name;
        TextView city;
        TextView airIndex;


    }

}
