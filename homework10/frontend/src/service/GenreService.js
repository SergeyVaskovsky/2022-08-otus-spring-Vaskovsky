export default class GenreService {
    getGenres = async () => {
        return await fetch(`/books/genres`)
            .then(response => response.json());
    }
}