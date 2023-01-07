export default class AuthorService {
    getAuthors = async () => {
        return await fetch(`/datarest/authors`)
            .then(response => {console.log(response); return response.json()});
    }

    getAuthor = async href => {
        return await fetch(href)
            .then(response => response.json());
    }
}