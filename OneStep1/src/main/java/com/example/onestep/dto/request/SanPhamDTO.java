package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SanPhamDTO {

    private Integer maSanPham;


    private String tenSanPham;


    private String maCode;


    private String moTa;

    private MultipartFile duongDanAnh;


    private Integer trangThai;


    private Integer thuongHieuId;


    private Integer chatLieuId;  // SỬA: Đổi từ chatLieuIdv


    private Integer deGiayId;

    private LocalDate ngayCapNhat;


    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}