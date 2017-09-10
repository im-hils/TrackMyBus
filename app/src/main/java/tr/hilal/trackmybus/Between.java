package tr.hilal.trackmybus;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import tr.hilal.trackmybus.adapter.AutoTextCustomArrayAdapter;
import tr.hilal.trackmybus.db.BusDataSource;
import tr.hilal.trackmybus.model.Schedule;
import tr.hilal.trackmybus.model.Stop;

/**
 * Created by TOSHIBA on 6/21/2015.
 */
public class Between extends ActionBarActivity implements View.OnClickListener{

    //Initialization
    ArrayAdapter<Stop> myAdapter;
    private List<Schedule> schedules;
    BusDataSource busDataSource;
    Intent i;
    InputMethodManager imm;

    static View centerDotted;
    String spinnerValue;
    static Button searchButton;
    static ImageButton clearSource;
    static ImageButton clearDestination;
    static ImageButton interchange;
    LinearLayout lytPassing;
    Spinner classDropDown;
    static CustomAutoCompleteTextView textViewSource;
    static CustomAutoCompleteTextView textViewDestination;
    private static final String LOGTAG = "HILSTAG";

    //Oncreate Method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_between);
        initialize();
        try {
            busDataSource = new BusDataSource(Between.this);
            try {
                busDataSource.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            textViewSource.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                    RelativeLayout rl = (RelativeLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(0);
                    textViewSource.setText(tv.getText().toString());

                }

            });
            textViewDestination.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                    RelativeLayout rl = (RelativeLayout) arg1;
                    TextView tv = (TextView) rl.getChildAt(0);
                    textViewDestination.setText(tv.getText().toString());

                }

            });
            textViewSource.addTextChangedListener(new BetweenAutoTextChangeListener(this));
            textViewDestination.addTextChangedListener(new BetweenAutoTextChangeListener(this));


            Stop myObject = null;
            Stop[] ObjectItemData = new Stop[1];

            myObject = new Stop("None");
            ObjectItemData[0] = myObject;

            myAdapter = new AutoTextCustomArrayAdapter(this, R.layout.tv_list_item, ObjectItemData);
            textViewSource.setAdapter(myAdapter);
            textViewDestination.setAdapter(myAdapter);

            classDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    spinnerValue = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), "Selected: " + spinnerValue, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            searchButton.setOnClickListener(this);
            clearSource.setOnClickListener(this);
            lytPassing.setOnClickListener(this);
            clearDestination.setOnClickListener(this);
            interchange.setOnClickListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Code For Initialization
    private void initialize() {
        textViewSource = (CustomAutoCompleteTextView) findViewById(R.id.etSource);
        textViewDestination = (CustomAutoCompleteTextView) findViewById(R.id.etDestination);
        searchButton = (Button) findViewById(R.id.btSearch);
        clearSource = (ImageButton) findViewById(R.id.btClearSource);
        clearDestination = (ImageButton) findViewById(R.id.btClearDestination);
        centerDotted=findViewById(R.id.vDottedLineCenter);
        interchange= (ImageButton) findViewById(R.id.btInterchange);
        lytPassing= (LinearLayout) findViewById(R.id.layoutSwitchPassing);
        classDropDown= (Spinner) findViewById(R.id.spinnerClass);

    }

    //Onclick Listener for Which Button is Pressed
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btSearch:
                String source = textViewSource.getText().toString().trim();
                String destination=textViewDestination.getText().toString().trim();
                Bundle basket = new Bundle();
                basket.putString("sourceName", source);
                basket.putString("destinationName", destination);
                basket.putString("category",spinnerValue);
                i = new Intent(Between.this, BetweenResult.class);
                i.putExtras(basket);
                imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                startActivity(i);
                break;
            case R.id.btClearSource:
                textViewSource.getText().clear();
                break;
            case R.id.layoutSwitchPassing:
                i=new Intent(Between.this,Passingby.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.btClearDestination:
                textViewDestination.getText().clear();
                break;
            case R.id.btInterchange:
                RotateAnimation ra=new RotateAnimation(0f,180f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                ra.setFillAfter(false);
                ra.setDuration(1000);
                interchange.startAnimation(ra);
                String temp1=textViewSource.getText().toString();
                textViewSource.setText(textViewDestination.getText().toString());
                textViewDestination.setText(temp1);
                break;
                 }
    }
    private void doExit(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Between.this);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setNegativeButton("No", null);
        alertDialog.setMessage("Are You Sure Want To Exit?");
        alertDialog.setTitle("Travel Hub");
        alertDialog.show();
        alertDialog.setCancelable(true);
    }

    @Override
    public void onBackPressed() {
        doExit();
    }
}