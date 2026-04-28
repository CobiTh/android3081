package com.example.android3081;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FriendDao {

    @Insert
    void insert(Friend friend);

    @Update
    void update(Friend friend);

    @Delete
    void delete(Friend friend);

    @Query("SELECT * FROM friends WHERE user_id = :userId LIMIT 1")
    Friend getFriendForUser(int userId);
}