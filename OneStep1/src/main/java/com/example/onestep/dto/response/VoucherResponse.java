package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {
    private Integer id;
    private String ma;
    private String ten;
    private Integer loai;
    private Float giaTri;
    private Float dieuKien;
    private Integer soLuong;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String duongDanAnh;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
