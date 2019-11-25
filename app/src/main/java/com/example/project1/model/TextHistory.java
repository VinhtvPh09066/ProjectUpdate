package com.example.project1.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TextHistory {
    @PrimaryKey
    public int id;
    @ColumnInfo(name="time")
    public String time;

    @ColumnInfo(name ="name")
    public String name;

    public TextHistory(int id, String time, String name) {
        this.id = id;
        this.time = time;
        this.name = name;
    }
}
