package com.example.getum;

public class RecordData {

    private int logNo;
    private int rentflag;
    private String location;
    private String timestamp;

    public RecordData(int logNo, int rentflag, String location, String timestamp){
        this.logNo = logNo;
        this.rentflag = rentflag;
        this.location = location;
        this.timestamp = timestamp;
    }

    public int getLogNo(){
        return logNo;
    }

    public int getRentflag(){
        return rentflag;
    }

    public String getLocation(){
        return location;
    }

    public String getTimestamp(){
        return timestamp;
    }

}
