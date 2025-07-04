package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.MauSacResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.MauSac;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.MauSacRepository;
import com.example.onestep.service.MauSacService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
