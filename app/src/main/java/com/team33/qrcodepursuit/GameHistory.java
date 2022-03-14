package com.team33.qrcodepursuit;

import java.util.ArrayList;


/**
 * A user's game history.
 * This contains all the QR codes that they have scanned, plus statistics associated with the codes.
 * A local copy is stored in the application.
 * A remote origin is stored on a Firebase database.
 */
public class GameHistory {
    ArrayList<GameQRCode> codeHistory;
    GameStats stats;
    String associatedUsername;

    /**
     * Create a GameHistory with the associated username attached.
     * @param username
     */
    public GameHistory(String username){
        stats = new GameStats(username);
    }

    /**
     * Add a QR code to the QR codes collected, both remotely and locally.
     * @param gameQRCode
     */
    public void addCode(GameQRCode gameQRCode){
        codeHistory.add(gameQRCode);
    }

    /**
     * Get the GameStats class associated with this GameHistory
     * @return GameStats - the class that can allow modification and access of stats related to the codes.
     */
    public GameStats getStats(){
        return this.stats;
    }

    /**
     * Set the username of which this game history is associated to.
     * @param associatedUsername - the username of the user
     */
    public void setAssociatedUser(String associatedUsername){
        this.associatedUsername = associatedUsername;
    }

    /**
     * Get the associated username of this game history
     * @return associatedUsername - a string of the associated username
     */
    public String getAssociatedUser(){
        return associatedUsername;
    }

    /**
     * Get the array of QR codes collected by this user.
     * @return codeHistory - an ArrayList of QR code data
     */
    public ArrayList<GameQRCode> getCodeHistory(){
        return this.codeHistory;
    }
}
