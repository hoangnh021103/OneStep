package com.example.onestep.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRRequest {
    private String bankBin;
    private String accountNo;
    private String accountName;
    private long amount;
    private String addInfo;
}
