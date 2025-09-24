package com.example.onestep.controller;

import com.example.onestep.dto.request.ChatLieuDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.service.ChatLieuService;
import com.example.onestep.service.ChiTietGioHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat-lieu")
public class ChatLieuController {
    @Autowired
    private ChatLieuService chatLieuService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<ChatLieuResponse>> getAll() {
        return ResponseEntity.ok(chatLieuService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<ChatLieuResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(chatLieuService.phanTrang(pageable));
    }

    // 3. Thêm sản phẩm
    @PostMapping("/add")
    public ResponseEntity<ChatLieuResponse> add(@RequestBody @Valid ChatLieuDTO dto) {
        ChatLieuResponse response = chatLieuService.add(dto);
        return ResponseEntity.ok(response);
    }

    // 4. Cập nhật sản phẩm theo id
    @PutMapping("/update/{id}")
    public ResponseEntity<ChatLieuResponse> update(@PathVariable Integer id, @RequestBody @Valid ChatLieuDTO dto) {
        ChatLieuResponse updated = chatLieuService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 5. Xoá sản phẩm theo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        chatLieuService.delete(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

    // 6. Lấy chi tiết sản phẩm theo id
    @GetMapping("/{id}")
    public ResponseEntity<ChatLieuResponse> getById(@PathVariable Integer id) {
        Optional<ChatLieuResponse> optional = chatLieuService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
