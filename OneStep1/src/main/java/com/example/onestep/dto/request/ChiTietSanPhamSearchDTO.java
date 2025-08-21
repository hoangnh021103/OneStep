package com.example.onestep.dto.request;

import lombok.Data;


@Data
public class ChiTietSanPhamSearchDTO {
    private String tenSanPham;
    private String maCode;
    private Integer trangThai;
    private Integer daXoa;
    private Integer thuongHieuId;
    private Integer kieuDangId;
    private Integer kichCoId;
    private Integer chatLieuId;
    private Integer mauSacId;
    private Double giaMin;
    private Double giaMax;
}