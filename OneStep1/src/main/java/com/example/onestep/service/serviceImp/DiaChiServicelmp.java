package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.DiaChiDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.service.DiaChiService;
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
public class DiaChiServicelmp implements DiaChiService {
    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DiaChiResponse> getAll() {
        return diaChiRepository.findAll().stream()
                .map(dc -> modelMapper.map(dc, DiaChiResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DiaChiResponse> phanTrang(Pageable pageable) {
        Page<DiaChi> page = diaChiRepository.findAll(pageable);
        List<DiaChiResponse> dtoList = page.getContent().stream()
                .map(dc -> modelMapper.map(dc, DiaChiResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public DiaChiResponse add(DiaChiDTO dto) {
        DiaChi entity = modelMapper.map(dto, DiaChi.class);
        entity.setNgayCapNhat(LocalDate.now());
        DiaChi saved = diaChiRepository.save(entity);
        return modelMapper.map(saved, DiaChiResponse.class);
    }

    @Override
    public DiaChiResponse update(Integer id, DiaChiDTO dto) {
        Optional<DiaChi> optional = diaChiRepository.findById(id);
        if (optional.isEmpty()) return null;

        DiaChi entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        DiaChi updated = diaChiRepository.save(entity);
        return modelMapper.map(updated, DiaChiResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (diaChiRepository.existsById(id)) {
            diaChiRepository.deleteById(id);
        }
    }

    @Override
    public Optional<DiaChiResponse> getById(Integer id) {
        return diaChiRepository.findById(id)
                .map(entity -> modelMapper.map(entity, DiaChiResponse.class));
    }
}
