package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.PhuongThucVanChuyenDTO;
import com.example.onestep.dto.response.PhuongThucVanChuyenResponse;
import com.example.onestep.entity.PhuongThucVanChuyen;
import com.example.onestep.repository.PhuongThucVanChuyenRepository;
import com.example.onestep.service.PhuongThucVanChuyenService;
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
public class PhuongThucVanChuyenServiceImpl implements PhuongThucVanChuyenService {
    @Autowired
    private PhuongThucVanChuyenRepository phuongThucVanChuyenRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PhuongThucVanChuyenResponse> getAll() {
        return phuongThucVanChuyenRepository.findAll().stream()
                .filter(ptvc -> ptvc.getDaXoa() == 0)
                .map(ptvc -> modelMapper.map(ptvc, PhuongThucVanChuyenResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PhuongThucVanChuyenResponse> phanTrang(Pageable pageable) {
        Page<PhuongThucVanChuyen> page = phuongThucVanChuyenRepository.findAll(pageable);
        List<PhuongThucVanChuyenResponse> dtoList = page.getContent().stream()
                .filter(ptvc -> ptvc.getDaXoa() == 0)
                .map(ptvc -> modelMapper.map(ptvc, PhuongThucVanChuyenResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public PhuongThucVanChuyenResponse add(PhuongThucVanChuyenDTO dto) {
        PhuongThucVanChuyen entity = modelMapper.map(dto, PhuongThucVanChuyen.class);
        entity.setNgayCapNhat(LocalDate.now());
        entity.setDaXoa(0);
        PhuongThucVanChuyen saved = phuongThucVanChuyenRepository.save(entity);
        return modelMapper.map(saved, PhuongThucVanChuyenResponse.class);
    }

    @Override
    public PhuongThucVanChuyenResponse update(Integer id, PhuongThucVanChuyenDTO dto) {
        Optional<PhuongThucVanChuyen> optional = phuongThucVanChuyenRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDaXoa() == 1) return null;

        PhuongThucVanChuyen entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        PhuongThucVanChuyen updated = phuongThucVanChuyenRepository.save(entity);
        return modelMapper.map(updated, PhuongThucVanChuyenResponse.class);
    }

    @Override
    public void delete(Integer id) {
        Optional<PhuongThucVanChuyen> optional = phuongThucVanChuyenRepository.findById(id);
        if (optional.isPresent() && optional.get().getDaXoa() == 0) {
            PhuongThucVanChuyen entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            phuongThucVanChuyenRepository.save(entity);
        }
    }

    @Override
    public Optional<PhuongThucVanChuyenResponse> getById(Integer id) {
        return phuongThucVanChuyenRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, PhuongThucVanChuyenResponse.class));
    }
}
