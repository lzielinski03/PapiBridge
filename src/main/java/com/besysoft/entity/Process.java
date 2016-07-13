package com.besysoft.entity;

import java.io.Serializable;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Process implements Serializable{

    private int id;
    private String name;

    public Process(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
