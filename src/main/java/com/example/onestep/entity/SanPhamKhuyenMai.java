package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SanPhamKhuyenMai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamKhuyenMai {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id")
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khuyen_mai_id")
    private Voucher voucher;

    @Column(name = "ngay_cap_nhat")
    private java.time.LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat")
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
