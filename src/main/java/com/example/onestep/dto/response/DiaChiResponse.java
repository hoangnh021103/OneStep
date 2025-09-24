package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChiResponse {
    private Integer id;
    private Integer khachHangId;
    private String maQuan;
    private String tenQuan;
    private String maTinh;
    private String tenTinh;
    private String maPhuong;
    private String tenPhuong;
    private Boolean laMacDinh;
    private String thongTinThem;
    private String soDienThoai;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
