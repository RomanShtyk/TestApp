package com.example.rdsh.testapp.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Message.class, User.class}, version = 11, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract DaoMessage daoMessage();
    public abstract DaoUser daoUser();
}
