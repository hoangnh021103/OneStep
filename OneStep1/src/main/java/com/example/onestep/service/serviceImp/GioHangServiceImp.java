package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.GioHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.GioHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.GioHangRepository;
import com.example.onestep.service.GioHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GioHangServiceImp implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GioHangResponse> getAll() {
        return gioHangRepository.findAll().stream()
                .map(gh -> modelMapper.map(gh, GioHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<GioHangResponse> phanTrang(Pageable pageable) {
        Page<GioHang> page = gioHangRepository.findAll(pageable);
        List<GioHangResponse> dtoList = page.getContent().stream()
                .map(gh -> modelMapper.map(gh, GioHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public GioHangResponse add(GioHangDTO dto) {
        GioHang entity = modelMapper.map(dto, GioHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        GioHang saved = gioHangRepository.save(entity);
        return modelMapper.map(saved, GioHangResponse.class);
    }

    @Override
    public GioHangResponse update(Integer id, GioHangDTO dto) {
        Optional<GioHang> optional = gioHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        GioHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        GioHang updated = gioHangRepository.save(entity);
        return modelMapper.map(updated, GioHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (gioHangRepository.existsById(id)) {
            gioHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<GioHangResponse> getById(Integer id) {
        return gioHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, GioHangResponse.class));
    }

}
