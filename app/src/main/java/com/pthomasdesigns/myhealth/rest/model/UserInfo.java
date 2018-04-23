package com.pthomasdesigns.myhealth.rest.model;

import com.pthomasdesigns.myhealth.MainActivity;
import com.pthomasdesigns.myhealth.MainFragment;

import org.parceler.Parcel;

@Parcel
public class UserInfo {
    String id;
    String password;
    String name;
    int visits;
    int bills;
    int messages;

    public static final String TAG = MainActivity.PACKAGE_NAME + ".UserInfo";
    public static final String USER_ID_TAG = MainActivity.PACKAGE_NAME + ".UserId";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
