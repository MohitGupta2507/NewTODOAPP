package com.example.mohitgupta.newtodoapp;

/**
 * Created by Mohit Gupta on 15-12-2017.
 */

public class Task {
    private String Name,Time;
    public Task()
    {

    }
    public Task(String Name,String Time)
    {
        this.Name=Name;
        this.Time=Time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
