import React from 'react';
import '../App.css';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

export default function Home() {
    return (
        <div>
            <Container fluid>
                <Button color="link"><Link to="/books">Книги</Link></Button>
            </Container>
        </div>
    );
}