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
public class DiaChiDTO {

    private Integer id;

    @NotNull(message = "Khách hàng không được để trống")
    private Integer khachHangId;  // chỉ truyền ID thay vì cả entity

    @Size(max = 50, message = "Mã quận tối đa 50 ký tự")
    private String maQuan;

    @Size(max = 50, message = "Tên quận tối đa 50 ký tự")
    private String tenQuan;

    @Size(max = 50, message = "Mã tỉnh tối đa 50 ký tự")
    private String maTinh;

    @Size(max = 50, message = "Tên tỉnh tối đa 50 ký tự")
    private String tenTinh;

    @Size(max = 50, message = "Mã phường tối đa 50 ký tự")
    private String maPhuong;

    @Size(max = 50, message = "Tên phường tối đa 50 ký tự")
    private String tenPhuong;

    private Boolean laMacDinh;

    @Size(max = 255, message = "Thông tin thêm tối đa 255 ký tự")
    private String thongTinThem;

    @Size(max = 20, message = "Số điện thoại tối đa 20 ký tự")
    @Pattern(regexp = "^\\d{9,11}$", message = "Số điện thoại không hợp lệ")
    private String soDienThoai;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}