package tr.hilal.trackmybus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import tr.hilal.trackmybus.adapter.AutoTextCustomArrayAdapter;
import tr.hilal.trackmybus.db.BusDBopenHelper;
import tr.hilal.trackmybus.db.BusDataSource;
import tr.hilal.trackmybus.model.Schedule;
import tr.hilal.trackmybus.model.Stop;

/**
 * Created by TOSHIBA on 6/21/2015.
 */
public class Passingby extends ActionBarActivity implements View.OnClickListener {
    ArrayAdapter<Stop> myAdapter;
    private List<Schedule> schedules;
    Intent i;
    TextView testSearch;
    static ImageButton clear;
    BusDataSource busDataSource;
    private static final String LOGTAG = "HILSTAG";
    private BusDBopenHelper stopAssistant=new BusDBopenHelper(this);
    SQLiteDatabase db;
    CustomAutoCompleteTextView stopName;
    static Button search;
    LinearLayout lytBetween;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing);
        db=stopAssistant.getReadableDatabase();
        initialize();
        stopName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                RelativeLayout rl = (RelativeLayout) arg1;
                TextView tv = (TextView) rl.getChildAt(0);
                stopName.setText(tv.getText().toString());

            }

        });
        busDataSource = new BusDataSource(Passingby.this);
        try {
            busDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stop myObject = null;
        Stop[] ObjectItemData = new Stop[1];

        myObject = new Stop("None");
        ObjectItemData[0] = myObject;
        myAdapter = new AutoTextCustomArrayAdapter(this, R.layout.tv_list_item, ObjectItemData);
        stopName.setAdapter(myAdapter);
        stopName.addTextChangedListener(new PassingbyAutoTextChangeListener(this));
        clear.setOnClickListener(this);
        search.setOnClickListener(this);
        lytBetween.setOnClickListener(this);

    }

    private void initialize() {
        search= (Button) findViewById(R.id.btSearchPassingby);
        clear= (ImageButton) findViewById(R.id.btClear);
        stopName= (CustomAutoCompleteTextView) findViewById(R.id.etStopName);
        testSearch=(TextView) findViewById(R.id.tvSearch);
        lytBetween= (LinearLayout) findViewById(R.id.layoutSwitchBetween);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btClear:
                stopName.getText().clear();
                break;
            case R.id.layoutSwitchBetween:
                i=new Intent(Passingby.this,Between.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.btSearchPassingby:
                String stop = stopName.getText().toString().trim();
                Bundle basket = new Bundle();
                basket.putString("stopName", stop);
                i = new Intent(Passingby.this, PassingbyResult.class);
                i.putExtras(basket);
                InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                startActivity(i);
                break;
        }
    }

}
