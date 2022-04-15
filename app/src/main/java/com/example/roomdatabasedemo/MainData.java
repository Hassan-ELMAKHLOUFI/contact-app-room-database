package com.example.roomdatabasedemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName ="table_name")

public class MainData implements Serializable {

    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    // Create text column
    @ColumnInfo(name="nom")
    private String nom;
    @ColumnInfo(name="phone")
    private String phone;

    @ColumnInfo(name="email")
    private String email;

    @ColumnInfo(name="job")
    private String job;
    //Generate getters and setters

    public int getID() {
        return ID;
    }

    public String getNom() {
        return nom;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
