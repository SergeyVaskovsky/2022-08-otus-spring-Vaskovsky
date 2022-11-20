import React, {Component} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';

class BookEdit extends Component {

    emptyItem = {
        name: '',
        author: {id: -1},
        genre: {id: -1}
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            authors: [],
            genres: [],
            currentAuthor: {}
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleChangeAuthor = this.handleChangeAuthor.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        const authors = await (await fetch(`/books/authors`)).json();
        this.setState({authors: authors});

        const genres = await (await fetch(`/books/genres`)).json();
        this.setState({genres: genres});

        if (this.props.id !== 'new') {
            const book = await (await fetch(`/books/${this.props.id}`)).json();
            this.setState({item: book});
            this.setState({currentAuthor: book.author});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleChangeAuthor(event) {
        const value = this.state.authors.filter(function (author) {
            return author.id == event.target.value;
        });
        let item = {...this.state.item};
        item.author = value[0];
        this.setState({item});
        const currentAuthor = value[0]
        this.setState({currentAuthor})
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        await fetch('/books' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.navigate('/books');
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Изменить книгу' : 'Добавить книгу'}</h2>;
        const {currentAuthor} = this.state.item.author;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Название</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="author">Автор</Label>
                        <select
                            id="authorDropDown"
                            onChange={this.handleChangeAuthor}
                            value={currentAuthor}
                        >
                            <option key={-1} value={-1} disabled>Выберите автора</option>
                            {this.state.authors.map(author => <option key={author.id}
                                                                      value={author.id}>{author.name}</option>)}
                        </select>
                        {/*<Input type="text" name="author" id="author" value={item.author.name || ''}
                               onChange={this.handleChange} autoComplete="author"/>*/}
                    </FormGroup>
                    {/*<FormGroup>
                        <Label for="genre">Жанр</Label>
                        <Input type="text" name="genre" id="genre" value={item.genre.name || ''}
                               onChange={this.handleChange} autoComplete="genre"/>
                    </FormGroup>*/}
                    <FormGroup>
                        <Button color="primary" type="submit">Сохранить</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Отмена</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

function BookEditWithNavigate(props) {
    let navigate = useNavigate();
    const {id} = useParams();
    return <BookEdit {...props} navigate={navigate} id={id}/>
}

export default BookEditWithNavigate