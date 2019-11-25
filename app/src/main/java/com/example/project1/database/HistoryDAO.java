package com.example.project1.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project1.model.Alphabet;
import com.example.project1.model.TextHistory;

import java.util.List;

@Dao
public interface HistoryDAO {


    @Query("SELECT * FROM TextHistory ORDER BY name ASC ")
    List<TextHistory> getAllHistory();

    @Insert
    long[] insertHistory(TextHistory... textHistories);

    @Query("DELETE  FROM TextHistory")
    void deleteAll();
}
