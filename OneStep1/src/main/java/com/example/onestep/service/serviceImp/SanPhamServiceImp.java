package com.example.onestep.service.serviceImp;

import com.example.onestep.config.cloudinary.CloudinaryUtils;
import com.example.onestep.dto.request.SanPhamDTO;

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

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImp implements SanPhamService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private CloudinaryUtils cloudinaryUtils;
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
        // Kiểm tra tính hợp lệ của dto
        if (dto == null || dto.getDuongDanAnh() == null) {
            throw new IllegalArgumentException("Dữ liệu sản phẩm hoặc file ảnh không được để trống.");
        }

        SanPham entity = modelMapper.map(dto, SanPham.class);
        entity.setNgayCapNhat(LocalDate.now());

        // Tạo mã code ngẫu nhiên
        Random random = new Random();
        int number = random.nextInt(10000);
        String code = String.format("SP%04d", number);
        entity.setMaCode(code);

        String imgPath = null;
        try {
            // Đọc dữ liệu ảnh
            byte[] imageData = dto.getDuongDanAnh().getBytes();

            // Tải ảnh lên Cloudinary và chờ kết quả
            imgPath = CompletableFuture.supplyAsync(() -> cloudinaryUtils.uploadImage(imageData, code))
                    .join(); // Chờ hoàn thành và lấy kết quả

            // Cập nhật đường dẫn ảnh
            entity.setDuongDanAnh(imgPath);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi xử lý file ảnh: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage(), e);
        }

        // Lưu vào cơ sở dữ liệu
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


}

