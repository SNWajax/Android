package com.example.ajays.serachview;

/**
 * Created by ajays on 2/25/2017.
 */

public class Users {
    private String name;
    private int name_id;

    public Users(String name, int name_id){
        this.setName(name);
        this.setName_id(name_id);
    }

    public int getName_id() {
        return name_id;
    }

    public void setName_id(int name_id) {
        this.name_id = name_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
