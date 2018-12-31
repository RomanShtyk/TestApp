package com.example.rdsh.testapp.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "bubble_out")
    private String message;
    @ColumnInfo(name = "time")
    private long time;
    @ColumnInfo(name = "isfromme")
    private int isFromMe;
    @ColumnInfo(name = "userid")
    private int user_id;

    public Message(String message, long time, int isFromMe, int user_id) {
        this.message = message;
        this.time = time;
        this.isFromMe = isFromMe;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIsFromMe() {
        return isFromMe;
    }

    public void setIsFromMe(int isFromMe) {
        this.isFromMe = isFromMe;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
