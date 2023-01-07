import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link, useNavigate} from 'react-router-dom';
import Comments from "../comment/Comments";
import BookService from "../service/BookService";

export default function BookList() {

    const [books, setBooks] = useState([]);
    const [isShow, setIsShow] = useState(false);
    const [currentBook, setCurrentBook] = useState(undefined);
    const bookService = new BookService();

    useEffect(() => {
        bookService.getBooks()
            .then(data => setBooks(data._embedded.books));
    }, [setBooks]);

    const remove = async book => {
        bookService.remove(book).then(() => {
            let updatedBooks = [...books].filter(b =>
                b._links.self.href !== book._links.self.href
            );
            if (currentBook && currentBook._links.self.href === book._links.self.href) {
                setIsShow(false);
                setCurrentBook(undefined);
            }
            setBooks(updatedBooks);
        });
    }

    const showComments = book => {
        setIsShow(true);
        console.log(book);
        setCurrentBook(book);
    }

    const bookList =
        books.map(book => {
            return <tr key={book._links.self.href}>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td>{book.authorName}</td>
                <td>{book.genreName}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" onClick={() => showComments(book)}>Отзывы</Button>
                    </ButtonGroup>
                </td>
                <td>
                    <ButtonGroup>
                        <Link
                            to = "/books/edit"
                            state = {book}
                            >
                            <Button size="sm" color="primary">Изменить</Button>
                        </Link>
                        <Button size="sm" color="danger" onClick={() => remove(book)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <h2>Книги</h2>
                <div className="float-right">
                    <Button color="success" tag={Link} to="/books/new">Добавить книгу</Button>
                </div>

                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="50%">Название</th>
                        <th width="15%">Автор</th>
                        <th width="15%">Жанр</th>
                        <th width="10%">Отзывы</th>
                        <th width="10%">Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    {bookList}
                    </tbody>
                </Table>
            </Container>
            {isShow && currentBook &&
                <Comments book={currentBook}/>
            }
        </div>
    );

}