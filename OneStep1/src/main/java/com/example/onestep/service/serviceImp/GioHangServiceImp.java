package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.GioHangDTO;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.entity.GioHang;
import com.example.onestep.repository.GioHangRepository;
import com.example.onestep.service.GioHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GioHangServiceImp implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GioHangResponse> getAll() {
        return gioHangRepository.findAll().stream()
                .map(gh -> modelMapper.map(gh, GioHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<GioHangResponse> phanTrang(Pageable pageable) {
        Page<GioHang> page = gioHangRepository.findAll(pageable);
        List<GioHangResponse> dtoList = page.getContent().stream()
                .map(gh -> modelMapper.map(gh, GioHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

}
