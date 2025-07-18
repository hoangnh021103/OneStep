package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.KhachHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.service.KhachHangService;
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
public class KhachHangServicelmp implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<KhachHangResponse> getAll() {
        return khachHangRepository.findAll().stream()
                .map(kh -> modelMapper.map(kh, KhachHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<KhachHangResponse> phanTrang(Pageable pageable) {
        Page<KhachHang> page = khachHangRepository.findAll(pageable);
        List<KhachHangResponse> dtoList = page.getContent().stream()
                .map(kh -> modelMapper.map(kh, KhachHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public KhachHangResponse add(KhachHangDTO dto) {
        KhachHang entity = modelMapper.map(dto, KhachHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        KhachHang saved = khachHangRepository.save(entity);
        return modelMapper.map(saved, KhachHangResponse.class);
    }

    @Override
    public KhachHangResponse update(Integer id, KhachHangDTO dto) {
        Optional<KhachHang> optional = khachHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        KhachHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        KhachHang updated = khachHangRepository.save(entity);
        return modelMapper.map(updated, KhachHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (khachHangRepository.existsById(id)) {
            khachHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<KhachHangResponse> getById(Integer id) {
        return khachHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, KhachHangResponse.class));
    }
}
