package com.codingdojo.peru.ft2022.bc.controllers;

import com.codingdojo.peru.ft2022.bc.models.Book;
import com.codingdojo.peru.ft2022.bc.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookServ;

    @GetMapping("/books")
    public String books(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/";
        else {
            List<Book> books = bookServ.allBooks();
            model.addAttribute("books", books);
            model.addAttribute("userName", session.getAttribute("userName"));
            return "dashboard.jsp";
        }
    }

    @GetMapping("/books/new")
    public String showNewBookForm(Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/";
        else {
            model.addAttribute("newBook", new Book());
            return "newbook.jsp";
        }
    }

    @PostMapping("/books/new")
    public String saveNewBook(@Valid @ModelAttribute("newBook") Book newBook,
                              BindingResult result, HttpSession session) {
        Book book = bookServ.saveNewBook(newBook, result, session);
        if (result.hasErrors()) return "newbook.jsp";
        else return "redirect:/books";
    }

    @GetMapping("/books/{id}")
    public String showBookDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/";
        else {
            Book book = bookServ.findBookById(id);
            if (book == null) {
                return "redirect:/books";
            } else {
                model.addAttribute("userId", session.getAttribute("userId"));
                model.addAttribute("book", book);
                return "showbook.jsp";
            }
        }
    }

    @GetMapping("/books/{id}/edit")
    public String showEditBookForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/";
        else {
            Book book = bookServ.findBookById(id);
            if ((book == null) || (!bookServ.userAuthorized(session, book))) {
                return "redirect:/books";
            } else {
                model.addAttribute("book", book);
                return "editbook.jsp";
            }
        }
    }

    @PutMapping("/books/{id}")
    public String saveEditBook(@Valid @ModelAttribute("book") Book book,
                               BindingResult result, HttpSession session) {
        Book bookSaved = bookServ.saveNewBook(book, result, session);
        if (result.hasErrors()) return "editbook.jsp";
        else return "redirect:/books";
    }
}
