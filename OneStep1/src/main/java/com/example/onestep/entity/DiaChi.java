package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "DiaChi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @Column(name = "ma_quan", length = 50)
    private String maQuan;

    @Column(name = "ten_quan", length = 50)
    private String tenQuan;

    @Column(name = "ma_tinh", length = 50)
    private String maTinh;

    @Column(name = "ten_tinh", length = 50)
    private String tenTinh;

    @Column(name = "ma_phuong", length = 50)
    private String maPhuong;

    @Column(name = "ten_phuong", length = 50)
    private String tenPhuong;

    @Column(name = "la_mac_dinh")
    private Boolean laMacDinh;

    @Column(name = "thong_tin_them", length = 255)
    private String thongTinThem;

    @Column(name = "so_dien_thoai", length = 20)
    private String soDienThoai;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao", length = 255)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 255)
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
