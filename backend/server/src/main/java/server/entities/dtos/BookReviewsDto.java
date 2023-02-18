package server.entities.dtos;

import server.entities.dtos.user.UserDto;

public interface BookReviewsDto extends ReviewDto {
    UserDto getUser();
}
