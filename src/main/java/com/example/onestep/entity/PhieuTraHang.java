package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "PhieuTraHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhieuTraHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ma", nullable = false, unique = true, length = 10)
    private String ma;

    @Column(name = "gia_tri")
    private Float giaTri;

    @Column(name = "so_tien_phai_tra")
    private Float soTienPhaiTra;

    @Column(name = "thong_tin_thanh_toan", length = 255)
    private String thongTinThanhToan;

    @Column(name = "hinh_thuc_thanh_toan", length = 255)
    private String hinhThucThanhToan;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "nguoi_tao", length = 255)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 255)
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
