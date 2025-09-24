package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuTraHangResponse {
    private Integer id;
    private String ma;
    private Float giaTri;
    private Float soTienPhaiTra;
    private String thongTinThanhToan;
    private String hinhThucThanhToan;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
