package com.example.onestep.service;

import com.example.onestep.dto.response.ChatLieuResponse;
import com.example.onestep.dto.response.ChiTietGioHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatLieuService {
    List<ChatLieuResponse> getAll();

    Page<ChatLieuResponse> phanTrang(Pageable pageable);
}
