package com.mitrais.javabootcamp.controller;

import com.mitrais.javabootcamp.model.Book;
import com.mitrais.javabootcamp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public ModelAndView getListPage() {
        ModelAndView mv = new ModelAndView("book/list");
        List<Book> bookList = bookService.findAll();
        mv.addObject("bookList", bookList);
        return mv;
    }

    @GetMapping("")
    public String getFormPage(Book book) {
        return "book/form";
    }

    @GetMapping("/{bookId}/delete")
    public String deleteBook(@PathVariable("bookId") Long bookId, RedirectAttributes redirectAttributes) {
        bookService.delete(bookId);
        redirectAttributes.addFlashAttribute("successMsg", "Successfully delete book data");
        return "redirect:/books/list";
    }

    @GetMapping("/{bookId}")
    public ModelAndView getUpdateForm(@PathVariable("bookId") Long bookId) {
        Book existingBook = bookService.findOne(bookId);
        ModelAndView mv = new ModelAndView("book/form");
        mv.addObject("book", existingBook);
        return mv;
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "book/form";

        bookService.saveOrUpdate(book);
        return "redirect:/books/list";
    }


}
