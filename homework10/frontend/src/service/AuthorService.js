export default class AuthorService {
    getAuthors = async () => {
        return await fetch(`/books/authors`)
            .then(response => response.json());
    }
}