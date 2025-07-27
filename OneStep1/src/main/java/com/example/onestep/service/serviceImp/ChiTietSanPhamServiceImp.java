package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.*;
import com.example.onestep.repository.*;
import com.example.onestep.service.ChiTietSanPhamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImp implements ChiTietSanPhamService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private KieuDangRepository kieuDangRepository;

    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<ChiTietSanPhamResponse> getAll() {
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findAll();
        return list.stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietSanPhamResponse> phanTrang(Pageable pageable) {
        Page<ChiTietSanPham> page = chiTietSanPhamRepository.findAll(pageable);
        List<ChiTietSanPhamResponse> dtoList = page.getContent().stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietSanPhamResponse add(ChiTietSanPhamDTO dto) {
        ChiTietSanPham entity = modelMapper.map(dto, ChiTietSanPham.class);

        // Gán các quan hệ
        entity.setSanPham(sanPhamRepository.findById(dto.getSanPhamId()).orElse(null));
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieuId()).orElse(null));
        entity.setHangSanXuat(thuongHieuRepository.findById(dto.getHangSanXuatId()).orElse(null));
        entity.setKieuDang(kieuDangRepository.findById(dto.getKieuDangId()).orElse(null));
        entity.setKichCo(kichCoRepository.findById(dto.getKichCoId()).orElse(null));
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieuId()).orElse(null));
        entity.setMauSac(mauSacRepository.findById(dto.getMauSacId()).orElse(null));

        // Gán các thông tin hệ thống
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiTao());
        entity.setDaXoa(0);

        ChiTietSanPham saved = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(saved, ChiTietSanPhamResponse.class);
    }


    @Override
    public ChiTietSanPhamResponse update(Integer id, ChiTietSanPhamDTO dto) {
        Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy chi tiết sản phẩm với ID: " + id);
        }

        ChiTietSanPham entity = optional.get();

        // Cập nhật các trường đơn
        entity.setSoLuongTon(dto.getSoLuongTon());
        entity.setGiaTien(dto.getGiaTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setNgayCapNhat(LocalDate.now());

        // Cập nhật các quan hệ – có kiểm tra tồn tại
        entity.setSanPham(
                sanPhamRepository.findById(dto.getSanPhamId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + dto.getSanPhamId()))
        );

        entity.setThuongHieu(
                thuongHieuRepository.findById(dto.getThuongHieuId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu với ID: " + dto.getThuongHieuId()))
        );

        entity.setHangSanXuat(
                thuongHieuRepository.findById(dto.getHangSanXuatId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng sản xuất với ID: " + dto.getHangSanXuatId()))
        );

        entity.setKieuDang(
                kieuDangRepository.findById(dto.getKieuDangId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy kiểu dáng với ID: " + dto.getKieuDangId()))
        );

        entity.setKichCo(
                kichCoRepository.findById(dto.getKichCoId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ với ID: " + dto.getKichCoId()))
        );

        entity.setChatLieu(
                chatLieuRepository.findById(dto.getChatLieuId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu với ID: " + dto.getChatLieuId()))
        );

        entity.setMauSac(
                mauSacRepository.findById(dto.getMauSacId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + dto.getMauSacId()))
        );

        // Lưu và trả về kết quả
        ChiTietSanPham updated = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(updated, ChiTietSanPhamResponse.class);
    }



    @Override
    public void delete(Integer id) {
        Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(id);
        if (optional.isPresent()) {
            ChiTietSanPham entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM"); // Hoặc lấy từ người dùng hiện tại

            chiTietSanPhamRepository.save(entity);
        }
    }

    @Override
    public Optional<ChiTietSanPhamResponse> getById(Integer id) {
        return chiTietSanPhamRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, ChiTietSanPhamResponse.class));
    }

}
