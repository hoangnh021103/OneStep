package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.NhanVienResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.NhanVien;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.NhanVienRepository;
import com.example.onestep.service.NhanVienService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NhanVienServicelmp implements NhanVienService {
    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NhanVienResponse> getAll() {
        return nhanVienRepository.findAll().stream()
                .map(nv -> modelMapper.map(nv, NhanVienResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<NhanVienResponse> phanTrang(Pageable pageable) {
        Page<NhanVien> page = nhanVienRepository.findAll(pageable);
        List<NhanVienResponse> dtoList = page.getContent().stream()
                .map(nv -> modelMapper.map(nv, NhanVienResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
