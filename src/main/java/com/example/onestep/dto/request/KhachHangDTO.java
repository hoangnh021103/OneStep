package com.example.onestep.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import com.example.onestep.validator.UniqueEmail;
import com.example.onestep.validator.UniquePhoneNumber;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KhachHangDTO {

    private Integer id;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải có độ dài từ 2 đến 100 ký tự")
    @Pattern(regexp = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+$", message = "Họ và tên chỉ được chứa chữ cái và khoảng trắng")
    private String hoTen;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    @UniqueEmail(message = "Email đã được sử dụng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0[3|5|7|8|9])[0-9]{8}$", message = "Số điện thoại không đúng định dạng (VD: 09xxxxxxxx, 03xxxxxxxx)")
    @Size(min = 10, max = 10, message = "Số điện thoại phải có đúng 10 chữ số")
    @UniquePhoneNumber(message = "Số điện thoại đã được sử dụng")
    private String soDienThoai;

    @Pattern(regexp = "^(Nam|Nữ)$", message = "Giới tính phải là 'Nam' hoặc 'Nữ'", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String gioiTinh;

    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate ngaySinh;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    private String nguoiTao;

    private String nguoiCapNhat;

    private Integer daXoa;
}
