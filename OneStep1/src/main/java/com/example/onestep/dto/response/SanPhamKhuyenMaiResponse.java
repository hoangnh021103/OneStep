package com.example.onestep.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamKhuyenMaiResponse {
    private Integer id;
    private Integer sanPhamId;
    private String tenSanPham;
    private Integer voucherId;
    private String tenVoucher;
    private LocalDate ngayCapNhat;
    private String nguoiTao;
    private String nguoiCapNhat;
    private Integer daXoa;
}
