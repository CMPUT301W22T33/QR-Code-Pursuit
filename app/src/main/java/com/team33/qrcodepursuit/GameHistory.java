package com.team33.qrcodepursuit;

import java.util.ArrayList;

public class GameHistory {
    ArrayList<GameQRCode> codeHistory;
    GameStats stats;
    String associatedUsername;

    public void addCode(GameQRCode gameQRCode){
        codeHistory.add(gameQRCode);
    }

    public GameStats getStats(){
        return this.stats;
    }

    public void setAssociatedUser(String associatedUsername){
        this.associatedUsername = associatedUsername;
    }

    public String getAssociatedUser(){
        return associatedUsername;
    }

    public ArrayList<GameQRCode> getCodeHistory(){
        return this.codeHistory;
    }
}
