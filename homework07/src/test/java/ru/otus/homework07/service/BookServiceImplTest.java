package ru.otus.homework07.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.homework07.dao.BookDao;
import ru.otus.homework07.model.Author;
import ru.otus.homework07.model.Book;
import ru.otus.homework07.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false",
        classes = {BookServiceImpl.class})
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookServiceImpl;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookDao bookDao;

    @Test
    void shouldReturnAllBooks() {
        when(bookDao.findAll()).thenReturn(
                List.of(
                        new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин")),
                        new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")),
                        new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(1L, "Наука"))
                )
        );
        List<Book> books = bookServiceImpl.getAll();
        assertThat(books.size()).isEqualTo(3);
    }

    @Test
    void shouldInsertBook() {
        Book book = new Book(0L, "Котики", new Author(1L, "Ученый"), new Genre(3L, "Наука"));
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин")));
        books.add(new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")));
        books.add(new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(3L, "Наука")));

        when(bookDao.findAll()).thenReturn(books);
        when(bookDao.save(book)).thenAnswer(a -> {
            books.add(book);
            return a.getArgument(0);
        });

        bookServiceImpl.upsert(0L, "Котики", 1L, 1L);
        Book actualBook = bookServiceImpl
                .getAll()
                .stream()
                .filter(b -> b.getId() == 0L)
                .findFirst()
                .orElse(null);
        assertThat(actualBook).isNotNull();
        assertThat(actualBook.getId()).isEqualTo(0L);
    }

    @Test
    void shouldDeleteBook() {
        Book bookToDelete = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        List<Book> books = new ArrayList<>();
        books.add(bookToDelete);
        books.add(new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")));
        books.add(new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(3L, "Наука")));

        when(bookDao.findAll()).thenReturn(books);
        when(bookDao.findById(bookToDelete.getId())).thenReturn(Optional.of(bookToDelete));

        doAnswer(invocation -> {
            books.remove(bookToDelete);
            return null;
        }).when(bookDao).delete(bookToDelete);

        bookServiceImpl.delete(bookToDelete.getId());
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == bookToDelete.getId())).isEqualTo(false);
    }

    @Test
    void shouldUpdateBook() {
        Book bookToUpdate = new Book(1L, "Роман", new Author(1L, "Писатель"), new Genre(1L, "Для женщин"));
        List<Book> books = new ArrayList<>();
        books.add(bookToUpdate);
        books.add(new Book(2L, "Повесть", new Author(2L, "Писатель 2"), new Genre(2L, "Беллитристика")));
        books.add(new Book(3L, "Статья", new Author(3L, "Ученый"), new Genre(3L, "Наука")));

        when(bookDao.findAll()).thenReturn(books);
        doAnswer(invocation -> {
            bookToUpdate.setName("Хроники");
            bookToUpdate.setAuthor(new Author(3L, "Ученый"));
            bookToUpdate.setGenre(new Genre(3L, "Наука"));
            return null;
        }).when(bookDao).save(bookToUpdate);

        bookServiceImpl.upsert(1L, "Хроники", 3L, 3L);
        assertThat(bookServiceImpl
                .getAll()
                .stream()
                .anyMatch(b -> b.getId() == 1L &&
                        "Хроники".equals(b.getName()) &&
                        "Ученый".equals(b.getAuthor().getName()) &&
                        "Наука".equals(b.getGenre().getName())
                )).isEqualTo(true);
    }
}