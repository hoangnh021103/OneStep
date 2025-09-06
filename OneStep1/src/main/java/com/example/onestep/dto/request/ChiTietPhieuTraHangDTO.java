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

    @NotNull(message = "ID phiếu trả hàng không được để trống")
    private Integer phieuTraHangId;

    @NotNull(message = "ID chi tiết sản phẩm không được để trống")
    private Integer chiTietSanPhamId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Giá trị không được để trống")
    @DecimalMin(value = "0.0", message = "Giá trị phải lớn hơn hoặc bằng 0")
    private Double giaTri;

    @Size(max = 1000, message = "Ghi chú tối đa 1000 ký tự")
    private String ghiChu;

    private LocalDate ngayCapNhat;

    @Size(max = 255, message = "Người tạo tối đa 255 ký tự")
    private String nguoiTao;

    @Size(max = 255, message = "Người cập nhật tối đa 255 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}
