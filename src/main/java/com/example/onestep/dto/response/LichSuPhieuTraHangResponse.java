package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuPhieuTraHangResponse {
    private Integer id;
    private Integer phieuTraHangId;
    private Short trangThaiHanhDong;
    private String ghiChu;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
