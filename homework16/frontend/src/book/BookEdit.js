import React, {useEffect, useState} from 'react';
import {Link, useLocation, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import AuthorSelect from "../author/AuthorSelect";
import GenreSelect from "../genre/GenreSelect";
import BookService from "../service/BookService";
import AuthorService from "../service/AuthorService";
import GenreService from "../service/GenreService";

export default function BookEdit() {

    const emptyItem = {
        name: '',
        author: '',
        genre: ''
    };
    const [item, setItem] = useState(emptyItem);
    let navigate = useNavigate();
    const {id} = useParams();

    const location = useLocation()
    const book = location.state;

    const bookService = new BookService();
    const authorService = new AuthorService();
    const genreService = new GenreService();

    useEffect(() => {
        if (id !== 'new') {
            bookService.getBook(book._links.book.href.replace('{?projection}', '?projection=customBook'))
                .then(async data => {
                    let item = data;
                    await authorService.getAuthor(data._links.author.href).then(data => item.author = data._links.self.href);
                    await genreService.getGenre(data._links.genre.href).then(data => item.genre = data._links.self.href);
                    setItem(item);
                });
        }
    }, [id, setItem, book]);

    const handleChange = value => {
        let bookToChange = {...item};
        bookToChange.name = value;
        setItem(bookToChange);
    }

    const handleSubmit = async event => {
        event.preventDefault();
        if (id !=='new' ) {
            await bookService.change(item);
        } else {
            await bookService.add(item)
        }
        navigate('/books');

    }

    return (
        <div>
            <AppNavbar/>
            <Container>
                <h2>{item.id !== 'new' ? 'Изменить книгу' : 'Добавить книгу'}</h2>
                <Form onSubmit={(event) => {
                    handleSubmit(event)
                }}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={(event) => handleChange(event.target.value)} autoComplete="name"/>
                    </FormGroup>

                    <AuthorSelect
                        itemAuthorId = {item.author}
                        onSelectAuthor={(author) => {
                            item.author = author;
                            setItem(item);
                        }}
                    />

                    <GenreSelect
                        itemGenreId={item.genre}
                        onSelectGenre={(genre) => {
                            item.genre = genre;
                            setItem(item);
                        }}
                    />

                    <br/>
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Отмена</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

