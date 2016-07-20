package com.besysoft.controller;

import com.besysoft.entity.User;
import com.besysoft.webService.Papi;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import stubs.InstanceInfoBean;

import com.besysoft.entity.Process;

import javax.xml.ws.soap.SOAPFaultException;


/**
 * Created by lzielinski on 12/07/2016.
 */

@RestController
@RequestMapping("/api")
public class ProcessController {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/auth", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE, headers = "content-type=application/json")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        try {
            new Papi(user.getUsername(), user.getPassword()).getCurrentParticipant();
        } catch(SOAPFaultException e) {
            //return e.getMessage();
            return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);
        }

        //return "success";
        return new ResponseEntity<String>("{\"sucess\": \"true\"}", HttpStatus.OK);
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/processes", method = RequestMethod.GET)
//    public Process getProcesses() {
//
////        Test x = new Test();
////        x.test();
//
//        Papi papi = new Papi(user.getUsername(), user.getPassword());
//
//        System.out.println("\nprocesses");
//
//        for (InstanceInfoBean instanceInfoBean : papi.processGetInstances().getInstances()) {
//            System.out.println(instanceInfoBean.getDescription());
//            System.out.println(instanceInfoBean.getParticipant());
//            System.out.println(instanceInfoBean.getActivityName());
//            System.out.println(instanceInfoBean.getProcess());
//        }

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

//        List<Process> result = new ArrayList<>();
//        result.add(new Process(1, "Proces 1"));
//        result.add(new Process(2, "Proces 2"));
//        return new Process(1, "Proces 1");
//    }
}
