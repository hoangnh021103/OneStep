package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DiaChiDTO {

    private Integer id;


    private Integer khachHangId;  // chỉ truyền ID thay vì cả entity


    private String maQuan;


    private String tenQuan;

    private String maTinh;

    private String tenTinh;


    private String maPhuong;

    private String tenPhuong;

    private Boolean laMacDinh;


    private String thongTinThem;


    private String soDienThoai;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}