package com.chuck_norris_random_facts_in_yodish.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntityReturnObject {
    private boolean success;
    private int httpStatusCode;
    private String returnMessage;
    private HttpStatus httpStatus;
    private Object result;

}
