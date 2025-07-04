package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.DonHang;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.repository.DonHangRepository;
import com.example.onestep.service.DonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonHangServicelmp implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DonHangResponse> getAll() {
        return donHangRepository.findAll().stream()
                .map(dh -> modelMapper.map(dh, DonHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DonHangResponse> phanTrang(Pageable pageable) {
        Page<DonHang> page = donHangRepository.findAll(pageable);
        List<DonHangResponse> dtoList = page.getContent().stream()
                .map(dh -> modelMapper.map(dh, DonHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
