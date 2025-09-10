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
public class NhanVienDTO {

    private Integer id;


    private String hoTen;


    private LocalDate ngaySinh;


    private String gioiTinh;


    private String email;


    private String matKhau;


    private String soDienThoai;


    private String diaChi;

    private String urlAnh; // TEXT nên không giới hạn độ dài ở DTO


    private Integer vaiTroId; // Truyền ID của VaiTro, tránh truyền cả object

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Size(max = 255)
    private String nguoiTao;

    @Size(max = 255)
    private String nguoiCapNhat;

    private Integer daXoa;
}
