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
public class KhachHangDTO {

    private Integer id;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 255, message = "Họ tên tối đa 255 ký tự")
    private String ten;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 255, message = "Email tối đa 255 ký tự")
    private String email;

    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại không hợp lệ")
    private String sdt;

    @NotBlank(message = "Giới tính không được để trống")
    @Pattern(regexp = "Nam|Nữ|Khác", message = "Giới tính phải là Nam, Nữ hoặc Khác")
    private String gioiTinh;

    private LocalDate ngaySinh;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matKhau;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Size(max = 200)
    private String nguoiTao;

    @Size(max = 200)
    private String nguoiCapNhat;

    private Integer daXoa;
}
