package ru.otus.homework11.changelogs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.otus.homework11.model.Author;
import ru.otus.homework11.model.Book;
import ru.otus.homework11.model.Comment;
import ru.otus.homework11.model.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;
import ru.otus.homework11.repository.GenreRepository;

@Component
@RequiredArgsConstructor
public class DbInitializer {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public void initDb() {
        Author pushkin = new Author("1", "Пушкин Александр Сергеевич");
        Author kyte = new Author("2", "Кайт Том");
        Author mcConnell = new Author("3", "Макконел Стивен");
        Genre fiction = new Genre("1", "Фантастика");
        Genre novell = new Genre("2", "Роман");
        Genre science = new Genre("3", "Научпоп");
        Book fictionAndOrdinaryPeople = new Book("1", "Фантастика и обычные люди", kyte, fiction);
        Comment good = new Comment("1", "Хорошо", fictionAndOrdinaryPeople);
        Comment bad = new Comment("2", "Плохо", fictionAndOrdinaryPeople);
        Comment soso = new Comment("3", "Ни рыба не мясо", fictionAndOrdinaryPeople);
        Book novellAndSomething = new Book("2", "Онегин и прочий бред", pushkin, novell);
        Book code = new Book("3", "Совершенный код", mcConnell, science);
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
