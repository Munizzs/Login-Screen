package com.estudos.login_screen.models.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserCreationDTO userCreationDTO);

    User toEntity(UserUpdateDTO userUpdateDTO);

    UserDTO toDTO(User user);
}
