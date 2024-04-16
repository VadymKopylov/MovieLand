package com.kopylov.movieland.mapper;

import com.kopylov.movieland.dto.UserDto;
import com.kopylov.movieland.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
