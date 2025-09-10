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
public class KhachHangDTO {

    private Integer id;


    private String hoTen;


    private String email;


    private String so_dien_thoai;


    private String gioiTinh;

    private LocalDate ngaySinh;


    private String matKhau;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
