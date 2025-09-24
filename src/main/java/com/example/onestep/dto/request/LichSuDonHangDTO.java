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
public class LichSuDonHangDTO {

    private Integer id;


    private Integer donHangId;


    private Integer trangThai;

    private LocalDate ngayCapNhat;


    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
