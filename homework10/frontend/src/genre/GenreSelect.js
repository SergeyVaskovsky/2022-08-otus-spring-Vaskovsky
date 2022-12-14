import React, {useEffect, useState} from 'react';
import {FormGroup, Label} from 'reactstrap';
import GenreService from "../service/GenreService";

export default function GenreSelect({itemGenreId, onSelectGenre = f => f}) {

    const [genres, setGenres] = useState([]);
    const genreService = new GenreService();

    useEffect(() => {
        genreService.getGenres()
            .then(data => setGenres(data));
    }, [setGenres]);

    const handleChange = value => {
        const tmpGenres = genres.filter(function (genre) {
            return genre.id == value;
        });
        onSelectGenre(tmpGenres[0]);
    }

    return (
        <FormGroup>
            <Label for="genre">Жанр</Label><br/>
            <select
                id="genreDropDown"
                onChange={(event) => handleChange(event.target.value)}>
                <option key={-1} value={-1} selected disabled>Выберите жанр</option>
                {genres.map(genre => <option key={genre.id}
                                             value={genre.id}
                                             selected={genre.id == itemGenreId}>{genre.name}</option>)}
            </select>
        </FormGroup>
    );
}