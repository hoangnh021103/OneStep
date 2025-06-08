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
public class SanPhamDTO {

    private Integer maSanPham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm tối đa 255 ký tự")
    private String tenSanPham;

    @NotBlank(message = "Mã code không được để trống")
    @Size(max = 20, message = "Mã code tối đa 20 ký tự")
    private String maCode;

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String moTa;

    @Size(max = 200, message = "Đường dẫn ảnh tối đa 200 ký tự")
    private String duongDanAnh;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 200)
    private String nguoiTao;

    @Size(max = 200)
    private String nguoiCapNhat;

    private Integer daXoa;
}
