package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import server.entities.PersonalBook;
import server.entities.User;
import server.entities.dtos.PersonalBookDto;
import server.mappers.PersonalBookMapper;
import server.services.BooksService;
import server.services.PersonalBooksService;
import server.services.UsersService;

import java.security.Principal;
import java.util.List;

@RestController
// TODO: 17.11.2022 remove on production
@CrossOrigin("*")
@RequestMapping("/api/v1/library")
public class PersonalBooksController {

    private final PersonalBooksService personalBooksService;

    private final UsersService usersService;
    private final BooksService booksService;

    @Autowired
    PersonalBooksController(PersonalBooksService personalBooksService, UsersService usersService, BooksService booksService) {
        this.personalBooksService = personalBooksService;
        this.usersService = usersService;
        this.booksService = booksService;
    }

    @GetMapping(value = "", produces = "application/json")
    public List<PersonalBookDto> getPersonalBooks(Principal principal) {
        String phoneOrEmail = principal.getName();
        List<PersonalBook> personalBooks = personalBooksService.getPersonalBooks(phoneOrEmail, phoneOrEmail);
        return PersonalBookMapper.PERSONAL_BOOK_MAPPER.toDtoList(personalBooks);

    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    // TODO: 04.04.2023 return PersonalBookDto 
    public ResponseEntity<PersonalBook> addPersonalBook(Principal principal, @RequestBody PersonalBook personalBook) {
        if (principal == null) {
            throw new AuthenticationCredentialsNotFoundException("Для совершения этого дейставия необходимо авторизоваться.");
        }
        // TODO: 03.04.2023 maybe put throwing exception into service
        User user = usersService.getUserByPhoneOrEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не существует"));
        personalBook.setPhone(user.getPhone());
        personalBook.setEmail(user.getEmail());
        personalBooksService.saveOrUpdate(personalBook);
        return ResponseEntity.ok(personalBook);
    }
}
