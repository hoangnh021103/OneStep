package com.example.onestep.dto.responerror;
import com.example.onestep.dto.responerror.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private List<ErrorDetails> errors;
}
