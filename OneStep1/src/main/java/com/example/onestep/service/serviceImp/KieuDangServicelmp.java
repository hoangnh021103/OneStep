package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.KieuDangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KieuDangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.KieuDang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.KieuDangRepository;
import com.example.onestep.service.KieuDangService;
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
public class KieuDangServicelmp implements KieuDangService {
    @Autowired
    private KieuDangRepository kieuDangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<KieuDangResponse> getAll() {
        return kieuDangRepository.findAll().stream()
                .map(kd -> modelMapper.map(kd, KieuDangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<KieuDangResponse> phanTrang(Pageable pageable) {
        Page<KieuDang> page = kieuDangRepository.findAll(pageable);
        List<KieuDangResponse> dtoList = page.getContent().stream()
                .map(kd -> modelMapper.map(kd, KieuDangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public KieuDangResponse add(KieuDangDTO dto) {
        KieuDang entity = modelMapper.map(dto, KieuDang.class);
        entity.setNgayCapNhat(LocalDate.now());
        KieuDang saved = kieuDangRepository.save(entity);
        return modelMapper.map(saved, KieuDangResponse.class);
    }

    @Override
    public KieuDangResponse update(Integer id, KieuDangDTO dto) {
        Optional<KieuDang> optional = kieuDangRepository.findById(id);
        if (optional.isEmpty()) return null;

        KieuDang entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        KieuDang updated = kieuDangRepository.save(entity);
        return modelMapper.map(updated, KieuDangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (kieuDangRepository.existsById(id)) {
            kieuDangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<KieuDangResponse> getById(Integer id) {
        return kieuDangRepository.findById(id)
                .map(entity -> modelMapper.map(entity, KieuDangResponse.class));
    }
}
