package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChiTietSanPhamDTO {

    private Integer maChiTiet;

    @NotNull(message = "Thương hiệu bắt buộc")
    @Positive(message = "Thương hiệu không hợp lệ")
    private Integer thuongHieuId;

    @Positive(message = "Kiểu dáng không hợp lệ")
    private Integer kieuDangId;

    @NotNull(message = "Kích cỡ bắt buộc")
    @Positive(message = "Kích cỡ không hợp lệ")
    private Integer kichCoId;

    @NotNull(message = "Sản phẩm bắt buộc")
    @Positive(message = "Sản phẩm không hợp lệ")
    private Integer sanPhamId;

    @NotNull(message = "Chất liệu bắt buộc")
    @Positive(message = "Chất liệu không hợp lệ")
    private Integer chatLieuId;

    @NotNull(message = "Màu sắc bắt buộc")
    @Positive(message = "Màu sắc không hợp lệ")
    private Integer mauSacId;

    @Positive(message = "Hãng sản xuất không hợp lệ")
    private Integer hangSanXuatId;

    @NotNull(message = "Giá tiền bắt buộc")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá tiền phải > 0")
    private Float giaTien;

    @NotNull(message = "Số lượng tồn bắt buộc")
    @Min(value = 0, message = "Số lượng tồn không âm")
    private Integer soLuongTon;

    @NotNull(message = "Trạng thái không được null")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    private Integer trangThai;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tiền giảm giá không âm")
    private Float tienGiamGia = 0f;

    private Integer daXoa = 0;

    private LocalDate ngayCapNhat = LocalDate.now();

    @Size(max = 200, message = "Người tạo tối đa 200 ký tự")
    private String nguoiTao;
    @Size(max = 200, message = "Người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;
}
