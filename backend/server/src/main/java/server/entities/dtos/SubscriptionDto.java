package server.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: 17.11.2022 include in project
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private List<Long> usersId;
    private List<Long> booksId;
}
