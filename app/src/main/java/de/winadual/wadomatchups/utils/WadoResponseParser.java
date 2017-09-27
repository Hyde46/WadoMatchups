package de.winadual.wadomatchups.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by monarch on 27.09.17.
 */

public class WadoResponseParser {


    public final static int MATCHUP_AVAILABLE = 0;
    public final static int MATCHUP_NOT_READY = 1;
    public final static int MATCHUP_SERVER_ERROR = 2;

    private final String testJSON = "{ \"opFirstName\":\"Dude\",\"opLastName\":\"Dudeson\" ,\"roundNumber\":\"2\",\"tableNumber\":\"1\"}";

    public ResponseContainer parseResponse(String serverResponse){
        ResponseContainer rC = new ResponseContainer();
        String parsedName ="";
        String parsedFirstName="";
        String parsedTableNumber="";
        String parsedRoundNumber="";

        //parse serverResponse as JSON string
        try {
            //JSONObject jsonObj = new JSONObject(serverResponse);
            JSONObject jsonObj = new JSONObject(testJSON);

            parsedFirstName = jsonObj.getString("opFirstName");
            parsedName = jsonObj.getString("opLastName");
            parsedRoundNumber = jsonObj.getString("roundNumber");
            parsedTableNumber = jsonObj.getString("tableNumber");

        } catch (final JSONException e) {
            Log.e("", "Json parsing error: " + e.getMessage());
        }

        if(!parsedName.equals("") &&
                !parsedFirstName.equals("") &&
                !parsedTableNumber.equals("") &&
                !parsedRoundNumber.equals("")) {
            rC.setContainerValues(parsedFirstName, parsedName, parsedTableNumber, parsedRoundNumber);
        }else {
            rC.setFailedParseState();
        }
        return rC;
    }

    public class ResponseContainer {
        private String firstName;
        private String lastName;
        private String tableNumber;
        private String roundNumber;

        private int responseState;

        public ResponseContainer(){
            responseState = MATCHUP_SERVER_ERROR;
        }

        public void setContainerValues(String fN, String lN, String tN, String rN){
            this.firstName = fN;
            this.lastName = lN;
            this.tableNumber = tN;
            this.roundNumber = rN;
            responseState = MATCHUP_AVAILABLE;
        }
        public void setFailedParseState(){responseState = MATCHUP_SERVER_ERROR;}

        public String getFirstName(){
            return this.firstName;
        }
        public String getLastName(){
            return this.lastName;
        }
        public String getTableNumber(){
            return this.tableNumber;
        }
        public String getRoundNumber(){
            return this.roundNumber;
        }
        public int getResponseState(){
            return this.responseState;
        }
    }
}
