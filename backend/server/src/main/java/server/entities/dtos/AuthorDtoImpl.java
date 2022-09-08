package server.entities.dtos;

public record AuthorDtoImpl(String name, String role) implements AuthorDto {
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRole() {
        return role;
    }
}
