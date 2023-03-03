import React from 'react';
import '../App.css';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';
import AuthService from "../service/AuthService";

export default function Home() {
    return (
        <div>
            <Container fluid>
                {
                    AuthService.getCurrentAuthorities().includes("AUTHOR") ?
                        <Button color="link"><Link to="/poems">К редактированию стихов</Link></Button>
                        : ""
                }
                <Button color="link"><Link to="/readonly/poems">К стихам</Link></Button>
            </Container>
        </div>
    );
}