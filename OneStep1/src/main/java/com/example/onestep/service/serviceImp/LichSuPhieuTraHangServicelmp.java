package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.LichSuPhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.LichSuPhieuTraHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.LichSuPhieuTraHangRepository;
import com.example.onestep.service.LichSuPhieuTraHangService;
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
public class LichSuPhieuTraHangServicelmp implements LichSuPhieuTraHangService {
    @Autowired
    private LichSuPhieuTraHangRepository lichSuPhieuTraHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LichSuPhieuTraHangResponse> getAll() {
        return lichSuPhieuTraHangRepository.findAll().stream()
                .map(lspth -> modelMapper.map(lspth, LichSuPhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<LichSuPhieuTraHangResponse> phanTrang(Pageable pageable) {
        Page<LichSuPhieuTraHang> page = lichSuPhieuTraHangRepository.findAll(pageable);
        List<LichSuPhieuTraHangResponse> dtoList = page.getContent().stream()
                .map(lspth -> modelMapper.map(lspth, LichSuPhieuTraHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public LichSuPhieuTraHangResponse add(LichSuPhieuTraHangDTO dto) {
        LichSuPhieuTraHang entity = modelMapper.map(dto, LichSuPhieuTraHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        LichSuPhieuTraHang saved = lichSuPhieuTraHangRepository.save(entity);
        return modelMapper.map(saved, LichSuPhieuTraHangResponse.class);
    }

    @Override
    public LichSuPhieuTraHangResponse update(Integer id, LichSuPhieuTraHangDTO dto) {
        Optional<LichSuPhieuTraHang> optional = lichSuPhieuTraHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        LichSuPhieuTraHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        LichSuPhieuTraHang updated = lichSuPhieuTraHangRepository.save(entity);
        return modelMapper.map(updated, LichSuPhieuTraHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (lichSuPhieuTraHangRepository.existsById(id)) {
            lichSuPhieuTraHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<LichSuPhieuTraHangResponse> getById(Integer id) {
        return lichSuPhieuTraHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, LichSuPhieuTraHangResponse.class));
    }
}
