package server.entities.dtos;

public record PublisherDtoImpl(String title, String description) implements PublisherDto {

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
