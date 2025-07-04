package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.VaiTro;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.VaiTroRepository;
import com.example.onestep.service.VaiTroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaiTroServicelmp implements VaiTroService {
    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<VaiTroResponse> getAll() {
        return vaiTroRepository.findAll().stream()
                .map(vt -> modelMapper.map(vt, VaiTroResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<VaiTroResponse> phanTrang(Pageable pageable) {
        Page<VaiTro> page = vaiTroRepository.findAll(pageable);
        List<VaiTroResponse> dtoList = page.getContent().stream()
                .map(vt -> modelMapper.map(vt, VaiTroResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
