package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.GioHangResponse;
import com.example.onestep.entity.ChatLieu;
import com.example.onestep.entity.GioHang;
import com.example.onestep.repository.ChatLieuRepository;
import com.example.onestep.repository.GioHangRepository;
import com.example.onestep.service.ChatLieuService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
