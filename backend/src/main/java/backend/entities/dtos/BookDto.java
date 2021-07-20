package backend.entities.dtos;

public interface BookDto {
    Long getId();

//    @Size(min = 4, message = "Title too short")
//    @Min(value = 1, message = "Cannot be negative or zero")
    String getTitle();
}
