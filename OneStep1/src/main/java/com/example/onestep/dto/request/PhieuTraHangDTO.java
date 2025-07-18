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
public class PhieuTraHangDTO {

    private Integer id;

    @NotBlank(message = "Mã phiếu trả không được để trống")
    @Size(max = 10, message = "Mã phiếu trả tối đa 10 ký tự")
    private String ma;

    @NotNull(message = "Giá trị không được để trống")
    @PositiveOrZero(message = "Giá trị phải lớn hơn hoặc bằng 0")
    private Float giaTri;

    @NotNull(message = "Số tiền phải trả không được để trống")
    @PositiveOrZero(message = "Số tiền phải trả phải lớn hơn hoặc bằng 0")
    private Float soTienPhaiTra;

    @Size(max = 255, message = "Thông tin thanh toán tối đa 255 ký tự")
    private String thongTinThanhToan;

    @Size(max = 255, message = "Hình thức thanh toán tối đa 255 ký tự")
    private String hinhThucThanhToan;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
