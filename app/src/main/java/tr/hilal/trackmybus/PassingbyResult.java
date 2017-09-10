package tr.hilal.trackmybus;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import tr.hilal.trackmybus.adapter.PassingbyListAdapter;
import tr.hilal.trackmybus.db.BusDataSource;
import tr.hilal.trackmybus.model.Schedule;

/**
 * Created by TOSHIBA on 6/30/2015.
 */
public class PassingbyResult extends ActionBarActivity {
    String gotStop;
    List<Schedule> schedules;
    ProgressDialog pb;
    BusDataSource busDataSource;
    TextView head,subHead;
    ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_list);
        pb=new ProgressDialog(this);
        pb.setTitle("Searching");
        pb.setMessage("Please Be Patient.. We Are Searching in Our Database");
        pb.setCancelable(false);
        pb.show();
        initialize();
        lv= (ListView) findViewById(R.id.passingList);
        Bundle gotBasket=getIntent().getExtras();
        gotStop=gotBasket.getString("stopName");
        head.setText("Passing Services Near");
        subHead.setText(gotStop);
        busDataSource=new BusDataSource(PassingbyResult.this);
        try {
            busDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MyTask task=new MyTask();
        task.execute(gotStop);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                Schedule schedule=schedules.get(position);
                i = new Intent(PassingbyResult.this,ListDetails.class);
                i.putExtra("busid",schedule.getBus().getId());
                i.putExtra("busName",schedule.getBus().getBusName());
                i.putExtra("busClass",schedule.getBus().getClassName());
                startActivity(i);
            }
        });

    }

    public void refreshDisplay() {

        lv.setAdapter(new PassingbyListAdapter(this, schedules));
    }

    private void initialize() {
        head= (TextView) findViewById(R.id.tvHeading);
        subHead= (TextView) findViewById(R.id.tvSubheading);
    }

    private class MyTask extends AsyncTask<String, String, List<Schedule>>
    {



        @Override
        protected void onPreExecute() {
            Log.d("HILSTAG","in ON PREEXECUE");
        }

        @Override
        protected List<Schedule> doInBackground(String... params) {
            schedules=busDataSource.getPassingList(params[0]);
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
                AlertDialog.Builder noResults=new AlertDialog.Builder(PassingbyResult.this);
                noResults.setTitle("No Results");
                noResults.setMessage("No Results From Our Database");
                noResults.setCancelable(true);
                AlertDialog myAlert=noResults.create();
                myAlert.show();
            }

        }
    }
   }