export default class GenreService {
    getGenres = async () => {
        return await fetch(`/datarest/genres`)
            .then(response => response.json());
    }

    getGenre = async href => {
        return await fetch(href)
            .then(response => response.json());
    }
}