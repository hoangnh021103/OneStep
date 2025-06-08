package com.example.onestep.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SanPhamResponse {

    private Integer maSanPham;

    private String tenSanPham;

    private String maCode;

    private String moTa;

    private String duongDanAnh;

    private Integer trangThai;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;

}
