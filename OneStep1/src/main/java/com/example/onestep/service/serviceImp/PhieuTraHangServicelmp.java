package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.PhieuTraHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhieuTraHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.PhieuTraHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.PhieuTraHangRepository;
import com.example.onestep.service.PhieuTraHangService;
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
public class PhieuTraHangServicelmp implements PhieuTraHangService {
    @Autowired
    private PhieuTraHangRepository phieuTraHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhieuTraHangResponse> getAll() {
        return phieuTraHangRepository.findAll().stream()
                .map(pth -> modelMapper.map(pth, PhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PhieuTraHangResponse> phanTrang(Pageable pageable) {
        Page<PhieuTraHang> page = phieuTraHangRepository.findAll(pageable);
        List<PhieuTraHangResponse> dtoList = page.getContent().stream()
                .map(pth -> modelMapper.map(pth, PhieuTraHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public PhieuTraHangResponse add(PhieuTraHangDTO dto) {
        PhieuTraHang entity = modelMapper.map(dto, PhieuTraHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        PhieuTraHang saved = phieuTraHangRepository.save(entity);
        return modelMapper.map(saved, PhieuTraHangResponse.class);
    }

    @Override
    public PhieuTraHangResponse update(Integer id, PhieuTraHangDTO dto) {
        Optional<PhieuTraHang> optional = phieuTraHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        PhieuTraHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        PhieuTraHang updated = phieuTraHangRepository.save(entity);
        return modelMapper.map(updated, PhieuTraHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (phieuTraHangRepository.existsById(id)) {
            phieuTraHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<PhieuTraHangResponse> getById(Integer id) {
        return phieuTraHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, PhieuTraHangResponse.class));
    }
}
