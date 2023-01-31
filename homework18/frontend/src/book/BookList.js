import React, {useEffect, useState} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import {Link} from 'react-router-dom';
import Comments from "../comment/Comments";
import BookService from "../service/BookService";
import Loading from "../main/Loading";

export default function BookList() {

    const [books, setBooks] = useState([]);
    const [isShow, setIsShow] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [currentBook, setCurrentBook] = useState({});
    const bookService = new BookService();
    const [available, setAvailable] = useState(true);

    useEffect(() => {
        bookService.getBooks()
            .then(data => {
                setBooks(data);
                setIsLoading(false);
            });
    }, []);

    const remove = async id => {
        setAvailable(true);
        setIsLoading(true);
        bookService.remove(id).then(() => {
            let updatedBooks = [...books].filter(i => i.id !== id);
            setBooks(updatedBooks);
            if (currentBook.id === id) {
                setIsShow(false);
                setCurrentBook({});
            }
            setIsLoading(false);
        }).catch((error) => {
            setAvailable(false);
            setIsLoading(false);
        });
    }

    const showComments = book => {
        setIsShow(true);
        setCurrentBook(book);
    }

    const bookList =
        books.map(book => {
            return <tr key={book.id}>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td>{book.authorName}</td>
                <td>{book.genreName}</td>
                <td> {book.id !== 0 ?
                    <ButtonGroup>
                        <Button size="sm" color="primary" onClick={() => showComments(book)}>Отзывы</Button>
                    </ButtonGroup> : ""
                }
                </td>
                <td> {book.id !== 0 ?
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Изменить</Button>
                        <Button size="sm" color="danger" onClick={() => remove(book.id)}>Удалить</Button>
                    </ButtonGroup> : ""
                }
                </td>
            </tr>
        });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <Loading isLoading={isLoading}/>
                <h2>Книги</h2>
                {available ?
                    <div>
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
                        {isShow &&
                            <Comments book={currentBook}/>
                        }
                    </div> : "По техническим причинам удалить книгу из библиотеки не получилось. Попробуйте повторить позднее"}
            </Container>

        </div>
    );

}