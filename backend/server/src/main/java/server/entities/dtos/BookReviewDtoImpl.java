package server.entities.dtos;

import server.entities.dtos.user.ProfileDtoImpl;

public record BookReviewDtoImpl(String review, Integer estimation, ProfileDtoImpl profileDto) implements BookReviewsDto {

    @Override
    public String getReview() {
        return review;
    }

    @Override
    public Integer getEstimation() {
        return estimation;
    }

    @Override
    public ProfileDtoImpl getProfile() {
        return profileDto;
    }
}
