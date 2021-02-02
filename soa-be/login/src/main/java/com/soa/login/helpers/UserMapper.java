package com.soa.login.helpers;

import com.soa.login.model.UserDto;
import com.soa.login.model.UserEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto Entity2Dto(UserEntity userEntity);

    UserEntity Dto2Entity(UserDto user);
    
    List<UserDto> EntityList2DtoList(List<UserEntity> users);
}