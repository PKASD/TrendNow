package com.example.trend_project.service;

import com.example.trend_project.dto.GalleryDto;
import com.example.trend_project.entity.board.GalleryEntity;
import com.example.trend_project.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GalleryService {
    private final GalleryRepository galleryRepository;

    public GalleryDto save(GalleryDto dto) {
        return entityToDto(galleryRepository.save(dtoToEntity(dto)));
    }

    public GalleryDto findByName(String name) {
        var find = galleryRepository.findByName(name).orElse(null);
        return entityToDto(find);
    }

    private GalleryDto entityToDto(GalleryEntity entity) {
        var dto = GalleryDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .build();

        return dto;
    }

    private GalleryEntity dtoToEntity(GalleryDto dto) {
        var entity = GalleryEntity.builder()
                .idx(dto.getIdx())
                .name(dto.getName())
                .build();

        return entity;
    }
}
