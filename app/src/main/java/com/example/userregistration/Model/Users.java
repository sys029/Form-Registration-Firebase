package com.example.userregistration.Model;

public class Users {

    private String Password;
    private String Place;
    private String Gender;
    private String Time;

    public Users() {
    }

    public Users(String password, String place, String gender, String time) {
        Password = password;
        Place = place;
        Gender = gender;
        Time = time;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
