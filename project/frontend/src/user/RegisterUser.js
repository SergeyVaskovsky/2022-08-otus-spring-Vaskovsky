import {Button, Container, FormGroup, Input, Label} from 'reactstrap';
import React, {useState} from "react";
import UserService from "../service/UserService";
import {Link} from "react-router-dom";

export default function RegisterUser() {

    const [newUser, setNewUser] = useState({});
    const userService = new UserService();
    const handleChangeName = async value => {
        let changeableUser = {...newUser};
        changeableUser.name = value;
        setNewUser(changeableUser);
    }

    const handleChangeEmail = async value => {
        let changeableUser = {...newUser};
        changeableUser.email = value;
        setNewUser(changeableUser);
    }

    const handleChangePassword = async value => {
        let changeableUser = {...newUser};
        changeableUser.password = value;
        setNewUser(changeableUser);
    }

    const register = async () => {
        await userService.register(newUser).then(user => {
        })
    }

    return (
        <Container>
            <FormGroup>
                <Label for="name">Как Вас зовут</Label>
                <Input type="text" name="name" id="name" defaultValue=""
                       onChange={(event) => handleChangeName(event.target.value)}/>

                <Label for="email">Ваш e-mail</Label>
                <Input type="text" name="email" id="email" defaultValue={newUser.email || ""}
                       onChange={(event) => handleChangeEmail(event.target.value)}/>

                <Label for="password">Ваш пароль</Label>
                <Input type="password" name="password" id="password" defaultValue=""
                       onChange={(event) => handleChangePassword(event.target.value)}/>

                <Button size="sm" onClick={() => register()}>Зарегистрироваться</Button>
                <Button size="sm" color="secondary" tag={Link} to="/">Отменить</Button>
            </FormGroup>
        </Container>
    );
}