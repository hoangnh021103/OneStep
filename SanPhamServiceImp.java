package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.SanPhamRepository;
import com.example.onestep.service.SanPhamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImp implements SanPhamService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPhamResponse> getAll() {
        List<SanPham> list = sanPhamRepository.findAll();
        return list.stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamResponse> phanTrang(Pageable pageable) {
        Page<SanPham> page = sanPhamRepository.findAll(pageable);
        List<SanPhamResponse> dtoList = page.getContent().stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
