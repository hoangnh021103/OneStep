package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ThanhToanDTO {

    private Integer id;

    @NotNull(message = "Đơn hàng không được để trống")
    private Integer donHangId;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private Integer phuongThucId;

    @Size(max = 50, message = "Mã giao dịch tối đa 50 ký tự")
    private String maGiaoDich;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Tổng tiền phải lớn hơn 0")
    private Float tongTien;

    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String moTa;

    @NotNull(message = "Trạng thái không được để trống")
    private Integer trangThai;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
