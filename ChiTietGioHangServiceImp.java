package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietGioHangDTO;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.entity.ChiTietGioHang;
import com.example.onestep.repository.ChiTietGioHangRepository;
import com.example.onestep.service.ChiTietGioHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImp implements ChiTietGioHangService {

    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietGioHangResponse> getAll() {
        return chiTietGioHangRepository.findAll().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietGioHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietGioHang> page = chiTietGioHangRepository.findAll(pageable);
        List<ChiTietGioHangResponse> dtoList = page.getContent().stream()
                .map(ct -> modelMapper.map(ct, ChiTietGioHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
