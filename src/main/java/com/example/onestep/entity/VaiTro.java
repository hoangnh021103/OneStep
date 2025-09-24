package com.example.onestep.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "VaiTro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ten_vai_tro", nullable = false, length = 255)
    private String tenVaiTro;

    @Column(name = "ngay_tao")
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column(name = "ngay_cap_nhat")
    @Temporal(TemporalType.DATE)
    private Date ngayCapNhat;

    @Column(name = "nguoi_tao", length = 255)
    private String nguoiTao;

    @Column(name = "nguoi_cap_nhat", length = 255)
    private String nguoiCapNhat;

    @Column(name = "da_xoa")
    private Integer daXoa;
}
