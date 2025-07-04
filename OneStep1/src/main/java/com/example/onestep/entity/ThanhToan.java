package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "ThanhToan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Quan hệ 1-1 với đơn hàng
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id", nullable = false, unique = true)
    private DonHang donHang;

    // Quan hệ N-1 với phương thức thanh toán
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phuong_thuc_id")
    private PhuongThucThanhToan phuongThuc;

    @Column(name = "ma_giao_dich", length = 50)
    private String maGiaoDich;

    @Column(name = "tong_tien")
    private Float tongTien;

    @Column(name = "mo_ta", length = 1000)
    private String moTa;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao", length = 255)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 255)
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
