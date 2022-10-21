package ru.otus.homework07.command;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Comment;
import ru.otus.homework07.model.Genre;
import ru.otus.homework07.service.*;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class Commands {

    private final BookService bookService;
    private final OutputService outputService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    @ShellMethod(value = "Books", key = {"b", "books"})
    public void getBooks() {
        List<Book> books = bookService.getAll();
        books.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s, author: %s, genre: %s",
                        book.getId(),
                        book.getName(),
                        book.getAuthor().getName(),
                        book.getGenre().getName()))
        );
    }

    @ShellMethod(value = "Insert, params delimited by space: book name, author id, genre id", key = {"i", "insert"})
    public void insertBook(
            @ShellOption() String bookName,
            @ShellOption() long authorId,
            @ShellOption() long genreId
    ) {
        bookService.upsert(0, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Delete, params: book id", key = {"d", "delete"})
    public void deleteBook(
            @ShellOption() long bookId
    ) {
        bookService.delete(bookId);
    }

    @ShellMethod(value = "Update, params delimited by space: book id, book name, author id, genre id", key = {"u", "update"})
    public void updateBook(
            @ShellOption() long bookId,
            @ShellOption() String bookName,
            @ShellOption() long authorId,
            @ShellOption() long genreId
    ) {
        bookService.upsert(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Authors", key = {"a", "authors"})
    public void getAuthors() {
        List<Author> authors = authorService.getAll();
        authors.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s",
                        book.getId(),
                        book.getName()))
        );
    }

    @ShellMethod(value = "Genres", key = {"g", "genres"})
    public void getGenres() {
        List<Genre> genres = genreService.getAll();
        genres.forEach(book ->
                outputService.outputString(String.format("id: %d, name: %s",
                        book.getId(),
                        book.getName()))
        );
    }

    @ShellMethod(value = "Comments, param: book id", key = {"c", "comments"})
    public void getComments(
            @ShellOption() long bookId
    ) {
        List<Comment> comments = commentService.getAll(bookId);
        comments.forEach(comment ->
                outputService.outputString(String.format("id: %d, comment: %s, book: %s",
                        comment.getId(),
                        comment.getDescription(),
                        comment.getBook().getName()))
        );
    }

    @ShellMethod(value = "Insert comments, params delimited by space: description, book id", key = {"ic"})
    public void getInsertComment(
            @ShellOption() String description,
            @ShellOption() long bookId
    ) {
        commentService.upsert(0, description, bookId);
    }

    @ShellMethod(value = "Delete comment, params: comment id", key = {"dc"})
    public void deleteComment(
            @ShellOption() int commentId
    ) {
        commentService.delete(commentId);
    }

}

