package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PhuongThucThanhToanDTO {

    private Integer id;


    private String ten;

    private LocalDate ngayCapNhat;


    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
