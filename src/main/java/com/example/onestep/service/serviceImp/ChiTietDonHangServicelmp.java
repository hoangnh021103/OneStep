package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.repository.DonHangRepository;
import com.example.onestep.service.ChiTietDonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangServicelmp implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietDonHangResponse> getAll() {
        return chiTietDonHangRepository.findAll().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietDonHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietDonHang> page = chiTietDonHangRepository.findAll(pageable);
        List<ChiTietDonHangResponse> dtoList = page.getContent().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietDonHangResponse add(ChiTietDonHangDTO dto) {
        ChiTietDonHang entity = modelMapper.map(dto, ChiTietDonHang.class);

        // Thiết lập thông tin mặc định khi thêm mới
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiTao()); // ban đầu người cập nhật cũng là người tạo
        entity.setDaXoa(0); // chưa xóa

        // Thiết lập quan hệ nếu cần
        entity.setDonHang(donHangRepository.findById(dto.getDonHangId()).orElse(null));
        entity.setChiTietSanPham(chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId()).orElse(null));

        ChiTietDonHang saved = chiTietDonHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietDonHangResponse.class);
    }

    @Override
    public ChiTietDonHangResponse update(Integer id, ChiTietDonHangDTO dto) {
        Optional<ChiTietDonHang> optional = chiTietDonHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChiTietDonHang entity = optional.get();

        // Không dùng modelMapper.map(dto, entity) vì có thể ghi đè cả ID và quan hệ
        // Cập nhật thủ công các trường được phép sửa
        entity.setSoLuong(dto.getSoLuong());
        entity.setDonGia(dto.getDonGia());
        entity.setTongTien(dto.getTongTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setDaXoa(dto.getDaXoa());

        // Chỉ cập nhật quan hệ nếu khác ID
        if (!entity.getDonHang().getId().equals(dto.getDonHangId())) {
            entity.setDonHang(donHangRepository.findById(dto.getDonHangId()).orElse(null));
        }

        if (!entity.getChiTietSanPham().getMaChiTiet().equals(dto.getChiTietSanPhamId())) {
            entity.setChiTietSanPham(chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId()).orElse(null));
        }

        ChiTietDonHang updated = chiTietDonHangRepository.save(entity);
        return modelMapper.map(updated, ChiTietDonHangResponse.class);
    }


    @Override
    public void delete(Integer id) {
        Optional<ChiTietDonHang> optional = chiTietDonHangRepository.findById(id);
        if (optional.isPresent()) {
            ChiTietDonHang entity = optional.get();
            entity.setDaXoa(1); // xóa mềm
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM"); // hoặc lấy từ user hiện tại

            chiTietDonHangRepository.save(entity);
        }
    }

    @Override
    public Optional<ChiTietDonHangResponse> getById(Integer id) {
        return chiTietDonHangRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0) // bỏ qua nếu đã xóa mềm
                .map(entity -> modelMapper.map(entity, ChiTietDonHangResponse.class));
    }

}
