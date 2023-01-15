export default class BookService {
    getBooks = async () => {
        return await fetch('/datarest/books')
            .then(response => response.json());
    }

    getBook = async id => {
        return await fetch(id)
            .then(response => response.json())
    }

    remove = async book => {
        return await fetch(book._links.self.href, {
            method: 'DELETE'
        });
    }

    changeRelations = async (item, data) => {
        await fetch(data._links.author.href, {
            method: 'PUT',
            headers: {
                'Content-Type': 'text/uri-list'
            },
            body: item.author
        });

        await fetch(data._links.genre.href, {
            method: 'PUT',
            headers: {
                'Content-Type': 'text/uri-list'
            },
            body: item.genre
        });
    }

    change = async item => {

        await fetch(item._links.self.href, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        }).then(response => response.json())
            .then(async data => {
                await fetch(data._links.author.href, {
                    method: 'DELETE'
                });

                await fetch(data._links.genre.href, {
                    method: 'DELETE'
                });

                await this.changeRelations(item, data);
                return data;
            });
    }

    add = async item => {
        await fetch('/datarest/books', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item.name),
        }).then(response => response.json())
            .then(async data => {
                    await this.changeRelations(item, data);
                    return data;
                }
            );
    }
}