export default class BookService {
    getBooks = async () => {
        return await fetch('/api/books')
            .then(response => response.json());
    }

    getBook = async id => {
        return await fetch(`/api/books/${id}`)
            .then(response => response.json())
    }

    remove = async id => {
        return await fetch(`/api/books/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (!response.ok) {
                throw new Error();
            }
        });
    }

    save = async item => {
        return await fetch('/api/books' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        }).then(response => response.json());
    }
}