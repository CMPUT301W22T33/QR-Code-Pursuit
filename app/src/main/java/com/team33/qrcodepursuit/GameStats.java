package com.team33.qrcodepursuit;


// User issues
// View total number of QR codes scanned
// View sum of scores scanned
// View highest and lowest scoring QR codes
//
// Classes
// Scoreboard
// GameStats
//

// Game Stats responsibilities
// Summarize stats of a Game History
// Can be viewed by other players
// Stores ranking for account


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GameStats extends GameHistory {
    private static final String TAG = "Test";
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    int totalScore = 0;
    int totalQRCodes = 0;
    String associatedUsername;
    int highestQRAttained;

    GameQRCode currentLowest;
    GameQRCode currentHighest;

    public void setInitialPointsFromData(){
        if (totalPoints != 0)
            GameQRProcessor processor = new GameQRProcessor();
        ArrayList<GameQRCode> codes = super.codeHistory;
        int points = 0;
        for(int i = 0; i < codes.size(); i++){
            int value = processor.calculateScore(codes[i]);
            points += value;
        }
        setTotalPoints(points);
    }
    public void addPoints(int points){
        this.totalPoints += points;
    }
    public void addPoints(GameQRCode code){
        GameQRProcessor processor = new GameQRProcessor();
        this.totalPoints += processor.getScore(code);
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getTotalQRCodes() {
        return super.getCodeHistory().size();
    }

    public GameQRCode getLowestQRCode(){
        return this.currentLowest;
    }

    public GameQRCode getHighestQRCode(){
        return this.currentHighest;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
    public void setTotalQRCodes(int totalQRCodes){
        this.totalQRCodes = totalQRCodes;
    }
}
