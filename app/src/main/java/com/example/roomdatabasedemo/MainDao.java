package com.example.roomdatabasedemo;
import android.app.UiAutomation;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

 @Dao
public interface MainDao {

    // Insert query
    @Insert(onConflict =REPLACE)
    void insert(MainData mainData);
     // Delete query

     @Delete
     void delete(MainData mainData);
     //to do

     // Update query
     @Query("UPDATE table_name SET nom= :sText,job=:sJob,phone=:sPhone,email=:sEmail where ID=:sID")
     void update(int sID, String sText,String sJob,String sPhone,String sEmail);

     // Get all data query
     @Query("SELECT * FROM table_name")
     List<MainData> getAll();
     @Query("SELECT * FROM table_name WHERE nom LIKE '%' || :search ||'%' ")
     public List<MainData> findUserWithName(String search);


}
