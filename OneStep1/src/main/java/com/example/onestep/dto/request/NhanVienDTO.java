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
public class NhanVienDTO {

    private Integer id;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 255, message = "Họ tên tối đa 255 ký tự")
    private String hoTen;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;

    @NotBlank(message = "Giới tính không được để trống")
    @Size(max = 50, message = "Giới tính tối đa 50 ký tự")
    private String gioiTinh;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 255, message = "Email tối đa 255 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(max = 255, message = "Mật khẩu tối đa 255 ký tự")
    private String matKhau;

    @Size(max = 20, message = "Số điện thoại tối đa 20 ký tự")
    private String soDienThoai;

    @Size(max = 500, message = "Địa chỉ tối đa 500 ký tự")
    private String diaChi;

    private String urlAnh; // TEXT nên không giới hạn độ dài ở DTO

    @NotNull(message = "Vai trò không được để trống")
    private Integer vaiTroId; // Truyền ID của VaiTro, tránh truyền cả object

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Size(max = 255)
    private String nguoiTao;

    @Size(max = 255)
    private String nguoiCapNhat;

    private Integer daXoa;
}
