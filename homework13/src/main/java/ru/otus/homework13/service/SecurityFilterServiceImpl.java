package ru.otus.homework13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.otus.homework13.model.Book;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityFilterServiceImpl implements SecurityFilterService{
    private static final long FORBIDDEN_GENRE_ID = 1L;
    private static final GrantedAuthority ADMIN_ROLE_NAME = new SimpleGrantedAuthority("ADMIN");

    public boolean filter(Authentication authentication, List<Book> domain) {

        Iterator<Book> iValues = domain.iterator();
        while(iValues.hasNext()) {
            Book value = iValues.next();
            if (FORBIDDEN_GENRE_ID == value.getGenre().getId() &&
                    !authentication
                            .getAuthorities()
                            .contains(ADMIN_ROLE_NAME)) {
                iValues.remove();
            }
        }
        return true;
    }

    public boolean filter(Authentication authentication, Book book) {
        return FORBIDDEN_GENRE_ID != book.getGenre().getId() || authentication.getAuthorities().contains(ADMIN_ROLE_NAME);
    }
}
