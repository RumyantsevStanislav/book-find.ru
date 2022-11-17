package server.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    // TODO: 17.11.2022 add expired date
    private String token;
}
