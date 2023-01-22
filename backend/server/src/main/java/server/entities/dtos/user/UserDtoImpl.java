package server.entities.dtos.user;

public record UserDtoImpl(String phone, String email, String firstName, String lastName) implements UserDto {

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
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
