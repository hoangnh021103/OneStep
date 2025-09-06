package com.example.onestep.controller;

import com.example.onestep.dto.request.DeGiayDTO;
import com.example.onestep.dto.response.DeGiayResponse;
import com.example.onestep.service.DeGiayService;
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
@RequestMapping("/de-giay")
public class DeGiayController {
    @Autowired
    private DeGiayService deGiayService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<DeGiayResponse>> getAll() {
        return ResponseEntity.ok(deGiayService.getAll());
    }

    @GetMapping("/phan-trang")
    public ResponseEntity<Page<DeGiayResponse>> phanTrang(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok(deGiayService.phanTrang(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<DeGiayResponse> add(@RequestBody @Valid DeGiayDTO dto) {
        DeGiayResponse response = deGiayService.add(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DeGiayResponse> update(@PathVariable Integer id, @RequestBody @Valid DeGiayDTO dto) {
        DeGiayResponse updated = deGiayService.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        deGiayService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeGiayResponse> getById(@PathVariable Integer id) {
        Optional<DeGiayResponse> optional = deGiayService.getById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
