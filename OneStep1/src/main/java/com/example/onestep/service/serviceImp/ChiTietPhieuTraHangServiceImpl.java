package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietPhieuTraHangDTO;
import com.example.onestep.dto.response.ChiTietPhieuTraHangResponse;
import com.example.onestep.entity.ChiTietPhieuTraHang;
import com.example.onestep.entity.PhieuTraHang;
import com.example.onestep.entity.ChiTietSanPham;
import com.example.onestep.repository.ChiTietPhieuTraHangRepository;
import com.example.onestep.repository.PhieuTraHangRepository;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.service.ChiTietPhieuTraHangService;
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
public class ChiTietPhieuTraHangServiceImpl implements ChiTietPhieuTraHangService {
    @Autowired
    private ChiTietPhieuTraHangRepository chiTietPhieuTraHangRepository;

    @Autowired
    private PhieuTraHangRepository phieuTraHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietPhieuTraHangResponse> getAll() {
        return chiTietPhieuTraHangRepository.findAll().stream()
                .filter(ctpth -> ctpth.getDaXoa() == 0)
                .map(ctpth -> modelMapper.map(ctpth, ChiTietPhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietPhieuTraHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietPhieuTraHang> page = chiTietPhieuTraHangRepository.findAll(pageable);
        List<ChiTietPhieuTraHangResponse> dtoList = page.getContent().stream()
                .filter(ctpth -> ctpth.getDaXoa() == 0)
                .map(ctpth -> modelMapper.map(ctpth, ChiTietPhieuTraHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietPhieuTraHangResponse add(ChiTietPhieuTraHangDTO dto) {
        ChiTietPhieuTraHang entity = modelMapper.map(dto, ChiTietPhieuTraHang.class);
        
        // Set PhieuTraHang entity
        if (dto.getPhieuTraHangId() != null) {
            Optional<PhieuTraHang> phieuTraHang = phieuTraHangRepository.findById(dto.getPhieuTraHangId());
            phieuTraHang.ifPresent(entity::setPhieuTraHang);
        }
        
        // Set ChiTietSanPham entity
        if (dto.getChiTietSanPhamId() != null) {
            Optional<ChiTietSanPham> chiTietSanPham = chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId());
            chiTietSanPham.ifPresent(entity::setChiTietSanPham);
        }
        
        entity.setNgayCapNhat(LocalDate.now());
        entity.setDaXoa(0);
        ChiTietPhieuTraHang saved = chiTietPhieuTraHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietPhieuTraHangResponse.class);
    }

    @Override
    public ChiTietPhieuTraHangResponse update(Integer id, ChiTietPhieuTraHangDTO dto) {
        Optional<ChiTietPhieuTraHang> optional = chiTietPhieuTraHangRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDaXoa() == 1) return null;

        ChiTietPhieuTraHang entity = optional.get();
        modelMapper.map(dto, entity);
        
        // Update PhieuTraHang entity
        if (dto.getPhieuTraHangId() != null) {
            Optional<PhieuTraHang> phieuTraHang = phieuTraHangRepository.findById(dto.getPhieuTraHangId());
            phieuTraHang.ifPresent(entity::setPhieuTraHang);
        }
        
        // Update ChiTietSanPham entity
        if (dto.getChiTietSanPhamId() != null) {
            Optional<ChiTietSanPham> chiTietSanPham = chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId());
            chiTietSanPham.ifPresent(entity::setChiTietSanPham);
        }
        
        entity.setNgayCapNhat(LocalDate.now());

        ChiTietPhieuTraHang updated = chiTietPhieuTraHangRepository.save(entity);
        return modelMapper.map(updated, ChiTietPhieuTraHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        Optional<ChiTietPhieuTraHang> optional = chiTietPhieuTraHangRepository.findById(id);
        if (optional.isPresent() && optional.get().getDaXoa() == 0) {
            ChiTietPhieuTraHang entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            chiTietPhieuTraHangRepository.save(entity);
        }
    }

    @Override
    public Optional<ChiTietPhieuTraHangResponse> getById(Integer id) {
        return chiTietPhieuTraHangRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, ChiTietPhieuTraHangResponse.class));
    }

    @Override
    public List<ChiTietPhieuTraHangResponse> getByPhieuTraHangId(Integer phieuTraHangId) {
        return chiTietPhieuTraHangRepository.findAll().stream()
                .filter(ctpth -> ctpth.getDaXoa() == 0 && ctpth.getPhieuTraHang() != null && ctpth.getPhieuTraHang().getId().equals(phieuTraHangId))
                .map(ctpth -> modelMapper.map(ctpth, ChiTietPhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietPhieuTraHangResponse> getByChiTietSanPhamId(Integer chiTietSanPhamId) {
        return chiTietPhieuTraHangRepository.findAll().stream()
                .filter(ctpth -> ctpth.getDaXoa() == 0 && ctpth.getChiTietSanPham() != null && ctpth.getChiTietSanPham().getMaChiTiet().equals(chiTietSanPhamId))
                .map(ctpth -> modelMapper.map(ctpth, ChiTietPhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }
}
