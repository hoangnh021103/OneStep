package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.AnhSanPhamDTO;
import com.example.onestep.dto.response.AnhSanPhamResponse;
import com.example.onestep.entity.AnhSanPham;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.AnhSanPhamRepository;
import com.example.onestep.repository.SanPhamRepository;
import com.example.onestep.service.AnhSanPhamService;
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
public class AnhSanPhamServiceImpl implements AnhSanPhamService {
    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AnhSanPhamResponse> getAll() {
        return anhSanPhamRepository.findAll().stream()
                .filter(asp -> asp.getDaXoa() == 0)
                .map(asp -> modelMapper.map(asp, AnhSanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AnhSanPhamResponse> phanTrang(Pageable pageable) {
        Page<AnhSanPham> page = anhSanPhamRepository.findAll(pageable);
        List<AnhSanPhamResponse> dtoList = page.getContent().stream()
                .filter(asp -> asp.getDaXoa() == 0)
                .map(asp -> modelMapper.map(asp, AnhSanPhamResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public AnhSanPhamResponse add(AnhSanPhamDTO dto) {
        AnhSanPham entity = modelMapper.map(dto, AnhSanPham.class);
        // Validate sanPhamId
        if (dto.getSanPhamId() == null) {
            throw new IllegalArgumentException("sanPhamId không được null");
        }
        Optional<SanPham> sanPham = sanPhamRepository.findById(dto.getSanPhamId());
        if (sanPham.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm với id: " + dto.getSanPhamId());
        }
        entity.setSanPham(sanPham.get());
        entity.setNgayCapNhat(LocalDate.now());
        entity.setDaXoa(0);
        AnhSanPham saved = anhSanPhamRepository.save(entity);
        return modelMapper.map(saved, AnhSanPhamResponse.class);
    }

    @Override
    public AnhSanPhamResponse update(Integer id, AnhSanPhamDTO dto) {
        Optional<AnhSanPham> optional = anhSanPhamRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDaXoa() == 1) return null;

        AnhSanPham entity = optional.get();
        modelMapper.map(dto, entity);
        // Validate sanPhamId
        if (dto.getSanPhamId() == null) {
            throw new IllegalArgumentException("sanPhamId không được null");
        }
        Optional<SanPham> sanPham = sanPhamRepository.findById(dto.getSanPhamId());
        if (sanPham.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy sản phẩm với id: " + dto.getSanPhamId());
        }
        entity.setSanPham(sanPham.get());
        entity.setNgayCapNhat(LocalDate.now());

        AnhSanPham updated = anhSanPhamRepository.save(entity);
        return modelMapper.map(updated, AnhSanPhamResponse.class);
    }

    @Override
    public void delete(Integer id) {
        Optional<AnhSanPham> optional = anhSanPhamRepository.findById(id);
        if (optional.isPresent() && optional.get().getDaXoa() == 0) {
            AnhSanPham entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            anhSanPhamRepository.save(entity);
        }
    }

    @Override
    public Optional<AnhSanPhamResponse> getById(Integer id) {
        return anhSanPhamRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, AnhSanPhamResponse.class));
    }

    @Override
    public List<AnhSanPhamResponse> getBySanPhamId(Integer sanPhamId) {
        return anhSanPhamRepository.findAll().stream()
                .filter(asp -> asp.getDaXoa() == 0 && asp.getSanPham() != null && asp.getSanPham().getMaSanPham().equals(sanPhamId))
                .map(asp -> modelMapper.map(asp, AnhSanPhamResponse.class))
                .collect(Collectors.toList());
    }
}
