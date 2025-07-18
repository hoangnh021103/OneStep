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
public class KieuDangDTO {

    private Integer id;

    @NotBlank(message = "Tên kiểu dáng không được để trống")
    @Size(max = 255, message = "Tên kiểu dáng tối đa 255 ký tự")
    private String ten;

    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 200, message = "Tên người tạo tối đa 200 ký tự")
    private String nguoiTao;

    @Size(max = 200, message = "Tên người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
