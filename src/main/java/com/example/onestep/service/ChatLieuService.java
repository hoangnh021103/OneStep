package com.example.onestep.service;

import com.example.onestep.dto.request.ChatLieuDTO;
import com.example.onestep.dto.request.SanPhamDTO;
import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import com.example.onestep.dto.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChatLieuService {
    List<ChatLieuResponse> getAll();

    Page<ChatLieuResponse> phanTrang(Pageable pageable);

    ChatLieuResponse add(ChatLieuDTO chatLieuDTO);

    ChatLieuResponse update(Integer id, ChatLieuDTO chatLieuDTO);

    void delete(Integer id);

    Optional<ChatLieuResponse> getById(Integer id);
}
