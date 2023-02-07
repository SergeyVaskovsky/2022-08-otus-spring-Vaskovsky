import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemTextElementEdit ({ state, onChangeTextState, onDeleteTextState }) {

    const handleChangeState = (value) => {
        onChangeTextState(state.index, value);
    };

    const handleDeleteText = () => {
        onDeleteTextState(state.index);
    };

    return (
        <div key = {state.index}>
            <Container>
               {/* <Form onSubmit={(event) => {}}>*/}
                    <FormGroup>
                        <textarea type="text" name="title" id="title" defaultValue={state.element.content} onChange={(event) => handleChangeState(event.target.value)}/>
                        <br/>
                        <Button color="secondary" onClick={() => handleDeleteText()}>Удалить</Button>
                    </FormGroup>
                {/*</Form>*/}
            </Container>
        </div>
    );
}