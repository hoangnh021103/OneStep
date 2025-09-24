package com.example.onestep.controller;

import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietGioHangService;
import com.example.onestep.service.DonHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/don-hang")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<DonHangResponse>> getAll() {
        return ResponseEntity.ok(donHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<DonHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(donHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<DonHangResponse> add(@RequestBody @Valid DonHangDTO dto) {
        DonHangResponse response = donHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid DonHangDTO dto) {
        try {
            System.out.println("=== DEBUG: Cập nhật đơn hàng ID: " + id + " ===");
            System.out.println("Trạng thái mới: " + dto.getTrangThai());
            
            // Lấy đơn hàng hiện tại để kiểm tra trạng thái
            Optional<DonHangResponse> currentOrder = donHangService.getById(id);
            if (currentOrder.isEmpty()) {
                System.out.println("❌ Không tìm thấy đơn hàng với ID: " + id);
                return ResponseEntity.notFound().build();
            }

            DonHangResponse current = currentOrder.get();
            Integer currentStatus = current.getTrangThai();
            Integer newStatus = dto.getTrangThai();
            
            System.out.println("Trạng thái hiện tại: " + currentStatus);
            System.out.println("Trạng thái mới: " + newStatus);

            // Validation: Kiểm tra quy tắc chuyển đổi trạng thái nếu có thay đổi trạng thái
            // Đảm bảo cả hai đều là Integer để so sánh chính xác
            Integer effectiveCurrentStatus = currentStatus != null ? currentStatus : 1;
            Integer effectiveNewStatus = newStatus;
            
            System.out.println("🔄 Kiểm tra quy tắc chuyển đổi trạng thái...");
            System.out.println("🔍 So sánh: currentStatus=" + currentStatus + " (" + (currentStatus != null ? currentStatus.getClass().getSimpleName() : "null") + 
                             "), newStatus=" + newStatus + " (" + (newStatus != null ? newStatus.getClass().getSimpleName() : "null") + ")");
            System.out.println("🔍 Effective: currentStatus=" + effectiveCurrentStatus + ", newStatus=" + effectiveNewStatus);
            System.out.println("🔍 So sánh effective: " + !effectiveCurrentStatus.equals(effectiveNewStatus));
            
            if (newStatus != null && !effectiveCurrentStatus.equals(effectiveNewStatus)) {
                boolean isValidTransition = isValidStatusTransition(effectiveCurrentStatus, effectiveNewStatus);
                System.out.println("Kết quả validation: " + isValidTransition);
                
                if (!isValidTransition) {
                    System.out.println("❌ Chuyển đổi trạng thái không hợp lệ: " + effectiveCurrentStatus + " -> " + effectiveNewStatus);
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("success", false);
                    errorResponse.put("message", "Không thể chuyển đổi trạng thái từ " + getStatusName(effectiveCurrentStatus) + " sang " + getStatusName(effectiveNewStatus) + ". Vui lòng kiểm tra quy tắc chuyển đổi trạng thái.");
                    errorResponse.put("currentStatus", effectiveCurrentStatus);
                    errorResponse.put("newStatus", effectiveNewStatus);
                    return ResponseEntity.badRequest().body(errorResponse);
                }
            } else {
                System.out.println("🔄 Không có thay đổi trạng thái hoặc newStatus null");
                System.out.println("🔍 newStatus: " + newStatus);
                System.out.println("🔍 currentStatus: " + currentStatus);
                System.out.println("🔍 effectiveCurrentStatus: " + effectiveCurrentStatus);
                System.out.println("🔍 effectiveNewStatus: " + effectiveNewStatus);
                System.out.println("🔍 So sánh effective: " + effectiveCurrentStatus.equals(effectiveNewStatus));
            }

            DonHangResponse updated = donHangService.update(id, dto);
            if (updated == null) {
                System.out.println("❌ Cập nhật đơn hàng thất bại");
                return ResponseEntity.notFound().build();
            }
            
            System.out.println("✅ Cập nhật đơn hàng thành công");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi cập nhật đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        donHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<DonHangResponse> getById(@PathVariable Integer id) {
        Optional<DonHangResponse> optional = donHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 7. Cập nhật trạng thái đơn hàng
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam Integer trangThai) {
        try {
            System.out.println("=== DEBUG: Cập nhật trạng thái đơn hàng ID: " + id + " ===");
            System.out.println("Trạng thái mới: " + trangThai);
            
            // Lấy đơn hàng hiện tại
            Optional<DonHangResponse> currentOrder = donHangService.getById(id);
            if (currentOrder.isEmpty()) {
                System.out.println("❌ Không tìm thấy đơn hàng với ID: " + id);
                return ResponseEntity.notFound().build();
            }

            DonHangResponse current = currentOrder.get();
            Integer currentStatus = current.getTrangThai();
            
            System.out.println("Trạng thái hiện tại: " + currentStatus);

            // Validation: Kiểm tra quy tắc chuyển đổi trạng thái
            // Nếu currentStatus là null, coi như trạng thái ban đầu là "Chờ xác nhận" (1)
            Integer effectiveCurrentStatus = currentStatus != null ? currentStatus : 1;
            System.out.println("🔍 Effective current status: " + effectiveCurrentStatus);
            
            if (!isValidStatusTransition(effectiveCurrentStatus, trangThai)) {
                System.out.println("❌ Chuyển đổi trạng thái không hợp lệ: " + effectiveCurrentStatus + " -> " + trangThai);
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Không thể chuyển đổi trạng thái từ " + getStatusName(effectiveCurrentStatus) + " sang " + getStatusName(trangThai) + ". Vui lòng kiểm tra quy tắc chuyển đổi trạng thái.");
                errorResponse.put("currentStatus", effectiveCurrentStatus);
                errorResponse.put("newStatus", trangThai);
                return ResponseEntity.badRequest().body((Object) errorResponse);
            }

            // Tạo DTO với thông tin hiện tại và trạng thái mới
            DonHangDTO updateDto = DonHangDTO.builder()
                    .khachHangId(current.getKhachHangId())
                    .hoTen(current.getHoTen())
                    .soDienThoai(current.getSoDienThoai())
                    .email(current.getEmail())
                    .maDon(current.getMaDon())
                    .tongTienGoc(current.getTongTienGoc())
                    .tienShip(current.getTienShip())
                    .tienGiam(current.getTienGiam())
                    .tongTien(current.getTongTien())
                    .loaiDon(current.getLoaiDon())
                    .trangThai(trangThai) // Cập nhật trạng thái mới
                    .ghiChu(current.getGhiChu())
                    .nguoiTao(current.getNguoiTao())
                    .ngayCapNhat(java.time.LocalDate.now())
                    .ngayXacNhan(current.getNgayXacNhan())
                    .daXoa(current.getDaXoa())
                    .build();

            DonHangResponse updated = donHangService.update(id, updateDto);
            System.out.println("✅ Cập nhật trạng thái đơn hàng thành công");
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // 8. Test endpoint để kiểm tra validation logic
    @GetMapping("/test-validation/{currentStatus}/{newStatus}")
    public ResponseEntity<Map<String, Object>> testValidation(
            @PathVariable Integer currentStatus, 
            @PathVariable Integer newStatus) {
        System.out.println("=== TEST VALIDATION ===");
        System.out.println("Current Status: " + currentStatus);
        System.out.println("New Status: " + newStatus);
        
        boolean isValid = isValidStatusTransition(currentStatus, newStatus);
        
        Map<String, Object> result = new HashMap<>();
        result.put("currentStatus", currentStatus);
        result.put("newStatus", newStatus);
        result.put("isValid", isValid);
        result.put("message", isValid ? "Chuyển đổi hợp lệ" : "Chuyển đổi không hợp lệ");
        
        System.out.println("Result: " + result);
        return ResponseEntity.ok(result);
    }

    /**
     * Kiểm tra tính hợp lệ của việc chuyển đổi trạng thái đơn hàng
     * Quy tắc: Chỉ được chuyển đổi theo thứ tự tiến trình hoặc sang trạng thái hủy
     * 
     * @param currentStatus Trạng thái hiện tại
     * @param newStatus Trạng thái mới
     * @return true nếu hợp lệ, false nếu không hợp lệ
     */
    private boolean isValidStatusTransition(Integer currentStatus, Integer newStatus) {
        System.out.println("🔍 Kiểm tra chuyển đổi trạng thái: " + currentStatus + " -> " + newStatus);
        System.out.println("🔍 Kiểu dữ liệu currentStatus: " + (currentStatus != null ? currentStatus.getClass().getSimpleName() : "null"));
        System.out.println("🔍 Kiểu dữ liệu newStatus: " + (newStatus != null ? newStatus.getClass().getSimpleName() : "null"));
        
        // Kiểm tra null
        if (currentStatus == null || newStatus == null) {
            System.out.println("❌ Trạng thái null không hợp lệ");
            return false;
        }
        
        // Đảm bảo cả hai đều là Integer
        int current = currentStatus.intValue();
        int newStatusInt = newStatus.intValue();
        
        System.out.println("🔍 Sau khi convert: current=" + current + ", newStatus=" + newStatusInt);
        
        // Trạng thái: 1=Chờ xác nhận, 2=Đã xác nhận, 3=Chờ giao, 4=Đang giao, 5=Hoàn thành, 6=Đã hủy
        
        // Luôn cho phép chuyển sang trạng thái hủy (6) từ bất kỳ trạng thái nào
        if (newStatusInt == 6) {
            System.out.println("✅ Cho phép chuyển sang trạng thái hủy");
            return true;
        }
        
        // Không cho phép chuyển từ trạng thái hủy sang trạng thái khác
        if (current == 6) {
            System.out.println("❌ Không thể chuyển từ trạng thái hủy");
            return false;
        }
        
        // Không cho phép chuyển từ trạng thái hoàn thành sang trạng thái khác
        if (current == 5) {
            System.out.println("❌ Không thể chuyển từ trạng thái hoàn thành");
            return false;
        }
        
        // Quy tắc chuyển đổi theo thứ tự tiến trình
        switch (current) {
            case 1: // Chờ xác nhận
                boolean validFromPending = newStatusInt == 2 || newStatusInt == 3 || newStatusInt == 6;
                System.out.println("Trạng thái Chờ xác nhận (1) -> " + newStatusInt + ": " + validFromPending);
                System.out.println("🔍 Chi tiết validation: newStatus==2? " + (newStatusInt == 2) + 
                                 ", newStatus==3? " + (newStatusInt == 3) + 
                                 ", newStatus==6? " + (newStatusInt == 6));
                return validFromPending; // Được chuyển sang Đã xác nhận, Chờ giao hoặc Hủy
            case 2: // Đã xác nhận
                boolean validFromConfirmed = newStatusInt == 3 || newStatusInt == 6;
                System.out.println("Trạng thái Đã xác nhận (2) -> " + newStatusInt + ": " + validFromConfirmed);
                return validFromConfirmed; // Chỉ được chuyển sang Chờ giao hoặc Hủy
            case 3: // Chờ giao
                boolean validFromShipping = newStatusInt == 4 || newStatusInt == 6;
                System.out.println("Trạng thái Chờ giao (3) -> " + newStatusInt + ": " + validFromShipping);
                return validFromShipping; // Chỉ được chuyển sang Đang giao hoặc Hủy
            case 4: // Đang giao
                boolean validFromDelivering = newStatusInt == 5 || newStatusInt == 6;
                System.out.println("Trạng thái Đang giao (4) -> " + newStatusInt + ": " + validFromDelivering);
                return validFromDelivering; // Chỉ được chuyển sang Hoàn thành hoặc Hủy
            default:
                System.out.println("❌ Trạng thái không hợp lệ: " + current);
                return false;
        }
    }
    
    /**
     * Lấy tên trạng thái từ mã số
     */
    private String getStatusName(Integer status) {
        if (status == null) return "Không xác định";
        switch (status) {
            case 1: return "Chờ xác nhận";
            case 2: return "Đã xác nhận";
            case 3: return "Chờ giao";
            case 4: return "Đang giao";
            case 5: return "Hoàn thành";
            case 6: return "Đã hủy";
            default: return "Không xác định";
        }
    }
}
