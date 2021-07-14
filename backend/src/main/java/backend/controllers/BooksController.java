package backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import backend.entities.Book;
import backend.repositories.specifications.BookSpecifications;
import backend.services.BooksService;
import backend.utils.BookFilter;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public String showAllBooks(Model model, @RequestParam Map<String, String> requestParams) {
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        BookFilter bookFilter = new BookFilter(requestParams);
        Page<Book> books = booksService.findAll(bookFilter.getSpec(), pageNumber);
        model.addAttribute("filterDef", bookFilter.getFilterDefinition().toString());
        return "all_books";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_book_form";
    }

    @PostMapping("/add")
    public String saveNewStudent(@ModelAttribute Book newBook) {
        booksService.saveOrUpdate(newBook);
        return "redirect:/books/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "edit_boor_form";
    }

    @PostMapping("/edit")
    public String modifyBook(@ModelAttribute Book modifiedBook) {
        booksService.saveOrUpdate(modifiedBook);
        return "redirect:/books/";
    }
}

