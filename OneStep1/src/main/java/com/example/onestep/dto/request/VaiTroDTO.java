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
public class VaiTroDTO {

    private Integer id;

    @NotBlank(message = "Tên vai trò không được để trống")
    @Size(max = 255, message = "Tên vai trò tối đa 255 ký tự")
    private String tenVaiTro;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
