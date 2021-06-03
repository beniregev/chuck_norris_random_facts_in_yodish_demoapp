package com.chuck_norris_random_facts_in_yodish.controller;

import com.chuck_norris_random_facts_in_yodish.model.User;
import com.chuck_norris_random_facts_in_yodish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private ResponseEntityReturnObject returnObject = new ResponseEntityReturnObject();

    @Autowired
    private UserService userService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/")
    public ResponseEntity createUser(@RequestBody String requestBody) {
        try {
            User user = userService.createUser(requestBody);
            returnObject = ResponseEntityReturnObject.builder()
                    .success(true)
                    .httpStatus(HttpStatus.CREATED)
                    .httpStatusCode(HttpStatus.CREATED.value())
                    .returnMessage(HttpStatus.CREATED.name())
                    .result(user)
                    .build();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                    .returnMessage(HttpStatus.BAD_REQUEST.name() +
                            " -- " + ioe.getMessage())
                    .result(null)
                    .build();
        }
        return new ResponseEntity(returnObject, returnObject.getHttpStatus());
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/all")
    public ResponseEntity getAll() {
        List<User> listUsers = userService.getAllUsers();
        returnObject = ResponseEntityReturnObject.builder()
                .httpStatus(HttpStatus.OK)
                .httpStatusCode(HttpStatus.OK.value())
                .returnMessage(HttpStatus.OK.name())
                .result(listUsers)
                .build();
        return new ResponseEntity(returnObject, returnObject.getHttpStatus());
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.OK)
                    .httpStatusCode(HttpStatus.OK.value())
                    .returnMessage(HttpStatus.OK.name())
                    .result(user)
                    .build();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .httpStatusCode(HttpStatus.NOT_FOUND.value())
                    .returnMessage(HttpStatus.NOT_FOUND.name() +
                            " -- " + cnfe.getMessage())
                    .result(null)
                    .build();
        }
        return new ResponseEntity(returnObject, returnObject.getHttpStatus());
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(value = "/{id}")
    public ResponseEntity updateUser(@PathVariable String id,
                                     @RequestBody String requestBody) {
        try {
            User user = userService.updateUser(id, requestBody);
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.OK)
                    .httpStatusCode(HttpStatus.OK.value())
                    .returnMessage(HttpStatus.OK.name())
                    .result(user)
                    .build();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                    .returnMessage(HttpStatus.BAD_REQUEST.name() +
                            " -- " + ioe.getMessage())
                    .result(null)
                    .build();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .httpStatusCode(HttpStatus.NOT_FOUND.value())
                    .returnMessage(HttpStatus.NOT_FOUND.name() +
                            " -- " + cnfe.getMessage())
                    .result(null)
                    .build();
        }
        return new ResponseEntity(returnObject, returnObject.getHttpStatus());
    }


    @CrossOrigin
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUserById(@PathVariable String id) {
        try {
            User user = userService.deleteUserById(id);
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.OK)
                    .httpStatusCode(HttpStatus.OK.value())
                    .returnMessage(HttpStatus.OK.name())
                    .result(user)
                    .build();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            returnObject = ResponseEntityReturnObject.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .httpStatusCode(HttpStatus.NOT_FOUND.value())
                    .returnMessage(HttpStatus.NOT_FOUND.name() +
                            " -- " + cnfe.getMessage())
                    .result(null)
                    .build();
        }
        return new ResponseEntity(returnObject, returnObject.getHttpStatus());
    }

}
