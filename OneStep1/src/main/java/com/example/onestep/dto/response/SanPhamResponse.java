package com.example.onestep.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SanPhamResponse {

    private Integer maSanPham;
    private String tenSanPham;
    private String maCode;
    private String moTa;
    private Integer thuongHieuId;
    private Integer chatLieuId;  // SỬA: Đảm bảo đúng tên
    private Integer deGiayId;

    private String duongDanAnh;
    private Integer trangThai;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}