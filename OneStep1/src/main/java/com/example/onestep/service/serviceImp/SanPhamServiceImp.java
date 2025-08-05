package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.request.SanPhamSearchDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.SanPhamRepository;
import com.example.onestep.service.SanPhamService;
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
public class SanPhamServiceImp implements SanPhamService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPhamResponse> getAll() {
        List<SanPham> list = sanPhamRepository.findAll();
        return list.stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamResponse> phanTrang(Pageable pageable) {
        Page<SanPham> page = sanPhamRepository.findAll(pageable);
        List<SanPhamResponse> dtoList = page.getContent().stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public SanPhamResponse add(SanPhamDTO dto) {
        SanPham entity = modelMapper.map(dto, SanPham.class);
        entity.setNgayCapNhat(LocalDate.now());
        SanPham saved = sanPhamRepository.save(entity);
        return modelMapper.map(saved, SanPhamResponse.class);
    }

    @Override
    public SanPhamResponse update(Integer id, SanPhamDTO dto) {
        Optional<SanPham> optional = sanPhamRepository.findById(id);
        if (optional.isEmpty()) return null;

        SanPham entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        SanPham updated = sanPhamRepository.save(entity);
        return modelMapper.map(updated, SanPhamResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (sanPhamRepository.existsById(id)) {
            sanPhamRepository.deleteById(id);
        }
    }

    @Override
    public Optional<SanPhamResponse> getById(Integer id) {
        return sanPhamRepository.findById(id)
                .map(entity -> modelMapper.map(entity, SanPhamResponse.class));
    }

    @Override
    public Page<SanPhamResponse> timKiemSanPham(SanPhamSearchDTO searchDTO, Pageable pageable) {
        // Lấy tất cả sản phẩm từ database
        List<SanPham> allSanPham = sanPhamRepository.findAll();
        
        // Lọc theo các tiêu chí
        List<SanPham> filteredList = allSanPham.stream()
                .filter(sp -> searchDTO.getTenSanPham() == null || 
                        sp.getTenSanPham().toLowerCase().contains(searchDTO.getTenSanPham().toLowerCase()))
                .filter(sp -> searchDTO.getMaCode() == null || 
                        sp.getMaCode().equals(searchDTO.getMaCode()))
                .filter(sp -> searchDTO.getTrangThai() == null || 
                        sp.getTrangThai().equals(searchDTO.getTrangThai()))
                .filter(sp -> searchDTO.getDaXoa() == null || 
                        sp.getDaXoa().equals(searchDTO.getDaXoa()))
                .collect(Collectors.toList());
        
        // Phân trang thủ công
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredList.size());
        
        List<SanPham> pageContent = filteredList.subList(start, end);
        List<SanPhamResponse> dtoList = pageContent.stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
                
        return new PageImpl<>(dtoList, pageable, filteredList.size());
    }

    @Override
    public List<SanPhamResponse> timKiemTheoTen(String tenSanPham) {
        List<SanPham> allSanPham = sanPhamRepository.findAll();
        return allSanPham.stream()
                .filter(sp -> sp.getTenSanPham().toLowerCase().contains(tenSanPham.toLowerCase()))
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamResponse> locTheoTrangThai(Integer trangThai) {
        List<SanPham> allSanPham = sanPhamRepository.findAll();
        return allSanPham.stream()
                .filter(sp -> sp.getTrangThai().equals(trangThai))
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamResponse> locTheoThuongHieu(String thuongHieu) {
        // Vì SanPham không có trực tiếp thương hiệu, method này sẽ trả về tất cả sản phẩm
        // Để lọc theo thương hiệu, cần sử dụng ChiTietSanPham thay vì SanPham
        List<SanPham> allSanPham = sanPhamRepository.findAll();
        return allSanPham.stream()
                .map(sp -> modelMapper.map(sp, SanPhamResponse.class))
                .collect(Collectors.toList());
    }
}

