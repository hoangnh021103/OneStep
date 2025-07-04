package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.service.DiaChiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
