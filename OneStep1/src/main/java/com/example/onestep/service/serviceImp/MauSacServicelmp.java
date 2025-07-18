package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.MauSacDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.MauSacResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.MauSac;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.MauSacRepository;
import com.example.onestep.service.MauSacService;
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
public class MauSacServicelmp implements MauSacService {
    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MauSacResponse> getAll() {
        return mauSacRepository.findAll().stream()
                .map(ms -> modelMapper.map(ms, MauSacResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MauSacResponse> phanTrang(Pageable pageable) {
        Page<MauSac> page = mauSacRepository.findAll(pageable);
        List<MauSacResponse> dtoList = page.getContent().stream()
                .map(ms -> modelMapper.map(ms, MauSacResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public MauSacResponse add(MauSacDTO dto) {
        MauSac entity = modelMapper.map(dto, MauSac.class);
        entity.setNgayCapNhat(LocalDate.now());
        MauSac saved = mauSacRepository.save(entity);
        return modelMapper.map(saved, MauSacResponse.class);
    }

    @Override
    public MauSacResponse update(Integer id, MauSacDTO dto) {
        Optional<MauSac> optional = mauSacRepository.findById(id);
        if (optional.isEmpty()) return null;

        MauSac entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        MauSac updated = mauSacRepository.save(entity);
        return modelMapper.map(updated, MauSacResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (mauSacRepository.existsById(id)) {
            mauSacRepository.deleteById(id);
        }
    }

    @Override
    public Optional<MauSacResponse> getById(Integer id) {
        return mauSacRepository.findById(id)
                .map(entity -> modelMapper.map(entity, MauSacResponse.class));
    }
}
