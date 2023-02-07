import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../main/AppNavbar';
import PoemService from "../service/PoemService";
import Loading from "../main/Loading";

export default function PoemPictureElementEdit ({ state, onChangePictureState, onDeleteState }) {

    const [file, setFile] = useState();
    const handleChangeState = (event) => {
        setFile(URL.createObjectURL(event.target.files[0]));
        onChangePictureState(state.index, event.target.files[0]);
    };

    const handleDelete = () => {
        onDeleteState(state.index);
    };

    return (
        <div key = {state.index}>
            <Container>
                    <FormGroup>
                        <img src={file} />
                        <br/>
                        <input
                            type="file"
                            name="picture"
                            onChange={(event) => {handleChangeState(event)}}
                        />
                        <br/>
                        <Button color="secondary" onClick={() => handleDelete()}>Удалить</Button>
                    </FormGroup>
            </Container>
        </div>
    );
}