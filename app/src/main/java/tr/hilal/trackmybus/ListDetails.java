package tr.hilal.trackmybus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import tr.hilal.trackmybus.adapter.ListDetailsAdapter;
import tr.hilal.trackmybus.adapter.PassingbyListAdapter;
import tr.hilal.trackmybus.db.BusDataSource;
import tr.hilal.trackmybus.model.Schedule;

/**
 * Created by TOSHIBA on 7/17/2015.
 */
public class ListDetails extends ActionBarActivity {
    private List<Schedule> scheduleList;
    BusDataSource busDataSource;
    ListView lv;
    TextView head,subHead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_details);
        initialize();
        Bundle b=getIntent().getExtras();
        int busId=b.getInt("busid");
        String busClass=b.getString("busClass");
        String busName=b.getString("busName");
        Log.d("TEST",busClass);
//        head.setText(busName);
        subHead.setText("");
        StringBuilder sb=new StringBuilder();
        sb.append(busName);
        sb.append("\n");
        sb.append(busClass);
        head.setText(sb);

        Log.d("HLSTAG", String.valueOf(busId));
        busDataSource=new BusDataSource(this);
        try {
            busDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        scheduleList=busDataSource.getScheduleDetails(busId);
        lv= (ListView) findViewById(R.id.List);
        refreshDisplay();


    }

    private void initialize() {
        head = (TextView) findViewById(R.id.tvHeading);
        subHead = (TextView) findViewById(R.id.tvSubheading);
    }


    private void refreshDisplay() {
        lv.setAdapter(new ListDetailsAdapter(this, scheduleList));
    }
}
