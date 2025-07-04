package com.example.onestep.service.serviceImp;


import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.KichCoResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.KichCo;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.KichCoRepository;
import com.example.onestep.service.KichCoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KichCoServicelmp implements KichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<KichCoResponse> getAll() {
        return kichCoRepository.findAll().stream()
                .map(kc -> modelMapper.map(kc, KichCoResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<KichCoResponse> phanTrang(Pageable pageable) {
        Page<KichCo> page = kichCoRepository.findAll(pageable);
        List<KichCoResponse> dtoList = page.getContent().stream()
                .map(kc -> modelMapper.map(kc, KichCoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

}
