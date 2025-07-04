package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.LichSuPhieuTraHangResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.LichSuPhieuTraHang;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.LichSuPhieuTraHangRepository;
import com.example.onestep.service.LichSuPhieuTraHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LichSuPhieuTraHangServicelmp implements LichSuPhieuTraHangService {
    @Autowired
    private LichSuPhieuTraHangRepository lichSuPhieuTraHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<LichSuPhieuTraHangResponse> getAll() {
        return lichSuPhieuTraHangRepository.findAll().stream()
                .map(lspth -> modelMapper.map(lspth, LichSuPhieuTraHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<LichSuPhieuTraHangResponse> phanTrang(Pageable pageable) {
        Page<LichSuPhieuTraHang> page = lichSuPhieuTraHangRepository.findAll(pageable);
        List<LichSuPhieuTraHangResponse> dtoList = page.getContent().stream()
                .map(lspth -> modelMapper.map(lspth, LichSuPhieuTraHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
