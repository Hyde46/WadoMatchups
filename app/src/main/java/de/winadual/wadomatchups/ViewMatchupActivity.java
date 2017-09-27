package de.winadual.wadomatchups;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.winadual.wadomatchups.utils.WadoResponseParser;

public class ViewMatchupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_matchup);
        toggleMatchupVisibility(View.VISIBLE, View.INVISIBLE);
        updateMatchup(null);
    }

    public void updateMatchup(View view){
        String[] playerName = loadPreferences();
        requestMatchup(playerName);
    }


    private String[] loadPreferences(){

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
        return new String[]{loadedFirstName,loadedLastName};
    }



    /*
    Requests the matchup for given playerName from WADO - Matchup Server
    @ [...] adress
     */
    private void requestMatchup(String[] playerName){

        RequestQueue queue = Volley.newRequestQueue(this);

        //Incoporate playerName into request
        // as GET Request for now

        String url = "http://www.google.com";
        String reqUrl = url+"?firstName="+playerName[0]+"&lastName="+playerName[1];

        StringRequest stringRequest = new StringRequest(Request.Method.GET, reqUrl,
                new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            setMatchup(response);
                        }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            toggleMatchupVisibility(View.INVISIBLE, View.VISIBLE);
                    }

                });
        queue.add(stringRequest);

    }


    private void toggleMatchupVisibility(int viewVisibility, int errVisibility){
        //There has to be a better way to bundle several Textviews...
        // Maybe fragments?
        TextView leftOpponentText = (TextView) findViewById(R.id.leftOpponentText);
        TextView rightOpponentText = (TextView) findViewById(R.id.rightOpponentText);

        TextView vsText = (TextView) findViewById(R.id.vsText);

        TextView tableText = (TextView) findViewById(R.id.tableText);
        TextView tableNumberText = (TextView) findViewById(R.id.tableNumber);

        TextView roundText = (TextView) findViewById(R.id.roundText);
        TextView roundNumberText = (TextView) findViewById(R.id.roundNumber);

        TextView errMatchupText = (TextView) findViewById(R.id.noConnectionView);
        TextView errNoMatchupText = (TextView) findViewById(R.id.noMatchupView);


        //Toggle visibility on all the Textviews we fetched.
        leftOpponentText.setVisibility(viewVisibility);
        rightOpponentText.setVisibility(viewVisibility);

        vsText.setVisibility(viewVisibility);

        tableText.setVisibility(viewVisibility);
        tableNumberText.setVisibility(viewVisibility);

        roundText.setVisibility(viewVisibility);
        roundNumberText.setVisibility(viewVisibility);

        errMatchupText.setVisibility(errVisibility);

        //reset "no machup found" message in case we displayed it during the last update
        errNoMatchupText.setVisibility(View.INVISIBLE);
    }

    private void setMatchup(String response){
        toggleMatchupVisibility(View.VISIBLE, View.INVISIBLE);

        WadoResponseParser.ResponseContainer rC = new WadoResponseParser().parseResponse(response);
        int responseCode = rC.getResponseState();

        switch(responseCode){
            case WadoResponseParser.MATCHUP_AVAILABLE:
                fillUiMatchup(rC.getFirstName(),rC.getLastName(),rC.getTableNumber(),rC.getRoundNumber());
                break;
            case WadoResponseParser.MATCHUP_NOT_READY:
                setMatchupError();
                break;
            case WadoResponseParser.MATCHUP_SERVER_ERROR:
                setMatchupError();
                break;
            default:

        }
    }

    private void fillUiMatchup(String firstName, String lastName, String tableNumber, String roundNumber){
        TextView rightOpponentText = (TextView) findViewById(R.id.rightOpponentText);
        TextView tableNumberText = (TextView) findViewById(R.id.tableNumber);
        TextView roundNumberText = (TextView) findViewById(R.id.roundNumber);

        rightOpponentText.setText(firstName+" "+lastName);
        tableNumberText.setText(tableNumber);
        roundNumberText.setText(roundNumber);
    }

    private void setMatchupError(){
        toggleMatchupVisibility(View.INVISIBLE, View.INVISIBLE);
        TextView errNoMatchupText = (TextView) findViewById(R.id.noMatchupView);
        errNoMatchupText.setVisibility(View.VISIBLE);
    }

}
