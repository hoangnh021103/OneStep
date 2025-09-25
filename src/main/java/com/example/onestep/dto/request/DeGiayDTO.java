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
public class DeGiayDTO {

    private Integer id;

    @NotBlank(message = "Tên đế giày không được để trống")
    @Size(max = 255, message = "Tên đế giày tối đa 255 ký tự")
    private String ten;

    @NotNull(message = "Trạng thái không được null")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 200, message = "Người tạo tối đa 200 ký tự")
    private String nguoiTao;

    @Size(max = 200, message = "Người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
