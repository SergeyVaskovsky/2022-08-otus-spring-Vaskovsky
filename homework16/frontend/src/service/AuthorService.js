export default class AuthorService {
    getAuthors = async () => {
        return await fetch(`/datarest/authors`)
            .then(response => response.json());
    }

    getAuthor = async href => {
        return await fetch(href)
            .then(response => response.json());
    }
}