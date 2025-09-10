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
public class DonHangDTO {

    private Integer id;


    private Integer khachHangId;

    private Integer nhanVienId;

    private Integer voucherId;

    private Integer diaChiId;


    private String soDienThoai;


    private String hoTen;

    private String email;

    private Float tongTienGoc;

    private Float tienGiam;

    private Float tongTien;

    private Float tienShip;

    private LocalDate ngayXacNhan;

    private LocalDate ngayDuKien;

    private LocalDate ngayNhan;

    private Integer loaiDon;


    private String ghiChu;


    private String maDon;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
