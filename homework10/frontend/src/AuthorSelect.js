import React, {useEffect, useState} from 'react';
import {FormGroup, Label} from 'reactstrap';

export default function AuthorSelect({itemAuthorId, onSelectAuthor = f => f}) {

    const [authors, setAuthors] = useState([]);

    useEffect(() => {
        fetch(`/books/authors`)
            .then(response => response.json())
            .then(data => setAuthors(data));
    }, [setAuthors]);

    const handleChange = value => {
        const tmpAuthors = authors.filter(function (author) {
            return author.id == value;
        });
        onSelectAuthor(tmpAuthors[0]);
    }

    return (
        <FormGroup>
            <Label for="author">Автор</Label><br/>
            <select
                id="authorDropDown"
                onChange={(event) => handleChange(event.target.value)}>
                <option key={-1} value={-1} selected disabled>Выберите автора</option>
                {authors.map(author => <option key={author.id}
                                               value={author.id}
                                               selected={author.id == itemAuthorId}>{author.name}</option>)}
            </select>
        </FormGroup>
    );
}