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
    public ChiTietSanPhamResponse add(ChiTietSanPhamDTO req) {
        ChiTietSanPham entity = modelMapper.map(req, ChiTietSanPham.class);

        entity.setSanPham(sanPhamRepository.findById(req.getSanPhamId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm")));

        entity.setThuongHieu(thuongHieuRepository.findById(req.getThuongHieuId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu")));

        entity.setKieuDang(kieuDangRepository.findById(req.getKieuDangId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kiểu dáng")));

        entity.setKichCo(kichCoRepository.findById(req.getKichCoId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ")));

        entity.setChatLieu(chatLieuRepository.findById(req.getChatLieuId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu")));

        entity.setMauSac(mauSacRepository.findById(req.getMauSacId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc")));

        entity.setHangSanXuat(thuongHieuRepository.findById(req.getHangSanXuatId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng sản xuất")));

        if (entity.getDaXoa() == null) entity.setDaXoa(0);
        if (entity.getNgayCapNhat() == null) entity.setNgayCapNhat(java.time.LocalDate.now());

        ChiTietSanPham saved = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(saved, ChiTietSanPhamResponse.class);
    }

    @Override
    public Optional<ChiTietSanPhamResponse> update(Integer id, ChiTietSanPhamDTO req) {
        return chiTietSanPhamRepository.findById(id).map(ctsp -> {

            ctsp.setSanPham(sanPhamRepository.findById(req.getSanPhamId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm")));

            ctsp.setThuongHieu(thuongHieuRepository.findById(req.getThuongHieuId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy thương hiệu")));

            ctsp.setKieuDang(kieuDangRepository.findById(req.getKieuDangId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy kiểu dáng")));

            ctsp.setKichCo(kichCoRepository.findById(req.getKichCoId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ")));

            ctsp.setChatLieu(chatLieuRepository.findById(req.getChatLieuId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy chất liệu")));

            ctsp.setMauSac(mauSacRepository.findById(req.getMauSacId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc")));

            ctsp.setHangSanXuat(thuongHieuRepository.findById(req.getHangSanXuatId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng sản xuất")));

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
                        (ctsp.getDuongDanAnh() != null &&
                                ctsp.getDuongDanAnh().toLowerCase().contains(keyword.toLowerCase()))))
                .filter(ctsp -> (trangThaiMin == null || ctsp.getTrangThai() >= trangThaiMin))
                .filter(ctsp -> (trangThaiMax == null || ctsp.getTrangThai() <= trangThaiMax))
                .collect(Collectors.toList());

        return list.stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
    }
}
