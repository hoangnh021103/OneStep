package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.SanPhamKhuyenMaiDTO;
import com.example.onestep.dto.response.SanPhamKhuyenMaiResponse;
import com.example.onestep.entity.SanPhamKhuyenMai;
import com.example.onestep.entity.SanPham;
import com.example.onestep.entity.Voucher;
import com.example.onestep.repository.SanPhamKhuyenMaiRepository;
import com.example.onestep.repository.SanPhamRepository;
import com.example.onestep.repository.VoucherRepository;
import com.example.onestep.service.SanPhamKhuyenMaiService;
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
public class SanPhamKhuyenMaiServiceImpl implements SanPhamKhuyenMaiService {

    @Autowired
    private SanPhamKhuyenMaiRepository sanPhamKhuyenMaiRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SanPhamKhuyenMaiResponse> getAll() {
        return sanPhamKhuyenMaiRepository.findAll().stream()
                .filter(spkm -> spkm.getDaXoa() == 0)
                .map(spkm -> modelMapper.map(spkm, SanPhamKhuyenMaiResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SanPhamKhuyenMaiResponse> phanTrang(Pageable pageable) {
        Page<SanPhamKhuyenMai> page = sanPhamKhuyenMaiRepository.findAll(pageable);
        List<SanPhamKhuyenMaiResponse> dtoList = page.getContent().stream()
                .filter(spkm -> spkm.getDaXoa() == 0)
                .map(spkm -> modelMapper.map(spkm, SanPhamKhuyenMaiResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public SanPhamKhuyenMaiResponse add(SanPhamKhuyenMaiDTO dto) {
        SanPhamKhuyenMai entity = new SanPhamKhuyenMai();

        // Liên kết sản phẩm
        if (dto.getSanPhamId() != null) {
            SanPham sanPham = sanPhamRepository.findById(dto.getSanPhamId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            entity.setSanPham(sanPham);
        }

        // Liên kết voucher
        if (dto.getVoucherId() != null) {
            Voucher voucher = voucherRepository.findById(dto.getVoucherId())
                    .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
            entity.setVoucher(voucher);
        }

        // Map thủ công các trường khác (KHÔNG map toàn bộ bằng modelMapper để tránh override id)
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setNgayCapNhat(LocalDate.now());
        entity.setDaXoa(0);

        SanPhamKhuyenMai saved = sanPhamKhuyenMaiRepository.save(entity);
        return modelMapper.map(saved, SanPhamKhuyenMaiResponse.class);
    }

    @Override
    public SanPhamKhuyenMaiResponse update(Integer id, SanPhamKhuyenMaiDTO dto) {
        SanPhamKhuyenMai entity = sanPhamKhuyenMaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khuyến mãi sản phẩm"));

        if (entity.getDaXoa() == 1) {
            throw new RuntimeException("Bản ghi đã bị xóa");
        }

        // Cập nhật field cơ bản
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setNgayCapNhat(LocalDate.now());

        // Cập nhật liên kết sản phẩm
        if (dto.getSanPhamId() != null) {
            SanPham sanPham = sanPhamRepository.findById(dto.getSanPhamId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            entity.setSanPham(sanPham);
        }

        // Cập nhật liên kết voucher
        if (dto.getVoucherId() != null) {
            Voucher voucher = voucherRepository.findById(dto.getVoucherId())
                    .orElseThrow(() -> new RuntimeException("Voucher không tồn tại"));
            entity.setVoucher(voucher);
        }

        SanPhamKhuyenMai updated = sanPhamKhuyenMaiRepository.save(entity);
        return modelMapper.map(updated, SanPhamKhuyenMaiResponse.class);
    }

    @Override
    public void delete(Integer id) {
        sanPhamKhuyenMaiRepository.findById(id).ifPresent(entity -> {
            if (entity.getDaXoa() == 0) {
                entity.setDaXoa(1);
                entity.setNgayCapNhat(LocalDate.now());
                sanPhamKhuyenMaiRepository.save(entity);
            }
        });
    }

    @Override
    public Optional<SanPhamKhuyenMaiResponse> getById(Integer id) {
        return sanPhamKhuyenMaiRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, SanPhamKhuyenMaiResponse.class));
    }

    @Override
    public List<SanPhamKhuyenMaiResponse> getBySanPhamId(Integer sanPhamId) {
        return sanPhamKhuyenMaiRepository.findAll().stream()
                .filter(spkm -> spkm.getDaXoa() == 0
                        && spkm.getSanPham() != null
                        && spkm.getSanPham().getMaSanPham().equals(sanPhamId))
                .map(spkm -> modelMapper.map(spkm, SanPhamKhuyenMaiResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamKhuyenMaiResponse> getByVoucherId(Integer voucherId) {
        return sanPhamKhuyenMaiRepository.findAll().stream()
                .filter(spkm -> spkm.getDaXoa() == 0
                        && spkm.getVoucher() != null
                        && spkm.getVoucher().getId().equals(voucherId))
                .map(spkm -> modelMapper.map(spkm, SanPhamKhuyenMaiResponse.class))
                .collect(Collectors.toList());
    }
}
