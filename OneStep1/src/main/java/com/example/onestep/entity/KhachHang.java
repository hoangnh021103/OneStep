package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ho_ten", nullable = false)
    private String ten;

    @Column(name = "email")
    private String email;

    @Column(name = "so_dien_thoai")
    private String sdt;

    @Column(name = "gioi_tinh")
    private String gioiTinh; // sửa từ Boolean → String

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;

}
