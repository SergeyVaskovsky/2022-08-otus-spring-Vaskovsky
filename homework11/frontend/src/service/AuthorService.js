export default class AuthorService {
    getAuthors = async () => {
        return await fetch(`/api/authors`)
            .then(response => response.json());
    }
}