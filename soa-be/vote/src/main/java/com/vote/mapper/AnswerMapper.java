package com.vote.mapper;


import com.vote.dto.AnswerDto;
import com.vote.entity.AnswerEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerDto Entity2Dto(AnswerEntity dao);

    AnswerEntity Dto2Entity(AnswerDto dto);

    List<AnswerDto> EntityList2DtoList(List<AnswerEntity> daos);
}
