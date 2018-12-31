package com.example.rdsh.testapp.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoMessage {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addMessage(Message message);

    @Query("SELECT * From messages where id = :id")
    Message getMessageById(int id);

    @Query("SELECT * FROM messages where userid = :userId")
    List<Message> getChatByUserId(int userId);

    @Query("DELETE FROM messages")
    void deleteAll();
}
