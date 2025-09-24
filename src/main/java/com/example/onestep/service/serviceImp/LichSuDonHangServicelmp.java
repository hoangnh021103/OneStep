package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.LichSuDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.LichSuDonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.LichSuDonHangRepository;
import com.example.onestep.service.LichSuDonHangService;
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
public class LichSuDonHangServicelmp implements LichSuDonHangService {
    @Autowired
    private LichSuDonHangRepository lichSuDonHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LichSuDonHangResponse> getAll() {
        return lichSuDonHangRepository.findAll().stream()
                .map(lsdh -> modelMapper.map(lsdh, LichSuDonHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<LichSuDonHangResponse> phanTrang(Pageable pageable) {
        Page<LichSuDonHang> page = lichSuDonHangRepository.findAll(pageable);
        List<LichSuDonHangResponse> dtoList = page.getContent().stream()
                .map(lsdh -> modelMapper.map(lsdh, LichSuDonHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public LichSuDonHangResponse add(LichSuDonHangDTO dto) {
        LichSuDonHang entity = modelMapper.map(dto, LichSuDonHang.class);
        entity.setNgayCapNhat(LocalDate.now());
        LichSuDonHang saved = lichSuDonHangRepository.save(entity);
        return modelMapper.map(saved, LichSuDonHangResponse.class);
    }

    @Override
    public LichSuDonHangResponse update(Integer id, LichSuDonHangDTO dto) {
        Optional<LichSuDonHang> optional = lichSuDonHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        LichSuDonHang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        LichSuDonHang updated = lichSuDonHangRepository.save(entity);
        return modelMapper.map(updated, LichSuDonHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (lichSuDonHangRepository.existsById(id)) {
            lichSuDonHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<LichSuDonHangResponse> getById(Integer id) {
        return lichSuDonHangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, LichSuDonHangResponse.class));
    }
}
