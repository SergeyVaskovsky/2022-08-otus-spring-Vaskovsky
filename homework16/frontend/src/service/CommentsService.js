export default class CommentsService {

    getComments = async book => {
        return await fetch(book._links.comments.href)
            .then(response => response.json());
    }

    remove = async href => {
        return await fetch(href, {
            method: 'DELETE'
        });
    }

    add = async (description, book) => {

        return await fetch('/datarest/comments', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(description)
        }).then(response => response.json())
            .then(data => {
                    fetch(data._links.book.href.replace('{?projection}', ''), {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'text/uri-list'
                        },
                        body: book._links.self.href
                    });
                    return data;
                });
    }
}

