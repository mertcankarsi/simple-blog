package com.mertcankarsi.simpleblog.mapper;

import com.mertcankarsi.simpleblog.dto.PostDto;
import com.mertcankarsi.simpleblog.dto.request.PostCreateDto;
import com.mertcankarsi.simpleblog.entity.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  Post toEntity(PostCreateDto dto);

  PostDto toDto(Post entity);

  List<PostDto> toDtoList(List<Post> entities);

  @Mapping(target = "referenceKey", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateEntity(@MappingTarget Post entity, PostDto dto);
}
