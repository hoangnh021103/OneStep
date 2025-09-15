package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.NhanVienDTO;
import com.example.onestep.dto.response.NhanVienResponse;
import com.example.onestep.entity.NhanVien;
import com.example.onestep.entity.VaiTro;
import com.example.onestep.repository.NhanVienRepository;
import com.example.onestep.service.NhanVienService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NhanVienServicelmp implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    private NhanVienResponse mapToResponse(NhanVien nv) {
        NhanVienResponse res = new NhanVienResponse();
        res.setId(nv.getId());
        res.setHoTen(nv.getHoTen());
        res.setNgaySinh(nv.getNgaySinh());
        res.setGioiTinh(nv.getGioiTinh());
        res.setEmail(nv.getEmail());
        res.setSoDienThoai(nv.getSoDienThoai());
        res.setDiaChi(nv.getDiaChi());
        res.setVaiTro(nv.getVaiTro() != null ? nv.getVaiTro().getTenVaiTro() : null);
        res.setNgayTao(nv.getNgayTao());
        res.setNgayCapNhat(nv.getNgayCapNhat());
        res.setDaXoa(nv.getDaXoa());
        return res;
    }

    @Override
    public List<NhanVienResponse> getAll() {
        return nhanVienRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public Page<NhanVienResponse> phanTrang(Pageable pageable) {
        return nhanVienRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public Optional<NhanVienResponse> getById(Integer id) {
        return nhanVienRepository.findById(id).map(this::mapToResponse);
    }

    @Override
    public NhanVienResponse add(NhanVienDTO dto) {
        NhanVien nv = new NhanVien();
        nv.setHoTen(dto.getHoTen());
        nv.setNgaySinh(dto.getNgaySinh());
        nv.setGioiTinh(dto.getGioiTinh());
        nv.setEmail(dto.getEmail());
        nv.setSoDienThoai(dto.getSoDienThoai());
        nv.setDiaChi(dto.getDiaChi());
        nv.setNgayTao(LocalDateTime.now());
        nv.setNgayCapNhat(LocalDateTime.now());
        nv.setDaXoa(false);

        if (dto.getVaiTroId() != null) {
            VaiTro vaiTro = new VaiTro();
            vaiTro.setId(dto.getVaiTroId());
            nv.setVaiTro(vaiTro);
        }

        return mapToResponse(nhanVienRepository.save(nv));
    }

    @Override
    public NhanVienResponse update(Integer id, NhanVienDTO dto) {
        Optional<NhanVien> opt = nhanVienRepository.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy nhân viên với id " + id);
        }

        NhanVien nv = opt.get();
        nv.setHoTen(dto.getHoTen());
        nv.setNgaySinh(dto.getNgaySinh());
        nv.setGioiTinh(dto.getGioiTinh());
        nv.setEmail(dto.getEmail());
        nv.setSoDienThoai(dto.getSoDienThoai());
        nv.setDiaChi(dto.getDiaChi());
        nv.setNgayCapNhat(LocalDateTime.now());

        if (dto.getVaiTroId() != null) {
            VaiTro vaiTro = new VaiTro();
            vaiTro.setId(dto.getVaiTroId());
            nv.setVaiTro(vaiTro);
        }

        return mapToResponse(nhanVienRepository.save(nv));
    }

    @Override
    public void delete(Integer id) {
        Optional<NhanVien> opt = nhanVienRepository.findById(id);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy nhân viên với id " + id);
        }
        NhanVien nv = opt.get();
        nv.setDaXoa(true); // xóa mềm
        nv.setNgayCapNhat(LocalDateTime.now());
        nhanVienRepository.save(nv);
    }
}
