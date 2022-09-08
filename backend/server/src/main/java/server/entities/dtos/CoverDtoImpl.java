package server.entities.dtos;

public record CoverDtoImpl(String path, String extension) implements CoverDto {
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getExtension() {
        return extension;
    }
}
