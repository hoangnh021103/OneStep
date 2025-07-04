package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.ThuongHieuResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.ThuongHieu;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.ThuongHieuRepository;
import com.example.onestep.service.ThuongHieuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThuongHieuServicelmp implements ThuongHieuService {
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ThuongHieuResponse> getAll() {
        return thuongHieuRepository.findAll().stream()
                .map(th -> modelMapper.map(th, ThuongHieuResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ThuongHieuResponse> phanTrang(Pageable pageable) {
        Page<ThuongHieu> page = thuongHieuRepository.findAll(pageable);
        List<ThuongHieuResponse> dtoList = page.getContent().stream()
                .map(th -> modelMapper.map(th, ThuongHieuResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
