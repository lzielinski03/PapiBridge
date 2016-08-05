package com.besysoft.controller;

import com.besysoft.entity.User;
import com.besysoft.webService.PapiConnection;
import com.besysoft.webService.PapiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;


/**
 * Created by lzielinski on 12/07/2016.
 */

@RestController
@RequestMapping("/api")
public class AuthController {

    @CrossOrigin(origins = "*")
    @RequestMapping(
            value = "/auth",
            method = RequestMethod.POST,
            consumes= MediaType.APPLICATION_JSON_VALUE,
            headers = "content-type=application/json"
    )
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        try {
            new PapiService(user.getUsername(), user.getPassword()).getCurrentParticipant();

        } catch(SOAPFaultException e) {
            return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.FORBIDDEN);
        } catch (WebServiceException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("{\"sucess\": \"false\"}", HttpStatus.SERVICE_UNAVAILABLE);
        }

        return new ResponseEntity<String>("{\"sucess\": \"true\"}", HttpStatus.OK);
    }

}
