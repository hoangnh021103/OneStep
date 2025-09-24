package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChiTietDonHangDTO {

    private Integer id;


    private Integer donHangId;


    private Integer chiTietSanPhamId;


    private Integer soLuong;

    private Float donGia;


    private Float tongTien;


    private Integer trangThai;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
