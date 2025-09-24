package com.example.onestep.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChiTietGioHangDTO {

    private Integer id;


    private Integer gioHangId;


    private Integer chiTietSanPhamId;


    private Integer soLuong;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
