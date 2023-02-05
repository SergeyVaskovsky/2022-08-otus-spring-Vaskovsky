import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemTextElementEdit() {


    return (
        <div>
            <Container>
                <Form onSubmit={(event) => {}}>
                    <FormGroup>
                        <textarea type="text" name="title" id="title"/>
                        <br/>
                        <Button color="secondary">Удалить</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}