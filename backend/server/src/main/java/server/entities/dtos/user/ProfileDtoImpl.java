package server.entities.dtos.user;

public record ProfileDtoImpl(Long id, String firstName, String lastName) implements ProfileDto {

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}
