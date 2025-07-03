package com.example.onestep.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GioHangResponse {

    private Integer id;

    private Integer khachHangId;

    private String tenKhachHang; // Optionally thêm tên KH nếu cần

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}