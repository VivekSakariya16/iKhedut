package com.example.ikhedut;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void insertTodo(TODO todo);

    @Query("SELECT * FROM item_data")
    List<TODO> getAllItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=1")
    List<TODO> getBiyaranItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=2")
    List<TODO> getDavaItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=3")
    List<TODO> getKhatarItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=4")
    List<TODO> getDieselItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=5")
    List<TODO> getMajurUpadItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=6")
    List<TODO> getMajuriItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=7")
    List<TODO> getOjarItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=8")
    List<TODO> getLightItem();
    
    @Query("SELECT * FROM item_data WHERE itemID=9")
    List<TODO> getotherItem();

}
