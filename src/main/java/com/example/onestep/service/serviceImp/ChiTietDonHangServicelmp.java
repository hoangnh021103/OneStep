package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChiTietDonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChiTietDonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.repository.ChiTietSanPhamRepository;
import com.example.onestep.repository.DonHangRepository;
import com.example.onestep.service.ChiTietDonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietDonHangServicelmp implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChiTietDonHangResponse> getAll() {
        return chiTietDonHangRepository.findAll().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChiTietDonHangResponse> phanTrang(Pageable pageable) {
        Page<ChiTietDonHang> page = chiTietDonHangRepository.findAll(pageable);
        List<ChiTietDonHangResponse> dtoList = page.getContent().stream()
                .map(ctdh -> modelMapper.map(ctdh, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChiTietDonHangResponse add(ChiTietDonHangDTO dto) {
        ChiTietDonHang entity = modelMapper.map(dto, ChiTietDonHang.class);

        // Thiết lập thông tin mặc định khi thêm mới
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiTao(dto.getNguoiTao());
        entity.setNguoiCapNhat(dto.getNguoiTao()); // ban đầu người cập nhật cũng là người tạo
        entity.setDaXoa(0); // chưa xóa

        // Thiết lập quan hệ nếu cần
        entity.setDonHang(donHangRepository.findById(dto.getDonHangId()).orElse(null));
        entity.setChiTietSanPham(chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId()).orElse(null));

        ChiTietDonHang saved = chiTietDonHangRepository.save(entity);
        return modelMapper.map(saved, ChiTietDonHangResponse.class);
    }

    @Override
    public ChiTietDonHangResponse update(Integer id, ChiTietDonHangDTO dto) {
        Optional<ChiTietDonHang> optional = chiTietDonHangRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChiTietDonHang entity = optional.get();

        // Không dùng modelMapper.map(dto, entity) vì có thể ghi đè cả ID và quan hệ
        // Cập nhật thủ công các trường được phép sửa
        entity.setSoLuong(dto.getSoLuong());
        entity.setDonGia(dto.getDonGia());
        entity.setTongTien(dto.getTongTien());
        entity.setTrangThai(dto.getTrangThai());
        entity.setNgayCapNhat(LocalDate.now());
        entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        entity.setDaXoa(dto.getDaXoa());

        // Chỉ cập nhật quan hệ nếu khác ID
        if (!entity.getDonHang().getId().equals(dto.getDonHangId())) {
            entity.setDonHang(donHangRepository.findById(dto.getDonHangId()).orElse(null));
        }

        if (!entity.getChiTietSanPham().getMaChiTiet().equals(dto.getChiTietSanPhamId())) {
            entity.setChiTietSanPham(chiTietSanPhamRepository.findById(dto.getChiTietSanPhamId()).orElse(null));
        }

        ChiTietDonHang updated = chiTietDonHangRepository.save(entity);
        return modelMapper.map(updated, ChiTietDonHangResponse.class);
    }


    @Override
    public void delete(Integer id) {
        Optional<ChiTietDonHang> optional = chiTietDonHangRepository.findById(id);
        if (optional.isPresent()) {
            ChiTietDonHang entity = optional.get();
            entity.setDaXoa(1); // xóa mềm
            entity.setNgayCapNhat(LocalDate.now());
            entity.setNguoiCapNhat("SYSTEM"); // hoặc lấy từ user hiện tại

            chiTietDonHangRepository.save(entity);
        }
    }

    @Override
    public Optional<ChiTietDonHangResponse> getById(Integer id) {
        return chiTietDonHangRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0) // bỏ qua nếu đã xóa mềm
                .map(entity -> modelMapper.map(entity, ChiTietDonHangResponse.class));
    }
    
    @Override
    public List<ChiTietDonHangResponse> getByDonHangId(Integer donHangId) {
        return chiTietDonHangRepository.findAll().stream()
                .filter(chiTiet -> chiTiet.getDonHang() != null && 
                        chiTiet.getDonHang().getId().equals(donHangId) &&
                        chiTiet.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, ChiTietDonHangResponse.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Map<String, Object>> getByDonHangIdWithProductDetails(Integer donHangId) {
        System.out.println("🔍 Tìm chi tiết đơn hàng cho donHangId: " + donHangId);
        
        List<ChiTietDonHang> chiTietList = chiTietDonHangRepository.findByDonHangId(donHangId);
        System.out.println("📦 Tìm thấy " + chiTietList.size() + " chi tiết đơn hàng");
        
        return chiTietList.stream()
                .map(entity -> {
                    System.out.println("🔍 Xử lý chi tiết đơn hàng ID: " + entity.getId());
                    System.out.println("  - Số lượng: " + entity.getSoLuong());
                    System.out.println("  - Đơn giá: " + entity.getDonGia());
                    System.out.println("  - Chi tiết sản phẩm: " + (entity.getChiTietSanPham() != null ? "Có" : "Null"));
                    Map<String, Object> result = new HashMap<>();
                    
                    // Thông tin cơ bản của chi tiết đơn hàng
                    result.put("id", entity.getId());
                    result.put("donHangId", entity.getDonHang().getId());
                    result.put("soLuong", entity.getSoLuong());
                    result.put("donGia", entity.getDonGia());
                    result.put("tongTien", entity.getTongTien());
                    result.put("trangThai", entity.getTrangThai());
                    
                    // Thông tin chi tiết sản phẩm
                    if (entity.getChiTietSanPham() != null) {
                        Map<String, Object> chiTietSanPham = new HashMap<>();
                        chiTietSanPham.put("maChiTiet", entity.getChiTietSanPham().getMaChiTiet());
                        chiTietSanPham.put("duongDanAnh", entity.getChiTietSanPham().getDuongDanAnh());
                        chiTietSanPham.put("giaTien", entity.getChiTietSanPham().getGiaTien());
                        chiTietSanPham.put("soLuongTon", entity.getChiTietSanPham().getSoLuongTon());
                        
                        // Thông tin sản phẩm
                        if (entity.getChiTietSanPham().getSanPham() != null) {
                            Map<String, Object> sanPham = new HashMap<>();
                            sanPham.put("maSanPham", entity.getChiTietSanPham().getSanPham().getMaSanPham());
                            sanPham.put("tenSanPham", entity.getChiTietSanPham().getSanPham().getTenSanPham());
                            sanPham.put("maCode", entity.getChiTietSanPham().getSanPham().getMaCode());
                            sanPham.put("moTa", entity.getChiTietSanPham().getSanPham().getMoTa());
                            
                            // Thương hiệu
                            if (entity.getChiTietSanPham().getSanPham().getThuongHieu() != null) {
                                Map<String, Object> thuongHieu = new HashMap<>();
                                thuongHieu.put("id", entity.getChiTietSanPham().getSanPham().getThuongHieu().getId());
                                thuongHieu.put("ten", entity.getChiTietSanPham().getSanPham().getThuongHieu().getTen());
                                sanPham.put("thuongHieu", thuongHieu);
                            }
                            
                            // Chất liệu
                            if (entity.getChiTietSanPham().getSanPham().getChatLieu() != null) {
                                Map<String, Object> chatLieu = new HashMap<>();
                                chatLieu.put("id", entity.getChiTietSanPham().getSanPham().getChatLieu().getId());
                                chatLieu.put("ten", entity.getChiTietSanPham().getSanPham().getChatLieu().getTen());
                                sanPham.put("chatLieu", chatLieu);
                            }
                            
                            chiTietSanPham.put("sanPham", sanPham);
                        }
                        
                        // Kích cỡ
                        if (entity.getChiTietSanPham().getKichCo() != null) {
                            Map<String, Object> kichCo = new HashMap<>();
                            kichCo.put("id", entity.getChiTietSanPham().getKichCo().getId());
                            kichCo.put("ten", entity.getChiTietSanPham().getKichCo().getTen());
                            chiTietSanPham.put("kichCo", kichCo);
                        }
                        
                        // Màu sắc
                        if (entity.getChiTietSanPham().getMauSac() != null) {
                            Map<String, Object> mauSac = new HashMap<>();
                            mauSac.put("id", entity.getChiTietSanPham().getMauSac().getId());
                            mauSac.put("ten", entity.getChiTietSanPham().getMauSac().getTen());
                            chiTietSanPham.put("mauSac", mauSac);
                        }
                        
                        result.put("chiTietSanPham", chiTietSanPham);
                    }
                    
                    return result;
                })
                .collect(Collectors.toList());
    }

}
