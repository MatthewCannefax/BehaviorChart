package com.matthewcannefax.behaviorchart.model;

import com.matthewcannefax.behaviorchart.model.Enum.BehaviorEnum;

import java.util.ArrayList;
import java.util.List;

public class Kid {
    private String pName;
    private int pID;
    private List<BehaviorEnum> infractions;
    final int CANDY_THRESHOLD = 3;
    final int VIDEO_GAME_THRESHOLD = 5;

    public boolean getCandy(){
        return getPoints() < CANDY_THRESHOLD;
    }

    public boolean playScreens(){
        return getPoints() < VIDEO_GAME_THRESHOLD;
    }

    public int getPoints(){
        if(infractions != null && infractions.size() != 0){
            int total = 0;
            for(BehaviorEnum behavior: infractions){
                total += behavior.getValue();
            }
            return total;
        }else {
            return 0;
        }
    }

    public List<BehaviorEnum> getInfractions() {
        if(infractions == null){
            infractions = new ArrayList<>();
            return infractions;
        }else{
            return infractions;
        }
    }

    public void setInfractions(List<BehaviorEnum> infractions) {
        this.infractions = infractions;
    }

    public Kid(String pName, int pID) {
        this.pName = pName;
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }
}
