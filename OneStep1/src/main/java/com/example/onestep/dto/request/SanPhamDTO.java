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

    private Integer chatLieuIdv;

    private Integer deGiayId;

    private Integer kieuDangId;

    private LocalDate ngayCapNhat;

    @Size(max = 200)
    private String nguoiTao;

    @Size(max = 200)
    private String nguoiCapNhat;

    private Integer daXoa;
}
