package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AnhSanPham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnhSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    @Column(name = "duong_dan_anh")
    private String duongDanAnh;

    @Column(name = "ngay_cap_nhat")
    private java.time.LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
