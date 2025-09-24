package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietGioHang;
import com.example.onestep.entity.ChiTietSanPham;
import com.example.onestep.entity.GioHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietGioHangRepository;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.repository.GioHangRepository;
import com.example.onestep.service.ChiTietGioHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImp implements ChiTietGioHangService {

    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietGioHangResponse> getAll() {
        return chiTietGioHangRepository.findAll().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietGioHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietGioHang> page = chiTietGioHangRepository.findAll(pageable);
        List<ChiTietGioHangResponse> dtoList = page.getContent().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietGioHangResponse add(ChiTietGioHangDTO dto) {
        ChiTietGioHang entity = modelMapper.map(dto, ChiTietGioHang.class);

        // Gán quan hệ
        entity.setGioHang(gioHangRepository.findById(dto.getGioHangId()).orElse(null));
        entity.setChiTietSanPham(chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId()).orElse(null));

        // Gán thông tin hệ thống
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiTao());
        entity.setDaXoa(0);

        ChiTietGioHang saved = chiTietGioHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietGioHangResponse.class);
    }

    @Override
    public ChiTietGioHangResponse update(Integer id, ChiTietGioHangDTO dto) {
        // Tìm chi tiết giỏ hàng hiện có
        ChiTietGioHang existing = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết giỏ hàng có id: " + id));

        // Tìm giỏ hàng từ ID
        GioHang gioHang = gioHangRepository.findById(dto.getGioHangId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        // Tìm chi tiết sản phẩm từ ID
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết sản phẩm"));

        // Cập nhật thông tin
        existing.setGioHang(gioHang);
        existing.setChiTietSanPham(chiTietSanPham);
        existing.setSoLuong(dto.getSoLuong());
        existing.setNguoiCapNhat(dto.getNguoiCapNhat());
        existing.setNgayCapNhat(LocalDate.now());
        existing.setDaXoa(dto.getDaXoa());

        // Lưu lại
        ChiTietGioHang updated = chiTietGioHangRepository.save(existing);

        // Trả về response
        return modelMapper.map(updated, ChiTietGioHangResponse.class);
    }




    @Override
    public void delete(Integer id) {
        Optional<ChiTietGioHang> optional = chiTietGioHangRepository.findById(id);
        if (optional.isPresent()) {
            ChiTietGioHang entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM"); // hoặc lấy từ user đang đăng nhập

            chiTietGioHangRepository.save(entity);
        }
    }

    @Override
    public Optional<ChiTietGioHangResponse> getById(Integer id) {
        return chiTietGioHangRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, ChiTietGioHangResponse.class));
    }

}
