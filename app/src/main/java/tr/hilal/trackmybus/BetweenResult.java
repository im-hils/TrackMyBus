package tr.hilal.trackmybus;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import tr.hilal.trackmybus.adapter.BetweenListAdapter;
import tr.hilal.trackmybus.adapter.PassingbyListAdapter;
import tr.hilal.trackmybus.db.BusDataSource;
import tr.hilal.trackmybus.model.Schedule;

/**
 * Created by TOSHIBA on 6/21/2015.
 */
public class BetweenResult extends ActionBarActivity {
    private List<Schedule> schedules;
    String gotSource,gotDestination,gotSpinnerValue;
    BusDataSource busDataSource;
    TextView head,subHead;
    ProgressDialog pb;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between_list);
        initialize();
        pb=new ProgressDialog(this);
        pb.setTitle("Searching");
        pb.setMessage("This May take Some Time...Be patient");
        pb.setCancelable(false);
        pb.show();
        Bundle gotBasket=getIntent().getExtras();

        gotSource=gotBasket.getString("sourceName");
        gotDestination=gotBasket.getString("destinationName");
        gotSpinnerValue=gotBasket.getString("category");

        StringBuilder sb=new StringBuilder("Bus Between ");
        sb.append(gotSource);
        sb.append(" and ");
        sb.append(gotDestination);
        head.setText(sb);
        subHead.setText("");

        busDataSource=new BusDataSource(this);
        try {
            busDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MyTask task=new MyTask();
        task.execute(gotSource,gotDestination,gotSpinnerValue);
        lv= (ListView) findViewById(R.id.betweenList);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                Schedule schedule = schedules.get(position);
                i = new Intent(BetweenResult.this, ListDetails.class);
                i.putExtra("busid", schedule.getBus().getId());
                i.putExtra("busName",schedule.getBus().getBusName());
                i.putExtra("busClass",schedule.getBus().getClassName());
                startActivity(i);
            }
        });
    }

    private void initialize() {
        head= (TextView) findViewById(R.id.tvHeading);
        subHead= (TextView) findViewById(R.id.tvSubheading);
    }

    public void refreshDisplay() {
        lv.setAdapter(new BetweenListAdapter(this, schedules));
    }
    private class MyTask extends AsyncTask<String, String, List<Schedule>>
    {



        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<Schedule> doInBackground(String... params) {
//            switch (gotSpinnerValue){
//                case "All":
                    schedules=busDataSource.getBetweenList(params[0], params[1],params[2]);
//                    break;
//            }
            Log.d("HILSTAG", "in Do background");
            return schedules;
        }

        @Override
        protected void onPostExecute(List<Schedule> result) {
            Log.d("HILSTAG", "TASK REMOVED");
            pb.dismiss();
            schedules=result;
            if(schedules.size()>0) {
                refreshDisplay();
                Log.d("HILSTAG", "REFRESH DISPLAY FROM OPOST EXECUTE");
            }
            else{
                AlertDialog.Builder noResults=new AlertDialog.Builder(BetweenResult.this);
                noResults.setTitle("No Results");
                noResults.setMessage("No Results From Our Database");
                noResults.setCancelable(true);
                AlertDialog myAlert=noResults.create();
                myAlert.show();
            }

        }
    }
}
