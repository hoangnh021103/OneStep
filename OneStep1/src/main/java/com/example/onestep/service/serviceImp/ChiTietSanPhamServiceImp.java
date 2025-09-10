package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
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
    private KichCoRepository kichCoRepository;


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
        entity.setKichCo(kichCoRepository.findById(dto.getKichCoId()).orElse(null));

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



        entity.setKichCo(
                kichCoRepository.findById(dto.getKichCoId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ với ID: " + dto.getKichCoId()))
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

    @Override
    public List<ChiTietSanPhamResponse> getBySanPhamId(Integer sanPhamId) {
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findBySanPhamId(sanPhamId);
        System.out.print("list"+list);
        return list.stream().map(c -> ChiTietSanPhamResponse.builder()
                .maChiTiet(c.getMaChiTiet())
                .sanPhamId(c.getSanPham().getMaSanPham())
                .kichCoId(c.getKichCo().getId())
                .mauSacId(c.getMauSac().getId())
                .duongDanAnh(c.getDuongDanAnh())
                .giaTien(c.getGiaTien())
                .soLuongTon(c.getSoLuongTon())
                .trangThai(c.getTrangThai())
                .tienGiamGia(c.getTienGiamGia())
                .daXoa(c.getDaXoa())
                .ngayCapNhat(c.getNgayCapNhat())
                .nguoiTao(c.getNguoiTao())
                .nguoiCapNhat(c.getNguoiCapNhat())
                .build()).toList();
    }



}
