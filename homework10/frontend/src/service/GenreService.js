export default class GenreService {
    getGenres = async () => {
        return await fetch(`/api/genres`)
            .then(response => response.json());
    }
}