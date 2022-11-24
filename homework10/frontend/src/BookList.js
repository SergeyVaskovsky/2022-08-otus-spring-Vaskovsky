import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import Comments from "./Comments";


class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {books: [], show: false, currentBook: {}};
        this.remove = this.remove.bind(this);
        this.showComments = this.showComments.bind(this);
    }

    componentDidMount() {
        fetch('/books')
            .then(response => response.json())
            .then(data => this.setState({books: data}));
    }

    async remove(id) {
        await fetch(`/books/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedBooks = [...this.state.books].filter(i => i.id !== id);
            this.setState({books: updatedBooks});
            if (this.state.currentBook.id === id) {
                this.setState({show: false, currentBook: {}})
            }
        });
    }

    showComments(book) {
        this.setState({show: true, currentBook: book})
        this.forceUpdate();
    }

    render() {
        const {books} = this.state;

        const bookList = books.map(book => {
            return <tr key={book.id}>
                <td style={{whiteSpace: 'nowrap'}}>{book.name}</td>
                <td>{book.authorName}</td>
                <td>{book.genreName}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" onClick={() => this.showComments(book)}>Отзывы</Button>
                    </ButtonGroup>
                </td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Изменить</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(book.id)}>Удалить</Button>
                    </ButtonGroup>
                </td>
            </tr>

        });


        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h2>Книги</h2>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/books/new">Добавить книгу</Button>
                    </div>

                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="50%">Название</th>
                            <th width="15%">Автор</th>
                            <th width="15%">Жанр</th>
                            <th width="10%">Отзывы</th>
                            <th width="10%">Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {bookList}
                        </tbody>
                    </Table>
                </Container>
                {this.state.show &&
                    <Comments book={this.state.currentBook}/>
                }
            </div>
        );
    }
}

export default BookList;