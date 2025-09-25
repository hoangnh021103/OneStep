package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietSanPhamDTO;
import com.example.onestep.dto.response.ChiTietSanPhamResponse;
import com.example.onestep.entity.*;
import com.example.onestep.repository.*;
import com.example.onestep.service.ChiTietSanPhamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private KichCoRepository kichCoRepository;


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

        // Gán các quan hệ
        entity.setSanPham(sanPhamRepository.findById(dto.getSanPhamId()).orElse(null));

        entity.setKichCo(kichCoRepository.findById(dto.getKichCoId()).orElse(null));

        entity.setMauSac(mauSacRepository.findById(dto.getMauSacId()).orElse(null));

        // Gán các thông tin hệ thống
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiTao());
        entity.setDaXoa(0);

        ChiTietSanPham saved = chiTietSanPhamRepository.save(entity);
        
        // Đồng bộ trạng thái với bảng SanPham
        syncProductStatus(dto.getSanPhamId());
        
        return modelMapper.map(saved, ChiTietSanPhamResponse.class);
    }


    @Override
    public ChiTietSanPhamResponse update(Integer id, ChiTietSanPhamDTO dto) {
        Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy chi tiết sản phẩm với ID: " + id);
        }

        ChiTietSanPham entity = optional.get();

        // Cập nhật các trường đơn
        entity.setSoLuongTon(dto.getSoLuongTon());
        entity.setGiaTien(dto.getGiaTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setNgayCapNhat(LocalDate.now());

        // Cập nhật các quan hệ – có kiểm tra tồn tại
        entity.setSanPham(
                sanPhamRepository.findById(dto.getSanPhamId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + dto.getSanPhamId()))
        );



        entity.setKichCo(
                kichCoRepository.findById(dto.getKichCoId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy kích cỡ với ID: " + dto.getKichCoId()))
        );



        entity.setMauSac(
                mauSacRepository.findById(dto.getMauSacId())
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy màu sắc với ID: " + dto.getMauSacId()))
        );

        // Lưu và trả về kết quả
        ChiTietSanPham updated = chiTietSanPhamRepository.save(entity);
        
        // Đồng bộ trạng thái với bảng SanPham
        syncProductStatus(dto.getSanPhamId());
        
        return modelMapper.map(updated, ChiTietSanPhamResponse.class);
    }



    @Override
    public void delete(Integer id) {
        Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(id);
        if (optional.isPresent()) {
            ChiTietSanPham entity = optional.get();
            Integer sanPhamId = entity.getSanPham().getMaSanPham(); // Lưu ID sản phẩm trước khi xóa
            
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM"); // Hoặc lấy từ người dùng hiện tại

            chiTietSanPhamRepository.save(entity);
            
            // Đồng bộ trạng thái với bảng SanPham sau khi xóa
            syncProductStatus(sanPhamId);
        }
    }

    @Override
    public Optional<ChiTietSanPhamResponse> getById(Integer id) {
        return chiTietSanPhamRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, ChiTietSanPhamResponse.class));
    }

    @Override
    public List<ChiTietSanPhamResponse> getBySanPhamId(Integer sanPhamId) {
        List<ChiTietSanPham> list = chiTietSanPhamRepository.findBySanPhamId(sanPhamId);
        System.out.print("list"+list);
        return list.stream().map(c -> ChiTietSanPhamResponse.builder()
                .maChiTiet(c.getMaChiTiet())
                .sanPhamId(c.getSanPham().getMaSanPham())
                .kichCoId(c.getKichCo().getId())
                .mauSacId(c.getMauSac().getId())
                .duongDanAnh(c.getDuongDanAnh())
                .giaTien(c.getGiaTien())
                .soLuongTon(c.getSoLuongTon())
                .trangThai(c.getTrangThai())
                .tienGiamGia(c.getTienGiamGia())
                .daXoa(c.getDaXoa())
                .ngayCapNhat(c.getNgayCapNhat())
                .nguoiTao(c.getNguoiTao())
                .nguoiCapNhat(c.getNguoiCapNhat())
                .build()).toList();
    }

    @Override
    public List<ChiTietSanPham> findBySanPhamIdEntity(Integer sanPhamId) {
        return null;
    }

    @Override
    public List<ChiTietSanPham> getAllForBanHang() {
        return chiTietSanPhamRepository.findAllForBanHang();
    }

    @Override
    public List<ChiTietSanPham> searchForBanHang(String keyword) {
        return chiTietSanPhamRepository.searchForBanHang(keyword);
    }

    @Override
    public boolean updateInventoryQuantity(Integer chiTietSanPhamId, Integer quantitySold) {
        try {
            Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(chiTietSanPhamId);
            if (optional.isEmpty()) {
                return false;
            }

            ChiTietSanPham entity = optional.get();
            int currentQuantity = entity.getSoLuongTon();
            
            // Kiểm tra số lượng tồn kho có đủ không
            if (currentQuantity < quantitySold) {
                return false;
            }

            // Trừ số lượng tồn kho
            int newQuantity = currentQuantity - quantitySold;
            entity.setSoLuongTon(newQuantity);
            
            // Cập nhật trạng thái: 0 = hết hàng, 1 = còn hàng
            entity.setTrangThai(newQuantity > 0 ? 1 : 0);
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM_SALE");

            chiTietSanPhamRepository.save(entity);
            
            // Đồng bộ trạng thái với bảng SanPham
            syncProductStatus(entity.getSanPham().getMaSanPham());
            
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật số lượng tồn kho: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreInventoryQuantity(Integer chiTietSanPhamId, Integer quantityToRestore) {
        try {
            System.out.println("=== DEBUG: restoreInventoryQuantity ===");
            System.out.println("ChiTietSanPhamId: " + chiTietSanPhamId);
            System.out.println("Quantity to restore: " + quantityToRestore);
            
            Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(chiTietSanPhamId);
            if (optional.isEmpty()) {
                System.err.println("ERROR: Không tìm thấy chi tiết sản phẩm ID: " + chiTietSanPhamId);
                return false;
            }

            ChiTietSanPham entity = optional.get();
            int currentQuantity = entity.getSoLuongTon();
            
            System.out.println("Tồn kho hiện tại: " + currentQuantity);
            
            // Cộng lại số lượng tồn kho
            int newQuantity = currentQuantity + quantityToRestore;
            entity.setSoLuongTon(newQuantity);
            
            // Cập nhật trạng thái: nếu trước đó hết hàng thì giờ có hàng trở lại
            entity.setTrangThai(1); // 1 = còn hàng
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM_RESTORE");

            chiTietSanPhamRepository.save(entity);
            
            // Đồng bộ trạng thái với bảng SanPham
            syncProductStatus(entity.getSanPham().getMaSanPham());
            
            System.out.println("✅ Đã khôi phục " + quantityToRestore + " sản phẩm. Tồn kho mới: " + newQuantity);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi khôi phục số lượng tồn kho: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkInventoryQuantity(Integer chiTietSanPhamId, Integer quantityNeeded) {
        try {
            System.out.println("=== DEBUG: checkInventoryQuantity ===");
            System.out.println("ChiTietSanPhamId: " + chiTietSanPhamId);
            System.out.println("Quantity needed: " + quantityNeeded);
            
            Optional<ChiTietSanPham> optional = chiTietSanPhamRepository.findById(chiTietSanPhamId);
            if (optional.isEmpty()) {
                System.out.println("ERROR: Không tìm thấy ChiTietSanPham với ID: " + chiTietSanPhamId);
                return false;
            }

            ChiTietSanPham entity = optional.get();
            System.out.println("Tìm thấy ChiTietSanPham:");
            System.out.println("- Số lượng tồn: " + entity.getSoLuongTon());
            System.out.println("- Trạng thái: " + entity.getTrangThai());
            System.out.println("- Đã xóa: " + entity.getDaXoa());
            System.out.println("- Sản phẩm: " + (entity.getSanPham() != null ? entity.getSanPham().getTenSanPham() : "null"));
            
            boolean hasEnoughStock = entity.getSoLuongTon() >= quantityNeeded && entity.getDaXoa() == 0;
            System.out.println("Kết quả kiểm tra: " + hasEnoughStock);
            
            return hasEnoughStock;
        } catch (Exception e) {
            System.err.println("Lỗi khi kiểm tra số lượng tồn kho: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> getInventoryInfo(Integer chiTietSanPhamId) {
        return null;
    }

    @Override
    public List<ChiTietSanPham> findByMaCode(String maCode) {
        return null;
    }

    /**
     * Đồng bộ trạng thái sản phẩm với bảng SanPham dựa trên tồn kho của ChiTietSanPham
     * @param sanPhamId ID của sản phẩm
     */
    private void syncProductStatus(Integer sanPhamId) {
        try {
            // Lấy tất cả chi tiết sản phẩm của sản phẩm này (chưa bị xóa)
            List<ChiTietSanPham> chiTietList = chiTietSanPhamRepository.findBySanPhamId(sanPhamId);
            
            // Tính tổng tồn kho của tất cả chi tiết sản phẩm
            int totalStock = chiTietList.stream()
                    .filter(ct -> ct.getDaXoa() == 0) // Chỉ tính những chi tiết chưa bị xóa
                    .mapToInt(ChiTietSanPham::getSoLuongTon)
                    .sum();
            
            // Cập nhật trạng thái sản phẩm: có tồn kho = còn hàng, không có tồn kho = hết hàng
            Optional<SanPham> sanPhamOptional = sanPhamRepository.findById(sanPhamId);
            if (sanPhamOptional.isPresent()) {
                SanPham sanPham = sanPhamOptional.get();
                sanPham.setTrangThai(totalStock > 0 ? 1 : 0);
                sanPham.setNgayCapNhat(LocalDate.now());
                sanPham.setNguoiCapNhat("SYSTEM_SYNC");
                
                sanPhamRepository.save(sanPham);
                System.out.println("Đã đồng bộ trạng thái sản phẩm ID " + sanPhamId + ": tồn kho = " + totalStock + ", trạng thái = " + (totalStock > 0 ? "Còn hàng" : "Hết hàng"));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi đồng bộ trạng thái sản phẩm ID " + sanPhamId + ": " + e.getMessage());
        }
    }

}
