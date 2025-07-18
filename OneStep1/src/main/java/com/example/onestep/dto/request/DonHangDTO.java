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
public class DonHangDTO {

    private Integer id;

    @NotNull(message = "Khách hàng không được để trống")
    private Integer khachHangId;

    private Integer nhanVienId;

    private Integer voucherId;

    private Integer diaChiId;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 20, message = "Số điện thoại tối đa 20 ký tự")
    private String soDienThoai;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 255, message = "Họ tên tối đa 255 ký tự")
    private String hoTen;

    @Email(message = "Email không hợp lệ")
    @Size(max = 255, message = "Email tối đa 255 ký tự")
    private String email;

    private Float tongTienGoc;

    private Float tienGiam;

    private Float tongTien;

    private Float tienShip;

    private LocalDate ngayXacNhan;

    private LocalDate ngayDuKien;

    private LocalDate ngayNhan;

    private Integer loaiDon;

    @Size(max = 1000, message = "Ghi chú tối đa 1000 ký tự")
    private String ghiChu;

    @NotBlank(message = "Mã đơn không được để trống")
    @Size(max = 10, message = "Mã đơn tối đa 10 ký tự")
    private String maDon;

    private LocalDate ngayCapNhat;

    @Size(max = 200, message = "Người tạo tối đa 200 ký tự")
    private String nguoiTao;

    @Size(max = 200, message = "Người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
