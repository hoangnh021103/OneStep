package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BanHangTaiQuayDTO {
    
    @NotEmpty(message = "Mã đơn hàng không được trống")
    private String maHoaDon;
    
    private Integer khachHangId;
    
    private String diaChiGiaoHang;
    
    private Float phiGiaoHang;
    
    private String maGiamGia;
    
    @NotEmpty(message = "Phương thức thanh toán không được trống")
    private String phuongThucThanhToan;
    
    @NotNull(message = "Tổng tiền không được null")
    @Min(value = 0, message = "Tổng tiền phải lớn hơn 0")
    private Float tongTien;
    
    private Integer trangThai;
    
    @NotEmpty(message = "Chi tiết đơn hàng không được trống")
    private List<ChiTietDonHangBanHangDTO> chiTietDonHang;
    
    private String ghiChu;
    
    private String nguoiTao;
    
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ChiTietDonHangBanHangDTO {
        
        @NotNull(message = "ID sản phẩm không được null")
        private Integer sanPhamId;
        
        // Field để lưu ChiTietSanPhamId sau khi tìm được
        private Integer chiTietSanPhamId;
        
        @NotNull(message = "Số lượng không được null")
        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        private Integer soLuong;
        
        @NotNull(message = "Đơn giá không được null")
        @Min(value = 0, message = "Đơn giá phải lớn hơn 0")
        private Float donGia;
        
        @NotNull(message = "Thành tiền không được null")
        @Min(value = 0, message = "Thành tiền phải lớn hơn 0")
        private Float thanhTien;
        
        private String kichThuoc;
        
        private String mauSac;
    }
}

