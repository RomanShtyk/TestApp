package com.example.rdsh.testapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Message.class, User.class}, version = 12, exportSchema = false)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract DaoMessage daoMessage();
    public abstract DaoUser daoUser();
}
