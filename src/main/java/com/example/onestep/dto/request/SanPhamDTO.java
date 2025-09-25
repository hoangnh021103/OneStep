package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SanPhamDTO {

    private Integer maSanPham;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm tối đa 255 ký tự")
    private String tenSanPham;

    @Size(max = 50, message = "Mã code tối đa 50 ký tự")
    private String maCode;

    @Size(max = 2000, message = "Mô tả tối đa 2000 ký tự")
    private String moTa;

    // Ảnh chỉ bắt buộc khi thêm; controller/service có thể kiểm tra rỗng khi cần
    private MultipartFile duongDanAnh;

    @NotNull(message = "Trạng thái không được null")
    @Min(value = 0, message = "Trạng thái không hợp lệ")
    private Integer trangThai;

    @NotNull(message = "Thương hiệu là bắt buộc")
    @Positive(message = "Thương hiệu không hợp lệ")
    private Integer thuongHieuId;

    @NotNull(message = "Chất liệu là bắt buộc")
    @Positive(message = "Chất liệu không hợp lệ")
    private Integer chatLieuId;  // SỬA: Đổi từ chatLieuIdv

    @NotNull(message = "Đế giày là bắt buộc")
    @Positive(message = "Đế giày không hợp lệ")
    private Integer deGiayId;

    private LocalDate ngayCapNhat;

    @Size(max = 200, message = "Người tạo tối đa 200 ký tự")
    private String nguoiTao;

    @Size(max = 200, message = "Người cập nhật tối đa 200 ký tự")
    private String nguoiCapNhat;

    private Integer daXoa;
}