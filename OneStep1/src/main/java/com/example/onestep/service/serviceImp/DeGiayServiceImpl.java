package com.example.onestep.service.serviceImp;

import com.example.onestep.dto.request.DeGiayDTO;
import com.example.onestep.dto.response.DeGiayResponse;
import com.example.onestep.entity.DeGiay;
import com.example.onestep.repository.DeGiayRepository;
import com.example.onestep.service.DeGiayService;
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
public class DeGiayServiceImpl implements DeGiayService {
    @Autowired
    private DeGiayRepository deGiayRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DeGiayResponse> getAll() {
        return deGiayRepository.findAll().stream()
                .filter(dg -> dg.getDaXoa() == 0)
                .map(dg -> modelMapper.map(dg, DeGiayResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DeGiayResponse> phanTrang(Pageable pageable) {
        Page<DeGiay> page = deGiayRepository.findAll(pageable);
        List<DeGiayResponse> dtoList = page.getContent().stream()
                .filter(dg -> dg.getDaXoa() == 0)
                .map(dg -> modelMapper.map(dg, DeGiayResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    @Override
    public DeGiayResponse add(DeGiayDTO dto) {
        DeGiay entity = modelMapper.map(dto, DeGiay.class);
        entity.setNgayCapNhat(LocalDate.now());
        entity.setDaXoa(0);
        DeGiay saved = deGiayRepository.save(entity);
        return modelMapper.map(saved, DeGiayResponse.class);
    }

    @Override
    public DeGiayResponse update(Integer id, DeGiayDTO dto) {
        Optional<DeGiay> optional = deGiayRepository.findById(id);
        if (optional.isEmpty() || optional.get().getDaXoa() == 1) return null;

        DeGiay entity = optional.get();
        modelMapper.map(dto, entity);
        entity.setNgayCapNhat(LocalDate.now());

        DeGiay updated = deGiayRepository.save(entity);
        return modelMapper.map(updated, DeGiayResponse.class);
    }

    @Override
    public void delete(Integer id) {
        Optional<DeGiay> optional = deGiayRepository.findById(id);
        if (optional.isPresent() && optional.get().getDaXoa() == 0) {
            DeGiay entity = optional.get();
            entity.setDaXoa(1);
            entity.setNgayCapNhat(LocalDate.now());
            deGiayRepository.save(entity);
        }
    }

    @Override
    public Optional<DeGiayResponse> getById(Integer id) {
        return deGiayRepository.findById(id)
                .filter(entity -> entity.getDaXoa() == 0)
                .map(entity -> modelMapper.map(entity, DeGiayResponse.class));
    }
}
