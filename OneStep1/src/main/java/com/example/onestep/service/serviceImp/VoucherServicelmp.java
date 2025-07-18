package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VoucherDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VoucherResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.SanPham;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Override
    public VoucherResponse add(VoucherDTO dto) {
        Voucher entity = modelMapper.map(dto, Voucher.class);
        entity.setNgayCapNhat(LocalDate.now());
        Voucher saved = voucherRepository.save(entity);
        return modelMapper.map(saved, VoucherResponse.class);
    }

    @Override
    public VoucherResponse update(Integer id, VoucherDTO dto) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        if (optional.isEmpty()) return null;

        Voucher entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        Voucher updated = voucherRepository.save(entity);
        return modelMapper.map(updated, VoucherResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (voucherRepository.existsById(id)) {
            voucherRepository.deleteById(id);
        }
    }

    @Override
    public Optional<VoucherResponse> getById(Integer id) {
        return voucherRepository.findById(id)
                .map(entity -> modelMapper.map(entity, VoucherResponse.class));
    }
}
