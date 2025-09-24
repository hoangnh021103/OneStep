package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.PhuongThucThanhToanDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.PhuongThucThanhToanResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.PhuongThucThanhToan;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.PhuongThucThanhToanRepository;
import com.example.onestep.service.PhuongThucThanhToanService;
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

    @Override
    public PhuongThucThanhToanResponse add(PhuongThucThanhToanDTO dto) {
        PhuongThucThanhToan entity = modelMapper.map(dto, PhuongThucThanhToan.class);
        entity.setNgayCapNhat(LocalDate.now());
        PhuongThucThanhToan saved = phuongThucThanhToanRepository.save(entity);
        return modelMapper.map(saved, PhuongThucThanhToanResponse.class);
    }

    @Override
    public PhuongThucThanhToanResponse update(Integer id, PhuongThucThanhToanDTO dto) {
        Optional<PhuongThucThanhToan> optional = phuongThucThanhToanRepository.findById(id);
        if (optional.isEmpty()) return null;

        PhuongThucThanhToan entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        PhuongThucThanhToan updated = phuongThucThanhToanRepository.save(entity);
        return modelMapper.map(updated, PhuongThucThanhToanResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (phuongThucThanhToanRepository.existsById(id)) {
            phuongThucThanhToanRepository.deleteById(id);
        }
    }

    @Override
    public Optional<PhuongThucThanhToanResponse> getById(Integer id) {
        return phuongThucThanhToanRepository.findById(id)
                .map(entity -> modelMapper.map(entity, PhuongThucThanhToanResponse.class));
    }
}
