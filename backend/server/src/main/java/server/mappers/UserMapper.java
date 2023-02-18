package server.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import server.entities.User;
import server.entities.dtos.user.ProfileDtoImpl;
import server.entities.dtos.user.UserDtoImpl;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDtoImpl toDto(User user);

    ProfileDtoImpl toProfileDto(User user);

}
