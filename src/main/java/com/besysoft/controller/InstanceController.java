package com.besysoft.controller;

import com.besysoft.entity.User;
import com.besysoft.webService.PapiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lzielinski on 12/07/2016.
 */

@RestController
@RequestMapping("/api")
public class InstanceController {

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/instances",
            method = RequestMethod.GET,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> getInstances(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        String response = new PapiService(user.getUsername(), user.getPassword()).getInstances();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
