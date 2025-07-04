package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhieuTraHangResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.PhieuTraHang;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.PhieuTraHangRepository;
import com.example.onestep.service.PhieuTraHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhieuTraHangServicelmp implements PhieuTraHangService {
    @Autowired
    private PhieuTraHangRepository phieuTraHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhieuTraHangResponse> getAll() {
        return phieuTraHangRepository.findAll().stream()
                .map(pth -> modelMapper.map(pth, PhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PhieuTraHangResponse> phanTrang(Pageable pageable) {
        Page<PhieuTraHang> page = phieuTraHangRepository.findAll(pageable);
        List<PhieuTraHangResponse> dtoList = page.getContent().stream()
                .map(pth -> modelMapper.map(pth, PhieuTraHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
