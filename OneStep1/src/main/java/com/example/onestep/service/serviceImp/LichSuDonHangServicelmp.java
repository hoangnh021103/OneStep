package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuDonHangResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.LichSuDonHang;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.LichSuDonHangRepository;
import com.example.onestep.service.LichSuDonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
