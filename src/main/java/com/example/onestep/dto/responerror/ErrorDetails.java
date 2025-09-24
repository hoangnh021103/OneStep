package com.example.onestep.dto.responerror;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private String field;
    private String message;
}
