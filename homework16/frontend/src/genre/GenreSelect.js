import React, {useEffect, useState} from 'react';
import {FormGroup, Label} from 'reactstrap';
import GenreService from "../service/GenreService";

export default function GenreSelect({itemGenreId, onSelectGenre = f => f}) {

    const [genres, setGenres] = useState([]);
    const genreService = new GenreService();

    useEffect(() => {
        genreService.getGenres()
            .then(data => setGenres(data._embedded.genres));
    }, [setGenres]);

    const handleChange = value => {
        const tmpGenres = genres.filter(function (genre) {
            return genre._links.self.href === value;
        });
        onSelectGenre(tmpGenres[0]._links.self.href);
    }

    return (
        <FormGroup>
            <Label for="genre">Жанр</Label><br/>
            <select
                id="genreDropDown"
                onChange={(event) => handleChange(event.target.value)}>
                <option key={-1} value={-1} selected disabled>Выберите жанр</option>
                {genres.map(genre => <option key={genre._links.self.href}
                                             value={genre._links.self.href}
                                             selected={genre._links.self.href === itemGenreId}>{genre.name}</option>)}
            </select>
        </FormGroup>
    );
}