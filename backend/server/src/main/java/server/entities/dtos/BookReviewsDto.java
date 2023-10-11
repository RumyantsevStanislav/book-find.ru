package server.entities.dtos;

import server.entities.dtos.user.ProfileDto;

public interface BookReviewsDto extends ReviewDto {
    ProfileDto getProfile();
}
