package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.DonHangDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.DiaChiResponse;
import com.example.onestep.dto.response.DonHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.DiaChi;
import com.example.onestep.entity.DonHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.entity.ChiTietDonHang;
import com.example.onestep.repository.DiaChiRepository;
import com.example.onestep.repository.DonHangRepository;
import com.example.onestep.repository.ChiTietDonHangRepository;
import com.example.onestep.service.DonHangService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class DonHangServicelmp implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;
    
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DonHangResponse> getAll() {
        return donHangRepository.findAllWithKhachHang().stream()
                .map(this::mapToDonHangResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DonHangResponse> phanTrang(Pageable pageable) {
        Page<DonHang> page = donHangRepository.findAllWithKhachHangPaged(pageable);
        List<DonHangResponse> dtoList = page.getContent().stream()
                .map(this::mapToDonHangResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public DonHangResponse add(DonHangDTO dto) {
        System.out.println("=== DEBUG: DonHangService.add() ===");
        System.out.println("DTO loaiDon: " + dto.getLoaiDon());
        
        DonHang entity = modelMapper.map(dto, DonHang.class);
        System.out.println("Entity loaiDon sau khi map: " + entity.getLoaiDon());
        
        // QUAN TRỌNG: Đảm bảo loaiDon được set đúng từ DTO
        // ModelMapper có thể bỏ qua hoặc override giá trị này
        if (dto.getLoaiDon() != null) {
            entity.setLoaiDon(dto.getLoaiDon());
            System.out.println("🔧 Đã force set loaiDon từ DTO: " + dto.getLoaiDon());
        }
        
        // ✅ LOGIC MỚI: Tự động set trạng thái "Hoàn thành" cho đơn hàng tại quầy
        if (entity.getLoaiDon() != null && entity.getLoaiDon() == 0) {
            entity.setTrangThai(5); // 5 = Hoàn thành
            System.out.println("🏪 Đơn hàng tại quầy - Tự động set trạng thái = 5 (Hoàn thành)");
        } else {
            // Đối với đơn hàng online, giữ nguyên trạng thái từ DTO hoặc mặc định là 1
            if (entity.getTrangThai() == null) {
                entity.setTrangThai(1); // 1 = Chờ xác nhận (mặc định cho online)
                System.out.println("🌐 Đơn hàng online - Set trạng thái mặc định = 1 (Chờ xác nhận)");
            }
        }
        
        // Đảm bảo ngày tạo được set đúng cho từng đơn hàng với độ chính xác cao
        // Thêm một chút delay để tránh trùng timestamp
        LocalDateTime now = LocalDateTime.now().withNano((int) (System.nanoTime() % 1_000_000_000));
        entity.setNgayCapNhat(now);
        
        // Debug: Log thời gian được set
        System.out.println("🕒 Set ngayCapNhat: " + now + " for order: " + entity.getMaDon());
        
        // Nếu chưa có nguoi_tao, set mặc định
        if (entity.getNguoiTao() == null || entity.getNguoiTao().isEmpty()) {
            entity.setNguoiTao("system");
        }
        
        System.out.println("Entity ngayCapNhat: " + entity.getNgayCapNhat());
        System.out.println("Entity loaiDon trước khi save: " + entity.getLoaiDon());
        System.out.println("Entity trangThai trước khi save: " + entity.getTrangThai());
        
        DonHang saved = donHangRepository.save(entity);
        
        System.out.println("Entity loaiDon sau khi save: " + saved.getLoaiDon());
        System.out.println("Entity trangThai sau khi save: " + saved.getTrangThai());
        System.out.println("Entity ngayCapNhat sau khi save: " + saved.getNgayCapNhat());
        
        DonHangResponse response = modelMapper.map(saved, DonHangResponse.class);
        System.out.println("Response loaiDon: " + response.getLoaiDon());
        System.out.println("Response trangThai: " + response.getTrangThai());
        System.out.println("Response ngayCapNhat: " + response.getNgayCapNhat());
        
        return response;
    }

    @Override
    public DonHangResponse update(Integer id, DonHangDTO dto) {
        System.out.println("=== DEBUG: DonHangService.update() ===");
        System.out.println("ID: " + id);
        System.out.println("DTO trangThai: " + dto.getTrangThai());
        
        Optional<DonHang> optional = donHangRepository.findById(id);
        if (optional.isEmpty()) {
            System.out.println("❌ Không tìm thấy đơn hàng với ID: " + id);
            return null;
        }

        DonHang entity = optional.get();
        System.out.println("Entity trangThai trước khi update: " + entity.getTrangThai());
        
        // Cập nhật thủ công các trường thay vì dùng ModelMapper.map()
        // để tránh ghi đè các trường không mong muốn
        // Note: Không cập nhật khachHangId vì đây là quan hệ ManyToOne
        if (dto.getHoTen() != null) entity.setHoTen(dto.getHoTen());
        if (dto.getSoDienThoai() != null) entity.setSoDienThoai(dto.getSoDienThoai());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getTongTienGoc() != null) entity.setTongTienGoc(dto.getTongTienGoc());
        if (dto.getTienGiam() != null) entity.setTienGiam(dto.getTienGiam());
        if (dto.getTongTien() != null) entity.setTongTien(dto.getTongTien());
        if (dto.getTienShip() != null) entity.setTienShip(dto.getTienShip());
        if (dto.getLoaiDon() != null) entity.setLoaiDon(dto.getLoaiDon());
        if (dto.getGhiChu() != null) entity.setGhiChu(dto.getGhiChu());
        if (dto.getDiaChiGiaoHang() != null) entity.setDiaChiGiaoHang(dto.getDiaChiGiaoHang());
        if (dto.getMaDon() != null) entity.setMaDon(dto.getMaDon());
        if (dto.getNguoiTao() != null) entity.setNguoiTao(dto.getNguoiTao());
        if (dto.getNguoiCapNhat() != null) entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        if (dto.getDaXoa() != null) entity.setDaXoa(dto.getDaXoa());
        
        // ✅ LOGIC MỚI: Cập nhật trạng thái - NGĂN CHẶN thay đổi trạng thái đơn hàng tại quầy
        if (dto.getTrangThai() != null) {
            // Kiểm tra xem đây có phải đơn hàng tại quầy không
            if (entity.getLoaiDon() != null && entity.getLoaiDon() == 0) {
                // Đơn hàng tại quầy - KHÔNG CHO PHÉP thay đổi trạng thái
                System.out.println("🚫 NGĂN CHẶN: Không thể thay đổi trạng thái đơn hàng tại quầy (loaiDon=0)");
                System.out.println("🏪 Đơn hàng tại quầy luôn giữ trạng thái = 5 (Hoàn thành)");
                entity.setTrangThai(5); // Force về trạng thái hoàn thành
            } else {
                // Đơn hàng online - CHO PHÉP thay đổi trạng thái bình thường
                System.out.println("🔄 Cập nhật trạng thái từ " + entity.getTrangThai() + " sang " + dto.getTrangThai());
                entity.setTrangThai(dto.getTrangThai());
            }
        }
        
        // Cập nhật ngày xác nhận nếu có
        if (dto.getNgayXacNhan() != null) entity.setNgayXacNhan(dto.getNgayXacNhan());
        
        // Chỉ cập nhật ngày cập nhật khi có thay đổi thực sự với timestamp unique
        LocalDateTime updateTime = LocalDateTime.now().withNano((int) (System.nanoTime() % 1_000_000_000));
        entity.setNgayCapNhat(updateTime);
        
        System.out.println("🕒 Update ngayCapNhat: " + updateTime + " for order: " + entity.getMaDon());
        
        // Cập nhật người cập nhật
        if (dto.getNguoiCapNhat() != null && !dto.getNguoiCapNhat().isEmpty()) {
            entity.setNguoiCapNhat(dto.getNguoiCapNhat());
        } else {
            entity.setNguoiCapNhat("admin");
        }
        
        System.out.println("Entity trangThai sau khi update: " + entity.getTrangThai());

        DonHang updated = donHangRepository.save(entity);
        System.out.println("✅ Lưu thành công, trangThai cuối cùng: " + updated.getTrangThai());
        
        return modelMapper.map(updated, DonHangResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (donHangRepository.existsById(id)) {
            donHangRepository.deleteById(id);
        }
    }

    @Override
    public Optional<DonHangResponse> getById(Integer id) {
        return donHangRepository.findById(id)
                .map(this::mapToDonHangResponse);
    }
    
    private DonHangResponse mapToDonHangResponse(DonHang donHang) {
        DonHangResponse response = modelMapper.map(donHang, DonHangResponse.class);
        
        // Debug logging cho trangThai
        System.out.println("🔍 mapToDonHangResponse - Entity trangThai: " + donHang.getTrangThai() + " (" + (donHang.getTrangThai() != null ? donHang.getTrangThai().getClass().getSimpleName() : "null") + ")");
        System.out.println("🔍 mapToDonHangResponse - Response trangThai: " + response.getTrangThai() + " (" + (response.getTrangThai() != null ? response.getTrangThai().getClass().getSimpleName() : "null") + ")");
        
        // Nếu có khách hàng được chọn, sử dụng thông tin từ khách hàng
        if (donHang.getKhachHang() != null) {
            response.setHoTen(donHang.getKhachHang().getHoTen());
            response.setSoDienThoai(donHang.getKhachHang().getSoDienThoai());
            response.setEmail(donHang.getKhachHang().getEmail());
            response.setKhachHangId(donHang.getKhachHang().getId());
        }
        // Nếu không có khách hàng, sử dụng thông tin đã lưu trong đơn hàng
        // (hoTen, soDienThoai, email đã được set từ DonHangDTO)
        
        // Tính toán thông tin sản phẩm trong đơn hàng bằng query hiệu quả
        try {
            Integer tongSoLuong = chiTietDonHangRepository.countTotalQuantityByDonHangId(donHang.getId());
            Integer soLoaiSanPham = chiTietDonHangRepository.countProductTypesByDonHangId(donHang.getId());
            
            response.setSoLuongSanPham(tongSoLuong != null ? tongSoLuong : 0);
            response.setSoLoaiSanPham(soLoaiSanPham != null ? soLoaiSanPham : 0);
            
            System.out.println("📊 Đơn hàng " + donHang.getMaDon() + ": " + tongSoLuong + " sản phẩm (" + soLoaiSanPham + " loại)");
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi tính toán thông tin sản phẩm cho đơn hàng " + donHang.getMaDon() + ": " + e.getMessage());
            response.setSoLuongSanPham(0);
            response.setSoLoaiSanPham(0);
        }
        
        return response;
    }
    
    @Override
    public List<DonHangResponse> getByKhachHangId(Integer khachHangId, Integer loaiDon) {
        // QUAN TRỌNG: Lọc đơn hàng theo khách hàng và loại đơn
        // loaiDon = 0: OFFLINE (từ admin)
        // loaiDon = 1: ONLINE (từ trangchu)
        return donHangRepository.findAll().stream()
                .filter(donHang -> {
                    // QUAN TRỌNG: Lọc theo loại đơn trước (bắt buộc)
                    boolean matchLoaiDon = false;
                    if (loaiDon != null) {
                        // Nếu có chỉ định loaiDon, chỉ lấy đơn hàng có loaiDon khớp
                        matchLoaiDon = donHang.getLoaiDon() != null && donHang.getLoaiDon().equals(loaiDon);
                    } else {
                        // Nếu không chỉ định, lấy tất cả
                        matchLoaiDon = true;
                    }
                    
                    // Không lấy đơn hàng đã xóa
                    boolean notDeleted = donHang.getDaXoa() == 0;
                    
                    // Lọc theo khách hàng (linh hoạt hơn)
                    boolean matchKhachHang = true; // Mặc định là true
                    if (khachHangId != null) {
                        // Nếu có khachHangId, kiểm tra khớp với khachHang trong đơn hàng
                        // HOẶC với thông tin khách hàng trong đơn hàng (email, sdt)
                        matchKhachHang = (donHang.getKhachHang() != null && 
                                         donHang.getKhachHang().getId().equals(khachHangId));
                                         
                        // Nếu không khớp với khachHang entity, có thể đơn hàng được tạo với thông tin trực tiếp
                        // trong trường hợp này vẫn có thể hiển thị nếu là đơn online
                        if (!matchKhachHang && loaiDon != null && loaiDon == 1) {
                            // Đối với đơn hàng ONLINE, cho phép hiển thị ngay cả khi khachHangId không khớp
                            // vì có thể đơn hàng được tạo với khachHangId = null
                            matchKhachHang = true;
                            System.out.println("🔄 Cho phép hiển thị đơn hàng ONLINE không có khachHang entity: " + donHang.getMaDon());
                        }
                    } else {
                        // QUAN TRỌNG: Nếu khachHangId = null (gọi từ /tat-ca-online), 
                        // hiển thị TẤT CẢ đơn hàng ONLINE để debug
                        matchKhachHang = true;
                        if (loaiDon != null && loaiDon == 1) {
                            System.out.println("🔧 DEBUG: Hiển thị tất cả đơn hàng ONLINE: " + donHang.getMaDon());
                        }
                    }
                    
                    // Debug log chi tiết hơn
                    if (donHang.getMaDon() != null) {
                        System.out.println("Filtering: " + donHang.getMaDon() + 
                            " - loaiDon=" + donHang.getLoaiDon() + 
                            " - matchLoaiDon=" + matchLoaiDon +
                            " - khachHangId=" + (donHang.getKhachHang() != null ? donHang.getKhachHang().getId() : "null") +
                            " - requestKhachHangId=" + khachHangId +
                            " - matchKhachHang=" + matchKhachHang +
                            " - email=" + donHang.getEmail() +
                            " - sdt=" + donHang.getSoDienThoai());
                    }
                    
                    return matchKhachHang && matchLoaiDon && notDeleted;
                })
                .sorted((a, b) -> {
                    // Sắp xếp theo ngày cập nhật mới nhất trước
                    if (a.getNgayCapNhat() != null && b.getNgayCapNhat() != null) {
                        return b.getNgayCapNhat().compareTo(a.getNgayCapNhat());
                    }
                    return b.getId().compareTo(a.getId());
                })
                .map(this::mapToDonHangResponse)
                .collect(Collectors.toList());
    }
}
