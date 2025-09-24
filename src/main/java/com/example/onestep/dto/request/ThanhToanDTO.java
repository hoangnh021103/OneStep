package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ThanhToanDTO {

    private Integer id;


    private Integer donHangId;


    private Integer phuongThucId;

    private String maGiaoDich;


    private Float tongTien;


    private String moTa;


    private Integer trangThai;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
