package tr.hilal.trackmybus.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tr.hilal.trackmybus.R;
import tr.hilal.trackmybus.model.Schedule;

/**
 * Created by TOSHIBA on 7/17/2015.
 */
public class ListDetailsAdapter extends ArrayAdapter<Schedule> {
    private Context context;
    List<Schedule> schedules;
    public ListDetailsAdapter(Context context, List<Schedule> schedules) {
        super(context, R.layout.tv_onclick_details);
        this.context = context;
        this.schedules = schedules;
    }
    private class ViewHolder {
        TextView schStop;
        TextView schTime;
    }
    @Override
    public int getCount() {
        return schedules.size();
    }
    @Override
    public Schedule getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tv_onclick_details, null);
            holder = new ViewHolder();

            holder.schTime = (TextView) convertView
                    .findViewById(R.id.tvTimeRow);
            holder.schStop = (TextView) convertView
                    .findViewById(R.id.tvStopRow);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Schedule schedule = getItem(position);
        holder.schTime.setText(schedule.getTime());
        holder.schStop.setText(schedule.getStop().getStopName());
        return convertView;

    }
    @Override
    public void add(Schedule schedule) {
        schedules.add(schedule);
        notifyDataSetChanged();
        super.add(schedule);
    }

    @Override
    public void remove(Schedule schedule) {
        schedules.remove(schedule);
        notifyDataSetChanged();
        super.remove(schedule);
    }
}
