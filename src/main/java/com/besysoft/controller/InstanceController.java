package com.besysoft.controller;

import com.besysoft.webService.PapiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lzielinski on 12/07/2016.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class InstanceController {

    @RequestMapping(
            value = "/instance",
            method = RequestMethod.GET,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> getInstances(
            @RequestHeader(value="username") String username,
            @RequestHeader(value="password") String password) {

        String response = new PapiService(username, password).getInstances();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/instance/{id}",
            method = RequestMethod.GET,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> getInstanceInfo(
            @RequestHeader(value="username") String username,
            @RequestHeader(value="password") String password,
            @PathVariable("id") String id) {

        // if instanceId null, send 404
        String response = new PapiService(username, password).getInstanceInfo(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
