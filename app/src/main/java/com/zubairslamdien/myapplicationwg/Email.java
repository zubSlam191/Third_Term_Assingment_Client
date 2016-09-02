package com.zubairslamdien.myapplicationwg;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by student on 2016/04/04.
 */
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    long id;
    String address;
    String description;
    String date;

    public Email() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
