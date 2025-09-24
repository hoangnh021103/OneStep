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


    private String soDienThoai;

    private String gioiTinh;

    @Past(message = "Ngày sinh phải nhỏ hơn ngày hiện tại")
    private LocalDate ngaySinh;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
