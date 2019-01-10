package com.example.rdsh.testapp.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoMessage {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMessage(Message message);

// --Commented out by Inspection START (10.01.2019 13:24):
//    @Query("SELECT * From messages where id = :id")
//    Message getMessageById(int id);
// --Commented out by Inspection STOP (10.01.2019 13:24)

    @Query("SELECT * FROM messages where userid = :userId")
    List<Message> getChatByUserId(int userId);

// --Commented out by Inspection START (10.01.2019 13:24):
//    @Query("DELETE FROM messages")
//    void deleteAll();
// --Commented out by Inspection STOP (10.01.2019 13:24)
}
