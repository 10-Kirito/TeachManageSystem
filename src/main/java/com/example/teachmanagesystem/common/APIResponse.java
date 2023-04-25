package com.example.teachmanagesystem.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse <T>{
    private T data;
    private APIStatusCode code;
    private String msg;
}

