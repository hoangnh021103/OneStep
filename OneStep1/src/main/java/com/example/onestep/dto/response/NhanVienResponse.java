package com.example.onestep.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class NhanVienResponse {
    private Integer id;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private String vaiTro;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
    private Boolean daXoa;
}
