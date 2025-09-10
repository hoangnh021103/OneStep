package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "SanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_san_pham")
    private Integer maSanPham;

    @Column(name = "ten_san_pham")
    private String tenSanPham;

    @Column(name = "ma_code")
    private String maCode;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuong_hieu_id", referencedColumnName = "id")
    private ThuongHieu thuongHieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "de_giay_id", referencedColumnName = "id")
    private DeGiay deGiay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kieu_dang_id", referencedColumnName = "id")
    private KieuDang kieuDang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hang_san_xuat_id", referencedColumnName = "id")
    private ThuongHieu hangSanXuat; // Dùng lại entity ThuongHieu

    @Column(name = "duong_dan_anh")
    private String duongDanAnh;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
