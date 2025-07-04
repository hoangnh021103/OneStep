package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.VoucherResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.Voucher;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.VoucherRepository;
import com.example.onestep.service.VoucherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServicelmp implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<VoucherResponse> getAll() {
        return voucherRepository.findAll().stream()
                .map(vc -> modelMapper.map(vc, VoucherResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<VoucherResponse> phanTrang(Pageable pageable) {
        Page<Voucher> page = voucherRepository.findAll(pageable);
        List<VoucherResponse> dtoList = page.getContent().stream()
                .map(vc -> modelMapper.map(vc, VoucherResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
