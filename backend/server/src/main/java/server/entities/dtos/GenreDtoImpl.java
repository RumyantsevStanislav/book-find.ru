package server.entities.dtos;

public record GenreDtoImpl(String path) implements GenreDto {

    @Override
    public String getPath() {
        return path;
    }
}
