package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietPhieuTraHangResponse {
    private Integer id;
    private Integer phieuTraHangId;
    private String maPhieuTraHang;
    private Integer chiTietSanPhamId;
    private String tenSanPham;
    private String tenMauSac;
    private String tenKichCo;
    private Integer soLuong;
    private Double giaTri;
    private String ghiChu;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
