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

    @Column(name = "thuong_hieu_id")
    private Integer thuongHieuId;

    @Column(name = "kieu_dang_id")
    private Integer kieuDangId;

    @Column(name = "kich_co_id")
    private Integer kichCoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", referencedColumnName = "ma_san_pham")
    private SanPham sanPham;

    @Column(name = "chat_lieu_id")
    private Integer chatLieuId;

    @Column(name = "mau_sac_id")
    private Integer mauSacId;

    @Column(name = "hang_san_xuat_id")
    private Integer hangSanXuatId;

    @Column(name = "de_giay_id")
    private Integer deGiayId;

    @Column(name = "duong_dan_anh")
    private String duongDanAnh;

    @Column(name = "gia_tien")
    private Float giaTien;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "tien_giam_gia")
    private Float tienGiamGia;

    @Column(name = "da_xoa")
    private Integer daXoa;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;
}
