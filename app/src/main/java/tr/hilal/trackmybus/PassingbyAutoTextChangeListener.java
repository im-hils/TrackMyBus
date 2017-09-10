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
public class PassingbyAutoTextChangeListener implements TextWatcher {
    Context context;
    public PassingbyAutoTextChangeListener(Context context) {
        this.context = context;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Passingby.search.setEnabled(false);

    }

    @Override
    public void onTextChanged(CharSequence userInput, int start, int before, int count) {
        try {

            // if you want to see in the logcat what the user types
//            Log.d("HILSTAG", "User input: " + userInput);

            Passingby mainActivity = ((Passingby) context);

            // update the adapater
            mainActivity.myAdapter.notifyDataSetChanged();
//            Log.d("HILSTAG", "DAtaset Changed in TectChangedListener");

            // get suggestions from the database
            mainActivity.busDataSource.open();
            Stop[] myObjs = mainActivity.busDataSource.getAllStops(userInput.toString());
            Log.d("HILSTAG", userInput.toString());

            mainActivity.myAdapter = new AutoTextCustomArrayAdapter(mainActivity, R.layout.tv_list_item, myObjs);
            mainActivity.stopName.setAdapter(mainActivity.myAdapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userInput.length() > 0) {
            Passingby.clear.setVisibility(View.VISIBLE);
            Passingby.search.setEnabled(true);
        } else {
            Passingby.clear.setVisibility(View.INVISIBLE);
            Passingby.search.setEnabled(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            Passingby.search.setEnabled(true);
        } else {
            Passingby.search.setEnabled(false);
        }

    }
}
