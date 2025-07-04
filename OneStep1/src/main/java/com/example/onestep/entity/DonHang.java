package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "DonHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhan_vien_id")
    private NhanVien nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_chi_id")
    private DiaChi diaChi;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "tong_tien_goc")
    private Float tongTienGoc;

    @Column(name = "tien_giam")
    private Float tienGiam;

    @Column(name = "tong_tien")
    private Float tongTien;

    @Column(name = "tien_ship")
    private Float tienShip;

    @Column(name = "ngay_xac_nhan")
    private LocalDate ngayXacNhan;

    @Column(name = "ngay_du_kien")
    private LocalDate ngayDuKien;

    @Column(name = "ngay_nhan")
    private LocalDate ngayNhan;

    @Column(name = "loai_don")
    private Integer loaiDon;

    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    @Column(name = "ma_don", unique = true, length = 10, nullable = false)
    private String maDon;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
