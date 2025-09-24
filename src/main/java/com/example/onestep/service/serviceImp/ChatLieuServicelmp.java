package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.ChatLieuDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import com.example.onestep.entity.ChatLieu;
import com.example.onestep.entity.GioHang;
import com.example.onestep.entity.SanPham;
import com.example.onestep.repository.ChatLieuRepository;
import com.example.onestep.repository.GioHangRepository;
import com.example.onestep.service.ChatLieuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatLieuServicelmp implements ChatLieuService {
    @Autowired
    private ChatLieuRepository chatLieuRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ChatLieuResponse> getAll() {
        return chatLieuRepository.findAll().stream()
                .map(ch -> modelMapper.map(ch, ChatLieuResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ChatLieuResponse> phanTrang(Pageable pageable) {
        Page<ChatLieu> page = chatLieuRepository.findAll(pageable);
        List<ChatLieuResponse> dtoList = page.getContent().stream()
                .map(ch -> modelMapper.map(ch, ChatLieuResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public ChatLieuResponse add(ChatLieuDTO dto) {
        ChatLieu entity = modelMapper.map(dto, ChatLieu.class);
        entity.setNgayCapNhat(LocalDate.now());
        ChatLieu saved = chatLieuRepository.save(entity);
        return modelMapper.map(saved, ChatLieuResponse.class);
    }

    @Override
    public ChatLieuResponse update(Integer id, ChatLieuDTO dto) {
        Optional<ChatLieu> optional = chatLieuRepository.findById(id);
        if (optional.isEmpty()) return null;

        ChatLieu entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        ChatLieu updated = chatLieuRepository.save(entity);
        return modelMapper.map(updated, ChatLieuResponse.class);
    }

    @Override
    public void delete(Integer id) {
        if (chatLieuRepository.existsById(id)) {
            chatLieuRepository.deleteById(id);
        }
    }

    @Override
    public Optional<ChatLieuResponse> getById(Integer id) {
        return chatLieuRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ChatLieuResponse.class));
    }
}
