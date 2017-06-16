package com.cocos.mfcn.models;

public enum GrilState {
    ONLINE(1),OFF(0);
    private int value;


    GrilState(int value) {
        this.value = value;
    }

    public static GrilState cast(int val)
    {
        switch (val)
        {
            case 0:
                return OFF;
            case 1:
                return ONLINE;
        }
        return OFF;
    }

    public static GrilState cast(String enVal)
    {
        switch (enVal)
        {
            case "Offline":
                return OFF;
            case "Online":
                return ONLINE;
        }
        return OFF;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (this)
        {
            case OFF:
                return "离线";
            case ONLINE:
                return "在线";
            default:
                    return "未知";
        }
    }


}
