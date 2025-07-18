package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietGioHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietGioHangRepository;
import com.example.onestep.service.ChiTietGioHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImp implements ChiTietGioHangService {

    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietGioHangResponse> getAll() {
        return chiTietGioHangRepository.findAll().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietGioHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietGioHang> page = chiTietGioHangRepository.findAll(pageable);
        List<ChiTietGioHangResponse> dtoList = page.getContent().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietGioHangResponse add(ChiTietGioHangDTO dto) {
        ChiTietGioHang entity = modelMapper.map(dto, ChiTietGioHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        ChiTietGioHang saved = chiTietGioHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietGioHangResponse.class);
    }

    @Override
    public ChiTietGioHangResponse update(Integer id, ChiTietGioHangDTO dto) {
        Optional<ChiTietGioHang> optional = chiTietGioHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChiTietGioHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ChiTietGioHang updated = chiTietGioHangRepository.save(entity);
        return modelMapper.map(updated, ChiTietGioHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (chiTietGioHangRepository.existsById(id)) {
            chiTietGioHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ChiTietGioHangResponse> getById(Integer id) {
        return chiTietGioHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ChiTietGioHangResponse.class));
    }
}
