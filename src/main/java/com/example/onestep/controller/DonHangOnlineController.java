package com.example.onestep.controller;

import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.service.DonHangService;
import com.example.onestep.service.ChiTietDonHangService;
import com.example.onestep.service.ChiTietSanPhamService;
import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/don-hang-online")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5177"})
public class DonHangOnlineController {

    @Autowired
    private DonHangService donHangService;
    
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    
    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    /**
     * API tạo đơn hàng từ trang chủ (customer)
     * @param dto - Thông tin đơn hàng từ trang chủ
     * @return ResponseEntity chứa đơn hàng vừa tạo
     */
    @PostMapping("/tao-don-hang")
    public ResponseEntity<?> taoDonHangOnline(@RequestBody @Valid DonHangOnlineDTO dto) {
        try {
            System.out.println("=== Tạo đơn hàng online từ trang chủ ===");
            System.out.println("📥 Dữ liệu nhận được: " + dto.toString());
            System.out.println("📥 Chi tiết đơn hàng: " + (dto.getChiTietDonHang() != null ? dto.getChiTietDonHang().size() + " items" : "null"));
            
            // Log từng trường để debug
            System.out.println("🔍 HoTen: " + dto.getHoTen());
            System.out.println("🔍 SoDienThoai: " + dto.getSoDienThoai());
            System.out.println("🔍 Email: " + dto.getEmail());
            System.out.println("🔍 TongTien: " + dto.getTongTien());
            System.out.println("🔍 TongTienGoc: " + dto.getTongTienGoc());
            System.out.println("🔍 TienShip: " + dto.getTienShip());
            System.out.println("🔍 TienGiam: " + dto.getTienGiam());

            // Validation cơ bản
            if (dto.getHoTen() == null || dto.getHoTen().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Tên khách hàng không được để trống"
                ));
            }

            if (dto.getSoDienThoai() == null || dto.getSoDienThoai().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Số điện thoại không được để trống"
                ));
            }

            if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Email không được để trống"
                ));
            }

            if (dto.getTongTien() == null || dto.getTongTien() <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Tổng tiền phải lớn hơn 0"
                ));
            }
            
            // Validation thêm cho các trường số
            if (dto.getTongTienGoc() == null) {
                dto.setTongTienGoc(0.0f);
            }
            if (dto.getTienShip() == null) {
                dto.setTienShip(0.0f);
            }
            if (dto.getTienGiam() == null) {
                dto.setTienGiam(0.0f);
            }
            
            // Debug tiền giảm
            System.out.println("🔍 TienGiam sau validation: " + dto.getTienGiam());

            // Tạo đơn hàng với loaiDon = 1 (ONLINE)
            // Sử dụng mã đơn từ frontend nếu có, hoặc tạo mới
            String maDon = dto.getMaDon();
            if (maDon == null || maDon.isEmpty()) {
                // Tạo mã đơn ngắn hơn để tránh vượt quá 20 ký tự
                maDon = "ORD" + String.valueOf(System.currentTimeMillis()).substring(7); // Lấy 6 số cuối
            }
            
            // Đảm bảo mã đơn không quá 20 ký tự
            if (maDon.length() > 20) {
                maDon = maDon.substring(0, 20);
            }

            System.out.println("🔄 Tạo đơn hàng với mã: " + maDon);

            DonHangDTO donHangDTO = DonHangDTO.builder()
                    .khachHangId(dto.getKhachHangId())
                    .hoTen(dto.getHoTen())
                    .soDienThoai(dto.getSoDienThoai())
                    .email(dto.getEmail())
                    .maDon(maDon) // Sử dụng mã đơn thống nhất
                    .tongTienGoc(dto.getTongTienGoc())
                    .tienShip(dto.getTienShip())
                    .tienGiam(dto.getTienGiam())
                    .tongTien(dto.getTongTien())
                    .loaiDon(1) // 1: ONLINE (từ trang chủ)
                    .trangThai(1) // 1: Chờ xác nhận
                    .ghiChu(dto.getGhiChu())
                    .diaChiGiaoHang(dto.getDiaChiGiaoHang())
                    .nguoiTao(dto.getEmail()) // Người tạo là email khách hàng
                    .ngayCapNhat(LocalDateTime.now())
                    .daXoa(0)
                    .build();

            System.out.println("🔄 Gọi service để tạo đơn hàng...");
            DonHangResponse donHangResponse = donHangService.add(donHangDTO);
            System.out.println("✅ Đơn hàng đã được tạo: " + donHangResponse.getId() + " - " + donHangResponse.getMaDon());

            // Tạo chi tiết đơn hàng nếu có
            if (dto.getChiTietDonHang() != null && !dto.getChiTietDonHang().isEmpty()) {
                System.out.println("🔄 Tạo chi tiết đơn hàng: " + dto.getChiTietDonHang().size() + " sản phẩm");

                // ✅ BƯỚC 1: Kiểm tra tồn kho trước khi tạo đơn hàng
                for (ChiTietDonHangOnlineDTO chiTiet : dto.getChiTietDonHang()) {
                    if (chiTiet.getChiTietSanPhamId() == null || chiTiet.getChiTietSanPhamId() <= 0) {
                        System.out.println("⚠️ Bỏ qua chi tiết sản phẩm không hợp lệ: " + chiTiet.getChiTietSanPhamId());
                        continue;
                    }
                    
                    // Validate số lượng
                    if (chiTiet.getSoLuong() == null || chiTiet.getSoLuong() <= 0) {
                        chiTiet.setSoLuong(1);
                    }
                    
                    // Kiểm tra tồn kho
                    System.out.println("🔍 Kiểm tra tồn kho cho sản phẩm ID: " + chiTiet.getChiTietSanPhamId() + ", số lượng: " + chiTiet.getSoLuong());
                    boolean hasEnoughStock = chiTietSanPhamService.checkInventoryQuantity(
                        chiTiet.getChiTietSanPhamId(), chiTiet.getSoLuong());
                    
                    if (!hasEnoughStock) {
                        System.out.println("❌ Không đủ tồn kho cho sản phẩm ID: " + chiTiet.getChiTietSanPhamId());
                        return ResponseEntity.badRequest().body(Map.of(
                            "success", false,
                            "message", "Sản phẩm không đủ số lượng tồn kho để đặt hàng"
                        ));
                    }
                }

                // ✅ BƯỚC 2: Tạo chi tiết đơn hàng và cập nhật tồn kho
                for (ChiTietDonHangOnlineDTO chiTiet : dto.getChiTietDonHang()) {
                    if (chiTiet.getChiTietSanPhamId() == null || chiTiet.getChiTietSanPhamId() <= 0) {
                        continue;
                    }
                    
                    // Validate và set default values cho các trường số
                    if (chiTiet.getSoLuong() == null || chiTiet.getSoLuong() <= 0) {
                        chiTiet.setSoLuong(1);
                    }
                    if (chiTiet.getDonGia() == null || chiTiet.getDonGia() < 0) {
                        chiTiet.setDonGia(0.0f);
                    }
                    if (chiTiet.getThanhTien() == null || chiTiet.getThanhTien() < 0) {
                        chiTiet.setThanhTien(chiTiet.getDonGia() * chiTiet.getSoLuong());
                    }

                    ChiTietDonHangDTO chiTietDTO = ChiTietDonHangDTO.builder()
                            .donHangId(donHangResponse.getId())
                            .chiTietSanPhamId(chiTiet.getChiTietSanPhamId())
                            .soLuong(chiTiet.getSoLuong())
                            .donGia(chiTiet.getDonGia())
                            .tongTien(chiTiet.getThanhTien())
                            .trangThai(1)
                            .nguoiTao(dto.getEmail())
                            .ngayCapNhat(LocalDate.now())
                            .daXoa(0)
                            .build();

                    chiTietDonHangService.add(chiTietDTO);
                    System.out.println("✅ Đã tạo chi tiết sản phẩm: " + chiTiet.getChiTietSanPhamId());
                    
                    // ✅ BƯỚC 3: Cập nhật số lượng tồn kho (QUAN TRỌNG!)
                    boolean updateSuccess = chiTietSanPhamService.updateInventoryQuantity(
                        chiTiet.getChiTietSanPhamId(), chiTiet.getSoLuong());
                    
                    if (!updateSuccess) {
                        System.out.println("❌ Lỗi khi cập nhật tồn kho cho sản phẩm ID: " + chiTiet.getChiTietSanPhamId());
                        return ResponseEntity.badRequest().body(Map.of(
                            "success", false,
                            "message", "Lỗi khi cập nhật số lượng tồn kho"
                        ));
                    }
                    
                    System.out.println("✅ Đã cập nhật tồn kho cho sản phẩm ID: " + chiTiet.getChiTietSanPhamId());
                }
            }

            System.out.println("✅ Đơn hàng online đã được tạo thành công: " + donHangResponse.getMaDon());

            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Đơn hàng đã được tạo thành công",
                "data", donHangResponse
            ));

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi tạo đơn hàng online: " + e.getMessage());
            System.err.println("❌ Exception type: " + e.getClass().getSimpleName());
            e.printStackTrace();
            
            // Trả về response với format JSON đúng
            Map<String, Object> errorResponse = Map.of(
                "success", false,
                "message", "Có lỗi xảy ra khi tạo đơn hàng: " + e.getMessage(),
                "error", e.getClass().getSimpleName()
            );
            
            System.out.println("📤 Error response: " + errorResponse);
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * API lấy danh sách đơn hàng của khách hàng (CHỈ lấy đơn hàng ONLINE từ trangchu)
     * @param khachHangId - ID khách hàng (có thể null để lấy tất cả đơn online)
     * @return Danh sách đơn hàng online của khách hàng
     */
    @GetMapping("/khach-hang/{khachHangId}")
    public ResponseEntity<?> getDonHangByKhachHang(@PathVariable Integer khachHangId) {
        try {
            // QUAN TRỌNG: Chỉ lấy đơn hàng ONLINE (loaiDon = 1)
            // Không lấy đơn hàng OFFLINE từ admin (loaiDon = 0)
            List<DonHangResponse> donHangs = donHangService.getByKhachHangId(khachHangId, 1); // loaiDon = 1 (ONLINE only)
            
            System.out.println("=== Lấy đơn hàng cho khách hàng " + khachHangId + " ===");
            System.out.println("Chỉ lấy đơn hàng ONLINE (loaiDon = 1)");
            System.out.println("Số lượng đơn hàng: " + donHangs.size());
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", donHangs,
                "message", "Chỉ hiển thị đơn hàng từ trangchu"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Có lỗi khi lấy danh sách đơn hàng: " + e.getMessage()
            ));
        }
    }
    
    /**
     * API lấy TẤT CẢ đơn hàng online (không phân biệt khách hàng)
     * Dùng cho trường hợp chưa có authentication
     * @return Danh sách tất cả đơn hàng online
     */
    @GetMapping("/tat-ca-online")
    public ResponseEntity<?> getAllDonHangOnline() {
        try {
            // Lấy tất cả đơn hàng ONLINE (loaiDon = 1), không lọc theo khachHangId
            List<DonHangResponse> donHangs = donHangService.getByKhachHangId(null, 1); // khachHangId = null, loaiDon = 1
            
            System.out.println("=== Lấy TẤT CẢ đơn hàng ONLINE ===");
            System.out.println("Số lượng: " + donHangs.size());
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "data", donHangs,
                "message", "Tất cả đơn hàng online từ trangchu"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Có lỗi khi lấy danh sách đơn hàng: " + e.getMessage()
            ));
        }
    }

    /**
     * API lấy chi tiết một đơn hàng
     * @param id - ID đơn hàng
     * @return Chi tiết đơn hàng
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getChiTietDonHang(@PathVariable Integer id) {
        try {
            Optional<DonHangResponse> donHang = donHangService.getById(id);
            
            if (donHang.isPresent()) {
                // Lấy thêm chi tiết sản phẩm trong đơn hàng
                Map<String, Object> response = Map.of(
                    "donHang", donHang.get(),
                    "chiTietDonHang", chiTietDonHangService.getByDonHangId(id)
                );
                
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", response
                ));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Có lỗi khi lấy chi tiết đơn hàng: " + e.getMessage()
            ));
        }
    }

    /**
     * API hủy đơn hàng (khách hàng chỉ có thể hủy khi đơn hàng ở trạng thái Chờ xác nhận)
     * @param id - ID đơn hàng
     * @return Kết quả hủy đơn hàng
     */
    @PutMapping("/{id}/huy")
    public ResponseEntity<?> huyDonHang(@PathVariable Integer id) {
        try {
            Optional<DonHangResponse> donHang = donHangService.getById(id);
            
            if (donHang.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            DonHangResponse current = donHang.get();
            
            // Chỉ cho phép hủy khi đơn hàng ở trạng thái Chờ xác nhận (1)
            if (current.getTrangThai() != 1) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Không thể hủy đơn hàng ở trạng thái này"
                ));
            }
            
            // ✅ BƯỚC 1: Khôi phục tồn kho trước khi hủy đơn hàng
            List<ChiTietDonHangResponse> chiTietList = chiTietDonHangService.getByDonHangId(id);
            System.out.println("🔄 Khôi phục tồn kho cho " + chiTietList.size() + " sản phẩm");
            
            for (ChiTietDonHangResponse chiTiet : chiTietList) {
                boolean restoreSuccess = chiTietSanPhamService.restoreInventoryQuantity(
                    chiTiet.getChiTietSanPhamId(), chiTiet.getSoLuong());
                
                if (!restoreSuccess) {
                    System.out.println("⚠️ Không thể khôi phục tồn kho cho sản phẩm ID: " + chiTiet.getChiTietSanPhamId());
                    // Vẫn tiếp tục hủy đơn hàng, chỉ log warning
                }
                
                System.out.println("✅ Đã khôi phục " + chiTiet.getSoLuong() + " sản phẩm ID: " + chiTiet.getChiTietSanPhamId());
            }
            
            // ✅ BƯỚC 2: Cập nhật trạng thái thành Đã hủy (6)
            DonHangDTO updateDTO = DonHangDTO.builder()
                    .trangThai(6) // Đã hủy
                    .ghiChu(current.getGhiChu() + " | Khách hàng đã hủy đơn")
                    .ngayCapNhat(LocalDateTime.now())
                    .build();
            
            DonHangResponse updatedDonHang = donHangService.update(id, updateDTO);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Đơn hàng đã được hủy thành công",
                "data", updatedDonHang
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "Có lỗi khi hủy đơn hàng: " + e.getMessage()
            ));
        }
    }

    // DTO classes
    public static class DonHangOnlineDTO {
        private Integer khachHangId;
        private String hoTen;
        private String soDienThoai;
        private String email;
        private String diaChiGiaoHang;
        private String maDon; // Thêm mã đơn từ frontend
        private Float tongTienGoc;
        private Float tienShip;
        private Float tienGiam;
        private Float tongTien;
        private String ghiChu;
        private List<ChiTietDonHangOnlineDTO> chiTietDonHang;

        // Getters and setters
        public Integer getKhachHangId() { return khachHangId; }
        public void setKhachHangId(Integer khachHangId) { this.khachHangId = khachHangId; }
        
        public String getHoTen() { return hoTen; }
        public void setHoTen(String hoTen) { this.hoTen = hoTen; }
        
        public String getSoDienThoai() { return soDienThoai; }
        public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getDiaChiGiaoHang() { return diaChiGiaoHang; }
        public void setDiaChiGiaoHang(String diaChiGiaoHang) { this.diaChiGiaoHang = diaChiGiaoHang; }
        
        public Float getTongTienGoc() { return tongTienGoc; }
        public void setTongTienGoc(Float tongTienGoc) { this.tongTienGoc = tongTienGoc; }
        
        public Float getTienShip() { return tienShip; }
        public void setTienShip(Float tienShip) { this.tienShip = tienShip; }
        
        public Float getTienGiam() { return tienGiam; }
        public void setTienGiam(Float tienGiam) { this.tienGiam = tienGiam; }
        
        public Float getTongTien() { return tongTien; }
        public void setTongTien(Float tongTien) { this.tongTien = tongTien; }
        
        public String getGhiChu() { return ghiChu; }
        public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
        
        public String getMaDon() { return maDon; }
        public void setMaDon(String maDon) { this.maDon = maDon; }
        
        public List<ChiTietDonHangOnlineDTO> getChiTietDonHang() { return chiTietDonHang; }
        public void setChiTietDonHang(List<ChiTietDonHangOnlineDTO> chiTietDonHang) { 
            this.chiTietDonHang = chiTietDonHang; 
        }
    }

    public static class ChiTietDonHangOnlineDTO {
        private Integer chiTietSanPhamId;
        private Integer soLuong;
        private Float donGia;
        private Float thanhTien;

        // Getters and setters
        public Integer getChiTietSanPhamId() { return chiTietSanPhamId; }
        public void setChiTietSanPhamId(Integer chiTietSanPhamId) { 
            this.chiTietSanPhamId = chiTietSanPhamId; 
        }
        
        public Integer getSoLuong() { return soLuong; }
        public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
        
        public Float getDonGia() { return donGia; }
        public void setDonGia(Float donGia) { this.donGia = donGia; }
        
        public Float getThanhTien() { return thanhTien; }
        public void setThanhTien(Float thanhTien) { this.thanhTien = thanhTien; }
    }
}
