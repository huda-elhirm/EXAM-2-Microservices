package com.example.examms.User.handler;

import java.util.Map;

public record ErrorResponse (
    Map<String, String> errors

) {

}
