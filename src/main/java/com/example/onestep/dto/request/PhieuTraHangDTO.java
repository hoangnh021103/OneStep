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
public class PhieuTraHangDTO {

    private Integer id;


    private String ma;


    private Float giaTri;


    private Float soTienPhaiTra;


    private String thongTinThanhToan;


    private String hinhThucThanhToan;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
