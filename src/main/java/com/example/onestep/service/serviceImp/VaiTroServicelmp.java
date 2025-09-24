package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.VaiTroDTO;
import com.example.onestep.dto.response.KhachHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.dto.response.VaiTroResponse;
import com.example.onestep.dto.response.VoucherResponse;
import com.example.onestep.entity.KhachHang;
import com.example.onestep.entity.SanPham;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    @Override
    public VaiTroResponse add(VaiTroDTO dto) {
        VaiTro entity = modelMapper.map(dto, VaiTro.class);
        entity.setNgayCapNhat(java.sql.Date.valueOf(LocalDate.now()));
        VaiTro saved = vaiTroRepository.save(entity);
        return modelMapper.map(saved, VaiTroResponse.class);
    }

    @Override
    public VaiTroResponse update(Integer id, VaiTroDTO dto) {
        Optional<VaiTro> optional = vaiTroRepository.findById(id);
        if (optional.isEmpty()) return null;

        VaiTro entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(java.sql.Date.valueOf(LocalDate.now()));

        VaiTro updated = vaiTroRepository.save(entity);
        return modelMapper.map(updated, VaiTroResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (vaiTroRepository.existsById(id)) {
            vaiTroRepository.deleteById(id);
        }
    }

    @Override
    public Optional<VaiTroResponse> getById(Integer id) {
        return vaiTroRepository.findById(id)
                .map(entity -> modelMapper.map(entity, VaiTroResponse.class));
    }
}
