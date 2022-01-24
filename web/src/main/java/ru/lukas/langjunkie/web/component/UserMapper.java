package ru.lukas.langjunkie.web.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.lukas.langjunkie.web.dto.CreateUserDto;
import ru.lukas.langjunkie.web.model.User;

/**
 * @author Dmitry Lukashevich
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", source = "dto.fullname")
    User toUserModel(CreateUserDto dto);
}
