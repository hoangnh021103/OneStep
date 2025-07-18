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
public class VoucherDTO {

    private Integer id;

    @NotBlank(message = "Mã voucher không được để trống")
    @Size(max = 10, message = "Mã voucher tối đa 10 ký tự")
    private String ma;

    @Size(max = 255, message = "Tên voucher tối đa 255 ký tự")
    private String ten;

    @NotNull(message = "Loại voucher không được để trống")
    private Integer loai;

    @NotNull(message = "Giá trị voucher không được để trống")
    @Positive(message = "Giá trị phải lớn hơn 0")
    private Float giaTri;

    @PositiveOrZero(message = "Điều kiện phải lớn hơn hoặc bằng 0")
    private Float dieuKien;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải từ 0 trở lên")
    private Integer soLuong;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate ngayKetThuc;

    @Size(max = 1000, message = "Đường dẫn ảnh tối đa 1000 ký tự")
    private String duongDanAnh;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
