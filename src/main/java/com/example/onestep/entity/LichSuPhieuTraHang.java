package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "LichSuPhieuTraHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichSuPhieuTraHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phieu_tra_hang_id", nullable = false)
    private PhieuTraHang phieuTraHang;

    @Column(name = "trang_thai_hanh_dong")
    private Short trangThaiHanhDong;

    @Column(name = "ghi_chu", length = 1000)
    private String ghiChu;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao", length = 255)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 255)
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
