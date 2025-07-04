package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.service.KhachHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
