package server.controllers;

import server.entities.Category;
import server.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import server.entities.Book;
import server.services.BooksService;
import server.utils.BookFilter;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {
    private BooksService booksService;
    private CategoriesService categoriesService;

    @Autowired
    public BooksController(BooksService booksService, CategoriesService categoriesService) {
        this.booksService = booksService;
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String showAll(Model model, @RequestParam Map<String, String> requestParams, @RequestParam(name = "categories", required = false) List<Long> categoriesIds) {
        List<Category> categoriesFilter = null;
        if (categoriesIds != null) {
            categoriesFilter = categoriesService.getCategoriesByIds(categoriesIds);
        }
        Integer pageNumber = Integer.parseInt(requestParams.getOrDefault("p", "1"));
        BookFilter bookFilter = new BookFilter(requestParams, categoriesFilter);
        Page<Book> books = booksService.findAll(bookFilter.getSpec(), pageNumber);
        model.addAttribute("books", books);
        model.addAttribute("filterDef", bookFilter.getFilterDefinition().toString());
        return "all_books";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add_book_form";
    }

    @PostMapping("/add")
    public String saveNewBook(@ModelAttribute Book newBook) {
        booksService.saveOrUpdate(newBook);
        return "redirect:/books/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", booksService.findById(id));
        return "edit_book_form";
    }

    @PostMapping("/edit")
    public String modifyBook(@ModelAttribute Book modifiedBook) {
        booksService.saveOrUpdate(modifiedBook);
        return "redirect:/books/";
    }
}

