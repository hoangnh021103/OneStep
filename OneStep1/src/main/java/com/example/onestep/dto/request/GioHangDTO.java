package com.example.onestep.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GioHangDTO {

    private Integer id;

    @NotNull(message = "Khách hàng không được để trống")
    private Integer khachHangId;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
