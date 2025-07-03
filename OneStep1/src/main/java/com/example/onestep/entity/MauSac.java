package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MauSac")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MauSac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "ma_mau")
    private String maMau;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_cap_nhat")
    private java.time.LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
