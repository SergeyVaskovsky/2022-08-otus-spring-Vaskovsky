export default class CommentsService {

    getComments = async id => {
        return await fetch(`/books/${id}/comments`)
            .then(response => response.json());
    }

    remove = async id => {
        return await fetch(`/books/comments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
    }

    add = async (description, id) => {
        return await fetch(`/books/${id}/comments`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: description,
        }).then(response => response.json());
    }
}

