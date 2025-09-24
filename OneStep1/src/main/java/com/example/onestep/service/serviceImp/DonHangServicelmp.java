package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.DonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.repository.DonHangRepository;
import com.example.onestep.service.DonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonHangServicelmp implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DonHangResponse> getAll() {
        return donHangRepository.findAllWithKhachHang().stream()
                .map(this::mapToDonHangResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DonHangResponse> phanTrang(Pageable pageable) {
        Page<DonHang> page = donHangRepository.findAllWithKhachHangPaged(pageable);
        List<DonHangResponse> dtoList = page.getContent().stream()
                .map(this::mapToDonHangResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public DonHangResponse add(DonHangDTO dto) {
        DonHang entity = modelMapper.map(dto, DonHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        DonHang saved = donHangRepository.save(entity);
        return modelMapper.map(saved, DonHangResponse.class);
    }

    @Override
    public DonHangResponse update(Integer id, DonHangDTO dto) {
        System.out.println("=== DEBUG: DonHangService.update() ===");
        System.out.println("ID: " + id);
        System.out.println("DTO trangThai: " + dto.getTrangThai());
        
        Optional<DonHang> optional = donHangRepository.findById(id);
        if (optional.isEmpty()) {
            System.out.println("❌ Không tìm thấy đơn hàng với ID: " + id);
            return null;
        }

        DonHang entity = optional.get();
        System.out.println("Entity trangThai trước khi update: " + entity.getTrangThai());
        
        // Cập nhật thủ công các trường thay vì dùng ModelMapper.map()
        // để tránh ghi đè các trường không mong muốn
        // Note: Không cập nhật khachHangId vì đây là quan hệ ManyToOne
        if (dto.getHoTen() != null) entity.setHoTen(dto.getHoTen());
        if (dto.getSoDienThoai() != null) entity.setSoDienThoai(dto.getSoDienThoai());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getTongTienGoc() != null) entity.setTongTienGoc(dto.getTongTienGoc());
        if (dto.getTienGiam() != null) entity.setTienGiam(dto.getTienGiam());
        if (dto.getTongTien() != null) entity.setTongTien(dto.getTongTien());
        if (dto.getTienShip() != null) entity.setTienShip(dto.getTienShip());
        if (dto.getLoaiDon() != null) entity.setLoaiDon(dto.getLoaiDon());
        if (dto.getGhiChu() != null) entity.setGhiChu(dto.getGhiChu());
        if (dto.getMaDon() != null) entity.setMaDon(dto.getMaDon());
        if (dto.getNguoiTao() != null) entity.setNguoiTao(dto.getNguoiTao());
        if (dto.getNguoiCapNhat() != null) entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        if (dto.getDaXoa() != null) entity.setDaXoa(dto.getDaXoa());
        
        // Cập nhật trạng thái - đây là trường quan trọng nhất
        if (dto.getTrangThai() != null) {
            System.out.println("🔄 Cập nhật trạng thái từ " + entity.getTrangThai() + " sang " + dto.getTrangThai());
            entity.setTrangThai(dto.getTrangThai());
        }
        
        // Cập nhật ngày xác nhận nếu có
        if (dto.getNgayXacNhan() != null) entity.setNgayXacNhan(dto.getNgayXacNhan());
        
        entity.setNgayCapNhat(LocalDate.now());
        
        System.out.println("Entity trangThai sau khi update: " + entity.getTrangThai());

        DonHang updated = donHangRepository.save(entity);
        System.out.println("✅ Lưu thành công, trangThai cuối cùng: " + updated.getTrangThai());
        
        return modelMapper.map(updated, DonHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (donHangRepository.existsById(id)) {
            donHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<DonHangResponse> getById(Integer id) {
        return donHangRepository.findById(id)
                .map(this::mapToDonHangResponse);
    }
    
    private DonHangResponse mapToDonHangResponse(DonHang donHang) {
        DonHangResponse response = modelMapper.map(donHang, DonHangResponse.class);
        
        // Nếu có khách hàng được chọn, sử dụng thông tin từ khách hàng
        if (donHang.getKhachHang() != null) {
            response.setHoTen(donHang.getKhachHang().getHoTen());
            response.setSoDienThoai(donHang.getKhachHang().getSoDienThoai());
            response.setEmail(donHang.getKhachHang().getEmail());
            response.setKhachHangId(donHang.getKhachHang().getId());
        }
        // Nếu không có khách hàng, sử dụng thông tin đã lưu trong đơn hàng
        // (hoTen, soDienThoai, email đã được set từ DonHangDTO)
        
        return response;
    }
}
