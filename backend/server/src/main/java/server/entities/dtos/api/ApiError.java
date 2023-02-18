package server.entities.dtos.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private List<String> messages;

    public ApiError(String message) {
        messages = Collections.singletonList(message);
    }
}
