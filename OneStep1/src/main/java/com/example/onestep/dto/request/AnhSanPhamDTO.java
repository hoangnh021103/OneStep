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
public class AnhSanPhamDTO {

    private Integer id;

    @NotNull(message = "ID sản phẩm không được để trống")
    private Integer sanPhamId;

    @Size(max = 200, message = "Đường dẫn ảnh tối đa 200 ký tự")
    private String duongDanAnh;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
