package com.example.onestep.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NhanVienDTO {
    @NotBlank
    private String hoTen;

    private LocalDate ngaySinh;

    private String gioiTinh;

    @Email
    private String email;

    @Size(max = 20)
    private String soDienThoai;

    private String diaChi;

    private Integer vaiTroId;
}
