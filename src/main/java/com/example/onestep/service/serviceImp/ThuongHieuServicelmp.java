package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.ThuongHieuDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.ThuongHieuResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.SanPham;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Override
    public ThuongHieuResponse add(ThuongHieuDTO dto) {
        ThuongHieu entity = modelMapper.map(dto, ThuongHieu.class);
        entity.setNgayCapNhat(LocalDate.now());
        ThuongHieu saved = thuongHieuRepository.save(entity);
        return modelMapper.map(saved, ThuongHieuResponse.class);
    }

    @Override
    public ThuongHieuResponse update(Integer id, ThuongHieuDTO dto) {
        Optional<ThuongHieu> optional = thuongHieuRepository.findById(id);
        if (optional.isEmpty()) return null;

        ThuongHieu entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ThuongHieu updated = thuongHieuRepository.save(entity);
        return modelMapper.map(updated, ThuongHieuResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (thuongHieuRepository.existsById(id)) {
            thuongHieuRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ThuongHieuResponse> getById(Integer id) {
        return thuongHieuRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ThuongHieuResponse.class));
    }
}
