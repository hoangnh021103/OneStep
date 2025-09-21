package com.example.onestep.controller;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThanhToanDTO;
import com.example.onestep.dto.request.BanHangTaiQuayDTO;
import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.SanPhamBanHangResponse;
import com.example.onestep.dto.response.ThanhToanResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.SanPhamService;
import com.example.onestep.service.ThanhToanService;
import com.example.onestep.service.DonHangService;
import com.example.onestep.service.ChiTietDonHangService;
import com.example.onestep.service.ChiTietSanPhamService;
import com.example.onestep.service.KhachHangService;
import com.example.onestep.entity.ChiTietSanPham;
import com.example.onestep.dto.response.KhachHangResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/thanh-toan")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class ThanhToanController {
    private final SanPhamService sanPhamService;
    @Autowired
    private ThanhToanService thanhToanService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;
    @Autowired
    private KhachHangService khachHangService;

    public ThanhToanController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ThanhToanResponse>> getAll() {
        return ResponseEntity.ok(thanhToanService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ThanhToanResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(thanhToanService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ThanhToanResponse> add(@RequestBody @Valid ThanhToanDTO dto) {
        ThanhToanResponse response = thanhToanService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ThanhToanResponse> update(@PathVariable Integer id, @RequestBody @Valid ThanhToanDTO dto) {
        ThanhToanResponse updated = thanhToanService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        thanhToanService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết thanh toán theo id
    @GetMapping("/{id}")
    public ResponseEntity<ThanhToanResponse> getById(@PathVariable Integer id) {
        Optional<ThanhToanResponse> optional = thanhToanService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Thêm mới hóa đơn (hoặc thêm sản phẩm vào giỏ hàng)
    @PostMapping("/add-hoa-don")
    public ResponseEntity<ThanhToanResponse> addHoaDon(@RequestBody @Valid ThanhToanDTO dto) {
        return ResponseEntity.ok(thanhToanService.add(dto));
    }
    @PutMapping("/{hoaDonId}/chon-khach-hang/{khachHangId}")
    public ResponseEntity<ThanhToanResponse> chonKhachHang(
            @PathVariable Integer hoaDonId,
            @PathVariable Integer khachHangId) {
        return ResponseEntity.ok(thanhToanService.chonKhachHang(hoaDonId, khachHangId));
    }
    // 7. Hủy hóa đơn
    @PutMapping("/{hoaDonId}/huy")
    public ResponseEntity<ThanhToanResponse> huyHoaDon(@PathVariable Integer hoaDonId) {
        return ResponseEntity.ok(thanhToanService.huyHoaDon(hoaDonId));
    }
    @PutMapping("/{hoaDonId}/apply-discount")
    public ResponseEntity<ThanhToanResponse> applyDiscount(
            @PathVariable Integer hoaDonId,
            @RequestParam String code) {
        return ResponseEntity.ok(thanhToanService.applyDiscount(hoaDonId, code));
    }
    @GetMapping("/san-pham")
    public ResponseEntity<List<SanPhamBanHangResponse>> getDanhSachSanPham() {
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAllForBanHang();
        List<SanPhamBanHangResponse> responses = chiTietSanPhams.stream()
                .map(this::mapToSanPhamBanHangResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/san-pham/search")
    public ResponseEntity<List<SanPhamBanHangResponse>> timKiemSanPham(@RequestParam String keyword) {
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.searchForBanHang(keyword);
        List<SanPhamBanHangResponse> responses = chiTietSanPhams.stream()
                .map(this::mapToSanPhamBanHangResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    private SanPhamBanHangResponse mapToSanPhamBanHangResponse(ChiTietSanPham chiTiet) {
        return SanPhamBanHangResponse.builder()
                .id(chiTiet.getMaChiTiet())
                .chiTietSanPhamId(chiTiet.getMaChiTiet())
                .maSanPham(chiTiet.getSanPham().getMaSanPham())
                .maCode(chiTiet.getSanPham().getMaCode())
                .tenSanPham(chiTiet.getSanPham().getTenSanPham())
                .duongDanAnh(chiTiet.getDuongDanAnh() != null ? chiTiet.getDuongDanAnh() : chiTiet.getSanPham().getDuongDanAnh())
                .giaBan(chiTiet.getGiaTien())
                .soLuongTon(chiTiet.getSoLuongTon())
                .tenKichThuoc(chiTiet.getKichCo() != null ? chiTiet.getKichCo().getTen() : "Không xác định")
                .tenMauSac(chiTiet.getMauSac() != null ? chiTiet.getMauSac().getTen() : "Không xác định")
                .tenThuongHieu(chiTiet.getSanPham().getThuongHieu() != null ? chiTiet.getSanPham().getThuongHieu().getTen() : "Không xác định")
                .tenChatLieu(chiTiet.getSanPham().getChatLieu() != null ? chiTiet.getSanPham().getChatLieu().getTen() : "Không xác định")
                .trangThai(chiTiet.getTrangThai())
                .build();
    }

    @GetMapping("/khach-hang")
    public ResponseEntity<List<KhachHangResponse>> getDanhSachKhachHang() {
        List<KhachHangResponse> khachHangs = khachHangService.getAll();
        return ResponseEntity.ok(khachHangs);
    }

    @GetMapping("/khach-hang/search")
    public ResponseEntity<List<KhachHangResponse>> timKiemKhachHang(@RequestParam String keyword) {
        List<KhachHangResponse> allCustomers = khachHangService.getAll();
        List<KhachHangResponse> filteredCustomers = allCustomers.stream()
                .filter(customer -> 
                    (customer.getHoTen() != null && customer.getHoTen().toLowerCase().contains(keyword.toLowerCase())) ||
                    (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(keyword.toLowerCase())) ||
                    (customer.getSoDienThoai() != null && customer.getSoDienThoai().toLowerCase().contains(keyword.toLowerCase()))
                )
                .toList();
        return ResponseEntity.ok(filteredCustomers);
    }

    @PostMapping("/ban-hang-tai-quay")
    public ResponseEntity<?> banHangTaiQuay(@RequestBody @Valid BanHangTaiQuayDTO dto) {
        try {
            // 1. Tạo đơn hàng
            DonHangDTO donHangDTO = DonHangDTO.builder()
                    .khachHangId(dto.getKhachHangId())
                    .hoTen(dto.getKhachHangId() != null ? null : "Khách lẻ")
                    .maDon(dto.getMaHoaDon())
                    .tongTienGoc(dto.getTongTien() - (dto.getPhiGiaoHang() != null ? dto.getPhiGiaoHang() : 0))
                    .tienShip(dto.getPhiGiaoHang() != null ? dto.getPhiGiaoHang() : 0f)
                    .tienGiam(0f) // Sẽ tính sau khi áp dụng voucher
                    .tongTien(dto.getTongTien())
                    .loaiDon(1) // 1: Bán tại quầy
                    .ghiChu(dto.getGhiChu())
                    .nguoiTao(dto.getNguoiTao() != null ? dto.getNguoiTao() : "Admin")
                    .ngayCapNhat(java.time.LocalDate.now())
                    .daXoa(0)
                    .build();

            DonHangResponse donHangResponse = donHangService.add(donHangDTO);

            // 2. Tạo chi tiết đơn hàng
            for (BanHangTaiQuayDTO.ChiTietDonHangBanHangDTO chiTiet : dto.getChiTietDonHang()) {
                ChiTietDonHangDTO chiTietDTO = ChiTietDonHangDTO.builder()
                        .donHangId(donHangResponse.getId())
                        .chiTietSanPhamId(chiTiet.getSanPhamId()) // Sử dụng chiTietSanPhamId
                        .soLuong(chiTiet.getSoLuong())
                        .donGia(chiTiet.getDonGia())
                        .tongTien(chiTiet.getThanhTien())
                        .trangThai(1)
                        .nguoiTao(dto.getNguoiTao() != null ? dto.getNguoiTao() : "Admin")
                        .ngayCapNhat(java.time.LocalDate.now())
                        .daXoa(0)
                        .build();

                chiTietDonHangService.add(chiTietDTO);
            }

            // 3. Tạo thông tin thanh toán
            ThanhToanDTO thanhToanDTO = ThanhToanDTO.builder()
                    .donHangId(donHangResponse.getId())
                    .phuongThucId(dto.getPhuongThucThanhToan().equals("cash") ? 1 : 2) // 1: Tiền mặt, 2: Chuyển khoản
                    .maGiaoDich(dto.getMaHoaDon())
                    .tongTien(dto.getTongTien())
                    .moTa("Thanh toán bán hàng tại quầy")
                    .trangThai(1) // 1: Đã thanh toán
                    .nguoiTao(dto.getNguoiTao() != null ? dto.getNguoiTao() : "Admin")
                    .ngayCapNhat(java.time.LocalDate.now())
                    .daXoa(0)
                    .build();

            ThanhToanResponse thanhToanResponse = thanhToanService.add(thanhToanDTO);

            return ResponseEntity.ok().body(Map.of(
                    "success", true,
                    "message", "Tạo đơn hàng thành công",
                    "donHang", donHangResponse,
                    "thanhToan", thanhToanResponse
            ));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Lỗi khi tạo đơn hàng: " + e.getMessage()
            ));
        }
    }
}
