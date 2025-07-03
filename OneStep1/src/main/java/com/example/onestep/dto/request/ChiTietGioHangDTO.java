package com.example.onestep.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChiTietGioHangDTO {

    private Integer id;

    @NotNull(message = "Giỏ hàng không được để trống")
    private Integer gioHangId;

    @NotNull(message = "Chi tiết sản phẩm không được để trống")
    private Integer chiTietSanPhamId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    private Integer soLuong;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
