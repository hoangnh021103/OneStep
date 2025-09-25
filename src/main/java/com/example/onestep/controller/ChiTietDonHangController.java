package com.example.onestep.controller;

import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChiTietDonHangService;
import com.example.onestep.service.ChiTietGioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chi-tiet-don-hang")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5177"})
public class ChiTietDonHangController {
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChiTietDonHangResponse>> getAll() {
        return ResponseEntity.ok(chiTietDonHangService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChiTietDonHangResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chiTietDonHangService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ChiTietDonHangResponse> add(@RequestBody @Valid ChiTietDonHangDTO dto) {
        ChiTietDonHangResponse response = chiTietDonHangService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ChiTietDonHangResponse> update(@PathVariable Integer id, @RequestBody @Valid ChiTietDonHangDTO dto) {
        ChiTietDonHangResponse updated = chiTietDonHangService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chiTietDonHangService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ChiTietDonHangResponse> getById(@PathVariable Integer id) {
        Optional<ChiTietDonHangResponse> optional = chiTietDonHangService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // API lấy chi tiết đơn hàng với đầy đủ thông tin sản phẩm
    @GetMapping("/don-hang/{donHangId}")
    public ResponseEntity<Map<String, Object>> getByDonHangIdWithProductDetails(@PathVariable Integer donHangId) {
        try {
            System.out.println("🔍 [Controller] Nhận request cho đơn hàng ID: " + donHangId);
            
            List<Map<String, Object>> details = chiTietDonHangService.getByDonHangIdWithProductDetails(donHangId);
            
            System.out.println("📦 [Controller] Trả về " + details.size() + " chi tiết sản phẩm");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", details);
            response.put("count", details.size());
            response.put("message", details.isEmpty() ? "Không có sản phẩm nào trong đơn hàng này" : "Lấy thông tin thành công");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("❌ [Controller] Lỗi khi lấy chi tiết đơn hàng: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("data", new ArrayList<>());
            errorResponse.put("count", 0);
            errorResponse.put("message", "Có lỗi khi lấy chi tiết đơn hàng: " + e.getMessage());
            errorResponse.put("error", e.getClass().getSimpleName());
            
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
