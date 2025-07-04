package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhuongThucThanhToanResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.PhuongThucThanhToan;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.PhuongThucThanhToanRepository;
import com.example.onestep.service.PhuongThucThanhToanService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhuongThucThanhToanServicelmp implements PhuongThucThanhToanService {
    @Autowired
    private PhuongThucThanhToanRepository phuongThucThanhToanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhuongThucThanhToanResponse> getAll() {
        return phuongThucThanhToanRepository.findAll().stream()
                .map(pttt -> modelMapper.map(pttt, PhuongThucThanhToanResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PhuongThucThanhToanResponse> phanTrang(Pageable pageable) {
        Page<PhuongThucThanhToan> page = phuongThucThanhToanRepository.findAll(pageable);
        List<PhuongThucThanhToanResponse> dtoList = page.getContent().stream()
                .map(pttt -> modelMapper.map(pttt, PhuongThucThanhToanResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
