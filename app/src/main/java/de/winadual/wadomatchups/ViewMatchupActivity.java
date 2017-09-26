package de.winadual.wadomatchups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewMatchupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matchup);

        loadPreferences();
    }


    private void loadPreferences(){

        //check if credentials exist in shared preferences
        //if they do, put them into firstName / lastName Edit Texts
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);

        String defaultFirstName = getResources().getString(R.string.saved_first_name_default);
        String loadedFirstName = sharedPref.getString(getString(R.string.saved_first_name), defaultFirstName);

        String defaultLastName = getResources().getString(R.string.saved_last_name_default);
        String loadedLastName = sharedPref.getString(getString(R.string.saved_last_name), defaultLastName);

        if(!loadedFirstName.equals(defaultFirstName) && !loadedLastName.equals(defaultLastName) )
            ((TextView)findViewById(R.id.leftOpponentText)).setText(loadedFirstName+" "+loadedLastName);
    }

}
