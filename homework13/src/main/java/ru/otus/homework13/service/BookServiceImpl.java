package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.homework13.exception.BookNotFoundException;
import ru.otus.homework13.model.Author;
import ru.otus.homework13.model.Book;
import ru.otus.homework13.model.Genre;
import ru.otus.homework13.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookRepository bookRepository;
    private final CommentService commentService;
    private final MutableAclService mutableAclService;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(
                String.format("Book with id = %d not found", bookId)
        ));
    }

    @Transactional
    @Override
    public Book upsert(long bookId, String bookName, long authorId, long genreId) {
        Author author = authorService.getById(authorId);
        Genre genre = genreService.getById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        Book savedBook = bookRepository.save(book);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid( authentication );
        ObjectIdentity oid = new ObjectIdentityImpl( savedBook.getClass(), savedBook.getId() );

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl( oid );
        acl.setOwner( owner );
        acl.insertAce( acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true );

        mutableAclService.updateAcl( acl );


        return savedBook;
    }

    @Transactional
    @Override
    public void delete(long bookId) {
        commentService.deleteAll(commentService.getAll(bookId));
        bookRepository.deleteById(bookId);
    }

}
