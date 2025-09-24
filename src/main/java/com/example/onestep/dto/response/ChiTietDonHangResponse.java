package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHangResponse {
    private Integer id;
    private Integer donHangId;
    private Integer chiTietSanPhamId;
    private Integer soLuong;
    private Float donGia;
    private Float tongTien;
    private Integer trangThai;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
