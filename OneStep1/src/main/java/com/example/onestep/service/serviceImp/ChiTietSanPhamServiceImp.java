package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.*;
import com.example.onestep.repository.*;
import com.example.onestep.service.ChiTietSanPhamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImp implements ChiTietSanPhamService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private KieuDangRepository kieuDangRepository;

    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<ChiTietSanPhamResponse> getAll() {
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findAll();
        return list.stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietSanPhamResponse> phanTrang(Pageable pageable) {
        Page<ChiTietSanPham> page = chiTietSanPhamRepository.findAll(pageable);
        List<ChiTietSanPhamResponse> dtoList = page.getContent().stream()
                .map(ctsp -> modelMapper.map(ctsp, ChiTietSanPhamResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietSanPhamResponse add(ChiTietSanPhamDTO dto) {
        ChiTietSanPham entity = modelMapper.map(dto, ChiTietSanPham.class);
        entity.setNgayCapNhat(LocalDate.now());
        ChiTietSanPham saved = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(saved, ChiTietSanPhamResponse.class);
    }

    @Override
    public ChiTietSanPhamResponse update(Integer id, ChiTietSanPhamDTO dto) {
        Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChiTietSanPham entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ChiTietSanPham updated = chiTietSanPhamRepository.save(entity);
        return modelMapper.map(updated, ChiTietSanPhamResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (chiTietSanPhamRepository.existsById(id)) {
            chiTietSanPhamRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ChiTietSanPhamResponse> getById(Integer id) {
        return chiTietSanPhamRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ChiTietSanPhamResponse.class));
    }

}
