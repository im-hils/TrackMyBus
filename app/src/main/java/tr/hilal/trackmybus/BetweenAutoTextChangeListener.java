package tr.hilal.trackmybus;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import tr.hilal.trackmybus.adapter.AutoTextCustomArrayAdapter;
import tr.hilal.trackmybus.model.Stop;

/**
 * Created by TOSHIBA on 7/2/2015.
 */
public class BetweenAutoTextChangeListener implements TextWatcher {
    Context context;

    public BetweenAutoTextChangeListener(Context context){
        this.context = context;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(Between.textViewSource.getText().toString().trim().equalsIgnoreCase(Between.textViewDestination.getText().toString().trim()))
        {
            Between.searchButton.setEnabled(false);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        if(Between.textViewSource.getText().toString().trim().equalsIgnoreCase(Between.textViewDestination.getText().toString().trim()))
        {
            Between.searchButton.setEnabled(false);
        }

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        if (Between.textViewSource.getText().hashCode() == userInput.hashCode())
        {
            if(userInput.length()>0) {
                Between.clearSource.setVisibility(View.VISIBLE);
                if (userInput.toString().trim().length() > 0) {
                    Between.clearSource.setVisibility(View.VISIBLE);
                    int countDest = Between.textViewDestination.getText().length();
                    if (countDest > 0) {
                        Between.interchange.setVisibility(View.VISIBLE);
                        Between.searchButton.setEnabled(true);
                        Between.centerDotted.setVisibility(View.INVISIBLE);
                    }
                }
                } else {
                    Between.clearSource.setVisibility(View.INVISIBLE);
                    Between.interchange.setVisibility(View.INVISIBLE);
                    Between.centerDotted.setVisibility(View.VISIBLE);
                    Between.searchButton.setEnabled(false);

            }
        }
        else if (Between.textViewDestination.getText().hashCode() == userInput.hashCode())
        {
            if(userInput.length()>0) {
                Between.clearDestination.setVisibility(View.VISIBLE);
                if (userInput.toString().trim().length() > 0) {
                    Between.clearDestination.setVisibility(View.VISIBLE);
                    int countSource = Between.textViewSource.getText().length();
                    if (countSource > 0) {
                        Between.interchange.setVisibility(View.VISIBLE);
                        Between.centerDotted.setVisibility(View.INVISIBLE);
                        Between.searchButton.setEnabled(true);
                    }
                }
                } else {
                    Between.clearDestination.setVisibility(View.INVISIBLE);
                    Between.interchange.setVisibility(View.INVISIBLE);
                    Between.centerDotted.setVisibility(View.VISIBLE);
                    Between.searchButton.setEnabled(false);

            }
        }

        try {

            //see in the logcat what the user types
            Log.d("HILSTAG", "User input: " + userInput);
            Between mainActivity = ((Between) context);

            // update the adapater
            mainActivity.myAdapter.notifyDataSetChanged();

            // get suggestions from the database
            mainActivity.busDataSource.open();
            Stop[] myObjs = mainActivity.busDataSource.getAllStops(userInput.toString());
            mainActivity.myAdapter = new AutoTextCustomArrayAdapter(mainActivity, R.layout.tv_list_item, myObjs);
            mainActivity.textViewSource.setAdapter(mainActivity.myAdapter);
            mainActivity.textViewDestination.setAdapter(mainActivity.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
