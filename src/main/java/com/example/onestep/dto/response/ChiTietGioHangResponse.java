package com.example.onestep.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChiTietGioHangResponse {

    private Integer id;

    private Integer gioHangId;

    private Integer chiTietSanPhamId;
    private Integer soLuong;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
