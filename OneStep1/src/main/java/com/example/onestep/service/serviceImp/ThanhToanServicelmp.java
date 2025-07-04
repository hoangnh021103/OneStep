package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.ThanhToanResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.ThanhToan;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.ThanhToanRepository;
import com.example.onestep.service.ThanhToanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThanhToanServicelmp implements ThanhToanService {
    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ThanhToanResponse> getAll() {
        return thanhToanRepository.findAll().stream()
                .map(tt -> modelMapper.map(tt, ThanhToanResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ThanhToanResponse> phanTrang(Pageable pageable) {
        Page<ThanhToan> page = thanhToanRepository.findAll(pageable);
        List<ThanhToanResponse> dtoList = page.getContent().stream()
                .map(tt -> modelMapper.map(tt, ThanhToanResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
