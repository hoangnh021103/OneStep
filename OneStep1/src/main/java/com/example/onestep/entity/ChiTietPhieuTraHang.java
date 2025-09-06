package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChiTietPhieuTraHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietPhieuTraHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phieu_tra_hang_id")
    private PhieuTraHang phieuTraHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chi_tiet_san_pham_id")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_tri")
    private Double giaTri;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "ngay_cap_nhat")
    private java.time.LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
