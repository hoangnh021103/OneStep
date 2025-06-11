package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.entity.ChiTietSanPham;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.repository.SanPhamRepository;
import com.example.onestep.service.ChiTietSanPhamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    public ChiTietSanPhamResponse add(ChiTietSanPhamDTO req) {
        ChiTietSanPham entity = modelMapper.map(req, ChiTietSanPham.class);

        // Lấy entity SanPham theo id rồi set
        SanPham sanPham = sanPhamRepository.findById(req.getSanPhamId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id = " + req.getSanPhamId()));
        entity.setSanPham(sanPham);

        if (entity.getDaXoa() == null) {
            entity.setDaXoa(0);
        }
        if (entity.getNgayCapNhat() == null) {
            entity.setNgayCapNhat(java.time.LocalDate.now());
        }
        ChiTietSanPham saved = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(saved, ChiTietSanPhamResponse.class);
    }

    @Override
    public Optional<ChiTietSanPhamResponse> update(Integer id, ChiTietSanPhamDTO req) {
        return chiTietSanPhamRepository.findById(id).map(ctsp -> {
            ctsp.setThuongHieuId(req.getThuongHieuId());
            ctsp.setKieuDangId(req.getKieuDangId());
            ctsp.setKichCoId(req.getKichCoId());

            // Lấy entity SanPham theo id rồi set
            SanPham sanPham = sanPhamRepository.findById(req.getSanPhamId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với id = " + req.getSanPhamId()));
            ctsp.setSanPham(sanPham);
            ctsp.setChatLieuId(req.getChatLieuId());
            ctsp.setMauSacId(req.getMauSacId());
            ctsp.setHangSanXuatId(req.getHangSanXuatId());
            ctsp.setDuongDanAnh(req.getDuongDanAnh());
            ctsp.setGiaTien(req.getGiaTien());
            ctsp.setSoLuongTon(req.getSoLuongTon());
            ctsp.setTrangThai(req.getTrangThai());
            ctsp.setTienGiamGia(req.getTienGiamGia());
            ctsp.setDaXoa(req.getDaXoa());
            ctsp.setNgayCapNhat(req.getNgayCapNhat() != null ? req.getNgayCapNhat() : java.time.LocalDate.now());
            ctsp.setNguoiTao(req.getNguoiTao());
            ctsp.setNguoiCapNhat(req.getNguoiCapNhat());

            ChiTietSanPham updated = chiTietSanPhamRepository.save(ctsp);
            return modelMapper.map(updated, ChiTietSanPhamResponse.class);
        });
    }

    @Override
    public void delete(Integer id) {
        chiTietSanPhamRepository.deleteById(id);
    }

    @Override
    public List<ChiTietSanPhamResponse> search(String keyword, Integer trangThaiMin, Integer trangThaiMax) {
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findAll().stream()
                .filter(ctsp -> (keyword == null ||
                        (ctsp.getDuongDanAnh() != null && ctsp.getDuongDanAnh().toLowerCase().contains(keyword.toLowerCase()))))
                .filter(ctsp -> (trangThaiMin == null || ctsp.getTrangThai() >= trangThaiMin))
                .filter(ctsp -> (trangThaiMax == null || ctsp.getTrangThai() <= trangThaiMax))
                .collect(Collectors.toList());
        return list.stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
    }
}
