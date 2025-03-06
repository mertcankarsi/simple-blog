package com.mertcankarsi.simpleblog.mapper;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);
    
    @Mapping(target = "referenceKey", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toEntity(PostDto dto);
    
    PostDto toDto(Post entity);
    
    List<PostDto> toDtoList(List<Post> entities);
    
    @Mapping(target = "referenceKey", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Post entity, PostDto dto);
} 