package tr.hilal.trackmybus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tr.hilal.trackmybus.R;
import tr.hilal.trackmybus.model.Schedule;

/**
 * Created by TOSHIBA on 7/12/2015.
 */
public class PassingbyListAdapter extends ArrayAdapter<Schedule> {

    private Context context;
    List<Schedule> schedules;


    public PassingbyListAdapter(Context context, List<Schedule> schedules) {
        super(context, R.layout.tv_list_passing);
        this.context = context;
        this.schedules = schedules;
    }

    private class ViewHolder {
        TextView schTime;
        TextView schBusName;
        TextView schClass;
        TextView schTowards;

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
            convertView = inflater.inflate(R.layout.tv_list_passing, null);
            holder = new ViewHolder();

            holder.schTime = (TextView) convertView
                    .findViewById(R.id.tvTimeRow);
            holder.schBusName = (TextView) convertView
                    .findViewById(R.id.tvNameRow);

            holder.schClass = (TextView) convertView
                    .findViewById(R.id.tvClassRow);

            holder.schTowards= (TextView) convertView.
                    findViewById(R.id.tvTowardsRow);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Schedule schedule = getItem(position);
        holder.schTime.setText(schedule.getTime());
        holder.schBusName.setText(schedule.getBus().getBusName());
        holder.schClass.setText(schedule.getBus().getClassName());
        holder.schTowards.setText(schedule.getStop().getTowards());



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
