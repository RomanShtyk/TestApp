package com.example.rdsh.testapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoUser {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User user);

// --Commented out by Inspection START (10.01.2019 13:25):
//    @Query("SELECT name FROM user where id = :id")
//    String getName(int id);
// --Commented out by Inspection STOP (10.01.2019 13:25)

    @Query("SELECT * FROM user")
    List<User> getAll();

// --Commented out by Inspection START (10.01.2019 13:25):
//    @Query("DELETE FROM user")
//    void deleteAll();
// --Commented out by Inspection STOP (10.01.2019 13:25)

    @Query("SELECT * From user where id = :id")
    User getUserById(int id);
}
