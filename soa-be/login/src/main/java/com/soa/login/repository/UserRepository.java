package com.soa.login.repository;

import com.soa.login.model.UserDto;
import com.soa.login.model.UserEntity;
import com.soa.login.helpers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    final private UserJpaRepository userJpaRepository;
    final private UserMapper userMapper;

    @Autowired
    public UserRepository(final UserJpaRepository userJpaRepository, final UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserById(final Long id){
        UserEntity dao = userJpaRepository.getOne(id);
        return userMapper.Entity2Dto(dao);
    }

    public UserDto save(final UserDto userToSave){
        UserEntity savedUser = userJpaRepository.save(userMapper.Dto2Entity(userToSave));
        return userMapper.Entity2Dto(savedUser);
    }

    public List<UserDto> findAll(){
        List<UserEntity> allUsers = userJpaRepository.findAll();
        return userMapper.EntityList2DtoList(allUsers);
    }

    public UserDto findByUserName(final String userName){
        UserEntity user = userJpaRepository.findByUserName(userName);
        return userMapper.Entity2Dto(user);
    }
}
