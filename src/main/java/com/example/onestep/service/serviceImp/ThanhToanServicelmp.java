package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThanhToanDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThanhToanResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.entity.ThanhToan;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.ThanhToanRepository;
import com.example.onestep.service.ThanhToanService;
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
public class ThanhToanServicelmp implements ThanhToanService {
    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ThanhToanResponse> getAll() {
        return thanhToanRepository.findAll().stream()
                .map(tt -> modelMapper.map(tt, ThanhToanResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ThanhToanResponse> phanTrang(Pageable pageable) {
        Page<ThanhToan> page = thanhToanRepository.findAll(pageable);
        List<ThanhToanResponse> dtoList = page.getContent().stream()
                .map(tt -> modelMapper.map(tt, ThanhToanResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ThanhToanResponse add(ThanhToanDTO dto) {
        ThanhToan entity = modelMapper.map(dto, ThanhToan.class);
        entity.setNgayCapNhat(LocalDate.now());
        ThanhToan saved = thanhToanRepository.save(entity);
        return modelMapper.map(saved, ThanhToanResponse.class);
    }

    @Override
    public ThanhToanResponse update(Integer id, ThanhToanDTO dto) {
        Optional<ThanhToan> optional = thanhToanRepository.findById(id);
        if (optional.isEmpty()) return null;

        ThanhToan entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ThanhToan updated = thanhToanRepository.save(entity);
        return modelMapper.map(updated, ThanhToanResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (thanhToanRepository.existsById(id)) {
            thanhToanRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ThanhToanResponse> getById(Integer id) {
        return thanhToanRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ThanhToanResponse.class));
    }
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public ThanhToanResponse chonKhachHang(Integer hoaDonId, Integer khachHangId) {
        ThanhToan hoaDon = thanhToanRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        KhachHang khachHang = khachHangRepository.findById(khachHangId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        // set khách hàng vào hóa đơn
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNgayCapNhat(LocalDate.now());

        ThanhToan updated = thanhToanRepository.save(hoaDon);

        // map sang response
        return modelMapper.map(updated, ThanhToanResponse.class);
    }
    @Override
    public ThanhToanResponse huyHoaDon(Integer id) {
        ThanhToan hoaDon = thanhToanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // set trạng thái = 2 (đã hủy)
        hoaDon.setTrangThai(2);
        hoaDon.setNgayCapNhat(LocalDate.now());

        ThanhToan updated = thanhToanRepository.save(hoaDon);
        return modelMapper.map(updated, ThanhToanResponse.class);
    }
    @Override
    public ThanhToanResponse applyDiscount(Integer orderId, String discountCode) {
        // Lấy hóa đơn theo id
        ThanhToan hoaDon = thanhToanRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        // Giả sử giảm giá cố định 10%
        float discount = 0.1f;
        float tongTienMoi = hoaDon.getTongTien() - hoaDon.getTongTien() * discount;

        hoaDon.setTongTien(tongTienMoi);  // giờ khớp kiểu float
        hoaDon.setNgayCapNhat(LocalDate.now());

        ThanhToan updated = thanhToanRepository.save(hoaDon);
        return modelMapper.map(updated, ThanhToanResponse.class);
    }
}
