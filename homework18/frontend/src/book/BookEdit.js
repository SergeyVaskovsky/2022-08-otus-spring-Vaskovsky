import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import AuthorSelect from "../author/AuthorSelect";
import GenreSelect from "../genre/GenreSelect";
import BookService from "../service/BookService";
import Loading from "../main/Loading";

export default function BookEdit() {

    const emptyItem = {
        name: '',
        authorId: -1,
        genreId: -1
    };
    const [item, setItem] = useState(emptyItem);
    let navigate = useNavigate();
    const {id} = useParams();
    const bookService = new BookService();
    const [crash, setCrash] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        setCrash(false);
        setIsLoading(false);
        if (id !== 'new') {
            setIsLoading(true);
            bookService.getBook(id)
                .then(data => {
                    setIsLoading(false);
                    if (data.id === 0) {
                        setCrash(true);
                    }

                    setItem(data);
                });
        }
    }, [id, setItem]);

    const handleChange = value => {
        let book = {...item};
        book.name = value;
        setItem(book);
    }

    const handleSubmit = async event => {
        event.preventDefault();
        if (item.id === 0 || item.authorId === 0 || item.genreId === 0) {
            setCrash(true);
            return;
        }
        setIsLoading(true);
        await bookService.save(item).then(book => {
            setIsLoading(false);
            if (!book || book.id === 0) {
                setCrash(true);
            } else {
                navigate('/books');
            }
        })
    }

    return (
        <div>
            <AppNavbar/>
            <Container>
                <Loading isLoading={isLoading}/>
                <h2>{item.id ? 'Изменить книгу' : 'Добавить книгу'}</h2>
                <Form onSubmit={(event) => {
                    handleSubmit(event)
                }}>
                    {!crash ?
                        <div>
                            <FormGroup>
                                <Label for="name">Название</Label>
                                <Input type="text" name="name" id="name" value={item.name || ''}
                                       onChange={(event) => handleChange(event.target.value)} autoComplete="name"/>
                            </FormGroup>

                            <AuthorSelect
                                itemAuthorId={item.authorId}
                                onSelectAuthor={(author) => {
                                    item.authorId = author.id;
                                    item.authorName = author.name;
                                    setItem(item);
                                }}
                            />

                            <GenreSelect
                                itemGenreId={item.genreId}
                                onSelectGenre={(genre) => {
                                    item.genreId = genre.id;
                                    item.genreName = genre.name;
                                    setItem(item);
                                }}
                            />

                            <br/>
                        </div> :
                        "Сервис недоступен. Попробуйте позже."
                    }
                    <FormGroup>
                        {!crash ? <Button color="primary" type="submit">Сохранить</Button> : ""}
                        <Button color="secondary" tag={Link} to="/books">Отмена</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

