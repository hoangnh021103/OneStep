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

    @NotNull(message = "Thương hiệu không được để trống")
    private Integer thuongHieuId;

    @NotNull(message = "Kiểu dáng không được để trống")
    private Integer kieuDangId;

    @NotNull(message = "Kích cỡ không được để trống")
    private Integer kichCoId;

    @NotNull(message = "Sản phẩm không được để trống")
    private Integer sanPhamId;

    @NotNull(message = "Chất liệu không được để trống")
    private Integer chatLieuId;

    @NotNull(message = "Màu sắc không được để trống")
    private Integer mauSacId;

    @NotNull(message = "Hãng sản xuất không được để trống")
    private Integer hangSanXuatId;

    private String duongDanAnh;

    @NotNull(message = "Giá tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá tiền phải lớn hơn hoặc bằng 0")
    private Float giaTien;

    @NotNull(message = "Số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn phải lớn hơn hoặc bằng 0")
    private Integer soLuongTon;

    @NotNull(message = "Trạng thái không được để trống")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    private Integer trangThai;

    @DecimalMin(value = "0.0", inclusive = true, message = "Tiền giảm giá phải lớn hơn hoặc bằng 0")
    private Float tienGiamGia = 0f;

    private Integer daXoa = 0;

    @PastOrPresent(message = "Ngày cập nhật không được lớn hơn ngày hiện tại")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngayCapNhat = LocalDate.now();

    @NotBlank(message = "Người tạo không được để trống")
    private String nguoiTao;
    private String nguoiCapNhat;
}
