package com.example.onestep.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ChiTietGioHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gio_hang_id")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "chi_tiet_san_pham_id")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
