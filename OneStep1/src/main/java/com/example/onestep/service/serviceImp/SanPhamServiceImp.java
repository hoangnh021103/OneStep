package com.example.onestep.service.serviceImp;

import com.example.onestep.config.cloudinary.CloudinaryUtils;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.*;
import com.example.onestep.repository.*;
import com.example.onestep.service.SanPhamService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SanPhamServiceImp implements SanPhamService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private KieuDangRepository kieuDangRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DeGiayRepository deGiayRepository;

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
        log.info("Bắt đầu add sản phẩm, DTO: {}", dto);

        // Validation đã được xử lý bởi @Valid, nhưng kiểm tra file rỗng
        if (dto.getDuongDanAnh().isEmpty()) {
            log.error("File ảnh rỗng");
            throw new IllegalArgumentException("File ảnh không được rỗng");
        }

        SanPham entity = modelMapper.map(dto, SanPham.class);
        entity.setMaSanPham(null); // Force null để database tự sinh ID
        entity.setNgayCapNhat(LocalDate.now());

        // Tạo mã code ngẫu nhiên
        Random random = new Random();
        int number = random.nextInt(10000);
        String code = String.format("SP%04d", number);
        entity.setMaCode(code);

        // Upload ảnh
        String imgPath = null;
        try {
            byte[] imageData = dto.getDuongDanAnh().getBytes();
            log.info("Đang upload ảnh với mã: {}", code);
            imgPath = CompletableFuture.supplyAsync(() -> cloudinaryUtils.uploadImage(imageData, code)).join();
            entity.setDuongDanAnh(imgPath);
        } catch (IOException e) {
            log.error("Lỗi xử lý file ảnh", e);
            throw new RuntimeException("Lỗi khi xử lý file ảnh: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Lỗi upload Cloudinary", e);
            throw new RuntimeException("Lỗi khi tải ảnh lên Cloudinary: " + e.getMessage(), e);
        }

        // Kiểm tra và set các quan hệ
        entity.setDeGiay(deGiayRepository.findById(dto.getDeGiayId())
                .orElseThrow(() -> new IllegalArgumentException("Đế giày không tồn tại: " + dto.getDeGiayId())));
        entity.setKieuDang(kieuDangRepository.findById(dto.getKieuDangId())
                .orElseThrow(() -> new IllegalArgumentException("Kiểu dáng không tồn tại: " + dto.getKieuDangId())));
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieuId())
                .orElseThrow(() -> new IllegalArgumentException("Thương hiệu không tồn tại: " + dto.getThuongHieuId())));
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieuId())
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại: " + dto.getChatLieuId())));

        SanPham saved = sanPhamRepository.save(entity);
        log.info("Lưu sản phẩm thành công, ID: {}", saved.getMaSanPham());
        return modelMapper.map(saved, SanPhamResponse.class);
    }

    @Override
    public SanPhamResponse update(Integer id, SanPhamDTO dto) {
        log.info("Bắt đầu update sản phẩm ID: {}", id);

        SanPham entity = sanPhamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại với ID: " + id));

        // Map dữ liệu cơ bản
        entity.setTenSanPham(dto.getTenSanPham());
        entity.setMaCode(dto.getMaCode());
        entity.setMoTa(dto.getMoTa());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayCapNhat(LocalDate.now());

        // Cập nhật các quan hệ
        entity.setDeGiay(deGiayRepository.findById(dto.getDeGiayId())
                .orElseThrow(() -> new IllegalArgumentException("Đế giày không tồn tại: " + dto.getDeGiayId())));
        entity.setKieuDang(kieuDangRepository.findById(dto.getKieuDangId())
                .orElseThrow(() -> new IllegalArgumentException("Kiểu dáng không tồn tại: " + dto.getKieuDangId())));
        entity.setThuongHieu(thuongHieuRepository.findById(dto.getThuongHieuId())
                .orElseThrow(() -> new IllegalArgumentException("Thương hiệu không tồn tại: " + dto.getThuongHieuId())));
        entity.setChatLieu(chatLieuRepository.findById(dto.getChatLieuId())
                .orElseThrow(() -> new IllegalArgumentException("Chất liệu không tồn tại: " + dto.getChatLieuId())));

        // Xử lý ảnh - chỉ cập nhật khi có file mới
        if (dto.getDuongDanAnh() != null && !dto.getDuongDanAnh().isEmpty()) {
            try {
                byte[] imageData = dto.getDuongDanAnh().getBytes();
                String code = entity.getMaCode();
                String imgPath = CompletableFuture.supplyAsync(() ->
                        cloudinaryUtils.uploadImage(imageData, code)).join();
                entity.setDuongDanAnh(imgPath);
                log.info("Đã cập nhật ảnh mới cho sản phẩm ID: {}", id);
            } catch (IOException e) {
                log.error("Lỗi xử lý file ảnh khi update", e);
                throw new RuntimeException("Lỗi khi xử lý file ảnh: " + e.getMessage(), e);
            }
        } else {
            log.info("Giữ nguyên ảnh cũ cho sản phẩm ID: {}", id);
        }

        SanPham updated = sanPhamRepository.save(entity);
        return modelMapper.map(updated, SanPhamResponse.class);
    }
    @Override
    public void delete(Integer id) {
        log.info("Xóa sản phẩm ID: {}", id);
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