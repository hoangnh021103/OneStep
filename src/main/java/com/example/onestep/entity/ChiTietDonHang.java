package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ChiTietDonHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id")
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chi_tiet_san_pham_id")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "don_gia")
    private Float donGia;

    @Column(name = "tong_tien")
    private Float tongTien;

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
