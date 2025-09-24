package com.example.onestep.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SanPhamBanHangResponse {

    private Integer id;
    private Integer maSanPham;
    private String maCode;
    private String tenSanPham;
    private String duongDanAnh;
    private Float giaBan;
    private Integer soLuongTon;
    
    // Thông tin chi tiết từ ChiTietSanPham
    private Integer chiTietSanPhamId;
    private String tenKichThuoc;
    private String tenMauSac;
    private String tenThuongHieu;
    private String tenChatLieu;
    
    // Thông tin trạng thái
    private Integer trangThai;
}

