package com.example.rdsh.testapp.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoUser {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

    @Query("SELECT name FROM user where id = :id")
    String getName(int id);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * From user where id = :id")
    User getUserById(int id);
}
