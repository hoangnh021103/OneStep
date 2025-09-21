package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHangResponse {
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
    private Integer trangThai;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}