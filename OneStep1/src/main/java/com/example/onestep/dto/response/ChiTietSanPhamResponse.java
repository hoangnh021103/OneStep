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
public class ChiTietSanPhamResponse {

    private Integer maChiTiet;

    private Integer kichCoId;

    private Integer sanPhamId;

    private Integer mauSacId;

    private String duongDanAnh;

    private Float giaTien;

    private Integer soLuongTon;

    private Integer trangThai;

    private Float tienGiamGia;

    private Integer daXoa;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;
}