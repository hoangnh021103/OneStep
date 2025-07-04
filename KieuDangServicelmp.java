package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KieuDangResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.KieuDang;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.KieuDangRepository;
import com.example.onestep.service.KieuDangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
