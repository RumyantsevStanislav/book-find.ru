package server.entities.dtos;

import java.io.Serializable;

public record PersonalBookDto(String status, Integer estimation, String comment,
                              BookDtoImpl bookDtoImpl) implements Serializable {
}
