package de.winadual.wadomatchups.utils;

/**
 * Created by monarch on 27.09.17.
 */

public class WadoResponseParser {


    public final static int MATCHUP_AVAILABLE = 0;
    public final static int MATCHUP_NOT_READY = 1;
    public final static int MATCHUP_SERVER_ERROR = 2;

    public ResponseContainer parseResponse(String serverResponse){
        ResponseContainer rC = new ResponseContainer();
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
