package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.NhanVienDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.NhanVienResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.NhanVien;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.KhachHangRepository;
import com.example.onestep.repository.NhanVienRepository;
import com.example.onestep.service.NhanVienService;
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

    @Override
    public NhanVienResponse add(NhanVienDTO dto) {
        NhanVien entity = modelMapper.map(dto, NhanVien.class);
        entity.setNgayCapNhat(LocalDate.now());
        NhanVien saved = nhanVienRepository.save(entity);
        return modelMapper.map(saved, NhanVienResponse.class);
    }

    @Override
    public NhanVienResponse update(Integer id, NhanVienDTO dto) {
        Optional<NhanVien> optional = nhanVienRepository.findById(id);
        if (optional.isEmpty()) return null;

        NhanVien entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        NhanVien updated = nhanVienRepository.save(entity);
        return modelMapper.map(updated, NhanVienResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (nhanVienRepository.existsById(id)) {
            nhanVienRepository.deleteById(id);
        }
    }

    @Override
    public Optional<NhanVienResponse> getById(Integer id) {
        return nhanVienRepository.findById(id)
                .map(entity -> modelMapper.map(entity, NhanVienResponse.class));
    }
}
