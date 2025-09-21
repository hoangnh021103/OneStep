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
    
    // Thông tin bổ sung từ chi tiết sản phẩm cho bán hàng
    private Float giaBan;
    private Integer soLuongTon;
    private String tenKichThuoc;
    private String tenMauSac;
    private String tenThuongHieu;
    private String tenChatLieu;
}