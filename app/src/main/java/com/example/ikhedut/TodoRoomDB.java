package com.example.ikhedut;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {TODO.class},version = 1)
public abstract class TodoRoomDB extends RoomDatabase {

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract TodoDao todoDao();
    public static volatile TodoRoomDB INSTANCE;

    static TodoRoomDB getInstance(Context context){
        if(INSTANCE==null){
            synchronized (TodoRoomDB.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TodoRoomDB.class,"iKhedut").build();
                }
            }
        }
        return INSTANCE;
    }
}
