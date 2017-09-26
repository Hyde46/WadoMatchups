package de.winadual.wadomatchups;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SetNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_name);

        loadPreferences();
    }

    public void viewMatchup(View view){
        safeCredentials();
        Intent intent = new Intent(this, ViewMatchupActivity.class);
        startActivity(intent);
    }

    private void safeCredentials(){

        EditText firstNameEdit = (EditText) findViewById(R.id.firstNameText);
        EditText lastNameEdit = (EditText) findViewById(R.id.lastNameText);

        String firstName =  firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();

        //safe names as key value pairs to shared preferences
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getString(R.string.saved_first_name),firstName);
        editor.putString(getString(R.string.saved_last_name),lastName);
        editor.apply();
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

        if(!loadedFirstName.equals(defaultFirstName))
            ((TextView)findViewById(R.id.firstNameText)).setText(loadedFirstName);

        if(!loadedLastName.equals(defaultLastName))
            ((TextView)findViewById(R.id.lastNameText)).setText(loadedLastName);
    }
}
