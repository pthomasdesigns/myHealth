package com.pthomasdesigns.myhealth.rest.model;


import com.pthomasdesigns.myhealth.MainActivity;

import org.parceler.Parcel;

@Parcel
public class Message {
    String from;
    String to;
    String fromdn;
    String todn;
    String date;
    String message;

    public static final String TAG = MainActivity.PACKAGE_NAME + ".Message";

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromdn() {
        return fromdn;
    }

    public void setFromdn(String fromdn) {
        this.fromdn = fromdn;
    }

    public String getTodn() {
        return todn;
    }

    public void setTodn(String todn) {
        this.todn = todn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
