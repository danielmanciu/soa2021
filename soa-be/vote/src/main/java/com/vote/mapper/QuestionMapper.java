package com.vote.mapper;

import com.vote.dto.QuestionDto;
import com.vote.entity.QuestionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    QuestionDto Entity2Dto(QuestionEntity dao);

    QuestionEntity Dto2Entity(QuestionDto dto);

    List<QuestionDto> EntityList2DtoList(List<QuestionEntity> daos);
}
