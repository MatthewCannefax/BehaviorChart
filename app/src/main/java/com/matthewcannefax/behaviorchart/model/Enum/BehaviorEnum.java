package com.matthewcannefax.behaviorchart.model.Enum;

public enum BehaviorEnum {

    //Behavior Categories and their value
    WHINING("Whining", 1),
    NOT_LISTENING("Not Listening", 1),
    FIGHTING("Fighting", 1),
    FIT_THROWING("Fit Throwing", 3),
    TIMEOUT("Timeout", 3);

    final String mName;
    final int mValue;

    BehaviorEnum(String name, int value){
        mName = name;
        mValue = value;
    }

    @Override
    public String toString() {
        return mName;
    }

    public int getValue() {
        return mValue;
    }
}
