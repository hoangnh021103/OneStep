package com.example.onestep.service.serviceImp;


import com.example.onestep.dto.request.KichCoDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KichCoResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.KichCo;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.KichCoRepository;
import com.example.onestep.service.KichCoService;
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
public class KichCoServicelmp implements KichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<KichCoResponse> getAll() {
        return kichCoRepository.findAll().stream()
                .map(kc -> modelMapper.map(kc, KichCoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<KichCoResponse> phanTrang(Pageable pageable) {
        Page<KichCo> page = kichCoRepository.findAll(pageable);
        List<KichCoResponse> dtoList = page.getContent().stream()
                .map(kc -> modelMapper.map(kc, KichCoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public KichCoResponse add(KichCoDTO dto) {
        KichCo entity = modelMapper.map(dto, KichCo.class);
        entity.setNgayCapNhat(LocalDate.now());
        KichCo saved = kichCoRepository.save(entity);
        return modelMapper.map(saved, KichCoResponse.class);
    }

    @Override
    public KichCoResponse update(Integer id, KichCoDTO dto) {
        Optional<KichCo> optional = kichCoRepository.findById(id);
        if (optional.isEmpty()) return null;

        KichCo entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        KichCo updated = kichCoRepository.save(entity);
        return modelMapper.map(updated, KichCoResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (kichCoRepository.existsById(id)) {
            kichCoRepository.deleteById(id);
        }
    }

    @Override
    public Optional<KichCoResponse> getById(Integer id) {
        return kichCoRepository.findById(id)
                .map(entity -> modelMapper.map(entity, KichCoResponse.class));
    }

}
