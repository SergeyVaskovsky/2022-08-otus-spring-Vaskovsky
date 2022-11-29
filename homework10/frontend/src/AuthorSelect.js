import React, {useEffect, useState} from 'react';
import {FormGroup, Label} from 'reactstrap';

export default function AuthorSelect({onSelectAuthor = f => f}) {

    const [authors, setAuthors] = useState([]);
    const [authorId, setAuthorId] = useState({});

    useEffect(() => {
        fetch(`/books/authors`)
            .then(response => response.json())
            .then(data => setAuthors(data));
    }, [setAuthors]);

    const handleChangeAuthor = value => {
        const tmpAuthors = authors.filter(function (author) {
            return author.id == value;
        });
        setAuthorId(tmpAuthors[0].authorId);
        onSelectAuthor(authorId);
    }

    return (
        <FormGroup>
            <Label for="author">Автор</Label><br/>
            <select
                id="authorDropDown"
                onChange={(event) => handleChangeAuthor(event.target.value)}>
                <option key={-1} value={-1} selected disabled>Выберите автора</option>
                {authors.map(author => <option key={author.id}
                                               value={author.id}
                                               selected={author.id == item.authorId}>{author.name}</option>)}
            </select>
        </FormGroup>
    );
}