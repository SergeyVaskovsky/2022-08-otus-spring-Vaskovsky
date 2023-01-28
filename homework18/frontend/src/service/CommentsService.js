export default class CommentsService {

    getComments = async id => {
        return await fetch(`/api/books/${id}/comments`)
            .then(response => response.json());
    }

    remove = async id => {
        return await fetch(`/api/books/comments/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
            console.log(response.status + " " + response.ok);
            if (!response.ok) {
                throw new Error();
            }
        });
    }


    add = async (description, id) => {
        return await fetch(`/api/books/${id}/comments`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: description,
        }).then(response => response.json());
    }
}

