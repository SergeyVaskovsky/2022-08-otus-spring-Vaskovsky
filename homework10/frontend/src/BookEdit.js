import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import AuthorSelect from "./AuthorSelect";
import GenreSelect from "./GenreSelect";

export default function BookEdit() {

    const emptyItem = {
        name: '',
        authorId: -1,
        genreId: -1
    };
    const [item, setItem] = useState(emptyItem);
    let navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/books/${id}`)
                .then(response => response.json())
                .then(data => setItem(data));
        }
    }, [id, setItem]);

    const handleChange = value => {
        let book = {...item};
        book.name = value;
        setItem(book);
    }

    const handleSubmit = async event => {
        event.preventDefault();
        console.log(item);
        await fetch('/books' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        navigate('/books');
    }

    return (
        <div>
            <AppNavbar/>
            <Container>
                <h2>{item.id ? 'Изменить книгу' : 'Добавить книгу'}</h2>
                <Form onSubmit={(event) => {
                    handleSubmit(event)
                }}>
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
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Отмена</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

