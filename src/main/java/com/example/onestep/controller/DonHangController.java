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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/don-hang")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5177"})
public class DonHangController {
    @Autowired
    private DonHangService donHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<DonHangResponse>> getAll() {
        // Lấy TẤT CẢ đơn hàng (cả ONLINE từ trangchu và OFFLINE từ admin)
        List<DonHangResponse> allOrders = donHangService.getAll();
        System.out.println("=== Admin lấy TẤT CẢ đơn hàng ===");
        System.out.println("Tổng số: " + allOrders.size());

        // Thống kê loại đơn
        long onlineCount = allOrders.stream()
            .filter(o -> o.getLoaiDon() != null && o.getLoaiDon() == 1)
            .count();
        long offlineCount = allOrders.stream()
            .filter(o -> o.getLoaiDon() != null && o.getLoaiDon() == 0)
            .count();

        System.out.println("Đơn ONLINE (từ trangchu): " + onlineCount);
        System.out.println("Đơn OFFLINE (từ admin): " + offlineCount);

        // Log chi tiết từng đơn hàng để debug
        allOrders.forEach(order -> {
            System.out.println("📄 Đơn hàng: " + order.getMaDon() +
                             " - Loại: " + (order.getLoaiDon() == 1 ? "ONLINE (trangchu)" : "OFFLINE (admin)") +
                             " - Trạng thái: " + order.getTrangThai() +
                             " - Tổng tiền: " + order.getTongTien());
        });

        return ResponseEntity.ok(allOrders);
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
            Integer loaiDon = current.getLoaiDon();
            
            System.out.println("Trạng thái hiện tại: " + currentStatus);
            System.out.println("Trạng thái mới: " + newStatus);
            System.out.println("Loại đơn: " + loaiDon);

            // ✅ VALIDATION MỚI: Ngăn chặn cập nhật trạng thái đơn hàng tại quầy
            if (loaiDon != null && loaiDon == 0) {
                System.out.println("🚫 NGĂN CHẶN: Không thể cập nhật đơn hàng tại quầy (loaiDon=0)");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Không thể cập nhật đơn hàng tại quầy. Đơn hàng tại quầy luôn có trạng thái 'Hoàn thành' và không thể thay đổi.");
                errorResponse.put("currentStatus", currentStatus);
                errorResponse.put("loaiDon", loaiDon);
                errorResponse.put("reason", "COUNTER_ORDER_IMMUTABLE");
                return ResponseEntity.badRequest().body(errorResponse);
            }

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
            Integer loaiDon = current.getLoaiDon();
            
            System.out.println("Trạng thái hiện tại: " + currentStatus);
            System.out.println("Loại đơn: " + loaiDon);

            // ✅ VALIDATION MỚI: Ngăn chặn cập nhật trạng thái đơn hàng tại quầy
            if (loaiDon != null && loaiDon == 0) {
                System.out.println("🚫 NGĂN CHẶN: Không thể cập nhật trạng thái đơn hàng tại quầy (loaiDon=0)");
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "Không thể cập nhật trạng thái đơn hàng tại quầy. Đơn hàng tại quầy luôn có trạng thái 'Hoàn thành' và không thể thay đổi.");
                errorResponse.put("currentStatus", currentStatus);
                errorResponse.put("loaiDon", loaiDon);
                errorResponse.put("reason", "COUNTER_ORDER_IMMUTABLE");
                return ResponseEntity.badRequest().body((Object) errorResponse);
            }

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
                    .ngayCapNhat(java.time.LocalDateTime.now())
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

    // ✅ ENDPOINT MỚI: Cập nhật tất cả đơn hàng tại quầy về trạng thái "Hoàn thành"
    @PostMapping("/fix-counter-orders-status")
    public ResponseEntity<Map<String, Object>> fixCounterOrdersStatus() {
        try {
            System.out.println("=== FIX COUNTER ORDERS STATUS ===");
            
            // Lấy tất cả đơn hàng tại quầy
            List<DonHangResponse> allOrders = donHangService.getAll();
            List<DonHangResponse> counterOrders = allOrders.stream()
                .filter(order -> order.getLoaiDon() != null && order.getLoaiDon() == 0)
                .filter(order -> order.getDaXoa() == 0)
                .collect(Collectors.toList());
            
            System.out.println("Tìm thấy " + counterOrders.size() + " đơn hàng tại quầy");
            
            int updatedCount = 0;
            int alreadyCompletedCount = 0;
            
            for (DonHangResponse order : counterOrders) {
                if (order.getTrangThai() != null && order.getTrangThai() != 5) {
                    // Cập nhật về trạng thái hoàn thành
                    DonHangDTO updateDto = DonHangDTO.builder()
                        .khachHangId(order.getKhachHangId())
                        .hoTen(order.getHoTen())
                        .soDienThoai(order.getSoDienThoai())
                        .email(order.getEmail())
                        .maDon(order.getMaDon())
                        .tongTienGoc(order.getTongTienGoc())
                        .tienShip(order.getTienShip())
                        .tienGiam(order.getTienGiam())
                        .tongTien(order.getTongTien())
                        .loaiDon(order.getLoaiDon())
                        .trangThai(5) // Force về hoàn thành
                        .ghiChu(order.getGhiChu())
                        .nguoiTao(order.getNguoiTao())
                        .nguoiCapNhat("system_auto_fix")
                        .ngayCapNhat(LocalDateTime.now())
                        .ngayXacNhan(order.getNgayXacNhan())
                        .daXoa(order.getDaXoa())
                        .build();
                    
                    DonHangResponse updated = donHangService.update(order.getId(), updateDto);
                    if (updated != null) {
                        updatedCount++;
                        System.out.println("✅ Cập nhật đơn hàng " + order.getMaDon() + " từ trạng thái " + order.getTrangThai() + " về 5 (Hoàn thành)");
                    }
                } else {
                    alreadyCompletedCount++;
                    System.out.println("ℹ️ Đơn hàng " + order.getMaDon() + " đã có trạng thái hoàn thành");
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Đã cập nhật trạng thái đơn hàng tại quầy thành công");
            result.put("totalCounterOrders", counterOrders.size());
            result.put("updatedOrders", updatedCount);
            result.put("alreadyCompletedOrders", alreadyCompletedCount);
            result.put("timestamp", LocalDateTime.now());
            
            System.out.println("=== KẾT QUẢ FIX ===");
            System.out.println("Tổng đơn hàng tại quầy: " + counterOrders.size());
            System.out.println("Đã cập nhật: " + updatedCount);
            System.out.println("Đã hoàn thành từ trước: " + alreadyCompletedCount);
            
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi fix trạng thái đơn hàng tại quầy: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "Lỗi khi cập nhật trạng thái đơn hàng tại quầy: " + e.getMessage());
            errorResult.put("timestamp", LocalDateTime.now());
            
            return ResponseEntity.badRequest().body(errorResult);
        }
    }
}
