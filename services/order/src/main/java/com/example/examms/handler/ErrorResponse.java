package com.example.examms.handler;

import java.util.Map;

public record ErrorResponse(
    Map<String, String> errors
) {

}
