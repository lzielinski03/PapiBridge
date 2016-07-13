package com.besysoft.controller;

import com.besysoft.Test;
import com.besysoft.webService.Papi;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.besysoft.entity.Process;
import stubs.ActivityBean;
import stubs.InstanceInfoBean;
import stubs.ProcessBean;
import stubs.StringListBean;

/**
 * Created by lzielinski on 12/07/2016.
 */

@RestController
public class ProcessController {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/processes", method = RequestMethod.GET)
    public List<Process> getProcesses() {

//        Test x = new Test();
//        x.test();

        Papi papi = new Papi();

        System.out.println("\nprocesses");
        for (InstanceInfoBean instanceInfoBean : papi.processGetInstances().getInstances()) {
            System.out.println(instanceInfoBean.getDescription());
            System.out.println(instanceInfoBean.getParticipant());
            System.out.println(instanceInfoBean.getActivityName());
            System.out.println(instanceInfoBean.getProcess());
        }

/*
            System.out.println("Process activities: ");
            for (ActivityBean activity : x.getActivities()) {
                System.out.println("activity name: " + activity.getName());
                System.out.println("activity Process id: " + activity.getProcessId());
                System.out.println("activity Role: " + activity.getRole());
                System.out.println("activity IsActive: " + activity.isIsActive());
                System.out.println();
            }
            System.out.println();
        for (ProcessBean x : papi.processesGet().getProcesses()) {

            System.out.println("Process id: " + x.getId());
            System.out.println("Process name: " + x.getName());
            System.out.println("Process active: " + x.isIsActive());
        }*/

        List<Process> result = new ArrayList<>();
        result.add(new Process(1, "Proces 1"));
        result.add(new Process(2, "Proces 2"));
        return result;
    }
}
