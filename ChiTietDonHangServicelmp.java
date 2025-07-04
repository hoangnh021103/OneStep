package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.service.ChiTietDonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangServicelmp implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietDonHangResponse> getAll() {
        return chiTietDonHangRepository.findAll().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietDonHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietDonHang> page = chiTietDonHangRepository.findAll(pageable);
        List<ChiTietDonHangResponse> dtoList = page.getContent().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
