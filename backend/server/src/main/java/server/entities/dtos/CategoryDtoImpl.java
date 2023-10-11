package server.entities.dtos;

public record CategoryDtoImpl(String title) implements CategoryDto {

    @Override
    public String getTitle() {
        return title;
    }
}
