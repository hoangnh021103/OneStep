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
public class MauSacDTO {

    private Integer id;

    @NotBlank(message = "Tên màu sắc không được để trống")
    @Size(max = 100, message = "Tên màu sắc tối đa 100 ký tự")
    private String ten;

    @Size(max = 20, message = "Mã màu tối đa 20 ký tự")
    private String maMau;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 200, message = "Người tạo tối đa 200 ký tự")
    private String nguoiTao;

    @Size(max = 200, message = "Người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
