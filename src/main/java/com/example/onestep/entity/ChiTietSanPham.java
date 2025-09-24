package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ChiTietSanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet")
    private Integer maChiTiet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kich_co_id", referencedColumnName = "id")
    private KichCo kichCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", referencedColumnName = "ma_san_pham")
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", referencedColumnName = "id")
    private MauSac mauSac;

    @Column(name = "duong_dan_anh")
    private String duongDanAnh;

    @Column(name = "gia_tien", nullable = false)
    private Float giaTien;

    @Column(name = "so_luong_ton", nullable = false)
    private Integer soLuongTon;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai;

    @Column(name = "tien_giam_gia", nullable = false)
    private Float tienGiamGia ;

    @Column(name = "da_xoa", nullable = false)
    private Integer daXoa ;

    @Column(name = "ngay_cap_nhat", nullable = false)
    private LocalDate ngayCapNhat = LocalDate.now();

    @Column(name = "nguoi_tao", nullable = false)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", nullable = false)
    private String nguoiCapNhat;
}
