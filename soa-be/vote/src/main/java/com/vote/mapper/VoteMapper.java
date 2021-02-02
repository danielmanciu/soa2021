package com.vote.mapper;

import com.vote.dto.VoteDto;
import com.vote.entity.VoteEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    VoteDto Entity2Dto(VoteEntity dao);

    VoteEntity Dto2Entity(VoteDto dto);

    List<VoteDto> EntityList2DtoList(List<VoteEntity> daos);
}
