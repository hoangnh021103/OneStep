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
public class SanPhamKhuyenMaiDTO {

    private Integer id;

    @NotNull(message = "ID sản phẩm không được để trống")
    private Integer sanPhamId;

    @NotNull(message = "ID voucher không được để trống")
    private Integer voucherId;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
