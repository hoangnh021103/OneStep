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

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String moTa;

    private MultipartFile duongDanAnh;

    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 200)
    private String nguoiTao;

    @Size(max = 200)
    private String nguoiCapNhat;

    private Integer daXoa;
}
