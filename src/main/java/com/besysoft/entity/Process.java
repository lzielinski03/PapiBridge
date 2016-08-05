package com.besysoft.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Process implements Serializable{

    public String id;
    public String name;
    public List<Instance> instances = new ArrayList<>();

    public Process(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Process(String id, String name, Instance instance) {
        this.id = id;
        this.name = name;
        this.instances.add(instance);
    }
}
