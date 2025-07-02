package com.example.onestep.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChiTietSanPhamResponse {

    private Integer maChiTiet;

    private Integer thuongHieuId;

    private Integer kieuDangId;

    private Integer kichCoId;

    private Integer sanPhamId;

    private Integer chatLieuId;

    private Integer mauSacId;

    private Integer hangSanXuatId;

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
