package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.ReviewDto;
import com.kopylov.movieland.entity.Review;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ReviewMapper {

    ReviewDto toDto(Review review);

    Review toEntity(ReviewDto dto);

    List<ReviewDto> toDtoList(List<Review> entities);

    List<Review> toEntityList(List<ReviewDto> dtos);
}