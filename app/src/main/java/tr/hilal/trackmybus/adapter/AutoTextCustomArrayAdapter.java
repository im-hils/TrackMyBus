package tr.hilal.trackmybus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import tr.hilal.trackmybus.R;
import tr.hilal.trackmybus.model.Stop;

/**
 * Created by TOSHIBA on 7/3/2015.
 */
public class AutoTextCustomArrayAdapter extends ArrayAdapter<Stop> {
    Context mContext;
    int layoutResourceId;
    Stop data[] = null;

    public AutoTextCustomArrayAdapter(Context mContext, int layoutResourceId, Stop[] data) {
        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try{   
            if(convertView==null){
                // inflate the tv_list_passing
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }

            // object item based on the position
            Stop objectItem = data[position];

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem = (TextView) convertView.findViewById(R.id.textViewItem);
            textViewItem.setText(objectItem.getStopName());

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;

    }
}
