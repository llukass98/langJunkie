package ru.lukas.langjunkie.web.component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.lukas.langjunkie.web.dto.UserDto;
import ru.lukas.langjunkie.web.model.User;

/**
 * @author Dmitry Lukashevich
 */
@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface UserMapper {

    @Mapping(target = "fullname", source = "entity.fullName")
    UserDto toUserDto(User entity);

    @Mapping(target = "fullName", source = "dto.fullname")
    User toUserModel(UserDto dto);
}
