package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChiTietSanPhamDTO {

    private Integer maChiTiet;


    private Integer thuongHieuId;


    private Integer kieuDangId;

    private Integer kichCoId;

    private Integer sanPhamId;


    private Integer chatLieuId;


    private Integer mauSacId;


    private Integer hangSanXuatId;



    private Float giaTien;


    private Integer soLuongTon;


    private Integer trangThai;

    private Float tienGiamGia = 0f;

    private Integer daXoa = 0;


    private LocalDate ngayCapNhat = LocalDate.now();


    private String nguoiTao;
    private String nguoiCapNhat;
}
