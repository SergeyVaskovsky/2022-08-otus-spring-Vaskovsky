package ru.otus.homework11.changelogs;

import lombok.RequiredArgsConstructor;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;
import ru.otus.homework11.repository.GenreRepository;

@RequiredArgsConstructor
public class ApplicationStartup {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private Author pushkin = new Author("1", "Пушкин Александр Сергеевич");
    private Author kyte = new Author("2", "Кайт Том");
    private Author mcConnell = new Author("3", "Макконел Стивен");
    private Genre fiction = new Genre("1", "Фантастика");
    private Genre novell = new Genre("2", "Роман");
    private Genre science = new Genre("3", "Научпоп");
    private Book fictionAndOrdinaryPeople = new Book("1", "Фантастика и обычные люди", kyte, fiction);
    private Comment good = new Comment("1", "Хорошо", fictionAndOrdinaryPeople);
    private Comment bad = new Comment("2", "Плохо", fictionAndOrdinaryPeople);
    private Comment soso = new Comment("3", "Ни рыба не мясо", fictionAndOrdinaryPeople);
    private Book novellAndSomething = new Book("2", "Онегин и прочий бред", pushkin, novell);
    private Book code = new Book("3", "Совершенный код", mcConnell, science);

    public void start() {
        authorRepository.save(pushkin).block();
        authorRepository.save(kyte).block();
        authorRepository.save(mcConnell).block();
        genreRepository.save(fiction).block();
        genreRepository.save(novell).block();
        genreRepository.save(science).block();
        bookRepository.save(fictionAndOrdinaryPeople).block();
        bookRepository.save(novellAndSomething).block();
        bookRepository.save(code).block();
        commentRepository.save(good).block();
        commentRepository.save(bad).block();
        commentRepository.save(soso).block();
    }
}
