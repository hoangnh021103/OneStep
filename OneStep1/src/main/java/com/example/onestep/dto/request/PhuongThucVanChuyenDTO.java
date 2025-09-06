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
public class PhuongThucVanChuyenDTO {

    private Integer id;

    @NotBlank(message = "Tên phương thức vận chuyển không được để trống")
    @Size(max = 255, message = "Tên phương thức vận chuyển tối đa 255 ký tự")
    private String ten;

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String moTa;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
