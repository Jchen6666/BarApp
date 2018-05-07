package com.example.jerrychen.barapp;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

/**
 * Created by jerrychen on 5/2/18.
 */

public class Order {
    private Date date;
    private Map<String,Integer> oderMap;



    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    private boolean isFinished;

 public Order(Date date,Map orderMap){
     this.date=date;
     this.oderMap=orderMap;
 }

    public Date getDate() {
        return date;
    }

    public Map<String, Integer> getOderMap() {
        return oderMap;
    }

}
