import React, {useEffect, useState} from 'react';
import {FormGroup, Label} from 'reactstrap';
import AuthorService from "../service/AuthorService";

export default function AuthorSelect({itemAuthorId, onSelectAuthor = f => f}) {

    const [authors, setAuthors] = useState([]);
    const authorService = new AuthorService();

    useEffect(() => {
        authorService.getAuthors()
            .then(data => {console.log(data); return setAuthors(data._embedded.authors);});
    }, [setAuthors]);

    const handleChange = value => {
        const tmpAuthors = authors.filter(function (author) {
            return author._links.self.href === value;
        });
        onSelectAuthor(tmpAuthors[0]._links.self.href);
    }

    return (
        <FormGroup>
            <Label for="author">Автор</Label><br/>
            <select
                id="authorDropDown"
                onChange={(event) => handleChange(event.target.value)}>
                <option key={-1} value={-1} selected disabled>Выберите автора</option>
                {authors.map(author => <option key={author._links.self.href}
                                               value={author._links.self.href}
                                               selected={author._links.self.href === itemAuthorId}>{author.name}</option>)}
            </select>
        </FormGroup>
    );
}