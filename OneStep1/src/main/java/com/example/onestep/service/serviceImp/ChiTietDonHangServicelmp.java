package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietDonHangRepository;
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
        entity.setNgayCapNhat(LocalDate.now());
        ChiTietDonHang saved = chiTietDonHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietDonHangResponse.class);
    }

    @Override
    public ChiTietDonHangResponse update(Integer id, ChiTietDonHangDTO dto) {
        Optional<ChiTietDonHang> optional = chiTietDonHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChiTietDonHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ChiTietDonHang updated = chiTietDonHangRepository.save(entity);
        return modelMapper.map(updated, ChiTietDonHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (chiTietDonHangRepository.existsById(id)) {
            chiTietDonHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ChiTietDonHangResponse> getById(Integer id) {
        return chiTietDonHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ChiTietDonHangResponse.class));
    }
}
