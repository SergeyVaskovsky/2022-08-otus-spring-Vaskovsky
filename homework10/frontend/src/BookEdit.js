import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import AuthorSelect from "./AuthorSelect";

export default function BookEdit() {

    const emptyItem = {
        name: '',
        authorId: -1,
        genreId: -1
    };
    const [item, setItem] = useState(emptyItem);

    const [genres, setGenres] = useState([]);
    let navigate = useNavigate();
    const {id} = useParams();

    useEffect(() => {
        fetch(`/books/authors`)
            .then(response => response.json())
            .then(data => setAuthors(data));

        fetch(`/books/genres`)
            .then(response => response.json())
            .then(data => setGenres(data));

        if (id !== 'new') {
            fetch(`/books/${id}`)
                .then(response => response.json())
                .then(data => setItem(data));
        }
    }, [id, setItem, setAuthors, setGenres]);

    const handleChange = value => {
        let book = {...item};
        book.name = value;
        setItem(book);
    }


    const handleChangeGenre = value => {
        const tmpGenres = genres.filter(function (genre) {
            return genre.id == value;
        });
        let book = {...item};
        book.genreId = tmpGenres[0].id;
        setItem(book);
    }

    const handleSubmit = async event => {
        event.preventDefault();
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

                    <AuthorSelect onSelectAuthor{(authorId) => {
                        let book = {...item};
                        book.authorId = authorId;
                        setItem(book);
                    }}
                    />

                    <Label for="author">Жанр</Label><br/>
                    <select
                        id="genreDropDown"
                        onChange={(event) => handleChangeGenre(event.target.value)}>
                        <option key={-1} value={-1} selected disabled>Выберите жанр</option>
                        {genres.map(genre => <option key={genre.id}
                                                     value={genre.id}
                                                     selected={genre.id == item.genreId}>{genre.name}</option>)}
                    </select>
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

