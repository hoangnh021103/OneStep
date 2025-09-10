package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChiTietPhieuTraHangDTO {

    private Integer id;


    private Integer phieuTraHangId;


    private Integer chiTietSanPhamId;

    private Integer soLuong;


    private Double giaTri;

    private String ghiChu;

    private LocalDate ngayCapNhat;

    private String nguoiTao;


    private String nguoiCapNhat;

    private Integer daXoa;
}
