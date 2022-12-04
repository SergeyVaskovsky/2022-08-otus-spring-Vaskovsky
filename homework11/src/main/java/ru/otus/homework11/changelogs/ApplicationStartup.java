package ru.otus.homework11.changelogs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
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
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
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
        authorRepository.save(pushkin).subscribe();
        authorRepository.save(kyte).subscribe();
        authorRepository.save(mcConnell).subscribe();
        genreRepository.save(fiction).subscribe();
        genreRepository.save(novell).subscribe();
        genreRepository.save(science).subscribe();
        bookRepository.save(fictionAndOrdinaryPeople).subscribe();
        bookRepository.save(novellAndSomething).subscribe();
        bookRepository.save(code).subscribe();
        commentRepository.save(good).subscribe();
        commentRepository.save(bad).subscribe();
        commentRepository.save(soso).subscribe();
    }
}
