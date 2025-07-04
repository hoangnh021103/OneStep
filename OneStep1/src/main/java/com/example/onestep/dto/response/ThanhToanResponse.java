package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToanResponse {
    private Integer id;
    private Integer donHangId;
    private Integer phuongThucId;
    private String maGiaoDich;
    private Float tongTien;
    private String moTa;
    private Integer trangThai;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
